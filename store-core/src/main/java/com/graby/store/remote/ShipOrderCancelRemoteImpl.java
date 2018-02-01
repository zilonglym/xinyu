package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.ShipOrderCancel;
import com.graby.store.service.wms.ShipOrderCancelService;

@RemotingService(serviceInterface = ShipOrderCancelRemote.class, serviceUrl = "/cancel.call")
public class ShipOrderCancelRemoteImpl implements ShipOrderCancelRemote{

	@Autowired
	private ShipOrderCancelService orderCancelService;
	
	@Override
	public List<ShipOrderCancel> getShipOrderCancelByList(Map<String, Object> params, int page, int rows) {
		return this.orderCancelService.getShipOrderCancelByList(params,page,rows);
	}

	@Override
	public int selectByCount(Map<String, Object> params) {
		return this.orderCancelService.selectByCount(params);
	}

}
