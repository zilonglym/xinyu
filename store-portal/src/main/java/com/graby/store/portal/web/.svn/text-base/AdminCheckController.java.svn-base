package com.graby.store.portal.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.entity.CheckOut;
import com.graby.store.entity.Item;
import com.graby.store.entity.Person;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.service.base.PersonService;
import com.graby.store.service.base.UserService;
import com.graby.store.service.check.CheckService;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.service.inventory.InventoryService;
import com.graby.store.service.inventory.QmInventoryService;
import com.graby.store.service.item.ItemService;
import com.graby.store.service.wms.ShipOrderService;
import com.graby.store.service.wms.SystemItemService;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/check")
public class AdminCheckController extends BaseController{
	
	public static final Logger logger = Logger.getLogger(AdminCheckController.class);
	@Autowired
	private PersonService personRemote;
	
	@Autowired
	private CheckService  checkRemote;
	
	@Autowired
	private SystemItemService  systemItemRemote;
	
	@Autowired
	private UserService  userRemote;
	
	@Autowired
	private ItemService  itemRemote;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private QmInventoryService  qmInventoryRemote;
	@Autowired
	private ShipOrderService shipOrderRemote;
	
	
	
	/**
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "getItemInfoBybarCode",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCode(@RequestParam(value = "barCode", defaultValue = "0") String barCode,Model model) throws ApiException {
		
		return checkRemote.getItemInfoBybarCode(barCode);
	}
	/**
	 * 取快递公司
	 * @return
	 */
	@RequestMapping(value="getExpressCompany")
	@ResponseBody
	public Map<String,Object> getExpressCompany(){
		String expressNo=this.getString("orderCode");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("expressNo", expressNo.substring(0, 4));
		params.put("type", StoreSystemItemEnums.CHECK_COMP.getKey());
		List<SystemItem> systemItem=this.systemItemRemote.findSystemItemByTypeAndVal(params);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for(int i=0;systemItem!=null && i<systemItem.size();i++){
			resultMap.put("code", 200);
			resultMap.put("item", systemItem.get(0));
		}
		if(systemItem==null || systemItem.size()==0){
			resultMap.put("code", 404);
		}
		return resultMap;
	}
	
	/**商品条码取数据
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "getItemInfoBybarCodes",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCodes(@RequestParam(value = "barCode", defaultValue = "0") String barCode,Model model) throws ApiException {
		
		return checkRemote.getItemInfoBybarCodes(barCode);
	}
	
	private static List<String>  sysItemList; //是否开启检验
	
	/**订单出库检验信息
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "checkTrade",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkTrade(@RequestParam(value = "orderCode", defaultValue = "0") String orderCode,
			@RequestParam(value = "barCode", defaultValue = "0") String barCode,
			@RequestParam(value = "stock", defaultValue = "0") String stock,
			@RequestParam(value = "cp", defaultValue = "0") String cp,
			@RequestParam(value = "userId", defaultValue = "0") String userId,
			@RequestParam(value = "persinId", defaultValue = "0") String persinId,
			Model model) throws Exception {
		Date date=new Date();
		if("0".equals(persinId)){
			Map<String, Object>   retMap  = new HashMap<String,Object>();
			retMap.put("code", "400");
			retMap.put("msg"," 无法获得操作人员信息,请重新登录 ");
			return retMap;
		}
		
		if(sysItemList==null){
			sysItemList = new ArrayList<String>();
			List<SystemItem>  sysItem = systemItemRemote.findSystemItemByType("CHECK_TRADE");
			for(int i = 0 ,size =sysItem.size();i<size;i++ ){
				sysItemList.add(sysItem.get(i).getValue());
			}
		}
		Map<String,Object> map =checkRemote.checkTrade(orderCode, barCode,stock,cp,userId,persinId,sysItemList,"");
		logger.debug("验货时间:"+(new Date().getTime()-date.getTime()));;
		return map;
	}
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFm.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String format1 = dateFm.format(cal.getTime());
		System.out.println(format);
		System.out.println(format1);
	}
	/**
	 * 取每日订单出库校验统计
	 * @return
	 */
	@RequestMapping(value="getCheckTradeInfo")
	@ResponseBody
	public Map<String,Object> getCheckTradeInfo(){
		Map<String,Object> params = new HashMap<String,Object>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = dateFm.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String endDate = dateFm.format(cal.getTime());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("importTrade", shipOrderRemote.getCountByDate(params));//导入订单数
		resultMap.put("checkTrade", shipOrderRemote.getCheckSuccessCountByDate(params));//已校验订单数
		resultMap.put("successTrade", shipOrderRemote.getSuccessCountByDate(params));//成功订单数
		resultMap.put("failTrade", shipOrderRemote.getCheckFailCountByDate(params));//失败订单数
		
//		resultMap.put("importTrade", 10);//导入订单数
//		resultMap.put("checkTrade", 10);//已校验订单数
//		resultMap.put("successTrade", 10);//成功订单数
//		resultMap.put("failTrade", 10);//失败订单数
		return resultMap;
	}
	
