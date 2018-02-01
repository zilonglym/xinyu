$(document).ready(function(){
	user.initTable();
});

var user={};
user.initTable=function(){
	$('#tb_user').datagrid({
		url:ctx+"/user/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"id",title:"ID",hidden:true},
		   {field:"tbUserId",title:"货主ID",width:100},
		   {field:"subscriberName",title:"用户名字",width:100},	   
		   {field:"subscriberNick",title:"用户昵称",width:150},	   
		   {field:"subscriberMobile",title:"用户手机",width:100},	   
		   {field:"subscriberPhone",title:"用户电话",width:150},	   
		   {field:"subscriberContactEmail",title:"用户邮箱",width:150},	   
		   {field:"subscriberAddress",title:"用户地址",width:150},
		   {field:"ownerCode",title:"ownerCode",width:150},
		   {field:"content",title:"仓储参数",width:150},
		   {field:"remark",title:"备注信息",width:100},  
		   {field:"operator",title:"操作",width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='user.editUser();'>修改</a>";
	        }}  
		]]
	});
	
	$('#tb_user').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
	
	$('#searchText').searchbox({
		searcher:function(value,name){
			$('#tb_user').datagrid('load',{
				name:value
			});
		}
	});
}

user.addUser=function(){
	$('#dialog').dialog({
		title: '新建',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/user/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"tbUserId:'"+$("#tbUserId").textbox("getValue")+"',";
                     json=json+"serviceCode:'"+$("#serviceCode").textbox("getValue")+"',";
                     json=json+"subscriberName:'"+$("#subscriberName").textbox("getValue")+"',";
                     json=json+"subscriberNick:'"+$("#subscriberNick").textbox("getValue")+"',";
                     json=json+"subscriberMobile:'"+$("#subscriberMobile").textbox("getValue")+"',";
                     json=json+"subscriberPhone:'"+$("#subscriberPhone").textbox("getValue")+"',";
                     json=json+"subscriberContactEmail:'"+$("#subscriberContactEmail").textbox("getValue")+"',";
                     json=json+"subscriberAddress:'"+$("#subscriberAddress").textbox("getValue")+"',";
                     json=json+"content:'"+$("#content").textbox("getValue")+"',";
                     json=json+"remark:'"+$("#remark").textbox("getValue")+"',";
                     json=json+"ownerCode:'"+$("#ownerCode").textbox("getValue")+"'}";    
                 	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                     $.post(ctx+"/user/saveUser",{json:json},function(data){
                    		$.messager.progress('close');
                    	 if(data&&data.ret=="repeat"){
                    		 $.messager.alert("提示","该商家已存在！！！！");
                    	 }else if(data.ret=="success"){
                			 $.messager.alert("成功","新建成功！！！！！");
                			 $('#dialog').window('close');
                			 $('#tb_user').datagrid('load',{
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
                       $('#tb_user').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

user.editUser=function(){
	var row=$("#tb_user").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改信息',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/user/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                    	json=json+"id:'"+$("#id").val()+"',";
	                    	json=json+"tbUserId:'"+$("#tbUserId").textbox("getValue")+"',";
	                        json=json+"serviceCode:'"+$("#serviceCode").textbox("getValue")+"',";
	                        json=json+"subscriberName:'"+$("#subscriberName").textbox("getValue")+"',";
	                        json=json+"subscriberNick:'"+$("#subscriberNick").textbox("getValue")+"',";
	                        json=json+"subscriberMobile:'"+$("#subscriberMobile").textbox("getValue")+"',";
	                        json=json+"subscriberPhone:'"+$("#subscriberPhone").textbox("getValue")+"',";
	                        json=json+"subscriberContactEmail:'"+$("#subscriberContactEmail").textbox("getValue")+"',";
	                        json=json+"subscriberAddress:'"+$("#subscriberAddress").textbox("getValue")+"',";
	                        json=json+"content:'"+$("#content").textbox("getValue")+"',";
	                        json=json+"remark:'"+$("#remark").textbox("getValue")+"',";
	                        json=json+"ownerCode:'"+$("#ownerCode").textbox("getValue")+"'}";  
	                    	$.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                     $.post(ctx+"/user/saveUser",{json:json},function(data){
	                    		$.messager.progress('close');
	                    	 if(data&&data.ret=="repeat"){
	                    		 $.messager.alert("提示","该商家已存在！！！！");
	                    	 }else if(data.ret=="success"){
	                			 $.messager.alert("成功","新建成功！！！！！");
	                			 $('#dialog').window('close');
	                			 $('#tb_user').datagrid('load',{
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
	                       $('#tb_user').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}
