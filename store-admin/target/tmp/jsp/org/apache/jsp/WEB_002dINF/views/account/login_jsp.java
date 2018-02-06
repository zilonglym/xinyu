package org.apache.jsp.WEB_002dINF.views.account;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import com.graby.store.entity.Centro;

public final class login_jsp extends org.apache.jasper.runtime.HttpJspBase
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("  <head>\n");
      out.write("    <meta charset=\"utf-8\">\n");
      out.write("    <title>Flat UI</title>\n");
      out.write("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n");
      out.write("    <!-- Loading Bootstrap -->\n");
      out.write("    <link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/flat-ui/css/bootstrap.css\" rel=\"stylesheet\">\n");
      out.write("    <!-- Loading Flat UI -->\n");
      out.write("    <link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/flat-ui/css/flat-ui.css\" rel=\"stylesheet\">\n");
      out.write("    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->\n");
      out.write("    <!--[if lt IE 9]>\n");
      out.write("      <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/flat-ui/js/html5shiv.js\"></script>\n");
      out.write("    <![endif]-->\n");
      out.write("\t\n");
      out.write("  </head>\n");
      out.write("  <body>\n");
      out.write(" \t<form id=\"loginForm\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/login\" method=\"post\" class=\"form-horizontal login-form\" >\n");
      out.write("    <div class=\"container\">\n");
      out.write("    ");

	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	
      out.write("\n");
      out.write("\t\t<div class=\"alert alert-error input-medium controls\">\n");
      out.write("\t\t\t<button class=\"close\" data-dismiss=\"alert\">×</button>登录失败，请重试.\n");
      out.write("\t\t</div>\n");
      out.write("\t");

	}
	
      out.write("\n");
      out.write("\t\n");
      out.write("    \t<div class=\"login\">\n");
      out.write("        <div class=\"login-screen\">\n");
      out.write("          <div class=\"login-icon\">\n");
      out.write("            <h4>欢迎访问 <small>仓储云后台</small></h4>\n");
      out.write("          </div>\n");
      out.write("\n");
      out.write("          <div class=\"login-form\">\n");
      out.write("            <div class=\"control-group\">\n");
      out.write("            <table>\n");
      out.write("            \t<tr>\n");
      out.write("            \t\t<td style=\"color:black;font-size:16px;\">帐号:</td>\n");
      out.write("            \t\t<td> <input type=\"text\" name=\"username\" value=\"admin\">\n");
      out.write("            \t </td>\n");
      out.write("            \t\t</tr>\n");
      out.write("            \t<tr>\n");
      out.write("            \t\t<td style=\"color:black;font-size:16px;\">密码:</td>\n");
      out.write("            \t\t<td><input type=\"password\" name=\"password\" value=\"\"></td>\n");
      out.write("            \t</tr>\n");
      out.write("            \t<tr>\n");
      out.write("            \t\t<td> \n");
      out.write("            \t\t\n");
      out.write("              \t\t\n");
      out.write("              \t\t\t</td>\n");
      out.write("              \t\t\t\n");
      out.write("            \t\t<td>\n");
      out.write("            \t\t<select id=\"centro_select\" name=\"centro\" class=\"span3\">\n");
      out.write("            \t\t\t");
 
              				List<Centro> centros  =  (List<Centro>)request.getAttribute("centros");
              			for(int i= 0,size = centros.size();i<size; i ++){
              				Centro  centro = centros.get(i);
              			
      out.write("\n");
      out.write("              \t\t\t\t<option value=\"");
      out.print( centro.getId());
      out.write('"');
      out.write('>');
      out.print( centro.getName());
      out.write("</option>\n");
      out.write("              \t\t\t");
}
      out.write("\n");
      out.write("            \t\t  \n");
      out.write("              \t\t</select>\n");
      out.write("              </td>\n");
      out.write("            \t</tr>\n");
      out.write("            </table>\n");
      out.write("              \n");
      out.write("            </div>\n");
      out.write("            <input type=\"button\" class=\"btn btn-primary btn-large btn-block\" value=\" 登 录 \" onclick=\"loginSubmit()\">\n");
      out.write("          </div>\n");
      out.write("        </div>\n");
      out.write("      </div>\n");
      out.write(" \t</div>\n");
      out.write("\t</form>\n");
      out.write("    <script src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("/static/jquery/jquery-1.8.3.min.js\" type=\"text/javascript\"></script>\n");
      out.write("    <script>\n");
      out.write("    var ctx=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.evaluateExpression("${ctx}", java.lang.String.class, (PageContext)_jspx_page_context, null));
      out.write("\";\n");
      out.write("    \tfunction loginSubmit(){\n");
      out.write("    \t\t$.post(ctx+\"login/loginf7Submit\",{'centro':$(\"#centro_select\").val()},function(data){\n");
      out.write("    \t\t\tif(data && data.ret==1){\n");
      out.write("    \t\t\t\t$(\"#loginForm\").submit();\n");
      out.write("    \t\t\t}\n");
      out.write("    \t\t});\n");
      out.write("    \t}\n");
      out.write("    </script>\n");
      out.write("  </body>\n");
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
}
