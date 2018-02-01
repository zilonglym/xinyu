package com.xinyu.check.model;

import java.io.Serializable;

/**
 * 系统数据
 * 
 * @author yangmin
 */
public class SystemItem  implements Serializable{

	private static final long serialVersionUID = 7855809701869500049L;
	
	private String id;
	
	private String value;
	
	private String description;
	
	private String type;
	
	private int parentId;
	
	private int seq;
	
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	

}
