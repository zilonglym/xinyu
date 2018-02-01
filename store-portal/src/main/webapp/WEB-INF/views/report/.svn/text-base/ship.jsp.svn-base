<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<link href="${ctx}/static/bootstrap-daterangepicker/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="all" href="${ctx}/static/bootstrap-daterangepicker/daterangepicker-bs3.css" />
    <script src="${ctx}/static/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <script src="${ctx}/static/bootstrap-daterangepicker/moment.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		function getReport(start, end) {
			var action = "${ctx}/report/ship/list?startDate="+start+"&endDate="+end;
			window.location.href = action;
		}
	</script>
</head>
<body>

    <div class="well" style="overflow: auto">
    
    <c:if test="${not empty orders}">
    	共${total}条记录 
    	<c:if test="${total > 0}">
    		<a href="${ctx}/report/ship/report?startDate=${startDate}&endDate=${endDate}" target="_blank">导出报表(excel)</a>
    	</c:if>
    </c:if>

       <div id="reportrange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 5px; border: 1px solid #ccc">
          发货纪录查询 <i class="glyphicon glyphicon-calendar icon-calendar icon-large"></i>
          <span></span> <b class="caret"></b>
       </div>

       <script type="text/javascript">
       $(document).ready(function() {
          $('#reportrange').daterangepicker(
             {
                startDate: moment().subtract('days', 20),
                endDate: moment(),
                minDate: '2013/01/01',
                maxDate: moment(),
                dateLimit: { days: 100 },
                showDropdowns: true,
                showWeekNumbers: true,
                timePicker: false,
                timePickerIncrement: 1,
                timePicker12Hour: true,
                ranges: {
                   '今天': [moment(), moment()],
                   '昨天': [moment().subtract('days', 1), moment().subtract('days', 1)],
                   '最后7天': [moment().subtract('days', 6), moment()],
                   '最后30天': [moment().subtract('days', 29), moment()],
                   '本月': [moment().startOf('month'), moment().endOf('month')],
                   '上月': [moment().subtract('month', 1).startOf('month'), moment().subtract('month', 1).endOf('month')],
                   '上季度': [moment().subtract('month', 3).startOf('month'), moment().subtract('month', 1).endOf('month')]
                },
                opens: 'left',
                buttonClasses: ['btn btn-default'],
                applyClass: 'btn-small btn-primary',
                cancelClass: 'btn-small',
                format: 'YYYY-MM-DD',
                separator: ' to ',
                locale: {
                    applyLabel: '查询',
                    cancelLabel: '取消',
                    fromLabel: '从',
                    toLabel: '至',
                    customRangeLabel: '自定义区间',
                    daysOfWeek: ['日', '一', '二', '三', '四', '五','六'],
                    monthNames: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
                    firstDay: 1
                }
             },
             function(start, end) {
               console.log("Callback has been called!");
               $('#reportrange span').html(start.format('YYYY/MM/DD') + ' - ' + end.format('YYYY/MM/DD'));
               getReport(start.format('YYYY-MM-DD')+" 00:00", end.format('YYYY-MM-DD')+" 23:59");
             }
          );
          //Set the initial state of the picker label
          //$('#reportrange span').html(moment().subtract('days', 29).format('YYYY/MM/DD') + ' - ' + moment().format('YYYY/MM/DD'));
          <c:if test="${not empty startDate}">
          	$('#reportrange span').html('${startDate}' + ' - ' + '${endDate}');
          </c:if>
          
       });
       </script>

    </div>
	
	<c:if test="${not empty orders}">
		<table id="contentTable" class="table table-striped"  >
			<thead><tr>
			<th>发货日期</th>
			<th>买家昵称</th>
			<th>淘宝订单交易号</th>
			<th>物流公司</th>
			<th>运单号</th>
			<th>收货人</th>
			<th>商品</th>
			</tr></thead>
			<tbody>
			<c:forEach items="${orders.content}" var="order">
				<tr>
					<td><fmt:formatDate value="${order.lastUpdateDate}" type="date" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${order.buyerNick}</td>
					<td>${order.remark}</td>
					<td>${order.expressCompany}</td>
					<td>${order.expressOrderno}</td>
					<td>${order.receiverName}</td>
					<td>
						<c:forEach items="${order.details}" var="detail">
						${detail.item.title}
						<c:if test="${detail.item.sku!=null}">
							(${detail.item.sku})
						</c:if>
						<span class="label label-success">${detail.num}件</span> <br>
						</c:forEach>
					</td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
		<tags:pagination page="${orders}" paginationSize="20"/>
	</c:if>
	
</body>
</html>