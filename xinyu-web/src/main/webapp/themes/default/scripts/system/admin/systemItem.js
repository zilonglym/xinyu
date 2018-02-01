$(document).ready(function(){
	systemItem.initTable();
});

var systemItem={};
systemItem.initTable=function(){
	$('#tb_system_item').datagrid({
		url:ctx+"/systemItem/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"ck",checkbox:true},
		   {field:"description",title:"系统参数名",width:150},	
		   {field:"type",title:"系统参数类型",width:100},
		   {field:"value",title:"系统参数值",width:350},	      
		   {field:"id",title:"ID",width:100,hidden:true}  
		]]
	});
	
	$('#tb_system_item').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
	
	$('#q').searchbox({
		searcher:function(value,name){
			$('#tb_system_item').datagrid('load',{
				name:value
			});
		}
	});
}

systemItem.add=function(){
	$('#id').hide();
	$('#dialog').dialog({
		title: '添加参数',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/systemItem/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"value:'"+$("#value").textbox("getValue")+"',";
                     json=json+"description:'"+$("#description").textbox("getValue")+"',";
                     json=json+"type:'"+$("#type").combobox("getValue")+"'}";	
                 	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                     $.post(ctx+"/systemItem/save",{json:json},function(data){
                    		$.messager.progress('close');
                    		 if(data.ret=="insert"){
                    			 $.messager.alert("成功","添加成功！！！！！");
                    			 $('#dialog').window('close');
                        		 $('#tb_system_item').datagrid('load',{
                        			 name:null
                        		 }); 
                    		 }else if(data.ret=="update"){
                    			 $.messager.alert("成功","修改成功！！！！！");
                    			 $('#dialog').window('close');
                    			 $('#tb_system_item').datagrid('load',{
                        			 name:null
                        		 });
                    		 }else{
                    			 $.messager.alert("错误","操作错误！！！！");
                    		 }
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_system_item').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

systemItem.edit=function(){
	$('#id').hide();
	var row=$("#tb_system_item").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改商品信息',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/systemItem/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                 var json="{";
                 json=json+"id:'"+$("#id").val()+"',";
                 json=json+"value:'"+$("#value").textbox("getValue")+"',";
                 json=json+"description:'"+$("#description").textbox("getValue")+"',";
                 json=json+"type:'"+$("#type").combobox("getValue")+"'}";	  
             	$.messager.progress({
        		    title: '请稍等',
        		    msg: '数据处理中，请稍等...',
        		    text: '正在处理.......'
        		});
                 $.post(ctx+"/systemItem/save",{json:json},function(data){
                		$.messager.progress('close');
                		 if(data.ret=="insert"){
                			 $.messager.alert("成功","添加成功！！！！！");
                			 $('#dialog').window('close');
                    		 $('#tb_system_item').datagrid('load',{
                    			 name:null
                    		 }); 
                		 }else if(data.ret=="update"){
                			 $.messager.alert("成功","修改成功！！！！！");
                			 $('#dialog').window('close');
                			 $('#tb_system_item').datagrid('load',{
                    			 name:null
                    		 });
                		 }else{
                			 $.messager.alert("错误","操作错误！！！！");
                		 }
                 });
                }
            },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_system_item').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}

systemItem.remove=function(){
	var row=$("#tb_system_item").datagrid("getSelected");
	if(row){
		 $.messager.confirm('确认','是否确认删除该信息？',function(r){
			if(r){
				$.messager.progress({
        		    title: '请稍等',
        		    msg: '数据处理中，请稍等...',
        		    text: '正在处理.......'
        		});
				$.post(ctx+'/systemItem/delete',{id:row.id},function(result){
					$.messager.progress('close');
					if(result.ret=="false"){
						$.messager.alert("失败","删除失败！！！");
						 $('#tb_system_item').datagrid('load',{
                			 name:null
                		 });
					}else{
						$.messager.alert("成功","删除成功！！！");
						 $('#tb_system_item').datagrid('load',{
                			 name:null
                		 });
					}
				});
			}
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}