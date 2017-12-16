package com.yimai.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.yimai.bean.AccessToken;
import com.yimai.bean.Button;
import com.yimai.bean.ComplexButton;
import com.yimai.bean.Menu;
import com.yimai.bean.ViewButton;
import com.yimai.bean.weixin.message.resp.Article;
import com.yimai.bean.weixin.message.resp.NewsMessage;
import com.yimai.bean.weixin.message.resp.TextMessage;
import com.yimai.core.util.MessageType;
import com.yimai.core.util.MessageUtil;
import com.yimai.core.util.WeixinUtil;
import com.yimai.service.IJuanDecorateWeiXinService;

/**
 * 微信服务类
 * 
 * @author sunfx
 *
 */
@Service("weiXinServiceImpl")
public class IJuanDecorateWeiXinServiceImpl implements IJuanDecorateWeiXinService {

	public Log log4j = LogFactory.getLog(getClass());

	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public String processRequest(HttpServletRequest request) {

		String respMessage = "";

		try {

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);
			log4j.info("requestMap:" + JSONArray.toJSONString(requestMap));

			// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");
			// 消息类型
			String msgType = requestMap.get("MsgType");
			// 默认的消息
			respMessage = this.getDefaultTextMessage(fromUserName, toUserName);

			// event事件
			if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型  
                String eventType = requestMap.get("Event"); 
                // 点击关注
                if (eventType.equals(MessageType.EVENT_TYPE_SUBSCRIBE)) {
                	log4j.info("点击关注！");
        			respMessage = this.getDefaultNewsMessage(fromUserName, toUserName);
                	
                } 
                // 取消关注,用户接受不到我们发送的消息了，可以在这里记录用户取消关注的日志信息  
                else if (eventType.equals(MessageType.EVENT_TYPE_UNSUBSCRIBE)) {  
                	log4j.info("取消关注！");
                } 
                // 点击事件
                else if (eventType.equals(MessageType.EVENT_TYPE_CLICK)) {  
                	// 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = requestMap.get("EventKey");
                    log4j.info("eventKey:" + eventKey);
                    if (eventKey.equals("11")) {  
                        //respContent = "天气预报菜单项被点击！";
                    } else if (eventKey.equals("12")) {  
                        //respContent = "公交查询菜单项被点击！";
                    }  
                }
            }  

