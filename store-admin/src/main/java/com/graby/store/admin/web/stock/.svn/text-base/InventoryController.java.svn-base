package com.graby.store.admin.web.stock;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.admin.util.InventoryExcelReader;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Centro;
import com.graby.store.entity.CheckInventory;
import com.graby.store.entity.CheckInventoryDetail;
import com.graby.store.entity.Item;
import com.graby.store.entity.Person;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.StorageStatusEnums;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.CheckInventoryRemote;
import com.graby.store.remote.CheckRemote;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.service.inventory.QmInventoryService;
import com.taobao.api.ApiException;

@Controller
@RequestMapping(value = "/stock")
public class InventoryController extends BaseController  {

	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private CheckRemote  checkRemote;

	@Autowired
	private InventoryRemote inventoryRemote;
	
	@Autowired
	private CentroRemote centroRemote;
	
	@Autowired
	private CheckInventoryRemote checkInventoryRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@RequestMapping(value = "/toCheckInventoryList")
	public String toCheckInventoryList(ModelMap model) throws Exception {
		Map<String,Object> params=new HashMap<String, Object>();
		List<CheckInventory> checkInventory = checkInventoryRemote.getCheckInventory(params);
		
		for(CheckInventory check:checkInventory){
			Centro centro=centroRemote.findCentroById(check.getCentro().getId());
			check.setCentro(centro);
			User user=userRemote.getUser(check.getPerson().getId());
			check.setPerson(user);
		}
		
		model.put("checkInventoryList",checkInventory);
		
		return "checkInventory/CheckInventoryList";
	
	}
	
