package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.Item;
import com.graby.store.entity.ShipOrderReturn;

/**
 * 商品服务 serviceUrl = "/return.call"
 */
public interface ShipOrderReturnRemote {

	public List<ShipOrderReturn> findShipOrderByexpress(Map<String, Object> params);

	public void save(ShipOrderReturn orderReturn);
}