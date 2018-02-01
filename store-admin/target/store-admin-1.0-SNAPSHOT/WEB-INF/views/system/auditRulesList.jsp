<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>员工管理</title>
	<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/InitArtDialog.js" type="text/javascript"></script>
	<script src="${ctx}/static/artDialog4/plugins/iframeTools.source.js" type="text/javascript"></script>
</head>

<body>	
	<br>
	<div>
		<input type="button" class="btn btn-primary" onclick="location.href='${ctx}/sys/auditRules/edit'" value="添加规则"> 
	</div>
	<br>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>序号</th>
				<th>商家</th>
				<th>快递</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${list}" var="obj" varStatus="i">
			<tr>
				<td>${i.index+1}</td>
				<td>${obj.user.shopName}</td>
				<td>${obj.expressCompany}</td>
				<td>
					<a href="${ctx}/sys/auditRules/edit?id=${obj.id}">修改</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
