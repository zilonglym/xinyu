<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@page import="com.graby.store.entity.Centro"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <title>Flat UI</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Loading Bootstrap -->
    <link href="${ctx}/static/flat-ui/css/bootstrap.css" rel="stylesheet">
    <!-- Loading Flat UI -->
    <link href="${ctx}/static/flat-ui/css/flat-ui.css" rel="stylesheet">
    <!-- HTML5 shim, for IE6-8 support of HTML5 elements. All other JS at the end of file. -->
    <!--[if lt IE 9]>
      <script src="${ctx}/static/flat-ui/js/html5shiv.js"></script>
    <![endif]-->
	
  </head>
  <body>
 	<form id="loginForm" action="${ctx}/login" method="post" class="form-horizontal login-form" >
    <div class="container">
    <%
	String error = (String) request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
	if(error != null){
	%>
		<div class="alert alert-error input-medium controls">
			<button class="close" data-dismiss="alert">×</button>登录失败，请重试.
		</div>
	<%
	}
	%>
	
    	<div class="login">
        <div class="login-screen">
          <div class="login-icon">
            <h4>欢迎访问 <small>仓储云后台</small></h4>
          </div>

          <div class="login-form">
            <div class="control-group">
            <table>
            	<tr>
            		<td style="color:black;font-size:16px;">帐号:</td>
            		<td> <input type="text" name="username" value="admin">
            	 </td>
            		</tr>
            	<tr>
            		<td style="color:black;font-size:16px;">密码:</td>
            		<td><input type="password" name="password" value=""></td>
            	</tr>
            	<tr>
            		<td> 
            		
              		
              			</td>
              			
            		<td>
            		<select id="centro_select" name="centro" class="span3">
            			<% 
              				List<Centro> centros  =  (List<Centro>)request.getAttribute("centros");
              			for(int i= 0,size = centros.size();i<size; i ++){
              				Centro  centro = centros.get(i);
              			%>
              				<option value="<%= centro.getId()%>"><%= centro.getName()%></option>
              			<%}%>
            		  
              		</select>
              </td>
            	</tr>
            </table>
              
            </div>
            <input type="button" class="btn btn-primary btn-large btn-block" value=" 登 录 " onclick="loginSubmit()">
          </div>
        </div>
      </div>
 	</div>
	</form>
    <script src="${ctx}/static/jquery/jquery-1.8.3.min.js" type="text/javascript"></script>
    <script>
    var ctx="${ctx}";
    	function loginSubmit(){
    		$.post(ctx+"login/loginf7Submit",{'centro':$("#centro_select").val()},function(data){
    			if(data && data.ret==1){
    				$("#loginForm").submit();
    			}
    		});
    	}
    </script>
  </body>
</html>
