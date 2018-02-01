<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<base target="_blank" />
	<title>编辑商家信息</title>
</head>
<body>
	<table cellpadding="5">
	    	<tr>
	    			<td>货主ID:</td>
	    			<td><input class="easyui-textbox" type="text" name="tbUserId" id="tbUserId" value="${user.tbUserId}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>仓库编码:</td>
	    			<td><input class="easyui-textbox" type="text" name="serviceCode" id="serviceCode" value="${user.serviceCode}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户名字:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberName" id="subscriberName" value="${user.subscriberName}" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户昵称:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberNick" id="subscriberNick" value="${user.subscriberNick}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户手机:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberMobile" id="subscriberMobile" value="${user.subscriberMobile}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户电话:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberPhone" id="subscriberPhone" value="${user.subscriberPhone}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户邮箱:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberContactEmail" id="subscriberContactEmail" value="${user.subscriberContactEmail}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>用户地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="subscriberAddress" id="subscriberAddress" value="${user.subscriberAddress}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>仓储参数:</td>
	    			<td><input class="easyui-textbox" type="text" name="content" id="content" value="${user.content}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>备注信息:</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" value="${user.remark}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>奇门code:</td>
	    			<td><input class="easyui-textbox" type="text" name="ownerCode" id="ownerCode" value="${user.ownerCode}"></input></td>
	    		</tr>
	    	</table>
	    	 <input type="hidden" id="id" value="${user.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/person.js"></script>
</html>
