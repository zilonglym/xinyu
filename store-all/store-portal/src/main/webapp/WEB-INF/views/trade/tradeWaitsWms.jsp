<%@ page contentType="text/html;charset=UTF-8" %> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib prefix="fmt"
uri="http://java.sun.com/jsp/jstl/fmt" %> <%@ taglib prefix="e"
uri="http://www.wlpost.com/jsp/jstl/core" %> <%@ taglib prefix="fn"
uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>电子面单设置</title>
<link href="${ctx}/static/messenger/messenger.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/static/messenger/messenger-theme-future.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/static/messenger/messenger.min.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {

		// 全选事件
		$("#checkAll").click(function() {
			if ($(this).attr("checked") == "checked") {
				$("input[name='trade_select[]']").each(function() {
					$(this).attr("checked", true);
				});
			} else {
				$("input[name='trade_select[]']").each(function() {
					$(this).attr("checked", false);
				});
			}
		});

		$('#submit').bind('click', function(e) {
			
			var expressValue = $('#expressCompany').val();
			if (expressValue == '-1') {
				alert('请选择物流公司');
				return;
			}
			
			var chk_value = [];
			$('input[name="trade_select[]"]:checked').each(function() {
				chk_value.push($(this).val());
			});
			if (chk_value.length == 0) {
				alert('你还没有选择任何订单！');
			} else {
				var arr = new Array();
				var size = 200;
				for (var i = 1, len = chk_value.length; i <= len; i++) {
					arr.push(chk_value[i - 1]);
					if (i % size == 0) {
						postTrade(arr);
						arr = [];
					} else if (i == len) {
						postTrade(arr);
					}
				}
			}
		});
		
		function postTrade(tids) {
			var action = "${ctx}/rest/trade/wms.create?tids=" + tids + "&cp=" + $("#expressCompany").val();
		    $.post(action, function(data){
			    $.globalMessenger().post({message:data,  showCloseButton: true});
			    $("#body").html(data);
		    });
		}

	});
</script>
</head>

<body>
<div id="body">
	<table id="contentTable" class="table table-striped table-condensed">
		<thead>
			<tr>
				<th>来源商铺</th>
				<th>建单时间</th>
				<th>交易订单号</th>
				<th class="span3">收货地址</th>
				<th class="span4">订购商品</th>
				<th class="span3">备注</th>
				<th class="span2"><input type="checkbox" id="checkAll"
					name="checkAll" /> 全选</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${trades}" var="trade">
				<c:if test="${trade.id != null}">
					<tr>
						<td class="span2">${trade.user.shopName}</td>
						<td class="span2"><fmt:formatDate value="${trade.payTime}"
								type="date" pattern="yyyy-MM-dd HH:mm" /></td>
						<td class="span2">${trade.tradeFrom}</td>
						<td class="span3">${trade.receiverState}
							${trade.receiverCity} ${trade.receiverDistrict} <br>
							${trade.receiverAddress}
						</td>
						<td class="span3">${trade.itemTitles}</td>
						<td>买家：${trade.buyerMemo} ${trade.buyerMessage} <br>
							卖家：${trade.sellerMemo}
						</td>
						<td><input type='checkbox' id='trade_select'
							name='trade_select[]' value='${trade.id}' /></td>
					</tr>
				</c:if>
			</c:forEach>
		</tbody>
	</table>

	<div class="row">
		<div class="pull-right">
			<select name="expressCompany" id="expressCompany">
				<option value="-1">快递公司选择</option>
				<c:forEach items="${expressCompanys}" var="e">
					<option value="${e.key}">${e.value}</option>
				</c:forEach>
			</select>
	  		<a id="submit" href="#" class="btn btn-success pull-right">设置电子面单</a>
		</div>
	</div>
</div>
</body>
</html>
