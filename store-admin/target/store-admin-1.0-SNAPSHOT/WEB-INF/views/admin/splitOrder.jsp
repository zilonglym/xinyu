<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.2.2/js/bootstrap.min.js" type="text/javascript"></script>
<head>
	<title>拆单</title>
	<script  type="text/javascript">
	</script>
</head>
<body >

	<div data-options="title:'批量拆单'" style="padding:2px;">
	<table id="splitTable">
		<thead>
		<tr>
		<th>商铺</th>
		<th>支付日期</th>
		<th>订单来源</th>
		<th class="span3">寄送地址</th>
		<th class="span4">购买商品</th>
		<th class="span3">重量(KG)</th>
		<th class="span3">备注</th>	
		</tr>
		</thead>
	</table>
	</div> 
</body>
</html>
