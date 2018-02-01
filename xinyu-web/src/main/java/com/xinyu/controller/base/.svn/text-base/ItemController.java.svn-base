package com.xinyu.controller.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taobao.api.ApiException;
import com.xinyu.common.BaseController;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.ItemTypeEnum;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.service.caoniao.WmsItemQueryCpService;
import com.xinyu.service.system.ItemOperatorService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;

/**
 * 商品信息操作类
 */
@Controller
@RequestMapping(value = "item")
public class ItemController extends BaseController {
	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemOperatorService operatorService;
	@Autowired
	private UserService userService;
	@Autowired
	private WmsItemQueryCpService itemQueryService;

	/**
	 * 商品管理页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "list")
	public String toItemList(ModelMap model) {
		//添加仓库ID参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("cu", this.getCurrentAccount().getCu());
		List<User> userList = this.userService.getUserBySearch(params);
		model.put("users", userList);
		return "admin/item/itemList";
	}
	/**
	 * 菜鸟商品查询
	 * @return
	 */
	@RequestMapping(value="wms/query")
	@ResponseBody
	public Map<String,Object> wmsItemQuery(){
		String userId=this.getString("userId");
		String ids=this.getString("itemIds");
		List<Long> itemIds=new ArrayList<Long>();
		String[] idList=ids.split(",");
		for(int i=0;idList!=null && i<idList.length;i++){
			itemIds.add(Long.valueOf(idList[i]));
		}
		List<Item> itemList=this.itemQueryService.orderItemQuery(userId, itemIds, null);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("itemList", itemList);
		return resultMap;
	}

	/**
	 * 商品列表数据查询
	 * @param page
	 * @param rows
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "listData")
	@ResponseBody
	public Map<String, Object> itemlistData(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "100") int rows, HttpServletRequest request) {
		if (rows == 10) {
			rows = 100;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> params = new HashMap<String, Object>();
		String searchText = request.getParameter("searchText");
		String userId = request.getParameter("userId");
		params.put("searchText", searchText);
		params.put("userId", userId);
		
		//添加仓库ID参数
		params.put("cu", this.getCurrentAccount().getCu());
		
		List<Item> items = this.itemService.getItemsByPage(page, rows, params);
		int total = this.itemService.getTotal(params); 
		resultMap.put("total", total);
		resultMap.put("page", page);
		resultMap.put("rows", this.itemService.buildItemList(items));
		return resultMap;
	}

	/**
	 * 商品条码自动生成并保存
	 * @return
	 * */
	@RequestMapping(value = "generateBarCode")
	@ResponseBody
	public Map<String, Object> generateBarCode() {
		String id = this.getString("id");
		Item item = this.itemService.getItem(id);
		String oldCode = item.getBarCode();
		Map<String, Object> resultMap = this.itemService.generateBarCode(item);
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(id+"|"+item.getBarCode());
		operator.setOldValue(id+"|"+oldCode);
		operator.setDescription("商品条码生成："+id+"|"+item.getBarCode());
		this.operatorService.insertItemOperator(operator);
		
		return resultMap;
	}

	/**
	 * 转向至商品条码修改页面
	 * @param request
	 * @param model
	 * @return String
	 */
	@RequestMapping(value = "barCodef7Page")
	public String toAddbarCode(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		model.put("item", item);
		return "admin/item/itemf7barCode";
	}

