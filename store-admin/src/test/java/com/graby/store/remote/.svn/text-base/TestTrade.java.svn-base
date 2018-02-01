package com.graby.store.remote;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.Trade;


@ContextConfiguration(locations = { "/applicationContext-client.xml" })
public class TestTrade extends AbstractJUnit4SpringContextTests {
	@Autowired
	private TradeRemote tradeRemote;
	
	@Test
	public void testFindAll() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", 2);
		param.put("receiverState", "天津");
		param.put("receiverCity", "天津市");
		param.put("itemTitle", "%欧时力%");
		List<Trade> trades = tradeRemote.findWaitAuditTradesBy(param);
		System.out.println(trades.size());
	}
}
