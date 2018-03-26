package org.seckill.entity;

import java.util.Date;

public class SuccessKilled {

	private long seckillId;	// 秒杀成功商品Id

	private long userPhone;	// 秒杀成功用户手机号
	
	private short state;
	
	private Date createTime;
	
	//多条购买明细对应同一个商品，在多方将记录表示出来
	private Seckill seckill;

	/**
	 * @return seckillId
	 */
	public long getSeckillId() {
		return seckillId;
	}

	/**
	 * @param seckillId 要设置的 seckillId
	 */
	public void setSeckillId(long seckillId) {
		this.seckillId = seckillId;
	}

	/**
	 * @return userPhone
	 */
	public long getUserPhone() {
		return userPhone;
	}

	/**
	 * @param userPhone 要设置的 userPhone
	 */
	public void setUserPhone(long userPhone) {
		this.userPhone = userPhone;
	}

	/**
	 * @return state
	 */
	public short getState() {
		return state;
	}

	/**
	 * @param state 要设置的 state
	 */
	public void setState(short state) {
		this.state = state;
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
		return "SuccessKilled [seckillId=" + seckillId + ", userPhone=" + userPhone + ", state=" + state
				+ ", createTime=" + createTime + "]";
	}

	public Seckill getSeckill() {
		return seckill;
	}

	public void setSeckill(Seckill seckill) {
		this.seckill = seckill;
	}
}
