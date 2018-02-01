$(document).ready(function(){
	group.initTable();
});

var group={};

group.initTable=function(){
	$('#tb_group').datagrid({
	    url:ctx+'/group/listData',
	    height:600,
	    singleSelect:true,
	    rownumbers:true,
	    queryParams:{
	    	userId:$('#userId').combobox('getValue'),
			searchText:$("#searchText").textbox('getValue')
	    },
	    columns:[[
	        {field:'id',hidden:true},
	        {field:'userName',title:'商家',width:150},
	        {field:'itemName',title:'商品名称',width:160},
	        {field:'barCode',title:'条形码',width:120},
	        {field:'laterDate',title:'更新时间',width:160},
	        {field:'remark',title:'备注',width:200},   
	        {field:'state',title:'是否启用',width:100,formatter:function(value,row,index){
	        	if(row.state=="true"){
	        		return "<a href='javascript:void(0);' onclick=\"group.close(\'"+row.id+"'\);\">禁用</a>";
	        	}else if(row.state=="false"){
	        		return "<a href='javascript:void(0);' onclick=\"group.open(\'"+row.id+"'\);\">启用</a>";
	        	}
	        }},   
	        {field:'priority',title:'优先级',width:60},
	        {field:'operator',title:'操作',width:100,formatter:function(value,row,index){
	        	return "<a href='javascript:void(0);' onclick=\"group.edit(\'"+row.id+"'\);\">修改</a>";
	        }}
	    ]],
	    pagination:true,
	    onClickRow:function(rowIndex, rowData){
	    	initDetailTable(rowData);
	    }
	});
	
	var pagination =$('#tb_group').datagrid('getPager');
	$(pagination).pagination({   
		pageSize: 100,//每页显示的记录条数,默认为10 
		pageList:[100,200], 
        showPageList: false,
        beforePageText: '第',//页数文本框前显示的汉字 
        afterPageText: '页    共 {pages} 页', 
        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	});
	
}

function initDetailTable(rowData){
	if(rowData){
		$('#tb_groupDetail').datagrid({
		    url:ctx+'/group/detail/listData',
		    height:150,
		    singleSelect:true,
		    rownumbers:true,
		    queryParams:{
		    	id:rowData.id
		    },
		    columns:[[ 
		      {field:'itemId',hidden:true},
			  {field:'itemName',title:'商品名称',width:250},
			  {field:'color',title:'商品属性',width:150},
			  {field:'barCode',title:'条码',width:150},       
			  {field:'num',title:'数量',width:80}
			]]
		});
	}
}

group.searchList = function(){
	$('#tb_group').datagrid('load',{
		userId:$('#userId').combobox('getValue'),
		searchText:$("#searchText").textbox('getValue')
	});
}

group.close = function(id){
	$.messager.confirm('提示信息', '是否继续?', function(r) {
		if (id) {
			$.post(ctx + "/group/closeItemGroupState", {
				id:id
			}, function(data) {
				$.messager.alert('提示', data.msg);

				$('#tb_group').datagrid('load',{
					userId:$('#userId').combobox('getValue'),
					searchText:$("#searchText").textbox('getValue')
				});

			});
		}
	});
	
}

group.open = function(id){
	$.messager.confirm('提示信息', '是否继续?', function(r) {
		if (id) {
			$.post(ctx + "/group/openItemGroupState", {
				id:id
			}, function(data) {
				$.messager.alert('提示', data.msg);
				$('#tb_group').datagrid('load',{
					userId:$('#userId').combobox('getValue'),
					searchText:$("#searchText").textbox('getValue')
				});

			});
		}
	});
	
}

group.add=function(){
	$('#dialog').dialog({
		title: '新建组合商品',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/group/f7Add',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"userId:'"+$("#user").combobox("getValue")+"',";
                     json=json+"barCode:'"+$("#barCode").textbox("getValue")+"',";
                     json=json+"priority:'"+$("#priority").combobox("getValue")+"',";
                     json=json+"itemName:'"+$("#itemName").textbox("getValue")+"',";
                     json=json+"state:'"+$("#state").combobox("getValue")+"',";
                     json=json+"remark:'"+$("#remark").textbox("getValue")+"',}";
                     var rows  =$('#tb_itemDetail').datagrid("getRows");
                 	 var dateStr = JSON.stringify(rows); 
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/group/save",{json:json,rows:dateStr},function(data){
                    		 $.messager.alert("提示",data.msg);
                    		 $('#dialog').window('close');
                    		 $('#tb_group').datagrid('load',{
                    			userId:$('#userId').combobox('getValue'),
                    			searchText:$("#searchText").textbox('getValue')
                    		});
                    	
                     });
                     $.messager.progress('close');
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});
}

