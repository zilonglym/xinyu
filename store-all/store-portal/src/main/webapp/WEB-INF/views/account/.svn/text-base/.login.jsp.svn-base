<html><head>
	<title>登录页</title>
	<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
	<link href="${ctx}/static/styles/default.css" type="text/css" rel="stylesheet"/>	
	<script>
		$(document).ready(function() {
			$(&quot;#loginForm&quot;).validate();
		});
	</script>
</head><body>
	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal">
	&lt;%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%&gt;
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
	&lt;%
	}
	%&gt;
		<div class="control-group">
			<label for="username" class="control-label">名称:</label>
			<div class="controls">
				                                                                                                                         
			</div>
		</div>
		<div class="control-group">
			<label for="password" class="control-label">密码:</label>
			<div class="controls">
				                                                                                                        
			</div>
		</div>
				
		<div class="control-group">
			<div class="controls">
				<label class="checkbox" for="rememberMe"><input type="checkbox" id="rememberMe" name="rememberMe"/> 记住我</label>
				<input id="submit_btn" class="btn btn-primary" type="submit" value="登录"/> <a class="btn" href="${ctx}/register">注册</a>
			 	<span class="help-block">(管理员: <b>admin/admin</b>, 普通用户: <b>user/user</b>)</span>
			</div>
		</div>
	</form>
</body></html>