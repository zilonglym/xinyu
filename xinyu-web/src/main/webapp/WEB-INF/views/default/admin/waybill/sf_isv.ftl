<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Powered By 顺丰打印</title>
<meta name="Author" content="360chain Team" />
<meta name="Copyright" content="360chain" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<script src="${ctx}/static/waybill/LodopFuncs.js" type="text/javascript"></script>

<script  language="javascript" type="text/javascript">
    //调用打印预览
    function OpenPreview() {
        LODOP.PRINT_INIT("SF快递特惠电子面单打印");
        //LODOP.SET_PRINT_STYLE("FontSize",13);  //设置整体的打印字体
        LODOP.SET_PRINT_PAGESIZE(1,"0","0","CreateCustomPage"); //设置打印纸张,根据具体要求定尺寸
		LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
   		//LODOP.ADD_PRINT_SETUP_BKIMG("http://127.0.0.1:9090/static/waybill/sf.tif");
   		LODOP.ADD_PRINT_SETUP_BKIMG("/home/sf.tif");
   		
   		LODOP.SET_SHOW_MODE("BKIMG_LEFT",0);
   		LODOP.SET_SHOW_MODE("BKIMG_TOP",0);
   		LODOP.SET_SHOW_MODE("BKIMG_WIDTH",378);
   		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT",567);
   	    //用函数SET_SHOW_MODE的类型BKIMG_LEFT、BKIMG_TOP、BKIMG_WIDTH、BKIMG_HEIGHT可以设置背景图的位置和大小，
   	    <c:forEach items="${tradeList}" var="trade">
   	    LODOP.NewPage();
   	    //上方运单号 条形码
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    LODOP.ADD_PRINT_BARCODE(65,50,161,50,"128C","${trade.sf_orderno}");
		
   	    //目的地
   	    LODOP.SET_PRINT_STYLE("FontSize",24); 
   	    LODOP.ADD_PRINT_TEXT(99,263,93,27,"${trade.sf_destcode}");
   	 
   	    //收货地址
   	    LODOP.SET_PRINT_STYLE("FontSize",14); 
   	    LODOP.ADD_PRINT_TEXT(136,69,265,27,"${trade.sf_destaddress}");
   	    
   	     //详细地址
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.ADD_PRINT_TEXT(158,69,265,22,"${trade.sf_destdetailaddress}");
   	    
   	    
   	    //收货人  和 收货电话号码
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.ADD_PRINT_TEXT(180,69,169,22,"${trade.sf_destname}");
   	    
   	    
   	    //代收货款
   	    LODOP.SET_PRINT_STYLE("FontSize",12);
   	    LODOP.ADD_PRINT_TEXT(215,282,90,18,"");
   	    
   	    //卡号
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.ADD_PRINT_TEXT(234,289,80,22,"");
   	    
   	    //运费
   	    LODOP.ADD_PRINT_TEXT(251,316,60,22,"");
   	    //费用合计
   	    LODOP.ADD_PRINT_TEXT(271,316,80,22,"");
   	    
   	    //下方运单号 条形码
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    LODOP.ADD_PRINT_BARCODE(350,190,180,50,"128C","${trade.sf_orderno}");
   	    
 		//寄件信息
   	    LODOP.SET_PRINT_STYLE("FontSize",6);
   	    LODOP.ADD_PRINT_TEXT(295,62,150,22,"${trade.sf_selleraddress}");
   	    LODOP.ADD_PRINT_TEXT(307,62,150,22,"${trade.sf_sellerdetailaddress}");
   	    LODOP.ADD_PRINT_TEXT(329,62,150,22,"${trade.sf_sellername}");
   	    
   	    //下寄件方信息
   	    LODOP.ADD_PRINT_TEXT(410,30,140,22,"${trade.sf_selleraddress}");
   	    LODOP.ADD_PRINT_TEXT(422,30,140,22,"${trade.sf_sellerdetailaddress}");
   	    LODOP.ADD_PRINT_TEXT(444,30,140,22,"${trade.sf_sellername}");
   	    
   	    //下收方信息
   	    LODOP.ADD_PRINT_TEXT(410,190,140,22,"${trade.sf_destaddress}");
   	    LODOP.ADD_PRINT_TEXT(422,190,140,22,"${trade.sf_destdetailaddress}");
   	    LODOP.ADD_PRINT_TEXT(434,190,140,22,"${trade.sf_destname}");
   	    
   	    
   	     //数量   寄托物品
   	    LODOP.ADD_PRINT_TEXT(482,10,25,22,"");
   	    LODOP.ADD_PRINT_TEXT(482,47,260,30,"${trade.sf_selleritems}");
   	    
   	    //备注
   	    LODOP.ADD_PRINT_TEXT(482,300,50,30,"");
   	    
   	  
   	    
   	    //合计费用
   	    LODOP.ADD_PRINT_TEXT(539,320,40,20,"");
   	    
   	    //原寄地CODE
   	    LODOP.ADD_PRINT_TEXT(325,20,60,22,"${trade.sf_sellercode}");
   	     //单号
   	    LODOP.SET_PRINT_STYLE("FontSize",6);
   	    LODOP.ADD_PRINT_TEXT(539,15,285,22,"${trade.sf_selforderno}");
   	    
   	    //付款方式 
   	    LODOP.SET_PRINT_STYLE("FontSize",6);
   	    
   	    LODOP.ADD_PRINT_TEXT(208,56,83,18,"寄付月结 ");
   	    
   	    //月结账号
   	    LODOP.ADD_PRINT_TEXT(208,187,83,18,"7312005783");
   	      LODOP.SET_PRINT_STYLE("FontSize",7);
   	    //打印时间
   	    LODOP.ADD_PRINT_TEXT(182,290,74,18,"${trade.sf_date}");
   	    </c:forEach>
   	 	
 
        //LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","100%");//按整页缩放
        LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
        //LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,""); //隐藏工具条，设置适高显示
        LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",1); //预览界面内嵌到页面内
        
        
        LODOP.PREVIEWB();
        blPreviewOpen=false;
    };

  

