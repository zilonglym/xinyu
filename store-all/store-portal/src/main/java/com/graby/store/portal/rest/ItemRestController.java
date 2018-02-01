package com.graby.store.portal.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.graby.store.service.item.ItemService;
import com.taobao.api.ApiException;


@Controller
@RequestMapping(value = "/rest/item")
public class ItemRestController {
		
	@Autowired
	private ItemService itemService;
	
	/**
	 * 同步所有淘宝商品
     * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> syncTop() throws ApiException {
		itemService.syncTop();
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}
	 
	/**
	 * 同步淘宝商品
	 * @param numIid 商品ID
	 * @param skuId skuID
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value = "/relate/{numIid}/{skuId}", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<String> relateItem(
			@PathVariable("numIid") Long numIid, 
			@PathVariable("skuId") Long skuId) 
			throws ApiException {
		itemService.syncTop(numIid, skuId);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}	
	
	@RequestMapping(value = "/position/{itemId}/{position}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> positionItem(
			@PathVariable("itemId") Long itemId,
			@PathVariable("position") String position) 
			throws ApiException {
		itemService.position(itemId, position);
		return new ResponseEntity<String>("success", HttpStatus.OK);
	}	
}
