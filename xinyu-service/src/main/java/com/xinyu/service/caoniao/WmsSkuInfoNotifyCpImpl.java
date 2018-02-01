package com.xinyu.service.caoniao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.taobao.pac.sdk.cp.ReceiveListener;
import com.taobao.pac.sdk.cp.ReceiveSysParams;
import com.taobao.pac.sdk.cp.dataobject.request.WMS_SKU_INFO_NOTIFY.WmsSkuInfoNotifyRequest;
import com.taobao.pac.sdk.cp.dataobject.response.ALIBABA_LOGISTICS_EXPRESS_TRACKING.track;
import com.taobao.pac.sdk.cp.dataobject.response.WMS_SKU_INFO_NOTIFY.WmsSkuInfoNotifyResponse;
import com.xinyu.model.base.Item;
import com.xinyu.model.base.ItemOperator;
import com.xinyu.model.base.SampleRule;
import com.xinyu.model.base.SnSample;
import com.xinyu.model.base.User;
import com.xinyu.model.base.enums.CaiNiaoBillOperatorTypeEnum;
import com.xinyu.model.base.enums.ItemStatusEnum;
import com.xinyu.model.base.enums.ItemTypeEnum;
import com.xinyu.model.common.SessionUser;
import com.xinyu.model.system.Account;
import com.xinyu.model.system.enums.OperatorModel;
import com.xinyu.model.system.enums.SystemSourceEnums;
import com.xinyu.service.common.Constants;
import com.xinyu.service.system.AccountService;
import com.xinyu.service.system.ItemOperatorService;
import com.xinyu.service.system.ItemService;
import com.xinyu.service.system.UserService;

/**
 * 商品信息通知 商品创建、更新、删除信息通知
 * 
 * @author yangmin
 *
 */
@Component
public class WmsSkuInfoNotifyCpImpl implements ReceiveListener<WmsSkuInfoNotifyRequest, WmsSkuInfoNotifyResponse> {

	public static final Logger logger = Logger.getLogger(WmsSkuInfoNotifyCpImpl.class);

	@Autowired
	private ItemService itemService;
	@Autowired
	private UserService userService;
	@Autowired
	private ItemOperatorService operatorService;
	@Autowired
	private AccountService accountService;

	public WmsSkuInfoNotifyResponse execute(ReceiveSysParams params, WmsSkuInfoNotifyRequest request) {
		// TODO Auto-generated method stub
		// 业务处理逻辑
		logger.error("商品更新业务逻辑开始处理!");
		String content = params.getContent();
		// Map<String, Object> map = XmlUtil.Dom2Map(content);
		String actionType = request.getActionType();
		WmsSkuInfoNotifyResponse response = new WmsSkuInfoNotifyResponse();
		try {
			// String actionType = (String) map.get("actionType");
			if (StringUtils.isNotEmpty(actionType) && actionType.equals(CaiNiaoBillOperatorTypeEnum.ADD.getKey())) {
				this.addItem(request);
			} else if (StringUtils.isNotEmpty(actionType)
					&& actionType.equals(CaiNiaoBillOperatorTypeEnum.MODIFY.getKey())) {
				// this.updateItem(request);
				this.addItem(request);
			} else if (StringUtils.isNotEmpty(actionType)
					&& actionType.equals(CaiNiaoBillOperatorTypeEnum.DELETE.getKey())) {
				this.deleteItem(request);
			}
			
			response.setSuccess(true);// 业务成功
		} catch (Exception e) {
			logger.error("商品同步失败！");
			e.printStackTrace();
			response.setSuccess(false);
			response.setErrorMsg(e.getMessage());
		}
		return response;
	}

	/**
	 * 商品新增
	 * 
	 * @param params
	 * @throws Exception
	 */
	@Transactional
	private void addItem(WmsSkuInfoNotifyRequest request) {
		logger.debug("新增商品资料");
		Item item = new Item();
		item.generateId();
		String ownerUserId = request.getOwnerUserId();
		User user = this.userService.getUserByOwnerCode(ownerUserId);
		item.setUser(user);
		String itemId = request.getItemId();
		item.setItemCode(itemId);
		buildCuByStoreCode(user);
		/**
		 * 判断当前商品是否存在系统
		 */
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", user.getId());
		params.put("itemCode", request.getItemCode());
		List<Item> itemList = this.itemService.getItemByList(params);
		logger.debug("商品查询参数:" + params);
		logger.debug("商品查询结果:" + itemList);
		if (itemList != null && itemList.size() > 0) {
			logger.debug("商品存在，修改！");
			/**
			 * 此商品已存在，修改此商品
			 */
			this.updateItem(request);
		} else {
			logger.debug("不存在此商品，新增!");
			this.buildItem(item, request);

			this.itemService.saveItem(item);
			saveItemOperator(item, "菜鸟新增商品资料", OperatorModel.ITEM_CREATE);
		}
	}

