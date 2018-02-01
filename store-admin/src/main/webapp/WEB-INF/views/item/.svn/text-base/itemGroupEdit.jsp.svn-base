<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>编辑</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
</head>
<body>
	<div id="order">
		<table cellpadding="5">
				<tr>
	    			<td>商家:</td>
	    			<td>
	    				<select id="user" class="easyui-combobox" name="user" style="width:170px;">  
	    					<option value="">请选择商家</option>
	    					<c:forEach items="${users}" var="user">
								<option value='${user.id}'  
								<c:if test="${user.id == userId}">
									selected='selected'
								</c:if>
								>${user.shopName}</option>
							</c:forEach>
						</select>  
	    			</td>
	    			<td>商品名称:</td>
	    			<td><input class="easyui-textbox" type="text" id="itemName" name="itemName" value="${itemGroup.name}"/></td>
	    		</tr>
	    		<tr>
	    			<td>条形码:</td>
	    			<td><input class="easyui-textbox" type="text" id="barCode" name="barCode" value="${itemGroup.barCode}"/></td>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" type="text" id="remark" name="remark" value="${itemGroup.remark}"/></td>
	    		</tr>
	    		<tr>
	    			<td>是否启用:</td>
	    			<td>
	    				<select id="state" class="easyui-combobox" name="state" style="width:170px;">  
	    					<option value="true" selected>启用</option>
	    					<option value="false">禁用</option>
						</select>  
	    			</td>
	    			<td>优先级:</td>
	    			<td>
	    				<select id="priority" class="easyui-combobox" name="priority" style="width:170px;">  
	    					<option value="1" selected>1</option>
	    					<option value="2">2</option>
	    					<option value="3">3</option>
	    					<option value="4">4</option>
	    					<option value="5">5</option>
	    					<option value="6">6</option>
						</select>  
	    			</td>
	    		</tr>
	   	 	</table>
	   	 	<input type="hidden" id="id" value="${itemGroup.id}"/>
		</div>
		<div id="items">
			<div style="height:auto;padding:5px;">
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="batchAppend();">添加商品</a>
			</div>
			<table cellpadding="5" id="tb_itemDetail">
				<thread>
					
				</thread>
			</table>
			<div id="item"></div>
		</div>		    
	</body>
	<script type="text/javascript" src="${ctx}/static/scripts/group.js"></script>
</html>
