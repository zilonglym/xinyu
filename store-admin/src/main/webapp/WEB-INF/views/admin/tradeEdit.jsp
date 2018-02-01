<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>订单修改</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/tradeWaits.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="10">
	    		<tr>
	    			<td>店铺名:</td>
	    			<td><input class="easyui-textbox" type="text" name="userName" id="userName"  disabled value="${trade.user.shopName}"></input></td>
	    			<td>
	    				<input type="hidden" name="userId" id="userId"  disabled value="${trade.user.id}"/>
	    				<input type="hidden" id="id" name="id" value="${trade.id}"/>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>收件人姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="receiverName" id="receiverName" data-options="required:true" value="${trade.receiverName}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>联系手机:</td>
	    			<td><input class="easyui-textbox" type="text" name="receiverMobile" id="receiverMobile" data-options="required:true" value="${trade.receiverMobile}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input class="easyui-textbox" type="text" name="receiverPhone" id="receiverPhone" data-options="required:true" value="${trade.receiverPhone}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>收件人地址:</td>
	    			<td><input class="easyui-textbox" type="text" name="receiverState" id="receiverState" data-options="required:true" value="${trade.receiverState}"></input></td>
	    		</tr>
	    		<tr>
	    			<td></td>    			
	    			<td><input class="easyui-textbox" type="text" name="receiverCity" id="receiverCity" data-options="required:true" value="${trade.receiverCity}"></input></td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    			<td><input class="easyui-textbox" type="text" name="receiverDistrict" id="receiverDistrict" data-options="required:true" value="${trade.receiverDistrict}"></input></td>
	    		</tr>
	    		<tr>
	    			<td></td>
	    			<td><input class="easyui-textbox" type="text" name="receiverAddress" id="receiverAddress" data-options="multiline:true,required:true" style="height:50px;" value="${trade.receiverAddress}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>备注留言:</td>
	    			<td><input class="easyui-textbox" type="text" name="remark" id="remark" data-options="multiline:true,required:true" style="height:50px;" value="${trade.sellerMemo}${trade.buyerMessage}${trade.buyerMemo}"></input></td>
	    		</tr>
	    	</table>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
