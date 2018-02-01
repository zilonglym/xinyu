<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>异常订单列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:200px;">
			<option value="">全部</option> 
			<#list users as user>
				<option value="${user.id}">${user.subscriberName}</option>
			</#list>
		</select>
		<select id="status" name="status" class="easyui-combobox" style="width:200px;">
			<option value="">全部</option> 			
			<option value="作废">作废</option>
			<option value="删除">删除</option>
			<option value="拦截">拦截</option>
		</select>
		<input  style="width:300px;" id="txt" name="txt" type="text" class="easyui-textbox" data-options="prompt:'运单号'"/>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="orderStop.searchList();" >查询</a>
	</div>
	
	<table id="tb_orderStop"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/orderStop.js"></script>
</body>
</html>
