package com.graby.store.admin.web.group;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.IRedisProxy;
import com.graby.store.admin.web.BaseController;
import com.graby.store.admin.web.RdsConstants;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.LocalRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.util.ObjectTranscoder;

/**
 * 各组日常工作控制类
 * 
 * @author yangmin 2017年6月21日
 *
 */
@Controller
@RequestMapping(value = "mobile")
public class MobileController extends BaseController {

	@Autowired
	private ShipOrderRemote shipOrderService;
	@Autowired
	private ItemRemote itemService;
	@Autowired
	private UserRemote userService;
	@Autowired
	private SystemItemRemote systemRemote;
	@Autowired
	private IRedisProxy redisProxy;
	@Autowired
	private LocalRemote localRemote;
	

	/**
	 * 组工作页面首页
	 * 
	 * @return
	 */
	@RequestMapping(value = "index")
	public String index(ModelMap model) {
		return "group/workReport";
	}
	/**
	 * 工作组页面数据获取
	 * 
	 * @return
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Map<String, Object> workListData() {
		Long s=new Date().getTime();
		String userGroup=this.getString("userGroup");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> qmMap=(Map<String, Object>) ObjectTranscoder.deserialize(this.redisProxy.get(RdsConstants.MOBILE_ORDER_ITEM.getBytes()));
		Map<String,Object> cainiaoMap=(Map<String, Object>) ObjectTranscoder.deserialize(this.redisProxy.get(RdsConstants.MOBILE_ORDER_ITEM_CAINIAO.getBytes()));
		List<Map<String,String>> qmList=null;
		List<Map<String,String>> cainiaoList=null;
		if(StringUtils.isEmpty(userGroup)){
			//userGroup为空。查询所有的
			Set<String> qmSet=qmMap.keySet();
			qmList=new ArrayList<Map<String,String>>();
			cainiaoList=new ArrayList<Map<String,String>>();
			for(Iterator<String> iterator=qmSet.iterator();iterator.hasNext();){
				String key=iterator.next();
				qmList.addAll((List<Map<String, String>>) qmMap.get(key));
			}
			Set<String> cainiaoSet=cainiaoMap.keySet();
			for(Iterator<String> iterator=cainiaoSet.iterator();iterator.hasNext();){
				String key=iterator.next();
				cainiaoList.addAll((List<Map<String, String>>) cainiaoMap.get(key));
			}
			
		}else{
			if(qmMap==null){
				qmList=new ArrayList<Map<String,String>>();
			}else{
				qmList=(List<Map<String, String>>)qmMap.get(userGroup);
			}
			if(cainiaoMap!=null){
				cainiaoList=(List<Map<String, String>>)cainiaoMap.get(userGroup);;
			}else{
				cainiaoList=new ArrayList<Map<String,String>>();
			}
		}
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();
		list.addAll(qmList);
		list.addAll(cainiaoList);
		logger.debug("处理时间:"+(new Date().getTime()-s)+"|||"+list.size());
		resultMap.put("total", list.size());
		resultMap.put("page", 1);
		resultMap.put("rows", list);
		return resultMap;
	}
	/**
	 * 查询商品
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="getItemBySearch")
	@ResponseBody
	public Map<String,Object> getItemBySerach() throws Exception{
		String searchText=this.getString("searchText");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("searchText", searchText);
		params.put("row", 10);
		String itemKey=RdsConstants.MOBILE_ITEM+"*"+searchText.toUpperCase()+"*";
		Set<String> itemKeys=this.redisProxy.keys(itemKey);
		List<Map<String,Object>> itemMapList=new ArrayList<Map<String,Object>>();
		int i=0;
		for(Iterator<String> iter=itemKeys.iterator();iter.hasNext();){
			String key=iter.next();
			Map<String,Object> map=(Map<String, Object>) ObjectTranscoder.deserialize(this.redisProxy.get(key.getBytes("utf-8")));
			itemMapList.add(map);
			if(i>=15){
				break;
			}
			i++;
		}
		resultMap.put("ret", "1");
		resultMap.put("list", itemMapList);
		return resultMap;
	}
	
	
	
	@RequestMapping(value="initMobileItem")
	@ResponseBody
	public Map<String,Object> initMobileItemInfo() throws Exception{	
		logger.error("移动端商品更新！");
		String userIds="8,13,14,19,21,22,24,25,26,27,30,31,32,33,34,35,36,37,38,39,41,44,46,51,55,56,59,67,71";
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String, Object>();
//		params.put("userIds", userIds);
//		List<Item> itemList=this.itemService.getItemListByParams(params);
		List<LocalItem> itemList=this.localRemote.getLocalItemList(null);
		for(int i=0;i<itemList.size();i++){
			LocalItem item=itemList.get(i);
			
			Map<String,String> itemMap=new HashMap<String, String>();
			itemMap.put("id", String.valueOf(item.getId()));
			itemMap.put("itemName",item.getName());
			itemMap.put("code", item.getSku());
			itemMap.put("barCode", item.getBarCode());
			itemMap.put("color", item.getSku());
			itemMap.put("userId", item.getShopId());
			itemMap.put("userName", item.getShopName());
			String itemKey=RdsConstants.MOBILE_ITEM+item.getId()+item.getBarCode()+item.getName()+(item.getSku()==null?"":item.getSku());
			this.redisProxy.set(itemKey.getBytes("utf-8"), ObjectTranscoder.serialize(itemMap));
			logger.error(i+""+itemMap);
		}
		logger.error("初始化完成！");
		return resultMap;
	}
	
	/**
	 * initOrderItemByGroup
	 * @return
	 */
	@RequestMapping(value="initOrderItemByGroup")
	@ResponseBody
	public Map<String,Object> initMobileOrderItemByGroup(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = sdf.format(new Date()) + " 00:00";
		String endDate = sdf.format(new Date()) + " 23:59";
		List<SystemItem> systemList=this.systemRemote.findSystemItemByType("GROUP");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		for(int i=0;systemList!=null && i<systemList.size();i++){
			SystemItem itemObj=systemList.get(i);
			params.put("userIds",itemObj.getValue());
			params.remove("status");
			params.remove("itemId");
			List<Map<String, Object>> list = this.shipOrderService.getShipOrderItemByWorkGroup(params);
			List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
			for (int j = 0; list != null && j < list.size(); j++) {
				Map<String, Object> map = new HashMap<String, Object>();
				Map<String, Object> obj = list.get(j);
				logger.error(i+"-"+j+"-"+obj);
				String itemId = String.valueOf(obj.get("itemId"));
				String quantity = String.valueOf(obj.get("quantity"));
				Item item = this.itemService.getItem(Long.valueOf(itemId));
				User user = this.userService.getUser(item.getUserid());
				map.put("itemName", item.getTitle());
				map.put("itemCode", item.getCode());
				map.put("quantity", quantity);
				map.put("shopName", user.getShopName());
				map.put("itemId", itemId);
				params.put("itemId", itemId);
				params.put("status", OrderStatusEnums.WMS_FINASH);
				List<Map<String, Object>> temp = this.shipOrderService.getShipOrderItemByWorkGroup(params);
				String finishQuantity = "0";
				if (temp.size() > 0) {
					Map m = temp.get(0);
					finishQuantity = String.valueOf(m.get("quantity"));
					map.put("finishQuantity", m.get("quantity"));
				} else {
					map.put("finishQuantity", temp.size());
				}
				
				if (quantity.equals(finishQuantity)) {
					logger.error(quantity+"||||"+finishQuantity);
					continue;
				}
				params.put("status", OrderStatusEnums.WMS_AUDIT);
				temp = this.shipOrderService.getShipOrderItemByWorkGroup(params);
				if (temp.size() > 0) {
					Map m = temp.get(0);
					map.put("auditQuantity", m.get("quantity"));
				} else {
					map.put("auditQuantity", 0);
				}
				params.put("status", OrderStatusEnums.WMS_PRINT);
				temp = this.shipOrderService.getShipOrderItemByWorkGroup(params);
				if (temp.size() > 0) {
					Map m = temp.get(0);
					map.put("printQuantity", m.get("quantity"));
				} else {
					map.put("printQuantity", temp.size());
				}

				resultList.add(map);
			}			
			resultMap.put(itemObj.getDescription(), resultList);			
		}
		String key=RdsConstants.MOBILE_ORDER_ITEM;
		this.redisProxy.set(key.getBytes(), ObjectTranscoder.serialize(resultMap));		
		resultMap.put("ret", 1);
		resultMap.put("msg", "ok");
		return resultMap;
	}
}
