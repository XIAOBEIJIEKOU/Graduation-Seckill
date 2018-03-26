package org.seckill.entity;

import java.util.Date;

/**
 * 数据库中的seckill表
 * @author ZhuangYefan
 *
 */
public class Seckill {
	
	private  Long seckillId;	// 秒杀商品Id

	private String name;	// 秒杀商品名
	
	private int number;		//	秒杀商品件数
	
	private Date startTime;	//秒杀开启时间
	
	private Date endTime;	//秒杀结束时间
	
	private Date createTime;

	/**
	 * @return seckillId
	 */
	public Long getSeckillId() {
		return seckillId;
	}

	/**
	 * @param seckillId 要设置的 seckillId
	 */
	public void setSeckillId(Long seckillId) {
		this.seckillId = seckillId;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name 要设置的 name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number 要设置的 number
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime 要设置的 startTime
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime 要设置的 endTime
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return createTime
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime 要设置的 createTime
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Seckill [seckillId=" + seckillId + ", name=" + name + ", number=" + number + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", createTime=" + createTime + "]";
	}
	
	
}