	/**
	 * 提交修改好的商品条形码
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "updateBarCode")
	@ResponseBody
	public Map<String, Object> updateBarCode(HttpServletRequest request) {
		
		String itemId = request.getParameter("itemId");
		String barCode = request.getParameter("barCode");
		Item item = this.itemService.getItem(itemId);
		
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(itemId+"|"+barCode);
		operator.setOldValue(itemId+"|"+item.getBarCode());
		operator.setDescription("商品条码修改："+itemId+"|"+barCode);
		this.operatorService.insertItemOperator(operator);
		
		item.setBarCode(barCode);
		this.itemService.updateItem(item);
			
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 跳转至商品重量修改页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7PackageWeight")
	public String topackageWeight(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", item.getId());
		map.put("name", item.getName());
		map.put("grossWeight", String.valueOf(item.getGrossWeight()));
		model.put("item", map);
		
		return "admin/item/itemf7packageWeight";
	}

	/**
	 * 提交修改好的商品的重量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitpackageWeight")
	@ResponseBody
	public Map<String, Object> updatePackageWeight(HttpServletRequest request) {
		
		String itemId = request.getParameter("itemId");
		String packageWeight = request.getParameter("packageWeight");
		String weight=request.getParameter("weight");
		Item item = this.itemService.getItem(itemId);
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(itemId+"|"+packageWeight+"|"+weight);
		operator.setOldValue(itemId+"|"+item.getWmsGrossWeight()+"|"+item.getGrossWeight());
		operator.setDescription("商品重量修改："+itemId+"|"+packageWeight);
		this.operatorService.insertItemOperator(operator);
		
		item.setWmsGrossWeight(Long.valueOf(packageWeight));
		item.setGrossWeight(Long.valueOf(weight));
		this.itemService.updateItem(item);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 跳转至商品名称修改页面
	 * @param model
	 * @param request
	 * @return string
	 */
	@RequestMapping(value = "tof7ItemTitle")
	public String toItemTitle(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		model.put("item", item);
		return "admin/item/itemf7Title";
	}
	
	
	/**
	 * 提交修改好的商品的名称
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemTitle")
	@ResponseBody
	public Map<String, Object> submitItemTitle(HttpServletRequest request) {
		
		String itemId = request.getParameter("itemId");
		String itemCode = request.getParameter("itemTitle");
		Item item = this.itemService.getItem(itemId);
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(itemId+"|"+itemCode);
		operator.setOldValue(itemId+"|"+item.getName());
		operator.setDescription("商品名称修改："+itemId+"|"+itemCode);
		this.operatorService.insertItemOperator(operator);
		
		item.setName(itemCode);
		this.itemService.updateItem(item);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	/**
	 * 单个商品入库页面
	 * @param model
	 * @param request
	 * @return string
	 */
	@RequestMapping(value = "tof7ItemCount")
	public String toItemCount(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		model.put("item", item);
		return "admin/item/itemf7Count";
	}
	
