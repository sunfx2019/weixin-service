package com.yimai.web.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONArray;
import com.yimai.bean.AccessToken;
import com.yimai.bean.JsonResult;
import com.yimai.core.util.DecriptUtil;
import com.yimai.core.util.StringUtil;
import com.yimai.core.util.WeixinUtil;
import com.yimai.service.IJuanDecorateWeiXinService;
import com.yimai.web.base.AbctractBaseController;

/**
 * 微信公共平台-居安整装-后台服务
 * 
 * @author sunfx
 *
 */
@Controller
public class JuanDecorateWeiXinServiceController extends AbctractBaseController {

	private Log log4j = LogFactory.getLog(getClass());

	//微信公众号平台上的 token
	public final static String token = "e30b0df0b07e427796c1b7fd93bb46ee";
	//微信公众号平台上的 appId
	public final static String appId = "wx2db855389e66fba4";
	//微信公众号平台上的 appSecret
	public final static String appSecret = "661a1abde79e64c5a9181af7a3481313";
	//微信公众号平台上的 EncodingAESKey
	public final static String encodingAESKey = "QHjO9SMQtGLZMdRj9jaX7mDWRk2vUxIabKULV95mJMN";

	@Autowired
	public IJuanDecorateWeiXinService weiXinService;

	/**
	 * Get微信验证Signature
	 * 
	 * @param signature
	 */
	@RequestMapping(value = "/juandecorate-service.shtml", method = RequestMethod.GET)
	public void serviceGet(HttpServletRequest request, HttpServletResponse response) {
		// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，失败则随便输出，接入失败
		String signature = request.getParameter("signature");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String timestamp = request.getParameter("timestamp");
		if (StringUtil.isEmpty(signature) || StringUtil.isEmpty(nonce) ||  
			StringUtil.isEmpty(echostr) || StringUtil.isEmpty(timestamp)) {
			this.renderText(response, "false");
		}else{
			if (this.validateSignature(signature, timestamp, nonce)) {
				this.renderText(response, echostr);
			} else {
				this.renderText(response, "false");
			}
		}
	}

	/**
	 * POST 服务请求
	 */
	@RequestMapping(value = "/juandecorate-service.shtml", method = RequestMethod.POST)
	public void servicePost(HttpServletRequest request, HttpServletResponse response) {
		try {
			String responseMessage = weiXinService.processRequest(request);
			log4j.info("responseMessage:" + JSONArray.toJSONString(responseMessage));
			this.renderText(response, responseMessage);
		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
			this.renderText(response, "");
		}
	}
	
	/**
	 * 初始化菜单
	 * 
	 * @param signature
	 */
	@RequestMapping(value = "/juandecorate-service-initmenu.shtml", method = RequestMethod.GET)
	public void initMenu(HttpServletRequest request, HttpServletResponse response) {
		boolean bool = weiXinService.initMenu(appId, appSecret);
		if(bool){
			this.renderJson(response, JsonResult.returnSuccess());
		}else{
			this.renderJson(response, JsonResult.returnFailure());
		}
	}
	
	/**
	 * 初始化菜单
	 * 
	 * @param signature
	 */
	@RequestMapping(value = "/juandecorate-service-gettoken.shtml", method = RequestMethod.GET)
	public void gettoken(HttpServletRequest request, HttpServletResponse response) {
		AccessToken accessToken = WeixinUtil.getAccessToken(appId, appSecret);
		this.renderJson(response, JsonResult.returnSuccess(accessToken));
	}

	/**
	 * 检验微信回调的signature
	 * 
	 * @return
	 */
	public boolean validateSignature(String signature, String timestamp, String nonce) {
		// 拼接字符串
		String[] arr = new String[] { token, timestamp, nonce };
		// 排序
		Arrays.sort(arr);
		// 生成字符串
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			content.append(arr[i]);
		}
		// SHA1加密
		String tmp = DecriptUtil.SHA1(content.toString());
		return tmp.equals(signature);
	}

}
