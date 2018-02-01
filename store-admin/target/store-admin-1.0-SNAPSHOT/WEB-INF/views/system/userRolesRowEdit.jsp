<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>
		
		添加员工权限信息
		
	</title>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/InitArtDialog.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/plugins/iframeTools.source.js" type="text/javascript"></script>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
</head>

<body>	
	<form id="frm" name="frm" method="post" action="${ctx}/sys/sysUserRolesRowSave">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td>商户:</td>
			<td>
					<select id="person" name="person">
						<c:forEach items="${userList}" var="user">
							<option value="${user.id}">${user.username}</option>
						</c:forEach>
					</select>				
			</td>
		</tr>
		
		<tr>
			<td>权限:</td>
			<td>
					<select id="storeMode" name="storeMode">
						<c:forEach items="${storeModeList}" var="storeMode">
							<option value="${storeMode.key}">${storeMode.title}</option>
						</c:forEach>
					</select>				
			</td>
		</tr>
		
		<tr>
			<td>操作人员:</td>
			<td>
					<select id="user" name="user">
						<c:forEach items="${userList}" var="user">
							<option value="${user.id}">${user.username}</option>
						</c:forEach>
					</select>				
			</td>
		</tr>
		
		<tr>
			<td>备注:</td>
			<td><input type="text" name="remark" value=""/>
			</td>
		</tr>
		<tr>
			<td  colspan="2" align="left">
				<input type="submit" value="保存" onclick=""/>
				<input type="button" value="取消" onclick="location.href='${ctx}/sys/sysUserRolesRowSave'"/>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>
