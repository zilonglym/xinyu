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
	<script src="${ctx}/static/scripts/person.js?v=1.01" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<input id="name" class="easyui-searchbox" name="name"></input>
			<a href="javascript:person.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增员工</a>
			<a href="javascript:person.remove();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">删除员工</a>
			<a href="javascript:person.edit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改员工</a>
			<a href="javascript:person.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-refresh'">重新加载</a>
	</div>
	<div data-options="title:'员工列表'" style="padding:2px;">
		<table id="tb_person"  >
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
