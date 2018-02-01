package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name = "sc_item")
public class Item implements Serializable {

	private static final long serialVersionUID = 981755267881595757L;

	//public static final String TYPE_NORMAL = "normal";

	private Long id;//
	private String code;
	private Long userid;
	private String title;
	private Long weight;
	/**
	 * ZC=正常商品, FX=分销商品, ZH=组合商品, ZP=赠品, BC=包材, HC=耗材, FL=辅料, XN=虚拟品, FS=附属品, CC=残次品, OTHER=其它)
	 * 存放英文编码
	 */
	private String type;//商品类型
	private String sku;
	private String position;
	private String description;
	
	private String itemId;//仓库系统商口ID
	private String shortName;//简称
	private String barCode;//条形码
	private String packageMaterial;//商品包材类型
	private String remark;//备注
	private Date createTime;//创建时间
	private String stockUnit;//商品计量单位
	private String color;//商品颜色
	private String categoryId;//商品类别id
	private String categoryName;//商品类别名称
	private String safetyStock;//安全库存
	private String itemType;//商品类型 (ZC=正常商品, FX=分销商品, ZH=组合商品, ZP=赠品, BC=包材, HC=耗材, FL=辅料, XN=虚拟品, FS=附属品, CC=残次品, OTHER=其它) ,  string (10) , 必填,  (只传英文编码)
	private String brandCode;//品牌代码
	private String brandName;//品牌名称
	private Date updateTime;
	
	
	
	private List<ItemMapping> mapping = new ArrayList<ItemMapping>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	@OneToMany(mappedBy = "item")
	public List<ItemMapping> getMapping() {
		return mapping;
	}

	@Index(name = "idx_userid")
	public Long getUserid() {
		return userid;
	}

	@Index(name = "idx_code")
	public String getCode() {
		return code;
	}

	@Index(name = "idx_type")
	public String getType() {
		return type;
	}

	@Index(name = "idx_title")
	public String getTitle() {
		return title;
	}
	
	@Index(name = "idx_position")
	public String getPosition() {
		return position;
	}

	public Long getWeight() {
		return weight;
	}

	public String getDescription() {
		return description;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setWeight(Long weight) {
		this.weight = weight;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setMapping(List<ItemMapping> tbItemMapping) {
		this.mapping = tbItemMapping;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getPackageMaterial() {
		return packageMaterial;
	}

	public void setPackageMaterial(String packageMaterial) {
		this.packageMaterial = packageMaterial;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	

	public String getStockUnit() {
		return stockUnit;
	}

	public void setStockUnit(String stockUnit) {
		this.stockUnit = stockUnit;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(String safetyStock) {
		this.safetyStock = safetyStock;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getBrandCode() {
		return brandCode;
	}

	public void setBrandCode(String brandCode) {
		this.brandCode = brandCode;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	

}
