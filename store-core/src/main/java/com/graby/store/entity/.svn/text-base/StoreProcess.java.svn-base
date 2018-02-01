package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sc_process")
public class StoreProcess  implements Serializable{

	private static final long serialVersionUID = 1548734360178863536L;


	private Integer id;

    
    private String code;

    
    private String type;

    
    private String createtime;

    
    private String plantime;

    private String servicetype;

   
    private Integer planqty;

   
    private String remark;

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCreatetime() {
		return createtime;
	}

	public void setCreatetime(String createtime) {
		this.createtime = createtime;
	}

	public String getPlantime() {
		return plantime;
	}

	public void setPlantime(String plantime) {
		this.plantime = plantime;
	}

	public String getServicetype() {
		return servicetype;
	}

	public void setServicetype(String servicetype) {
		this.servicetype = servicetype;
	}

	public Integer getPlanqty() {
		return planqty;
	}

	public void setPlanqty(Integer planqty) {
		this.planqty = planqty;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}