package org.seckill.dto;

/**
 * 封装的是秒杀详情页的数据
 * 暴露秒杀接口
 * 含有多个构造函数应对不同的业务逻辑
 */
public class Exposer {
	//是否开启秒杀
	private boolean exposed;
	//加密值
	private String md5;
	//秒杀的商品ID
	private long seckillId;
	//系统当前时间
	private long now;
	//秒杀开启时间
	private long startTime;
	//秒杀结束时间
	private long endTime;
	/**
	 *  若秒杀开启，需要ID和加密信息
	 * @param exposed
	 * @param md5
	 * @param seckillId
	 */
	public Exposer(boolean exposed, String md5, long seckillId) {
		super();
		this.exposed = exposed;
		this.md5 = md5;
		this.seckillId = seckillId;
	}
	/**
	 * 	若秒杀没有开启，则显示各个时间
	 * @param exposed
	 * @param now
	 * @param start
	 * @param end
	 */
	public Exposer(boolean exposed, long seckillId, long now, long start, long end) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
		this.now = now;
		this.startTime = start;
		this.endTime = end;
	}
	/**
	 * 	
	 * @param exposed
	 * @param seckillId
	 */
	public Exposer(boolean exposed, long seckillId) {
		super();
		this.exposed = exposed;
		this.seckillId = seckillId;
	}

	/**
	 * @return exposed
	 */
	public boolean isExposed() {
		return exposed;
	}

	/**
	 * @param exposed 要设置的 exposed
	 */
	public void setExposed(boolean exposed) {
		this.exposed = exposed;
	}

	/**
	 * @return md5
	 */
	public String getMd5() {
		return md5;
	}

	/**
	 * @param md5 要设置的 md5
	 */
	public void setMd5(String md5) {
		this.md5 = md5;
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
	 * @return now
	 */
	public long getNow() {
		return now;
	}

	/**
	 * @param now 要设置的 now
	 */
	public void setNow(long now) {
		this.now = now;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "Exposer{" +
				"exposed=" + exposed +
				", md5='" + md5 + '\'' +
				", seckillId=" + seckillId +
				", now=" + now +
				", startTime=" + startTime +
				", endTime=" + endTime +
				'}';
	}
}
