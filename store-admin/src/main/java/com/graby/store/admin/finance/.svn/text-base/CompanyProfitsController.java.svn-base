package com.graby.store.admin.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.SystemItemRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.CompanyProfits;
import com.graby.store.entity.SystemItem;
import com.graby.store.entity.User;

/**
 * 物流利润明细
 * */
@Controller
@RequestMapping(value="companyProfits")
public class CompanyProfitsController {
	
	@Autowired
	private UserRemote userRemote;
	
	@Autowired
	private SystemItemRemote sysRemote;
	
	@Autowired
	private ReportRemote profitsRemote;
	
	/**
	 * 物流利润明细表
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="list")
	public String list(ModelMap model){
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList",userList);
		String type="LOGISTICS";
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		return "finance/companyProfitsList";
	}
	
	/**
	 * 物流利润明细数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return resultMap
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		Map<String,Object> params=new HashMap<String, Object>();
		String userName=request.getParameter("userName");
		String expressCompany=request.getParameter("expressCompany");
		String beigainTime=request.getParameter("beigainTime");
		String lastTime=request.getParameter("lastTime");
		params.put("userName",userName);
		params.put("expressCompany",expressCompany);
		params.put("beigainTime", beigainTime);
		params.put("lastTime", lastTime);
		int total=this.profitsRemote.getCompanyProfitsTotalResult(params);
		List<CompanyProfits> profits=this.profitsRemote.findCompanyProfits(params, page, rows);
		List<Map<String,Object>> resultList=CompanyProfitsListData(profits);	
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("total",total);
		resultMap.put("rows",resultList);
		return resultMap;
	}
	
	/**
	 * 数据重组
	 * @param list
	 * @return list
	 * */
	private List<Map<String, Object>> CompanyProfitsListData(List<CompanyProfits> companyProfitsList){
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(CompanyProfits companyProfits:companyProfitsList){
			Map<String, Object> map=new HashMap<String, Object>();
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("id",companyProfits.getId());
			map.put("dateTime",sf.format(companyProfits.getDate()));
			map.put("userName",companyProfits.getUserName());
			map.put("expressCompany",companyProfits.getExpressCompany());
			map.put("expressIncome",companyProfits.getExpressIncome());
			map.put("planeIncome",companyProfits.getPlaneIncome());
			map.put("totalIncome",companyProfits.getTotalIncome());
			map.put("expressExpend",companyProfits.getExpressExpend());
			map.put("sendFee",companyProfits.getSendFee());
			map.put("warehouseFee",companyProfits.getWarehouseFee());
			map.put("planeExpend",companyProfits.getPlaneExpend());
			map.put("totalCost",companyProfits.getTotalCost());
			map.put("totalProfits",companyProfits.getTotalProfits());
			map.put("num",companyProfits.getNum());
			resultList.add(map);
		}
		return resultList;
	}
	
	/**
	 * 添加明细
	 * @param model
	 * @return String
	 * */
	@RequestMapping(value="f7Add")
	public String add(ModelMap model){
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList",userList);
		String type="LOGISTICS";
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		return "finance/companyProfitsAdd";
	}
	
	/**
	 * 编辑明细
	 * @param model
	 * @param request
	 * @return String
	 * */
	@RequestMapping(value="f7Edit")
	public String edit(ModelMap model,HttpServletRequest request){
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findUsers();
		model.put("userList",userList);
		String type="LOGISTICS";
		List<SystemItem> systemItems=this.sysRemote.findSystemItemByType(type);
		model.put("expressList", systemItems);
		String id=request.getParameter("id");
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id",id);
		CompanyProfits companyProfits=this.profitsRemote.findCompanyProfitsByParam(params);
		model.put("profits", companyProfits);
		return "finance/companyProfitsEdit";
	}
	
	/**
	 * 保存明细
	 * @param request
	 * @return resultMap
	 * @exception JSONException
	 * @exception ParseException
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request) throws JSONException, ParseException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id=object.getString("id");
		String dateTime=object.getString("dateTime");
		String userName=object.getString("userName");
		String expressCompany=object.getString("expressCompany");
		String expressIncome=object.getString("expressIncome");
		String planeIncome=object.getString("planeIncome");
		String expressExpend=object.getString("expressExpend");
		String sendFee=object.getString("sendFee");
		String warehouseFee=object.getString("warehouseFee");
		String planeExpend=object.getString("planeExpend");
		String num=object.getString("num");
		Double totalIncome=Double.valueOf(expressIncome)+Double.valueOf(planeIncome);
		Double totalCost=Double.valueOf(expressExpend)+Double.valueOf(sendFee)+Double.valueOf(warehouseFee)+Double.valueOf(planeExpend);
		Double totalProfits=totalIncome-totalCost;
		if (id.isEmpty()) {
			CompanyProfits companyProfits=new CompanyProfits();
			companyProfits.setDate(sf.parse(dateTime));
			companyProfits.setExpressCompany(expressCompany);
			companyProfits.setExpressExpend(Double.valueOf(expressExpend));
			companyProfits.setExpressIncome(Double.valueOf(expressIncome));
			companyProfits.setNum(Double.valueOf(num));
			companyProfits.setPlaneExpend(Double.valueOf(planeExpend));
			companyProfits.setPlaneIncome(Double.valueOf(planeIncome));
			companyProfits.setSendFee(Double.valueOf(sendFee));
			companyProfits.setTotalCost(totalCost);
			companyProfits.setTotalIncome(totalIncome);
			companyProfits.setTotalProfits(totalProfits);
			companyProfits.setUserName(userName);
			companyProfits.setWarehouseFee(Double.valueOf(warehouseFee));
			this.profitsRemote.save(companyProfits);
			resultMap.put("ret","insert");
		}else {
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("id",id);
			CompanyProfits companyProfits=this.profitsRemote.findCompanyProfitsByParam(params);
			companyProfits.setDate(sf.parse(dateTime));
			companyProfits.setExpressCompany(expressCompany);
			companyProfits.setExpressExpend(Double.valueOf(expressExpend));
			companyProfits.setExpressIncome(Double.valueOf(expressIncome));
			companyProfits.setNum(Double.valueOf(num));
			companyProfits.setPlaneExpend(Double.valueOf(planeExpend));
			companyProfits.setPlaneIncome(Double.valueOf(planeIncome));
			companyProfits.setSendFee(Double.valueOf(sendFee));
			companyProfits.setTotalCost(totalCost);
			companyProfits.setTotalIncome(totalIncome);
			companyProfits.setTotalProfits(totalProfits);
			companyProfits.setUserName(userName);
			companyProfits.setWarehouseFee(Double.valueOf(warehouseFee));
			this.profitsRemote.update(companyProfits);
			resultMap.put("ret","update");
		}
		return resultMap;
	}
}
