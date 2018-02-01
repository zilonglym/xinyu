package com.graby.store.remote;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.TradeBatch;
import com.graby.store.service.waybill.TradeBatchService;


@RemotingService(serviceInterface = TradeBatchRemote.class, serviceUrl = "/batch.call")
public class TradeBatchRemoteImpl implements TradeBatchRemote{
	
	@Autowired
	private TradeBatchService batchService;
	
	@Override
	public List<TradeBatch> findTradeBatchByUserId(Long userId) {
		return batchService.findTradeBatchByUserId(userId);
	}

	@Override
	public TradeBatch findTradeBatchById(Long id) {
		return batchService.findTradeBatchById(id);
	}

}
