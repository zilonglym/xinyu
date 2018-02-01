$(document).ready(function(){
	checkout.initTable();
});

var checkout={};
checkout.initTable=function(){
	$("#tb_checkout").datagrid({
		url:ctx+"/record/checkout/listData",
		height:850,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		remoteSort:false,
		loadMsg:"正在加载中.......",
		columns:[[
				  {field:'id',title:'唯一标识',width:50,hidden:true},
				  {field:'createDate',title:'出库时间',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						} 
				  },
				  {field:'operator',title:'操作人',width:80,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'user',title:'商家名称',width:100,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'state',title:'目的地',width:100,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'expressCompany',title:'物流公司',width:80,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'expressOrderNo',title:'物流单号',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'barCode',title:'条形码',width:100,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'itemName',title:'出库商品',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'items',title:'订单商品',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'weight',title:'重量（KG）',width:80,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'num',title:'数量',width:100,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'msg',title:'信息',width:200,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
				  },
				  {field:'status',hidden:true}
				]],
		rowStyler:function(index,row){
					if (row.status=="成功"){
						return 'background-color:#53FF53;';
					}else {
						return 'background-color:#FF5151;';
					}
				}
		});
			
			//分页显示插件
			$('#tb_checkout').datagrid('getPager').pagination({
				showPageList:false,
				pageSize:100,
				pageList:[100,200,500],
		        beforePageText: '第',
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
			});

}

checkout.search=function(){
	$("#tb_checkout").datagrid('load',{
		userId:$("#userId").combobox('getValue'),
		status:$("#status").combobox('getValue'),
		sysId:$("#sysId").combobox('getValue'),
		startDate:$("#beigainTime").datetimebox('getValue'),
		endDate:$("#lastTime").datetimebox('getValue'),
		q:$("#q").textbox('getValue')
	});
}

checkout.refresh=function(){
	$("#tb_checkout").datagrid('load',{
		name:null
	});
}

