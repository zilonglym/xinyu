<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>ERP导入记录</title>
</head>
<body>
	<div id="button-bar" style="margin-bottom:5px;margin-top:5px;">
			<select id="status" class="easyui-combobox" name="status">
				<option value="">全部</option>
			</select>
			<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'"/>
			~
			<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'"/>  
			<input id="q" class="easyui-textbox" name="q"  data-options="prompt:'请输入物流单号/商品名称/信息'"></input>
			<a href="" class="easyui-linkbutton" data-options="iconCls:'icon-search'">查询</a>
	</div>
	<div data-options="title:'ERP 导入记录条数'" style="padding:2px;">
		<table id="tb_importRecord"  >
			<thead><tr>
			<th>创建日期</th>
			<th>快递公司 </th>
			<th>商品信息</th>
			<th>面单号</th>
			<th>反馈信息</th>
			</tr>
			</thead>
		</table>
	</div>
	<script>
	$(function(){
		var beigainTime=$("#beigainTime").datetimebox('getValue');
		var lastTime=$("#lastTime").datetimebox('getValue');
		$('#tb_importRecord').datagrid({
	    url:'${ctx}/import/importRecordListData',
	    height:750,
	    singleSelect:true,
	    queryParams:{
	    	q:$("#q").val(),
	    	startDate:beigainTime,
	    	endDate:lastTime
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'createDateStr',title:'创建日期',width:180},
	        {field:'company',title:'快递公司',width:180},
	        {field:'itemCode',title:'商品信息',width:280},
	        {field:'expressOrderno',title:'面单号',width:280},
	        {field:'msg',title:'反馈信息'}
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_importRecord').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	})
	</script>
	</body>
</html>
