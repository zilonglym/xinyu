package com.xinyu.service.inventory;

import java.util.List;
import java.util.Map;

import com.xinyu.model.inventory.InventoryAdjustUpload;
import com.xinyu.service.common.BaseService;

public interface InventoryAdjustUploadService extends BaseService{

	Map<String, Object> saveInventoryAdjust(Map<String, Object> params);

	List<InventoryAdjustUpload> getInventoryAdjustUploadListByParams(Map<String, Object> params);

	int getToTal(Map<String, Object> params);

	List<Map<String, Object>> buildListData(List<InventoryAdjustUpload> adjustUploads);

	InventoryAdjustUpload getInventoryAdjustUploadListById(String id);

}
