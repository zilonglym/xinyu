<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>商品库存初始化</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<form id="uploadForm" action="${ctx}/inventory/upload" method="post" enctype="multipart/form-data" class="form-horizontal">
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
	<script>
	var ctx="${ctx}";
	</script>
</body>
</html>
