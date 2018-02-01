<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易列表</title>
</head>

<body>

	<legend><small>未完成交易</small></legend>
	
	<div class="tab-content">  
		<div class="tab-pane active" id="taobao">        
		<table id="contentTable" class="table table-striped table-condensed"  >
			<thead><tr>
			<th>商铺</th>
			<th>建单时间</th>
			<th>物流方式</th>
			<th>是否次日达\三日达</th>
			<th>收货人</th>
			<th>收货地址</th>
			<th>状态</th>
			<th>操作</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${trades.content}" var="trade">
				<tr>
					<td>${trade.user.shopName} </td>
					<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
					<td>
	                <c:if test="${trade.shippingType == 'free'}">
	                卖家包邮
	                </c:if>
	                <c:if test="${trade.shippingType == 'post'}">
	                平邮
	                </c:if>  
	                <c:if test="${trade.shippingType == 'express'}">
	                快递
	                </c:if> 
	                <c:if test="${trade.shippingType == 'ems'}">
	                EMS
	                </c:if>  
	                <c:if test="${trade.shippingType == 'virtual'}">
	                虚拟发货
	                </c:if> 
	                </td>
	                <td>
	                	<c:if test="${trade.lgAgingType != null}">
	                	${trade.lgAgingType} ${trade.lgAging}
	                	</c:if>
	                	<c:if test="${trade.lgAgingType == null}">
	                	 无要求
	                	</c:if>                	
	                </td>
	                
					<td>${trade.receiverName}</td>
					<td>${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
					 	${trade.receiverAddress}
					</td>
					<td>
						<c:if test="${trade.status == 'TRADE_WAIT_CENTRO_AUDIT'}">
							等待物流通审核			
						</c:if>
						<c:if test="${trade.status == 'TRADE_WAIT_EXPRESS_SHIP'}">
							物流通审核通过 快递配送中
						</c:if>
						<c:if test="${trade.status == 'TRADE_WAIT_BUYER_RECEIVED'}">
							物流通已发货 等待签收
						</c:if>	
					</td>
					<td>
						<a class="btn btn-primary" href="${ctx}/trade/reset/${trade.id}">退回待处理</a>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<!-- -->
		<tags:pagination page="${trades}" paginationSize="15"/> 
		</div>
	
	</div>
</body>
</html>
