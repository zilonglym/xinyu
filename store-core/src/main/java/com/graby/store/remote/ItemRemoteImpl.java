package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import com.graby.store.base.remote.service.RemotingService;
import com.graby.store.entity.Item;
import com.graby.store.service.item.ItemService;

@RemotingService(serviceInterface = ItemRemote.class, serviceUrl = "/item.call")
public class ItemRemoteImpl implements ItemRemote {

	@Autowired
	private ItemService itemService;
	
	@Override
	public Item getItem(Long id) {
		return itemService.getItem(id);
	}

	
	@Override
	public void updateItemBarCode(Long itemId, String braCode) {
		itemService.updateItemBarCode(itemId, braCode);
	}
		
	@Override
	public Page<Item> findPageUserItems(Long userId, int pageNo, int pageSize) {
		return itemService.findPageUserItems(userId, "", pageNo, pageSize);
	}

	@Override
	public List<Item> findUserItems(Long userid) {
		return itemService.findPageUserItems(userid, "", 1, Integer.MAX_VALUE).getContent();
	}

	@Override
	public Long getRelatedItemId(Long numIid, Long skuId) {
		return itemService.getRelatedItemId(numIid, skuId);
	}

	@Override
	public void position(Long itemId, String position) {
		itemService.position(itemId, position);
	}


	@Override
	public void updateBarCode(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.itemService.updateBarCode(params);
	}


	@Override
	public Page<Item> getItemsByPage(int page, int rows, Map<String, Object> params) {
		// TODO Auto-generated method stub
		return this.itemService.getItemsByPage(page, rows, params);
	}


	@Override
	public void saveItem(Item item) {
		// TODO Auto-generated method stub
		this.itemService.saveItem(item);
	}


	@Override
	public void saveItemList(List<Item> list) {
		// TODO Auto-generated method stub
		this.itemService.saveItems(list);
	}
	
	@Override
	public void updatePackageWeight(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.itemService.updatePackageWeight(params);
	}


	@Override
	public void updateItemCode(Map<String, Object> params) {
		// TODO Auto-generated method stub
		this.itemService.updateItemCode(params);
	}
	@Override
	public void updateItemTitle(Map<String,Object> params){
		this.itemService.updateItemTitle(params);
	}

	@Override
	public Item findItmeByBarCodeAndUserId(Map<String, Object> map) {
		return itemService.findItmeByBarCodeAndUserId(map);
	}


	@Override
	public int findPageUserItemsCount(Long userId) {
		// TODO Auto-generated method stub
		return this.itemService.findPageUserItemsCount(userId);
	}
	
	@Override
	public List<Item> findItmeByBarCodes(String barCode){
		return this.itemService.findItmeByBarCodes(barCode);
	}


	@Override
	public List<Item> getItemListByParams(Map<String, Object> params) {
		return this.itemService.getItemListByParams(params);
	}


	@Override
	public void updateItemType(Map<String, Object> params) {
		this.itemService.updateItemType(params);
	}

}
