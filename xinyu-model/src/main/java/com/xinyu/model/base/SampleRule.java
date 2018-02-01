package com.xinyu.model.base;

import com.xinyu.model.common.Entity;

public class SampleRule extends Entity {

	private static final long serialVersionUID = -2502468217457631122L;
	
	private SnSample snSample;
	/**
	 * 规则描述
	 */
	private String ruleDesc;
	/**
	 * 规则正则表达式
	 */
	private String ruleRegularExpression;
	/**
	 * 规则对应图面url
	 */
	private String ruleImgUrl;
	/**
	 * 规则示例
	 */
	private String ruleSample;

	public String getRuleDesc() {
		return ruleDesc;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public String getRuleRegularExpression() {
		return ruleRegularExpression;
	}

	public void setRuleRegularExpression(String ruleRegularExpression) {
		this.ruleRegularExpression = ruleRegularExpression;
	}

	public String getRuleImgUrl() {
		return ruleImgUrl;
	}

	public void setRuleImgUrl(String ruleImgUrl) {
		this.ruleImgUrl = ruleImgUrl;
	}

	public String getRuleSample() {
		return ruleSample;
	}

	public void setRuleSample(String ruleSample) {
		this.ruleSample = ruleSample;
	}

	public SnSample getSnSample() {
		return snSample;
	}

	public void setSnSample(SnSample snSample) {
		this.snSample = snSample;
	}

}
