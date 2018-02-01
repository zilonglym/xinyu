<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<script>
	
	</script>
</head>
<body>
	<table class="table table-striped table-condensed"  >
		<tr>
				<td>单据编号</td><td>${order.code}</td>
				<td>状态</td><td>${order.type}</td>
			</tr>
	</tabl>
    <form  id="subForm" action="${ctx}/store/entry/confirm/submitStoreprocessDetail/${order.id}" method="post">
	<table id="contentTable" class="table table-condensed alert">
		<thead><tr>
		<th>商品编号</th>
		<th>数量</th>
		<c:forEach items="${details}" var="detail">
		<tr>
				<td>${detail.itemcode}</td>
				<td><input name="detail" type="hidden" value ="${detail.id}"/><input name="quantity" type="test" value ="${detail.quantity}"/></td>
			</tr>
		</c:forEach>
		
		</tbody>
	</table>
	<a class="btn" href="javascript:void(0);$('#subForm').submit();">确认</a>
	</form>
	
</body>
</html>
