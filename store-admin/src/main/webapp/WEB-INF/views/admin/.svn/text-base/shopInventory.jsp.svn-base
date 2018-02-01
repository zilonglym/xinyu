<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<script src="${ctx}/static/scripts/items.js" type="text/javascript"></script>
<script type="text/javascript">
	var ctx="${ctx}";
$(function() {

$('#inventoryTable').datagrid({
	    url:'${ctx}/stock/listData',
	    height:750,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#selectUser").val(),
	    	itemName:$("#itemName").val()
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'itemCode',title:'商品编码'},
	        {field:'itemName',title:'商品名称'},
	        {field:'itemSku',title:'sku属性'},
	        {field:'c3',title:'可销库存'},
	        {field:'ca',title:'不良品'},
	        {field:'c7',title:'冻结(仓库发货中)'},
	        {field:'c8',title:'已售出'},
	        {field:'c0',title:'操作',formatter:function(value,row,index){
	         	return "<a href='javascript:void(0);' onclick='items.addBarCode("+value+")'>扫描条码</a>"+
	         	"&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp;<a href='javascript:void(0);' onclick='items.updatePackageWeig("+value+")'>打包重量修改</a>";
	         }}
	    ]],
	    pagination:true,
	     toolbar:[{
	    	text:'添加商品',
	    	iconCls:'icon-add',
	    	handler:function(){
	    	}
	    }]
	});
	var pagination =$('#inventoryTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 200,//每页显示的记录条数，默认为10 
		pageList:[200,300],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});

});
</script>
</head>
<body>
	<form id = "myForm" action="${ctx}/stock" method="post">
		<select id="selectUser" name ="userId" class="easyui-combobox">
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'
					<c:if test="${user.id == userId}">
						selected='selected'
					</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
		<input type="text" name="itemName" value="${itemName}" id="itemName"  class="easyui-textbox" data-options="prompt:'商品名称'"/>
		<button class="easyui-linkbutton"  onclick="javascirpt:$('#myForm').submit()">查看库存</button>
		
	</form>
	<table id="inventoryTable">
		<thead><tr>
		<th>序号</th>
		<th>商品编码</th>
		<th>商品名称</th>
		<th>sku属性</th>
		<th>可销库存</th>
		<th>不良品</th>
		<th>冻结(仓库发货中)</th>
		<th>已售出</th>
		<th>操作</th>
		</tr></thead>
	</table>
		<div id="dialog"></div>
		<div id="dialog1" >
		</div>
</body>
</html>