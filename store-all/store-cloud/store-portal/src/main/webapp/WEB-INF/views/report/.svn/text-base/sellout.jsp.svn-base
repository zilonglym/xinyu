<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<link href="${ctx}/static/bootstrap-daterangepicker/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" media="all" href="${ctx}/static/bootstrap-daterangepicker/daterangepicker-bs3.css" />
    <script src="${ctx}/static/bootstrap-daterangepicker/daterangepicker.js" type="text/javascript"></script>
    <script src="${ctx}/static/bootstrap-daterangepicker/moment.js" type="text/javascript"></script>
	<script type="text/javascript">
		
		function getReport(start, end) {
			var action = "${ctx}/report/sellout/ajax/sum?start="+start+"&end="+end;
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

    <div class="well" style="overflow: auto">

       <div id="reportrange" class="pull-right" style="background: #fff; cursor: pointer; padding: 5px 5px; border: 1px solid #ccc">
          售出商品查询   <i class="glyphicon glyphicon-calendar icon-calendar icon-large"></i>
          <span></span> <b class="caret"></b>
       </div>

       <script type="text/javascript">
       $(document).ready(function() {
          $('#reportrange').daterangepicker(
             {
                startDate: moment().subtract('days', 29),
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
          $('#reportrange span').html(moment().subtract('days', 29).format('YYYY/MM/DD') + ' - ' + moment().format('YYYY/MM/DD'));
       });
       </script>

    </div>

	<div id="content">
	</div>
	
</body>
</html>