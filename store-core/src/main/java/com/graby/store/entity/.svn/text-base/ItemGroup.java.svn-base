package com.graby.store.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "sc_itemgroup")
public class ItemGroup implements Serializable{

	private static final long serialVersionUID = 4749520819375302462L;
	
	private Long id;
	
	private String cu;
	
	//组合名称
	private  String name;
	
	//条码
	private  String barCode;
	
	//商家
	private User user;
	
	//最后修改时间
	private  Date  laterDate ;
	
	//状态   是否启用
	private String state;
	
	//备注
	private String remark;
	
	//优先级别1,2,3,4,5,6
	private int priority;
	
	private List<ItemGroupDetail>  detail = new ArrayList<ItemGroupDetail>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getCu() {
		return cu;
	}


	public void setCu(String cu) {
		this.cu = cu;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getLaterDate() {
		return laterDate;
	}


	public void setLaterDate(Date laterDate) {
		this.laterDate = laterDate;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}


	@OneToMany(mappedBy = "itemGroup")
	public List<ItemGroupDetail> getDetail() {
		return detail;
	}

	public void setDetail(List<ItemGroupDetail> detail) {
		this.detail = detail;
	}


	public int getPriority() {
		return priority;
	}


	public void setPriority(int priority) {
		this.priority = priority;
	}


	public String getBarCode() {
		return barCode;
	}


	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	
}
