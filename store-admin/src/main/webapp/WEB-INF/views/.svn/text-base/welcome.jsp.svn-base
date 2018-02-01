<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>后台首页</title>
	<script type="text/javascript">
	var ctx="${ctx}";
	</script>
</head>

<body>	
	<h1><i>欢迎使用中仓物流通系统</i></h1>
	&nbsp;
	<c:forEach items="${resultList}" var="obj">
		<h4>注意！<span style="color:red;">${obj.userName}</span>有未审核订单<span style="color:red;">${obj.num}</span>单，由此<span><a href="${ctx}/trade/waits/search?userId=${obj.userId}">进入操作</a></span>。</h4>
	</c:forEach>
</body>
</html>