	/**
	 * 显示单据详情
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value="checkView")
	public String view(ModelMap model,HttpServletRequest request){
		String id=request.getParameter("id");
		CheckInventory checkInventory=this.checkInventoryRemote.getCheckInventoryById(Long.valueOf(id));
	
		Centro centro=centroRemote.findCentroById(checkInventory.getCentro().getId());
		User person=userRemote.getUser(checkInventory.getPerson().getId());
		checkInventory.setCentro(centro);
		checkInventory.setPerson(person);
		
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", checkInventory.getId());
		List<CheckInventoryDetail> checkInventoryDetail = checkInventoryRemote.checkInventoryDetailbyId(params);
		for(int i=0;checkInventoryDetail!=null && i<checkInventoryDetail.size();i++){
			CheckInventoryDetail obj=checkInventoryDetail.get(i);
			Item item=this.itemRemote.getItem(obj.getItem().getId());
			obj.setItem(item);
		}
		checkInventory.setDetails(checkInventoryDetail);
		
		model.put("checkInventory", checkInventory);
		return "checkInventory/CheckInventory";
	}
	
	
	@RequestMapping(value = "/toCheckInventoryAdd")
	public String toCheckInventoryAdd(HttpServletRequest  request,Model model) throws Exception {
		List<User> userList=this.userRemote.findAll(null);
		model.addAttribute("users", userList);
		return "checkInventory/CheckInventoryAdd";
	
	}
	
	@RequestMapping(value = "checkInventory/audit")
	public String updateCheckInventoryAudit(HttpServletRequest  request,Model model) throws Exception {
		Map<String,Object> params=new HashMap<String, Object>();
		String id=request.getParameter("id");
		params.put("id", id);
		checkInventoryRemote.updateCheckInventoryAudit(params);
		return "redirect:/stock/toCheckInventoryList";
	
	}
	
	@RequestMapping(value="saveCheckInventory")
	@ResponseBody
	public Map<String, Object> saveCheckInventory(HttpServletRequest request,ModelMap model) throws JSONException{
		
		String  jsonStr  =  request.getParameter("json");
		
		String str=jsonStr.substring(1, jsonStr.length()-1);
		System.err.println(str);
		JSONObject json=new JSONObject(str);
        JSONArray date= json.getJSONArray("date");
        System.out.println(date);
        Long  userId  = json.getLong("userId");
        int centroId = BaseResource.getCurrentCentroId();
        Centro  centro  = centroRemote.getCentroById(centroId);
        CheckInventory  checkInventory  =  new CheckInventory();
        checkInventory.setPerson(userRemote.getUser(userId));
        List<CheckInventoryDetail> detailList=  new ArrayList<CheckInventoryDetail>();
        checkInventory.setCentro(centro);
        checkInventory.setStatus(CheckInventory.Status.save);
        checkInventory.setType(CheckInventory.Types.down);
        
        for(int i = 0, size    = date.length();i<size ;i++ ){
        	JSONObject obj=date.getJSONObject(i);
        	CheckInventoryDetail  detail = new CheckInventoryDetail();
        	Long itmeId  =  Long.valueOf(""+obj.get("itemId"));
        	detail.setQuantity(Integer.valueOf(""+obj.get("quantity")));
        	detail.setCentro(centro);
        	Item item = itemRemote.getItem(itmeId);
        	detail.setItem(item);
        	detailList.add(detail);
        }
        checkInventory.setDetails(detailList);
        checkInventoryRemote.insert(checkInventory);
        
		return null;
	}
	
 
	@RequestMapping(value = "")
	public String show(@RequestParam(value = "userId", defaultValue = "0") Long userId, 
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			Model model) throws Exception {
		User user=BaseResource.getCurrentUser();
		List<User> users = userRemote.findAll(String.valueOf(user.getId()));
		model.addAttribute("users", users);
		if (userId != 0L) { 
			model.addAttribute("userId", userId);
		}
		if (!"".equals(itemName)) { 
			model.addAttribute("itemName", itemName);
		}
		return "admin/shopInventory";
	}
 
	@RequestMapping(value = "/ajax/detail")
	public String detail(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws Exception {
		if (userId != 0L) {
			User user=BaseResource.getCurrentUser();
			long centroId=1L;
			if(user.getCentro()!=null){
				centroId=user.getCentro().getId();
			}
			System.err.println(centroId+":"+userId);
			List<Map<String, Long>> stat = inventoryRemote.stat(centroId, userId);
			model.addAttribute("stat", stat);
		}
		return "admin/shopInventoryDetail";
	}	
	/**
	 * 取数
	 * @return
	 */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(HttpServletRequest request,ModelMap model,
			@RequestParam(value = "userId", defaultValue = "0") Long userId,
			@RequestParam(value = "itemName", defaultValue = "") String itemName,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		Map<String,Object> resultMap=new HashMap<String, Object>();
		Map<String,Object> params=new HashMap<String,Object>();
		if(rows==10){
			rows=100;
		}
		User user = BaseResource.getCurrentUser();
		long centroId = 1L;
		if (user.getCentro() != null) {
			centroId = user.getCentro().getId();
		}
		params.put("centroId", centroId);
		params.put("userId", userId);
		System.out.println("itemName:"+itemName);
		if(!"".equals(itemName)){
			params.put("itemName", "%"+itemName+"%");
		}
	    List<Map<String, Object>> statPage = inventoryRemote.statPage(page, rows, params);
	    Long count  = inventoryRemote.statPageCount(params);
	    resultMap.put("rows",statPage);
        resultMap.put("total", count);
		return resultMap;
	}
	
	
	
	@RequestMapping(value = "/position")
	public String position(Model model) throws Exception {
		User user=BaseResource.getCurrentUser();
		List<User> users = userRemote.findAll(String.valueOf(user.getId()));
		model.addAttribute("users", users);
		return "admin/itemPosition";
	}	
	
	@RequestMapping(value = "/ajax/position/detail")
	public String positionDetail(@RequestParam(value = "userid", defaultValue = "0") Long userId, Model model) throws Exception {
		List<Item> items = itemRemote.findUserItems(userId);
		model.addAttribute("items", items);
		return "admin/itemPositionDetail";
	}	
	
	
		//上传商品页面
		@RequestMapping(value = "batchAdd" ,method = RequestMethod.GET)
		public String upload() throws ApiException {
			return "/system/batchInventory";
		}
		
		// 库存初始化
		@RequestMapping(value = "upload", method=RequestMethod.POST)
		public String uploadItems(@RequestParam(value = "file", required = true) MultipartFile file, ModelMap model) 
				throws FileNotFoundException, IOException {
			System.out.println("sys:");
		    InventoryExcelReader inventory  =new InventoryExcelReader(file.getInputStream());
		    List<Map<String, Object>> list = inventory.getInventory();
		    inventoryRemote.inItInventory(list);
			return "/system/batchInventory";
		}
	
}
