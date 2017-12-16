package com.yimai.bean;

import com.yimai.core.util.DateUtil;

public class JsonResult {

	public boolean success;

	public String message;

	public String time;
	
	public Object data;

	private JsonResult() {

	}

	public static JsonResult returnFailure() {
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setMessage(null);
		result.setTime(DateUtil.getTime());
		return result;
	}
	
	public static JsonResult returnFailure(String msg) {
		JsonResult result = new JsonResult();
		result.setSuccess(false);
		result.setMessage(msg);
		result.setTime(DateUtil.getDateTime());
		return result;
	}

	public static JsonResult returnSuccess() {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.setMessage(null);
		result.setData(null);
		result.setTime(DateUtil.getDateTime());
		return result;
	}
	
	public static JsonResult returnSuccess(Object data) {
		JsonResult result = new JsonResult();
		result.setSuccess(true);
		result.setMessage(null);
		result.setData(data);
		result.setTime(DateUtil.getDateTime());
		return result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
