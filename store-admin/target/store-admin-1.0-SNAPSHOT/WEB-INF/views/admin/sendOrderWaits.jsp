<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>运单设置</title>
		<script src="${ctx}/static/scripts/sendOrder.js?v=2.0.1" type="text/javascript"></script>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tooltip.js" type="text/javascript"></script>
	<script>
		$(function () {
	       $("[rel='tooltip']").tooltip();
	    });
		var ctx="${ctx}";
	</script>
</head>

<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select name="selectUser" id="selectUser"  class="easyui-combobox">
			<option value="0">全部</option>
				<c:forEach items="${users}" var="user">
					<c:if test="${user.shopName!=null}">
						<option value="${user.id}">${user.shopName}</option>
					</c:if>	
				</c:forEach>
		</select>
		<select name="selectCompany" id="selectCompany" class="easyui-combobox">
			<option value="">全部</option>
				<c:forEach items="${companys}" var="company">
					<option value="${company.value}">${company.description}</option>
				</c:forEach>
		</select>
		<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入运单号/姓名/手机/商品名/批次号'"></input>
		<a href="javascript:sendOrder.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'快递运单设置列表'" style="padding:2px;">	
	<table id="tb_sendOrder">
		<thead>
			<tr>
		
			</tr>
		</thead>
		
	</table>
	</div>
</body>
</html>
