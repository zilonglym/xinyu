package com.graby.store.admin.web.shiporder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.eclipse.jdt.internal.core.Assert;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.util.CityJson;
import com.graby.store.admin.web.BaseController;
import com.graby.store.base.ReceiveStateEnums;
import com.graby.store.base.ServiceException;
import com.graby.store.entity.AuditRules;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ItemGroup;
import com.graby.store.entity.ItemGroupDetail;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderGroup;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.Trade;
import com.graby.store.entity.TradeOrder;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.remote.AuditAreaRemote;
import com.graby.store.remote.AuditRulesRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ExpressPriceRemote;
import com.graby.store.remote.ExpressRemote;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemGroupRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderCancelRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.SystemOperatorRecordRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.inventory.Accounts;
import com.taobao.api.ApiException;

/**
 * 订单管理相关模块
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/trade/")
public class AdminTradeController extends BaseController {
	
	public static final Logger logger = Logger.getLogger(AdminTradeController.class);
	
	private  String noAduitExpressCompany="29";//审单过程中不处理快递，只把快传递过来的快递覆盖过来
	@Autowired
	private UserRemote userRemote;

	@Autowired
	private TradeRemote tradeRemote;

	@Autowired
	private InventoryRemote inventoryRemote;

	@Autowired
	private ItemRemote itemRemote;

	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
	@Autowired
	private ShipOrderCancelRemote shipOrderCancelRemote;

	@Autowired
	private ExpressRemote expressRemote;
	
	@Autowired
	private CentroRemote centroRemote;
	
	@Autowired
	private AuditRulesRemote  auditRulesRemote;
	
	@Autowired
	private SystemItemRemote systemItemRemote;
	
	@Autowired
	private ExpressPriceRemote expressPriceRemote;
	
	@Autowired
	private SystemOperatorRecordRemote recordRemote;
	
	@Autowired
	private AuditAreaRemote auditAreaRemote;
	@Autowired
	private ItemGroupRemote itemGroupRemote;
	/**
	 * 去自动审核界面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "toAuditRuleswait", method = RequestMethod.GET)
	public String toAuditRuleswait(Model model)
			throws Exception {
		User user=BaseResource.getCurrentUser();
		List<User> users = userRemote.findAll(String.valueOf(user.getId()));
		
		model.addAttribute("users", users);
		Map<String, String> expressMap = expressRemote.getExpressMap();
		model.addAttribute("expressCompanys", expressMap);
		return "/admin/auditRulesWaits";
	}
	
	
	
	/**
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "toAuditAreaYUNDA/{type}", method = RequestMethod.GET)
	public String toAuditAreaYUNDA(@PathVariable("type") String type,HttpServletRequest request,Model model)
			throws Exception {
		
		String  ids  = request.getParameter("tradeIds");
		
		model.addAttribute("tradeIds",ids.substring(0,ids.length()-1));
		
		model.addAttribute("type",type);
		
		return "/admin/auditAreaWaitsYUNDA";
	}
	
	private String  formartAddress(Trade  trade){
		//重复出现省市区的商家：
		//天际   18       优益33   优比特 36   黑白调 55    知音   56
		String   userId = ""+trade.getUser().getId();
		String address = "";
		if("18".equals(userId) || "33".equals(userId) || "36".equals(userId)  || "55".equals(userId) || "56".equals(userId)){
			address  = trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress();
		}else{
			address  = trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress();
		}
		
		return address;
	}
	
	
	
	@RequestMapping(value = "ajax/auditAreaYUNDA/{type}")
	@ResponseBody
	public Map<String,Object> auditAreaYUNDA(@PathVariable("type") String type,@RequestParam(value = "tradeIds", defaultValue = "0") long[] tradeIds
		) throws Exception {
		
		if(!("YUNDA411353".equals(type)  || "YUNDA".equals(type) || "YTO".equals(type)) ){
			logger.error("快递公司异常【"+type+"】");
			return null;
		}
		
		logger.debug("YUNDA自动审单Start");
		Map<String,Object>  resultMap  =new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("array", tradeIds);
		try {
			List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);	
			
			Map<String, Map<String,Object>>  mapList=  new HashMap<String, Map<String,Object>>();
			for(Trade  trade:  trades){
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id", trade.getId());
				map.put("address", formartAddress(trade));
				mapList.put(""+trade.getId(), map);
			}
			Map<String, Object> apiRet = auditAreaRemote.checkAddressApi(mapList);
			
			System.out.println("apiRet:"+apiRet);
			
			Map<String, Map<String,Object>> maps = (Map<String, Map<String, Object>>) apiRet.get("maps");
			
			resultMap.put("rows", buildYunDaAuditTradeList(trades,maps,type));
	        resultMap.put("total", trades.size());
		} catch (Exception e) {
			 logger.error(e.getMessage());
			 e.printStackTrace();
		}
		logger.debug("YUNDA自动审单End");
		return resultMap;
	}
	
	/**
	 *  韵达快递 构建自动审核  订单结构
	 * @param trades
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private List<Map<String,Object>> buildYunDaAuditTradeList(List<Trade> trades,Map<String, Map<String,Object>> retMaps,String type) throws UnsupportedEncodingException{
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=0;trades!=null && i<trades.size();i++){
			Trade trade=trades.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			Map<String, Object> retMap = retMaps.get(""+trade.getId());
			System.out.println("retMaps:"+retMaps);
			System.out.println("retMap:"+retMap);
			if("1".equals(retMap.get("reach"))){
				try {
					map.put("msg", "送达");
					auditOneTrade(trade.getId(), type);
					map.put("audit", "成功");
					map.put("status", "1");
				} catch (Exception e) {
					map.put("audit", "联系管理员");
					map.put("status", "2");
					logger.error(e.getMessage());
				}
				
			}else if("0".equals(retMap.get("reach"))){
				map.put("status", "0");
				map.put("msg", "不到");
			}else{
				map.put("audit", "单据异常");
				map.put("msg", "不到");
			}
			
			map.put("val", trade.getId());
			map.put("shopName", trade.getUser().getShopName());
			map.put("payDate", sdf.format(trade.getPayTime()));
			map.put("tradeOrder", trade.getLgAgingType());
			String phone=((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+((trade.getReceiverPhone()==null)?"":","+trade.getReceiverPhone());
			map.put("phone",phone);
			map.put("address", formartAddress(trade));
			String items="";
			items=this.tradeRemote.getItemsByTrade(trade.getId().intValue());
			map.put("items", items);
			if (trade.getBuyerNick()!=null) {
				map.put("buyerNick", trade.getBuyerNick());
			}else {
				map.put("buyerNick", trade.getReceiverName());
			}
			DecimalFormat df=new DecimalFormat("######0.00"); 
			map.put("weight",(trade.getWeight()!=null)?df.format(trade.getWeight()):0.00);
			map.put("id", trade.getId());
			map.put("lgAging", trade.getLgAging());
			resultList.add(map);
		}
		return resultList;
	}
	
	
	/**
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "toAuditArea", method = RequestMethod.GET)
	public String toAuditArea(HttpServletRequest request,Model model)
			throws Exception {
		String  ids  = request.getParameter("tradeIds");
		model.addAttribute("tradeIds",ids.substring(0,ids.length()-1));
		return "/admin/auditAreaWaits";
	}
	
	
	
	
	@RequestMapping(value = "ajax/auditArea")
	@ResponseBody
	public Map<String,Object> auditArea(@RequestParam(value = "tradeIds", defaultValue = "0") long[] tradeIds
		) throws Exception {
		logger.debug("SF自动审单Start");
		Map<String,Object>  resultMap  =new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("array", tradeIds);
		try {
			List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);	
			resultMap.put("rows", buildSfAuditTradeList(trades));
	        resultMap.put("total", trades.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		logger.debug("SF自动审单End");
		return resultMap;
	}
	
	
	
	/**
	 *  顺丰快递 构建自动审核  订单结构
	 * @param trades
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private List<Map<String,Object>> buildSfAuditTradeList(List<Trade> trades) throws UnsupportedEncodingException{
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("trades.size:"+trades.size());
		for(int i=0;trades!=null && i<trades.size();i++){
			Trade trade=trades.get(i);
			Map<String, Object> checkArea = auditAreaRemote.checkArea(trade.getId());//顺风地址检查
			Map<String,Object> map=new HashMap<String, Object>();
			String msg  = ""+checkArea.get("msg");
			map.put("msg",msg);
			if(msg.indexOf("未开通")>=0){
				map.put("status", "0");
			}
//			System.out.println("msg:"+checkArea.get("msg"));
			if(Boolean.valueOf(""+checkArea.get("flag"))==true){
				try {
					auditOneTrade(trade.getId(), "SF");
					map.put("audit", "成功");
					map.put("status", "1");
				} catch (Exception e) {
					map.put("audit", "联系管理员");
					map.put("status", "2");
					logger.error(e.getMessage());
				}
			}else{
				map.put("audit", "单据异常");
			}
			map.put("val", trade.getId());
			map.put("shopName", trade.getUser().getShopName());
			map.put("payDate", sdf.format(trade.getPayTime()));
			map.put("tradeOrder", trade.getLgAgingType());
			String phone=((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+((trade.getReceiverPhone()==null)?"":","+trade.getReceiverPhone());
			map.put("phone",phone);
			map.put("address", trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			String items="";
			items=this.tradeRemote.getItemsByTrade(trade.getId().intValue());
			map.put("items", items);
			if (trade.getBuyerNick()!=null) {
				map.put("buyerNick", trade.getBuyerNick());
			}else {
				map.put("buyerNick", trade.getReceiverName());
			}
			DecimalFormat df=new DecimalFormat("######0.00"); 
			map.put("weight",(trade.getWeight()!=null)?df.format(trade.getWeight()):0.00);
//			map.put("buyerMessage",trade.getBuyerMessage());
//			map.put("sellerMessage",trade.getSellerMemo());
			map.put("id", trade.getId());
			map.put("lgAging", trade.getLgAging());
//			map.put("sellerFlag", trade.getSellerFlag());
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * 自动审核订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "auditRuleswait", method = RequestMethod.GET)
	public String auditRuleswait(HttpServletRequest request, Model model)
			throws Exception {
		
		String expressCompanyStr= request.getParameter("expressCompany");
		
		String  userStr = request.getParameter("userId");
		Map<String,Object>  param = new HashMap<String, Object>();
		param.put("userId", userStr);
		param.put("expressCompany", expressCompanyStr);
		
		List<AuditRules> auditRulesList = auditRulesRemote.getAuditRulesList(param);
		//快递规则存在
		String msg = "";
		if(auditRulesList!=null  &&auditRulesList.size()>0){
			param.clear();
			param.put("userId", userStr);
			int cuid=BaseResource.getCurrentCentroId();
			if(cuid!=1){
				param.put("cuid", BaseResource.getCurrentCentroId());
			}
			List<Trade> trades = tradeRemote.findWaitAuditTradesBy(param);
			logger.debug("自动审单开始:params:{"+param+"},tradeInfo:"+trades.size());
			//获取第一条数据   审核提交
			AuditRules auditRules = auditRulesList.get(0);
			

			String cpCode = auditRules.getExpressCompany();// 快递公司 编码

			String[] includes = auditRules.getIncludes().split(",");// 包括到达的省

			String[] exincludes = auditRules.getExincludes().split(",");// 不到达的地址
//			String temp="长安街道长安中路城中花园二栋一单元201室";
//			isIndexOf(temp, exincludes);
			double startWegiht = auditRules.getStartWegiht();// 起始重量

			double endWegiht = auditRules.getEndWegiht(); // 结束重量
			int  auditSize =  0 ;
			if (trades != null && trades.size() > 0) {
				for (int i = 0, size = trades.size(); i < size; i++) {
					Trade trade = trades.get(i);
					String receiverState = trade.getReceiverState();
					if(trade==null){
						msg=msg+"订单号【" + trade.getLgAgingType() + "】,有备注信息!<br>";
						continue;
					}
					if(StringUtils.isNotEmpty(trade.getSellerMemo())  || StringUtils.isNotEmpty(trade.getBuyerMessage()) || StringUtils.isNotEmpty(trade.getBuyerMemo())){
						msg=msg+"订单号【" + trade.getLgAgingType() + "】,有备注信息!<br>";
						continue;
					}
					if (!isIndexOf(receiverState,includes)){ // 如果省份不在
						msg = msg + "订单号【" + trade.getLgAgingType() + "】," + receiverState + "不在省快送范围之内<br>";
						continue;
					}

					String receiverDistrict =trade.getReceiverCity()+trade.getReceiverDistrict()+ trade.getReceiverAddress();
					if (isIndexOf(receiverDistrict,exincludes)){ // 如果地区在
						logger.debug("地址:"+receiverDistrict+"|"+trade.getLgAgingType());
						msg = msg + "订单号【" + trade.getLgAgingType() + "】," + receiverDistrict + "在" + receiverState
								+ "无网点范围之内<br>";
						continue;
					}
					trade=this.tradeRemote.getTrade(trade.getId());
					List<TradeOrder> orders = trade.getOrders();
					double weigth = 0;
					for (TradeOrder order : orders) {
						Long itemId = order.getItem().getId();
						if (itemId != null) {
							Item item = itemRemote.getItem(itemId);
							weigth = weigth + item.getWeight()*order.getNum();
						}
					}
					if (weigth < startWegiht || weigth > endWegiht) {
						msg = msg + "订单号【" + trade.getLgAgingType() + "】," + weigth + "公斤不在" + startWegiht + "-" + endWegiht
								+ "重量范围之内<br>";
						continue;
					}
					try {
						auditOneTrade(trade.getId(), cpCode);
					} catch (ServiceException e) {
						msg = msg + "订单号【" + trade.getLgAgingType() + "】,"+e.getMessage()+"<br>";
					} catch (Exception e) {
						msg = msg + "订单号【" + trade.getLgAgingType() + "】,审核异常:"+e.getMessage()+"<br>";
					}
					
					auditSize++;
				}
			}
			msg =  "自动审核通过【"+auditSize+ "】条;<br>"+msg;
		}else{
			msg = "该商家没有对应的审核规则";
			
		}
		
		User user=BaseResource.getCurrentUser();
		List<User> users = userRemote.findAll(String.valueOf(user.getId()));
		model.addAttribute("users", users);
		
		Map<String, String> expressMap = expressRemote.getExpressMap();
		model.addAttribute("expressCompanys", expressMap);
		model.addAttribute("msg", msg);
		return "/admin/auditRulesWaits";
	}

	
	private boolean isIndexOf(String index,String str[]){
		boolean  flag = false;
		for(int i =0, size =  str.length ; i<size;i++){
			if(index.indexOf(str[i])!=-1){
				flag =  true;
				break;
			}else{
				logger.info("");
			}
		}
		return flag;
	}
	
	private static boolean isIndexOf1(String index,String str[]){
		boolean  flag = false;
		for(int i =0, size =  str.length ; i<size;i++){
			if(index.indexOf(str[i])!=-1){
				
				flag =  true;
				break;
			}
		}
		return flag;
	}
	public static void main(String[] args) {
		String address="桥驿镇";
		String st="桥桥,桥桥桥,桥驿镇";
		String[] ary=st.split(",");
		System.err.println(isIndexOf1(address, ary));
	}
	
	/**
	 * 转向批量审单查询页面
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "waits/search", method = RequestMethod.GET)
	public String waitAuditsHead(Model model,HttpServletRequest request) throws Exception {
		/**
		 * 首页跳转审核页面传递的userId的获取
		 * */
		String userId=request.getParameter("userId");
		if (userId!=null) {
			model.addAttribute("userId", userId);
		}
		
		User user=BaseResource.getCurrentUser();
		List<User> users = userRemote.findAll(String.valueOf(user.getId()));
		model.addAttribute("users", users);

		CityJson cj = new CityJson();
		Map<String,Object> params=new HashMap<String,Object>();
		int cuid=BaseResource.getCurrentCentroId();
		if(cuid!=1){
			params.put("cuid", BaseResource.getCurrentCentroId());
		}		
