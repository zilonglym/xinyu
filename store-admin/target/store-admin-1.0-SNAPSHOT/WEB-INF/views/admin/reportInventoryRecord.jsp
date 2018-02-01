<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出入库统计</title>
	<script src="${ctx}/static/scripts/inventoryRecord.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value="0">全部</option> 
			<c:forEach items="${users}" var="user">
				<c:if test="${user.shopName!=null and user.shopName!=''}">
					<option value="${user.id}">${user.shopName}</option>
				</c:if>
			</c:forEach>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>
		<input id="itemTitle" class="easyui-textbox" name="itemTitle"  data-options="prompt:'请输入商品名称'"></input>   
		<a href="javascript:inventoryRecord.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportData()">库存明细下载</a>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exporInventoryData()">出入库下载</a>
	</div>
	<div data-options="title:'出入库统计'" style="padding:2px;">
		<table id="tb_inventoryRecord">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<script>
	var ctx="${ctx}"; 
	function exportData(){
		var userId=$("#userId").combobox('getValue');	
		var title=$("#itemTitle").textbox('getValue');	
		var startDate=$("#beigainTime").datetimebox('getValue');	
		var endDate=$("#lastTime").datetimebox('getValue');	
		window.location=ctx+"/report/inventory/xls?userId="+userId+"&title="+title+"&startDate="+startDate+"&endDate="+endDate;
	}
	function exporInventoryData(){
		var userId=$("#userId").combobox('getValue');	
		var title=$("#itemTitle").textbox('getValue');	
		var startDate=$("#beigainTime").datetimebox('getValue');	
		var endDate=$("#lastTime").datetimebox('getValue');	
		window.location=ctx+"/report/inventoryRecord/xls?userId="+userId+"&title="+title+"&startDate="+startDate+"&endDate="+endDate;
	}
	</script>
</body>
</html>
