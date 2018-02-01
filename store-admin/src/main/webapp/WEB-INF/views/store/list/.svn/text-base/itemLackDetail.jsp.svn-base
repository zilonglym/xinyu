<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<script>
	
	</script>
</head>
<body>
 <form  id="subForm" action="${ctx}/store/shipOrder/itemLack/${order.id}" method="post">
	<table class="table table-striped table-condensed"  >
		<tr>
				<td>发货单编号</td><td>${order.orderno}</td>
				<td>状态</td><td>${order.status}</td>
		</tr>
	</table>
   
	<table id="contentTable" class="table table-condensed alert">
		<thead>
		<tr>
		<th>商品编号</th>
		<th>商品标题</th>
		<th>应发数量</th>
		<th>数量</th>
		<th>缺货原因</th>
		<th>已发数量</th>
		</thead>
		<tbody>
		<c:forEach items="${detailList}" var="detail">
		<tr>
				<td>${detail.item.code}</td>
				<td>${detail.item.title}</td>
				<td>${detail.num}</td>
				
				<td>
				<input name="detailId" type="hidden" value ="${detail.id}"/>
				<input name="item" type="hidden" value ="${detail.item.code}"/>
				<input name="itemId" type="hidden" value ="${detail.item.id}"/>
				<input name="numTemp" type="hidden" value = "${detail.num - detail.actualnum}"/><input name="num" type="text" value = "0"/></td>
				<td>
					<select name="reason">
						<option value="系统报缺">系统报缺</option>
						<option value="实物报缺">实物报缺</option>
					</select>
				</td>
				<td>${detail.actualnum}<input name="actualnum" type="hidden" value ="${detail.actualnum}"/></td>
			</tr>
		</c:forEach>
		<!--
		<c:forEach items="${order.details}" var="detail">
			<tr>
				<td>${detail.item.code}</td>
				<td>${detail.item.title}</td>
				<td>${detail.item.sku}</td>
				<td>${detail.item.weight}</td>
				<td>${detail.num}</td>
				<td>${detail.actualnum}</td>
			</tr>
		</c:forEach>
		-->
		
		
		</tbody>
	</table>
	<a class="btn" href="javascript:void(0);$('#subForm').submit();">确认</a>
	</form>
	
</body>
</html>