	/**
	 * 商品更新
	 * 
	 * @param params
	 */
	@Transactional
	private void updateItem(WmsSkuInfoNotifyRequest request) {
		logger.debug("修改商品资料!");
		String ownerUserId = request.getOwnerUserId();
		User user = this.userService.getUserByOwnerCode(ownerUserId);
		String itemId = request.getItemId();
		Map<String, Object> p = new HashMap<String, Object>();
		p.put("itemCode", request.getItemCode());
		p.put("userId", user.getId());
		Item item = this.itemService.getItemByList(p).get(0);

		// modify start by shark_cj
		Long oldV = item.getItemVersion();
		Long newV = request.getItemVersion();
		/**
		 * by 淘宝 陈秋勇 说的 系统里还是保存的最高的那个版本号
		 * 
		 */
		if (oldV > newV) {
			saveItemOperator(item, "菜鸟提交低版本号商品，本系统不做操作", OperatorModel.ITEM_UPDATE);
			return;
		}
		// modify end

		// 如果是低版本到搞版本覆盖
		item.setItemCode(itemId);
		this.buildItem(item, request);
		this.itemService.updateItem(item);
		saveItemOperator(item, "菜鸟修改商品资料", OperatorModel.ITEM_UPDATE);

	}

	@Transactional
	private void deleteItem(WmsSkuInfoNotifyRequest request) {
		logger.debug("商品资料删除!");
		String ownerUserId = request.getOwnerUserId();
		User user = this.userService.getUserByOwnerCode(ownerUserId);
		String itemId = request.getItemId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("itemCode", request.getItemCode());
		params.put("userId", user.getId());
		List<Item> itemList = this.itemService.getItemByList(params);
		if (itemList != null && itemList.size() > 0) {
			Item item = itemList.get(0);
			params.clear();
			params.put("id", item.getId());
			params.put("status", ItemStatusEnum.DELETED);
			this.itemService.updateItemStatus(params);
			this.saveItemOperator(item, "菜鸟商品删除!", OperatorModel.ITEM_DELETE);
		} else {
			logger.debug("菜鸟删除商品不存在");
		}
	}

