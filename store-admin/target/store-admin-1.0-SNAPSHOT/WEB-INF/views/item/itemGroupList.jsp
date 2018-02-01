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
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:190px;">
			<option value=''>全部</option> 
	    	<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
					<c:if test="${user.id == userId}">
						selected='selected'
					</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
		<input  style="width:250px;margin-left: 5px;" id="searchText" name="searchText" type="text" class="easyui-textbox" data-options="prompt:'商品名称'"/>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="group.searchList();" >查询</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="group.add();" >新建</a>
	</div>
	
	<table id="tb_group"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<table id="tb_groupDetail"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${ctx}/static/scripts/group.js"></script>
</body>
</html>
