<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易订单仓库设置</title>
	<link href="${ctx}/static/styles/step.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/prod.css" rel="stylesheet" media="all" />
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-confirm.js" type="text/javascript"></script>
</head>
<body>
	<form id="frm" action="${ctx}/trade/submitDistOrder">
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	<tr>
		<td>选择仓库:</td>
		<td>
			<select name="centro">
				<c:forEach items="${list}" var="centro">
					<option value="${centro.id}">${centro.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<input id="cancel_btn" class="btn" type="submit" value="保存"/>
		</td>
	</tr>
		<input type="hidden" name="tradeId" value="${trade.id}" />
	</table>
	</form>
	
</body>
</html>
