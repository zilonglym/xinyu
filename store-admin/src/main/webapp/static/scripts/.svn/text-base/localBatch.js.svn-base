$(document).ready(function(){
	localBatch.initTable();
});
var localBatch = {};
localBatch.initTable = function(){
	$('#tb_localBatch').datagrid({
	    url:ctx+'/pcLocal/localBatch/listData',
	    height:650,
	    singleSelect:false,
	    rownumbers:true,
	    columns:[[
	              {field:'id',checkbox:true},
	              {field:'lastUpdate',title:'时间',width:135},
	    		  {field:'shopName',title:'商家',width:120},
	    		  {field:'entryCode',title:'入库单号',width:120},
	    		  {field:'code',title:'批次单据',width:120},
	    		  {field:'itemName',title:'名称',width:120},
	    		  {field:'sku',title:'属性',width:120},
	    		  {field:'barCode',title:'条码',width:120},
	    		  {field:'num',title:'数量',width:60},
	    		  {field:'birthDate',title:'生产日期',width:120},
	    		  {field:'isHigh',title:'板位类型',width:120},
	    		  {field:'status',title:'状态',width:100,formatter:function(value,row,index){
	    				if(value=="WMS_PRINT"){
	    	        		return "已打印";
	    	        	}else if(value=="WMS_CONFIRM"){
	    	        		return "已确认";
	    	        	}else if(value=="WMS_FINASH"){
	    	        		return "已完成";
	    	        	}else{
	    	        		return "未打印";
	    	        	}
	    		  }} 
	    		]],
	    pagination:true
	});
	
	var pagination =$('#tb_localBatch').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200,500], 
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

localBatch.search = function(){
	$('#tb_localBatch').datagrid('load',{
		shopId:$('#shopId').combobox('getValue'),
		status:$('#status').combobox('getValue'),
		q:$('#q').textbox('getValue'),
		startDate:$('#startDate').datetimebox('getValue'),
		endDate:$('#endDate').datetimebox('getValue')
	});
}

localBatch.print = function(){
	var rows = $('#tb_localBatch').datagrid('getChecked');
	var ids="";
	for(var i=0;i<rows.length;i++){
		var id=rows[i].id;
		ids=ids+id+",";
	}
	window.open(ctx+"/pcLocal/localBatch/toBatchPrint?ids="+ids);
}