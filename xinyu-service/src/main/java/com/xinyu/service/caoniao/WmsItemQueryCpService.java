package com.xinyu.service.caoniao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.taobao.pac.sdk.cp.ReceiveListener;
import com.taobao.pac.sdk.cp.ReceiveSysParams;
import com.taobao.pac.sdk.cp.SendSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_ITEM_QUERY.WmsItemQueryRequest;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.Item;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.SampleRule;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.SnSample;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.WmsItemQueryResponse;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.ItemTypeEnum;
import com.xinyu.service.common.Constants;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.SnSampleService;
import com.xinyu.service.system.UserService;
import com.xinyu.service.util.CaiNiaoPacClient;

/**
 * 商品查询接口，根据货主ID或查询货主指定的商品
 * 
 * @author yangmin 2017年4月28日
 *
 */

@Component
public class WmsItemQueryCpService{

	@Autowired
	private ItemService itemService;
	@Autowired
	private SnSampleService sampleService;
	@Autowired
	private UserService userService;
	/**
	 * itemCods为非必填
	 * @param providerTpId
	 * @param itemIds
	 * @param itemCodes
	 * @return
	 */
	public List<com.xinyu.model.base.Item> orderItemQuery(String providerTpId,List<Long> itemIds,List<String> itemCodes){
		User user=this.userService.getUserById(providerTpId);
		WmsItemQueryRequest request=new WmsItemQueryRequest();
		SendSysParams params=new SendSysParams();
		params.setFromCode(Constants.cainiao_fromCode);
		request.setItemCodes(itemCodes);
		request.setItemIds(itemIds);
		request.setProviderTpId(user.getTbUserId());
		WmsItemQueryResponse response=CaiNiaoPacClient.getClient().send(request, params);
		List<com.xinyu.model.base.Item> itemList=new ArrayList<com.xinyu.model.base.Item>();
		if(response.isSuccess()){
			List<com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.Item> resultList=response.getItemList();
			Map<String,Object> p=new HashMap<String, Object>();
			for(int i=0;resultList!=null && i<resultList.size();i++){
				com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.Item item=resultList.get(i);
				p.put("userId", providerTpId);
				p.put("itemCode", item.getItemCode());
				List<com.xinyu.model.base.Item> list=this.itemService.getItemByList(p);
				if(list==null || list.size()==0){
					com.xinyu.model.base.Item o=saveItem(item);
					itemList.add(o);
				}
			}
		}
		
		return itemList;
	}
	
	
	
	private com.xinyu.model.base.Item saveItem(com.taobao.pac.sdk.cp.dataobject.response.WMS_ITEM_QUERY.Item item){
		com.xinyu.model.base.Item obj=new com.xinyu.model.base.Item();
		obj.generateId();
		obj.setItemCode(item.getItemCode());
		obj.setName(item.getItemName());
		obj.setBarCode(item.getBarCode());
		obj.setItemVersion(new Long(item.getVersion()));
		//user
//		obj.setProviderTpId(providerTpId);
		obj.setType(ItemTypeEnum.valueOf(item.getType()));
		obj.setBrand(item.getBrand());
		obj.setBrandName(item.getBrandName());
		obj.setSpecification(item.getSpecification());
		obj.setColor(item.getColor());
		obj.setSize(item.getSize());
		obj.setApprovalNumber(item.getApprovalNumber());
		obj.setGrossWeight(item.getGrossWeight());
		obj.setNetWeight(item.getNetWeight());
		obj.setLength(item.getLength());
		obj.setWidth(item.getWidth());
		obj.setHeight(item.getHeight());
		obj.setVolume(item.getVolume());
		obj.setPcs(item.getPcs());
		obj.setShelflife(item.isIsShelflife());
		obj.setLifecycle(item.getLifecycle());
		obj.setRejectLifecycle(item.getRejectLifecycle());
		obj.setLockupLifecycle(item.getLockupLifecycle());
		obj.setAdventLifecycle(item.getAdventLifecycle());
		obj.setSnMgt(item.isIsSnMgt());
		obj.setHygroscopic(item.isIsHygroscopic());
		obj.setDanger(item.isIsDanger());
		obj.setSnMode(item.getSnMode());
		obj.setPackagingScheme(item.getPackagingScheme());
		obj.setProduceCodeMgt(item.isIsProduceCodeMgt());
		obj.setCartonLength(item.getCartonLength());
		obj.setCartonWidth(item.getCartonWidth());
		obj.setCartonHeight(item.getCartonHeight());
		obj.setCartonWeight(item.getCartonWeight());
		obj.setCartonVolume(item.getCartonVolume());
		obj.setPoMgt(item.isIsPoMgt());
		obj.setPrecious(item.isIsPrecious());
		obj.setTemperatureRequirement(item.getTemperatureRequirement());
		obj.setDosageForms(item.getDosageForms());
		obj.setProducingArea(item.getProducingArea());
		obj.setManufacturer(item.getManufacturer());
		obj.setClassification(item.getClassification());
		obj.setFirstState(item.isFirstState());
		obj.setImported(item.isIsImported());
		obj.setDrugs(item.isIsDrugs());
		obj.setCategory(item.getCategory());
		obj.setCategoryName(item.getCategoryName());
		obj.setUnit(item.getUnit());
		obj.setIncludeBattery(item.getIncludeBattery());
		obj.setPackageUnit(item.getPackageUnit());
		obj.setMaterialGroup(item.getMaterialGroup());
		obj.setMaterialClass(item.getMaterialClass());
	
		List<SnSample> snSampleList=item.getSnSampleList();
	
		List<com.xinyu.model.base.SnSample> snList=new ArrayList<com.xinyu.model.base.SnSample>();
		for( int i = 0;snSampleList!=null && i<snSampleList.size();i++){
			SnSample sample=snSampleList.get(i);
			com.xinyu.model.base.SnSample snSample=new com.xinyu.model.base.SnSample();
			snSample.setSampleDesc(sample.getSampleDesc());
			snSample.setSnSeq(sample.getSnSeq());
			snSample.generateId();
			snSample.setItem(obj);
			
			List<com.xinyu.model.base.SampleRule> ruleList=new ArrayList<com.xinyu.model.base.SampleRule>();
			for(int j=0;sample.getSampleRuleList()!=null && j<sample.getSampleRuleList().size();j++){
				SampleRule rule=sample.getSampleRuleList().get(j);
				com.xinyu.model.base.SampleRule srule=new com.xinyu.model.base.SampleRule();
				srule.generateId();
				srule.setSnSample(snSample);
				srule.setRuleDesc(rule.getRuleDesc());
				srule.setRuleImgUrl(rule.getRuleImgUrl());
				srule.setRuleRegularExpression(rule.getRuleRegularExpression());
				srule.setRuleSample(rule.getRuleSample());
				ruleList.add(srule);
			}
			//数据库持久化com.xinyu.model.base.SampleRule
			this.sampleService.insertSnSampleRuleList(ruleList);
			//数据库持久化 com.xinyu.model.base.SnSample
			this.sampleService.insertSnSample(snSample);
		}
		this.itemService.saveItem(obj);
		return obj;
	}
	
	

}
