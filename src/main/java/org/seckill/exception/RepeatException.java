package org.seckill.exception;

/**
 *	重复秒杀异常 ,运行期异常
 *
 */
public class RepeatException extends SeckillException {

	public RepeatException(String message, Throwable cause) {
		super(message, cause);
		// TODO 自动生成的构造函数存根
	}

	public RepeatException(String message) {
		super(message);
		// TODO 自动生成的构造函数存根
	}
	
}
