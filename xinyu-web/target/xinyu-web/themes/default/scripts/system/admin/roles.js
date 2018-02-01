$(document).ready(function(){
	roles.initTable();
});

var roles={};
roles.initTable=function(){
	$('#tb_roles').datagrid({
		url:ctx+"/account/accountRolesListData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"id",title:"ID",hidden:true},
		   {field:"name",title:"角色名",width:100},	   
		   {field:"remark",title:"角色功能描述",width:150},	   
		   {field:"operator",title:"操作",width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='roles.edit();'>修改</a>&nbsp;|&nbsp;<a href='javascript:void(0);' onclick='roles.editMenu();'>设置权限</a>";
	        }}  
		]]
	});
	
	$('#tb_roles').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

roles.editMenu=function(){
	var ids = '';
	var row=$("#tb_roles").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '新建',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/account/f7AddRolesMenu?id='+row.id,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var name = $("#name").val();
	                    	var remark = $("#remark").val();
	                    	var id = $("#id").val();
	                    	var obj=document.getElementsByName('menu'); 
	                    	for(var i=0; i<obj.length; i++){
	                    	if(obj[i].checked) ids+=obj[i].value+","; 
	                    	}
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                     $.post(ctx+"/account/saveRolesMenu",{ids:ids,remark:remark,id:id,name:name},function(data){
	                    	 $.messager.progress('close');
	                    	if(data.ret=="success"){
	                			 $.messager.alert("成功","设置成功！！！！！");
	                			 $('#dialog').window('close');
	                			 $('#tb_roles').datagrid('load',{
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
	                       $('#tb_roles').datagrid('load',{
	              			 name:null
	              		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}	
}

roles.add=function(){
	$('#dialog').dialog({
		title: '新建',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/account/f7AddRoles',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    	  var json="{";
                          json=json+"id:'"+$("#id").val()+"',";
                          json=json+"name:'"+$("#name").val()+"',";
                          json=json+"remark:'"+$("#remark").val()+"'}";
                          $.messager.progress({
                  		    title: '请稍等',
                  		    msg: '数据处理中，请稍等...',
                  		    text: '正在处理.......'
                  		});
                     $.post(ctx+"/account/saveRoles",{json:json},function(data){
                    	 $.messager.progress('close');
                    	 if(data&&data.ret=="repeat"){
                    		 $.messager.alert("提示","该角色名已存在！！！！");
                    	 }else if(data.ret=="insert"){
                			 $.messager.alert("成功","新建成功！！！！！");
                			 $('#dialog').window('close');
                			 $('#tb_roles').datagrid('load',{
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
                       $('#tb_roles').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

roles.edit=function(){
	var ids = '';
	var row=$("#tb_roles").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/account/f7EditRoles?id='+row.id,
		    modal: false,
		    buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                	var remark = $("#remark").val();
                	var id = $("#id").val();
                	var name = $("#name").val();
                	var obj=document.getElementsByName('menu'); 
                	for(var i=0; i<obj.length; i++){
                	if(obj[i].checked) ids+=obj[i].value+","; 
                	}
                	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                 $.post(ctx+"/account/saveRolesMenu",{ids:ids,remark:remark,id:id,name:name},function(data){
                	 $.messager.progress('close');
                	if(data.ret=="success"){
            			 $.messager.alert("成功","设置成功！！！！！");
            			 $('#dialog').window('close');
            			 $('#tb_roles').datagrid('load',{
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
	                       $('#tb_roles').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}