var maxPages4preview = 100000000;//最大预览页数
var LODOP=getLodop(document.getElementById('LODOP_X'),document.getElementById('LODOP_EM')); //声明为全局变量
var blPreviewOpen=true;  //默认为预览
var totalPages =1;//总的打印的页数recordsJSON
if (totalPages> maxPages4preview) //如果是记录数>maxPages4preview,则不预览
   blPreviewOpen = false;

//如果是IE浏览器并且记录数不超过maxPages4preview打开预览
if (navigator.appVersion.indexOf("MSIE")>=0 && blPreviewOpen)
    OpenPreview();
else
{
    if(blPreviewOpen)  //如果不是IE浏览器,要在预览后手动点击翻页
    {
        OpenPreview();
   }
    else
    {
      $("#LODOP_X").height(0);
      $("#LODOP_EM").height(0);
      //此处不能隐藏打印控件，否则在 谷歌浏览器下直接打印会找不到控件
      $("#printControllerTable").height(0);
      $("#printControllerTable").attr("border",0);
      $("#printLogDiv").show(); //显示打印日志输出框
      printWithouPreview(maxPages4preview);//每50张输出到打印机一次
   }
}

//第一页
function actionFirst(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_GOFIRST",0);
};
//前一页
function actionPre(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_GOPRIOR",0);
};
//后一页
function actionNext(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_GONEXT",0);
};
//最后一页
function actionLast(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_GOLAST",0);
};
//到第几页
function actionGoto(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_GOTO",document.getElementById('inputpage').value);//PREVIEW_GOSKIP
};
//打印设置
function actionPrintSetup(){
if (!blPreviewOpen) OpenPreview();
LODOP.DO_ACTION("PREVIEW_SETUP",0);
};
//打印全部
function actionPrintAll(){
if (!blPreviewOpen) OpenPreview();
var iPageCount=LODOP.GET_VALUE("PREVIEW_PAGE_COUNT",0);//获得页数
LODOP.SET_PRINT_MODE("PRINT_START_PAGE",1);
LODOP.SET_PRINT_MODE("PRINT_END_PAGE",iPageCount);
LODOP.DO_ACTION("PREVIEW_PRINT",0);
};
//打印当前页
function actionPrintCurrent(){
if (!blPreviewOpen) OpenPreview();
var iThisNumber=LODOP.GET_VALUE("PREVIEW_PAGE_NUMBER",0);//获得当前页号
LODOP.SET_PRINT_MODE("PRINT_START_PAGE",iThisNumber);
LODOP.SET_PRINT_MODE("PRINT_END_PAGE",iThisNumber);
LODOP.DO_ACTION("PREVIEW_PRINT",0);
};
//关闭
function actionClose(){
	if (!blPreviewOpen) return;
	LODOP.DO_ACTION("PREVIEW_CLOSE",0);
	blPreviewOpen=false;
};

function MyDesign(){
	LODOP=getLodop(document.getElementById('LODOP_X'),document.getElementById('LODOP_EM'));
	//LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",false); //预览界面内嵌到页面内
	
	
	OpenPreview();
	LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
	LODOP.PRINT_DESIGN();
};
</script>


</head>
<body class="list">
<div class="bar">

</div>

<a href="javascript:MyDesign()">打印设计(上线后去除)</a>
<div class="body" id="printControllerDiv">
    <table  id="printControllerTable" border="1" style="border-color:gray" width="100%"  height="100%">
        <tr>
            <td width="100%" colspan="16">
                <object id="LODOP_X" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=550>
                    <param name="Color" value="#d3e6ff">
                    <embed id="LODOP_EM" TYPE="application/x-print-lodop" width=100% height=550  color="#d3e6ff"  PLUGINSPAGE="install_lodop32.exe"></embed>
                </object>
            </td>
        </tr>
    </table>
</div>
<div class="body" id="printLogDiv" style="display:none">
    <table border="0" width="100%"  height="100%">
        <tr>
            <td width="100%" colspan="16">
                打印日志
            </td>
        </tr>
        <tr>
            <td width="100%" colspan="16">
                <textarea  cols=150 rows=25 id="printLogoOutput">
                </textarea>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
