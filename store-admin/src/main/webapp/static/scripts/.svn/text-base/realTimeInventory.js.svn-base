$(document).ready(function(){
	inventoryRecord.initTable();
	inventoryRecord.search();
	setTimeout('inventoryRecord.autoPage()',5000)
});
var inventoryRecord={};
inventoryRecord.initTable=function(){
	$('#tb_inventoryRecord').datagrid({
		height:750,
		url:ctx+"/realTime/listData",
		singleSelect:true,
		rownumbers:true,
		pagination:true,
		fitColumns:true,
		striped : true,//设置为true将交替显示行背景。
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'shopName',title:'商家名',width:100},
		  {field:'name',title:'商品名称',width:100},
		  {field:'sku',title:'sku属性',width:100},
		  {field:'num',title:'库存',width:50,formatter:function(value,row,index){
			  var html="<span style='color:red;'>"+value+"</span>";
			  return html;
		  }},
		  {field:'shopName1',title:'商家名',width:100},
		  {field:'name1',title:'商品名称',width:100},
		  {field:'sku1',title:'sku属性',width:100},
		  {field:'num1',title:'库存',width:50,formatter:function(value,row,index){
			  var html="<span style='color:red;'>"+value+"</span>";
			  return html;
		  }}
		]]
	});
	$('#tb_inventoryRecord').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 60,
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

inventoryRecord.search=function(){
	
	$('#tb_inventoryRecord').datagrid('load',{});
};

inventoryRecord.autoPage=function(){
	var options = $('#tb_inventoryRecord').datagrid('getPager').data("pagination").options;  
	var curr = options.pageNumber;  
	var total = options.total;  
	var max = Math.ceil(total/options.pageSize);
	
	if(curr<max){
		curr++;
	}else{
		curr=1;
	}  
	$('#tb_inventoryRecord').datagrid('gotoPage', {
		page: curr,
	})
	setTimeout('inventoryRecord.autoPage()',5000)
}
