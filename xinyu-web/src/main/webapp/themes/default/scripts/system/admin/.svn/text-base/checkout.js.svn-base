$(document).ready(function(){
	checkout.initTable();
});

var checkout={};
checkout.initTable=function(){
	$("#tb_checkout").datagrid({
		url:ctx+"/checkOut/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		loadMsg:"正在加载中.......",
		columns:[[
				  {field:'id',title:'ID',width:50,hidden:true},
				  {field:'createDate',title:'出库时间',width:150},
				  {field:'person',title:'操作人',width:80},
				  {field:'userName',title:'商家名称',width:100},
				  {field:'express',title:'物流公司',width:80},
				  {field:'code',title:'物流单号',width:150 },
				  {field:'barCode',title:'条形码',width:100},
				  {field:'itemName',title:'商品',width:150},
				  {field:'status',title:'状态',width:100},
				  {field:'msg',title:'信息',width:200}
				]]
		});
			
			//分页显示插件
			$('#tb_checkout').datagrid('getPager').pagination({
				showPageList:false,
				pageSize:100,
				pageList:[100,200,500],
		        beforePageText: '第',
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
			});

}

checkout.search=function(){
	$("#tb_checkout").datagrid('load',{
		userId:$("#userId").combobox('getValue'),
		status:$("#status").combobox('getValue'),
		express:$("#express").combobox('getValue'),
		startDate:$("#startDate").datetimebox('getValue'),
		endDate:$("#endDate").datetimebox('getValue'),
		searchText:$("#searchText").textbox('getValue')
	});
}

