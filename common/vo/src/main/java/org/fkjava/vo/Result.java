package org.fkjava.vo;
/**
 * 
 *vo,Value Object 值对象，用于封装各层的传递数据，不用存储到数据库的数据
 */
public class Result {

	/**
	 * 成功状态
	 */
	public final static int STATUS_OK = 1;
	/**
	 * 失败状态
	 */
	public final static int STATUS_ERROR = 2;

	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static Result of(int status) {
		 
		Result result=new Result();
		result.setStatus(status);
		
		return result;
	}
}
