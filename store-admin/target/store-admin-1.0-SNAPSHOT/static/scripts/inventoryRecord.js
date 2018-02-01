$(document).ready(function(){
	inventoryRecord.initTable();
});
var inventoryRecord={};
inventoryRecord.initTable=function(){
	$('#tb_inventoryRecord').datagrid({
		height:750,
		url:ctx+"/report/inventoryRecord/listData",
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'shopName',title:'商家名',width:150},
		  {field:'barCode',title:'条形码',width:150},
		  {field:'code',title:'商品编码',width:150},
		  {field:'name',title:'商品名称',width:150},
		  {field:'sku',title:'sku属性',width:150},
		  {field:'centroName',title:'所属仓库',width:150},
		  {field:'anum',title:'入库',width:100},
		  {field:'bnum',title:'出库',width:100},
		  {field:'num',title:'库存',width:100}
		]]
	});
	$('#tb_inventoryRecord').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200,500],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

inventoryRecord.search=function(){
	$('#tb_inventoryRecord').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		startDate:$('#beigainTime').datetimebox('getValue'),
		endDate:$('#lastTime').datetimebox('getValue'),
		itemTitle:$('#itemTitle').textbox('getValue')
	});
}
