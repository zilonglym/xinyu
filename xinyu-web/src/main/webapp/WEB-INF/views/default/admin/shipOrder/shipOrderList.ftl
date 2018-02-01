<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox" style="width:200px;">
			<option value="">全部</option> 
			<#list users as user>
				<option value="${user.id}">${user.subscriberName}</option>
			</#list>
		</select>
		<select id="status" name="status" class="easyui-combobox" style="width:100px;">
			<option value="">全部</option> 			
			<option value="WMS_ACCEPT">未审核</option>
			<option value="WMS_AUDIT">已审核</option>
			<option value="WMS_PRINT">已打印</option>
			<option value="WMS_FINASH">已发货</option>
			<option value="WMS_CANCEL">已取消</option>
			<option value="WMS_DELETED">已删除</option>
		</select>
		
		<select id="searchType" name="searchType" class="easyui-combobox" style="width:100px;">
			<option value="mobile">电话</option>
			<option value="receiverName">收件人</option>
			<option value="tmsOrderCode">运单号</option>
			<option value="nick">呢称</option>
			<option value="batchCode">批次号</option>
		</select>
		
		<input  style="width:300px;" id="searchText" name="searchText" type="text" class="easyui-textbox" data-options="prompt:'运单号/联系人电话/批次号/收件人/买家ID'"/>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="shipOrder.searchList();" >查询</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="updateShipOrderStatus();" >状态上传</a>
	   	
	    
	    
	</div>
	
	<table id="tb_shipOrder"  >
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	function updateShipOrderStatus(){
			var rows=$('#tb_shipOrder').datagrid("getChecked");
			if(rows==null||rows.length<1){
					$.messager.alert('错误','请选择订单！！');
				}else{
					var ids = [];
					for(var i=0; i<rows.length; i++){
						ids.push(rows[i].id);
					}
					$.post(ctx+"/shipOrder/order/statusUpload",{ids:ids},function(data){
						if(data && data.ret==1){
							$.messager.alert('提示','订单状态上传成功!');
						}else{
							$.messager.alert('提示','订单状态上传失败!['+data.msg+']');
						}
					});
				}
	}
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/shipOrder.js?t=1"></script>
</body>
</html>
