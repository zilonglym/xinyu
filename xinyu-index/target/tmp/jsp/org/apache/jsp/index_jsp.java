package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=EmulateIE7\" /> \r\n");
      out.write("<title>æ¹åä¸­ä»ç½ç»ç§ææéå¬å¸</title>\r\n");
      out.write("<meta name=\"keywords\" content=\"æ¹åä¸­ä»ç½ç»ç§ææéå¬å¸\" />\r\n");
      out.write("<meta name=\"description\" content=\"\" />\r\n");
      out.write("<link href=\"css/common.css\" rel=\"stylesheet\" type=\"text/css\">\r\n");
      out.write("<!--[if IE 6]>\r\n");
      out.write("<script src='JsCss/iepng.js' type='text/javascript'></script>\r\n");
      out.write("<script type='text/javascript'>\r\n");
      out.write("   EvPNG.fix('*'); \r\n");
      out.write("</script>\r\n");
      out.write("<![endif]-->\r\n");
      out.write("</head>\r\n");
      out.write("<script src=\"js/setimg.js\"></script>\r\n");
      out.write("<script src=\"js/jquery.js\" ></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"js/roll.js\"></script> \r\n");
      out.write("<body>\r\n");
      out.write("\t<div id=\"header\"></div>\r\n");
      out.write("\t<div class=\"Main\"><img src=\"images/t_pic.gif\" align=\"top\" alt=\"\" title=\"\" /></div>\r\n");
      out.write("\r\n");
      out.write("<script>document.getElementById('Link1').className='Sy';</script>\r\n");
      out.write("\t<div class=\"Main\">\r\n");
      out.write("\t\t<div class=\"I_about\">\r\n");
      out.write("\t\t\t<h2 class=\"I_h2\"><strong>å³äºå¬å¸</strong>ABOUT MINNENG</h2>\r\n");
      out.write("\t\t\t<div class=\"clear\"></div>\r\n");
      out.write("\t\t\t<div class=\"I_view\">\r\n");
      out.write("\t\t\t\t<p>");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${description}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("</p>\r\n");
      out.write("\t\t\t</div>        \r\n");
      out.write("            <img name=\"\" src=\"images/guanyugongsi.jpg\" alt=\"\">    \r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"I_news\">\r\n");
      out.write("\t\t\t<h2 class=\"I_h2\">\r\n");
      out.write("\t\t\t\t<div id=\"tab1\">\r\n");
      out.write("\t\t\t\t\t<span onmouseover=\"setTab(1,0)\" class=\"on\" id=\"T1\">æ°é»å¿«è®¯<b>NEWS</b><a href=\"#\" title=\"\"><img src=\"images/more.gif\" alt=\"\" title=\"\" /></a></span>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</h2>\r\n");
      out.write("\t\t\t<div id=\"tablist1\">\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("                    <li><span class=\"Title\"><a href=\"#\" target=\"_blank\">ææéå¢2012å¹´åº¦æ»ç»è¡¨å½°å¤§ä¼æ¨å°¾çæå®´ä¸¾è¡</a></span>2013-1-30</li>\r\n");
      out.write("                    \r\n");
      out.write("                    <li><span class=\"Title\"><a href=\"#\" target=\"_blank\">ææéå¢ä¸ä¸æµ·åå­¦é¢ç­¾çº¦å±å»º</a></span>2013-1-22</li>\r\n");
      out.write("                    \r\n");
      out.write("                    <li><span class=\"Title\"><a href=\"#\" target=\"_blank\">ææéå¢æèµå¹³åå»ºè®¾å°å­¦å©åæè²åå±</a></span>2013-1-5</li>\r\n");
      out.write("                    \r\n");
      out.write("                    <li><span class=\"Title\"><a href=\"#\" target=\"_blank\">ç­çç¥è´ºææéå¢è¢«è¯ä¸º2011å¹´â2012å¹´åº¦ä¸æµ·å¸ç¦å»ºå</a></span>2012-12-10</li>\r\n");
      out.write("                    \r\n");
      out.write("                    <li><span class=\"Title\"><a href=\"#\" target=\"_blank\">ææåçµéå¢æç«\"é¢å£«ä¸å®¶å·¥ä½ç«\"</a></span>2012-10-25</li>\r\n");
      out.write("                    \t\t\t\t\t\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t\t<ul style=\"display:none;\">\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t\t<div class=\"I_right\">\r\n");
      out.write("\t\t\r\n");
      out.write("\t\t\t<h2 class=\"I_h2\"><strong>æ ¸å¿æå¡</strong>CORE SERVOCE</h2>\r\n");
      out.write("\t\t\t<select class=\"Select\" onchange=\"javascript:window.open(this.options[this.selectedIndex].value)\">\r\n");
      out.write("                <option value=\"#\">åæé¾æ¥</option>\t\t\t\t\r\n");
      out.write("                <option value=\"#\">æ·å®ç½</option>                 \r\n");
      out.write("                <option value=\"#\">äº¬ä¸ç½</option>                 \r\n");
      out.write("                <option value=\"#\">å¤©ç«ç½</option>                               \r\n");
      out.write("\t\t\t</select>\t\r\n");
      out.write("\t\t\t<div class=\"Pic1\"><a href=\"http://admin.wlpost.com/home\" title=\"\"><img src=\"images/login.gif\" alt=\"\" title=\"\" /></a></div>\r\n");
      out.write("\t\t\t<div class=\"Pic1\"><a href=\"https://fuwu.taobao.com/ser/detail.html?spm=0.0.0.0.EKS7lz&service_code=FW_GOODS-1974754\" title=\"\"><img src=\"images/order.gif\" alt=\"\" title=\"\" /></a></div>\r\n");
      out.write("\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("    \r\n");
      out.write("    <div class=\"infiniteCarousel\">\r\n");
      out.write("    \t\t<h2 class=\"I_h2\"><strong>æå¡å±ç¤º</strong>SERVICE SHOW</h2>\r\n");
      out.write("            <div class=\"wrapper\">\r\n");
      out.write("                <ul>\r\n");
      out.write("\t\t\t\t\t\r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/cangchu.gif\" width=\"217\" height=\"117\" alt=\"ä»å¨æå¡\" title=\"ä»å¨æå¡\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"ä»å¨æå¡\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/zhuangxie.gif\" width=\"217\" height=\"117\" alt=\"è£å¸æå¡\" title=\"è£å¸æå¡\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"è£å¸æå¡\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/baozhuang.gif\" width=\"217\" height=\"117\" alt=\"åè£æå¡\" title=\"åè£æå¡\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"åè£æå¡\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/fahuo.gif\" width=\"217\" height=\"117\" alt=\"çµååè´§æå¡\" title=\"çµååè´§æå¡\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"çµååè´§æå¡\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/cangchu.gif\" width=\"217\" height=\"117\" alt=\"çç©å¶è¯\" title=\"çç©å¶è¯\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"çç©å¶è¯\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                        <li><div onmouseover=\"this.className='on-div'\" onmouseout=\"this.className=''\"><table cellpadding=\"0\" cellspacing=\"0\"><tr><td width=\"137\" height=\"100\" valign=\"middle\"><img src=\"images/baozhuang.gif\" width=\"217\" height=\"117\" alt=\"ç°ä»£åä¸\" title=\"ç°ä»£åä¸\"/></td></tr></table><a href=\"#\" target=\"_blank\" title=\"ç°ä»£åä¸\"></a></div></li>\r\n");
      out.write("                    \r\n");
      out.write("                </ul>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"Infi_bg\"></div>\r\n");
      out.write("            <a id=\"prev\" class=\"btn_icon\" href=\"javascript:;\"></a>\r\n");
      out.write("            <a id=\"next\" class=\"btn_icon\" href=\"javascript:;\"></a>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("\t<div id=\"footer\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
      out.write("<script type=\"text/javascript\"> \r\n");
      out.write("\r\n");
      out.write("$(function(){  \r\n");
      out.write("\t$('#header').load('header.html');  \r\n");
      out.write("\t$('#footer').load('footer.html');  \r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("$(document).ready(function(){\r\n");
      out.write("\t$(\".Nav ul li a\").mouseover(function() { //When trigger is clicked...\r\n");
      out.write("\t\t//Following events are applied to the subnav itself (moving subnav up and down)\r\n");
      out.write("\t\t$(this).parent().find(\".subnav\").slideDown('fast').show(); //Drop down the subnav on click\r\n");
      out.write("\t\t$(this).parent().hover(function() {\r\n");
      out.write("\t\t}, function(){\t\r\n");
      out.write("\t\t\t$(this).parent().find(\".subnav\").slideUp('slow'); //When the mouse hovers out of the subnav, move it back up\r\n");
      out.write("\t\t});\r\n");
      out.write("\t\t//Following events are applied to the trigger (Hover events for the trigger)\r\n");
      out.write("\t\t})\r\n");
      out.write("});\r\n");
      out.write("\r\n");
      out.write("function setTab(m,n){\r\n");
      out.write("\tvar menu=document.getElementById(\"tab\"+m).getElementsByTagName(\"span\");   //è·åIDä¸ºtab+mæ ç­¾ä¸çliæ ç­¾\r\n");
      out.write("\tvar showdiv=document.getElementById(\"tablist\"+m).getElementsByTagName(\"ul\");   //è·åIDä¸ºtablist+mæ ç­¾ä¸çdivæ ç­¾\r\n");
      out.write("\tfor(i=0;i<menu.length;i++)\r\n");
      out.write("\t{\r\n");
      out.write("\t\tmenu[i].className=i==n?\"on\":\"\";   //å¦æi=n;é£ä¹menu[i].classnameä¸ºnow\r\n");
      out.write("\t\tshowdiv[i].style.display=i==n?\"block\":\"none\";   //å¦æå¦æi=n;é£ä¹showdiv[i].style.displayä¸ºblock\"\r\n");
      out.write("\t}\r\n");
      out.write("\t};\r\n");
      out.write("\t//é¼ æ ç»è¿åæ¢ç¶divèæ¯\r\n");
      out.write("\t$(function(){\r\n");
      out.write("\t\t$(\"#T1\").mouseover( \r\n");
      out.write("\t\t\tfunction () { \r\n");
      out.write("\t\t\t\t$(this).parent(\"div\").removeClass(\"N_li\");  \r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t$(\"#T2\").mouseover( \r\n");
      out.write("\t\t\tfunction () { \r\n");
      out.write("\t\t\t\t$(this).parent(\"div\").addClass(\"N_li\");   //é¼ æ ç»è¿æ¶å¢å æ ·å¼N_li\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t});\r\n");
      out.write("\t//é¼ æ ç»è¿liåæ¢èæ¯ãå­ä½ é¢è²\r\n");
      out.write("\t$(\"#tablist1 li\").hover( \r\n");
      out.write("\t\tfunction () { \r\n");
      out.write("\t\t\t$(this).addClass(\"li_color\");   //é¼ æ ç»è¿æ¶å¢å æ ·å¼P02\r\n");
      out.write("\t\t}, \r\n");
      out.write("\t\tfunction () { \r\n");
      out.write("\t\t\t$(this).removeClass(\"li_color\"); //é¼ æ ç¦»å¼æ¶ç§»é¤æ ·å¼P02\r\n");
      out.write("\t\t}\r\n");
      out.write("\t);\r\n");
      out.write("</script>\r\n");
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
