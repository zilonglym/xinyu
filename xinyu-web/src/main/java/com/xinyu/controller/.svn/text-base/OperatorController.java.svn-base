package com.xinyu.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xinyu.common.BaseController;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.trade.ShipOrder;
import com.xinyu.model.trade.ShipOrderOperator;
import com.xinyu.service.trade.ShipOrderOperatorService;

@Controller
@RequestMapping(value="operator")
public class OperatorController extends BaseController {

	@Autowired
	private ShipOrderOperatorService operatorService;
	/**
	 * 保存验货批次信息
	 * @return
	 */
	@RequestMapping(value="saveCheckTradeBatchRecord")
	@ResponseBody
	public Map<String,Object> saveCheckTradeBatchRecord(){
		String personId=this.getString("personId");
		String description=this.getString("description");
		String num=this.getString("orderCodeIndex");
		ShipOrderOperator operator=new ShipOrderOperator();
		operator.generateId();
		operator.setAccount(new Account(personId));
		operator.setOperatorDate(new Date());
		operator.setDescription(description);
		operator.setNewValue(num);
		ShipOrder order=new ShipOrder();
		order.setId("check");
		operator.setShipOrder(order);
		operator.setOperatorModel(OperatorModel.TRADE_CHECK);
		this.operatorService.saveShipOrderOperator(operator);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		resultMap.put("ret", "1");
		resultMap.put("msg", "");
		return resultMap;
	}
}
