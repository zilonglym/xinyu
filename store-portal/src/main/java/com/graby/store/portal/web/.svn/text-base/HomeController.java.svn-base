package com.graby.store.portal.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrder.EntryOrderStatus;
import com.graby.store.service.wms.ShipOrderService;

@Controller
@RequestMapping(value = "/home")
public class HomeController {
	@Autowired
	private ShipOrderService orderService;
	
	@RequestMapping(value = "")
	public String index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return "welcome";
	}
	
}
