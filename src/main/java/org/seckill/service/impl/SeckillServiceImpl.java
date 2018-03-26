package org.seckill.service.impl;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seckill.dao.SeckillDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.entity.SuccessKilled;
import org.seckill.enums.StateEnums;
import org.seckill.exception.RepeatException;
import org.seckill.exception.SeckillClose;
import org.seckill.exception.SeckillException;
import org.seckill.jms.ProducerService;
import org.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

@Service
public class SeckillServiceImpl implements SeckillService {
	// 日志
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	 * 在spring容器中查找已经存在的SeckillDao，SuccessKilledDao
	 * 找到之后就自动注入到service中使用
	 * 不需要自己 new 一个对象出来
	 */
	@Autowired
	private SeckillDao seckillDao;
	@Autowired
	private SuccessKilledDao successKilledDao;
	@Autowired
	private RedisDao redisDao;
	@Autowired
	private ProducerService producerService;

	// 盐值
	private final String salt = "fmjsaoij544654";

	// 只是内部使用不想暴露 所以private ，生成md5值
	private String getMd5(long seckillId) {
		String base = seckillId + "/" + salt;
		return DigestUtils.md5DigestAsHex(base.getBytes());
	}

	public List<Seckill> getSeckillList() {
		return seckillDao.queryAll(0, 10);
	}

	public Seckill getSeckill(long seckillId) {
		return seckillDao.queryById(seckillId);
	}

	/**
	 * 使用redis 暴露秒杀接口
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrlWithRedis(long seckillId) {
		//	每一个请求秒杀都会经过这个暴露的接口，在这边优化
		Seckill seckill = redisDao.getSeckill(seckillId);
		if (seckill == null) {
			seckill = seckillDao.queryById(seckillId);
			if (seckill == null) {
				return new Exposer(false, seckillId);
			}
			redisDao.putSeckill(seckill);
		}
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		//不在秒杀时间段内
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 不使用redis 缓存暴露秒杀接口
	 * @param seckillId
	 * @return
	 */
	public Exposer exportSeckillUrlNoRedis(long seckillId) {
		//	每一个请求秒杀都会经过这个暴露的接口，在这边优化
		Seckill seckill = seckillDao.queryById(seckillId);
		Date startTime = seckill.getStartTime();
		Date endTime = seckill.getEndTime();
		Date nowTime = new Date();
		//不在秒杀时间段内
		if (nowTime.getTime() < startTime.getTime() || nowTime.getTime() > endTime.getTime()) {
			return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
		}
		String md5 = getMd5(seckillId);
		return new Exposer(true, md5, seckillId);
	}

	/**
	 * 适用注解控制事务的优点：
	 * 1.开发团队达成一直约定，形成规范，而不是一次配置永久生效（@Autowires把所有的数据库操作全部作为事务）
	 * 2.保证事务的执行时间尽可能短，不要穿插其他网络操作HTTP等或者剥离到方法外部类似于 getMd5()
	 * 3.只有一条操作或者只有查询的时候不需要并发和事务
	 */
	/**
	 * 不用redis 缓存直接秒杀
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @throws SeckillException
	 * @throws RepeatException
	 * @throws SeckillClose
	 */
	@Transactional
	public SeckillExecution executeSeckillNoRedis(long seckillId, long userPhone) throws SeckillException, RepeatException, SeckillClose {
	    Date now = new Date();
		try {
			//  记录购买明细
			int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertCount == 0) {
				throw new RepeatException("重复秒杀");
			}
			//  减库存
			int updateCount = seckillDao.reduceNumber(seckillId, now);
			if (updateCount == 0) {
				throw new SeckillClose("秒杀结束");
			}
			SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
			return new SeckillExecution(seckillId, StateEnums.SUCCESS, successKilled);
		} catch (SeckillClose seckillClose) {
			throw seckillClose;
		} catch (RepeatException repeatException) {
			throw repeatException;
		} catch (Exception e) {
			//  数据库连接断了，服务器挂了。。。
			throw new SeckillException("系统错误");
		}
	}

	/**
	 * 还是直接操作数据库，只不过更新一次数据库即更新一次redis，进入秒杀操作前先判断库存
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @throws SeckillException
	 * @throws RepeatException
	 * @throws SeckillClose
	 */
	@Transactional
	public SeckillExecution executeSeckillWithRedis(long seckillId, long userPhone) throws SeckillException, RepeatException, SeckillClose {
		Date killTime = new Date();
		Seckill seckill = null;
		//	由于是从exposer接口转到这边的，那必然redis有对应商品的缓存，Redis判断库存
		if (redisDao.getSeckill(seckillId) != null) {
			seckill = redisDao.getSeckill(seckillId);
		} else {
			seckill = seckillDao.queryById(seckillId);
			redisDao.putSeckill(seckill);
		}
		if (seckill.getNumber() != 0) {
			//	记录购买明细
			int insertNum = successKilledDao.insertSuccessKilled(seckillId, userPhone);
			if (insertNum <= 0) {
				throw new RepeatException("重复秒杀");
			} else {
				//	减库存
				int updateNum = seckillDao.reduceNumber(seckillId, killTime);
				if (updateNum <= 0) {
					throw new SeckillException("系统错误");
				}
				//	每更新一次数据库即更新一次redis缓存
				redisDao.putSeckill(seckillDao.queryById(seckillId));
				SuccessKilled successKilled = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
				//返回给 controller 层dto
				return new SeckillExecution(seckillId, StateEnums.SUCCESS, successKilled);
			}
		} else {
			redisDao.deleteSeckill(seckill);
			throw new SeckillClose("秒杀结束");
		}
	}

	/**
	 * 秒杀进行时只操作redis数据库，然后通过ActiveMQ 异步将记录刷到 Mysql
	 * @param seckillId
	 * @param userPhone
	 * @return
	 * @throws SeckillException
	 * @throws RepeatException
	 * @throws SeckillClose
	 */
	public SeckillExecution executeSeckillAllRedis(long seckillId, long userPhone) throws SeckillException, RepeatException, SeckillClose {
		Seckill seckill = null;
		if (redisDao.getSeckill(seckillId) != null) {
			seckill = redisDao.getSeckill(seckillId);
		}else {
			seckill = seckillDao.queryById(seckillId);
			redisDao.putSeckill(seckill);
		}
		if (seckill.getNumber() != 0) {
			if (redisDao.isRepeatSeckill(seckillId , userPhone)){
				throw new RepeatException("重复秒杀");
			}else{
				//  事务执行秒杀操作
				if (redisDao.executeSeckill(seckill ,userPhone)){
                    //  将秒杀记录发送消息给ActiveMQ
                    ObjectMapper mapper = new ObjectMapper();
                    SuccessKilled successKilled = new SuccessKilled();
                    try {
                        successKilled.setSeckillId(seckillId);
                        successKilled.setUserPhone(userPhone);
                        String jsonMsg = mapper.writeValueAsString(successKilled);
                        //	发送json信息
						producerService.sendQueueMessage(jsonMsg);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
					//  返回给 controller 层dto
					return new SeckillExecution(seckillId, StateEnums.SUCCESS);
				}else{
					throw new SeckillException("系统错误");
				}
			}
		} else {
			redisDao.deleteSeckill(seckill);
			throw new SeckillClose("秒杀结束");
		}
	}
}
