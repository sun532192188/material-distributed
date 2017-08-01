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
	private Integer userId = 0 ;
	/**
	 * 用户名
	 */
	private String userName= "";
	/**
	 * 登录时间
	 */
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
	@Column(name="userid")
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	@Column(name="username")
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name="logintime")
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
