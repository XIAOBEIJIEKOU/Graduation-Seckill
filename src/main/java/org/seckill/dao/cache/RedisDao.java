package org.seckill.dao.cache;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import org.seckill.entity.Seckill;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.util.List;

/**
 * @Author Zhuang YeFan
 * @Date
 * @Description
 **/
public class RedisDao {

    private JedisPool jedisPool;

    private RuntimeSchema<Seckill> seckillRuntimeSchema = RuntimeSchema.createFrom(Seckill.class);

    public RedisDao(String ip, int port){
        jedisPool = new JedisPool(ip,port);
    }

    /**
     * 依据seckillId 获得商品信息
     * @param seckillId
     * @return
     */
    public Seckill getSeckill(long seckillId){
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+seckillId;
        //  redis并没有实现内部序列化，按byte[]存储，所以我们需要自定义反序列化获得相应对象的二进制数组
        byte[] bytes = jedis.get(key.getBytes());
        if (bytes != null){
            //创造一个新的空对象（message），然后将reids缓存的bytes数组反序列化构造这个空的对象
            Seckill seckill = seckillRuntimeSchema.newMessage();
            ProtostuffIOUtil.mergeFrom(bytes, seckill, seckillRuntimeSchema);
            return seckill;
        }
        jedis.close();
        return null;
    }

    /**
     * 将 Seckill 对象执行序列化成byte[] 然后存储到redis中
     * @param seckill
     */
    public String putSeckill(Seckill seckill){
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+seckill.getSeckillId();
        //  将seckill对象按照schema定义的模型转成byte[]，其中需要缓存器加快效率
        byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,seckillRuntimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        //  设置有失效时间的缓存，返回缓存结果
        String result = jedis.setex(key.getBytes(),60 * 60,bytes);
        jedis.close();
        return result;
    }

    public void deleteSeckill(Seckill seckill){
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+seckill.getSeckillId();
        jedis.del(key);
        jedis.close();
    }

    public boolean executeSeckill(Seckill seckill , long userPhone){
        Jedis jedis = jedisPool.getResource();
        Transaction transaction = jedis.multi();
        String recordKey = "seckillRecord:" + seckill.getSeckillId();
        //  记录购买明细
        transaction.sadd(recordKey , String.valueOf(userPhone));
        //  减库存
        int reduceStockNum = seckill.getNumber() - 1;
        seckill.setNumber(reduceStockNum);
        String reduceNumKey = "seckill:"+seckill.getSeckillId();
        byte[] bytes = ProtostuffIOUtil.toByteArray(seckill,seckillRuntimeSchema, LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE));
        transaction.set(reduceNumKey.getBytes() , bytes);
        //  事务执行
        List<Object> list = transaction.exec();
        jedis.close();
        if (list != null){
            //返回给 controller 层dto
            return true;
        }else{
            return false;
        }
    }


    /**
     * set保存秒杀记录
     * @param seckillId
     * @param userPhone
     * @return
     */
    public long saveSeckillRecord(long seckillId ,long userPhone){
        Jedis jedis = jedisPool.getResource();
        String key = "seckillRecord:" + seckillId;
        long result = jedis.sadd(key, String.valueOf(userPhone));
        jedis.close();
        return result;
    }

    /**
     * 若存在返回true
     * @param seckillId
     * @param userPhone
     * @return
     */
    public boolean isRepeatSeckill(long seckillId , long userPhone){
        Jedis jedis = jedisPool.getResource();
        String key = "seckillRecord:" + seckillId;
        boolean flag = jedis.sismember(key, String.valueOf(userPhone));
        jedis.close();
        return flag;
    }

    public String getSeckillStock(long seckillId){
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+ seckillId + "stock";
        String seckillStock = jedis.get(key);
        if (seckillStock != null){
            return seckillStock;
        }
        return null;
    }

    public String putSeckillStock(long seckillId, int stock){
        Jedis jedis = jedisPool.getResource();
        String key = "seckill:"+ seckillId + "stock";
        String result = jedis.set(key , String.valueOf(stock));
        return result;
    }
}
