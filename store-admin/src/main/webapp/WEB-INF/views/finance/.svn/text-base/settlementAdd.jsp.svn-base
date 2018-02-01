<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>设置列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/settlemnt.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
				<tr>
	    			<td>编号:</td>
	    			<td><input class="easyui-textbox" type="text" name="codeNumber" id="codeNumber"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商家店铺名:</td>
	    			<td>
	    				<select id="user_name" name="user_name" class="easyui-combobox">
	    					<c:forEach items="${userList}" var="user">
	    						<c:if test="${user.shopName!=null and user.shopName!=''}">
	    							<option value="${user.shopName}">${user.shopName}</option>
	    						</c:if>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>日期:</td>
	    			<td><input class="easyui-datetimebox" type="text" name="dateTime" id="dateTime" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>科目名称:</td>
	    			<td>
	    				<select id="subject" name="subject" class="easyui-combobox">
	    					<option value="应付账款">应付账款</option>
	    					<option value="应收账款">应收账款</option>	
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>备注:</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true" style="height:100px;"></input></td>
	    		</tr>
	    		<tr>
	    			<td>借方:</td>
	    			<td><input class="easyui-textbox" type="text" name="debit" id="debit" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>贷方:</td>
	    			<td><input class="easyui-textbox" type="text" name="credit" id="credit" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>方向:</td>
	    			<td>
	    				<select id="direction" name="direction" class="easyui-combobox">
	    					<option value="借">借</option>
	    					<option value="贷">贷</option>	
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>余额:</td>
	    			<td><input class="easyui-textbox" type="text" name="balance" id="balance" ></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id" value="${id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
