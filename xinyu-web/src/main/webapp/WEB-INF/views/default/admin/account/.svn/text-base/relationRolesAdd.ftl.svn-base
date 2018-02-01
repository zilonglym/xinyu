<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>账号角色设置</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>账号：</td>
	    			<td>${account.userName}</td>
	    		</tr>
	    		<tr>
	    			<td>角色:</td>
	    			<td>
	    				<select id="accountRole" class="easyui-combobox" name="accountRole" style="width:220px;">   
	    					<#list roles as role>
	    						<option value="${role.id}">${role.name}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    </table>
	     <input type="hidden" id="id" value="${account.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/person.js"></script>
</html>
