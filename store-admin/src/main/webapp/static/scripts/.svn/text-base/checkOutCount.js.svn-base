$(document).ready(function(){
	checkOutCount.initTable();
});

var checkOutCount={};
checkOutCount.initTable=function(){
	$("#tb_checkOutCount").datagrid({
		url:ctx+"/record/checkout/count/listData",
		height:850,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fitColumns:true,
		columns:[[
		          {field:'userId',title:'商家id',width:50,hidden:true},
				  {field:'user',title:'商家名称',width:100},
				  {field:'num',title:'总出库',width:100},
				  {field:'operate',title:'操作',width:150,formatter:function(value,row,index){
					  var startDate=$("#beigainTime").datetimebox('getValue');
					  var endDate=$("#lastTime").datetimebox('getValue');
					  var sysId=$("#sysId").datetimebox('getValue');
					  var url1=ctx+"/report/checkout/count/express/xls?userId="+row.userId+"&sysId="+sysId+"&startDate="+startDate+"&endDate="+endDate;
					  var url2=ctx+"/report/checkout/count/item/xls?userId="+row.userId+"&sysId="+sysId+"&startDate="+startDate+"&endDate="+endDate;
					  var url3=ctx+"/report/checkout/xls?userId="+row.userId+"&sysId="+sysId+"&startDate="+startDate+"&endDate="+endDate;
					  return "<a href='"+url1+"'>快递汇总</a>|<a href='"+url2+"'>商品汇总</a>|<a href='"+url3+"'>出库明细</a>";  
				  }}
				]]
		});
		//分页显示插件
		$('#tb_checkOutCount').datagrid('getPager').pagination({
			showPageList:false,
			pageSize:100,
			pageList:[100,200],
		       beforePageText: '第',
		       afterPageText: '页    共 {pages} 页', 
		       displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
}

checkOutCount.search=function(){
	$("#tb_checkOutCount").datagrid('load',{
		userId:$("#userId").combobox('getValue'),
		sysId:$("#sysId").combobox('getValue'),
		startDate:$("#beigainTime").datetimebox('getValue'),
		endDate:$("#lastTime").datetimebox('getValue')
	});
}
