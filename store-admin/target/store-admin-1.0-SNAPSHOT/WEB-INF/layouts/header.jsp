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
<div id="header" style="background:#B3DFDA;">
	<div id="title">
	    <shiro:user>
	    <div class="easyui-panel" style="padding:0px;">
			<a href="${ctx}/home" class="easyui-menubutton" data-options="menu:'#logout'">物流通[<shiro:principal property="username"/>]</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#storage'" style="font-size: 18px;">库存管理</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#shipOrder'">订单管理</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#qm'">奇门</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#report'">统计报表</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#system'">系统设置</a>
			<a href="#" class="easyui-menubutton" data-options="menu:'#local'">货位管理</a>
		</div>
		
		<div id="storage" style="width:100px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='STORAGE'}">
					<div><a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		
		<div id="shipOrder" style="width:150px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='TRADE'}">
					<div>	<a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
			
		<div id="qm" style="width:100px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='QM'}">
					<div>	<a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		
		<div id="report" style="width:100px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='REPORT'}">
					<div>	<a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
	<div id="system" style="width:100px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='SYSTEM'}">
					<div>	<a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		<div id="local" style="width:100px;">
			<c:if test="${!empty menuList}">
				<c:forEach items="${menuList}" var="obj">
					<c:if test="${obj.menus=='LOCAL'}">
					<div>	<a href="${ctx}/${obj.link}">${obj.title}</a></div>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		<div id="logout" style="width:100px;">
			<div><a href="${ctx}/logout" >退出</a></div>
		</div>	
		</shiro:user>
		</h1>
	</div>
</div>