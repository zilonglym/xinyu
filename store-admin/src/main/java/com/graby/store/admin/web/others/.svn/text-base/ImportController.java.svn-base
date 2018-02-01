package com.graby.store.admin.web.others;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.jfree.ui.LengthAdjustmentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.util.ERPOrderExcelReader;
import com.graby.store.admin.util.ExcelReaderUtil;
import com.graby.store.admin.util.InventoryExcelReader;
import com.graby.store.admin.util.OrderExcelReader;
import com.graby.store.admin.util.SfAreaExcelReader;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.ImportRecord;
import com.graby.store.entity.Item;
import com.graby.store.entity.Storage;
import com.graby.store.entity.User;
import com.graby.store.remote.AuditAreaRemote;
import com.graby.store.remote.ImportRecordRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.util.ERPEnums;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/import")
public class ImportController extends BaseController{
	
	@Autowired
	private  ShipOrderRemote  shipOrderRemote;
	
	@Autowired  
	private ImportRecordRemote  importRecordRemote;
	
	@Autowired
	private  UserRemote  userRemote;
	
	@Autowired
	private  ItemRemote  itemRemote;
	
	@Autowired
	private  AuditAreaRemote   auditAreaRemote;
	
	/**
	 * 进入  ERP  订单  导入
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "toOrderFromERP", method = RequestMethod.GET)
	public String uploadFromERP(ModelMap model) throws ApiException {
		List<User> users = userRemote.findUsers();  //获得 所有 商户信息
		model.put("users", users);    
		model.put("erps", ERPEnums.values());  //系统可导入 ERP 系统 
		return "/system/orderFromERPImport";
	}
	/**
	 * ERP  订单 数据导入  提交
	 * @param file
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws Exception 
	 */
	@RequestMapping(value = "order/uploadFromERP", method = RequestMethod.POST)
	public String uploadFromERP(HttpServletRequest request,@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model) throws FileNotFoundException, IOException{
		System.out.println("1111");
		
		ERPOrderExcelReader order  = new ERPOrderExcelReader(file.getInputStream());
		
		System.err.println("order:"+order);
		
		String erpKey  =  request.getParameter("erpKey");   //erp  类型 
		
		String  userId =  request.getParameter("userId");   //用户ID
	
		/**
		 * 根据Eexcel表格解析结果code进行操作
		 * 500：信息出现错误，不执行数据库相关操作，直接返回错误信息前台显示
		 * 200：信息正确，向下执行数据库相关操作
		 */
		Map<String, Object> retMap =  order.getOrder(erpKey);
	
		String code = ""+retMap.get("code");

		System.err.println("code:"+code);
		if ("500".equals(code)) {
			List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
			results.add(retMap);
			model.put("ordrMap", results);		
			model.put("count", 0);		
		}else {		
			List<Map<String, Object>> paramsList = (List<Map<String, Object>>) retMap.get("list"); //封装 订单相信
			if(paramsList==null){   //无法获得 ERP 信息
				List<User> users = userRemote.findUsers();  //获得 所有 商户信息
				model.put("users", users);    
				model.put("erps", ERPEnums.values());  //系统可导入 ERP 系统 
				return "/system/orderFromERPImport";
			}
			Map<String,Object>  params  = new HashMap<String,Object>();
			params.put("userId", userId);  
			params.put("orderList", paramsList);
			params.put("erp", erpKey);
			params.put("centroId",BaseResource.getCurrentCentroId());  //当前仓库
			params.put("accountId", BaseResource.getCurrentUser());
			List<Map<String, Object>> ordrMap = shipOrderRemote.importERPOrders(params);
			int  count = 0  ;
			for(Map<String,Object> map  :  ordrMap){
				if((""+map.get("msg")).indexOf("成功")!=-1){
					count++;
				}
			
				System.err.println("retMap:" +  map);
			}
			model.put("count", count);  //系统可导入  数量
			model.put("ordrMap", ordrMap);  //系统可导入 MSG
		}
		
		List<User> users = userRemote.findUsers();  //获得 所有 商户信息
		model.put("users", users);    
		model.put("erps", ERPEnums.values());  //系统可导入 ERP 系统 
		
		return "/system/orderFromERPImport";
	}
	
