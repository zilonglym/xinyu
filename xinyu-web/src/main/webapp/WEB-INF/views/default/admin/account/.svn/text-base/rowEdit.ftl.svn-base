<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>修改账号权限</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>角色:</td>
	    			<td>
	    				<select id="accountRole" class="easyui-combobox" name="accountRole" style="width:220px;">
	    					<#list roles as role>
	    						<option value="${role.id}" <#if row.roles.id==role.id>selected</#if>>${role.name}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>角色:</td>
	    			<td>
	    				<select id="model" class="easyui-combobox" name="model" style="width:220px;">   
	    					<#list models as model>
	    						<option value="${model.key}" <#if row.menu.menus.key==model.key>selected</#if>>${model.title}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true;" style="width:230px;height:150px;" value="${row.remark}"/></td>
	    		</tr>
	    </table>
	     <input type="hidden" id="id" value="${row.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/rolesRow.js"></script>
</html>
