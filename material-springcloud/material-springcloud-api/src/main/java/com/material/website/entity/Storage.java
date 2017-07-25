package com.material.website.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 入库实体
 * 
 * @author sunxiaorong
 * 
 */
@Entity
@Table(name = "storage")
public class Storage {
	/**
	 * 编号
	 */
	private Integer id;
	/**
	 * 入库编号
	 */
	@Column(name="storageno")
	private String storageNo;
	/**
	 * 供应商编号
	 */
	@Column(name="supplierid")
	private Integer supplierId;
	/**
	 * 入库日期
	 */
	@Column(name="storagedate")
	private Date storageDate = new Date(0);
	/**
	 * 入库物资金额
	 */
	@Column(name="storagemoney")
	private Double storageMoney;
	/**
	 * 入库类型 1.验收  2.预存
	 */
	@Column(name="storagetype")
	private Integer storageType;
	/**
	 * 签收部门
	 */
	@Column(name="signdepart")
	private Integer signDepart;
	/**
	 * 签收员姓名
	 */
	@Column(name="signname")
	private String signName;
	/**
	 * 备注
	 */
	private String remarks;
	/**
	 * 入账状态  0.未入账   1.已入账
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

	public String getStorageNo() {
		return storageNo;
	}

	public void setStorageNo(String storageNo) {
		this.storageNo = storageNo;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Date getStorageDate() {
		return storageDate;
	}

	public void setStorageDate(Date storageDate) {
		this.storageDate = storageDate;
	}

	public Double getStorageMoney() {
		return storageMoney;
	}

	public void setStorageMoney(Double storageMoney) {
		this.storageMoney = storageMoney;
	}

	public Integer getStorageType() {
		return storageType;
	}

	public void setStorageType(Integer storageType) {
		this.storageType = storageType;
	}

	public Integer getSignDepart() {
		return signDepart;
	}

	public void setSignDepart(Integer signDepart) {
		this.signDepart = signDepart;
	}

	public String getSignName() {
		return signName;
	}

	public void setSignName(String signName) {
		this.signName = signName;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
