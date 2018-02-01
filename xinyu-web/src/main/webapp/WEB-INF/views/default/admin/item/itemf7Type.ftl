<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>商品类型修改</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<table width="100%" border="0">
		<tr>
			<td>商品名称:</td>
			<td><input type="text" readonly="readonly" class="easyui-textbox" value="${item.name!''}"/></td>
		</tr>
		<tr>
			<td>商品类型：</td>
			<td>
				<select id="itemType" name="itemType" class="easyui-combobox">
					<option value="ZC">正常商品</option>
					<option value="ZH">组合商品</option>
					<option value="ZP">赠品</option>
					<option value="OTHER">其他</option>
				</select>
			<td>
		</tr>
	</table>
	
	<input type="hidden" id="itemId" name="itemId" value="${item.id!''}" />
	<script>
		var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/items.js"></script>
</body>
</html>
