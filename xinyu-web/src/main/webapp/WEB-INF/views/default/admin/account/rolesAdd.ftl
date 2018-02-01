<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>新建角色信息</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>角色名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" id="name" data-options="required:true" ></input></td>
	    		</tr>
	    	    <tr>
	    			<td>备注:</td>	
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true" style="width:180px;height:100px;"></input></td>
	    	  </tr>	
	    </table>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/role.js"></script>
</html>
