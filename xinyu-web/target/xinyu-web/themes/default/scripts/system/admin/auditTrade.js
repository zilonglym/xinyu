$(document).ready(function(){
	if(userId!=null && userId.length>0)	
    $('#selectUser').combobox('select', userId);
	audit.initTable();
	
});

var audit={};
audit.initTable=function(){ 
	$('#tb_auditTable').datagrid({
		url:ctx+'/shipOrder/waits/listData',
	    height:750,
	    queryParams:{
	    	 userId:$("#selectUser").combobox('getValue'),
	   		 q:$("#q").textbox('getValue'),
	   		 searchType:$("#searchType").textbox('getValue'),
	   		 others:$("#others").combobox('getValue'),
	   		 state:$("#selectState").combobox('getValue'),
	   		 beigainTime:$('#beigainTime').datetimebox('getValue'),
	   		 weight_x:$('#weight_x').combobox('getValue'),
	   		 weight:$("#weight").textbox('getValue'),
			 lastTime:$('#lastTime').datetimebox('getValue'),
			 company:$('#selectCompany').combobox('getValue')
	    },
	    rownumbers:true,
	    pagination:true,
	    remoteSort:false,
	    columns:[[
	    	{field:'tmsId',hidden:true},
	    	{field:'id',checkbox:true},
	        {field:'userName',title:'商家',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	    	},
	    	 {field:'shopName',title:'店铺',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	    	},
	        {field:'expressCode',title:'物流公司',width:80,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'nick',title:'昵称',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'phone',title:'联系手机',width:80,sortable:true,
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
	        {field:'tmsWeight',title:'重量',width:60,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'buyerMemo',title:'买家备注',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'sellerMemo',title:'卖家备注',width:100,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'createDate',title:'日期',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'orderCode',title:'菜鸟单号',width:120,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'payTime',title:'付款时间',width:150,sortable:true,
				  sorter:function(a,b){
						return (a>b?1:-1);
					} 
	        },
	        {field:'status',hidden:true},
	        {field:'operate',title:'操作',width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);'  onclick=\"editAddress(\'"+row.id+"'\);\">修改收件地址</a>";
	        }},
	    ]],
	    rowStyler: function(index,row){
			if (row.status =='WMS_DELETED'){
				return 'background-color:#6293BB;color:#fff;font-weight:bold;';
			}else if(row.status =='WMS_CANCEL'){
				return 'background-color:red;color:#fff;font-weight:bold;';
			}
		}
 	});
	var pagination = $('#tb_auditTable').datagrid('getPager');
	$(pagination).pagination({
		pageSize: 500,//每页显示的记录条数，默认为10 
		pageList:[100,200],
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
}

function editAddress(id){
	if(id){
		$('#dialog').dialog({
			title:'修改地址',
			width:650,
			height:300,
			href: ctx+'/shipOrder/toEditReceiverInfo?id='+id,
			closed: false,
			cache: false,
			modal: true,
			buttons:[{
                text:'确认',
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
                    $.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                 $.post(ctx+"/shipOrder/saveReceiverInfo",{id:id,name:name,nick:nick,mobile:mobile,phone:phone,province:province,city:city,area:area,town:town,address:address},function(data){
                	 $.messager.progress('close');
                	 $.messager.alert('提示',data.msg);
                	 $('#dialog').window('close');
                 }); 
                }        
			},{
                text:'取消',
                iconCls:'icon-closed',
                handler:function(){
                	 $.messager.progress('close');
                	$('#dialog').window('close');
                }        				
			}]
		});
	}
}

audit.searchList=function(){
	$('#tb_auditTable').datagrid('load', {
		 userId:$("#selectUser").combobox('getValue'),
   		 q:$("#q").textbox('getValue'),
   		 searchType:$("#searchType").textbox('getValue'),
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
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单审核','请选择要审核的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.tmsId+",";
	}
	var expressId = $('#expressCompany').val();
	if(expressId==-1){
		$.messager.alert('订单审核','请选择要审核的快递公司!');
		return;
	}
	$.post(ctx+"/shipOrder/mkships",{'tradeIds':ids,'expressCompany':expressId},function(data){
		if(data && data.ret==1){
			$('#myModal').hide();
			$('.modal-backdrop').css("display","nones");
			$('#tb_auditTable').datagrid('load', {
				 userId:$("#selectUser").combobox('getValue'),
		   		 q:$("#q").textbox('getValue'),
		   		 searchType:$("#searchType").textbox('getValue'),
		   		 others:$("#others").combobox('getValue'),
		   		 state:$("#selectState").combobox('getValue'),
		   		 beigainTime:$('#beigainTime').datetimebox('getValue'),
		   		 weight_x:$('#weight_x').combobox('getValue'),
		   		 weight:$("#weight").textbox('getValue'),
				 lastTime:$('#lastTime').datetimebox('getValue'),
				 company:$('#selectCompany').combobox('getValue')
			});
		}else{
			 $.messager.alert('审核失败','订单审核失败,请联系管理员!');
		}
	});
}

audit.splitTrade=function(){
	//获取选中的ID
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单拆分','请选择要拆分的订单!!');
		return;
	}else{
		var ids="";
		for(var i=0;i<rows.length;i++){
			var obj=rows[i];
			ids+=obj.val+",";
		}	
		$('#dialog').dialog({
			title: '拆单信息信息',
		    width: 400,
		    height: 200,
		    closed: false,
		    cache: false,
		    href:ctx+"/shipOrder/ajax/batchSplit",
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                   	handler:function(){
	                   		$.post(ctx+"/shipOrder/ajax/batchSplitData",{'tradeIds':ids,'type':$('#type').val(),'num':$('#num').val()},function(data){
	                			if(data && data.ret==1){
	                				$.messager.alert('拆单','拆单成功!');
	                				$('#dialog').window('close');
									$('#tb_auditTable').datagrid('load', {
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
	                			}else{
	                				$.messager.alert('拆单','拆单失败,请联系管理员!');
	                				$('#dialog').window('close');
	                			}
	                		});
	                   	}
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $.messager.alert("错误","系统异常");
	                       $('#dialog').window('close');   
	                    }
	                }]
		});
	}
}

