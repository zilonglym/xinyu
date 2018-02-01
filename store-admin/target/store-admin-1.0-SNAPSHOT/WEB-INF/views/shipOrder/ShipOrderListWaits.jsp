<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title>待打印订单列表</title>
		<script src="${ctx}/static/scripts/shipOrder.js?v=1.0.4" type="text/javascript"></script>
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
			<select id="sysId" name="sysId" class="easyui-combobox" style="width:120px">
				<option value="0">全部</option>
				<c:forEach items="${itemList}" var="item">
					<option value="${item.value}">${item.description}</option>
				</c:forEach>
			</select>

			<select id="receiveState" name="tradeBatch" class="easyui-combobox" data-options="valueField:'id', textField:'no'" style="width:120px">
				<option value="0">全部</option>
				<c:forEach items="${stateList}" var="obj">
					<option value="${obj.description}">${obj.description}</option>
				</c:forEach>
			</select>

			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:120px"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:120px"/>
			<input id="q" name="q" class="easyui-textbox" data-options="prompt:'姓名/手机/昵称/批次号'"/>
			
			<input id="txtno" name="txtno" class="easyui-textbox" data-options="prompt:'请输入不包含的型号'">
			
			<a href="javascript:shipOrder.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<!-- <a href="javascript:shipOrder.sort();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批次整理</a> -->
			<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?ids=" data-options="iconCls:'icon-print'">预览打</a>-->
			<a href="#" class="easyui-linkbutton" onclick="printBatchClound(this);" key="${ctx}/waybill/c_print?ids=" data-options="iconCls:'icon-print'">菜鸟取单号</a>
			<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?type=print&ids=" data-options="iconCls:'icon-print'">批量打</a>-->
			<a href="javascript:void(0);" class="easyui-linkbutton" onclick="printBatchAjax(this);" key="${ctx}/waybill/preview?type=print&ids=" data-options="iconCls:'icon-print'">顺丰取单号</a>
			<a href="javascript:void(0);" onclick="batchResetAudit();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批量反审</a>
			<a href="javascript:void(0);" onclick="batchDelTrade();" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">批量删除</a>
		</div>
		<div data-options="title:'待打印订单列表'" style="padding:2px;">
			<table id="tb_shipOrder">
				<thead>
					<tr>

					</tr>
				</thead>
			</table>
		</div>
		<script>
			var ctx = "${ctx}";

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
				$.post(ctx + "/waybill/c_getPrintData", {
					'ids' : ids,
					'cp_code' : waybill
				}, function(data) {
					if (data && data.ret == 0) {
						ids=data.ids;
						$.messager.alert('异常单据提示', data.msg);
					}
					$.messager.alert('提示', "菜鸟取单号完成!");
				});
			}

			function printBatch(obj) {
				var url = $(obj).attr("key");
				var ids = "";
				var rows = $("#tb_shipOrder").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tradeId;
					ids = ids + id + ",";
				}
				var waybill = $("#sysId").combobox("getValue");
				if (ids == '') {
					$.messager.alert('提示', "请选择要打印订单!");
					return;
				}
				$.post(ctx + "/waybill/ajax/getPrint", {
					'ids' : ids,
					'cp_code' : waybill
				}, function(data) {
					if (waybill == 'SF') {
						if (url.indexOf("print") == -1) {
							url = ctx + "/waybill/sfpreview?ids=" + ids
						} else {
							url = ctx + "/waybill/sfpreview?type=print&ids=" + ids
						}
						window.open(url);
					} else {
						url = url + ids + "&cp_code=" + waybill;
						window.open(url);
					}
				});
			}

			function printBatchAjax(obj) {
				var url = "";
				var ids = "";
				var rows = $("#tb_shipOrder").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tradeId;
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
					$.post(ctx + "/waybill/getPrintAjax", {
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
				var ids = "";
				var rows = $("#tb_shipOrder").datagrid("getChecked");
				for (var i = 0; i < rows.length; i++) {
					var id = rows[i].tradeId;
					ids = ids + id + ",";
				}
				$.messager.confirm('提示', '反审后单据状态将发生变化，请确认是否继续？', function(r) {
					if (r) {
						$.post(ctx + "/trade/ajax/resetAuditBatch", {
							ids : ids
						}, function(data) {
							if (data && data.code == 200) {
								//window.location.reload();
								shipOrder.refresh();
							} else {
								$.messager.alert('提示', "反审失败,请联系管理员!");
							}
						});
					}
				});
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
				$.messager.confirm('批量删除确认', '是否确认删单,此操作不可撤消?', function(r){
					if (r){
						$.ajax({
							type : 'POST',
							url : ctx + "/waybill/batchDeleteTrade",
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
				})			
			}


			$(document).ready(function() {
				$("#sysId").combobox({
					onChange : function() {
						var userId = $("#userId").combobox('getValue');
						var sysId = $("#sysId").combobox('getValue');
						var url = ctx + "/wayBill/tradeBatch/ajax?userId=" + userId + "&sysId=" + sysId;
						$.ajax({
							type : "GET",
							dataType : "json",
							url : url,
							success : function(msg) {
								var tradeBatchs = msg.tradeBatchList;
								if (tradeBatchs != null) {
									$("#tradeBatch").combobox("loadData", tradeBatchs);
								}
							}
						});
					}
				});
			});

		</script>
	</body>
</html>
