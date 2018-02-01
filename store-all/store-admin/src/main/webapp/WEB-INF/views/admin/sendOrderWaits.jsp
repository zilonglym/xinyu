<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>运单设置</title>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tooltip.js" type="text/javascript"></script>
	<script>
		$(function () {
	       $("[rel='tooltip']").tooltip();
	    });
	</script>
</head>

<body>

	<div class="alert alert-info span4">
		待打印运单列表，请使用<strong>批量打单系统</strong>处理。
	</div>
	
	<table id="contentTable" class="table table-striped"  >
		<thead><tr>
		<th>创建日期</th>
		<th>商铺名称</th>
		<th>出库单号</th>
		<th>电子面单</th>
		<th>传统面单处理</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${order.createUser.shopName}</td>
				<td>${order.orderno}</td>
				<td>${order.expressCompany} : ${order.expressOrderno}</td>
				<td><a href="${ctx}/trade/send/do/${order.id}" rel="tooltip" title="手工设置已发货运单号。（建议使用批量打单系统）">设置运单号</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
