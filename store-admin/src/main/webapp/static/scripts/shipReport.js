$(document).ready(function(){
	ship.initTable();
});
var ship={};
ship.initTable=function(){
	$("#tb_ship").datagrid({
		url:ctx+"/report/ship/listData",
		height:850,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		columns:[[
		  {field:'userId',checkbox:true},
		  {field:'userName',title:'商家名称',width:150},
		  {field:'num',title:'订单数量',width:100},
		  {field:'items',title:'商品数量',width:100},
		  {field:'operation',title:'操作',width:200,formatter:function(value,row,index){
			  var startDate=$("#beigainTime").datetimebox('getValue');
			  var endDate=$("#lastTime").datetimebox('getValue');
			  var cpCode=$("#sysId").combobox('getValue');
			  var url1=ctx+"/report/inventory/xls?userId="+row.userId;
			  var url2=ctx+"/report/ship/xls?userId="+row.userId+"&startDate="+startDate+"&endDate="+endDate+"&cpCode="+cpCode;
			  var url3=ctx+"/report/ship/item/xls?userId="+row.userId+"&startDate="+startDate+"&endDate="+endDate+"&cpCode="+cpCode;
			  return "<a href='"+url1+"'>库存明细</a>|<a href='"+url2+"' target='_blank'>发货明细</a>|<a href='"+url3+"'>发货汇总</a>";  
		  }}
		]],
		onLoadSuccess:function compute() {//计算函数
		    var rows = $('#tb_ship').datagrid('getRows');//获取当前的数据行
		    var ptotal = 0;//计算listprice的总和
		    var utotal = 0;//统计unitcost的总和
		    for (var i = 0; i < rows.length; i++) {
		        ptotal += rows[i]['num'];
		        utotal += rows[i]['items'];
		    }
		    //新增一行显示统计信息
		    $('#tb_ship').datagrid('appendRow', { userName: '<b>统计：</b>', num: ptotal, items: utotal });
		} 
	});
	$('#tb_ship').datagrid('getPager').pagination({
		showPageList:false,
		pageSize:100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

ship.search=function(){
	$('#tb_ship').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		cpCode:$("#sysId").combobox('getValue'),
	    startDate:$('#beigainTime').datetimebox('getValue'),
	    endDate:$('#lastTime').datetimebox('getValue')
	});
}

ship.refresh=function(){
	$('#tb_ship').datagrid('load',{
		name:null
	});
}

ship.exportData = function(){
	var cpCode = $('#sysId').combobox('getValue');
	var userId = $('#userId').combobox('getValue');
	var startDate = $('#beigainTime').datetimebox('getValue');
	var endDate = $('#lastTime').datetimebox('getValue');
	if(typeof startDate == "undefined" || startDate == null || startDate == ""||typeof endDate == "undefined" || endDate == null || endDate == ""){
		$.messager.alert("错误","起始截止时间不能为空!");
	}else{
		var url = ctx + "/report/order/xls?userId="+userId+"&cpCode="+cpCode+"&startDate="+startDate+"&endDate="+endDate;
		window.location.href = url;
	}
}