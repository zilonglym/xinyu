<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>拣货单</title>
	<script src="${ctx}/static/scripts/pick.js?v=2.0.4" type="text/javascript"></script>
	<script  type="text/javascript">
	$(function() {
		
	    // 重置为运单打印状态
	   $('#btn_express').bind('click', function (e) {
	   		var chk_value =[];  
		  		$('input[name="order_select[]"]:checked').each(function(){  
		   		chk_value.push($(this).val());  
	  		});  
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/express?ids=" + chk_value;
	  			window.location.href=action;
	  		}
	   });
	   
	   // 拣货单打印（批量）
	   $('#btn_pick_batch').bind('click', function (e) {
		    var rows = $('#tb_pick').datagrid('getChecked'); 
		    var chk_value =[]; 
		    for(var i=0;i<rows.length;i++){
		    	var row=rows[i];
		    	var id=row.id;
		    	chk_value.push(id); 
		    }
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/pick/report?type=batchPickReport&ids=" + chk_value;
	  			window.open(action);
	  		}
	   });
	   
	   // 拣货单打印（分拣）
	   $('#btn_pick').bind('click', function (e) {
	   	  	var rows = $('#tb_pick').datagrid('getChecked'); 
		    var chk_value =[]; 
		    for(var i=0;i<rows.length;i++){
		    	var row=rows[i];
		    	var id=row.id;
		    	chk_value.push(id); 
		    }
	  		if (chk_value.length==0) {
	  			alert('你还没有选择任何货单！');
	  		} else {
	  			var action = "${ctx}/trade/send/pick/report?type=minPickReport&ids=" + chk_value;
	  			window.open(action);
	  		}
	   });
	});
	</script>	
</head>

<body>
	<div class="row" id="button-bar" style="margin-top: 5px;margin-bottom: 5px;">
		<select id="selectUser" class="easyui-combobox" name="selectUser" style="width:200px;">
			<c:if test="${user.id!=null and user.shopName!=''}">
				<option value="${user.id}">${user.shopName}</option>
			</c:if>
			<option value="">全部</option>
			<c:forEach items="${users}" var="user">
				<c:if test="${user.shopName!=null and user.shopName!=''}">
					<option value="${user.id}">${user.shopName}</option>
				</c:if>
			</c:forEach>	
		</select>
		<select id="waybill" class="easyui-combobox" name="waybill" style="width:200px;">
			<option value="">全部</option>
			<c:forEach items="${waybills}" var="waybill">		
				<option value="${waybill.value}">${waybill.description}</option>
			</c:forEach>	
		</select> 	
		<a href="javascript:pick.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	  	<a id="btn_pick_batch" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印汇总拣货单</a>
	  	<a id="btn_pick" href="#" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印合并拣货单</a>
	  	<a href="javascript:pick.submit();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">确认已拣货</a>
	</div>
	
	<div data-options="title:'批量拣货列表'" style="padding:2px;">
		<table id="tb_pick"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	</script>
</body>
</html>
