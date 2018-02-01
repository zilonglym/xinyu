<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出库单列表</title>
</head>

<body>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success"><button data-dismiss="alert" class="close">×</button>${message}</div>
	</c:if>
	<br>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>序号</th>
		<th>出库单号</th>
		<th>业务类型</th>
		<th>运输公司运单号</th>
		<th>运输公司</th>
		<th>总件数</th>
		<th>创建日期</th>
		<th>状态</th>
		<th>操作</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${orders}" var="order" varStatus="i">
			<tr>
				<td>${i.index+1}</td>
				<td style="vertical-align: middle;">${order.orderno}</td>
				<td style="vertical-align: middle;">${order.orderType}</td>
				<td style="vertical-align: middle;">${order.expressOrderno}</td>
				<td style="vertical-align: middle;">${order.expressCompany}</td>
				<td style="vertical-align: middle;">${order.totalnum}</td>
				<td style="vertical-align: middle;"><fmt:formatDate value="${order.createDate}" type="date" pattern="yyyy-MM-dd"/></td>
				<td style="vertical-align: middle;">	
				<c:if test="${order.status == 'ENTRY_WAIT_SELLER_SEND'}">
					待发送 <a href="">发送</a>
				</c:if>
				<c:if test="${order.status == 'ENTRY_WAIT_STORAGE_RECEIVED'}">
					已发送
				</c:if>
				<c:if test="${order.status == 'ENTRY_FINISH'}">
					已接收
				</c:if>		
				</td>
				<td style="vertical-align: middle;">
					<a href="${ctx}/store/shipOrder/toConfirmDelivery/${order.id}">确认发货</a>
					<a href="${ctx}/store/shipOrder/detailInfo/${order.id}">查看明细</a>
					<a href="${ctx}/store/shipOrder/toItemLack/${order.id}">缺货通知</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
</body>
</html>
