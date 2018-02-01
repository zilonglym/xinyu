$(document).ready(function(){
	monthProfits.initTable();
});
var monthProfits={};
monthProfits.initTable=function(){
	$("#tb_month").datagrid({
		url:ctx+"/monthProfits/listData",
		height:750,
		rownumbers:true,
		pagination:true,
		singleSelect:false,
		fitColumns:true,
		loadMsg:"正在加载中.......",
		columns:[[
		  {field:'id',title:'唯一标识',width:100,hidden:true},
		  {field:'dateTime',title:'时间',width:100},
		  {field:'mainIncome',title:'主营业务收入',width:100},
		  {field:'mainCost',title:'主营业务成本',width:100},
		  {field:'mainProfits',title:'营业利润',width:100},
		  {field:'deliveryFee',title:'发个人件及接货费',width:100},
		  {field:'basketFee',title:'废纸箱',width:100},
		  {field:'otherIncome',title:'其他收入合计',width:100},
		  {field:'packingFee',title:'本月打包费',width:100},
		  {field:'materielFee',title:'物料,防水袋胶带',width:100},
		  {field:'damagedFee',title:'破损,漏发等',width:100},
		  {field:'otherCost',title:'其他支出合计',width:100},
		  {field:'totalProfits',title:'利润总额',width:100},
		  {field:'financeFee',title:'财务费用',width:100},
		  {field:'propertyFee',title:'房租,物业费',width:100},
		  {field:'waterFee',title:'水电',width:100},
		  {field:'officeFee',title:'其他办公费用',width:100},
		  {field:'boardFee',title:'伙食费',width:100},
		  {field:'vehicleFee',title:'车辆费用',width:100},
		  {field:'assetsFee',title:'固定无形资产待摊',width:100},
		  {field:'hospitalityFee',title:'招待费',width:100},
		  {field:'taxFee',title:'应交税金',width:100},
		  {field:'insuranceFee',title:'保险+工会经费',width:100},
		  {field:'wagesFee',title:'工资',width:100},
		  {field:'manageFee',title:'管理费用合计',width:100},
		  {field:'profits',title:'净利润',width:100}
		]]
	});
	
	$('#tb_month').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		pageList:[100,200,500],
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
}

monthProfits.refresh=function(){
	$('#tb_month').datagrid('load',{
		name:null
	});
}

monthProfits.search=function(){
	$('#tb_month').datagrid('load',{
		beigainTime:$("#beigainTime").datetimebox('getValue'),
		lastTime:$("#lastTime").datetimebox('getValue')
	});
}

monthProfits.add=function(){
	$('#dialog').dialog({
		title: '添加',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/monthProfits/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
                     json=json+"mainIncome:'"+$("#mainIncome").textbox("getValue")+"',";
                     json=json+"mainCost:'"+$("#mainCost").textbox("getValue")+"',";
                     json=json+"deliveryFee:'"+$("#deliveryFee").textbox("getValue")+"',";
                     json=json+"basketFee:'"+$("#basketFee").textbox("getValue")+"',";
                     json=json+"packingFee:'"+$("#packingFee").textbox("getValue")+"',";
                     json=json+"materielFee:'"+$("#materielFee").textbox("getValue")+"',";
                     json=json+"damagedFee:'"+$("#damagedFee").textbox("getValue")+"',";
                     json=json+"financeFee:'"+$("#financeFee").textbox("getValue")+"',";
                     json=json+"propertyFee:'"+$("#propertyFee").textbox("getValue")+"',";
                     json=json+"waterFee:'"+$("#waterFee").textbox("getValue")+"',";
                     json=json+"officeFee:'"+$("#officeFee").textbox("getValue")+"',";
                     json=json+"boardFee:'"+$("#boardFee").textbox("getValue")+"',";
                     json=json+"vehicleFee:'"+$("#vehicleFee").textbox("getValue")+"',";
                     json=json+"assetsFee:'"+$("#assetsFee").textbox("getValue")+"',";
                     json=json+"hospitalityFee:'"+$("#hospitalityFee").textbox("getValue")+"',";
                     json=json+"taxFee:'"+$("#taxFee").textbox("getValue")+"',";
                     json=json+"insuranceFee:'"+$("#insuranceFee").textbox("getValue")+"',";    
                     json=json+"wagesFee:'"+$("#wagesFee").textbox("getValue")+"'}";    
                     $.post(ctx+"/monthProfits/save",{json:json},function(data){
                    	 if(data&&data.ret=="insert"){
                    		 alert("添加成功！");
                    		 $('#dialog').window('close');
                             $('#tb_month').datagrid('load',{
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
                       $('#tb_month').datagrid('load',{
              			 name:null
              		 });
                    }
                }]	
	});
}

monthProfits.edit=function(){
	var row=$('#tb_month').datagrid('getSelected');
	if(row){
		$('#dialog').dialog({
			title: '编辑',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/monthProfits/f7Edit?id='+row.id,
		    modal: true,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                     var json="{";
	                     json=json+"id:'"+$("#id").val()+"',";
	                     json=json+"dateTime:'"+$("#dateTime").datetimebox("getValue")+"',";
	                     json=json+"mainIncome:'"+$("#mainIncome").textbox("getValue")+"',";
	                     json=json+"mainCost:'"+$("#mainCost").textbox("getValue")+"',";
	                     json=json+"deliveryFee:'"+$("#deliveryFee").textbox("getValue")+"',";
	                     json=json+"basketFee:'"+$("#basketFee").textbox("getValue")+"',";
	                     json=json+"packingFee:'"+$("#packingFee").textbox("getValue")+"',";
	                     json=json+"materielFee:'"+$("#materielFee").textbox("getValue")+"',";
	                     json=json+"damagedFee:'"+$("#damagedFee").textbox("getValue")+"',";
	                     json=json+"financeFee:'"+$("#financeFee").textbox("getValue")+"',";
	                     json=json+"propertyFee:'"+$("#propertyFee").textbox("getValue")+"',";
	                     json=json+"waterFee:'"+$("#waterFee").textbox("getValue")+"',";
	                     json=json+"officeFee:'"+$("#officeFee").textbox("getValue")+"',";
	                     json=json+"boardFee:'"+$("#boardFee").textbox("getValue")+"',";
	                     json=json+"vehicleFee:'"+$("#vehicleFee").textbox("getValue")+"',";
	                     json=json+"assetsFee:'"+$("#assetsFee").textbox("getValue")+"',";
	                     json=json+"hospitalityFee:'"+$("#hospitalityFee").textbox("getValue")+"',";
	                     json=json+"taxFee:'"+$("#taxFee").textbox("getValue")+"',";
	                     json=json+"insuranceFee:'"+$("#insuranceFee").textbox("getValue")+"',";    
	                     json=json+"wagesFee:'"+$("#wagesFee").textbox("getValue")+"'}";    
	                     $.post(ctx+"/monthProfits/save",{json:json},function(data){
	                    	 if(data&&data.ret=="update"){
	                    		 alert("修改成功！");
	                    		 $('#dialog').window('close');
	                             $('#tb_month').datagrid('load',{
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
	                       $('#tb_month').datagrid('load',{
	              			 name:null
	              		 });
	                    }
	                }]	
		});
	}else{
		alert("请选择需修改的记录！");
	}
	
}
