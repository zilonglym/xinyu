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
	<table style="width: 90%;font-size: 20px;">
		<tr>
			<td>仓库：${storage.centro.name}</td>
			<td>
				<c:if test="${storage.status.key=='ENTRY_DELIVERY'}">
					出库单号: ${storage.orderNo}
				</c:if>
				<c:if test="${storage.status.key!='ENTRY_DELIVERY'}">
					入库单号: ${storage.orderNo}
				</c:if>
			</td>
			<td>单据时间: <fmt:formatDate value="${storage.operateTime}" type="date" pattern="yyyy-MM-dd"/></td>
			<td>备注: ${storage.description}</td>
		</tr>
	</table>
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>序号</th>
		<th>商品条码</th>
		<th>商品编号</th>
		<th>商品名称</th>
		<th>数量</th>
		<th>库位</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${storage.items}" var="obj" varStatus="i">
		<tr>
			<td>${i.index+1}</td>
			<td>${obj.item.description}</td>
			<td>${obj.item.code}</td>
			<td>${obj.item.title}</td>
			<td>${obj.quantity}</td>
			<td>${obj.item.position}</td>
		</tr>
		</c:forEach><tr>
		
		</tbody>
	</table>
	<input type="hidden" id="status" name="status" value="${status}" />
	<script>
		
		$("#btnOpen").on("click",function(){
			var url=ctx+'/storage/f7toCreate?status='+$("#status").val();
			art.dialog.open(url,{title:"f7title", lock: true,skin: 'aero',padding:0,width:800,height:430});
		})
	</script>
</body>
</html>
