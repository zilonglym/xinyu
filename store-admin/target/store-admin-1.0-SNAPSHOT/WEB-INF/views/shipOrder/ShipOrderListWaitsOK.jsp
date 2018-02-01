<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>已打印订单列表</title>
	<script src="${ctx}/static/scripts/shipOrderOK.js?v=2.0.3" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value="0">全部</option> 
			<c:forEach items="${users}" var="user">
				<c:if test="${user.shopName!=null and user.shopName!=''}">
					<option value="${user.id}">${user.shopName}</option>
				</c:if>
			</c:forEach>
		</select>

		<select id="sysId" name="sysId" class="easyui-combobox">
			<option value="">全部</option>
			<c:forEach items="${itemList}" var="item">
				<option value="${item.value}">${item.description}</option>
			</c:forEach>
		</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
			~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
		<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入运单号/姓名/手机/商品名/批次号'"></input>
		<a href="javascript:shipOrder.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?ids=" data-options="iconCls:'icon-print'">打印预览</a>-->
		<a href="#" class="easyui-linkbutton" onclick="printBatchClound(this);" key="${ctx}/waybill/c_print?type=again" data-options="iconCls:'icon-print'">菜鸟云打</a>
		<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?type=print&ids=" data-options="iconCls:'icon-print'">批量打印</a>-->
		<a href="#" class="easyui-linkbutton" onclick="printBatchAjax(this);" data-options="iconCls:'icon-print'">顺丰打印</a>
		<a href="#" class="easyui-linkbutton" onclick="printPDF(this);"data-options="iconCls:'icon-print'">小票打印</a>
		<a href="javascript:shipOrder.refresh();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">重新加载</a>
	</div>
	<div data-options="title:'已打印订单列表'" style="padding:2px;">
		<table id="tb_shipOrder">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<form id="frm" name="frm" method="post" target="_blank" >
			<input type="hidden" id="ids" name="ids" />
			<input type="hidden" id="cpCode" name="cpCode" />
		</form>
	<script>
	var ctx="${ctx}"; 	
	function cancelOne(_id){
		var url = ctx+"/wayBill/ajax/cancel"
		$.ajax({
        type: "POST",
        	dataType: "json",
       		url: url,
       		data: {id:_id},
       		 success: function(msg){
       		 	if(msg.code==200){
        			alert("取消成功");
        		}else{
        			alert(msg.flag);
        		}
        		window.location.reload();
        	}
		});
	}	
	function printOne(obj){
		var url=$(obj).attr("key");
		var ids=$(obj).attr("ttt");
		var waybill=$("#sysId").combobox("getValue");
		url=url+ids+"&cp_code="+$(obj).attr("cp_code");
		window.open(url);
	}
	
	function printBatchAjax(obj){
		var url="";
		var ids="";
		var rows=$("#tb_shipOrder").datagrid("getChecked");
		for(var i=0;i<rows.length;i++){
			var id=rows[i].id;
			ids=ids+id+",";
		}
		var waybill=$("#sysId").combobox("getValue");
		if(ids==''){
			alert("请选择要打印订单!");
			return;
		}
		url=ctx+"/waybill/sfAjax?type=again&ids="+ids+"&cpCode="+waybill
		window.open(url);
	}
	
	function printPDF(obj){
		var url="";
		var ids="";
		var rows=$("#tb_shipOrder").datagrid("getChecked");
		for(var i=0;i<rows.length;i++){
			var id=rows[i].id;
			ids=ids+id+",";
		}
		var waybill=$("#sysId").combobox("getValue");
		if(ids==''){
			alert("请选择要打印订单!");
			return;
		}
		url= ctx+"/trade/send/pick/report?type=minPickReport&ids="+ids;
		window.open(url);
	}
	
	function printBatch(obj){
		var rows=$('#tb_shipOrder').datagrid('getChecked');		
		var url=$(obj).attr("key");
		var ids="";
		for(var i=0;i<rows.length;i++){
			var id=rows[i].tradeId;
			ids=ids+id+",";
		}
		var waybill=$("#sysId").val();
		url=url+ids;
		window.open(url);
		
	}
	
		function printBatchClound(obj) {
				var url = $(obj).attr("key");
				var ids = "";
				var rows = $("#tb_shipOrder").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].id;
					ids = ids + id + ",";
				}
				var waybill = $("#sysId").combobox("getValue");
				if (ids == '') {
					$.messager.alert('提示', "请选择要打印订单!");
					return;
				}
				//url = url + ids + "&cp_code=" + waybill;
				$("#ids").val(ids);
				$("#cpCode").val(waybill);
				$("#frm").attr("action",url);
				$("#frm").submit();
			}

	</script>
</body>
</html>
