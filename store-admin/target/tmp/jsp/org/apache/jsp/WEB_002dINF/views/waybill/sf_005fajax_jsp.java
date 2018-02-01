package org.apache.jsp.WEB_002dINF.views.waybill;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class sf_005fajax_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("\t<head>\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
      out.write("\t\t<title>Powered By 顺丰打印</title>\n");
      out.write("\t\t<meta name=\"Author\" content=\"360chain Team\" />\n");
      out.write("\t\t<meta name=\"Copyright\" content=\"360chain\" />\n");
      out.write("\t\t<link rel=\"icon\" href=\"favicon.ico\" type=\"image/x-icon\" />\n");
      out.write("\t\t<link rel=\"stylesheet\" type=\"text/css\" href=\"http://127.0.0.1:8000/c_c2.css\" />\n");
      out.write("\t\t<script src='http://127.0.0.1:8000/CLodopfuncs.js'></script>\n");
      out.write("\n");
      out.write("\t</head>\n");
      out.write("\t<body class=\"list\" >\n");
      out.write("\t\t<div class=\"bar\">\n");
      out.write("\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<select id=\"printIndex\"></select>\n");
      out.write("\t\t<button type=\"button\" onclick=\"MyPrint();\" class=\"btn btn-primary\" >\n");
      out.write("\t\t\t点击打印\n");
      out.write("\t\t</button>\n");
      out.write("\t\t<div class=\"body\" id=\"printControllerDiv\" style=\"display:none;\">\n");
      out.write("\t\t\t<table  id=\"printControllerTable\" border=\"1\" style=\"border-color:gray;\" width=\"100%\"  height=\"100%\">\n");
      out.write("\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t<td width=\"100%\" colspan=\"16\">\n");
      out.write("\t\t\t\t\t<object id=\"LODOP_X\" classid=\"clsid:2105C259-1E0C-4534-8141-A753534CB4CA\" width=100% height=650>\n");
      out.write("\t\t\t\t\t\t<param name=\"Color\" value=\"#d3e6ff\">\n");
      out.write("\t\t\t\t\t\t<embed id=\"LODOP_EM\" TYPE=\"application/x-print-lodop\" width=100% height=650  color=\"#d3e6ff\"  PLUGINSPAGE=\"install_lodop32.exe\"></embed>\n");
      out.write("\t\t\t\t\t</object></td>\n");
      out.write("\t\t\t\t</tr>\n");
      out.write("\t\t\t</table>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<div class=\"body\" id=\"printLogDiv\">\n");
      out.write("\t\t\t<table border=\"0\" width=\"100%\"  height=\"100%\">\n");
      out.write("\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t<td width=\"100%\" colspan=\"16\"><h1> 打印日志</h1></td>\n");
      out.write("\t\t\t\t</tr>\n");
      out.write("\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t<td id=\"title\"></td>\n");
      out.write("\t\t\t\t</tr>\n");
      out.write("\t\t\t\t<tr>\n");
      out.write("\t\t\t\t\t<td width=\"100%\" colspan=\"16\">\t\t\t\t\t<textarea  cols=150 rows=25 id=\"printLogoOutput\" style=\"display:none;\">\n");
      out.write("                </textarea></td>\n");
      out.write("\t\t\t\t</tr>\n");
      out.write("\t\t\t</table>\n");
      out.write("\t\t</div>\n");
      out.write("\t\t<script language=\"javascript\" type=\"text/javascript\">\n");
      out.write("\t\t\tvar LODOP;\n");
      out.write("\t\t\t//声明为全局变量\n");
      out.write("\t\t\tvar data = '");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ids}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("';\n");
      out.write("\t\t\tvar ctx = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\n");
      out.write("\t\t\tvar batchCode = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${batchCode}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\n");
      out.write("\t\t\tvar type = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${type}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\n");
      out.write("\t\t\tvar index = 10, t = 0, count = 0;\n");
      out.write("\t\t\tfunction initPrint() {\n");
      out.write("\t\t\t\tCLODOP.Create_Printer_List(document.getElementById('printIndex'));\n");
      out.write("\n");
      out.write("\t\t\t}\n");
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t$(document).ready(function() {\n");
      out.write("\t\t\t\tinitPrint();\n");
      out.write("\t\t\t});\n");
      out.write("\n");
      out.write("\t\t\tfunction MyPrint() {\n");
      out.write("\t\t\t\tvar json = data.split(\",\");\n");
      out.write("\t\t\t\tvar s = json.length;\n");
      out.write("\t\t\t\tvar obj, html, ids = \"\", page = 0;\n");
      out.write("\t\t\t\t$.ajax({\n");
      out.write("\t\t\t\t\turl : ctx + \"/waybill/getSfPrint\",\n");
      out.write("\t\t\t\t\tdata : {\n");
      out.write("\t\t\t\t\t\tids : data,\n");
      out.write("\t\t\t\t\t\tcpCode : 'SF',\n");
      out.write("\t\t\t\t\t\tbatchCode : batchCode,\n");
      out.write("\t\t\t\t\t\ttype : type\n");
      out.write("\t\t\t\t\t},\n");
      out.write("\t\t\t\t\tcache : false,\n");
      out.write("\t\t\t\t\tasync : true,\n");
      out.write("\t\t\t\t\tsuccess : function(data) {\n");
      out.write("\t\t\t\t\t\tif (data && data.code == 200) {\n");
      out.write("\t\t\t\t\t\t\tprintsf(data.data, page, true);\n");
      out.write("\t\t\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t\t\t//String html=data.msg\n");
      out.write("\t\t\t\t\t\t\t//$(html).insertBefore(\"#printLogoOutput\");\n");
      out.write("\t\t\t\t\t\t}\n");
      out.write("\t\t\t\t\t}\n");
      out.write("\t\t\t\t});\n");
      out.write("\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\tfunction printsf(json, seq, isEnd) {\n");
      out.write("\t\t\t\tLODOP.PRINT_INIT(\"顺丰快递特惠电子面单打印\" + seq);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_PAGESIZE(0, \"1000\", \"1500\", \"LodopCustomPage\");\n");
      out.write("\t\t\t\t//设置打印纸张,根据具体要求定尺寸\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_MODE(\"POS_BASEON_PAPER\", true);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_SETUP_BKIMG(\"<img border='0' src='http://admin.wlpost.com/static/waybill/sf.png'/>\");\n");
      out.write("\t\t\t\tLODOP.SET_SHOW_MODE(\"BKIMG_LEFT\", 0);\n");
      out.write("\t\t\t\tLODOP.SET_SHOW_MODE(\"BKIMG_TOP\", 0);\n");
      out.write("\t\t\t\tLODOP.SET_SHOW_MODE(\"BKIMG_WIDTH\", 378);\n");
      out.write("\t\t\t\tLODOP.SET_SHOW_MODE(\"BKIMG_HEIGHT\", 567);\n");
      out.write("\t\t\t\tLODOP.SET_SHOW_MODE(\"BKIMG_PRINT\", true);\n");
      out.write("\t\t\t\tvar printIndex = $(\"#printIndex\").val()\n");
      out.write("\t\t\t\tLODOP.SET_PRINTER_INDEX(printIndex);\n");
      out.write("\n");
      out.write("\t\t\t\tfor ( i = 0; i < json.length; i++) {\n");
      out.write("\t\t\t\t\tcount++;\n");
      out.write("\t\t\t\t\tobj = json[i];\n");
      out.write("\t\t\t\t\thtml = \"<h5>[\" + count + \"]\" + obj.sf_destname + \"已发送到打印机</h5>\";\n");
      out.write("\t\t\t\t\t$(html).insertBefore(\"#printLogoOutput\");\n");
      out.write("\t\t\t\t\tCreateOnePage(obj, count);\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_MODE(\"SF_TASK_NAME\", \"顺丰电子面单\" + seq);\n");
      out.write("\t\t\t\t//为每个打印单独设置任务名\n");
      out.write("\t\t\t\t//LODOP.PRINT();\n");
      out.write("\t\t\t\tLODOP.PREVIEW();\n");
      out.write("\t\t\t}\n");
      out.write("\n");
      out.write("\t\t\tfunction CreateOnePage(obj, seq) {\n");
      out.write("\t\t\t\tLODOP.NewPage();\n");
      out.write("\t\t\t\t//标记E\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 24);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_e\", 22, 195, 61, 40, \"E\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 14);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_count\", 37, 95, 61, 40, seq);\n");
      out.write("\t\t\t\t//上方运单号 条形码\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 10);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_BARCODE(65, 50, 161, 50, \"128C\", obj.sf_orderno);\n");
      out.write("\t\t\t\t//目的地\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_date\", 60, 270, 93, 27, \"顺丰特惠\");\n");
      out.write("\n");
      out.write("\t\t\t\t// LODOP.SET_PRINT_STYLE(\"FontSize\",8);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_address\", 85, 255, 93, 27, \"目的地：\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 24);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_address\", 99, 263, 93, 27, obj.sf_destcode);\n");
      out.write("\n");
      out.write("\t\t\t\t//收货地址\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 10);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_receive\", 136, 15, 80, 27, \"收方：\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_receive\", 136, 50, 295, 27, obj.sf_destaddress);\n");
      out.write("\t\t\t\t//详细地址\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_addressInfo\", 150, 15, 350, 27, obj.sf_destdetailaddress);\n");
      out.write("\t\t\t\t//收货人  和 收货电话号码\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_destname\", 180, 15, 220, 22, obj.sf_destname);\n");
      out.write("\t\t\t\t//代收货款\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_p\", 215, 282, 90, 18, \"\");\n");
      out.write("\n");
      out.write("\t\t\t\t//卡号\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_card\", 234, 255, 80, 22, \"卡号：\");\n");
      out.write("\n");
      out.write("\t\t\t\t//运费\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 10);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_yun\", 253, 255, 90, 18, \"运费：\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_total_price\", 270, 255, 90, 18, \"费用合计：\");\n");
      out.write("\n");
      out.write("\t\t\t\t//同意自取\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_a\", 246, 16, 120, 22, \"同意自取\");\n");
      out.write("\n");
      out.write("\t\t\t\t//转寄协议客户\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_x\", 266, 16, 120, 22, \"转寄协议客户\");\n");
      out.write("\n");
      out.write("\t\t\t\t//下方运单号 条形码\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 10);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_BARCODE(350, 190, 180, 50, \"128C\", obj.sf_orderno);\n");
      out.write("\n");
      out.write("\t\t\t\t//寄件信息\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_jf\", 295, 15, 80, 22, \"寄方：\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 7);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_selleraddress\", 295, 55, 150, 22, obj.sf_selleraddress);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sellerdetail\", 307, 55, 150, 22, obj.sf_sellerdetailaddress);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sellername\", 329, 55, 150, 22, obj.sf_sellername);\n");
      out.write("\n");
      out.write("\t\t\t\t//下寄件方信息\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"l_jf\", 410, 15, 50, 22, \"寄方：\");\n");
      out.write("\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 7);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 0);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sfaddress\", 410, 45, 150, 22, obj.sf_selleraddress);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sfdetail\", 422, 15, 150, 22, obj.sf_sellerdetailaddress);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sfname\", 450, 15, 150, 22, obj.sf_sellername);\n");
      out.write("\n");
      out.write("\t\t\t\t//下收方信息\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(410, 175, 50, 22, \"收方：\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 7);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 0);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_destaddress\", 410, 205, 165, 32, obj.sf_destdetailaddress);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_destname\", 450, 175, 180, 22, obj.sf_destname);\n");
      out.write("\t\t\t\tif (obj.userId == 55) {\n");
      out.write("\t\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\t} else {\n");
      out.write("\t\t\t\t\t//  //数量   寄托物品\n");
      out.write("\t\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\t}\n");
      out.write("\t\t\t\t//LODOP.ADD_PRINT_TEXTA(\"L_X\",482,10,25,22,\"\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"l_x\", 475, 15, 350, 85, obj.sf_selleritems);\n");
      out.write("\n");
      out.write("\t\t\t\t//备注\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_remark\", 482, 300, 50, 30, \"\");\n");
      out.write("\n");
      out.write("\t\t\t\t//合计费用\n");
      out.write("\t\t\t\t//LODOP.SET_PRINT_STYLE(\"FontSize\",7);\n");
      out.write("\t\t\t\t//LODOP.ADD_PRINT_TEXTA(\"l_hj\",525,315,80,20,\"费用合计：\");\n");
      out.write("\n");
      out.write("\t\t\t\t//原寄地CODE\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_jd\", 315, 15, 60, 22, \"原寄地：\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 12);\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"Bold\", 1);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_sellercode\", 325, 15, 60, 22, obj.sf_sellercode);\n");
      out.write("\t\t\t\t//单号\n");
      out.write("\t\t\t\t//LODOP.SET_PRINT_STYLE(\"FontSize\",8);\n");
      out.write("\t\t\t\t//LODOP.SET_PRINT_STYLE(\"Bold\",1);\n");
      out.write("\t\t\t\t//LODOP.ADD_PRINT_TEXTA(\"label_orderno\",525,15,285,22,\"订单号：\");\n");
      out.write("\t\t\t\t//LODOP.SET_PRINT_STYLE(\"FontSize\",6);\n");
      out.write("\t\t\t\t//LODOP.ADD_PRINT_TEXTA(\"s_orderno\",539,15,285,22,obj.sf_selforderno);\n");
      out.write("\n");
      out.write("\t\t\t\t//付款方式\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 9);\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_fs\", 206, 16, 83, 18, \"付款方式:\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_fs\", 206, 86, 83, 18, \"寄付月结 \");\n");
      out.write("\n");
      out.write("\t\t\t\t//月结账号\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_account\", 226, 16, 83, 18, \"月结帐号:\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_account\", 226, 86, 83, 18, \"7312005783\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\n");
      out.write("\t\t\t\t//计费重量\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_weight\", 206, 149, 73, 18, \"计费重量:\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_weight\", 206, 209, 53, 18, \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${trade.weight}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\");\n");
      out.write("\t\t\t\tLODOP.SET_PRINT_STYLE(\"FontSize\", 8);\n");
      out.write("\t\t\t\t//打印时间\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"label_time\", 182, 250, 74, 18, \"打印时间：\");\n");
      out.write("\t\t\t\tLODOP.ADD_PRINT_TEXTA(\"s_time\", 182, 310, 74, 18, obj.sf_date);\n");
      out.write("\t\t\t};\n");
      out.write("\n");
      out.write("\t\t</script>\n");
      out.write("\t</body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }

  private boolean _jspx_meth_c_set_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:set
    org.apache.taglibs.standard.tag.rt.core.SetTag _jspx_th_c_set_0 = (org.apache.taglibs.standard.tag.rt.core.SetTag) _jspx_tagPool_c_set_var_value_nobody.get(org.apache.taglibs.standard.tag.rt.core.SetTag.class);
    _jspx_th_c_set_0.setPageContext(_jspx_page_context);
    _jspx_th_c_set_0.setParent(null);
    _jspx_th_c_set_0.setVar("ctx");
    _jspx_th_c_set_0.setValue((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${pageContext.request.contextPath}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    int _jspx_eval_c_set_0 = _jspx_th_c_set_0.doStartTag();
    if (_jspx_th_c_set_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
      return true;
    }
    _jspx_tagPool_c_set_var_value_nobody.reuse(_jspx_th_c_set_0);
    return false;
  }
}
