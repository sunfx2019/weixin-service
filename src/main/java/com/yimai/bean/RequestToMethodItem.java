package com.yimai.bean;

public class RequestToMethodItem {

	private String requestUrl;
	private String requestType;
	private String controllerName;
	private String requestMethodName;
	private Class<?>[] methodParamTypes;
	private Class<?> methodReturnTypes;

	public RequestToMethodItem(String requestUrl, String requestType, String controllerName, String requestMethodName,
			Class<?>[] methodParamTypes, Class<?> methodReturnTypes) {
		this.requestUrl = requestUrl;
		this.requestType = requestType;
		this.controllerName = controllerName;
		this.requestMethodName = requestMethodName;
		this.methodParamTypes = methodParamTypes;
		this.methodReturnTypes = methodReturnTypes;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public String getRequestType() {
		return requestType;
	}

	public String getControllerName() {
		return controllerName;
	}

	public String getRequestMethodName() {
		return requestMethodName;
	}

	public Class<?>[] getMethodParamTypes() {
		return methodParamTypes;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public void setControllerName(String controllerName) {
		this.controllerName = controllerName;
	}

	public void setRequestMethodName(String requestMethodName) {
		this.requestMethodName = requestMethodName;
	}

	public void setMethodParamTypes(Class<?>[] methodParamTypes) {
		this.methodParamTypes = methodParamTypes;
	}

	public Class<?> getMethodReturnTypes() {
		return methodReturnTypes;
	}

	public void setMethodReturnTypes(Class<?> methodReturnTypes) {
		this.methodReturnTypes = methodReturnTypes;
	}

}
