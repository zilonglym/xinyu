package com.graby.store.portal.qm.web;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.graby.store.portal.qm.enums.XmlEnum;
import com.graby.store.portal.qm.service.QmSyncService;
import com.graby.store.portal.qm.util.XmlUtil;

/**
 * 奇门接口入口
 * 
 * @author 杨敏
 *
 */
@Controller
public class QmWebController {
	private String appKey = "1023012748";
	private String customerId = "";
	@Autowired
	private QmSyncService qmService;

	/**
	 * 奇门调用WMS接口入口
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "qm_index")
	public String index(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String method = request.getParameter("method");// 调用API的方法名，根据相就在的method来调用不同的方法。
		Map<String, Object> resultMap = new HashMap<String, Object>();
		InputStreamReader reader = new InputStreamReader(request.getInputStream(), "UTF8");
		char[] buff = new char[1024];
		int length = 0;
		StringBuilder requestStr=new StringBuilder();
		while ((length = reader.read(buff)) != -1) {
			String x = new String(buff, 0, length);
			requestStr.append(x);
		}
		String xmlStr = requestStr.toString();
		String msg = "";
		try {
			if (method == null || method.length() == 0) {
				resultMap.put("flag", "failure");
				resultMap.put("message", "method参数为空!");
				String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
				System.err.println(resultXml);
				return resultXml;
			}
			if (method.equals("singleitem.synchronize")) {// 商品同步
				msg = this.qmService.itemSync(xmlStr);
			} else if (method.equals("combineitem.synchronize")) {// 组合商品
				msg = this.qmService.combineitem(xmlStr);
			} else if (method.equals("entryorder.create")) {// 入库单创建
				msg = this.qmService.entryorder(xmlStr);
			} else if (method.equals("returnorder.create")) {// 退货入库单
				msg = this.qmService.returnorder(xmlStr);
			} else if (method.equals("stockout.create")) {// 出库单创建
				msg = this.qmService.stockout(xmlStr);
			} else if (method.equals("deliveryorder.create")) {// 发货单创建
				msg = this.qmService.deliveryorder(xmlStr);
			} else if (method.equals("deliveryorder.query")) {// 发货单查询接口
				this.qmService.deliveryQuery(xmlStr);
			} else if (method.equals("orderprocess.query")) {// 订单查询接口

			}else if(method.equals("order.cancel")){//单据取消接口
				msg=this.qmService.orderCancel(xmlStr);
			}else if(method.equals("inventory.query")){//库存查询接口
				this.qmService.inventoryQuery(xmlStr);
			}else if(method.equals("storeprocess.create")){//仓内加工单创建接口
				this.qmService.storeprocessCreate(xmlStr);
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("flag", "failure");
		}
		if (msg != null && msg.length() > 0) {
			resultMap.put("flag", "failure");
			resultMap.put("message", msg);
		}
		resultMap.put("itemId", "");
		String resultXml = XmlUtil.converterPayPalm(resultMap, XmlEnum.RESPONSE);
		System.err.println(resultXml);
		return resultXml;
	}

}
