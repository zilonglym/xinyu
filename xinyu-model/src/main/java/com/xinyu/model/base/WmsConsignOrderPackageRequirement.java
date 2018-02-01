package com.xinyu.model.base;

import com.xinyu.model.common.Entity;
import com.xinyu.model.trade.ShipOrder;
/**
 * 包装要求信息
 * @author yangmin
 * 2017年4月25日
 *
 */
public class WmsConsignOrderPackageRequirement extends Entity {

	/**
	 * FALSE	　	所有可用的包材编码列出，以逗号分隔
	 */
	private String materialTypes;
	/**
	 * FALSE	　	包材分类
	 */
	private String materialClass;
	/**
	 * FALSE	　	包材分组
	 */
	private String materialGroup;
	/**
	 * FALSE	　	优先级 1最高，10最低。
	 */
	private String priority;
	
	private ShipOrder order;
	
	public String getMaterialTypes() {
		return materialTypes;
	}
	public void setMaterialTypes(String materialTypes) {
		this.materialTypes = materialTypes;
	}
	public String getMaterialClass() {
		return materialClass;
	}
	public void setMaterialClass(String materialClass) {
		this.materialClass = materialClass;
	}
	public String getMaterialGroup() {
		return materialGroup;
	}
	public void setMaterialGroup(String materialGroup) {
		this.materialGroup = materialGroup;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public ShipOrder getOrder() {
		return order;
	}
	public void setOrder(ShipOrder order) {
		this.order = order;
	}
}
