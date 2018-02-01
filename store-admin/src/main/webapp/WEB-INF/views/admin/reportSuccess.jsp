<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>导出成功！</title>
	
</head>
<body>
	<br /><br /><br /><br /><br /><br />
	<h1>文件成功到桌面上，点击<a href="javascript:void(0);" onclick="window.close();">关闭</a>页面！</h1>
	<script>
	var ctx="${ctx}"; 
	</script>
</body>
</html>
