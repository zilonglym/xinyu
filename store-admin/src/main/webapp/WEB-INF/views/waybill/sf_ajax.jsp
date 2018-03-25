<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Powered By 顺丰打印</title>
		<meta name="Author" content="360chain Team" />
		<meta name="Copyright" content="360chain" />
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="http://127.0.0.1:8000/c_c2.css" />
		<script src='http://127.0.0.1:8000/CLodopfuncs.js'></script>

	</head>
	<body class="list"  onLoad="initPrint()">
		<div class="bar">

		</div>
		<select id="printIndex"></select>
		<button type="button" onclick="MyPrint();" class="btn btn-primary" >
			点击打印
		</button>
		<div class="body" id="printControllerDiv" style="display:none;">
			<table  id="printControllerTable" border="1" style="border-color:gray;" width="100%"  height="100%">
				<tr>
					<td width="100%" colspan="16">
					<object id="LODOP_X" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=650>
						<param name="Color" value="#d3e6ff">
						<embed id="LODOP_EM" TYPE="application/x-print-lodop" width=100% height=650  color="#d3e6ff"  PLUGINSPAGE="install_lodop32.exe"></embed>
					</object></td>
				</tr>
			</table>
		</div>
		<div class="body" id="printLogDiv">
			<table border="0" width="100%"  height="100%">
				<tr>
					<td width="100%" colspan="16"><h1> 打印日志</h1></td>
				</tr>
				<tr>
					<td id="title"></td>
				</tr>
				<tr>
					<td width="100%" colspan="16">					<textarea  cols=150 rows=25 id="printLogoOutput" style="display:none;">
                </textarea></td>
				</tr>
			</table>
		</div>
		<script language="javascript" type="text/javascript">
			var LODOP;
			//声明为全局变量
			var data = '${ids}';
			var ctx = "${ctx}";
			var batchCode = "${batchCode}";
			var type = "${type}";
			var index = 10, t = 0, count = 0;
			function initPrint() {
				CLODOP.Create_Printer_List(document.getElementById('printIndex'));

			}
			
			$(document).ready(function(){
				initPrint();
			});
			function MyPrint() {
				load();
				var json = data.split(",");
				var s = json.length;
				var obj, html, ids = "", page = 0;
				$.ajax({
					type : 'post',
					url : ctx + "/waybill/getSfPrint",
					data : {
						ids : data,
						cpCode : 'SF',
						batchCode : batchCode,
						type : type
					},
					cache : false,
					async : true,
					success : function(data) {
						if (data && data.code == 200) {
							printsf(data.data, page, true);
						} else {
							//String html=data.msg
							//$(html).insertBefore("#printLogoOutput");
						}
					}
				});
			}

			function printsf(json, seq, isEnd) {
				var printIndex = $("#printIndex").val()
				LODOP.PRINT_INIT("顺丰快递特惠电子面单打印" + seq);

				var index = 0;
				seq = 0;
				for ( i = 0; i < json.length; i++) {
					count++;
					index++;
					obj = json[i];
					html = "<h5>[" + count + "]" + obj.sf_destname + "已发送到打印机</h5>";
					$(html).insertBefore("#printLogoOutput");
					CreateOnePage(obj, count);
					if(index==20){
						disLoad();
						index=0;
						LODOP.SET_PRINTER_INDEX(printIndex);
						LODOP.SET_PRINT_PAGESIZE(0, "1000", "1500", "LodopCustomPage");
						LODOP.SET_PRINT_MODE("POS_BASEON_PAPER", true);
						LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='http://admin.wlpost.com/static/waybill/sf.png'/>");
						LODOP.SET_SHOW_MODE("BKIMG_LEFT", 0);
						LODOP.SET_SHOW_MODE("BKIMG_TOP", 0);
						LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 378);
						LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 567);
						LODOP.SET_SHOW_MODE("BKIMG_PRINT", true);	
						LODOP.SET_PRINT_MODE("SF_TASK_NAME","顺丰电子面单"+seq);//为每个打印单独设置任务名	
						console.log(seq+"--print--"+printIndex);
						LODOP.PRINT();
						seq++;
						LODOP.PRINT_INIT("顺丰快递特惠电子面单打印" + seq);
					}
				}
				if(index>0){
					LODOP.SET_PRINTER_INDEX(printIndex);
					LODOP.SET_PRINT_PAGESIZE(0, "1000", "1500", "LodopCustomPage");
					LODOP.SET_PRINT_MODE("POS_BASEON_PAPER", true);
					LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='http://admin.wlpost.com/static/waybill/sf.png'/>");
					LODOP.SET_SHOW_MODE("BKIMG_LEFT", 0);
					LODOP.SET_SHOW_MODE("BKIMG_TOP", 0);
					LODOP.SET_SHOW_MODE("BKIMG_WIDTH", 378);
					LODOP.SET_SHOW_MODE("BKIMG_HEIGHT", 567);
					LODOP.SET_SHOW_MODE("BKIMG_PRINT", true);	
					LODOP.SET_PRINT_MODE("SF_TASK_NAME","顺丰电子面单"+seq);//为每个打印单独设置任务名	
					console.log(seq+"--END--"+printIndex);
					LODOP.PRINT();
					//LODOP.PREVIEW();
				}
			}

			function CreateOnePage(obj, seq) {
				LODOP.NewPage();
				//标记E
				LODOP.SET_PRINT_STYLE("FontSize", 24);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("s_e", 22, 195, 61, 40, "E");
				LODOP.SET_PRINT_STYLE("FontSize", 14);
				LODOP.ADD_PRINT_TEXTA("s_count", 37, 95, 61, 40, seq);
				//上方运单号 条形码
				LODOP.SET_PRINT_STYLE("FontSize", 10);
				LODOP.ADD_PRINT_BARCODE(65, 50, 161, 50, "128C", obj.sf_orderno);
				//目的地
				LODOP.SET_PRINT_STYLE("FontSize", 12);
				LODOP.ADD_PRINT_TEXTA("s_date", 60, 270, 93, 27, "顺丰特惠");

				// LODOP.SET_PRINT_STYLE("FontSize",8);
				LODOP.ADD_PRINT_TEXTA("label_address", 85, 255, 93, 27, "目的地：");
				LODOP.SET_PRINT_STYLE("FontSize", 24);
				LODOP.ADD_PRINT_TEXTA("s_address", 99, 263, 93, 27, obj.sf_destcode);

				//收货地址
				LODOP.SET_PRINT_STYLE("FontSize", 10);
				LODOP.ADD_PRINT_TEXTA("label_receive", 136, 15, 80, 27, "收方：");
				LODOP.ADD_PRINT_TEXTA("s_receive", 136, 50, 295, 27, obj.sf_destaddress);
				//详细地址
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.ADD_PRINT_TEXTA("s_addressInfo", 150, 15, 350, 27, obj.sf_destdetailaddress);
				//收货人  和 收货电话号码
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.ADD_PRINT_TEXTA("s_destname", 180, 15, 220, 22, obj.sf_destname);
				//代收货款
				LODOP.SET_PRINT_STYLE("FontSize", 12);
				LODOP.ADD_PRINT_TEXTA("s_p", 215, 282, 90, 18, "");

				//卡号
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("label_card", 234, 255, 80, 22, "卡号：");

				//运费
				LODOP.SET_PRINT_STYLE("FontSize", 10);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("label_yun", 253, 255, 90, 18, "运费：");
				LODOP.ADD_PRINT_TEXTA("label_total_price", 270, 255, 90, 18, "费用合计：");

				//同意自取
				LODOP.SET_PRINT_STYLE("FontSize", 12);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("label_a", 246, 16, 120, 22, "同意自取");

				//转寄协议客户
				LODOP.SET_PRINT_STYLE("FontSize", 12);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("label_x", 266, 16, 120, 22, "转寄协议客户");

				//下方运单号 条形码
				LODOP.SET_PRINT_STYLE("FontSize", 10);
				LODOP.ADD_PRINT_BARCODE(350, 190, 180, 50, "128C", obj.sf_orderno);

				//寄件信息
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.ADD_PRINT_TEXTA("label_jf", 295, 15, 80, 22, "寄方：");
				LODOP.SET_PRINT_STYLE("FontSize", 7);
				LODOP.ADD_PRINT_TEXTA("s_selleraddress", 295, 55, 150, 22, obj.sf_selleraddress);
				LODOP.ADD_PRINT_TEXTA("s_sellerdetail", 307, 55, 150, 22, obj.sf_sellerdetailaddress);
				LODOP.ADD_PRINT_TEXTA("s_sellername", 329, 55, 150, 22, obj.sf_sellername);

				//下寄件方信息
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("l_jf", 410, 15, 50, 22, "寄方：");

				LODOP.SET_PRINT_STYLE("FontSize", 7);
				LODOP.SET_PRINT_STYLE("Bold", 0);
				LODOP.ADD_PRINT_TEXTA("s_sfaddress", 410, 45, 150, 22, obj.sf_selleraddress);
				LODOP.ADD_PRINT_TEXTA("s_sfdetail", 422, 15, 150, 22, obj.sf_sellerdetailaddress);
				LODOP.ADD_PRINT_TEXTA("s_sfname", 450, 15, 150, 22, obj.sf_sellername);

				//下收方信息
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA(410, 175, 50, 22, "收方：");
				LODOP.SET_PRINT_STYLE("FontSize", 7);
				LODOP.SET_PRINT_STYLE("Bold", 0);
				LODOP.ADD_PRINT_TEXTA("s_destaddress", 410, 205, 165, 32, obj.sf_destdetailaddress);
				LODOP.ADD_PRINT_TEXTA("s_destname", 450, 175, 180, 22, obj.sf_destname);
				if (obj.userId == 55) {
					LODOP.SET_PRINT_STYLE("FontSize", 12);
				} else {
					//  //数量   寄托物品
					LODOP.SET_PRINT_STYLE("FontSize", 8);
				}
				//LODOP.ADD_PRINT_TEXTA("L_X",482,10,25,22,"");
				LODOP.ADD_PRINT_TEXTA("l_x", 475, 15, 350, 85, obj.sf_selleritems);

				//备注
				LODOP.ADD_PRINT_TEXTA("s_remark", 482, 300, 50, 30, "");

				//合计费用
				//LODOP.SET_PRINT_STYLE("FontSize",7);
				//LODOP.ADD_PRINT_TEXTA("l_hj",525,315,80,20,"费用合计：");

				//原寄地CODE
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("label_jd", 315, 15, 60, 22, "原寄地：");
				LODOP.SET_PRINT_STYLE("FontSize", 12);
				LODOP.SET_PRINT_STYLE("Bold", 1);
				LODOP.ADD_PRINT_TEXTA("s_sellercode", 325, 15, 60, 22, obj.sf_sellercode);
				//单号
				//LODOP.SET_PRINT_STYLE("FontSize",8);
				//LODOP.SET_PRINT_STYLE("Bold",1);
				//LODOP.ADD_PRINT_TEXTA("label_orderno",525,15,285,22,"订单号：");
				//LODOP.SET_PRINT_STYLE("FontSize",6);
				//LODOP.ADD_PRINT_TEXTA("s_orderno",539,15,285,22,obj.sf_selforderno);

				//付款方式
				LODOP.SET_PRINT_STYLE("FontSize", 9);
				LODOP.ADD_PRINT_TEXTA("label_fs", 206, 16, 83, 18, "付款方式:");
				LODOP.ADD_PRINT_TEXTA("s_fs", 206, 86, 83, 18, "寄付月结 ");

				//月结账号
				LODOP.ADD_PRINT_TEXTA("label_account", 226, 16, 83, 18, "月结帐号:");
				LODOP.ADD_PRINT_TEXTA("s_account", 226, 86, 83, 18, "7312005783");
				LODOP.SET_PRINT_STYLE("FontSize", 8);

				//计费重量
				LODOP.ADD_PRINT_TEXTA("label_weight", 206, 149, 73, 18, "计费重量:");
				//LODOP.ADD_PRINT_TEXTA("s_weight", 206, 209, 53, 18, "${trade.weight}");
				LODOP.SET_PRINT_STYLE("FontSize", 8);
				//打印时间
				LODOP.ADD_PRINT_TEXTA("label_time", 182, 250, 74, 18, "打印时间：");
				LODOP.ADD_PRINT_TEXTA("s_time", 182, 310, 74, 18, obj.sf_date);
			};

			function load() {  
			    $("<div class=\"datagrid-mask\"></div>").css({ display: "block", width: "100%", height: $(window).height() }).appendTo("body");  
			    $("<div class=\"datagrid-mask-msg\"></div>").html("数据加载中，请稍候。。。").appendTo("body").css({ display: "block", left: ($(document.body).outerWidth(true) - 190) / 2, top: ($(window).height() - 45) / 2 });  
			}
			//取消加载层  
			function disLoad() {  
			    $(".datagrid-mask").remove();  
			    $(".datagrid-mask-msg").remove();  
			}
		</script>
	</body>
</html>
