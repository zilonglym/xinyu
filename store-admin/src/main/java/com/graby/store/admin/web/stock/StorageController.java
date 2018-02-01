package com.graby.store.admin.web.stock;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Centro;
import com.graby.store.entity.CentroItem;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.StorageStatusEnums;
import com.graby.store.entity.enums.StoreSystemItemEnums;
import com.graby.store.remote.CentroItemRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.StorageRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.web.auth.ShiroContextUtils;


/**
 * 入库单控制类
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="storage")
public class StorageController extends BaseController {
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private CentroRemote centroRemote;
		
	@Autowired
	private ReportRemote reportRemote;
	
	@Autowired
	private StorageRemote storageRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private CentroItemRemote centroItemRemote;
	
	
	
	/**
	 * 入库单列表页面
	 * @param request
	 * @param model
	 * @return
	 */

	@RequestMapping(value="index")
	public String index(HttpServletRequest request,ModelMap model){
		String userId  =  request.getParameter("userId");
		List<User> userList=this.userRemote.findAll(null);
		model.put("users", userList);
		Map<String,Object>  params  = new HashMap<String, Object>();
		if (userId != null && !"null".equals(userId)) {
			params.put("userId", userId);
			model.put("userId", userId);
		}	
		List<Storage> storageList = storageRemote.getStorageList(params);
		model.put("storageList", storageList);
		return "storage/StorageList";
	}
	
	
	// 商品列表
		@RequestMapping(value = "item/listData")
		@ResponseBody
		public Map<String,Object> listData(Model model) {
			Map<String,Object> params=new HashMap<String, Object>();			
			params.put("userId", this.getString("userId"));
			params.put("searchText", this.getString("searchText"));
			Page<Item> items = itemRemote.getItemsByPage(1, 50, params);
			Map<String,Object>  retMap  = new HashMap<String,Object>();
			retMap.put("rows", items.getContent());
			return retMap;
		}
	
		
	/**
	 * 入库单取数
	 * @return
	 */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,ModelMap model,@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		if(rows==10){
			rows=100;
		}
		String userId  =  request.getParameter("userId");
		System.out.println("userId:"+userId);
		if (userId != null && !"null".equals(userId)) {
			params.put("userId", userId);
		}
