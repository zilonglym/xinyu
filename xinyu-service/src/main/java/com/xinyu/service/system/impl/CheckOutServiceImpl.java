package com.xinyu.service.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinyu.dao.base.AccountDao;
import com.xinyu.dao.base.ItemDao;
import com.xinyu.dao.base.PersonDao;
import com.xinyu.dao.base.UserDao;
import com.xinyu.dao.trade.CheckOutDao;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.Person;
import com.xinyu.model.trade.CheckOut;
import com.xinyu.service.common.BaseServiceImpl;
import com.xinyu.service.system.CheckOutService;

/**
 * 出库记录业务接口实现
 * */
@Service("checkOutServiceImpl")
public class CheckOutServiceImpl extends BaseServiceImpl implements CheckOutService{

	public static final Logger logger = Logger.getLogger(CheckOutServiceImpl.class);
	
	@Autowired
	private CheckOutDao checkOutDao;
	
	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private AccountDao accountDao;

	/**
	 * 分页查询出库记录
	 * @param params
	 * @param page
	 * @param rows
	 * @return list
	 * */
	@Override
	public List<CheckOut> findCheckOutPage(Map<String, Object> params, int page, int rows) {
		int pageNum = (page-1)*rows;
		int pageSize = rows;
		params.put("pageNum", pageNum);
		params.put("pageSize", pageSize);
		return this.checkOutDao.findCheckOuts(params);
	}

	/**
	 * 符合条件的出库记录数
	 * @param params
	 * @return int
	 * */
	@Override
	public int getTotal(Map<String, Object> params) {
		return this.checkOutDao.getTotal(params);
	}

	/**
	 * checkOut数据重组
	 * @param list
	 * @return list
	 * */
	@Override
	public List<Map<String, Object>> buildData(List<CheckOut> checkOuts) {
		Date date=new Date();
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(CheckOut checkOut:checkOuts){
			Map<String, Object> map = new HashMap<String, Object>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("id", checkOut.getId());
			map.put("createDate", sf.format(checkOut.getCreateDate()));
			if (checkOut.getUser()!=null) {
				User user = this.userDao.getUserById(checkOut.getUser().getId());
				if (user!=null) {
					map.put("userName", user.getSubscriberName());
				}		
			}
			map.put("express", checkOut.getCpCompany());
			map.put("code", checkOut.getOrderCode());
			map.put("barCode", checkOut.getBarCode());
			map.put("status", checkOut.getStatus());
			map.put("itemName", checkOut.getItemName());
			if (checkOut.getAccount()!=null) {
				Account account = this.accountDao.findAcountById(checkOut.getAccount().getId());
				if (account!=null) {
					map.put("person", account.getUserName());
				}else {
					Person person = this.personDao.findStroePersonById(checkOut.getAccount().getId());
					map.put("person", person.getName());
					map.put("person", "");
				}	
			}
			map.put("msg", checkOut.getMsg());
			results.add(map);
		}
		logger.error("出库列表数据封装耗时:"+(new Date().getTime()-date.getTime()));
		return results;
	}

	
//	1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 * </pre>
	 * @return
	 */
	public Map<String,Object> getItemInfoBybarCode(String barCode){
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		Item item  = null;
		try {
			retMap.put("barCode", barCode);
			item = this.itemDao.getItemByList(retMap).get(0);
			retMap.clear();
			if (item != null) {
				retMap.put("item", item);
				retMap.put("code", "200");
			}else{
				retMap.put("code", "404");
			}
			return retMap ;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg","获取商品信息失败:barCode=" +barCode+",["+e.toString()+"]"); 
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return  retMap;
	}
	
//	1.根据条码取商品相应信息
	/**
	 * 
	 * @param barCode
	 * <pre>
	 *  code = 200 匹配成功
	 *  code = 404  无法匹配信息
	 *  code = 500  系统异常
	 *  msg  错误信息    
	 *  error 系统异常时， 异常错误 
	 * </pre>
	 * @return
	 */
	public Map<String,Object> getItemInfoBybarCodes(String barCode){
		
		Map<String,Object>  retMap  =  new HashMap<String,Object>();
		List<Item> list  = null;
		try {
			retMap.put("barCode", barCode);
			list= this.itemDao.getItemByList(retMap);
			retMap.clear();
			if (list != null) {
				retMap.put("items", list);
				retMap.put("code", "200");
			}else{
				retMap.put("code", "404");
			}
			return retMap ;
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("msg","获取商品信息失败:barCode=" +barCode+",["+e.toString()+"]"); 
			retMap.put("error", e);
			retMap.put("code", "500");
		}
		return  retMap;
	}

	@Override
	public void insertCheckOut(CheckOut checkOut) {
		this.checkOutDao.insertCheckOut(checkOut);
	}
	
}
