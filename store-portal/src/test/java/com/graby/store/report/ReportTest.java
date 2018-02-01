package com.graby.store.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.graby.store.dao.mybatis.ReportDao;
import com.graby.store.entity.ShipOrder;

//@ContextConfiguration(locations = { "/applicationContext.xml" })
public class ReportTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private ReportDao reportDao;
	
	@Test
	public void testReport() {
		Map p = new HashMap();
//		p.put("userId", 16);
		p.put("startDate", "2013-04-01 00:00");
		p.put("endDate", "2013-07-06 23:59");
		List<Map<String, Object>> result = reportDao.sumUserSellouts(p);
		System.out.println(result.size());
		for (Map<String, Object> map : result) {
			System.out.println(map);
		}
	}
//	
	@Test
	public void testReport2() {
		Map p = new HashMap();
		p.put("userId", 16);
		p.put("startDate", "2013-04-01 00:00");
		p.put("endDate", "2013-07-06 23:59");
		p.put("start", 0);
		p.put("offset", 1);
		List<ShipOrder> result = reportDao.findOrderSellout(p);
		System.out.println(result.size());
		for (ShipOrder o : result) {
			System.out.println(o.getBuyerNick());
		}
	}
}
