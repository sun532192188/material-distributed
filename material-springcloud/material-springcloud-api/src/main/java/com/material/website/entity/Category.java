package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 商品类型实体
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="category")
public class Category {
   
	/**
	 * 主键编号
	 */
	private Integer id;
	/**
	 * 类型名称
	 */
	private String categoryName;
    /**
     * 父类型编号
     */
	private Integer parentId;
	
	/**
	 * 分类编号
	 */
	private String categoryNo;
	
	/**
	 * 分类 0.未移除   1.已移除
	 */
	private Integer status = 0;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="categoryname")
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Column(name="parentid")
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	@Column(name="categoryno")
	public String getCategoryNo() {
		return categoryNo;
	}
	public void setCategoryNo(String categoryNo) {
		this.categoryNo = categoryNo;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
