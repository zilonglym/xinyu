<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>菜单信息新建</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>菜单名：</td>
	    			<td><input class="easyui-textbox" type="text" name="linkTitle" id="linkTitle" data-options="required:true" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>父菜单:</td>
	    			<td>
	    				<select id="models" class="easyui-combobox" name="models" style="width:220px;">   
	    					<#list models as model>
	    						<option value="${model.key}">${model.title}</option>
	    					</#list>
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>菜单链接:</td>
	    			<td><input class="easyui-textbox" type="text" name="link" id="link" data-options="required:true" ></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/menu.js"></script>
</html>
