<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>韵达ISV面单</title>
<link href="${ctx}/static/waybill/styles.css" rel="stylesheet"  type="text/css">
<script src="${ctx}/static/waybill/CaiNiaoPrintFuncs.js" type="text/javascript"></script>
<object id="CaiNiaoPrint_OB" classid="clsid:09896DB8-1189-44B5-BADC-D6DB5286AC57" width=0 height=0> 
	<embed id="CaiNiaoPrint_EM" TYPE="application/x-cainiaoprint" width=0 height=0  ></embed>
</object> 
</head>
<body>
<h2><font color="#009999" size="6pt">韵达电子面单打印设计</font>
</h2>
<dl class="mulu"><dt><b>目录</b></dt><dd>
<ol>
    <li><a href="#no1">设置身份</a></li>
    <li><a href="#no2">模板代码</a></li>
    <li><a href="#no3">赋值代码</a></li>
    <li><a href="#no4">打印设计</a></li>
    <li><a href="#no5">打印代码</a></li>
</ol>
</dd></dl>
<p align=left ><br />
<strong ><a id="no1" name="no1">&nbsp&nbsp&nbsp&nbsp 1、设置身份</a>：</strong></p>
<pre><ol class="dp-xml">
<li> CNPrint.SET_PRINT_IDENTITY("AppKey="+AppKey+"&Seller_ID="+Seller_ID);<font color="green">//设置身份信息</font></li>
</pre>
<table>
<tr><td>
AppKey : <input type="text" id="AppKey" value="98801" size="40"><br> 
Seller_ID: <input type="text" id="Seller_ID" value="155809" size="40"><br>  
<a href="javascript:login()">开始设置身份信息</a> 
</td></tr></table>
<p align=left ><br />
<strong ><a id="no2" name="no2">&nbsp&nbsp&nbsp&nbsp2、模板代码</a>：</strong></p>
<pre><ol class="dp-xml"><li>CNPrint.PRINT_INITA(0,0,400,700,"打印控件功能演示_CNPrint功能_在线编辑获得程序代码");</li>
<li>CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE=ShunFeng&CONFIG=0");<font color="green">//加载模板及模板辅助信息</font></li>
</pre>

<p align=left ><br />
<strong ><a id="no3" name="no3">&nbsp&nbsp&nbsp&nbsp3、赋值函数代码</a>：</strong></p>
<pre><ol class="dp-xml"><li>CNPrint.SET_PRINT_CONTENT("ali_waybill_product_type","代收货款");//单据类型</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_short_address","021D-123-789");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_name","黑龙江齐齐哈尔集散");//集散地名称</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_code","053277886278");//集散地条码</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_waybill_code","053277886278");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_cod_amount","FKFS=到付;PSRQ=2015-07-10");//服务</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_name","齐齐哈尔沐鱼");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_phone","15605883677");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_address","黑龙江省齐齐哈尔市建华区文化大街42号齐齐哈尔大学计算机工程学院计算机001班");//收件人地址</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_send_name","浙江杭州行者");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_send_phone","180000980909");</li>
<li >CNPrint.SET_PRINT_CONTENT("ali_waybill_shipping_address","浙江省杭州市余杭区文一西路1001号阿里巴巴淘宝城5号小邮局");</li>
<li >CNPrint.SET_PRINT_CONTENT("YUNDA","123456789012");</li>
</pre>

<TABLE><TR><TD>
<p align=left ><br />
<strong ><a id="no4" name="no4">&nbsp&nbsp&nbsp&nbsp4、打印设计</a>：</strong></p>
<pre><ol class="dp-xml">
<li>模板CP_CODE : <input type="text" id="CP_CODE" value="EYB" size="40"></li>
<li>模板辅助信息  CONFIG : <input type="text" id="CONFIG" value="0" size="40"></li>
<li><a  id="no4" name="no4" href="javascript:DisplayDesign()">进入打印设计</a>编辑自定义区域的打印项</li>
</pre>
</TR></TD><TD>
<object id="CaiNiaoPrint_OB_2" classid="clsid:09896DB8-1189-44B5-BADC-D6DB5286AC57" width=850 height=800> 
  <param name="Caption" value="内嵌显示区域">
  <param name="Border" value="1">
  <param name="Color" value="#C0C0C0">
  <embed id="CaiNiaoPrint_EM_2" TYPE="application/x-cainiaoprint" width=850 height=800>
</object> 
</TD> </TR></TABLE>

