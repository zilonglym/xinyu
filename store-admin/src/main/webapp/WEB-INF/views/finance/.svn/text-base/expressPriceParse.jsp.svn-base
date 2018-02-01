<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>快递列表</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/scripts/express.js" type="text/javascript"></script>
</head>
<body>
	<div style="margin-bottom:5px;margin-top:5px;">
		<form id="countPrice"  method="post" enctype="multipart/form-data" action="${ctx}/record/upload">
			<div style="margin-bottom:10px;margin-top:10px;margin-left:15px;">
				<span>商家名称：</span>
				<select id="userId" name="userId" class="easyui-combobox">
					<c:forEach items="${userList}" var="user">
						<c:if test="${user.shopName!=null and user.shopName!=''}">
							<option value="${user.id}">${user.shopName}</option>
						</c:if>
					</c:forEach>	
				</select>
			</div>
			<div style="margin-bottom:10px;margin-top:10px;margin-left:15px;">
				<span>物流公司：</span>
				<select id="sysId" name="sysId" class="easyui-combobox">
					<c:forEach items="${expressList}" var="express">
						<option value="${express.id}">${express.description}</option>
					</c:forEach>	
				</select>
			</div>
			<div style="margin-bottom:10px;margin-top:10px;margin-left:15px;">
      			<input id="filename" name="filename" class="easyui-filebox" style="width:300px" data-options="prompt:'请选择文件...'">
      		</div>
      		<div style="margin-bottom:10px;margin-top:10px;margin-left:15px;">
      			<button type="submit">计算运费</button>
      			<a href="${ctx}/static/download/运费模板.xls" class="">下载运费模板 <small>(运费模板.xls)</small></a>
      		</div>
    	</form>  
	</div>
	<div style="margin-bottom:5px;margin-top:5px;">
		<c:if test="${success=='success'}">
			<span>已成功导入记录${num}条,更新记录${sum}条!</span>
		</c:if>
	</div>
	<div id="dialog"></div>
	<script>
		var ctx="${ctx}";
	</script>
</body>
</html>
