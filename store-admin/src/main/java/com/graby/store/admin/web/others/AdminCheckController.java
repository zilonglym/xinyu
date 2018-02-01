package com.graby.store.admin.web.others;

import java.io.UnsupportedEncodingException;
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

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.Item;
import com.graby.store.entity.Person;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.remote.CheckRemote;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.inventory.Accounts;
import com.graby.store.util.qm.HttpClientUtils;

@Controller
@RequestMapping(value = "/check")
public class AdminCheckController extends BaseController {

	public static final Logger logger = Logger.getLogger(AdminCheckController.class);
	@Autowired
	private PersonRemote personRemote;

	@Autowired
	private CheckRemote checkRemote;

	@Autowired
	private SystemItemRemote systemItemRemote;

	@Autowired
	private UserRemote userRemote;

	@Autowired
	private ItemRemote itemRemote;

	@Autowired
	private InventoryRemote inventoryRemote;
	@Autowired
	private ShipOrderRemote shipOrderRemote;

	private Map<String, String> cpMap = null;
	
	private static final String cainiao_url="http://zc.wlpost.com/check/checkTrade_new";
//	private static final String cainiao_url="http://localhost:8080/xinyu-web/check/checkTrade_new";

	/**
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItemInfoBybarCode", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCode(@RequestParam(value = "barCode", defaultValue = "0") String barCode, Model model)
			throws Exception {

		return checkRemote.getItemInfoBybarCode(barCode);
	}

	/**
	 * 刷新快递公司
	 * 
	 * @return
	 */
	@RequestMapping(value = "refreshCp")
	public Map<String, Object> refershCp() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		initCpMap();
		resultMap.put("ret", "1");
		resultMap.put("msg", "刷新快递公司成功");
		return resultMap;
	}

	/**
	 * 取快递公司
	 * 
	 * @return
	 */
	@RequestMapping(value = "getExpressCompany")
	@ResponseBody
	public Map<String, Object> getExpressCompany() {
		String expressNo = this.getString("orderCode");

		if (cpMap == null) {
			initCpMap();
		}
		String cp = cpMap.get(expressNo.substring(0, 4));
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", 200);
		SystemItem item = new SystemItem();
		item.setId(88l);
		if (cp == null) {
			item.setDescription("");
		} else {
			item.setDescription(cp);
		}
		resultMap.put("item", item);
		// if(cp==null){
		// resultMap.put("code", 404);
		// }
		return resultMap;
	}

	/**
	 * 商品条码取数据
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getItemInfoBybarCodes", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> barCodes(@RequestParam(value = "barCode", defaultValue = "0") String barCode,
			Model model) throws Exception {

		return checkRemote.getItemInfoBybarCodes(barCode);
	}

	private static List<String> sysItemList; // 是否开启检验

	/**
	 * 订单出库检验信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkTrade", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> checkTrade(@RequestParam(value = "orderCode", defaultValue = "0") String orderCode,
			@RequestParam(value = "barCode", defaultValue = "0") String barCode,
			@RequestParam(value = "stock", defaultValue = "0") String stock,
			@RequestParam(value = "cp", defaultValue = "0") String cp,
			@RequestParam(value = "userId", defaultValue = "0") String userId,
			@RequestParam(value = "persinId", defaultValue = "0") String persinId, Model model) throws Exception {
		if (cpMap == null) {
			initCpMap();
		}

		/**
		 * 判断条码,如果快递条码不在查询出来的快递中间，则两才交换位置
		 */
		String tempOrderCode = orderCode.substring(0, 4);
		if (cpMap.get(tempOrderCode) == null) {
			String temp = orderCode;
			orderCode = barCode;
			barCode = temp;
		}

		if (cp == null || cp.equals("0")) {
			cp = cpMap.get(orderCode.substring(0, 4));
		}
		Date date = new Date();
		if ("0".equals(persinId)) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("code", "400");
			retMap.put("msg", " 无法获得操作人员信息,请重新登录 ");
			return retMap;
		}

		if (sysItemList == null) {
			sysItemList = new ArrayList<String>();
			List<SystemItem> sysItem = systemItemRemote.findSystemItemByType("CHECK_TRADE");
			for (int i = 0, size = sysItem.size(); i < size; i++) {
				sysItemList.add(sysItem.get(i).getValue());
			}
		}
		Map<String, Object> map = checkRemote.checkTrade(orderCode, barCode, stock, cp, userId, persinId, sysItemList,
				"");
		// logger.debug("验货结果:"+map);
		// logger.debug("验货时间:"+(new
		// Date().getTime()-date.getTime())+"[barCode:"+barCode+"|orderCode:"+orderCode+"]");;
		return map;
	}

	/**
	 * 订单出库检验信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "checkTrade_new")
	@ResponseBody
	public Map<String, Object> checkTrade_new(@RequestParam(value = "orderCode", defaultValue = "0") String orderCode,
			@RequestParam(value = "barCode", defaultValue = "0") String barCode,
			@RequestParam(value = "stock", defaultValue = "0") String stock,
			@RequestParam(value = "cp", defaultValue = "0") String cp,
			@RequestParam(value = "userId", defaultValue = "0") String userId,
			@RequestParam(value = "persinId", defaultValue = "0") String persinId, Model model) throws Exception {

		Map<String, Object> map = null;
		try {
			Date date = new Date();
			if (cpMap == null) {
				initCpMap();
			}
			if (cp == null || cp.equals("0")) {
				cp = cpMap.get(orderCode.substring(0, 4));
			}
			/**
			 * 判断条码,如果快递条码不在查询出来的快递中间，则两才交换位置
			 */
			String tempOrderCode = orderCode.substring(0, 4);
			if (cpMap.get(tempOrderCode) == null) {
				String temp = orderCode;
				orderCode = barCode;
				barCode = temp;
			}
			ShipOrder order=this.shipOrderRemote.findShipOrderByExpressOrderno(orderCode);
			/**
			 * 查询订单，如果订单没有找到则认为是菜鸟的单据,转发请求去菜鸟查
			 */
			if(order==null){
				logger.error("订单不存在，切换菜鸟自动验货!");
				String url=cainiao_url+"?stock=1&orderCode="+orderCode+"&barCode="+barCode;
				logger.error(url);
				String result=this.checkRemote.checkHttpTrade(url);
				JSONObject object=new JSONObject(result);
				map=new HashMap<String, Object>();
				map.put("code",object.getString("code"));
				map.put("msg", object.getString("msg"));
				logger.error(result);
			}else{
				map = checkRemote.checkTrade(orderCode, barCode, stock, cp, userId, persinId, sysItemList, "JAVA");
			}
			long time=new Date().getTime() - date.getTime();
			if(time>200){
				logger.error("JAVA 超时验货时间:" + time + "[barCode:" + barCode + "|orderCode:"+ orderCode + "]");
			}else{
				logger.error("JAVA 验货时间:" + time + "[barCode:" + barCode + "|orderCode:"+ orderCode + "]");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("JAVA验货失败！"+ "[barCode:" + barCode + "|orderCode:"
					+ orderCode + "]");
			logger.error(e.getLocalizedMessage());
			logger.error(e.getMessage());
			map=new HashMap<String, Object>();
			map.put("msg", "验货失败!");
			map.put("code", "500");
		}
		return map;
	}

	private void initCpMap() {
		cpMap = new HashMap<String, String>();
		List<SystemItem> systemItem = this.systemItemRemote
				.findSystemItemByType(StoreSystemItemEnums.CHECK_COMP.getKey());
		for (int i = 0; systemItem != null && i < systemItem.size(); i++) {
			SystemItem item = systemItem.get(i);
			String valueStr = item.getValue();
			String[] ary = valueStr.split(",");
			for (int j = 0; ary != null && j < ary.length; j++) {
				cpMap.put(ary[j], item.getDescription());
			}
		}
	}
	/**
	 * 取每日订单出库校验统计
	 * 
	 * @return
	 */
	@RequestMapping(value = "getCheckTradeInfo")
	@ResponseBody
	public Map<String, Object> getCheckTradeInfo() {
		Map<String, Object> params = new HashMap<String, Object>();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat dateFm = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = dateFm.format(cal.getTime());
		cal.add(Calendar.DATE, 1);
		String endDate = dateFm.format(cal.getTime());
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("importTrade", shipOrderRemote.getCountByDate(params));// 导入订单数
		resultMap.put("checkTrade", shipOrderRemote.getCheckSuccessCountByDate(params));// 已校验订单数
		resultMap.put("successTrade", shipOrderRemote.getSuccessCountByDate(params));// 成功订单数
		resultMap.put("failTrade", shipOrderRemote.getCheckFailCountByDate(params));// 失败订单数

		// resultMap.put("importTrade", 10);//导入订单数
		// resultMap.put("checkTrade", 10);//已校验订单数
		// resultMap.put("successTrade", 10);//成功订单数
		// resultMap.put("failTrade", 10);//失败订单数
		return resultMap;
	}

	/**
	 * 刷新订单校验商家信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "reLoadSystem", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> reLoadSystem(Model model) throws Exception {
		List<SystemItem> sysItem = systemItemRemote.findSystemItemByType("CHECK_TRADE");
		sysItemList = new ArrayList<String>();
		for (int i = 0, size = sysItem.size(); i < size; i++) {
			sysItemList.add(sysItem.get(i).getValue());
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sysItemList", sysItemList);
		return map;
	}

	/**
	 * 保存出库记录流水
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "saveCheckOut", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> saveCheckOut(HttpServletRequest request, ModelMap model) throws Exception {

		Map<String, Object> retMap = new HashMap<String, Object>();

		String jsonStr = request.getParameter("json");
		System.err.println(jsonStr);
		JSONObject json = new JSONObject(jsonStr);
		JSONArray date = json.getJSONArray("detail");
		String persinId = json.getString("personId");

		// if("0".equals(persinId)){
		// retMap.put("code", "400");
		// retMap.put("msg"," 无法获得操作人员信息,请重新登录 ");
		// return retMap;
		// }

		// json.getString("orderCode");

		String orderCode = json.getString("orderCode");
		String cp = json.getString("cp");
		String stock = json.getString("stock");
		String userId = json.getString("userId");
		String state = json.getString("state");

		try {
			int size = date.length();
			for (int i = 0; i < size; i++) {
				JSONObject obj = date.getJSONObject(i);
				Long itmeId = Long.valueOf("" + obj.get("itemId"));
				Long count = Long.valueOf("" + obj.get("count"));
				// storageItem.setQuantity(Integer.valueOf(""+obj.get("quantity")));
				Item item = itemRemote.getItem(itmeId); // 获得商品信息
				CheckOut checkOut = new CheckOut();
				String barCode = item.getBarCode(); // 单号号 条码
				checkOut.setOrderCode(orderCode);
				checkOut.setBarCode(item.getBarCode()); // 商品条码信息
				checkOut.setCpCompany(cp);// 快递公司
				checkOut.setCreateDate(new Date());
				checkOut.setCentroId(Long.valueOf(stock));
				checkOut.setUserId(Long.valueOf(userId));
				checkOut.setState(state);
				checkOut.setPersonId(new Long(persinId));
				Map<String, Object> itemParams = new HashMap<String, Object>();
				itemParams.put("barCode", barCode);
				itemParams.put("userId", userId);

				if (item.getPackageWeight() > 0) { // 有包裹重量 取包裹重量 。 没有时， 直接取商品重量
					checkOut.setWeight(item.getPackageWeight());
				} else {
					checkOut.setWeight(item.getWeight());
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("itemId", item.getId());
				params.put("orderNo", orderCode);
				Long existOrderNo = inventoryRemote.existOrderNo(params);
				checkOut.setItemId(item.getId());
				checkOut.setItemName(item.getTitle());
				if (existOrderNo == 0) {
					checkOut.setMsg("补录成功");
					checkOut.setStatus(CheckOut.CheckOutStatus.SUCCESS_ADD);
					checkOut.setNum(count);
					inventoryRemote.addInventory(Long.valueOf(stock), new Long(userId), item.getId(), -count,
							Accounts.CODE_SALEABLE, "手动出库", orderCode);
					// 手工补录，出库释放已使用数量
					this.inventoryRemote.updateUserNum(Long.valueOf(stock), item.getId(), Accounts.CODE_SALEABLE,
							-count);
				} else {
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
		return retMap;
	}

	// public InventoryRemote inventoryRemote;

	/**
	 * 获取所有商家信息
	 * 
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getUsers", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getUsers(Model model) throws Exception {
		Map<String, Object> retMap = new HashMap<String, Object>();
		try {
			retMap.put("users", userRemote.findUsers());
			retMap.put("code", "200");
		} catch (Exception e) {
			retMap.put("code", "500");
			retMap.put("msg", e.getMessage());
		}
		return retMap;
	}

	/**
	 * 提交登录
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	@RequestMapping(value = "submitLogin")
	@ResponseBody
	public Map<String, Object> getUsers(@RequestParam(value = "userName", defaultValue = "") String userName,
			@RequestParam(value = "password", defaultValue = "") String password) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		/**
		 * 1.取得此用户名的用户
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userName", userName);
		List<Person> list = this.personRemote.searchPerson(params);
		Person person = list != null ? list.get(0) : null;
		if (person == null) {
			resultMap.put("code", "500");
			return resultMap;
		}
		/**
		 * 2.判断用户密码
		 */
		String pwd = person.getPassword();
		person.setPassword(password);
		super.entryptPassword(person, person.getSalt());
		logger.info(pwd + "||" + person.getPassword());
		if (pwd.equals(person.getPassword())) {
			resultMap.put("code", "200");
			resultMap.put("person", person);
		} else {
			resultMap.put("code", "500");
		}
		return resultMap;
	}
	
	public static  void main(String[] args) throws Exception {
		String barCode="6933859147791";
		String orderCode="3956720586509";
		String url=cainiao_url+"?stock=1&orderCode="+orderCode+"&barCode="+barCode;
		String result=HttpClientUtils.httpGet(url, null);
		
		System.err.println("test:"+result);
	}

}
