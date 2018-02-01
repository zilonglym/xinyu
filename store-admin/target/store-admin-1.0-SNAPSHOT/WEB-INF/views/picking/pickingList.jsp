<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>分拣单列表</title>
	<script src="${ctx}/static/scripts/picking.js" type="text/javascript"></script>
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
		<input  style="width:350px;" id="q" name="q" type="text"  
		class="easyui-textbox" data-options="prompt:'运单号/联系人电话/批次号/收件人/买家ID'"/>
		<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="trade.searchList();" >查询</a>
	</div>
	
	<table id="contentTable"  >
		<thead><tr>
		<th>分拣日期</th>
		<th>单据编号</th>
		<th>单据状态</th>
		<th>操作人</th>
		<th>操作</th>
		</tr></thead>
	</table>
	<div id="dialog"></div>
	<script>var ctx="${ctx}";
		$(document).ready(function(){
			picking.initTable();
	});
	</script>
</body>
</html>
