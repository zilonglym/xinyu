package com.xinyu.model.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xinyu.model.base.enums.ItemStatusEnum;
import com.xinyu.model.base.enums.ItemTypeEnum;
import com.xinyu.model.common.Entity;
import com.xinyu.model.system.enums.OrderStatusEnum;
import com.xinyu.model.system.enums.SystemSourceEnums;

/**
 * 商品实体类
 * 
 * @author yangmin 2017年4月24日
 *
 */
public class Item extends Entity {
	
	/**
	 * 商品来源系统，菜鸟或奇门或其它
	 */
	private SystemSourceEnums systemSource;
	
	private ItemStatusEnum status;
	
	private Date createTime;
	
	private String itemId;
	
	private String shortName;
	
	private String goodsNo;
	/**
	 * 商品编码
	 */
	private String itemCode;
	/**
	 * 商品条码，多条码用;分隔
	 */
	private String barCode;
	/**
	 * 商品名称
	 */
	private String name;
	
	/**
	 * 所属商家
	 */
	private User user;
	/**
	 * 商品版本
	 */
	private Long itemVersion;
	/**
	 * 商品类型 NORMAL-普通商品、 PACKING_MATERIALS-包材、 CONSUMPTIVE_MATERIALS-耗材
	 */
	private ItemTypeEnum type;
	/**
	 * WMS内部识别码
	 */
	private String itemType;
	/**
	 * 商品类别编码
	 */
	private String category;
	/**
	 * 商品类别名称
	 */
	private String categoryName;
	/**
	 * 品牌编码
	 */
	private String brand;
	/**
	 * 品牌名称
	 */
	private String brandName;
	/**
	 * 规格
	 */
	private String specification;
	/**
	 * 颜色
	 */
	private String color;
	/**
	 * 尺码
	 */
	private String size;
	/**
	 * 商品计量单位：件、箱、KG等
	 */
	private String unit;
	/**
	 * 毛重，单位克 这个是菜鸟下发的长宽高体积重量，同时菜鸟需要WMS回传实际的长宽高体积重量，
	 */
	private Long grossWeight;
	/**
	 * WMS自测毛重
	 */
	private Long wmsGrossWeight;
	/**
	 * 净重，单位克
	 */
	private Long netWeight;
	/**
	 * 长度，单位毫米 这个是菜鸟下发的长宽高体积重量，同时菜鸟需要WMS回传实际的长宽高体积重量，建议WMS将自己测量和菜鸟下发分开保存
	 */
	private Long length;
	private Long wmsLength;
	/**
	 * 宽
	 */
	private Long width;
	private Long wmsWidth;
	/**
	 * 高
	 */
	private Long height;
	private Long wmsHeight;
	/**
	 * 体积
	 */
	private Long volume;
	private Long wmsVolume;
	/**
	 * 外箱参数
	 */
	private Long cartonWeight;
	private Long cartonLength;
	private Long cartonWidth;
	private Long cartonHeight;
	private Long cartonVolume;
	/**
	 * false	1	包装方式：1 原箱发货; 2 原箱发货(加缠绕膜); 3 非原箱发货
	 */
	private String packagingScheme;
	/**
	 * false	USD	币种 默认CNY
	 */
	private String currency;
	
