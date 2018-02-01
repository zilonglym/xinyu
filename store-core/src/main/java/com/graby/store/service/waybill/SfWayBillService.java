package com.graby.store.service.waybill;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.remote.CentroRemote;
import com.graby.store.service.base.CentroService;
import com.graby.store.service.base.UserService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.trade.TradeService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.service.wms.SystemItemService;
import com.graby.store.util.AddressUtil;
import com.graby.store.util.qm.XmlUtil;

import jxl.common.Logger;


@Component
public class SfWayBillService {
	
	Logger logger=Logger.getLogger(SfWayBillService.class);
	
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private TradeService tradeService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService  userService  ;
	@Autowired
	private CentroService centroService;
	@Autowired
	private SystemItemService systemService;
	
	
	private static String order_service_url="http://bsp-oisp.sf-express.com/bsp-oisp/sfexpressService";//下订单
	private static String checkword = "B65oS5ljKgGlVfS7VwJeLGtPc6WPulME";//"PAYHZH    Hi9go87nCbFEuRWC";  RccCt43AVd8ESfuk
	private static String check_no="7310524035";
//	private  String check_no="7312005783"; //2017-06-18修改
	private static  int port = 9443;
	
	
	@Transactional
	public Map<String, Object> getSFBillNo(String[] ids,String batchCode) throws Exception {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,String> map=new HashMap<String, String>();
		for(int i=0;i<ids.length;i++){
			Trade trade=this.tradeService.getTrade(Long.valueOf(ids[i]));
			/**
			 * 如果该单据已经有运单号了。则认为此批单据都是有运单号的。这里视为订单重打。不再取数
			 */
			ShipOrder order=this.shipOrderService.getSendShipOrderByTradeId(trade.getId());
			if(order!=null && order.getExpressOrderno()!=null){
				continue;
			}
			map=billSfGet(trade,batchCode);
			if(map==null || !map.get("code").equals("200")){
				/**
				 * 如果单据取号不成功，则中断这批操作。并返回
				 */
				
				throw new Exception("");
			}
		}
		resultMap.put("code", 200);
		return resultMap;
	}
	
