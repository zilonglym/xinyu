<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>入库单列表</title>
	<script>
		var ctx="${ctx}";
	</script>
</head>
<body>
	<legend></legend>
		<select id="selectUser" name="userId" >
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
	<button class="easyui-linkbutton"  onclick="search();" style="margin-bottom: 10px;">查询</button>
	<button class="easyui-linkbutton"  onclick="addStorage();" style="margin-bottom: 10px;">创建入库单</button>
	
	<table id="contentTable">
		<thead><tr>
		<th>创建日期</th>
		<th>商家</th>
		<th>运单号</th>
		<th>操作</th>
		</tr></thead>
		
	</table>
	<input type="hidden" id="status" name="status" value="${status}" />
	<script>
		$(function(){
		$('#contentTable').datagrid({
	    url:ctx+'/storage/listData',
	    height:750,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#selectUser").val(),
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'operateTime',title:'创建日期',width:220},
	        {field:'user',title:'商家',width:120},
	        {field:'orderNo',title:'单号',width:280},
	        {field:'id',title:'操作',width:320,formatter:function(value,row,index){
	        	return "<a data-toggle='modal' href='${ctx}/storage/view?id="+value+"' target='_blank'>查看</a>"+"&nbsp;&nbsp;&nbsp;&nbsp;<a href='${ctx}/storage/report/xls?id="+value+"'>导出</a>";
	        }}
	    ]],
	    pagination:true
	});
	
	var pagination =$('#contentTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
})
		function search(){
			$('#contentTable').datagrid('load',{
				userId:$('#selectUser').val()
			});
		}
		function addStorage(){
			var userId=$('#selectUser').val();
			window.open(ctx+"/storage/toCreate?userId="+userId);
		}
	</script>
</body>
</html>
