<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>明细列表</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px; margin-left:5px;">
		<select id="shop" class="easyui-combobox" name="shop" style="width:160px;">  
	    	<#list users as user>
	    		<option value="${user.id}">${user.subscriberName}</option>
	    	</#list>
	    	<option value="22">广东宝卡</option>
			<option value="24">小熊百洋</option>
			<option value="55">黑白调</option>
			<option value="56">广州知音</option>
			<option value="54">公司</option>
			<option value="74">游鲜生</option>
			<option value="75">火焰皇</option>
			<option value="26">山东烤箱</option>
		</select>  
		<input id="others" name="others" class="easyui-textbox" style="width:200px"/>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchItem();" >查询</a>
	</div>

	<table id="tb_item">
		<thread>
		
		</thread>
	</table>
	<script>
	var ctx="${ctx}";
	$(document).ready(function(){	
		$('#tb_item').datagrid({
			url:ctx+'/orderBack/itemListData',
			height:535,
			singleSelect:false,
			rownumbers:true,
			columns:[[
				{field:'id',checkbox:true},
				{field:'itemCode',title:'商品编码',width:100},
				{field:'name',title:'商品名称',width:100},
				{field:'specification',title:'商品属性',width:100},
				{field:'barCode',title:'商品条码',width:100}
			]],
			pagination:true
		});
		var pagination =$('#tb_item').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 100,//每页显示的记录条数，默认为10 
			pageList:[100,200],
       	    showPageList: false,
       	 	beforePageText: '第',//页数文本框前显示的汉字 
        	afterPageText: '页    共 {pages} 页', 
        	displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	});
	function searchItem(){
		$('#tb_item').datagrid('load',{
			userId:$('#shop').combobox("getValue"),
			q:$("#others").textbox("getValue")
		});
	}
	</script>
</body>
</html>
