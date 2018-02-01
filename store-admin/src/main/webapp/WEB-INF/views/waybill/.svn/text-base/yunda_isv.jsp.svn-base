<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>电子面单</title>
<link href="${ctx}/static/waybill/styles.css" rel="stylesheet"  type="text/css">
<script src="${ctx}/static/waybill/CaiNiaoPrintFuncs.js" type="text/javascript"></script>
<object id="CaiNiaoPrint_OB" classid="clsid:09896DB8-1189-44B5-BADC-D6DB5286AC57" width=0 height=0> 
	<embed id="CaiNiaoPrint_EM" TYPE="application/x-cainiaoprint" width=0 height=0  ></embed>
</object> 
</head>
<body >
<h2><font color="#009999" size="6pt">电子面单打印</font>
</h2>

<p align=left ><br />
<input type="hidden" id="AppKey" value="98801" size="40"><br> 
<input type="hidden" id="Seller_ID" value="155809" size="40"><br>  
<input type="hidden" id="CP_CODE" value="EYB" size="40">
<input type="hidden" id="CONFIG" value="0" size="40">
<TABLE><TR><TD>
<p align=left ><br />
<pre><ol class="dp-xml">
<li><a  id="no4" name="no4" href="javascript:DisplayDesign()">打印</a></li>
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
	    var config="";
        CNPrint.SET_PRINT_MODE("CAINIAOPRINT_MODE","CP_CODE="+CP_CODE+"&CONFIG="+config);
       
		
	};
   	function DisplayDesign() {		
		CreatePage();
		CNPrint.SET_SHOW_MODE("DESIGN_IN_BROWSE",1);		
	  
	     //----------替换变量--------------------------------------------------------------		
		CNPrint.SET_PRINT_CONTENT("ali_waybill_product_type","${ali_waybill_product_type}");//单据类型
		CNPrint.SET_PRINT_CONTENT("ali_waybill_short_address","${ali_waybill_short_address}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_name","${ali_waybill_package_center_name}");//集散地名称
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_code","${ali_waybill_package_center_code}");//集散地条码
		CNPrint.SET_PRINT_CONTENT("ali_waybill_waybill_code","${ali_waybill_waybill_code}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_cod_amount","${ali_waybill_cod_amount}");//服务
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_name","${ali_waybill_consignee_name}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_phone","${ali_waybill_consignee_phone}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_address","${ali_waybill_consignee_address}");//收件人地址
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_name","${ali_waybill_send_name}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_phone","${ali_waybill_send_phone}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_shipping_address","${ali_waybill_shipping_address}");
		CNPrint.SET_PRINT_CONTENT("YUNDA",${YUNDA});
		CNPrint.ADD_PRINT_TEXTA ("sp_name ",574,40,400,50,"${goodsInfo}11");
		CNPrint.NewPageA();
		CNPrint.SET_PRINT_CONTENT("ali_waybill_product_type","${ali_waybill_product_type}");//单据类型
		CNPrint.SET_PRINT_CONTENT("ali_waybill_short_address","湖南株洲向阳村");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_name","${ali_waybill_package_center_name}");//集散地名称
		CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_code","${ali_waybill_package_center_code}");//集散地条码
		CNPrint.SET_PRINT_CONTENT("ali_waybill_waybill_code","${ali_waybill_waybill_code}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_cod_amount","${ali_waybill_cod_amount}");//服务
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_name","${ali_waybill_consignee_name}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_phone","${ali_waybill_consignee_phone}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_address","${ali_waybill_consignee_address}");//收件人地址
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_name","${ali_waybill_send_name}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_send_phone","${ali_waybill_send_phone}");
		CNPrint.SET_PRINT_CONTENT("ali_waybill_shipping_address","${ali_waybill_shipping_address}");
		CNPrint.ADD_PRINT_TEXTA ("sp_name ",574,40,400,50,"${goodsInfo}");
		//--------------------------------------------------------------------------
	   	//CNPrint.PRINT_DESIGN();//打开ISV设计模式
        CNPrint.PREVIEW();	//输出打印预览任务
	   //CNPrint.PRINT_SETUP();	//打印维护
       		
	};
	
	setTimeout(function(){DisplayDesign();},100);
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
	

	
</script> 

</body>
</html>