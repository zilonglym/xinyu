<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>退货入库单确认</title>
	<script src="${ctx}/static/scripts/returnOrder.js?v=2.0.4" type="text/javascript"></script>
</head>

<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value=''>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
		<select id="status" name="status" class="easyui-combobox">
			<option value='ENTRY_WAIT_STORAGE_RECEIVED'>待确认</option> 
			<option value='FULFILLED'>已确认</option> 
		</select>
		<input  style="width:350px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'请输入关键信息'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="returnOrder.search();" >查询</a>
	</div>
	
	<table id="tb_returnOrder">
		<thead><tr>
		</tr></thead>
	</table>
	<script>
	var ctx="${ctx}"; 	
	</script>
</body>
</html>
