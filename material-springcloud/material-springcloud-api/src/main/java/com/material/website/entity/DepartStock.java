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
	@Column(name="goodsid")
	private Integer goodsId = 0;
	/**
	 * 商品单价
	 */
	@Column(name="goodsprice")
	private Double goodsPrice = 0.0;
	/**
	 * 数量
	 */
	@Column(name="stocknum")
	private Double stockNum = 0.0;
	/**
	 * 1.物资 2.固定资产
	 */
	@Column(name="goodstype")
	private Integer goodsType = 0;
	/**
	 * 1.预存 2.调拨 3.部门调拨
	 */
	@Column(name="stocktype")
	private Integer stockType = 0;
	/**
	 * 一级分类
	 */
	@Column(name="categoryone")
	private Integer categoryOne;
	/**
	 * 二级分类
	 */
	@Column(name="categorytwo")
	private Integer categoryTwo;
	/**
	 * 备注
	 */
	private String remarks = "";

	/**
	 * 部门编号
	 */
	@Column(name="departmentid")
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

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public Double getGoodsPrice() {
		return goodsPrice;
	}

	public void setGoodsPrice(Double goodsPrice) {
		this.goodsPrice = goodsPrice;
	}

	public Double getStockNum() {
		return stockNum;
	}

	public void setStockNum(Double stockNum) {
		this.stockNum = stockNum;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public Integer getStockType() {
		return stockType;
	}

	public void setStockType(Integer stockType) {
		this.stockType = stockType;
	}

	public Integer getCategoryOne() {
		return categoryOne;
	}

	public void setCategoryOne(Integer categoryOne) {
		this.categoryOne = categoryOne;
	}

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
