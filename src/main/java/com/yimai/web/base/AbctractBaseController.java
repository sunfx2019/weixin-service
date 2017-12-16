package com.yimai.web.base;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.yimai.core.util.ApplicationContextFactoryUtil;
import com.yimai.core.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 抽象的 Base Controller
 * 
 * @author sunfx
 * 
 */
public abstract class AbctractBaseController {

	protected final Log log4j = LogFactory.getLog(getClass());

	@Autowired
	protected ApplicationContextFactoryUtil springContextUtil;

	// 当前页
	public static final String PAGENO_KEY = "pageNo";

	// 每页大小
	public static final String PAGESIZE_KEY = "pageSize";

	// 分页对象 Session Key
	public static final String PAGE_KEY = "page";

	// 请求返回消息KEY
	public static final String MESSAGE_KEY = "message_key";

	// 请求返回结果KEY
	public static final String RESULT_KEY = "result_key";

	// 重定向前缀
	public static final String REDIRECT_PREFIX = "redirect:";

	// 系统用户 KEY（页面Session中取值：${session.SPRING_SECURITY_CONTEXT)）
	public static final String USER_CONTEXT_KEY = "SPRING_SECURITY_CONTEXT";

	// WEB-INF/view/jsp/message.jsp
	public static final String error_message_jsp = "message";

	/**
	 * 获取请求属性封装为Map类型
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	protected HashMap<String, Object> getRequestParameters(HttpServletRequest req) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		Map map = req.getParameterMap();
		for (Object o : map.keySet()) {
			String key = (String) o;
			conditions.put(key, ((String[]) map.get(key))[0]);
		}
		return conditions;
	}

	/**
	 * 设置Session请求参数
	 * 
	 * @param paramertValue
	 */
	protected void setSessionAttribute(HttpServletRequest req, HashMap<String, Object> paramertValue) {
		if (paramertValue == null || paramertValue.isEmpty())
			return;
		for (String key : paramertValue.keySet()) {
			req.getSession().setAttribute(key, paramertValue.get(key));
		}
	}

	/**
	 * 设置Request请求参数
	 * 
	 * @param paramertValue
	 */
	protected void setRequestAttribute(HttpServletRequest req, HashMap<String, Object> paramertValue) {
		if (paramertValue == null || paramertValue.isEmpty())
			return;
		for (String key : paramertValue.keySet()) {
			req.setAttribute(key, paramertValue.get(key));
		}
	}

	/**
	 * Rorward 跳转到 Controller
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String forward(String url) {
		return REDIRECT_PREFIX + url;
	}

	/**
	 * Redirect 跳转到 Controller
	 * 
	 * example "redirect:/namespace/toController"
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String redirect(String url) {
		return "redirect:" + url;
	}

	/**
	 * Redirect 跳转到 Controller 带简单的参数
	 * 
	 * example "redirect:/namespace/toController?parmters="
	 * 
	 * @param url
	 * @param parmters
	 * @return
	 */
	protected String redirect(String url, Map<String, String> parmters) {
		StringBuffer buffer = new StringBuffer("redirect:");
		buffer.append(url);
		if (parmters != null && parmters.size() > 0) {
			for (Map.Entry<String, String> entry : parmters.entrySet()) {
				buffer.append("?").append(entry.getKey()).append("=").append(entry.getValue());
			}
		}
		return buffer.toString();
	}

	/**
	 * 返回一个 ModelAndView
	 * 
	 * @param viewName
	 * @return
	 */
	protected ModelAndView createModelAndView(String viewName) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	/**
	 * 直接输出。
	 * 
	 * @param contentType
	 *            内容的类型.html,text,xml的值见后，json为"text/x-json;charset=UTF-8"
	 */
	protected void render(HttpServletResponse res, String text, String contentType) {
		try {
			if (StringUtil.isEmpty(text))
				text = "null";
			res.setContentType(contentType);
			res.getWriter().write(text);
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
		}
	}

	/**
	 * 直接输 Object json对象.
	 */
	protected void renderJson(HttpServletResponse res, Object obj) {
		if (obj == null)
			render(res, "", "application/json;charset=UTF-8");
		else
			render(res, JSONArray.toJSONString(obj), "application/json;charset=UTF-8");
	}

	/**
	 * 直接输json对象.
	 */
	protected void renderJson(HttpServletResponse res, String text) {
		render(res, text, "application/json;charset=UTF-8");
	}

	/**
	 * 直接输出纯字符串.
	 */
	protected void renderText(HttpServletResponse res, String text) {
		render(res, text, "text/plain;charset=UTF-8");
	}

	/**
	 * 直接输出纯HTML.
	 */
	protected void renderHtml(HttpServletResponse res, String html) {
		render(res, html, "text/html;charset=UTF-8");
	}

	/**
	 * 直接输出纯XML.
	 */
	protected void renderXML(HttpServletResponse res, String xml) {
		render(res, xml, "text/xml;charset=UTF-8");
	}

	/**
	 * 读取到服务器的路径息
	 * 
	 * @param relativePath
	 *            相对于服务器工程目录的路径
	 * @return
	 */
	protected String getServicePath(HttpServletRequest req, String relativePath) {
		return req.getSession().getServletContext().getRealPath(relativePath);
	}

	/**
	 * 在指定的相对路径下保存文件,返回相对于服务器的相对路径，包括文件名
	 * 
	 * @param request
	 * @param relativePath
	 * @param formFile
	 * @return
	 */
	protected String saveFileToService(HttpServletRequest request, String relativePath, File formFile) {
		return this.saveFileToService(request, relativePath, null, formFile);
	}

	/**
	 * 在指定的相对路径下保存文件,返回相对于服务器的相对路径，包括文件名
	 * 
	 * @param request
	 * @param relativePath
	 * @param fileName
	 * @param formFile
	 * @return
	 */
	protected String saveFileToService(HttpServletRequest request, String relativePath, String fileName,
			File formFile) {

		// 上传文件...

		return relativePath + "/" + fileName;

	}

	/**
	 * 在服务器上删除指定的文件，其中文件路径为相对于服务器所在目录的路径
	 * 
	 * @param request
	 * @param relativePath
	 * @return
	 */
	protected void deleteFileFromServcie(HttpServletRequest request, String relativePath) {

	}

	/**
	 * 设置客户端对网页不进行缓存
	 * 
	 * @param request
	 */
	protected void setClientNoCache(HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragrma", "no-cache");
		response.setDateHeader("Expires", 0);
	}

}
