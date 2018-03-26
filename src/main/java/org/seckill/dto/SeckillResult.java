package org.seckill.dto;

/**
 * 封装前台请求的Json显示结果
 * 所有ajax返回的json
 * 
 */
public class SeckillResult<T> {
	
	private boolean success;
	
	private T data;
	
	private String error;
	
	/*
	 * 成功
	 */
	public SeckillResult(boolean success, T data) {
		super();
		this.success = success;
		this.data = data;
	}
	
	/*
	 * 失败
	 */
	public SeckillResult(boolean success, String error) {
		super();
		this.success = success;
		this.error = error;
	}

	/**
	 * @return success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success 要设置的 success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	/**
	 * @return data
	 */
	public T getData() {
		return data;
	}

	/**
	 * @param data 要设置的 data
	 */
	public void setData(T data) {
		this.data = data;
	}

	/**
	 * @return error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error 要设置的 error
	 */
	public void setError(String error) {
		this.error = error;
	}
	
	
}
