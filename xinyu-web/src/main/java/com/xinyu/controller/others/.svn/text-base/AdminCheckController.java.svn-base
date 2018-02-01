package com.xinyu.controller.others;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
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
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.AccountRelation;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.trade.CheckOut;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.AccountRelationService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.CheckOutService;
import com.xinyu.service.system.CheckService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.PersonService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderService;



@Controller
@RequestMapping(value = "/check")
public class AdminCheckController extends BaseController{
	
	public static final Logger logger = Logger.getLogger(AdminCheckController.class);
	
	@Autowired
	private CheckService  checkService;
	
	@Autowired
	private CheckOutService  checkOutService;
	
	@Autowired
	private SystemItemService  systemItemService;
	
	@Autowired
	private UserService  userService;
	
	@Autowired
	private AccountService  accountService;
	
	@Autowired
	private AccountRelationService relationService;
	
	@Autowired
	private ItemService  itemService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	
	private Map<String,String> cpMap=null;
	
	
	
	/**
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItemInfoBybarCode",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCode(@RequestParam(value = "barCode", defaultValue = "0") String barCode,Model model) throws Exception {
		
		return checkService.getItemInfoBybarCode(barCode);
	}
	
	/**
	 * 刷新快递公司
	 * @return
	 */
	@RequestMapping(value="refreshCp")
	public Map<String,Object> refershCp(){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		initCpMap();
		resultMap.put("ret", "1");
		resultMap.put("msg", "刷新快递公司成功");
		return resultMap;
	}
	/**
	 * 取快递公司
	 * @return
	 */
	@RequestMapping(value="getExpressCompany")
	@ResponseBody
	public Map<String,Object> getExpressCompany(){
		String expressNo=this.getString("orderCode");
		
		
		if(cpMap==null){
			initCpMap();
		}
		String cp=cpMap.get(expressNo.substring(0,4));
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("code", 200);
		
		SystemItem item=new SystemItem();
		item.setId("88");
		
		if(cp==null){
			item.setDescription("");
		}else{
			item.setDescription(cp);
		}
		
		resultMap.put("item", item);
//		if(cp==null){
//			resultMap.put("code", 404);
//		}
		return resultMap;
	}
	
	/**商品条码取数据
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItemInfoBybarCodes",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCodes(@RequestParam(value = "barCode", defaultValue = "0") String barCode,Model model) throws Exception {
		
		return checkService.getItemInfoBybarCodes(barCode);
	}
	
	private static List<String>  sysItemList; //是否开启检验
	
	/**订单出库检验信息
	 * @param model
	 * @return
	 * @throws Exception
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
		if(cpMap==null){
			initCpMap();
		}
		
		/**
		 * 判断条码,如果快递条码不在查询出来的快递中间，则两才交换位置
		 */
		String tempOrderCode=orderCode.substring(0,4);
		if(cpMap.get(tempOrderCode)==null){
			String temp=orderCode;
			orderCode=barCode;
			barCode=temp;
		}
		
		if(cp==null || cp.equals("0")){
			cp=cpMap.get(orderCode.substring(0,4));
		}
		
		Date date=new Date();
		if("0".equals(persinId)){
			Map<String, Object>   retMap  = new HashMap<String,Object>();
			retMap.put("code", "400");
			retMap.put("msg"," 无法获得操作人员信息,请重新登录 ");
			return retMap;
		}
		
