package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 库存实体
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="stock")
public class Stock {
   
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
	 * 供应商
	 */
	@Column(name="supplierid")
	public Integer supplierId;
	/**
	 * 数量
	 */
	@Column(name="stocknum")
	private Double stockNum = 0.0;
	/**
	 * 1.物资  2.固定资产
	 */
	@Column(name="goodstype")
	private Integer goodsType = 0;
	/**
	 * 1.验收  2.预存
	 */
	@Column(name="stocktype")
	private Integer stockType = 0 ;
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
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	@Column(name="supplierid")
	public Integer getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
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
}
