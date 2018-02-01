package com.graby.store.remote;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;

import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeBatch;

public interface WayBillRemote {
	
	/**
	 * 单据批量
	 * @param params
	 * @return
	 */
	 Map<String, Object>  createTradeTatch(Map<String,Object> params);
	 
	 
	 List<TradeBatch>  getTradeBatch(Map<String,Object> params);
	
	
	
	 Map<String, String>  billGet(Trade trade,String cpCode,String batchCode);
	 
	 /**
	  * 取新的菜鸟单号
	  * @param trade
	  * @param cpCode
	  * @param url
	  * @param batchCode
	  * @return
	  */
	 Map<String,Object>  getCainiaoBill(String ids,String cpCode,String url,String batchCode);
	 
	 
	 String getCainiaoTemplates()  throws Exception ;
	 
	 Map<String, String>  billCancel(Trade trade);
	 
	 
	/**
	 * <pre>
	 * 顺丰电子面单下单
	 * service  OrderService
	 * &#64;throws IOException 
	 * &#64;throws ClientProtocolException
	 * </pre>
	 */
	 Map<String, String>  billSfGet(Trade trade,String batchCode) throws ClientProtocolException, IOException;
	 /**
	  * 批量的取订单号
	  * @param ids
	  * @return
	  * @throws Exception
	  */
	 Map<String,Object> getSFBillNo(String[] ids,String batchCode) throws Exception;
	/**
	 * <pre>
	 * 顺丰电子面单确认 or 取消
	 * service OrderConfirmService dealtype  1： 确认 2： 取消
	 * @throws IOException
	 * @throws ClientProtocolException
	 * </pre>
	 */
	 Map<String, String>  billSfConfirm(Trade trade,String dealtype) throws ClientProtocolException, IOException;
	 
	 
	 /**
	  * 面单查询
	  * @param trade
	  * @return
	  * @throws ClientProtocolException
	  * @throws IOException
	  */
	 Map<String, String>  billSfSearch(Trade trade)throws ClientProtocolException, IOException; 
	 /**
	  * 查看快递单号状态
	  * @param code
	  * @return
	  */
	 Map<String,Object> queryDetail(String code);

}
