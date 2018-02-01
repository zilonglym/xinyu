package com.xinyu.model.order.child;

import java.util.List;

import com.xinyu.model.common.Entity;
import com.xinyu.model.order.StockInOrder;

/**
 * @author shark_cj
 * @since  2017-04-27
 * 装箱信息
 */
public class WmsStockInCaseInfo  extends Entity {
	
	
	private  StockInOrder stockInOrder;
	
	/**
	 * TRUE   箱号
	 */
	private String  caseNumber;
	
	/**
	 * TRUE   箱编码
	 */
	private String  caseSequence;
	
	/**
	 * FALSE   托盘号
	 */
	private  String  palletNumebr;
	
	/**
	 * FALSE   集装箱编号
	 */
	private  String  containerNumber;
	
	/**
	 * FALSE   重量 单位克
	 */
	private   long  weight;
	
	/**
	 * FALSE   体积 单位立方厘米
	 */
	private int    volume;
	
	public String getCaseNumber() {
		return caseNumber;
	}

	public void setCaseNumber(String caseNumber) {
		this.caseNumber = caseNumber;
	}

	public String getCaseSequence() {
		return caseSequence;
	}

	public void setCaseSequence(String caseSequence) {
		this.caseSequence = caseSequence;
	}

	public String getPalletNumebr() {
		return palletNumebr;
	}

	public void setPalletNumebr(String palletNumebr) {
		this.palletNumebr = palletNumebr;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public long getWeight() {
		return weight;
	}

	public void setWeight(long weight) {
		this.weight = weight;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}



	public List<WmsStockInCaseItem> getCaseItemList() {
		return caseItemList;
	}

	public void setCaseItemList(List<WmsStockInCaseItem> caseItemList) {
		this.caseItemList = caseItemList;
	}

	
	public long getHeight() {
		return height;
	}

	public void setHeight(long height) {
		this.height = height;
	}


	/**
	 * fals  长 单位 mm
	 */
	private  long  length;
	
	/**
	 * FALSE  宽 单位 mm
	 */
	private long   width;
	
	/**
	 * false   高 单位 mm
	 */
	private  long  height;
	
	
	
	public long getWidth() {
		return width;
	}

	public void setWidth(long width) {
		this.width = width;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}


	/**
	 * TRUE  装箱明细列表
	 */
	private List<WmsStockInCaseItem>  caseItemList;

	public StockInOrder getStockInOrder() {
		return stockInOrder;
	}

	public void setStockInOrder(StockInOrder stockInOrder) {
		this.stockInOrder = stockInOrder;
	}
	
	
}
