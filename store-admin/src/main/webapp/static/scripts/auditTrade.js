$(document).ready(function(){
	audit.initTable();
});

var audit={};
audit.initTable=function(){ 
	var editRow = undefined;
	$('#auditTable').datagrid({
	    url:ctx+'/trade/ajax/waits',
	    height:850,
	    queryParams:{
	    	userId:$("#selectUser").combobox('getValue')
	    },
	    remoteSort:false,
	    rownumbers:true,
	    pagination:true,
	    columns:[[
	    	{field:'val',checkbox:true},
	        {field:'shopName',title:'商铺',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'logisticsCode',title:'物流公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'buyerNick',title:'昵称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'phone',title:'联系手机',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'address',title:'地址',width:450,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'items',title:'商品',width:220,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	    	},
	        {field:'weight',title:'重量',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'buyerMessage',title:'买家备注',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'sellerMessage',title:'卖家备注',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'payDate',title:'日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        },
	        {field:'tradeOrder',title:'订单号',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					}  
	        }
	    ]],
	    rowStyler:function(index,row){
			if (row.sellerFlag=="1"){
				return 'background-color:red;';
			}else if(row.sellerFlag=="3"){
				return 'background-color:yellow;';
			}else if(row.sellerFlag=="4"){
				return 'background-color:green;';
			}else if(row.sellerFlag=="ZTO"){
				return 'background-color:blue;';
			}else if(row.sellerFlag=="5"){
				return 'background-color:green;';
			}else if(row.sellerFlag=="2"){
				return 'background-color:purple;';
			}
		}
//	    onDblClickRow:function(index,row){
//	    	var id=row.val;
//			$('#dialog').dialog({
//				title: '选择物流公司',
//			    width: 400,
//			    height: 150,
//			    closed: false,
//			    cache: false,
//			    href: ctx+'/trade/toSelect/setExpressCompany',
//			    modal: false,
//			    buttons: [{
//		                    text:'确定',
//		                    iconCls:'icon-ok',
//		                    handler:function(){
//		                    	var json="{";
//		                        json=json+"id:'"+id+"',";
//		                        json=json+"company:'"+$("#selectCompany").combobox("getValue")+"'}";
//		                     $.post(ctx+"/trade/company/save",{json:json},function(data){
//		                    	 if(data&&data.ret=="success"){
//		                    		 $('#dialog').window('close');
//		                    		 $('#auditTable').datagrid('updateRow',{
//		                    			 index:index,
//		                    			 row:{
//		                    				 sellerFlag:$("#selectCompany").combobox('getValue')
//		                    			 }
//		                    		 });
//		                    	 }else{
//		                    		 alert("操作失败！");
//		                    	 }
//		                     }); 
//		                    }
//		                },{
//		                    text:'取消',
//		                    handler:function(){
//		                       $('#dialog').window('close');
//		                    }
//		                }]
//			});
//		}
 	});
	
	var pagination =$('#auditTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 500,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

audit.searchList=function(){
	$('#auditTable').datagrid('load', {
   		 userId:$("#selectUser").combobox('getValue'),
   		 q:$("#q").textbox('getValue'),
   		 others:$("#others").combobox('getValue'),
   		 state:$("#selectState").combobox('getValue'),
   		 beigainTime:$('#beigainTime').datetimebox('getValue'),
   		 weight_x:$('#weight_x').combobox('getValue'),
   		 weight:$("#weight").textbox('getValue'),
		 lastTime:$('#lastTime').datetimebox('getValue'),
		 company:$('#selectCompany').combobox('getValue')
	});
}
audit.auditTrade=function(){
	//获取选中的ID
	var rows=$('#auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单审核','请选择要审核的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.val+",";
	}
	var expressId = $('#expressCompany').val();
	if(expressId==-1){
		$.messager.alert('订单审核','请选择要审核的快递公司!');
		return;
	}
	$.post(ctx+"/trade/mkships",{'tradeIds':ids,'expressCompany':expressId},function(data){
		if(data && data.ret==1){
			$('#myModal').hide();
			$('.modal-backdrop').css("display","nones");
			$('#auditTable').datagrid('reload');   
		}else{
			 $.messager.alert('审核失败','订单审核失败,请联系管理员!');
		}
	});
}

audit.splitTrade=function(){
	//获取选中的ID
	var rows=$('#auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单拆分','请选择要拆分的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.val+",";
	}
	  $.messager.progress({
		    title: '请稍等',
		    msg: '数据处理中，请稍等...',
		    text: '正在处理.......'
		});
	$.post(ctx+"/trade/ajax/batchSplit",{'tradeIds':ids,'type':$('#type').val(),'num':$('#num').val()},function(data){
		 $.messager.progress('close');
		if(data && data.ret==1){
			$('#mySplitModal').hide();
			$('.modal-backdrop').css("display","nones");
			$('#auditTable').datagrid('reload');   
		}else{
			 $.messager.alert('拆单失败','拆单失败,请联系管理员!');
		}
	});
}


audit.splite=function(orderId){
	    var url1=ctx+"/store/shipOrder/f7Split?id="+orderId;
		$('#dialog').dialog({
			title: '拆单信息信息',
		    width: 1000,
		    height: 500,
		    closed: false,
		    cache: false,
		    href:url1,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                   	handler:function(){
	                   		$.messager.confirm('拆单确认', '是否确认拆单,此操作不可撤消?', function(r){
                             if (r){
                   		        $('#dialog').window('close');
	                    		var json="[";
							    $("tr[key='demo']").each(function(){
							    var row=$(this);
								json=json+"{";
								json=json+"detailId:'"+$($(row).find("td")[0]).find("input").val()+"',";
								json=json+"quantity:'"+$($(row).find("td")[3]).find("input").val()+"'},";
								});
								var  date = "{id:"+$("#shipOrderId").val()+",date:"+json.substr(0,json.length -1) +"]}";
		   						 date  = JSON.stringify(date); 
		   						$.messager.progress({
		   						    title: '请稍等',
		   						    msg: '数据处理中，请稍等...',
		   						    text: '正在处理.......'
		   						});             
	                    	    $.post(ctx+"/store/shipOrder/splitShipOrder",{json:date},function(ret){
	                    	    	$.messager.progress('close');
	                    			$.messager.alert('拆单结果',ret.msg);
	                    			audit.searchList();
	                    		 }); 
	                  	     }  
          		           });
          		           }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	alert("系统异常");
	                       $('#dialog').window('close');
	                       
	                    }
	                }]
		});
}