		if(sysItemList==null){
			sysItemList = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", StoreSystemItemEnums.CHECK_TRADE.getKey());
			List<SystemItem>  sysItem = systemItemService.findSystemItemByList(params);
			for(int i = 0 ,size =sysItem.size();i<size;i++ ){
				sysItemList.add(sysItem.get(i).getValue());
			}
		}
		Map<String,Object> map =checkService.checkTrade(orderCode, barCode,stock,cp,userId,persinId,sysItemList,"");
//		logger.debug("验货结果:"+map);
//		logger.debug("验货时间:"+(new Date().getTime()-date.getTime())+"[barCode:"+barCode+"|orderCode:"+orderCode+"]");;
		return map;
	}
	
	
	
	/**订单出库检验信息
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkTrade_new")
	@ResponseBody
	public Map<String,Object> checkTrade_new(@RequestParam(value = "orderCode", defaultValue = "0") String orderCode,
			@RequestParam(value = "barCode", defaultValue = "0") String barCode,
			@RequestParam(value = "stock", defaultValue = "0") String stock,
			@RequestParam(value = "cp", defaultValue = "0") String cp,
			@RequestParam(value = "userId", defaultValue = "0") String userId,
			@RequestParam(value = "persinId", defaultValue = "0") String persinId,
			Model model) throws Exception {
		logger.error("菜鸟验货入口!");
		if(cpMap==null){
			initCpMap();
		}
		
		Date date=new Date();
		if(sysItemList==null){
			sysItemList = new ArrayList<String>();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("type", StoreSystemItemEnums.CHECK_TRADE.getKey());
			List<SystemItem>  sysItem = systemItemService.findSystemItemByList(params);
			for(int i = 0 ,size =sysItem.size();i<size;i++ ){
				sysItemList.add(sysItem.get(i).getValue());
			}
		}
		
		if(cp==null || cp.equals("0")){
			cp=cpMap.get(orderCode.substring(0,4));
		}
		/**
		 * 判断条码,如果快递条码不在查询出来的快递中间，则两才交换位置
		 */
		String tempOrderCode=orderCode.substring(0,4);
		if(cpMap.get(tempOrderCode)==null){
			String temp=orderCode;
			orderCode=barCode;
			barCode=temp;
		}
		
		Map<String,Object> map =checkService.checkTrade(orderCode, barCode,stock,cp,userId,persinId,sysItemList,"JAVA");
		logger.error("验货时间:"+(new Date().getTime()-date.getTime())+"[barCode:"+barCode+"|orderCode:"+orderCode+"]");
		return map;
	}
	
	private void initCpMap(){
		cpMap=new HashMap<String, String>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.CHECK_COMP.getKey());
		
		List<SystemItem>  systemItem = systemItemService.findSystemItemByList(params);
		for(int i=0;systemItem!=null && i<systemItem.size();i++){
			SystemItem item=systemItem.get(i);
			String valueStr=item.getValue();
			String[] ary=valueStr.split(",");
			for(int j=0;ary!=null && j<ary.length;j++){
				cpMap.put(ary[j], item.getDescription());
			}
		}
	}
	
	public static void main(String[] args) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		String format = dateFm.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String format1 = dateFm.format(cal.getTime());
		System.out.println(format);
		System.out.println(format1);
		System.err.println(StringUtils.isNumeric("12345.123"));
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
		resultMap.put("importTrade", shipOrderService.getTotal(params));//导入订单数
		resultMap.put("checkTrade", checkService.getCheckSuccessCountByDate(params));//已校验订单数
		resultMap.put("successTrade", checkService.getSuccessCountByDate(params));//成功订单数
		resultMap.put("failTrade", checkService.getCheckFailCountByDate(params));//失败订单数
		
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
	 * @throws Exception
	 */
	@RequestMapping(value = "reLoadSystem",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> reLoadSystem(Model model) throws Exception {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", StoreSystemItemEnums.CHECK_TRADE.getKey());
		
		List<SystemItem>  sysItem = systemItemService.findSystemItemByList(params);
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
	 * @throws Exception
	 */
	@RequestMapping(value = "saveCheckOut",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveCheckOut(HttpServletRequest request,ModelMap model) throws Exception {
	
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
				String itemId  =  String.valueOf(""+obj.get("itemId"));
				
				Long count =  Long.valueOf(""+obj.get("count"));
				
//	        	storageItem.setQuantity(Integer.valueOf(""+obj.get("quantity")));
				Item item = itemService.getItem(itemId); //获得商品信息
				
				CheckOut  checkOut  =  new CheckOut();	
				String barCode  =item.getBarCode();  //单号号 条码
				
				checkOut.setOrderCode(orderCode);
				
				checkOut.setBarCode(item.getBarCode()); //  商品条码信息
				
				checkOut.setCpCompany(cp);//快递公司
				
				checkOut.setCreateDate(new Date());
				
//				checkOut.setCentroId(Long.valueOf(stock));
				User user = this.userService.getUserById(userId);
				checkOut.setUser(user);
				
				checkOut.setState(state);
				
				AccountRelation relation = this.relationService.findAccountRlationByPersonId(persinId);
				
				Account account = this.accountService.findAcountById(relation.getAccount().getId());
				checkOut.setAccount(account);
				
				Map<String,Object>  itemParams  = new HashMap<String,Object>();
				itemParams.put("barCode", barCode);
				itemParams.put("userId", userId);

				if(item.getWmsGrossWeight() >0 ){ //有包裹重量  取包裹重量 。 没有时，  直接取商品重量
//					checkOut.setWeight(item.getWmsGrossWeight());
				}else{
//					checkOut.setWeight(item.getGrossWeight());
				}
				
				Map<String,Object> params   =   new HashMap<String,Object>();
				params.put("itemId", item.getId());
				params.put("orderNo", orderCode);
//				Long existOrderNo = inventoryService.existOrderNo(params);
				
				checkOut.setItem(item);
				
				checkOut.setItemName(item.getName());
//				if(existOrderNo==0){
//					checkOut.setMsg("补录成功");
//					checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_ADD);
//					checkOut.setNum(count);
//					inventoryService.addInventory(Long.valueOf(stock), new Long(userId), item.getId(), -count, Accounts.CODE_SALEABLE, "手动出库", orderCode);
//					//手工补录，出库释放已使用数量
//					this.inventoryService.updateUserNum(Long.valueOf(stock),  item.getId(), Accounts.CODE_SALEABLE, -count );
//				}else{
//					checkOut.setMsg("订单重复补录");
//					checkOut.setStatus(CheckOut.CheckOutStatus.FAIL_ADD);
//					checkOut.setNum(count);
//				}
				retMap.put("code", "200");
				checkOutService.insertCheckOut(checkOut);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
		}
		return  retMap;
	}
	
//	public  InventoryService inventoryService;
	
	/**
	 * 获取所有商家信息
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUsers",  method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUsers(
			Model model) throws Exception {
		Map<String, Object>   retMap  = new HashMap<String,Object>();
		try {
			retMap.put("users", userService.getUserBySearch(null));
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
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException 
	 */
	@RequestMapping(value = "submitLogin")
	@ResponseBody
	public Map<String, Object> getUsers(
			@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		/**
		 * 1.取得此用户名的用户
		 */
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userName", userName);
		List<Account> list=this.accountService.findAccountsByList(params);
		Account account=list!=null?list.get(0):null;
		if(account==null){
			resultMap.put("code", "500");
			return resultMap;
		}
		/**
		 * 2.判断用户密码
		 */
		String pwd=account.getPassword();
		account.setPassword(password);
		logger.info(pwd+"||"+account.getPassword());
		if(pwd.equals(EncoderByMd5(account.getPassword()))){
			resultMap.put("code", "200");
			resultMap.put("person", account);
		}else{
			resultMap.put("code", "500");
		}
		return resultMap;
	}
	
	
	
}
