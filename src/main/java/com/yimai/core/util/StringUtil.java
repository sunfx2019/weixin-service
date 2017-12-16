package com.yimai.core.util;

import java.util.UUID;

/**
 * 
 * String 工具类
 * 
 * @author caspar.chen
 * @version 1.0.0, 2016-9-26
 */
public class StringUtil {

	public static boolean isEmpty(Object value) {
		return (value == null || "".equals(value) || "null".equalsIgnoreCase(String.valueOf(value)));
	}

	public static boolean isNotEmpty(Object value) {
		return (!isEmpty(value));
	}

	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.toUpperCase().replace("-", "");
	}

	public static void main(String[] args) {
		Object obj = null;
		System.out.println(String.valueOf(obj));
		System.out.println(StringUtil.isEmpty(obj));
	}

}
