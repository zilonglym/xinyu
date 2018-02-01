<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>结算记录列表</title>
	<script src="${ctx}/static/scripts/settlement.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="userName" class="easyui-combobox" name="userName" style="width:200px;">
				<option value="">全部</option>
				<c:forEach items="${userList}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.shopName}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/> 
			<input class="easyui-textbox" name="code" id="code" data-options="prompt:'请输入结算编号'"/> 
			<a href="javascript:settlement.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:settlement.add();" class="easyui-linkbutton" data-options="iconCls:'icon-add'">新建</a>
			<a href="javascript:settlement.edit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">编辑</a>
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-export'" onClick="exportData()">导出</a>
			<a href="javascript:settlement.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重新加载</a>
	</div>
	<div data-options="title:'结算记录列表'" style="padding:2px;">
		<table id="tb_settlement"  >
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
		var userName=$("#userName").combobox('getValue');
		var code=$("#code").textbox('getValue');
		window.location = ctx+"/report/settlement/xls?beigainTime="+beigainTime+"&lastTime="+lastTime+"&userName="+userName+"&code="+code; 
	}
	</script>
</body>
</html>
