package com.graby.store.portal.qm.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.jpa.EntryOrderDetailJpaDao;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemInventory;
import com.graby.store.entity.Process;
import com.graby.store.entity.ProcessItem;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.User;
import com.graby.store.portal.qm.enums.XmlEnum;
import com.graby.store.portal.qm.util.XmlUtil;
import com.graby.store.service.base.CentroService;
import com.graby.store.service.base.UserService;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ProcessService;
import com.graby.store.service.wms.ShipOrderCancelService;
import com.graby.store.service.wms.ShipOrderService;

/**
 * 奇门接口具体事务处理类
 * @author 杨敏
 *
 */
@Component
public class QmSyncServiceImpl implements QmSyncService {
	@Autowired
	private CentroService centroService;
	@Autowired
	private UserService userService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private ShipOrderService shipOrderService;
	@Autowired
	private EntryOrderDetailJpaDao entryOrderDetailJpaDao;
	@Autowired
	private ShipOrderCancelService cancelService;
	@Autowired
	private ProcessService processService;
	/**
	 * 商品商步
	 */
	@Override
	@Transactional
	public String itemSync(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> map= XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> xmlMap=map;
		String actionType=(String) xmlMap.get("actionType");
		String msg="";
		if(actionType!=null && actionType.equals("add")){
			msg=this.addItem(xmlMap);
		}else if(actionType!=null && actionType.equals("update")){
			msg=this.updateItem(xmlMap);
		}else{
			//参数actionType异常
			msg="参数actionType异常";
		}
		return msg;
	}
	
	/**
	 * 组合商品
	 */
	@Override
	public String combineitem(String xmlStr) throws Exception {
		// TODO Auto-generated method stub
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		Item item=new Item();
		String code=(String) map.get("itemCode");
		item.setCode(code);
		item.setTitle("");
		item.setShortName("");
		item.setBarCode("");
		item.setPackageMaterial("");
		item.setType("");
		//保存新商品，减去原商品库存数量
		Map<String,Object> items=(Map<String, Object>) map.get("items");
		List<Map<String,Object>> itemList=(List<Map<String, Object>>) items.get("item");
		for(int i=0;itemList!=null && i<itemList.size();i++){
			//迭代减少相应的库存
			Map<String,Object> obj=itemList.get(0);
			String itemCode=(String) obj.get("itemCode");
			int quantity=(Integer) obj.get("quantity");
			//找到商品的库存信息进行修改，并写入相应的流水信息
		}
		return null;
	}

	/**
	 * 入库单创建
	 */
	@Override
	public String entryorder(String xmlStr) throws Exception{
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		
		//1.保存入库单据信息
		ShipOrder order=new ShipOrder();
		order.setType(ShipOrder.TYPE_ENTRY);
		buildShipOrder(order, map);
		//持久化相关信息
		this.shipOrderService.saveEntryOrder(order);
		for(int i=0;order.getDetails()!=null && i<order.getDetails().size();i++){
			this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
		}
		
		return null;
	}
	/**
	 * 退货入库单创建
	 */
	@Override
	public String returnorder(String xmlStr) throws Exception{
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		ShipOrder order=new ShipOrder();
		order.setType(ShipOrder.TYPE_ENTRY_RETURN);
		buildShipOrderReturn(order, map);
		//持久化相关信息
		this.shipOrderService.saveEntryOrder(order);
		for(int i=0;order.getDetails()!=null && i<order.getDetails().size();i++){
			this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
		}
		return null;
	}
	/**
	 * 出库单创建
	 */
	public  String stockout(String xmlStr) throws Exception{
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		ShipOrder order=new ShipOrder();
		order.setType(ShipOrder.TYPE_SEND);
		buildStockoutShipOrder(order, map);
		//持久化相关信息
		this.shipOrderService.saveEntryOrder(order);
		for(int i=0;order.getDetails()!=null && i<order.getDetails().size();i++){
			this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
		}
		return null;
	}
	
	/**
	 * 发货单创建
	 */
	public String deliveryorder(String xmlStr)throws Exception{
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		ShipOrder order=new ShipOrder();
		order.setType(ShipOrder.TYPE_DELIVER);
		buildDeliveryOrderShipOrder(order, map);
		//持久化相关信息
		this.shipOrderService.saveEntryOrder(order);
		for(int i=0;order.getDetails()!=null && i<order.getDetails().size();i++){
			this.entryOrderDetailJpaDao.save(order.getDetails().get(i));
		}
		return null;
	}
	
