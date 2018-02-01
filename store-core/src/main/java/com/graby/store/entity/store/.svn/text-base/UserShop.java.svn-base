package com.graby.store.entity.store;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.graby.store.entity.User;

/**
 * 商家店铺设置
 * @author lenovo
 *
 */
@Entity
@Table(name = "sc_usershop")
public class UserShop {

	private static final long serialVersionUID = 7432590876019720817L;
	
	private Long id;
	
	private User user; //商家
	
	private String name;//店铺用户名
	
	private String sessionKey;//登录 用key
	
	private ShopTypeEnums shopSource;//店铺来源
	
	private StatusEnums status;//店铺状态
	
	private Date createTime;
	
	private String createBy;
	
	private String description;//备注
	
	private Date lastUpdateTime;
	
	private String lastUpdateBy;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSessionKey() {
		return sessionKey;
	}

	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateBy() {
		return lastUpdateBy;
	}

	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}

	public ShopTypeEnums getShopSource() {
		return shopSource;
	}

	public void setShopSource(ShopTypeEnums shopSource) {
		this.shopSource = shopSource;
	}

	public StatusEnums getStatus() {
		return status;
	}

	public void setStatus(StatusEnums status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
