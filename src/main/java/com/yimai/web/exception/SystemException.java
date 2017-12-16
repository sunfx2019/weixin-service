package com.yimai.web.exception;

//系统自定义异常处理类,针对预期的异常，需要在程序中抛出此类的异常  
public class SystemException extends Exception {

	private static final long serialVersionUID = 7423216437769073602L;
	
	// 异常信息
	private String message;

	public SystemException(String message){  
      super(message);  
      this.message=message;  
  }

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
