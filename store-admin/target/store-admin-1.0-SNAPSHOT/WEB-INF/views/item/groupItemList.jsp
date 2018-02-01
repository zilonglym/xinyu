<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>单据明细列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px; margin-left:5px;">
		<select id="user" name="user" class="easyui-combobox" style="width:150px;">
			<option value='${user.id}'>${user.shopName}</option> 
		</select>
		<input id="name" name="name" class="easyui-textbox" type="text" style="width:200px"/>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchItem();" >查询</a>
	</div>

	<table id="tb_groupItem">
		<thread>
		
		</thread>
	</table>
	<script>
	var ctx="${ctx}";
	$(document).ready(function(){
		var userId = $("#user").combobox("getValue");
			$('#tb_groupItem').datagrid({
				url:ctx+'/itemGroup/item/listData',
				height:535,
				singleSelect:false,
				rownumbers:true,
				queryParams:{
					userId:userId
				},
				columns:[[
					{field:'id',checkbox:true},
					{field:'code',title:'商品编码',width:100},
					{field:'title',title:'商品名称',width:100},
					{field:'sku',title:'商品属性',width:100},
					{field:'barCode',title:'商品条码',width:100}
				]]
			});
	});
	function searchItem(){
		$('#tb_groupItem').datagrid('load',{
			userId:$("#user").combobox('getValue'),
			itemName:$("#name").textbox('getValue')
		});
	}
	</script>
</body>
</html>
