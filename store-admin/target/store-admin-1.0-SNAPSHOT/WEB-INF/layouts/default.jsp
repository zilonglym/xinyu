<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>物流通后台:<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link type="image/x-icon" href="${ctx}/static/images/favicon.ico" rel="shortcut icon">
<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${ctx}/static/bootstrap/2.2.2/js/bootstrap.min.js" type="text/javascript"></script>
<sitemesh:head />

</head>

<body class="easyui-layout">
		<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA; margin-top: -13px;">
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		</div>
		<div data-options="region:'center',title:''">
			<sitemesh:body />
		</div>
	</body>
</html>