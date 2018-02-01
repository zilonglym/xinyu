<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>当前账号信息</title>
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${base}/scripts/jquery-easyui/themes/icon.css">
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.min.js"></script>
	<script type="text/javascript" src="${base}/scripts/jquery-easyui/jquery.easyui.min.js"></script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'功能'" style="width:150px;">
    	<span style="font-size:13px;margin-left:10px;"><a href="javascript:void(0);" onclick="editPassword();">修改密码</a></span>
    </div>
    <div data-options="region:'center',title:'操作'" id="content">
  
    </div>
    <script>
    	var ctx="${ctx}";
    	function editPassword(){
    		$("#content").load('editPassword.ftl');
    	}
    </script>
</body>
</html>
