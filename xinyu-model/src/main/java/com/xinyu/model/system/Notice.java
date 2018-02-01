package com.xinyu.model.system;

import java.util.Date;

import com.xinyu.model.common.Entity;

/**
 * 首页公告信息
 * */
public class Notice extends Entity{
	
	//公告信息
	private String msg;
	
	//更新时间
	private Date lastUpdate;
	
	//发布公告人员
	private Account account;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}
