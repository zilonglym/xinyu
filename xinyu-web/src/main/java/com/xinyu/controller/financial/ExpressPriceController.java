package com.xinyu.controller.financial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.User;
import com.xinyu.model.financial.ExpressPrice;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.trade.TmsOrder;
import com.xinyu.service.financial.ExpressPriceService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.TmsOrderService;

/**
 * 运费设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping(value="price")
public class ExpressPriceController extends BaseController{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemItemService systemService;

	@Autowired
	private ExpressPriceService priceService;
	
	@Autowired
	private TmsOrderService orderService;
	
	@RequestMapping(value="list")
	public String expressPriceList(ModelMap model){
		
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", "waybill");
		List<SystemItem> systemItems = this.systemService.findSystemItemByList(params);
		model.put("companys", systemItems);
		
		return "admin/financial/priceList";
	}
	
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> listData(HttpServletRequest request,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		if (rows==10) {
			rows=100;
		}
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		String area=request.getParameter("area");
		String code=request.getParameter("code");
		String userId=request.getParameter("userId");
		params.put("area",area);
		params.put("code", code);
		params.put("userId", userId);
		int total=this.priceService.getTotalResult(params);
		params.put("start", (page-1)*rows);
		params.put("offset", rows);
		List<ExpressPrice> expressPrices=this.priceService.findExpressPriceList(params);
		System.err.println(total);
		result.put("page", page);
		result.put("rows", this.buildData(expressPrices));
		result.put("total", total);
		return result;
	}
	
	/**
	 * 封装数据
	 * @param expressPrices
	 * @return
	 */
	private List<Map<String, Object>> buildData(List<ExpressPrice> expressPrices) {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();
		for(ExpressPrice price:expressPrices){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", price.getId());
			User user = this.userService.getUserById(price.getUserId());
			map.put("userName", user.getSubscriberName());
			map.put("code", price.getCode());
			map.put("name", price.getName());
			map.put("area", price.getArea());
			map.put("firstCost", price.getFirstCost());
			map.put("firstPrice", price.getFirstPrice());
			map.put("initialCost", price.getInitialCost());
			map.put("initialPrice", price.getInitialPrice());
			map.put("deliveryCost", price.getDeliveryCost());
			map.put("otherCost", price.getOtherCost());
			map.put("deliveryPrice", price.getDeliveryPrice());
			map.put("otherPrice", price.getOtherPrice());
			results.add(map);
		}
		System.err.println(results);
		return results;
	}

	/**
	 * 新建运费设置
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Add")
	public String expressPriceAdd(ModelMap model){
		
		List<User> userList=this.userService.getUserBySearch(null);
		model.put("users", userList);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type","waybill");
		List<SystemItem> systemItems=this.systemService.findSystemItemByList(params);
		model.put("companys", systemItems);
		
		return "admin/financial/addPrice";
	}
	
	/**
	 * 修改编辑运费设置
	 * @param model
	 * @param request
	 * @return string
	 * */
	@RequestMapping(value="f7Edit")
	public String expressPriceEdit(ModelMap model,HttpServletRequest request){
		
		Map<String, Object> params=new HashMap<String, Object>();
		String id=request.getParameter("id");
	
		params.put("id", id);
		ExpressPrice price = this.priceService.findExpressPriceByParam(params);
		model.put("price", price);
		
		List<User> userList=this.userService.getUserBySearch(null);
		model.put("users", userList);
		
		params.clear();
		params.put("type","waybill");
		List<SystemItem> systemItems=this.systemService.findSystemItemByList(params);
		model.put("companys", systemItems);
		
		return "admin/financial/editPrice";
	}
	
	/**
	 * 提交保存运费设置信息
	 * @param request
	 * @return result
	 * @throws JSONException 
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> expressPriceSave(HttpServletRequest request) throws JSONException{
		Map<String, Object> result=new HashMap<String, Object>();
		Map<String, Object> params=new HashMap<String, Object>();
		ExpressPrice expressPrice=new ExpressPrice();
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id=object.getString("id");
		String userId=object.getString("userId");
		String sysId=object.getString("sysId");
		String area=object.getString("area");
		String firstPrice=object.getString("firstPrice");
		String initialPrice=object.getString("initialPrice");
		String deliveryPrice=object.getString("deliveryPrice");
		String otherPrice=object.getString("otherPrice");
		String firstCost=object.getString("firstCost");
		String initialCost=object.getString("initialCost");
		String deliveryCost=object.getString("deliveryCost");
		String otherCost=object.getString("otherCost");
		try {
			if (id.isEmpty()) {
				expressPrice.generateId();
				expressPrice.setArea(area);
				expressPrice.setDeliveryPrice(Double.parseDouble(deliveryPrice));
				expressPrice.setFirstPrice(Double.parseDouble(firstPrice));
				expressPrice.setInitialPrice(Double.parseDouble(initialPrice));
				expressPrice.setOtherPrice(Double.parseDouble(otherPrice));
				expressPrice.setFirstCost(Double.valueOf(firstCost));
				expressPrice.setInitialCost(Double.valueOf(initialCost));
				expressPrice.setDeliveryCost(Double.valueOf(deliveryCost));
				expressPrice.setOtherCost(Double.valueOf(otherCost));
				expressPrice.setUserId(userId);
			
				SystemItem systemItem=this.systemService.findSystemItemById(sysId);
				expressPrice.setCode(systemItem.getValue());
				expressPrice.setName(systemItem.getDescription());
				this.priceService.save(expressPrice);
				
				result.put("msg","运费设置成功！");
				
			}else {
				
				params.put("id", id);
				expressPrice=this.priceService.findExpressPriceByParam(params);
				
				expressPrice.setArea(area);
				expressPrice.setDeliveryPrice(Double.parseDouble(deliveryPrice));
				expressPrice.setFirstPrice(Double.parseDouble(firstPrice));
				expressPrice.setInitialPrice(Double.parseDouble(initialPrice));			
				expressPrice.setOtherPrice(Double.parseDouble(otherPrice));	
				expressPrice.setFirstCost(Double.valueOf(firstCost));
				expressPrice.setInitialCost(Double.valueOf(initialCost));
				expressPrice.setDeliveryCost(Double.valueOf(deliveryCost));
				expressPrice.setOtherCost(Double.valueOf(otherCost));
				expressPrice.setUserId(userId);
				
				if (sysId.contains("default")) {
//					System.err.println("sysId:"+sysId);
				}else {
					SystemItem systemItem=this.systemService.findSystemItemById(sysId);
					expressPrice.setCode(systemItem.getValue());
					expressPrice.setName(systemItem.getDescription());
				}
				
				this.priceService.update(expressPrice);
				
				result.put("msg","运费更新成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg",e.getMessage());
		}		
		return result;
	}
	
	@RequestMapping(value="count")
	@ResponseBody
	public Map<String, Object> refreshPrice(HttpServletRequest request){
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		String orderNo = request.getParameter("code");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderCode", orderNo);
		List<TmsOrder> orders = this.orderService.getTmsOrderByList(params);
		if (orders.size()>0) {
			TmsOrder order = orders.get(0);
			retMap = this.priceService.priceCalculate(order);
		}else {
			retMap.put("code", "500");
			retMap.put("msg",orderNo+"订单不存在！");
		}
		return retMap;
	}
	
}
