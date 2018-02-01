/*

 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.graby.store.admin.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Objects;
import com.graby.store.admin.util.BaseResource;
import com.graby.store.entity.Person;
import com.graby.store.remote.AuthRemote;
import com.graby.store.remote.CentroRemote;
import com.graby.store.remote.PersonRemote;
import com.graby.store.service.base.AuthService;
import com.graby.store.util.Encodes;

public class ShiroDbRealm extends AuthorizingRealm {

	@Autowired
	protected AuthRemote authRemote;
	@Autowired
	protected CentroRemote centroRemote;
	@Autowired
	protected PersonRemote personRemote;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		try{
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userName", token.getUsername());
		Person person=this.personRemote.searchPerson(params).get(0);
		if (person != null) {
			byte[] salt = Encodes.decodeHex(person.getSalt());
			
			return new SimpleAuthenticationInfo(
					new ShiroUser(person.getId(), person.getUserName()), 
					person.getPassword(),	ByteSource.Util.bytes(salt), getName());
		} else {
			return null;
		}
		/*	User user = authRemote.findUserByUsername(token.getUsername());
			if (user != null) {
				byte[] salt = Encodes.decodeHex(user.getSalt());
				
				return new SimpleAuthenticationInfo(
						new ShiroUser(user.getId(), user.getUsername()), 
						user.getPassword(),	ByteSource.Util.bytes(salt), getName());
			} else {
				return null;
			}*/
		}catch(Exception e){
			e.printStackTrace();
			throw new AuthenticationException(e);
		}
	}
	

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		/*
		User user = authRemote.findUserByUsername(shiroUser.username);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.addRoles(user.getRoleList());*/
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("userName", shiroUser.username);
		Person person=this.personRemote.searchPerson(params).get(0);
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> roles=new ArrayList<String>();
		roles.add(person.getRoles());
		info.addRoles(roles);
		SecurityUtils.getSubject().getSession().setAttribute(BaseResource.ADMIN_USER_KEY, person);
		return info;
	}

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(AuthService.HASH_ALGORITHM);
		matcher.setHashIterations(AuthService.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}
 
	@Autowired
	public void setAuthRemote(AuthRemote authRemote) {
		this.authRemote = authRemote;
	}
	@Autowired
	public void setCentroRemote(CentroRemote centroRemote) {
		this.centroRemote = centroRemote;
	}
	@Autowired
	public void setPersonRemote(PersonRemote personRemote) {
		this.personRemote = personRemote;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {

		private static final long serialVersionUID = -1373760761780840081L;

		private Long userid;
		private String username;
		
		public ShiroUser(Long userid, String username) {
			this.userid = userid;
			this.username = username;
		}

		public String getUsername() {
			return username;
		}

		public Long getUserid() {
			return userid;
		}

		
		public void setUsername(String username) {
			this.username = username;
		}

		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return username;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(username);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (username == null) {
				if (other.username != null)
					return false;
			} else if (!username.equals(other.username))
				return false;
			return true;
		}

	}
}
