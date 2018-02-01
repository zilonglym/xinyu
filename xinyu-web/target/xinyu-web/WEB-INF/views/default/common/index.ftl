<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<base target="_blank" />
		<title>星宇ERP</title>
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	</head>
	<body class="easyui-layout">
		<div data-options="region:'north',border:false" style="height:60px;background:#B3DFDA;padding:10px">
		<#include "header.ftl"/>
		</div>
		<div data-options="region:'center',title:''">
			<#include "body.ftl"/>
		</div>
	</body>
	<div id="dialog"></div>
	<script type="text/javascript">
	var ctx="${ctx}";
		function addPanel(url,title,id){
			if ($('#tt').tabs('exists', title)){
				$('#tt').tabs('select', title);
		} else {
				$('#tt').tabs('add',{
					id:id,
					title: title,
					height:800,
					content: '<div style="padding:5px"><iframe id='+id+' style="width:100%;min-height:800px;" src="'+url+'"></iframe></div>',
					closable: true,
					tools:[{
						iconCls:'icon-mini-refresh',
						handler:function(){
							document.getElementById(id).location.reload();
						}
						}]
				});
			}
		}
		function removePanel(){
			var tab = $('#tt').tabs('getSelected');
			if (tab){
				var index = $('#tt').tabs('getTabIndex', tab);
				$('#tt').tabs('close', index);
			}
		}
		
function addNotice(){	
	$('#dialog').dialog({
		title: '填写公告',
	    width: 570,
	    height: 550,
	    closed: false,
	    cache: false,
	    href: ctx+'/systemItem/f7AddNotice',
	    modal: true,
	    buttons: [{
                    text:'保存',
                    iconCls:'icon-ok',
                    handler:function(){
                     var msg = $("#msg").textbox("getValue");
                     $.messager.progress({
             		    title: '请稍等',
             		    msg: '数据处理中，请稍等...',
             		    text: '正在处理.......'
             		});
                     $.post(ctx+"/systemItem/saveNotice",{msg:msg},function(data){
                    	 $.messager.progress('close');
                    	 $('#dialog').window('close');
                    	 $.messager.alert("提示","公告内容填写成功！");
                    	  window.location.reload();
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
	</script>
</html>