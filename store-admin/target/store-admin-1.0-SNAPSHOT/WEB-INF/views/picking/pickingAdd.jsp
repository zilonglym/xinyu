<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>新建分拣单</title>
	<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/picking.js" type="text/javascript"></script>
</head>
<body>
	<table id="pickingTable"  >
		<thead>
			<tr>
			<th>商品编号</th>
			<th>商品名称</th>
			<th>拣货仓库</th>
			<th>拣货数量</th>
			<th>操作</th>
			</tr>
		</thead>
	</table>
	<script>var ctx="${ctx}";
		$(document).ready(function(){
			picking.initPickingAddTable();
		});
	</script>
</body>
</html>
