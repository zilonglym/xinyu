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
	<title>顺风地址批量审核</title>
	<script  type="text/javascript">
	   var ctx="${ctx}";
	
		$(document).ready(function(){
			 audit.initTable();
		});	
	
		var audit={};
audit.initTable=function(){ 
	var editRow = undefined;
	$('#auditTable').datagrid({
	    url:ctx+'/trade/ajax/auditArea',
	    height:850,
	    queryParams:{
	    	 tradeIds:"${tradeIds}"
	    },
	    rownumbers:true,
	    pagination:true,
	    rowStyler: function(index,row){
					if (row.status =='1'){
						return 'background-color:green;color:#fff;font-weight:bold;';
					}else  if(row.status =='0'){
						return 'background-color:red;color:#fff;font-weight:bold;';
					}else  if(row.status =='2'){
						return 'background-color:black;color:#fff;font-weight:bold;';
					}
				},
	    columns:[[
	        {field:'shopName',title:'商铺',width:120},
	        {field:'buyerNick',title:'昵称',width:100},
	        {field:'phone',title:'联系手机',width:100},
	        {field:'address',title:'地址',width:450},
	        {field:'items',title:'商品',width:220},
	        {field:'weight',title:'重量',width:80},
	        {field:'msg',title:'地址匹配结果',width:300,
	        	formatter: function(value,row,index){
				   return value;
				}
			},
			{field:'audit',title:'审核结果',width:80}
	    ]]
	});
	
	var pagination =$('#auditTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 500,//每页显示的记录条数，默认为10 
		pageList:[500,1000],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}	
	</script>
</head>
<body>
	<div data-options="title:'批量审核'" style="padding:2px;">
	<table id="auditTable">
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
