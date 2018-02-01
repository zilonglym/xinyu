<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<div id="leftbar">
	<h1>库存管理</h1>
	<div class="submenu">
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="obj">
				<c:if test="${obj.menus=='STORAGE'}">
					<a href="${ctx}/${obj.link}">${obj.title}</a>
				</c:if>
			</c:forEach>
		</c:if>
		<!-- <a href="${ctx}/stock">库存状态</a>
		<a href="${ctx}/entry">待处理入库单</a>
		
		<a href="${ctx}/stock/position">库位管理</a>
		 -->
	</div>
	<h1>订单管理</h1>
	<div class="submenu" style="width: 150px;">
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="obj">
				<c:if test="${obj.menus=='TRADE'}">
					<a href="${ctx}/${obj.link}">${obj.title}</a>
				</c:if>
			</c:forEach>
		</c:if>
		<!--
		<a href="${ctx}/trade/waits">代发订单处理</a>
		<a href="${ctx}/trade/splited">已拆分订单合并</a>
		<a href="${ctx}/trade/waits/search">交易订单审核(批量)</a>
		<a href="${ctx}/trade/unfinish">订单重置（待发）</a>
		<a href="${ctx}/wayBill/waits">面单打印</a>
		<a href="${ctx}/trade/send/waits">快递运单设置</a>
		<a href="${ctx}/trade/send/pickings">批量拣货处理</a>
		<a href="${ctx}/trade/ship/audit">运单出库审核</a>
		<a href="${ctx}/trade/unfinish">订单重置（待发）</a>
		<a href="${ctx}/wayBill/waits">面单打印</a>
		
		<a href="${ctx}/waybill/sfpreview">电子面单打印</a>
		<!--
		<a href="${ctx}/trade/sign/waits">用户签收处理</a>
		<a href="${ctx}/wayBill/waitsOk">面单重新打印</a>
		-->
	</div>
	<h1>奇门</h1>
	<div class="submenu">
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="obj">
				<c:if test="${obj.menus=='QM'}">
					<a href="${ctx}/${obj.link}">${obj.title}</a>
				</c:if>
			</c:forEach>
		</c:if>
		<!--<a href="${ctx}/store/entry/list">发货入库</a>
		<a href="${ctx}/store/shipOrder/entryOrderList">入库单列表</a>
		<a href="${ctx}/store/entry/confirm/entryOrderList">入库确认</a>
		<a href="${ctx}/store/entry/confirm/returnorderList">退货入库确认</a>
		<a href="${ctx}/store/shipOrder/stockoutList">出库单列表</a>
		<a href="${ctx}/store/entry/confirm/stockoutList">出库确认</a>
		<a href="${ctx}/store/shipOrder/deliveryorderList">发货列表</a>
		<a href="${ctx}/store/entry/list">仓内加工</a>
		<a href="${ctx}/store/entry/list">库存盘点</a>-->
	</div>
	<h1>统计报表</h1>
	<div class="submenu">
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="obj">
				<c:if test="${obj.menus=='REPORT'}">
					<a href="${ctx}/${obj.link}">${obj.title}</a>
				</c:if>
			</c:forEach>
		</c:if>
		<a href="${ctx}/report/inventoryRecord">出入库统计</a>
		<a href="${ctx}/report/inventory">库存统计</a>
	</div>
	<h1>系统设置</h1>
	<div class="submenu">
		<a href="${ctx}/sys/sysUserRolesRow">权限设置</a>
		<c:if test="${!empty menuList}">
			<c:forEach items="${menuList}" var="obj">
				<c:if test="${obj.menus=='SYSTEM'}">
					<a href="${ctx}/${obj.link}">${obj.title}</a>
				</c:if>
			</c:forEach>
		</c:if>
		<!-- <a href="${ctx}/report/ship">系统变量设置</a>-->
	</div>
</div>
