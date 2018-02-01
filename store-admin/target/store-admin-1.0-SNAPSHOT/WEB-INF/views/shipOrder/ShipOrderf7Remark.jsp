<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>订单备注</title>
	
	<script src="${ctx}/static/scripts/trade.js" type="text/javascript"></script>
</head>
<body>
	<table width="100%" border="0">
		&nbsp;
		<tr>
			<td>&nbsp;&nbsp;昵称：</td>
			<td><input type="text" id="buyerNick" name="buyerNick" class="easyui-textbox" value="${shipOrder.buyerNick}" disabled/></td></td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;快递：</td>
			<td><input type="text" id="orderCode" name="orderCode" class="easyui-textbox" value="${shipOrder.expressCompany}" disabled/></td></td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;单号：</td>
			<td><input type="text" id="orderNo" name="orderNo" class="easyui-textbox" value="${shipOrder.expressOrderno}" disabled/></td></td>
		</tr>
		<tr>
			<td>&nbsp;&nbsp;备注:</td>
			<td><input type="text" id="remark" name="remark" class="easyui-textbox" data-options="multiline:true" style="height:70px" value="${shipOrder.remark}"/></td>
		</tr>
	</table>
	
	<input type="hidden" id="orderId" name="orderId" value="${shipOrder.id}" />
	<script>
		var ctx="${ctx}";
	</script>
</body>
</html>
