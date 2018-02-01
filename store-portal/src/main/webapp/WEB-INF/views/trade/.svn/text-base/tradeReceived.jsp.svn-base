<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>已接收订单</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		
		<input id="q" name="q" class="easyui-textbox"/>
		<a href="###" onclick="javascript:received.search();" class="easyui-linkbutton"> 查询 </a>
	</div>
	
	<table id="tb_received"  >
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
		received.initTable();
	});
	
	var received={};
	received.initTable=function(){
		$('#tb_received').datagrid({
		    url:ctx+'/trade/received/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    onLoadSuccess(data){
				if(data.total==0){
					 alert("没有您需要查询的订单");
				}
			},
		    columns:[[
				{field:'tid',checkbox:true},
		        {field:'payTime',title:'付款时间',width:150},
		        {field:'tb',title:'淘宝订单号',width:250},
		        {field:'status',title:'订单状态',width:120,formatter:function(value,row,index){
		        	if(value=='TRADE_WAIT_CENTRO_AUDIT'){
		        		return "等待物流通审核";
		        	}else if(value=='TRADE_WAIT_EXPRESS_SHIP'){
		        		return "审核已通过,正在配送";
		        	}else if(value=='TRADE_WAIT_EXPRESS_NOFITY'){
		        		return "物流通已发货 通知用户签收";
		        	}else if(value=='TRADE_WAIT_BUYER_RECEIVED'){
		        		return "物流通已发货 等待买家签收";
		        	}
		        }},
		        {field:'shippingType',title:'物流方式',width:120,formatter:function(value,row,index){
		        	if(value=='free'){
		        		return "卖家包邮";
		        	}else if(value=='post'){
		        		return "平邮";
		        	}else if(value=='express'){
		        		return "快递";
		        	}else if(value=='ems'){
		        		return "EMS";
		        	}else if(value=='virtual'){
		        		return "虚拟发货";
		        	}else{
		        		return "无要求";
		        	}
		        }},
		        {field:'receiverName',title:'收货人',width:100},
		        {field:'phone',title:'联系方式',width:150},
		        {field:'address',title:'收货地址',width:250},
		        {field:'items',title:'商品',width:400},
		        {field:'remark',title:'备注',width:250}
		    ]],
		    pagination:true
		});
		
		var pagination =$('#tb_received').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200,250,500],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	
	received.search=function(){
		$('#tb_received').datagrid('load',{
			q:$('#q').textbox('getValue')
		});
	}
	
	</script>
</body>
</html>
