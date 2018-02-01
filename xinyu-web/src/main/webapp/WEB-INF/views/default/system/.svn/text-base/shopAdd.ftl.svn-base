<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base target="_blank" />
		<title>店铺设置</title>
	</head>
	<body>
		<table cellpadding="5">
	    		<tr>
	    			<td>商家:</td>
	    			<td><input class="easyui-textbox" type="text" name="user" id="user" disabled="disabled"></input></td>
	    		</tr>
	    		<tr>
	    			<td>店铺帐号:</td>
	    			<td><input class="easyui-textbox" type="text" name="account" id="account" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>店铺名:</td>
	    			<td><input class="easyui-textbox" type="text" name="name" id="name" data-options="required:true"></input></td>
	    		</tr>
	    		<tr>
	    			<td>店铺类型:</td>
	    			<td>
	    				<select class="easyui-combobox" name="source" id="source">
	    				<#if typeList??>
	    					<#list typeList as obj>
	    						<option value="${obj.key}">${obj.description}</option>
	    					</#list>
	    				</#if>
	    				
	    				</select>
	    			</td>
	    		</tr>

	    		<tr>
	    			<td>sessionKey:</td>
	    			<td>
	    				<input class="easyui-textbox" readonly="true" id="sessionKey">
	    				<a href="javascript:void(0);" class="easyui-linkbutton" onclick="shopList.getSessionKey();">获取</a>
					</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" name="description" id="description" data-options="multiline:true" style="height:60px"></input></td>
	    		</tr>
	    		
	    	</table>
	    	<div id="dialog_session"  style="display:none;">
	    	<!--	<iframe id="fra" style="width:100%;min-height:400px;" target="_self" src="${codeUrl}" ></iframe>-->
	    	</div>
	    	<input type="hidden" id="id" value="${id!''}"/>
	    	<input type="hidden" id="codeUrl" value="${codeUrl!''}"/>
	</body>
	<script type="text/javascript" src="${base}/scripts/system/shopList.js"></script>
</html>