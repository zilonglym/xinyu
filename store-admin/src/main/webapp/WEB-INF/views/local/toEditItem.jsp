<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>修改商品</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/localItem.js" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>商品名称:</td>
	    			<td><input class="easyui-textbox" type="text" name="itemName" id="itemName" data-options="required:true" value="${localItem.name}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>sku属性:</td>
	    			<td><input class="easyui-textbox" type="text" name="sku" id="sku" value="${localItem.sku}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商品条码:</td>
	    			<td><input class="easyui-textbox" type="text" name="barCode" id="barCode" data-options="required:true" value="${localItem.barCode}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>商家:</td>
	    			<td>
	    				<select id="shop" name="shop" class="easyui-combobox">
							<c:forEach items="${shops}" var="shop">
								<option value="${shop.id}"  
								<c:if test="${shop.id == localItem.shopId}">
									selected='selected'
								</c:if>
								>${shop.name}</option>
							</c:forEach>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>来源：</td>
	    			<td>
	    				<select id="source" name="source" class="easyui-combobox">
							<option value="qimen" <c:if test="${localItem.source == 'qimen'}">selected='selected'</c:if>>奇门</option>
							<option value="cainiao" <c:if test="${localItem.source == 'cainiao'}">selected='selected'</c:if>>菜鸟</option>
							<option value="import"  <c:if test="${localItem.source == 'import'}">selected='selected'</c:if>>导入</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>商品类型：</td>
	    			<td>
	    				<select id="itemType" name=""itemType"" class="easyui-combobox">
							<option value="ZC" <c:if test="${localItem.itemType == 'ZC'}">selected='selected'</c:if>>正常商品</option>
							<option value="ZP" <c:if test="${localItem.itemType == 'ZP'}">selected='selected'</c:if>>赠品</option>
							<option value="OTHER" <c:if test="${localItem.itemType == 'OTHER'}">selected='selected'</c:if>>其他商品</option>
						</select>
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>低位数量:</td>
	    			<td><input class="easyui-textbox" type="text" name="lowNum" id="lowNum" data-options="required:true" value="${localItem.num}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>高位数量:</td>
	    			<td><input class="easyui-textbox" type="text" name="highNum" id="highNum" data-options="required:true" value="${localItem.highNum}"></input></td>
	    		</tr>
	    </table>
	     <input type="hidden" id="id" value="${localItem.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
