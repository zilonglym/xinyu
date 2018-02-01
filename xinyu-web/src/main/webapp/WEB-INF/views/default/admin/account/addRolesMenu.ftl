<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>角色权限设置</title>
</head>
<body>
	<table cellpadding="5">
				<tr>
	    			<td>角色名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" id="name" data-options="required:true;" value="${roles.name}" ></input></td>
	    		</tr>
				<tr>
					<td>权限设置:</td>
					<td>
	    				<#list menus as menu>
	    					<input type="checkbox" id="menu" name="menu" value="${menu.id}"/>${menu.title}
	    					<#if (menu.index+1)%2==0>
	    					<br/>
	    					</#if>
	    				</#list>
	   				</td>	 
	    		</tr>		
	    		<tr>
	    			<td>备注:</td>	
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true" style="width:180px;height:100px;"></input></td>
	    	  </tr>
	    		
	    </table>
	     <input type="hidden" id="id" value="${roles.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/roles.js"></script>
</html>
