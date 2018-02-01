<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>已打印订单列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
	<script src="${base}/scripts/system/admin/waybillOK.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<select id="userId" name="userId" class="easyui-combobox" style="width:120px;">
				<option value="">全部</option>
				<#list users as user>
					<option value="${user.id}">${user.subscriberName}</option>
				</#list>
			</select>
			<select id="sysId" name="sysId" class="easyui-combobox" style="width:120px;">
				<option value="">全部</option>
				<#list itemList as item>
					<option value="${item.value}">${item.description}</option>
				</#list>
			</select>
			<select id="receiveState" name="receiveState" class="easyui-combobox" data-options="valueField:'id', textField:'no'" style="width:80px;">
				<option value="">全部</option>
				<#list stateList as obj>
					<option value="${obj.description}">${obj.description}</option>
				</#list>
			</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:120px;"/>
			~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:120px;"/> 
		<select id="searchType" name="searchType" class="easyui-combobox" style="width:100px;">
					<option value="mobile">电话</option>
					<option value="receiverName">收件人</option>
					<option value="tmsOrderCode">运单号</option>
					<option value="nick">呢称</option>
					<option value="batchCode">批次号</option>
				</select>   
		<input id="searchText" name="searchText" class="easyui-textbox" data-options="prompt:'请输入运单号/姓名/手机/商品名/批次号'" style="width:100px;"></input>
		<input id="txtno" name="txtno" class="easyui-textbox" data-options="prompt:'请输入不包含的型号'" style="width:180px;"></input>
		<a href="javascript:waybill.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
		<a href="#" class="easyui-linkbutton" onclick="printBatchClound(this);" key="${ctx}/express/c_print?type=again" data-options="iconCls:'icon-print'">云打</a>
		<a href="#" class="easyui-linkbutton" onclick="printBatchAjax(this);" key="${ctx}/express/preview?type=print&ids=" data-options="iconCls:'icon-print'">打印</a>
		<a href="#" class="easyui-linkbutton" onclick="printPDF(this);"data-options="iconCls:'icon-print'">小票打印</a>
	</div>
	<div data-options="title:'已打印订单列表'" style="padding:2px;">
		<table id="tb_waybill">
			<thead>
				<tr>
		
				</tr>
			</thead>
		</table>
	</div>	
	<form id="frm" name="frm" method="post" target="_blank" >
			<input type="hidden" id="ids" name="ids" />
			<input type="hidden" id="cpCode" name="cpCode" />
			<input type="hidden" id="cp_code" name="cp_code" />
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
	
	
	function printBatchClound(obj) {
				var url = $(obj).attr("key");
				var ids = "";
				var rows = $("#tb_waybill").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tmsId;
					ids = ids + id + ",";
				}
				var cpCode = $("#sysId").combobox("getValue");
				if (ids == '') {
					$.messager.alert('提示', "请选择要打印订单!");
					return;
				}
				if(cpCode=='' || cpCode==0){
					$.messager.alert('提示', "请选择要打印快递种类!");
					return;
				}
				//url = url + ids + "&cp_code=" + cpCode;
				//window.open(url);
				
				$("#ids").val(ids);
				$("#cp_code").val(cpCode);
				$("#frm").attr("action",url);
				$("#frm").submit();
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
		var rows=$("#tb_waybill").datagrid("getChecked");
		for(var i=0;i<rows.length;i++){
			var id=rows[i].id;
			ids=ids+id+",";
		}
		var waybill=$("#sysId").combobox("getValue");
		if(ids==''){
			alert("请选择要打印订单!");
			return;
		}
	//	url=ctx+"/express/sfAjax?ids="+ids+"&cpCode="+waybill
	//	window.open(url);
	
		url = ctx + "/express/sfAjax?type=again";
				$("#ids").val(ids);
				$("#cpCode").val(waybill);
				$("#frm").attr("action",url);
				$("#frm").submit();
	}
	
	function printPDF(obj){
		var url="";
		var ids="";
		var rows=$("#tb_waybill").datagrid("getChecked");
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
		var rows=$('#tb_waybill').datagrid('getChecked');		
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
	</script>
</body>
</html>
