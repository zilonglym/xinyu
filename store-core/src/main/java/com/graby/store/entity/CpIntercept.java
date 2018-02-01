package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 快递审核拦截(拒单)
 * @author fufanfgjue
 *
 */
@Entity
@Table(name = "sc_company_intercept")
public class CpIntercept implements Serializable{

	private static final long serialVersionUID = -2076384570829237678L;

	private int id;
	
	//地区编码
	private String  code;
	
	//区域名称
	private String  name;
	
	//父类 地区编码
	private String parentCode;
	
	//级别
	private int level;
	
	//所属快递
	private String company;
	
	//是否最后子节点:0（不是），1（是），2（禁用）
	private boolean  end;
	
	//信息
	private String   msg;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isEnd() {
		return end;
	}

	public void setEnd(boolean end) {
		this.end = end;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
