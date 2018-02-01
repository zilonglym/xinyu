package com.xinyu.service.system.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.ItemOperatorDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.system.Account;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.ItemOperatorService;

/**
 * 商品操作日志
 * */
@Service("itemOperatorServiceImpl")
public class ItemOperatorServiceImpl extends BaseServiceImpl implements ItemOperatorService{

	@Autowired 
	private ItemOperatorDao operatorDao;
	
	@Autowired 
	private ItemDao itemDao;
	
	@Autowired 
	private AccountDao accountDao;
	
	/**
	 * 生成日志
	 * @param ItemOperator
	 * */
	@Override
	public void insertItemOperator(ItemOperator info) {
		this.operatorDao.insertItemOperator(info);
	}

	/**
	 * 更新日志
	 * @param ItemOperator
	 * */
	@Override
	public void updateItemOperator(ItemOperator info) {
		this.operatorDao.updateItemOperator(info);
	}

	/**
	 * 根据ID查日志
	 * @param id
	 * @return ItemOperator
	 * */
	@Override
	public ItemOperator getItemOperatorById(String id) {
		return this.operatorDao.getItemOperatorById(id);
	}

	/**
	 * 条件查日志
	 * @param map
	 * @return list
	 * */
	@Override
	public List<ItemOperator> getItemOperatorByList(Map<String, Object> params) {
		return this.operatorDao.getItemOperatorByList(params);
	}

	/**
	 * 分页查日志
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<ItemOperator> getItemOperatorByPage(Map<String, Object> params, int page, int rows) {
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageSize", pageSize);
		params.put("pageNum", pageNum);
		return this.operatorDao.getItemOperatorByList(params);
	}

	/**
	 * 数据封装
	 * @param operators
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildListData(List<ItemOperator> operators) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for (ItemOperator operator:operators) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", operator.getId());
			Item item = this.itemDao.getItem(operator.getItem().getId());
			if (item!=null) {
				map.put("remark", item.getId()+"|"+item.getName());
			}else{
				map.put("remark", operator.getItem().getId());
			}
			map.put("operatorDate", sf.format(operator.getOperatorDate()));
			Account account = this.accountDao.findAcountById(operator.getAccount().getId());
			if (account!=null) {
				map.put("account", (account!=null?account.getShortName():"cainiao"));
			}else{
				map.put("account", "cainiao");
			}
			map.put("model", operator.getOperatorModel().getDescription());
			map.put("oldValue", operator.getOldValue());
			map.put("newValue", operator.getNewValue());
			map.put("description", operator.getDescription());
			results.add(map);
		}
		return results;
	}

	/**
	 * 统计计数
	 * @param map
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		return this.operatorDao.getTotal(params);
	}

}
