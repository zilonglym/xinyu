package com.graby.store.entity.local;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 本地商家资料
 * @author Administrator
 *	2017-10-23
 */
@Entity
@Table(name = "sc_local_shop")
public class LocalShop implements Serializable{

	private static final long serialVersionUID = 9089856142909370414L;
	
	private int id;
	
	//商家名称
	private String  name;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

}
