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
package com.visionet.letsdesk.app.user.service;

import com.google.common.base.Objects;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.security.password.PwdEncryptor;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.base.rest.RestException;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.utils.Encodes;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class ShiroDbRealm extends AuthorizingRealm {

	protected AccountService accountService;

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;

		User user = accountService.findByLoginName(token.getUsername());


		if (user == null) return null;

		if (user.getIsLock().equals(SysConstants.USER_ACTIVITY_DISABLED)) {
			throw new DisabledAccountException();
		}

		if(user.getRoleSet()==null || user.getRoleSet().isEmpty()){
			throw new RestException("role can not be null!");
		}

		if(user.getOrgId()==null){
			throw new RestException("organization can not be null!");
		}

        ShiroUser shiroUser = new ShiroUser(user.getId(),
                user.getAliasName(), user.getLoginName(), user.getOrgId(),
                user.getIsLock(), user.getLastLogin(),user.getRoleSet(),
                new String(token.getPassword()));
        if(Validator.isNotNull(user.getPasswordSalt())){
            byte[] salt = Encodes.decodeHex(user.getPasswordSalt());
            return new SimpleAuthenticationInfo(shiroUser,user.getPassword(), ByteSource.Util.bytes(salt), getName());
        }else{
            return new SimpleAuthenticationInfo(shiroUser,user.getPassword(),getName());
        }

	}

	/**
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		User user = accountService.findByLoginName(shiroUser.loginName);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		for (Role role : user.getRoleSet()) {
			//基于Role的权限信息
			info.addRole(role.getName());
			//基于Permission的权限信息
			info.addStringPermissions(role.getPermissionList());
		}
		
		return info;
	}
	

	/**
	 * 设定Password校验的Hash算法与迭代次数.
	 */
	@PostConstruct
	public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(PwdEncryptor.PASSWORDS_ENCRYPTION_ALGORITHM);
		matcher.setHashIterations(PwdEncryptor.HASH_INTERATIONS);

		setCredentialsMatcher(matcher);
	}

	@Autowired
	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	/**
	 * 自定义Authentication对象，使得Subject除了携带用户的登录名外还可以携带更多信息.
	 */
	public static class ShiroUser implements Serializable {
		private static final long serialVersionUID = -1373760761780840081L;
		public Long id;
		public String loginName;
		public String aliasName;
		
		public Long orgId;

		public Integer isLock;
		public Integer isLockSec;
		public Long secId;
		public Date lastLogin;
		public Set<Role> roleList ; 
		private String plainPassword;
		public String secName;
		public String userImgUrl;
		public String locale;
	
		public ShiroUser(String loginName) {
			this.loginName = loginName;
		}
		
		public ShiroUser(Long id, String loginName) {
			this.id = id;
			this.loginName = loginName;
		}
		
		
		public ShiroUser(Long id, String aliasName,String loginName,Long orgId,Integer isLock,
				Date lastLogin,Set<Role> roleList,String plainPassword) {
			this.id = id;
			this.aliasName = aliasName;
			this.loginName = loginName;
			this.orgId = orgId;
			this.isLock = isLock;
			this.lastLogin = lastLogin;
			this.roleList = roleList;
		}

        private static void setLoaclNull(){
            BaseController.DefaultLocale = null;
        }

		public Long getUserId() {
			return this.id;
		}
		
		public Long getOrgId() {
			return this.orgId;
		}
		
		public String getName() {
			return this.aliasName;
		}
		
		public String getPlainPassword() {
			return plainPassword;
		}

		public String getUserImgUrl(){
			return userImgUrl;
		}
		public String getRoleNames() {
			return Collections3.extractToString(roleList, "name", ", ");
		}


		/**
		 * 本函数输出将作为默认的<shiro:principal/>输出.
		 */
		@Override
		public String toString() {
			return loginName;
		}

		/**
		 * 重载hashCode,只计算loginName;
		 */
		@Override
		public int hashCode() {
			return Objects.hashCode(loginName);
		}

		/**
		 * 重载equals,只计算loginName;
		 */
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			ShiroUser other = (ShiroUser) obj;
			if (loginName == null) {
				if (other.loginName != null)
					return false;
			} else if (!loginName.equals(other.loginName))
				return false;
			return true;
		}
	}
}