	/**
	 * 箱规
	 */
	private Long pcs;
	/**
	 * 是否启用批次管理-保质期管理
	 * 如果是true，那么这个商品按照保质期进行管理，在入库、出库、发货确认等接口回传生产日期或者失效日期，如果没有回传，接口目前不会报错，
	 * 会生成报警记录，由技术支持来和仓库核对没有回传的原因 空或者fasel：不按照保质期进行管理
	 */
	private Boolean isShelflife;
	/**
	 * 商品的保质期天数
	 */
	private Integer lifecycle;
	/**
	 * 保质期禁收天数
	 */
	private Integer rejectLifecycle;
	/**
	 * 保质期禁售天数
	 */
	private Integer lockupLifecycle;
	/**
	 * 保质期临期预警天数
	 */
	private Integer adventLifecycle;
	/**
	 * 是否启用序列号管理
	 */
	private Boolean isSnMgt;
	/**
	 * 1：出库+入库 2:出库+销退
	 * 
	 */
	private String snMode;
	/**
	 * ListSnSample sn示例列表
	 */
	private List<SnSample> snSampleList=new ArrayList<SnSample>();
	/**
	 * 是否易碎品
	 */
	private Boolean isHygroscopic;
	/**
	 * 是否危险品
	 */
	private Boolean isDanger;
	/**
	 * 是否启用批次管理-PO管理
	 */
	private Boolean isPoMgt;
	/**
	 * 是否生产批号管理
	 */
	private Boolean isProduceCodeMgt;
	/**
	 * 是否贵品
	 */
	private Boolean isPrecious;
	/**
	 * 温度要求 1:常温 2:5°C-12°C 3:0°C-4°C 4:-18°C-0°C
	 * 
	 */
	private String temperatureRequirement;
	/**
	 * 剂型
	 */
	private String dosageForms;
	/**
	 * 产地
	 */
	private String producingArea;
	/**
	 * 生产厂家
	 */
	private String manufacturer;
	/**
	 * 存储分类
	 */
	private String classification;
	/**
	 * 首营状态
	 */
	private Boolean firstState;
	/**
	 * 是否进口
	 */
	private Boolean isImported;
	/**
	 * 是否监管
	 */
	private Boolean isDrugs;
	/**
	 * 批准文号
	 */
	private String approvalNumber;
	/**
	 * 包含电池：1 无电池，2 内置电池，3 外置电池
	 */
	private String includeBattery;
	/**
	 * 包装单位
	 */
	private String packageUnit;
	/**从这个属性开始。对象中没有处理
	 * false	111	成本价，单位分	
	 */
	private double costPrice;
	/**
	 * false	1111	吊牌价，单位分
	 */
	private double tagPrice;
	/**
	 * false	1111	零售价，单位分
	 */
	private double retailPrice;
	/**
	 * 	false	1111	采购价,单位分
	 */
	private double purchasePrice;
	/**
	 * false		(包材耗材)套装编码
	 */
	private String materialGroup;
	/**
	 * false		(包材耗材)材质编码
	 */
	private String materialClass;
	/**
	 * false	false	是否报关
	 */
	private Boolean isCustomsDeclaration;
	/**
	 * false	false	是否报检
	 */
	private Boolean isInspectionDeclaration;
	/**
	 * false	5000000011407	灰度标识，服务商品id
	 */
	private String grayFlag;
	/**
	 * false	111	包材内长，单位毫米
	 */
	private Long innerLength;
	/**
	 * false	111	包材内宽，单位毫米
	 */
	private Long innerWidth;
	/**
	 * false	111	包材内高，单位毫米
	 */
	private Long innerHeight;
	/**
	 * false	111	承重，单位克
	 */
	private Long loadbearing;
	/**
	 * false	1	(包材)类型 1:空白箱 2:菜鸟联盟包材
	 */
	private String materialType;
	/**
	 * false	/aaa/bbb	图片地址(url)
	 */
	private String picture;
	
	
	
	private Map<String,Object> extendFields=new HashMap<String, Object>();

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getItemVersion() {
		return itemVersion;
	}

	public void setItemVersion(Long itemVersion) {
		this.itemVersion = itemVersion;
	}

	public ItemTypeEnum getType() {
		return type;
	}

