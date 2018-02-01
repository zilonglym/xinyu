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
	<script src="${ctx}/static/scripts/monthProfits.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>时间:</td>
	    			<td><input class="easyui-datetimebox" type="text" name="dateTime" id="dateTime" value="${monthProfits.dateTime}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>主营业务收入:</td>
	    			<td><input id="mainIncome" name="mainIncome"  class="easyui-textbox" type="text" value="${monthProfits.mainIncome}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>主营业务成本:</td>
	    			<td><input id="mainCost" name="mainCost"  class="easyui-textbox" type="text" value="${monthProfits.mainCost}"></input></td>
	    		</tr>
	    		<tr>
	    			<td> 发个人件及接货费:</td>
	    			<td><input class="easyui-textbox" type="text" name="deliveryFee" id="deliveryFee"  value="${monthProfits.deliveryFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>废纸箱:</td>
	    			<td><input class="easyui-textbox" type="text" name="basketFee" id="basketFee" value="${monthProfits.basketFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>本月打包费:</td>
	    			<td><input class="easyui-textbox" type="text" name="packingFee" id="packingFee" value="${monthProfits.packingFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>物料,防水袋胶带:</td>
	    			<td><input class="easyui-textbox" type="text" name="materielFee" id="materielFee" value="${monthProfits.materielFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>破损,漏发等:</td>
	    			<td><input class="easyui-textbox" type="text" name="damagedFee" id="damagedFee" value="${monthProfits.damagedFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>财务费用:</td>
	    			<td><input class="easyui-textbox" type="text" name="financeFee" id="financeFee" value="${monthProfits.financeFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>房租,物业费:</td>
	    			<td><input class="easyui-textbox" type="text" name="propertyFee" id="propertyFee" value="${monthProfits.propertyFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>水电:</td>
	    			<td><input class="easyui-textbox" type="text" name="waterFee" id="waterFee" value="${monthProfits.waterFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>其他办公费用:</td>
	    			<td><input class="easyui-textbox" type="text" name="officeFee" id="officeFee" value="${monthProfits.officeFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>伙食费:</td>
	    			<td><input class="easyui-textbox" type="text" name="boardFee" id="boardFee" value="${monthProfits.boardFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>车辆费用:</td>
	    			<td><input class="easyui-textbox" type="text" name="vehicleFee" id="vehicleFee" value="${monthProfits.vehicleFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>固定、无形资产待摊:</td>
	    			<td><input class="easyui-textbox" type="text" name="assetsFee" id="assetsFee" value="${monthProfits.assetsFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>招待费:</td>
	    			<td><input class="easyui-textbox" type="text" name="hospitalityFee" id="hospitalityFee" value="${monthProfits.hospitalityFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>应交税金:</td>
	    			<td><input class="easyui-textbox" type="text" name="taxFee" id="taxFee" value="${monthProfits.taxFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>保险+工会经费:</td>
	    			<td><input class="easyui-textbox" type="text" name="insuranceFee" id="insuranceFee" value="${monthProfits.insuranceFee}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>工资:</td>
	    			<td><input class="easyui-textbox" type="text" name="wagesFee" id="wagesFee" value="${monthProfits.wagesFee}"></input></td>
	    		</tr>
	    </table>
	    <input type="hidden" id="id" value="${monthProfits.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
