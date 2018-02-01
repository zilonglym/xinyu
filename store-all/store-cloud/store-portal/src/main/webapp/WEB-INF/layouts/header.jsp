<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div id="header">
<div class="navbar  navbar-fixed-top">
    <div class="navbar-inner">
        <div class="nav-collapse">
          <ul class="nav">
            <li><a  href="#"> &nbsp;&nbsp;<strong>仓储云</strong>&nbsp;电商仓储配送中心</a></li>
            <li></li>
            <li class="active"><a href="#"><i class="icon-user"></i>&nbsp;<shiro:principal property="shopname"/></a></li>
          </ul>
          <ul class="nav pull-right">
	        <ul class="nav">
	        <li ><a target="_blank" href="http://www.taobao.com/webww/ww.php?ver=3&touid=tang7987&siteid=cntaobao&status=1&charset=utf-8" ><img border="0" src="http://amos.alicdn.com/online.aw?v=2&uid=tang7987&site=cntaobao&s=1&charset=utf-8" alt="点击这里给我发消息" /></a>
            </ul>          
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">功能 <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">设置</a></li>
                <li class="divider"></li>
                <li><a href="#">退出</a></li>
              </ul>
            </li>
          </ul>
        </div><!-- /.nav-collapse -->
    </div><!-- /navbar-inner -->
  </div><!-- /navbar -->
 	
  <legend></legend> 
  <br><br>

</div>