$(document).ready(function(){
	person.initTable();
});

//日期格式化输出
Date.prototype.format = function(format) {
    var o = {
        "M+": this.getMonth() + 1, // month
        "d+": this.getDate(), // day
        "h+": this.getHours(), // hour
        "m+": this.getMinutes(), // minute
        "s+": this.getSeconds(), // second
        "q+": Math.floor((this.getMonth() + 3) / 3), // quarter
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
}
function formatDatebox(value) {
	if (value == null || value == '') {
		return '';
	}
	var dt;
	if (value instanceof Date) {
		dt = value;
	} else {
		dt = new Date(value);	
	}
	return dt.format("yyyy-MM-dd hh:mm:ss"); 
}

var person={};
person.initTable=person.refresh=function(){
	$('#tb_person').datagrid({
		url:ctx+"/person/listData",
		height:850,
		rownumbers:true,
		pagination:true,
		loadMsg:"正在加载中.......",
		singleSelect:true,
		fitColumns:true,
		columns:[[
		   {field:"ck",checkbox:true},
		   {field:"idCard",title:"员工号",width:100},	   
		   {field:"name",title:"员工姓名",width:150},	   
		   {field:"age",title:"年龄",width:100},	   
		   {field:"phone",title:"联系手机",width:150},	   
		   {field:"userName",title:"用户名",width:150},	   
		   {field:"roles",title:"角色",width:150},	   
		   {field:"inputDate",title:"入职时间",formatter:formatDatebox,width:150},
		   {field:"id",title:"员工唯一码",width:100}  
		]]
	});
	
	//隐藏id列
	$('#tb_person').datagrid('hideColumn', 'id');
	
	$('#tb_person').datagrid('getPager').pagination({
		pageSize:100,
		showPageList:false,
		beforePageText: '第',
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} 到 {to} 条记录   共 {total} 条记录'
	});
	
	$('#name').searchbox({
		searcher:function(value,name){
			$('#tb_person').datagrid('load',{
				name:value
			});
		}
	});
}

person.add=function(){
	$('#id').hide();
	$('#dialog').dialog({
		title: '添加员工信息',
	    width: 400,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/person/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"idCard:'"+$("#idCard").textbox("getValue")+"',";
                     json=json+"name:'"+$("#personName").textbox("getValue")+"',";
                     json=json+"age:'"+$("#age").textbox("getValue")+"',";
                     json=json+"sex:'"+$("#sex").combobox("getValue")+"',";
                     json=json+"phone:'"+$("#phone").textbox("getValue")+"',";
                     json=json+"centro:'"+$("#centro").combobox("getValue")+"'}";	                     
                     $.post(ctx+"/person/save",{json:json},function(data){
                    	 if(data&&data.card=="false"){
                    		 alert("该员工号已存在！！！！");
                    	 }else{
                    		 if(data.ret=="insert"){
                    			 alert("添加成功！！！！！");
                    			 $('#dialog').window('close');
                        		 $('#tb_person').datagrid('load',{
                        			 name:null
                        		 }); 
                    		 }else if(data.ret=="update"){
                    			 alert("修改成功！！！！！");
                    			 $('#dialog').window('close');
                    			 $('#tb_person').datagrid('load',{
                        			 name:null
                        		 });
                    		 }else{
                    			 alert("操作错误！！！！");
                    		 }
                    	 }
                     });
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                       $('#tb_person').datagrid('load',{
              			 name:null
              		 });
                    }
                }]
	});
}

person.edit=function(){
	$('#id').hide();
	var row=$("#tb_person").datagrid("getSelected");
	if(row){
		$('#dialog').dialog({
			title: '修改商品信息',
		    width: 400,
		    height: 500,
		    closed: false,
		    cache: false,
		    href: ctx+'/person/f7Edit?id='+row.id,
		    modal: false,
		    buttons: [{
	                    text:'保存',
	                    iconCls:'icon-ok',
	                    handler:function(){
	                    	 var json="{";
	                         json=json+"id:'"+$("#id").val()+"',";
	                         json=json+"idCard:'"+$("#idCard").textbox("getValue")+"',";
	                         json=json+"password:'"+$("#password").textbox("getValue")+"',";
	                         json=json+"name:'"+$("#personName").textbox("getValue")+"',";
	                         json=json+"age:'"+$("#age").textbox("getValue")+"',";
	                         json=json+"sex:'"+$("#sex").combobox("getValue")+"',";
	                         json=json+"phone:'"+$("#phone").textbox("getValue")+"',";
	                         json=json+"centro:'"+$("#centro").combobox("getValue")+"'}";                     
	                     $.post(ctx+"/person/save",{json:json},function(data){
	                    	 if(data&&data.card=="false"){
	                    		 alert("该员工号已存在！！！！");
	                    	 }else{
	                    		 if(data.ret=="insert"){
	                    			 alert("添加成功！！！！！");
	                    			 $('#dialog').window('close');
	                    			 $('#tb_person').datagrid('load',{
	                        			 name:null
	                        		 });
	                    		 }else if(data.ret=="update"){
	                    			 alert("修改成功！！！！！");
	                    			 $('#dialog').window('close');
	                    			 $('#tb_person').datagrid('load',{
	                        			 name:null
	                        		 });
	                    		 }else{
	                    			 alert("操作错误！！！！");
	                    		 }
	                    	 }
	                     }); 
	                    }
	                },{
	                    text:'取消',
	                    handler:function(){
	                       $('#dialog').window('close');
	                       $('#tb_person').datagrid('load',{
                  			 name:null
                  		 });
	                    }
	                }]
		});
	}
}

person.remove=function(){
	var row=$("#tb_person").datagrid("getSelected");
	if(row){
		 $.messager.confirm('确认','是否确认删除该员工信息？',function(r){
			if(r){
				$.post(ctx+'/person/delete',{id:row.id},function(result){
					if(result.ret=="false"){
						alert("删除失败！！！");
						 $('#tb_person').datagrid('load',{
                			 name:null
                		 });
					}else{
						alert("删除成功！！！");
						 $('#tb_person').datagrid('load',{
                			 name:null
                		 });
					}
				});
			}
		});
	}
}