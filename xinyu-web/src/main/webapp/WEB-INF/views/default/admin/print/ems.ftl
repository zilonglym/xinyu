<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>EMS普通面单打印</title>
<meta name="Author" content="360chain Team" />
<meta name="Copyright" content="360chain" />
<script src='http://127.0.0.1:8000/CLodopfuncs.js'></script>
<script  language="javascript" type="text/javascript">
var LODOP; //声明为全局变量
    //调用打印预览
    function OpenPreview() {
        LODOP.PRINT_INIT("商品条码打印");
        //LODOP.SET_PRINT_STYLE("FontSize",13);  //设置整体的打印字体
        LODOP.SET_PRINT_PAGESIZE(1,"2000","1600"); //设置打印纸张,根据具体要求定尺寸
		LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
   	    LODOP.NewPage();
   	    
   	      //付款方式 
   	    LODOP.SET_PRINT_STYLE("FontSize",18);
   	    LODOP.ADD_PRINT_TEXT(50,50,470,100,"xxxxxxx");
   	    LODOP.SET_PRINT_STYLE("FontSize",18);
   	    LODOP.ADD_PRINT_TEXT(50,150,390,100,"tttttxxx");
   	    //下方运单号 条形码

   	 	
        //LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","100%");//按整页缩放
        LODOP.SET_SHOW_MODE("HIDE_PAPER_BOARD",true);//隐藏走纸板
       // LODOP.SET_PREVIEW_WINDOW(1,0,0,0,0,""); //隐藏工具条，设置适高显示
        LODOP.SET_SHOW_MODE("PREVIEW_IN_BROWSE",1); //预览界面内嵌到页面内
        LODOP.PREVIEW();
       // LODOP.PRINT_DESIGN();
        blPreviewOpen=true;
    };

</script>


</head>
<body class="list">
<div class="bar">

</div>
<button type="button" onclick="OpenPreview()" class="btn btn-primary" >点击打印</button>
<div class="body" id="printLogDiv" style="">
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
