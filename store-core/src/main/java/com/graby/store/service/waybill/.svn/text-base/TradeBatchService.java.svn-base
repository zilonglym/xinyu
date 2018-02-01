package com.graby.store.service.waybill;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.graby.store.dao.mybatis.CentroDao;
import com.graby.store.dao.mybatis.ShipOrderDao;
import com.graby.store.dao.mybatis.SystemItemDao;
import com.graby.store.dao.mybatis.TradeBatchDao;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.Centro;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.TradeBatch;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.service.wms.ShipOrderService;

@Component
public class TradeBatchService {
	
	@Autowired
	private TradeBatchDao  tradeBatchDao;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private UserDao  userDao  ;
	
	
	@Autowired
	private CentroDao  centroDao  ;
	
	
	
	@Autowired
	private SystemItemDao systemDao;
	
	public List<TradeBatch> getTradeBatch(Map<String,Object> params){
		
		return tradeBatchDao.getTradeBatch(params);
	}
	
	public void updateComplete(){
		
		List<Map<String, Object>> completeIds = tradeBatchDao.getCompleteIds();
		if(completeIds!=null  && completeIds.size()>0){
			Map<String,Object>  params =  new HashMap<String, Object>();
			List<Object> list =new   ArrayList<Object>();
			for(int i = 0 ,size  = completeIds.size();i<size;i++){
				list.add(completeIds.get(i).get("trade_batch_id"));
				
			}
			params.put("list", list);
			tradeBatchDao.updateComplete(params);
		}
		
	}
	
	
	private List<ShipOrder> getBatchPageList(List<ShipOrder> list,int page , int size){
		if(list.size()<=size){
			return list;
		}
		
		List<ShipOrder>  shipList  = new ArrayList<ShipOrder>();
		int startSize = page*size  ;
		int endSize  =  size+(page*size);
		if(list.size()-1 < endSize) {
			endSize =  list.size()-1;
		}
		shipList  = list.subList(startSize,endSize);
		return shipList;
	}
	
	public Map<String,Object> createTradeTatch(Map<String,Object> params){
		
		List<SystemItem> systemItemList = systemDao.findSystemItemByType(StoreSystemItemEnums.BATCHQUANTITY.getKey());
		int systemItemCount = 100 ;
		if(systemItemList!=null  &&  systemItemList.size()>0){
			SystemItem systemItem = systemItemList.get(0);
			systemItemCount   =   Integer.valueOf(systemItem.getValue());
		}
		String  cuid  =""+params.get("cuid");
		Centro centro = centroDao.findCentro(Long.valueOf(cuid));
		String  userId  =""+params.get("userId"); 
		User user = userDao.get(Long.valueOf(userId));
		
		String  cpCode  =""+params.get("cpCode");
		
		List<ShipOrder> orderList=this.shipOrderService.findTradeBatchSendOrderWaits(params);
		
		String msg  ="没有有效数据,请确认商家和快递信息";
		if(orderList != null    && orderList.size()>0){
			int  orderSize =  orderList.size();
			int pages  = ((orderSize-1)/systemItemCount)+1;
			int page = 0;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHm");
			String format2 = format.format(new Date());
			System.out.println("pages:"+pages);
			msg  =orderSize+"条订单,生产【"+pages+"】批次信息;";
			while (pages > page) {
				
				List<ShipOrder> batchPageList = getBatchPageList(orderList, page, systemItemCount);
				page++;
			
				String no = format2+""+user.getId()+""+page;
				msg=msg+",【"+no+"】";
				TradeBatch tradeBatch = new TradeBatch();

				tradeBatch.setCentroId(centro.getId());
				tradeBatch.setCentroName(centro.getName());

				tradeBatch.setUserId(user.getId());
				tradeBatch.setUserName(user.getShopName());

				tradeBatch.setDate(new Date());
				tradeBatch.setQuantity(systemItemCount);
				tradeBatch.setNum(0);
				tradeBatch.setStatus(TradeBatch.TradeBatchStatusEnum.PRINTING);
				tradeBatch.setNo(no);
				tradeBatch.setExpressCompany(cpCode);
				
				tradeBatchDao.save(tradeBatch);

				List<Long> tardeList = new ArrayList<Long>();
				Map<String, Object> dataParam = new HashMap<String, Object>();
				System.out.println("tradeBatchId::"+tradeBatch.getId());
				dataParam.put("tradeBatchId", tradeBatch.getId());
				dataParam.put("tradeBatch", no);
				for (int size = 0; size < batchPageList.size(); size++) {
					ShipOrder order = batchPageList.get(size);
					tardeList.add(order.getId());
				}
				System.out.println("tardeList::"+tardeList.size());
				dataParam.put("list", tardeList);
				shipOrderService.updateShipOrderTradeBatchId(dataParam);
			}
		}
		Map<String,Object>  retMap  = new HashMap<String, Object>();
		retMap.put("msg", msg);
		return retMap;
	}
	
	public List<TradeBatch> findTradeBatchByUserId(Long userId) {
		return tradeBatchDao.findTradeBatchByUserId(userId);
	}
	
	public TradeBatch findTradeBatchById(Long id) {
		return tradeBatchDao.findTradeBatchById(id);
	}
}
