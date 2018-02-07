package com.xinyu.check.dao.base;

import java.io.Serializable;
import java.util.UUID;

public class Entity implements Serializable,Cloneable {
	private static final long serialVersionUID = 981755267881595757L;
	protected String id;

	protected String cu;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCu() {
		return cu;
	}

	public void setCu(String cu) {
		this.cu = cu;
	}

	public String generateId() {
		this.id = UUID.randomUUID().toString();
		return this.id;
	}
	
	public Object clone() throws CloneNotSupportedException {  
	     return super.clone();  
	  }
	
}
