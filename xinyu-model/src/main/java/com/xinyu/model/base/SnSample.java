package com.xinyu.model.base;

import java.util.List;

import com.xinyu.model.common.Entity;
import com.xinyu.model.trade.ShipOrder;

/**
 * 序列子对象
 * 
 * @author yangmin 2017年4月24日
 *
 */
public class SnSample extends Entity {

	private static final long serialVersionUID = 1006037003483426146L;
	
	/**
	 * sn示例顺序
	 */
	private String snSeq;
	
	private Item item;
	/**
	 * sn示例描述
	 */
	private String sampleDesc;
	/**
	 * 示例规则列表
	 */
	private List<SampleRule> sampleRuleList;

	public String getSnSeq() {
		return snSeq;
	}

	public void setSnSeq(String snSeq) {
		this.snSeq = snSeq;
	}

	public String getSampleDesc() {
		return sampleDesc;
	}

	public void setSampleDesc(String sampleDesc) {
		this.sampleDesc = sampleDesc;
	}

	public List<SampleRule> getSampleRuleList() {
		return sampleRuleList;
	}

	public void setSampleRuleList(List<SampleRule> sampleRuleList) {
		this.sampleRuleList = sampleRuleList;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}


}
