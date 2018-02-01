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
<thead><tr class="always_top">
	<th>商家</th>
	<th>已发送运单 （合计${total}单）</th>
	<th>已售出商品 （合计${totalItmes}件）</th>
	</tr></thead>
	<tbody>
		<c:forEach items="${results}" var="val">
		<tr>
			<td><a href="${ctx}/report/inventory/xls?userId=${val.userId}">${val.shopName}</a></td>
			<td>${val.num} <a href="${ctx}/report/ship/xls?userId=${val.userId}&startDate=${startDate}&endDate=${endDate}">明细下载</a></td>
			<td>${val.items} <a href="${ctx}/report/ship/item/xls?userId=${val.userId}&startDate=${startDate}&endDate=${endDate}">汇总下载</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>

</c:if>
</body>
</html>