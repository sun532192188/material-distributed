package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 部门库存表
 * 
 * @author sunxiaorong
 *
 */
@Entity
@Table(name ="departstock")
public class DepartStock {

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 商品编号
	 */
	private Integer goodsId = 0;
	/**
	 * 商品单价
	 */
	private Double goodsPrice = 0.0;
	/**
	 * 数量
	 */
	private Double stockNum = 0.0;
	/**
	 * 1.物资 2.固定资产
	 */
	private Integer goodsType = 0;
	/**
	 * 1.预存 2.调拨 3.部门调拨
	 */
	private Integer stockType = 0;
	/**
	 * 一级分类
	 */
	private Integer categoryOne;
	/**
	 * 二级分类
	 */
	private Integer categoryTwo;
	/**
	 * 备注
	 */
	private String remarks = "";

	/**
	 * 部门编号
	 */
	private Integer departmentId;
	/**
	 * 状态(固定资产使用) 1.未使用   2.使用中  3.已损坏   4.已调拨
	 */
	private Integer status;
	

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="goodsid")
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}
	@Column(name="goodsprice")
	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	@Column(name="stocknum")
	public Double getStockNum() {
		return stockNum;
	}

	public void setStockNum(Double stockNum) {
		this.stockNum = stockNum;
	}
	@Column(name="goodstype")
	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}
	@Column(name="stocktype")
	public Integer getStockType() {
		return stockType;
	}

	public void setStockType(Integer stockType) {
		this.stockType = stockType;
	}
	@Column(name="categoryone")
	public Integer getCategoryOne() {
		return categoryOne;
	}

	public void setCategoryOne(Integer categoryOne) {
		this.categoryOne = categoryOne;
	}
	@Column(name="categorytwo")
	public Integer getCategoryTwo() {
		return categoryTwo;
	}

	public void setCategoryTwo(Integer categoryTwo) {
		this.categoryTwo = categoryTwo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="departmentid")
	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
