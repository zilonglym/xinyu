<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>注册账号</title>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="userName" id="userName" value="${account.userName}" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-textbox" type="password" name="password" id="password" value="${account.password}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>简称:</td>
	    			<td><input class="easyui-textbox" type="text" name="shortName" id="shortName" value="${account.shortName}" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>手机:</td>
	    			<td><input class="easyui-textbox" type="text" name="mobile" id="mobile" value="${account.mobile}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>座机:</td>
	    			<td><input class="easyui-textbox" type="text" name="phone" id="phone" value="${account.phone}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>Email:</td>
	    			<td><input class="easyui-textbox" type="text" name="email" id="email" value="${account.email}"></input></td>
	    		</tr>
	    </table>
	     <input type="hidden" id="id" value="${account.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/account.js"></script>
</html>
