$(document).ready(function(){
	companyProfits.initTable();
});
var companyProfits={};
companyProfits.initTable=function(){
	$("#tb_profits").datagrid({
		url:ctx+"/companyProfits/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:true,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'ck',checkbox:true},
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'dateTime',title:'日期',width:120},
		  {field:'userName',title:'商家名',width:120},
		  {field:'expressCompany',title:'物流公司',width:80},
		  {field:'expressIncome',title:'快递费（应收）',width:100},
		  {field:'planeIncome',title:'面单费（应收）',width:100},
		  {field:'totalIncome',title:'小计（应收）',width:100},
		  {field:'expressExpend',title:'快递费（应付）',width:100},
		  {field:'sendFee',title:'发件费（应付）',width:100},
		  {field:'warehouseFee',title:'包仓费（应付）',width:100},
		  {field:'planeExpend',title:'面单费（应付）',width:100},
		  {field:'totalCost',title:'成本合计',width:100},
		  {field:'totalProfits',title:'利润',width:100},
		  {field:'num',title:'单量',width:100}
		]]
	});
	
	$('#tb_profits').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:true,
		pageList:[100,200,500],
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

companyProfits.refresh=function(){
	$('#tb_profits').datagrid('load',{
		name:null
	});
}

companyProfits.search=function(){
	$('#tb_profits').datagrid('load',{
		userName:$('#userName').combobox('getValue'),
		expressCompany:$('#expressName').combobox('getValue'),
		beigainTime:$('#beigainTime').datetimebox('getValue'),
		lastTime:$('#lastTime').datetimebox('getValue')
	});
}

companyProfits.edit=function(){
	var row=$("#tb_profits").datagrid('getSelected');
	if(row){
		$('#dialog').dialog({
			title: '修改信息',
		    width: 500,
		    height: 600,
		    resizable: true,
		    closed: false,
		    cache: false,
		    draggable: true,
		    shadow: true,
		    href: ctx+'/companyProfits/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	var json="{";
	                        json=json+"id:'"+$("#id").val()+"',";
	                        json=json+"userName:'"+$("#user_name").combobox('getValue')+"',";
	                        json=json+"expressCompany:'"+$("#express_name").combobox("getValue")+"',";
	                        json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
	                        json=json+"expressIncome:'"+$("#expressIncome").textbox("getValue")+"',";
	                        json=json+"planeIncome:'"+$("#planeIncome").textbox("getValue")+"',";
	                        json=json+"expressExpend:'"+$("#expressExpend").textbox("getValue")+"',";
	                        json=json+"planeExpend:'"+$("#planeExpend").textbox("getValue")+"',";
	                        json=json+"sendFee:'"+$("#sendFee").textbox("getValue")+"',";
	                        json=json+"warehouseFee:'"+$("#warehouseFee").textbox("getValue")+"',";
	                        json=json+"num:'"+$("#num").textbox("getValue")+"'}"                
	                     $.post(ctx+"/companyProfits/save",{json:json},function(data){
	                    	if(data&&data.ret=="update"){
	                    		alert("更新成功！");
	                    		$('#dialog').window('close');
	  	                       	$('#tb_profits').datagrid('load',{
	                    			 name:null
	                    		 });
	                    	}else{
	                    		alert("更新失败！");
	                    	}
	                     }); 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_profits').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}else{
		alert("请勾选需要修改的选项！");
		 $('#tb_profits').datagrid('load',{
  			 name:null
  		 });
	}
}

companyProfits.add=function(){
	$("#dialog").dialog({
		title: '添加信息',
	    width: 500,
	    height: 600,
	    resizable: true,
	    closed: false,
	    cache: false,
	    draggable: true,
	    shadow: true,
	    href: ctx+'/companyProfits/f7Add',
	    modal: false,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                    	var json="{";
                        json=json+"id:'"+$("#id").val()+"',";
                        json=json+"userName:'"+$("#user_name").combobox('getValue')+"',";
                        json=json+"expressCompany:'"+$("#express_name").combobox("getValue")+"',";
                        json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
                        json=json+"expressIncome:'"+$("#expressIncome").textbox("getValue")+"',";
                        json=json+"planeIncome:'"+$("#planeIncome").textbox("getValue")+"',";
                        json=json+"expressExpend:'"+$("#expressExpend").textbox("getValue")+"',";
                        json=json+"planeExpend:'"+$("#planeExpend").textbox("getValue")+"',";
                        json=json+"sendFee:'"+$("#sendFee").textbox("getValue")+"',";
                        json=json+"warehouseFee:'"+$("#warehouseFee").textbox("getValue")+"',";
                        json=json+"num:'"+$("#num").textbox("getValue")+"'}"                
                     $.post(ctx+"/companyProfits/save",{json:json},function(data){
                    	if(data&&data.ret=="insert"){
                    		alert("添加成功！");
                    		$('#dialog').window('close');
  	                       	$('#tb_profits').datagrid('load',{
                    			 name:null
                    		 });
                    	}else{
                    		alert("添加失败！");
                    	}
                     }); 
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_profits').datagrid('load',{
              			 name:null
              		 });
                    }
                }]	
	});
}