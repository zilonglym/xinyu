<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>运费设置列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/companyProfits.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
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
	    			<td>快递名:</td>
	    			<td>
	    				<select id="express_name" name="express_name" class="easyui-combobox">
	    					<c:forEach items="${expressList}" var="express">
	    						<option value="${express.description}">${express.description}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>日期:</td>
	    			<td><input class="easyui-datetimebox" type="text" name="dateTime" id="dateTime" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>应收快递费:</td>
	    			<td><input class="easyui-textbox" type="text" name="expressIncome" id="expressIncome"  ></input></td>
	    		</tr>
	    		<tr>
	    			<td>应收面单费:</td>
	    			<td><input class="easyui-textbox" type="text" name="planeIncome" id="planeIncome" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>应付快递费:</td>
	    			<td><input class="easyui-textbox" type="text" name="expressExpend" id="expressExpend" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>应付面单费:</td>
	    			<td><input class="easyui-textbox" type="text" name="planeExpend" id="planeExpend" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>发件费:</td>
	    			<td><input class="easyui-textbox" type="text" name="sendFee" id="sendFee" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>包仓费:</td>
	    			<td><input class="easyui-textbox" type="text" name="warehouseFee" id="warehouseFee" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>数量:</td>
	    			<td><input class="easyui-textbox" type="text" name="num" id="num" ></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id" value="${id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
