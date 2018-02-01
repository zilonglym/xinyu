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

	<legend><small>订单导入</small></legend>

	<ul class="nav nav-tabs">
	  <li class="active"><a href="#upload" data-toggle="tab">订单打印批量导入</a></li>
	  <li> <a href="#import" data-toggle="tab">导入确认(未确认(${count})条)</a></li>
	</ul>
	
    <div class="tab-content">
    	<div class="tab-pane" id="import">
    	<form id="importForm" action="${ctx}/import/orderSub" method="post" ><input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/></form>
   		 </div>
    	<!--订单导入 -->
    	<div class="tab-pane active" id="upload">
		<form id="uploadForm" action="${ctx}/import/order/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
		<fieldset>
			<div >
				<div class="controls">
					<input type="file" name="file"/> 
				</div>
			</div>	
			<div class="form-actions">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</fieldset>
		</form>
		</div>
	
		<h6 style="color:red">成功导入【${count}】条</h6>
		
	</div>
</body>
</html>