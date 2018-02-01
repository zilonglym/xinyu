<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>单据信息</title>
	<script src="${ctx}/static/scripts/localBatch.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="shopId" name="shopId" class="easyui-combobox">
			<option value="">全部</option> 
			<c:forEach items="${shops}" var="shop">
				<option value='${shop.id}'>${shop.name}</option>
			</c:forEach>
		</select>
		<select id="status" name="status" class="easyui-combobox">
			<option value="">全部</option> 
			<option value="WMS_GETNO">待打印</option> 
			<option value="WMS_PRINT">已打印</option> 
			<option value="WMS_CONFIRM">已确认</option> 
			<option value="WMS_FINASH">已完成</option> 
		</select>
		<input class="easyui-datetimebox" name="startDate" id="startDate" data-options="prompt:'请选择起始时间'" style="width:120px"/>
		~
		<input class="easyui-datetimebox" name="endDate" id="endDate" data-options="prompt:'请选择截止时间'" style="width:120px"/>
		<input style="width:350px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'请输入关键信息'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="localBatch.search();" >查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-print'" onclick="localBatch.print();" >重打</a>
	</div>
	<div data-options="title:'单据信息'" style="padding:2px;">
		<table id="tb_localBatch"  >
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
