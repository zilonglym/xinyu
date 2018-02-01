
$(document).ready(function(){
	shopList.initTable();
});

var shopList={};
shopList.initTable=function(){
		$('#tb_shop').datagrid({
	   // url:'datagrid_data.json',
	    height:500,
	    columns:[[
	        {field:'code',title:'商家名',width:100},
	        {field:'name',title:'店铺名',width:100},
	        {field:'name',title:'店铺类型',width:100},
	        {field:'status',title:'店铺状态',width:100}
	    ]]
	});
}
shopList.add=function(){
	$('#dialog').dialog({
	    title: '添加新店铺',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/shop/toAdd',
	    modal: true,
	    buttons: [{
                    text:'获取',
                    iconCls:'icon-ok',
                    handler:function(){
                    	$.post(ctx+"/shop/getTokenCode",{},function(data){
                    		$("#sessionKey").textbox("setValue",data);
                    		//document.getElementById("sessionKey").value=data;
                    		$('#dialog_session').window('close');
                    	});
                    }
                },{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     // 	$("#sessionKey").textbox("setValue",data);
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"user:'"+$("#user").textbox("getValue")+"',";
                     json=json+"name:'"+$("#name").textbox("getValue")+"',";
                     json=json+"account:'"+$("#account").textbox("getValue")+"',";
                     json=json+"source:'"+$("#source").textbox("getValue")+"',";
                     json=json+"sessionKey:'"+$("#sessionKey").textbox("getValue")+"',";
                     json=json+"description:'"+$("#description").textbox("getValue")+"'}";
                     $.post(ctx+"/shop/save",{json:json},function(data){
                     	
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
shopList.getSessionKey=function(){
	$('#dialog_session').css("display","");
	window.open($("#codeUrl").val());
	/*$('#dialog_session').dialog({
	    title: '获取sessionKey',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    modal: true,
	    buttons: [{
                    text:'获取',
                    iconCls:'icon-ok',
                    handler:function(){
                    	$.post(ctx+"/shop/getTokenCode",{},function(data){
                    		$("#sessionKey").textbox("setValue",data);
                    		//document.getElementById("sessionKey").value=data;
                    		$('#dialog_session').window('close');
                    	});
                    }
                },{
                    text:'关闭',
                    handler:function(){
                       $('#dialog_session').window('close');
                    }
                }]
	});
	*/
}
