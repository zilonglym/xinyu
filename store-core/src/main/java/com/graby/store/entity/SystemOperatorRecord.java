package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.graby.store.entity.enums.OperatorModel;

/**
 * 系统操作记录表
 * @author yangmin
 *
 */
@Entity
@Table(name = "sc_operator_record")
public class SystemOperatorRecord implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8734764687325895636L;
	private int id;
	/**
	 * 操作时间
	 */
	private Date time;  
	
	/**
	 * 操作模块
	 */
	private OperatorModel operatorModel;
	/**
	 * 操作人员
	 */
	private int operator;
	
	/**
	 * 操作商家
	 */
	private int user;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 操作描述
	 */
	private String description;
	/**
	 * 操作人IP
	 */
	private String ipaddr;
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public OperatorModel getOperatorModel() {
		return operatorModel;
	}
	public void setOperatorModel(OperatorModel operatorModel) {
		this.operatorModel = operatorModel;
	}
	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}
