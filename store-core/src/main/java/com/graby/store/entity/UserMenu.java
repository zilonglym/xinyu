package com.graby.store.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.graby.store.entity.enums.StoreMenuModelEnums;

/**
 * 用户菜单 功能列表
 * @author huabiao.mahb
 */

@Entity
@Table(name = "sc_user_menu")
public class UserMenu implements Serializable {

	private static final long serialVersionUID = 6394979939798384816L;
	
	private Long id;
	
	private UserRoles roles;
	
	private String title;//显示名称
	
	private String link;//链接URL
	
	private StoreMenuModelEnums menus;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public UserRoles getRoles() {
		return roles;
	}

	public void setRoles(UserRoles roles) {
		this.roles = roles;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public StoreMenuModelEnums getMenus() {
		return menus;
	}

	public void setMenus(StoreMenuModelEnums menus) {
		this.menus = menus;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
}
