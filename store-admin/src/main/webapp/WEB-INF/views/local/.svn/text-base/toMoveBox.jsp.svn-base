<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title>库位调整</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
		<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	</head>
	<body style="text-align:center;">
		<div>
			<div style="width:305px;height:230px;margin-top:5px;margin-left:5px;float:left;border:1px double #E0E0E0;">
				<table cellpadding="5">
					<tr>
						<td>货位信息：</td>
						<td><input class="easyui-textbox" type="text" name="boxOut" id="boxOut" value="${localPlate.boxOut}" disabled/></td>
					</tr>
					<tr>
						<td>商家：</td>
						<td><input class="easyui-textbox" type="text" name="shopName" id="shopName" value="${localPlate.shopName}" disabled/></td>
					</tr>
					<tr>
						<td>商品名称：</td>
						<td><input class="easyui-textbox" type="text" name="itemName" id="itemName" value="${localPlate.title}" disabled/></td>
					</tr>
					<tr>
						<td>商品属性：</td>
						<td><input class="easyui-textbox" type="text" name="sku" id="sku" value="${localPlate.sku}" disabled/></td>
					</tr>
					<tr>
						<td>商品条码：</td>
						<td><input class="easyui-textbox" type="text" name="barCode" id="barCode" value="${localPlate.barCode}" disabled/></td>
					</tr>
					<tr>
						<td>商品数量：</td>
						<td><input class="easyui-textbox" type="text" name="num" id="num" value="${localPlate.num}" disabled/></td>
					</tr>
					<tr>
						<td>更新时间：</td>
						<td><input class="easyui-textbox" type="text" name="lastUpdate" id="lastUpdate" value="${localPlate.lastUpdate}" disabled/></td>
					</tr>
				</table>
				<input type="hidden" id="id" value="${localPlate.id}"/>
			</div>
			<div style="float:left;padding:90px 0 0 20px;" >
				<img src="${ctx}/static/images/right.png"  width="100" height="20" />
			</div>
			<div style="width:305px;height:230px;margin-top:5px;margin-right:5px;float:right;border:1px double #E0E0E0;">
				<table cellpadding="5">
					<tr>
						<td>货位信息：</td>
						<td>
							<select id="bid" class="easyui-combobox" name="bid">   
   							 	<c:forEach items="${boxOuts}" var="boxOut">
   							 		<option value="${boxOut.id}">${boxOut.boxout}</option> 
   							 	</c:forEach>
							</select> 
						</td>
					</tr>
					<tr>
						<td>商家：</td>
						<td><input class="easyui-textbox" type="text" name="bShopName" id="bShopName" value="" disabled/></td>
					</tr>
					<tr>
						<td>商品名称：</td>
						<td><input class="easyui-textbox" type="text" name="bItemName" id="bItemName" value="" disabled/></td>
					</tr>
					<tr>
						<td>商品属性：</td>
						<td><input class="easyui-textbox" type="text" name="bSku" id="bSku" value="" disabled/></td>
					</tr>
					<tr>
						<td>商品条码：</td>
						<td><input class="easyui-textbox" type="text" name="bBarCode" id="bBarCode" value="" disabled/></td>
					</tr>
					<tr>
						<td>商品数量：</td>
						<td><input class="easyui-textbox" type="text" name="bnum" id="bnum" value="" disabled/></td>
					</tr>
					<tr>
						<td>更新时间：</td>
						<td><input class="easyui-textbox" type="text" name="bLastUpdate" id="bLastUpdate" value="" disabled/></td>
					</tr>
				</table>
			</div>
		</div>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
