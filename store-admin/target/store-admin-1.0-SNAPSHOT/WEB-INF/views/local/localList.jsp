<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>货位商品信息</title>
	<script src="${ctx}/static/scripts/local.js" type="text/javascript"></script>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		商家：
		<select id="shopId" name="shopId" class="easyui-combobox" style="width:150px;">
			<option value="">全部</option> 
			<c:forEach items="${shops}" var="shop">
				<option value='${shop.id}'>${shop.name}</option>
			</c:forEach>
		</select>
		货位：
		<input style="width:100px;" id="row" name="row" type="text" class="easyui-textbox" data-options="prompt:'货架号'" />
		-
		<input style="width:100px;" id="boxOut" name="boxOut" type="text" class="easyui-textbox" data-options="prompt:'位置'"/>
		-
		<input style="width:100px;" id="code" name="code" type="text" class="easyui-textbox" data-options="prompt:'托板号'"/>
		-
		<input style="width:100px;" id="floor" name="floor" type="text" class="easyui-textbox" data-options="prompt:'层数'"/>
		是否空置：
		<select id="state" name="state" class="easyui-combobox" style="width:150px;">
			<option value="">全部</option> 
			<option value='0'>空置</option>
			<option value='1'>非空</option>
		</select>
		关键字：
		<input style="width:250px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'请输入型号/条码'"/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" onclick="local.clear();" >清空</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="local.search();" >查询</a>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="local.move();" >库位调整</a>
	</div>
	<div data-options="title:'货位商品信息'" style="padding:2px;">
		<table id="tb_local"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
		<table id="tb_record"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	$(document).ready(function () {
        $('#row').textbox('textbox').keydown(function (e) {
            if (e.keyCode == 13) {
            	local.search();
            }
        });
        $('#boxOut').textbox('textbox').keydown(function (e) {
            if (e.keyCode == 13) {
            	local.search();
            }
        });
        $('#code').textbox('textbox').keydown(function (e) {
            if (e.keyCode == 13) {
            	local.search();
            }
        });
        $('#floor').textbox('textbox').keydown(function (e) {
            if (e.keyCode == 13) {
            	local.search();
            }
        });
        $('#q').textbox('textbox').keydown(function (e) {
            if (e.keyCode == 13) {
            	local.search();
            }
        });
    });
	</script>
</body>
</html>
