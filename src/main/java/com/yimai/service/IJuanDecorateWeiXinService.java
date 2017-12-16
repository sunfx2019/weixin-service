package com.yimai.service;

import javax.servlet.http.HttpServletRequest;

import com.yimai.bean.Menu;

/**
 * 居安整装-微信服务
 * 
 * @author sunfx
 *
 */
public interface IJuanDecorateWeiXinService {

	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public String emoji(int hexEmoji);

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request);

	/**
	 * 获得菜单
	 * 
	 * @return
	 */
	public Menu getMenu();

	/**
	 * 初始化公众号上的功能菜单
	 */
	public boolean initMenu(String appId, String appSecret);

}
