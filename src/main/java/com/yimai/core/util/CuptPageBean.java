package com.yimai.core.util;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 辅住数据 分页管理
 * 
 * @author
 * 
 */
public class CuptPageBean {

	protected Log log = LogFactory.getLog(getClass());

	private String fontColor = "<font color='#ca147b'>";

	private String fontEnd = "</font>";

	/**
	 * 本页数据
	 */
	private Collection<?> data;

	/**
	 * 当前第几页
	 */
	private int currentPageNo;

	/**
	 * 总页数
	 */
	private int totalPageCount;

	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 当前页第一条数据的位置,从0开始
	 */
	private int start;

	/**
	 * 每页的记录数
	 */
	private int pageSize;

	/**
	 * 是否有下一页
	 */
	private boolean hasNextPage = true;

	/**
	 * 是否有上一页
	 */
	private boolean hasPreviousPage = true;

	/**
	 * 用于分页超链接要用的字符串参数
	 */
	private String url = null;

	private String param = "restore_param";

	private int default_page_size = 10;

	public CuptPageBean(Page<?> page, String url, String param) {
		this.start = page.getStart();
		this.pageSize = page.getPageSize();
		this.currentPageNo = page.getCurrentPageNo();
		this.totalCount = page.getTotalCount();
		this.data = page.getData();
		this.url = url;
		if (param != null && param != "") {
			this.param = param;
		}
		this.totalPageCount = this.totalCount / this.pageSize;
		int mod = this.totalCount % this.pageSize;
		if (mod > 0) {
			this.totalPageCount++;
		}
		if (this.totalPageCount == 0
				|| this.totalPageCount == this.currentPageNo) {
			this.hasNextPage = false;
		}
		if (1 == this.currentPageNo) {
			this.hasPreviousPage = false;
		}
	}

	public Collection<?> getData() {
		return data;
	}

	public final boolean isHasNextPage() {
		return hasNextPage;
	}

	public final boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final int getStart() {
		return start;
	}

	public final int getTotalCount() {
		return totalCount;
	}

	public final int getCurrentPageNo() {
		return currentPageNo;
	}

	public final int getTotalPageCount() {
		return totalPageCount;
	}

	protected String getParamString() {
		String paramstr = "";
		int pos = url.lastIndexOf("?");
		int len = url.length();
		if (pos == -1) {
			paramstr = "?";
		} else if (pos < len) {
			paramstr = "&";
		}
		return paramstr;
	}

