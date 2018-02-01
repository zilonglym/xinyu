package com.graby.store.admin.finance;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.graby.store.entity.Centro;
import com.graby.store.entity.CheckOut;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.Item;
import com.graby.store.entity.Person;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.CheckRemote;
import com.graby.store.remote.ExpressPriceRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.UserRemote;


/**
 * 报表记录列表
 * */
@Controller
@RequestMapping(value="record")
public class RecordController {
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private SystemItemRemote sysRemote;
	
	@Autowired 
	private ExpressPriceRemote expressPriceRemote;
	
	@Autowired
	private CheckRemote  checkRemote;
	
	@Autowired
	private CentroRemote centroRemote;
	
	@Autowired
	private ShipOrderRemote orderRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private PersonRemote personRemote;
	
/****************************扫码出库BEIGAIN***************************************************************/
	/**
	 * 扫码出库明细
	 * @param model
	 * @return
	 * */
	@RequestMapping(value="checkout/list")
	public String CheckOutList(ModelMap model){
		List<User> userList=userRemote.findUsers();
		model.put("users",userList);
		String type="waybill";
		List<SystemItem> expressList=sysRemote.findSystemItemByType(type);
		model.put("expressList",expressList);
		return "/finance/checkOutList";
	}
	
	/**
	 * 去除出库的快递单号重复
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="checkout/build")
	@ResponseBody
	public Map<String,Object> buildCheckOut(HttpServletRequest request,ModelMap model){
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		int result=this.checkRemote.buildCheckOut(startDate, endDate);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", 1);
		resultMap.put("msg", result);
		return resultMap;
	}
	
	
	/**
	 * 扫码出库明细数据显示
	 * @param page
	 * @param rows
	 * @param request
	 * @return resultMap
	 * */
	@RequestMapping(value="checkout/listData")
	@ResponseBody
	public Map<String,Object> CheckOutListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		Map<String,Object> params=new HashMap<String, Object>();
		String userId=request.getParameter("userId");
		String sysId=request.getParameter("sysId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
 		String status=request.getParameter("status");
		String q=request.getParameter("q");
		if (sysId!=null&&sysId!="") {
			SystemItem systemItem=this.sysRemote.findSystemItem(Integer.valueOf(sysId));
			params.put("expressCompany",systemItem.getValue());
		}
		params.put("userId",userId);
		params.put("q", q);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("status",status);
//		System.err.println(params);
		List<CheckOut> checkOuts=checkRemote.findCheckOutByPage(page, rows, params);
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(CheckOut checkOut:checkOuts){
			String orderNo=checkOut.getOrderCode();
			ShipOrder shipOrder=this.orderRemote.findShipOrderByExpressOrderno(orderNo);
			Person person=this.personRemote.findPersonById(checkOut.getPersonId());
			if (shipOrder!=null) {
				Map<String,Object> map=ShipOrderListData(checkOut);
				if (person!=null) {
					map.put("operator",person.getName());
				}else {
					map.put("operator","");
				}
				resultList.add(map);
			}else {
				Map<String,Object> map=CheckOutListData(checkOut);
				if (person!=null) {
					map.put("operator",person.getName());
				}else {
					map.put("operator","");
				}
				resultList.add(map);
			}
		}
		int total=checkRemote.getTotalResult(params);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",total);
		return resultMap;
	}
	
