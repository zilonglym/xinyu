package com.graby.store.admin.finance;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Settlement;
import com.graby.store.entity.User;
import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.UserRemote;


/**
 * 商家结算记录明细
 * */
@Controller
@RequestMapping(value="settlement")
public class SettlementController {
	@Autowired
	private UserRemote userRemote;
	@Autowired
	private ReportRemote settlementRemote;
	
	/**
	 * 商家结算记录明细列表
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="list")
	public String list(ModelMap model){
		User user=BaseResource.getCurrentUser();
		List<User> userList=this.userRemote.findAll(String.valueOf(user.getId()));
		model.put("userList",userList);
		return "finance/settlementRecordList";
	}
	
	/**
	 * 商家结算记录明细列表数据填充
	 * @param page
	 * @param rows
	 * @param request
	 * @return map
	 * */
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> listData(@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows,HttpServletRequest request){
		if (rows==10) {
			rows=100;
		}
		Map<String, Object> params=new HashMap<String, Object>();
		String userName=request.getParameter("userName");
		String code=request.getParameter("code");
		String beigainTime=request.getParameter("beigainTime");
		String lastTime=request.getParameter("lastTime");
		params.put("userName",userName);
		params.put("code",code);
		params.put("beigainTime", beigainTime);
		params.put("lastTime", lastTime);
		int total=this.settlementRemote.getSettlementTotalResult(params);
		List<Settlement> settlements=this.settlementRemote.findSettlemnets(params, page, rows);
		List<Map<String,Object>> resultList=SettlementListData(settlements);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("page",page);
		resultMap.put("rows",resultList);
		resultMap.put("total",total);
		return resultMap;
	}
	
	/**
	 * 商家结算记录明细添加
	 * @param model
	 * @return string
	 * */
	@RequestMapping(value="f7Add")
	public String SettlementAdd(ModelMap model){
		List<User> userList=this.userRemote.findUsers();
		model.put("userList",userList);
		return "finance/settlementAdd";
	}
	
	/**
	 * 商家结算记录明细编辑
	 * @param model
	 * @param request
	 * @return string
	 * */
	@RequestMapping(value="f7Edit")
	public String SettlementEdit(ModelMap model,HttpServletRequest request){
		List<User> userList=this.userRemote.findUsers();
		model.put("userList",userList);
		Map<String, Object> params=new HashMap<String, Object>();
		String id=request.getParameter("id");
		params.put("id",id);
		Settlement settlement=this.settlementRemote.findSettlemnetByParam(params);
		model.put("settlement",settlement);
		return "finance/settlementEdit";
	}
	
	/**
	 * 商家结算记录明细保存
	 * @param request
	 * @return resultMap
	 * @exception JSONException
	 * @exception ParseException
	 * */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String,Object> SettlementSave(HttpServletRequest request) throws JSONException, ParseException{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Double total=0.0;
		String json=request.getParameter("json");
		JSONObject object=new JSONObject(json);
		String id=object.getString("id");
		String code=object.getString("code");
		String userName=object.getString("userName");
		String dateTime=object.getString("dateTime");
		String subject=object.getString("subject");
		String remark=object.getString("remark");
		String debit=object.getString("debit");
		String credit=object.getString("credit");
		String direction=object.getString("direction");
		String balance=object.getString("balance");
		if (id.isEmpty()) {
			Settlement settlement=new Settlement();
			if (direction.equals("贷")) {
				total=Double.valueOf(balance)-Double.valueOf(debit)+Double.valueOf(credit);
			}else if (direction.equals("借")) {
				total=Double.valueOf(balance)+Double.valueOf(debit)-Double.valueOf(credit);
			}
			settlement.setBalance(total);
			settlement.setCode(code);
			settlement.setCredit(Double.valueOf(credit));
			settlement.setDateTime(sf.parse(dateTime));
			settlement.setDebit(Double.valueOf(debit));
			settlement.setDirection(direction);
			settlement.setRemark(remark);
			settlement.setSubject(subject);
			settlement.setUserName(userName);
			this.settlementRemote.save(settlement);
			resultMap.put("ret","insert");
		}else {
			Map<String,Object> params=new HashMap<String, Object>();
			params.put("id",id);
			Settlement settlement=this.settlementRemote.findSettlemnetByParam(params);
			if (direction.equals("贷")) {
				total=Double.valueOf(balance)-Double.valueOf(debit)+Double.valueOf(credit);
			}else if (direction.equals("借")) {
				total=Double.valueOf(balance)+Double.valueOf(debit)-Double.valueOf(credit);
			}
			settlement.setBalance(total);
			settlement.setCode(code);
			settlement.setCredit(Double.valueOf(credit));
			settlement.setDateTime(sf.parse(dateTime));
			settlement.setDebit(Double.valueOf(debit));
			settlement.setDirection(direction);
			settlement.setRemark(remark);
			settlement.setSubject(subject);
			settlement.setUserName(userName);
			this.settlementRemote.update(settlement);
			resultMap.put("ret","update");
		}
		return resultMap;
	}
	
	/**
	 * 数据重组
	 * @param list
	 * @return list
	 * */
	private List<Map<String,Object>> SettlementListData(List<Settlement> settlements){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String,Object>> resultList=new ArrayList<Map<String,Object>>();
		for(Settlement settlement:settlements){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("id",settlement.getId());
			map.put("dateTime",sf.format(settlement.getDateTime()));
			map.put("userName",settlement.getUserName());
			map.put("code",settlement.getCode());
			map.put("subject", settlement.getSubject());
			map.put("remark", settlement.getRemark());
			map.put("debit", settlement.getDebit());
			map.put("credit", settlement.getCredit());
			map.put("direction", settlement.getDirection());
			map.put("balance", settlement.getBalance());
			resultList.add(map);
		}
		return resultList;
	}
}