	/**
	 * 取得简单的分页字符信息
	 * 
	 * @return
	 */
	public String getSimplePageString() {

		String paramstr = getParamString();

		StringBuffer bufstr = new StringBuffer();

		bufstr.append("每页" + this.getPageSize() + "条记录&nbsp;");
		bufstr.append("共" + this.getTotalCount() + "条记录&nbsp;&nbsp;");
		bufstr.append("第" + this.getCurrentPageNo() + "页&nbsp;&nbsp;");
		bufstr.append("共" + this.getTotalPageCount() + "页&nbsp;&nbsp;");

		if (this.hasPreviousPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getFirstPage() + "&" + param
					+ "=true" + "\">首页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getPrePage() + "\">上页</A>&nbsp;&nbsp;");
		} else {
			bufstr.append("首页&nbsp;&nbsp;");
			bufstr.append("上页&nbsp;&nbsp;");
		}
		if (this.hasNextPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getNextPage() + "\">下页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getLastPage() + "\">尾页</A>&nbsp;&nbsp;");
		} else {
			bufstr.append("下页&nbsp;&nbsp;");
			bufstr.append("尾页&nbsp;&nbsp;");
		}

		return bufstr.toString();

	}

	/**
	 * 标签分页2
	 * 
	 * @return
	 */
	public String getSimplePageString2() {

		String paramstr = getParamString(); // 取 ? or &

		StringBuffer bufstr = new StringBuffer();
		bufstr.append("共" + fontColor + this.getTotalCount() + fontEnd
				+ "条记录&nbsp;&nbsp;");
		bufstr.append("第" + fontColor + this.getCurrentPageNo() + fontEnd
				+ "页&nbsp;&nbsp;");
		bufstr.append("共" + fontColor + this.getTotalPageCount() + fontEnd
				+ "页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

		if (this.hasPreviousPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getFirstPage() + "\" class='a2'>首页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getPrePage() + "\" class='a2'>上一页</A>&nbsp;&nbsp;");
		} else {
			bufstr.append("首页&nbsp;&nbsp;");
			bufstr.append("上一页&nbsp;&nbsp;");
		}
		if (this.hasNextPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getNextPage() + "\" class='a2'>下一页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "="
					+ this.getLastPage() + "\" class='a2'>尾页</A>");
		} else {
			bufstr.append("下一页&nbsp;&nbsp;");
			bufstr.append("尾页");
		}
		return bufstr.toString();
	}

	public String getSimplePageString3() {
		return this.getSimplePageString() + this.getPageString();
	}

	public String getSimplePageString4() {

		String paramstr = getParamString(); // 取 ? or &

		StringBuffer bufstr = new StringBuffer();
		bufstr.append("第" + this.getCurrentPageNo() +  "页&nbsp;&nbsp;");
		bufstr.append("共" + this.getTotalPageCount() + "页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

		if (this.hasPreviousPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "=" + this.getFirstPage() + "\" class='yangshi'>首页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "=" + this.getPrePage() + "\" class='yangshi'>上一页</A>&nbsp;&nbsp;");
		} else {
			bufstr.append("首页&nbsp;&nbsp;");
			bufstr.append("上一页&nbsp;&nbsp;");
		}

		bufstr.append("&nbsp;");
		
		int pageNumSize = 5;						//表示最多一页显示多少条页码号
		int totalPages = this.totalPageCount; 		//表示一共要显示多少页的数据
		
		int a = (this.currentPageNo / pageNumSize) + 1; 	//开始数字页
		
		int startPageNo = (a-1) * pageNumSize;
		int endPageNo = startPageNo + pageNumSize;
		
		startPageNo = (startPageNo <= 0) ? 1 : startPageNo;
		endPageNo   = (endPageNo > totalPages) ? totalPages : endPageNo;
		
		for (int i = startPageNo; i <= endPageNo; i++) {
			if(this.currentPageNo == i){
				bufstr.append(fontColor + i + fontEnd + "&nbsp;&nbsp;");
			}else{
				bufstr.append("<A href=\"" + url + paramstr + param + "=" + i + "\" class='yangshi'>" + i + "</A>&nbsp;&nbsp;");
			}
		}
		
		bufstr.append("&nbsp;");
		
		if (this.hasNextPage) {
			bufstr.append("<A href=\"" + url + paramstr + param + "=" + this.getNextPage() + "\" class='yangshi'>下一页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + url + paramstr + param + "=" + this.getLastPage() + "\" class='yangshi'>尾页</A>");
		} else {
			bufstr.append("下一页&nbsp;&nbsp;");
			bufstr.append("尾页");
		}
		return bufstr.toString();
	}

	/**
	 * 下拉列表 分页
	 * 
	 * @return
	 */
	public String getPageString() {

		String paramstr = getParamString();

		StringBuffer bufstr = new StringBuffer();
		bufstr.append(this.getSimplePageString());
		bufstr.append("&nbsp;&nbsp;&nbsp;转到&nbsp;");
		bufstr
				.append("<select id='_gotoPageCount' onchange=\"window.location.href='"
						+ url + paramstr + param + "='+this.value+'" + "'\">");
		String select = "";
		for (int i = 1; i <= this.totalPageCount; i++) {
			if (i == this.currentPageNo) {
				select = " selected ";
			} else {
				select = "";
			}
			bufstr.append("<option " + select + " value=" + i + " >第" + i
					+ "页</option>");
		}
		bufstr.append("</select>");

		bufstr.append("&nbsp;&nbsp;&nbsp;&nbsp;");

		bufstr
				.append("<select id='_gotoPage' onchange=\"window.location.href='"
						+ url
						+ paramstr
						+ param
						+ "="
						+ this.getFirstPage()
						+ "&"
						+ param
						+ "='+this.value+''\">");
		select = "";
		for (int i = 1; i <= 5; i++) {
			if (this.pageSize == (default_page_size * i)) {
				select = " selected ";
			} else {
				select = "";
			}
			bufstr.append("<option " + select + " value="
					+ default_page_size * i + " >每页"
					+ (default_page_size * i) + "条记录</option>");
		}
		bufstr.append("</select>");

		return bufstr.toString();
	}

	/**
	 * 获取脚本分页标签
	 * 
	 * @return
	 */
	public String getScriptSimplePageString() {
		StringBuffer bufstr = new StringBuffer();
		bufstr.append("共" + this.getTotalCount() + "条记录&nbsp;&nbsp;");
		bufstr.append("第" + this.getCurrentPageNo() + "页&nbsp;&nbsp;");
		bufstr.append("共" + this.getTotalPageCount()  + "页&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");

		if (this.hasPreviousPage) {
			bufstr.append("<A href=\"" + "javascript:page("
					+ this.getFirstPage() + ");"
					+ "\" class='yangshi'>首页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + "javascript:page(" + this.getPrePage()
					+ ");" + "\" class='yangshi'>上一页</A>&nbsp;&nbsp;");
		} else {
			bufstr.append("首页&nbsp;&nbsp;");
			bufstr.append("上一页&nbsp;&nbsp;");
		}
		if (this.hasNextPage) {
			bufstr.append("<A href=\"" + "javascript:page("
					+ this.getNextPage() + ");"
					+ "\" class='yangshi'>下一页</A>&nbsp;&nbsp;");
			bufstr.append("<A href=\"" + "javascript:page("
					+ this.getLastPage() + ");" + "\" class='yangshi'>尾页</A>");
		} else {
			bufstr.append("下一页&nbsp;&nbsp;");
			bufstr.append("尾页");
		}
		return bufstr.toString();
	}

	private final int getLastPage() {
		return this.totalPageCount;
	}

	private final int getNextPage() {
		if ((this.currentPageNo + 1) > this.totalPageCount) {
			return this.totalPageCount;
		}
		return this.currentPageNo + 1;
	}

	private final int getPrePage() {
		if ((this.currentPageNo - 1) < 1) {
			return 1;
		}
		return this.currentPageNo - 1;
	}

	private final int getFirstPage() {
		return 1;
	}

	public final String getUrl() {
		return url;
	}

	public String getParam() {
		return param;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
