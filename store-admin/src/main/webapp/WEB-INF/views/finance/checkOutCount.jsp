<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>扫描出库统计</title>
	<script src="${ctx}/static/scripts/checkOutCount.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<select id="userId" class="easyui-combobox" name="userId">
				<option value="">全部</option>
				<c:forEach items="${users}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.id}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<select id="sysId" class="easyui-combobox" name="sysId">
				<option value="">全部</option>
				<c:forEach items="${expressList}" var="express">
					<option value="${express.id}">${express.description}</option>
				</c:forEach>	
			</select>	
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/>    
		<a href="javascript:checkOutCount.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="${ctx}/record/checkout/list" class="easyui-linkbutton" target="_blank">出库明细</a>
	</div>
	<div data-options="title:'扫描出库统计'" style="padding:2px;">
		<table id="tb_checkOutCount">
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
