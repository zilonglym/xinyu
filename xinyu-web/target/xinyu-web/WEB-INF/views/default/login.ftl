<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>星宇ERP</title>
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="${base}/styles/login.css">
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
		<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
	</head>
	
	<body>  
    <div id="login">  
        <h1>湖南中仓网络科技有限公司</h1>  
        <br>
            <input type="text" required="required" placeholder="用户名" id="userName" name="userName" onkeydown="keySubmit(event);" value=""></input>  
            <input type="password" required="required" placeholder="密码" id="password" name="password" onkeydown="keySubmit(event)" value=""></input>  
            <select id="centro" name="centro">
            <#if centroList??>
            	<#list centroList as obj>
            	<option value="${obj.id}">${obj.name!''}</option>
            	</#list>
            </#if>
            </select>
            <button class="but" type="button" onclick="submit();" style="cursor:hand;">登录</button>  
    </div>  
</body>  

	<script type="text/javascript">
	function keySubmit(e){
		var keynum = window.event ? e.keyCode : e.which;
		if(keynum==13){
			submit();
		}
	}
	var ctx="${ctx}";
		function submit(){
			var userName=$("#userName").val();
			var password=$("#password").val();
			var centro=$("#centro").val();
			if(userName==''){
				 $.messager.alert('提示','用户名不能为空!',"warning");
				 return;
			}
			if(password==''){
				 $.messager.alert('提示','密码不能为空!',"warning");
				 return;
			}
			$.post(ctx+"/login/submitLogin",{userName:userName,password:password,centro:centro},function(data){
				if(data && data.ret==1){
					window.location.href=ctx+"/index"
				}else{
					$.messager.alert("提示","用户名密码有误,请重新登录!","error");
				}
			})
		}
	</script>
</html>