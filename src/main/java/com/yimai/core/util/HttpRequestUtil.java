package com.yimai.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSONArray;

/**
 * 请求相关处理
 * 
 * @author
 */

public class HttpRequestUtil {

	private static final String STOWED_REQUEST_ATTRIBS = "ssl.redirect.attrib.stowed";

	private transient static Log log = LogFactory.getLog(HttpRequestUtil.class);

	/**
	 * 请请求参数转换成查询字符(这个方法把参数都经过URLEncoder转换...)
	 */
	@SuppressWarnings("rawtypes")
	public static String getRequestParameters(HttpServletRequest aRequest) {
		Map m = aRequest.getParameterMap();
		return createQueryStringFromMap(m, "&").toString();
	}

	/**
	 * 获取请求属性封装为Map类型
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static HashMap<String, Object> getRequestParametersAsMap(HttpServletRequest request) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		for (Object o : map.keySet()) {
			String key = (String) o;
			conditions.put(key, ((String[]) map.get(key))[0]);
		}
		return conditions;
	}

	/**
	 * 请请求参数转换成查询字符
	 */
	@SuppressWarnings("unused")
	public static String getRequestParameters2(HttpServletRequest aRequest) {
		StringBuffer aReturn = new StringBuffer("");
		aReturn.append(getURL(aRequest));
		Enumeration<?> e = aRequest.getParameterNames();
		if (e.hasMoreElements()) {
			aReturn.append("?");
		}
		int index = 0;
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = (String) aRequest.getParameter(key);
			aReturn.append("&");
			aReturn.append(key);
			aReturn.append("=");
			aReturn.append(value);
			index++;
		}
		return aReturn.toString();
	}

	/**
	 * 将Map中的对象，按指定的分隔符ampersand拼接成字符串
	 */
	@SuppressWarnings("rawtypes")
	public static StringBuffer createQueryStringFromMap(Map m, String ampersand) {
		StringBuffer aReturn = new StringBuffer("");
		Set aEntryS = m.entrySet();
		Iterator aEntryI = aEntryS.iterator();

		while (aEntryI.hasNext()) {
			Map.Entry aEntry = (Map.Entry) aEntryI.next();
			Object o = aEntry.getValue();

			if (o == null) {
				append(aEntry.getKey(), "", aReturn, ampersand);
			} else if (o instanceof String) {
				append(aEntry.getKey(), o, aReturn, ampersand);
			} else if (o instanceof String[]) {
				String[] aValues = (String[]) o;
				for (int i = 0; i < aValues.length; i++) {
					append(aEntry.getKey(), aValues[i], aReturn, ampersand);
				}
			} else {
				append(aEntry.getKey(), o, aReturn, ampersand);
			}
		}

		return aReturn;
	}

	/**
	 * Appends new key and value pair to query string
	 * 
	 * @param key
	 *            parameter name
	 * @param value
	 *            value of parameter
	 * @param queryString
	 *            existing query string
	 * @param ampersand
	 *            string to use for ampersand (e.g. "&" or "&amp;")
	 * 
	 * @return query string (with no leading "?")
	 */
	private static StringBuffer append(Object key, Object value, StringBuffer queryString, String ampersand) {
		if (queryString.length() > 0) {
			queryString.append(ampersand);
		}

		try {
			queryString.append(URLEncoder.encode(key.toString(), "UTF-8"));
			queryString.append("=");
			queryString.append(URLEncoder.encode(value.toString(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			// won't happen since we're hard-coding UTF-8
		}
		return queryString;
	}

	/**
	 * Stores request attributes in session
	 * 
	 * @param aRequest
	 *            the current request
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void stowRequestAttributes(HttpServletRequest aRequest) {
		if (aRequest.getSession().getAttribute(STOWED_REQUEST_ATTRIBS) != null) {
			return;
		}

		Enumeration e = aRequest.getAttributeNames();
		Map map = new HashMap();

		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			map.put(name, aRequest.getAttribute(name));
		}

		aRequest.getSession().setAttribute(STOWED_REQUEST_ATTRIBS, map);
	}

	/**
	 * Returns request attributes from session to request
	 * 
	 * @param aRequest
	 *            DOCUMENT ME!
	 */
	@SuppressWarnings("rawtypes")
	public static void reclaimRequestAttributes(HttpServletRequest aRequest) {
		Map map = (Map) aRequest.getSession().getAttribute(STOWED_REQUEST_ATTRIBS);

		if (map == null) {
			return;
		}

		Iterator itr = map.keySet().iterator();

		while (itr.hasNext()) {
			String name = (String) itr.next();
			aRequest.setAttribute(name, map.get(name));
		}

		aRequest.getSession().removeAttribute(STOWED_REQUEST_ATTRIBS);
	}

	/**
	 * 写设置Cookie�?
	 * 
	 */
	public static void setCookie(HttpServletResponse response, String name, String value, String path) {
		if (log.isDebugEnabled()) {
			log.info("Setting cookie '" + name + "' on path '" + path + "'");
		}

		Cookie cookie = new Cookie(name, value);
		cookie.setSecure(false);
		cookie.setPath(path);
		cookie.setMaxAge(3600 * 24 * 30); // 30 days

		response.addCookie(cookie);
	}

	/**
	 * 按名字取得Cookie的�??
	 * 
	 * @return 返回Cookie的�?? 如果没有到相应�?�，则返回NULL
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		Cookie returnCookie = null;

		if (cookies == null) {
			return returnCookie;
		}

		for (int i = 0; i < cookies.length; i++) {
			Cookie thisCookie = cookies[i];

			if (thisCookie.getName().equals(name)) {
				// cookies with no value do me no good!
				if (!thisCookie.getValue().equals("")) {
					returnCookie = thisCookie;
					break;
				}
			}
		}

		return returnCookie;
	}

	/**
	 * 删除指定的Cookie
	 */
	public static void deleteCookie(HttpServletResponse response, Cookie cookie, String path) {
		if (cookie != null) {
			// Delete the cookie by setting its maximum age to zero
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
	}

	/**
	 * 取得当前请求的URL信息
	 */
	public static String getURL(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		url.append(request.getServletPath());
		return url.toString();
	}

	public static String getURLAndQueryString(HttpServletRequest request) {
		StringBuffer url = new StringBuffer();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80; // Work around java.net.URL bug
		}
		String scheme = request.getScheme();
		url.append(scheme);
		url.append("://");
		url.append(request.getServerName());
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		url.append(request.getServletPath() + "?" + request.getQueryString());
		return url.toString();
	}

	/**
	 * 获得 客户端请求 的 IP 地址
	 * 
	 * @param request
	 * @return
	 */

	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 返回 客户端 IE 类型
	 * 
	 * @return
	 */
	public static String getIE(HttpServletRequest request) {
		String Agent = request.getHeader("User-Agent");
		// StringTokenizer st = new StringTokenizer(Agent, ";");
		// return st.nextToken();
		return Agent;
	}

	/**
	 * getAgent
	 * 
	 * @param request
	 * @return
	 */
	public static String getAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获得 用户的 全部请求信息
	 * 
	 * @param request
	 * @return
	 */
	public static String getHead(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	/**
	 * 获取用户名
	 * 
	 * @param request
	 * @return
	 */
	public static String getUserName(HttpServletRequest request) {
		// Object obj =
		// request.getSession().getAttribute(Constants.USER_SESSION);
		// return obj == null ? null : obj.toString();
		return null;
	}

	public static String getBasePath(HttpServletRequest request) {
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path
				+ "/";
		return basePath;
	}
	
	/**
	 *  判断是否是 Ajax 请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(HttpServletRequest request){
		return  (request.getHeader("X-Requested-With") != null && "XMLHttpRequest".equals(request.getHeader("X-Requested-With").toString())) ;
	}
	
	/**
	 * 直接输出。
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	public static void render(HttpServletResponse response, String text, String contentType) {
		try {
			response.setContentType(contentType);
			response.getWriter().write(text);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 直接输 Object json对象.
	 */
	public static void renderJson(HttpServletResponse response, Object obj) {
		if (obj == null)
			render(response, "", "application/json;charset=UTF-8");
		else
			render(response, JSONArray.toJSONString(obj), "application/json;charset=UTF-8");
	}

	/**
	 * 直接输json对象.
	 */
	public static void renderJson(HttpServletResponse response, String text) {
		render(response, text, "application/json;charset=UTF-8");
	}
	
	/**
	 * 直接输出纯字符串.
	 */
	public static void renderText(HttpServletResponse response, String text) {
		render(response, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	public static void renderHtml(HttpServletResponse response, String html) {
		render(response, html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	public static void renderXML(HttpServletResponse response, String xml) {
		render(response, xml, "text/xml;charset=UTF-8");
	}
	
}
