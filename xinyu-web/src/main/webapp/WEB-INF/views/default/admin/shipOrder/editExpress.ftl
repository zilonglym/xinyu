<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>修改快递</title>
</head>
<body>
	<table cellpadding="5" style="margin-top:10px;margin-left:10px;">
		<div style="margin-top:10px;margin-left:10px;">
			选择快递：
			  <select id="expressName" name="expressName" class="easyui-combobox" style="width:200px;">
					<#list expressNames as expressName>
						<option value='${expressName.value}'>${expressName.description}</option>
					</#list>
			</select>
	    </div>	  
	</table>
</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/shipOrder.js"></script>
</html>
