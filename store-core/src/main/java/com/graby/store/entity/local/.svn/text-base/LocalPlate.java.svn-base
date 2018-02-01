package com.graby.store.entity.local;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.graby.store.entity.Item;

/**
 * @author shark_cj
 * @since  2017 -8-19
 * 板位表
 */

@Entity
@Table(name = "sc_local_plate")
public class LocalPlate   implements Serializable{
	
	private static final long serialVersionUID = -9174927100028303559L;
	
	private  int  id ;
	
	private LocalBoxOut  boxOut;  //卡位
	
	private  String  code;
	
	private  String   state;
	
	private  String  item;
	
	private Long  num;
	
	private String fastCode;
	
	private  Date  lastUpdateDate;
	
	private String batchId;
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@ManyToOne
	@JoinColumn(name="boxOut_id")
	public LocalBoxOut getBoxOut() {
		return boxOut;
	}

	public void setBoxOut(LocalBoxOut boxOut) {
		this.boxOut = boxOut;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	
	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public String getbatchId() {
		return batchId;
	}

	public void setbatchId(String batchId) {
		this.batchId = batchId;
	}


	public String getFastCode() {
		return fastCode;
	}

	public void setFastCode(String fastCode) {
		this.fastCode = fastCode;
	}

	
}
