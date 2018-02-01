<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title>订单列表</title>
		<script src="${ctx}/static/scripts/workReport.js" type="text/javascript"></script>
	</head>
	<body>
		<div style="margin-top: 5px;margin-bottom: 5px;">
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" >刷新数据</a>
		<table id="contentTable"  >
			<thead>
				<tr>
				</tr>
			</thead>
		</table>
		<script>
			var ctx="${ctx}";
			$(document).ready(function() {
				work.initTable();
			});

		</script>
</html>
