<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>运费列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="userId" class="easyui-combobox" name="userId" style="width:160px;">
				<option value="">全部</option>
				<#list users as user>
					<option value="${user.id}">${user.subscriberName}</option>
				</#list>
			</select>
			<select id="sysId" name="sysId" class="easyui-combobox" style="width:160px;">
				<option value="">全部</option> 
				<#list companys as company>
					<option value="${company.id}">${company.description}</option>
				</#list>
			</select>
			<input id="area" class="easyui-textbox" name="area"></input>
			<a href="javascript:void(0);" onclick="price.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:void(0);" onclick="price.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
	</div>
	<div data-options="title:'运费列表'" style="padding:2px;">
		<table id="tb_price" >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/price.js?v=1.01"></script>
</body>
</html>
