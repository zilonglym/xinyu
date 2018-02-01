$(document).ready(function(){
	items.initTable();
});

var items={};

items.initTable=function(){
	$('#tb_item').datagrid({
	    url:ctx+'/item/listData',
	    height:750,
	    singleSelect:true,
	    rownumbers:true,
	    queryParams:{
	    	userId:$('#userId').combobox('getValue'),
			searchText:$("#searchText").textbox('getValue')
	    },
	    columns:[[
	        {field:'id',hidden:true},
	        {field:'itemName',title:'商品名称',width:260},
	        {field:'itemCode',title:'商品编码',width:120},
	        {field:'sku',title:'商品SKU',width:120},
	        {field:'barCode',title:'商品条码',width:120},
	        {field:'itemType',title:'商品类型',width:100,formatter:function(value,row,index){
	        	if(value=="ZC"){
	        		return "正常商品";
	        	}else if(value=="ZP"){
	        		return "赠品";
	        	}else if(value=="ZH"){
	        		return "组合商品";
	        	}else{
	        		return "其他商品";
	        	}
	        }},
	        {field:'weight',title:'净重量',width:80},
	        {field:'packageWeight',title:'打包重量',width:80},
	        {field:'userName',title:'所属商家',width:150},
	        {field:'operator',title:'操作',width:600,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick=\"items.addBarCode(\'"+value+"'\);\">扫描条码</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.updatePackageWeig(\'"+value+"'\);\">打包重量修改</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.updateItemType(\'"+value+"'\);\">商品类型</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.updateItemTitle(\'"+value+"'\);\">商品名称</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.generateBarCode(\'"+value+"'\);\">生成条码</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.printBarCode(\'"+value+"'\);\">打印条码</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.updateItemCount(\'"+value+"'\);\">增加库存</a>"+
	        	"&nbsp;|&nbsp;<a href='javascript:void(0);' onclick=\"items.updateItemCode(\'"+value+"'\);\">商品编码</a>";
	        }}
	    ]],
	    pagination:true
	});
	
	var pagination =$('#tb_item').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

items.add=function(){
	$('#dialog').dialog({
		title: '新建商品',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/item/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"userId:'"+$("#userId").combobox("getValue")+"',";
                     json=json+"itemCode:'"+$("#itemCode").textbox("getValue")+"',";
                     json=json+"itemName:'"+$("#itemName").textbox("getValue")+"',";
                     json=json+"barCode:'"+$("#barCode").textbox("getValue")+"',";
                     json=json+"specification:'"+$("#specification").textbox("getValue")+"',";
                     json=json+"color:'"+$("#color").textbox("getValue")+"',";
                     json=json+"grossWeight:'"+$("#grossWeight").textbox("getValue")+"',";
                     json=json+"wmsGrossWeight:'"+$("#wmsGrossWeight").textbox("getValue")+"'}";
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/item/save",{json:json},function(data){
                    	 $.messager.progress('close');
                    	 if(data&&data.ret=="rpeat"){
                    	
                    		 $.messager.alert("提示","该商品信息已存在！！！！");
                    	 }else if(data&&data.ret=="success"){
                    		
                    		 $.messager.alert("成功","新建成功！！！！！");
                    		 $('#dialog').window('close');
                    		 $('#tb_item').datagrid('load',{
                    			userId:$('#userId').combobox('getValue'),
                    			searchText:$("#searchText").textbox('getValue')
                    		});
                    	 }else{
                    		
                    		 $.messager.alert("错误","新建失败！！！！！");
                    		 $('#dialog').window('close');
                    		 $('#tb_item').datagrid('load',{
                    			userId:$('#userId').combobox('getValue'),
                    			searchText:$("#searchText").textbox('getValue')
                    		}); 
                    	 }
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});
}

items.addBarCode=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '扫描商品条码',
		    width: 400,
		    height: 200,
		    closed: false,
		    cache: false,
		    href: ctx+'/item/barCodef7Page?itemId='+id,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var itemId=$("#itemId").val();
	                    	var barCode=$("#barCode").val();
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                    	$.post(ctx+"/item/updateBarCode",{itemId:itemId,barCode:barCode},function(data){
	                    		 $.messager.progress('close');
	                    		if(data && data.ret==1){
	                    		
	                    			$.messager.alert('修改成功','商品条码修改成功');
	                    			$('#dialog').window('close');
	                    			$('#tb_item').datagrid('load',{
	                            		userId:$('#userId').combobox('getValue'),
	                            		searchText:$("#searchText").val()
	                            	});
	                    		}
	                    	});
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_item').datagrid('load',{
	                   		userId:$('#userId').combobox('getValue'),
	                		searchText:$("#searchText").val() 		
	                   	});
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
	
}

items.updatePackageWeig=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '设置发货重量',
		    width: 400,
		    height: 200,
		    href: ctx+'/item/tof7PackageWeight?itemId='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var itemId=$("#itemId").val();
	                    	var packageWeight=$("#packageWeight").val();
	                    	var weight=$("#weight").val();
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                    	$.post(ctx+"/item/submitpackageWeight",{itemId:itemId,packageWeight:packageWeight,weight:weight},function(data){
	                    		 $.messager.progress('close');
	                    		if(data && data.ret==1){
									
									$.messager.alert('修改成功','商品发货重量修改成功');
									$('#dialog').window('close');
									$('#tb_item').datagrid('load',{
										userId:$('#userId').combobox('getValue'),
										searchText:$("#searchText").val()									
									});
								}
							});
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	$("#dialog").hide();
	                       $('#dialog').window('close');
	                       $('#tb_item').datagrid('load',{
	                   		userId:$('#userId').combobox('getValue'),
	                   		searchText:$("#searchText").val()	                   		
	                   	});
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
	
}

