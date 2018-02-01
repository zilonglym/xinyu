package com.xinyu.model.base;

import java.util.Date;
import java.util.List;

import com.xinyu.model.common.Entity;

public class ItemGroup extends Entity{

	//组合名称
	private  String name;
	
	//条码
	private String barCode;
	
	//商家
	private User user;
	
	//最后修改时间
	private  Date  laterDate ;
	
	//状态   是否启用
	private String state;
	
	//备注
	private String remark;
	
	//优先级别
	private String priority;
	
	
	private List<ItemGroupDetail>  detai;


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getLaterDate() {
		return laterDate;
	}


	public void setLaterDate(Date laterDate) {
		this.laterDate = laterDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	public List<ItemGroupDetail> getDetai() {
		return detai;
	}


	public void setDetai(List<ItemGroupDetail> detai) {
		this.detai = detai;
	}


	public String getPriority() {
		return priority;
	}


	public void setPriority(String priority) {
		this.priority = priority;
	}


	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	
}
