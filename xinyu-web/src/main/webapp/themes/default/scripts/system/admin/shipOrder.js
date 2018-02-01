$(document).ready(function(){
	shipOrder.initTable();
});

var shipOrder={};
shipOrder.initTable=function(){
	$('#tb_shipOrder').datagrid({
		url:ctx+'/shipOrder/listData',
	    height:750,
	    remoteSort:false,
	    singleSelect:false,
	    queryParams:{
	    	userId:$("#userId").val(),
	    	status:$("#status").val(),
	    	searchType:$("#searchType").combobox('getValue'),
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
	        {field:'erpCode',title:'订单号',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderCode',title:'菜鸟单号',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'shopName',title:'店铺名称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	         {field:'payTime',title:'付款时间',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'operator',title:'操作',width:360,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);'  onclick=\"selectRow(\'"+row.id+"'\);\">修改快递</a>"+"&nbsp;<a href='javascript:void(0);' onclick=\"delTrade(\'"+row.id+"\')\">删除</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick=\"addReturn(\'"+row.id+"\')\">退货</a>"+"&nbsp;<a href='javascript:void(0);' onclick=\"shipOrder.addRemark(\'"+row.id+"\')\">备注</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick=\"spliteTrade(\'"+row.id+"\')\">反审</a>&nbsp<a href='###' onclick=\"invalidTrade(\'"+row.id+"\')\">订单作废</a>"
	        	+" &nbsp;<a href='javascript:void(0);' onclick=\"shipOrder.editReceiverInfo(\'"+row.id+"\')\">修改收件地址</a>";
	        	
	        }}
	       
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
	
	var pagination =$('#tb_shipOrder').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 100,//每页显示的记录条数，默认为10 
		pageList:[100,200,500,1000],
        showPageList: true,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function initShipOrderOperatorList(id){
	$('#tb_operator').datagrid({
		url:ctx+'/shipOrder/operatorListData',
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

shipOrder.searchList=function(){
	$('#tb_shipOrder').datagrid('load', {
   		userId:$("#userId").combobox('getValue'),
   		searchType:$("#searchType").combobox('getValue'),
   		status:$("#status").combobox('getValue'),
   		searchText:$("#searchText").textbox('getValue')
	});
}

/**
 * 修改收件信息
 * */
shipOrder.editReceiverInfo=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '修改收件信息',
		    width: 650,
		    height: 300,
		    href: ctx+'/shipOrder/toEditReceiverInfo?id='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var id = $("#id").val();
	                    	var name = $("#receiverName").textbox('getValue');
	                    	var nick = $("#receiverNick").textbox('getValue');
	                    	var mobile = $("#receiverMobile").textbox('getValue');
	                    	var phone = $("#receiverPhone").textbox('getValue');
	                    	var province = $("#receiverProvince").textbox('getValue');
	                    	var city = $("#receiverCity").textbox('getValue');
	                    	var area = $("#receiverArea").textbox('getValue');
	                    	var town = $("#receiveTown").textbox('getValue');
	                    	var address = $("#receiverAddress").textbox('getValue');
	                    	$.post(ctx+"/shipOrder/saveReceiverInfo",{id:id,name:name,nick:nick,mobile:mobile,phone:phone,province:province,city:city,area:area,town:town,address:address},function(data){
								$.messager.alert('提示',data.msg);
								$('#dialog').window('close');
								$('#tb_shipOrder').datagrid('load', {
							   		userId:$("#userId").combobox('getValue'),
							   		status:$("#status").combobox('getValue'),
							   		searchText:$("#searchText").textbox('getValue')
								});
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
	}else{
		$.messager.alert("提示","请勾选需要操作的订单！");
	}
	
}

/**
 * 修改备注
 * */
shipOrder.addRemark=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '修改备注',
		    width: 310,
		    height: 300,
		    href: ctx+'/shipOrder/tof7Remark?id='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var id=$("#id").val();
	                    	var remark=$("#remark").val();
	                    	$.post(ctx+"/shipOrder/updateRemark",{id:id,remark:remark},function(data){
								if(data && data.ret=="success"){
									$.messager.alert('修改成功','备注修改成功');
									$('#dialog').window('close');
									$('#tb_shipOrder').datagrid('load', {
								   		userId:$("#userId").combobox('getValue'),
								   		status:$("#status").combobox('getValue'),
								   		searchText:$("#searchText").textbox('getValue')
									});
								}else{
									$.messager.alert('修改失败','备注修改失败');
									$('#dialog').window('close');
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
	}else{
		$.messager.alert("提示","请勾选需要操作的订单！");
	}
	
}

/**
 * 修改快递
 * */
function selectRow(id){
	var rows=$('#tb_shipOrder').datagrid("getChecked");
	if(rows==null||rows.length<1){
		$.messager.alert('错误','请选择订单！！');
	}else{
		var ids = [];
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$('#dialog').dialog({
		    title: '修改快递',
		    width: 350,
		    height: 150,
		    href: ctx+'/shipOrder/tof7Express',
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var expressName=$("#expressName").combobox("getValue");
	                    	$.post(ctx+"/shipOrder/updateExpress",{ids:ids,expressName:expressName},function(data){
								if(data && data.ret==1){
									$.messager.alert('修改成功',data.msg);
									$('#dialog').window('close');
									$('#tb_shipOrder').datagrid('load', {
								   		userId:$("#userId").combobox('getValue'),
								   		status:$("#status").combobox('getValue'),
								   		searchText:$("#searchText").textbox('getValue')
									});
								}else{
									$('#dialog').window('close');
									$.messager.alert('修改失败',data.msg);
									$('#tb_shipOrder').datagrid('load', {
								   		userId:$("#userId").combobox('getValue'),
								   		status:$("#status").combobox('getValue'),
								   		searchText:$("#searchText").textbox('getValue')
									});
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
	
	
}

/**
 * 订单反审
 * */
function spliteTrade(id){
	var rows=$('#tb_shipOrder').datagrid("getChecked");
	if(rows==null||rows.length<1){
		$.messager.alert('错误','请选择订单！！');
	}else{
		var ids = [];
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].id);
		}
		$.post(ctx+"/shipOrder/ajax/resetAudit",{ids:ids},function(data){
			if(data && data.ret=="success"){
				$.messager.alert("提示",data.msg);
				$('#tb_shipOrder').datagrid('load', {
			   		userId:$("#userId").combobox('getValue'),
			   		status:$("#status").combobox('getValue'),
			   		searchText:$("#searchText").textbox('getValue')
				});
			}else if(data && data.ret=="fail"){
				$.messager.alert("提示",data.msg);
				$('#tb_shipOrder').datagrid('load', {
			   		userId:$("#userId").combobox('getValue'),
			   		status:$("#status").combobox('getValue'),
			   		searchText:$("#searchText").textbox('getValue')
				});
			}else{
				$.messager.alert("提示","订单反审失败，请联系系统管理人员！");
				$('#tb_shipOrder').datagrid('load', {
			   		userId:$("#userId").combobox('getValue'),
			   		status:$("#status").combobox('getValue'),
			   		searchText:$("#searchText").textbox('getValue')
				});
			}
		});
		
	}	
}

/**
 * 订单删除
 * */
function delTrade(id){
	var row=$("#tb_shipOrder").datagrid("getSelected");
	//删除的单 据必须是没有订单号的。
	var express=row.orderNo;
	if(express!='' && express!=null){
		$.messager.alert("提示","该单据有运单号，请先取消再来删除!");
	}else{
		$.post(ctx+"/shipOrder/deleteShipOrder",{id:row.id},function(data){
			if(data && data.ret==1){
				$.messager.alert("提示","订单删除成功!");
				$('#tb_shipOrder').datagrid('load', {
			   		userId:$("#userId").combobox('getValue'),
			   		status:$("#status").combobox('getValue'),
			   		searchText:$("#searchText").textbox('getValue')
				});
			}else{
				$.messager.alert("提示","删除失败,请联系管理员!");
			}
		});
	}
}

/**
 * 订单作废
 * */
function invalidTrade(id){
	var row = $("#tb_shipOrder").datagrid("getSelected");	
	$.messager.confirm('订单作废确认', '是否作废此订单,此操作不可撤消?', function(row){
        if (row){
            $.post(ctx+"/shipOrder/invalidShipOrder",{id:id},function(data){
				if(data && data.ret==1){
					$.messager.alert("提示","订单作废成功!");
					$('#tb_shipOrder').datagrid('load', {
				   		userId:$("#userId").combobox('getValue'),
				   		status:$("#status").combobox('getValue'),
				   		searchText:$("#searchText").textbox('getValue')
					});
				}else{
					$.messager.alert('作废失败',data.msg);
				}
			});
        }
    });
}
/**
 * 订单退货
 * */
function addReturn(id){
	var row=$("#tb_shipOrder").datagrid("getSelected");
	$.messager.confirm('退货确认', '是否确认退货,此操作不可撤消?', function(row){
        if (row){
            $.post(ctx+"/shipOrder/addReturn",{id:id},function(data){
            	$.messager.alert('退货结果',data.msg);
            });
        }
    });
}

shipOrder.submit=function(){
	var rows = $('#tb_shipOrder').datagrid('getChecked'); 
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
				$('#tb_shipOrder').datagrid('reload');
			});
		}
}

