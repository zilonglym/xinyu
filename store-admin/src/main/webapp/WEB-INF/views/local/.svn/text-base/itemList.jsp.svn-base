<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>本地商品信息</title>
	<script src="${ctx}/static/scripts/localItem.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="shopId" name="shopId" class="easyui-combobox">
			<option value="">全部</option> 
			<c:forEach items="${shops}" var="shop">
				<option value='${shop.id}'>${shop.name}</option>
			</c:forEach>
		</select>
		<input style="width:200px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'请输入型号'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="localItem.search();" >查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-add'" onclick="localItem.add();" >新建</a>
	</div>
	<div data-options="title:'本地商品信息'" style="padding:2px;">
		<table id="tb_localItem"  >
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
