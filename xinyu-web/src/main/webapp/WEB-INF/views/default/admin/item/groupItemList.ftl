<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>单据明细列表</title>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px; margin-left:5px;">
		<select id="user" name="user" class="easyui-combobox" style="width:150px;">
			<option value='${user.id}'>${user.subscriberName}</option> 
		</select>
		<input id="title" name="title" class="easyui-textbox" style="width:200px"/>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="javascript:searchItem();" >查询</a>
	</div>

	<table id="tb_groupItem">
		<thread>
		
		</thread>
	</table>
	<script>
	var ctx="${ctx}";
	$(document).ready(function(){
		var userId = $("#user").combobox("getValue");
			$('#tb_groupItem').datagrid({
				url:ctx+'/group/item/listData',
				height:535,
				singleSelect:false,
				rownumbers:true,
				queryParams:{
					userId:userId
				},
				columns:[[
					{field:'id',checkbox:true},
					{field:'itemCode',title:'商品编码',width:100},
					{field:'name',title:'商品名称',width:100},
					{field:'color',title:'商品属性',width:100},
					{field:'barCode',title:'商品条码',width:100}
				]]
			});
	});
	function searchItem(){
		$('#tb_groupItem').datagrid('load',{
			userId:$("#userId").combobox("getValue"),
			itemName:$("#title").textbox("getValue")
		});
	}
	</script>
</body>
</html>
