<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>货位统计</title>
	<script src="${ctx}/static/scripts/localReport.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="shopId" name=""shopId"" class="easyui-combobox">
			<option value="">全部</option> 
			<c:forEach items="${shops}" var="shop">
				<option value="${shop.id}">${shop.name}</option>
			</c:forEach>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>  
		<input style="width:250px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'请输入型号'"/>  
		<a href="javascript:localReport.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'货位统计'" style="padding:2px;">
		<table id="tb_localReport">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 
	</script>
</body>
</html>
