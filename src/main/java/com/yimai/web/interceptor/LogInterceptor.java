package com.yimai.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.yimai.core.util.DateUtil;
import com.yimai.core.util.HttpRequestUtil;

/**
 * 日志记录拦截器
 * 
 * @author Administrator
 *
 */
public class LogInterceptor implements HandlerInterceptor {

	public Log log4j = LogFactory.getLog(getClass());

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(log4j.isDebugEnabled()){
			if (handler instanceof HandlerMethod) {
				StringBuilder sb = new StringBuilder();
				HandlerMethod h = (HandlerMethod) handler;
				sb.append("\n---------------------------------------------------------------------------------------------------------------------\n");
				//sb.append("UserID     : ").append((userDetails == null ? "" : userDetails.getId())).append("\n");
				sb.append("DateTime   : ").append(DateUtil.getDateTime()).append("\n");
				sb.append("RequestIP  : ").append(HttpRequestUtil.getIpAddr(request)).append("\n");
				sb.append("Controller : ").append(h.getBean().getClass().getName()).append(".").append(h.getMethod().getName()).append("\n");
				sb.append("RequestURI : ").append(request.getRequestURL()).append("\n");
				sb.append("Parameters : ").append(getRequestParameters(request)).append("\n");
				sb.append("---------------------------------------------------------------------------------------------------------------------\n");
				System.out.println(sb);
			}
		}
		
		return true;
		
	}

	// after the handler is executed
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	/**
	 * 获得请求参数
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private HashMap<String, Object> getRequestParameters(HttpServletRequest request) {
		HashMap<String, Object> conditions = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		for (Object o : map.keySet()) {
			String key = (String) o;
			conditions.put(key, ((String[]) map.get(key))[0]);
		}
		return conditions;
	}

	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}
}