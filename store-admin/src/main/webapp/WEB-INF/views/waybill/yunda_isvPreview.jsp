<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<title>电子面单打印</title>
<link href="${ctx}/static/waybill/styles.css" rel="stylesheet"  type="text/css">
<script src="${ctx}/static/waybill/CaiNiaoPrintFuncs.js" type="text/javascript"></script>
<object id="CaiNiaoPrint_OB" classid="clsid:09896DB8-1189-44B5-BADC-D6DB5286AC57" width=0 height=0> 
	<embed id="CaiNiaoPrint_EM" TYPE="application/x-cainiaoprint" width=0 height=0  ></embed>
</object> 
</head>
<body  >
<h2><font color="#009999" size="8pt">菜鸟面单打印</font>
</h2>
<object  id="CaiNiaoPrint_OB_2" classid="clsid:09896DB8-1189-44B5-BADC-D6DB5286AC57" width=1 height=1> 
  <param name="Caption" value="模板">
  <param name="Border" value="1">
  <param name="Color" value="#C0C0C0">
  <embed id="CaiNiaoPrint_EM_2" TYPE="application/x-cainiaoprint" width=1 height=1></object> 

<div style="display:none">

<p align=left ><br />
<input type="hidden" id="AppKey" value="${app_key}" size="40"><br> 
<input type="hidden" id="Seller_ID" value="${user_id}" size="40"><br>  
<input type="hidden" id="CP_CODE" value="${cp_code}" size="40">
<input type="hidden" id="CONFIG" value="0" size="40">
<TABLE><TR><TD>
<p align=left ><br />
<pre><ol class="dp-xml">
<li><a  id="no4" name="no4" href="javascript:DisplayDesign()">打印</a></li>
</pre>
</TR></TD><TD>

</TD> </TR></TABLE>

<p align=left ><br />
<strong ><a id="no1" name="no1">&nbsp&nbsp&nbsp&nbsp5、打印代码生成</a>：</strong></p>
<pre><ol class="dp-xml">
<li><a href="javascript:;" onclick="javascript:getP();">获得自定义区程序数据</a></li>
<li><textarea rows="10" id="S2" cols="105" >自定义区程序数据（64码）</textarea></li>
<li><a href="javascript:;" onclick="javascript:S2_PREVIEW_LIST();">以上自定义区程序数据代码直接预览</a>(自动追加模板所有内容)</li>
</pre>
</div>
<script language="javascript" type="text/javascript"> 
	var CNPrint; //声明为全局变量 
	
	function CreatePage(){
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
	   	CNPrint.PRINT_INITA(0,5,1000,1900,"打印控件功能演示_CNPrint功能_在线编辑获得程序代码");
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
   		login();	
		CreatePage();
		CNPrint.SET_SHOW_MODE("DESIGN_IN_BROWSE",0);		
	  
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
		CNPrint.SET_PRINT_STYLE("FontSize",9); 
		CNPrint.SET_PRINT_STYLE("Bold",1); 
		CNPrint.ADD_PRINT_TEXTA ("sp_name ",574,40,360,100,"${goodsInfos}");
		
		//CNPrint.SET_PRINT_CONTENT("YUNDA",${YUNDA});
		var cpCode=document.getElementById('CP_CODE').value;
		if(cpCode!='EYB'){
			CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_up","PreviewOnly",0);
		}
		//CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_down","PreviewOnly",0);
		
		<c:forEach items="${tradeList}" var="trade">
			CNPrint.NewPageA();
			CNPrint.SET_PRINT_STYLE("FontSize",14); 
			CNPrint.SET_PRINT_STYLE("Bold",1); 
			CNPrint.SET_PRINT_STYLEA("ali_waybill_cp_logo_up","PreviewOnly",0);
			CNPrint.SET_PRINT_CONTENT("ali_waybill_product_type","${trade.ali_waybill_product_type}");//单据类型
			CNPrint.SET_PRINT_CONTENT("ali_waybill_short_address","${trade.ali_waybill_short_address}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_name","${trade.ali_waybill_package_center_name}");//集散地名称
			CNPrint.SET_PRINT_CONTENT("ali_waybill_package_center_code","${trade.ali_waybill_package_center_code}");//集散地条码
			CNPrint.SET_PRINT_CONTENT("ali_waybill_waybill_code","${trade.ali_waybill_waybill_code}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_cod_amount","${trade.ali_waybill_cod_amount}");//服务
			CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_name","${trade.ali_waybill_consignee_name}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_phone","${trade.ali_waybill_consignee_phone}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_consignee_address","${trade.ali_waybill_consignee_address}");//收件人地址
			CNPrint.SET_PRINT_CONTENT("ali_waybill_send_name","${trade.ali_waybill_send_name}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_send_phone","${trade.ali_waybill_send_phone}");
			CNPrint.SET_PRINT_CONTENT("ali_waybill_shipping_address","${trade.ali_waybill_shipping_address}");
			CNPrint.SET_PRINT_STYLE("FontSize",9); 
			CNPrint.SET_PRINT_STYLE("Bold",1)
			CNPrint.ADD_PRINT_TEXTA ("sp_name ",574,40,360,100,"${trade.goodsInfos}");
		</c:forEach>
		//--------------------------------------------------------------------------
	   //	CNPrint.PRINT_DESIGN();//打开ISV设计模式
       CNPrint.PREVIEW();	//输出打印预览任务
	   // CNPrint.PRINT_SETUP();	//打印维护
	    // CNPrint.PRINT();
       		
	};
	function login() {	//登录提供appkey 和seller_id
     	var AppKey=document.getElementById('AppKey').value;
		var Seller_ID=document.getElementById('Seller_ID').value;
		CNPrint=getCaiNiaoPrint(document.getElementById('CaiNiaoPrint_OB_2'),document.getElementById('CaiNiaoPrint_EM_2')); 
	    CNPrint.SET_PRINT_IDENTITY("AppKey="+AppKey+"&Seller_ID="+Seller_ID);//登陆appkey、seller_id 验证
	    //alert("身份验证成功！\n AppKey="+AppKey+"&Seller_ID="+Seller_ID);
	};	
	setTimeout(function(){DisplayDesign()},1000);
</script> 

</body>
</html>