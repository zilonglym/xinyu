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
	<script src="${ctx}/static/scripts/local.js" type="text/javascript"></script>
</head>
<body>
	<div id="bar" style="margin-top: 2px;margin-bottom: 2px;">
		<select id="sid" name="sid" class="easyui-combobox" style="width:100px;">
			<option value=''>全部</option>
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'>${user.name}</option>
			</c:forEach>
		</select>
		<input id="aq" name="aq" class="easyui-textbox" type="text" style="width:200px" data-options="prompt:'请输入关键字'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="local.searchItem();" >查询</a>
	</div>
	<table id="tb_groupItem">
		<thread>
		
		</thread>
	</table>
	<script>
	var ctx="${ctx}";
	$(document).ready(function(){
		$('#tb_groupItem').datagrid({
				url:ctx+'/pcLocal/localPlate/item/listData',
				height:565,
				singleSelect:true,
				rownumbers:true,
				toolbar:'#bar',
				columns:[[
				          {field:'id',hidden:true},
			              {field:'shopName',title:'商家',width:80},
			              {field:'name',title:'品名',width:300},
			              {field:'sku',title:'属性',width:60},
			              {field:'barCode',title:'条码',width:100}      
				]],
				pagination:true
			});
		var pagination = $('#tb_groupItem').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数,默认为10 
			pageList:[100,200,500], 
        		showPageList: false,
        		beforePageText: '第',//页数文本框前显示的汉字 
        		afterPageText: '页    共 {pages} 页', 
        		displayMsg: '当前{from} - {to} 条   共 {total} 条'
		});
	});
	</script>
</body>
</html>
