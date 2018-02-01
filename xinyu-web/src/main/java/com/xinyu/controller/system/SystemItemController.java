package com.xinyu.controller.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.User;
import com.xinyu.model.common.Seqence;
import com.xinyu.model.order.StockOrderOperator;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.Notice;
import com.xinyu.model.system.SystemItem;
import com.xinyu.model.system.enums.StoreSystemItemEnums;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.service.order.StockOrderOperatorService;
import com.xinyu.service.system.AccountRoleRelationService;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.ItemOperatorService;
import com.xinyu.service.system.SystemItemService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.trade.ShipOrderOperatorService;

/**
 * 系统参数设置控制类
 * 
 * */
@Controller
@RequestMapping(value="systemItem")
public class SystemItemController extends BaseController{
	
	@Autowired
	private SystemItemService systemItemService;
	
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private ShipOrderOperatorService orderOperatorService;
	
	@Autowired
	private StockOrderOperatorService stockOrderOperatorService;
	
	@Autowired
	private ItemOperatorService itemOperatorService;
	
	/**
	 *系统参数设置列表
	 *@param HttpServletRequest
	 *@param ModelMap
	 *@return String
	 * */
	@RequestMapping(value="/systemItemList")
	public String SystemItemList(HttpServletRequest request,ModelMap model){
		return "admin/system/systemItemList";
	}
	
	/**
	 *系统参数设置列表数据填充
	 *@param HttpServletRequest
	 *@param page
	 *@param rows
	 *@return Map<String,Object>
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		
		if (rows==10) {
			rows=100;
		}
		
		Map<String,Object> params=new HashMap<String,Object>();
		String q=request.getParameter("q");
		params.put("q",q);
		List<SystemItem> systemItems = this.systemItemService.findSystemItemByList(params);	
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("rows",this.systemItemService.buildListData(systemItems));
		result.put("page",page);
		result.put("total",systemItems.size());
		return result;
	}
	
	/**
	 * 添加系统参数
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="f7Add")
	public String SystemItemAdd(ModelMap model){
		
		List<Map<String, Object>> types = new ArrayList<Map<String,Object>>();
		
		for(StoreSystemItemEnums s: StoreSystemItemEnums.values()){
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("key", s.getKey());
           map.put("value", s.getDescription());
           types.add(map);
        }
		
		model.put("types", types);
		
		return "admin/system/systemItemAdd";
	}
	
	/**
	 * 修改系统参数
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="f7Edit")
	public String SystemItemEdit(ModelMap model,HttpServletRequest request){
		
		String id = request.getParameter("id");
		SystemItem systemItem = this.systemItemService.findSystemItemById(id);
		model.put("systemItem", systemItem);
		
		List<Map<String, Object>> types = new ArrayList<Map<String,Object>>();
		for(StoreSystemItemEnums s: StoreSystemItemEnums.values()){
           Map<String, Object> map = new HashMap<String, Object>();
           map.put("key", s.getKey());
           map.put("value", s.getDescription());
           types.add(map);
        }
		model.put("types", types);
		
		return "admin/system/systemItemEdit";
	}
	
	/**
	 * 删除系统参数
	 * @param model
	 * @return map
	 * */
	@RequestMapping(value="delete")
	@ResponseBody
	public Map<String, Object> SystemItemDelete(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String,Object>();
		String id = request.getParameter("id");
		this.systemItemService.deleteById(id);
		map.put("ret", "sucess");
		return map;
	}
	
