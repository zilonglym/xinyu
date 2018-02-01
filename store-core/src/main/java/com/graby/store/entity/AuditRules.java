package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 审核单 据规则
 * @author yangmin
 *
 */
@Entity
@Table(name = "sc_audit_rules")
public class AuditRules  implements Serializable{

	private static final long serialVersionUID = 6066621812987181287L;
	
	private int id;
	
	private User user;//商家
	
	private String expressCompany;//快递公司 编码
	
	private String includes;//包括到达的省
	
	private String exincludes;//不到达的地址
	
	private double startWegiht;//起始重量
	
	private double endWegiht;//结束重量

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getExpressCompany() {
		return expressCompany;
	}

	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}

	public String getIncludes() {
		return includes;
	}

	public void setIncludes(String includes) {
		this.includes = includes;
	}

	public String getExincludes() {
		return exincludes;
	}

	public void setExincludes(String exincludes) {
		this.exincludes = exincludes;
	}

	public double getStartWegiht() {
		return startWegiht;
	}

	public void setStartWegiht(double startWegiht) {
		this.startWegiht = startWegiht;
	}

	public double getEndWegiht() {
		return endWegiht;
	}

	public void setEndWegiht(double endWegiht) {
		this.endWegiht = endWegiht;
	}
	

}
