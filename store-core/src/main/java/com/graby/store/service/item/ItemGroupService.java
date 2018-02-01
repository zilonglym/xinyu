package com.graby.store.service.item;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.dao.mybatis.ItemGroupDao;
import com.graby.store.dao.mybatis.ShipOrderGroupDao;
import com.graby.store.dao.mybatis.UserDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderGroup;
import com.graby.store.entity.User;

/**
 * 组合商品业务操作
 * */
@Component
@Transactional
public class ItemGroupService {

	private static Logger logger = LoggerFactory.getLogger(ItemGroupService.class);
	
	@Autowired
	private ItemGroupDao itemGroupDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private ShipOrderGroupDao shipOrderGroupDao;
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	

	/**
	 * 分页查询组合商品信息
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ItemGroup> getItemGroupListByParams(Map<String, Object> params, int page, int rows) {
		int start = (page-1)*rows;
		int offset = rows;
		params.put("start", start);
		params.put("offset",offset);
		List<ItemGroup> itemGroups = this.itemGroupDao.getItemGroupListByParams(params);
		return itemGroups;
	}

	/**
	 * 组合商品统计
	 * @param map
	 * @return int
	 * */
	public int getToTal(Map<String, Object> params) {
		return this.itemGroupDao.getToTal(params);
	}

	/**
	 * 根据ID查询组合商品信息
	 * @param id
	 * @return ItemGroup
	 * */
	public ItemGroup getItemGroupById(String id) {
		return this.itemGroupDao.getItemGroupById(id);
	}

	/**
	 * 参数查询组合商品明细
	 * @param map
	 * @return list
	 * */
	public List<ItemGroupDetail> getDetailsByList(Map<String, Object> params) {
		return this.itemGroupDao.getDetailsByList(params);
	}

	/**
	 * 持久化组合商品
	 * 新建需要根据user和name判断是否已存在
	 * @param map
	 * @return map
	 * */
	public Map<String, Object> saveItemGroup(Map<String, Object> params) {
		
		Map<String, Object> data = (Map<String, Object>) params.get("data");
		
		List<Map<String, Object>> dataList = (List<Map<String, Object>>) params.get("dataList");
		
//		System.err.println("data:"+data+" dataList"+dataList);
		
		String id = ""+data.get("id");
// 		System.err.println(id.isEmpty());
		ItemGroup itemGroup = new ItemGroup();
		if (StringUtils.isBlank(id)) {
			List<ItemGroupDetail> details = this.createItemGroupDetail(dataList,itemGroup);
//			System.err.println(details.size());
			itemGroup.setDetail(details);
		}else {
			itemGroup = this.itemGroupDao.getItemGroupById(id);
			this.itemGroupDao.deleteDetails(id);
			List<ItemGroupDetail> details = this.createItemGroupDetail(dataList,itemGroup);
			itemGroup.setDetail(details);
		}	
		
		itemGroup.setLaterDate(new Date());
		itemGroup.setCu("1");
		
		String name = ""+data.get("itemName");
		itemGroup.setName(name);
		
		String remark = ""+data.get("remark");
		itemGroup.setRemark(remark);
		
		String state = ""+data.get("state");
		itemGroup.setState(state);
		
		String barCode = ""+data.get("barCode");
		itemGroup.setBarCode(barCode);
		
		String level = ""+data.get("level");
		itemGroup.setPriority(Integer.parseInt(level));
		
		String userId = ""+data.get("userId");
		User user = this.userDao.get(Long.parseLong(userId));
		itemGroup.setUser(user);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", name);
			map.put("userId", userId);
			/**
			 * 根据name和user统计组合商品数目
			 * total大于等于1不作操作，返回已存在
			 * total小于1执行持久化操作
			 * */
			int total = this.itemGroupDao.getToTal(map);		
			if (id.isEmpty()) {
				if (total<1) {
					this.itemGroupDao.insertItemGroup(itemGroup);	
					ItemGroup group = this.itemGroupDao.getItemGroupByName(itemGroup.getName());
					for(ItemGroupDetail itemGroupDetail:itemGroup.getDetail()){
						itemGroupDetail.setItemGroup(group);
						this.itemGroupDao.insertItemGroupDetail(itemGroupDetail);
					}
					retMap.put("msg", "组合商品创建成功！");
				}else {
					retMap.put("msg", "该组合商品名称已使用！");
				}
			}else {
				this.itemGroupDao.updateItemGroup(itemGroup);
				for(ItemGroupDetail itemGroupDetail:itemGroup.getDetail()){
					itemGroupDetail.setItemGroup(itemGroup);
					this.itemGroupDao.insertItemGroupDetail(itemGroupDetail);
				}
				retMap.put("msg", "组合商品更新成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg", "组合商品操作失败！"+e.getMessage());
		}
		return retMap;	
	}

