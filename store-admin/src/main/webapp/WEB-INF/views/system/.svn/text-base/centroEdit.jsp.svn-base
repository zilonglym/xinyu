<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>
		<c:if test="${empty centro}">
		添加仓库</c:if>
		<c:if test="${!empty centro}">
		修改仓库
		</c:if>
	</title>
</head>

<body>	
	<form id="frm" name="frm" method="post" action="${ctx}/sys/submitCentro">
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<tr>
			<td>仓库编码:</td><td><input type="text" name="code" id="code" value="<c:if test="${centro!=null}">${centro.code}</c:if>"/></td>
		</tr>
		<tr>
			<td>仓库名称:</td><td><input  type="text"  name="name" id="name" value="<c:if test="${centro!=null}">${centro.name}</c:if>"/></td>
		</tr>
		<tr>
			<td>负责人:</td><td><input  type="text"  name="person" id="person" value="<c:if test="${centro!=null}">${centro.person}</c:if>"/></td>
		</tr>
		<tr>
			<td>联系电话:</td><td><input  type="text"  name="phone" id="phone" value="<c:if test="${centro!=null}">${centro.phone}</c:if>"/></td>
		</tr>
		<tr>
			<td>仓库地址:</td><td><textarea name="address" id="address"><c:if test="${centro!=null}">${centro.address}</c:if></textarea></td>
		</tr>
		<tr>
			<td  colspan="2" align="left">
				<input type="button" value="保存" onclick="submit();"/>
				<input type="button" value="重置"/>
			</td>
		</tr>
		<input type="hidden" id="id" name="id" value="<c:if test="${centro!=null}">${centro.id}</c:if>"/>
	</table>
	</form>
	<script>
		function submit(){
			var code=$("#code").val();
			var name=$("#name").val();
			var person=$("#person").val();
			var phone=$("#phone").val();
			var address=$("#address").val();
			if(code==''){
				alert("仓库编码不能为空!");
				return;
			}
			if(name==''){
				alert("仓库名称不能为空!");
				return;
			}
			if(person==''){
				alert("仓库负责人不能为空!");
				return;
			}
			if(phone==''){
				alert("联系电话不能为空!");
				return ;
			}
			if(address==''){
				alert("仓库地址不能为空!");
				return;
			}
			document.getElementById("frm").submit();
		}
	</script>
</body>
</html>
