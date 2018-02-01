
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
 <%@ page contentType="text/html;charset=UTF-8" %>
 <head>
 
 </head>
 <body>
  	<h1><i>欢迎使用中仓物流通系统</i></h1>
  	&nbsp;
  	<h3>操作流程：
  		<h4> 
  		<i><a href="${ctx}/item/add">商品同步</a></i>--->
  		<i><a href="${ctx}/item/list">查看商品</a></i>--->
  		<i><a href="${ctx}/trade/waits">待发货订单</a></i>--->
  		<i><a href="${ctx}/trade/received">仓库已接收</a></i>--->
  		<i><a href="${ctx}/trade/refunds">退款处理</a></i>--->
  		<i><a href="${ctx}/trade/notifys">通知用户签收</a></i>
  		</h4>
  	</h3>
  	<h4><i><a href="${ctx}/item/add">商品同步</a>："同步淘宝数据"同步淘宝商品资料。</i></h4>
  	<h4><i><a href="${ctx}/item/list">查看商品</a>：查看已成功同步的商品信息。</i></h4>
  	<h4><i><a href="${ctx}/trade/waits">待发货订单</a>："同步时间请选择在同一个工作日之内"同步对应时间段的淘宝订单数据，"推送仓库发货"将订单推送到仓库打单发货。</i></h4>
  	<h4><i><a href="${ctx}/trade/received">仓库已接收</a>：查看已成功推送的仓库的订单。</i></h4>
  	<h4><i><a href="${ctx}/trade/refunds">退款处理</a>：查看已退款的订单并进行处理。</i></h4>
  	<h4><i><a href="${ctx}/trade/notifys">通知用户签收</a>："上传淘宝发货"已打印的订单上传发货到淘宝后台。</i></h4>
  	<h4><i><a href="${ctx}/trade/search">订单查询</a>：根据关键字查询特定订单的信息。</i></h4>
  	<h4><i><a href="${ctx}/store/info">库存状态</a>：查看商品系统库存。</i></h4>
  	<h4><i><a href="${ctx}/report/sellout">商品统计</a>：根据时间段统计商品发货信息。</i></h4>
  	<h4><i><a href="${ctx}/report/ship">发货纪录</a>：根据时间段统计发货订单。</i></h4>
 </body>
 </html>
