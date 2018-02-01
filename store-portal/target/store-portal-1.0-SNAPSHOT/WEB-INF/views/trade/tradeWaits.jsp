<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>未处理订单</title>
</head>                                                                                                         
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="status" name="status" class="easyui-combobox">
			<option value="useable">可发送</option>
			<option value="related">已发送</option>
			<option value="failed">发送失败</option>
			<option value="refund">已退款</option>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/>  
		<input class="easyui-textbox" name="q" id="q" data-options="prompt:'请输入 商品编码 地址 备注 关键字'"/>   
		<a href="###" onclick="javascript:waits.fetchTrade()" class="easyui-linkbutton">查询</a>
		
		<!--<a href="###" onclick="javascript:waits.fetchTrade()" class="easyui-linkbutton"> 今 天 </a>
	  	<a href="###" onclick="javascript:waits.fetchTrade()" class="easyui-linkbutton"> 昨 天 </a>
	  	<a href="###" onclick="javascript:waits.fetchTrade()" class="easyui-linkbutton">最近一周</a>-->
	  	<a href="###" onclick="javascript:waits.related()" class="easyui-linkbutton">推送仓库发货</a>
	</div>
	
	<table id="tb_waits"  >
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
		waits.initTable();
	});
	
	
	
	
	
	var waits={};
	waits.initTable=function(){
		$('#tb_waits').datagrid({
		    url:ctx+'/trade/waits/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    columns:[[
				{field:'tid',checkbox:true},
		        {field:'payTime',title:'付款时间',width:150},
		        {field:'code',title:'商品编码',width:100,},
		        {field:'items',title:'商品标题',width:300,},
		        {field:'address',title:'收货地址',width:300},
		        {field:'buyerNick',title:'昵称',width:100},
		        {field:'remark',title:'备注',width:300},
		        {field:'receiverName',title:'收货人',width:80},
		        {field:'phone',title:'联系方式',width:100},
		        {field:'tb',title:'淘宝订单号',width:100},	
		        {field:'tid',title:'TID',width:100}	
		    ]],
		    onLoadSuccess(data){
				if(data.total==0){
					alert("没有您需要查询的订单");
				}
			},
		    pagination:true,
			rowStyler:function(index,row){
				if (row.sellerFlag=="1"){
					return 'background-color:red;';
				}else if(row.sellerFlag=="2"){
					return 'background-color:yellow;';
				}else if(row.sellerFlag=="3"){
					return 'background-color:green;';
				}else if(row.sellerFlag=="4"){
					return 'background-color:blue;';
				}else if(row.sellerFlag=="5"){
					return 'background-color:purple;';
				}
			}
		});
		
		var pagination =$('#tb_waits').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200,250,500],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	 }
	
	waits.fetchTrade=function(){
		$('#tb_waits').datagrid('load',{
			startDate:$("#beigainTime").datetimebox('getValue'),
		    endDate:$("#lastTime").datetimebox('getValue'),
			status:$('#status').combobox('getValue'),
			q:$('#q').textbox('getValue')
		});
	}
	
	waits.related=function(){
		var rows=$('#tb_waits').datagrid('getChecked');
		if(rows==null || rows==''){
			$.messager.alert('订单发送','请选择要发送的订单!!');
			return;
		}else{
			var ids=new Array();
			var size=200;
			for(var i = 0, len = rows.length; i<= len-1; i++ ){
				var row=rows[i];
				ids.push(row.tid);
				if (size == 1 ) {
					 var action = ctx+"/rest/trade/send?tids=" + ids;
					  $.post(action, function(data){
						   $.messager.alert('订单发送',data);
						   waits.fetchTrade();
					   });
  					ids=[];
  				} else if (i == len-1) {
  				   var action = ctx+"/rest/trade/send?tids=" + ids;
  				   $.post(action, function(data){
  					 $.messager.alert('订单发送',data);
  					 waits.fetchTrade();
  				   });
  				}
			}
		}
	}
	</script>
</body>
</html>
