<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>已售出商品统计</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
	  	<a href="###" onclick="javascript:sellout.search()" class="easyui-linkbutton">查询</a>
	</div>
	
	<table id="tb_sellout"  >
		<thead>
			<tr>
	
			</tr>
		</thead>
	</table>
	<div id="loadingDiv" class="hint hide">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>
	<script type="text/javascript">
	var ctx="${ctx}";
	$(document).ready(function(){
		sellout.initTable();
	});
	
	var sellout={};
	sellout.initTable=function(){
		$('#tb_sellout').datagrid({
		    url:ctx+'/report/sellout/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    columns:[[
		        {field:'itemName',title:'商品名称',width:120},
		        {field:'sku',title:'sku属性',width:120},
		        {field:'num',title:'数量',width:250}
		    ]],
		    pagination:true
		});
		
		var pagination =$('#tb_sellout').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200,250,500],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	
	sellout.search=function(){
		$('#tb_sellout').datagrid('load',{
			startDate:$('#beigainTime').datetimebox('getValue'),
			endDate:$('#lastTime').datetimebox('getValue')
		});
	}
	</script>
</body>
</html>
