<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	
</head>

<body>	
	<form id="frm" name="frm" method="post" action="${ctx}/sys/createAccount/${centro.id}">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td>用户名:</td><td><input type="text" name="userName" id="userName"/></td>
		</tr>
		<tr>
			<td>密码:</td><td><input  type="password"  name="password" id="password" /></td>
		</tr>
		<tr>
			<td>重复密码:</td><td><input  type="password"  name="password1" id="password1" /></td>
		</tr>
		<tr>
			<td  colspan="2" align="left">
				<input type="button" value="保存" onclick="submit();"/>
				<input type="button" value="重置"/>
			</td>
		</tr>
	</table>
	</form>
	<script>
		function submit(){
			var userName=$("#userName").val();
			var password=$("#password").val();
			var password1=$("#password1").val();
			var index=0;
			if(userName==''){
				index=1;
				alert("用户名不能为空!");
			}
			if(password=='' || password1==''){
				index=1;
				alert("密码不能为空!");
			}
			if(password!=password1){
				index=1;
				alert("两次输入密码不一致");
			}
			if(index==0){
				document.getElementById("frm").submit();
			}
		}
	</script>
</body>
</html>
