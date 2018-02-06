<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:200px;">
			<option value="">全部</option> 
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
		<input class="easyui-datetimebox" name="startDate" id="startDate" data-options="prompt:'请选择起始时间'" style="width:160px" />
		~
		<input class="easyui-datetimebox" name="endDate" id="endDate" data-options="prompt:'请选择截止时间'" style="width:160px" />   
		<input  style="width:150px;" id="q" name="q" type="text" class="easyui-textbox"/>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="backOrder.search();" >查询</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="backOrder.add();" >新建</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onclick="backOrder.exportData();" >导出</a>
	</div>
	
	<table id="tb_backOrder"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/backOrder.js?t=1"></script>
</body>
</html>