	public void setType(ItemTypeEnum type) {
		this.type = type;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getSpecification() {
		return specification;
	}

	public void setSpecification(String specification) {
		this.specification = specification;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Long getGrossWeight() {
		return grossWeight;
	}

	public void setGrossWeight(Long grossWeight) {
		this.grossWeight = grossWeight;
	}

	public Long getWmsGrossWeight() {
		return wmsGrossWeight;
	}

	public void setWmsGrossWeight(Long wmsGrossWeight) {
		this.wmsGrossWeight = wmsGrossWeight;
	}

	public Long getNetWeight() {
		return netWeight;
	}

	public void setNetWeight(Long netWeight) {
		this.netWeight = netWeight;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public Long getWmsLength() {
		return wmsLength;
	}

	public void setWmsLength(Long wmsLength) {
		this.wmsLength = wmsLength;
	}

	public Long getWidth() {
		return width;
	}

	public void setWidth(Long width) {
		this.width = width;
	}

	public Long getWmsWidth() {
		return wmsWidth;
	}

	public void setWmsWidth(Long wmsWidth) {
		this.wmsWidth = wmsWidth;
	}

	public Long getHeight() {
		return height;
	}

	public void setHeight(Long height) {
		this.height = height;
	}

	public Long getWmsHeight() {
		return wmsHeight;
	}

	public void setWmsHeight(Long wmsHeight) {
		this.wmsHeight = wmsHeight;
	}

	public Long getVolume() {
		return volume;
	}

	public void setVolume(Long volume) {
		this.volume = volume;
	}

	public Long getWmsVolume() {
		return wmsVolume;
	}

	public void setWmsVolume(Long wmsVolume) {
		this.wmsVolume = wmsVolume;
	}

	public Long getCartonWeight() {
		return cartonWeight;
	}

	public void setCartonWeight(Long cartonWeight) {
		this.cartonWeight = cartonWeight;
	}

	public Long getCartonLength() {
		return cartonLength;
	}

	public void setCartonLength(Long cartonLength) {
		this.cartonLength = cartonLength;
	}

	public Long getCartonWidth() {
		return cartonWidth;
	}

	public void setCartonWidth(Long cartonWidth) {
		this.cartonWidth = cartonWidth;
	}

	public Long getCartonHeight() {
		return cartonHeight;
	}

	public void setCartonHeight(Long cartonHeight) {
		this.cartonHeight = cartonHeight;
	}

	public Long getCartonVolume() {
		return cartonVolume;
	}

	public void setCartonVolume(Long cartonVolume) {
		this.cartonVolume = cartonVolume;
	}

	public Long getPcs() {
		return pcs;
	}

	public void setPcs(Long pcs) {
		this.pcs = pcs;
	}

	public Boolean isShelflife() {
		return isShelflife;
	}

	public void setShelflife(Boolean isShelflife) {
		this.isShelflife = isShelflife;
	}

	public Integer getLifecycle() {
		return lifecycle;
	}

	public void setLifecycle(Integer lifecycle) {
		this.lifecycle = lifecycle;
	}

	public Integer getRejectLifecycle() {
		return rejectLifecycle;
	}

	public void setRejectLifecycle(Integer rejectLifecycle) {
		this.rejectLifecycle = rejectLifecycle;
	}

	public Integer getLockupLifecycle() {
		return lockupLifecycle;
	}

	public void setLockupLifecycle(Integer lockupLifecycle) {
		this.lockupLifecycle = lockupLifecycle;
	}

	public Integer getAdventLifecycle() {
		return adventLifecycle;
	}

	public void setAdventLifecycle(Integer adventLifecycle) {
		this.adventLifecycle = adventLifecycle;
	}

	public Boolean isSnMgt() {
		return isSnMgt;
	}

	public void setSnMgt(Boolean isSnMgt) {
		this.isSnMgt = isSnMgt;
	}

	public String getSnMode() {
		return snMode;
	}

	public void setSnMode(String snMode) {
		this.snMode = snMode;
	}

	public List<SnSample> getSnSampleList() {
		return snSampleList;
	}

	public void setSnSampleList(List<SnSample> snSampleList) {
		this.snSampleList = snSampleList;
	}

	public Boolean isHygroscopic() {
		return isHygroscopic;
	}

	public void setHygroscopic(Boolean isHygroscopic) {
		this.isHygroscopic = isHygroscopic;
	}

	public Boolean isDanger() {
		return isDanger;
	}

	public void setDanger(Boolean isDanger) {
		this.isDanger = isDanger;
	}

	public Boolean isPoMgt() {
		return isPoMgt;
	}

	public void setPoMgt(Boolean isPoMgt) {
		this.isPoMgt = isPoMgt;
	}

	public Boolean isProduceCodeMgt() {
		return isProduceCodeMgt;
	}

	public void setProduceCodeMgt(Boolean isProduceCodeMgt) {
		this.isProduceCodeMgt = isProduceCodeMgt;
	}

	public Boolean isPrecious() {
		return isPrecious;
	}

	public void setPrecious(Boolean isPrecious) {
		this.isPrecious = isPrecious;
	}

	public String getTemperatureRequirement() {
		return temperatureRequirement;
	}

	public void setTemperatureRequirement(String temperatureRequirement) {
		this.temperatureRequirement = temperatureRequirement;
	}

	public String getDosageForms() {
		return dosageForms;
	}

	public void setDosageForms(String dosageForms) {
		this.dosageForms = dosageForms;
	}

	public String getProducingArea() {
		return producingArea;
	}

	public void setProducingArea(String producingArea) {
		this.producingArea = producingArea;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public Boolean isFirstState() {
		return firstState;
	}

	public void setFirstState(Boolean firstState) {
		this.firstState = firstState;
	}

	public Boolean isImported() {
		return isImported;
	}

	public void setImported(Boolean isImported) {
		this.isImported = isImported;
	}

	public Boolean isDrugs() {
		return isDrugs;
	}

	public void setDrugs(Boolean isDrugs) {
		this.isDrugs = isDrugs;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getIncludeBattery() {
		return includeBattery;
	}

	public void setIncludeBattery(String includeBattery) {
		this.includeBattery = includeBattery;
	}

	public String getPackageUnit() {
		return packageUnit;
	}

	public void setPackageUnit(String packageUnit) {
		this.packageUnit = packageUnit;
	}

	public Map<String, Object> getExtendFields() {
		return extendFields;
	}

	public void setExtendFields(Map<String, Object> extendFields) {
		this.extendFields = extendFields;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getPackagingScheme() {
		return packagingScheme;
	}

	public void setPackagingScheme(String packagingScheme) {
		this.packagingScheme = packagingScheme;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(double costPrice) {
		this.costPrice = costPrice;
	}

	public double getTagPrice() {
		return tagPrice;
	}

	public void setTagPrice(double tagPrice) {
		this.tagPrice = tagPrice;
	}

	public double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public String getMaterialGroup() {
		return materialGroup;
	}

	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}

	public String getMaterialClass() {
		return materialClass;
	}

	public void setMaterialClass(String materialClass) {
		this.materialClass = materialClass;
	}

	public Boolean isCustomsDeclaration() {
		return isCustomsDeclaration;
	}

	public void setCustomsDeclaration(Boolean isCustomsDeclaration) {
		this.isCustomsDeclaration = isCustomsDeclaration;
	}

	public Boolean isInspectionDeclaration() {
		return isInspectionDeclaration;
	}

	public void setInspectionDeclaration(Boolean isInspectionDeclaration) {
		this.isInspectionDeclaration = isInspectionDeclaration;
	}

	public String getGrayFlag() {
		return grayFlag;
	}

	public void setGrayFlag(String grayFlag) {
		this.grayFlag = grayFlag;
	}

	public Long getInnerLength() {
		return innerLength;
	}

	public void setInnerLength(Long innerLength) {
		this.innerLength = innerLength;
	}

	public Long getInnerWidth() {
		return innerWidth;
	}

	public void setInnerWidth(Long innerWidth) {
		this.innerWidth = innerWidth;
	}

	public Long getInnerHeight() {
		return innerHeight;
	}

	public void setInnerHeight(Long innerHeight) {
		this.innerHeight = innerHeight;
	}

	public Long getLoadbearing() {
		return loadbearing;
	}

	public void setLoadbearing(Long loadbearing) {
		this.loadbearing = loadbearing;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SystemSourceEnums getSystemSource() {
		return systemSource;
	}

	public void setSystemSource(SystemSourceEnums systemSource) {
		this.systemSource = systemSource;
	}

	public ItemStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ItemStatusEnum status) {
		this.status = status;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	
	
}
