package com.graby.store.admin.report;


import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.util.PoiExcelExport;
import com.graby.store.admin.util.SfAreaExcelReader;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Centro;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;
import com.graby.store.entity.Item;
import com.graby.store.entity.POIModel;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.Trade;
import com.graby.store.entity.User;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.CheckInventoryRemote;
import com.graby.store.remote.CheckRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.TradeRemote;
import com.graby.store.remote.UserRemote;



/**
 *报表控制类 
 * */
@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController{
	
	@Autowired
	private UserRemote userRemote;

	@Autowired
	private ReportRemote reportRemote;
	
	@Autowired
	private CentroRemote centroRemote;
	
	@Autowired
	private CheckInventoryRemote checkInventoryRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private CheckRemote checkRemote;
	
	@Autowired
	private SystemItemRemote sysRemote; 
	
	@Autowired
	private TradeRemote tradeRemote;
	
	/**
	 * 商品库存列表
	 *@param request
	 *@param model
	 *@return String	 
	 **/
	@RequestMapping(value="inventory")
	public String inventory(HttpServletRequest request,ModelMap model){
		List<User> users = userRemote.findAll(null);
		model.addAttribute("users", users);		
		return "admin/reportInventory";
	}
	
	/**
	 * 商品库存数据填充
	 *@param request
	 *@param page
	 *@param rows
	 *@return resultMap	 
	 **/
	@RequestMapping(value="inventory/listData")
	@ResponseBody
	public Map<String, Object> inventoryListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();		
		Map<String,Object> params=new HashMap<String, Object>();
		String userId=request.getParameter("userId");
		String title=request.getParameter("itemTitle");
		params.put("userId",userId);
		params.put("title",title);
		params.put("centroId",BaseResource.getCurrentCentroId());
		List<Map<String,Object>> results = reportRemote.getInventoryReportDetails(params,page,rows);
//		int total=this.reportRemote.getTotalResult(params);
		List<Map<String,Object>> objectList=reportRemote.getInventoryReport(params);
		for(Map<String,Object> objectMap:results){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("shopName",objectMap.get("shopname"));
			map.put("code",objectMap.get("code"));
			map.put("name",objectMap.get("name"));
			map.put("sku",objectMap.get("sku"));
			map.put("num",objectMap.get("num"));
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("total",objectList.size());
		resultMap.put("rows",resultList);
		return resultMap;
	}
	

	/**
	 * 库存明细单下载
	 * @param userId
	 * @param format
	 * @return ModelAndView
	 * */
	@RequestMapping(value = "/inventory/xls")
	public String inventoryReport(HttpServletRequest request,HttpServletResponse response) {
		String userId = request.getParameter("userId");
		String title = request.getParameter("title");
		Long centroId=(long) BaseResource.getCurrentCentroId();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("centroId", centroId);
		p.put("title", title);
		p.put("userId", userId);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		List<Map<String,Object>> results = reportRemote.getInventoryReport(p);
		List<POIModel> poiModels=new ArrayList<POIModel>();
		for(Map<String, Object> map:results){
			POIModel poiModel=new POIModel();
			poiModel.setM1(String.valueOf(map.get("shopname")));
			poiModel.setM2(String.valueOf(map.get("name")));
			poiModel.setM3(String.valueOf(map.get("code")));
			poiModel.setM4(String.valueOf(map.get("sku")));
			poiModel.setM5(String.valueOf(map.get("num")));
			poiModels.add(poiModel);
		}
		User user=this.userRemote.getUser(Long.valueOf(userId));
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//拼接Excel文件名称
		String filename=sdf.format(new Date())+user.getShopName()+"库存明细";
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,filename,"sheet1");
		//Excel文件填充内容属性
		String titleColumn[] = {"m1","m2","m3","m4","m5"};  
		//Excel文件填充内容列名
		String titleName[] = {"店铺名称","商品名称","商品编码","sku","库存数量"};  
		//Excel文件填充内容大小
		int titleSize[] = {20,20,20,20,20};  
		//调用PoiExcelExport导出Excel文件
		pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		return null;
	}
	
	/**
	 * 出入库记录列表
	 * @param request
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="inventoryRecord")
	public String inventoryRecord(HttpServletRequest request,ModelMap model){
		List<User> users = userRemote.findAll(null);
		model.addAttribute("users", users);		
		return "admin/reportInventoryRecord";
	}
	
	/**
	 * 出入库记录数据填充
	 *@param request
	 *@param page
	 *@param rows
	 *@return resultMap	 
	 * */
	@RequestMapping(value="inventoryRecord/listData")
	@ResponseBody
	public Map<String,Object> inventoryRecordListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String title=request.getParameter("itemTitle");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("title", title);
		params.put("centroId",BaseResource.getCurrentCentroId());
		if (startDate!=null&&endDate!=null&&startDate!=""&&endDate!="") {
			params.put("startDate",startDate);
			params.put("endDate",endDate);
		}else {
			params.put("startDate",sf.format(new Date()));
			params.put("endDate",sf.format(new Date()));
		}
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		int total=this.reportRemote.getInventoryRecordTotal(params);
		List<Map<String,Object>> results = this.reportRemote.getInventoryRecordReportByPages(params,page,rows);
		for(Map<String,Object> objectMap:results){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("shopName",objectMap.get("shopname"));
			map.put("code", objectMap.get("code"));
			map.put("name", objectMap.get("name"));
			map.put("barCode", objectMap.get("barCode"));
			map.put("sku", objectMap.get("sku"));
			map.put("centroName",objectMap.get("centroName"));
			map.put("anum", objectMap.get("anum"));
			map.put("bnum",objectMap.get("bnum"));
			map.put("num", objectMap.get("num"));
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",total);
		return resultMap;
	}
	
	/**
	 * 出入库明细导出
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param title
	 * @param format
	 * @return inventoryRecordReport
	 * */
	@RequestMapping(value="/inventoryRecord/xls")
	public ModelAndView inventoryRecordReport(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "title") String title,
			@RequestParam(value = "format", defaultValue = "xls") String format){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("title", title);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("centroId",BaseResource.getCurrentCentroId());
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> results = this.reportRemote.getInventoryRecordReport(params);
		for(Map<String,Object> objectMap:results){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("shopName",objectMap.get("shopname"));
			map.put("code", objectMap.get("code"));
			map.put("name", objectMap.get("name"));
			map.put("sku", objectMap.get("sku"));
			map.put("centroName",objectMap.get("centroName"));
			map.put("anum", objectMap.get("anum"));
			map.put("bnum",objectMap.get("bnum"));
			map.put("num", objectMap.get("num"));
			resultList.add(map);
		}
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("data", resultList);
		model.put("format", format);
		model.put("shopName", "");
		model.put("dateDesc", startDate + " ~ " + endDate);
		return new ModelAndView("inventoryRecordReport",model);
	}
	
	/**
	 * 发货统计
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value = "/ship")
	public String ship(Model model) {
		List<User> users = userRemote.findAll(null);
		model.addAttribute("users", users);
		
		String type = "waybill";
		List<SystemItem> companys = this.sysRemote.findSystemItemByType(type);
		model.addAttribute("companys", companys);
		
 		return "admin/reportShip";
	}
	
	/**
	 * 发货统计数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return resultMap
	 * */
	@RequestMapping(value="ship/listData")
	@ResponseBody
	public Map<String,Object> ShipListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String cpCode=request.getParameter("cpCode");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId",userId);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("cpCode",cpCode);
		List<Map<String,Object>> itemsNum = reportRemote.shipCount(params);
		List<Map<String, Object>> orderNum = reportRemote.orderCount(params);	
		List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<itemsNum.size();i++){
			Map<String, Object> itemsMap = itemsNum.get(i);
			for(int j=0;j<orderNum.size();j++){
				Map<String,Object> map=new HashMap<String, Object>();
				Map<String, Object> orderMap = orderNum.get(j);
				if (itemsMap.get("userId").equals(orderMap.get("userId"))) {
					String shopName=(String) itemsMap.get("shopName");
					Long num=(Long) orderMap.get("num");
					BigDecimal items=(BigDecimal) itemsMap.get("items");
					Long id=(Long) itemsMap.get("userId");
					map.put("userId", id);
					map.put("userName", shopName);
					map.put("num", num);
					map.put("items", items);
					resultList.add(map);
				}
			}
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("total",resultList.size());
		resultMap.put("rows",resultList);
		return resultMap;
	}
	
	/**
	 * 发货汇总单下载
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param format
	 * @return ModelAndView
	 * */
	@RequestMapping(value = "/ship/item/xls")
	public ModelAndView shipItem(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "format", defaultValue = "xls") String format) {		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDate);
		p.put("endDate", endDate);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		List<Map<String,Object>> results = reportRemote.sumUserSellouts(userId, startDate, endDate);
		Map<String, Object> model = new HashMap<String, Object>();
		List<Map<String,Object>> resultMap=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> objectMap:results){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("userName",objectMap.get("userName"));
			map.put("shopName",objectMap.get("shopName"));
			map.put("itemName",objectMap.get("itemName"));
			map.put("itemCode",objectMap.get("itemCode"));
			map.put("itemSku",objectMap.get("itemSku"));
			String barCode = (String) (objectMap.get("barCode1")==null?objectMap.get("barCode2"):objectMap.get("barCode1"));
			map.put("description",barCode);
			map.put("num",objectMap.get("num"));
			map.put("centroName",objectMap.get("centroName"));
			map.put("position",objectMap.get("position"));
			resultMap.add(map);
		}
		model.put("data", resultMap);
		model.put("format", format);
		model.put("shopName", "");
		model.put("dateDesc", startDate + " ~ " + endDate);
		return new ModelAndView("summaryReport", model); 
	}
	

	/**
	 * 发货明细单(POI)下载(单个商家)
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param cpCode
	 * @return 
	 * @throws IOException 
	 * */
	@RequestMapping(value = "/ship/xls")
	public String shipReport(	
			@RequestParam(value = "userId") long userId,
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "cpCode") String cpCode,
			@RequestParam(value = "endDate") String endDay,HttpServletResponse response) throws IOException {
		//当前导出商家ID
		User user=this.userRemote.getUser(userId);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		p.put("cpCode", cpCode);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);	
		//新建shipOrder数组存放新shipOrder对象
		List<POIModel> poiModels=new ArrayList<POIModel>();	
		List<ShipOrder> orders = reportRemote.findOrderSellout(p);
		for(ShipOrder shipOrder:orders){
			//新建一个shipOrder对象
			POIModel poiModel=new POIModel();
			Long tradeId=shipOrder.getTradeId();
			Trade trade=this.tradeRemote.getTrade(tradeId);
			//对新shipOrder对象进行赋值
			if (trade!=null) {
				//订单号
				poiModel.setM1(trade.getLgAgingType());
			}
	
			poiModel.setM2(user.getShopName());
			
			//时间
			poiModel.setM3(sf.format(shipOrder.getLastUpdateDate()));
			//昵称
			poiModel.setM4(shipOrder.getBuyerNick());
			//仓库备注
			String remark = "留言："+(shipOrder.getRemark()==null?"":shipOrder.getRemark())+" 卖家："+(shipOrder.getSellerMemo()==null?"":shipOrder.getSellerMemo())+(shipOrder.getSellerMessage()==null?"":shipOrder.getSellerMessage())+" 买家:"+(shipOrder.getBuyerMemo()==null?"":shipOrder.getBuyerMemo())+(shipOrder.getBuyerMessage()==null?"":shipOrder.getBuyerMessage());
			poiModel.setM5(remark);
			//物流公司
			poiModel.setM6(shipOrder.getExpressCompany());
			//物流单号
			poiModel.setM7(shipOrder.getExpressOrderno());
			//收件人
			poiModel.setM8(shipOrder.getReceiverName());
			//收件地址
			poiModel.setM9(shipOrder.getReceiverState()+shipOrder.getReceiverCity()+shipOrder.getReceiverDistrict()+shipOrder.getReceiverAddress());
			//联系方式
			String phone=(shipOrder.getReceiverMobile()==null?"":shipOrder.getReceiverMobile())+","+(shipOrder.getReceiverPhone()==null?"":shipOrder.getReceiverPhone());
			poiModel.setM0(phone);
			//重量
			if (shipOrder.getTotalWeight()!=null) {
				poiModel.setM10(String.valueOf(shipOrder.getTotalWeight()));
			}else {
				poiModel.setM10("0.00");
			}		
			//商品明细
			poiModel.setM11(shipOrder.getItems());
			//省
			poiModel.setM12(shipOrder.getReceiverState());
			
			//添加到新shipOrder数组
			poiModels.add(poiModel);
		}		
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//拼接Excel文件名称
//		String filename=desktopPath+sdf.format(new Date())+user.getShopName()+"发货明细.xls";
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,sdf.format(new Date())+user.getShopName()+"发货明细","sheet1");
		//Excel文件填充内容属性
		String titleColumn[] = {"m2","m3","m1","m4","m6","m7","m8","m0","m12","m9","m11","m10","m5"};  
        //Excel文件填充内容列名
		String titleName[] = {"店铺名称","更新时间","订单号","昵称","物流公司","物流单号","收件人","联系方式","省","收件地址","商品明细","重量","备注"};  
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20,20,20,20,20,20,20};  
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		return null; 
	}
	
	/**
	 * 发货明细单(POI)下载
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param cpCode
	 * @return 
	 * @throws IOException 
	 * */
	@RequestMapping(value = "/order/xls")
	public String shipOrderReport(	
			@RequestParam(value = "userId") long userId,
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "cpCode") String cpCode,
			@RequestParam(value = "endDate") String endDay,HttpServletResponse response) throws IOException {
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		p.put("cpCode", cpCode);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);	
		//新建shipOrder数组存放新shipOrder对象
		List<POIModel> poiModels=new ArrayList<POIModel>();	
		List<ShipOrder> orders = reportRemote.findOrderSellout(p);
		for(ShipOrder shipOrder:orders){
			//新建一个shipOrder对象
			POIModel poiModel=new POIModel();
			Long tradeId=shipOrder.getTradeId();
			Trade trade=this.tradeRemote.getTrade(tradeId);
			//对新shipOrder对象进行赋值
			if (trade!=null) {
				//订单号
				poiModel.setM1(trade.getLgAgingType());
			}
			
			User user = this.userRemote.getUser(shipOrder.getCreateUser().getId());	
			poiModel.setM2(user.getShopName());
			
			//时间
			poiModel.setM3(sf.format(shipOrder.getLastUpdateDate()));
			//昵称
			poiModel.setM4(shipOrder.getBuyerNick());
			
			//物流公司
			poiModel.setM6(shipOrder.getExpressCompany());
			//物流单号
			poiModel.setM7(shipOrder.getExpressOrderno());
			//收件人
			poiModel.setM8(shipOrder.getReceiverName());
			//收件地址
			poiModel.setM9(shipOrder.getReceiverState());
			
			poiModel.setM10(shipOrder.getReceiverCity());
			
			poiModel.setM11(shipOrder.getReceiverDistrict());
			
			poiModel.setM12(shipOrder.getReceiverAddress());
		
			//联系方式
			String phone=(shipOrder.getReceiverMobile()==null?"":shipOrder.getReceiverMobile())+","+(shipOrder.getReceiverPhone()==null?"":shipOrder.getReceiverPhone());
			poiModel.setM13(phone);
			//重量
			if (shipOrder.getTotalWeight()!=null) {
				poiModel.setM14(String.valueOf(shipOrder.getTotalWeight()));
			}else {
				poiModel.setM14("0.00");
			}		
			//商品明细
			poiModel.setM15(shipOrder.getItems());
		
			//添加到新shipOrder数组
			poiModels.add(poiModel);
		}		
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//拼接Excel文件名称
//		String filename=desktopPath+sdf.format(new Date())+user.getShopName()+"发货明细.xls";
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,sdf.format(new Date())+cpCode+"发货明细","sheet1");
		//Excel文件填充内容属性
		String titleColumn[] = {"m1","m2","m3","m6","m7","m4","m8","m13","m9","m10","m11","m12","m15","m14"};  
        //Excel文件填充内容列名
		String titleName[] = {"订单号","店铺名称","更新时间","物流公司","物流单号","昵称","收件人","联系方式","省","市","区","地址","商品明细","重量"};  
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20,20,20,20,20,20,20,20};  
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		return null; 
	}
	
	/**
	 * 库存盘点单明细xls导出
	 * @param id
	 * @param format
	 * @return 
	 * */
	@RequestMapping(value = "checkInventory/xls")
	public ModelAndView checkInventoryReport(			
			@RequestParam(value = "id") String id,
			@RequestParam(value = "format", defaultValue = "xls") String format) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", id);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		Map<String, Object> model = new HashMap<String, Object>();
		List<CheckInventoryDetail> details=checkInventoryRemote.checkInventoryDetailbyId(p);
		for(CheckInventoryDetail detail:details){
			Item item=itemRemote.getItem(detail.getItem().getId());
			User user=userRemote.getUser(item.getUserid());
			item.setShopUser(user);
			detail.setItem(item);
			Centro centro=centroRemote.findCentroById(detail.getCentro().getId());
			detail.setCentro(centro);
			CheckInventory checkInventory=checkInventoryRemote.getCheckInventoryById(Long.parseLong(id));
			detail.setCheckInventory(checkInventory);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.put("dateDesc",df.format(checkInventory.getCreateDate()));
		}
		model.put("data", details);
		model.put("format", format);
		return new ModelAndView("checkInventoryReport",model); 
	}
	
	
