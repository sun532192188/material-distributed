package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 物资消耗商品目录
 * 
 * @author sunxiaorong
 *
 */
@Entity
@Table(name ="materialconsumedetail")
public class MaterialConsumeDetail {

	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 消耗编号
	 */
	@Column(name="outstockid")
	private Integer outStockId;
	/**
	 * 入库商品
	 */
	@Column(name="goodsid")
	private Integer goodsId = 0;
	/**
	 * 商品单价
	 */
	@Column(name="goodsprice")
	private Double goodsPrice = 0.0;
	/**
	 * 入库数量
	 */
	@Column(name="goodsnum")
	private Double goodsNum = 0.0;
	/**
	 * 金额
	 */
	@Column(name="singlemoney")
	private Double singleMoney = 0.0;

	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="outstockid")
	public Integer getOutStockId() {
		return outStockId;
	}

	public void setOutStockId(Integer outStockId) {
		this.outStockId = outStockId;
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
	@Column(name="goodsnum")
	public Double getGoodsNum() {
		return goodsNum;
	}

	public void setGoodsNum(Double goodsNum) {
		this.goodsNum = goodsNum;
	}
	@Column(name="singlemoney")
	public Double getSingleMoney() {
		return singleMoney;
	}

	public void setSingleMoney(Double singleMoney) {
		this.singleMoney = singleMoney;
	}
}