	@RequestMapping(value = "toImportRecord", method = RequestMethod.GET)
	public String toImportRecord(ModelMap model) throws ApiException {
		
		
		return "/import/importRecordList";
	}
	
	
	@RequestMapping(value = "toLastImportRecord", method = RequestMethod.GET)
	public String toLastImportRecord(ModelMap model) throws ApiException {
		
		return "/import/lastImportRecordList";
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
		if(rows==10){
			rows=100;
		}
//		Map<String,Object> params=new HashMap<String, Object>();
		List<ImportRecord> findImportRecord = importRecordRemote.findLastBatchImportRecord();
		resultMap.put("page",page);
        resultMap.put("rows",findImportRecord);
        resultMap.put("total", findImportRecord.size());
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
		if(rows==10){
			rows=100;
		}
		Map<String,Object> params=new HashMap<String, Object>();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		params.put("startDate",startDate);
		params.put("endDate",endDate);
		List<ImportRecord> findImportRecord = importRecordRemote.findImportRecord(page, rows, params);
		resultMap.put("page",page);
        resultMap.put("rows",findImportRecord);
        resultMap.put("total", importRecordRemote.findImportRecordCount(params));
		return resultMap;
	}
	
	
	
	// 上传商品页面
	@RequestMapping(value = "toOrder", method = RequestMethod.GET)
	public String upload(ModelMap model) throws ApiException {
		model.put("count",  shipOrderRemote.findSendOrderImportCount());
		return "/system/orderImport";
	}

