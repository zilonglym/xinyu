$(document).ready(function(){
	report.initTable();
});

var report={};

report.initTable=function(){
	$("#tb_report").datagrid({
		url:ctx+"/report/ship/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		columns:[[
		  {field:'userId',checkbox:true},
		  {field:'userName',title:'商家名称',width:150},
		  {field:'num',title:'订单数量',width:100},
		  {field:'sum',title:'商品数量',width:100},
		  {field:'operation',title:'操作',width:200,formatter:function(value,row,index){
			  var startDate=$("#beigainTime").datetimebox('getValue');
			  var endDate=$("#lastTime").datetimebox('getValue');
			  var cpCode = $('#sysId').combobox('getValue');
			  var url2=ctx+"/report/ship/xls?userId="+row.userId+"&startDate="+startDate+"&endDate="+endDate+"&cpCode="+cpCode;
			  var url3=ctx+"/report/ship/item/xls?userId="+row.userId+"&startDate="+startDate+"&endDate="+endDate+"&cpCode="+cpCode;
			  return "<a href='"+url2+"' target='_blank'>发货明细</a>&nbsp;|&nbsp;<a href='"+url3+"'>发货汇总</a>";  
		  }}
		]],
		onLoadSuccess:function compute() {//计算函数
		    var rows = $('#tb_report').datagrid('getRows');//获取当前的数据行
		    var ptotal = 0;//计算listprice的总和
		    var utotal = 0;//统计unitcost的总和
		    for (var i = 0; i < rows.length; i++) {
		        ptotal += rows[i]['num'];
		        utotal += rows[i]['sum'];
		    }
		    //新增一行显示统计信息
		    $('#tb_report').datagrid('appendRow', { userName: '<b>统计：</b>', num: ptotal, sum: utotal });
		} 
	});
	$('#tb_report').datagrid('getPager').pagination({
		showPageList:false,
		pageSize:100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

report.search=function(){
	$('#tb_report').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		cpCode:$('#sysId').combobox('getValue'),
	    startDate:$('#beigainTime').datetimebox('getValue'),
	    endDate:$('#lastTime').datetimebox('getValue')
	});
}

report.exportData = function(){
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

function compute() {//计算函数
    var rows = $('#tb_report').datagrid('getRows');//获取当前的数据行
    var ptotal = 0;//计算listprice的总和
    var utotal = 0;//统计unitcost的总和
    for (var i = 0; i < rows.length; i++) {
        ptotal += rows[i]['num'];
        utotal += rows[i]['sum'];
    }
    //新增一行显示统计信息
    $('#tb_report').datagrid('appendRow', { itemid: '<b>统计：</b><b>总数</b>', listprice: ptotal, unitcost: utotal });
}