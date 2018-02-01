$(document).ready(function(){
	oldOrder.initTable();
});

var oldOrder={};
oldOrder.initTable=function(){
	$('#tb_oldOrder').datagrid({
		url:ctx+'/shipOrder/oldOrder/listData',
	    height:750,
	    remoteSort:false,
	    singleSelect:false,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	status:$("#status").val(),
	    	searchText:$("#searchText").val()
	    },
	    rownumbers:true,
	    columns:[[
	        {field:'ck',checkbox:true},
	        {field:'id',hidden:true},
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'userName',title:'商铺名称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'items',title:'商品',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'nick',title:'昵称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'name',title:'收件人',width:60,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'phone',title:'联系方式',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'address',title:'目的地',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'expressCode',title:'快递公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderNo',title:'运单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'buyerMemo',title:'买家留言',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'sellerMemo',title:'卖家留言',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'remark',title:'备注',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderCode',title:'菜鸟单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        }
	    ]],
	    rowStyler: function(index,row){
					if (row.status =='WMS_DELETED'){
						return 'background-color:#6293BB;color:#fff;font-weight:bold;';
					}else if(row.status =='WMS_CANCEL'){
						return 'background-color:red;color:#fff;font-weight:bold;';
					}
				},
		onDblClickRow: function(rowIndex, rowData){
			initShipOrderOperatorList(rowData.id);
		},
	    pagination:true
	});
	
	var pagination =$('#tb_oldOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function initShipOrderOperatorList(id){
	$('#tb_operator').datagrid({
		url:ctx+'/oldOrder/operatorListData',
	    height:200,
	    singleSelect:true,
	    queryParams:{
	    	id:id
	    },
	    rownumbers:false,
	    columns:[[
	        {field:'operatorDate',title:'操作时间',width:130},
	        {field:'account',title:'操作账号',width:100},
	        {field:'model',title:'操作模块',width:150},
	        {field:'oldValue',title:'原值',width:100},
	        {field:'newValue',title:'新值',width:100},
	        {field:'description',title:'操作描述',width:200},
	        {field:'remark',title:'备注',width:280}
	    ]]
	});
}

oldOrder.searchList=function(){
	$('#tb_oldOrder').datagrid('load', {
   		userId:$("#userId").combobox('getValue'),
   		status:$("#status").combobox('getValue'),
   		searchText:$("#searchText").textbox('getValue')
	});
}

oldOrder.submit=function(){
	var rows = $('#tb_oldOrder').datagrid('getChecked'); 
	var ids = [];
	for(var i=0;i<rows.length;i++){
    	var row=rows[i];
    	var id=row.id;
    	ids.push(id); 
    }
	if (ids.length==0) {
		$.messager.alert('提示','你还没有选择任何货单！');
		} else {
			$.post(ctx+"/memcached/orderInfo",{ids:ids.toString()},function(data){
				$.messager.alert("提示",data.msg);
				$('#tb_oldOrder').datagrid('reload');
			});
		}
}

