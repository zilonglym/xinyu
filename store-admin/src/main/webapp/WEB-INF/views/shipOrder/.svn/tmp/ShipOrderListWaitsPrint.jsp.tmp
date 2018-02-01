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
		<script src="${ctx}/static/scripts/shipOrderPrint.js?v=1.0.1" type="text/javascript"></script>
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
				<option value="">全部</option>
				<c:forEach items="${itemList}" var="item">
					<option value="${item.value}">${item.description}</option>
				</c:forEach>
			</select>

			<select id="receiveState" name="receiveState" class="easyui-combobox" style="width:120px">
				<option value="">全部</option>
				<c:forEach items="${stateList}" var="obj">
					<option value="${obj.description}">${obj.description}</option>
				</c:forEach>
			</select>

			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:120px"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:120px"/>
			<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入运单号/姓名/手机/商品名/批次号'">
			</input>
			<input id="txtno" name="txtno" class="easyui-textbox" data-options="prompt:'请输入不包含的型号'">
			</input>
			<a href="javascript:shipOrder.search();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
			<!-- <a href="javascript:shipOrder.sort();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批次整理</a> -->
			<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?ids=" data-options="iconCls:'icon-print'">预览打</a>-->
			<a href="#" class="easyui-linkbutton" onclick="printBatchClound(this);" key="${ctx}/waybill/c_print" data-options="iconCls:'icon-print'">云打</a>
			<!--<a href="#" class="easyui-linkbutton" onclick="printBatch(this);" key="${ctx}/waybill/preview?type=print&ids=" data-options="iconCls:'icon-print'">批量打</a>-->
			<!--<a href="javascript:void(0);" class="easyui-linkbutton" onclick="printBatchAjax(this);" key="${ctx}/waybill/preview?type=print&ids=" data-options="iconCls:'icon-print'">顺丰打印</a>-->
			<!--<a href="javascript:void(0);" onclick="batchResetAudit();" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">批量反审</a>
			<a href="javascript:void(0);" onclick="batchDelTrade();" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'">批量删除</a>
			-->
		</div>
		<div data-options="title:'待打印订单列表'" style="padding:2px;">
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
			var ctx = "${ctx}";

			
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
				
				$("#ids").val(ids);
				$("#cpCode").val(waybill);
				$("#frm").attr("action",url);
				$("#frm").submit();
				shipOrder.search();
			}

			
		</script>
	</body>
</html>
