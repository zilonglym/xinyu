package com.xinyu.controller.inventory;

import java.io.FileNotFoundException;
import java.io.IOException;
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
import com.xinyu.model.base.User;
import com.xinyu.model.inventory.InventoryRecord;
import com.xinyu.model.util.InventoryExcelReader;
import com.xinyu.service.inventory.InventoryRecordService;
import com.xinyu.service.inventory.InventoryService;
import com.xinyu.service.system.UserService;





/**
 * 库存相关控制类
 * */
@Controller
@RequestMapping(value = "inventory")
public class InventoryController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private InventoryService inventoryService;
	
	@Autowired
	private InventoryRecordService inventoryRecordService;
	
	
	
	/**
	 * 商品库存列表页面
	 * @param request
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="list")
	public String inventoryList(HttpServletRequest request,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/inventory/inventoryList";
	}
	
	/**
	 * 商品库存列表数据填充
	 * @param request
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String, Object> inventoryListData(HttpServletRequest request,ModelMap model,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		if (rows==10) {
			rows=100;
		}
		String userId = request.getParameter("userId");
		String searchText = request.getParameter("searchText");
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("userId", userId);
		params.put("searchText", searchText);
		//List<Inventory> inventories = this.inventoryService.findInventoryByPage(params,page,rows);
		List<Map<String, Object>> inventories = this.inventoryService.findInventoryByPage(params,page,rows);
		int total = this.inventoryService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("page", page);
		retMap.put("total", total);
		retMap.put("rows", this.inventoryService.buildListData(inventories));
		return retMap;
	}
	
	/**
	 * 库存流水记录表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="inventoryRecord/list")
	public String InventoryRecordList(ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/inventory/inventoryRecordList";
	}
	
	/**
	 * 出库流水记录数据
	 * @param page
	 * @param rows
	 * @param roquest
	 * @return map
	 * */
	@RequestMapping(value="inventoryRecord/listData")
	@ResponseBody
	public Map<String, Object> InventoryRecordListData(HttpServletRequest request,
			@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		String userId = request.getParameter("userId");
		String searchText = request.getParameter("searchText");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId",userId);
		params.put("searchText",searchText);
		List<InventoryRecord> records = this.inventoryRecordService.findInventoryRecordsByPage(params,page,rows);
		int total = this.inventoryRecordService.getTotal(params);
		Map<String, Object> retMap = new HashMap<String,Object>();
		retMap.put("page", page);
		retMap.put("rows", this.inventoryRecordService.buildListData(records));
		retMap.put("total", total);
		return retMap;
	}
	
	/**
	 * 库存初始化页面
	 * */
	@RequestMapping(value = "batchAdd" ,method = RequestMethod.GET)
	public String upload() throws ApiException {
		return "/admin/inventory/batchInventory";
	}
	
	/**
	 *  库存初始化
	 * */
	@RequestMapping(value = "upload", method=RequestMethod.POST)
	public String uploadItems(@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model) 
		throws FileNotFoundException, IOException {
		InventoryExcelReader inventory  =new InventoryExcelReader(file.getInputStream());
		List<Map<String, Object>> list = inventory.getInventory();
		this.inventoryService.inItInventory(list);
		return "/admin/inventory/batchInventory";
	}
	
}
