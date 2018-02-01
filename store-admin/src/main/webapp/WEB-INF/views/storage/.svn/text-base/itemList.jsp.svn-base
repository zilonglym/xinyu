<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品列表</title>
	<script src="${ctx}/static/scripts/items.js" type="text/javascript"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value='0'>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		<input  style="width:350px;" id="searchText" name="searchText" type="text"  
		class="easyui-textbox" data-options="prompt:'商品名称'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="items.searchList();" >查询</a>
	</div>
	
	<table id="contentTable"  >
		<thead><tr>
		<th>商品名称</th>
		<th>商品编码</th>
		<th>商品SKU</th>
		<th>商品条码</th>
		<th>商品净重量</th>
		<th>商品打包重量</th>
		<th>所属商家</th>
		<th>操作</th>
		</tr></thead>
	</table>
	<div id="dialog"></div>
	<div id="dialog1"></div>
	<script>var ctx="${ctx}";
		$(document).ready(function(){
			items.initTable();
	});
	</script>
</body>
</html>
