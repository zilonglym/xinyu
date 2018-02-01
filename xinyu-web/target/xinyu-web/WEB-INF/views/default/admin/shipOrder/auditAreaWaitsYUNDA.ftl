<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
	<title>韵达地址批量审核</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>

	
	<script  type="text/javascript">
	   var ctx="${ctx}";
	
		$(document).ready(function(){
			 audit.initTable();
		});	
	
		var audit={};
audit.initTable=function(){ 
	var editRow = undefined;
	$('#auditTable').datagrid({
	    url:ctx+'/shipOrder/ajax/auditAreaYUNDA/${type}',
	    height:850,
	    queryParams:{
	    	 ids:"${ids}"
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
	    	{field:'id',hidden:true},
	        {field:'shopName',title:'商铺',width:120},
	        {field:'createDate',title:'创建时间',width:120},
	        {field:'erpOrderCode',title:'订单号',width:120},
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
