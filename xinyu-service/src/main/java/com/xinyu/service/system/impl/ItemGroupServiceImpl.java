package com.xinyu.service.system.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.ItemGroupDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemGroup;
import com.xinyu.model.base.ItemGroupDetail;
import com.xinyu.model.base.User;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.WmsConsignOrderItem;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.ItemGroupService;

/**
 * 组合商品业务实现接口
 * */
@Service("itemGroupServiceImpl")
public class ItemGroupServiceImpl extends BaseServiceImpl implements ItemGroupService{

	@Autowired
	private ItemGroupDao itemGroupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ItemDao itemDao;
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows) {	
		return this.itemGroupDao.getItemGroupListByParams(params,page,rows);
	}


	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	@Override
	public int getToTal(Map<String, Object> params) {
		return this.itemGroupDao.getToTal(params);
	}

	/**
	 * 封装组合商品
	 * @param itemGroups
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<ItemGroup> itemGroups) {
		
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(ItemGroup itemGroup:itemGroups){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", itemGroup.getId());
			User user = this.userDao.getUserById(itemGroup.getUser().getId());
			map.put("userName", user.getSubscriberName());
			map.put("remark", itemGroup.getRemark());
			map.put("itemName", itemGroup.getName());
			map.put("barCode", itemGroup.getBarCode());
			map.put("priority", itemGroup.getPriority());
			map.put("laterDate", sf.format(itemGroup.getLaterDate()));
			map.put("state", itemGroup.getState());
			results.add(map);
		}
		return results;
	}

	/**
	 * 持久化组合商品
	 * @param map
	 * @return map
	 * */
	@Override
	public Map<String, Object> saveItemGroup(Map<String, Object> params) {
		
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
		String id = ""+data.get("id");
	
		ItemGroup itemGroup = new ItemGroup();
		if (id.isEmpty()) {
			itemGroup.generateId();
			List<ItemGroupDetail> details = this.createItemGroupDetail(dataList,itemGroup);
			itemGroup.setDetai(details);
		}else {
			itemGroup = this.itemGroupDao.getItemGroupById(id);
			this.itemGroupDao.deleteDetails(id);
			List<ItemGroupDetail> details = this.createItemGroupDetail(dataList,itemGroup);
			itemGroup.setDetai(details);
		}	
		
		itemGroup.setLaterDate(new Date());
//		itemGroup.setCu("7d6f6f97-a988-45a2-a3de-zc8888centro");
		
		String name = ""+data.get("itemName");
		itemGroup.setName(name);
		
		String remark = ""+data.get("remark");
		itemGroup.setRemark(remark);
		
		String state = ""+data.get("state");
		itemGroup.setState(state);
		
		String userId = ""+data.get("userId");
		User user = this.userDao.getUserById(userId);
		itemGroup.setUser(user);
		
		String barCode = ""+data.get("barCode");
		itemGroup.setBarCode(barCode);
		
		String priority = ""+data.get("priority");
		itemGroup.setPriority(priority);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			if (id.isEmpty()) {
				this.itemGroupDao.insertItemGroup(itemGroup);
				retMap.put("msg", "组合商品创建成功！");
			}else {
				this.itemGroupDao.updateItemGroup(itemGroup);
				retMap.put("msg", "组合商品更新成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "组合商品操作失败！"+e.getMessage());
		}	
		return retMap;
	}

	/**
	 * 生成组合商品明细
	 * @param dataList
	 * @param itemGroup
	 * @return list
	 * */
	private List<ItemGroupDetail> createItemGroupDetail(List<Map<String, Object>> dataList, ItemGroup itemGroup) {
		
		List<ItemGroupDetail> details = new ArrayList<ItemGroupDetail>();
		
		for(Map<String, Object> map:dataList){
			ItemGroupDetail itemGroupDetail = new ItemGroupDetail();		
			itemGroupDetail.generateId();
			itemGroupDetail.setItemGroup(itemGroup);		
			String num = ""+map.get("num");
			itemGroupDetail.setNum(Long.parseLong(num));		
			String itemId = ""+map.get("itemId");
			Item item = this.itemDao.getItem(itemId);
			itemGroupDetail.setItem(item);		
			details.add(itemGroupDetail);				
		}
		
		return details;
	}

	/**
	 * 更新组合商品启用状态
	 * @param map
	 * */
	@Override
	public void updateState(Map<String, Object> params) {
		this.itemGroupDao.updateState(params);
	}

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	@Override
	public ItemGroup getItemGroupById(String id) {
		return this.itemGroupDao.getItemGroupById(id);
	}

	/**
	 * 封装组合商品明细
	 * @param details
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildDetaisListData(List<ItemGroupDetail> details) {
		List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
		for(ItemGroupDetail detail:details){
			Map<String, Object> map = new HashMap<String, Object>();
			Item item = this.itemDao.getItem(detail.getItem().getId());
			map.put("itemId", item.getId());
			map.put("itemName", item.getName());
			map.put("color", item.getColor());
			map.put("barCode", item.getBarCode());
			map.put("num", detail.getNum());
			results.add(map);
		}
		return results;
	}

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	@Override
	public List<ItemGroupDetail> getDetailsByList(Map<String, Object> params) {
		return this.itemGroupDao.getDetailsByList(params);
	}
	
	/**
	 * 根据订单查组合商品
	 * @param shipOrder
	 * @return list
	 * */
	@Override
	public List<ItemGroup> getGroupByShip(ShipOrder  order){
		
		if(order==null || order.getOrderItemList()==null||order.getOrderItemList().size()==0){
			return new ArrayList<ItemGroup>();
		}
		
		User user = order.getUser();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("userId", user.getId());
		System.out.println("userId:"+user.getId());
		
		List<WmsConsignOrderItem> orderItemList = order.getOrderItemList();
		
		
//		StringBuffer   strBuf  = new StringBuffer("");
		//SQL 拼写    //暂时先放弃
		//(t1.fkitemId =  '086cc623-c43c-48ee-a0ec-bdbb242ac67d'  and  t1.fnum  <=1 )    
		//or (t1.fkitemId =  '086cc623-c43c-48ee-a0ec-bdbb242ac67d'  and  t1.fnum  <=1)
		List<Map<String,Object>>  list  = new ArrayList<Map<String,Object>>();
		
		for(WmsConsignOrderItem orderItem  :  orderItemList){
			
//			strBuf.append(" (t1.fkitemId =  '");
//			strBuf.append(orderItem.getItem().getId()).append("' ");
//			strBuf.append(" and t1.fnum  <=").append(orderItem.getItemQuantity());
//			strBuf.append(" ) or ");
			
			Map<String,Object>  map = new HashMap<String, Object>();
			map.put("itemId", orderItem.getItem().getId());
			map.put("num", orderItem.getItemQuantity());
			list.add(map);
		}
		
//		String  sqlStr = strBuf.substring(0, strBuf.length()-4);
		params.put("itemList", list);
//		params.put("sqlStr", sqlStr);
//		System.out.println("sqlStr:"+sqlStr);
		List<ItemGroup> itemGroupList = itemGroupDao.getItemGroupByOrder(params);
		if(itemGroupList!=null){
			params.clear();
			for(ItemGroup group  :  itemGroupList){
				params.put("itemGroupId", group.getId());
				group.setDetai(this.itemGroupDao.getDetailsByList(params));
			}
		}
		return  itemGroupList;
	}

}
