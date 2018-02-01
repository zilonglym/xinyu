<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>出库列表</title>
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
		<select id="express" name="express" class="easyui-combobox" style="width:190px;">
			<option value=''>全部</option> 
			<#list expressList as express >
				<option value='${express.id}'>${express.description}</option>
			</#list>
		</select>
		<select id="status" name="status" class="easyui-combobox" style="width:190px;">
			<option value=''>全部</option> 
			<option value='success'>成功</option> 
			<option value='fail'>失败</option> 
		</select>
		<input class="easyui-datetimebox" name="startDate" id="startDate" data-options="prompt:'请选择起始时间'"/>
			~
		<input class="easyui-datetimebox" name="endDate" id="endDate" data-options="prompt:'请选择截止时间'"/>  
		<input  style="width:250px;margin-left: 5px;" id="searchText" name="searchText" type="text" class="easyui-textbox"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="checkout.search();" >查询</a>
	</div>
	
	<table id="tb_checkout"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/checkout.js?v=1.01"></script>
</body>
</html>
