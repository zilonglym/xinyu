<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>商品管理</title>
</head>
<body>
     <div style="margin-top: 5px;margin-bottom: 5px;">
     	   	<input  style="width:350px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'商品标题'"/>
     		<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchList();" >查询</a>
     		<a href="###" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="add();" >查询</a>
     	</div>  
	<table id="contentTable">
		 <thead>
			<tr>
			<th>商品编码</th>
			<th>商品标题</th>
			<th>规格(sku)</th>
			<th>重量(克)</th>
		</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<div id="dialog"></div>
	<script>
	   $(document).ready(function(){
			select();
	});


function  select(){
	$('#contentTable').datagrid({
	    url:'${ctx}/item/listData',
	    height:850,
	    singleSelect:true,
	    queryParams:{
	    	q:$("#q").val()
	    },
	    rownumbers:true,
	    columns:[[
			{field:'id',title:'ID',hidden:true},
	        {field:'code',title:'商品编码',width:250,formatter: function(value,row,index){
	            return '<a style="color:blue" href="${ctx}/item/update/'+row.id+'">'+row.code+'</a>';
	        }},
	        {field:'title',title:'商品标题',width:350},
	        {field:'sku',title:'规格(sku)',width:250},
	        {field:'packageWeight',title:'重量(克)',width:150}
	    	]],
	      pagination:true
	});
	
	var pagination =$('#contentTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10
		pageList:[100],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

 function  searchList(){
	$('#contentTable').datagrid('load', {
   		 q:$("#q").textbox('getValue')
	});
}   



	</script>
	
</body>
</html>
