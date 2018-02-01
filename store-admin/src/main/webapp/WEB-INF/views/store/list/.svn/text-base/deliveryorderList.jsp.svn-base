<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>出库单列表</title>
</head>

<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
				<select name="selectUser" id="selectUser" class="easyui-combobox">
					<option value="">全部</option>
					<c:forEach items="${users}" var="user">
						<c:if test="${user.shopName!=null}">
							<option value="${user.id}">${user.shopName}</option>
						</c:if>	
					</c:forEach>
				</select>
				<select name="selectCompany" id="selectCompany" class="easyui-combobox">
					<option value="">全部</option>
					<c:forEach items="${companys}" var="company">
						<option value="${company.value}">${company.description}</option>
					</c:forEach>
				</select>
				<select name="status" id="status" class="easyui-combobox">
					<option value="WMS_PRINT" <c:if test="${status == 'WMS_PRINT'}">selected="selected"</c:if>>未完成</option>
					<option value="WMS_FINISH" <c:if test="${status == 'WMS_FINISH'}">selected="selected"</c:if> >已确认</option>
				</select>
				<input class="easyui-datetimebox" name="beigainTime" id="beigainTime" data-options="prompt:'请选择起始时间'" style="width:160px"/>
				~
				<input class="easyui-datetimebox" name="lastTime" id="lastTime" data-options="prompt:'请选择截止时间'" style="width:160px"/>    
				<input id="q" name="q" class="easyui-textbox" data-options="prompt:'请输入关键字查询'"></input>
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
			    url:ctx+'/store/shipOrder/deliveryorderList/listData',
			    height:850,
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
			        {field:'lastDate',title:'打印日期',width:150,sortable:true,
						  sorter:function(a,b){
								return (a>b?1:-1);
							}  
			        },
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
			        	if(value=='WAIT_EXPRESS_PICKING'){
			        		return '待发送';
			        	}else if(value=='WMS_FINISH'){
			        		return '已发送';
			        	}
			        }},
			        {field:'operator',title:'操作',width:120,formatter:function(value,row,index){
			        	return "<a href='javascript:void(0);'  onclick='submitOk("+row.id+");'>标记完成</a> "
			        	+"&nbsp;<a href='${ctx}/store/shipOrder/detailInfo/"+row.id+"'>查看明细</a>"+"&nbsp;<a href='${ctx}/store/shipOrder/toItemLack/"+row.id+"'>缺货通知</a>";
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
			var url=ctx+"/store/shipOrder/submitButchDeliverOrder";
			var ids="";
			for (var i = 0; rows && i < rows.length; i++) {
				var row = rows[i];
				ids=ids+row.id+",";
				}
			if(ids!=''){
				$.messager.progress({
        		    title: '请稍等',
        		    msg: '数据处理中，请稍等...',
        		    text: '正在处理.......'
        		});
				$.post(url,{ids:ids},function(data){
					if(data.ret==1){
						alert("批量确认成功!");
						//window.location.reload();
						$.messager.progress('close');
						$('#contentTable').datagrid('reload');
					}
				});
			}
		}
		
		function submitOk(id){
			 $.messager.confirm('确认提示', '标记后此单将不能再确认发货，是否继续?', function(r){
                if (r){
                    $.post(ctx+"/store/shipOrder/submitOk",{id:id},function(data){
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
