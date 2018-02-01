$(document).ready(function(){
	express.initTable();
});

//运费设置列表初始化
var express={};
express.initTable=express.refresh=function(){
	$('#tb_express').datagrid({
		url:ctx+"/expressPrice/listData",
		height:850,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'id',checkbox:true},
		  {field:'userName',title:'商家店铺名',width:100},
		  {field:'name',title:'快递名',width:80},
		  {field:'code',title:'快递编码',width:80},
		  {field:'area',title:'区域',width:350},
		  {field:'firstCost',title:'首重（KG）',width:80},
		  {field:'firstPrice',title:'首重费用（元）',width:120},
		  {field:'initialCost',title:'续重（KG）',width:80},
		  {field:'initialPrice',title:'续重费用（元/KG）',width:120},
		  {field:'deliveryCost',title:'仓储费用（元）',width:120},
		  {field:'otherCost',title:'打包费用（元）',width:120},
		  {field:'deliveryPrice',title:'派送费用（元）',width:120},
		  {field:'otherPrice',title:'其他费用（元）',width:120},
		  {field:'operator',title:'操作',width:100,formatter:function(value,row,index){
			  return  "<a href='javascript:void(0);' onclick='edit("+row.id+")'>修改</a>";
		  }},
		]],
		
		//双击弹出修改框
		onDblClickRow:function(index,row){
			$('#dialog').dialog({
				title: '修改',
			    width: 400,
			    height: 500,
			    closed: false,
			    cache: false,
			    href: ctx+'/expressPrice/f7Edit?id='+row.id,
			    modal: false,
			    buttons: [{
		                    text:'保存',
		                    iconCls:'icon-ok',
		                    handler:function(){
		                    	var json="{";
		                        json=json+"id:'"+$("#id").val()+"',";
		                        json=json+"userId:'"+$("#shop_name").combobox("getValue")+"',";
		                        json=json+"sysId:'"+$("#express_name").combobox("getValue")+"',";
		                        json=json+"area:'"+$("#area").textbox("getValue")+"',";
		                        json=json+"firstPrice:'"+$("#firstPrice").textbox("getValue")+"',";
		                        json=json+"initialPrice:'"+$("#initialPrice").textbox("getValue")+"',";
		                        json=json+"deliveryPrice:'"+$("#deliveryPrice").textbox("getValue")+"',";
		                        json=json+"otherPrice:'"+$("#otherPrice").textbox("getValue")+"',";   
		                        json=json+"firstCost:'"+$("#firstCost").textbox("getValue")+"',";
		                        json=json+"initialCost:'"+$("#initialCost").textbox("getValue")+"',";
		                        json=json+"deliveryCost:'"+$("#deliveryCost").textbox("getValue")+"',";
		                        json=json+"otherCost:'"+$("#otherCost").textbox("getValue")+"'}";    
		                     $.post(ctx+"/expressPrice/save",{json:json},function(data){
		                    	 if(data&&data.ret=="insert"){
		                    		 alert("添加成功！");
		                    		 $('#dialog').window('close');
		                             $('#tb_express').datagrid('load',{
		                    			 name:null
		                    		 });
		                    	 }else if(data&&data.ret=="update"){
		                    		 alert("修改成功！");
		                    		 $('#dialog').window('close');
		                             $('#tb_express').datagrid('load',{
		                    			 name:null
		                    		 });
		                    	 }else{
		                    		 alert("操作失败！");
		                    	 }
		                     }); 
		                    }
		                },{
		                    text:'取消',
		                    handler:function(){
		                       $('#dialog').window('close');
		                       $('#tb_express').datagrid('load',{
	                  			 name:null
	                  		 });
		                    }
		                }]
			});
		}
	});
	
	//分页显示插件
	$('#tb_express').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200],
        beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

//查询
express.search=express.refresh=function(){
	$('#tb_express').datagrid('load',{
		area:$('#expressArea').textbox('getValue'),
		code:$('#expressName').combobox('getValue'),
		userId:$('#userName').combobox('getValue')
	});
}

//新增
express.add=function(){
	$('#dialog').dialog({
		title: '添加',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/expressPrice/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"userId:'"+$("#shop_name").combobox("getValue")+"',";
                     json=json+"sysId:'"+$("#express_name").combobox("getValue")+"',";
                     json=json+"area:'"+$("#area").textbox("getValue")+"',";
                     json=json+"firstPrice:'"+$("#firstPrice").textbox("getValue")+"',";
                     json=json+"initialPrice:'"+$("#initialPrice").textbox("getValue")+"',";
                     json=json+"deliveryPrice:'"+$("#deliveryPrice").textbox("getValue")+"',";
                     json=json+"otherPrice:'"+$("#otherPrice").textbox("getValue")+"',";
                     json=json+"firstCost:'"+$("#firstCost").textbox("getValue")+"',";
                     json=json+"initialCost:'"+$("#initialCost").textbox("getValue")+"',";
                     json=json+"deliveryCost:'"+$("#deliveryCost").textbox("getValue")+"',";
                     json=json+"otherCost:'"+$("#otherCost").textbox("getValue")+"'}";    
                     $.post(ctx+"/expressPrice/save",{json:json},function(data){
                    		$('#dialog').window('close');
                    		$.messager.alert("提示",data.msg);
                    		$('#tb_express').datagrid("reload");
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

//修改
function edit(id){
	if(id){
		$('#dialog').dialog({
			title: '修改',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/expressPrice/f7Edit?id='+id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                        json=json+"id:'"+$("#id").val()+"',";
	                        json=json+"userId:'"+$("#shop_name").combobox("getValue")+"',";
	                        json=json+"sysId:'"+$("#express_name").combobox("getValue")+"',";
	                        json=json+"area:'"+$("#area").textbox("getValue")+"',";
	                        json=json+"firstPrice:'"+$("#firstPrice").textbox("getValue")+"',";
	                        json=json+"initialPrice:'"+$("#initialPrice").textbox("getValue")+"',";
	                        json=json+"deliveryPrice:'"+$("#deliveryPrice").textbox("getValue")+"',";
	                        json=json+"otherPrice:'"+$("#otherPrice").textbox("getValue")+"',";  
	                        json=json+"firstCost:'"+$("#firstCost").textbox("getValue")+"',";
	                        json=json+"initialCost:'"+$("#initialCost").textbox("getValue")+"',";
	                        json=json+"deliveryCost:'"+$("#deliveryCost").textbox("getValue")+"',";
	                        json=json+"otherCost:'"+$("#otherCost").textbox("getValue")+"'}";    
	                     $.post(ctx+"/expressPrice/save",{json:json},function(data){
	                    	 	$('#dialog').window('close');
                 				$.messager.alert("提示",data.msg);
                 				$('#tb_express').datagrid("reload");
                 			}); 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要修改的选项！");
	}
}


