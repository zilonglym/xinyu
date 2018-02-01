<%@ page contentType="text/html;charset=UTF-8"%>
<link href="${ctx}/static/bootstrap/2.2.2/css/bootstrap.css" type="text/css" rel="stylesheet" />
<html>
<head/>

<script>
	function forward() {
		window.setTimeout(
		function(){
		  document.loginform.submit();
		},800);
	}
	
	
</script>
 
<body onload="javascript:forward()">
	<div class="container">
	  <div class="inner">
	    <p>欢迎访问云端仓储物流配送中心, 正在进行安全检查 ...</p>
	    <form name="loginform" action="/login" method="post">
			<input type="hidden" id="username" name="username" value="${username}">
			<input type="hidden" id="password" name="password" value="${password}">
		</form>
	  </div>
	</div>

 </body>
</html>