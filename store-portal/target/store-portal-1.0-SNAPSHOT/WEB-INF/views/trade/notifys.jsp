<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>通知用户签收</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
	  	<a href="###" onclick="javascript:notifys.send()" class="easyui-linkbutton">上传淘宝发货</a>
	</div>
	
	<table id="tb_notifys"  >
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
		notifys.initTable();
	});
	
	var notifys={};
	notifys.initTable=function(){
		$('#tb_notifys').datagrid({
		    url:ctx+'/trade/notifys/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    columns:[[
				{field:'tid',checkbox:true},
		        {field:'payTime',title:'付款时间',width:120},
		        {field:'shippingType',title:'物流方式',width:80,formatter:function(value,row,index){
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
		        {field:'company',title:'物流公司',width:80},
		        {field:'orderno',title:'物流单号',width:80},
		        {field:'buyerNick',title:'昵称',width:100},
		        {field:'receiverName',title:'收货人',width:80},
		        {field:'phone',title:'联系方式',width:100},
		        {field:'address',title:'收货地址',width:250},
		        {field:'items',title:'商品',width:250}
		    ]],
		    pagination:true
		});
		
		var pagination =$('#tb_notifys').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200,250,500],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	
	notifys.send=function(){
		var rows=$('#tb_notifys').datagrid('getChecked');
		if(rows==null || rows==''){
			$.messager.alert('订单发货','请选择要发货的订单!!');
			return;
		}else{
			var ids=new Array();
			for(var i = 0, len = rows.length; i<= len-1; i++ ){
				ids.push(rows[i].tid);
			}
			var action = "${ctx}/rest/trade/notify?tradeIds=" + ids;
  			$.post(action, function(data){
  				$.messager.alert('订单发送',data);
  			});	  		
		}
		
	}
	</script>
</body>
</html>
