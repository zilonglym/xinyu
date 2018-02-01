$(document).ready(function(){
	account.initTable();
});

var account={};
account.initTable=function(){
	$('#tb_account').datagrid({
		url:ctx+"/account/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"id",title:"ID",hidden:true},
		   {field:"userName",title:"用户名",width:100},	   
		   {field:"shortName",title:"简称",width:100},	   
		   {field:"mobile",title:"手机",width:150},	   
		   {field:"phone",title:"电话",width:150},	   
		   {field:"email",title:"邮箱",width:150},	   
		   {field:"operator",title:"操作",width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='account.editAccount();'>修改</a>";
	        }}  
		]]
	});
	
	$('#tb_account').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
	
	$('#searchText').searchbox({
		searcher:function(value,name){
			$('#tb_account').datagrid('load',{
				name:value
			});
		}
	});
}

account.editAccount=function(){
	var row=$("#tb_account").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改信息',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/account/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                    	json=json+"id:'"+$("#id").val()+"',";
	                    	json=json+"userName:'"+$("#userName").textbox("getValue")+"',";
	                        json=json+"password:'"+$("#password").textbox("getValue")+"',";
	                        json=json+"shortName:'"+$("#shortName").textbox("getValue")+"',";
	                        json=json+"mobile:'"+$("#mobile").textbox("getValue")+"',";
	                        json=json+"phone:'"+$("#phone").textbox("getValue")+"',";
	                        json=json+"email:'"+$("#email").textbox("getValue")+"'}";
	                        $.messager.progress({
                    		    title: '请稍等',
                    		    msg: '数据处理中，请稍等...',
                    		    text: '正在处理.......'
                    		});
	                     $.post(ctx+"/account/accountSave",{json:json},function(data){
	                    	 $.messager.progress('close');
	                    	 if(data&&data.ret=="repeat"){
	                    		 $.messager.alert("提示","该账号已存在！！！！");
	                    	 }else if(data.ret=="success"){
	                    		
	                    		 $.messager.alert("成功","修改成功！！！！！");
	                			 $('#dialog').window('close');
	                			 $('#tb_account').datagrid('load',{
	                    			 name:null
	                    		 });	 
	                    	 }else{
	                    	
	                    		 $.messager.alert("错误","修改失败！！！！！");
	                    	 }
	                     }); 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_account').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		 $.messager.alert("错误","请勾选需修改的信息！！");
	}
}
