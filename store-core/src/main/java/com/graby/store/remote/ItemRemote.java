package com.graby.store.remote;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

import com.graby.store.entity.Item;

/**
 * 商品服务
 * serviceUrl = "/item.call"
 */
public interface ItemRemote {

	
	/**
	 * 更新商品条码信息
	 * @param itemId
	 * @param braCode
	 */
	public void updateItemBarCode(Long itemId, String braCode);
	
	 void updatePackageWeight(Map<String,Object> params);
	 
	 void updateItemCode(Map<String,Object> params);
	
	 void updateItemTitle(Map<String,Object> params);
	 
	public void saveItem(Item item);
	
	public void saveItemList(List<Item> list);
	
	/**
	 * 获取商品
	 * @param id
	 * @return
	 */
	public Item getItem(Long id);
	/**
	 * 
	 * 更新商品二维码
	 * @param params
	 */
	public void updateBarCode(Map<String,Object> params);

	/**
	 * 获取用户商品 分页
	 * 
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Item> findPageUserItems(Long userId, int pageNo, int pageSize);
	
	public int findPageUserItemsCount(Long userId);

	/**
	 * 获取用户商品
	 * 
	 * @param userid
	 * @return
	 */
	public List<Item> findUserItems(Long userid);
	
	/**
	 * 淘宝商品关联的商品ID
	 * 
	 * @param itemId
	 * @param numIid
	 * @return
	 */
	public Long getRelatedItemId(Long numIid, Long skuId);
	
	/**
	 * 设置商品库位
	 * @param itemId
	 * @param position
	 */
	public void position(Long itemId, String position);	
	
	public Page<Item> getItemsByPage(int page, int rows,Map<String,Object> params);
	
	public Item findItmeByBarCodeAndUserId(Map<String,Object>  map);
	
	List<Item> findItmeByBarCodes(String barCode);

	public List<Item> getItemListByParams(Map<String, Object> params);

	/**
	 * 修改商品类型
	 * @param map
	 * */
	public void updateItemType(Map<String, Object> params);



}