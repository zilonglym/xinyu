<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>批次信息</title>
</head>
<body>
	<div id="button-bar" style="margin-top:5px;margin-bottom:5px;">
		入库数量：<input style="width:100px;" id="anum" name="anum" type="text" class="easyui-textbox" value="${anum}" disabled/>
		默认数量：<input style="width:100px;" id="dnum" name="dnum" type="text" class="easyui-textbox" value="${dnum}" disabled/>
		条码数量：<input style="width:100px;" id="count" name="count" type="text" class="easyui-textbox" value="${count}" disabled/>
		入库单号：<input style="width:100px;" id="entryCode" name="entryCode" type="text" class="easyui-textbox" value="${entryCode}" disabled/>
		是否高位：<input style="width:100px;" id="isHigh" name="isHigh" type="text" class="easyui-textbox" value="${isHigh}" disabled/>
		生产日期：<input style="width:100px;" id="birthDate" name="birthDate" type="text" class="easyui-datetimebox" value="${birthDate}" disabled/>
		<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="print();" >打印</a>
	</div>
	<div data-options="title:'批次信息'" style="padding:2px;">
		<table id="tb_localBatch"  >
			<thead>
				<tr>

				</tr>
			</thead>
		</table>
		<input id="itemId" name="itemId" value="${itemId}" hidden="true"/>
	</div>
	<div id="dialog"></div>
	<script>
	var ctx="${ctx}";
	$(document).ready(function(){
		$('#tb_localBatch').datagrid({
		    url:ctx+'/pcLocal/localBatch/print/listData',
		    height:850,
		    singleSelect:false,
		    rownumbers:true,
		    queryParams:{
		    	id:$('#itemId').val(),
		    	anum:$('#anum').textbox('getValue'),
				dnum:$('#dnum').textbox('getValue'),
				isHigh:$('#isHigh').textbox('getValue'),
				entryCode:$('#entryCode').textbox('getValue'),
				birthDate:$('#birthDate').datetimebox('getValue')
		    },
		    columns:[[
		    		  {field:'id',checkbox:true},
		    		  {field:'itemId',hidden:true},
		    		  {field:'shopId',hidden:true},
		    		  {field:'entryCode',title:'入库单编号',width:130},
		    		  {field:'shopName',title:'商家',width:80},
		    		  {field:'itemName',title:'品名',width:100},
		    		  {field:'sku',title:'属性',width:60},
		    		  {field:'barCode',title:'条码',width:80},
		    		  {field:'num',title:'数量',width:60},
		    		  {field:'isHigh',title:'是否高板',width:80},
		    		  {field:'code',title:'批次号',width:130}	  
		    		]]
		})
	});
	function print(){
			var rows = $('#tb_localBatch').datagrid('getChecked');
			var ids="";
			for(var i=0;i<rows.length;i++){
				var id=rows[i].id;
				ids=ids+id+",";
			}
			window.open(ctx+"/pcLocal/localBatch/toBatchPrint?ids="+ids);
	}
	</script>
</body>
</html>