	/**
	 * <pre>
	 * 顺丰电子面单下单
	 * service  OrderService
	 * @throws IOException 
	 * @throws ClientProtocolException  
	 * </pre>
	 */
	public Map<String,String> billSfGet(Trade trade,String batchCode) throws ClientProtocolException, IOException{

	    User  user  = userService.getUser(trade.getUser().getId());
	    Centro  centro  =centroService.findCentroById(""+trade.getCentro().getId());
	    //_SYSTEM  默认值
//	    "<Request service='OrderService' lang='zh-CN'>"+
//		"<Head>7310524035</Head>"+
//		"<Body>"+
	    StringBuffer xmlStr  =  new StringBuffer("<Request service='OrderService' lang='zh-CN'>");
	    
	    xmlStr.append("<Head>"+check_no+"</Head>");
	    xmlStr.append("<Body>");
	    
	//    xmlStr.append("<Order orderid ='000003'");
	    xmlStr.append("<Order orderid ='");
	    xmlStr.append(trade.getOtherOrderNo()).append("'");
//	    " j_company='华为'"   寄件方公司名称
	    xmlStr.append(" j_company='星宇物流'");; //必填
//	    " j_contact='任先生' j_tel='010-1111112' j_mobile='13800138000'"+
	    xmlStr.append(" j_contact='").append(user.getShopName()).append("'"); //必填
	    
	    xmlStr.append(" j_tel='").append(user.getCode()).append("'"); //必填 _SYSTEM
	    xmlStr.append(" j_mobile='").append("13337223310").append("'");
//	    xmlStr.append(" j_tel='").append(trade.getSellerPhone()!=null?trade.getSellerPhone():"").append("'"); //必填 _SYSTEM
//	    xmlStr.append(" j_mobile='").append(trade.getSellerMobile()!=null?trade.getSellerMobile():"").append("'");
	    
	    
//	    " j_province='北京' j_city='北京' j_county='朝阳区'"+
//		" j_address='北京市朝阳区科学园科健路338号'"+
	    xmlStr.append(" j_province='").append(centro.getProvince()).append("'");
	    xmlStr.append(" j_city='").append(centro.getCity()).append("'");
	    xmlStr.append(" j_county='").append(centro.getArea()).append("'");
	    xmlStr.append(" j_address='").append(centro.getSfAddress().replaceAll("&", "")).append("'");
	    
	    
//		" d_company='顺丰速运'"+
	    xmlStr.append(" d_company='无'");
//		" d_contact='西门俊宇' d_tel='无' d_mobile='17002930913'"+
	    xmlStr.append(" d_contact='").append(AddressUtil.sfFormat(trade.getReceiverName())).append("'");
	    xmlStr.append(" d_tel='").append(trade.getReceiverPhone()!=null?trade.getReceiverPhone():trade.getReceiverMobile()).append("'");
	    xmlStr.append(" d_mobile='").append(StringUtils.isNotBlank(trade.getReceiverMobile())?trade.getReceiverMobile():trade.getReceiverPhone()).append("'");
	    
	    
//		" d_province='广东省' d_city='深圳市' d_county='福田区'"+
//		" d_address='广东省深圳市福田区新洲十一街万基商务大厦10楼'"+
	    xmlStr.append(" d_province='").append(trade.getReceiverState()).append("'");
	    xmlStr.append(" d_city='").append(trade.getReceiverCity()).append("'");
	    xmlStr.append(" d_county='").append(trade.getReceiverDistrict()).append("'");
	    xmlStr.append(" d_address='").append(AddressUtil.sfFormat(trade.getReceiverAddress())).append("'");
	    
	    //  3.顺丰电商特惠   28  电商专配  38 云仓隔日,2顺丰特惠
	    xmlStr.append(" express_type='2'");
		
//		" pay_method ='2' custid ='7551878519'"+
	    xmlStr.append(" custid='7312005783'");
//		" parcel_quantity ='1' cargo_total_weight ='2.35' sendstarttime ='2015-01-16 10:26:43'"+
//		" order_source ='西门府' remark =''>"+
	    //1 .寄放付  、    2. 收方付
	    xmlStr.append(" pay_method='1'  parcel_quantity='1'");
	    xmlStr.append(" cargo_total_weight='").append("totalWeight").append("'>");
	    List<TradeOrder> orders = trade.getOrders();
	    double totalWeight=0f;
	    if(orders!=null ){
	    	for(int i = 0 , size  = orders.size();  i<size ;i++){
	    		TradeOrder tradeOrder = orders.get(i);
	    		Item item = itemService.getItem(tradeOrder.getItem().getId());
	    		if(item!=null){
	    			totalWeight+=item.getPackageWeight()*tradeOrder.getNum();
	    		}else{
	    			totalWeight+=0f;
	    		}
	    		xmlStr.append("<Cargo name='"+item.getTitle()+"' > </Cargo>");
	    	}
	    }
	   int k= xmlStr.indexOf("totalWeight");
	    xmlStr.replace(k, k+11, totalWeight+"");
	    
	    xmlStr.append("</Order>");
	    xmlStr.append("</Body></Request>");
	    System.out.println("顺丰参数:"+xmlStr);
		HttpResponse response = getHttpResponse(xmlStr.toString());
		
		Map<String,String>  retMap  =  new HashMap<String, String>();
		if (response.getStatusLine().getStatusCode() == 200){
			String domStr=EntityUtils.toString(response.getEntity());
			System.out.println("顺丰返回:"+domStr);
			Map<String, Object> dom2Map  =new HashMap<String, Object>();
			try {
				dom2Map = XmlUtil.Dom2Map(domStr);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
			String headDom =""+dom2Map.get("Head");
			if("OK".equals(headDom)){ //返回成功
				Map<String,Object> bodyDom = (Map<String,Object>)dom2Map.get("Body");
				if(bodyDom.get("mailno")==null){
					Map<String,Object> reBodyDom   = reBillSfSearch(trade);
					if(reBodyDom!=null){
						bodyDom  = reBodyDom;
					}
				}
				String orderid  = bodyDom.get("orderid")!=null?""+bodyDom.get("orderid"):null; //客户订单ID  
				String mailno  =  bodyDom.get("mailno")!=null?""+bodyDom.get("mailno"):null;  //顺丰订单号
				String origincode  =  bodyDom.get("origincode")!=null?""+bodyDom.get("origincode"):null;; //寄件人区域
				String destcode  =  bodyDom.get("destcode")!=null?""+bodyDom.get("destcode"):null;;  //目的区域编码
			
				retMap  = new HashMap<String, String>();
				try{
					
				  //更新出库单状态
					ShipOrder sendShipOrder = shipOrderService.getSendShipOrderByTradeId(trade.getId());
					List<Map<String,String>> paramMapList =  new  ArrayList<Map<String,String>>();
					Map<String,String>  paramMap =  new HashMap<String, String>();
					paramMap.put("id", ""+sendShipOrder.getId());
					paramMap.put("expressCompany", "SF");
					paramMap.put("expressOrderno", mailno);
					
					paramMap.put("status", OrderStatusEnums.WMS_GETNO.getKey());
					
					paramMap.put("orderFlag", "SF");  //  大头
					paramMap.put("sellerMobile", origincode); //寄件人区域
					paramMap.put("sellerPhone", destcode);    //目的区域编码
					paramMap.put("batchCode", batchCode);
					paramMapList.add(paramMap);
					shipOrderService.setSendOrderExpressAndStatusById(paramMapList);
					//更新订单状态
					tradeService.updateTradeStatus(trade.getId(), Trade.Status.TRADE_WAIT_EXPRESS_NOFITY);
					retMap.put("flag", "成功");
					retMap.put("orderNo",mailno);
					retMap.put("code", "200");
				}catch(Exception e){
					e.printStackTrace();
					retMap.put("flag", "接口调用成功,数据反写系统失败");
					retMap.put("code", "501");
				}
			}else{  //ERR
				String  err  = ""+dom2Map.get("ERROR");
				retMap.put("flag", "接口调用成功,接口数据异常");
				retMap.put("error", err);
				retMap.put("code", "502");
				
			}
		} else {
			EntityUtils.consume(response.getEntity());
			throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
		}
		return retMap;
	}
	
	private  HttpResponse   getHttpResponse(String xml) throws ClientProtocolException, IOException{
		System.out.println("xml:"+xml);
		String verifyCode=Util.md5EncryptAndBase64(xml + checkword);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("xml", xml));
		nvps.add(new BasicNameValuePair("verifyCode", verifyCode));
		
		HttpClient httpclient=getHttpClient(port);
		
		HttpPost httpPost = new HttpPost(order_service_url);
		
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		
		HttpResponse response = httpclient.execute(httpPost);
		
		return response;
	}
	/**
	 * <pre>
	 * 顺丰电子面单确认 or 取消
	 * service OrderConfirmService 
	 * dealtype  1： 确认 2： 取消
	 * @throws IOException
	 * @throws ClientProtocolException
	 * </pre>
	 */
	public Map<String,String> billSfConfirm(Trade trade,String dealtype) throws ClientProtocolException, IOException{
		
		if(dealtype==null  || "".equals(dealtype)){
			dealtype  =  "1";
		}
		
		User user = userService.getUser(trade.getUser().getId());
		Centro centro = centroService.findCentroById(""+trade.getCentro().getId());
		
		ShipOrder sendShipOrder = shipOrderService.getSendShipOrderByTradeId(trade.getId());
		
		StringBuffer xmlStr = new StringBuffer("<Request service='OrderConfirmService' lang='zh-CN'>");
		xmlStr.append("<Head>7310524035</Head>");
		xmlStr.append("<Body>");
		
		
	    xmlStr.append("<Order orderid ='");
	    xmlStr.append(trade.getId()).append("'");
	    xmlStr.append(" mailno='").append(sendShipOrder.getExpressOrderno()).append("'");
	    xmlStr.append(" dealtype='").append(dealtype).append("'></OrderConfirm>");
	    xmlStr.append("</Body></Request>");
	    
	    
	    String xml  =  xmlStr.toString();
		System.out.println("xml:"+xmlStr.toString());
		String verifyCode=Util.md5EncryptAndBase64(xml + checkword);
		
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("xml", xml));
		nvps.add(new BasicNameValuePair("verifyCode", verifyCode));
		