			// 文本消息
			if (msgType.equals(MessageType.REQ_MESSAGE_TYPE_TEXT)) {
				// 接收用户发送的文本消息内容
				String content = requestMap.get("Content");
				// 创建图文消息
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);
				List<Article> articleList = new ArrayList<Article>();
				// 单图文消息
				if ("1".equals(content)) {
					Article article = new Article();
					article.setTitle("微信公众帐号开发教程Java版");
					article.setDescription("柳峰，80后，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
					article.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article.setUrl("http://blog.csdn.net/lyq8479");
					articleList.add(article);
					// 设置图文消息个数
					newsMessage.setArticleCount(articleList.size());
					// 设置图文消息包含的图文集合
					newsMessage.setArticles(articleList);
					// 将图文消息对象转换成xml字符串
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 单图文消息---不含图片
				else if ("2".equals(content)) {
					Article article = new Article();
					article.setTitle("微信公众帐号开发教程Java版");
					// 图文消息中可以使用QQ表情、符号表情
					article.setDescription("柳峰，80后，" + emoji(0x1F6B9) + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
					// 将图片置为空
					article.setPicUrl("");
					article.setUrl("http://blog.csdn.net/lyq8479");
					articleList.add(article);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息
				else if ("3".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程\n引言");
					article1.setDescription("");
					article1.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");
					Article article2 = new Article();
					article2.setTitle("第2篇\n微信公众帐号的类型");
					article2.setDescription("");
					article2.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");
					Article article3 = new Article();
					article3.setTitle("第3篇\n开发模式启用及接口配置");
					article3.setDescription("");
					article3.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");
					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---首条消息不含图片
				else if ("4".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("微信公众帐号开发教程Java版");
					article1.setDescription("");
					// 将图片置为空
					article1.setPicUrl("");
					article1.setUrl("http://blog.csdn.net/lyq8479");
					Article article2 = new Article();
					article2.setTitle("第4篇\n消息及消息处理工具的封装");
					article2.setDescription("");
					article2.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");
					Article article3 = new Article();
					article3.setTitle("第5篇\n各种消息的接收与响应");
					article3.setDescription("");
					article3.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");
					Article article4 = new Article();
					article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
					article4.setDescription("");
					article4.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");
					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					articleList.add(article4);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
				// 多图文消息---最后一条消息不含图片
				else if ("5".equals(content)) {
					Article article1 = new Article();
					article1.setTitle("第7篇\n文本消息中换行符的使用");
					article1.setDescription("");
					article1.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");
					Article article2 = new Article();
					article2.setTitle("第8篇\n文本消息中使用网页超链接");
					article2.setDescription("");
					article2.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
					article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");
					Article article3 = new Article();
					article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持柳峰！");
					article3.setDescription("");
					// 将图片置为空
					article3.setPicUrl("");
					article3.setUrl("http://blog.csdn.net/lyq8479");
					articleList.add(article1);
					articleList.add(article2);
					articleList.add(article3);
					newsMessage.setArticleCount(articleList.size());
					newsMessage.setArticles(articleList);
					respMessage = MessageUtil.newsMessageToXml(newsMessage);
				}
			}

		} catch (Exception e) {
			log4j.error(e.getMessage(), e);
		}

		return respMessage;

	}

	public String getDefaultTextMessage(String fromUserName, String toUserName){
		// 默认回复此文本消息
		TextMessage textMessage = new TextMessage();
		textMessage.setToUserName(fromUserName);
		textMessage.setFromUserName(toUserName);
		textMessage.setCreateTime(new Date().getTime());
		textMessage.setMsgType(MessageType.REQ_MESSAGE_TYPE_TEXT);
		textMessage.setFuncFlag(0);
		StringBuffer contentMsg = new StringBuffer();  
		contentMsg.append("O(∩_∩)O 亲爱的用户,欢迎访问<a href=\"http://www.yimaisc.com\">居安整装</a>!\n\n");
        contentMsg.append("您还可以回复下列数字，体验相应服务。").append("\n");
        contentMsg.append("1  测试1").append("\n");  
        contentMsg.append("2  测试2").append("\n");  
        contentMsg.append("3  测试3").append("\n");  
        contentMsg.append("4  测试4").append("\n");  
        contentMsg.append("5  测试5").append("\n");  
        textMessage.setContent(contentMsg.toString());
		// 将文本消息对象转换成xml字符串
        return MessageUtil.textMessageToXml(textMessage);
	}
	
	public String getDefaultNewsMessage(String fromUserName, String toUserName){
		// 创建图文消息
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageType.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		List<Article> articleList = new ArrayList<Article>();
		Article article = new Article();
		article.setTitle("O(∩_∩)O亲爱的用户，欢迎访问居安建材！");
		//article.setDescription("	居安装饰是集室内设计及施工于一体的现代家装企业。公司现有员工近100人，设市场部、设计部、工程部、主材管理部、工程监理部、客服售后部、财务部。其中设计部汇聚业内知名设计师多位，工程部大多由技艺精湛的南方技工结成，客服售后部专业细致的服务让您的家装全程无忧。");
		article.setPicUrl("http://www.yimaisc.com/public/images/0a/6c/b4/513fdc7ed7bdb877754d5d00d844bee67e669dfc.jpg");
		article.setUrl("http://www.yimaisc.com/");
		
		Article article1 = new Article();
		article1.setTitle("爱心活动");
		article1.setDescription("");
		article1.setPicUrl("http://wx.yimaisc.com:8042/yimaiwx/Public/img/1.jpg");
		article1.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Activity/index.html");
		Article article2 = new Article();
		article2.setTitle("家装设计师");
		article2.setDescription("");
		article2.setPicUrl("http://wx.yimaisc.com:8042/yimaiwx/Public/img/designer-img-big.jpg");
		article2.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Designer/index.html");
		Article article3 = new Article();
		article3.setTitle("君墅湾效果图");
		article3.setDescription("");
		article3.setPicUrl("http://wx.yimaisc.com:8042/yimaiwx/Public/img/quarters-img.jpg");
		article3.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Design/index.html");
		Article article4 = new Article();
		article4.setTitle("易买建材");
		article4.setDescription("");
		article4.setPicUrl("http://www.yimaisc.com/public/images/1e/17/6a/cb63d7ab4b9359319c6b7857c9ddbbeb3852c5bc.jpg");
		article4.setUrl("http://www.yimaisc.com/");
		Article article5 = new Article();
		article5.setTitle("Hello易买");
		article5.setDescription("");
		article5.setPicUrl("http://www.yimaisc.com/public/images/be/e9/01/2aed38d8fd24f73832d94e89337a730296a857ec.jpg");
		article5.setUrl("http://www.yimaisc.com/");
		
		articleList.add(article);
		articleList.add(article1);
		articleList.add(article2);
		articleList.add(article3);
		articleList.add(article4);
		articleList.add(article5);
		
		// 设置图文消息个数
		newsMessage.setArticleCount(articleList.size());
		// 设置图文消息包含的图文集合
		newsMessage.setArticles(articleList);
		// 将图文消息对象转换成xml字符串
		return MessageUtil.newsMessageToXml(newsMessage);
	}
	
	/**
	 * emoji表情转换(hex -> utf-16)
	 * 
	 * @param hexEmoji
	 * @return
	 */
	public String emoji(int hexEmoji) {
		return String.valueOf(Character.toChars(hexEmoji));
	}
	
	/**
	 * 初始化公众号菜单
	 */
	 public boolean initMenu(String appId, String appSecret) {
	        // 第三方用户唯一凭证
	        //String appId = "000000000000000000";
	        // 第三方用户唯一凭证密钥
	        //String appSecret = "00000000000000000000000000000000";
	        // 调用接口获取access_token
	        AccessToken at = WeixinUtil.getAccessToken(appId, appSecret);
	        if (null != at) {
	            // 调用接口创建菜单
	            int result = WeixinUtil.createMenu(getMenu(), at.getToken());
	            // 判断菜单创建结果
	            if (0 == result){
	            	log4j.info("菜单创建成功！");
	            	return true;
	            }else{
	            	log4j.info("菜单创建失败，错误码：" + result);
	            	return false;
	            }
	        }else{
	        	log4j.info("获取AccessToken失败！");
	        	return false;
	        }
	    }

	    /**
	     * 组装菜单数据
	     * 
	     * @return
	     */
	    public Menu getMenu() {
	    	
	    	ViewButton btn21 = new ViewButton();  
	        btn21.setName("君墅湾效果图");
	        btn21.setType("view");
	        btn21.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Design/index.html");

	        ViewButton btn22 = new ViewButton();
	        btn22.setName("家装设计师");
	        btn22.setType("view");
	        btn22.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Designer/index.html");
	        
	        ViewButton btn23 = new ViewButton();
	        btn23.setName("爱心活动");
	        btn23.setType("view");
	        btn23.setUrl("http://wx.yimaisc.com:8042/yimaiwx/Activity/index.html");

	        ViewButton btn31 = new ViewButton();
	        btn31.setName("预约上门");
	        btn31.setType("view");
	        btn31.setUrl("http://r.xiumi.us/stage/v5/2FjrB/43747789");

	        ViewButton btn32 = new ViewButton();
	        btn32.setName("体验馆");
	        btn32.setType("view");
	        btn32.setUrl("http://ditu.amap.com/search?id=B0FFGZ619F&city=441900&geoobj=113.772477%7C23.034469%7C113.781617%7C23.040748&query_type=IDQ&query=易买建材城&zoom=17");

	        /**
	         * 微信：  mainBtn1,mainBtn2,mainBtn3底部的三个一级菜单。
	         */
	        
	        ViewButton mainBtn1 = new ViewButton();  
	        mainBtn1.setName("商城");  
	        mainBtn1.setType("view");  
	        mainBtn1.setUrl("http://www.yimaisc.com/");
	        
	        ComplexButton mainBtn2 = new ComplexButton();
	        mainBtn2.setName("活动");
	        mainBtn2.setSub_button(new ViewButton[] { btn21, btn22, btn23 });
	        
	        ComplexButton mainBtn3 = new ComplexButton();
	        mainBtn3.setName("联系我们");
	        mainBtn3.setSub_button(new ViewButton[] { btn31, btn32 });
	        
	        /**
	         * 封装整个菜单
	         */
	        Menu menu = new Menu();
	        
	        menu.setButton(new Button[] { mainBtn1, mainBtn2, mainBtn3 });

	        return menu;
	    }
	
}