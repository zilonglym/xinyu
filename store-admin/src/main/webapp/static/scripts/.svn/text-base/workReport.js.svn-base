
var work={};
work.initTable=function(){
	$('#contentTable').datagrid({
	    url:ctx+'/group/listData',
	    height:850,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	q:$("#q").val()
	    },
	    rownumbers:true,
	    remoteSort:false,
	    columns:[[
	    	{field:'shopName',title:'商家',width:180},
	        {field:'itemName',title:'商品名称',width:220},
	        {field:'itemCode',title:'商品编码',width:120},
	        {field:'quantity',title:'已推单数据',width:80},
	        {field:'auditQuantity',title:'审单中数量',width:80},
	        {field:'printQuantity',title:'已打单数量',width:80},
	        {field:'finishQuantity',title:'已完成数量',width:80},
	        
	    ]],
	    pagination:true
	});
	
	var pagination =$('#contentTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}
