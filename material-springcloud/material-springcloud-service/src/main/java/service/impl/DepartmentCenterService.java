package com.material.website.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.material.website.args.DepartPlanAddArgs;
import com.material.website.args.DepartPlanQueryArgs;
import com.material.website.args.DepartStockQueryArgs;
import com.material.website.args.MaterialConsumeAddArgs;
import com.material.website.args.MaterialConsumeQueryArgs;
import com.material.website.args.StaticsDepartPlanArgs;
import com.material.website.args.StatisDepartConsumeArgs;
import com.material.website.dao.IDepartmentCenterDao;
import com.material.website.dao.IDepartmentDao;
import com.material.website.dao.IGoodsDao;
import com.material.website.dao.IStorageDao;
import com.material.website.dao.IUseAlloctDao;
import com.material.website.dto.DeparPlanDto;
import com.material.website.dto.DepartStockDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.MaterialConsumeDto;
import com.material.website.dto.MonthPlanGoodsDto;
import com.material.website.dto.StaticsDepartPlanDto;
import com.material.website.dto.StatisDepartCounsumeDto;
import com.material.website.dto.StockDto;
import com.material.website.entity.DepartPlan;
import com.material.website.entity.DepartStock;
import com.material.website.entity.Department;
import com.material.website.entity.Goods;
import com.material.website.entity.MaterialApply;
import com.material.website.entity.MaterialConsume;
import com.material.website.entity.MaterialConsumeDetail;
import com.material.website.entity.UseAlloct;
import com.material.website.entity.UseAlloctDetail;
import com.material.website.service.IDepartmentCenterService;
import com.material.website.system.MaterialOperate;
import com.material.website.system.Pager;
import com.material.website.util.BigDecimaUtil;
import com.material.website.util.MaterialNoUtil;

@Service
public class DepartmentCenterService implements IDepartmentCenterService {

	@Inject
	private IDepartmentCenterDao departmentCenterDao;
	@Inject
	private IGoodsDao goodsDao;
	@Inject
	private IUseAlloctDao useAlloctDao;
	@Inject
	private IDepartmentDao departmentDao;
	@Inject
	private IStorageDao storageDao;

	@Override
	public Pager<DeparPlanDto> queryPlanPager(DepartPlanQueryArgs queryArgs) {
		return departmentCenterDao.queryPlanPager(queryArgs);
	}

	@Override
	public List<MonthPlanGoodsDto> queryMonthPlanGoods(Integer planId) {
		return departmentCenterDao.queryMonthPlanGoods(planId);
	}

	@Override
	public boolean addMonthPlan(DepartPlanAddArgs addArgs) {
		try {
			String operatNo = addArgs.getTempId();
			DepartPlan plan = new DepartPlan();
			plan.setCreateDate(new Date());
			plan.setDepartmentId(addArgs.getDepartmentId());
			plan.setDepartmentName(addArgs.getDepartmentName());
			plan.setRemarks(addArgs.getRemarks());
			plan.setStatus(addArgs.getStatus());
			plan.setSumPrice(addArgs.getSumPrice());
			plan.setPlanName(addArgs.getPlanName());
			plan = (DepartPlan) departmentCenterDao.add(plan);
			List<GoodsInstallDto> goodsList = goodsDao.queryAllTemp(null,
					operatNo, null);
			for (GoodsInstallDto dto : goodsList) {
				MaterialApply apply = new MaterialApply();
				BeanUtils.copyProperties(dto, apply);
				apply.setPlanId(plan.getId());
				apply.setSinglePrice(dto.getPrice() * dto.getGoodsNum());
				goodsDao.addEntity(apply);
			}
			goodsDao.delAllTemp(null, addArgs.getTempId(), null);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Object> addDepartOutStock(MaterialConsumeAddArgs addArgs) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 记录部门物资消耗生成ck单号
			MaterialConsume consume = new MaterialConsume();
			BeanUtils.copyProperties(addArgs, consume);
			if (addArgs.getTargetDepartId() == null) {
				consume.setTargetDepartId(addArgs.getDepartmentId());
				consume.setType(1);
			}
			consume.setDepartId(addArgs.getDepartmentId());
			consume.setConsumeDate(new Date());
			consume.setStatus(0);
			consume = (MaterialConsume) departmentCenterDao.add(consume);
			// 2.更新临时表中的信息
			Integer operatType = null;
			if (consume.getType() == 1) {
				operatType = 5;
			} else if (consume.getType() == 2) {
				operatType = 6;
			}
			storageDao.updateOperatTemp(consume.getId(), operatType,
					consume.getOperatNo());
			map.put("status", 200);
			map.put("msg", "操作成功!");
			return map;
		} catch (BeansException e) {
			map.put("status", 500);
			map.put("msg", "出库失败!");
			e.printStackTrace();
			return map;
		}
	}

