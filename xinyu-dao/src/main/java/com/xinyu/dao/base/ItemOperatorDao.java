package com.xinyu.dao.base;
import java.util.List;
import java.util.Map;
import com.xinyu.dao.common.BaseDao;
import com.xinyu.model.base.ItemOperator;

/**
 * 商品操作日志
 * */
public interface ItemOperatorDao extends BaseDao {
	
	/**
	 * 生成日志
	 * @param ItemOperator
	 * */
	public void insertItemOperator(ItemOperator info);
	 
	/**
	 * 更新日志
	 * @param ItemOperator
	 * */
	public void updateItemOperator(ItemOperator info);
	
	/**
	 * 根据ID查日志
	 * @param id
	 * @return ItemOperator
	 * */
	public ItemOperator getItemOperatorById(String id);
	
	/**
	 * 条件查日志
	 * @param map
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByList(Map<String, Object> params);
	
	/**
	 * 分页查日志
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByPage(Map<String, Object> params,int page,int rows);
	

	public int getTotal(Map<String, Object> params);
}
