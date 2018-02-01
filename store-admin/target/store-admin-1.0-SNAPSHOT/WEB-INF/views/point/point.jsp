﻿﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Powered By 商品条码打印</title>
<meta name="Author" content="360chain Team" />
<meta name="Copyright" content="360chain" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<script src="${ctx}/static/waybill/LodopFuncs.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>

<script  language="javascript" type="text/javascript">
    //调用打印预览
    function OpenPreview() {
        LODOP.PRINT_INIT("商品条码打印");
        //LODOP.SET_PRINT_STYLE("FontSize",13);  //设置整体的打印字体
        LODOP.SET_PRINT_PAGESIZE(1,"1000","600","LodopCustomPage"); //设置打印纸张,根据具体要求定尺寸
		LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
		<c:forEach var="s"  begin="1"  end="${count}">
   	   
   	    LODOP.NewPage();
   	      //付款方式 
   	    LODOP.SET_PRINT_STYLE("FontSize",15);
   	    LODOP.ADD_PRINT_TEXT(5,5,370,18,"${user.shopName}");
   	    LODOP.SET_PRINT_STYLE("FontSize",25);
   	    LODOP.ADD_PRINT_TEXT(30,5,390,100,"${item.title}");
   	    //下方运单号 条形码
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    LODOP.ADD_PRINT_BARCODE(125,55,300,85,"${barCode}","${item.barCode}");
   	 	</c:forEach>
   	 	
        //LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","100%");//按整页缩放
        LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
       // LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,""); //隐藏工具条，设置适高显示
        LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",1); //预览界面内嵌到页面内
        LODOP.SET_SHOW_MODE("NP_NO_RESULT",false);
        LODOP.PREVIEW();
       // LODOP.PRINT_DESIGN();
        blPreviewOpen=true;
    };
/*
var maxPages4preview = 500;//最大预览页数
var LODOP=getLodop(document.getElementById('LODOP_X'),document.getElementById('LODOP_EM')); //声明为全局变量
var blPreviewOpen=true;  //默认为预览
var totalPages =1;//总的打印的页数recordsJSON
if (totalPages> maxPages4preview){ //如果是记录数>maxPages4preview,则不预览
   blPreviewOpen = false;
  }

//如果是IE浏览器并且记录数不超过maxPages4preview打开预览
if (navigator.appVersion.indexOf("MSIE")>=0 && blPreviewOpen)
    OpenPreview();
else
{
    if(blPreviewOpen)  //如果不是IE浏览器,要在预览后手动点击翻页
    {
    	// LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true);
     	//	OpenPreview();
       // LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
		//LODOP.PRINT_DESIGN();
   }
    else
    {
      $("#LODOP_X").height(0);
      $("#LODOP_EM").height(0);
      //此处不能隐藏打印控件，否则在 谷歌浏览器下直接打印会找不到控件
      $("#printControllerTable").height(0);
      $("#printControllerTable").attr("border",0);
      $("#printLogDiv").show(); //显示打印日志输出框
      LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",true);
      printWithouPreview(maxPages4preview);//每50张输出到打印机一次
   }
}
*/
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

	OpenPreview();
    LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
	//LODOP.PRINT_DESIGN();
};
</script>


</head>
<body class="list">
<div class="bar">

</div>
<button type="button" onclick="MyDesign();" class="btn btn-primary" >点击打印</button>
<div class="body" id="printControllerDiv">
    <table  id="printControllerTable" border="1" style="border-color:gray;" width="100%"  height="100%">
        <tr>
            <td width="100%" colspan="16">
                <object id="LODOP_X" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=650>
                    <param name="Color" value="#d3e6ff">
                    <embed id="LODOP_EM" TYPE="application/x-print-lodop" width=100% height=650  color="#d3e6ff"  PLUGINSPAGE="install_lodop32.exe"></embed>
                </object>
            </td>
        </tr>
    </table>
</div>
<div class="body" id="printLogDiv" style="display:none;">
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
