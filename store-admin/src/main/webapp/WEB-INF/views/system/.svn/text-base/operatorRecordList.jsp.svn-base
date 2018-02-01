<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>操作日志列表</title>
	<script src="${ctx}/static/scripts/operatorRecord.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;margin-left:5px;">
		<select id="selectUser" name="selectUser" class="easyui-combobox">
			<option value='0'>全部</option>
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'>${user.shopName}</option>
			</c:forEach>
		</select>
		<select id="selectPerson" name="selectPerson" class="easyui-combobox">
			<option value='0'>全部</option>
			<c:forEach items="${persons}" var="person">
				<option value='${person.id}'>${person.name}</option>
			</c:forEach>
		</select>
		<select id="selectModels" name="selectModels" class="easyui-combobox">
			<option value=''>全部</option>
			<c:forEach items="${models}" var="model">
				<option value='${model.key}'>${model.description}</option>
			</c:forEach>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>  
		<input class="easyui-textbox" name="q" id="q" data-options="prompt:'请输入关键字'" style="width:160px"/>
		<a href="javascript:operatorRecord.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'操作日志列表'" style="padding:2px;">
		<table id="tb_operatorRecord"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
</body>
</html>
