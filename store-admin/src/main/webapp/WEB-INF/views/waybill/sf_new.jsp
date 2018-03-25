﻿<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Powered By 顺丰打印</title>
<meta name="Author" content="360chain Team" />
<meta name="Copyright" content="360chain" />
<link rel="icon" href="favicon.ico" type="image/x-icon" />
<script src="${ctx}/static/waybill/LodopFuncs.js" type="text/javascript"></script>


</head>
<body class="list">
<div class="bar">

</div>
<select id="printIndex"></select>
<button type="button" onclick="MyPrint();" class="btn btn-primary" >点击打印</button>

<div class="body" id="printControllerDiv">
    <table  id="printControllerTable" border="1" style="border-color:gray;" width="100%"  height="100%">
        <tr>
            <td width="100%" colspan="16">
                <object id="LODOP_X" classid="clsid:2105C259-1E0C-4534-8141-A753534CB4CA" width=100% height=650>
                    <param name="Color" value="#d3e6ff">
                    <embed id="LODOP_EM" TYPE="application/x-print-lodop" width=100% height=650  color="#d3e6ff"  PLUGINSPAGE="install_lodop32.exe"></embed>
                </object>
            </td>
        </tr>
    </table>
</div>
<div class="body" id="printLogDiv">
    <table border="0" width="100%"  height="100%">
        <tr>
            <td width="100%" colspan="16">
                打印日志
            </td>
        </tr>
        <tr>
            <td width="100%" colspan="16">
                <textarea  cols=150 rows=25 id="printLogoOutput">
                </textarea>
            </td>
        </tr>
    </table>
