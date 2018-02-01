package com.xinyu.controller.trade;

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

import com.xinyu.common.BaseController;
import com.xinyu.model.base.User;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.trade.CheckOut;
import com.xinyu.service.system.CheckOutService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;

/**
 * 出库记录信息控制
 * */
@Controller
@RequestMapping(value = "checkOut")
public class CheckOutController extends BaseController{
	
	@Autowired
	private CheckOutService checkOutService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemItemService systemItemService;
	
	/**
	 * 出库记录列表
	 * @param HttpServletRequest
	 * @param ModelMap
	 * @return 
	 * */
	@RequestMapping(value="checkoutList")
	public String CheckOutList(HttpServletRequest request,ModelMap model){
		
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		
		String type=StoreSystemItemEnums.WAYBILL.getKey();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("type", type);
		List<SystemItem> expressList = this.systemItemService.findSystemItemByList(params);
		model.put("expressList",expressList);
		
		return "admin/checkOut/checkOutList";
	}

	/**
	 * 出库记录信息列表数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> CheckOutListData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		
		if (rows==10) {
			rows = 100;
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String express = request.getParameter("express");
		String status = request.getParameter("status");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String searchText = request.getParameter("searchText");
		params.put("userId", userId);
		params.put("express", express);
		params.put("status", status);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		params.put("searchText", searchText);
//		System.err.println("params:"+params);
		List<CheckOut> checkOuts = this.checkOutService.findCheckOutPage(params,page,rows);
		int total = this.checkOutService.getTotal(params);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("rows", this.checkOutService.buildData(checkOuts));
		resultMap.put("total", total);
		resultMap.put("page", page);
		
		return resultMap;
	}
}
