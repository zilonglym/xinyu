$(document).ready(function(){
	rolesRow.initTable();
});

var rolesRow={};
rolesRow.initTable=function(){
	$("#tb_rolesRow").datagrid({
		url:ctx+"/account/accountRolesRowListData",
		height:750,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"id",title:"ID",hidden:true},
		   {field:"roles",title:"角色",width:150},	   
		   {field:"model",title:"操作类型",width:150},	   
		   {field:"remark",title:"备注",width:150},	   
		   {field:"operator",title:"操作",width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick='rolesRow.edit();'>修改</a>";
	        }}  
		]]
	});
	
	$('#tb_rolesRow').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}


rolesRow.edit=function(){
	var row=$("#tb_rolesRow").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/account/f7RowEdit?id='+row.id,
		    modal: false,
		    buttons: [{
                text:'保存',
                iconCls:'icon-ok',
                handler:function(){
                    var json="{";
                    json=json+"id:'"+$("#id").val()+"',";
                    json=json+"roles:'"+$("#accountRole").combobox("getValue")+"',";
                    json=json+"account:'"+$("#account").combobox("getValue")+"',";
                    json=json+"model:'"+$("#model").combobox("getValue")+"',";
                    json=json+"remark:'"+$("#remark").textbox("getValue")+"'}";
                    $.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                    $.post(ctx+"/account/saveMenu",{json:json},function(data){
                    	$.messager.progress('close');
                   	 if(data&&data.ret=="repeat"){
                   		 $.messager.alert("提示","该设置已存在！！！！");
                   	 }else if(data.ret=="update"){
               			 $.messager.alert("成功","修改成功！！！！！");
               			 $('#dialog').window('close');
               			 $('#tb_rolesRow').datagrid('load',{
                   			 name:null
                   		 });	 
                   	 }else{
                   		 $.messager.alert("失败","修改失败！！！！！");
                   	 }
                    });
                   }
            },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_rolesRow').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		$.messager.alert("提示","请勾选需要操作的信息！！");
	}
}

rolesRow.search = function(){
	$('#tb_rolesRow').datagrid('load',{
		searchText:$("#searchText").textbox("getValue")
	});	 
}
