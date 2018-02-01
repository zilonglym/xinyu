package com.graby.store.portal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.graby.store.entity.ShipOrder;
import com.graby.store.service.report.ReportService;
import com.graby.store.web.auth.ShiroContextUtils;

/**
 * 用户交易
 * 
 * @author huabiao.mahb
 */
@Controller
@RequestMapping(value = "/report")
public class ReportController {
	
	public static final int PAGE_SIZE= 100;

	@Autowired
	private ReportService reportService;
	
	
	
	/**
	 * 已售商品统计
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/sellout")
	public String sellout() throws Exception {
		//return "report/sellout";
		return "report/newSellOut";
	}
	
	@RequestMapping(value = "/sellout/listData")
	@ResponseBody
	public Map<String, Object> ListData(@RequestParam(defaultValue="1") int page,
			@RequestParam(defaultValue="100") int rows,
			HttpServletRequest request,ModelMap model) throws Exception {
		if (rows!=100) {
			rows=100;
		}
		String startDay=request.getParameter("startDate");
		String endDay=request.getParameter("endDate");
		Long userId = ShiroContextUtils.getUserid();
		List<Map<String,Object>> result = reportService.sumUserSellouts(userId, startDay, endDay);
		Map<String, Object> retMap=new HashMap<String, Object>();
		retMap.put("rows", result);
		retMap.put("total", result.size());
		return retMap;
	}
	
//	@RequestMapping(value = "/sellout/ajax/sum")
//	public String sellerSums(
//			@RequestParam(value = "start") String startDay,
//			@RequestParam(value = "end") String endDay,
//			Model model) throws Exception {
//		Long userId = ShiroContextUtils.getUserid();
//		List<Map<String,Object>> result = reportService.sumUserSellouts(userId, startDay, endDay);
//		
//		long total = 0;
//		if (CollectionUtils.isNotEmpty(result)) {
//			for (Map<String, Object> map : result) {
//				Number sumVal = (Number)map.get("num");
//				total += sumVal.longValue();
//			}
//		}
//		model.addAttribute("result", result);
//		model.addAttribute("total", total);
//		return "report/selloutDetail";
//	}
	
	/**
	 * 发货统计
	 * @param
	 * @return
	 * */
	@RequestMapping(value = "/ship")
	public String ship() throws Exception {
		//return "report/ship";
		return "report/newShip";
	}
	
	@RequestMapping(value = "/ship/listData")
	@ResponseBody
	public Map<String, Object> listData(
			@RequestParam(value = "page", defaultValue = "1") int page, 
			@RequestParam(value = "rows", defaultValue = "100") int rows, 
			HttpServletRequest request,ModelMap model) throws Exception {
		Long userId = ShiroContextUtils.getUserid();
		String startDay=request.getParameter("startDate");
		String endDay=request.getParameter("endDate");
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		
		long count = reportService.findOrderSelloutCount(p);

		int pageNo = page - 1;
		int start = pageNo*PAGE_SIZE;
		int offset = PAGE_SIZE;
		p.put("start", start);
		p.put("offset", offset);

		List<ShipOrder> orders = reportService.findOrderSellout(p);
		PageRequest pageable = new PageRequest((int)pageNo, (int)PAGE_SIZE);
		Page<ShipOrder> pages = new PageImpl<ShipOrder>(orders, pageable, count);
		Map<String, Object> retMap=new HashMap<String, Object>();
		retMap.put("rows",pages.getContent());
		retMap.put("total",pages.getTotalElements());
		retMap.put("page",pages);
		return retMap;
	}
	
//	@RequestMapping(value = "/ship/list")
//	public String ship(
//			@RequestParam(value = "page", defaultValue = "1") int page, 
//			@RequestParam(value = "startDate") String startDay,
//			@RequestParam(value = "endDate") String endDay,
//			Model model) throws Exception {
//		Long userId = ShiroContextUtils.getUserid();
//		Map<String, Object> p = new HashMap<String, Object>();
//		p.put("userId", userId);
//		p.put("startDate", startDay);
//		p.put("endDate", endDay);
//		
//		long count = reportService.findOrderSelloutCount(p);
//
//		int pageNo = page - 1;
//		int start = pageNo*PAGE_SIZE;
//		int offset = PAGE_SIZE;
//		p.put("start", start);
//		p.put("offset", offset);
//
//		List<ShipOrder> orders = reportService.findOrderSellout(p);
//		PageRequest pageable = new PageRequest((int)pageNo, (int)PAGE_SIZE);
//		Page<ShipOrder> pages = new PageImpl<ShipOrder>(orders, pageable, count);
//		model.addAttribute("orders", pages);
//		
//		String searchParams = "startDate=" + startDay + "&endDate=" + endDay;
//		model.addAttribute("searchParams", searchParams);
//		model.addAttribute("startDate", startDay);
//		model.addAttribute("endDate", endDay);
//		model.addAttribute("total", count);
//		return "report/ship";
//	}
	
	@RequestMapping(value = "ship/report")
	public ModelAndView shipReport(			
			@RequestParam(value = "startDate") String startDay,
			@RequestParam(value = "endDate") String endDay,
			@RequestParam(value = "format", defaultValue = "xls") String format) {
		Map<String, Object> model = new HashMap<String, Object>();
		
		Long userId = ShiroContextUtils.getUserid();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("userId", userId);
		p.put("startDate", startDay);
		p.put("endDate", endDay);
		p.put("start", 0);
		p.put("offset", Integer.MAX_VALUE);
		List<ShipOrder> orders = reportService.findOrderSellout(p);
		model.put("data", orders);
		model.put("format", format);
		model.put("shopName", ShiroContextUtils.getCurrentUser().getShopname());
		model.put("dateDesc", startDay + " ~ " + endDay);
		return new ModelAndView("shipReport", model); 
	}
	

}
