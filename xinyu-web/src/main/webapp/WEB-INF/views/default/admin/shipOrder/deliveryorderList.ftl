<html>
<head>
	<title>发货确认列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
	
</head>

<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
				<select name="selectUser" id="selectUser" class="easyui-combobox" style="width:180px;">
					<option value="">全部</option>
					<#list users as user>
						<option value="${user.id}" <#if userId??&&userId==user.id>selected</#if>>${user.subscriberName}</option>
					</#list>
				</select>
				<select name="selectCompany" id="selectCompany" class="easyui-combobox" style="width:180px;">
					<option value="">全部</option>
					<#list companys as item>
						<option value="${item.value}"  <#if company??&&item.value==company>selected</#if>>${item.description}</option>
					</#list>
				</select>
				<select name="status" id="status" class="easyui-combobox" style="width:180px;">
					<option value="WMS_PRINT" <#if status??&&"WMS_PRINT"==status>selected</#if>>未完成</option>
					<option value="WMS_FINASH" <#if status??&&"WMS_FINASH"==status>selected</#if>>已确认</option>
				</select>
				<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px" <#if beigainTime??>value="${beigainTime}"</#if>/>
				~
				<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px" <#if lastTime??>value="${lastTime}"</#if>/>   
				<select id="searchType" name="searchType" class="easyui-combobox" style="width:100px;">
					<option value="mobile" <#if searchType??&&searchType=="mobile">selected</#if>>电话</option>
					<option value="receiverName" <#if searchType??&&searchType=="receiverName">selected</#if>>收件人</option>
					<option value="tmsOrderCode" <#if searchType??&&searchType=="tmsOrderCode">selected</#if>>运单号</option>
					<option value="nick" <#if searchType??&&searchType=="nick">selected</#if>>呢称</option>
					<option value="batchCode" <#if searchType??&&searchType=="batchCode">selected</#if>>批次号</option>
					<option value="item" <#if searchType??&&searchType=="item">selected</#if>>型号</option>
				</select> 
				<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入查询值!'" style="width:100px;" <#if q??>value="${q}"</#if>/>
				<input id="txtno" name="txtno" class="easyui-textbox" data-options="prompt:'请输入不包含的型号'" style="width:180px;" <#if txtno??>value="${txtno}"</#if>/>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="delivery.searchList();" >查询</a>
				<a href="javascript:delivery.submit();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">批量确认</a>
				<span style="color:red;text-align:right;"> 订单总数：${count}</span>
	</div>		
	<table id="contentTable">
		<thead><tr>
		<th>序号</th>
		<th>商铺名</th>
		<th>出库单号</th>
		<th>业务类型</th>
		<th>收件人(昵称)</th>
		<th>运输公司</th>
		<th>运输公司运单号</th>
		<th>创建日期</th>
		<th>状态</th>
		<th>操作</th>
		</tr></thead>
	</table>
	<div id="dialog"></div>
	<script type="text/javascript" src="${base}/scripts/shipOrder/deliveryOrder.js?t=2"></script>
	<script>
		var ctx="${ctx}";
		var user="";
		function submitOk(id){
			 $.messager.confirm('确认提示', '标记后此单将不能再确认发货，是否继续?', function(r){
                if (r){
                    $.post(ctx+"/shipOrder/submitOk",{id:id},function(data){
                    	if(data && data.ret=='1'){
                    		$.messager.alert('提示','标记确认成功!');
                    		window.location.reload();
                    	}
                    });
                }
            });
		}
	</script>
</body>
</html>
