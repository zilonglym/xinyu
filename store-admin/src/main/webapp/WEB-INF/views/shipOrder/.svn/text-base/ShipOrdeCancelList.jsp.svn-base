<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>作废订单列表</title>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;margin-left:5px;">		
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>  
		<input class="easyui-textbox" name="orderCode" id="orderCode" data-options="prompt:'请输入订单单号'" style="width:160px"/>  
		<a href="javascript:cancel.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'取消订单列表'" style="padding:2px;">
		<table id="tb_shipOrder_cancel">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 	
	$(document).ready(function(){
		cancel.initTable();
	});

	var cancel={};
	cancel.initTable=function(){
		$('#tb_shipOrder_cancel').datagrid({
		    url:ctx+'/trade/Cancel/listData',
		    height:850,
		    rownumbers:true,
		    columns:[[
		        {field:'createDate',title:'创建日期',width:160},
		        {field:'userName',title:'商铺名称',width:180},
		        {field:'orderCode',title:'订单单号',width:180},
		        {field:'name',title:'收件人',width:100}
		    ]],
		    pagination:true
		});
		
		var pagination =$('#tb_shipOrder_cancel').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}

	cancel.search=function(){
		$('#tb_shipOrder_cancel').datagrid('load', {
			orderCode:$("#orderCode").textbox('getValue'),
	   		 startDate:$("#beigainTime").datetimebox('getValue'),
	   		 endDate:$("#lastTime").datetimebox('getValue')
		});
	}
	</script>
</body>
</html>
