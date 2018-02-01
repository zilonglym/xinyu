$(document).ready(function(){
	localReport.initTable();
});
var localReport = {};
localReport.initTable = function(){
	$('#tb_localReport').datagrid({
	    url:ctx+'/pcLocal/report/listData',
	    height:850,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	              {field:'shopId',hidden:true},
	    		  {field:'shopName',title:'商家名称',width:120},
	    		  {field:'bnum',title:'占用货位',width:120},
	    		  {field:'num',title:'商品数量',width:60},
	    		  {field:'operate',title:'操作',width:100,formatter:function(value,row,index){
	    			  var startTime=$("#beigainTime").datetimebox('getValue');
	    			  var endTime=$("#lastTime").datetimebox('getValue');
	    			  var url = ctx + "/pcLocal/report/xls?shopId="+row.shopId+"&shopName="+row.shopName+"&startTime="+startTime+"&endTime="+endTime;
	    			  var url1 = ctx + "/pcLocal/report/item/xls?shopId="+row.shopId+"&shopName="+row.shopName+"&startTime="+startTime+"&endTime="+endTime;
	    			  return "<a href='"+url+"' target='_blank'>库位明细</a>&nbsp;"+"<a href='"+url1+"' target='_blank'>商品汇总</a>";
	    		  }}
	    		]],
	    pagination:true
	});
	
	var pagination =$('#tb_localReport').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200,500], 
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

localReport.search = function(){
	$('#tb_localReport').datagrid('load',{
		shopId:$('#shopId').combobox('getValue'),
		startTime:$('#beigainTime').datetimebox('getValue'),
		endTime:$('#lastTime').datetimebox('getValue'),
		q:$('#q').textbox('getValue')
	});
}
