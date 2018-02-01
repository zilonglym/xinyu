<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单导入</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
    <div class="tab-content">
    	<!--订单导入 -->
    	<div class="tab-pane active" id="upload" style="margin-left:10px;margin-top:15px;">
		<form id="uploadForm" action="${ctx}/import/order/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
			<div style="margin-left:10px;margin-top:15px;">		
				<input type="file" name="file"/> 	
			</div>	
			<div class="form-actions" style="margin-left:10px;margin-top:15px;">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
			</div>
		</form>
		</div>
		<div style="margin-left:10px;margin-top:15px;"><h6 style="color:red">成功导入【${count}】条</h6></div>
		<div style="margin-left:10px;margin-top:15px;">${errorMsg}</div>
	</div>
</body>
</html>