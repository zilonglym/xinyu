<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>入库单列表</title>
	<script>
		var ctx="${ctx}";
	</script>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/InitArtDialog.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/plugins/iframeTools.source.js" type="text/javascript"></script>
</head>
<body>
	<legend></legend>
	<table style="width: 80%;font-size: 20px;">
		<tr>
			<td>盘点单据编号： ${checkInventory.id}</td>
			<td>单据时间：<fmt:formatDate value="${checkInventory.createDate}" type="date" pattern="yyyy-MM-dd"/></td>
			<td>仓库： ${checkInventory.centro.name}</td>
			<td>操作人： ${checkInventory.person.username}</td>
		</tr>
	</table>
	<br/>
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>序号</th>
		<th>商品编号</th>
		<th>商品名称</th>
		<th>数量</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${checkInventory.details}" var="obj" varStatus="i">
		<tr>
			<td>${i.index+1}</td>
			<td>${obj.item.code}</td>
			<td>${obj.item.title}</td>
			<td>${obj.quantity}</td>
		</tr>
		</c:forEach><tr>
		</tbody>
	</table>
	<div class="right">
	<c:if test="${checkInventory.status=='SAVE'}">
		<form id = "myForm" action="${ctx}/stock/checkInventory/audit?id=${checkInventory.id}" method="post">
			<button type="button" class="btn btn-primary" id="btnOpen" style="margin-bottom: 10px;" onclick="javascirpt:$('#myForm').submit()">盘点确认</button>
		</form>  
	</c:if>	
	</div>
</body>
</html>
