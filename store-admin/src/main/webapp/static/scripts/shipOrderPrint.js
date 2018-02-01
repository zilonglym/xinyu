$(document).ready(function(){
	shipOrder.initTable();
});
var shipOrder={};
shipOrder.initTable=function(){
	$('#tb_shipOrder').datagrid({
		url:ctx+"/wayBill/listPrintData",
		height:850,
		loadMsg:"正在加载中.......",
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		remoteSort:false,
		columns:[[
		  {field:'ck',checkbox:true},
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'tradeId',title:'订单标识',width:100,hidden:true},
		  {field:'createDate',title:'时间',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'sellerMessage',title:'卖家备注',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'buyerMessage',title:'买家备注',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'userName',title:'商家名',width:150,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'items',title:'商品明细',width:250,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'weight',title:'重量',width:60,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'receiverName',title:'收件人',width:100,sortable:true,
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
		  {field:'expressName',title:'物流公司',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  },
		  {field:'orderNo',title:'物流单号',width:100,sortable:true,
			  sorter:function(a,b){
					return (a>b?1:-1);
				}  
		  }
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

shipOrder.refresh=shipOrder.search=function(){
	$('#tb_shipOrder').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		q:$('#q').textbox('getValue'),
		beigainTime:$('#beigainTime').datetimebox('getValue'),
		lastTime:$('#lastTime').datetimebox('getValue'),
		sysId:$('#sysId').combobox('getValue'),
		receiveState:$('#receiveState').combobox('getValue'),
		txtno:$('#txtno').textbox('getValue')
	});
}
/*
shipOrder.refresh=function(){
	$('#tb_shipOrder').datagrid('load',{
		name:null
	});
}
	*/
shipOrder.sort=function(){
	var userId=$('#userId').combobox('getValue');
	var q=$('#q').textbox('getValue');
	var beigainTime=$('#beigainTime').datetimebox('getValue');
	var lastTime=$('#lastTime').datetimebox('getValue');
	var sysId=$('#sysId').combobox('getValue');
	var json="{";
	json=json+"'userId':'"+userId+"',";
	json=json+"'sysId':'"+sysId+"',";
	json=json+"'q':'"+q+"',";
	json=json+"'beigainTime':'"+beigainTime+"',";
	json=json+"'lastTime':'"+lastTime+"'}";
	$.ajax({
		url:ctx+"/wayBill/sort",
		type:"POST",
		data:{json:json},
		dataType:"json",
		sunccess:function(data){
			console.info(data.msg);
		},
		error: function () {       
             alert("操作失败！");
        }       
	});
}
