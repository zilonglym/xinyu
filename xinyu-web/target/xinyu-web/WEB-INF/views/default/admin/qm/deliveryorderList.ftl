<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>发货确认列表</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/easyui-lang-zh_CN.js"></script>
</head>

<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
				<select name="selectUser" id="selectUser" class="easyui-combobox" style="width:180px;">
					<option value="">全部</option>
					<#list users as user>
						<option value="${user.id}">${user.subscriberName}</option>
					</#list>
				</select>
				<select name="selectCompany" id="selectCompany" class="easyui-combobox" style="width:180px;">
					<option value="">全部</option>
					<#list companys as item>
						<option value="${item.value}">${item.description}</option>
					</#list>
				</select>
				<select name="status" id="status" class="easyui-combobox" style="width:180px;">
					<option value="WMS_PRINT">未完成</option>
					<option value="WMS_FINASH">已确认</option>
				</select>
				<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
				~
				<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
				<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入关键字查询'" style="width:180px;"></input>
				<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="delivery.searchList();" >查询</a>
				<a href="javascript:delivery.submit();" class="easyui-linkbutton" data-options="iconCls:'icon-ok'">批量确认</a>
	</div>		
	<table id="contentTable">
		<thead><tr>
		<th>序号</th>
		<th>商铺名</th>
		<th>出库单号</th>
		<th>业务类型</th>
		<th>收件人(昵称)</th>
		<th>运输公司</th>
		<th>运输公司运单号</th>
		<th>创建日期</th>
		<th>状态</th>
		<th>操作</th>
		</tr></thead>
	</table>
	<script>
		var ctx="${ctx}";
		var user="";
		$(document).ready(function(){
			delivery.initTable();
		});
		var delivery={};
		delivery.initTable=function(){
			$('#contentTable').datagrid({
			    url:ctx+'/qm/shipOrder/deliveryorderList/listData',
			    height:750,
			    rownumbers:true,
			    remoteSort:false,
			    queryParams:{
			    	selectUser:$("#selectUser").combobox('getValue'),
					selectCompany:$("#selectCompany").combobox('getValue'),
			   		status:$("#status").combobox('getValue'),
			   		beigainTime:$("#beigainTime").datetimebox('getValue'),
			   		lastTime:$("#lastTime").datetimebox('getValue'),
			   		q:$("#q").textbox('getValue')
			    },
			    columns:[[
			        {field:'id',checkbox:true},
			        {field:'lastDate',title:'打印日期',width:150},
			        {field:'originPersion',title:'商铺名',width:120,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'expressCompany',title:'运输公司',width:150,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'expressOrderno',title:'运单号',width:100,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'buyerNick',title:'昵称',width:100,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'receiverName',title:'收件人',width:100,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'items',title:'商品明细',width:250,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'orderno',title:'出库单号',width:100,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'orderType',title:'业务类型',width:100,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
			        {field:'status',title:'状态',width:80,formatter:function(value,row,index){
			        	if(value=='WMS_PRINT'){
			        		return '待发送';
			        	}else if(value=='WMS_FINISH'){
			        		return '已发送';
			        	}
			        }},
			        {field:'operator',title:'操作',width:120,formatter:function(value,row,index){
			        	return "<a href='javascript:void(0);'  onclick='submitOk("+row.id+");'>标记完成</a> <a href='${ctx}/qm/shipOrder/toConfirmDelivery/"+row.id+"'>确认发货</a>"
			        	+"&nbsp;<a href='${ctx}/qm/shipOrder/detailInfo/"+row.id+"'>查看明细</a>"+"&nbsp;<a href='${ctx}/qm/shipOrder/toItemLack/"+row.id+"'>缺货通知</a>";
			        }}
			    ]],
			    pagination:true
			});
			
			var pagination =$('#contentTable').datagrid('getPager');
			$(pagination).pagination({
				pageSize: 100,//每页显示的记录条数，默认为10 
				pageList:[100,200,250,300,500],
		        showPageList: true,
		        beforePageText: '第',//页数文本框前显示的汉字 
		        afterPageText: '页    共 {pages} 页', 
		        displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
			});
		}
		delivery.searchList=function(){
			user=$("#selectUser").combobox('getValue');
			$('#contentTable').datagrid('load', {
				selectUser:user,
				selectCompany:$("#selectCompany").combobox('getValue'),
		   		status:$("#status").combobox('getValue'),
		   		beigainTime:$("#beigainTime").datetimebox('getValue'),
		   		lastTime:$("#lastTime").datetimebox('getValue'),
		   		q:$("#q").textbox('getValue')
			});
			
			
		}		
		delivery.submit=function (){
			var rows= $('#contentTable').datagrid('getSelections');
			var url=ctx+"/qm/shipOrder/submitButchDeliverOrder";
			var ids="";
			for (var i = 0; rows && i < rows.length; i++) {
				var row = rows[i];
				ids=ids+row.id+",";
				}
			if(ids!=''){
				$.post(url,{ids:ids},function(data){
					if(data.ret==1){
						alert("批量确认成功!");
						//window.location.reload();
						$('#contentTable').datagrid('reload');;
					}
				});
			}
		}
		
		function submitOk(id){
			 $.messager.confirm('确认提示', '标记后此单将不能再确认发货，是否继续?', function(r){
                if (r){
                    $.post(ctx+"/qm/shipOrder/submitOk",{id:id},function(data){
                    	if(data && data.ret=='1'){
                    		$.messager.alert('提示','标记确认成功!');
                    		window.location.reload();
                    	}
                    });
                }
            });
		}
	</script>
</body>
</html>
