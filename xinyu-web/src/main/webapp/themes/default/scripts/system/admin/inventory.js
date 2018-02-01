$(document).ready(function(){
	inventory.initTable();
});

var inventory={};

inventory.initTable=function(){
	$('#tb_inventory').datagrid({
	    url:ctx+'/inventory/listData',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	        {field:'id',title:'ID',width:260,hidden:true},
	        {field:'userName',title:'所属商家',width:150},
	        {field:'itemName',title:'商品名称',width:260},
	        {field:'itemCode',title:'商品编码',width:120},
	        {field:'sku',title:'商品SKU',width:100},
	        {field:'num1',title:'正品数量',width:80},
	        {field:'num2',title:'残品数量',width:80},
	        {field:'num3',title:'机损数量',width:80},
	        {field:'num4',title:'箱损数量',width:80},
	        {field:'num5',title:'在途数量',width:80},   
	        {field:'num6',title:'冻结数量',width:80},   
	        {field:'num7',title:'其他数量',width:80}    
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_inventory').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

inventory.search=function(){
	$('#tb_inventory').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		searchText:$("#searchText").textbox('getValue')
	});
}
