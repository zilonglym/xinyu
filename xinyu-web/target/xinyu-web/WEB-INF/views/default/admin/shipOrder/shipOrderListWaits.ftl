<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>取单号订单列表</title>
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
		<script src="${base}/scripts/system/admin/waybill.js?t=1" type="text/javascript"></script>
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
	</head>
	<body>
		<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
			<select id="userId" name="userId" class="easyui-combobox" style="width:120px;">
				<option value="">全部</option>
				<#list users as user>
					<option value="${user.id}">${user.subscriberName}</option>
				</#list>
			</select>
			<select id="sysId" name="sysId" class="easyui-combobox" style="width:80px;">
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
					<option value="item">型号</option>
				</select>
		   
			<input id="searchText" name="searchText" class="easyui-textbox" data-options="prompt:'请输入查询值!'" style="width:100px;">
			</input>
			<input id="txtno" name="txtno" class="easyui-textbox" data-options="prompt:'请输入不包含的型号'" style="width:180px;">
			</input>
			<a href="javascript:waybill.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<a href="#" class="easyui-linkbutton" onclick="printBatchClound(this);" key="${ctx}/express/c_print?ids=" data-options="iconCls:'icon-print'">生成菜鸟单号</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="printBatchAjax(this);" data-options="iconCls:'icon-print'">生成顺丰单号</a>
			<a href="javascript:void(0);" onclick="batchResetAudit();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批量反审</a>
			<a href="javascript:void(0);" onclick="batchPrint();" class="easyui-linkbutton" data-options="iconCls:'icon-print'">打印普通面单</a>
			<a href="javascript:void(0);" onclick="batchDelTrade();" style="display: none;" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批量删除</a>
		</div>
		<div data-options="title:'待打印订单列表'" style="padding:2px;">
			<table id="tb_waybill">
				<thead>
					<tr>

					</tr>
				</thead>
			</table>
		</div>
		<div id="dialog"></div>
		<form id="frm" name="frm" method="post" target="_blank" >
			<input type="hidden" id="ids" name="ids" />
			<input type="hidden" id="cpCode" name="cpCode" />
		</form>
		<script>
			var ctx = "${ctx}";
			
			function batchDelTrade(){
				var rows=$('#tb_waybill').datagrid("getChecked");
				if(rows==null||rows.length<1){
					$.messager.alert('错误','请选择订单！！');
				}else{
					var ids = [];
					for(var i=0; i<rows.length; i++){
						ids.push(rows[i].id);
					}
					$.post(ctx+"/shipOrder/batchDeleteShipOrder",{ids:ids},function(data){
						if(data.ret==1){
							$.messager.alert('提示','订单删除成功!');
							$('#tb_waybill').datagrid('load',{
	   							userId:$("#userId").combobox('getValue'),
	   							sysId:$("#sysId").combobox('getValue'),
	   							searchText:$("#searchText").textbox('getValue'),
	   							txtno:$("#txtno").textbox('getValue'),
	   							receiveState:$("#receiveState").combobox('getValue'),
	   							beigainTime:$("#beigainTime").datetimebox('getValue'),
	   							lastTime:$("#lastTime").datetimebox('getValue')
	 						});
						}else{
							$.messager.alert('提示','订单删除失败!');
						}
					});
				}
			}
			
			function printOne(obj) {
				var url = $(obj).attr("key");
				var ids = $(obj).attr("ttt");
				var waybill = $("#sysId").combobox('getValue');
				url = url + ids + "&cp_code=" + waybill;
				$.post(ctx + "/waybill/ajax/getPrint", {
					ids : ids,
					cp_code : waybill
				}, function(data) {
					if (waybill == 'SF') {
						url = ctx + "/waybill/sfpreview?ids=" + ids
						window.open(url);
					} else {
						var retList = data.retList
						var size = retList.length;
						for (var i = 0; i < size; i++) {
							$.messager.alert('提示', data.retList[i].errorInfo);
						}
						url = url + ids + "&cp_code=" + waybill;
						window.open(url);
					}
				});

			}

			function spilt(obj) {
				var ids = $(obj).attr("ttt");
				var url = ctx + "/store/shipOrder/f7Split?id=" + ids;

				window.open(url);
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
				$.post(ctx + "/express/c_getPrintData", {
					'ids' : ids,
					'cp_code' : cpCode
				}, function(data) {
					if (data && data.ret =='0') {
						ids=data.ids;
						$.messager.alert('取单号提示', data.msg);
					}else{
						$.messager.alert('取单号提示', "取单号成功!");
					}
			
				});
			}
		
			function printBatchAjax(obj) {
				var url = "";
				var ids = "";
				var rows = $("#tb_waybill").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tmsId;
					ids = ids + id + ",";
				}
				var waybill = $("#sysId").combobox("getValue");
				if (ids == '') {
					$.messager.alert('提示', "请选择要打印订单!");
					return;
				}
				if (waybill != 'SF') {
					$.messager.alert('提示', '此处只能打顺丰面单！');
				} else {
					$.post(ctx + "/express/getPrintAjax", {
						'ids' : ids,
						'cp_code' : 'SF'
					}, function(data) {
						if (data && data.code =='200') {
							ids=data.ids;
							$.messager.alert('取单号提示', "取单号成功!");
						}else{
							$.messager.alert('取单号提示', data.msg);
						}
				
					});				
				}
			}

			function batchResetAudit() {				
				var rows=$('#tb_waybill').datagrid("getChecked");
				if(rows==null||rows.length<1){
					$.messager.alert('错误','请选择订单！！');
				}else{
				var ids = [];
				for(var i=0; i<rows.length; i++){
					ids.push(rows[i].id);
				}
				$.post(ctx+"/shipOrder/ajax/resetAudit",{ids:ids},function(data){
					if(data && data.ret=="success"){
						$.messager.alert("提示",data.msg);
						$('#tb_waybill').datagrid('load',{
	   						userId:$("#userId").combobox('getValue'),
	   						sysId:$("#sysId").combobox('getValue'),
	   						searchText:$("#searchText").textbox('getValue'),
	   						txtno:$("#txtno").textbox('getValue'),
	   						receiveState:$("#receiveState").combobox('getValue'),
	   						beigainTime:$("#beigainTime").datetimebox('getValue'),
	   						lastTime:$("#lastTime").datetimebox('getValue')
	 					});
					}else if(data && data.ret=="fail"){
						$.messager.alert("提示",data.msg);
						$('#tb_waybill').datagrid('load', {
			   				userId:$("#userId").combobox('getValue'),
			   				searchText:$("#searchText").textbox('getValue')
						});
					}else{
						$.messager.alert("提示","订单反审失败，请联系系统管理人员！");
						$('#tb_waybill').datagrid('load',{
	   						userId:$("#userId").combobox('getValue'),
	   						sysId:$("#sysId").combobox('getValue'),
	   						searchText:$("#searchText").textbox('getValue'),
	   						txtno:$("#txtno").textbox('getValue'),
	   						receiveState:$("#receiveState").combobox('getValue'),
	   						beigainTime:$("#beigainTime").datetimebox('getValue'),
	   						lastTime:$("#lastTime").datetimebox('getValue')
	 					});
					}
			});
		
		}	
	}		

			function ajaxLoading() {
				$("<div class=\"datagrid-mask\"></div>").css({
					display : "block",
					width : "100%",
					height : $(window).height()
				}).appendTo("body");
				$("<div class=\"datagrid-mask-msg\"></div>").html("正在处理，请稍候。。。").appendTo("body").css({
					display : "block",
					left : ($(document.body).outerWidth(true) - 190) / 2,
					top : ($(window).height() - 45) / 2
				});
			}

			function ajaxLoadEnd() {
				$(".datagrid-mask").remove();
				$(".datagrid-mask-msg").remove();
			}

			function batchDelTrade() {
				var ids = "";
				var rows = $("#tb_shipOrder").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tradeId;
					ids = ids + id + ",";
				}

				$.ajax({
					type : 'POST',
					url : ctx + "/express/batchDeleteTrade",
					data : {
						ids : ids
					},
					beforeSend : ajaxLoading,
					success : function(data) {
						ajaxLoadEnd();
						if (data && data.ret == 1) {
							shipOrder.refresh();
							//window.location.reload();
						} else {
							alert("删除失败,请联系管理员!");
						}

					}
				});
			}


			function batchPrint() {
				var ids = "";
				var rows = $("#tb_waybill").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tmsId;
					ids = ids + id + ",";
				}

				window.open(ctx+"/print/ems?ids="+ids);
			}
			
			$(document).ready(function() {
				$("#sysId").combobox({
					onChange : function() {
						var userId = $("#userId").combobox('getValue');
						var sysId = $("#sysId").combobox('getValue');
						var url = ctx + "/express/tradeBatch/ajax?userId=" + userId + "&sysId=" + sysId;
					/*	$.ajax({
							type : "GET",
							dataType : "json",
							url : url,
							success : function(msg) {
								var tradeBatchs = msg.tradeBatchList;
								if (tradeBatchs != null) {
									$("#tradeBatch").combobox("loadData", tradeBatchs);
								}
							}
						});*/
					}
				});
			});

		</script>
	</body>
</html>
