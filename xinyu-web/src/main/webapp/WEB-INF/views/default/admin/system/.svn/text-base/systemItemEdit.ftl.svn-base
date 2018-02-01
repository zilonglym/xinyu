<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>系统参数修改</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>系统参数名：</td>
	    			<td><input class="easyui-textbox" type="text" name="description" id="description" data-options="required:true" value="${systemItem.description}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>系统参数类型::</td>
	    			<td>
	    				<select id="type" class="easyui-combobox" name="type" style="width:220px;">   
	    					<#list types as type>
	    						<option value="${type.key}" <#if systemItem.type.key==type.key>selected</#if>>${type.value}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>系统参数值:</td>
	    			<td><input class="easyui-textbox" type="text" name="value" id="value" data-options="required:true" value="${systemItem.value}"></input></td>
	    		</tr>
	    	</table>
	    	 <input type="hidden" id="id" value="${systemItem.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/systemItem.js"></script>
</html>
