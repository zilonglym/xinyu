package com.graby.store.admin.web.others;

import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.local.LocalItem;
import com.graby.store.remote.LocalRemote;
import com.graby.store.remote.StorageLocationRemote;

import jxl.common.Logger;

/**
 * 仓库商品库位查询安卓客户端查询接口
 * */
@Controller
@RequestMapping(value="admin/location")
public class AdminLocationController extends BaseController{
	
	public static final Logger logger=Logger.getLogger(AdminLocationController.class);
	
	@Autowired
	private LocalRemote localRemote;
	
	@Autowired
	private StorageLocationRemote locationRemote;
	
	
	/**
	 * 根据客户端传递商品型号，模糊查询，
	 * 将结果回传给客户端
	 * @param txt
	 * @param page
	 * @param row
	 * @return map
	 * */
	@RequestMapping(value = "find")
	@ResponseBody
	public Map<String, Object> findLocationList(@RequestParam(value="itemId",defaultValue="") String itemId) throws Exception{
		logger.debug("仓库库位查询开始！！");
		Map<String, Object> retMap = new HashMap<String, Object>();		
		try{
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("itemId", itemId);				
			int total = this.localRemote.getTotal(params);
//			System.err.println("plate params:"+params);
			List<Map<String, Object>> plates = this.localRemote.findPCLocalPlate(params);
			retMap.put("total", total);
			retMap.put("page", 1);
			retMap.put("rows", this.buildList(plates));
		}catch(Exception e){
			e.printStackTrace();
		}	
		logger.debug("仓库库位查询结束！！");
		return retMap;
	}

	private List<Map<String, Object>> buildList(List<Map<String, Object>> plates) throws ParseException {
		List<Map<String, Object>> results = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> objMap : plates){
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("HJ", objMap.get("row"));
			map.put("id", objMap.get("id"));
			map.put("WZ", objMap.get("boxOut"));
			map.put("BW", objMap.get("code"));
			map.put("CS", objMap.get("floor"));
			String name = objMap.get("name") + ":" + (objMap.get("sku")==null?"":objMap.get("sku")); 
			map.put("name", name==null?"":name);
			map.put("shop", objMap.get("shopName")==null?"":objMap.get("shopName"));
			map.put("type", objMap.get("barCode")==null?"":objMap.get("barCode"));
			map.put("num", objMap.get("num")==null?"":objMap.get("num"));
			String birthDate = "" + objMap.get("birthDate");
//			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (objMap.get("birthDate")!=null) {
				map.put("lastDate", birthDate);
			}else {
				String lastUpdate = "" + objMap.get("lastUpdate");
				map.put("lastDate", lastUpdate);
			}
			results.add(map);
		}
		return results;
	}
	
	/**
	 * 根据关键字查商品
	 * @param txt
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "findItem")
	@ResponseBody
	public Map<String, Object> findLocalItem(@RequestParam(value="txt",defaultValue="") String txt) throws Exception{
		logger.debug("仓库商品信息查询开始！！");
		Map<String, Object> retMap = new HashMap<String, Object>();		
		try{
			//手机端中文转UTF-8编码再查询
			String strUTF8 = URLDecoder.decode(txt, "UTF-8");
			
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("searchText", strUTF8);				
//			System.err.println("item params:"+params);
			List<LocalItem> items = this.localRemote.getLocalItemList(params);
			retMap.put("rows", items);
		}catch(Exception e){
			e.printStackTrace();
		}	
		logger.debug("仓库商品信息查询结束！！");
		return retMap;
	}

}
