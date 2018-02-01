package com.graby.store.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "sc_process_item")
public class StoreProcessItem  implements Serializable {
	private static final long serialVersionUID = 2893152068365984339L;
	private Integer id;
    private String type;
    private String itemcode;
    private String itemid;

    private String inventorytype;

    private Integer quantity;

    private Date productdate;

    private Date expiredate;
    private String producecode;

    private String remark;

    private Integer parentid;

   @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public String getItemcode() {
	return itemcode;
}

public void setItemcode(String itemcode) {
	this.itemcode = itemcode;
}

public String getItemid() {
	return itemid;
}

public void setItemid(String itemid) {
	this.itemid = itemid;
}

public String getInventorytype() {
	return inventorytype;
}

public void setInventorytype(String inventorytype) {
	this.inventorytype = inventorytype;
}

public Integer getQuantity() {
	return quantity;
}

public void setQuantity(Integer quantity) {
	this.quantity = quantity;
}

public Date getProductdate() {
	return productdate;
}

public void setProductdate(Date productdate) {
	this.productdate = productdate;
}

public Date getExpiredate() {
	return expiredate;
}

public void setExpiredate(Date expiredate) {
	this.expiredate = expiredate;
}

public String getProducecode() {
	return producecode;
}

public void setProducecode(String producecode) {
	this.producecode = producecode;
}

public String getRemark() {
	return remark;
}

public void setRemark(String remark) {
	this.remark = remark;
}

public Integer getParentid() {
	return parentid;
}

public void setParentid(Integer parentid) {
	this.parentid = parentid;
}

public void setId(Integer id) {
	this.id = id;
}

}