package com.graby.store.admin.web.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.JsonModel;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.Centro;
import com.graby.store.entity.CentroItem;
import com.graby.store.remote.CentroItemRemote;
import com.graby.store.remote.CentroRemote;


@Controller
@RequestMapping(value = "/centroItem/")
public class CentroItemController extends BaseController  {

	@Autowired
	private CentroItemRemote centroItemRemote;
	
	@Autowired
	private CentroRemote centroRemote;
	
	
	
	@RequestMapping(value="list")
	public String toCentroItemList(ModelMap model){
		return "centroItem/centroItemList";
	}
	
	
	@RequestMapping(value="toAddCentroItem")
	public String toAdd(ModelMap model){
		model.put("centros", centroRemote.findCentros());

		return "centroItem/centroItemAdd";
	}
	
	@RequestMapping(value="saveCentroItem")
	@ResponseBody
	public Map<String,Object>  savePermission(HttpServletRequest request,ModelMap model,HttpServletResponse response){
		CentroItem  centroItem  = new CentroItem();
		String name=request.getParameter("name");
		Centro centro = centroRemote.findCentroById(new Long(name));
		String itemQuantity=request.getParameter("itemQuantity");
		String size=request.getParameter("size");
		String address=request.getParameter("address");
		JsonModel json  = new  JsonModel();
		try {
			centroItem.setRoot(0);
			centroItem.setCentro(centro);
			centroItem.setName(centro.getName());
			centroItem.setSize(new Double(size));
			centroItem.setItemQuantity(new Integer(itemQuantity));
			centroItem.setAddress(address);
			centroItemRemote.saveCentroItem(centroItem);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			json.setMsg("系统异常【"+e.getMessage()+"】");
		}
		Map<String,Object> ret = new HashMap<String, Object>();
		ret.put("json", json);
		return ret;
		
	}
	
	@RequestMapping(value="getCentroItemList")
	@ResponseBody
	public List<CentroItem> ajaxCentroItem(ModelMap model,String page,String rows,String text){
		Map<String, Object>  params   = new HashMap<String, Object>();
		
//		return permissionService.getPermissionAll();
		return centroItemRemote.findCentroItems(params);
	}
	
}
