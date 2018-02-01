<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<script src="${ctx}/static/bootstrap-plugin-js/bootstrap-tab.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
	<script src="${ctx}/static/jquery-validation/1.10.0/jquery.validate.ext.js" type="text/javascript"></script>
	<link href="${ctx}/static/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />	
	<script>
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#item_title").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate();
			// 同步按钮
			$('#btn_sycn').click(function () {
	        	var btn = $(this);
		        btn.button('loading');
		        $('#sync_result').html(" loading ....请勿关闭");
		        sync();
		    });			
		});
		
		function sync() {
			$.ajax({ 
                type: 'get', 
                url: '${ctx}/rest/item/sync',
                success: function() {
                	$('#btn_sycn').button('reset');
                	$('#sync_result').html("同步成功");
                },
                error:function() {
	                $('#btn_sycn').button('reset');
	                $('#sync_result').html("同步失败");
                }
        	}); 
		}

	</script>
</head>
<body>
    <div class="tab-content"  >
		<!-- 淘宝商品导入 -->
		<div class="tab-pane active" id="import" style="margin-top:50px;">
			<div class="container">
				<button id="btn_sycn" data-loading-text="数据同步中..." class="btn btn-primary btn-large">
                    同步淘宝数据
                 </button>
                 <label id="sync_result"></label>
			</div>
		</div>
	</div>
</body>
</html>