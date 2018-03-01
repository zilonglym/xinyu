$(document).ready(function(){
	localItem.initTable();
});
var localItem = {};
localItem.initTable = function(){
	$('#tb_localItem').datagrid({
	    url:ctx+'/pcLocal/localItem/listData',
	    height:850,
	    singleSelect:true,
	    rownumbers:true,
	    columns:[[
	    		  {field:'id',checkbox:true},
	    		  {field:'shopName',title:'商家名称',width:100},
	    		  {field:'name',title:'商品名称',width:250},
	    		  {field:'sku',title:'商品属性',width:80},
	    		  {field:'barCode',title:'商品条码',width:100},
	    		  {field:'source',title:'商品来源',width:80,formatter:function(value,row,index){
	    			  if(row.source=='qimen'){
	    				  return "奇门";
	    			  }else if(row.source=='cainiao'){
	    				  return "菜鸟";
	    			  }else{
	    				  return "导入";
	    			  }
	    		  }},
	    		  {field:'itemType',title:'商品类型',width:100,formatter:function(value,row,index){
	    			  if(row.itemType=='ZC'){
	    				  return "正常商品";
	    			  }else if(row.itemType=='ZP'){
	    				  return "赠品/配件";
	    			  }else{
	    				  return "其他商品";
	    			  }
	    		  }},
	    		  {field:'num',title:'标板数量',width:60},
	    		  {field:'highNum',title:'自定义数量',width:60},
	    		  {field:'operate',title:'操作',formatter:function(value,row,index){
	    			  return "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>"+"&nbsp;<a href='javascript:void(0);' onclick='batch("+row.id+")'>打印条码</a>";
	    		  }}
	    		]],
	    pagination:true
	});
	
	var pagination =$('#tb_localItem').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

localItem.search = function(){
	$('#tb_localItem').datagrid('load',{
		shopId:$('#shopId').combobox('getValue'),
		q:$('#q').textbox('getValue')
	});
}

localItem.add = function(){
		$('#dialog').dialog({
			title:'新建商品',
			width:400,
			height:500,
			closed:false,
			cache:false,
			href:ctx+'/pcLocal/localItem/toAddItem',
			modal:true,
			buttons:[{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                 var json="{";
                 json=json+"id:'"+$("#id").val()+"',";
                 json=json+"name:'"+$("#itemName").textbox("getValue")+"',";
                 json=json+"sku:'"+$("#sku").textbox("getValue")+"',";
                 json=json+"barCode:'"+$("#barCode").textbox("getValue")+"',";
                 json=json+"shopId:'"+$("#shop").combobox("getValue")+"',";
                 json=json+"itemType:'"+$("#itemType").combobox("getValue")+"',";
                 json=json+"lowNum:'"+$("#lowNum").textbox("getValue")+"',";
                 json=json+"highNum:'"+$("#highNum").textbox("getValue")+"',"; 
                 json=json+"source:'"+$("#source").combobox("getValue")+"'}"; 
                 $.post(ctx+"/pcLocal/localItem/saveItem",{json:json},function(data){
                	$.messager.alert('提示',data.msg);
                	$('#dialog').window('close');
                	$('#tb_localItem').datagrid('reload');
                 });
                }
            }],
		});
}

function edit(id){
	if(id){
		$('#dialog').dialog({
			title:'修改商品',
			width:400,
			height:500,
			closed:false,
			cache:false,
			href:ctx+'/pcLocal/localItem/toEditItem?id='+id,
			modal:true,
			buttons:[{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                 var json="{";
                 json=json+"id:'"+id+"',";
                 json=json+"name:'"+$("#itemName").textbox("getValue")+"',";
                 json=json+"sku:'"+$("#sku").textbox("getValue")+"',";
                 json=json+"barCode:'"+$("#barCode").textbox("getValue")+"',";
                 json=json+"itemType:'"+$("#itemType").combobox("getValue")+"',";
                 json=json+"shopId:'"+$("#shop").combobox("getValue")+"',";
                 json=json+"lowNum:'"+$("#lowNum").textbox("getValue")+"',";
                 json=json+"highNum:'"+$("#highNum").textbox("getValue")+"',";
                 json=json+"source:'"+$("#source").combobox("getValue")+"'}";                     
                 $.post(ctx+"/pcLocal/localItem/saveItem",{json:json},function(data){
                	$.messager.alert('提示',data.msg);
                	$('#dialog').window('close');
                	$('#tb_localItem').datagrid('reload');
                 });
                }
            }],
		});
	}else{
		$.messager.alert('提示','请勾选需要修改的商品！');
	}
}

function del(id){
	if(id){
		$.post(ctx+"/pcLocal/localItem/deleteItem",{id:id},function(data){
	     	$.messager.alert('提示',data.msg);
	     	$('#dialog').window('close');
	     	$('#tb_localItem').datagrid('reload');
	    });
	}else{
		$.messager.alert('提示','请勾选需要删除的商品！');
	}
}


function batch(id){
	if(id){
		$('#dialog').dialog({
			title:'打印商品条码',
			width:400,
			height:500,
			closed:false,
			cache:false,
			href:ctx+'/pcLocal/localBatch/toPrint?id='+id,
			modal:true,
			buttons:[{
                text:'确认',
                iconCls:'icon-ok',
                handler:function(){ 
                	var id = $('#id').val();  
                	var anum = $('#anum').textbox('getValue');  
                	var isHigh = $('#isHigh').combobox('getValue');  
                	var code = $('#code').textbox('getValue');  
                	var cnum = $('#cnum').textbox('getValue');  
                	var birthDate = $('#birthDate').datetimebox('getValue'); 
                	if(code == "" || code == null || code == undefined){
                		$.messager.alert("提示","入库单号不能为空");
                	}else if(anum == "" || anum == null || anum == undefined){
                		$.messager.alert("提示","入库数量不能为空");
                	}else if(id == "" || id == null || id == undefined){
                		$.messager.alert("提示","请重新选择商品");
                	}else if(isHigh == "true"){
                		if(cnum == "" || cnum == null || cnum == undefined){
                			$.messager.alert("提示","请填入自定义数量");
                		}else{
					window.open(ctx+"/pcLocal/localBatch/print/list?id="+id+"&anum="+anum+"&isHigh="+isHigh+"&code="+code+"&cnum="+cnum+"&birthDate="+birthDate);
                    	 		$('#dialog').window('close');
				}
                	}else{
                		 window.open(ctx+"/pcLocal/localBatch/print/list?id="+id+"&anum="+anum+"&isHigh="+isHigh+"&code="+code+"&cnum="+cnum+"&birthDate="+birthDate);
                    	 	$('#dialog').window('close');
                	}
                }
            }],
		});
	}
}
