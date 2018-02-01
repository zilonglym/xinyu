<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>修改备注</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>备注:</td>
	    			<td>
	    				<#if shipOrder.remark??>
	    					<input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true;" style="width:240px;height:180px;" value="${shipOrder.remark}"></input>
	    				<#else>
	    					<input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true;" style="width:240px;height:180px;"></input>
	    				</#if>
	    			
	    			</td>
	    		</tr>
	 </table>
	 <input type="hidden" id="id" name="id" value="${shipOrder.id}"/>
</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/shipOrder.js"></script>
</html>
