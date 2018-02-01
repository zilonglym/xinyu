package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Index;


@Entity
@Table(name = "sc_check_inventory")
public class CheckInventory  implements Serializable{
	
	private static final long serialVersionUID = 8152327912135613260L;

	private Long id;
	//编码
	private String code;
	//用户
	private User person;
	
	private Date createDate;
	
	private Date auditDate;
	
	private String status;
	
	private String type;
	
	private Centro centro;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@Index(name="person_id")
	public User getPerson() {
		return person;
	}

	public void setPerson(User person) {
		this.person = person;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Index(name="centro_id")
	public Centro getCentro() {
		return centro;
	}

	public void setCentro(Centro centro) {
		this.centro = centro;
	}

	@Transient
	public List<CheckInventoryDetail> getDetails() {
		return details;
	}

	public void setDetails(List<CheckInventoryDetail> details) {
		this.details = details;
	}

	public List<CheckInventoryDetail> details;
	
	/* ------------- 库存盘点状态 -------------*/
	public interface Status {
		
		public static final String save = "SAVE";
		
		public static final String audit = "AUDIT";
		
	}
	
	/* ------------- 库存盘点类型 -------------*/
	public interface Types {
		
		/** 盘赢  */
		public static final String up = "UP";
		
		/** 盘亏  */
		public static final String down = "DOWN";
		
	}
	

}
