<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/><html>
<html>
	<head>
		<title>店铺设置</title>
		<script type="text/javascript" src="${ctx}/static/scripts/shopList.js"></script>
	    <script>
			var ctx="${ctx}";
			$(document).ready(function(){
				shopList.initTable();
			});
	 </script>
	</head>
	<body>
		<div id="button-bar" style="margin-bottom:10px;">
			<a href="javascript:shopList.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加新店铺</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除店铺</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-refresh'">重新加载</a>
		</div>
		<table id="tb_shop" >
		<thead>
			<tr>
				<td>商家名</td>
				<td>店铺名</td>
				<td>店铺类型</td>
				<td>店铺状态</td>
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	</body>
</html>