<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<script src="${ctx}/static/util/jquery.cityselect.js" type="text/javascript"></script>
	<script type="text/javascript">
	$(function() {
		
		// 全选事件
	   	$("#checkAll").click(function() {
	   		if($(this).attr("checked") == "checked") {
	   			$("input[name='order_select[]']").each(function() {
	            	$(this).attr("checked", true);
	        	});
	   		} else {
	   			$("input[name='order_select[]']").each(function() {
	            	$(this).attr("checked", false);
	        	});
	   		}
		});
		
	});
	
	$(document).ready(function() {
		$("#city_div").citySelect({
			url:${cityJson},
			prov:"",
			city:"",
			dist:"",
			required:false
		});
		
		$("#call").click(function() {
	   		var action = "${ctx}/trade/ajax/waits?" + 
	   			"userId=" + $("#selectUser").val() + 
	   			"&state=" + $("#selectState").val() +
	   			"&city=" + $("#selectCity").val() +
	   			"&itemTitle=" + $("#itemTitle").val();
	   		htmlobj=$.ajax({
				url:action,
				async:true,
				type:"post",
				success: function(msg) {
					$("#content").empty();
	                $("#content").html(htmlobj.responseText);
	            },
					error: function(XMLHttpRequest, textStatus, errorThrown) {
					$("#content").html(htmlobj.responseText);
	           }
			});
		});
	
	});
	
	</script>

</head>
<body>
	<legend></legend>
	
	<div class="well" style=" padding: 5px 5px 5px 5px;overflow: auto">
	<form action="${ctx}/trade/waits/batch">
	    <span>
		   	店铺选择 <select id="selectUser" name="userId">
			<option value='0'>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
			</select>
		</span>
			
		<span id="city_div">
		 	 城市选择
		 	<select id="selectState" name="state" class="prov span2"></select>
			<select id="selectCity" name="city" class="city span2" disabled="disabled"></select>
	   	</span>
	    	
	   	<span>
	   		 商品名称
	    	<input id="itemTitle" name="itemTitle" type="text" name="itemTitle" placeholder="输入商品名称查询 ...">
	    </span>
	    	
	    <div  class="pull-right" style="padding: 5px 5px; ">
	    	<a id="call" href="#" class="btn btn-primary">搜索</a>
	    </div>
	    
	</form>
	
	</div>
	
	
	<div>
		<div id="content">
	</div>
	
</body>
</html>