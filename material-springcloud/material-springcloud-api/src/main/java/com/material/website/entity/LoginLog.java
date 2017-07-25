package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 登录日志实体
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="loginlog")
public class LoginLog {
    /**
     * 编号
     */
	private Integer id;
	/**
	 * 用户编号
	 */
	@Column(name="userid")
	private Integer userId = 0 ;
	/**
	 * 用户名
	 */
	@Column(name="username")
	private String userName= "";
	/**
	 * 登录时间
	 */
	@Column(name="logintime")
	private String loginTime = "";
	/**
	 * 登录日志表    1 正在登录  0 未登录
	 */
	private Integer status = -1;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
