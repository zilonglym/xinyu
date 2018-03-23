package com.xinyu.controller.report;

import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ReceiverInfo;
import com.xinyu.model.base.User;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.model.util.POIModel;
import com.xinyu.service.report.ReportService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ReceiverInfoService;
import com.xinyu.service.trade.ShipOrderService;
import com.xinyu.util.PoiExcelExport;

/**
 * 报表
 * */
@Controller
@RequestMapping(value = "/report")
public class ReportController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private ReceiverInfoService receiverInfoService;
	
	@Autowired
	private ShipOrderService shipOrderService;
	
	@Autowired
	private SystemItemService sysService;
	
	/**
	 * 发货统计
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value = "/ship")
	public String ship(Model model) {
		List<User> users = this.userService.getUserBySearch(null);
		model.addAttribute("users", users);
		
		String type = "waybill";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		List<SystemItem> companys = this.sysService.findSystemItemByList(params);
		model.addAttribute("companys", companys);
		
 		return "admin/report/reportShip";
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
	public Map<String,Object> ShipListData(HttpServletRequest request){
		
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cpCode = request.getParameter("cpCode");
		
		params.put("userId", userId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("cpCode", cpCode);
		params.put("status", "report");
		
		List<Map<String, Object>> ships = this.reportService.findShipCount(params);
		
		List<Map<String, Object>> itemCounts = this.reportService.findItemTotal(params);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("rows", this.reportService.buildListData(ships,itemCounts));
		resultMap.put("total", ships.size());
		
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
	public String shipItem(
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			@RequestParam(value = "cpCode") String cpCode,HttpServletResponse response) {	
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDate);
		p.put("endDate", endDate);
		p.put("tmsServiceCode", cpCode);
//		p.put("status", "WMS_FINASH");
//		System.err.println(p);
		
		List<Map<String,Object>> results = this.reportService.findItemCount(p);
		List<POIModel> poiModels=new ArrayList<POIModel>();	
	
		for(Map<String,Object> objectMap:results){
			
			POIModel poiModel=new POIModel();
			
			poiModel.setM0(objectMap.get("userName").toString());
					
			String itemId = objectMap.get("itemId").toString();
			Item item = this.itemService.getItem(itemId);
			poiModel.setM1(item.getName());
			
			poiModel.setM2(item.getItemCode());
			
			poiModel.setM3(item.getBarCode());
			
			poiModel.setM6(item.getGoodsNo());
			
			poiModel.setM5(item.getColor()+";"+item.getSpecification());
			
			String numStr = objectMap.get("sum").toString();
			Double num = Double.valueOf(numStr);
			int i = (new Double(num)).intValue();
			poiModel.setM4(String.valueOf(i));
			
			poiModels.add(poiModel);
		}
		
		User user = this.userService.getUserById(userId);
		//时间格式化
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//拼接Excel文件名称
//		String filename=desktopPath+sdf.format(new Date())+user.getShopName()+"发货明细.xls";
		
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,user.getSubscriberName()+"商品汇总","sheet1");
		
		//Excel文件填充内容属性
		String titleColumn[] = {"m0","m1","m2","m6","m5","m3","m4"};  
       
		//Excel文件填充内容列名
		String titleName[] = {"店铺名称","商品名称","商品编码","产品编码","SKU","商品条码","数量"};  
		
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20};  
		
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
		
        return null; 
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
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "endDate") String endDay,
			@RequestParam(value = "cpCode") String cpCode,HttpServletResponse response) throws IOException {
		long start = new Date().getTime();
		//当前导出商家ID
		User user=this.userService.getUserById(userId);
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
 		p.put("status", "report");
 		p.put("tmsServiceCode", cpCode);
 		
		//新建shipOrder数组存放新shipOrder对象
		List<POIModel> poiModels=new ArrayList<POIModel>();	
		List<ShipOrder> orders = this.shipOrderService.getShipOrderListByAll(p);
		
		for(ShipOrder order:orders){
			
			ReceiverInfo receiverInfo =order.getReceiverInfo();
			
			TmsOrder tmsOrder=order.getTmsOrder();
			
			//新建一个shipOrder对象
			POIModel poiModel=new POIModel();
			
			//对新shipOrder对象进行赋值
			//订单号
			poiModel.setM1(order.getErpOrderCode());
			
			if (order.getShopName()!=null) {
				poiModel.setM2(order.getShopName());
			}else {
				poiModel.setM2(user.getSubscriberName());
			}
			
			//时间
			poiModel.setM3(sf.format(order.getCreateTime()));
			
			//昵称
			poiModel.setM4(receiverInfo.getReceiverNick());
			
			//仓库备注
			String remark = "留言："+(order.getRemark()==null?"":order.getRemark())+" 卖家："+(order.getSellerMessage()==null?"":order.getSellerMessage())+" 买家:"+(order.getBuyerMessage()==null?"":order.getBuyerMessage());
			poiModel.setM5(remark);
			
			//物流公司
			poiModel.setM6(tmsOrder.getCode());
			
			//物流单号
			poiModel.setM7(tmsOrder.getOrderCode());
			
			//收件人
			poiModel.setM8(receiverInfo.getReceiverName());
			
			//收件地址
			poiModel.setM9(receiverInfo.getReceiverProvince()+receiverInfo.getReceiverCity()+receiverInfo.getReceiverArea()+receiverInfo.getReceiverAddress());
			
			//联系方式
			String phone=(receiverInfo.getReceiverMobile()==null?"":receiverInfo.getReceiverMobile())+","+(receiverInfo.getReceiverPhone()==null?"":receiverInfo.getReceiverPhone());
			poiModel.setM0(phone);
			
			//重量
			poiModel.setM10(String.valueOf(order.getTotalWeight()));		
			
			//商品明细
			poiModel.setM11(tmsOrder.getItems());
			
			//收件省份
			poiModel.setM12(receiverInfo.getReceiverProvince());
			
			//添加到新shipOrder数组
			poiModels.add(poiModel);
		}		
		
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//拼接Excel文件名称
//		String filename=desktopPath+sdf.format(new Date())+user.getShopName()+"发货明细.xls";
		
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,sdf.format(new Date())+user.getSubscriberName()+"发货明细","sheet1");
		
		//Excel文件填充内容属性
		String titleColumn[] = {"m2","m3","m1","m4","m6","m7","m8","m0","m12","m9","m11","m10","m5"};  
       
		//Excel文件填充内容列名
		String titleName[] = {"店铺名称","更新时间","订单号","昵称","物流公司","物流单号","收件人","联系方式","省","收件地址","商品明细","重量(g)","备注"};  
		
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20,20,20,20,20,20,20};  
		
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
        
        long end = new Date().getTime();
        
        System.err.println("发货明细导出耗时："+(end - start));
        
        return null; 
	}
	
	
	/**
	 * 发货明细单xls导出
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @param cpCode
	 * @return 
	 * @throws IOException 
	 * */
	@RequestMapping(value = "/order/xls")
	public String shipOrderReport(	
			@RequestParam(value = "userId") String userId,
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "endDate") String endDay,
			@RequestParam(value = "cpCode") String cpCode,HttpServletResponse response) throws IOException {
		long start = new Date().getTime();
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		p.put("tmsServiceCode", cpCode);
		p.put("status", "report");
		
		//新建shipOrder数组存放新shipOrder对象
		List<POIModel> poiModels=new ArrayList<POIModel>();	
		List<ShipOrder> orders = this.shipOrderService.getShipOrderListByAll(p);
		
		for(ShipOrder order:orders){
			
			ReceiverInfo receiverInfo =order.getReceiverInfo();
			
			TmsOrder tmsOrder=order.getTmsOrder();
			
			//新建一个shipOrder对象
			POIModel poiModel=new POIModel();
			
			//对新shipOrder对象进行赋值
			//订单号
			poiModel.setM1(order.getErpOrderCode());
			
			User user = this.userService.getUserById(""+order.getUser().getId());	
			poiModel.setM2(user.getSubscriberName());
				
			//时间
			poiModel.setM3(sf.format(order.getCreateTime()));
			
			//昵称
			poiModel.setM4(receiverInfo.getReceiverNick());
			
			//物流公司
			poiModel.setM5(tmsOrder.getCode());
			
			//物流单号
			poiModel.setM6(tmsOrder.getOrderCode());
			
			//收件人
			poiModel.setM7(receiverInfo.getReceiverName());
			
			//联系方式
			String phone=(receiverInfo.getReceiverMobile()==null?"":receiverInfo.getReceiverMobile())+","+(receiverInfo.getReceiverPhone()==null?"":receiverInfo.getReceiverPhone());
			poiModel.setM8(phone);
			
			//收件地址
			poiModel.setM9(receiverInfo.getReceiverProvince());
			
			poiModel.setM10(receiverInfo.getReceiverCity());
			
			poiModel.setM11(receiverInfo.getReceiverArea());
			
			poiModel.setM12(receiverInfo.getReceiverAddress());

			
			//重量
			poiModel.setM13(String.valueOf(order.getTotalWeight()));		
			
			//商品明细
			poiModel.setM14(tmsOrder.getItems());
			
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
		String titleColumn[] = {"m1","m2","m3","m4","m5","m6","m7","m8","m9","m10","m11","m12","m14","m13"};  
       
		//Excel文件填充内容列名
		String titleName[] = {"订单号","店铺","时间","昵称","物流公司","物流单号","收件人","联系方式","省","市","区","地址","商品明细","重量(g)"};  
		
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20,20,20,20,20,20,20,20};  
		
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
        
        long end = new Date().getTime();
        
        System.err.println("发货明细导出耗时："+(end - start));
        
        return null; 
	}
	
	/**
	 * 未出库xls导出
	 * @param startDate
	 * @param endDate
	 * @return 
	 * @throws IOException 
	 * */
	@RequestMapping(value = "/notExist/xls")
	public String notExistReport(	
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,HttpServletResponse response) throws IOException {
		long start = new Date().getTime();
	
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("startDate", startDate);
		p.put("endDate", endDate);
		
		//新建shipOrder数组存放新shipOrder对象
		List<POIModel> poiModels=new ArrayList<POIModel>();	
		
		List<Map<String, Object>> noList = this.checkOutService.findNotExist(p,1,Integer.MAX_VALUE);
		
		for(Map<String, Object> map:noList){
			
			//新建一个shipOrder对象
			POIModel poiModel=new POIModel();
			
			//对新shipOrder对象进行赋值
			//时间
			poiModel.setM1(""+map.get("lastUpdateTime"));
			
			//商家
			poiModel.setM2(""+map.get("subscriberName"));
				
			//物流公司
			poiModel.setM3(""+map.get("code"));
			
			//物流单号
			poiModel.setM4(""+map.get("orderCode"));
			
			//昵称
			poiModel.setM5(""+map.get("receiverNick"));
			
			//收件人
			poiModel.setM6(""+map.get("receiverName"));
			
			//联系方式
			String phone=map.get("receiverMobile")+","+map.get("receiverPhone");
			poiModel.setM7(phone);
			
			//收件地址
			poiModel.setM8(""+map.get("receiverProvince"));
			
			poiModel.setM9(""+map.get("receiverCity"));
			
			poiModel.setM10(""+map.get("receiverArea"));
			
			poiModel.setM11(""+map.get("receiveTown"));
			
			poiModel.setM12(""+map.get("receiverAddress"));
	
			//重量
			poiModel.setM13(""+map.get("totalWeight"));		
			
			//商品明细
			poiModel.setM14(""+map.get("items"));
			
			//添加到新shipOrder数组
			poiModels.add(poiModel);
		}		
		
		//时间格式化
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		
		//新建PoiExcelExport对象
		PoiExcelExport pee = new PoiExcelExport(response,"菜鸟未出库统计","sheet1");
		
		//Excel文件填充内容属性
		String titleColumn[] = {"m1","m2","m3","m4","m5","m6","m7","m8","m9","m10","m11","m12","m14","m13"};  
       
		//Excel文件填充内容列名
		String titleName[] = {"时间","店铺","物流公司","物流单号","昵称","收件人","联系方式","省","市","区","县","地址","商品明细","重量(kg)"};  
		
		//Excel文件填充内容列宽
		int titleSize[] = {20,20,20,20,20,20,20,20,20,20,20,20,20,20};  
		
		//调用PoiExcelExport导出Excel文件
        pee.wirteExcel(titleColumn, titleName, titleSize, poiModels);
        
        long end = new Date().getTime();
        
        System.err.println("未出库明细导出耗时："+(end - start));
        
        return null; 
	}
}
