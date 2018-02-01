<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
	<head>
		<title>库位调整</title>
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${ctx}/static/jquery-easyui/themes/icon.css">
		<script type="text/javascript" src="${ctx}/static/jquery-easyui//jquery.min.js"></script>
		<script src="${ctx}/static/jquery-easyui/jquery.easyui.min.js" type="text/javascript"></script>
		<script src="${ctx}/static/scripts/local.js" type="text/javascript"></script>
	</head>
	<body style="text-align:center;">
		<div>
			<div style="width:500px;height:500px;margin-top:5px;margin-left:5px;float:left;border:1px double #E0E0E0;">
				<div id="oldTab">
					<input style="width:50px;" id="oRow" name="oRow" type="text" class="easyui-textbox" data-options="prompt:'行'"/>
					-
					<input style="width:50px;" id="oBoxOut" name="oBoxOut" type="text" class="easyui-textbox" data-options="prompt:'卡位'"/>
					-
					<input style="width:50px;" id="oCode" name="oCode" type="text" class="easyui-textbox" data-options="prompt:'板位'"/>
					-
					<input style="width:50px;" id="oFloor" name="oFloor" type="text" class="easyui-textbox" data-options="prompt:'层'"/>
					
					是否空置：
					<select id="oState" name="oState" class="easyui-combobox" style="width:80px;">
						<option value="">全部</option> 
						<option value='0'>空置</option>
						<option value='1'>非空</option>
					</select>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="local.initOld();" >查询</a>
				</div>
				<table id="tb_oldPlate" >
					<thead>
						<tr>

						</tr>
					</thead>
				</table>
			</div>
			<div style="float:left;padding:200px 0 0 30px;" >
				<img src="${ctx}/static/images/point.png"  width="120" height="50" />
			</div>
			<div style="width:500px;height:500px;margin-top:5px;margin-right:5px;float:right;border:1px double #E0E0E0;">
				<div id="newTab">
					<input style="width:50px;" id="nRow" name="nRow" type="text" class="easyui-textbox" data-options="prompt:'行'"/>
					-
					<input style="width:50px;" id="nBoxOut" name="nBoxOut" type="text" class="easyui-textbox" data-options="prompt:'卡位'"/>
					-
					<input style="width:50px;" id="nCode" name="nCode" type="text" class="easyui-textbox" data-options="prompt:'板位'"/>
					-
					<input style="width:50px;" id="nFloor" name="nFloor" type="text" class="easyui-textbox" data-options="prompt:'层'"/>
					是否空置：
					<select id="nState" name="nState" class="easyui-combobox" style="width:80px;">
						<option value="">全部</option> 
						<option value='0'>空置</option>
						<option value='1'>非空</option>
					</select>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="local.initNew();" >查询</a>
				</div>
				<table id="tb_newPlate" >
					<thead>
						<tr>

						</tr>
					</thead>
				</table>
			</div>
		</div>
	</body>
	<script>
	var ctx="${ctx}";
	</script>
</html>
