package com.xinyu.service.system.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.ItemOperatorDao;
import com.xinyu.dao.base.SnSampleDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.inventory.InventoryDao;
import com.xinyu.dao.inventory.InventoryRecordDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.SnSample;
import com.xinyu.model.base.User;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.inventory.Inventory;
import com.xinyu.model.inventory.InventoryRecord;
import com.xinyu.model.inventory.enums.InventoryOrderTypeEnum;
import com.xinyu.model.inventory.enums.InventoryTypeEnum;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.ItemService;

/**
 * 商品处理业务接口实现
 * @author lenovo
 *
 */
@Service("itemServiceImpl")
public class ItemServiceImpl extends BaseServiceImpl implements ItemService {
	
	public static final Logger logger = Logger.getLogger(ItemServiceImpl.class);
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemOperatorDao operatorDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	private InventoryRecordDao recordDao;
	
	@Autowired
	private SnSampleDao sampleDao;
	
	/**
	 * 新增Item信息
	 * @param item
	 * */
	@Transactional
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		logger.debug("保存商品！");
		try{
			this.itemDao.saveItem(item);
			if (item.getSnSampleList() != null && item.getSnSampleList().size() > 0) {
				for (int i = 0; i < item.getSnSampleList().size(); i++) {
					SnSample sample = item.getSnSampleList().get(i);
					for (int j = 0; sample.getSampleRuleList() != null && j < sample.getSampleRuleList().size(); j++) {
						this.sampleDao.insertSnSampleRule(sample.getSampleRuleList().get(j));
					}
					this.sampleDao.insertSnSample(sample);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 批量新增Item信息
	 * @param list
	 * */
	public void saveItemList(List<Item> itemList) {
		// TODO Auto-generated method stub
		for (Item item : itemList) {
			this.saveItem(item);
		}
	}

	/**
	 * 根据ID查询Item信息
	 * @param id
	 * @return Item
	 * */
	public Item getItem(String id) {
		// TODO Auto-generated method stub
		return this.itemDao.getItem(id);
	}

	/**
	 * 更新商品信息
	 * @param item
	 * */
	@Transactional
	public void updateItem(Item item) {
		// TODO Auto-generated method stub
		this.itemDao.updateItem(item);
		
		if(item.getSnSampleList()!=null && item.getSnSampleList().size()>0){
			for(int i=0;i<item.getSnSampleList().size();i++){
				SnSample sample=item.getSnSampleList().get(i);
				for(int j=0;sample.getSampleRuleList()!=null && j<sample.getSampleRuleList().size();j++){
					this.sampleDao.updateSnSampleRule(sample.getSampleRuleList().get(j));
				}
				this.sampleDao.updateSnSample(sample);
			}
		}
	}

	/**
	 * 条件查询商品信息
	 * @param map
	 * @return list
	 * */
	public List<Item> getItemByList(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.itemDao.getItemByList(params);
	}

	/**
	 * 分页查询商品信息
	 * @param page
	 * @param rows
	 * @param params
	 * @return list
	 * */
	@Override
	public List<Item> getItemsByPage(int page, int rows, Map<String, Object> params) {
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		return this.itemDao.getItemByList(params);
	}

	/**
	 * 符合条件的商品数量
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		// TODO Auto-generated method stub
		int total = this.itemDao.getTotal(params);
		return total;
	}

	/**
	 * 商品自动生成条码
	 * @param item
	 * @return map
	 * */
	@Override
	public Map<String, Object> generateBarCode(Item item) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (item != null && item.getBarCode() != null && item.getBarCode().length()>0) {
			resultMap.put("ret", 0);
			resultMap.put("msg", "");
		} else {
			NumberFormat nf = NumberFormat.getInstance();
			// 设置是否使用分组
			nf.setGroupingUsed(false);
			// 设置最大整数位数
			nf.setMaximumIntegerDigits(4);
			// 设置最小整数位数
			nf.setMinimumIntegerDigits(3);
			
			User user = this.userDao.getUserById(item.getUser().getId());
			
			String barCode = "29";
			barCode = barCode + nf.format(Long.parseLong(item.getItemId().substring(0, 4)));
			String tbUserId = String.valueOf(user.getTbUserId()).substring(0, 3);
			barCode = barCode + nf.format(Long.parseLong(tbUserId));
			// 66|18|0000|
			//int seq = this.itemRemote.findPageUserItemsCount(item.getUserid());
			//seq++;
			Random rand=new Random();
			int seq=rand.nextInt(999);
			barCode = barCode + nf.format(seq);
			//调用EAN13校验码生成方法
			System.err.println("barCode:"+barCode);
			String newBarCode = this.EAN13Check(barCode);
			System.err.println("newBarCode:"+newBarCode);
			item.setBarCode(newBarCode);
			this.updateItem(item);
			resultMap.put("ret", 1);
		}
		return resultMap;
	}

	/**
	 * 
	 * @modify 2017-09-05
	 * @author fufangjue
	 * 
	 * EAN13根据前12位随机数字生成第13位校验码
	 * 重新跟前12位拼接成13位EAN13的条码
	 * @param string
	 * @return string
	 * */
	private String EAN13Check(String code) {
		
		int c1=0;int c2=0; 
		
		for(int i=0;i<12;i+=2){ 
			char c=code.charAt(i);//字符串code中第i个位置上的字符
			int n=c-'0'; 
			c1+=n;//累加奇数位的数字和
		} 
		
		for(int i=1;i<12;i+=2){ 
			char c=code.charAt(i);//字符串code中第i个位置上的字符
			int n=c-'0'; 
			c2+=n;//累加偶数位的数字和
		} 
		
		int cc=c1+c2*3;
		int check=cc%10; 
		check=(10-cc%10)%10; 
		code = code + check;
		
		return code;
	}

	/**
	 * 商品信息重组
	 * @param items
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildItemList(List<Item> items) {
		DecimalFormat df = new DecimalFormat("######0.00");
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < items.size(); i++) {
			Item item = items.get(i);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", item.getId());
			map.put("index", i);
			map.put("itemCode", item.getItemCode());
			map.put("itemName", item.getName());
			map.put("type", item.getType().getDescription());
			map.put("sku", (item.getColor()==null?"":(item.getColor()+";"))+(item.getSpecification()==null?"":item.getSpecification()));
			map.put("barCode", item.getBarCode());
			map.put("weight", item.getGrossWeight());
			map.put("packageWeight", item.getWmsGrossWeight());
 			User user = this.userDao.getUserById(item.getUser().getId());
 			if (user!=null) {
 				map.put("userName", user.getSubscriberName());
			}
			map.put("operator", item.getId());
			
			map.put("itemType", item.getItemType());
			
			resultList.add(map);
		}
		return resultList;
	}

	/**
	 * 商品入库处理
	 * @param itemId
	 * @param itemCount
	 * @return map
	 * */
	@Override
	public Map<String, Object> submitItemCount(String itemId, String itemCount) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Long count  =   Long.valueOf(itemCount.trim());
			Item item = this.getItem(itemId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemId", itemId);
			params.put("inventoryType", InventoryTypeEnum.NORMAL);
			Inventory inventory = this.inventoryDao.findInventoryByParam(params);
			if (inventory!=null) {
				//更新库存
				Long num = count + inventory.getNum();
				inventory.setNum(num);
				this.inventoryDao.updateInventory(inventory);
				//创建库存记录
				params.clear();
				params.put("item", item);
				params.put("num", count);	
				this.insertInventoryRecord(params);
				//创建操作日志
				this.insertItemOperator(params);
			}else{
				//创建库存
				Inventory in = new Inventory();
				in.generateId();
				in.setInventoryType(InventoryTypeEnum.NORMAL);
				in.setItem(item);
				in.setNum(count);
				in.setUser(item.getUser());
				this.inventoryDao.insertInventory(in);
				//创建库存记录
				params.clear();
				params.put("item", item);
				params.put("num", count);	
				this.insertInventoryRecord(params);
				//创建操作日志
				this.insertItemOperator(params);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ret", 3);
			resultMap.put("msg", e.getMessage());
			return resultMap;
		}
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 生成商品操作记录
	 * @param map
	 * */
	private void insertItemOperator(Map<String, Object> map) {	
		Item item = (Item) map.get("item");
		String num = "" + map.get("num");
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		Account account = SessionUser.get();
		operator.setAccount(account);
		operator.setItem(item);
		operator.setDescription(item.getName()+"添加库存！");
		operator.setNewValue(num);
		operator.setOldValue(num);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.STORAGE);
		this.operatorDao.insertItemOperator(operator);
	}

	/**
	 * 生成库存记录
	 * @param map
	 * */
	private void insertInventoryRecord(Map<String, Object> params) {
		Item item = (Item) params.get("item");
		String num = "" + params.get("num");
		InventoryRecord inventoryRecord = new InventoryRecord();
		inventoryRecord.generateId();
		inventoryRecord.setCreateDate(new Date());
		inventoryRecord.setDescription(item.getName()+"可销售库存添加"+num);
		inventoryRecord.setInventoryType(InventoryTypeEnum.NORMAL);
		inventoryRecord.setItem(item);
		inventoryRecord.setOrderNo(item.getId());
		inventoryRecord.setOrderType(InventoryOrderTypeEnum.NORMALGRN);
		inventoryRecord.setUser(item.getUser());
		this.recordDao.insertInventoryRecord(inventoryRecord);
	}

	
	public void updateItemStatus(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.itemDao.updateItemStatus(params);
	}

	@Override
	public void updateItemType(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.itemDao.updateItemType(params);
	}
	@Override
	public Map<String, Object> findStoreItemById(String id) {
		// TODO Auto-generated method stub
		return this.itemDao.findStoreItemById(id);
	}
	@Override
	public List<Map<String, Object>> getStoreItemsByPage(int page, int rows, Map<String, Object> params) {
		// TODO Auto-generated method stub
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageSize", pageSize);
		params.put("pageNum", pageNum);
		return this.itemDao.getStoreItemList(params);
	}

	public int getStoreTotal(Map<String, Object> params) {
		return this.itemDao.getStoreTotal(params);
	}
}
