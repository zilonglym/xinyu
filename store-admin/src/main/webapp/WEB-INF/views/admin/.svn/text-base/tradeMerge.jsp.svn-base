<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>合并交易订单</title>
	<script type="text/javascript">
		function merge() {
		
			var chk_value =[];  
	  		$('input[name="trade_select[]"]:checked').each(function(){  
	   		chk_value.push($(this).val());
	  		});
	  		if (chk_value.length<=1) {
	  			alert('至少选择2笔订单！');
	  			return;
	  		}
			var action = "${ctx}/trade/merge?tradeIds=" + chk_value
			window.location.href = action;
		}
	</script>
</head>

<body>

	<legend></legend>
	
	<table id="contentTable" class="table table-striped table-condensed"  >
		<thead><tr>
		<th>来源商铺</th>
		<th>建单时间</th>
		<th>交易订单号</th>
		<th>物流方式</th>
		<th>收货地址</th>
		<th>商品</th>
		<th>选择</th>
		</tr></thead>
		<tbody>
		<c:forEach items="${trades}" var="trade">
			<c:if test="${trade.id != null}">
			<tr>
				<td class="span2">${trade.user.shopName}</td>
				<td class="span2"><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/> </td>
				<td class="span2">${trade.tradeFrom}</td>
				<td class="span2">
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
				<td class="span3">${trade.receiverState} ${trade.receiverCity} ${trade.receiverDistrict} <br>
				 	${trade.receiverAddress}
				</td>
				<td>${trade.itemTitles}</td>
				<td class="span1">
					<input name="trade_select[]" type="checkbox" value='${trade.id}' /></td>
				</td>
			</tr>
			</c:if>
		</c:forEach>
		</tbody>
	</table>
	
	<div class="pull-right" style="padding: 5px 5px; ">
		<a id="submit_sign" class="btn btn-primary" href="javascript:merge();">执行合并</a>
	</div>
	
</body>
</html>
