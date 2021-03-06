$(document).ready(function(){
	shipOrder.initTable();
});
var shipOrder={};
shipOrder.initTable=function(){
	$('#tb_shipOrder').datagrid({
		url:ctx+"/wayBill/waitsOk/listData",
		height:850,
		loadMsg:"正在加载中.......",
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		fitColumns:true,
		remoteSort:false,
		columns:[[
		  {field:'ck',checkbox:true},
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'tradeId',title:'订单标识',width:100,hidden:true},
		  {field:'printDate',title:'打印时间',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'printBatch',title:'打印批次号',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'userName',title:'商家名',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'items',title:'商品明细',width:200,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'receiverName',title:'收件人',width:80,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'receiverPhone',title:'联系电话',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'address',title:'目的地',width:250,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'expressName',title:'物流公司',width:60,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'expressOrderNo',title:'物流单号',width:110,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		   {field:'createDate',title:'创建日期',width:120,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'operator',title:'操作',width:100,formatter:function(value,row,index){
			  var url=ctx+"/waybill/preview?ids=";
			  return "<a href='#' onClick='printOne(this);' cp_code='"+row.expressName+"' ttt='"+row.tradeId+"' key='"+url+"'>打印</a>|<a href='#' onClick='cancelOne("+row.tradeId+");'>取消</a>";
		  }}
		]]
	});
	
	$('#tb_shipOrder').datagrid('getPager').pagination({
		showPageList:true,
		pageSize: 100,
		pageList:[100,200,500,1000],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

shipOrder.search=function(){
	$('#tb_shipOrder').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		code:$('#sysId').combobox('getValue'),
		q:$('#q').textbox('getValue'),
		beigainTime:$('#beigainTime').datetimebox('getValue'),
		lastTime:$('#lastTime').datetimebox('getValue')
	});
}

shipOrder.refresh=function(){
	$('#tb_shipOrder').datagrid('load',{
		name:null
	});

}
