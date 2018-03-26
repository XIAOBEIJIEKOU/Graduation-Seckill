package org.seckill.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.Seckill;

/**
 * 对商品库存的操作
 * 
 * @author Administrator
 *
 */
public interface SeckillDao {
	/**
	 * 减库存
	 * 
	 * @param seckillId
	 * @param killTime
	 * @return 返回影响行数
	 */
	int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);
	
	/**
	 * 查询单条记录
	 */
	Seckill queryById(long seckillId);

	/**
	 * 查询全部记录数 offset 偏移量 limit 一次查询的记录数
	 * 
	 * 当方法中的参数有多个时需要使用注解注明每个参数的名字才能被xml文件中使用
	 */
	List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);
}
