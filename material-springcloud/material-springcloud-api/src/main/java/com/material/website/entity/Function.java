package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统操作员模块功能实体
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="function")
public class Function {
   
	/**
	 * 主键编号
	 */
	private Integer id;
	/**
	 * 功能名称
	 */
	@Column(name="functionname")
	private String functionName = "";
	
	/**
	 * 功能路径
	 */
	private String url = "";
	/**
	 * 父模块编号
	 */
	@Column(name="parentid")
	private Integer parentId = 0;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="functionname")
	public String getFunctionName() {
		return functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	@Column(name="parentid")
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
}