	/**
	 * 创建组合商品明细信息
	 * @param dataList
	 * @param itemGroup
	 * @return list
	 * */
	private List<ItemGroupDetail> createItemGroupDetail(List<Map<String, Object>> dataList, ItemGroup itemGroup) {
		
		List<ItemGroupDetail> details = new ArrayList<ItemGroupDetail>();
		
		for(Map<String, Object> map:dataList){
			ItemGroupDetail itemGroupDetail = new ItemGroupDetail();			
			String num = ""+map.get("num");
			itemGroupDetail.setNum(Long.parseLong(num));		
			String itemId = ""+map.get("itemId");
			Item item = this.itemDao.get(Long.parseLong(itemId));
			itemGroupDetail.setItem(item);	
			itemGroupDetail.setCu("1");
			details.add(itemGroupDetail);				
		}
		
		return details;
	}

	/**
	 * 更新组合商品是否启用
	 * @param map
	 * */
	public void updateState(Map<String, Object> params) {
		this.itemGroupDao.updateState(params);
	}
	
	
	/**
	 * 根据订单查组合商品
	 * @param shipOrder
	 * @return list
	 * */
	public List<ItemGroup> getGroupByShip(ShipOrder  order){
		
		if(order==null || order.getDetails()==null||order.getDetails().size()==0){
			return new ArrayList<ItemGroup>();
		}
		
		User user = order.getCreateUser();
		
		Map<String,Object> params = new HashMap<String, Object>();
		
		params.put("userId", user.getId());
		System.out.println("userId:"+user.getId());
		
		List<ShipOrderDetail> orderItemList = order.getDetails();
		
		
//		StringBuffer   strBuf  = new StringBuffer("");
		//SQL 拼写    //暂时先放弃
		//(t1.fkitemId =  '086cc623-c43c-48ee-a0ec-bdbb242ac67d'  and  t1.fnum  <=1 )    
		//or (t1.fkitemId =  '086cc623-c43c-48ee-a0ec-bdbb242ac67d'  and  t1.fnum  <=1)
		List<Map<String,Object>>  list  = new ArrayList<Map<String,Object>>();
		
		for(ShipOrderDetail orderItem  :  orderItemList){
			
//			strBuf.append(" (t1.fkitemId =  '");
//			strBuf.append(orderItem.getItem().getId()).append("' ");
//			strBuf.append(" and t1.fnum  <=").append(orderItem.getItemQuantity());
//			strBuf.append(" ) or ");
			
			Map<String,Object>  map = new HashMap<String, Object>();
			map.put("itemId", orderItem.getItem().getId());
			map.put("num", orderItem.getNum());
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
				group.setDetail(this.itemGroupDao.getDetailsByList(params));
			}
		}
		return  itemGroupList;
	}

	public void insertShipOrderGroupList(List<ShipOrderGroup> orderList) {
		// TODO Auto-generated method stub
		for(int i=0;orderList!=null && i<orderList.size();i++){
			this.shipOrderGroupDao.insertShipOrderGroup(orderList.get(i));
		}
	}

}
