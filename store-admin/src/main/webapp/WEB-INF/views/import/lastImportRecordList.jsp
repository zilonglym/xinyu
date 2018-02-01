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
	<div data-options="title:'本批次ERP 导入记录条数'" style="padding:2px;">
		<table id="tb_lastImportRecord"  >
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
		$('#tb_lastImportRecord').datagrid({
	    url:'${ctx}/import/lastImportRecordListData',
	    height:750,
	    singleSelect:true,
	    queryParams:{
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
	
	var pagination =$('#tb_lastImportRecord').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 10000,//每页显示的记录条数，默认为10000 
		pageList:[10000,20000,30000],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	})
	</script>
	</body>
</html>
