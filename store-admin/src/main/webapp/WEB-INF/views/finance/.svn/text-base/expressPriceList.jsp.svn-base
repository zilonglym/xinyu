<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>快递列表</title>
	<script src="${ctx}/static/scripts/express.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="userName" class="easyui-combobox" name="userName" style="width:200px;">
				<option value="">全部</option>
				<c:forEach items="${userList}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.id}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<select id="expressName" class="easyui-combobox" name="expressName">
				<option value="">全部</option>
				<c:forEach items="${expressList}" var="express">
					<option value="${express.id}">${express.description}</option>
				</c:forEach>	
			</select>
			<input id="expressArea" class="easyui-textbox" name="expressArea"></input>
			<a href="javascript:express.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:express.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
	</div>
	<div data-options="title:'快递列表'" style="padding:2px;">
		<table id="tb_express"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
</body>
</html>
