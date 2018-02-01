<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>
		查看员工信息
	</title>
	
</head>

<body>
	<br>	
	<table id="contentTable" class="table table-striped table-condensed" >
		<tr>
			<td>员工号:</td><td><c:if test="${person!=null}">${person.idCard}</c:if></td>
		</tr>
		<tr>
			<td>员工姓名:</td><td><c:if test="${person!=null}">${person.name}</c:if></td>
		</tr>
		<tr>
			<td>性别:</td>
			<td>	
				<c:if test="${person.sex=='male'}">男</c:if>
				<c:if test="${person.sex=='female'}">女</c:if>
			</td>
		</tr>
		<tr>
			<td>年龄:</td><td><c:if test="${person!=null}">${person.age}</c:if>
		</tr>
		<tr>
			<td>用户名:</td><td><c:if test="${person!=null}">${person.userName}</c:if></td>
		</tr>
		<tr>
			<td>联系电话:</td><td><c:if test="${person!=null}">${person.phone}</c:if></td>
		</tr>
		<tr>
			<td>更新日期:</td><td><c:if test="${person!=null}"><fmt:formatDate value="${person.inputDate}" type="date" pattern="yyyy-MM-dd"/></c:if></td>
		</tr>
		<tr>
			<td>所在仓库:</td>
			<td>
				${person.centro.name}		
			</td>
		</tr>
		<tr>
			<td  colspan="2" >
				<input type="button" value="返回" onclick="location.href='${ctx}/person/personList'"/>
			</td>
		</tr>
	</table>
</body>
</html>
