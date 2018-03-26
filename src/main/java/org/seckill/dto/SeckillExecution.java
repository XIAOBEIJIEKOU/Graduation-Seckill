package org.seckill.dto;

import org.seckill.entity.SuccessKilled;
import org.seckill.enums.StateEnums;

/**
 *	封装执行秒杀后的输出结果 
 */
public class SeckillExecution {
	//秒杀单
	private long seckillId;
	//秒杀的状态
	private int state;
	//秒杀状态说明
	private String stateInfo;
	//秒杀明细
	private SuccessKilled successKilled;
	
	/**
	 * 	若秒杀成功，返回所有
	 * @param seckillId
	 * @param successKilled
	 */
	public SeckillExecution(long seckillId, StateEnums stateEnums, SuccessKilled successKilled) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnums.getState();
		this.stateInfo = stateEnums.getStateInfo();
		this.successKilled = successKilled;
	}
	
	/**
	 * 	若秒杀失败，返回失败的状态和原因
	 * @param seckillId
	 */
	public SeckillExecution(long seckillId, StateEnums stateEnums) {
		super();
		this.seckillId = seckillId;
		this.state = stateEnums.getState();
		this.stateInfo = stateEnums.getStateInfo();
	}

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
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state 要设置的 state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * @return stateInfo
	 */
	public String getStateInfo() {
		return stateInfo;
	}

	/**
	 * @param stateInfo 要设置的 stateInfo
	 */
	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	/**
	 * @return successKilled
	 */
	public SuccessKilled getSuccessKilled() {
		return successKilled;
	}

	/**
	 * @param successKilled 要设置的 successKilled
	 */
	public void setSuccessKilled(SuccessKilled successKilled) {
		this.successKilled = successKilled;
	}

	/* （非 Javadoc）
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SeckillExecution [seckillId=" + seckillId + ", state=" + state + ", stateInfo=" + stateInfo
				+ ", successKilled=" + successKilled + "]";
	}
	
	
	
}
