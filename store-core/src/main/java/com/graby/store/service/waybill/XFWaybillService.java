package com.graby.store.service.waybill;


import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.graby.store.entity.Centro;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;

/**
 * 顺丰专用
 * @author lenovo
 *
 */
public class XFWaybillService {
	
	private static String order_service_url="https://bsp-oisp.test.sf-express.com/bsp-oisp/sfexpressService";//下订单

	private static String checkword = "j8DzkIFgmlomPt0aLuwU";//"PAYHZH    Hi9go87nCbFEuRWC";
	
	private static String xmlFile="d:\\xml.txt";
	
	public static void main(String[] args) throws Exception {
//		String  xml = ""+
//		"<Request service='OrderService' lang='zh-CN'>"+
//		"<Head>BSPdevelop</Head>"+
//		"<Body>"+
//		"<Order orderid ='XJFS_09110031'"+
//		" j_company='华为'"+
//		" j_contact='任先生' j_tel='010-1111112' j_mobile='13800138000'"+
//		" j_province='北京' j_city='北京' j_county='朝阳区'"+
//		" j_address='北京市朝阳区科学园科健路338号'"+
//		" d_company='顺丰速运'"+
//		" d_contact='西门俊宇' d_tel='无' d_mobile='17002930913'"+
//		" d_province='广东省' d_city='深圳市' d_county='福田区'"+
//		" d_address='广东省深圳市福田区新洲十一街万基商务大厦10楼'"+
//		" express_type ='1'"+
//		" pay_method ='2' custid ='7551878519'"+
//		" parcel_quantity ='1' cargo_total_weight ='2.35' sendstarttime ='2015-01-16 10:26:43'"+
//		" order_source ='西门府' remark =''>"+
////		" <Cargo Name='扇子' count='2' unit='台' weight='0.02' amount='100' currency='CNY' source_area='中国'></Cargo>"+
//		" </Order>"+
//		"</Body></Request>";
		
		
	    Trade trade  = new Trade();
	    User  user  =  new User();
	    Centro  centro  =new Centro();
	    //_SYSTEM  默认值
//	    "<Request service='OrderService' lang='zh-CN'>"+
//		"<Head>BSPdevelop</Head>"+
//		"<Body>"+
	    StringBuffer xmlStr  =  new StringBuffer("<Request service='OrderService' lang='zh-CN'>");
	    xmlStr.append("<Head>BSPdevelop</Head>");
	    xmlStr.append("<Body>");
	    
//	    "<Order orderid ='XJFS_09110031'"+
	    xmlStr.append("<Order orderid ='");
	    xmlStr.append(trade.getId()).append("'");
//	    " j_company='华为'"   寄件方公司名称
	    xmlStr.append(" j_company='").append(centro.getName()).append("'");; //必填
//	    " j_contact='任先生' j_tel='010-1111112' j_mobile='13800138000'"+
	    xmlStr.append(" j_contact='").append(user.getShopName()).append("'"); //必填
	    xmlStr.append(" j_tel='").append(trade.getSellerPhone()).append("'"); //必填 _SYSTEM
	    xmlStr.append(" j_mobile='").append(trade.getSellerMobile()).append("'");
	    
	    
//	    " j_province='北京' j_city='北京' j_county='朝阳区'"+
//		" j_address='北京市朝阳区科学园科健路338号'"+
	    xmlStr.append(" j_province='").append(centro.getProvince()).append("'");
	    xmlStr.append(" j_city='").append(centro.getCity()).append("'");
	    xmlStr.append(" j_county='").append(centro.getArea()).append("'");
	    xmlStr.append(" j_address='").append(centro.getAddress()).append("'");
	    
	    
//		" d_company='顺丰速运'"+
	    xmlStr.append(" d_company='无'");
//		" d_contact='西门俊宇' d_tel='无' d_mobile='17002930913'"+
	    xmlStr.append(" d_contact='").append(trade.getReceiverName()).append("'");
	    xmlStr.append(" d_tel='").append(trade.getReceiverPhone()).append("'");
	    xmlStr.append(" d_mobile='").append(trade.getReceiverMobile()).append("'");
	    
	    
//		" d_province='广东省' d_city='深圳市' d_county='福田区'"+
//		" d_address='广东省深圳市福田区新洲十一街万基商务大厦10楼'"+
	    xmlStr.append(" d_province='").append(trade.getReceiverState()).append("'");
	    xmlStr.append(" d_city='").append(trade.getReceiverCity()).append("'");
	    xmlStr.append(" d_county='").append(trade.getReceiverDistrict()).append("'");
	    xmlStr.append(" d_address='").append(trade.getReceiverAddress()).append("'");
	    
	    //  3.顺丰电商特惠
	    xmlStr.append(" express_type='3'");
		
//		" pay_method ='2' custid ='7551878519'"+
	    xmlStr.append(" custid='731524035'");
//		" parcel_quantity ='1' cargo_total_weight ='2.35' sendstarttime ='2015-01-16 10:26:43'"+
//		" order_source ='西门府' remark =''>"+
	    //1 .寄放付  、    2. 收方付
	    xmlStr.append(" pay_method='1'>");
//		" </Order>"+
//		"</Body></Request>";
	    xmlStr.append("</Order>");
	    xmlStr.append("</Body></Request>");
		int port = 9443;
//		String xml=Util.loadFile(xmlFile);
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

		if (response.getStatusLine().getStatusCode() == 200){
			System.out.println(EntityUtils.toString(response.getEntity()));
		} else {
			EntityUtils.consume(response.getEntity());
			throw new RuntimeException("response status error: " + response.getStatusLine().getStatusCode());
		}
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
