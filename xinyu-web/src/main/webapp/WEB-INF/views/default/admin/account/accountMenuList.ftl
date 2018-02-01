<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>菜单信息列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<input  style="width:250px;margin-left: 5px;" id="title" name="title" type="text" class="easyui-textbox" />
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="menu.search();" >查询</a>
		<a href="javascript:menu.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
	</div>
	<div data-options="title:'菜单信息列表'" style="padding:2px;">
		<table id="tb_menu">
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
	<script type="text/javascript" src="${base}/scripts/system/admin/menu.js"></script>
</body>
</html>
