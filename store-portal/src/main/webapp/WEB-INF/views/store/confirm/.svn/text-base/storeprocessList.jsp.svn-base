<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>入库单确认</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<br>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>入库单号</th>
		<th>总件数</th>
		<th>创建日期</th>
		<th>状态</th>
		<th>操作</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order">
			<tr>
				<td style="vertical-align: middle;">${order.code}</td>
				<td style="vertical-align: middle;">${order.planqty}</td>
				<td style="vertical-align: middle;">${order.createtime}</td>
				<td style="vertical-align: middle;">	
				
				</td>
				<td style="vertical-align: middle;">
					<a href="${ctx}/store/entry/confirm/storeprocessDetail/${order.id}">确认</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
