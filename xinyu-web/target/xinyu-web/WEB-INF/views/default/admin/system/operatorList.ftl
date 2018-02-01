<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>操作日志</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="modelType" name="modelType" class="easyui-combobox" style="width:160px;">
			<option value="shipOrder">订单</option>
			<option value="item">商品</option>
			<option value="inventory">库存</option>
		</select>
		<select id="accountId" name="accountId" class="easyui-combobox" style="width:160px;">
			<option value="">全部</option> 
			<#list accounts as account>
				<option value="${account.id}">${account.userName}</option>
			</#list>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>  
		<input class="easyui-textbox" name="searchText" id="searchText"/>  
		<a href="javascript:operator.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'日志信息'" style="padding:2px;">
		<table id="tb_operator">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/operator.js"></script>
</body>
</html>
