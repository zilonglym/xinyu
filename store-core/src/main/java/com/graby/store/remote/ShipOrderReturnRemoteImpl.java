package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.dao.mybatis.ItemDao;
import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrder;
import com.graby.store.entity.ShipOrderDetail;
import com.graby.store.entity.ShipOrderReturn;
import com.graby.store.service.inventory.AccountEntryArray;
import com.graby.store.service.waybill.TradeBatchService;
import com.graby.store.service.wms.ShipOrderReturnService;
import com.graby.store.service.wms.ShipOrderService;
import com.taobao.api.ApiException;

@RemotingService(serviceInterface = ShipOrderReturnRemote.class, serviceUrl = "/returnOrder.call")
public class ShipOrderReturnRemoteImpl implements ShipOrderReturnRemote {
	@Autowired
	private ShipOrderReturnService returnService;

	@Override
	public List<ShipOrderReturn> findShipOrderByexpress(Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.returnService.findShipOrderByexpress(params);
	}

	@Override
	public void save(ShipOrderReturn orderReturn) {
		// TODO Auto-generated method stub
		this.returnService.save(orderReturn);
	}
	
	}