	/**
	 * shipOrder没有找到
	 * 扫码出库记录数据重组
	 * @param checkOut
	 * @return map
	 * */
	private Map<String,Object> CheckOutListData(CheckOut checkOut){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String,Object> map=new HashMap<String, Object>();
 			map.put("expressCompany",checkOut.getCpCompany());
			map.put("barCode",checkOut.getBarCode());
			map.put("expressOrderNo",checkOut.getOrderCode());
			map.put("itemName",checkOut.getItemName());
			map.put("items","该订单系统没有数据");
			map.put("num",checkOut.getNum());
			if (checkOut.getUserId()!=null&&checkOut.getUserId()!=0) {
				User user=userRemote.getUser(checkOut.getUserId());
				if (user!=null) {
					map.put("user",user.getShopName());		
				}else{
					map.put("user","出库失败");
				}
			}else {
				map.put("user","出库失败");
			}		
			if (checkOut.getCentroId()!=null&&checkOut.getCentroId()!=0) {
				Centro centro=centroRemote.findCentroById(checkOut.getCentroId());
				if (centro!=null) {
					map.put("centro",centro.getName());	
				}else {
					map.put("centro","湘潭高新仓");	
				}				
			}else {
				map.put("centro","湘潭高新仓");	
			}
			if (checkOut.getCreateDate()!=null) {
				map.put("createDate",sf.format(checkOut.getCreateDate()));
			}
			if (checkOut.getStatus().equals("SUCCESS_TRADE")||checkOut.getStatus().equals("SUCCESS_GOODS")||checkOut.getStatus().equals("SUCCESS")) {
				map.put("status","成功");
			}else{
				map.put("status","失败");
			}
			map.put("msg",checkOut.getMsg());
			map.put("weight",checkOut.getWeight());
		return map;
	}
	
	/**
	 * shipOrder存在
	 * 扫码出库记录数据重组
	 * @param checkOut
	 * @return map
	 * */
	private Map<String,Object> ShipOrderListData(CheckOut checkOut){
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> map=new HashMap<String, Object>();
 		ShipOrder shipOrder=this.orderRemote.findShipOrderByExpressOrderno(checkOut.getOrderCode());
 		List<ShipOrderDetail> details=this.orderRemote.getShipOrderDetailByOrderId(shipOrder.getId());
 		Double weight=WeightCount(details);
 		if (checkOut.getState()==null) {
 			checkOut.setState(shipOrder.getReceiverState());
 			checkOut.setUserId(shipOrder.getCreateUser().getId());
 			checkOut.setOrderId(shipOrder.getId());
 		 	checkOut.setWeight(weight);
 		 	this.checkRemote.updateCheckOut(checkOut);	
		}
 		map.put("num",checkOut.getNum());
 		map.put("state",shipOrder.getReceiverState());		
 		map.put("id", checkOut.getId());
 		if(checkOut.getCreateDate()!=null){
 			map.put("createDate", sf.format(checkOut.getCreateDate()));
 		}
		User user=this.userRemote.getUser(shipOrder.getCreateUser().getId());
 		map.put("user", user.getShopName());
 		map.put("expressCompany",checkOut.getCpCompany());
 		map.put("expressOrderNo",checkOut.getOrderCode());
 		map.put("barCode", checkOut.getBarCode());
 		map.put("items",shipOrder.getItems());
 		map.put("itemName",checkOut.getItemName());
 		Centro centro=this.centroRemote.findCentroById(shipOrder.getCentroId());
 		map.put("centro",centro.getName());
 		if (checkOut.getStatus().equals("SUCCESS_TRADE")||checkOut.getStatus().equals("SUCCESS_GOODS")||checkOut.getStatus().equals("SUCCESS")) {
			map.put("status","成功");
		}else{
			map.put("status","失败");
		}
 		map.put("msg",checkOut.getMsg());
 		
		map.put("weight",weight);
		map.put("state",shipOrder.getReceiverState());		
 		return map;
	}
	
	/**
	 * checkout总重量计算
	 * @param details
	 * @return weight
	 * */
	private Double WeightCount(List<ShipOrderDetail> details){
		Double weight=0.0;
		for(ShipOrderDetail detail:details){
			Item item=this.itemRemote.getItem(detail.getItem().getId());
			weight=weight+item.getPackageWeight()*detail.getNum();	
		}
		return weight;
	}

	
	
