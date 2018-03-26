package org.seckill.enums;

/**
 * 	数据字典
 * 	枚举，记录web和service层要使用的产量
 * @author Administrator
 *
 */
public enum StateEnums {

	SUCCESS(1, "秒杀成功"), 
	END(0, "秒杀结束"), 
	REPEAT_KILL(-1, "重复秒杀"),
	DATA_MD5(-2, "数据篡改"), 
	SYSTEM_ERROR(-3, "系统异常");

	private int state;

	private String stateInfo;

	private StateEnums(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	/**
	 * @return state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @return stateInfo
	 */
	public String getStateInfo() {
		return stateInfo;
	}

	public static StateEnums stateOf(int index) {
		for (StateEnums state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
