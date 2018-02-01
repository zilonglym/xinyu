<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>��ㄦ�风�＄��</title>
	
	<script>
		$(document).ready(function() {
			//������绗�涓�涓�杈���ユ��
			$("#name").focus();
			//涓�inputForm娉ㄥ��validate��芥��
			$("#inputForm").validate();
		});
	</script>
</head>

<body>
	<form id="inputForm" action="${ctx}/admin/user/update" method="post" class="form-horizontal">
		<input type="hidden" name="CSRFToken" value="${csrf}" />
		<input type="hidden" name="id" value="${user.id}"/>
		<fieldset>
			<legend><small>��ㄦ�风�＄��</small></legend>
			<div class="control-group">
				<label class="control-label">��诲�����:</label>
				<div class="controls">
					<input type="text" value="${user.loginName}" class="input-large" disabled="" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">��ㄦ�峰��:</label>
				<div class="controls">
					<input type="text" id="name" name="name" value="${user.name}" class="input-large required"/>
				</div>
			</div>
			<div class="control-group">
				<label for="plainPassword" class="control-label">瀵����:</label>
				<div class="controls">
					<input type="password" id="plainPassword" name="plainPassword" class="input-large" placeholder="...Leave it blank if no change"/>
				</div>
			</div>
			<div class="control-group">
				<label for="confirmPassword" class="control-label">纭�璁ゅ�����:</label>
				<div class="controls">
					<input type="password" id="confirmPassword" name="confirmPassword" class="input-large" equalTo="#plainPassword" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">娉ㄥ����ユ��:</label>
				<div class="controls">
					<span class="help-inline" style="padding:5px 0px"><fmt:formatDate value="${user.registerDate}" pattern="yyyy骞�MM���dd���  HH���mm���ss绉�" /></span>
				</div>
			</div>
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="���浜�"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="杩����" onclick="history.back()"/>
			</div>
		</fieldset>
	</form>
</body>
</html>
