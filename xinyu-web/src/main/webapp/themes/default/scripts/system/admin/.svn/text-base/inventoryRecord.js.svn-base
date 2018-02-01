$(document).ready(function(){
	inventoryRecord.initTable();
});

var inventoryRecord = {};

inventoryRecord.initTable = function(){
	$('#tb_inventoryRecord').datagrid({
	    url:ctx+'/inventory/inventoryRecord/listData',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	        {field:'id',title:'ID',width:260,hidden:true},
	        {field:'createDate',title:'时间',width:150},
	        {field:'userName',title:'商家',width:150},
	        {field:'itemName',title:'名称',width:260},
	        {field:'itemCode',title:'编码',width:120},
	        {field:'itemColor',title:'SKU',width:120},
	        {field:'itemType',title:'类型',width:120},
	        {field:'type',title:'库存类型',width:120},
	        {field:'num',title:'数量',width:120}	        
	    ]],
	    pagination:true
	});
	
	var pagination = $('#tb_inventoryRecord').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

inventoryRecord.search = function(){
	$('#tb_inventoryRecord').datagrid('load', {
  		 userId:$("#userId").combobox('getValue'),
  		searchText:$("#searchText").textbox('getValue')
	});
}
