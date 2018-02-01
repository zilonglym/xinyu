<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>批量审核列表</title>
	<script src="${ctx}/static/scripts/tradeWaits.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<select id="userId" class="easyui-combobox" name="userId" style="width:200px;">
				<option value="">全部</option>
				<c:forEach items="${users}" var="user">
					<c:if test="${user.shopName!=null and user.shopName!=''}">
						<option value="${user.id}">${user.shopName}</option>
					</c:if>
				</c:forEach>	
			</select>
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
			<input id="q" class="easyui-textbox" name="q" data-options="prompt:'请输入信息'"></input>
			<a href="javascript:trade.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="javascript:trade.edit();" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
			<a href="javascript:trade.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-refresh'">重新加载</a>
	</div>
	<div data-options="title:'批量审核列表'" style="padding:2px;">
		<table id="tb_trade"  >
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
