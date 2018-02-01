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
	<script src="${ctx}/static/scripts/express.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>商家店铺名:</td>
	    			<td>
	    				<select id="shop_name" name="shop_name" class="easyui-combobox">
	    					<c:forEach items="${userList}" var="user">
	    						<c:if test="${user.shopName!=null and user.shopName!=''}">
	    							<option value="${user.id}">${user.shopName}</option>
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
	    						<option value="${express.id}">${express.description}</option>
	    					</c:forEach>
	    				</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>首重（KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="firstCost" id="firstCost" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>首重价格（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="firstPrice" id="firstPrice"  ></input></td>
	    		</tr>
	    			<tr>
	    			<td>续重（KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="initialCost" id="initialCost" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>续重价格（元/KG）:</td>
	    			<td><input class="easyui-textbox" type="text" name="initialPrice" id="initialPrice" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>仓储费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="deliveryCost" id="deliveryCost" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>打包费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="otherCost" id="otherCost" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>派送费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="deliveryPrice" id="deliveryPrice" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>其他费用（元）:</td>
	    			<td><input class="easyui-textbox" type="text" name="otherPrice" id="otherPrice" ></input></td>
	    		</tr>
	    		<tr>
	    			<td>区域:</td>
	    			<td><input class="easyui-textbox" type="text" name="area" id="area" data-options="multiline:true"  style="width:220px;height:80px;" ></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id" value="${id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
