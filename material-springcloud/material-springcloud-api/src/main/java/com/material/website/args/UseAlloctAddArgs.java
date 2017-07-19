package com.material.website.args;

import org.zh.validate.annotation.NotEmpty;


public class UseAlloctAddArgs {
	
	private Integer id;
	/**
	 * 领用/调拨编号
	 */
	@NotEmpty
	private String operatNo = "";
	/**
	 * 领用部门
	 */
	@NotEmpty
	private Integer departmentId;
	/**
	 * 总金额
	 */
	@NotEmpty
	private Double sumMoney;
	/**
	 * 备注
	 */
	private String remarks = "";
	/**
	 * 操作类型  1.调拨  2.领用   
	 */
	@NotEmpty
	private Integer type;
	
	/**
	 * 领用员
	 */
	@NotEmpty
	private String useName;

	public String getOperatNo() {
		return operatNo;
	}

	public void setOperatNo(String operatNo) {
		this.operatNo = operatNo;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Double getSumMoney() {
		return sumMoney;
	}

	public void setSumMoney(Double sumMoney) {
		this.sumMoney = sumMoney;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getUseName() {
		return useName;
	}

	public void setUseName(String useName) {
		this.useName = useName;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
