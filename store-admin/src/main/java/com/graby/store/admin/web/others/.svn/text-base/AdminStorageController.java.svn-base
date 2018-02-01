package com.graby.store.admin.web.others;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.entity.Centro;
import com.graby.store.entity.CentroItem;
import com.graby.store.entity.Item;
import com.graby.store.entity.Storage;
import com.graby.store.entity.StorageItem;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.StorageStatusEnums;
import com.graby.store.remote.CentroItemRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.StorageRemote;
import com.graby.store.remote.UserRemote;

import jxl.common.Logger;


@Controller
@RequestMapping(value = "/admin/Storage")
public class AdminStorageController {
	
	Logger logger=Logger.getLogger(AdminStorageController.class);
	
	@Autowired
	private CentroRemote centroRemote;
		
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private StorageRemote storageRemote;
	
	@Autowired
	private ItemRemote itemRemote;
	
	@Autowired
	private CentroItemRemote centroItemRemote;
	
	@RequestMapping(value="saveStorage")
	@ResponseBody
	public Map<String, Object> saveStorage(HttpServletRequest request,ModelMap model){
		String type= request.getParameter("type");
		String  jsonStr  =  request.getParameter("json");
		logger.info("保存入库单信息:"+jsonStr);
		JSONObject json=new JSONObject(jsonStr);
        JSONArray date= json.getJSONArray("date");
        Storage  storage  = new Storage();
//      int centroId = BaseResource.getCurrentCentroId();
        Long  userId  = json.getLong("userId");
        Long  centroItemId  = json.getLong("centroItemId");
        Centro  centro  = centroRemote.getCentroById(Integer.valueOf(""+centroItemId));
        
        Map<String, Object> params   =  new HashMap<String, Object>();
        params.put("id", centroItemId);
        List<CentroItem> centroItems = centroItemRemote.findCentroItems(params);
        if(centroItems.size()>0){
        	storage.setCentroItem(centroItems.get(0));
        }
        storage.setCentro(centro);
        storage.setDescription(type);
       
        
        storage.setStatus(StorageStatusEnums.valueOf(type));
        
        
        
       
        
        storage.setOperateTime(new Date(new java.util.Date().getTime()));
        
        List<StorageItem>  itemList  = new ArrayList<StorageItem>();
        for(int i = 0, size    = date.length();i<size ;i++ ){
        	JSONObject obj=date.getJSONObject(i);
        	StorageItem  storageItem = new StorageItem();
        	Long itmeId  =  Long.valueOf(""+obj.get("itemId"));
//        	String barCode  = ""+obj.get("barCode");
//        	itemRemote.updateItemBarCode(itmeId, barCode);
        	storageItem.setQuantity(Integer.valueOf(""+obj.get("quantity")));
        	storageItem.setCentro(centro);
        	Item item = itemRemote.getItem(itmeId);
        	storageItem.setItem(item);
        	if(userId==0 || userId.intValue()==0){
        		userId=item.getUserid();
            }
        	itemList.add(storageItem);
        }
        User user=userRemote.getUser(userId);
        storage.setUser(user);
        storage.setItems(itemList);
        Map<String, Object> params1 =  new HashMap<String, Object>();
        try {
        	  storageRemote.saveStorageAndAddInventory(storage);
              params1.put("msg", "录入成功");
              params1.put("code", "200");
		} catch (Exception e) {
			 e.printStackTrace();
			 params1.put("code", "500");
			 params1.put("msg", "系统录入异常,请检查数据！");
			 params1.put("error", e.toString());
		}
		return params1;
	}
}
