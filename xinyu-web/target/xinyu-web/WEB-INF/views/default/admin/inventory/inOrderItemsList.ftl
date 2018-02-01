<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>单据明细列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>
<body>
	<div id="header">
	
		<table>
			<tr>
				<td>商家名称：</td><td>${stockOrder.userName}</td>
				<td>入库单号：</td><td>${stockOrder.orderCode}</td>
			</tr>
			<tr>
				<td>外部单号：</td><td>${stockOrder.erpOrderCode}</td>
				<td>单据类型：</td><td>${stockOrder.orderType}</td>
			</tr>
			<tr>
				<td>预期到货：</td><td>${stockOrder.expectStartTime}</td>
				<td>最迟到货：</td><td>${stockOrder.expectEndTime}</td>
			</tr>
			<tr>
				<td>服务标识：</td><td>${stockOrder.orderFlag}</td>
				<td>订单来源：</td><td>${stockOrder.orderSource}</td>
			</tr>
			<tr>
				<td>创建时间：</td><td>${stockOrder.orderCreateTime}</td>
				<td>退货原因：</td><td>${stockOrder.returnReason}</td>
			</tr>
			<tr>
				<td>配送编码：</td><td>${stockOrder.tmsServiceCode}</td>
				<td>配送单号：</td><td>${stockOrder.tmsOrderCode}</td>
			</tr>
			<tr>
				<td>收件昵称：</td><td>${stockOrder.buyerNick}</td>
				<td>单据备注：</td><td>${stockOrder.remark}</td>
			</tr>
			
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>
	</div>
	<div id="items">
		<table id="tb_orderItem">
			<thread>
			</thread>
		</table>
	</div>
	
	<div id="confirms">
		<table id="tb_confirms">
			<thread>
			</thread>
		</table>
	</div>
	
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/stockInOrder.js"></script>
</body>
</html>
