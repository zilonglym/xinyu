package com.graby.store.admin.web.system;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringEscapeUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.util.qm.XmlUtil;
import com.graby.store.entity.Person;
import com.graby.store.entity.SystemOperatorRecord;
import com.graby.store.entity.User;
import com.graby.store.entity.enums.OperatorModel;
import com.graby.store.remote.PersonRemote;
import com.graby.store.remote.SystemOperatorRecordRemote;
import com.graby.store.remote.UserRemote;

/**
 * 系统操作日志处理控制类
 *
 */
@Controller
@RequestMapping(value="operatorRecord")
public class SystemOperatorController {
	
	@Autowired
	private SystemOperatorRecordRemote recordRemote;
	
	@Autowired
	private PersonRemote personReomte;
	
	@Autowired
	private UserRemote userRemote;
	
	/**
	 * 系统操作日志列表
	 * **/
	@RequestMapping(value="/operatorRecordlist")
	public String OperatorRecordList(HttpServletRequest request,ModelMap model){
		List<Person> persons=this.personReomte.findPersons();
		model.addAttribute("persons", persons);
		List<User> users=this.userRemote.findUsers();
		model.addAttribute("users", users);
		OperatorModel[] models=OperatorModel.values(); 
		model.addAttribute("models",models);
		return "system/operatorRecordList";
	}
	
	@RequestMapping(value="listData")
	@ResponseBody
	public Map<String,Object> OperatorRecordListData(HttpServletRequest request,@RequestParam(defaultValue="1")int page,@RequestParam(defaultValue="100")int rows) throws DocumentException{
		if (rows==10) {
			rows=100;
		}
		Map<String,Object> params=new HashMap<String,Object>();
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String operator = request.getParameter("personId");
		String userid = request.getParameter("userId");
		String q = request.getParameter("q");
		String model = request.getParameter("model");
		params.put("startDate", startTime);
		params.put("endDate", endTime);
		params.put("userId", userid);
		params.put("personId", operator);
		params.put("q", q);
		params.put("operatorModel", model);
		List<SystemOperatorRecord> records = this.recordRemote.findOperatorRecordsByParams(params,page,rows);
		List<Map<String,Object>> results=new ArrayList<Map<String,Object>>();
		for(SystemOperatorRecord record:records){
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("id",record.getId());
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			map.put("createDate",format.format(record.getTime()));
			Long personId=(long) record.getOperator();
			if (personId!=null&&personId!=0l) {
				Person person = this.personReomte.findPersonById(personId);
				map.put("person",person.getName());
			}else {
				map.put("person","");
			}
			Long userId=(long) record.getUser();
			if (userId!=null&&userId!=0l) {
				User user=this.userRemote.getUser(userId);
				map.put("user",user.getShopName());
			}else {
				map.put("user","");
			}
			map.put("ipaddr",record.getIpaddr());
//			System.err.println(record.getOperatorModel());
			map.put("operatorModel",record.getOperatorModel().getDescription());
			//xml字符串转义
			map.put("description",StringEscapeUtils.escapeHtml4(record.getDescription()));
//			System.err.println(record.getDescription());
			map.put("status",record.getStatus());
			results.add(map);
		}

		int total = this.recordRemote.getTotalResult(params);
		Map<String,Object> result = new HashMap<String,Object>();
		result.put("rows",results);
		result.put("page",page);
		result.put("total",total);

		return result;
	}
}
