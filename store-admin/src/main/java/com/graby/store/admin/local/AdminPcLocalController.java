package com.graby.store.admin.local;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.PoiExcelExport;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.POIModel;
import com.graby.store.entity.enums.OrderStatusEnums;
import com.graby.store.entity.local.LocalBatch;
import com.graby.store.entity.local.LocalBoxOut;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.entity.local.LocalOperateRecord;
import com.graby.store.entity.local.LocalPlate;
import com.graby.store.entity.local.LocalShop;
import com.graby.store.remote.LocalRemote;
import com.taobao.api.ApiException;

import net.sf.json.JSONArray;

@Controller
@RequestMapping(value = "/pcLocal")
public class AdminPcLocalController extends BaseController {
	
	@Autowired
	private LocalRemote localRemote;
	
	/**
	 * 本地商品列表页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value="localItem/itemList")
	public String localItemList(ModelMap model){
		List<LocalShop> shops = this.localRemote.getLocalShopList(null);
//		System.err.println("size:"+shops.size());
		model.put("shops", shops);
		return "local/itemList";
	}
	
	/**
	 * 本地商品列表数据
	 * @param page
	 * @param rows
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="localItem/listData")
	@ResponseBody
	public Map<String, Object> localItemListData(@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows,HttpServletRequest request,ModelMap model){
		if (rows==10) {
			rows=100;
		}
		String shopId = request.getParameter("shopId");
		String q = request.getParameter("q");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("shopId", shopId);
		params.put("searchText", q);
		List<LocalItem> localItems = this.localRemote.findLocalItemByPage(params,page,rows);
		int total = this.localRemote.getLocalItemCount(params);
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("page", page);
		retMap.put("rows", localItems);
		retMap.put("total", total);
		return retMap;
	}
	
	/**
	 * 新建本地商品资料
	 * @param model
	 * @return
	 */
	@RequestMapping(value="localItem/toAddItem")
	public String toAddLocalItem(ModelMap model){
		List<LocalShop> shops = this.localRemote.getLocalShopList(null);
		model.put("shops", shops);
		return "local/toAddItem";
	}
	
	/**
	 * 修改已有的商品资料
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="localItem/toEditItem")
	public String toEditLocalItem(ModelMap model,HttpServletRequest request){
		String itemId = request.getParameter("id");
		LocalItem localItem = this.localRemote.getLocalItemById(Integer.parseInt(itemId));
		model.put("localItem", localItem);
		List<LocalShop> shops = this.localRemote.getLocalShopList(null);
		model.put("shops", shops);
		return "local/toEditItem";
	}
	
	/**
	 * 本地商品资料保存
	 * @param request
	 * @return
	 */
	@RequestMapping(value="localItem/saveItem")
	@ResponseBody
	public Map<String, Object> saveLocalItem(HttpServletRequest request){
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id = object.getString("id");
		String name = object.getString("name");
		String barCode = object.getString("barCode");
		String itemType = object.getString("itemType");
		String source = object.getString("source");
		String sku = object.getString("sku");
		String shopId = object.getString("shopId");
		String lowNum = object.getString("lowNum");
		String highNum = object.getString("highNum");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", shopId);
		LocalShop localShop = this.localRemote.getLocalShop(params);
			
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("sku", sku);
		map.put("shopId", shopId);
		int count = this.localRemote.getLocalItemCount(map);
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		if (count>1) {
			retMap.put("msg", "该商品已存在，不可以重复创建！");
		}else {
			try {
				if (StringUtils.isEmpty(id)) {
					LocalItem localItem = new LocalItem();
					localItem.setBarCode(barCode);
					localItem.setItemType(itemType);
					localItem.setName(name);
					localItem.setShopId(shopId);
					localItem.setShopName(localShop.getName());
					localItem.setSku(sku);
					localItem.setSource(source);					
					localItem.setNum(Integer.parseInt(lowNum));					
					localItem.setHighNum(Integer.parseInt(highNum));					
					this.localRemote.saveLocalItem(localItem);
 					
//					LocalOperateRecord record = new LocalOperateRecord();
//					record.setBatchCode(localItem.getShopName());
//					record.setFastCode(localItem.getName());
//					record.setModel("create");
//					record.setOperateId(String.valueOf(this.getCurrentUser().getId()));
//					String description = "商品新建："+localItem.getName()+"|"+localItem.getShopName()+"|"+localItem.getBarCode();
//					record.setDescription(description);
//					this.localRemote.saveOperateRecord(record);
					
					retMap.put("msg", "商品创建成功！");
				}else {
					LocalItem item = this.localRemote.getLocalItemById(Integer.parseInt(id));
					item.setBarCode(barCode);
					item.setItemType(itemType);
					item.setName(name);
					item.setShopId(shopId);
					item.setShopName(localShop.getName());
					item.setSku(sku);
					item.setSource(source);
					item.setNum(Integer.parseInt(lowNum));
					item.setHighNum(Integer.parseInt(highNum));
					this.localRemote.updateLocalItem(item);
					retMap.put("msg", "商品更新成功！");
					
					LocalOperateRecord record = new LocalOperateRecord();
					record.setBatchCode(""+item.getId());
					record.setFastCode(item.getName());
					record.setModel("update");
					record.setOperateId(String.valueOf(this.getCurrentUser().getId()));
					String description = "商品更新："+item.getName()+"|"+item.getShopName()+"|"+item.getBarCode();
					record.setDescription(description);
					this.localRemote.saveOperateRecord(record);
				}
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("msg", e.getMessage());
			}
		}	
		return retMap;
	}
	
	
	@RequestMapping(value="localBatch/toPrint")
	public String tPrint(ModelMap model,HttpServletRequest request){
		String itemId = request.getParameter("id");
		LocalItem localItem = this.localRemote.getLocalItemById(Integer.parseInt(itemId));
		model.put("localItem", localItem);
		return "local/toPrintCode";
	}
	
	
	@RequestMapping(value="localBatch/print/list")
	public String batchList(ModelMap model,HttpServletRequest request){
		
		String itemId = request.getParameter("id");
		String anum = request.getParameter("anum");
		String isHigh = request.getParameter("isHigh");
		String cnum = request.getParameter("cnum");
		String birthDate = request.getParameter("birthDate");
		String entryCode = request.getParameter("code");
		int dnum = 0;
		LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(itemId));
		
