package com.graby.store.admin.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Centro;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.User;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.ItemRemote;
import com.graby.store.remote.ShipOrderRemote;
import com.graby.store.remote.UserRemote;

/**
 * 分拣单控制类
 * 
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value = "picking")
public class PickingController extends BaseController {

	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private ShipOrderRemote shipOrderRemote;
	@Autowired
	private ItemRemote itemRemote;
	@Autowired
	private CentroRemote centroRemote;

	@RequestMapping(value = "index")
	public String index(ModelMap model, HttpServletRequest request) {
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
		User user = BaseResource.getCurrentUser();
		List<User> userList = this.userRemote.findAll(String.valueOf(user.getId()));
		model.put("users", userList);
		model.put("userId", userId);
		model.put("q", q);
		return "picking/pickingList";
	}

	@RequestMapping(value = "list")
	@ResponseBody
	public Map<String, Object> list(HttpServletRequest request, ModelMap model,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = request.getParameter("userId");
		String q = request.getParameter("q");
	//	resultMap.put("rows", buildOrderListData(orders.getContent()));
	//	resultMap.put("total", orders.getTotalElements());
		resultMap.put("userId", userId);
		resultMap.put("q", q);
		return resultMap;
	}

	/**
	 * 添加新的分拣单 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "f7add")
	public String add(HttpServletRequest request,ModelMap model) {
		
		return "picking/pickingAdd";
	}
	/**
	 * 分拣单表格数据获取
	 * @param request
	 * @param model
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="addListData")
	@ResponseBody
	public Map<String,Object> addListData(HttpServletRequest request, 
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "rows", defaultValue = "100") int rows){
		Map<String,Object> params=new HashMap<String,Object>();
		String userId=request.getParameter("userId");
		String cpCode=request.getParameter("cpCode");
		Long id=0l;
		params.put("cpCode", cpCode);
		params.put("userId", userId);
		int centroId=BaseResource.getCurrentCentroId();
		params.put("centroId", centroId);
		if(centroId==0){
			centroId=1;
		}
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (userId==null&&cpCode==null) {
				List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderByStatus(Long.valueOf(centroId),
						ShipOrder.EntryOrderStatus.WAIT_EXPRESS_PICKING);
				resultMap=buildListDataInfo(sendOrders);
		}else if(userId=="0"&&cpCode==null){
			id=Long.parseLong(userId);
			List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderByStatus(Long.valueOf(centroId),
					ShipOrder.EntryOrderStatus.WAIT_EXPRESS_PICKING);
			resultMap=buildListDataInfo(sendOrders);
		}else if(userId!="0"&&cpCode==null){
			id=Long.parseLong(userId);
			List<ShipOrder> sendOrders = shipOrderRemote.findSendOrderByStatusAndUserId(Long.valueOf(centroId),
					ShipOrder.EntryOrderStatus.WAIT_EXPRESS_PICKING,id);
			resultMap=buildListDataInfo(sendOrders);
		}else{
			List<ShipOrder> sendOrders=shipOrderRemote.findShipOrdersByParams(params);
			resultMap=buildListDataInfo(sendOrders);
		}
		return resultMap;
	}
	
	/**
	 * 重新构建jqueryeasyui列表数据
	 * @param sendOrders
	 * @return
	 */
	private Map<String,Object> buildListDataInfo(List<ShipOrder> sendOrders){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String,Object>> mapList=new ArrayList<Map<String,Object>>();
		int centroId=BaseResource.getCurrentCentroId();
		//查询当前的仓库区域
		
		for(int i=0;sendOrders!=null && i<sendOrders.size();i++){
			ShipOrder order=sendOrders.get(i);
			List<ShipOrderDetail> detailList=this.shipOrderRemote.getShipOrderDetailByOrderId(order.getId());
			ShipOrderDetail detail=detailList.get(0);
			Item item=this.itemRemote.getItem(detail.getItem().getId());
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id", order.getId());//
			map.put("itemId", item.getId());
			map.put("itemNumber", item.getCode());
			map.put("itemName", item.getTitle());
			mapList.add(map);
		}
		resultMap.put("rows",mapList );
		resultMap.put("total",sendOrders.size());
		return resultMap;
	}

	/**
	 * 保存分拣单
	 * 
	 * @return
	 */
	@RequestMapping(value = "save")
	public String save() {
		return null;
	}

	/**
	 * 打印分拣单
	 * 
	 * @return
	 */
	@RequestMapping(value = "print")
	public String print() {
		return null;
	}

	/**
	 * 确认分拣单
	 * 
	 * @return
	 */
	@RequestMapping(value = "confirm")
	public String confirm() {
		return null;
	}
}
