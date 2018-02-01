<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>拆单</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
	<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
</head>
<body>

	<input  id =  "shipOrderId"  type="hidden" value="${shipOrder.id}"></input>
	<input  type="hidden" value="${shipOrder.orderno}"></input>
	<table cellpadding="5">
	 			<tr>
	    			<td>订单ID</td>
	    			<td>${shipOrder.id}</td>
	    			<td>订单号</td>
	    			<td style="color:red;">${shipOrder.orderno}</td>
	    			<td>&nbsp;</td>
	    		 </tr>
	    		   <tr>
	    			<td>序列</td>
	    			<td>商品名称</td>
	    			<td>实际商品数量</td>
	    			<td>数量</td>
	    		 </tr>
				　<c:forEach var="detail" items="${details}"  varStatus="st">
	    	     <tr key='demo'>
	    			<td><input  type="hidden" data-options="required:true" value="${detail.id}"></input>${st.index+1}</td>
	    			<td><input class="easyui-textbox" disabled="true"  type="text" data-options="required:true" value="${detail.item.title}"></input></td>
	    			<td><input class="easyui-textbox" disabled="true" type="text" data-options="required:true" value="${detail.num}"></input></td>
	    			<td><input class="easyui-textbox" type="text" data-options="required:true" value=""></input></td>
	    		 </tr>
	    		　</c:forEach>
	    	</table>
	    	<!--<input  type="button"  onclick="save()"  value="保存"/>--/>
	</body>
	<script>
	var ctx="${ctx}";
	
	function save(){
			var json="[";
			$("tr[key='demo']").each(function(){
				var row=$(this);
				json=json+"{";
				json=json+"detailId:'"+$($(row).find("td")[0]).find("input").val()+"',";
				json=json+"quantity:'"+$($(row).find("td")[3]).find("input").val()+"'},";
				
			});
			var  date = "{id:"+$("#shipOrderId").val()+",date:"+json.substr(0,json.length -1) +"]}";
		    date  = JSON.stringify(date)
		    var url =    ctx+"/store/shipOrder/splitShipOrder"
		$.ajax({
            type: "POST",
            dataType: "json",
            url: url,
            data: { json: date },
            success: function(msg){
            		alert("添加成功");
            		art.dialog.close();
            	}
			});
		}
	
	</script>
</html>
