<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>订单列表</title>
	<script src="${ctx}/static/scripts/trade.js?v=2.0.1" type="text/javascript"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value='0'>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择日期'" style="width:160px"/>
	   	<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="trade.searchList();" >查询</a>
	</div>
	
	<table id="contentTable"  >
		<thead><tr>
		<th>创建日期</th>
		<th>商铺名称</th>
		<th>商品</th>
		<th>目的地</th>
		<th>快递公司</th>
		<th>运单号</th>
		</tr></thead>
	</table>
	<div id="loadingDiv" class="hint hide">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>
	<script>
		var ctx="${ctx}";
		 
		$(document).ready(function(){
	trade.initTable();
});

var trade={};
trade.initTable=function(){
 var curr_time = new Date();
   var strDate = curr_time.getFullYear()+"-";
   strDate += curr_time.getMonth()+1+"-";
   strDate += curr_time.getDate()-1+" ";
   strDate += curr_time.getHours()+":";
   strDate += curr_time.getMinutes()+":";
   strDate += curr_time.getSeconds();
   $("#beigainTime").datebox("setValue", strDate); 
	$('#contentTable').datagrid({
	    url:ctx+'/NoEXISTS/listData',
	    height:850,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	startDate:$("#beigainTime").val()
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'createDate',title:'创建日期',width:120},
	        {field:'shopName',title:'商铺名称',width:180},
	        {field:'items',title:'商品',width:250},
	        {field:'buyerNick',title:'昵称',width:150},
	        {field:'receiverName',title:'收件人',width:150},
	        {field:'phone',title:'联系方式',width:100},
	        {field:'addressInfo',title:'目的地',width:400},
	        {field:'expressCompany',title:'快递公司',width:80},
	        {field:'expressOrderno',title:'运单号',width:120},
	        {field:'remark',title:'备注',width:120}
	    ]],
	    pagination:true
	});
	
	var pagination =$('#contentTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 500,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

trade.searchList=function(){
	$('#contentTable').datagrid('load', {
   		 userId:$("#userId").combobox('getValue'),
   		 startDate:$("#beigainTime").textbox('getValue')
	});
}
	</script>
</body>
</html>
