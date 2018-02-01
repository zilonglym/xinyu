package com.xinyu.model.base;

import com.xinyu.model.common.Entity;

/**
 * @author shark_cj
 * @since  2017-05-03
 * 包裹的包材信息
 */
public class PackageMaterial extends Entity {

	private PackageInfo  packageInfo;
	
	/**
	 * true    淘宝指定的包材型号
	 * type
	 */
	private String  materialType;
	
	/**
	 * true   包材的数量
	 * quantity
	 */
	private int materialQuantity;

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public String getMaterialType() {
		return materialType;
	}

	public void setMaterialType(String materialType) {
		this.materialType = materialType;
	}

	public int getMaterialQuantity() {
		return materialQuantity;
	}

	public void setMaterialQuantity(int materialQuantity) {
		this.materialQuantity = materialQuantity;
	}

}
