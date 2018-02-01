<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="e" uri="http://www.wlpost.com/jsp/jstl/core" %>  
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>订单列表</title>
	<script src="${ctx}/static/scripts/trade.js?v=2.0.11" type="text/javascript"></script>
</head>
<body>
	<div style="margin-top: 5px;margin-bottom: 5px;">
		<select id="userId" name="userId" class="easyui-combobox">
			<option value='0'>全部</option> 
			<c:forEach items="${users}" var="user">
				<option value='${user.id}'  
				<c:if test="${user.id == userId}">
					selected='selected'
				</c:if>
				>${user.shopName}</option>
			</c:forEach>
		</select>
		<input  style="width:350px;" id="q" name="q" type="text" class="easyui-textbox" data-options="prompt:'运单号/联系人电话/批次号/收件人/买家ID'"/>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="trade.searchList();" >查询</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" key="${ctx}/trade/waits">审单</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" key="${ctx}/trade/waits/search">批量审单</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" key="${ctx}/wayBill/waits">面单打印</a>
	   	<a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" key="${ctx}/wayBill/waitsOk">面单重打</a>
	    <a href="javascript:void(0);" class="easyui-linkbutton" onclick="openUrl(this);" key="${ctx}/trade/send/pickings">分拣单打印</a>
	    
	</div>
	
	<table id="contentTable"  >
		<thead><tr>
		
		<th>创建日期</th>
		<th>商铺名称</th>
		<th>商品</th>
		<th>目的地</th>
		<th>快递公司</th>
		<th>运单号</th>
		<th>操作</th>
		<th style="display:none;"></th>
		</tr></thead>
	</table>
	<div id="dialog"></div>
	
	<div class="modal hide fade" id="myModal">
 		<div class="modal-header">
    		<a class="close" data-dismiss="modal">快递修改</a>
    		<h3>修改快递</h3>
  		</div>
  		<div class="modal-body">
    		<p>
    		<span id="tids"></span>
            快递公司选择
            <select name="expressCompany" id="expressCompany">
		    	<option value="-1">未选择</option>
		    	<c:forEach items="${itemList}" var="item">
						<option value="${item.value}">${item.description}</option>
					</c:forEach>
		    </select>
		    </p>
  		</div>
		<div class="modal-footer">
			<a href="#" class="btn" data-dismiss="modal">关闭</a>
	    	<a href="javascript:updateExpree();" class="btn btn-primary">确认修改</a>
	  	</div>
	</div>
	 <input type="hidden" id="tradeId" />
	<div id="loadingDiv" class="hint hide">
		<img src = "${ctx}/static/images/fetch.gif">
	</div>
	<script>
	var ctx="${ctx}";
		$(document).ready(function(){
			trade.initTable();
		});
		function updateExpree(){
			var id=$("#tradeId").val();
			var expressCompany=$("#expressCompany").val();
			$('#loadingDiv').show();
			$('#myModal').hide();
			//修改快递，有快递的才能修改
			$.post(ctx+"/waybill/updateExpress",{tradeId:id,expressCompany:expressCompany},function(data){
				$('#loadingDiv').hide();
				if(data && data.ret==1){
					alert("修改成功!");
					window.location.reload();
				}else{
					alert(data.msg);
				}
			});
		};	
		function selectRow(id){
			$("#tradeId").val(id);
		}
		
		function batchDelTrade(){
			
		}
		function delTrade(id){
			$("#contentTable").datagrid("selectRecord",id);
			var row=$("#contentTable").datagrid("getSelected");
			//删除的单 据必须是没有订单 号的。
			var express=row.expressOrderno;
			if(express!='' && express!=null){
				alert("该单据有运单号，请先取消再来删除!");
			}else{
				$.messager.progress({
        		    title: '请稍等',
        		    msg: '数据处理中，请稍等...',
        		    text: '正在处理.......'
        		});
				$.post(ctx+"/waybill/deleteTrade",{id:id},function(data){
					$.messager.progress('close');
					if(data && data.ret==1){
						window.location.reload();
					}else{
						alert("删除失败,请联系管理员!");
					}
				});
			}
		}
		
		function invalidTrade(id){
		$("#contentTable").datagrid("selectRecord",id);
			var row=$("#contentTable").datagrid("getSelected");
			//
			$.messager.confirm('订单作废确认', '是否作废此订单,此操作不可撤消?', function(r){
                if (r){
                	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                    $.post(ctx+"/trade/invalidTrade",{id:id},function(data){
                    	$.messager.progress('close');
                    	$.messager.alert('提示',data.msg);
						window.location.reload();
					});
                }
            });
		}
		
		function spliteTrade(id){
			$("#contentTable").datagrid("selectRecord",id);
			var row=$("#contentTable").datagrid("getSelected");
			//删除的单 据必须是没有订单 号的。
			var express=row.expressCompany;
			if(express!='' && express!=null){
				$.messager.progress({
        		    title: '请稍等',
        		    msg: '数据处理中，请稍等...',
        		    text: '正在处理.......'
        		});
				$.post(ctx+"/trade/ajax/resetAudit",{tradeId:id},function(data){
					$.messager.progress('close');
					if(data && data.code==200){
						window.location.reload();
					}else{
						alert("反审失败,请联系管理员!");
					}
				});
				
			}else{
				alert("该单据无运单号，无法进行反审操作!");
				
			}
		}
		
		
		
		function chkAll(){
			var chk=$("input[ttt='chkAll']").attr("checked");
			$("input[ttt='chk']").each(function(){
				if(chk=='checked'){
					$(this).attr("checked",chk);
				}else{
					$(this).removeAttr("checked");
				}
			});
		}
		function btnSearch(){
			var userId=$("#selectUser").val();
			var q=eval(document.getElementById('q')).value;
			var url=window.location;
			if(url.indexOf("?")>0){
				url=url.substring(0,url.indexOf("?"))+"?userId="+userId+"?q="+q;
				alert(url);
			}else{
				url=url+"?userId="+userId+"?q="+q;
			}
			window.location=url;
		}
		function submitUpdate(){
			
		}
		function openUrl(obj){
			var url=$(obj).attr("key");
			window.location.href=url;
		}
		function addReturn(id){
			$.messager.confirm('退货确认', '是否确认退货,此操作不可撤消?', function(r){
                if (r){
                	$.messager.progress({
            		    title: '请稍等',
            		    msg: '数据处理中，请稍等...',
            		    text: '正在处理.......'
            		});
                    $.post(ctx+"/wayBill/addReturn",{id:id},function(data){
                    	$.messager.progress('close');
                    	$.messager.alert('退货结果',data.msg);
                    });
                }
            });
		}
	</script>
</body>
</html>