</div>
<script language="javascript" type="text/javascript"> 
	var LODOP; //声明为全局变量
	var data='${data}';
	
	function initPrint(){
		//获得打印机个数
		LODOP=getLodop();  
		var count=LODOP.GET_PRINTER_COUNT();
		for(var i=0;i<count;i++){
			var printName=LODOP.GET_PRINTER_NAME(i);
			var html="<option value='"+i+"'>"+printName+"</option>";
			$(html).appendTo("#printIndex");
		}
	}
	initPrint();
	
	function MyPrint() {	
		LODOP=getLodop();  	
		
		var json=eval("("+data+")");
		var s=json.length;
		var n=parseInt(s/10);
		var p=s%10;
		var index=10;
		if(p>0){
			n++;
		}
		var t=0,obj,html;
		for (i = 1; i <=n; i++) {
			LODOP.PRINT_INIT("顺丰快递特惠电子面单打印"+i);
	        LODOP.SET_PRINT_PAGESIZE(0,"1000","1500","LodopCustomPage"); //设置打印纸张,根据具体要求定尺寸
			LODOP.SET_PRINT_MODE("POS_BASEON_PAPER",true);
	   		LODOP.ADD_PRINT_SETUP_BKIMG("<img border='0' src='http://admin.wlpost.com/static/waybill/sf.png'/>");
	   		LODOP.SET_SHOW_MODE("BKIMG_LEFT",0);
	   		LODOP.SET_SHOW_MODE("BKIMG_TOP",0);
	   		LODOP.SET_SHOW_MODE("BKIMG_WIDTH",378);
	   		LODOP.SET_SHOW_MODE("BKIMG_HEIGHT",567);	
			LODOP.SET_SHOW_MODE("BKIMG_PRINT",true);
   			var printIndex=$("#printIndex").val()
   			LODOP.SET_PRINTER_INDEX(printIndex);
			if(i==n){
				index=p;
			}	
			for (j = 1; j <=index; j++) {
				obj=json[t];
				CreateOnePage(obj,t+1);	
				html="<h3>"+obj.sf_destname+"已发送到打印机</h3>";
				$(html).insertBefore("#printLogoOutput");
				t++;		
			}	
			LODOP.SET_PRINT_MODE("SF_TASK_NAME","顺丰电子面单"+t);//为每个打印单独设置任务名	
			LODOP.PRINT();
		}
	};	
	
	function CreateOnePage(obj,seq){	
		LODOP.NewPage();
		//标记E
   	  	LODOP.SET_PRINT_STYLE("FontSize",24);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("s_e",22,195,61,40,"E");
   	    LODOP.SET_PRINT_STYLE("FontSize",14);
   	    LODOP.ADD_PRINT_TEXTA("s_count",37,95,61,40,seq);
		 //上方运单号 条形码
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    LODOP.ADD_PRINT_BARCODE(65,50,161,50,"128C",obj.sf_orderno);
   	    //目的地
   	    LODOP.SET_PRINT_STYLE("FontSize",12); 
   	    LODOP.ADD_PRINT_TEXTA("s_date",60,270,93,27,"顺丰特惠");
   	    
   	   // LODOP.SET_PRINT_STYLE("FontSize",8); 
   	    LODOP.ADD_PRINT_TEXTA("label_address",85,255,93,27,"目的地：");
   	    LODOP.SET_PRINT_STYLE("FontSize",24); 
   	    LODOP.ADD_PRINT_TEXTA("s_address",99,263,93,27,obj.sf_destcode);
   	    
   	    //收货地址
   	    LODOP.SET_PRINT_STYLE("FontSize",10); 
   	 	LODOP.ADD_PRINT_TEXTA("label_receive",136,15,80,27,"收方：");
   	   	LODOP.ADD_PRINT_TEXTA("s_receive",136,50,295,27,obj.sf_destaddress);
   	   	  //详细地址
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.ADD_PRINT_TEXTA("s_addressInfo",150,15,350,27,obj.sf_destdetailaddress);
   	     //收货人  和 收货电话号码
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.ADD_PRINT_TEXTA("s_destname",180,15,220,22,obj.sf_destname);
   	    //代收货款
   	    LODOP.SET_PRINT_STYLE("FontSize",12);
   	    LODOP.ADD_PRINT_TEXTA("s_p",215,282,90,18,"");
   	    
   	     //卡号
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	 	LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("label_card",234,255,80,22,"卡号：");
   	    
   	     //运费
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	 	LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("label_yun",253,255,90,18,"运费：");
   	 	LODOP.ADD_PRINT_TEXTA("label_total_price",270,255,90,18,"费用合计：");
   	 	
   	 	 //同意自取
   	    LODOP.SET_PRINT_STYLE("FontSize",12);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("label_a",246,16,120,22,"同意自取");
   	    
   	    //转寄协议客户
   	    LODOP.SET_PRINT_STYLE("FontSize",12);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	  	LODOP.ADD_PRINT_TEXTA("label_x",266,16,120,22,"转寄协议客户");
   	  	
   	  	//下方运单号 条形码
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    LODOP.ADD_PRINT_BARCODE(350,190,180,50,"128C",obj.sf_orderno);
   	    
   	    //寄件信息
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	 	LODOP.ADD_PRINT_TEXTA("label_jf",295,15,80,22,"寄方：");
   	 	LODOP.SET_PRINT_STYLE("FontSize",7);
   	    LODOP.ADD_PRINT_TEXTA("s_selleraddress",295,55,150,22,obj.sf_selleraddress);
   	    LODOP.ADD_PRINT_TEXTA("s_sellerdetail",307,55,150,22,obj.sf_sellerdetailaddress);
   	    LODOP.ADD_PRINT_TEXTA("s_sellername",329,55,150,22,obj.sf_sellername);
   	    
   	    //下寄件方信息
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	 	LODOP.ADD_PRINT_TEXTA("l_jf",410,15,50,22,"寄方：");
   	 	
   	 	LODOP.SET_PRINT_STYLE("FontSize",7);
   	 	LODOP.SET_PRINT_STYLE("Bold",0);
   	 	LODOP.ADD_PRINT_TEXTA("s_sfaddress",410,45,150,22,obj.sf_selleraddress);
   	    LODOP.ADD_PRINT_TEXTA("s_sfdetail",422,15,150,22,obj.sf_sellerdetailaddress);
   	    LODOP.ADD_PRINT_TEXTA("s_sfname",450,15,150,22,obj.sf_sellername);
   	    
   	    //下收方信息
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	 	LODOP.ADD_PRINT_TEXTA(410,175,50,22,"收方：");
   	    LODOP.SET_PRINT_STYLE("FontSize",7);
   	 	LODOP.SET_PRINT_STYLE("Bold",0);
   	    LODOP.ADD_PRINT_TEXTA("s_destaddress",410,205,165,32,obj.sf_destdetailaddress);
   	    LODOP.ADD_PRINT_TEXTA("s_destname",450,175,180,22,obj.sf_destname);
   	    
   	    //  //数量   寄托物品
   	    LODOP.SET_PRINT_STYLE("FontSize",10);
   	    //LODOP.ADD_PRINT_TEXTA("L_X",482,10,25,22,"");
   	    LODOP.ADD_PRINT_TEXTA("s_selleritems",475,15,350,85,obj.sf_selleritems);
   	    
   	    //备注
   	    LODOP.ADD_PRINT_TEXTA("s_remark",482,300,50,30,"");
   	    
   	    //合计费用
   	    //LODOP.SET_PRINT_STYLE("FontSize",7);
   	    //LODOP.ADD_PRINT_TEXTA("l_hj",525,315,80,20,"费用合计：");
   	    
   	    //原寄地CODE
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	 	LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("label_jd",315,15,60,22,"原寄地：");
   	    LODOP.SET_PRINT_STYLE("FontSize",12);
   	    LODOP.SET_PRINT_STYLE("Bold",1);
   	    LODOP.ADD_PRINT_TEXTA("s_sellercode",325,15,60,22,obj.sf_sellercode);
   	     //单号
   	   	//LODOP.SET_PRINT_STYLE("FontSize",8);
   	 	//LODOP.SET_PRINT_STYLE("Bold",1);
   	    //LODOP.ADD_PRINT_TEXTA("label_orderno",525,15,285,22,"订单号：");
   	 	//LODOP.SET_PRINT_STYLE("FontSize",6);
   	   	//LODOP.ADD_PRINT_TEXTA("s_orderno",539,15,285,22,obj.sf_selforderno);
   	    
   	    //付款方式 
   	    LODOP.SET_PRINT_STYLE("FontSize",9);
   	    LODOP.ADD_PRINT_TEXTA("label_fs",206,16,83,18,"付款方式:");
   	    LODOP.ADD_PRINT_TEXTA("s_fs",206,86,83,18,"寄付月结 ");
   	    
   	    //月结账号
   	    LODOP.ADD_PRINT_TEXTA("label_account",226,16,83,18,"月结帐号:");
   	    LODOP.ADD_PRINT_TEXTA("s_account",226,86,83,18,"7312005783");
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    
   	    //计费重量
   	    LODOP.ADD_PRINT_TEXTA("label_weight",206,149,73,18,"计费重量:");
   	    //LODOP.ADD_PRINT_TEXTA("s_weight",206,209,53,18,"${trade.weight}");
   	    LODOP.SET_PRINT_STYLE("FontSize",8);
   	    //打印时间
   	    LODOP.ADD_PRINT_TEXTA("label_time",182,250,74,18,"打印时间：");
   	    LODOP.ADD_PRINT_TEXTA("s_time",182,310,74,18,obj.sf_date);
	};	
	
</script>
</body>
</html>
