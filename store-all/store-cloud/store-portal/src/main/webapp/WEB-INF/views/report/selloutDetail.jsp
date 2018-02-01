<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
</head>
<body>

<c:if test="${empty result}">
	无售出商品
</c:if>

<c:if test="${not empty result}">
<span class="label label-success">总共售出${total}件商品</span>
<table id="contentTable" class="table table-striped table-condensed"  >
<thead><tr class="always_top">
	<th>商品名称</th>
	<th>售出数量</th>
	</tr></thead>
	<tbody>
		<c:forEach items="${result}" var="val">
		<tr>
			<td>${val.itemName}</td>
			<td>${val.num}</td>
		</tr>
		</c:forEach>
	</tbody>
</table>

<span class="label label-success">总共售出${total}件商品</span>
</c:if>
</body>
</html>