package com.graby.store.entity.local;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author shark_cj
 * @since  2017 -8-19
 * 货架表
 */

@Entity
@Table(name = "sc_local_boxout")
public class LocalBoxOut implements Serializable{

	private static final long serialVersionUID = -7805800335975962658L;

	// id
	// 排 001
	// 位 A,B,C,D
	// 层 1,2,4,5
	// 状态 记录卡位上状态,是否全满 或者空卡位

	private int id;

	//行
	private String row;
	
	//卡位
	private String boxOut;
	
	//层
	private String floor;

	//状态
	private String state;

	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBoxOut() {
		return boxOut;
	}

	public void setBoxOut(String boxOut) {
		this.boxOut = boxOut;
	}
	
}
