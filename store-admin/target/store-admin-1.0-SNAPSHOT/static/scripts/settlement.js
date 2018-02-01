$(document).ready(function(){
	settlement.initTable();
});
var settlement={};
settlement.initTable=function(){
	$("#tb_settlement").datagrid({
		url:ctx+"/settlement/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'id',title:'标识',width:100,hidden:true},
		  {field:'dateTime',title:'时间',width:120},
		  {field:'code',title:'编号',width:50},
		  {field:'userName',title:'商家名称',width:120},
		  {field:'subject',title:'科目名称',width:100},
		  {field:'remark',title:'备注',width:250},
		  {field:'debit',title:'借方',width:100},
		  {field:'credit',title:'贷方',width:100},
		  {field:'direction',title:'方向',width:80},
		  {field:'balance',title:'余额',width:100}
		]]
	});
	
	$('#tb_settlement').datagrid('getPager').pagination({
		showPageList:false,
		pageSize: 100,
		pageList:[100,200],
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

settlement.refresh=function(){
	$("#tb_settlement").datagrid('load',{
		name:null
	});
}

settlement.search=function(){
	$("#tb_settlement").datagrid('load',{
		userName:$("#userName").combobox('getValue'),
		code:$("#code").textbox('getValue'),
		beigainTime:$("#beigainTime").datetimebox('getValue'),
		lastTime:$("#lastTime").datetimebox('getValue')
	});
}


settlement.add=function(){
	$("#dialog").dialog({
		title: '添加',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/settlement/f7Add',
	    modal: false,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var json="{";
                        json=json+"id:'"+$("#id").val()+"',";
                        json=json+"userName:'"+$("#user_name").combobox("getValue")+"',";
                        json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
                        json=json+"subject:'"+$("#subject").combobox("getValue")+"',";
                        json=json+"remark:'"+$("#remark").textbox("getValue")+"',";
                        json=json+"code:'"+$("#codeNumber").textbox("getValue")+"',";
                        json=json+"debit:'"+$("#debit").textbox("getValue")+"',";
                        json=json+"credit:'"+$("#credit").textbox("getValue")+"',";   
                        json=json+"direction:'"+$("#direction").combobox("getValue")+"',";
                        json=json+"balance:'"+$("#balance").textbox("getValue")+"'}";
                     $.post(ctx+"/settlement/save",{json:json},function(data){
                    	 if(data&&data.ret=="insert"){
                    		 alert("添加成功！");
                    		 $('#dialog').window('close');
                             $('#tb_settlement').datagrid('load',{
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
                       $('#tb_settlement').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	
	});
}

settlement.edit=function(){
	var row=$("#tb_settlement").datagrid('getSelected');
	if(row){
		$("#dialog").dialog({
			title: '修改',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/settlement/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                        json=json+"id:'"+$("#id").val()+"',";
	                        json=json+"userName:'"+$("#user_name").combobox("getValue")+"',";
	                        json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
	                        json=json+"subject:'"+$("#subject").combobox("getValue")+"',";
	                        json=json+"remark:'"+$("#remark").textbox("getValue")+"',";
	                        json=json+"debit:'"+$("#debit").textbox("getValue")+"',";
	                        json=json+"credit:'"+$("#credit").textbox("getValue")+"',";   
	                        json=json+"code:'"+$("#codeNumber").textbox("getValue")+"',";   
	                        json=json+"direction:'"+$("#direction").combobox("getValue")+"',";
	                        json=json+"balance:'"+$("#balance").textbox("getValue")+"'}";
	                     $.post(ctx+"/settlement/save",{json:json},function(data){
	                    	 if(data&&data.ret=="update"){
	                    		 alert("修改成功！");
	                    		 $('#dialog').window('close');
	                             $('#tb_settlement').datagrid('load',{
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
	                       $('#tb_settlement').datagrid('load',{
	              			 name:null
	                    });
	                }
	          }]
		});
	}else{
		alert("请选择需修改的记录");
	}
	
}