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
	
	
	<form id = "myForm" action="${ctx}/storage/index_delivery" method="post">
	<select id="selectUser" name="userId">
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
	<button class="btn btn-primary"  onclick="javascirpt:$('#myForm').submit()" style="margin-bottom: 10px;">确定</button>
	<button type="button" class="btn btn-primary" id="btnOpen" style="margin-bottom: 10px;">新建出库单</button>
	</form>
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr style="background-color: #9A9A9A">
		<th>序号</th>
		<th>创建日期</th>
		<th>单据编号</th>
		<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach items="${storageList}" var="storage" varStatus="i">
		<tr>
			<td width="50px">${i.index+1}</td>
			<td>${storage.operateTime}</td>
			<td>${storage.orderNo}</td>
			<td>
				<a href="${ctx}/storage/view?id=${storage.id}" target="_blank">查看</a>
				<a href="${ctx}/storage/report/xls?id=${storage.id}">导出</a>
			</td>
		</tr>
		</c:forEach><tr>
		
		</tbody>
	</table>
	<input type="hidden" id="status" name="status" value="${status}" />
	<script>
		
		$("#btnOpen").on("click",function(){
			var url=ctx+'/storage/f7toCreate_delivery?status='+$("#status").val();
			art.dialog.open(url,{title:"f7title", lock: true,skin: 'aero',padding:0,width:800,height:430});
		})
	</script>
</body>
</html>
