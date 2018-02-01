<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>批量生和待审核交易订单</title>
</head>
<body>

	<legend></legend>
	
	<form action="${ctx}/trade/auditRuleswait" class="form-inline">
		<select id="selectUser" name="userId">
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option> 
			</c:forEach>
		</select>
			<select id="selectExpressCompany" name="expressCompany">
			<c:forEach items="${expressCompanys}" var="expressCompany">
				<option value='${expressCompany.key}'  
				<c:if test="${expressCompany.key == cpCode}">
					selected='selected'
				</c:if>
				>${expressCompany.value}</option>
			</c:forEach>
		</select>
	   	<button type="submit" class="btn btn-primary">批量审核</button>
	</form>
	${msg}
</body>
</html>
