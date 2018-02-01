<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>库存流水记录列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:190px;">
			<option value=''>全部</option> 
			<#list users as user >
				<option value='${user.id}'>${user.subscriberName}</option>
			</#list>
		</select>
		<input  style="width:250px;margin-left: 5px;" id="searchText" name="searchText" type="text" class="easyui-textbox" data-options="prompt:'商品名称'"/>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="inventoryRecord.search();" >查询</a>
	</div>
	
	<table id="tb_inventoryRecord"  >
		<thead>
			<tr>
			
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/inventoryRecord.js"></script>
</body>
</html>
