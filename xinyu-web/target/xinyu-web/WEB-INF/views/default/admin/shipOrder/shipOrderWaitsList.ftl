<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>订单列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
	<script type="text/javascript" src="${base}/scripts/system/admin/auditTrade.js?t=2"></script>
	<script type="text/javascript">
		var ctx="${ctx}";
		var userId="${userId!''}";
	</script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;font-size:12px;">
		   	店铺:
		    <select id="selectUser" name="userId" class="easyui-combobox" style="width:150px;">
			<option value="">全部</option> 
			<#list users as user>
				<option value="${user.id}">${user.subscriberName}</option>
			</#list>
			</select>
			快递:
			<select id="selectCompany" name="company" class="easyui-combobox" style="width:70px;">
				<option value=''>全部</option> 
				<option value='SF'>顺丰</option>
				<option value='YUNDA'>韵达</option> 
				<option value='YTO'>圆通</option> 
				<option value='EYB'>EMS经济</option>
				<option value='POSTB'>邮政小包</option>
				<option value='STO'>申通</option>				
				<option value='HTKY5'>汇通5部</option>	
				<option value='ZTO'>中通</option>
							
			</select>
		<span id="city_div">
		 	 省份:
		 	 <select id="selectState" name="state" class="easyui-combobox" data-options="valueField:'id', textField:'no'" style="width:60px">	
		 		 <option value="0">全部</option>
		 		 <#list stateList as obj>
		 	 		<option value="${obj.description}">${obj.description}</option>
		 	 	</#list>	
			</select>	 
	   	</span>
	   	重量
	   	<select name="weight_x" id="weight_x" style="width: 50px;" class="easyui-combobox" >
	   		<option value=">=">&gt;=</option>
	   		<option value="=">=</option>
	   		<option value="&lt;=">&lt;= </option>
	   	</select>
	   	 <input class="easyui-textbox" id="weight" name="weight" style="width: 40px;" >
		乡镇村组:
			<select name="others" id="others" style="width: 80px;" class="easyui-combobox" >
				<option value="0">所有</option>
				<option value="1">包含</option>
				<option value="2">不包含</option>
			</select>
		<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:140px"/>
		~
		<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:140px"/> 
		<select id="searchType" name="searchType" class="easyui-combobox" style="width:60px;">
			<option value="mobile">电话</option>
			<option value="receiverName">收件人</option>
			<option value="tmsOrderCode">运单号</option>
			<option value="nick">呢称</option>
			<option value="batchCode">批次号</option>
			<option value="item">型号</option>
		</select>   
	    <input id="q" name="q" type="text" style="width:150px;" class="easyui-textbox" data-options="prompt:'输入关键字进行查询 ...'">
	    <a id="call" href="javascript:audit.searchList();" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	   	<a class="easyui-linkbutton"  href="javascript:selectRow();" >批量审核</a>
	   	<a class="easyui-linkbutton"  href="javascript:audit.auditSfArea();" >顺风</a>
	 	<a class="easyui-linkbutton"  href="javascript:audit.auditYUNDAArea('YUNDAX');" >韵达353</a>
	   	<a class="easyui-linkbutton"  href="javascript:audit.auditYUNDAArea('YUNDA');" >韵达106</a>
	   	<a class="easyui-linkbutton"  href="javascript:audit.auditYUNDAArea('YTO');" >圆通</a>
	   	<a class="easyui-linkbutton"  href="javascript:audit.addSplit();" >自定义拆单</a>
	</div>
	<div data-options="title:'批量审核'" style="padding:2px;">
	<table id="tb_auditTable">
		<thead>
			<tr>
		
			</tr>
		</thead>
	</table>
	</div>
	 <div id="dialog"></div>
</body>
</html>