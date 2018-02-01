<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
		<script src="${base}/scripts/system/cainiao_print.js?t=1121" type="text/javascript"></script>
	</head>
	<body>
		<br />
		<select id="printList" name="printList"></select>

		<input type="button" id="BtnPrint" name="BtnPrint" value="打印" onclick="printWeb();"  />
		<input type="button" onclick="doGetPrinters()"  value="刷新打印机列表"/>
		<input type="button" onclick="doPrinterConfig()"  value="当前打印机"/>
		<script>
			var ids = "${ids!''}";
			var ctx = "${ctx}";
			var batchCode = "${batchCode}";
			var type="${type!''}";
			var defaultPrinter;
			var index=0;
		function printWeb() {
				defaultPrinter = $("#printList").val();
				//doSetPrinterConfig(name);//设置打印机
				//取电子面单号
				//打印
				//doPrint();
				if(index==0){
					cainiao.doPrint();
				}
				index++;
			}
		</script>
	</body>
</html>