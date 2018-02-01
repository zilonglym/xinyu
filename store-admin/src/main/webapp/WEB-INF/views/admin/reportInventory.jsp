<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>发货统计</title>
	<script src="${ctx}/static/scripts/inventoryDetail.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value="0">全部</option> 
			<c:forEach items="${users}" var="user">
				<c:if test="${user.shopName!=null and user.shopName!=''}">
					<option value="${user.id}">${user.shopName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input id="itemTitle" class="easyui-textbox" name="itemTitle"  data-options="prompt:'请输入商品名称'"></input>
		<a href="javascript:inventoryDetail.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'发货统计'" style="padding:2px;">
		<table id="tb_inventoryDetail">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 
	</script>
</body>
</html>
