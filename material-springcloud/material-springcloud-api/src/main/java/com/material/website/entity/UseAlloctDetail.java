package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 领用/调拨 物资明细
 * 
 * @author sunxiaorong
 * 
 */
@Entity
@Table(name = "usealloctdetail")
public class UseAlloctDetail {
	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 领用/调拨单编号
	 */
	private Integer useAlloctId ;
	/**
	 * 物资编号
	 */
	private Integer goodsId = 0;
	/**
	 * 物资名称
	 */
	@Column(name="goodsname")
	private String goodsName = "";
	/**
	 * 品牌型号
	 */
	@Column(name="goodstype")
	private Integer goodsType;
	/**
	 * 单位
	 */
	private String spec = "";
	/**
	 * 数量
	 */
	@Column(name="goodsnum")
	private Double goodsNum = 0.0;
	/**
	 * 商品所属大类
	 */
	@Column(name="categoryone")
	private Integer categoryOne = 0;

	/**
	 * 商品所属小类
	 */
	@Column(name="categorytwo")
	private Integer categoryTwo = 0;
	/**
	 * 单价
	 */
	private Double price = 0.0;
	/**
	 * 金额
	 */
	@Column(name="singlemoney")
	private Double singleMoney = 0.0;
	/**
	 * 供应商编号
	 */
	@Column(name="supplierid")
	private Integer supplierId = 0;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="usealloctid")
	public Integer getUseAlloctId() {
		return useAlloctId;
	}

	public void setUseAlloctId(Integer useAlloctId) {
		this.useAlloctId = useAlloctId;
	}
	@Column(name="goodsid")
	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	@Column(name="goodsname")
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name="goodstype")
	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}
	@Column(name="goodsnum")
	public Double getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	@Column(name="singlemoney")
	public Double getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(Double singleMoney) {
		this.singleMoney = singleMoney;
	}
	@Column(name="supplierid")
	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
}
