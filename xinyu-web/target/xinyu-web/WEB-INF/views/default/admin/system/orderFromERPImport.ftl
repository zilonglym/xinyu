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
    	<!--ERP订单导入 -->
    	<div class="tab-pane active" id="upload">
			<form id="uploadForm" action="${ctx}/import/order/uploadFromERP" method="post" enctype="multipart/form-data" class="form-horizontal">
			<div style="margin-left:10px;margin-top:5px;">
				<select  id="selectUser" name="userId" class="easyui-combobox" style="width:150px;">
					<#list users as user>
						<option value='${user.id}'>${user.subscriberName}</option>
					</#list>
				</select>
		
				<select id="erp" name="erpKey" class="easyui-combobox" style="width:150px;">
					<#list erps as erp>
						<option value='${erp.key}'>${erp.description}</option>
					</#list>
				</select>	
					
				<input type="file" name="file"/> 			
				
			</div>	
			
			<div class="form-actions" style="margin-left:10px;margin-top:15px;">
				<input id="submit_btn" class="btn btn-primary" type="submit" value="提交"/>&nbsp;	
				<input id="cancel_btn" class="btn" type="button" value="返回" onclick="history.back()"/>
				<input id="cancel_btn" class="btn" type="button" value="查看最新批次记录" onclick="goTo()"/>
			</div>
			</form>
	    	<h6 style="color:red">成功导入【${count}】条</h6>
			<#if size!=0>
				<#list ordrMap as order>
					<h8>${order.msg}</h8>
				</#list>
			</#if>	
		</div>
	</div>
	<script>
		function  goTo(){
			window.location="${ctx}/import/toLastImportRecord";
		}
	</script>
</body>
</html>