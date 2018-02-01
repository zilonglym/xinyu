package org.apache.jsp.WEB_002dINF.views.admin;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class tradeWaitSearch_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_set_var_value_nobody;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_forEach_var_items;
  private org.apache.jasper.runtime.TagHandlerPool _jspx_tagPool_c_if_test;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _jspx_tagPool_c_set_var_value_nobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_forEach_var_items = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _jspx_tagPool_c_if_test = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _jspx_tagPool_c_set_var_value_nobody.release();
    _jspx_tagPool_c_forEach_var_items.release();
    _jspx_tagPool_c_if_test.release();
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

      out.write("\r\n");
      out.write("\r\n");
      if (_jspx_meth_c_set_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("\t<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/scripts/auditTrade.js?v=2.02\" type=\"text/javascript\"></script>\r\n");
      out.write("\t<script type=\"text/javascript\">\r\n");
      out.write("\tvar ctx=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\tfunction auditSfArea(){\r\n");
      out.write("\t//获取选中的ID\r\n");
      out.write("\tvar rows=$('#auditTable').datagrid(\"getChecked\");\r\n");
      out.write("\tif(rows==null || rows==''){\r\n");
      out.write("\t\t$.messager.alert('订单审核','请选择要审核的订单!!');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\tvar ids=\"\";\r\n");
      out.write("\tfor(var i=0;i<rows.length;i++){\r\n");
      out.write("\t\tvar obj=rows[i];\r\n");
      out.write("\t\tids+=obj.val+\",\";\r\n");
      out.write("\t}\r\n");
      out.write("\twindow.open(\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/trade/toAuditArea?tradeIds=\"+ids);\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function auditYUNDAArea(typeStr){\r\n");
      out.write("\t\t$.messager.confirm('自动审核', '是否自动审核'+typeStr+'?', function(r){\r\n");
      out.write("                if (r){\r\n");
      out.write("                   //获取选中的ID\r\n");
      out.write("\t\t\t\t\tvar rows=$('#auditTable').datagrid(\"getChecked\");\r\n");
      out.write("\t\t\t\t\tif(rows==null || rows==''){\r\n");
      out.write("\t\t\t\t\t\t$.messager.alert('订单审核','请选择要审核的订单!!');\r\n");
      out.write("\t\t\t\t\t\treturn;\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\tvar ids=\"\";\r\n");
      out.write("\t\t\t\t\tfor(var i=0;i<rows.length;i++){\r\n");
      out.write("\t\t\t\t\t\tvar obj=rows[i];\r\n");
      out.write("\t\t\t\t\t\tids+=obj.val+\",\";\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\twindow.open(\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/trade/toAuditAreaYUNDA/\"+typeStr+\"?tradeIds=\"+ids);\r\n");
      out.write("                }\r\n");
      out.write("       });\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("function addSplit(){\r\n");
      out.write("\t//获取选中的ID\r\n");
      out.write("\tvar rows=$('#auditTable').datagrid(\"getChecked\");\r\n");
      out.write("\tif(rows==null || rows==''){\r\n");
      out.write("\t\t$.messager.alert('订单拆单核','请选择要拆单的订单!!');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\tif(rows.length!=1){\r\n");
      out.write("\t\t$.messager.alert('订单拆单核','只能对单一订单进行操作!!');\r\n");
      out.write("\t\treturn;\r\n");
      out.write("\t}\r\n");
      out.write("\tvar  id  = rows[0].lgAging ;\r\n");
      out.write("\tvar  tradeId  = rows[0].val ;\r\n");
      out.write("\t$(\"#dialog\").dialog({\r\n");
      out.write("\t\ttitle: '拆单信息',\r\n");
      out.write("\t    width: 900,\r\n");
      out.write("\t    height: 600,\r\n");
      out.write("\t    resizable: true,\r\n");
      out.write("\t    closed: false,\r\n");
      out.write("\t    cache: false,\r\n");
      out.write("\t    draggable: true,\r\n");
      out.write("\t    shadow: true,\r\n");
      out.write("\t    href: ctx+'/trade/toSplitOrder',\r\n");
      out.write("\t    modal: true,\r\n");
      out.write("\t    onLoad:function() {\r\n");
      out.write("\t    \tinitSplitTable(id);\r\n");
      out.write("        },\r\n");
      out.write("\t    buttons: [{\r\n");
      out.write("                    text:'按数量拆单',\r\n");
      out.write("                    iconCls:'icon-ok',\r\n");
      out.write("                    handler:function(){\r\n");
      out.write("                    \tvar rows=$('#splitTable').datagrid(\"getChecked\");\r\n");
      out.write("                 \t\tvar json=\"[\";\r\n");
      out.write("\t\t\t\t\t\tfor(var i=0;i<rows.length;i++){\r\n");
      out.write("\t\t\t\t\t\t\t\tvar  obj = rows[i];\r\n");
      out.write("\t\t\t\t\t\t\t    json=json+\"{\";\r\n");
      out.write("\t\t\t\t\t\t\t\tjson=json+\"detailId:'\"+obj.id+\"',\";\r\n");
      out.write("\t\t\t\t\t\t\t\tjson=json+\"quantity:'\"+$(\"#split\"+obj.id).val()+\"'},\";\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tvar  date = \"{id:\"+tradeId+\",date:\"+json.substr(0,json.length -1) +\"]}\";\r\n");
      out.write("\t\t   \t\t\t\tdate  = JSON.stringify(date); \r\n");
      out.write("\t\t   \t\t\t  \t$.messager.progress({\r\n");
      out.write("\t             \t\t    title: '请稍等',\r\n");
      out.write("\t             \t\t    msg: '数据处理中，请稍等...',\r\n");
      out.write("\t             \t\t    text: '正在处理.......'\r\n");
      out.write("\t             \t\t});\r\n");
      out.write("\t               \t\t$.post(ctx+\"/store/shipOrder/splitShipOrder\",{json:date},function(ret){\r\n");
      out.write("\t               \t\t \t  $.messager.progress('close');\r\n");
      out.write("\t                          $.messager.alert('拆单结果',ret.msg);\r\n");
      out.write("\t                          if(ret.code=='200'){\r\n");
      out.write("\t                             $('#dialog').window('close');\r\n");
      out.write("\t                             audit.searchList();\r\n");
      out.write("\t                          }\r\n");
      out.write("\t                    }); \r\n");
      out.write("\t\t\t\t\t }}, \r\n");
      out.write("\t\t\t\t\t {\r\n");
      out.write("                    text:'按行拆单',\r\n");
      out.write("                    iconCls:'icon-ok',\r\n");
      out.write("                    handler:function(){\r\n");
      out.write("                    \tvar rows=$('#splitTable').datagrid(\"getChecked\");\r\n");
      out.write("                 \t\tvar json=\"[\";\r\n");
      out.write("\t\t\t\t\t\tfor(var i=0;i<rows.length;i++){\r\n");
      out.write("\t\t\t\t\t\t\t\tvar  obj = rows[i];\r\n");
      out.write("\t\t\t\t\t\t\t    json=json+\"{\";\r\n");
      out.write("\t\t\t\t\t\t\t\tjson=json+\"detailId:'\"+obj.id+\"'},\";\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("\t\t\t\t\t\tvar  date = \"{id:\"+tradeId+\",date:\"+json.substr(0,json.length -1) +\"]}\";\r\n");
      out.write("\t\t   \t\t\t\tdate  = JSON.stringify(date); \r\n");
      out.write("\t\t   \t\t\t\t$.messager.progress({\r\n");
      out.write("\t             \t\t    title: '请稍等',\r\n");
      out.write("\t             \t\t    msg: '数据处理中，请稍等...',\r\n");
      out.write("\t             \t\t    text: '正在处理.......'\r\n");
      out.write("\t             \t\t});\r\n");
      out.write("\t               \t\t$.post(ctx+\"/store/shipOrder/splitShipOrderLine\",{json:date},function(ret){\r\n");
      out.write("\t               \t\t \t $.messager.progress('close');\r\n");
      out.write("\t                          $.messager.alert('拆单结果',ret.msg);\r\n");
      out.write("\t                          if(ret.code=='200'){\r\n");
      out.write("\t                             $('#dialog').window('close');\r\n");
      out.write("\t                             audit.searchList();\r\n");
      out.write("\t                          }\r\n");
      out.write("\t                    }); \r\n");
      out.write("\t\t\t\t\t }}, \r\n");
      out.write("\t\t\t\t\t      \r\n");
      out.write("                   {\r\n");
      out.write("                    text:'取消',\r\n");
      out.write("                    handler:function(){\r\n");
      out.write("                        $('#dialog').window('close');\r\n");
      out.write("                    }\r\n");
      out.write("                }]\t\r\n");
      out.write("\t});\r\n");
      out.write("}\r\n");
      out.write("\r\n");
      out.write("function initSplitTable(id){ \r\n");
      out.write("\tvar editRow = undefined;\r\n");
      out.write("\t$('#splitTable').datagrid({\r\n");
      out.write("\t    url:ctx+'/trade/ajax/shipOrderdata',\r\n");
      out.write("\t    height:850,\r\n");
      out.write("\t    queryParams:{\r\n");
      out.write("\t    \t orderId:id\r\n");
      out.write("\t    },\r\n");
      out.write("\t    columns:[[\r\n");
      out.write("\t    \t{field:'id',checkbox:true},\r\n");
      out.write("\t        {field:'title',title:'商品名称',width:400},\r\n");
      out.write("\t        {field:'num',title:'数量',width:200},\r\n");
      out.write("\t        {field:'msg',title:'拆分数量',width:300,\r\n");
      out.write("\t        \tformatter: function(value,row,index){\r\n");
      out.write("\t\t\t\t   return \"<input type='text'  id='split\"+row.id+\"' value=''/>\";\r\n");
      out.write("\t\t\t\t}\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t    ]]\r\n");
      out.write("\t});\r\n");
      out.write("\t}\t\r\n");
      out.write("\t</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"button-bar\" style=\"margin-top:5px;margin-bottom:5px;\">\r\n");
      out.write("\t<form action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/trade/waits/batch\">\r\n");
      out.write("\t\t   \t店铺选择:\r\n");
      out.write("\t\t    <select id=\"selectUser\" name=\"userId\" class=\"easyui-combobox\" style=\"width:190px;\">\r\n");
      out.write("\t\t\t<option value='0'>全部</option> \r\n");
      out.write("\t\t\t");
      if (_jspx_meth_c_forEach_0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t\t</select>\r\n");
      out.write("\t\t\t快递：\r\n");
      out.write("\t\t\t<select id=\"selectCompany\" name=\"company\" class=\"easyui-combobox\" style=\"width:110px;\">\r\n");
      out.write("\t\t\t\t<option value=''>全部</option> \r\n");
      out.write("\t\t\t\t<option value='SF'>顺丰</option>\r\n");
      out.write("\t\t\t\t<option value='YUNDA'>韵达</option> \r\n");
      out.write("\t\t\t\t<option value='YTO'>圆通</option> \r\n");
      out.write("\t\t\t\t<option value='EYB'>EMS经济</option>\r\n");
      out.write("\t\t\t\t<option value='POSTB'>邮政小包</option>\r\n");
      out.write("\t\t\t\t<option value='STO'>申通</option>\t\t\t\t\r\n");
      out.write("\t\t\t\t<option value='HTKY'>汇通</option>\t\t\r\n");
      out.write("\t\t\t</select>\r\n");
      out.write("\t\t<span id=\"city_div\">\r\n");
      out.write("\t\t \t 省份:\r\n");
      out.write("\t\t \t <select id=\"selectState\" name=\"state\" class=\"easyui-combobox\" data-options=\"valueField:'id', textField:'no'\" style=\"width:120px\">\t\r\n");
      out.write("\t\t \t <option value=\"0\">全部</option>\r\n");
      out.write("\t\t\t");
      if (_jspx_meth_c_forEach_1(_jspx_page_context))
        return;
      out.write("\t\t\r\n");
      out.write("\t\t\t</select>\t \r\n");
      out.write("\t   \t</span>\r\n");
      out.write("\t   \t重量\r\n");
      out.write("\t   \t<select name=\"weight_x\" id=\"weight_x\" style=\"width: 50px;\" class=\"easyui-combobox\" >\r\n");
      out.write("\t   \t\t<option value=\">=\">&gt;=</option>\r\n");
      out.write("\t   \t\t<option value=\"=\">=</option>\r\n");
      out.write("\t   \t\t<option value=\"&lt;=\">&lt;= </option>\r\n");
      out.write("\t   \t</select>\r\n");
      out.write("\t   \t <input class=\"easyui-textbox\" id=\"weight\" name=\"weight\" style=\"width: 60px;\" >\r\n");
      out.write("\t\t乡镇村组:\r\n");
      out.write("\t\t\t<select name=\"others\" id=\"others\" style=\"width: 80px;\" class=\"easyui-combobox\" >\r\n");
      out.write("\t\t\t\t<option value=\"0\">所有</option>\r\n");
      out.write("\t\t\t\t<option value=\"1\">包含</option>\r\n");
      out.write("\t\t\t\t<option value=\"2\">不包含</option>\r\n");
      out.write("\t\t\t</select>\r\n");
      out.write("\t\t<input class=\"easyui-datetimebox\" name=\"beigainTime\" id=\"beigainTime\" data-options=\"prompt:'请选择起始时间'\" style=\"width:160px\"/>\r\n");
      out.write("\t\t~\r\n");
      out.write("\t\t<input class=\"easyui-datetimebox\" name=\"lastTime\" id=\"lastTime\" data-options=\"prompt:'请选择截止时间'\" style=\"width:160px\"/>    \r\n");
      out.write("\t    <input id=\"q\" name=\"q\" type=\"text\" style=\"width:150px;\" class=\"easyui-textbox\" data-options=\"prompt:'输入关键字进行查询 ...'\">\r\n");
      out.write("\t   \t<a  id=\"call\" href=\"javascript:audit.searchList();\" class=\"easyui-linkbutton\" data-options=\"iconCls:'icon-search'\">搜索</a>\r\n");
      out.write("\t   \t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"#myModal\" >批量审核</a>\r\n");
      out.write("\t   \t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"#mySplitModal\" >批量拆单</a>\r\n");
      out.write("\t   \t\t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"javascript:auditSfArea();\" >顺风</a>\r\n");
      out.write("\t   \t\t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"javascript:auditYUNDAArea('YUNDA411353');\" >韵达411353</a>\r\n");
      out.write("\t   \t\t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"javascript:auditYUNDAArea('YUNDA');\" >韵达</a>\r\n");
      out.write("\t   \t\t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"javascript:auditYUNDAArea('YTO');\" >圆通</a>\r\n");
      out.write("\t   \t\t<a class=\"easyui-linkbutton\" data-toggle=\"modal\" href=\"javascript:addSplit();\" >自定义拆单</a>\r\n");
      out.write("\t    </span>\r\n");
      out.write("\t    \r\n");
      out.write("\t    \r\n");
      out.write("\t    \r\n");
      out.write("\t</form>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div data-options=\"title:'批量审核'\" style=\"padding:2px;\">\r\n");
      out.write("\t<table id=\"auditTable\">\r\n");
      out.write("\t\t<thead>\r\n");
      out.write("\t\t<tr>\r\n");
      out.write("\t\t<th>商铺</th>\r\n");
      out.write("\t\t<th>支付日期</th>\r\n");
      out.write("\t\t<th>订单来源</th>\r\n");
      out.write("\t\t<th class=\"span3\">寄送地址</th>\r\n");
      out.write("\t\t<th class=\"span4\">购买商品</th>\r\n");
      out.write("\t\t<th class=\"span3\">重量(KG)</th>\r\n");
      out.write("\t\t<th class=\"span3\">备注</th>\t\r\n");
      out.write("\t\t</tr>\r\n");
      out.write("\t\t</thead>\r\n");
      out.write("\t</table>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div>\r\n");
      out.write("\t\t<div id=\"content\"></div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"modal hide fade\" id=\"myModal\">\r\n");
      out.write(" \t\t<div class=\"modal-header\">\r\n");
      out.write("    \t\t<a class=\"close\" data-dismiss=\"modal\">批量审核</a>\r\n");
      out.write("    \t\t<h3>批量审核订单</h3>\r\n");
      out.write("  \t\t</div>\r\n");
      out.write("  \t\t<div class=\"modal-body\">\r\n");
      out.write("    \t\t<p>\r\n");
      out.write("    \t\t<span id=\"tids\"></span>\r\n");
      out.write("            快递公司选择\r\n");
      out.write("            <select name=\"expressCompany\" id=\"expressCompany\">\r\n");
      out.write("\t\t    \t<option value=\"-1\">未选择</option>\r\n");
      out.write("\t\t\t\t");
      if (_jspx_meth_c_forEach_2(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("\t\t    </select>\r\n");
      out.write("\t\t    </p>\r\n");
      out.write("  \t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"btn\" data-dismiss=\"modal\">关闭</a>\r\n");
      out.write("\t    \t<a href=\"javascript:audit.auditTrade();\" class=\"btn btn-primary\">审核通过</a>\r\n");
      out.write("\t  \t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t\r\n");
      out.write("\t<div class=\"modal hide fade\" id=\"mySplitModal\">\r\n");
      out.write(" \t\t<div class=\"modal-header\">\r\n");
      out.write("    \t\t<a class=\"close\" data-dismiss=\"modal\">批量审核</a>\r\n");
      out.write("    \t\t<h3>批量审核订单</h3>\r\n");
      out.write("  \t\t</div>\r\n");
      out.write("  \t\t<div class=\"modal-body\">\r\n");
      out.write("    \t\t<p>\r\n");
      out.write("    \t\t<span id=\"tidsSplit\"></span>\r\n");
      out.write("               快速拆单模式\r\n");
      out.write("            <select name=\"type\" id=\"type\">\r\n");
      out.write("\t\t    \t<option value=\"line\">行</option>\r\n");
      out.write("\t\t    \t<option value=\"count\">数量</option>\r\n");
      out.write("\t\t    </select>\r\n");
      out.write("\t\t    </p>\r\n");
      out.write("\t\t 拆单数量(选择数量拆单模式时)\r\n");
      out.write("\t\t    <select name=\"num\" id=\"num\">\r\n");
      out.write("\t\t    \t<option value=\"1\">1</option>\r\n");
      out.write("\t\t    \t<option value=\"2\">2</option>\r\n");
      out.write("\t\t    \t<option value=\"3\">3</option>\r\n");
      out.write("\t\t    \t<option value=\"4\">4</option>\r\n");
      out.write("\t\t    \t<option value=\"5\">5</option>\r\n");
      out.write("\t\t    \t<option value=\"6\">6</option>\r\n");
      out.write("\t\t    \t<option value=\"7\">7</option>\r\n");
      out.write("\t\t    \t<option value=\"8\">8</option>\r\n");
      out.write("\t\t    \t<option value=\"9\">9</option>\r\n");
      out.write("\t\t    \t<option value=\"10\">10</option>\r\n");
      out.write("\t\t    \t<option value=\"11\">11</option>\r\n");
      out.write("\t\t    \t<option value=\"12\">12</option>\r\n");
      out.write("\t\t    \t<option value=\"16\">16</option>\r\n");
      out.write("\t\t    \t<option value=\"18\">18</option>\r\n");
      out.write("\t\t    \t<option value=\"24\">24</option>\r\n");
      out.write("\t\t    </select>\r\n");
      out.write("  \t\t</div>\r\n");
      out.write("\t\t<div class=\"modal-footer\">\r\n");
      out.write("\t\t\t<a href=\"#\" class=\"btn\" data-dismiss=\"modal\">关闭</a>\r\n");
      out.write("\t    \t<a href=\"javascript:audit.splitTrade();\" class=\"btn btn-primary\">拆单通过</a>\r\n");
      out.write("\t  \t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t <div id=\"dialog\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
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

  private boolean _jspx_meth_c_forEach_0(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_0.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_0.setParent(null);
    _jspx_th_c_forEach_0.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${users}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_forEach_0.setVar("user");
    int[] _jspx_push_body_count_c_forEach_0 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_0 = _jspx_th_c_forEach_0.doStartTag();
      if (_jspx_eval_c_forEach_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t<option value='");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${user.id}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("'  \r\n");
          out.write("\t\t\t\t");
          if (_jspx_meth_c_if_0((javax.servlet.jsp.tagext.JspTag) _jspx_th_c_forEach_0, _jspx_page_context, _jspx_push_body_count_c_forEach_0))
            return true;
          out.write("\r\n");
          out.write("\t\t\t\t>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${user.shopName}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</option>\r\n");
          out.write("\t\t\t");
          int evalDoAfterBody = _jspx_th_c_forEach_0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_0.doFinally();
      _jspx_tagPool_c_forEach_var_items.reuse(_jspx_th_c_forEach_0);
    }
    return false;
  }

  private boolean _jspx_meth_c_if_0(javax.servlet.jsp.tagext.JspTag _jspx_th_c_forEach_0, PageContext _jspx_page_context, int[] _jspx_push_body_count_c_forEach_0)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:if
    org.apache.taglibs.standard.tag.rt.core.IfTag _jspx_th_c_if_0 = (org.apache.taglibs.standard.tag.rt.core.IfTag) _jspx_tagPool_c_if_test.get(org.apache.taglibs.standard.tag.rt.core.IfTag.class);
    _jspx_th_c_if_0.setPageContext(_jspx_page_context);
    _jspx_th_c_if_0.setParent((javax.servlet.jsp.tagext.Tag) _jspx_th_c_forEach_0);
    _jspx_th_c_if_0.setTest(((java.lang.Boolean) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${user.id == userId}", java.lang.Boolean.class, (PageContext)_jspx_page_context, null)).booleanValue());
    int _jspx_eval_c_if_0 = _jspx_th_c_if_0.doStartTag();
    if (_jspx_eval_c_if_0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
      do {
        out.write("\r\n");
        out.write("\t\t\t\t\tselected='selected'\r\n");
        out.write("\t\t\t\t");
        int evalDoAfterBody = _jspx_th_c_if_0.doAfterBody();
        if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
          break;
      } while (true);
    }
    if (_jspx_th_c_if_0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
      _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
      return true;
    }
    _jspx_tagPool_c_if_test.reuse(_jspx_th_c_if_0);
    return false;
  }

  private boolean _jspx_meth_c_forEach_1(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_1.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_1.setParent(null);
    _jspx_th_c_forEach_1.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${stateList}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_forEach_1.setVar("obj");
    int[] _jspx_push_body_count_c_forEach_1 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_1 = _jspx_th_c_forEach_1.doStartTag();
      if (_jspx_eval_c_forEach_1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t<option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${obj.description}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${obj.description}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</option>\r\n");
          out.write("\t\t\t");
          int evalDoAfterBody = _jspx_th_c_forEach_1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_1.doFinally();
      _jspx_tagPool_c_forEach_var_items.reuse(_jspx_th_c_forEach_1);
    }
    return false;
  }

  private boolean _jspx_meth_c_forEach_2(PageContext _jspx_page_context)
          throws Throwable {
    PageContext pageContext = _jspx_page_context;
    JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_forEach_2 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _jspx_tagPool_c_forEach_var_items.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_forEach_2.setPageContext(_jspx_page_context);
    _jspx_th_c_forEach_2.setParent(null);
    _jspx_th_c_forEach_2.setItems((java.lang.Object) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${itemList}", java.lang.Object.class, (PageContext)_jspx_page_context, null));
    _jspx_th_c_forEach_2.setVar("item");
    int[] _jspx_push_body_count_c_forEach_2 = new int[] { 0 };
    try {
      int _jspx_eval_c_forEach_2 = _jspx_th_c_forEach_2.doStartTag();
      if (_jspx_eval_c_forEach_2 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\t\t\t\t\t<option value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${item.value}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write('"');
          out.write('>');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${item.description}", java.lang.String.class, (PageContext)_jspx_page_context, null));
          out.write("</option>\r\n");
          out.write("\t\t\t\t");
          int evalDoAfterBody = _jspx_th_c_forEach_2.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_forEach_2.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_forEach_2[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_forEach_2.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_forEach_2.doFinally();
      _jspx_tagPool_c_forEach_var_items.reuse(_jspx_th_c_forEach_2);
    }
    return false;
  }
}