	/**
	 * 单个商品提交入库数量
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemCount")
	@ResponseBody
	public Map<String, Object> submitItemCount(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		String itemCount = request.getParameter("itemCount");
		Map<String, Object> resultMap = this.itemService.submitItemCount(itemId,itemCount);
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());		
		Item item = this.itemService.getItem(itemId);
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(itemId+"|"+itemCount);
		operator.setOldValue("");
		operator.setDescription("商品入库："+itemId+"|"+itemCount);
		this.operatorService.insertItemOperator(operator);
		
		System.err.println(resultMap);
		return resultMap;
	}
	
	
	/**
	 * 跳转至商品编码修改页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7ItemCode")
	public String toItemCode(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		model.put("item", item);
		return "admin/item/itemf7Code";
	}

	/**
	 * 提交修改商品的编码 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemCode")
	@ResponseBody
	public Map<String, Object> updateItemCode(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		String itemCode = request.getParameter("itemCode");
		Item item = this.itemService.getItem(itemId);
		
		//商品操作日志
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setAccount(this.getCurrentAccount());
		operator.setItem(item);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
		operator.setNewValue(itemId+"|"+itemCode);
		operator.setOldValue(itemId+"|"+item.getItemCode());
		operator.setDescription("商品编码修改："+itemId+"|"+itemCode);
		this.operatorService.insertItemOperator(operator);
		
		item.setItemCode(itemCode);
		this.itemService.updateItem(item);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("ret", 1);
		return resultMap;
	}

	

	/**
	 * 跳转至商品类型修改页面
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "tof7ItemType")
	public String toItemType(HttpServletRequest request, ModelMap model) {
		String id = request.getParameter("itemId");
		Item item = this.itemService.getItem(id);
		model.put("item", item);
		return "admin/item/itemf7Type";
	}
	

	/**
	 * 商品类型修改提交
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "submitItemType")
	@ResponseBody
	public Map<String, Object> submitItemType(HttpServletRequest request, ModelMap model) {
		Map<String, Object> retMap = new HashMap<String,Object>();
		String id = request.getParameter("id");
		String itemType = request.getParameter("itemType");
		Map<String, Object> params =new HashMap<String, Object>();
		params.put("id", id);
		params.put("itemType", itemType);
		try {
			
			//商品操作日志
			ItemOperator operator = new ItemOperator();
			operator.generateId();
			operator.setAccount(this.getCurrentAccount());
			Item item = this.itemService.getItem(id);
			operator.setItem(item);
			operator.setOperatorDate(new Date());
			operator.setOperatorModel(OperatorModel.ITEM_UPDATE);
			operator.setNewValue(id+"|"+itemType);
			operator.setOldValue(id+"|"+item.getItemType());
			operator.setDescription("商品类型修改："+id+"|"+itemType);
			this.operatorService.insertItemOperator(operator);
			
			this.itemService.updateItemType(params);
			retMap.put("ret", "success");
			retMap.put("msg", "商品类型修改成功！");
		} catch (Exception e) {
			e.printStackTrace();
			retMap.put("ret", "fail");
			retMap.put("msg", "商品类型修改失败！"+e.getMessage());			
		}
		return retMap;
	}
	
	/**
	 * 新建商品信息（已对接中仓系统商家不可用）
	 * @param HttpServletRequest
	 * @param ModelMap
	 * @return String
	 * */
	@RequestMapping(value="f7Add")
	public String ItemAdd(HttpServletRequest request,ModelMap model){
		List<User> users = this.userService.getUserBySearch(null);
		model.put("users", users);
		return "admin/item/itemAdd";
	}

	/**
	 * 提交新建商品信息
	 * @param HttpServletRequest
	 * @return 
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> ItemSave(HttpServletRequest request){
		
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		
		Map<String,Object> resultMap=new HashMap<String, Object>();
		
		String userId = object.getString("userId");
		String itemCode = object.getString("itemCode");
		String itemName = object.getString("itemName");
		String barCode = object.getString("barCode");
		String specification = object.getString("specification");
		String color = object.getString("color");
		String grossWeight = object.getString("grossWeight");
		String wmsGrossWeight = object.getString("wmsGrossWeight");
		
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("itemCode", itemCode);
		params.put("itemName", itemName);
		
		List<Item> items = this.itemService.getItemByList(params);
		if (items.size()>0) {
			resultMap.put("ret", "repeat");
		}else {
			Item item = new Item();
			item.generateId();
			
			User user = this.userService.getUserById(userId);
			item.setUser(user);
			
			item.setItemCode(itemCode);
			
			item.setName(itemName);
			
			item.setBarCode(barCode);
			
			item.setSpecification(specification);
			
			item.setColor(color);
			
			item.setGrossWeight(Long.parseLong(grossWeight));
			
			item.setWmsGrossWeight(Long.parseLong(wmsGrossWeight));
			
			item.setType(ItemTypeEnum.NORMAL);
			
			this.itemService.saveItem(item);
			
			resultMap.put("ret", "success");
		}
		return resultMap;
	}
	
	@RequestMapping(value = "toPoint")
	public String toPoint(ModelMap model ,	
			@RequestParam(value = "itemId", defaultValue = "1") String itemId,
			@RequestParam(value = "count", defaultValue = "1") Long count) throws ApiException {
		Item item = this.itemService.getItem(itemId);
		
		User user = userService.getUserById(item.getUser().getId());
		
		String barCode = item.getBarCode();
		
		if (barCode.length()>14) {
			model.put("barCode", "128A");
		}else {
			model.put("barCode", "EAN13");
		}
		
		model.put("user", user);
		model.put("item", item);
		model.put("count", count);
		
		return "admin/print/point";
	}

	
}
