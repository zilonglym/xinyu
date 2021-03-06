package com.graby.store.admin.local;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.IRedisProxy;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.local.LocalBatch;
import com.graby.store.entity.local.LocalBoxOut;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.entity.local.LocalPlate;
import com.graby.store.entity.local.LocalShop;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.LocalRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.util.StringUtils;

@Controller
@RequestMapping(value = "/local")
public class AdminLocalController extends BaseController {

	@Autowired
	private LocalRemote localRemote;

	@Autowired
	private ItemRemote itemService;
	@Autowired
	private UserRemote userservice;
	@Autowired
	private IRedisProxy redisProxy;

	@RequestMapping(value = "inIt")
	@ResponseBody
	public String inIt() {
		String[] boxsD = { "A", "B", "C", "D"};
		
		//12排
		int crow = 13;
		for(int c = 1 ; c<crow;c++)
		for (int i = 1; i < 6; i++) { //层
			for(int j= 0 , size  = boxsD.length;j<size;j++){ //位
				LocalBoxOut boxOut = new LocalBoxOut();
				boxOut.setBoxOut(boxsD[j]);
				String  source =  ""+c;
				boxOut.setRow(StringUtils.formatAddZero(3, source));
				boxOut.setFloor("" + i);
				boxOut.setState("0,0,0");
				localRemote.saveLocalBoxOut(boxOut);
			}
		}
		
		
		String[] boxsI = { "A", "B", "C", "D","E","F","G","H","I"};
		String[] boxsE = { "A", "B", "C", "D","E"};
		String[] boxsM = { "A", "B", "C", "D","E","F","G","H","I","J","K","L","M"};
		String[] boxsN = { "A", "B", "C", "D","E","F","G","H","I","J","K","L","M","N"};
		String[] boxsO = { "A", "B", "C", "D","E","F","G","H","I","J","K","L","M","N","O"};
		String[] boxsJ = { "A", "B", "C", "D","E","F","G","H","I","J"};
		String[] boxsK = { "A", "B", "C", "D","E","F","G","H","I","J","K",};
		String[] boxsL = { "A", "B", "C", "D","E","F","G","H","I","J","K","L"};
		
//		五层货架21A-I
		crow = 22;
		for (int c = 13; c < crow; c++){
			for (int i = 1; i < 6; i++) { // 层
				for (int j = 0, size = boxsI.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsI[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
//		22A-E
		for (int i = 1; i < 6; i++) { // 层
			for (int j = 0, size = boxsE.length; j < size; j++) { // 位
				LocalBoxOut boxOut = new LocalBoxOut();
				boxOut.setBoxOut(boxsE[j]);
				String source = ""+22;
				boxOut.setRow(StringUtils.formatAddZero(3, source));
				boxOut.setFloor("" + i);
				boxOut.setState("0,0,0");
				localRemote.saveLocalBoxOut(boxOut);
			}
		}
		
		
//		五层货架35A-I						
		crow=  36;
		for (int c = 23; c < crow; c++){
			for (int i = 1; i < 6; i++) { // 层
				for (int j = 0, size = boxsI.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsI[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
//		五层货架36A-M						36A-M	卡位195	
//		五层货架37A-M						37A-M	卡位195	
//		五层货架38A-M						38A-M	卡位195	585
		
		crow=  39;
		for (int c = 36; c < crow; c++){
			for (int i = 1; i < 6; i++) { // 层
				for (int j = 0, size = boxsM.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsM[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
		
//		五层货架39A-N						39A-N	卡位210	
//		五层货架40A-N						40A-N	卡位210	
//		五层货架41A-N						41A-N	卡位210	
//		五层货架42A-N						42A-N	卡位210	840
		crow=  43;
		for (int c = 39; c < crow; c++){
			for (int i = 1; i < 6; i++) { // 层
				for (int j = 0, size = boxsN.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsN[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
//		五层货架43A-O						43A-O	卡位225	225
		for (int i = 1; i < 6; i++) { // 层
			for (int j = 0, size = boxsO.length; j < size; j++) { // 位
				LocalBoxOut boxOut = new LocalBoxOut();
				boxOut.setBoxOut(boxsO[j]);
				String source = ""+43;
				boxOut.setRow(StringUtils.formatAddZero(3, source));
				boxOut.setFloor("" + i);
				boxOut.setState("0,0,0");
				localRemote.saveLocalBoxOut(boxOut);
			}
		}
		
		
		
//		四层货架44A-J						44A-J	卡位116（A区少4卡位）	116
//		四层货架45A-J						45A-J	卡位110（过道少6卡位，A区少4卡位）	110
//		四层货架46A-J						46A-J	卡位110（过道少6卡位，A区少4卡位）	110
//		四层货架47A-J						47A-J	卡位110（过道少6卡位，A区少4卡位）	110
//		四层货架48A-J						48A-J	卡位110（过道少6卡位，A区少4卡位）	110
		
		crow=  49;
		for (int c = 44; c < crow; c++){
			for (int i = 1; i < 5; i++) { // 层
				for (int j = 0, size = boxsJ.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsJ[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
//		四层货架49A-I						49A-I	卡位98（过道少6卡位，A区少4卡位）	98
//		四层货架50A-I						50A-I	卡位102（过道少6卡位）	102
//		四层货架51A-I						51A-I	卡位108	108
//		四层货架52A-I						52A-I	卡位102（过道少6卡位）	102
		
		crow=  53;
		for (int c = 49; c < crow; c++){
			for (int i = 1; i < 5; i++) { // 层
				for (int j = 0, size = boxsI.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsI[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
//		四层货架53A-K						53A-K	卡位126（过道少6卡位）	126
		
		for (int i = 1; i < 5; i++) { // 层
			for (int j = 0, size = boxsK.length; j < size; j++) { // 位
				LocalBoxOut boxOut = new LocalBoxOut();
				boxOut.setBoxOut(boxsK[j]);
				String source = ""+53;
				boxOut.setRow(StringUtils.formatAddZero(3, source));
				boxOut.setFloor("" + i);
				boxOut.setState("0,0,0");
				localRemote.saveLocalBoxOut(boxOut);
			}
		}
		
//		四层货架54A-L						54A-L	卡位138（过道少6卡位）	
//		四层货架55A-L						55A-L	卡位138（过道少6卡位）	
//		四层货架56A-L						56A-L	卡位138（过道少6卡位）	
//		四层货架57A-L						57A-L	卡位138（过道少6卡位）	552
//		四层货架58A-L						58A-L	卡位144	144
		
		crow=  59;
		for (int c = 54; c < crow; c++){
			for (int i = 1; i < 5; i++) { // 层
				for (int j = 0, size = boxsL.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsL[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
//		四层货架59A-N						59A-N	卡位168	168
//		四层货架60A-N						60A-N	卡位168	168

		crow=  61;
		for (int c = 59; c < crow; c++){
			for (int i = 1; i < 5; i++) { // 层
				for (int j = 0, size = boxsN.length; j < size; j++) { // 位
					LocalBoxOut boxOut = new LocalBoxOut();
					boxOut.setBoxOut(boxsN[j]);
					String source = ""+c;
					boxOut.setRow(StringUtils.formatAddZero(3, source));
					boxOut.setFloor("" + i);
					boxOut.setState("0,0,0");
					localRemote.saveLocalBoxOut(boxOut);
				}
			}
		}
		
		return "";
	}

	/**
	 * 商品查询
	 * 
	 * @return
	 */
	@RequestMapping(value = "getItem/{q}")
	@ResponseBody
	public Map<String, Object> getItem(@PathVariable("q") String q) {

		// itemService.findItmeByBarCodeAndUserId(null);

		return null;
	}
	
	
	
	/**
	 * 查询商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "getLocalPlate/item/{itemId}")
	@ResponseBody
	public Map<String, Object> getLocalPlateByitemId(@PathVariable("itemId") int itemId) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			List<LocalPlate> list = localRemote.getLocalPlateByItemId(itemId);
			retMap.put("list", buildLocalPlate(list,true));
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}

	/**
	 * 查询整体库位
	 * 
	 * @return
	 */
	@RequestMapping(value = "getBoxOutList/{row}")
	@ResponseBody
	public Map<String, Object> getBoxOutList(@PathVariable("row") String row) {

		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, List<LocalBoxOut>> localBoxOutByRow = localRemote.getLocalBoxOutByRow(row);
			retMap.put(row, localBoxOutByRow);
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}

	/**
	 * 根据库位查询板位
	 * 
	 * @return
	 */
	@RequestMapping(value = "getLocalPlateList/{boxOutId}")
	@ResponseBody
	public Map<String, Object> getLocalPlateList(@PathVariable("boxOutId") int boxOutId) {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			List<LocalPlate> list = localRemote.getLocalPlateByBoxOutId(boxOutId);
			retMap.put("list", buildLocalPlate(list,false));
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}

	/**
	 * 根据条码查询商品
	 * 
	 * @param barCode
	 * @return
	 */
	@RequestMapping(value = "getLocalItemByBarCode/{barCode}")
	@ResponseBody
	public Map<String, Object> getLocalItemByBarCode(@PathVariable("barCode") String barCode) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
//			List<Item> itemList = this.itemService.findItmeByBarCodes(barCode);
			Map<String,Object> params =  new HashMap<String, Object>();
//			params.put("barCode", barCode);
			if(org.apache.commons.lang3.StringUtils.isNotBlank(barCode) && barCode.length()>9){
				params.put("barCode", barCode);
			}else{
				params.put("searchText", barCode);
			}
			List<LocalItem> localItemList = localRemote.getLocalItemList(params);
			for(int i=0;localItemList!=null && i<localItemList.size();i++){
				LocalItem item=localItemList.get(i);
				Map<String,Object> obj=new HashMap<String, Object>();
				obj.put("itemId", item.getId());
				obj.put("itemName", item.getName());
				obj.put("barCode", item.getBarCode());
				obj.put("userName", item.getShopName());
				obj.put("sku", item.getSku());
				mapList.add(obj);
			}
			resultMap.put("list", mapList);
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "500");
			resultMap.put("error", e.getMessage());
		}
		return resultMap;

	}

	private static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 构建卡位
	 * 
	 * @param list
	 * @return
	 * @throws Exception 
	 */
	private  List<Map<String, Object>> buildLocalPlate(List<LocalPlate> localPlatelist,boolean isBoxOut) throws Exception {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		for (LocalPlate localPlate : localPlatelist) {
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", localPlate.getId());
			obj.put("num", localPlate.getNum());
			obj.put("code", localPlate.getCode());
			obj.put("fastCode", localPlate.getFastCode());
			String date = dateFormater.format(localPlate.getLastUpdateDate());
			obj.put("date", date);
			obj.put("state", localPlate.getState()==null?"":localPlate.getState());
			String itemTemp = localPlate.getItem();
			if (itemTemp != null) {
//				String keys=RdsConstants.ITEM+itemTemp;
				LocalItem item = localRemote.getLocalItemById(Integer.valueOf(itemTemp));
//				Map<String,String> item=(Map<String, String>) ObjectTranscoder.deserialize(this.redisProxy.get(keys.getBytes("utf-8")));
//				Item item = itemService.getItem(itemTemp.getId());
//				User user=this.userservice.getUser(item.getUserid());
				obj.put("shopName",item.getShopName());
				obj.put("itemId", item.getId());			
				obj.put("itemCode", item.getBarCode());
				obj.put("itemName", item.getName());
				obj.put("sku", item.getSku());
			} else {
				obj.put("itemId", "");
				obj.put("itemCode", "");
				obj.put("itemName", "");
				obj.put("shopName", "");
				obj.put("sku", "");
			}
			logger.error("buildLocalPlate"+obj);
			if(isBoxOut){
				LocalBoxOut boxOut = localPlate.getBoxOut();
				List<LocalBoxOut> lists = localRemote.getLocalBoxOutById(""+boxOut.getId());
				if(lists!=null  &&  lists.size()>0){
					boxOut = lists.get(0);
					obj.put("row", boxOut.getRow());
					obj.put("floor", boxOut.getFloor());
					obj.put("boxOut", boxOut.getBoxOut());
					
				}
				obj.put("boxCode",localPlate.getCode());
			}
			list.add(obj);
		}
		return list;
	}

	/**
	 * 上架
	 * 
	 * @return
	 */
	@RequestMapping(value = "upLocalPlate/{localPlateId}/{itemId}/{num}")
	@ResponseBody
	public synchronized Map<String, Object> upLocalPlate(@PathVariable("localPlateId") int localPlateId,
			@PathVariable("itemId") String itemId, @PathVariable("num") int num) {
		String opertionId =  this.getString("opertionId");
		
		String batchId =  this.getString("batchId");
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("id", localPlateId);
		params.put("itemId", itemId);
		params.put("num", num);
		params.put("opertionId", opertionId);
		params.put("batchId", batchId);
		Map<String, Object> retMap = localRemote.upLocalPlate(params);

		return retMap;
	}

	/**
	 * 下架
	 * 
	 * @return
	 */
	@RequestMapping(value = "downLocalPlate/{localPlateId}")
	@ResponseBody
	public synchronized Map<String, Object> downLocalPlate(@PathVariable("localPlateId") int localPlateId) {
		String opertionId =  this.getString("opertionId");
		Map<String, Object> retMap = localRemote.downLocalPlate(localPlateId,opertionId);

		return retMap;
	}

	
	@RequestMapping(value = "findTop10")
	@ResponseBody
	public  Map<String, Object> findTop10() {
		
		Map<String, Object> retMap =new HashMap<String, Object>();
		try {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("opertionId", this.getString("opertionId"));
			retMap.put("code", "200");
			retMap.put("list", localRemote.findTop10LocalPlateWorkByUserId(params));
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
				 
		return retMap;
	}
	
	@RequestMapping(value = "getRows")
	@ResponseBody
	public Map<String, Object> getRows() {
		Map<String, Object> retMap = localRemote.getRows();
		return retMap;
	}
	
	/**
	 * save商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "save/item")
	@ResponseBody
	public synchronized Map<String, Object> saveLocalItem(Model model ) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			
			Map<String,Object> pp=this.getRequest().getParameterMap();
			System.err.println(pp);
			String name = this.getString("name");
			String shopName =  this.getString("shopName");
			String barCode  =this.getString("barCode");
			String sku = this.getString("sku");
			String itemType  =this.getString("itemType");
			String source  =this.getString("source");
			LocalItem  localItem = new LocalItem();
			localItem.setName(name);
			localItem.setBarCode(barCode);
			localItem.setSku(sku);
			localItem.setSource(source);
			localItem.setItemType(itemType);
			Map<String,Object>  params =  new HashMap<String, Object>(); 
			params.put("name", shopName);
			LocalShop localShop = localRemote.getLocalShop(params);
			localItem.setShopId(""+localShop.getId());
			localItem.setShopName(shopName);
			localRemote.saveLocalItem(localItem);
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "500");
			resultMap.put("error", e.getMessage());
		}
		return resultMap;
	}
	
	
	/**
	 * save商品
	 * 
	 * @return
	 */
	@RequestMapping(value = "update/item")
	@ResponseBody
	public synchronized Map<String, Object> updateLocalItem(Model model ) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			String name = this.getString("name");
			String shopName =  this.getString("shopName");
			String barCode  =this.getString("barCode");
			String sku = this.getString("sku");
			String itemType  =this.getString("itemType");
			String source  =this.getString("source");
			LocalItem  localItem = new LocalItem();
			
			String id = this.getString("itemId");
			localItem.setId(Integer.valueOf(id));
			localItem.setName(name);
			localItem.setBarCode(barCode);
			localItem.setSku(sku);
			localItem.setSource(source);
			localItem.setItemType(itemType);
			Map<String,Object>  params =  new HashMap<String, Object>(); 
			params.put("name", shopName);
			LocalShop localShop = localRemote.getLocalShop(params);
			localItem.setShopId(""+localShop.getId());
			localItem.setShopName(shopName);
			localRemote.updateLocalItem(localItem);
			resultMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("code", "500");
			resultMap.put("error", e.getMessage());
		}
		return resultMap;
	}
	
	
	
	
	
	@RequestMapping(value = "getShops")
	@ResponseBody
	public Map<String, Object> getShops() {
		
		Map<String, Object> retMap =  new HashMap<String, Object>();
		
		try {
			Map<String, Object> params  = new HashMap<String, Object>();
			List<LocalShop> localShopList = localRemote.getLocalShopList(params);
			
			retMap.put("list", localShopList);
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}
	
	
	/**
	 * 根据fastCode查询货位信息
	 * @param fastCode
	 * @return
	 */
	@RequestMapping(value = "getLocalPlateByFast/{fastCode}")
	@ResponseBody
	public Map<String, Object> getLocalPlateList(@PathVariable("fastCode") String fastCode) {
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			LocalPlate plate = this.localRemote.getLocalPlateByFastCode(fastCode);
		
			Map<String, Object> map = new  HashMap<String, Object>();
			String boxOutId = String.valueOf(plate.getBoxOut().getId());
			List<LocalBoxOut> boxOuts = this.localRemote.getLocalBoxOutById(boxOutId);
			LocalBoxOut boxOut = boxOuts.get(0);		
			map.clear();			
			//LocalItem信息
			String itemId = plate.getItem();
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(itemId)) {
				LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(itemId));
				map.put("itemId", item.getId());
				map.put("itemName", item.getName());
				map.put("itemSku", (item.getSku()==null?"":item.getSku()));
				map.put("barCode", item.getBarCode());
				map.put("shopName", item.getShopName());
			}
			
			//LocalBatch信息
			String batchId = plate.getbatchId();
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(batchId)) {
				LocalBatch batch = this.localRemote.findLocalBatchById(Integer.valueOf(batchId));
				map.put("batchId", batch.getId());
				map.put("batchCode", batch.getOrderCode());
				map.put("entryCode", batch.getEntryCode());		
			}
			
			map.put("plateId", plate.getId());
			map.put("boxOutId", boxOut.getId());
			map.put("row", boxOut.getRow());
			map.put("floor", boxOut.getFloor());
			map.put("boxOut", boxOut.getBoxOut());
			map.put("code", plate.getCode());
			map.put("num", plate.getNum());
			map.put("lastUpdate", sf.format(plate.getLastUpdateDate()));
			
			retMap.put("row", map);
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}
	
	/**
	 * 根据batchId更新对应单据状态信息
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value="updateBatch/{batchId}")
	@ResponseBody
	public Map<String, Object> updateBatchByBatchId(@PathVariable("batchId") String batchId){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			params.put("id", batchId);
			params.put("status", OrderStatusEnums.WMS_CONFIRM);
			
			this.localRemote.updateBatchByParams(params);
			
			retMap.put("code", "200");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}
	
	/**
	 * 根据batchCode查对应LocalPlate
	 * @param batchCode
	 * @return
	 */
	@RequestMapping(value="getLocalPlate/{batchCode}")
	@ResponseBody
	public Map<String, Object> getLocalPlateByBatchCode(@PathVariable("batchCode") String batchCode){
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		try {
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			//batchCode对应batch信息
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderCode", batchCode);
			List<LocalBatch> bathes = this.localRemote.findLocalBatchList(params);
			if(bathes==null  || bathes.size()==0){
				retMap.put("code", "404");
				retMap.put("msg", "无效板位信息");
				return retMap;
			}
			LocalBatch batch = bathes.get(0);
			map.put("batchId", batch.getId());
			map.put("batchCode", batch.getOrderCode());
			map.put("entryCode", batch.getEntryCode());		
			
			//单据对应商品信息
			int itemId = Integer.valueOf(batch.getItemId());
			LocalItem item = this.localRemote.getLocalItemById(itemId);
			if (item!=null) {
				map.put("itemId", itemId);
				map.put("itemName", item.getName());
				map.put("itemSku", (item.getSku()==null?"":item.getSku()));
				map.put("barCode", item.getBarCode());
				map.put("shopName", item.getShopName());
				map.put("quantity", batch.getNum());
			}
			
			//batchId对应plate信息
			String batchId = String.valueOf(batch.getId());
			LocalPlate plate = this.localRemote.findLocalPlateByBatchId(batchId);
			if (plate!=null) {
				
				//plate对应boxOut信息
				String boxOutId = String.valueOf(plate.getBoxOut().getId());
				List<LocalBoxOut> boxOuts = this.localRemote.getLocalBoxOutById(boxOutId);
				LocalBoxOut boxOut = boxOuts.get(0);
				map.put("boxOutId", boxOutId);
				map.put("row", boxOut.getRow());
				map.put("boxOut", boxOut.getBoxOut());
				map.put("floor", boxOut.getFloor());
			
				map.put("plateId", plate.getId());
				map.put("fastCode", plate.getFastCode());
				map.put("num", plate.getNum());
				map.put("code", plate.getCode());
				map.put("lastUpdate",sf.format(plate.getLastUpdateDate()));
			}
			
			retMap.put("row", map);
			retMap.put("code", "200");
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("error", e.getMessage());
		}
		return retMap;
	}
}