		if ("true".equals(isHigh)&&StringUtils.isNotBlank(cnum)) {
			dnum = Integer.parseInt(cnum);
		}else {
			dnum = item.getNum();
		}
		
		logger.error("anum:"+anum+",dnum="+dnum);
		
		int a = Integer.valueOf(anum);
		int d = Integer.valueOf(dnum);
		int count = a/d;
		model.put("itemId", itemId);
		model.put("count", count);
		model.put("anum", anum);
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		if (StringUtils.isNotBlank(birthDate)) {
			model.put("birthDate", birthDate);
		}else {
			model.put("birthDate", sf.format(new Date()));
		}
		
		model.put("dnum", dnum);
		model.put("entryCode", entryCode);
		model.put("isHigh", isHigh);
		return "local/printList";
	}

	@RequestMapping(value="localBatch/print/listData")
	@ResponseBody
	public Map<String, Object> batchListData(ModelMap model,HttpServletRequest request) throws ParseException{
		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		String itemId = request.getParameter("id");
		String anum = request.getParameter("anum");
		String dnum = request.getParameter("dnum");
		String entryCode = request.getParameter("entryCode");
		String isHigh = request.getParameter("isHigh");
		String birthDate = request.getParameter("birthDate");
		int a = Integer.parseInt(anum);
		int d = Integer.parseInt(dnum);
		int count = a/d;
		LocalItem item = this.localRemote.getLocalItemById(Integer.parseInt(itemId));
		for(int i = 0; i<count; i++){		
			LocalBatch batch = new LocalBatch();
			batch.setCreateDate(new Date());
			batch.setItemId(String.valueOf(item.getId()));
			batch.setLastUpdate(new Date());
			batch.setShopId(item.getShopId());
			batch.setEntryCode(entryCode);
			batch.setIsHigh(isHigh);
			batch.setStatus(OrderStatusEnums.WMS_GETNO);	
			batch.setNum(d);			
			batch.setBirthDate(sf.parse(birthDate));
			this.localRemote.saveBatch(batch);
			
//			LocalOperateRecord record = new LocalOperateRecord();
//			record.setBatchCode(batch.getItemId());
//			record.setFastCode(batch.getEntryCode());
//			record.setModel("create");
//			record.setOperateId(""+this.getCurrentUser().getId());
//			String description = "批次单据创建："+batch.getEntryCode()+"|"+batch.getItemId()+"|"+batch.getNum();
//			record.setDescription(description);
//			this.localRemote.saveOperateRecord(record);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", OrderStatusEnums.WMS_GETNO);
		params.put("itemId", item.getId());
		params.put("entryCode", entryCode);
		List<LocalBatch> batches = this.localRemote.findLocalBatchList(params);
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("rows", this.buildList(batches));
		retMap.put("total", batches.size());
		retMap.put("page", 1);
		return retMap;
	}
	
	private List<Map<String, Object>> buildList(List<LocalBatch> batches) {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(LocalBatch batch:batches){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", batch.getId());
			map.put("itemId", batch.getItemId());
			LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(batch.getItemId()));
			map.put("shopId", batch.getShopId());
			map.put("shopName", item.getShopName());
			map.put("itemName", item.getName());
			map.put("sku", (item.getSku()==null?"":item.getSku()));
			map.put("barCode", item.getBarCode());
			map.put("num", batch.getNum());
			map.put("status", batch.getStatus());
			map.put("code", batch.getOrderCode());
			map.put("entryCode", batch.getEntryCode());
			/**
			 * BirthDate为空，取lastUpdate
			 */
			if (batch.getBirthDate()!=null) {
				map.put("birthDate", sf.format(batch.getBirthDate()));
			}else {
				map.put("birthDate",sf.format(batch.getLastUpdate()) );
			}
			map.put("lastUpdate",sf.format(batch.getLastUpdate()) );
			if ("true".equals(batch.getIsHigh())) {
				map.put("isHigh", "自定义");
			}else{
				map.put("isHigh", "标板");
			}
			results.add(map);
		}
		return results;
	}
		
		/**
		 * 货位信息页面
		 * @param model
		 * @return
		 */
		@RequestMapping(value="localPlate/list")
		public String adminLocalList(ModelMap model){
			List<LocalShop> localShops = this.localRemote.getLocalShopList(null);
			model.addAttribute("shops", localShops);
			return "local/localList";
		}
		
		/**
		 * 货位信息数据
		 * @param page
		 * @param rows
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/listData")
		@ResponseBody
		public Map<String, Object> adminLocalListData(@RequestParam(defaultValue="1")int page,
				@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
			if (rows==10) {
				rows=100;
			}
			
			String shopId = request.getParameter("shopId");
			String row = request.getParameter("row");
			String boxOut = request.getParameter("boxOut");
			String floor = request.getParameter("floor");
			String code = request.getParameter("code");
			String q = request.getParameter("q");
			String state = request.getParameter("state");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopId", shopId);
			params.put("q", q);
			params.put("state", state);
			
			if (StringUtils.isNotEmpty(row)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(row);
				if (isNum.matches()) {
					params.put("row", row);
				}
			}
			
			if (StringUtils.isNotEmpty(floor)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(floor);
				if (isNum.matches()) {
					params.put("floor", floor);
				}
			}
			
			if (StringUtils.isNotEmpty(code)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(floor);
				if (isNum.matches()) {
					params.put("code", code);
				}
			}
			
			if (StringUtils.isNotEmpty(boxOut)) {
				boolean isWord=boxOut.matches("[a-zA-Z]+");
				if (isWord) {
					params.put("boxOut", boxOut);
				}
			}
			
			List<Map<String, Object>> localMaps = this.localRemote.findLocalPlateList(params,page,rows);
			int total = this.localRemote.getTotal(params);
			
			Map<String, Object> retMap =new HashMap<String, Object>();
			retMap.put("rows", this.buildData(localMaps));
			retMap.put("total", total);
			retMap.put("page", page);
			return retMap;
		}

		/**
		 * 数据封装
		 * @param localMaps
		 * @return
		 */
		private List<Map<String, Object>> buildData(List<Map<String, Object>> localMaps) {
			List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
			for(Map<String, Object> lMap : localMaps){
				System.err.println(lMap);
				Map<String, Object> map = new HashMap<String, Object>();
				String boxout = lMap.get("row")+"-"+lMap.get("boxOut")+"-"+lMap.get("code")+"-"+lMap.get("floor");
				map.put("boxout", boxout);
				map.put("fastCode", lMap.get("fastCode"));
				map.put("id", lMap.get("id"));
				map.put("shopName", lMap.get("shopName"));
				map.put("name", lMap.get("name"));
				map.put("sku", lMap.get("sku"));
				map.put("barCode", lMap.get("barCode"));
				map.put("num", lMap.get("num"));
				SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				map.put("lastUpdate", sf.format(lMap.get("lastUpdate")));
				
				String batchCode = "" + lMap.get("batchCode");	
				System.err.println(StringUtils.isNotBlank(batchCode));
				map.put("batchCode", StringUtils.isNotBlank(batchCode)==true?batchCode:"无");
							
				results.add(map);
			}
			return results;
		}
		
		/**
		 * 货架信息导出
		 * @param request
		 * @param response
		 * @return
		 * @throws ParseException
		 */
		@RequestMapping(value = "localPlate/xls")
		public String localPlateExport(HttpServletRequest request,HttpServletResponse response) throws ParseException{
			logger.debug("货架信息导出开始！");
			SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String,Object> params=new HashMap<String, Object>();
			String shopId = request.getParameter("shopId");
			String row = request.getParameter("row");
			String boxOut = request.getParameter("boxOut");
			String floor = request.getParameter("floor");
			String code = request.getParameter("code");
			String state=request.getParameter("state");
			String q=request.getParameter("q");
			params.put("shopId",shopId);
			params.put("row",row);
			params.put("boxOut",boxOut);
			params.put("floor",floor);
			params.put("code",code);
			params.put("state",state);
			params.put("q",q);						
			List<Map<String, Object>> plateMaps = this.localRemote.findLocalPlateList(params, 1, 999999);
					
			List<POIModel> poiModels=new ArrayList<POIModel>();
			
			for(Map<String,Object> map:plateMaps){
				
				POIModel poiModel=new POIModel();
				
				//货架信息
				poiModel.setM1(""+map.get("fastCode"));
				//商家
				poiModel.setM2(""+map.get("shopName"));
				//商品名称
				poiModel.setM3(""+map.get("name"));
				//商品属性
				poiModel.setM4(""+map.get("sku"));
				//数量
				poiModel.setM5(""+map.get("num"));
				//单据编号
				poiModel.setM6(""+map.get("batchCode"));
				//上架时间
				poiModel.setM7(""+map.get("lastUpdate"));	
				
				poiModels.add(poiModel);
			}
			//时间格式化
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			//拼接Excel文件名称
			String filename=sdf.format(new Date())+"货架信息";
			//新建PoiExcelExport对象
			PoiExcelExport pee = new PoiExcelExport(response,filename,"sheet1");
			//Excel文件填充内容属性
			String titleColumn[] = {"m1","m2","m3","m4","m5","m6","m7"};  
			//Excel文件填充内容列名
			String titleName[] = {"货架","商家","品名","属性","数量","编号","时间"};  
			//Excel文件填充内容大小
			int titleSize[] = {20,20,20,20,20,20,20};  
			//调用PoiExcelExport导出Excel文件
			pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
			
			logger.debug("货位信息导出结束！");
			
			return null;
		}
		
		@RequestMapping(value="report")
		public String adminLocalReport(ModelMap model){
			List<LocalShop> shops = this.localRemote.getLocalShopList(null);
			model.addAttribute("shops", shops);
			return "local/localReport";
		}
		
		@RequestMapping(value="report/listData")
		@ResponseBody
		public Map<String, Object> reportListData(@RequestParam(defaultValue="1")int page,
				@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
			if (rows==10) {
				rows=100;
			}
			
			String shopId = request.getParameter("shopId");
			String startTime = request.getParameter("startTime");
			String endTime = request.getParameter("endTime");
			String q = request.getParameter("q");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopId", shopId);
			params.put("startTime", startTime);
			params.put("endTime", endTime);
			params.put("q", q);
			
			List<Map<String, Object>> reports = this.localRemote.findLocalReport(params,page,rows);
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("page", page);
			retMap.put("rows", reports);
			retMap.put("total", reports.size());
			return retMap;
		}
		
		/**
		 * 上架明细单(POI)下载
		 * @param userId
		 * @param startDate
		 * @param endDate
		 * @return 
		 * @throws IOException 
		 * */
		@RequestMapping(value = "/report/xls")
		public String shipReport(	
				@RequestParam(value = "shopId") String shopId,
				@RequestParam(value = "shopName") String shopName,
				@RequestParam(value = "startTime") String startTime,
				@RequestParam(value = "endTime") String endTime,HttpServletResponse response) throws IOException {

			Map<String, Object> p = new HashMap<String, Object>();
			p.put("shopId", shopId);
			p.put("startTime", startTime);
			p.put("endTime", endTime);
			//新建shipOrder数组存放新shipOrder对象
			List<POIModel> poiModels=new ArrayList<POIModel>();	
			List<Map<String, Object>> boxOuts = this.localRemote.findLocalPlateList(p,1,Integer.MAX_VALUE);
			for(Map<String, Object> map:boxOuts){
				//新建一个shipOrder对象
				POIModel poiModel=new POIModel();
				String boxOut = map.get("row")+"-"+map.get("boxOut")+"-"+map.get("code")+"-"+map.get("floor");
				poiModel.setM1(boxOut);
				poiModel.setM2(""+map.get("shopName"));
				poiModel.setM3(""+map.get("name"));
				poiModel.setM4(""+(map.get("sku")!=null?map.get("sku"):""));
				poiModel.setM5(""+map.get("barCode"));
				poiModel.setM6(""+map.get("num"));
				poiModel.setM7(""+map.get("lastUpdate"));
				poiModels.add(poiModel);
			}		
			//新建PoiExcelExport对象
			PoiExcelExport pee = new PoiExcelExport(response,shopName+"货位明细","sheet1");
			//Excel文件填充内容属性
			String titleColumn[] = {"m1","m2","m3","m4","m5","m6","m7"};  
	        //Excel文件填充内容列名
			String titleName[] = {"货位信息","商家名称","商品名称","商品属性","商品条码","商品数量","更新时间"};  
			//Excel文件填充内容列宽
			int titleSize[] = {20,20,20,20,20,20,20};  
			//调用PoiExcelExport导出Excel文件
	        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
			return null; 
		}
		
		/**
		 * 货架商品汇总单(POI)下载
		 * @param userId
		 * @param startDate
		 * @param endDate
		 * @return 
		 * @throws IOException 
		 * */
		@RequestMapping(value = "/report/item/xls")
		public String itemReport(	
				@RequestParam(value = "shopId") String shopId,
				@RequestParam(value = "shopName") String shopName,
				@RequestParam(value = "startTime") String startTime,
				@RequestParam(value = "endTime") String endTime,HttpServletResponse response) throws IOException {

			Map<String, Object> p = new HashMap<String, Object>();
			p.put("shopId", shopId);
			p.put("startTime", startTime);
			p.put("endTime", endTime);
			//新建shipOrder数组存放新shipOrder对象
			List<POIModel> poiModels=new ArrayList<POIModel>();	
			List<Map<String, Object>> reports = this.localRemote.findItemReport(p);
			for(Map<String, Object> map:reports){
				//新建一个shipOrder对象
				POIModel poiModel=new POIModel();
				poiModel.setM1(""+map.get("shopName"));
				poiModel.setM2(""+map.get("itemName"));
				poiModel.setM3(""+(map.get("sku")!=null?map.get("sku"):""));
				poiModel.setM4(""+map.get("barCode"));
				poiModel.setM5(""+map.get("bnum"));
				poiModels.add(poiModel);
			}		
			//新建PoiExcelExport对象
			PoiExcelExport pee = new PoiExcelExport(response,shopName+"商品汇总","sheet1");
			//Excel文件填充内容属性
			String titleColumn[] = {"m1","m2","m3","m4","m5"};  
	        //Excel文件填充内容列名
			String titleName[] = {"商家名称","商品名称","商品属性","商品条码","商品数量"};  
			//Excel文件填充内容列宽
			int titleSize[] = {20,20,20,20,20};  
			//调用PoiExcelExport导出Excel文件
	        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
			return null; 
		}
		
		/**
		 * 库位操作记录页面
		 * @param request
		 * @return
		 */
		@RequestMapping(value="record/listData")
		@ResponseBody
		public Map<String, Object> recordListData(HttpServletRequest request){
			String plateId = request.getParameter("id");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("plateId", plateId);
			List<Map<String, Object>> reports = this.localRemote.findLocalPlateRecord(params);
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("page", 1);
			retMap.put("rows", reports);
			retMap.put("total", reports.size());
			return retMap;
		}
		
		/**
		 * 库位调整页面
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/toMove")
		public String localBoxMove(ModelMap model,HttpServletRequest request){
			return "local/toNewMoveBox";
		}

		/**
		 * 初始化库位信息数据
		 * @param page
		 * @param rows
		 * @param request
		 * @return
		 */
		@RequestMapping(value="initLocalPlate")
		@ResponseBody
		public Map<String, Object> initLocalPlate(@RequestParam(defaultValue="1")int page,
				@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
			if (rows==10) {
				rows=100;
			}
			
			String row = request.getParameter("row");
			String boxOut = request.getParameter("boxOut");
			String floor = request.getParameter("floor");
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			
			Map<String, Object> params = new HashMap<String, Object>();
			
			if (StringUtils.isNotEmpty(row)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(row);
				if (isNum.matches()) {
					params.put("row", row);
				}
			}
			
			if (StringUtils.isNotEmpty(floor)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(floor);
				if (isNum.matches()) {
					params.put("floor", floor);
				}
			}
			
			if (StringUtils.isNotEmpty(code)) {
				Pattern pattern = Pattern.compile("[0-9]*"); 
				Matcher isNum = pattern.matcher(floor);
				if (isNum.matches()) {
					params.put("code", code);
				}
			}
			
			if (StringUtils.isNotEmpty(boxOut)) {
				boolean isWord=boxOut.matches("[a-zA-Z]+");
				if (isWord) {
					params.put("boxOut", boxOut);
				}
			}
			
			params.put("state",state);
			
			List<Map<String, Object>> plateMaps = this.localRemote.findLocalPlateList(params, page, rows); 
			int total = this.localRemote.getTotal(params);
			
			Map<String,Object> retMap = new HashMap<String, Object>();
			retMap.put("page", page);
			retMap.put("rows", this.buildData(plateMaps));
			retMap.put("total", total);
			return retMap;
		}
		
		/**
		 * 库位调整数据保存
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/save")
		@ResponseBody
		public Map<String, Object> save(HttpServletRequest request){
			String oldId = request.getParameter("oldId");
			String newId = request.getParameter("newId");
			LocalPlate oldPlate = this.localRemote.findLocalPlate(oldId);
			String oldState = oldPlate.getState();
//			System.err.println(oldPlate.getId()+":"+oldPlate.getbatchId());
			LocalPlate newPlate = this.localRemote.findLocalPlate(newId);
//			System.err.println(newPlate.getId()+":"+newPlate.getbatchId());
			String newState = newPlate.getState();
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			
			try {
				
				if (!("0".equals(oldState))&&!("0".equals(newState))) {
					
					//newPlate的商品
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", Integer.valueOf(oldId));
					params.put("itemId", newPlate.getItem());
					params.put("num", newPlate.getNum().intValue());
					params.put("opertionId", String.valueOf(this.getCurrentUser().getId()));
					params.put("batchId", newPlate.getbatchId());
					
					//oldPlate的商品
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("id", Integer.valueOf(newId));
					p.put("itemId", oldPlate.getItem());
					p.put("num", oldPlate.getNum().intValue());
					p.put("opertionId", String.valueOf(this.getCurrentUser().getId()));
					p.put("batchId", oldPlate.getbatchId());
					
					//newPlate下架newPlate的商品
 					this.localRemote.downLocalPlate(Integer.valueOf(oldId), String.valueOf(this.getCurrentUser().getId()));
 					
 					//oldPlate下架oldPlate的商品
 					this.localRemote.downLocalPlate(Integer.valueOf(newId), String.valueOf(this.getCurrentUser().getId()));
 					
 					//oldPlate上架newPlate的商品
 					this.localRemote.upLocalPlate(params);
						
 					//newPlate上架oldPlate的商品
 					this.localRemote.upLocalPlate(p);
 					
					retMap.put("msg", "库位交换完成！");
					
				}else if(!("0".equals(oldState))&&("0".equals(newState))){
					
					//oldPlate下架商品
					this.localRemote.downLocalPlate(Integer.valueOf(oldId), String.valueOf(this.getCurrentUser().getId()));
					
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", Integer.valueOf(newId));
					params.put("itemId", oldPlate.getItem());
					params.put("num", oldPlate.getNum().intValue());
					params.put("opertionId", String.valueOf(this.getCurrentUser().getId()));
					params.put("batchId", oldPlate.getbatchId());
					//newPlate上架oldPlate的商品
//					this.localRemote.upLocalPlate(Integer.valueOf(newId), oldPlate.getItem(), oldPlate.getNum().intValue(), String.valueOf(this.getCurrentUser().getId()));				
					this.localRemote.upLocalPlate(params);
					
					retMap.put("msg", "库位调整完成！");
					
				}else if (("0".equals(oldState))&&!("0".equals(newState))) {
					
					//newPlate下架商品
					this.localRemote.downLocalPlate(Integer.valueOf(newId), String.valueOf(this.getCurrentUser().getId()));
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", Integer.valueOf(oldId));
					params.put("itemId", newPlate.getItem());
					params.put("num", newPlate.getNum().intValue());
					params.put("opertionId", String.valueOf(this.getCurrentUser().getId()));
					params.put("batchId", newPlate.getbatchId());
					//oldPlate上架newPlate的商品
					System.err.println(params);
					this.localRemote.upLocalPlate(params);
					
					retMap.put("msg", "库位调整完成！");
				}
				
				LocalOperateRecord record = new LocalOperateRecord();
				LocalPlate plate = this.localRemote.findLocalPlate(oldId);
				record.setBatchCode(plate.getbatchId());
				record.setFastCode(plate.getFastCode());
				record.setModel("update");
				record.setOperateId(""+this.getCurrentUser().getId());
				String description = "货位调整:"+plate.getFastCode();
				record.setDescription(description);
				this.localRemote.saveOperateRecord(record);
				
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("msg", "库位调整失败！");
			}
			
			return retMap;
		}
		
		/**
		 * 库位编辑页面
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/toEdit")
		public String localBoxEdit(ModelMap model,HttpServletRequest request){
			String id = request.getParameter("id");
			LocalPlate localPlate = this.localRemote.findLocalPlate(id);
			model.put("localPlate", this.buildPlate(localPlate));
			return "local/toEditBox";
		}
		
		/**
		 * 数据封装
		 * @param localPlate
		 * @return
		 */
		private Map<String, Object> buildPlate(LocalPlate localPlate) {
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("id", localPlate.getId());
			retMap.put("num", localPlate.getNum());
			List<LocalBoxOut> boxOuts = this.localRemote.getLocalBoxOutById(String.valueOf(localPlate.getBoxOut().getId()));
			LocalBoxOut boxOut = boxOuts.get(0);
			retMap.put("row", boxOut.getRow());
			retMap.put("boxOut", boxOut.getBoxOut());
			retMap.put("floor", boxOut.getFloor());
			retMap.put("code", localPlate.getCode());		
			return retMap;
		}

		/**
		 * 库位商品列表数据
		 * @param request
		 * @return map
		 * */
		@RequestMapping(value="localPlate/detail/listData")
		@ResponseBody
		public Map<String, Object> detailListData(HttpServletRequest request){
			
			String id = request.getParameter("id");
			LocalPlate localPlate = this.localRemote.findLocalPlate(id);
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			if (localPlate.getItem()!=null) {
				LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(localPlate.getItem()));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", item.getId());
				map.put("shopName", item.getShopName());
				map.put("name", item.getName());
				map.put("sku", item.getSku());
				map.put("barCode", item.getBarCode());
				map.put("num", localPlate.getNum());
				list.add(map);
			}
			
			Map<String, Object> retMap = new HashMap<String, Object>();	
			retMap.put("rows", list);
			return retMap;
		}
		
		/**
		 * 库位商品添加页面
		 * @param model
		 * @return
		 */
		@RequestMapping(value="localPlate/f7Item/list")
		public String itemList(ModelMap model){
			List<LocalShop> shops = this.localRemote.getLocalShopList(null);
			model.put("users",shops);
			return "local/detailList";
		}
		
		/**
		 * 商品列表数据
		 * @param page
		 * @param rows
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/item/listData")
		@ResponseBody
		public Map<String, Object> ItemListData(@RequestParam(defaultValue="1")int page,
				@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
			if (rows==10) {
				rows=100;
			}
			String shopId = request.getParameter("shopId");
			String q = request.getParameter("q");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopId", shopId);
			params.put("searchText", q);
			List<LocalItem> items = this.localRemote.findLocalItemByPage(params, page, rows);
			int total = this.localRemote.getLocalItemCount(params);
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("rows", items);
			retMap.put("page", 1);
			retMap.put("total", total);
			return retMap;
		}
		
		/**
		 * 库位商品明细
		 * @param request
		 * @return map
		 * */
		@RequestMapping(value="localPlate/newItem/listData")
		@ResponseBody
		public Map<String, Object> newItemListData(HttpServletRequest request){
			
			Object dataStr = request.getParameter("dataStr");
			
			List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
					
			JSONArray array = JSONArray.fromObject(dataStr);
			
			for(Object jsonObj : array){
				net.sf.json.JSONObject obj = (net.sf.json.JSONObject)jsonObj;
				String itemId = "" + obj.get("id");
				LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(itemId));
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", item.getId());
				map.put("name", item.getName());
				map.put("shopName", item.getShopName());
				map.put("sku", item.getSku());
				map.put("barCode", item.getBarCode());
				results.add(map);
				}		
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("rows", results);
			retMap.put("total", results.size());
			retMap.put("page", 1);
			return retMap;
			
		}
		
		/**
		 * 库位商品信息更新
		 * @param request
		 * @return
		 */
		@RequestMapping(value="localPlate/update")
		@ResponseBody
		public Map<String, Object> update(HttpServletRequest request){
			Map<String, Object> retMap = new HashMap<String, Object>();
			try {
				String json = request.getParameter("json");
				JSONObject object = new JSONObject(json);
				String plateId = object.getString("id");
				LocalPlate plate = this.localRemote.findLocalPlate(plateId);
				Object rows = request.getParameter("rows");  //集合数据来源   
				JSONArray array = JSONArray.fromObject(rows);
				for(Object jsonObj:array){
					net.sf.json.JSONObject obj = (net.sf.json.JSONObject) jsonObj;
					String num = ""+obj.get("num");
					String itemId = ""+obj.get("id");
					
					//下架
					this.localRemote.downLocalPlate(Integer.valueOf(plateId), String.valueOf(this.getCurrentUser().getId()));
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", Integer.valueOf(plateId));
					params.put("itemId", itemId);
					params.put("num", Integer.valueOf(num));
					params.put("opertionId", String.valueOf(this.getCurrentUser().getId()));
//					params.put("batchId", batchId);
					//上架
					this.localRemote.upLocalPlate(params);
				}
				
				LocalOperateRecord record = new LocalOperateRecord();
				record.setBatchCode(plate.getbatchId());
				record.setFastCode(plate.getFastCode());
				record.setModel("update");
				record.setOperateId(""+this.getCurrentUser().getId());
				String description = "货位更新修改:"+plate.getFastCode();
				record.setDescription(description);
				this.localRemote.saveOperateRecord(record);
				
				
				retMap.put("msg", "修改成功！");
			} catch (Exception e) {
				e.printStackTrace();
				retMap.put("msg", e.getMessage());
			}	
			return retMap;
		}
		
		/**
		 * 批次单据页面
		 * @param model
		 * @return
		 */
		@RequestMapping(value="/localBatch/list")
		public String localBatchList(ModelMap model){
			List<LocalShop> shops = this.localRemote.getLocalShopList(null);
			model.put("shops", shops);
			return "local/batchList";
		}

		/**
		 * 	
		 * 批次单据页面数据
		 * @param page
		 * @param rows
		 * @param request
		 * @return
		 */
		@RequestMapping(value="/localBatch/listData")
		@ResponseBody
		public Map<String, Object> localBatchListData(@RequestParam(defaultValue="1")int page,
				@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
			if (rows==10) {
				rows=100;
			}
			String shopId = request.getParameter("shopId"); 
			String q = request.getParameter("q"); 
			String startDate = request.getParameter("startDate"); 
			String endDate = request.getParameter("endDate"); 
			String status = request.getParameter("status"); 
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("shopId", shopId);
			params.put("q", q);
			params.put("startDate", startDate);
			params.put("endDate", endDate);
			params.put("status", status);
			params.put("pageNum", (page - 1) * rows);
			params.put("pageSize", rows);
			
			List<LocalBatch> batchList = this.localRemote.findLocalBatchList(params);
			int total = this.localRemote.getBatchTotal(params);
			
			Map<String, Object> retMap = new HashMap<String, Object>();
			retMap.put("page", page);
			retMap.put("rows", this.buildList(batchList));
			retMap.put("total", total);
			return retMap;
		}
		
		
		/**
		 * 单据打印
		 * @param model
		 * @param request
		 * @return
		 */
		@RequestMapping(value = "localBatch/toBatchPrint")
		public String toBatchPrint(ModelMap model, HttpServletRequest request){
					
					String idsObj = (String)request.getParameter("ids");
					
					List<Map<String, Object>> printList = new ArrayList<Map<String,Object>>();
					
					if (idsObj!=null) {
						
						String[] ids = idsObj.split(",");
						for(int i =0; i<ids.length; i++){
				
							LocalBatch batch = this.localRemote.findLocalBatchById(Integer.valueOf(ids[i]));
							
							Map<String, Object> params = new HashMap<String, Object>();
							params.put("id", batch.getShopId());
							LocalShop shop = this.localRemote.getLocalShop(params);
							
							LocalItem item = this.localRemote.getLocalItemById(Integer.valueOf(batch.getItemId()));	
						
							Map<String, Object> map = new HashMap<String, Object>();	
							map.put("shopName", shop.getName());
							map.put("itemName", item.getName());
							if ("true".equals(batch.getIsHigh())) {
								map.put("isHigh", "(自定义)");
							}else{
								map.put("isHigh", "(标板)");
							}
							map.put("sku", (item.getSku()==null?"":item.getSku()));
							map.put("barCode", item.getBarCode());
							map.put("code", batch.getOrderCode());
							map.put("num", batch.getNum());
							printList.add(map);
						
							//更新单据状态
							params.clear();
							params.put("id", ids[i]);
							params.put("status", OrderStatusEnums.WMS_PRINT);
							this.localRemote.updateBatchByParams(params);
							
							LocalOperateRecord record = new LocalOperateRecord();
							record.setBatchCode(batch.getOrderCode());
							record.setFastCode(batch.getEntryCode());
							record.setModel("print");
							record.setOperateId(""+this.getCurrentUser().getId());
							String description = "批次单据打印："+batch.getOrderCode()+"|"+batch.getEntryCode()+"|"+shop.getName()+"|"+item.getName()+"|"+item.getSku()+"|"+batch.getNum();
							record.setDescription(description);
							this.localRemote.saveOperateRecord(record);
							
						}
						
					}	
					model.put("list", printList);
					return "/local/toPrintPreview";
				}
}