	/**
	 * 构建商品对象
	 * 
	 * @param item
	 * @param params
	 */
	private void buildItem(Item item, WmsSkuInfoNotifyRequest request) {
		item.setCreateTime(new Date());
		item.setItemId(request.getItemId());
		item.setItemCode(request.getItemCode());
		item.setName(request.getName());

		if (StringUtils.isEmpty(item.getBarCode())) {
			item.setBarCode(request.getBarCode());
		}
		item.setGoodsNo(request.getGoodsNo());
		item.setItemVersion(request.getItemVersion());
		item.setType(ItemTypeEnum.valueOf(request.getType()));
		item.setCategory(request.getCategory());
		item.setCategoryName(request.getCategoryName());
		item.setBrand(request.getBrand());
		item.setBrandName(request.getBrandName());
		item.setSpecification(request.getSpecification());
		item.setColor(request.getColor());
		item.setSize(request.getSize());
//		item.setGrossWeight(request.getGrossWeight());
		item.setNetWeight(request.getNetWeight());
		item.setWidth(request.getWidth());
		item.setHeight(request.getHeight());
		item.setVolume(request.getVolume());
		item.setPcs(request.getPcs());
		item.setApprovalNumber(request.getApprovalNumber());
		item.setShelflife(request.isIsShelflife());
		item.setLifecycle(request.getLifecycle());
		item.setRejectLifecycle(request.getRejectLifecycle());
		item.setLockupLifecycle(request.getLockupLifecycle());
		item.setAdventLifecycle(request.getAdventLifecycle());
		item.setSnMgt(request.isIsSNMgt());
		item.setHygroscopic(request.isIsHygroscopic());
		item.setDanger(request.isIsDanger());
		item.setSnMode(StringUtils.isNotBlank(request.getSnMode()) ? request.getSnMode() : "");
		item.setPoMgt(request.isIsPoMgt() == null ? false : true);
		item.setPrecious(request.isIsPrecious() == null ? false : true);
		item.setTemperatureRequirement(this.isBlank(request.getTemperatureRequirement()));
		item.setDosageForms(this.isBlank(request.getDosageForms()));
		item.setProducingArea(this.isBlank(request.getProducingArea()));
		item.setManufacturer(request.getManufacturer());
		item.setClassification(request.getClassification());
		item.setFirstState(request.isFirstState() == null ? false : true);
		item.setImported(request.isIsImported() == null ? false : true);
		item.setDrugs(request.isIsDrugs() == null ? false : true);
		item.setPackagingScheme(request.getPackagingScheme());

		// modify start by shark_cj
		item.setLength(request.getLength());
		item.setUnit(request.getUnit());
		// modify end

		item.setProduceCodeMgt(request.isIsProduceCodeMgt() == null ? false : true);
		item.setCartonLength(request.getCartonLength());
		item.setCartonWeight(request.getCartonWeight());
		item.setCartonWidth(request.getCartonWidth());
		item.setCartonHeight(request.getCartonHeight());
		item.setCartonVolume(request.getCartonVolume());
		item.setIncludeBattery(request.getIncludeBattery());
		item.setPackageUnit(request.getPackageUnit());
		item.setCreateTime(new Date());
		item.setSystemSource(SystemSourceEnums.CAINIAO);
		// item.setisBatchMgt
		/**
		 * 判断子对象的类型，如果子对象集合只有一个，转化成的对象则不会是LIST，所以这里需要判断
		 */
		List<com.taobao.pac.sdk.cp.dataobject.request.WMS_SKU_INFO_NOTIFY.SnSample> snList = request.getSnSampleList();
		List<SnSample> list = new ArrayList<SnSample>();
		logger.debug("商品子对象处理:");
		for (int i = 0; snList != null && i < snList.size(); i++) {
			com.taobao.pac.sdk.cp.dataobject.request.WMS_SKU_INFO_NOTIFY.SnSample obj = snList.get(i);
			SnSample sample = new SnSample();
			sample.generateId();
			sample.setSampleDesc(obj.getSampleDesc());
			sample.setSnSeq(obj.getSnSeq());
			List<com.taobao.pac.sdk.cp.dataobject.request.WMS_SKU_INFO_NOTIFY.SampleRule> ruleList = obj
					.getSampleRuleList();
			list.add(sample);
			List<SampleRule> rList = new ArrayList<SampleRule>();
			for (int j = 0; ruleList != null && j < ruleList.size(); j++) {
				com.taobao.pac.sdk.cp.dataobject.request.WMS_SKU_INFO_NOTIFY.SampleRule o = ruleList.get(j);
				SampleRule rule = new SampleRule();
				rule.generateId();
				rule.setSnSample(sample);
				rule.setRuleDesc(o.getRuleDesc());
				rule.setRuleImgUrl(o.getRuleImgUrl());
				rule.setRuleRegularExpression(o.getRuleRegularExpression());
				rule.setRuleSample(o.getRuleSample());
				rList.add(rule);
			}
			sample.setSampleRuleList(rList);
		}
		item.setSnSampleList(list);
	}

	private String isBlank(String key) {
		if (StringUtils.isBlank(key)) {
			return "";
		} else {
			return key;
		}
	}

	private void saveItemOperator(Item item, String description, OperatorModel operateMode) {
		ItemOperator operator = new ItemOperator();
		operator.generateId();
		operator.setItem(item);
		operator.setDescription(description);
		operator.setOperatorDate(new Date());
		operator.setOperatorModel(operateMode);
		Account account = new Account();
		account.setId("cainiao");
		operator.setAccount(account);
		this.operatorService.insertItemOperator(operator);
	}
	
	/**
	 * 构建CU，并存放在当前的sessionUser中
	 * @param storeCode
	 */
	private void buildCuByStoreCode(User user){
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userName", Constants.cainiao_account);
		List<Account> list=this.accountService.findAccountsByList(params);
		//1.查询出属于菜鸟的那个帐号
		if(list!=null && list.size()>0){
			Account account=list.get(0);
			//2.
			account.setCu(user.getCu());
			SessionUser.set(account);
		}
	}
}