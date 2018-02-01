<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>公司利润列表</title>
	<script src="${ctx}/static/scripts/monthProfits.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/> 
			<a href="javascript:monthProfits.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:monthProfits.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新增</a>
			<a href="javascript:monthProfits.edit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportData()">导出</a>
			<a href="javascript:monthProfits.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重新加载</a>
	</div>
	<div data-options="title:'公司利润列表'" style="padding:2px;">
		<table id="tb_month"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	function exportData(){
		var beigainTime=$("#beigainTime").datetimebox('getValue');
		var lastTime=$("#lastTime").datetimebox('getValue');
		window.location=ctx+"/report/monthProfits/xls?beigainTime="+beigainTime+"&lastTime="+lastTime;
	}
	</script>
</body>
</html>
