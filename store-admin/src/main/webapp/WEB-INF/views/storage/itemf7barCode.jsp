<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品条码扫描</title>
	
	<script src="${ctx}/static/scripts/items.js" type="text/javascript"></script>
</head>
<body>
	<table width="100%" border="0">
		<tr>
			<td>商品名称:</td>
			<td><input type="text" readonly="readonly" class="easyui-textbox" value="${item.title}"/></td>
		</tr>
		<tr>
			<td>条码:</td>
			<td><input type="text" id="barCode" name="barCode" value=""/></td>
		</tr>
	</table>
	
	<input type="hidden" id="itemId" name="itemId" value="${item.id}" />
	<script>
		var ctx="${ctx}";
		  //$(document).ready(function(){  $("input[name=barCode]")[0].focus();});
		  document.getElementById("barCode").focus();
	</script>
</body>
</html>
