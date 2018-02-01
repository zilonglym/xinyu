package com.graby.store.admin.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.remote.InventoryRemote;
import com.graby.store.remote.ReportRemote;

/**
 * 实时库存控制类
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value="realTime")
public class RealTimeInventoryController extends BaseController {
	
	@Autowired
	private InventoryRemote inventoryRemote;
	
	@Autowired
	private ReportRemote reportRemote;
	
	@RequestMapping(value="index")
	public String index(){
		return "admin/realTimeInventory";
	}
	
	
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> inventoryRecordListData(@RequestParam(defaultValue="1")int page,
			@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=60;
		}
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String userId=request.getParameter("userId");
		String title=request.getParameter("itemTitle");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId", userId);
		params.put("title", title);
		params.put("centroId",BaseResource.getCurrentCentroId());
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		
		int start=(page-1)*rows;
		int offset=rows;
		params.put("start",start);
		params.put("offset",offset);
		int total=this.reportRemote.getTotalResult(params);
		List<Map<String,Object>> results = this.reportRemote.getInventoryReport(params);
		for(int i=0;i<results.size();i++){
			Map<String,Object> objectMap=results.get(i);
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("shopName",objectMap.get("shopname"));
			map.put("code", objectMap.get("code"));
			map.put("name", objectMap.get("name"));
			map.put("sku", objectMap.get("sku"));
			map.put("centroName",objectMap.get("centroName"));
			map.put("num", objectMap.get("num"));
			if(i%2==0){
				i++;
				if(i>=results.size()){
					break;
				}
				objectMap=results.get(i);
				map.put("shopName1",objectMap.get("shopname"));
				map.put("code1", objectMap.get("code"));
				map.put("name1", objectMap.get("name"));
				map.put("sku1", objectMap.get("sku"));
				map.put("num1", objectMap.get("num"));
			}
			resultList.add(map);
		}
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",total);
		return resultMap;
	}

}