	/**
	 * 系统参数信息提交更新
	 * @param HttpServletRequest
	 * @return map
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String,Object> SystemItemSave(HttpServletRequest request) throws JSONException{
		
		Map<String,Object> resultMap = new HashMap<String, Object>();
		
		String json = request.getParameter("json");
		JSONObject object = new JSONObject(json);
		String value = object.getString("value");
		String description = object.getString("description");
		String type = object.getString("type");
		String id = object.getString("id");
		
		if (id.isEmpty()) {
			
			SystemItem systemItem = new SystemItem();
			
			systemItem.generateId();
			
			systemItem.setDescription(description);
			
			systemItem.setValue(value);
			
			for(StoreSystemItemEnums s: StoreSystemItemEnums.values()){
		         if (s.getKey().equals(type)) {
		        	 systemItem.setType(s);
				}
		     }
			
			systemItem.setParentId(0);
			
			systemItem.setSeq(Integer.parseInt(Seqence.getInstance().next()));
			
			this.systemItemService.insertSystemItem(systemItem);
			
			resultMap.put("ret", "insert");
		}else {
			
			SystemItem systemItem = this.systemItemService.findSystemItemById(id);
			
			systemItem.setDescription(description);
			
			systemItem.setValue(value);
			
			for(StoreSystemItemEnums s: StoreSystemItemEnums.values()){
		         if (s.getKey().equals(type)) {
		        	 systemItem.setType(s);
				}
		     }
			
			systemItem.setParentId(0);
			
			systemItem.setSeq(Integer.parseInt(Seqence.getInstance().next()));
			
			this.systemItemService.updateSystemItem(systemItem);
			
			resultMap.put("ret", "update");
		}
		return resultMap;
	}
	

	/**
	 * 操作日志列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="operatorList")
	public String ShipOrderOperatorList(ModelMap model){
		List<Account> accounts = this.accountService.findAccountsByList(null);
		model.put("accounts", accounts);
		return "admin/system/operatorList";
	}
	
	/**
	 * 操作日志列表数据
	 * @param page
	 * @param map
	 * @param rows
	 * @return map
	 * */
	@RequestMapping(value="operatorListData")
	@ResponseBody
	public Map<String, Object> ShipOrderOperatorListData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows){
		
		if (rows==10) {
			rows=100;
		}
		
		Map<String, Object> retMap = new HashMap<String, Object>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		String accountId = request.getParameter("accountId");
		String searchText = request.getParameter("searchText");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String type = request.getParameter("type");
		params.put("accountId", accountId);
		params.put("startDate", startDate);
		params.put("endDate", endDate);
		
		//操作日志判断获取数据
		if (type!=null) {
			if (type.equals("shipOrder")) {
				params.put("searchText", searchText);
				//订单日志
				List<ShipOrderOperator> shipOrderOperators = this.orderOperatorService.getShipOrderOperatorByPage(params, page, rows);
				int total = this.orderOperatorService.getTotal(params);
				retMap.put("rows", this.orderOperatorService.buildListData(shipOrderOperators));
				retMap.put("total", total);
			}else if(type.equals("item")){
				params.put("searchText", searchText);
				//商品日志
				List<ItemOperator> itemOperators = this.itemOperatorService.getItemOperatorByPage(params, page, rows);
				int total = this.itemOperatorService.getTotal(params);
				retMap.put("rows", this.itemOperatorService.buildListData(itemOperators));
				retMap.put("total", total);
			}else if(type.equals("inventory")){
				params.put("description", searchText);
				//出入库和盘点单的操作日志
				List<StockOrderOperator> stockOrderOperators = this.stockOrderOperatorService.getStockOrderOperatorsByPage(params,page,rows);
				int total = this.stockOrderOperatorService.getTotal(params);
				retMap.put("rows", this.stockOrderOperatorService.buildListData(stockOrderOperators));
				retMap.put("total", total);
			}
			retMap.put("page", page);		
		}else{
			retMap.put("rows", "");
			retMap.put("total", 0);
		}
		return retMap;
	}
	
	/**
	 * 系统公告添加
	 */
	@RequestMapping("f7AddNotice")
	public String addNotice(){
		return "/admin/system/addNotice";
	}
	
	/**
	 * 系统公告添加
	 */
	@RequestMapping("saveNotice")
	@ResponseBody
	public Map<String, Object> saveNotice(HttpServletRequest request){
		String msg = request.getParameter("msg");
		Notice notice = this.systemItemService.findNoticeById(null);
		if (notice!=null) {
			notice.setMsg(msg.replaceAll("\n", "<br>"));
			notice.setAccount(this.getCurrentAccount());
			notice.setLastUpdate(new Date());
			this.systemItemService.updateNotice(notice);
		}else {
			Notice notice2 = new Notice();
			notice2.generateId();
			notice2.setMsg(msg.replaceAll("\n", "<br>"));
			notice2.setAccount(this.getCurrentAccount());
			notice2.setLastUpdate(new Date());
			this.systemItemService.insertNotice(notice2);
		}
		return null;
	}
	
}