audit.splite=function(){
		var row=$('#tb_auditTable').datagrid("getSelected");
	    var url1=ctx+"/shipOrder/f7Split?id="+row.id;
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
		   						              
	                    	    $.post(ctx+"/shipOrder/splitShipOrder",{json:date},function(ret){
	                    			$.messager.alert('拆单结果',ret.msg);
	                    			audit.searchList();
	                    		 }); 
	                  	     }  
          		           });
          		           }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	 $.messager.alert("错误","系统异常");
	                       $('#dialog').window('close');
	                       
	                    }
	                }]
		});
}

function selectRow(){
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null||rows.length<1){
		$.messager.alert('错误','请选择订单！！');
	}else{
		var ids = [];
		for(var i=0; i<rows.length; i++){
			ids.push(rows[i].tmsId);
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
	                    	var expressName = $("#expressName").combobox('getValue');
	                    	$.post(ctx+"/shipOrder/mkships",{"ids":ids,expressName:expressName},function(data){
								if(data && data.ret==1){
									$.messager.alert('修改成功',data.msg);
									$('#dialog').window('close');
									$('#tb_auditTable').datagrid('load', {
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
								}else{
									$('#dialog').window('close');
									$.messager.alert('修改失败',data.msg);
									$('#tb_auditTable').datagrid('load', {
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

audit.auditSfArea=function(){
	//获取选中的ID
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单审核','请选择要审核的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.tmsId+",";
	}
	window.open(ctx+"/shipOrder/toAuditArea?ids="+ids);
}

audit.addSplit=function(){
	//获取选中的ID
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单拆单核','请选择要拆单的订单!!');
		return;
	}
	if(rows.length!=1){
		$.messager.alert('订单拆单核','只能对单一订单进行操作!!');
		return;
	}
	var  id  = rows[0].tmsId;
	$("#dialog").dialog({
		title: '拆单信息',
	    width: 900,
	    height: 600,
	    resizable: true,
	    closed: false,
	    cache: false,
	    draggable: true,
	    shadow: true,
	    href: ctx+'/shipOrder/toSplitOrder',
	    modal: true,
	    onLoad:function() {
	    	initSplitTable(id);
        },
	    buttons: [{
                    text:'按数量拆单',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var rows=$('#splitTable').datagrid("getChecked");
                 		var json="[";
						for(var i=0;i<rows.length;i++){
								var  obj = rows[i];
							    json=json+"{";
								json=json+"detailId:'"+obj.id+"',";
								json=json+"quantity:'"+$("#split"+obj.id).val()+"'},";
						}
						var  date = "{id:"+id+",date:"+json.substr(0,json.length -1) +"]}";
		   				date  = JSON.stringify(date); 
	               		$.post(ctx+"/shipOrder/splitShipOrder",{json:date},function(ret){
	               			
	                          $.messager.alert('拆单结果',ret.msg);
	                          if(ret.code=='200'){
	                             $('#dialog').window('close');
	                             audit.searchList();
	                          }
	                    }); 
					 }}, 
					 {
                    text:'按行拆单',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var rows=$('#splitTable').datagrid("getChecked");
                 		var json="[";
						for(var i=0;i<rows.length;i++){
								var  obj = rows[i];
							    json=json+"{";
								json=json+"detailId:'"+obj.id+"'},";
						}
						var  date = "{id:"+id+",date:"+json.substr(0,json.length -1) +"]}";
		   				date  = JSON.stringify(date); 
	               		$.post(ctx+"/shipOrder/splitShipOrderLine",{json:date},function(ret){
	                          $.messager.alert('拆单结果',ret.msg);
	                          if(ret.code=='200'){
	                             $('#dialog').window('close');
	                             audit.searchList();
	                          }
	                    }); 
					 }}, 
					      
                   {
                    text:'取消',
                    handler:function(){
                        $('#dialog').window('close');
                    }
                }]	
	});
}

function initSplitTable(id){ 
	var editRow = undefined;
	$('#splitTable').datagrid({
	    url:ctx+'/shipOrder/ajax/shipOrderdata',
	    height:510,
	    queryParams:{
	    	 tmsId:id
	    },
	    columns:[[
	    	{field:'id',checkbox:true},
	        {field:'title',title:'商品名称',width:200},
	        {field:'num',title:'数量',width:80},
	        {field:'msg',title:'拆分数量',width:180,
	        	formatter: function(value,row,index){
				   return "<input type='text'  id='split"+row.id+"' value=''/>";
				}
			}
	    ]]
	});
	}	


audit.auditYUNDAArea=function(typeStr){
	//获取选中的ID
	var rows=$('#tb_auditTable').datagrid("getChecked");
	if(rows==null || rows==''){
		$.messager.alert('订单审核','请选择要审核的订单!!');
		return;
	}
	var ids="";
	for(var i=0;i<rows.length;i++){
		var obj=rows[i];
		ids+=obj.tmsId+",";
	}
	window.open(ctx+"/shipOrder/toAuditAreaYUNDA/"+typeStr+"?ids="+ids);
}
