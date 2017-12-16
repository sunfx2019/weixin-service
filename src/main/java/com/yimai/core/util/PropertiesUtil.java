package com.yimai.core.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

	/**
	 * @param fileName
	 *            ClassLoader范文下的properties文件名
	 * @return Properties
	 * 
	 *         加载指定文件名的properties
	 */
	public static Properties loadProperties(String fileName) {
		Properties prop = new Properties();
		try {
			prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * @return Properties
	 * 加载默认conf.properties文件
	 */
	public static Properties loadProperties() {
		Properties prop = new Properties();
		try {  
            prop.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("configSeparate.properties"));  

        } catch(IOException e) {  
            e.printStackTrace();  
        }  
		
		return prop;
	}
	/**
	 * @param filePath
	 * @return Properties
	 * 
	 *         加载指定文件路径的properties
	 */
	public static Properties loadPropertiesByFilePath(String filePath) {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * 
	 * @param key
	 * @param path
	 *            ---文件路径+文件名
	 * @return
	 */
	public static String getPropertyByFilePath(String key, String path) {
		return (String) loadPropertiesByFilePath(path).get(key);
	}

	/**
	 * 通过配置分离文件路径获取Property（内部自动添加配置分离文件路径）
	 * 
	 * @param key
	 * @param fileName
	 *            ---文件名
	 * @return
	 */
	public static String getPropertyBySepareatDirAndFileName(String key,String fileName) {
		return (String) loadPropertiesByFilePath(loadProperties().getProperty("separateDir")+fileName).get(key);
	}
	/**
	 * @param key
	 * @return String 通过key获取默认的value （conf.properties）
	 */

	/**
	 * @param key
	 * @param path
	 *            --文件名
	 */
	
	public static String getPropertyByPath(String key, String path) {
		return (String) loadProperties(path).get(key);
	}

}
