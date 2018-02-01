<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>店铺设置</title>
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	</head>
	<body>
		<div id="button-bar" style="margin-bottom:10px;">
			<a href="javascript:shopList.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加新店铺</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">删除店铺</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-refresh'">重新加载</a>
		</div>
		<table id="tb_shop" >
		<thead>
			<tr>
				
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	</body>
	<script>
		var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/shopList.js"></script>
</html>