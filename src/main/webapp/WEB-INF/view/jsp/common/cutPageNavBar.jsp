<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/jsp/common/taglib.jsp"%>

<c:if test="${not empty page && not empty page.list}">
<!-- 分页脚本 -->
<c:choose>
	<c:when test="${not empty param.formId}">
		<script type="text/javascript">
			function goPage(pageNo){
				debugger;
				var form_ = document.getElementById("${param.formId}");
				if(form_ == null){
					alert("Form对象[${param.formId}]为空！");
				}else{
					if(form_.action.indexOf('?') != -1){
						form_.action = form_.action + "&pageNo=" + pageNo + "&pageSize=${page.pageSize}";
					}else{
						form_.action = form_.action + "?pageNo=" + pageNo + "&pageSize=${page.pageSize}";
					}
					form_.submit();
				}
			}
		</script>
	</c:when>
	<c:otherwise>
		<script type="text/javascript">alert('分页参数formId为空!');</script>
	</c:otherwise>
</c:choose>

<!-- 分页导航 -->
<div>
	<table border="0" width="100%">
		<tr>
			<td align="left" valign="top"><b>· </b>共${page.total}条记录&nbsp;第${page.pageNum}/${page.pages}页 </td>
			<td align="right">
				<nav aria-label="Page navigation">
					<ul class="pagination">
						<li><a href="javascript:goPage(1);" aria-label="Previous">首页</a></li>
						<c:if test="${page.hasPreviousPage}">
							<li><a href="javascript:goPage(${page.pageNum-1});" aria-label="Previous">上一页</a></li>
						</c:if>
						<c:forEach begin="1" end="${page.pages}" var="each">
							<c:choose>
								<c:when test="${each == page.pageNum}">
									<li class="active"><a style="color: black;">${each}</a></li>
								</c:when>
								<c:when test="${each >= (page.pageNum-3) && each <= (page.pageNum+3)}">
									<li><a href="javascript:goPage(${each});" aria-label="Previous">${each}</a></li>
								</c:when>
							</c:choose>
						</c:forEach>
						<c:if test="${page.hasNextPage}">
							<li><a href="javascript:goPage(${page.pageNum+1});" aria-label="Next">下一页</a></li>
						</c:if>
						<li><a href="javascript:goPage(${page.pages});" aria-label="Previous">尾页</a></li>
					</ul>
				</nav>
			</td>
		</tr>
	</table>
</div>
</c:if>