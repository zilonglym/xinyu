$(document).ready(function(){
	menu.initTable();
});

var menu={};
menu.initTable=function(){
	$("#tb_menu").datagrid({
		url:ctx+"/account/accountMenuListData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"id",title:"ID",hidden:true},
		   {field:"menu",title:"一级菜单",width:100},	   
		   {field:"title",title:"二级菜单",width:150},	   
		   {field:"link",title:"菜单链接",width:150},	  
		   {field:"operator",title:"操作",width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='menu.edit();'>修改</a>";
	        }}  
		]]
	});
	
	$('#tb_menu').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

menu.add=function(){
	$('#dialog').dialog({
		title: '新建',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/account/f7AddMenu',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"title:'"+$("#linkTitle").textbox("getValue")+"',";
                     json=json+"link:'"+$("#link").textbox("getValue")+"',";
                     json=json+"menus:'"+$("#models").combobox("getValue")+"'}";
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/account/saveMenu",{json:json},function(data){
                    	 $.messager.progress('close');
                    	 if(data&&data.ret=="repeat"){
                    		
                    		 $.messager.alert("提示","该菜单设置已存在！！！！");
                    	 }else if(data.ret=="insert"){
                    		
                			 $.messager.alert("成功","新建成功！！！！！");
                			 $('#dialog').window('close');
                			 $('#tb_menu').datagrid('load',{
                    			 name:null
                    		 });	 
                    	 }else{
                    	
                    		 $.messager.alert("失败","新建失败！！！！！");
                    	 }
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_menu').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

menu.edit=function(){
	var row=$("#tb_menu").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/account/f7EditMenu?id='+row.id,
		    modal: false,
		    buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                    var json="{";
                    json=json+"id:'"+$("#id").val()+"',";
                    json=json+"title:'"+$("#linkTitle").textbox("getValue")+"',";
                    json=json+"link:'"+$("#link").textbox("getValue")+"',";
                    json=json+"menus:'"+$("#models").combobox("getValue")+"'}";
                    $.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                    $.post(ctx+"/account/saveMenu",{json:json},function(data){
                   	 $.messager.progress('close');
                   	 if(data&&data.ret=="repeat"){
                   		
                   		 $.messager.alert("提示","该菜单设置已存在！！！！");
                   	 }else if(data.ret=="update"){
                   		
               			 $.messager.alert("成功","新建成功！！！！！");
               			 $('#dialog').window('close');
               			 $('#tb_menu').datagrid('load',{
                   			 name:null
                   		 });	 
                   	 }else{
                   		
                   		 $.messager.alert("失败","新建失败！！！！！");
                   	 }
                    });
                   }
            },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_menu').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}

menu.search = function(){
	$('#tb_menu').datagrid('load',{
		title:$("#title").textbox("getValue")
	});	 
}
