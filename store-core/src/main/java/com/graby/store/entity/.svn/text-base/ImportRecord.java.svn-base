package com.graby.store.entity;

import java.beans.Transient;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "sc_import_record")
public class ImportRecord implements Serializable{

	private static final long serialVersionUID = -7019298447012160980L;

	private Long id;
	
	private Date createDate;
	
	private String createDateStr;
	
	private String  erp;
	
	private String company;//快递公司 
	
	private Long centroId;//仓库编码
	
	private String itemCode;//商品编码
	
	private Long userId; //商家ID
	
	private String expressOrderno ;//面单号
	
	private String msg;
	
	private  String status;
	
	private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	

	public interface ImportRecordStatus {
		/** 成功。*/
		public static final String SUCCESS = "SUCCESS";
		
		/** 失败。*/
		public static final String FAIL = "FAIL";
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		if(createDate!=null){
			setCreateDateStr(df.format(createDate));
		}
		this.createDate = createDate;
	}

	public String getErp() {
		return erp;
	}

	public void setErp(String erp) {
		this.erp = erp;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public Long getCentroId() {
		return centroId;
	}

	public void setCentroId(Long centroId) {
		this.centroId = centroId;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpressOrderno() {
		return expressOrderno;
	}

	
	public void setExpressOrderno(String expressOrderno) {
		this.expressOrderno = expressOrderno;
	}

	@Transient
	public String getCreateDateStr() {
		return createDateStr;
	}

	public void setCreateDateStr(String createDateStr) {
		this.createDateStr = createDateStr;
	}
}
