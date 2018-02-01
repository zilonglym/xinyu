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
		function getReport() {
			var user = $('#selectUser').val();
			var start = $('#startDate').val();
			var end = $('#endDate').val();
			var action = "${ctx}/report/ship/ajax?userId="+user+"&startDate="+start+"&endDate="+end;
			htmlobj=$.ajax({
			url:action,
			async:true,
			type:"post",
			success: function(msg) {
                 $("#content").html(htmlobj.responseText);
            },
				error: function(XMLHttpRequest, textStatus, errorThrown) {
           	}
			});
		
		}
	</script>
</head>
<body>

	<div class="" style=" padding: 5px 5px;overflow: auto">
		<div class="pull-right" style="padding: 5px 5px; ">
			<a id="submit_sign" class="btn btn-primary" href="javascript:getReport()">查询</a>
		</div>
		<div id="reportrange" class="pull-right" style=" cursor: pointer; padding: 5px 5px; border: 1px solid #ccc">
			<select id="selectUser" name="userId">
				<option value='0'>全部</option> 
				<c:forEach items="${users}" var="user">
					<option value='${user.id}'  
					<c:if test="${user.id == userId}">
						selected='selected'
					</c:if>
					>${user.shopName}</option>
				</c:forEach>
			</select>
	        日期选择 <i class="glyphicon glyphicon-calendar icon-calendar icon-large"></i>
	        <span></span> <b class="caret"></b>
	       	<input id='startDate' type='hidden'/>
	       	<input id='endDate' type='hidden'/>
		</div>
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
                applyLabel: '确定',
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
           $("#startDate").val(start.format('YYYY-MM-DD')+" 00:00");
           $("#endDate").val(end.format('YYYY-MM-DD')+" 23:59");
         }
      );
      //Set the initial state of the picker label
      //$('#reportrange span').html(moment().subtract('days', 29).format('YYYY/MM/DD') + ' - ' + moment().format('YYYY/MM/DD'));
      <c:if test="${not empty startDate}">
      	$('#reportrange span').html('${startDate}' + ' - ' + '${endDate}');
      </c:if>
      
   	});
   	</script>

	<div id="content">
	</div>
	
</body>
</html>