<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品添加库存</title>
	
	<script src="${ctx}/static/scripts/items.js" type="text/javascript"></script>
</head>
<body>
	<table width="100%" border="0">
		<tr>
			<td>商品添加库存数量:</td>
			<td><input type="text" id="itemCount" name="itemCount" class="easyui-textbox" value=""/></td>
		</tr>
	</table>
	
	<input type="hidden" id="itemId" name="itemId" value="${item.id}" />
	<script>
		var ctx="${ctx}";
	</script>
</body>
</html>
