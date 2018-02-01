package com.xinyu.model.common;

import java.io.Serializable;
import java.util.UUID;
import java.util.logging.Logger;

import com.xinyu.model.system.Account;

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
		Account account=SessionUser.get();
		if(account!=null){
			System.err.println(account.getCu());
		}else{
			System.err.println("Entity account is null!");
		}
		return this.id;
	}
	
	public Object clone() throws CloneNotSupportedException {  
	     return super.clone();  
	  }
	
}
