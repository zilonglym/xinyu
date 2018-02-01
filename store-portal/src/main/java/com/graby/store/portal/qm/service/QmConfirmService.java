package com.graby.store.portal.qm.service;

import java.util.Map;

/**
 * 
 * 奇门确认接口
 * @author shark_cj
 */
public interface QmConfirmService {
	
	String entryOrderConfirm(Map<String,Object> map) throws Exception;
	
    String stockoutConfirm(Map<String,Object> map) throws Exception ;
    
    String returnorderConfirm(Map<String,Object> map) throws Exception ;

}
