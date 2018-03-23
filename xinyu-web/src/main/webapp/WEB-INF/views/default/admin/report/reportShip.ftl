<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>发货统计</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:160px;">
			<option value="">全部</option> 
			<#list users as user>
				<option value="${user.id}">${user.subscriberName}</option>
			</#list>
		</select>
		<select id="sysId" name="sysId" class="easyui-combobox" style="width:160px;">
			<option value="">全部</option> 
			<#list companys as company>
				<option value="${company.value}">${company.description}</option>
			</#list>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'截止时间'" style="width:160px"/>   
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onclick="report.exportData();">导出</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="report.search();">查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onclick="report.export();">未出库导出</a>
	</div>
	<div data-options="title:'发货统计'" style="padding:2px;">
		<table id="tb_report">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/report.js"></script>
</body>
</html>
