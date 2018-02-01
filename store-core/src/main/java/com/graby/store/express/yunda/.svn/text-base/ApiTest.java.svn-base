package com.graby.store.express.yunda;

import java.util.List;
import java.util.Map;

import com.graby.store.express.yunda.api.DataSecurity;
import com.graby.store.express.yunda.api.HttpClient;


public class ApiTest {
	
	public final static String xmldata = 
			"<orders>"
	+"<order>"
	+"<id>2310000088544</id>"
	+"<receiver_address>山西省太原市小店区坞城路100号</receiver_address>"
	+"</order>"
	+"<order>"
	+"<id>2310000088545</id>"
	+"<receiver_address>上海市青浦区赵巷镇镇中路52弄</receiver_address>"
	+"</order>"
	+"</orders>";
	
	//正式数据  ：4111063456
	private static String partnerid = "7864641052";
	
	private static String password = "KkPHQixh7wE2MeyRrAFv8qf4aScmJ3";
	
	private static String version = "1.0";
	
	public void testYunDaAddress() {
		try {
			String data = DataSecurity.security(partnerid, password, xmldata);
			data += "&version=" + version + "&request=data";
			String result = HttpClient.post("http://orderdev.yundasys.com:10110/cus_order/order_interface/interface_new_package.php", data);
			result =  "<xinyu>"+result+"</xinyu>";
			System.out.println(result);
			Map<String, Object> map = com.graby.store.util.qm.XmlUtil.Dom2Map(result);
			System.out.println(map);
			Map<String, Object> map1  =(Map<String, Object>) map.get("responses");
			List<Map<String, Object>>  reqMapList  = (List<Map<String, Object>>) map1.get("response");
			System.out.println(reqMapList);
			Map<String, Object> reqMap = (Map<String, Object>) reqMapList.get(0);
			System.out.println(reqMap.get("msg"));
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}
}
