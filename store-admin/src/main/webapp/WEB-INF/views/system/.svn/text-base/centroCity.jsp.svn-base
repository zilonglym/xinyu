<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>
		仓库发货范围设置
	</title>
</head>

<body>	
	<form id="frm" name="frm" method="post" action="${ctx}/sys/submitCentroCity/${centro.id}">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td>仓库编码:</td><td>${centro.code}</td>
		</tr>
		<tr>
			<td>仓库名称:</td><td>${centro.name}</td>
		</tr>
		<tr>
			<td>发货范围:</td>
			<td><textarea name="cityStr" id="cityStr"><c:if test="${centro!=null}">${centro.cityStr}</c:if></textarea></td>
		</tr>
		<tr>
			<td colspan="2" align="left" style="color:red;">发货范围请填城市名称，多个城市之间请以逗号分隔!</td>
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
			var cityStr=$("#cityStr").val();
			if(cityStr==''){
				alert("发货范围不能为空!");
				return;
			}else{
				document.getElementById("frm").submit();
			}
		}
	</script>
</body>
</html>
