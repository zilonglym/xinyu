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
	<button type="button" class="btn btn-primary" id="btnOpen" style="margin-bottom: 10px;">新建盘点单</button>
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr style="background-color: #9A9A9A">
		<th>序号</th>
		<th>创建日期</th>
		<th>单据编号</th>
		<th>仓库</th>
		<th>操作人</th>
		<th>状态</th>
		<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${checkInventoryList}" var="check" varStatus="i">
		<tr>
			<td width="50px">${i.index+1}</td>
			<td><fmt:formatDate value="${check.createDate}" type="date" pattern="yyyy-MM-dd"/></td>
			<td>${check.id}</td>
			<td>${check.centro.name}</td>
			<td>${check.person.username}</td>
			<td>
				<c:if test="${check.status=='SAVE'}">未确认</c:if>
				<c:if test="${check.status=='AUDIT'}">已确认</c:if>
			</td>
			<td>
				<a href="${ctx}/stock/checkView?id=${check.id}" target="_blank">查看</a>
				<a href="${ctx}/report/checkInventory/xls?id=${check.id}">导出</a>
			</td>
		</tr>
		</c:forEach><tr>
		</tbody>
	</table>
	<script>
		$("#btnOpen").on("click",function(){
			var url="${ctx}/stock/toCheckInventoryAdd";
			art.dialog.open(url,{title:"f7title", lock: true,skin: 'aero',padding:0,width:800,height:430});
		})
	</script>
</body>
</html>
