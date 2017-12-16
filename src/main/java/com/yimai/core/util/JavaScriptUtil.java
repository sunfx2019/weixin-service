package com.yimai.core.util;

import javax.servlet.http.HttpServletRequest;

public class JavaScriptUtil {

	/**
	 * 返回脚本的提示信息
	 * 
	 * @param message
	 * @return
	 */
	public static String return_script_str(String message) {
		if (message == null || message.equals(""))
			return "";
		String str = "<script>alert('" + message + "');</script>";
		return str;
	}

	/**
	 * 返回退出脚本
	 * 
	 * @param request
	 * @param message
	 * @return
	 */
	public static String return_exit_script_str(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		String url = basePath + "exit.jsp";
		String str = "<script>alert('" + "您未登录或登录已超时,请重新登录!" + "');</script>";
		str += "<script>window.location.href='" + url + "'</script>";
		return str;
	}

	public static void main(String[] args) {

	}

}
