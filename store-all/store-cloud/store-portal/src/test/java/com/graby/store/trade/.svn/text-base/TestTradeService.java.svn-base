package com.graby.store.trade;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.entity.Trade;
import com.graby.store.service.trade.TradeService;

@ContextConfiguration(locations = { "/applicationContext.xml", "/applicationContext-rule.xml" })
public class TestTradeService extends AbstractJUnit4SpringContextTests {
	
	
	@Autowired
	private TradeService tradeService;
	
	@Test
	public void test1() {
		List<Trade> result = tradeService.findSplitedTrades();
		System.out.println(result);
	}
}