	/**
	 * 发货单查询
	 */
	@Override
	public String deliveryQuery(String xmlStr) throws Exception{
		Map<String,Object> xmlMap=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> map=(Map<String, Object>)xmlMap.get("request");
		String orderCode=(String) map.get("orderCode");
		String orderId=(String) map.get("orderId");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("orderId", orderId);
		params.put("type", ShipOrder.TYPE_DELIVER);
		//查询发货单
		List<ShipOrder> list=this.shipOrderService.queryShipOrderCodeIdAndType(params);
		//构建返回列表
		
		
		return null;
	}
	/**
	 * 单据取消接口
	 */
	@Override
	public String orderCancel(String xmlStr) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map=XmlUtil.Dom2Map(xmlStr);
		String warehouseCode=(String) map.get("warehouseCode");
		String ownerCode=(String) map.get("ownerCode");
		String orderCode=(String) map.get("orderCode");
		String orderId=(String) map.get("orderId");
		String orderType=(String) map.get("orderType");//单据类型，见枚举OrderTypeEnums
		String cancelReason=(String) map.get("cancelReason");//取消原因
		//先保存一个单据取消信息
		ShipOrderCancel cancel=new ShipOrderCancel();
		cancel.setOrdercode(orderCode);
		cancel.setOrderid(orderId);
		cancel.setOrdertype(orderType);
		cancel.setCancelreason(cancelReason);
		cancel.setCreatetime(sdf.format(new Date()));
		this.cancelService.save(cancel);
		//处理相应单据，进行逻辑删除
		//这里只处理入库单，发货单，退货单，出库单，仓内加工单五种
		//直接根据订单号查询数据
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("orderId", orderId);
		List<ShipOrder> list=this.shipOrderService.queryShipOrderCodeIdAndType(null);
		if(list!=null && list.size()>0){
			ShipOrder order=list.get(0);
			this.shipOrderService.updateShipOrder(order);
			return null;
		}else{
			return "没有找到此单据";
		}
	}
	
	/**
	 * 商品库存查询
	 */
	@Override
	public String inventoryQuery(String xmlStr) throws Exception{
		Map<String,Object> map=XmlUtil.Dom2Map(xmlStr);
		Map<String,Object> criterMap=(Map<String, Object>) map.get("criteriaList");
		List<Map<String,Object>> list=(List<Map<String, Object>>) criterMap.get("criteria");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		for(int i=0;list!=null && i<list.size();i++){
			Map<String,Object> obj=list.get(i);
			String warehouseCode=(String) obj.get("warehouseCode");
			String itemCode=(String) obj.get("itemCode");
			String itemId=(String) obj.get("itemId");
			String inventoryType=(String) obj.get("inventoryType");
			Map<String,Object> item=new HashMap<String, Object>();
			item.put("warehouseCode", warehouseCode);
			item.put("itemCode", itemCode);
			item.put("itemId", itemId);
			item.put("inventoryType", inventoryType);
			item.put("quantity", 0);
			item.put("lockQuantity", 0);
			mapList.add(item);
		}
		Map<String,Object> itemMap=new HashMap<String, Object>();
		itemMap.put("item", mapList);
		resultMap.put("items", itemMap);
		String result=XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		
		System.err.println(result);
		return null;
	}
	/**
	 * 仓内加工单
	 */
	@Override
	public String storeprocessCreate(String xmlStr) throws Exception{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map=XmlUtil.Dom2Map(xmlStr);
		Process process=new Process();
		process.setCode((String) map.get("processOrderCode"));
		process.setType((String) map.get("orderType"));
		process.setCreatetime((String) map.get("orderCreateTime"));
		process.setPlantime((String) map.get("planTime"));
		process.setServicetype((String) map.get("serviceType"));
		System.err.println(map.get("serviceType"));
		String planQty=(String) map.get("planQty");
		process.setPlanqty(Integer.valueOf(planQty));
		process.setRemark((String) map.get("remark"));
		this.processService.saveProcess(process);
		List<ProcessItem> itemList=new ArrayList<ProcessItem>();
		Map<String,Object> obj=(Map<String, Object>) map.get("materialitems");
		List<Map<String,Object>> list=(List<Map<String, Object>>) obj.get("item");
		for(int i=0;list!=null && i<list.size();i++){
			Map<String,Object> itemMap=list.get(i);
			ProcessItem item=new ProcessItem();
			item.setItemcode((String) itemMap.get("itemCode"));
			item.setItemid((String) itemMap.get("itemId"));
			item.setInventorytype((String) itemMap.get("inventoryType"));
			String quantity=(String) itemMap.get("quantity");
			item.setQuantity(Integer.valueOf(quantity));
			String productDate=(String) itemMap.get("productDate");
			if(productDate!=null && productDate.length()>0){
				item.setProductdate(sdf.parse(productDate));
			}
			String expireDate=(String) itemMap.get("expireDate");
			if(expireDate!=null && expireDate.length()>0){
				item.setExpiredate(sdf.parse(expireDate));
			}
			item.setProducecode((String) itemMap.get("produceCode"));
			item.setRemark((String) itemMap.get("remark"));
			item.setType("Mateiali");
			item.setParentid(process.getId());
			itemList.add(item);
		}
		Map<String,Object> product=(Map<String, Object>) map.get("productitems");
		List<Map<String,Object>> procuctList=(List<Map<String, Object>>) product.get("item");
		for(int i=0;list!=null && i<list.size();i++){
			Map<String,Object> itemMap=list.get(i);
			ProcessItem item=new ProcessItem();
			item.setItemcode((String) itemMap.get("itemCode"));
			item.setItemid((String) itemMap.get("itemId"));
			item.setInventorytype((String) itemMap.get("inventoryType"));
			String quantity=(String) itemMap.get("quantity");
			item.setQuantity(Integer.valueOf(quantity));
			String productDate=(String) itemMap.get("productDate");
			if(productDate!=null && productDate.length()>0){
				item.setProductdate(sdf.parse(productDate));
			}
			String expireDate=(String) itemMap.get("expireDate");
			if(expireDate!=null && expireDate.length()>0){
				item.setExpiredate(sdf.parse(expireDate));
			}
			item.setProducecode((String) itemMap.get("produceCode"));
			item.setRemark((String) itemMap.get("remark"));
			item.setType("Product");
			item.setParentid(process.getId());
			itemList.add(item);
		}
		this.processService.batchSaveProcessItem(itemList);
		return null;
	}
	/**
	 * 新增商品
	 * @param xmlStr
	 * @return
	 * @throws ParseException 
	 */
	private String addItem(Map<String,Object> xmlMap) throws ParseException{
		Map<String,Object> itemMap=(Map<String, Object>) xmlMap.get("item");
		Item item=new Item();
		buildItem(item, itemMap);
		ItemInventory tory=new ItemInventory();
		String warehouseCode=(String) xmlMap.get("warehouseCode");//仓库编码
		Centro centro=this.centroService.findCentroByCode(warehouseCode);
		tory.setCentro(centro);
		String ownerCode=(String) xmlMap.get("ownerCode");
		//User user=this.userService.getUser(Long.valueOf(ownerCode));
		User user=new User();
		user.setId(1l);
		tory.setUser(user);
		this.itemService.saveItem(item);
		//ItemInventory 方法暂时不做处理
		return null;
	}
	/**
	 * 修改商品
	 * @param xmlStr
	 * @return
	 * @throws ParseException 
	 */
	private String updateItem(Map<String,Object> xmlMap) throws ParseException{
		Map<String,Object> itemMap=(Map<String, Object>) xmlMap.get("item");
		String itemId=(String) itemMap.get("itemId");
		String itemCode=(String) itemMap.get("itemCode");
		//根据itemId找到系统中的item
		Item item=this.itemService.getItemByCode(itemCode);
		this.buildItem(item, itemMap);
		//因为这里只做系统的商品同步，因此是否需要存储仓库信息这里暂时不做处理
		return null;
	}
	/**
	 * 构建 Item对象
	 * @param itemMap
	 * @return
	 * @throws ParseException 
	 */
	private void buildItem(Item item,Map<String,Object> itemMap) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String itemCode=(String) itemMap.get("itemCode");
		item.setCode(itemCode);
		item.setItemId((String) itemMap.get("itemId"));
		item.setTitle((String) itemMap.get("itemName"));
		
		item.setShortName((String)itemMap.get("shortName"));
		item.setBarCode((String)itemMap.get("barCode"));
		item.setSku((String) itemMap.get("skuProperty"));
		item.setStockUnit((String) itemMap.get("stockUnit"));
		item.setColor((String) itemMap.get("color"));
		item.setTitle((String) itemMap.get("title"));
		item.setCategoryId((String) itemMap.get("categoryId"));
		item.setCategoryName((String) itemMap.get("categoryName"));
		item.setSafetyStock((String) itemMap.get("safetyStock"));
		item.setItemType((String) itemMap.get("itemType"));
		item.setBrandCode((String) itemMap.get("brandCode"));
		item.setBrandName((String) itemMap.get("brandName"));
		item.setDescription((String) itemMap.get("remark"));
		String updateTime=(String) itemMap.get("updateTime");
		item.setUpdateTime(sdf.parse(updateTime));
		
		item.setPackageMaterial((String)itemMap.get("packageMaterial"));
		item.setType((String)itemMap.get("itemType"));
		String createTime=(String) itemMap.get("createTime");
		if(createTime!=null){
			try {
				item.setCreateTime(sdf.parse(createTime));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			};
		}
		item.setRemark((String)itemMap.get("remark"));
	}

	/**
	 * 构建入库单及相关信息
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	private String buildShipOrder(ShipOrder order,Map<String,Object> map) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		order.setOrderno((String) map.get("entryOrderCode"));//入库单号
		
		String warehouseCode=(String) map.get("warehouseCode");//仓库信息
		Centro centro=this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		String orderCreateTime=(String) map.get("orderCreateTime");
		if(orderCreateTime!=null && orderCreateTime.length()>0){
			order.setCreateDate(sdf.parse(orderCreateTime));
		}
		order.setOrderType((String) map.get("orderType"));
		String logisticsCode=(String) map.get("logisticsCode");
		if(!logisticsCode.isEmpty()){
			order.setLogisticsCode(logisticsCode);
		}
		String logisticsName=(String) map.get("logisticsName");
		if(!logisticsName.isEmpty()){
			order.setLogisticsName(logisticsName);
		}
		String expressCode=(String) map.get("expressCode");
		if(!expressCode.isEmpty()){
			order.setExpressCode(expressCode);
		}
		String operatorCode=(String) map.get("operatorCode");
		if(!operatorCode.isEmpty()){
			order.setOperatorCode(operatorCode);
		}
		String operatorName=(String) map.get("operatorName");
		if(!operatorName.isEmpty()){
			order.setOperatorName(operatorName);
		}
		String operateTime=(String) map.get("operateTime");
		if(!operateTime.isEmpty()){
			order.setOperateTime(operateTime);
		}
		//发件人信息
		Map<String,Object>  info=(Map<String, Object>) map.get("senderInfo");
		String company=(String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		//收货人信息
		Map<String,Object> receive=(Map<String, Object>) map.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		//入库详情处理
		Map<String,Object> lineMap=(Map<String, Object>) map.get("orderLines");
		List<Map<String,Object>> itemList=(List<Map<String, Object>>) lineMap.get("orderLine");
		for(int i=0;itemList!=null && i<itemList.size();i++){
			Map<String,Object> item=itemList.get(i);
			ShipOrderDetail detail=new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			String itemCode=(String) item.get("itemCode");
			Item itemObj=this.itemService.getItemByCode(itemCode);
			detail.setItem(itemObj);
			int planQty=(Integer) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setSkuPropertiesName((String) item.get("skuProperty"));
			detail.setInventoryType((String) item.get("inventoryType"));
			order.getDetails().add(detail);
		}
		
		return null;
		
	}
	/**
	 * 退货入库单创建
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException
	 */
	private String buildShipOrderReturn(ShipOrder order,Map<String,Object> map) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		order.setOrderno((String) map.get("returnOrderCode"));//入库单号
		
		String warehouseCode=(String) map.get("warehouseCode");//仓库信息
		Centro centro=this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		String orderFlag=(String) map.get("orderFlag");
		order.setOrderFlag(orderFlag);
		order.setOrderType((String) map.get("orderType"));
		order.
		setPreDeliveryOrderCode((String) map.get("preDeliveryOrderCode"));
		order.setPreDeliveryOrderId((String) map.get("preDeliveryOrderId"));
		String logisticsCode=(String) map.get("logisticsCode");
		if(!logisticsCode.isEmpty()){
			order.setLogisticsCode(logisticsCode);
		}
		String logisticsName=(String) map.get("logisticsName");
		if(!logisticsName.isEmpty()){
			order.setLogisticsName(logisticsName);
		}
		String expressCode=(String) map.get("expressCode");
		if(!expressCode.isEmpty()){
			order.setExpressCode(expressCode);
		}
		order.setReturnReason((String) map.get("returnReason"));
		order.setBuyerNick((String) map.get("buyerNick"));
		order.setRemark((String) map.get("remark"));
		//发件人信息
		Map<String,Object>  info=(Map<String, Object>) map.get("senderInfo");
		String company=(String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		
		//入库详情处理
		Map<String,Object> lineMap=(Map<String, Object>) map.get("orderLines");
		List<Map<String,Object>> itemList=(List<Map<String, Object>>) lineMap.get("orderLine");
		for(int i=0;itemList!=null && i<itemList.size();i++){
			Map<String,Object> item=itemList.get(i);
			ShipOrderDetail detail=new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setTradeOrderNo((String) item.get("sourceOrderCode"));
			detail.setSubTradeOrderNo((String) item.get("subSourceOrderCode"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			String itemCode=(String) item.get("itemCode");
			Item itemObj=this.itemService.getItemByCode(itemCode);
			detail.setItem(itemObj);
			int planQty=(Integer) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setSkuPropertiesName((String) item.get("skuProperty"));
			detail.setInventoryType((String) item.get("inventoryType"));
			order.getDetails().add(detail);
		}
		
		return null;
	}
	/**
	 * 出库单信息构建
	 * @param order
	 * @param map
	 * @return
	 * @throws ParseException 
	 */
	private String buildStockoutShipOrder(ShipOrder order,Map<String,Object> maps) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map=(Map<String, Object>) maps.get("deliveryOrder");
		order.setOrderno((String) map.get("deliveryOrderCode"));//出库单号
		
		String warehouseCode=(String) map.get("warehouseCode");//仓库信息
		Centro centro=this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		order.setOrderType((String) map.get("orderType"));
		String createTime=(String) map.get("createTime");
		order.setCreateDate(sdf.parse(createTime));
		String scheduleDate=(String) map.get("scheduleDate");//要求出库时间
		order.setScheduleDate(scheduleDate);
		
		order.setRemark((String) map.get("remark"));
		String logisticsName=(String) map.get("logisticsName");
		if(!logisticsName.isEmpty()){
			order.setLogisticsName(logisticsName);
		}
		String transportMode=(String) map.get("transportMode");
		order.setTransportMode(transportMode);
		//提货人信息
		Map<String,Object> pickerMap=(Map<String, Object>) map.get("pickerInfo");
		order.setPickerCompany((String) pickerMap.get("company"));
		order.setPickerName((String) pickerMap.get("name"));
		order.setPickerTel((String) pickerMap.get("tel"));
		order.setPickerMobile((String) pickerMap.get("mobile"));
		order.setPickerId((String) pickerMap.get("id"));
		order.setPickerCarNo((String) pickerMap.get("carNo"));
		//发件人信息
		Map<String,Object>  info=(Map<String, Object>) map.get("senderInfo");
		String company=(String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		//收件人信息
		Map<String,Object> receive=(Map<String, Object>) map.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		//入库详情处理
		Map<String,Object> lineMap=(Map<String, Object>) map.get("orderLines");
		List<Map<String,Object>> itemList=(List<Map<String, Object>>) lineMap.get("orderLine");
		for(int i=0;itemList!=null && i<itemList.size();i++){
			Map<String,Object> item=itemList.get(i);
			ShipOrderDetail detail=new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			String itemCode=(String) item.get("itemCode");
			Item itemObj=this.itemService.getItemByCode(itemCode);
			detail.setItem(itemObj);
			int planQty=(Integer) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setInventoryType((String) item.get("inventoryType"));
			order.getDetails().add(detail);
		}
		
		return null;
	}
	
	/**
	 * 发货单构建
	 * @param order
	 * @param maps
	 * @return
	 * @throws ParseException
	 */
	private String buildDeliveryOrderShipOrder(ShipOrder order,Map<String,Object> maps) throws ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Map<String,Object> map=(Map<String, Object>) maps.get("deliveryOrder");
		order.setOrderno((String) map.get("deliveryOrderCode"));//出库单号
		order.setPreDeliveryOrderId((String) map.get("preDeliveryOrderId"));
		order.setPreDeliveryOrderCode((String) map.get("preDeliveryOrderCode"));
		
		String warehouseCode=(String) map.get("warehouseCode");//仓库信息
		Centro centro=this.centroService.findCentroByCode(warehouseCode);
		order.setCentroId(centro.getId());
		order.setOrderType((String) map.get("orderType"));
		order.setOrderFlag((String) map.get("orderFlag"));
		order.setPlaceOrderTime((String) map.get("placeOrderTime"));
		order.setPayTime((String) map.get("payTime"));
		order.setPayNo((String) map.get("payTime"));
		String createTime=(String) map.get("createTime");
		order.setCreateDate(sdf.parse(createTime));
		order.setOperateTime((String) map.get("operateTime"));
		order.setShopNick((String) map.get("shopNick"));
		order.setSellerNick((String) map.get("sellerNick"));
		order.setBuyerNick((String) map.get("buyerNick"));
		order.setLogisticsCode((String) map.get("logisticsCode"));
		order.setLogisticsName((String) map.get("logisticsName"));
		order.setLogisticsAreaCode((String) map.get("logisticsAreaCode"));
		order.setRemark((String) map.get("remark"));
		order.setUrgency((String) map.get("isUrgency"));
		order.setInvoiceFlag((String) map.get("invoiceFlag"));
		//发票信息处理
		Map<String,Object> invoice=(Map<String, Object>) map.get("invoices");
		List<Map<String,Object>> invoiceList=(List<Map<String, Object>>) invoice.get("invoice");
		//这里只处理一张发票信息
		if(invoiceList!=null && invoiceList.size()>0){
			Map<String,Object> obj=invoiceList.get(0);
			order.setInvoiceType((String) obj.get("type"));
			order.setInvoiceHeader((String) obj.get("header"));
			order.setInvoiceAmount((String) obj.get("amount"));
			order.setInvoiceAmount((String) obj.get("content"));
		}
		order.setInsuranceFlag((String) map.get("insuranceFlag"));
		Map<String,Object> insurance=(Map<String, Object>) map.get("insurance");
		if(insurance!=null){
			order.setInsuranceAmount((String) insurance.get("amount"));
			order.setInsuranceType((String) insurance.get("type"));
		}
		order.setBuyerMessage((String) map.get("buyerMessage"));
		order.setSellerMessage((String) map.get("sellerMessage"));
		
		//发件人信息
		Map<String,Object>  info=(Map<String, Object>) map.get("senderInfo");
		String company=(String) info.get("company");
		order.setSenderCompany(company);
		order.setOriginPersion((String) info.get("name"));
		order.setSenderZipCode((String) info.get("zipCode"));
		order.setOriginPhone((String) info.get("mobile"));
		order.setSenderProvince((String) info.get("province"));
		order.setSenderCity((String) info.get("city"));
		order.setSenderArea((String) info.get("area"));
		order.setSenderTown((String) info.get("town"));
		order.setSenderaddress((String) info.get("detailAddress"));
		//收件人信息
		Map<String,Object> receive=(Map<String, Object>) map.get("receiverInfo");
		order.setReceiveCopmany((String) receive.get("company"));
		order.setReceiverName((String) receive.get("name"));
		order.setReceiverZip((String) receive.get("zipCode"));
		order.setReceiverMobile((String) receive.get("mobile"));
		order.setReceiverState((String) receive.get("province"));
		order.setReceiverCity((String) receive.get("city"));
		order.setReceiverDistrict((String) receive.get("area"));
		order.setReceiverAddress((String) receive.get("detailAddress"));
		
		//发货详情处理
		Map<String,Object> lineMap=(Map<String, Object>) map.get("orderLines");
		List<Map<String,Object>> itemList=(List<Map<String, Object>>) lineMap.get("orderLine");
		for(int i=0;itemList!=null && i<itemList.size();i++){
			Map<String,Object> item=itemList.get(i);
			ShipOrderDetail detail=new ShipOrderDetail();
			detail.setOrderLineNo((String) item.get("orderLineNo"));
			detail.setTradeOrderNo((String) item.get("sourceOrderCode"));
			detail.setSubTradeOrderNo((String) item.get("subSourceOrderCode"));
			detail.setOwnerCode((String) item.get("ownerCode"));
			detail.setActualPrice((String) item.get("actualPrice"));
			String itemCode=(String) item.get("itemCode");
			Item itemObj=this.itemService.getItemByCode(itemCode);
			detail.setItem(itemObj);
			int planQty=(Integer) item.get("planQty");
			detail.setNum(Long.valueOf(planQty));
			detail.setInventoryType((String) item.get("inventoryType"));
			order.getDetails().add(detail);
		}
		
		return null;
	}
}

