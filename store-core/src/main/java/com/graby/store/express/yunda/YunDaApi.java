package com.graby.store.express.yunda;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.graby.store.express.yunda.api.DataSecurity;
import com.graby.store.express.yunda.api.HttpClient;

/**
 * @author shark_cj
 * @since  2017-08-11
 * 韵达接口
 */

@Component
public class YunDaApi {
		
	
		//正式数据  ：4111063456                 jMcNg36EeCnASwpf8zrJP5tdVbRUXG
	
		//7864641052	KkPHQixh7wE2MeyRrAFv8qf4aScmJ3     测试用
		//orderdev.yundasys.com:10110    测试用
		private static String partnerid = "4111063456";
		
		private static String password = "jMcNg36EeCnASwpf8zrJP5tdVbRUXG";
		
		private static String version = "1.0";
		
		private static String  checkAddressUrl=  "http://order.yundasys.com:10235/cus_order/order_interface/interface_new_package.php";
		
		
		
		
		/**
		 * 韵达 API  检测 地址是否达到
		 * @param maps
		 * @return
		 */
		public Map<String, Object> checkYunDaAddressByList(Map<String,Map<String,Object>>  maps) {
			Map<String,Object>  retMap  = new HashMap<String, Object>();
			try {
				
				StringBuffer xmldata = new StringBuffer("<orders>");

				Iterator<String> iter = maps.keySet().iterator();

				while (iter.hasNext()) {
					
					String key = iter.next();
					Map<String, Object> value = maps.get(key);
					String orderStr =	"<order>"
							+"<id>"+value.get("id")+"</id>"
							+"<receiver_address>"+value.get("address")+"</receiver_address>"
							+"</order>";
					xmldata.append(orderStr);

				}
				xmldata.append("</orders>");
//				+"<order>"
//				+"<id>2310000088544</id>"
//				+"<receiver_address>山西省太原市小店区坞城路100号</receiver_address>"
//				+"</order>"
				
				String data = DataSecurity.security(partnerid, password, xmldata.toString());
				data += "&version=" + version + "&request=data";
				String result = HttpClient.post(checkAddressUrl, data);
				result =  "<xinyu>"+result+"</xinyu>";
				System.out.println(result);
				Map<String, Object> map = com.graby.store.util.qm.XmlUtil.Dom2Map(result);
				Map<String, Object> map1  =(Map<String, Object>) map.get("responses");
				Object object = map1.get("response");
				if(object  instanceof List){
					List<Map<String, Object>>  reqMapList  = (List<Map<String, Object>>) object;
					
					for(Map<String, Object> mapTemp  : reqMapList){
						Map<String, Object> mapsTemp = maps.get(mapTemp.get("id"));
						//1 表示可以到达 ,0 表示不到达 
						mapsTemp.put("reach", mapTemp.get("reach"));
					}
				
				}else{
					Map<String, Object> mapTemp = (Map<String, Object>) object;
					Map<String, Object> mapsTemp = maps.get(mapTemp.get("id"));
					//1 表示可以到达 ,0 表示不到达 
					mapsTemp.put("reach", mapTemp.get("reach"));
				
				}
				
				retMap.put("code", "200");
				retMap.put("msg", "接口调用成功");
				retMap.put("maps", maps);
				return retMap;
			} catch (Exception e) {
				retMap.put("code", "500");
				retMap.put("msg", "失败");
				retMap.put("error", e.getMessage());
			}
			
			return retMap;
		}
}
