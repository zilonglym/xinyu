package com.graby.store.admin.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.User;
import com.graby.store.remote.ReportRemote;
import com.graby.store.remote.UserRemote;
import com.graby.store.web.auth.ShiroContextUtils;

@Controller
@RequestMapping(value = "/report")
public class ReportController {

	@Autowired
	private UserRemote userRemote;

	@Autowired
	private ReportRemote reportRemote;

	@RequestMapping(value = "/ship")
	public String ship(Model model) {
		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		return "admin/reportShip";
	}
	
	@RequestMapping(value = "/ship/ajax")
	public String ship(
			@RequestParam(value = "userId", defaultValue = "0") Long userId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			Model model) {
		
		Map<String, Object> p = new HashMap<String, Object>();
		if (userId > 0) {
			p.put("userId", userId);
		}
		p.put("startDate", startDate);
		p.put("endDate", endDate);
		List<Map<String,Object>> results = reportRemote.shipCount(p);
		long total = 0;
		long totalItmes = 0;
		if (CollectionUtils.isNotEmpty(results)) {
			for (Map<String, Object> map : results) {
				Number sumVal = (Number)map.get("num");
				total += sumVal.longValue();
				Number itemCount = (Number)map.get("items");
				totalItmes += itemCount.longValue();
			}
		}
		
		model.addAttribute("results", results);
		List<User> users = userRemote.findAll();
		model.addAttribute("users", users);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate",endDate);
		model.addAttribute("total",total);
		model.addAttribute("totalItmes",totalItmes);
		return "admin/reportShipDetail";
	}
	
	@RequestMapping(value = "/ship/item")
	public String shipItem(
			@RequestParam(value = "userId") Long userId,
			@RequestParam(value = "startDate") String startDate,
			@RequestParam(value = "endDate") String endDate,
			Model model) {
		
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDate);
		p.put("endDate", endDate);
		List<Map<String,Object>> results = reportRemote.sumUserSellouts(userId, startDate, endDate);
		model.addAttribute("results", results);
		long total = 0;
		if (CollectionUtils.isNotEmpty(results)) {
			for (Map<String, Object> map : results) {
				Number sumVal = (Number)map.get("num");
				total += sumVal.longValue();
			}
		}
		model.addAttribute("total", total);
		return "admin/reportShipItemDetail";
	}

	@RequestMapping(value = "/ship/xls")
	public ModelAndView shipReport(			
			@RequestParam(value = "userId") long userId,
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "endDate") String endDay,
			@RequestParam(value = "format", defaultValue = "xls") String format) {
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		List<ShipOrder> orders = reportRemote.findOrderSellout(p);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("data", orders);
		model.put("format", format);
		model.put("shopName", "");
		model.put("dateDesc", startDay + " ~ " + endDay);
		return new ModelAndView("shipReport", model); 
	}
	
}