/*****************************扫码出库报表BEIGAIN****************************************************************/

	/**
	 * 扫描出库明细xls导出(POI)
	 * @param request
	 * @return 
	 * @throws Exception 
	 * */
	@RequestMapping(value = "checkout/xls")
	public String CheckOutDetailExport(HttpServletRequest request,HttpServletResponse response) throws ParseException{
		logger.debug("出库订单导出开始！");
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String,Object> params=new HashMap<String, Object>();
		String sysId=request.getParameter("sysId");
		String userId=request.getParameter("userId");
		String q=request.getParameter("q");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String status=request.getParameter("status");
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		params.put("q",q);
		params.put("userId",userId);
		params.put("status",status);
//		System.err.println(params);
		if (sysId!=null&&sysId!="") {
			SystemItem systemItem=this.sysRemote.findSystemItem(Integer.parseInt(sysId));
			params.put("company",systemItem.getValue());
		}
		List<POIModel> poiModels=new ArrayList<POIModel>();
		List<Map<String,Object>> checkouts=this.checkRemote.findCheckOutDetail(params);
		for(Map<String,Object> map:checkouts){
			POIModel poiModel=new POIModel();
			poiModel.setM1(String.valueOf(map.get("createDate")));
			poiModel.setM2(String.valueOf(map.get("shopName")));
			poiModel.setM3(String.valueOf(map.get("expressCompany")));
			poiModel.setM4(String.valueOf(map.get("expressOrderno")));
			poiModel.setM5(String.valueOf(map.get("receiverName")));
			poiModel.setM6(String.valueOf(map.get("buyerNick")));
			if (map.get("weight")!=null) {
				poiModel.setM7(String.valueOf(map.get("weight")));
			} 
			poiModel.setM8(String.valueOf(map.get("items")));
			poiModel.setM9(String.valueOf(map.get("receiverState")));	
			poiModels.add(poiModel);
		}
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//拼接Excel文件名称
		String filename=sdf.format(new Date())+"出库明细";
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,filename,"sheet1");
		//Excel文件填充内容属性
		String titleColumn[] = {"m1","m2","m3","m4","m6","m5","m9","m8","m7"};  
		//Excel文件填充内容列名
		String titleName[] = {"更新时间","店铺名称","物流公司","物流单号","昵称","收件人","收件地址","商品明细","重量"};  
		//Excel文件填充内容大小
		int titleSize[] = {20,20,20,20,20,20,20,20,20};  
		//调用PoiExcelExport导出Excel文件
		pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		logger.debug("出库订单导出结束！");
		return null;
	}
	
	/**
	 * 未出库订单明细导出(POI)
	 * @param request
	 * @return
	 * */
	@RequestMapping(value="checkout/sum/xls")
	public String checkOutCountByTrade(HttpServletRequest request,HttpServletResponse response){
		Date start = new Date(); 
		logger.debug("未出库订单导出开始！");
		Map<String,Object> params=new HashMap<String,Object>();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		List<Map<String,Object>> listMap=checkRemote.sumTradeOuts(params);
		List<POIModel> poiModels=new ArrayList<POIModel>();
		for(int i=0;i<listMap.size();i++){
			POIModel poiModel=new POIModel();
			poiModel.setM1(String.valueOf(listMap.get(i).get("createDate")));
			poiModel.setM2(String.valueOf(listMap.get(i).get("shopName")));
			poiModel.setM3(String.valueOf(listMap.get(i).get("expressCompany")));
			poiModel.setM4(String.valueOf(listMap.get(i).get("orderNo")));
			poiModel.setM5(String.valueOf(listMap.get(i).get("items")));
			poiModels.add(poiModel);
		}
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		//拼接Excel文件名称
		String filename=sdf.format(new Date())+"未出库明细";
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,filename,"sheet1");
		//Excel文件填充内容属性
		String titleColumn[] = {"m1","m2","m3","m4","m5"};  
		//Excel文件填充内容列名
		String titleName[] = {"更新时间","店铺名称","物流公司","物流单号","商品明细"};  
		//Excel文件填充内容大小
		int titleSize[] = {20,20,20,20,20};  
		//调用PoiExcelExport导出Excel文件
		logger.debug("导出方法开始执行！");
		pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		logger.debug("导出方法执行完毕！");
		Date end = new Date();
		logger.debug("未出库订单导出结束！耗时："+(end.getTime() - start.getTime()));
		return null;
	}
	
	/**
	 * 按快递汇总出库
	 * @param format
	 * @param request
	 * @return
	 * */
	@RequestMapping(value="checkout/count/express/xls")
	public ModelAndView CheckOutCountByExpress(@RequestParam(defaultValue="xls")String format,HttpServletRequest request){
		Map<String,Object> params=new HashMap<String,Object>();
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		params.put("userId",userId);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		List<Map<String,Object>> listMap=checkRemote.findCheckOutByExpress(params);
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> resultMap=new HashMap<String, Object>();
			resultMap.put("shopName",String.valueOf(listMap.get(i).get("shopName")));
			resultMap.put("expressCompany",String.valueOf(listMap.get(i).get("expressCompany")));
			resultMap.put("num",String.valueOf(listMap.get(i).get("num")));
			resultList.add(resultMap);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dateDesc", startDate+"~"+endDate);
		model.put("data",resultList);
		model.put("format",format);
		return new ModelAndView("checkOutByCompanyReport",model);
	}
	
	/**
	 * 按商品汇总出库
	 * @param format
	 * @param request
	 * @return
	 * */
	@RequestMapping(value="checkout/count/item/xls")
	public ModelAndView CheckOutCountByItem(@RequestParam(defaultValue="xls")String format,HttpServletRequest request){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		Map<String,Object> params=new HashMap<String,Object>();
		String userId=request.getParameter("userId");
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		params.put("userId",userId);
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		List<Map<String,Object>> listMap=checkRemote.findCheckOutByItem(params);
		for(int i=0;i<listMap.size();i++){
			Map<String,Object> resultMap=new HashMap<String, Object>();
			resultMap.put("shopName",String.valueOf(listMap.get(i).get("shopName")));
			resultMap.put("barCode",String.valueOf(listMap.get(i).get("barCode")));
			resultMap.put("itemName",String.valueOf(listMap.get(i).get("itemName")));
			resultMap.put("num",String.valueOf(listMap.get(i).get("num")));
			resultList.add(resultMap);
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("dateDesc", startDate+"~"+endDate);
		model.put("data",resultList);
		model.put("format",format);
		return new ModelAndView("checkOutByItemReport",model);
	}

}