items.updateItemTitle=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '修改商品名称',
		    width: 400,
		    height: 200,
		    href: ctx+'/item/tof7ItemTitle?itemId='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var itemId=$("#itemId").val();
	                    	var itemTitle=$("#itemTitle").val();
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                    	$.post(ctx+"/item/submitItemTitle",{itemId:itemId,itemTitle:itemTitle},function(data){
	                    		 $.messager.progress('close');
	                    		if(data && data.ret==1){
									
									$.messager.alert('修改成功','商品名称修改成功');
									$('#dialog').window('close');
									$('#tb_item').datagrid('load',{
										userId:$('#userId').combobox('getValue'),
										searchText:$("#searchText").val()										
									});
								}
							});
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	$("#dialog").hide();
	                       $('#dialog').window('close');
	                       $('#tb_item').datagrid('load',{
	                   		userId:$('#userId').combobox('getValue'),
	                   		searchText:$("#searchText").val()	                   		
	                   		});
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}


items.updateItemCount=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '添加商品库存',
		    width: 400,
		    height: 200,
		    href: ctx+'/item/tof7ItemCount?itemId='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var itemId=$("#itemId").val();
	                    	var itemCount=$("#itemCount").val();
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                    	$.post(ctx+"/item/submitItemCount",{itemId:itemId,itemCount:itemCount},function(data){
	                    		 $.messager.progress('close');
	                    		if(data && data.ret==1){
									
									$.messager.alert('添加成功','商品添加库存成功');
									$('#dialog').window('close');
									$('#tb_item').datagrid('load',{
				                   		userId:$('#userId').combobox('getValue'),
				                   		searchText:$("#searchText").val()				                   		
				                   	});
								}else if(data.ret==3){
									
										$.messager.alert('修改失败',data.msg);
										$('#tb_item').datagrid('load',{
					                   		userId:$('#userId').combobox('getValue'),
					                   		searchText:$("#searchText").val()				                   		
					                   	});
								}
							});
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	$("#dialog").hide();
	                       $('#dialog').window('close');
	                       $('#tb_item').datagrid('load',{
		                   		userId:$('#userId').combobox('getValue'),
		                   		searchText:$("#searchText").val()		                   		
		                   	});
	                    }
	                }]
		});
	}else{
	   $.messager.alert("提示","请勾选需要操作的信息！！");
	}
}



items.updateItemCode=function(id){
	if(id){
		$('#dialog').dialog({
		    title: '修改商品编码',
		    width: 400,
		    height: 200,
		    href: ctx+'/item/tof7ItemCode?itemId='+id,
		    closed: false,
		    cache: false,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var itemId=$("#itemId").val();
	                    	var itemCode=$("#itemCode").val();
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                    	$.post(ctx+"/item/submitItemCode",{itemId:itemId,itemCode:itemCode},function(data){
	                    		 $.messager.progress('close');
	                    		if(data && data.ret==1){
								
									$.messager.alert('修改成功','商品编码修改成功');						
									$('#dialog').window('close');
									$('#tb_item').datagrid('load',{
				                   		userId:$('#userId').combobox('getValue'),
				                   		searchText:$("#searchText").val()			                   		
				                   	});
								}
							});
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                    	$("#dialog").hide();
	                       $('#dialog').window('close');
	                       $('#tb_item').datagrid('load',{
		                   		userId:$('#userId').combobox('getValue'),
		                   		searchText:$("#searchText").val()
		                   	
		                   	});
	                    }
	                }]
		});
	}else{
	  $.messager.alert("提示","请勾选需要操作的信息！！");
	}
}

items.searchList=function(){
	$('#tb_item').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		searchText:$("#searchText").textbox('getValue')
	});
}

items.printBarCode=function(id){
	if(id){
		 $.messager.prompt('条码打印', '请输入条码打印数量', function(r){
             if (r){
                 window.open(ctx+"/item/toPoint?itemId="+id+"&count="+r);
             }
         });
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}
items.generateBarCode=function(id){
	if(id){
		 $.messager.confirm('生成条码', '已有条码不能再次生成,是否确认?', function(r){
             if (r){
            	 $.messager.progress({
         		    title: '请稍等',
         		    msg: '数据处理中，请稍等...',
         		    text: '正在处理.......'
         		});
                 $.post(ctx+"/item/generateBarCode",{id:id},function(data){
                	 $.messager.progress('close');
                 	if(data && data.ret==1){
                 	
                 		 $.messager.alert('条码生成','商品条码生成功!');
                 		 $('#tb_item').datagrid('load',{
			                   		userId:$('#userId').combobox('getValue'),
			                   		searchText:$("#searchText").val()                 		
			                   	});
                 	}else{
                 	
                 		$.messager.alert('条码生成','商品已存在条码!');
                 		$('#tb_item').datagrid('load',{
		                   		userId:$('#userId').combobox('getValue'),
		                   		searchText:$("#searchText").val()	                   		
		                   	});
                 	}
                 });
             }
         });
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}


items.updateItemType=function(id){
	$('#dialog').dialog({
	    title: '修改商品类型',
	    width: 400,
	    height: 200,
	    href: ctx+'/item/tof7ItemType?itemId='+id,
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var itemId=$("#itemId").val();
                    	var itemType=$("#itemType").combobox('getValue');
                    	$.post(ctx+"/item/submitItemType",{id:itemId,itemType:itemType},function(data){
							$.messager.alert(data.ret,data.msg);
							$('#tb_item').datagrid("reload");
							$('#dialog').window('close');			
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
