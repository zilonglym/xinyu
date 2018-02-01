$(document).ready(function(){
	orderStop.initTable();
});

var orderStop={};
orderStop.initTable=function(){
	$('#tb_orderStop').datagrid({
		url:ctx+'/shipOrder/shipOrderStop/lisData',
	    height:750,
	    remoteSort:false,
	    singleSelect:false,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	status:$("#status").val(),
	    	searchText:$("#searchText").val()
	    },
	    rownumbers:true,
	    pagination:true,
	    columns:[[
	        {field:'id',hidden:true},
	        {field:'createDate',title:'创建日期',width:130,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'userName',title:'商铺名称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'tbNumber',title:'订单号',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'receiverName',title:'收件人',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'items',title:'商品',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },	      
	        {field:'company',title:'快递公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderNo',title:'运单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'description',title:'信息',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        }
	     ]]	   
	});
	
	var pagination =$('#tb_orderStop').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}


orderStop.searchList=function(){
	$('#tb_orderStop').datagrid('load', {
   		userId:$("#userId").combobox('getValue'),
   		type:$("#status").combobox('getValue'),
   		txt:$("#txt").textbox('getValue')
	});
}

