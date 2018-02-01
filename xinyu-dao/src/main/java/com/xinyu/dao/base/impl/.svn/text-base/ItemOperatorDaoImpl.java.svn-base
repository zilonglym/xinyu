package com.xinyu.dao.base.impl;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.xinyu.dao.common.BaseDaoImpl;
import com.xinyu.dao.base.ItemOperatorDao;
import com.xinyu.model.base.ItemOperator;

/**
 *
 * 商品操作日志
 * 
 * */
@Repository("itemOperatorDaoImpl")
public class ItemOperatorDaoImpl extends BaseDaoImpl implements ItemOperatorDao {
	
	/**
	 * 生成日志
	 * @param ItemOperator
	 * */
	public void insertItemOperator(ItemOperator info){
		this.insert("com.xinyu.dao.base.ItemOperatorDao.insertItemOperator",info);
	}
	
	/**
	 * 更新日志
	 * @param ItemOperator
	 * */
	public void updateItemOperator(ItemOperator info){
		this.insert("com.xinyu.dao.base.ItemOperatorDao.updateItemOperator",info);
	}
	
	/**
	 * 根据ID查日志
	 * @param id
	 * @return ItemOperator
	 * */
	public ItemOperator getItemOperatorById(String id){
		return (ItemOperator)this.selectOne("com.xinyu.dao.base.ItemOperatorDao.getItemOperatorById",id);
	}
	
	/**
	 * 条件查日志
	 * @param map
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByList(Map<String, Object> params){
		return (List<ItemOperator>)this.selectList("com.xinyu.dao.base.ItemOperatorDao.getItemOperatorByList",params);
	}
	
	/**
	 * 分页查日志
	 * @param map
	 * @param page
	 * @param rows
	 * @return list
	 * */
	public List<ItemOperator> getItemOperatorByPage(Map<String, Object> params,int page,int rows){
		return this.selectList("com.xinyu.dao.base.ItemOperatorDao.getItemOperatorByList",params,rows,page);
	}

	@Override
	public int getTotal(Map<String, Object> params) {
		return (Integer) this.selectOne("com.xinyu.dao.base.ItemOperatorDao.getTotal", params);
	}
}
