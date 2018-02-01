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
		<table style="font-size:15px;">
			<tr>
				<td style="font-weight:bold;">商家名称：</td><td>${stockOrder.userName}</td>
				<td style="font-weight:bold;">入库单号：</td><td>${stockOrder.orderCode}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">外部单号：</td><td>${stockOrder.orderCode}</td>
				<td style="font-weight:bold;">单据类型：</td><td>${stockOrder.orderType}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">出库时间：</td><td>${stockOrder.sendTime}</td>
				<td style="font-weight:bold;">创建时间：</td><td>${stockOrder.orderCreateTime}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">服务标识：</td><td>${stockOrder.orderFlag}</td>
				<td style="font-weight:bold;">出库方式：</td><td>${stockOrder.mode}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">配送编码：</td><td>${stockOrder.tmsServiceCode}</td>
				<td style="font-weight:bold;">取件公司：</td><td>${stockOrder.pickCompany}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">取件人名：</td><td>${stockOrder.pickName}</td>
				<td style="font-weight:bold;">取件证件：</td><td>${stockOrder.pickId}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">取件电话：</td><td>${stockOrder.pickTel}</td>
				<td style="font-weight:bold;">取件手机：</td><td>${stockOrder.pickCall}</td>
			</tr>
			<tr>
				<td style="font-weight:bold;">发货物流：</td>
				<td>
					<select class="easyui-combobox" id="company" name="company" style="width:172px;">
						<#list companys as company>
							<option value="${company.value}">${company.description}</option>
						</#list>
					</select>				
				</td>
				<td style="font-weight:bold;">物流单号：</td><td><input class="easyui-textbox" type="text" id="orderNo" name="orderNo"/></td>
			</tr>
			<tr>
				<td style="font-weight:bold;">长度(毫米)：</td><td><input class="easyui-numberbox" type="text" id="packageLength" name="packageLength"/></td>
				<td style="font-weight:bold;">宽度(毫米)：</td><td><input class="easyui-numberbox" type="text" id="packageWidth" name="packageWidth"/></td>
			</tr>
			<tr>
				<td style="font-weight:bold;">高度(毫米)：</td><td><input class="easyui-numberbox" type="text" id="packageHeight" name="packageHeight"/></td>
				<td style="font-weight:bold;">备注信息:</td><td><input class="easyui-textbox" type="text" id="remark" name="remark" value="${stockOrder.remark}"/></td>
			</tr>
		</table>
	</div>
	<div id="items">
		<table id="tb_orderItem">
			<thread>
				<tr>
					
				</tr>
			</thread>
		</table>
		<table id="tb_confirms">
			<thread>
				<tr>
					
				</tr>
			</thread>
		</table>
	</div>
	
	<script>
	var ctx="${ctx}";
	</script>
	<script type="text/javascript" src="${base}/scripts/system/admin/stockOutOrder.js"></script>
</body>
</html>
