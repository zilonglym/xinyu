$(document).ready(function(){
	inventoryDetail.initTable();
});
var inventoryDetail={};
inventoryDetail.initTable=function(){
	$("#tb_inventoryDetail").datagrid({
		height:750,
		url:ctx+"/report/inventory/listData",
		rownumbers:true,
		singleSelect:true,
		pagination:true,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'shopName',title:'商家',width:150},        
		  {field:'code',title:'编码',width:200},        
		  {field:'name',title:'名称',width:250},        
		  {field:'sku',title:'sku',width:200},        
		  {field:'num',title:'当前库存',width:150}        
		]]
	});
	$('#tb_inventoryDetail').datagrid('getPager').pagination({
		showPageList:true,
		pageSize:100,
		pageList:[100,200,500],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

inventoryDetail.search=function(){
	$('#tb_inventoryDetail').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		itemTitle:$('#itemTitle').textbox('getValue')
	});
}