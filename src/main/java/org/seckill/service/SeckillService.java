package org.seckill.service;

import java.util.List;

import org.seckill.dto.Exposer;
import org.seckill.dto.SeckillExecution;
import org.seckill.entity.Seckill;
import org.seckill.exception.RepeatException;
import org.seckill.exception.SeckillClose;
import org.seckill.exception.SeckillException;

/**
 * 业务接口： 1.方法定义粒度 2.参数 3.返回类型（return（实体、dto），exception）
 * 
 * @author Administrator
 *
 */
public interface SeckillService {

	/**
	 * 
	 */
	List<Seckill> getSeckillList();

	/**
	 * 
	 */
	Seckill getSeckill(long seckillId);

	/**
	 * 暴露秒杀接口地址 如果开启秒杀，转到另一个url进行秒杀 如果没有开启则显示时间和秒杀倒计时
	 */
	Exposer exportSeckillUrlWithRedis(long seckillId);

	Exposer exportSeckillUrlNoRedis(long seckillId);

	/*
	 * 需要验证md5的值和ID，userphone
	 */
	SeckillExecution executeSeckillNoRedis(long seckillId, long userPhone)
			throws SeckillException, RepeatException, SeckillClose;

	SeckillExecution executeSeckillWithRedis(long seckillId, long userPhone)
			throws SeckillException, RepeatException, SeckillClose;

	SeckillExecution executeSeckillAllRedis(long seckillId, long userPhone)
			throws SeckillException, RepeatException, SeckillClose;
}
