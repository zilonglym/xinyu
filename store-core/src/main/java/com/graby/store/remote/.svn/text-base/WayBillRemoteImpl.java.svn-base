package com.graby.store.remote;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeBatch;
import com.graby.store.service.waybill.SfWayBillService;
import com.graby.store.service.waybill.TradeBatchService;
import com.graby.store.service.waybill.WayBillService;


@RemotingService(serviceInterface = WayBillRemote.class, serviceUrl = "/wayBill.call")
public class WayBillRemoteImpl implements WayBillRemote {
	
	@Autowired
	private   WayBillService  wayBillService;
	
	@Autowired
	private SfWayBillService  sfWayBillService;
	
	@Autowired
	private TradeBatchService  tradeBatchService;
	
	public List<TradeBatch>  getTradeBatch(Map<String,Object> params){
		return  tradeBatchService.getTradeBatch(params);
	}
	
	
	public Map<String, Object>  createTradeTatch(Map<String,Object> params){
		return  tradeBatchService.createTradeTatch(params);
	}
	@Override
	public Map<String, String>  billGet(Trade trade,String cpCode,String batchCode){
		return  wayBillService.billGet(trade,cpCode,batchCode);
	}
	
	@Override
	public Map<String, String>  billCancel(Trade trade){
		return  wayBillService.billCancel(trade);
	}
	
	@Override
	public Map<String, String>  billSfGet(Trade trade,String batchCode) throws ClientProtocolException, IOException{
		return  sfWayBillService.billSfGet(trade,batchCode);
	}
	
	@Override
	public Map<String, String>  billSfConfirm(Trade trade,String dealtype) throws ClientProtocolException, IOException{
		return  sfWayBillService.billSfConfirm(trade, dealtype);
	}
	
	@Override
	public Map<String, String>  billSfSearch(Trade trade) throws ClientProtocolException, IOException{
		return  sfWayBillService.billSfSearch(trade);
	}


	@Override
	public Map<String, Object> queryDetail(String code) {
		// TODO Auto-generated method stub
		return this.wayBillService.querydetail(code);
	}


	@Override
	public Map<String, Object> getSFBillNo(String[] ids,String batchCode) throws Exception{
		// TODO Auto-generated method stub
		return this.sfWayBillService.getSFBillNo(ids,batchCode);
	}


	@Override
	public Map<String,Object> getCainiaoBill(String ids, String cpCode, String url, String batchCode) {
		// TODO Auto-generated method stub
		return wayBillService.getCainiaoBill(ids, cpCode, url, batchCode);
	}


	@Override
	public String getCainiaoTemplates() throws Exception {
		// TODO Auto-generated method stub
		return this.wayBillService.getCainiaoTemplates();
	}
	
}
