package com.material.website.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 领用调拨实体
 * @author sunxiaorong
 *
 */
@Entity
@Table(name="useAlloct")
public class UseAlloct {
	/**
     * 领用编号
     */
	private Integer id;
	/**
	 * 领用/调拨编号
	 */
	private String operatNo = "";
	/**
	 * 领用/调拨日期
	 */
	private Date useAlloctDate = new Date();
	/**
	 * 领用部门
	 */
	private Integer departmentId = 0;
	/**
	 * 领用员
	 */
	private String useName;
	/**
	 * 总金额
	 */
	private Double sumMoney = 0.0;
	/**
	 * 备注
	 */
	private String remarks = "";
	/**
	 * 操作类型   1.调拨   2.领用 3.部门入库
	 */
	private Integer type = 0;
	
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
	public String getOperatNo() {
		return operatNo;
	}
	public void setOperatNo(String operatNo) {
		this.operatNo = operatNo;
	}
	public Date getUseAlloctDate() {
		return useAlloctDate;
	}
	public void setUseAlloctDate(Date useAlloctDate) {
		this.useAlloctDate = useAlloctDate;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
