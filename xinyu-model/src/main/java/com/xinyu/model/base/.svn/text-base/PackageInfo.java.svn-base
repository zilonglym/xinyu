package com.xinyu.model.base;

import java.util.List;

import com.xinyu.model.common.Entity;
import com.xinyu.model.order.StockOutOrderConfirm;

/**
 * @author shark_cj
 * @since  2017-05-03
 * 包裹信息
 */
public class PackageInfo  extends Entity{
	
	private StockOutOrderConfirm stockOutOrderConfirm;
	
	/**
	 * FALSE   快递公司服务编码
	 */
	private String  tmsCode;
	
	/**
	 * 物流公司名称
	 * logisticsName
	 */
	private String  tmsServiceName;
	
	/**
	 * FALSE   运单编码
	 * expressCode
	 */
	private String  tmsOrderCode;
	
	/**
	 * FALSE   包裹号
	 * packageCode
	 */
	private String  packageCode;
	
	/**
	 * false  包裹质量
	 * 单位：克	Weight，
	 * 奇门单位千克，需要转换
	 */
	private  int  packageWeight;
	
	/**
	 * false  包裹长度
	 * 单位：毫米	Length，
	 * 奇门单位是厘米，需要转换
	 */
	private int  packageLength;
	
	/**
	 * FALSE  包裹宽度
	 * 单位：毫米	
	 * width奇门单位是厘米，需要转换
	 */
	private int packageWidth;
	
	/**
	 * false   包裹高度
	 * 单位：毫米	
	 * height奇门单位是厘米，需要转换
	 */
	private int packageHeight;
	
	/**
	 * FALSE   包裹体积
	 * 单位：立方厘米	
	 * Volume 奇门单位是(升)
	 */
	private String  packageVolumn;
	
	/**
	 * FALSE   包裹的包材信息列表
	 * packageMaterialList
	 */
	private List<PackageMaterial>  packageMaterialList;
	
	/**
	 * FALSE    包裹里面的商品信息列表
	 * package.items
	 */
	private List<PackageItem>  packageItemItems;

	public String getTmsCode() {
		return tmsCode;
	}

	public void setTmsCode(String tmsCode) {
		this.tmsCode = tmsCode;
	}

	public String getTmsServiceName() {
		return tmsServiceName;
	}

	public void setTmsServiceName(String tmsServiceName) {
		this.tmsServiceName = tmsServiceName;
	}

	public String getTmsOrderCode() {
		return tmsOrderCode;
	}

	public void setTmsOrderCode(String tmsOrderCode) {
		this.tmsOrderCode = tmsOrderCode;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}

	public int getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(int packageWeight) {
		this.packageWeight = packageWeight;
	}

	public int getPackageLength() {
		return packageLength;
	}

	public void setPackageLength(int packageLength) {
		this.packageLength = packageLength;
	}

	public int getPackageWidth() {
		return packageWidth;
	}

	public void setPackageWidth(int packageWidth) {
		this.packageWidth = packageWidth;
	}

	public int getPackageHeight() {
		return packageHeight;
	}

	public void setPackageHeight(int packageHeight) {
		this.packageHeight = packageHeight;
	}

	public String getPackageVolumn() {
		return packageVolumn;
	}

	public void setPackageVolumn(String packageVolumn) {
		this.packageVolumn = packageVolumn;
	}

	public List<PackageMaterial> getPackageMaterialList() {
		return packageMaterialList;
	}

	public void setPackageMaterialList(List<PackageMaterial> packageMaterialList) {
		this.packageMaterialList = packageMaterialList;
	}

	public List<PackageItem> getPackageItemItems() {
		return packageItemItems;
	}

	public void setPackageItemItems(List<PackageItem> packageItemItems) {
		this.packageItemItems = packageItemItems;
	}

	public StockOutOrderConfirm getStockOutOrderConfirm() {
		return stockOutOrderConfirm;
	}

	public void setStockOutOrderConfirm(StockOutOrderConfirm stockOutOrderConfirm) {
		this.stockOutOrderConfirm = stockOutOrderConfirm;
	}
}
