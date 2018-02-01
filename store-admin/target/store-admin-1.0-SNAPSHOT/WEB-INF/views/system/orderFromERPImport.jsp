<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tab.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />	
	<script>

	</script>
</head>
<body>

	<legend><small>ERP订单导入</small></legend>

	<ul class="nav nav-tabs">
	  <li class="active"><a href="#upload" data-toggle="tab">ERP批量订单导入</a></li>
	</ul>
    <div class="tab-content">
    	<!--ERP订单导入 -->
    	<div class="tab-pane active" id="upload">
		<form id="uploadForm" action="${ctx}/import/order/uploadFromERP" method="post" enctype="multipart/form-data" class="form-horizontal">
		<select  id="selectUser" name="userId" class="easyui-combobox">
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'>${user.shopName}</option>
			</c:forEach>
		</select>
		
		<select id="erp" name="erpKey" class="easyui-combobox">
			<c:forEach items="${erps}" var="erp">
				<option value='${erp.key}'>${erp.description}</option>
			</c:forEach>
		</select>
		
		<fieldset>
			<div style="margin-left:30px;margin-top:50px;">
				<div class="controls">
					<input type="file" name="file"/> 
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				<input id="cancel_btn" class="btn" type="button" value="查看最新批次记录" onclick="goTo()"/>
			</div>
		</fieldset>
		</form>
	    <h6 style="color:red">成功导入【${count}】条</h6>
		<c:forEach items="${ordrMap}" var="order">
				<h8>${order.msg}</h8>
			</c:forEach>
		</select>
		</div>
	</div>
	<script>
		function  goTo(){
			window.location="${ctx}/import/toLastImportRecord";
		}
	</script>
</body>
</html>