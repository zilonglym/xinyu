package com.graby.store.admin.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.graby.store.remote.ShipOrderRemote;

/**
 * 订单打印控制类
 * @author yangmin
 *
 */
@Controller
public class OrderPrintController {

	@Autowired
	private ShipOrderRemote shipOrderRemote;
	
}
