<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>利润统计列表</title>
	<script src="${ctx}/static/scripts/profit.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="userName" class="easyui-combobox" name="userName" style="width:200px;">
				<option value="0">全部</option>
				<c:forEach items="${userList}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.shopName}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/> 
			<a href="javascript:profit.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportData()">导出</a>
			<a href="javascript:profit.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重新加载</a>
	</div>
	<div data-options="title:'利润统计列表'" style="padding:2px;">
		<table id="tb_profit"  >
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
		window.location = ctx+"/report/profit/xls?beigainTime="+beigainTime+"&lastTime="+lastTime; 
	}
	</script>
</body>
</html>