	/**
	 * 扫码出库统计
	 * @param model
	 * @return 
	 * */
	@RequestMapping(value="checkout/count/list")
	public String CheckOutCount(ModelMap model){
		List<User> userList=userRemote.findUsers();
		model.put("users",userList);
		String type="waybill";
		List<SystemItem> systemItems=sysRemote.findSystemItemByType(type);
		model.put("expressList",systemItems);
		return "finance/checkOutCount";
	}
	
	/**
	 * 扫码出库数据统计
	 * @param page
	 * @param rows
	 * @param request
	 * @return resultMap
	 * */
	@RequestMapping(value="checkout/count/listData")
	@ResponseBody
	public Map<String,Object> CheckOutCountData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String,Object> params=new HashMap<String, Object>();
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String sysId=request.getParameter("sysId");
		params.put("userId",userId);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		if (sysId!=null&&sysId!="") {
			SystemItem systemItem=this.sysRemote.findSystemItem(Integer.valueOf(sysId));
			params.put("expressCompany",systemItem.getValue());
		}
		List<Map<String,Object>> list=checkRemote.findCheckOutByStatus(params);	
		for(int i=0;i<list.size();i++){
			Map<String,Object> map=new HashMap<String, Object>();
			Map<String,Object> objectMap=list.get(i);
			map.put("userId",objectMap.get("userId"));
			map.put("user",objectMap.get("shopName"));
			map.put("num",objectMap.get("num"));
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",resultList.size());
		return resultMap;
	} 
	
/****************************扫码出库END*******************************************************************/
	
	
	
	
//	/**
//	 * 导入页面
//	 * @param model
//	 * @return String
//	 * */
//	@RequestMapping(value="f7Parse")
//	public String parse(ModelMap model){
//		String type="LOGISTICS";
//		List<User> userList=this.userRemote.findUsers();
//		model.put("userList", userList);
//		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
//		model.put("expressList", systemItems);
//		return "finance/expressPriceParse";
//	}
	
//	/**
//	 * 导入数据处理
//	 * @param file
//	 * @param request
//	 * @return String
//	 * @exception FileNotFoundException, IOException, ParseException
//	 * */
//	@RequestMapping(value="upload")
//	public String ImportRecord(@RequestParam(value = "filename", required = true) MultipartFile file,HttpServletRequest request) throws FileNotFoundException, IOException, ParseException{
//		int num=0;
//		int sum=0;
//		String userId=request.getParameter("userId");
//		String sysId=request.getParameter("sysId");
//		//解析excel文件生成list
//		RecordExcelReader reader=new RecordExcelReader(file.getInputStream());
//		List<Record> records=reader.getRecords();
//		Map<String, Object> params=new HashMap<String, Object>();
//		User user=this.userRemote.getUser(Long.valueOf(userId));
//		SystemItem systemItem=this.sysRemote.findSystemItem(Integer.parseInt(sysId));
//		for(Record record:records){
//			//日期格式化
//			params.clear();
//			params.put("userId",Long.valueOf(userId));
//			params.put("code",systemItem.getValue());
//			params.put("area",record.getState());
//			//通过weight计算price
//			ExpressPrice expressPrice=this.expressPriceRemote.findExpressPriceByParam(params);
//			Double firstPrice=expressPrice.getFirstPrice();
//			Double initalPrice=expressPrice.getInitialPrice();
//			Double deliveryPrice=expressPrice.getDeliveryPrice();
//			Double otherPrice=expressPrice.getOtherPrice();
//			Double firstCost=expressPrice.getFirstCost();
//			Double initalCost=expressPrice.getInitialCost();
//			Double deliveryCost=expressPrice.getDeliveryCost();
//			Double otherCost=expressPrice.getOtherCost();
//			Double totalWeight=record.getWeight();
//			Double total=0.0;
//			Double b=totalWeight%1;
//			if (b==0.0) {			
//				total=Math.floor(totalWeight)-1;
//			}else {
//				total=Math.floor(totalWeight);	
//			}
//			//运费计算公式
//			Double price=total*initalPrice+firstPrice+otherPrice+deliveryPrice;	
//			Double cost=total*initalCost+firstCost+otherCost+deliveryCost;
//			record.setCost(cost);
//			record.setPrice(price);//总运费
//			record.setImportTime(new Date());
//			record.setExpressCompany(systemItem.getDescription());
//			record.setUserName(user.getShopName());
//			params.clear();
//			params.put("q",record.getExpressOrderNo());
//			int count=this.recordRemote.getTotalResult(params);
//			if (count==0) {
//				this.recordRemote.insert(record);
//				num=num+1;
//				System.err.println(record.getExpressOrderNo()+"导入成功!");
//			}else {
//				this.recordRemote.update(record);
//				sum=sum+1;
//				System.err.println(record.getExpressOrderNo()+"更新成功!");
//			}
//		}
//		System.err.println("成功导入记录"+num+"条,更新记录"+sum+"条!");
//		return "redirect:list"; 
//	}
	
//	/**
//	 * excel导入记录列表显示
//	 * @param model
//	 * @return string
//	 * */
//	@RequestMapping(value="list")
//	public String list(ModelMap model){
//		String type="LOGISTICS";
//		User user=BaseResource.getCurrentUser();
//		List<User> userList=this.userRemote.findUsers();
//		model.put("userList", userList);
//		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
//		model.put("expressList", systemItems);
//		return "finance/recordList";
//	}
	
//	/**
//	 * excel导入记录列表数据填充
//	 * @param page
//	 * @param rows
//	 * @param request
//	 * @return resultMap
//	 * */
//	@RequestMapping(value="listData")
//	@ResponseBody
//	public Map<String,Object> listData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
//		if (rows==10) {
//			rows=100;
//		}
//		Map<String,Object> params=new HashMap<String, Object>();
//		String userName=request.getParameter("userName");
//		String expressCompany=request.getParameter("expressName");
//		String beigainTime=request.getParameter("beigainTime");
//		String lastTime=request.getParameter("lastTime");
//		String q=request.getParameter("q");
//		params.put("userName",userName);
//		params.put("expressCompany", expressCompany);
//		params.put("beigainTime", beigainTime);
//		params.put("lastTime", lastTime);
//		params.put("q",q);
//		int total=this.recordRemote.getTotalResult(params);
//		List<Record> records=this.recordRemote.findRecords(params, page, rows);
//		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
//		for(Record record:records){
//			Map<String, Object> map=new HashMap<String, Object>();
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			map.put("createTime",sf.format(record.getCreateTime()));
//			map.put("userName",record.getUserName());
//			map.put("state", record.getState());
//			map.put("center", record.getCenter());
//			map.put("expressCompany", record.getExpressCompany());
//			map.put("expressOrderNo", record.getExpressOrderNo());
//			map.put("weight", record.getWeight());
//			map.put("price", record.getPrice());
// 			map.put("cost",record.getCost());
//			map.put("importTime",sf.format(record.getImportTime()));
//			resultList.add(map);
//		}
//		Map<String,Object> resultMap=new HashMap<String, Object>();
//		resultMap.put("page", page);
//		resultMap.put("rows",resultList);
//		resultMap.put("total", total);
//		return resultMap;
//	}
	
//	/**
//	 * 物流发货明细统计
//	 * @param model
//	 * @return string
//	 * */
//	@RequestMapping(value="countRecord")
//	public String countByExpressName(ModelMap model){
//		List<User> userList=this.userRemote.findUsers();
//		model.put("userList", userList);
//		return "finance/countRecord";
//	}
	
//	/**
//	 * 物流发货明细统计数据填充显示
//	 * @param request
//	 * @exception JSONException
//	 * @return result
//	 * */
//	@RequestMapping(value="countRecordData")
//	@ResponseBody
//	public Map<String,Object> countByExpressNameData(HttpServletRequest request) throws JSONException{
//		List<User> users=this.userRemote.findUsers();
//		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
//		Map<String,Object> params=new HashMap<String, Object>();
//		String userName=request.getParameter("userName");
//		String beigainTime=request.getParameter("beigainTime");
//		String lastTime=request.getParameter("lastTime");
//		params.put("beigainTime", beigainTime);
//		params.put("lastTime", lastTime);
//		if (userName==null||userName.equals("")) {
//			for(User user:users){
//				params.put("userName",user.getShopName());
//				List<Map<String, Object>> objectMap=this.recordRemote.getCountByExpressName(params);
//				Map<String, Object> resultMap=countReportListData(objectMap);
//				resultMap.put("userName",user.getShopName());							
//				resultList.add(resultMap);
//			}
//		}else {
//			params.put("userName",userName);
//			List<Map<String, Object>> objectMap=this.recordRemote.getCountByExpressName(params);
//			Map<String, Object> resultMap=countReportListData(objectMap);
//			resultMap.put("userName",userName);	
//			resultList.add(resultMap);
//		}
//		Map<String, Object> result=new HashMap<String, Object>();
//		result.put("rows", resultList);
//		result.put("total", resultList.size());
//		return result;
//	}
	
//	/**
//	 * 数据重组
//	 * @param list
//	 * @return list
//	 * */
//	private Map<String,Object> countReportListData(List<Map<String, Object>> objectMap){
//		Map<String, Object> resultMap=new HashMap<String, Object>();
//		Long num=0L;
//		Long sum=0L;
//		for(Map<String, Object> map:objectMap){
//			if (map.get("expressCompany").equals("韵达")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("yunda",num);
//			}else if (map.get("expressCompany").equals("圆通")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("yto",num);
//			}else if (map.get("expressCompany").equals("中通")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("zto",num);
//			}else if (map.get("expressCompany").equals("申通")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("sto",num);
//			}else if (map.get("expressCompany").equals("汇通")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("htky",num);
//			}else if (map.get("expressCompany").equals("国通")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("gto",num);
//			}else if (map.get("expressCompany").equals("EMS经济快递")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("ems",num);
//			}else if (map.get("expressCompany").equals("邮政小包")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("postb",num);
//			}else if (map.get("expressCompany").equals("顺丰")) {
//				num=(Long) map.get("num");
//				sum=sum+num;
//				resultMap.put("sf",num);
//			}
//			resultMap.put("sum",sum);
//		}
//		return resultMap;
//	}
	
//	/**
//	 * 各商家利润统计表
//	 * @param model
//	 * @return string
//	 * */
//	@RequestMapping(value="profit/list")
//	public String ProfitList(ModelMap model){
//		User user=BaseResource.getCurrentUser();
//		List<User> userList=this.userRemote.findUsers();
//		model.put("userList", userList);
//		return "finance/profitList";
//	}
	
//	/**
//	 * 各商家利润统计表数据填充
//	 * @param request
//	 * @return resultMap
//	 * */
//	@RequestMapping(value="profit/listData")
//	@ResponseBody
//	public Map<String,Object> ProfitListData(HttpServletRequest request){
//		List<User> users=this.userRemote.findUsers();
//		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
//		Map<String,Object> params=new HashMap<String, Object>();
//		String userName=request.getParameter("userName");
//		String beigainTime=request.getParameter("beigainTime");
//		String lastTime=request.getParameter("lastTime");
//		params.put("beigainTime", beigainTime);
//		params.put("lastTime", lastTime);
//		if (userName==null||userName.equals("0")) {
//			for(User user:users){
//				params.put("userName",user.getShopName());
//				List<Map<String, Object>> profitMap=this.recordRemote.getProfitByExpressName(params);
//				Map<String, Object> map=ProfitsListData(profitMap);
//				map.put("userName",user.getShopName());
//				List<Map<String, Object>> totalMap=this.recordRemote.getTotalProfitByExpressName(params);
//				for(Map<String,Object> tMap:totalMap){
//					if (tMap!=null) {
//						map.put("total_income",(Double)tMap.get("totalPrice"));
//						map.put("total_expend",(Double)tMap.get("totalCost"));
//						map.put("total_profits",(Double)tMap.get("totalPrice")-(Double)tMap.get("totalCost"));
//					}else {
//						map.put("total_income",0.0);
//						map.put("total_expend",0.0);
//						map.put("total_profits",0.0);
//					}
//					
//				}
//				resultList.add(map);
//			}
//		}else {
//			params.put("userName",userName);
//			List<Map<String, Object>> profitMap=this.recordRemote.getProfitByExpressName(params);
//			Map<String, Object> map=ProfitsListData(profitMap);
//			map.put("userName",userName);	
//			List<Map<String, Object>> totalMap=this.recordRemote.getTotalProfitByExpressName(params);
//			for(Map<String,Object> tMap:totalMap){
//				if (tMap!=null) {
//					map.put("total_income",(Double)tMap.get("totalPrice"));
//					map.put("total_expend",(Double)tMap.get("totalCost"));
//					map.put("total_profits",(Double)tMap.get("totalPrice")-(Double)tMap.get("totalCost"));
//				}else {
//					map.put("total_income",0.0);
//					map.put("total_expend",0.0);
//					map.put("total_profits",0.0);
//				}
//			}
//			resultList.add(map);					
//		}
//		Map<String, Object> resultMap=new HashMap<String, Object>();
//		resultMap.put("rows",resultList);
//		resultMap.put("total",resultList.size());
//		return resultMap;
//	}
	
//	/**
//	 * 数据重组
//	 * @param list
//	 * @param list
//	 * */
//	private Map<String,Object> ProfitsListData(List<Map<String, Object>> profitMap){
//		Map<String, Object> map=new HashMap<String, Object>();
//		for(Map<String,Object> pMap:profitMap){
//			if (pMap.get("expressCompany").equals("韵达")) {
//				map.put("yunda_income",(Double)pMap.get("price"));
//				map.put("yunda_expend",(Double)pMap.get("cost"));
//				map.put("yunda_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("圆通")) {
//				map.put("yto_income",(Double)pMap.get("price"));
//				map.put("yto_expend",(Double)pMap.get("cost"));
//				map.put("yto_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("中通")) {
//				map.put("zto_income",(Double)pMap.get("price"));
//				map.put("zto_expend",(Double)pMap.get("cost"));
//				map.put("zto_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("申通")) {
//				map.put("sto_income",(Double)pMap.get("price"));
//				map.put("sto_expend",(Double)pMap.get("cost"));
//				map.put("sto_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("汇通")) {
//				map.put("htky_income",(Double)pMap.get("price"));
//				map.put("htky_expend",(Double)pMap.get("cost"));
//				map.put("htky_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("国通")) {
//				map.put("gto_income",(Double)pMap.get("price"));
//				map.put("gto_expend",(Double)pMap.get("cost"));
//				map.put("gto_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("EMS经济快递")) {
//				map.put("ems_income",(Double)pMap.get("price"));
//				map.put("ems_expend",(Double)pMap.get("cost"));
//				map.put("ems_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("邮政小包")) {
//				map.put("postb_income",(Double)pMap.get("price"));
//				map.put("postb_expend",(Double)pMap.get("cost"));
//				map.put("postb_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}else if (pMap.get("expressCompany").equals("顺丰")) {
//				map.put("sf_income",(Double)pMap.get("price"));
//				map.put("sf_expend",(Double)pMap.get("cost"));
//				map.put("sf_profits",(Double)pMap.get("price")-(Double)pMap.get("cost"));
//			}
//		}
//		return map;
//	}
	
}