	//订单导入 初始化
	@RequestMapping(value = "order/upload", method = RequestMethod.POST)
	public String uploadItems(@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
			throws Exception {
		logger.info("订单导入开始！");
		try{
			OrderExcelReader order = new OrderExcelReader(file.getInputStream());
			List<Map<String, Object>> list = order.getOrder();
			shipOrderRemote.importOrders(list);
			model.put("count",  shipOrderRemote.findSendOrderImportCount());
		}catch(Exception e){
			logger.info("订单导入异常!"+e.getMessage());
			model.put("errorMsg", e.getMessage());
		}
		return "/system/orderImport";
	}
	@RequestMapping(value = "orderSub", method = RequestMethod.POST)
	public String orderSub(ModelMap model)   {
		shipOrderRemote.updateImportShipOrderSub();
		model.put("count",  shipOrderRemote.findSendOrderImportCount());
		return "/system/orderImport";
	}
	
	
	// 上传商品页面
	@RequestMapping(value = "toPoint", method = RequestMethod.GET)
	public String toPoint(ModelMap model ,	
			@RequestParam(value = "itemId", defaultValue = "1") Long itemId,
			@RequestParam(value = "count", defaultValue = "1") Long count) throws ApiException {
		Item item = itemRemote.getItem(itemId);
		
		String barCode = item.getBarCode();
		int lne = barCode.length();
		
		/**
		 * 商品条码长度小于14，使用EAN13编码（商品自带条码）
		 * 商品条码长度大于13，使用128B编码（系统生成条码）
		 * */
		if (lne<14) {
			model.put("barCode", "EAN13");
		}else {
			model.put("barCode", "128B");
		}
		
		User user = userRemote.getUser(item.getUserid());
		
		model.put("user", user);
		model.put("item", item);
		model.put("count", count);
		
		return "/point/point";
	}

	
	//舒服不到区域
	@RequestMapping(value = "toSFArea", method = RequestMethod.GET)
	public String toSFArea(ModelMap model) throws ApiException {
		model.put("count",  shipOrderRemote.findSendOrderImportCount());
		return "/system/sfAreaImport";
	}
	
	
	@RequestMapping(value = "uploadFromSfArea", method = RequestMethod.POST)
	public String uploadFromSfArea(HttpServletRequest request,@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
				throws FileNotFoundException, IOException {
		SfAreaExcelReader order  = new SfAreaExcelReader(file.getInputStream());
		List<Map<String, Object>> areas = order.getAreas();
		auditAreaRemote.importSfArea(areas);
		return "/system/sfAreaImport";
	}
	
	/**
	 * 订单导入打印（新）
	 * 2017-09-11 fufangjue
	 * */
	@RequestMapping(value = "importOrderToPrint", method = RequestMethod.GET)
	public String uploadOrder(ModelMap model) throws ApiException {
		List<User> users = userRemote.findUsers();  //获得 所有 商户信息
		model.put("users", users);    
		return "/system/orderImportNew";
	}
	
	@RequestMapping(value = "order/uploadOrder", method = RequestMethod.POST)
	public String uploadOrderPrint(HttpServletRequest request,@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model)
				throws FileNotFoundException, IOException {
		System.out.println("导入订单数据提交！");
		ERPOrderExcelReader order  = new ERPOrderExcelReader(file.getInputStream());
		
		System.err.println("order:"+order);
		
		String  userId =  request.getParameter("userId");   //用户ID
		
		/**
		 * 根据Eexcel表格解析结果code进行操作
		 * 500：信息出现错误，不执行数据库相关操作，直接返回错误信息前台显示
		 * 200：信息正确，向下执行数据库相关操作
		 */
		Map<String, Object> retMap = order.getOrder("FUNRUN");
		String code = ""+retMap.get("code");
		System.err.println("code:"+code);
		if ("500".equals(code)) {
			List<Map<String,Object>> results = new ArrayList<Map<String,Object>>();
			results.add(retMap);
			model.put("ordrMap", results);		
			model.put("count", 0);		
		}else {
			List<Map<String, Object>> paramsList = (List<Map<String, Object>>) retMap.get("list"); //封装 订单相信
			System.err.println("paramsList:"+paramsList);
			if(paramsList==null){   //无法获得 ERP 信息
				List<User> users = userRemote.findUsers();  //获得 所有 商户信息
				model.put("users", users);    
				return "/system/orderImportNew";
			}
		
			Map<String,Object>  params  = new HashMap<String,Object>();
			params.put("userId", userId);  
			params.put("orderList", paramsList);
			params.put("centroId",BaseResource.getCurrentCentroId());  //当前仓库
			params.put("accountId",BaseResource.getCurrentUser());  //当前仓库
			List<Map<String, Object>> ordrMap = shipOrderRemote.importOrdersNew(params);
		
			int  count = 0  ;
			for(Map<String,Object> map  :  ordrMap){
				if((""+map.get("msg")).indexOf("成功")!=-1){
					count++;
				}
			
				System.err.println("retMap:" +  map);
			}
			model.put("count", count);  //系统可导入  数量
			model.put("ordrMap", ordrMap);  //系统可导入 MSG
		}
		List<User> users = userRemote.findUsers();  //获得 所有 商户信息
		model.put("users", users);    
		return "/system/orderImportNew";
	}

	
	/**
	 *  shark 测试用
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "shark", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> shark(ModelMap model) throws ApiException {
		Map<String,Object>  map  = new HashMap<String, Object>();


		ExcelReaderUtil.filterEmoji("熊🐻惠娟");
		ExcelReaderUtil.filterEmoji("段华琴");
		ExcelReaderUtil.filterEmoji("21321🌹");
		
		return map;
	}
	
	/**
	 *  shark 测试用
	 * @param model
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "shark1", method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> shark1(ModelMap model) throws ApiException {
		Map<String,Object>  map  = new HashMap<String, Object>();
		ExcelReaderUtil.filterEmoji("熊🐻惠娟","");
		ExcelReaderUtil.filterEmoji("段华琴","");
		ExcelReaderUtil.filterEmoji("21321🌹","");
		return map;
	}
}