//		List<Map<String, String>> citys = tradeRemote.findWaitAuditCitys(params);
//		if (CollectionUtils.isNotEmpty(citys)) {
//			for (Map<String, String> cityMap : citys) {
//				cj.putCity(cityMap.get("receiverState"), cityMap.get("receiverCity"));
//			}
//		}
//		model.addAttribute("cityJson", cj.getJson()==""?"{}":cj.getJson());

		model.addAttribute("stateList", ReceiveStateEnums.values());
//		Map<String, String> expressMap = expressRemote.getExpressMap();
//		model.addAttribute("expressCompanys", expressMap);
		String type="waybill";
 		List<SystemItem> itemList=this.systemItemRemote.findSystemItemByType(type);
 		model.addAttribute("itemList", itemList);
		return "/admin/tradeWaitSearch";
	}

	/***订单审核已改造 BEGIN****/
	/**
	 * 审单列表数据查询
	 * @param userId
	 * @param state
	 * @param city
	 * @param q
	 * @param others
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "ajax/waits")
	@ResponseBody
	public Map<String,Object> waitAuditsBatch(@RequestParam(value = "userId", defaultValue = "0") Long userId,
			@RequestParam(value = "state", defaultValue = "") String state,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "q", defaultValue = "") String q,
 			@RequestParam(value = "beigainTime") String startDate,
 		    @RequestParam(value = "lastTime") String endDate,
 		    @RequestParam(value = "company") String company,
			@RequestParam(value = "others", defaultValue = "0") String others,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "500") int rows) throws Exception {
		String weight_x=this.getString("weight_x");
		String weight=this.getString("weight");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", userId);
 		if (StringUtils.isNotEmpty(startDate) && !startDate.equals("null")) {
 			params.put("startDate", startDate);
 		}
 		if (StringUtils.isNotEmpty(endDate) && !endDate.equals("null")) {
 			params.put("endDate", endDate);
 		}
		if (StringUtils.isNotEmpty(state) && !state.equals("0")) {
			params.put("receiverState", state+"%");
		}
//		if (StringUtils.isNotEmpty(city) && !city.equals("请选择") && !city.equals("null")) {
//			params.put("receiverCity", city);
//		}
		if (StringUtils.isNotBlank(q)) {
			params.put("q", "%" + q + "%");
		}
		int cuid=BaseResource.getCurrentCentroId();
		if(cuid!=1){
			params.put("cuid", BaseResource.getCurrentCentroId());
		}
		if(others!=null && !others.equals("0")){
			params.put("others", others);
		}
		if(weight!=null && !weight.equals("0")){
			params.put("weight", weight);
			if(weight_x.equals("=")){
				params.put("weight_x", "1");
			}else if(weight_x.equals(">=")){
				params.put("weight_x", "2");
			}else if(weight_x.equals("<=")){
				params.put("weight_x", "3");
			}
			
		}
		//System.err.println(params);
		//long t=new Date().getTime();
		List<Trade> trades=null;
		if (company!=null) {
			params.put("company", company);
			trades = tradeRemote.findWaitAuditTradesByLgAging(params);	
		}else {
			trades = tradeRemote.findWaitAuditTradesBy(params);	
		}
		//System.err.println("findWaitAuditTradesBy"+(new Date().getTime()-t));
		resultMap.put("rows", buildAuditTradeList(trades));
		//System.err.println("buildAuditTradeList:"+(new Date().getTime()-t));
        resultMap.put("total", trades.size());
        resultMap.put("userId", userId);
        resultMap.put("q", q);
		return resultMap;
	//	return "/admin/tradeWaitsDetail";
	}
	
	/**
	 * 构建审单列表表格数据
	 * @param trades
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private List<Map<String,Object>> buildAuditTradeList(List<Trade> trades) throws UnsupportedEncodingException{
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;trades!=null && i<trades.size();i++){
			Trade trade=trades.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("val", trade.getId());
			map.put("shopName", trade.getUser().getShopName());
			map.put("payDate", sdf.format(trade.getPayTime()));
			map.put("tradeOrder", trade.getLgAgingType());
			if (trade.getBuyerNick()!=null) {
				map.put("buyerNick", trade.getBuyerNick());
			}else {
				map.put("buyerNick", trade.getReceiverName());
			}
			String phone=((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+","+((trade.getReceiverPhone()==null)?"":trade.getReceiverPhone());
			map.put("phone",phone);
			map.put("address", trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			String items="";
			items=this.tradeRemote.getItemsByTrade(trade.getId().intValue());
			map.put("items", items);
//			System.err.println(items);
			DecimalFormat df=new DecimalFormat("######0.00"); 
			map.put("weight",(trade.getWeight()!=null)?df.format(trade.getWeight()):0.00);
			map.put("buyerMessage",trade.getBuyerMessage());
			map.put("sellerMessage",trade.getSellerMemo());
			map.put("id", trade.getId());
			map.put("lgAging", trade.getLgAging());
			map.put("sellerFlag", trade.getSellerFlag());	
			if (trade.getLgAging()!=null) {
				ShipOrder order=this.shipOrderRemote.getShipOrder(Long.parseLong(trade.getLgAging()));
				if (order!=null) {
					map.put("logisticsCode", order.getLogisticsCode());
				}
			}
			resultList.add(map);
		}
		return resultList;
	}
	
	@RequestMapping ("/toSelect/setExpressCompany" )
    public String expressSecList(Model model,HttpServletRequest request){
//        List<Map<String,Object>> expressSecList = new ArrayList<Map<String,Object>>();
//        String type="waybill";
//        List<SystemItem> systemItems=this.systemItemRemote.findSystemItemByType(type);
//        model.addAttribute("companys",systemItems);
//        System.err.println(expressSecList);
        return "/admin/companySelect";
   }

	@RequestMapping ("/company/save" )
	@ResponseBody
    public Map<String,Object> companySave(HttpServletRequest request){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String company=object.getString("company");	
		String id=object.getString("id");
		Trade trade=this.tradeRemote.getTrade(Long.parseLong(id));
		trade.setSellerFlag(company);
		params.put("id",trade.getId());
		params.put("sellerFlag",company);
//		System.err.println(params);
		this.tradeRemote.updateTrade(params);
		resultMap.put("ret", "success");
		return resultMap;
   }
	
	/**
	 * 批量审核订单
	 * @param tradeIds
	 * @param expressCompany
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "mkships")
	@ResponseBody
	public Map<String,Object> batchMkship(@RequestParam("tradeIds") Long[] tradeIds,
			@RequestParam(value = "expressCompany", defaultValue = "-1") String expressCompany) {
		Map<String,Object> resultMap=new HashMap<String, Object>();
		logger.debug("批量审单开始[tradeId:"+tradeIds+"]");
		try{
			for (Long tradeId :tradeIds ) {
				Trade trade = tradeRemote.getTrade(tradeId);
				//如果联系电话为空，则不审通过
				if(trade!=null && trade.getReceiverMobile()!=null){
					auditOneTrade(tradeId, expressCompany);
				}
			}
			resultMap.put("ret", 1);
		}catch(Exception e){
			resultMap.put("ret", 0);
			e.printStackTrace();
		}
		logger.debug("批量审单结束");
		return resultMap;
	}
	
	
	/***订单审核已改造 END****/
	/**
	 * 审核订单页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "audit/{id}", method = RequestMethod.GET)
	public String audit(@PathVariable("id") Long id, Model model) throws Exception {
		Trade trade = tradeRemote.getTrade(id);
		List<TradeOrder> orders = trade.getOrders();
		for (TradeOrder order : orders) {
			// 放置库存信息， 目前只支持单库存，如未来支持多库存这里要做改造
			Long itemId = order.getItem().getId();
			if (itemId == null) {
				order.setStockNum(-1);
			} else {
				// 已关联的设置库存
				long stockNum = inventoryRemote.getValue(1L, itemId, Accounts.CODE_SALEABLE);
				order.setStockNum(stockNum);
				Item item = itemRemote.getItem(itemId);
				order.setItem(item);
			}
		}
		model.addAttribute("trade", trade);
		Map<String, String> expressMap = expressRemote.getExpressMap();
		model.addAttribute("expressCompanys", expressMap);
		return "/admin/tradeAudit";
	}

	/**
	 * 删除订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "delete/{id}", method = RequestMethod.GET)
	public String deleteTrade(@PathVariable(value = "id") Long tradeId) {
		int userId=BaseResource.getCurrentUser().getId().intValue();
		tradeRemote.deleteTrade(tradeId,userId);
		return "redirect:/trade/waits";
	}

	/**
	 * 删除订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "reset/{id}", method = RequestMethod.GET)
	public String resetTrade(@PathVariable(value = "id") Long tradeId) {
		tradeRemote.reset(tradeId);
		return "redirect:/trade/unfinish";
	}
	 
	/**
	 * 查询被拆分订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "splited", method = RequestMethod.GET)
	public String splited(Model model) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cuid", BaseResource.getCurrentCentroId());
		List<Trade> trades = tradeRemote.findSplitedTrades(params);
		model.addAttribute("trades", trades);
		return "/admin/tradeSplited";
	}

	/**
	 * 查询被拆分订单
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "merge/{hash}", method = RequestMethod.GET)
	public String merge(@PathVariable(value = "hash") String mergeHash, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hash", mergeHash);
		params.put("cuid", BaseResource.getCurrentCentroId());
		List<Trade> trades = tradeRemote.findWaitAuditTradesBy(params);
		model.addAttribute("trades", trades);
		return "/admin/tradeMerge";
	}

	@RequestMapping(value = "merge")
	public String doMerge(@RequestParam("tradeIds") Long[] tradeIds, Model model) {
		Assert.isTrue(tradeIds != null && tradeIds.length>=2);
		tradeRemote.mergeTrade(tradeIds);
		return "/admin/success";
	}

	/**
	 * 拆分订单
	 * 
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "split/{tradeId}/{orderId}", method = RequestMethod.GET)
	public String splitTrade(@PathVariable(value = "tradeId") Long tradeId,
			@PathVariable(value = "orderId") Long orderId) {
		tradeRemote.splitTrade(tradeId, orderId);
		return "redirect:/trade/audit/" + tradeId;
	}

	/**
	 * 单个修改收件人信息保存提交。
	 */
	@RequestMapping(value = "mkship", method = RequestMethod.POST)
	public String mkship(@RequestParam("tradeId") Long tradeId,
			@RequestParam(value = "expressCompany", defaultValue = "-1") String expressCompany, Model model,HttpServletRequest request) {
		
		String receiverName=request.getParameter("receiverName");
		String buyerNick=request.getParameter("buyerNick");
		String mobile=request.getParameter("mobile");
		String phone=request.getParameter("phone");
		String zip=request.getParameter("zip");
		String state=request.getParameter("state");
		String city=request.getParameter("city");
		String district=request.getParameter("district");
		String address=request.getParameter("address");
		String sellerMemo=request.getParameter("sellerMemo");
 		Map<String, Object> params=new HashMap<String,Object>();
 		params.put("id", tradeId);
 		params.put("receiverName", receiverName);
 		params.put("buyerNick", buyerNick);
 		params.put("mobile", mobile);
 		params.put("phone", phone);
 		params.put("zip", zip);
 		params.put("state", state);
 		params.put("city", city);
 		params.put("district", district);
 		params.put("address", address);
 		params.put("sellerMemo", sellerMemo);
 	
 		//更新修改后的交易订单
 		tradeRemote.updateTrade(params);
 		
		ShipOrder sendOrder = auditOneTrade(tradeId, expressCompany);
	
		//更新发货单
		shipOrderRemote.updateShipOrderByTradeId(params);
		
		
		
		model.addAttribute("sendOrder", sendOrder);
		return "redirect:/trade/waits?userId=" + sendOrder.getCreateUser().getId();
	}

	/**
	 * 审核通过，创建出库单。
	 * */
	private ShipOrder auditOneTrade(Long tradeId, String expressCompany) {
		
		Map<String,Object> params=new HashMap<String, Object>();
		Date time=new Date();
		ShipOrder sendOrder = tradeRemote.createSendShipOrderByTradeId(tradeId);
		if(sendOrder==null){
			sendOrder=this.shipOrderRemote.getSendShipOrderByTradeId(tradeId);
		}
		String userId="";
		if(sendOrder!=null && sendOrder.getCreateUser()!=null){
			userId=String.valueOf(sendOrder.getCreateUser().getId());
		}else{
			userId="0";
		}
		if(noAduitExpressCompany.indexOf(userId)!=-1){
			expressCompany=sendOrder.getLogisticsCode();
		}
		Trade trade=this.tradeRemote.getTrade(tradeId);
		
		//如果trade的lgAging字段为空，则分配生成的shipOrder的id给这个字段（主要是订单类型为非奇门的）		
		if (trade.getLgAging()==null) {
			String lgAging = String.valueOf(sendOrder.getId());
			params.put("lgAging", lgAging);
			params.put("id", tradeId);
			this.tradeRemote.updateTrade(params);
			params.clear();
		}
		
		Double weight=trade.getWeight()!=null?trade.getWeight():0.11f;
		sendOrder.setTotalWeight(weight);
		params.put("weight", weight);
		params.put("id", sendOrder.getId());
		params.put("expressCompany", expressCompany);	
		/**
		 * 订单套餐处理
		 */
		if(sendOrder.getCreateUser().getId().intValue()==13){
			this.buildShipOrderItemGroup(sendOrder);
		}
		if(expressCompany!=null && expressCompany.equals("YUNDA411353")){
			params.put("expressCode", "411353");
		}else if(expressCompany!=null && expressCompany.equals("YUNDA")){
			params.put("expressCode", "411106");
		}else if(expressCompany!=null && expressCompany.equals("HTKY11")){
			params.put("expressCode", "HTKY11");
		}
		this.shipOrderRemote.updateShipOrder(params); 
		/**
		 * 写操作记录
		 */
		SystemOperatorRecord record=new SystemOperatorRecord();
		record.setDescription("审单["+sendOrder.getOrderno()+"|"+sendOrder.getId()+"]["+sendOrder.getReceiverName()+"]");
		record.setTime(new Date());
		record.setUser(sendOrder.getCreateUser().getId().intValue());
		record.setOperator(BaseResource.getCurrentUser().getId().intValue());
		record.setOperatorModel(OperatorModel.TRADE_AUDIT);
		this.recordRemote.insert(record);
	//	distOrder(sendOrder.getId());
		logger.debug("logger.debug 审单用时:"+(new Date().getTime()-time.getTime()));
		return sendOrder;
	}

	/**
	 * 套餐处理
	 * @param order
	 */
	private void buildShipOrderItemGroup(ShipOrder order) {
		StringBuffer buffer = new StringBuffer();
		Date date=new Date();
		List<ShipOrderDetail> itemList=this.shipOrderRemote.getShipOrderDetailByOrderId(order.getId());
		order.setDetails(itemList);
		Map<String, Object> itemMap = new HashMap<String, Object>();
		buildItemListToMap(itemMap, itemList);
		Map<String, Object> objMap=null;
		List<ShipOrderGroup> orderList=new ArrayList<ShipOrderGroup>();
		List<ItemGroup> groupList = this.itemGroupRemote.getGroupByShip(order);
		if (groupList != null && groupList.size() > 0) {
			for (int i = 0; groupList != null && i < groupList.size(); i++) {
				objMap = new HashMap<String, Object>();
				try {
					BeanUtils.copyProperties(objMap, itemMap);
					objMap.putAll(itemMap);
				} catch (Exception e) {
					e.printStackTrace();
				}
				ItemGroup group = groupList.get(i);
				List<ItemGroupDetail> detailList = group.getDetail();
				int index = 0;
				for (int j = 0; detailList != null && j < detailList.size(); j++) {
					ItemGroupDetail detail = detailList.get(j);
					/**
					 * 是否包含商品
					 */
					int include=0;
					if (objMap.get(String.valueOf(detail.getItem().getId())) != null) {
						include=1;
						// 匹配数量
						Long quantity = (Long) objMap.get(String.valueOf(detail.getItem().getId()));
						if (detail.getNum() <= quantity) {
							Long num = (Long) objMap.get(String.valueOf(detail.getItem().getId()));
							num = num - detail.getNum();
							/**
							 * 如果此商品与套餐计算之后为0，则删除掉此商品
							 */
							if (num == 0) {
								objMap.remove(String.valueOf(detail.getItem().getId()));
							}else{
								objMap.put(String.valueOf(detail.getItem().getId()), num);
							}
							index++;
						}else{
							include=0;
						}						
					}
					/**
					 * 判断是否列表的最后一个商品，如果是最后一个商品，则计算是否能进行再一次比对，如果可以则计时器还原重新开始，
					 * 如果不行则退出。 这里有一种情况可能会做多余的计算: 经过判断后套餐外的商品比套餐商品数量要多的话则会再计算机一次
					 */
					if (j == detailList.size()-1 ) {
						int k = objMap.values().size();
						if (k >= detailList.size() && include==1) {
							j = -1;
							include=0;
							continue;
						}
					}					
				}
				/**
				 * 一个套餐明细匹配完成，判断是否完成匹配. 如果相等则表明这轮套餐每个商品都匹配上，计算剩余商品的文本
				 */
				if (index>0 && index % detailList.size() == 0) {
					
					buffer.append(group.getName())
						  .append(index/detailList.size())
						  .append("[套] ");
					/**
					 * 修改订单的套餐标记
					 */
					order.setItemGroupStatus(ShipOrder.item_Group_Status);
					
					ShipOrderGroup orderGroup=new ShipOrderGroup();
					orderGroup.setCreateDate(new Date());
					orderGroup.setItemGroup(group);
					orderGroup.setOrder(order);
					orderGroup.setQuantity(index/detailList.size());
					orderGroup.setCreateDate(new Date());
					orderList.add(orderGroup);
				/*	int k = objMap.values().size();
					if (k >= detailList.size()) {
						continue;
					}*/
					
					/**
					 * 剩余商品匹配
					 */
					for (Map.Entry<String, Object> entry : objMap.entrySet()) { 
						String itemId=entry.getKey();
						Long num=(Long) entry.getValue();
						Item item=this.itemRemote.getItem(Long.valueOf(itemId));
						buffer.append(item.getTitle())
							  .append("[")
							  .append(num).append("件] ");
					}
					order.setItems(buffer.toString());
					logger.debug("套餐匹配时间长为:"+(new Date().getTime()-date.getTime()));
					break;
				}
				/**
				 * 退出匹配,
				 * 暂时如果匹配到了一个套餐后就不再对后续其它返回的套餐进行匹配
				 */
			}
		}
		if(StringUtils.isNotEmpty(order.getItemGroupStatus()) && order.getItemGroupStatus().equals(ShipOrder.item_Group_Status)){
			this.shipOrderRemote.updateShipOrder(order);
			this.itemGroupRemote.insertShipOrderGroupList(orderList);
		}
		
	}
	
	/**
	 * 订单明细封装为MAP
	 * @param itemMap
	 * @param itemList
	 */
	private void buildItemListToMap(Map<String, Object> itemMap, List<ShipOrderDetail> itemList) {
		for (int i = 0; itemList != null && i < itemList.size(); i++) {
			ShipOrderDetail item = itemList.get(i);
			itemMap.put(String.valueOf(item.getItem().getId()), item.getNum());
		}
	}
	
	

	/**
	 * 手工设置单个订单
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="submitDistOrder")
	public String setDistOrder(HttpServletRequest request,ModelMap model,HttpServletResponse response) throws IOException{
		String tradeId=request.getParameter("tradeId");
		String centroId=request.getParameter("centro");
		Centro centro=this.centroRemote.getCentroById(Integer.valueOf(centroId));
		ShipOrder order=this.shipOrderRemote.getShipOrder(Long.valueOf(tradeId));
		if(centro!=null){
			order.setCuid(centro.getId().intValue());
		}
		this.centroRemote.updateCentro(centro);
		response.getWriter().println("<script>window.close();</script>");
		return null;
	}
	/**
	 * 转向到选择仓库页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toDistOrder")
	public String toDistOrderPage(HttpServletRequest request,ModelMap model){
		String tradeId=request.getParameter("tradeId");
		List<Centro> list=this.centroRemote.findCentros();
		Trade trade=this.tradeRemote.getTrade(Long.valueOf(tradeId));
		model.put("trade", trade);	
		model.put("list", list);
		return "admin/tradeDistOrder";
	}
	
	/**
	 * 分配发货仓库,根据收货人的地址不同分配不同的发货仓库
	 * @param request
	 * @param model
	 * @return
	 */
	public void distOrder(Long tradeId){
		ShipOrder order=this.shipOrderRemote.getShipOrder(tradeId);
		
		if(order.getCuid()!=0){
			return;
		}
		/** 按收件人所在城市分仓**/
		// String city=order.getReceiverCity();
		// Centro centro=this.centroRemote.findCentrosByCity(city);
		
		/** 按收件人所在省份分仓**/
		String state=order.getReceiverState();
		Centro centro=this.centroRemote.findCentrosByCity(state);
		
		if(centro!=null){
			order.setCuid(centro.getId().intValue());
		}else{
			centro=new Centro();
			order.setCuid(BaseResource.getCurrentCentroId());
			centro.setId(Long.valueOf(order.getCuid()));
		}
		//更新数据库
	//	this.centroRemote.updateCentro(centro);
	///	this.shipOrderRemote.saveEntryOrder(order);
		this.shipOrderRemote.updateShipOrderCentro(centro.getId().intValue(), order.getId().intValue());
	}
	
	
	@RequestMapping(value = "special/waits")
	public String special() {
		return "/admin/tradeSpecialAudit";
	}

	/**
	 * 活动专场 审核所有待处理交易订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "mkship/all", method = RequestMethod.GET)
	public String auditAll() throws Exception {
		tradeRemote.createAllSendShipOrder(1L);
		return "redirect:/trade/waits";
	}

	/**
	 * 查询所有待处理出库单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "send/waits")
	public String sendWaits(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws Exception {
		List<User> users=userRemote.findUsers();
		String type="waybill";
		List<SystemItem> companys=this.systemItemRemote.findSystemItemByType(type);
		model.addAttribute("companys", companys);
		model.addAttribute("users", users);
//		System.err.println(users);
		return "/admin/sendOrderWaits";
	}
	
	@RequestMapping(value = "send/waits/listData")
	@ResponseBody
	public Map<String,Object> ListData(HttpServletRequest request,ModelMap model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
 		Map<String,Object> params=new HashMap<String,Object>();
 		String userId=request.getParameter("userId");
 		String cpCode=request.getParameter("cpCode");
 		String q=request.getParameter("q");
  		params.put("cuid", BaseResource.getCurrentCentroId());
 		params.put("userId",userId);
 		params.put("cpCode",cpCode);
 		params.put("q",q);
//  	System.err.println(params);
		List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderWaits(params);
		List<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
		for(ShipOrder order:sendOrders){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",order.getId());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("createDate",format.format(order.getCreateDate()));
			User user=this.userRemote.getUser(order.getCreateUser().getId());
			map.put("shopName",user.getShopName());
			map.put("items",order.getItems());
			map.put("buyerNick", order.getBuyerNick());
			map.put("receiverName", order.getReceiverName());
			map.put("phone", ((order.getReceiverMobile()==null)?"":order.getReceiverMobile())+","+((order.getReceiverPhone()==null)?"":order.getReceiverPhone()));
			map.put("addressInfo", order.getExpressCompany());
			resultList.add(map);
		}
		resultMap.put("rows", resultList);
		resultMap.put("total", resultList.size());
		return resultMap;
	}
	
	/**
	 * 手工设置运单
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/do/{id}", method = RequestMethod.GET)
	public String doSendOrderForm(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);	
		int cuid=sendOrder.getCuid();
		if (cuid==0) {
			Centro centro=centroRemote.getCentroById(1);
			model.addAttribute("centro",centro);
		}else {
			Centro centro=centroRemote.getCentroById(cuid);
			model.addAttribute("centro",centro);
		}
		model.addAttribute("order", sendOrder);
		model.addAttribute("expressCompanys", expressRemote.getExpressMap());
		return "/admin/sendOrderForm";
	}
	

	/**
	 * 查询所有待拣货出库单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "send/pickings")
	public String NewPickingList(HttpServletRequest request,ModelMap model){
		String type="waybill";
		List<SystemItem> waybills=systemItemRemote.findSystemItemByType(type);
		model.addAttribute("waybills", waybills);
 	
		List<User> users=userRemote.findUsers();
 		model.addAttribute("users", users);
		return "/admin/sendOrderPickings";
	}

	@RequestMapping(value = "send/pickings/listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		if (rows==10) {
			rows=500;
		}
		
		Map<String,Object> params = new HashMap<String,Object>();
		String userId = request.getParameter("userId");
		String cpCode = request.getParameter("cpCode");
		params.put("company", cpCode);
		params.put("userId", userId);
		params.put("status",OrderStatusEnums.WMS_PRINT);
		params.put("source", "import");
		int centroId = BaseResource.getCurrentCentroId();
		params.put("cuid", centroId);
		if(centroId == 0){
			centroId = 1;
		}
		Map<String,Object> result = new HashMap<String,Object>();
		List<ShipOrder> orders = this.shipOrderRemote.findeSendOrderByList(params,page,rows);
		int total = this.shipOrderRemote.getSendOrderCount(params);
		result.put("rows", buildOrderData(orders));
		result.put("page", page);
		result.put("total", total);
		return result;
	}
	
	private List<Map<String, Object>> buildOrderData(List<ShipOrder> orders) {
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results=new ArrayList<Map<String,Object>>();
		for(ShipOrder order:orders){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id", order.getId());
			map.put("createDate", sf.format(order.getLastUpdateDate()));
			User user=this.userRemote.getUser(order.getCreateUser().getId());
			map.put("shopName", (user==null?"":user.getShopName()));
			map.put("name", order.getReceiverName());
			map.put("company", order.getExpressCompany());
			map.put("orderno", order.getExpressOrderno());
			map.put("items", order.getItems());
			results.add(map);
		}
		return results;
	}

	
	/**
	 * 重置拣货单为运单打印状态
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "send/express")
	public String reExpress(@RequestParam(value = "ids", defaultValue = "") Long[] ids) {
		shipOrderRemote.reExpressShipOrder(ids);
		return "redirect:/trade/send/pickings";
	}

	/**
	 * 拣货单打印(PDF)
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "send/pick/report")
	public ModelAndView pickReport(@RequestParam(value = "ids", defaultValue = "") Long[] ids,
			@RequestParam(value = "format", defaultValue = "xls") String format,
			@RequestParam(value = "type", defaultValue = "minPickReport") String type) {
		Map<String, Object> model = new HashMap<String, Object>();		
 		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		String date=df.format(new Date());
 		model.put("date", date);
// 		System.err.println("type:"+type);
		if (type.equals("minPickReport")) {
			String[] idsL  =new String [ids.length];
			for(int  i  = 0, size  =  ids.length ;i<size ; i++){
				idsL[i]   = ""+ ids[i];
			}
			format="pdf";
			model.put("data",tradeListData(this.tradeRemote.findTradeListByLgAging(idsL)));
			model.put("format", format);
		}else {
			List<Map<String, Object>> findSendOrdersGroup = this.shipOrderRemote.findSendOrdersGroup(ids);
			model.put("data",findSendOrdersGroup);
			model.put("format", format);
		}
		return new ModelAndView(type, model);
	}
	
	
	
	
	/**
	 * PDF  拣货单 数据重组
	 * @param traderList
	 * @return
	 */
	private List<Map<String,Object>> tradeListData(List<Trade> traderList){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=1;
		for(Trade  trade   :  traderList){
			Map<String,Object> map=new HashMap<String, Object>();
			ShipOrder  order  = shipOrderRemote.getShipOrder(new Long(trade.getLgAging()));
			map.put("id", trade.getId());
			map.put("expressCompanyName", order.getExpressCompany());
			map.put("expressOrderno", order.getExpressOrderno());
			
			//计算合计金额， 数量
			List<TradeOrder> orders = trade.getOrders();
			long totalNum  =  0;
			double totalPrice  =  0.00;
			for(TradeOrder  tradeOrder  :  orders){
				totalNum  =  totalNum  +tradeOrder.getNum();
				    double  priceTemp=  0.00;
				try {
					priceTemp  = new Double(tradeOrder.getAdjustFee());
				} catch (Exception e) {
					logger.debug("trade.getId:"+trade.getId()+"转换金钱失败，为0 计算");
				}
				totalPrice  = totalPrice  + priceTemp;
			}
			map.put("totalNum", totalNum);
			map.put("totalPrice", totalPrice);
			map.put("details", orders);
			map.put("remark", (order.getRemark()==null)?"":order.getRemark());
			map.put("buyerNick", order.getBuyerNick()==null?"":" ( "+order.getBuyerNick()+" )");
			map.put("shopName", order.getShopName());
			map.put("createDate",sf.format(new Date()));
			map.put("sellerMemo", (order.getSellerMemo()==null)?"":order.getSellerMemo());
			map.put("sellerPhone", order.getSellerPhone());
			map.put("sellerMobile", order.getSellerMobile());
			map.put("buyerMemo", (order.getBuyerMemo()==null)?"":order.getBuyerMemo());
			map.put("buyerMessage", (order.getBuyerMessage()==null)?"":order.getBuyerMessage());
			map.put("receiverName", order.getReceiverName());
			map.put("receiverMobile", (order.getReceiverMobile()==null)?"":order.getReceiverMobile());
			map.put("receiverPhone", (order.getReceiverPhone()==null)?"":order.getReceiverPhone());
			map.put("receiverAddressDetail", order.getReceiverAddressDetail());
			map.put("rownum",i++);
			resultList.add(map);
		}
		return resultList;
	}
	
	
	private List<Map<String,Object>>ShipOrderListData(List<ShipOrder> orderList){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		int i=1;
		for(ShipOrder shipOrder:orderList){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", shipOrder.getId());
			map.put("expressCompanyName", shipOrder.getExpressCompany());
			map.put("expressOrderno", shipOrder.getExpressOrderno());
			map.put("details", shipOrder.getDetails());
			map.put("remark", (shipOrder.getRemark()==null)?"":shipOrder.getRemark());
			map.put("buyerNick", shipOrder.getBuyerNick());
			map.put("shopName", shipOrder.getShopName());
			map.put("createDate",sf.format(new Date()));
			map.put("sellerMemo", (shipOrder.getSellerMemo()==null)?"":shipOrder.getSellerMemo());
			map.put("sellerPhone", shipOrder.getSellerPhone());
			map.put("sellerMobile", shipOrder.getSellerMobile());
			map.put("buyerMemo", (shipOrder.getBuyerMemo()==null)?"":shipOrder.getBuyerMemo());
			map.put("buyerMessage", (shipOrder.getBuyerMessage()==null)?"":shipOrder.getBuyerMessage());
			map.put("receiverName", shipOrder.getReceiverName());
			map.put("receiverMobile", (shipOrder.getReceiverMobile()==null)?"":shipOrder.getReceiverMobile());
			map.put("receiverPhone", (shipOrder.getReceiverPhone()==null)?"":shipOrder.getReceiverPhone());
			map.put("totalNum", shipOrder.getTotalnum());
			map.put("receiverAddressDetail", shipOrder.getReceiverAddressDetail());
			map.put("rownum",i++);
//			int size=shipOrder.getDetails().size()*105+300;
//			map.put("size",size);
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * 订单已拣货，审核通过提交到系统。（批量 不建议）
	 * 
	 * @param ids
	 * @return
	 * @throws NumberFormatException
	 * @throws Exception
	 */
	@RequestMapping(value = "send/submits")
	@ResponseBody
	public Map<String, Object> submits(@RequestParam(value = "ids", defaultValue = "") Long[] ids,HttpServletRequest request,ModelMap model)
			throws Exception {
//		System.err.println("ids:"+ids);
		Map<String, Object> result = new HashMap<String, Object>();
		shipOrderRemote.submits(ids);
		result.put("ret", "success");
		return result;
	}

	@RequestMapping(value = "ship/audit")
	public String auditForm() {
		return "admin/shipAuditForm";
	}

	@RequestMapping(value = "ship/audit/ajax")
	public String auditOrder(@RequestParam(value = "q", defaultValue = "") String q, Model model) {
		List<ShipOrder> orders = shipOrderRemote.findSendOrderByQ(q);
		List<Entry> entrys = new ArrayList<Entry>();
		for (ShipOrder shipOrder : orders) {	
			Entry entry = new Entry();
			Long id=shipOrder.getId();
			List<ShipOrderDetail> details=shipOrderRemote.getShipOrderDetailByOrderId(id);
			for(ShipOrderDetail detail:details){
				Long itemId=detail.getItem().getId();
				Item item=itemRemote.getItem(itemId);
				detail.setItem(item);
			}
			shipOrder.setDetails(details);
			Trade tarde = tradeRemote.getTrade(shipOrder.getTradeId());
			int cuid=tarde.getCuid();
			if (cuid==0) {
				Centro centro=centroRemote.getCentroById(1);
				tarde.setCentro(centro);
			}else{
				Centro centro=centroRemote.getCentroById(cuid);
				tarde.setCentro(centro);
			}
  			
			entry.setOrder(shipOrder);
			entry.setTrade(tarde);
			entrys.add(entry);
		}
		model.addAttribute("entrys", entrys);
		return "admin/shipAuditOrder";
	}

	@RequestMapping(value = "ship/audit/done")
	public String auditdone() {
		return "admin/shipAuditDone";
	}

	/**
	 * (仓库方)提交出库单，等待用户签收。
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "send/submit", method = RequestMethod.POST)
	public String submitOrder(ShipOrder order, Model model) throws Exception {
		shipOrderRemote.submitSendOrder(order);
		return "redirect:/trade/send/waits";
	}

	/**
	 * 等待用户签收订单列表
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/waits")
	public String signWaits(@RequestParam(value = "q", defaultValue = "") String q, Model model) {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cuid", BaseResource.getCurrentCentroId());
		List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderSignWaits(params);
		model.addAttribute("orders", sendOrders);
		return "/admin/signWaits";
	}

	/**
	 * 用户签收页面
	 * 
	 * @param orderId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "send/sign/{id}", method = RequestMethod.GET)
	public String signSendOrder(@PathVariable("id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.getShipOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "/admin/signForm";
	}

	/**
	 * 点击用户签收
	 * 
	 * @param order
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "sign/submit/{id}", method = RequestMethod.GET)
	public String submitSign(@PathVariable(value = "id") Long orderId, Model model) {
		ShipOrder sendOrder = shipOrderRemote.signSendOrder(orderId);
		model.addAttribute("order", sendOrder);
		return "redirect:/trade/sign/waits";
	}

	/**
	 * 查询所有未关闭订单
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "unfinish", method = RequestMethod.GET)
	public String unfinish(@RequestParam(value = "page", defaultValue = "1") int page, Model model) throws Exception {
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("cuid", BaseResource.getCurrentCentroId());
		params.put("page", page);
		params.put("size", 20);
		Page<Trade> trades = tradeRemote.findUnfinishedTrades(page, 20,params);
		model.addAttribute("trades", trades);
		return "/admin/tradeUnfinishs";
	}

	public class Entry {
		private Trade trade;
		private ShipOrder order;

		public Trade getTrade() {
			return trade;
		}

		public ShipOrder getOrder() {
			return order;
		}

		public void setTrade(Trade trade) {
			this.trade = trade;
		}

		public void setOrder(ShipOrder order) {
			this.order = order;
		}
	}
	
	/**
	 * 待处理订单列表（代发订单处理）
	 * @param model
	 * @return string
	 * @throws Exception
	 */
	@RequestMapping(value="waits")
	public String waitAudits(ModelMap model){
		List<User> users = this.userRemote.findUsers();
		model.addAttribute("users", users);
		return "admin/tradeWaits";
	}
	
	/**
	 * 待处理订单列表数据填充（代发订单处理）
	 * @param page
	 * @param params
	 * @param rows
	 * @return resultMap
	 * @throws Exception
	 */
	
	@RequestMapping(value="waits/listData")
	@ResponseBody
	public Map<String,Object> waitAuditsData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String q=request.getParameter("q");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId",userId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("q", q);
//		params.put("cuid", BaseResource.getCurrentCentroId());
//		int total=this.tradeRemote.getTotalResult(params);
		List<Trade> trades = this.tradeRemote.findWaitAuditTradesBy(params);
		int total=trades.size();
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(Trade trade:trades){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("shopName",trade.getUser().getShopName());
			map.put("id",trade.getId());
			map.put("createDate",sf.format(trade.getPayTime()));
			map.put("name",trade.getReceiverName());
			map.put("phone",((trade.getReceiverMobile()==null)?"":trade.getReceiverMobile())+" "+((trade.getReceiverPhone()==null)?"":trade.getReceiverPhone()));
			map.put("address",trade.getReceiverState()+trade.getReceiverCity()+trade.getReceiverDistrict()+trade.getReceiverAddress());
			map.put("remark",((trade.getBuyerMemo()==null)?"":trade.getBuyerMemo())+((trade.getBuyerMessage()==null)?"":trade.getBuyerMessage())+((trade.getSellerMemo()==null)?"":trade.getSellerMemo()));
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("total",total);
		resultMap.put("rows",resultList);
		return resultMap;
	}
	
	/**
	 * 待处理订单收件人信息修改（代发订单处理）
	 * @param model
	 * @param request
	 * @return string
	 * @throws Exception
	 */
	@RequestMapping(value="waits/f7Edit")
	public String TradeEdit(HttpServletRequest request,ModelMap model){
		String id=request.getParameter("id");
		Trade trade=this.tradeRemote.getTrade(Long.parseLong(id));
		Long userId=trade.getUser().getId();
		User user=this.userRemote.getUser(userId);
		trade.setUser(user);
		model.put("trade",trade);
		return "admin/tradeEdit";
	}
	
	/**
	 * 待处理订单收件人信息修改完成保存提交（代发订单处理）
	 * @param params
	 * @param request
	 * @return resultMap
	 * @throws JSONException
	 */
	@RequestMapping(value="waits/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request,Map<String, Object> params) throws JSONException{
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id=object.getString("id");
		String receiverName=object.getString("receiverName");
		String mobile=object.getString("receiverMobile");
		String phone=object.getString("receiverPhone");
		String state=object.getString("receiverState");
		String city=object.getString("receiverCity");
		String district=object.getString("receiverDistrict");
		String address=object.getString("receiverAddress");
		String sellerMemo=object.getString("sellerMemo");
		params.put("address",address);
		params.put("receiverName",receiverName);
		params.put("mobile",mobile);
		params.put("phone",phone);
		params.put("state",state);
		params.put("city",city);
		params.put("district",district);
		params.put("sellerMemo",sellerMemo);
		params.put("id",id);
		this.tradeRemote.updateTrade(params);
		Trade trade=this.tradeRemote.getTrade(Long.parseLong(id));
		ShipOrder order=this.shipOrderRemote.getSendShipOrderByTradeId(trade.getId());
		if (order!=null) {
			order.setReceiverAddress(address);
			order.setReceiverCity(city);
			order.setReceiverDistrict(district);
			order.setReceiverState(state);
			order.setReceiverMobile(mobile);
			order.setReceiverName(receiverName);
			order.setReceiverPhone(phone);
			order.setSellerMemo(sellerMemo);
			this.shipOrderRemote.updateShipOrderById(order);
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", "success");
		return resultMap;
	}
	
	/**
	 * 订单反审
	 * @modify 2017-08-24
	 * @author fufangjue
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "ajax/resetAudit")
	@ResponseBody
	public Map<String,Object> resetAudit(@RequestParam(value = "tradeId") Long tradeId){
		Map<String, Object> retMap = new HashMap<String, Object>();
		ShipOrder shipOrder = this.shipOrderRemote.getSendShipOrderByTradeId(tradeId);
		String status = shipOrder.getStatus();
		/**
		 * 已作废和已取消的订单不可反审再次操作
		 * */
		if ("WMS_DELETE".equals(status)||"WMS_CANCEL".equals(status)) {		
			retMap.put("code", 200);
			retMap.put("msg", "已作废订单不可反审！");
		}else {
			retMap = tradeRemote.reSetAudit(tradeId, Long.valueOf(BaseResource.getCurrentCentroId()), BaseResource.getCurrentUser().getId(),BaseResource.getCurrentUser().getId());
		}
		return retMap;
	}
	
	
	/**
	 * 订单批量反审
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "ajax/resetAuditBatch")
	@ResponseBody
	public Map<String,Object> resetAuditBatch(){
		logger.debug("批量反审开始!");
		String tradeIds=this.getString("ids");
		String[] ary=tradeIds.split(",");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		for(int i=0;i<ary.length;i++){
			long tradeId=Long.valueOf(ary[i]);	
			tradeRemote.reSetAudit(tradeId, Long.valueOf(BaseResource.getCurrentCentroId()), BaseResource.getCurrentUser().getId(),BaseResource.getCurrentUser().getId());	
		}
		resultMap.put("code", 200);
		logger.debug("批量反审结束!");
		return resultMap;
	}
	
	/**
	 * 快速拆单
	 * @param tradeId
	 * @return
	 */
	@RequestMapping(value = "ajax/batchSplit")
	@ResponseBody
	public Map<String,Object> batchSplit(@RequestParam("tradeIds") String ids,
			@RequestParam(value = "type", defaultValue = "line") String type,
			@RequestParam(value = "num", defaultValue = "1") Long num
			){
		logger.debug("batchSplit:"+ids);
		String[] tradeIds=ids.split(",");
		Map<String,Object> resultMap=new HashMap<String, Object>();
		try {
			for(int i = 0  , size =  tradeIds.length;i<size;i++){
				if(tradeIds[i]!=null){
					shipOrderRemote.splitShipOrderOperation(type, Long.valueOf(tradeIds[i]), num);
				}
			}
			resultMap.put("ret", 1);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("ret", 0);
		}
		logger.debug("batchSplit"+resultMap);
		return resultMap;
	}
	
	/**
	 * 取消订单列表页面
	 * @param
	 * @return
	 * */
	@RequestMapping(value="Cancel/list")
	public String CancelTrade(HttpServletRequest request,ModelMap model){
		return "shipOrder/ShipOrdeCancelList";
	}
	
	/**
	 * 取消订单列表页面数据(分页显示)
	 * @param  orderCode
	 * @param  startDate
	 * @param  endDate
	 * @param  page
	 * @param  rows
	 * @return
	 * */
	@RequestMapping(value="Cancel/listData")
	@ResponseBody
	public Map<String, Object> CancelTradeListData(HttpServletRequest request,ModelMap model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		if (rows==10) {
			rows=100;
		}
		String orderCode = request.getParameter("orderCode");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderCode);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		List<ShipOrderCancel> cancelOrders = this.shipOrderCancelRemote.getShipOrderCancelByList(params,page,rows);
		int total = this.shipOrderCancelRemote.selectByCount(params);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("page", page);
		retMap.put("total", total);
		retMap.put("rows", this.buildCancelOrderData(cancelOrders));
		return retMap;
	}
	
	private List<Map<String, Object>> buildCancelOrderData(List<ShipOrderCancel> cancelOrders) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
		for(ShipOrderCancel shipOrderCancel:cancelOrders){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderCode", shipOrderCancel.getOrdercode()==null?"":shipOrderCancel.getOrdercode());
			String createTime = shipOrderCancel.getCreatetime();
			map.put("createDate", createTime);			
			if (shipOrderCancel.getUserid()!=null&&shipOrderCancel.getUserid()!="") {
				User user = this.userRemote.getUser(Long.valueOf(shipOrderCancel.getUserid()));
				if (user!=null) {
					map.put("userName", user.getShopName()==null?"":user.getShopName());
				}
			}		
			if (shipOrderCancel.getOrderid()!=null&&shipOrderCancel.getOrderid()!="") {
				ShipOrder shipOrder = this.shipOrderRemote.getShipOrder(Long.valueOf(shipOrderCancel.getOrderid()));
				if (shipOrder!=null) {
					map.put("name", shipOrder.getReceiverName());
				}
			}		
			resultList.add(map);
		}
		return resultList;
	}


	/**
	 * 作废订单列表页面
	 * @param
	 * @return
	 * */
	@RequestMapping(value="toSplitOrder")
	public String toSplitOrder(HttpServletRequest request,ModelMap model){
		return "admin/splitOrder";
	}
	
	
	
	@RequestMapping(value="ajax/shipOrderdata")
	@ResponseBody
	public Map<String, Object> shipOrderdataList(HttpServletRequest request,ModelMap model,
			@RequestParam(value = "orderId", defaultValue = "0") long orderId){
		System.err.println("id:"+orderId);
		List<ShipOrderDetail> detailList = shipOrderRemote.getShipOrderDetailByOrderId(orderId);
		
		Map<String, Object> resultMap=new HashMap<String, Object>();
// 		Map<String,Object> params=new HashMap<String,Object>();
		List<Map<String, Object>> resultList=new ArrayList<Map<String, Object>>();
//		List<TradeOrder> fetchTradeOrders = tradeRemote.fetchTradeOrders(tradeId);
		for(ShipOrderDetail orderDetail:detailList){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",orderDetail.getId());
			Long id = orderDetail.getItem().getId();
			Item item = itemRemote.getItem(id);
			map.put("title",item.getTitle());
			map.put("num",orderDetail.getNum());
			resultList.add(map);
		}
		resultMap.put("rows", resultList);
		resultMap.put("total", resultList.size());
		
		return resultMap;
	}
}
