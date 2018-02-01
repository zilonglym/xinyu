<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<style>
	.l-btn-text {
		  display: inline-block;
		  vertical-align: top;
		  width: auto;
		  line-height: 24px;
		  font-size: 15px;
		  padding: 0;
		  margin: 0 4px;
}
</style>
<div id="header">

<div id="title" >
    <shiro:user>
 	    <div class="easyui-panel" style="padding:0px;">
			    <a href="${ctx}/home" class="easyui-menubutton" data-options="menu:'#logout'"><shiro:principal property="shopname"/></a> 
				<a href="#" class="easyui-menubutton" data-options="menu:'#goods'">商品管理</a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#trade'">订单管理</a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#storage'">仓库管理</a>
				<a href="#" class="easyui-menubutton" data-options="menu:'#submenu'">统计查询</a>
		</div>
		<div id="logout" style="width:100px;">
		
		    <div><a href="${ctx}/home">首页</a></div>
			<div><a href="">退出</a></div>
		</div>
		<div id="trade" style="width:100px;">

			<div><a href="${ctx}/trade/waits">待发货订单</a></div>
			<div><a href="${ctx}/trade/search">订单查询</a></div>
			<div><a href="${ctx}/trade/refunds">退款处理</a></div>
			<div><a href="${ctx}/trade/notifys">通知用户签收</a></div>			
			<div><a href="${ctx}/trade/received">仓库已接收</a></div>

		</div>
		<div id="goods" style="width:100px;">
			<div><a href="${ctx}/item/add">商品同步</a></div>
			<div><a href="${ctx}/item/list">我的商品</a></div>
		</div>
		<div id="storage" style="width:100px;">
			<div><a href="${ctx}/store/info">库存状态</a></div>
		</div>
		 <div id="submenu" style="width:100px;">
			<div><a href="${ctx}/report/sellout">商品统计</a></div>
			<div><a href="${ctx}/report/ship">发货纪录</a></div>
		 </div>
	</shiro:user>
</div>
<!--
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
        </div>
    </div>
  </div> /navbar 
 	
  <legend></legend> 
  <br><br>
-->
</div>