group.edit=function(id){
	$('#dialog').dialog({
		title: '新建组合商品',
	    width: 600,
	    height: 500,
	    closed: false,
	    cache: false,
	    href: ctx+'/group/f7Edit',
	    modal: true,
	    queryParams:{
	    	id:id
	    },
	    onLoad:function() {
	    	initOrderItem(id);
        },
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var json="{";
                     json=json+"id:'"+$("#id").val()+"',";
                     json=json+"userId:'"+$("#user").combobox("getValue")+"',";
                     json=json+"barCode:'"+$("#barCode").textbox("getValue")+"',";
                     json=json+"priority:'"+$("#priority").combobox("getValue")+"',";
                     json=json+"itemName:'"+$("#itemName").textbox("getValue")+"',";
                     json=json+"state:'"+$("#state").combobox("getValue")+"',";
                     json=json+"remark:'"+$("#remark").textbox("getValue")+"',}";
                     var rows  =$('#tb_itemDetail').datagrid("getRows");
                 	 var dateStr = JSON.stringify(rows); 
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/group/save",{json:json,rows:dateStr},function(data){                   		
                    	 $.messager.alert("提示",data.msg);
                    	 $('#dialog').window('close');
                    	 $('#tb_group').datagrid('load',{
                    		userId:$('#userId').combobox('getValue'),
                    		searchText:$("#searchText").textbox('getValue')
                    	});  	
                     });
                     $.messager.progress('close');
                    }
                },{
                    text:'取消',
                    handler:function(){
                       $('#dialog').window('close');
                    }
                }]
	});
}

function batchAppend(){
	var userId = $('#user').combobox('getValue');
	$('#item').dialog({
		title:'商品列表',
		width:500,
		height:650,
		closed:false,
		cache:false,
		href:ctx+'/group/item/list',
		queryParams:{
			userId:userId
		},
		modal:true,
		buttons:[{
			text:'保存',
			iconCls:'icon-ok',
			handler:function(){
				$('#item').dialog('close');
				var rows = $('#tb_groupItem').datagrid('getChecked');
				var dateStr = JSON.stringify(rows);
				initNewAddTable(dateStr);
			}
		},{
            text:'取消',
            handler:function(){
               $('#item').window('close');
           }
      }]
	});
}

function initOrderItem(id){ 
	var url = ctx+'/group/detail/listData';
	var editRow = undefined;
	$('#tb_itemDetail').datagrid({
	    url:url,
	    height:600,
	    rownumbers:true,
	    queryParams:{
	    	id:id
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'itemName',title:'商品名称',width:100},
	              {field:'color',title:'商品属性',width:70},
	              {field:'barCode',title:'条码',width:70},       
	              {field:'num',title:'数量',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_itemDetail').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
}

function initNewAddTable(dataStr){ 
	var url = ctx+'/group/newItemList/listData';
	var editRow = undefined;
	$('#tb_itemDetail').datagrid({
	    url:url,
	    height:600,
	    rownumbers:true,
	    queryParams:{
	    	dataStr:dataStr
	    },
	    columns:[[
	              {field:'itemId',hidden:true},
	              {field:'itemName',title:'商品名称',width:100},
	              {field:'color',title:'商品属性',width:70},
	              {field:'barCode',title:'条码',width:70},       
	              {field:'num',title:'数量',width:70,editor:{type:'numberbox',options:{precision:0}}}
	            ]],
	            onClickCell:function(index,field,value){
	    		    if (endEditing()) {
	    		        $('#tb_itemDetail').datagrid('selectRow', index).datagrid('editCell', {
	    		            index : index,
	    		            field : field
	    		        });
	    		        editIndex = index;
	    		    }
	    	 	}
		});
	
}

$.extend($.fn.datagrid.methods, {
    editCell : function(jq, param) {
        return jq.each(function() {
            var opts = $('#tb_itemDetail').datagrid('options');
            var fields = $('#tb_itemDetail').datagrid('getColumnFields', true).concat(
                    $('#tb_itemDetail').datagrid('getColumnFields'));
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_itemDetail').datagrid('getColumnOption', fields[i]);
                col.editor1 = col.editor;
                if (fields[i] != param.field) {
                    col.editor = null;
                }
            }
            $('#tb_itemDetail').datagrid('beginEdit', param.index);
            for ( var i = 0; i < fields.length; i++) {
                var col = $('#tb_itemDetail').datagrid('getColumnOption', fields[i]);
                col.editor = col.editor1;
            }
        });
    }
});

var editIndex = undefined;
//结束编辑 
function endEditing() {
  if (editIndex == undefined) {
      return true
  }
  if ($('#tb_itemDetail').datagrid('validateRow', editIndex)) {
      $('#tb_itemDetail').datagrid('endEdit', editIndex);
      editIndex = undefined;
      return true;
  } else {
      return false;
  }
}