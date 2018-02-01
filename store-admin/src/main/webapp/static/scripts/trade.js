

var trade={};

trade.initTable=function(){
	$('#contentTable').datagrid({
	    url:ctx+'/wayBill/listData',
	    height:850,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	q:$("#q").val()
	    },
	    rownumbers:true,
	    remoteSort:false,
	    columns:[[
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'printBatch',title:'打印批次号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'shopName',title:'商铺名称',width:180,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'items',title:'商品',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'buyerNick',title:'昵称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'receiverName',title:'收件人',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'phone',title:'联系方式',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'addressInfo',title:'目的地',width:400,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressCompany',title:'快递公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressOrderno',title:'运单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'remark',title:'备注',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'operator',title:'操作',width:180,formatter:function(value,row,index){
	        	return "<a  data-toggle='modal' href='#myModal'  onclick='selectRow("+value+");'>修改快递</a> <a href='javascript:void(0);' onclick='delTrade("+value+")'>删除</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick='addReturn("+value+")'>退货</a>"+"&nbsp;<a href='javascript:void(0);' onclick='trade.addRemark("+value+")'>备注</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick='spliteTrade("+value+")'>反审</a>&nbsp<a href='###' onclick='invalidTrade("+value+")'>订单作废</a>&nbsp";
	        	
	        }},
	        {field:'status',title:'',width:0}
	    ]],
	    rowStyler: function(index,row){
					if (row.status =='WMS_DELETED'){
						return 'background-color:#6293BB;color:#fff;font-weight:bold;';
					}
				},
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




trade.initOldTable=function(){
	$('#contentTable').datagrid({
	    url:ctx+'/wayBill/oldListData',
	    height:850,
	    singleSelect:true,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	q:$("#q").val()
	    },
	    rownumbers:true,
	    remoteSort:false,
	    columns:[[
	        {field:'createDate',title:'创建日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'printBatch',title:'打印批次号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'shopName',title:'商铺名称',width:180,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'items',title:'商品',width:250,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'buyerNick',title:'昵称',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'receiverName',title:'收件人',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'phone',title:'联系方式',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'addressInfo',title:'目的地',width:400,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressCompany',title:'快递公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'expressOrderno',title:'运单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'remark',title:'备注',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'operator',title:'操作',width:180,formatter:function(value,row,index){
	        	return "<a  data-toggle='modal' href='#myModal'  onclick='selectRow("+value+");'>修改快递</a> <a href='javascript:void(0);' onclick='delTrade("+value+")'>删除</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick='addReturn("+value+")'>退货</a>"+"&nbsp;<a href='javascript:void(0);' onclick='trade.addRemark("+value+")'>备注</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick='spliteTrade("+value+")'>反审</a>&nbsp<a href='###' onclick='invalidTrade("+value+")'>订单作废</a>&nbsp";
	        	
	        }},
	        {field:'status',title:'',width:0}
	    ]],
	    rowStyler: function(index,row){
					if (row.status =='WMS_DELETED'){
						return 'background-color:#6293BB;color:#fff;font-weight:bold;';
					}
				},
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



trade.addRemark=function(id){
	$('#dialog').dialog({
	    title: '修改备注',
	    width: 310,
	    height: 300,
	    href: ctx+'/waybill/tof7Remark?tradeId='+id,
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var orderId=$("#orderId").val();
                    	var remark=$("#remark").textbox("getValue");
                    	$.post(ctx+"/waybill/submitRemark",{orderId:orderId,remark:remark},function(data){
							if(data && data.ret==1){
								$.messager.alert('修改成功','备注修改成功');
								$('#dialog').window('close');
								$('#contentTable').datagrid('reload');
							}
						});
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $("#dialog").hide();
                       $('#dialog').window('close');
                    }
                }]
	});
}

trade.searchList=function(){
	$('#contentTable').datagrid('load', {
   		 userId:$("#userId").combobox('getValue'),
   		 q:$("#q").textbox('getValue')
	});
}
