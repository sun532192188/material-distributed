package com.material.website.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 供应商
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="supplier")
public class Supplier {
    /**
     * 主键编号
     */
	private Integer id;
	/**
	 * 全称
	 */
	private String fullName;
	/**
	 * 简称
	 */
	private String shortName;
	/**
	 * 联系人姓名
	 */
	private String linkman;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 联系电话
	 */
	private String phone;
	/**
	 * 持卡人姓名
	 */
	private String cardholder;
	/**
	 * 开户行
	 */
	private String bank;
	/**
	 * 开户行所在地
	 */
	private String bankAddr;
	/**
	 * 银行卡帐号
	 */
	private String bankCard;
	
	@Id
	@GeneratedValue
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@Column(name="fullname")
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	@Column(name="shortname")
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCardholder() {
		return cardholder;
	}
	public void setCardholder(String cardholder) {
		this.cardholder = cardholder;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	@Column(name="bankaddr")
	public String getBankAddr() {
		return bankAddr;
	}
	public void setBankAddr(String bankAddr) {
		this.bankAddr = bankAddr;
	}
	@Column(name="bankcard")
	public String getBankCard() {
		return bankCard;
	}
	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}
}
