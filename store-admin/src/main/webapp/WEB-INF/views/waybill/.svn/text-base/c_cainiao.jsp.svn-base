<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script src="${ctx}/static/scripts/cainiao_print.js?t=1" type="text/javascript"></script>
	</head>
	<body>
		<br />
		<select id="printList" name="printList"></select>

		<input type="button" id="BtnPrint" name="BtnPrint" value="打印" onclick="printWeb();"  />

		<input type="button" onclick="doGetPrinters()"  value="刷新打印机列表"/>

		<input type="button" onclick="doPrinterConfig()"  value="当前打印机"/>
		<br />
		<h3>打印批次号:${batchCode}</h3>
		<h3>此批单据中已打印过的单据有:<span id="orderIndex"></span></h3>
		<h3>此次打印数量:<span id="printIndex"></span></h3>
		<h3>总数量:<span id="index"></span></h3>
		<div id="description"></div>
		<script>
			var ids = "${ids}";
			var ctx = "${ctx}";
			var type="${type}";
			var defaultPrinter;
		function printWeb() {
				defaultPrinter = $("#printList").val();
				//doSetPrinterConfig(name);//设置打印机
				//取电子面单号
				//打印
				//doPrint();
				cainiao.doPrint();
			}
		</script>
	</body>
</html>