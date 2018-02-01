package com.xinyu.controller.others;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taobao.api.ApiException;
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.User;
import com.xinyu.service.system.AuditAreaService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.util.ERPOrderExcelReader;
import com.xinyu.util.OrderExcelReader;
import com.xinyu.util.SfAreaExcelReader;
import com.xinyu.model.system.enums.ERPEnums;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.model.trade.ShipOrder;

@Controller
@RequestMapping(value = "/import")
public class ImportController extends BaseController{
	
	@Autowired
	private  ShipOrderService  shipOrderService;
	
	@Autowired
	private  UserService  userService;
	
	@Autowired
	private  ItemService  itemService;
	
	@Autowired
	private  AuditAreaService   auditAreaService;
	
	/**
	 * 进入  ERP  订单  导入
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "toOrderFromERP", method = RequestMethod.GET)
	public String uploadFromERP(ModelMap model) throws ApiException {
		List<User> users = userService.getUserBySearch(null); //获得 所有 商户信息
		model.put("users", users);  
		
		model.put("erps", ERPEnums.values()); 
		
		model.put("count", 0);//系统可导入 ERP 系统 
		
		model.put("size", 0);
		
		return "admin/system/orderFromERPImport";
	}
	/**
	 * ERP  订单 数据导入  提交
	 * @param file
	 * @param model
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@RequestMapping(value = "order/uploadFromERP", method = RequestMethod.POST)
	public String uploadFromERP(HttpServletRequest request,@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
				throws FileNotFoundException, IOException {
		System.out.println("1111");
		ERPOrderExcelReader order  = new ERPOrderExcelReader(file.getInputStream());
		
		System.err.println("order:"+order);
		
		String erpKey  =  request.getParameter("erpKey");   //erp  类型 
		
		String  userId =  request.getParameter("userId");   //用户ID
		List<Map<String, Object>> paramsList = order.getOrder(erpKey); //封装 订单相信
		if(paramsList==null){   //无法获得 ERP 信息
			List<User> users = userService.getUserBySearch(null);  //获得 所有 商户信息
			model.put("users", users);  
			
			model.put("erps", ERPEnums.values());  //系统可导入 ERP 系统 
			
			return "admin/system/orderFromERPImport";
		}
		
		Map<String,Object>  params  = new HashMap<String,Object>();
		params.put("userId", userId);  
		params.put("orderList", paramsList);
		params.put("erp", erpKey);
//		params.put("centroId",BaseResource.getCurrentCentroId());  //当前仓库
		
		List<Map<String, Object>> ordrMap = shipOrderService.importERPOrders(params);
		int  count = 0  ;
		for(Map<String,Object> map  :  ordrMap){
			if((""+map.get("msg")).indexOf("成功")!=-1){
				count++;
			}
			
			System.err.println("retMap:" +  map);
		}
		List<User> users = userService.getUserBySearch(null);  //获得 所有 商户信息
		model.put("users", users);    
		
		model.put("erps", ERPEnums.values());  //系统可导入 ERP 系统 
		
		model.put("count", count);  //系统可导入  数量
		
		model.put("ordrMap", ordrMap);  //系统可导入 MSG
		
		model.put("size", 1);
		
		return "admin/system/orderFromERPImport";
	}
	
	@RequestMapping(value = "toImportRecord", method = RequestMethod.GET)
	public String toImportRecord(ModelMap model) throws ApiException {		
		
		return "admin/import/importRecordList";
	}
	
	
	@RequestMapping(value = "toLastImportRecord", method = RequestMethod.GET)
	public String toLastImportRecord(ModelMap model) throws ApiException {
		
		return "admin/import/lastImportRecordList";
	}
	
	/**
	 * 取数
	 * @return
	 */
	@RequestMapping(value="lastImportRecordListData")
	@ResponseBody
	public Map<String,Object> lastImportRecordListData(HttpServletRequest request,ModelMap model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "10000") int rows){
		Map<String,Object> resultMap=new HashMap<String, Object>();
//		if(rows==10){
//			rows=100;
//		}
//		Map<String,Object> params=new HashMap<String, Object>();
//		List<ImportRecord> findImportRecord = importRecordService.findLastBatchImportRecord();
//		resultMap.put("page",page);
//        resultMap.put("rows",findImportRecord);
//        resultMap.put("total", findImportRecord.size());
		return resultMap;
	}
	
	/**
	 * 取数
	 * @return
	 */
	@RequestMapping(value="importRecordListData")
	@ResponseBody
	public Map<String,Object> importRecordListData(HttpServletRequest request,ModelMap model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		Map<String,Object> resultMap=new HashMap<String, Object>();
//		if(rows==10){
//			rows=100;
//		}
//		Map<String,Object> params=new HashMap<String, Object>();
//		String startDate=request.getParameter("startDate");
//		String endDate=request.getParameter("endDate");
//		params.put("startDate",startDate);
//		params.put("endDate",endDate);
//		List<ImportRecord> findImportRecord = importRecordService.findImportRecord(page, rows, params);
//		resultMap.put("page",page);
//        resultMap.put("rows",findImportRecord);
//        resultMap.put("total", importRecordService.findImportRecordCount(params));
		return resultMap;
	}
	
	
	
	// 上传商品页面
	@RequestMapping(value = "toOrder", method = RequestMethod.GET)
	public String upload(ModelMap model) throws ApiException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemSource", SystemSourceEnums.IMPORT.getKey());
		params.put("status", OrderStatusEnum.WMS_PRINT);
		model.put("errorMsg", "");
		
		model.put("count",  shipOrderService.getTotal(params));
		
		return "admin/system/orderImport";
	}

	//订单导入 初始化
	@RequestMapping(value = "order/upload", method = RequestMethod.POST)
	public String uploadItems(@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
			throws Exception {
		
		logger.info("订单导入开始！");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemSource", SystemSourceEnums.IMPORT.getKey());
		params.put("status", OrderStatusEnum.WMS_PRINT);
		
		try{
			
			OrderExcelReader order = new OrderExcelReader(file.getInputStream());
			List<Map<String, Object>> list = order.getOrder();
			shipOrderService.importOrders(list);	
			
			model.put("count",  shipOrderService.getTotal(params));
			
			model.put("errorMsg", "");
			
		}catch(Exception e){
			
			logger.info("订单导入异常!"+e.getMessage());
			
			model.put("count", shipOrderService.getTotal(params));
			
			model.put("errorMsg", e.getMessage());
		}
		return "admin/system/orderImport";
	}
	
	@RequestMapping(value = "orderSub", method = RequestMethod.POST)
	public String orderSub(ModelMap model)   {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemSource", SystemSourceEnums.IMPORT.getKey());
		params.put("status", OrderStatusEnum.WMS_PRINT);
		
		List<ShipOrder> shipOrders = this.shipOrderService.findShipOrderByList(params);
		for(ShipOrder shipOrder:shipOrders){
			
			shipOrder.setStatus(OrderStatusEnum.WMS_FINASH);
			
			shipOrder.setOrderExaminationTime(new Date());
			
			shipOrderService.updateShipOrder(shipOrder);
		}
		
		model.put("count",  shipOrderService.getTotal(params));
		
		return "admin/system/orderImport";
	}
	
	
	// 上传商品页面
	@RequestMapping(value = "toPoint", method = RequestMethod.GET)
	public String toPoint(ModelMap model ,	
			@RequestParam(value = "itemId", defaultValue = "1") String itemId,
			@RequestParam(value = "count", defaultValue = "1") Long count) throws ApiException {
		
		Item item = itemService.getItem(itemId);
		model.put("item", item);
		
		User user = userService.getUserById(item.getUser().getId());	
		model.put("user", user);
		
		model.put("count", count);
		
		return "admin/point/point";
	}

	
	//舒服不到区域
	@RequestMapping(value = "toSFArea")
	public String toSFArea(ModelMap model) throws ApiException {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("systemSource", SystemSourceEnums.IMPORT.getKey());
		params.put("status", OrderStatusEnum.WMS_PRINT);
		model.put("count",  shipOrderService.getTotal(params));
		
		return "admin/system/sfAreaImport";
	}
	
	
	@RequestMapping(value = "uploadFromSfArea")
	public String uploadFromSfArea(HttpServletRequest request,@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
				throws FileNotFoundException, IOException {
		
		SfAreaExcelReader order  = new SfAreaExcelReader(file.getInputStream());
		
		List<Map<String, Object>> areas = order.getAreas();
		auditAreaService.importSfArea(areas);
		
		return "admin/system/sfAreaImport";
	}

}