	/**
	 * 刷新订单校验商家信息
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "reLoadSystem",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> reLoadSystem(Model model) throws Exception {
		List<SystemItem> sysItem = systemItemRemote.findSystemItemByType("CHECK_TRADE");
		sysItemList = new ArrayList<String>();
		for (int i = 0, size = sysItem.size(); i < size; i++) {
			sysItemList.add(sysItem.get(i).getValue());
		}
		Map<String,Object>  map  =  new HashMap<String,Object>();
		map.put("sysItemList", sysItemList);
		return map;
	}
	
	/**
	 *  保存出库记录流水
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "saveCheckOut",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveCheckOut(HttpServletRequest request,ModelMap model) throws ApiException {
	
		Map<String, Object>   retMap  = new HashMap<String,Object>();
		
		String  jsonStr  =  request.getParameter("json");
		System.err.println(jsonStr);
		JSONObject json=new JSONObject(jsonStr);
        JSONArray date= json.getJSONArray("detail");
        String  persinId  = json.getString("personId"); 
		
//		if("0".equals(persinId)){
//			retMap.put("code", "400");
//			retMap.put("msg"," 无法获得操作人员信息,请重新登录 ");
//			return retMap;
//		}
		
//        json.getString("orderCode");
		
        String  orderCode  =   json.getString("orderCode");
        String  cp  =  json.getString("cp");
        String  stock  =  json.getString("stock");
        String  userId  =  json.getString("userId");
        String  state  =  json.getString("state");
        
		try {
			int  size  =  date.length();
			for(int i =0  ; i<size;i++){
				JSONObject obj = date.getJSONObject(i);
				Long itmeId  =  Long.valueOf(""+obj.get("itemId"));
				Long count =  Long.valueOf(""+obj.get("count"));
//	        	storageItem.setQuantity(Integer.valueOf(""+obj.get("quantity")));
				Item item = itemRemote.getItem(itmeId); //获得商品信息
				CheckOut  checkOut  =  new CheckOut();	
				String barCode  =item.getBarCode();  //单号号 条码
				checkOut.setOrderCode(orderCode);
				checkOut.setBarCode(item.getBarCode()); //  商品条码信息
				checkOut.setCpCompany(cp);//快递公司
				checkOut.setCreateDate(new Date());
				checkOut.setCentroId(Long.valueOf(stock));
				checkOut.setUserId(Long.valueOf(userId));
				checkOut.setState(state);
				checkOut.setPersonId(new Long(persinId));
				Map<String,Object>  itemParams  = new HashMap<String,Object>();
				itemParams.put("barCode", barCode);
				itemParams.put("userId", userId);

				if(item.getPackageWeight() >0 ){ //有包裹重量  取包裹重量 。 没有时，  直接取商品重量
					checkOut.setWeight(item.getPackageWeight());
				}else{
					checkOut.setWeight(item.getWeight());
				}
				Map<String,Object> params   =   new HashMap<String,Object>();
				params.put("itemId", item.getId());
				params.put("orderNo", orderCode);
				Long existOrderNo = qmInventoryRemote.existOrderNo(params);
				checkOut.setItemId(item.getId());
				checkOut.setItemName(item.getTitle());
				if(existOrderNo==0){
					checkOut.setMsg("补录成功");
					checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_ADD);
					checkOut.setNum(count);
					qmInventoryRemote.addInventory(Long.valueOf(stock), new Long(userId), item.getId(), -count, Accounts.CODE_SALEABLE, "手动出库", orderCode);
					//手工补录，出库释放已使用数量
					this.inventoryService.updateUserNum(Long.valueOf(stock),  item.getId(), Accounts.CODE_SALEABLE, -count );
				}else{
					checkOut.setMsg("订单重复补录");
					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_ADD);
					checkOut.setNum(count);
				}
				retMap.put("code", "200");
				checkRemote.saveCheckOut(checkOut);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
		}
		return  retMap;
	}
	
//	public  InventoryRemote inventoryRemote;
	
	/**
	 * 获取所有商家信息
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "getUsers",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUsers(
			Model model) throws ApiException {
		Map<String, Object>   retMap  = new HashMap<String,Object>();
		try {
			retMap.put("users", userRemote.findUsers());
			retMap.put("code", "200");
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
		}
		return  retMap;
	}
	
	
	
	/**
	 * 提交登录
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "submitLogin",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUsers(
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		/**
		 * 1.取得此用户名的用户
		 */
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userName", userName);
		Person person=this.personRemote.searchPerson(params).get(0);
		/**
		 * 2.判断用户密码
		 */
		
		String pwd=person.getPassword();
		person.setPassword(password);
		super.entryptPassword(person,person.getSalt());
		logger.info(pwd+"||"+person.getPassword());
		if(pwd.equals(person.getPassword())){
			resultMap.put("code", "200");
			resultMap.put("person", person);
		}else{
			resultMap.put("code", "500");
		}
		return resultMap;
	}
	
	
}
