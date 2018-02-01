<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>

<body>

<c:if test="${empty results}">
	无信息
</c:if>

<c:if test="${not empty results}">
<table id="contentTable" class="table table-striped table-condensed"  >
<thead>
	<tr class="always_top">
		<th>商家</th>
		<th>商品编码</th>
		<th>商品名称</th>
		<th>sku属性</th>
		<th>库存数量</th>
	</tr>
</thead>
	<tbody>
		<c:forEach items="${results}" var="val">
		<tr>
			<td><a href="${ctx}/report/inventory/xls?userId=${val.userId}">${val.shopname}</a></td>
			<td>${val.code}</td>
			<td>${val.name}</td>
			<td>${val.sku}</td>
			<td>${val.num}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>

</c:if>
</body>
</html>