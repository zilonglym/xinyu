package com.graby.store.admin.web.store;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.admin.web.BaseController;
import com.graby.store.entity.store.UserShop;
/**
 * 订单同步控制类
 * @author yangmin
 *
 */
@Controller
@RequestMapping(value="webTrade")
public class TradeWebController extends BaseController {
/*	
	@Autowired
	private UserShopService shopService;
	@Autowired
	private SyncTopTradeService syncTradeService;
	/**
	 * 转向至同步页面
	 * @return
	@RequestMapping(value="toSync")
	public String toSync(){
		return "";
	}

	/**
	 * 订单同步入口
	 * @return
	@RequestMapping(value="sync")
	@ResponseBody
	public Map<String,Object> sync(){
		int preDays=this.getInt("preDays",0);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		List<UserShop> list=this.shopService.getUserShopListByUserId("1");
		for(int i=0;list!=null && i<list.size();i++){
			UserShop shop=list.get(i);
			System.err.println(shop.getSessionKey());
			try {
				this.syncTradeService.syncTrade(shop.getSessionKey(), preDays);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return resultMap;
	}
	*/
}
