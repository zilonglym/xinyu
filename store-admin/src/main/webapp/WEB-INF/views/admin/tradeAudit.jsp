<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>交易订单审核</title>
	<link href="${ctx}/static/styles/step.min.css" type="text/css" rel="stylesheet" />
	<link href="${ctx}/static/styles/prod.css" rel="stylesheet" media="all" />
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-confirm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
		
			$('.confirm').confirm({
				'title' : '订单删除',
				'message' : '确认删除该订单 ？',
			});

			$('.confirm2').confirm({
				'title' : '订单拆分',
				'message' : '确认拆分该订单 ？',
			});			
			
			$("form").submit(function(){  
				$(":submit",this).attr("disabled","disabled");  
			});
		});
	
		function doDelete(id) {
			var action="${ctx}/trade/delete/"+id;
			window.location.href=action;
		}
		
		function doSplit(tradeId, orderId) {
			var action="${ctx}/trade/split/"+tradeId + "/" + orderId;
			window.location.href=action;
		}
		
		function update(){
			if(document.getElementById("buyerNick")!=null){
				document.getElementById("buyerNick").disabled="";
			}
			if(document.getElementById("phone")!=null){
				document.getElementById("phone").disabled="";
			}
			if(document.getElementById("mobile")!=null){
				document.getElementById("mobile").disabled="";
			}
			document.getElementById("receiverName").disabled="";
			document.getElementById("zip").disabled="";
			document.getElementById("state").disabled="";
			document.getElementById("city").disabled="";
			document.getElementById("district").disabled="";
			document.getElementById("address").disabled="";
		}
		
	</script>
</head>

<body>
	<ol class="progtrckr" data-progtrckr-steps="5">
    <li class="progtrckr-done">1.商家确认物流通配送</li>
    <li class="progtrckr-done">2.物流通审核</li>
    <li class="progtrckr-todo">3.快递配送流程</li>
    <li class="progtrckr-todo">4.收货人验收确认</li>
    <li class="progtrckr-todo">5.完成订单交易</li>
	</ol>
	
	<div class="page-header"> 来自淘宝订单：${trade.tradeFrom}</div>
	
	<form id="mkform" action="${ctx}/trade/mkship" method="post">
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message">
	
	<thead><tr>
		<th>买家付款时间</th>	
		<th>收货人(昵称)</th>
		<th>联系方式</th>
		<th colspan=2>收货地址详细信息<a style="display:block;text-align:right; float:right;" href="#" onclick="update()">修改</a></th>
		</tr>
	</thead>
	<tbody><tr>
		<td><fmt:formatDate value="${trade.payTime}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
		<td><input id="receiverName" name="receiverName" style="width:80px;" value="${trade.receiverName}" disabled/><br>
			<c:if test="${trade.buyerNick!=null}">
				<input id="buyerNick" name="buyerNick" style="width:80px;" value="${trade.buyerNick}" disabled/>
			</c:if>
		</td>
		<td>
			<strong>手机:</strong><input id="mobile" name="mobile" style="width:100px;" value="${trade.receiverMobile}" disabled/><br>
			<c:if test="${trade.receiverPhone != ''}">
				<strong>电话:</strong><input id="phone" name="phone" style="width:100px;" value="${trade.receiverPhone}" disabled/> 
			</c:if>
		</td>
		<td>
			<strong>邮编：</strong>
			<input id="zip" name="zip" style="width:100px;" value="${trade.receiverZip}" disabled/><br>  
			<strong>收件详细地址：</strong>
			<input id="state" name="state" style="width:150px;" value="${trade.receiverState}" disabled/>
			<input id="city" name="city" style="width:150px;" value="${trade.receiverCity}" disabled/>
			<input id="district" name="district" style="width:150px;" value="${trade.receiverDistrict}" disabled/> 
			<input id="address" name="address" style="width:350px;" value="${trade.receiverAddress}" disabled/> 
		</td>	
	</tr></tbody>
	</table>
	
	<table class="table optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	<thead><tr>
		<th class="span3">商品编号（条形码）</th>
		<th class="span6">订购商品名称</th>
		<th class="span6">商品重量（单位：KG）</th>
		<th class="span2">订购数量</th>		
		<th class="span4">仓库 - 湘潭高新仓</th>
		<th class="span2">拆分</th>
	</thead>
	<tbody>
	<c:set var="i" value="0" />
	<c:forEach items="${trade.orders}" var="order">
		<tr>
		<td>${order.item.code}</td>
		<td>
			${order.item.title}<br>
			<i class="icon-arrow-right"></i> ${order.item.sku}
			<br>
		</td>
		<td>${order.item.weight}</td>
		<td>${order.num}</td>
		<td>
			<c:if test="${order.stockNum == -1}">
				<span id="err" class="label label-important">
				未关联商品
				</span>
			</c:if>
			<c:if test="${order.stockNum <= 0}">
				<span id="err" class="label label-important">
				无库存
				</span>
			</c:if>
			<c:if test="${order.stockNum > 0}">
				<input type="hidden" name="orders[${i}].num" value="${order.num}">
				<input type="hidden" name="orders[${i}].item.id" value="${order.item.id}">
				<input type="hidden" name="orders[${i}].title" value="${order.item.title}">
				<c:set var="i" value="${i+1}"/>
				${order.item.title} <br>
				<i class="icon-arrow-right"></i>${order.item.sku}
				<span class="label label-success">
				${order.stockNum}件 
				</span>  
				<i class="icon-ok"/>
			</c:if>				
		</td>
		<td>
			<a href="javascript:doSplit(${trade.id}, ${order.id});"  class="confirm2 btn btn-primary ${fn:length(trade.orders) <= 1? "invisible":"visible"}">拆出</a>
		</td>
		</tr>
	</c:forEach>
	</tbody>
	</table>
	
	
	<label></label>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
            <p class="ui-tiptext ui-tiptext-message">
               <strong> 物流方式：</strong> 
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
                &nbsp; &nbsp; &nbsp; &nbsp;
               <strong>运输公司：</strong> 
                <select name="expressCompany">
			    	<option value="-1">未选择</option>
			    	<c:forEach items="${expressCompanys}" var="e">
			    		<option value="${e.key}">${e.value}</option>
			    	</c:forEach>
			    </select>
            </p>
 		</div>
	</div>
	
	<label></label>
	<div class="optEmail-notice ui-tiptext-container ui-tiptext-container-message" >
	    <div class="ui-tiptext-content">
                <p class="ui-tiptext ui-tiptext-message"><span class="ui-tiptext-icon"></span>信息</p>
                <p>卖家:${trade.sellerMemo}</p>
                <p>买家:${trade.buyerMemo}</p>
                <p>买家留言:${trade.buyerMessage}</p>
 		</div>
	</div>
	
	<input type="hidden" name="tradeId" value="${trade.id}"/>
	<div class="form-actions">
		<input id="submit_btn" class="btn btn-success" type="submit"  value="审核通过"/>
		<a href="javascript:doDelete(${trade.id});" class="btn btn-warning confirm">取消订单</a>
		<c:if test="trade.cuid==0">
		<input id="cancel_btn" class="btn" type="button" value="设置仓库" onclick="window.open('${ctx}/trade/toDistOrder?tradeId=${trade.id}')"/>
		</c:if>
		<input id="cancel_btn" class="btn" type="button" value="暂不处理" onclick="history.back()"/>
	</div>
	</form>
</body>
</html>
