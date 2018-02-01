<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>单据详情</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<br>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead>
		<tr>
		<td style="width:100px;">单号</td><td>${order.orderno}</td>
		<td>单据类型</td><td>
		<c:if test="${order.type == 'entry'}">
		入库单
		</c:if>
		<c:if test="${order.type == 'return'}">
		退货入库单
		</c:if>
		<c:if test="${order.type == 'send'}">
		出库单
		</c:if>
		<c:if test="${order.type == 'deliver'}">
		发货单
		</c:if>
		</td>
		<td></td>
		</tr>
		<tr>
		<th>单据行号</th>
		<th>商品名称</th>
		<th>商品编号</th>
		<th>库存类型</th>
		<th>数量</th>
		<thead>
		
		<tbody>
		<c:forEach items="${list}" var="obj">
			<tr>
				<td style="vertical-align: middle;">${obj.orderLineNo}</td>
				<td style="vertical-align: middle;">${obj.item.title}</td>
				<td style="vertical-align: middle;">${obj.item.code}</td>
				<td style="vertical-align: middle;">
				${obj.inventoryType}</td>
				<td style="vertical-align: middle;">${obj.num}</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
