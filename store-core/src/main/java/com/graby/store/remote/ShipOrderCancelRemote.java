package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import com.graby.store.entity.ShipOrderCancel;

public interface ShipOrderCancelRemote {

	List<ShipOrderCancel> getShipOrderCancelByList(Map<String, Object> params, int page, int rows);

	int selectByCount(Map<String, Object> params);

}
