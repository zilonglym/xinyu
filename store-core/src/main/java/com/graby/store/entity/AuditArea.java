package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sc_audit_area")
public class AuditArea   implements Serializable{

	private static final long serialVersionUID = 4930195250555634944L;
	
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
	private String copmany;
	
	//是否最子集节点
	private boolean  end;
	
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

	public String getCopmany() {
		return copmany;
	}

	public void setCopmany(String copmany) {
		this.copmany = copmany;
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
