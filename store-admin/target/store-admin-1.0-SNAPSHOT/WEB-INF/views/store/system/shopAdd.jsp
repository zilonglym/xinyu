<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/><html><html>
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
	    	<input type="hidden" id="id" value="${id}"/>
	    	<input type="hidden" id="codeUrl" value="${codeUrl}"/>
	</body>
	<script type="text/javascript" src="${ctx}/static/scripts/shopList.js"></script>
</html>