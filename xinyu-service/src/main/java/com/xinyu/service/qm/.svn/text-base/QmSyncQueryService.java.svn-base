package com.xinyu.service.qm;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.xinyu.model.qm.StoreConstants;
import com.xinyu.model.system.SystemItem;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.util.HttpClientUtils;





@Component
public class QmSyncQueryService{
	
	public static final Logger logger = Logger.getLogger(QmSyncQueryService.class);
	@Autowired
	private SystemItemService itemService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**
	 * 初始化奇门参数
	 * 把配置的参数放入数据库中，这样不用进行测试与正式环境的区分
	 * 
	 */
	private void initQm_params(){
		List<SystemItem> list=null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "qimen_appkey");
		list=this.itemService.findSystemItemByList(params);
		StoreConstants.qimen_appkey= list!=null?list.get(0).getValue():"";
		params.clear();
		params.put("type", "qm_secretKey");
		list=this.itemService.findSystemItemByList(params);
		StoreConstants.qimen_secretKey=list!=null?list.get(0).getValue():"";
		params.clear();
		params.put("type", "qm_url");
		list=this.itemService.findSystemItemByList(params);
		StoreConstants.qimen_url=list!=null?list.get(0).getValue():"";
		
	}
	
	/**
	 * 获取请求奇门URL
	 * @param method
	 * @return
	 */
	private String getQmUrl(String method,String customerId){
		StringBuilder urlStr=new StringBuilder();
		urlStr.append(StoreConstants.qimen_url)
			.append(method)
			.append("&timestamp=")
			.append(URLEncoder.encode(this.sdf.format(new Date())))
			.append("&format=xml&app_key=")
			.append(StoreConstants.qimen_appkey);
		if(customerId!=null && customerId.equals("zc16473350913")){
			urlStr.append("&v=2.3");
		}else{
			urlStr.append("&v=2.0");
		}
			urlStr.append("&sign_method=md5&customerId=")
			.append(customerId);
		
		return urlStr.toString();
	}
	/**
	 * 提交奇门的发送请求
	 * @param xmlStr XML格式的参数体
	 * @param method 调用方法
	 * @return
	 */
	public String submitQm(String xmlStr,String method,String customerId){
		initQm_params();
		String url=this.getQmUrl(method,customerId);//组装URL
		String md5 = sign(url, xmlStr, StoreConstants.qimen_secretKey);//计算答名
		url+="&sign="+md5;//组装签名
		String resultStr="";
		try{
			logger.debug("submitURL:"+url);
			resultStr=HttpClientUtils.httpPost(url, xmlStr);
			System.err.println("奇门返回:"+resultStr);
		}catch(Exception e){
			System.err.println(e.getMessage());
			e.printStackTrace();
			resultStr="";
		}
		/**
		 * 这里对结果进行判断处理，返回想要的数据或map
		 */
		return resultStr;
	}
	
	/***
	 * 签名
	 * @param url
	 * @param body
	 * @param secretKey
	 * @return
	 */
	public String sign(String url, String body, String secretKey) {
		Map<String, String> params = getParamsFromUrl(url);
        // 1. 第一步，确保参数已经排序

        String[] keys = params.keySet().toArray(new String[0]);

        Arrays.sort(keys);
        // 2. 第二步，把所有参数名和参数值拼接在一起(包含body体)

        String joinedParams = joinRequestParams(params, body, secretKey, keys);

        //your_secretKeyapp_keyyour_appkeycustomerIdyour_customerIdformatxmlmethodyour_methodsign_methodmd5timestamp2015-04-26 00:00:07vyour_versionyour_bodyyour_secretKey

        System.out.println(joinedParams);

        // 3. 第三步，使用加密算法进行加密（目前仅支持md5算法）

        String signMethod = params.get("sign_method");

        if (!"md5".equalsIgnoreCase(signMethod)) {
            //TODO
            return null;

        }
        byte[] abstractMesaage = digest(joinedParams);
        // 4. 把二进制转换成大写的十六进制

        String sign = byte2Hex(abstractMesaage);
        return sign;
	}
	
	
	private Map<String, String> getParamsFromUrl(String url) {

        Map<String, String> requestParams = new HashMap<String, String>();

        try {

            String fullUrl = URLDecoder.decode(url, "UTF-8");

            String[] urls = fullUrl.split("\\?");

            if (urls.length == 2) {

                String[] paramArray = urls[1].split("&");

                for (String param : paramArray) {

                    String[] params = param.split("=");

                    if (params.length == 2) {

                        requestParams.put(params[0], params[1]);

                    }

                }

            }

        } catch (UnsupportedEncodingException e) {

            // TODO

        }

        return requestParams;

    }

 

    private String byte2Hex(byte[] bytes) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

        int j = bytes.length;

        char str[] = new char[j * 2];

        int k = 0;

        for (byte byte0 : bytes) {

            str[k++] = hexDigits[byte0 >>> 4 & 0xf];

            str[k++] = hexDigits[byte0 & 0xf];

        }

        return new String(str);

    }

 

    private byte[] digest(String message)  {

        try {

            MessageDigest md5Instance = MessageDigest.getInstance("MD5");

            md5Instance.update(message.getBytes("UTF-8"));

            return md5Instance.digest();

        } catch (UnsupportedEncodingException e) {

            //TODO

            return null;

        } catch (NoSuchAlgorithmException e) {

            //TODO

            return null;

        }

    }

 

    private String joinRequestParams(Map<String, String> params, String body, String secretKey, String[] sortedKes) {

        StringBuilder sb = new StringBuilder(secretKey); // 前面加上secretKey

 

        for (String key : sortedKes) {

            if ("sign".equals(key)) {

                continue; // 签名时不计算sign本身

            } else {

                String value = params.get(key);

                if (isNotEmpty(key) && isNotEmpty(value)) {

                    sb.append(key).append(value);

                }

            }

        }

        sb.append(body); // 拼接body体

        sb.append(secretKey); // 最后加上secretKey

        return sb.toString();

    }

 

    private boolean isNotEmpty(String s) {

        return null != s && !"".equals(s);

    }
	/**
	 * 发货单缺货通知业务组装
	 */

	public String itemlackReport(String jsonStr) {
		if(jsonStr==null || jsonStr.length()==0){
			return "通知数据为空!";
		}
		return null;
	}
}
