<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#item_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/profile/${action}" method="post" class="form-horizontal">
		<fieldset>
			<legend><small>信息编辑</small></legend>
			<div class="control-group">
				<label for="item_title" class="control-label">电话:</label>
				<div class="controls">
					<input type="text" name="phone"  value="${profile.phone}" class="input-large required" minlength="8"/>
					<p class="help-block">联系电话</p>
				</div>
			</div>	
		</fieldset>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="保存"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="取消" onclick="history.back()"/>
			</div>
	</form>
</body>
</html>
