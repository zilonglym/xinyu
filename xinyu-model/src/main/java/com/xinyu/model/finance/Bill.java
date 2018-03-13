package com.xinyu.model.finance;

import java.util.Date;

import com.xinyu.model.common.Entity;

/**
 * 财务帐单
 * @author yangmin
 * 2018年3月12日
 *
 */
public class Bill extends Entity {

	private String cpCode;
	/**
	 * 快递单号
	 */
	private String orderCode;

	private Date date;
	
	private double weight;
	
	private String state;
	
}
