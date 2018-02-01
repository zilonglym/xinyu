<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>仓库管理</title>
</head>

<body>	
	<div>
		<a href="${ctx}/sys/toEditCenter">添加仓库</a>
	</div>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>编码</th>
				<th>仓库名</th>
				<th>负责人</th>
				<th>电话</th>
				<th>地址</th>
				<th>管理员帐号</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${centroList}" var="obj">
			<tr>
				<td>${obj.code}</td>
				<td>${obj.name}</td>
				<td>${obj.person}</td>
				<td>${obj.phone}</td>
				<td>${obj.address}</td>
				<td>${obj.account}</td>
				<td>
					<a href="${ctx}/sys/toEditCenter?id=${obj.id}">修改</a>
					<a href="${ctx}/sys/toCity/${obj.id}">设置发货范围</a>
					<c:if test="${empty obj.account}"><a href="${ctx}/sys/toCreateUser/${obj.id}">生成管理帐号</a></c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
