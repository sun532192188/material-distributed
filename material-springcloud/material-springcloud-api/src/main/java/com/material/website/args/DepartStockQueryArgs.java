package com.material.website.args;

/**
 * 部门库存查询参数类
 * @author sunxiaorong
 *
 */
public class DepartStockQueryArgs {

	private String  goodsName;
	
	private Integer categoryOne;
	
	private Integer categoryTwo;
	/**
	 * 1.领用  2调拨
	 */
	private Integer type;
	
	private Integer departmentId;
	
	/**
	 * 商品类型  1.物资 2 固定资产
	 */
	private Integer goodsType;
	
	private String returnPage;
	
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Integer getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(Integer goodsType) {
		this.goodsType = goodsType;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
}
