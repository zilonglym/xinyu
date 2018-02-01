<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>商品wms重量录入</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<table width="100%" border="0">
		<tr>
			<td>商品名称:</td>
			<td><input type="text" class="easyui-textbox" value="${item.name}" disabled/></td>
		</tr>
		<tr>
			<td>包裹重量:</td>
			<td><input  id="packageWeight" name="packageWeight" 
				class="easyui-numberbox"/></td>
		</tr>
		<tr>
			<td>净重量:</td>
			<td><input  id="weight" name="weight" 
				class="easyui-numberbox" value="${item.grossWeight}"/></td>
		</tr>
	</table>
	
	<input type="hidden" id="itemId" name="itemId" value="${item.id}" />
	<script>
		var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/items.js?v=1.01"></script>
</body>
</html>
