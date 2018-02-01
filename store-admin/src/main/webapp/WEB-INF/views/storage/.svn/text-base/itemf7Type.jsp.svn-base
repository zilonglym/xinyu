<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品类型修改</title>
	
	<script src="${ctx}/static/scripts/items.js" type="text/javascript"></script>
</head>
<body>
	<table width="100%" border="0">
		<tr>
			<td>商品名称:</td>
			<td>
			<input type="text" id="itemTitle" name="itemTitle" class="easyui-textbox" disabled value="${item.title}"/>
			</td>
		</tr>
		<tr>
			<td>商品类型：</td>
			<td>
				<select id="itemType" name="itemType" class="easyui-combobox">
					<option value="ZC">正常商品</option>
					<option value="ZH">组合商品</option>
					<option value="ZP">赠品</option>
					<option value="FX">分销商品</option>
					<option value="OTHER">其他</option>
				</select>
			<td>
		</tr>
	</table>
	
	<input type="hidden" id="itemId" name="itemId" value="${item.id}" />
	<script>
		var ctx="${ctx}";
	</script>
</body>
</html>
