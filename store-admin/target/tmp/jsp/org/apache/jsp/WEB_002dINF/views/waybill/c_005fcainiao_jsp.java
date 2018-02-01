package org.apache.jsp.WEB_002dINF.views.waybill;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class c_005fcainiao_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.Vector _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public Object getDependants() {
    return _jspx_dependants;
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
      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">\r\n");
      out.write("<html>\r\n");
      out.write("\t<head>\r\n");
      out.write("\t\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("\t\t<script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/scripts/cainiao_print.js?t=1\" type=\"text/javascript\"></script>\r\n");
      out.write("\t</head>\r\n");
      out.write("\t<body>\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\t\t<select id=\"printList\" name=\"printList\"></select>\r\n");
      out.write("\r\n");
      out.write("\t\t<input type=\"button\" id=\"BtnPrint\" name=\"BtnPrint\" value=\"打印\" onclick=\"printWeb();\"  />\r\n");
      out.write("\r\n");
      out.write("\t\t<input type=\"button\" onclick=\"doGetPrinters()\"  value=\"刷新打印机列表\"/>\r\n");
      out.write("\r\n");
      out.write("\t\t<input type=\"button\" onclick=\"doPrinterConfig()\"  value=\"当前打印机\"/>\r\n");
      out.write("\t\t<br />\r\n");
      out.write("\t\t<h3>打印批次号:");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${batchCode}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</h3>\r\n");
      out.write("\t\t<h3>此批单据中已打印过的单据有:<span id=\"orderIndex\"></span></h3>\r\n");
      out.write("\t\t<h3>此次打印数量:<span id=\"printIndex\"></span></h3>\r\n");
      out.write("\t\t<h3>总数量:<span id=\"index\"></span></h3>\r\n");
      out.write("\t\t<div id=\"description\"></div>\r\n");
      out.write("\t\t<script>\r\n");
      out.write("\t\t\tvar ids = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ids}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\t\tvar ctx = \"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\t\tvar type=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${type}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\r\n");
      out.write("\t\t\tvar defaultPrinter;\r\n");
      out.write("\t\tfunction printWeb() {\r\n");
      out.write("\t\t\t\tdefaultPrinter = $(\"#printList\").val();\r\n");
      out.write("\t\t\t\t//doSetPrinterConfig(name);//设置打印机\r\n");
      out.write("\t\t\t\t//取电子面单号\r\n");
      out.write("\t\t\t\t//打印\r\n");
      out.write("\t\t\t\t//doPrint();\r\n");
      out.write("\t\t\t\tcainiao.doPrint();\r\n");
      out.write("\t\t\t}\r\n");
      out.write("\t\t</script>\r\n");
      out.write("\t</body>\r\n");
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
}
