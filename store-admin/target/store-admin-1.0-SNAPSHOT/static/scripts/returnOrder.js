$(document).ready(function(){
	returnOrder.initTable();
});

var returnOrder={};
returnOrder.initTable=function(){
	$('#tb_returnOrder').datagrid({
	    url:ctx+'/store/entry/confirm/returnorderList/listData',
	    height:850,
	    singleSelect:true,
	    rownumbers:true,
	    remoteSort:false,
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'id',title:'ID',hidden:true},
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'shopName',title:'商家',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'orderno',title:'外部单号',width:180,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressName',title:'物流公司',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressCode',title:'物流单号',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'name',title:'收件人',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'buyerNick',title:'昵称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'status',title:'操作',width:180,formatter:function(value,row,index){
	        	if(row.status=='ENTRY_WAIT_STORAGE_RECEIVED'){
	        		var id = row.id;
		        	var url = ctx+'/store/entry/confirm/returnorderDetail/'+id;
		        	return "<a href='"+url+"'>确认</a>";
	        	}else{
	        		return "已确认";
	        	}  	
	        }}
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_returnOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,150,200,250,300,500],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

returnOrder.search=function(){
	$("#tb_returnOrder").datagrid('load',{
		userId:$("#userId").combobox('getValue'),
		q:$("#q").textbox('getValue'),
		status:$("#status").combobox('getValue')
	});
}