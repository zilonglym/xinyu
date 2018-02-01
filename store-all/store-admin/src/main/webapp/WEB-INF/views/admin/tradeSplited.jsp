<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
</head>
<body id="body">
	
	<table id="contentTable" class="table table-condensed"  >
		<thead><tr>
		<th>来源商铺</th>
		<th>建单时间</th>
		<th>交易订单号</th>
		<th class="span3">收货地址</th>
		<th class="span4">订购商品</th>
		<th class="span3">合并处理</th>	
		</tr></thead>
		<tbody>
		<c:forEach items="${trades}" var="trade">
			<c:if test="${trade.id != null}">
			<tr>
				<td class="span2">${trade.user.shopName}</td>
				<td class="span2"><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
				<td class="span2">${trade.tradeFrom}</td>
				<td class="span3">${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
				 	${trade.receiverAddress}
				</td>
				<td class="span3">${trade.itemTitles}</td>
				<td class="span3">
					<a href="${ctx}/trade/merge/${trade.mergeHash}" class="btn btn-primary">合并</a>
				</td>			
			</tr>
			</c:if>
		</c:forEach>
		</tbody>
	</table>
	 
	<div id="loadingDiv" class="hint hide">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>
	
</body>
</html>
