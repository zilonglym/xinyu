$(document).ready(function(){
	operator.initTable();
});
var operator={};
operator.initTable=function(){
	$("#tb_operator").datagrid({
		url:ctx+"/systemItem/operatorListData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		queryParams:{
			type:$('#modelType').combobox('getValue'),
			accountId:$('#accountId').combobox('getValue'),
		    startDate:$('#beigainTime').datetimebox('getValue'),
		    endDate:$('#lastTime').datetimebox('getValue'),
		    searchText:$('#searchText').textbox('getValue')
		},
		columns:[[
		  {field:'operatorDate',title:'操作时间',width:150},
		  {field:'account',title:'操作账号',width:150},
		  {field:'remark',title:'对象信息',width:150},
		  {field:'model',title:'操作类型',width:100},
		  {field:'oldValue',title:'原值',width:100},
		  {field:'newValue',title:'修改新值',width:100},
		  {field:'description',title:'详细信息',width:100}
		]]
	});
	$('#tb_operator').datagrid('getPager').pagination({
		showPageList:false,
		pageSize:100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

operator.search=function(){
	$('#tb_operator').datagrid('load',{
		type:$('#modelType').combobox('getValue'),
		accountId:$('#accountId').combobox('getValue'),
	    startDate:$('#beigainTime').datetimebox('getValue'),
	    endDate:$('#lastTime').datetimebox('getValue'),
	    searchText:$('#searchText').textbox('getValue')
	});
}