		HttpClient httpclient=getHttpClient(port);
		HttpPost httpPost = new HttpPost(order_service_url);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		HttpResponse response = httpclient.execute(httpPost);
		
		System.out.println(response.getEntity().toString());
		
		
		

		return null;
	}
	
	
	/**
	 * @param trade
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private Map<String,Object> reBillSfSearch(Trade trade) throws ClientProtocolException, IOException{
		
		StringBuffer xmlStr = new StringBuffer("<Request service='OrderSearchService' lang='zh-CN'>");
		xmlStr.append("<Head>7310524035</Head>");
		xmlStr.append("<Body>");
		
		
	    xmlStr.append("<Order orderid ='");
	    xmlStr.append(trade.getId()).append("'/>");
	    xmlStr.append("</Body></Request>");
	    
	    HttpResponse response = getHttpResponse(xmlStr.toString());
	    
		Map<String,Object>  retMap  =  null;
		
		if (response.getStatusLine().getStatusCode() == 200){
			

			String domStr=EntityUtils.toString(response.getEntity());
			System.out.println(domStr);
			Map<String, Object> dom2Map  = null;
			try {
				
				dom2Map = XmlUtil.Dom2Map(domStr);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
			String headDom =""+dom2Map.get("Head");
			
			if("OK".equals(headDom)){ //返回成功
				Map<String,Object> bodyDom = (Map<String,Object>)dom2Map.get("Body");
				return bodyDom;
			}else{  //ERR
				String  err  = ""+dom2Map.get("ERROR");
				retMap.put("flag", "接口调用成功,接口数据异常");
				retMap.put("error", err);
				retMap.put("code", "502");
			}
		} else {
			EntityUtils.consume(response.getEntity());
			throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
		}
		return retMap;
	}
	
	
	/**
	 * <pre>
	 * 顺丰电子查询
	 * service OrderSearchService 
	 * @throws IOException
	 * @throws ClientProtocolException
	 * </pre>
	 */
	public Map<String,String> billSfSearch(Trade trade) throws ClientProtocolException, IOException{
		
		StringBuffer xmlStr = new StringBuffer("<Request service='OrderSearchService' lang='zh-CN'>");
		xmlStr.append("<Head>7310524035</Head>");
		xmlStr.append("<Body>");
		
		
	    xmlStr.append("<Order orderid ='");
	    xmlStr.append(trade.getId()).append("'/>");
	    xmlStr.append("</Body></Request>");
	    
	    HttpResponse response = getHttpResponse(xmlStr.toString());
	    
		Map<String,String>  retMap  =  null;
		
		if (response.getStatusLine().getStatusCode() == 200){
			

			String domStr=EntityUtils.toString(response.getEntity());
			System.out.println(domStr);
			Map<String, Object> dom2Map  = null;
			try {
				
				dom2Map = XmlUtil.Dom2Map(domStr);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
			String headDom =""+dom2Map.get("Head");
			
			if("OK".equals(headDom)){ //返回成功
				
				Map<String,Object> bodyDom = (Map<String,Object>)dom2Map.get("Body");
				String orderid  = bodyDom.get("orderid")!=null?""+bodyDom.get("orderid"):null; //客户订单ID  
				String mailno  =  bodyDom.get("mailno")!=null?""+bodyDom.get("mailno"):null;;   //顺丰订单号
//				String origincode  =  bodyDom.get("origincode")!=null?""+bodyDom.get("origincode"):null;; //寄件人区域
//				String destcode  =  bodyDom.get("destcode")!=null?""+bodyDom.get("destcode"):null;;  //目的区域编码

				retMap = new HashMap<String, String>();

				retMap.put("flag", "成功");
				retMap.put("orderid", orderid);
				retMap.put("orderNo", mailno);
				retMap.put("code", "200");
			}else{  //ERR
				String  err  = ""+dom2Map.get("ERROR");
				retMap.put("flag", "接口调用成功,接口数据异常");
				retMap.put("error", err);
				retMap.put("code", "502");
				
			}
		} else {
			EntityUtils.consume(response.getEntity());
			throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
		}
		return retMap;
	}
	
	private static HttpClient getHttpClient(int port){
		PoolingClientConnectionManager pcm = new PoolingClientConnectionManager();
		SSLContext ctx=null;
		try{
			ctx = SSLContext.getInstance("TLS");
			X509TrustManager x509=new X509TrustManager(){
				public void checkClientTrusted(X509Certificate[] xcs, String string)
					throws CertificateException {
				}
				public void checkServerTrusted(X509Certificate[] xcs, String string)
					throws CertificateException {
				}
				public X509Certificate[] getAcceptedIssuers(){
					return null;
				}
			};
			ctx.init(null, new TrustManager[]{x509}, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Scheme sch = new Scheme("https", port, ssf);
		pcm.getSchemeRegistry().register(sch);
		return new DefaultHttpClient(pcm);
	}
}