<p align=left ><br />
<strong ><a id="no1" name="no1">&nbsp&nbsp&nbsp&nbsp5、打印代码生成</a>：</strong></p>
<pre><ol class="dp-xml">
<li><a href="javascript:;" onclick="javascript:getP();">获得自定义区程序数据</a></li>
<li><textarea rows="10" id="S2" cols="105" >自定义区程序数据（64码）</textarea></li>
<li><a href="javascript:;" onclick="javascript:S2_PREVIEW_LIST();">以上自定义区程序数据代码直接预览</a>(自动追加模板所有内容)</li>
</pre>



<script language="javascript" type="text/javascript"> 
	var CNPrint; //声明为全局变量 
	
	function CreatePage(){
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
	   	CNPrint.PRINT_INITA(0,0,400,800,"打印控件功能演示_CNPrint功能_在线编辑获得程序代码");
	 	//CNPrint.SET_SHOW_MODE("DEBUG_STATUS",true);//调整状态函数
		var AppKey=document.getElementById('AppKey').value;
		var Seller_ID=document.getElementById('Seller_ID').value;
		var CP_CODE=document.getElementById('CP_CODE').value;
	//	var CONFIG=document.getElementById('CONFIG').value;
		//CNPrint.SET_PRINT_MODE("TAOBAO_STYLE_SHEET",MBName);
	//	CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE="+CP_CODE+"&CONFIG="+CONFIG);
	var config="c2lnbkE1M0ZFODMzQjkzREVBRUREOUZBQzY2QjYxMEU2MTJEJnsidmVyc2lvbiI6IjEiLCJhbGlfd2F5YmlsbF9jcF9sb2dvX1ZJU0lCTEUiOiJmYWxzZSIsImFsaV93YXliaWxsX2NvbnNpZ25lZV9hZGRyZXNzX0NPTlRFTlQiOiLmtYvor5Xpgq7lr4Tmm7/mjaIiLCJhbGlfd2F5YmlsbF9zaGlwcGluZ19hZGRyZXNzX1BPU1RGSVgiOiLvvIjmt5jlrp3mtYvor5Xov73liqDlhoXlrrnvvIkifQ==";
        CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE="+CP_CODE+"&CONFIG="+config);
       
		
	};
   	function DisplayDesign() {		
		CreatePage();
		CNPrint.SET_SHOW_MODE("DESIGN_IN_BROWSE",1);		
	  
	     //----------替换变量--------------------------------------------------------------		
		CNPrint.SET_PRINT_CONTENT("ali_waybill_product_type","1代收货款");//单据类型
		CNPrint.SET_PRINT_CONTENT("ali_waybill_short_address","021D-123-789");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_name","1黑龙江齐齐哈尔集散");//集散地名称
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_code","053277886278");//集散地条码
		CNPrint.SET_PRINT_CONTENT("ali_waybill_waybill_code","111111886278");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_cod_amount","FKFS=到付;PSRQ=2015-07-10");//服务
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_name","齐齐哈尔沐鱼");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_phone","15605883677");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_address","黑龙江省齐齐哈尔市建华区文化大街42号齐齐哈尔大学计算机工程学院计算机001班");//收件人地址
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_name","浙江杭州行者");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_phone","180000980909");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_shipping_address","浙江省杭州市余杭区文一西路1001号阿里巴巴淘宝城5号小邮局");
		CNPrint.SET_PRINT_CONTENT("YUNDA","123456789012");
		//--------------------------------------------------------------------------
	   	CNPrint.PRINT_DESIGN();//打开ISV设计模式
       //CNPrint.PREVIEW();	//输出打印预览任务
	   //CNPrint.PRINT_SETUP();	//打印维护
       		
	};
    function getProgram() {	
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		document.getElementById('S1').value=CNPrint.GET_VALUE("ProgramCodes",0);//CNPrint.GET_VALUE("ProgramCodes",63) //单独获得自定义区域代码
	};	
	function login() {	//登录提供appkey 和seller_id
     	var AppKey=document.getElementById('AppKey').value;
		var Seller_ID=document.getElementById('Seller_ID').value;
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
	    CNPrint.SET_PRINT_IDENTITY("AppKey="+AppKey+"&Seller_ID="+Seller_ID);//登陆appkey、seller_id 验证
	    alert("身份验证成功！\n AppKey="+AppKey+"&Seller_ID="+Seller_ID);
	};
	function goPREVIEW() {	
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		document.getElementById('S1').value;
		CNPrint.PREVIEW();
	};
	function getP() {	
		document.getElementById('S2').value=CNPrint.GET_VALUE("CustomProgramData",0); // 可以应用于C/S及B/S程序调用，功能类似 CNPrint.GET_VALUE("ProgramCodes",63) 
	};
	function S2_PREVIEW_LIST() {
		S2_PREVIEW();
	}
	function S2_PREVIEW() {	
		//CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		//CNPrint.ADD_PRINT_DATA("ProgramData",document.getElementById('S2').value);
		//CNPrint.PREVIEW();
 
		var CP_CODE='EYB';
		
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		var AppKey=document.getElementById('AppKey').value;
		var Seller_ID=document.getElementById('Seller_ID').value;
		CNPrint.SET_PRINT_IDENTITY("AppKey="+AppKey+"98801&Seller_ID="+Seller_ID);
		CNPrint.PRINT_INITA(0,0,400,800,"打印控件功能演示_CNPrint功能_在线编辑获得程序代码");
		var CP_CODE="EYB";
		CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE="+CP_CODE);
		//增加商家自定义区域
		CNPrint.ADD_PRINT_DATA("ProgramData",document.getElementById('S2').value);
		CNPrint.SET_PRINT_STYLEA("ali_waybill_product_type","CONTENT","代收货款");//单据类型
		CNPrint.SET_PRINT_STYLEA("ali_waybill_short_address","CONTENT","EYB大头笔长8字");//大头笔
		CNPrint.SET_PRINT_STYLEA("ali_waybill_package_center_name","CONTENT","EYB集散地无限制");//集散地名称
		CNPrint.SET_PRINT_STYLEA("ali_waybill_package_center_code","CONTENT","053277886278");//集散地条码
		CNPrint.SET_PRINT_STYLEA("ali_waybill_waybill_code","CONTENT","1234555");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cod_amount","CONTENT","FKFS=到付;PSRQ=2015-07-10");//服务
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_name","CONTENT","齐齐哈尔沐鱼");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_phone","CONTENT","15605883677");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_address","CONTENT","黑龙江省齐齐哈尔市建华区文化大街42号齐齐哈尔大学计算机工程学院计算机001班");//收件人地址
		CNPrint.SET_PRINT_STYLEA("ali_waybill_send_name","CONTENT","浙江杭州行者");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_send_phone","CONTENT","180000980909");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_shipping_address","CONTENT","浙江省杭州市余杭区文一西路1001号阿里巴巴淘宝城5号小邮局");
		CNPrint.SET_PRINT_STYLEA("YUNDA","CONTENT","123456789012");
		CNPrint.SET_PRINT_STYLEA("dabiao","CONTENT","");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_up","PreviewOnly",0);
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_down","PreviewOnly",0);

	
		CNPrint.PREVIEW();
		
	};
	
	function S2_PRINT() {	
		//CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		//CNPrint.ADD_PRINT_DATA("ProgramData",document.getElementById('S2').value);
		//CNPrint.PREVIEW();
 
		var CP_CODE='EYB';
		
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
		var AppKey=document.getElementById('AppKey').value;
		var Seller_ID=document.getElementById('Seller_ID').value;
		CNPrint.SET_PRINT_IDENTITY("AppKey="+AppKey+"98801&Seller_ID="+Seller_ID);
		CNPrint.PRINT_INITA(0,0,400,800,"打印控件功能演示_CNPrint功能_在线编辑获得程序代码");
		var CP_CODE="EYB";
		CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE="+CP_CODE);
		CNPrint.ADD_PRINT_DATA("ProgramData",document.getElementById('S2').value);
		CNPrint.SET_PRINT_STYLEA("ali_waybill_product_type","CONTENT","代收货款");//单据类型
		CNPrint.SET_PRINT_STYLEA("ali_waybill_short_address","CONTENT","EYB大头笔长8字");//大头笔
		CNPrint.SET_PRINT_STYLEA("ali_waybill_package_center_name","CONTENT","EYB集散地无限制");//集散地名称
		CNPrint.SET_PRINT_STYLEA("ali_waybill_package_center_code","CONTENT","053277886278");//集散地条码
		CNPrint.SET_PRINT_STYLEA("ali_waybill_waybill_code","CONTENT","1234555");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cod_amount","CONTENT","FKFS=到付;PSRQ=2015-07-10");//服务
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_name","CONTENT","齐齐哈尔沐鱼");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_phone","CONTENT","15605883677");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_consignee_address","CONTENT","黑龙江省齐齐哈尔市建华区文化大街42号齐齐哈尔大学计算机工程学院计算机001班");//收件人地址
		CNPrint.SET_PRINT_STYLEA("ali_waybill_send_name","CONTENT","浙江杭州行者");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_send_phone","CONTENT","180000980909");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_shipping_address","CONTENT","浙江省杭州市余杭区文一西路1001号阿里巴巴淘宝城5号小邮局");
		CNPrint.SET_PRINT_STYLEA("YUNDA","CONTENT","11156789012");
		CNPrint.SET_PRINT_STYLEA("dabiao","CONTENT","");
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_up","PreviewOnly",0);
		CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_down","PreviewOnly",0);
		//CNPrint.PREVIEW();
		CNPrint.PRINT();
		
	};

</script> 

</body>
</html>