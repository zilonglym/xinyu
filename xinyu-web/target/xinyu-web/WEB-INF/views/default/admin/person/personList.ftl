<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>员工列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<input id="name" class="easyui-searchbox" name="name" style="width:300px;"></input>
			<a href="javascript:person.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
			<a href="javascript:person.remove();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">删除</a>
			<a href="javascript:person.edit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
	</div>
	<div data-options="title:'员工列表'" style="padding:2px;">
		<table id="tb_person"  >
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
	<script type="text/javascript" src="${base}/scripts/system/admin/person.js?t=11"></script>
</body>
</html>