//		params.put("status", "ENTRY_CHECK_FINISH");
//		params.put("status1", "ENTRY_FINISH");
	    Page<Storage> orders=storageRemote.findStoragesList(page,rows,params);
        resultMap.put("rows",buildStorageListData(orders.getContent()));
        resultMap.put("total", storageRemote.findStoragesCount(params));
		return resultMap;
	}
	
	
	/**
	 * 重构组建List数据
	 * @param list
	 * @return
	 */
	private List<Map<String,Object>> buildStorageListData(List<Storage> list){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(int i=0;list!=null && i<list.size();i++){
			Map<String,Object> map=new HashMap<String, Object>();
			Storage storage=list.get(i);
			map.put("id", storage.getId());
			map.put("seq", i+1);
			map.put("orderNo", storage.getOrderNo());
			if (storage.getUser()!=null) {
				Long userId=storage.getUser().getId();
				User user=this.userRemote.getUser(userId);
				map.put("user", user.getShopName());
			}
			if (storage.getOperateTime() != null) {
				map.put("operateTime", sdf.format(storage.getOperateTime()));

			}else{
				map.put("operateTime", "无");
			}
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * 出库单列表页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="index_delivery")
	public String index_delivery(HttpServletRequest request,ModelMap model){
		String userId  =  request.getParameter("userId");
		
		List<User> userList=this.userRemote.findAll(null);
		model.put("users", userList);
		Map<String,Object>  params  = new HashMap<String, Object>();
		if (userId != null && !"null".equals(userId)) {
			params.put("userId", userId);
			model.put("userId", userId);
		}	
		params.put("status", StorageStatusEnums.ENTRY_DELIVERY.getKey());
		model.put("status", StorageStatusEnums.ENTRY_DELIVERY.getKey());
		List<Storage> storageList = storageRemote.getStorageList(params);
		model.put("storageList", storageList);
		return "storage/StorageDeliveryList";
	}
	
	/**
	 * 转到出库创建页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="f7toCreate_delivery")
	public String toCreateStorageDelivery(HttpServletRequest request,ModelMap model){
		String status=request.getParameter("status");
		List<User> userList=this.userRemote.findAll(null);
		model.put("users", userList);
		model.put("status", status);
		return "storage/StorageAddDelivery";
	}
	
	/**
	 * 转到创建页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="toCreate")
	public String toCreateStorage(HttpServletRequest request,ModelMap model){
		String userId=request.getParameter("userId");
		String status=request.getParameter("status");
		User user=this.userRemote.getUser(Long.valueOf(userId));
		Map<String, Object> params   =  new HashMap<String, Object>();
	    params.put("centorId",BaseResource.getCurrentCentroId() );
	    model.put("centroItems",  centroItemRemote.findCentroItems(params));
		model.put("status", status);
		model.put("user", user);
		return "storage/StorageAdd";
	}
		
	@RequestMapping(value="saveStorage")
	@ResponseBody
	public Map<String, Object> saveStorage(HttpServletRequest request,ModelMap model){
		String status=request.getParameter("status");
		String description=request.getParameter("description");
		
		String  jsonStr  =  request.getParameter("json");
		System.err.println(jsonStr.substring(1,jsonStr.length()-1));
		JSONObject json=new JSONObject(jsonStr.substring(1,jsonStr.length()-1));
        JSONArray date= json.getJSONArray("date");
        Storage  storage  = new Storage();
        int centroId = BaseResource.getCurrentCentroId();
        Long  userId  = json.getLong("userId");
        Long  centroItemId  = json.getLong("centroItemId");
        Centro  centro  = centroRemote.getCentroById(centroId);
        
        Map<String, Object> params   =  new HashMap<String, Object>();
        params.put("id", centroItemId);
        List<CentroItem> centroItems = centroItemRemote.findCentroItems(params);
        if(centroItems.size()>0){
        	storage.setCentroItem(centroItems.get(0));
        }
        storage.setCentro(centro);
        storage.setDescription(description);
//        if(status==null || status==""){
//        	storage.setStatus(StorageStatusEnums.ENTRY_WAIT_SELLER_SEND);
//        }else{
        	storage.setStatus(StorageStatusEnums.ENTRY_DELIVERY);
//        }
        storage.setUser(userRemote.getUser(userId));
        
        storage.setOperateTime(new Date(new java.util.Date().getTime()));
        
        List<StorageItem>  itemList  = new ArrayList<StorageItem>();
        for(int i = 0, size    = date.length();i<size ;i++ ){
        	JSONObject obj=date.getJSONObject(i);
        	StorageItem  storageItem = new StorageItem();
        	Long itmeId  =  Long.valueOf(""+obj.get("itemId"));
        	String barCode  = ""+obj.get("barCode");
        	itemRemote.updateItemBarCode(itmeId, barCode);
        	storageItem.setQuantity(Integer.valueOf(""+obj.get("quantity")));
        	storageItem.setCentro(centro);
        	Item item = itemRemote.getItem(itmeId);
        	storageItem.setItem(item);
        	itemList.add(storageItem);
        }
        storage.setItems(itemList);
        
        storageRemote.saveStorageAndAddInventory(storage);
        
		return null;
	}
	/**
	 * 显示单据详情
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="view")
	public String view(ModelMap model,HttpServletRequest request){
		String id=request.getParameter("id");
		Storage storage=this.storageRemote.getStorageById(Long.valueOf(id));
		Centro centro=centroRemote.findCentroById(storage.getCentro().getId());
		storage.setCentro(centro);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", storage.getId());
		List<StorageItem> itemList=this.storageRemote.getStorageItemList(params);
		for(int i=0;itemList!=null && i<itemList.size();i++){
			StorageItem obj=itemList.get(i);
			Item item=this.itemRemote.getItem(obj.getItem().getId());
			obj.setItem(item);
		}
		storage.setItems(itemList);
		model.put("storage", storage);
		return "storage/StorageInfo";
	}
	
	/**
	 * 单据明细xls导出
	 * */
	@RequestMapping(value = "/report/xls")
	public ModelAndView entryReport(			
			@RequestParam(value = "id") String id,
			@RequestParam(value = "format", defaultValue = "xls") String format) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("id", id);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		Map<String, Object> model = new HashMap<String, Object>();
		List<StorageItem> storageItems=storageRemote.storageDetailbyId(p);
		for(StorageItem storageItem:storageItems){
			Storage storage=storageRemote.getStorageById(Long.parseLong(id));
			Item item=itemRemote.getItem(storageItem.getItem().getId());
			Centro centro=centroRemote.findCentroById(storageItem.getCentro().getId());
			User user=userRemote.getUser(item.getUserid());
			item.setShopUser(user);
			storageItem.setCentro(centro);
			storageItem.setItem(item);
			storageItem.setStorage(storage);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			model.put("dateDesc",df.format(storage.getOperateTime()));
		}
		model.put("data", storageItems);
		model.put("format", format);
		return new ModelAndView("storageReport",model); 
	}
	
}
