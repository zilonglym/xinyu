<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>员工列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/person.js?v=1.00" type="text/javascript"></script>
</head>
<body>
	<table cellpadding="5">
	    		<tr>
	    			<td>员工号/用户名:</td>
	    			<td><input class="easyui-textbox" type="text" name="idCard" id="idCard" data-options="required:true" value="${person.idCard}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>密码:</td>
	    			<td><input class="easyui-textbox" type="password" name="password" id="password" data-options="required:true" value="${person.name}"></input></td>
	    		</tr>
	    		
	    		<tr>
	    			<td>姓名:</td>
	    			<td><input class="easyui-textbox" type="text" name="personName" id="personName" data-options="required:true" value="${person.name}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>年龄:</td>
	    			<td><input class="easyui-textbox" type="text" name="age" id="age" data-options="required:true" value="${person.age}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>性别:</td>
	    			<td>
	    				<select id="sex" class="easyui-combobox" name="sex" style="width:220px;">
	    					<c:if test="${person.sex=='female'}"><option value="${person.sex}">女</option></c:if>
	    					<c:if test="${person.sex=='male'}"><option value="${person.sex}">男</option></c:if>
	    					</option>
   							 <option value="female">女</option>   
    						 <option value="male">男</option>   
						</select>  
	    			</td>
	    		</tr>
	    		<tr>
	    			<td>联系电话:</td>
	    			<td><input class="easyui-textbox" type="text" name="phone" id="phone" data-options="required:true" value="${person.phone}"></input></td>
	    		</tr>
	    		<tr>
	    			<td>所属仓库:</td>
	    			<td>
	    				<select id="centro" class="easyui-combobox" name="centro" style="width:220px;">   
	    					<option value="${person.centroId}">${person.centroId}</option>
   							  <c:forEach items="${centroList}" var="centro">
   							 	<option value="${centro.id}">${centro.name}</option> 
   							 </c:forEach>   
						</select> 
	    			</td>
	    		</tr>
	    	</table>
	    	 <input type="hidden" id="id" value="${person.id}"/>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
