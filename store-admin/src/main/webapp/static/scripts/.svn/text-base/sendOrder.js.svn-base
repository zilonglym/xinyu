
	$(document).ready(function(){
		sendOrder.initTable();
	});	
	var sendOrder={};
	sendOrder.initTable=function(){
		$('#tb_sendOrder').datagrid({
			url:ctx+'/trade/send/waits/listData',
			height:850,
			singleSelect:true,
			rownumbers:true,
			remoteSort:false,
			columns:[[
			    {field:'id',title:'ID',width:120,hidden:true},
				{field:'createDate',title:'创建日期',width:120,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'shopName',title:'商铺名称',width:180,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'items',title:'收件人',width:250,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'buyerNick',title:'昵称',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'receiverName',title:'联系方式',width:150,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'phone',title:'出库单号',width:100,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'addressInfo',title:'物流公司',width:400,sortable:true,
					  sorter:function(a,b){
							return (a>b?1:-1);
						}  
			    },
				{field:'operator',title:'操作',width:120,formatter:function(value,row,index){
					var url=ctx+'/trade/send/do/'+row.id;
					return "<a href='"+url+"' rel='tooltip' title='手工设置已发货运单号。（建议使用批量打单系统）'>设置运单号</a>";
				}}
			]],
			pagination:true 
		});
		
		var pagination =$('#tb_sendOrder').datagrid('getPager');
		$(pagination).pagination({
			pageSize: 500,//每页显示的记录条数，默认为10 
			pageList:[100,200],
	        showPageList: false,
	        beforePageText: '第',//页数文本框前显示的汉字 
	        afterPageText: '页    共 {pages} 页', 
	        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
		});
	}
	
	sendOrder.search=function(){
		$('#tb_sendOrder').datagrid('load', {
	   		 userId:$("#selectUser").combobox('getValue'),
	   		 cpCode:$("#selectCompany").combobox('getValue'),
	   		 q:$("#q").textbox('getValue')
		});
	}