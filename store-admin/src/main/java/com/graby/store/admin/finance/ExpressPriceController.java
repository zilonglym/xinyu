package com.graby.store.admin.finance;
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

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.util.RecordExcelReader;
import com.graby.store.entity.ExpressPrice;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.remote.ExpressPriceRemote;
import com.graby.store.remote.RecordRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.UserRemote;

/**
 * 运费计算规则设置操作类
 * */
@Controller
@RequestMapping(value="expressPrice")
public class ExpressPriceController {
	
	@Autowired
	private ExpressPriceRemote expressPriceRemote;
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private SystemItemRemote sysRemote;
	
	
	/**
	 * 运费设置列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="list")
	public String ExpressPriceList(ModelMap model){
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList", userList);
		String type="LOGISTICS";
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		return "finance/expressPriceList";
	}
	
	/**
	 * 运费设置列表数据加载
	 * @param request
	 * @param page
	 * @param rows
	 * @return result
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> ExpressPriceDataList(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
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
		List<ExpressPrice> expressPrices=this.expressPriceRemote.findExpressPrices(params, page, rows);
		int total=this.expressPriceRemote.getTotalResult(params);
		result.put("page", page);
		result.put("rows", expressPrices);
		result.put("total", total);
		return result;
	}
	
	/**
	 * 新建运费设置
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Add")
	public String expressPriceAdd(ModelMap model){
		String type="LOGISTICS";
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList", userList);
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		return "finance/expressPriceAdd";
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
		params.put("id",id);
		String type="LOGISTICS";
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList", userList);
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		ExpressPrice expressPrice=this.expressPriceRemote.findExpressPriceByParam(params);
		model.put("expressPrice", expressPrice);
		return "finance/expressPriceEdit";
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
				expressPrice.setArea(area);
				expressPrice.setDeliveryPrice(Double.parseDouble(deliveryPrice));
				expressPrice.setFirstPrice(Double.parseDouble(firstPrice));
				expressPrice.setInitialPrice(Double.parseDouble(initialPrice));
				expressPrice.setOtherPrice(Double.parseDouble(otherPrice));
				expressPrice.setFirstCost(Double.valueOf(firstCost));
				expressPrice.setInitialCost(Double.valueOf(initialCost));
				expressPrice.setDeliveryCost(Double.valueOf(deliveryCost));
				expressPrice.setOtherCost(Double.valueOf(otherCost));
				expressPrice.setUserId(Long.parseLong(userId));
				User user=this.userRemote.getUser(Long.parseLong(userId));
				expressPrice.setUserName(user.getShopName());
				SystemItem systemItem=this.sysRemote.findSystemItem(Integer.parseInt(sysId));
				expressPrice.setCode(systemItem.getValue());
				expressPrice.setName(systemItem.getDescription());
				this.expressPriceRemote.save(expressPrice);
				result.put("msg","运费设置成功！");
			}else {
				params.put("id", id);
				expressPrice=this.expressPriceRemote.findExpressPriceByParam(params);
				expressPrice.setArea(area);
				expressPrice.setDeliveryPrice(Double.parseDouble(deliveryPrice));
				expressPrice.setFirstPrice(Double.parseDouble(firstPrice));
				expressPrice.setInitialPrice(Double.parseDouble(initialPrice));			
				expressPrice.setOtherPrice(Double.parseDouble(otherPrice));	
				expressPrice.setFirstCost(Double.valueOf(firstCost));
				expressPrice.setInitialCost(Double.valueOf(initialCost));
				expressPrice.setDeliveryCost(Double.valueOf(deliveryCost));
				expressPrice.setOtherCost(Double.valueOf(otherCost));
				expressPrice.setUserId(Long.parseLong(userId));
				User user=this.userRemote.getUser(Long.parseLong(userId));
				expressPrice.setUserName(user.getShopName());
				if (sysId.contains("default")) {
//					System.err.println("sysId:"+sysId);
				}else {
					SystemItem systemItem=this.sysRemote.findSystemItem(Integer.parseInt(sysId));
					expressPrice.setCode(systemItem.getValue());
					expressPrice.setName(systemItem.getDescription());
				}
				this.expressPriceRemote.update(expressPrice);
				result.put("msg","运费更新成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("msg",e.getMessage());
		}		
		return result;
	}
	
}