	@Override
	public boolean updateMonthPlanStatus(Integer planId) {
		try {
			departmentCenterDao.updateMonthPlanStatus(planId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delPlanGoods(Integer goodsId) {
		MaterialApply apply = (MaterialApply) departmentCenterDao.loadEntity(
				MaterialApply.class, goodsId);
		if (apply != null) {
			departmentCenterDao.deleteEntity(apply);
			return true;
		}
		return false;
	}

	@Override
	public String updatePlanGoods(Integer planId, String goodsStr) {
		try {
			if (planId == null || StringUtils.isEmpty(goodsStr)) {
				return null;
			}
			String[] goodsArray = goodsStr.split(";");
			Double sumMoney = 0.0;
			for (String str : goodsArray) {
				String[] goods = str.split(",");
				Integer goodsId = Integer.valueOf(goods[0]);
				Double goodsNum = Double.valueOf(goods[1]);
				Double goodsPrice = Double.valueOf(goods[2]);
				goodsPrice = BigDecimaUtil.formatDouble(goodsPrice);
				MaterialApply apply = departmentCenterDao.queryGoodsByParam(
						goodsId, goodsPrice, planId);
				if (apply != null) {
					apply.setGoodsNum(goodsNum + apply.getGoodsNum());
					Double singlePrice = goodsNum * goodsPrice;
					singlePrice = singlePrice + apply.getSinglePrice();
					singlePrice = BigDecimaUtil.formatDouble(singlePrice);
					apply.setSinglePrice(singlePrice);
					departmentCenterDao.updateEntity(apply);
					sumMoney += singlePrice;
				} else {
					apply = new MaterialApply();
					apply.setGoodsId(goodsId);
					apply.setGoodsNum(goodsNum);
					apply.setPrice(goodsPrice);
					Double singlePrice = goodsNum * goodsPrice;
					singlePrice = BigDecimaUtil.formatDouble(singlePrice);
					apply.setSinglePrice(singlePrice);
					apply.setPlanId(planId);
					departmentCenterDao.addEntity(apply);
					sumMoney += goodsNum * goodsPrice;
				}

			}
			// 更新计划表总价格
			DepartPlan plan = (DepartPlan) departmentCenterDao.getEntity(
					DepartPlan.class, planId);
			sumMoney = BigDecimaUtil.formatDouble(sumMoney);
			plan.setSumPrice(sumMoney);
			departmentCenterDao.updateEntity(plan);
			return sumMoney.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Pager<MaterialConsumeDto> queryConsumePager(
			MaterialConsumeQueryArgs queryArgs) {
		return departmentCenterDao.queryConsumePager(queryArgs);
	}

	@Override
	public Pager<DepartStockDto> queryDepartStockPager(
			DepartStockQueryArgs queryArgs) {
		return departmentCenterDao.queryDepartStockPager(queryArgs);
	}

	@Override
	public Integer queryMaxCkCout(Integer departId) {
		return departmentCenterDao.queryMaxCkCout(departId);
	}

	@Override
	public List<StockDto> queryDepartStockList(DepartStockQueryArgs queryArgs) {
		List<StockDto> list = departmentCenterDao
				.queryDepartStockList(queryArgs);
		List<StockDto> departStockList = new ArrayList<StockDto>();
		Double tempNum = null;
		for (StockDto dto : list) {
			Double price = Double.valueOf(dto.getGoodsPrice().replaceAll(",",
					""));
			tempNum = goodsDao.queryTemp(dto.getGoodsId(), "departCk", price);
			Double stockNum = dto.getStockNum() - tempNum;
			if (stockNum > 0) {
				dto.setStockNum(stockNum);
				departStockList.add(dto);
			}
		}
		return departStockList;
	}

	@Override
	public List<GoodsInstallDto> queryCkGoodsList(Integer ckNo) {
		return departmentCenterDao.queryCkGoodsList(ckNo);
	}

	/**
	 * 根据编号更改固定资产使用状态
	 * 
	 * @param id
	 * @param type
	 * @param remarks
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean updateFixedGoodsStatus(Integer id, Integer type,
			String remarks, Integer departId, Integer targetDepart) {
		try {
			if (type == 4) {
				Department department = departmentDao.get(departId);
				DepartStock departStock = departmentCenterDao
						.queryDepartStockById(id);
				Goods goods = goodsDao.get(departStock.getGoodsId());
				if (departStock == null) {
					return false;
				}
				// 1.生成部门调拨单
				Integer count = useAlloctDao.queryUseAlloct(3);
				UseAlloct useAlloct = new UseAlloct();
				useAlloct.setOperatNo(MaterialNoUtil.getNo(
						MaterialOperate.BMDB.getName(), count + 1));
				useAlloct.setDepartmentId(targetDepart);
				useAlloct.setSumMoney(departStock.getGoodsPrice());
				useAlloct.setRemarks("该物资由【" + department.getDepartmentName()
						+ "】调拨");
				useAlloct.setType(3);
				useAlloct.setUseAlloctDate(new Date());
				useAlloct.setUseName("系统");
				useAlloct = useAlloctDao.add(useAlloct);
				// 添加部门调拨详情
				UseAlloctDetail userAlloctDetail = new UseAlloctDetail();
				userAlloctDetail.setUseAlloctId(useAlloct.getId());
				userAlloctDetail.setSingleMoney(departStock.getGoodsPrice());
				userAlloctDetail.setCategoryOne(goods.getCategoryOne());
				userAlloctDetail.setCategoryTwo(goods.getCategoryTwo());
				userAlloctDetail.setGoodsId(departStock.getGoodsId());
				userAlloctDetail.setGoodsName(goods.getGoodsName());
				userAlloctDetail.setGoodsNum(1D);
				userAlloctDetail.setGoodsType(goods.getGoodsType());
				userAlloctDetail.setPrice(departStock.getGoodsPrice());
				userAlloctDetail.setSpec(goods.getSpec());
				userAlloctDetail.setSupplierId(goods.getSupplierId());
				departmentCenterDao.addEntity(userAlloctDetail);
				// 2.更改被调拨部门库存量
				DepartStock saveStock = new DepartStock();
				BeanUtils.copyProperties(departStock, saveStock);
				saveStock.setRemarks("该物资由【" + department.getDepartmentName()
						+ "】调拨");
				saveStock.setDepartmentId(targetDepart);
				saveStock.setStatus(1);
				saveStock.setStockType(3);
				departmentCenterDao.addEntity(saveStock);
			}
			departmentCenterDao.updateFixedGoodsStatus(id, type, remarks);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Pager<StaticsDepartPlanDto> staticsDepartPlan(
			StaticsDepartPlanArgs queryArgs) {
		return departmentCenterDao.staticsDepartPlan(queryArgs);
	}

	@Override
	public Pager<StatisDepartCounsumeDto> statisDepartConsumePager(
			StatisDepartConsumeArgs queryArgs) {
		return departmentCenterDao.statisDepartConsumePager(queryArgs);
	}

	@Override
	public Map<String, Object> updateDepartConsumeInit(Integer consumeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		MaterialConsume consume = departmentCenterDao.queryConsumeInfo(null,
				consumeId);
		List<GoodsInstallDto> resultList = goodsDao.queryAllTemp(null,
				consume.getOperatNo(), consume.getId());
		map.put("consume", consume);
		map.put("resultList", resultList);
		return map;
	}

	@Override
	public boolean updateDepartConsume(MaterialConsumeAddArgs updateArgs) {
		try {
			MaterialConsume consume = departmentCenterDao.queryConsumeInfo(null, updateArgs.getId());
			Date date = consume.getConsumeDate();
			Integer tagetDepartId = consume.getTargetDepartId();
			BeanUtils.copyProperties(updateArgs, consume);
			consume.setConsumeDate(date);
			if(updateArgs.getTargetDepartId() == null){
				consume.setTargetDepartId(tagetDepartId);
			}
			String sql = "update materialconsume set "
					+ "operatNo='"+updateArgs.getOperatNo()+"',consumeDate='"+date+
					"',consumeMoney="+updateArgs.getConsumeMoney()+",type="+updateArgs.getType()+
					",targetDepartId="+updateArgs.getTargetDepartId()+","
							+ "remarks='"+updateArgs.getRemarks()+"' where id="+updateArgs.getId();
			departmentCenterDao.updateBySql(sql, null);
			// 2.更新临时表中的信息
			Integer operatType = null;
			if(updateArgs.getType() == 1){
				operatType = 5;
			}else if(updateArgs.getType() == 2){
				operatType = 6;
			}
			storageDao.updateOperatTemp(updateArgs.getId(),
					operatType, updateArgs.getOperatNo());
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public MaterialConsume queryConsumeInfo(String operatNo, Integer consumeId) {
		return departmentCenterDao.queryConsumeInfo(operatNo, consumeId);
	}

	@Override
	public boolean addLockConsume(Integer consumeId) {
		try {
			MaterialConsume consume = departmentCenterDao.queryConsumeInfo(
					null, consumeId);
			UseAlloct useAlloct = null;
			if (consume.getType() == 2) {
				// 等于2为部门调拨
				// 查询今天部门调拨数量
				// 记录部门调拨生成单号
				Integer count = useAlloctDao.queryUseAlloct(3);
				useAlloct = new UseAlloct();
				useAlloct.setOperatNo(MaterialNoUtil.getNo(
						MaterialOperate.BMDB.getName(), count + 1));
				useAlloct.setDepartmentId(consume.getTargetDepartId());
				useAlloct.setSumMoney(consume.getConsumeMoney());
				useAlloct.setRemarks(consume.getRemarks());
				useAlloct.setType(3);
				useAlloct.setUseAlloctDate(new Date());
				useAlloct.setUseName("系统");
				useAlloct = useAlloctDao.add(useAlloct);
			}
			List<GoodsInstallDto> resultList = goodsDao.queryAllTemp(null,
					consume.getOperatNo(), null);
			for (GoodsInstallDto dto : resultList) {
				DepartStock departStock = departmentCenterDao
						.queryDepartStockList(consume.getDepartId(),
								dto.getGoodsId(), dto.getPrice(), null);
				if (departStock != null
						&& departStock.getStockNum() >= dto.getGoodsNum()) {
					// 记录物资消耗详情
					MaterialConsumeDetail detail = new MaterialConsumeDetail();
					detail.setGoodsId(dto.getGoodsId());
					detail.setGoodsNum(dto.getGoodsNum());
					detail.setGoodsPrice(dto.getPrice());
					detail.setSingleMoney(dto.getGoodsNum() * dto.getPrice());
					detail.setOutStockId(consume.getId());
					departmentCenterDao.addEntity(detail);
					// 更改部门库存数量(对应减少)
					departStock.setStockNum(departStock.getStockNum()
							- dto.getGoodsNum());
					departmentCenterDao.updateEntity(departStock);
					if (consume.getType() == 1) {
						consume.setTargetDepartId(consume.getDepartId());
					} else if (consume.getType() == 2) {
						Department department = departmentDao.get(consume
								.getDepartId());
						Goods goods = goodsDao.get(dto.getGoodsId());
						// 添加部门调拨详情
						UseAlloctDetail userAlloctDetail = new UseAlloctDetail();
						BeanUtils.copyProperties(dto, userAlloctDetail);
						userAlloctDetail.setUseAlloctId(useAlloct.getId());
						userAlloctDetail.setSingleMoney(dto.getGoodsNum()
								* dto.getPrice());
						userAlloctDetail.setCategoryOne(goods.getCategoryOne());
						userAlloctDetail.setCategoryTwo(goods.getCategoryTwo());
						departmentCenterDao.addEntity(userAlloctDetail);
						// 调拨部门库存对应增加
						Double goodsPrice = departStock.getGoodsPrice();
						departStock = new DepartStock();
						departStock.setCategoryOne(goods.getCategoryOne());
						departStock.setCategoryTwo(goods.getCategoryTwo());
						departStock
								.setDepartmentId(consume.getTargetDepartId());
						departStock.setGoodsId(goods.getId());
						departStock.setGoodsPrice(goodsPrice);
						departStock.setGoodsType(goods.getGoodsType());
						departStock.setRemarks("该物资由"
								+ department.getDepartmentName() + "部门调拨");
						departStock.setStockNum(dto.getGoodsNum());
						departStock.setStockType(3);
						departmentCenterDao.addEntity(departStock);
					}
				}
			}
			consume.setStatus(1);
			departmentCenterDao.updateEntity(consume);
			goodsDao.delAllTemp(null, consume.getOperatNo(), null);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return false;
	}
}
