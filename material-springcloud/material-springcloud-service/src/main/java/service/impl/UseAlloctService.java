package com.material.website.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import com.material.website.args.StatisUseAlloctArgs;
import com.material.website.args.UseAlloctAddArgs;
import com.material.website.args.UseAlloctQueryArgs;
import com.material.website.dao.IDepartmentCenterDao;
import com.material.website.dao.IGoodsDao;
import com.material.website.dao.IStockDao;
import com.material.website.dao.IStorageDao;
import com.material.website.dao.ISupplierDao;
import com.material.website.dao.IUseAlloctDao;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.StatisUseAlloctDto;
import com.material.website.dto.UseAlloctDto;
import com.material.website.entity.DepartStock;
import com.material.website.entity.Goods;
import com.material.website.entity.Stock;
import com.material.website.entity.Supplier;
import com.material.website.entity.UseAlloct;
import com.material.website.entity.UseAlloctDetail;
import com.material.website.service.IUseAlloctService;
import com.material.website.system.MaterialOperate;
import com.material.website.system.Pager;
import com.material.website.util.BigDecimaUtil;
import com.material.website.util.MaterialNoUtil;

/**
 * 领用/调拨 业务实现类
 * 
 * @author sunxiaorong
 * 
 */
@Service
public class UseAlloctService implements IUseAlloctService {

	@Inject
	private IUseAlloctDao useAlloctDao;
	@Inject
	private IGoodsDao goodsDao;
	@Inject
	private IStockDao stockDao;
	@Inject
	private IDepartmentCenterDao departmentCenterDao;
	@Inject
	private IStorageDao storageDao;
	@Inject
	private ISupplierDao supplierDao;

	@Override
	public Pager<UseAlloctDto> queryDepartUsePager(UseAlloctQueryArgs queryArgs) {
		return useAlloctDao.queryDepartUsePager(queryArgs);
	}

	/**
	 * 添加 领用/调拨 物资单
	 */
	@Override
	public Map<String, Object> addUseAlloct(UseAlloctAddArgs addArgs) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 1.生成入库单数据
			UseAlloct useAlloct = useAlloctDao.queryByOperatNo(
					addArgs.getOperatNo(), addArgs.getType());
			if (useAlloct != null) {
				Integer count = useAlloctDao.queryUseAlloct(addArgs.getType());
				if (addArgs.getType() == 1) {
					addArgs.setOperatNo(MaterialNoUtil.getNo(
							MaterialOperate.LINGYONG.getName(), count + 1));
				} else {
					addArgs.setOperatNo(MaterialNoUtil.getNo(
							MaterialOperate.DIAOBO.getName(), count + 1));
				}
			} else {
				useAlloct = new UseAlloct();
			}
			useAlloct.setOperatNo(addArgs.getOperatNo());
			useAlloct.setDepartmentId(addArgs.getDepartmentId());
			useAlloct.setSumMoney(addArgs.getSumMoney());
			useAlloct.setRemarks(addArgs.getRemarks());
			useAlloct.setType(addArgs.getType());
			useAlloct.setUseAlloctDate(new Date());
			useAlloct.setUseName(addArgs.getUseName());
			useAlloct.setStatus(0);
			useAlloct = useAlloctDao.add(useAlloct);
			// 2.更新临时表中的信息
			Integer operatType = null;
			if(useAlloct.getType() == 1){
				operatType = 3;
			}else if (useAlloct.getType() == 2){
				operatType = 4;
			}else if(useAlloct.getType() == 3){
				operatType = 6;
			}
			storageDao.updateOperatTemp(useAlloct.getId(), operatType,
					useAlloct.getOperatNo());
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", 200);
		map.put("msg", "操作成功");
		return map;
	}

	@Override
	public Integer queryUseAlloct(Integer type) {
		return useAlloctDao.queryUseAlloct(type);
	}

	@Override
	public List<GoodsInstallDto> queryGoodsList(Integer useAlloctId) {
		List<GoodsInstallDto> list = useAlloctDao.queryGoodsList(useAlloctId);
		List<GoodsInstallDto> resultList = new ArrayList<GoodsInstallDto>();
		for (GoodsInstallDto dto : list) {
			String formatPrice = BigDecimaUtil.formatMoney(dto.getPrice());
			dto.setFormatPrice(formatPrice);
			String formatMoney = BigDecimaUtil
					.formatMoney(dto.getSingleMoney());
			dto.setFormatMoney(formatMoney);
			Supplier supplier = supplierDao.get(dto.getSupplierId());
			dto.setSupplierName(supplier.getShortName());
			resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public Pager<StatisUseAlloctDto> statisUseAlloctPager(
			StatisUseAlloctArgs queryArgs) {
		return useAlloctDao.statisUseAlloctPager(queryArgs);
	}

	@Override
	public Map<String, Object> updateUseAlloctInit(Integer useAlloctId) {
		Map<String, Object> map = new HashMap<String, Object>();
		UseAlloct useAlloct = useAlloctDao.get(useAlloctId);
		List<GoodsInstallDto> resultList = goodsDao.queryAllTemp(null,
				useAlloct.getOperatNo(), useAlloctId);
		map.put("useAlloct", useAlloct);
		map.put("resultList", resultList);
		return map;
	}

	@Override
	public boolean updateUseAlloct(UseAlloctAddArgs updateArgs) {
		try {
			UseAlloct useAlloct = useAlloctDao.get(updateArgs.getId());
			BeanUtils.copyProperties(updateArgs, useAlloct);
			useAlloctDao.updateEntity(useAlloct);
			// 2.更新临时表中的信息
			Integer operatType = null;
			if(useAlloct.getType() == 1){
				operatType = 3;
			}else if (useAlloct.getType() == 2){
				operatType = 4;
			}else if(useAlloct.getType() == 3){
				operatType = 6;
			}
			storageDao.updateOperatTemp(updateArgs.getId(),operatType, updateArgs.getOperatNo());
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public UseAlloct queryUseAlloctNo(String operatNo) {
		return useAlloctDao.queryUseAlloctNo(operatNo);
	}

	@Override
	public boolean addLockUseAlloct(Integer useAlloctId) {
		try {
			UseAlloct useAlloct = useAlloctDao.get(useAlloctId);
			// 添加入库商品
			List<GoodsInstallDto> goodsList = goodsDao.queryAllTemp(null, "",
					useAlloct.getId());
			for (GoodsInstallDto dto : goodsList) {
				Integer goodsId = dto.getGoodsId();
				Double goodsPrice = dto.getPrice();
				Double goodsNum = dto.getGoodsNum();
				Stock stock = stockDao.queryStockInfo(goodsId, goodsPrice,
						useAlloct.getType());
				if (stock != null) {
					if (stock.getStockNum() >= goodsNum) {
						// 大库存减去领用-调拨数量
						stock.setStockNum(stock.getStockNum() - goodsNum);
						stockDao.updateEntity(stock);
						// 对应部门库存添加/领用/调拨数量
						// 如果商品类型为固定资产则直接添加
						DepartStock departStock = departmentCenterDao
								.queryDepartStockList(
										useAlloct.getDepartmentId(), goodsId,
										goodsPrice, useAlloct.getType());
						Goods goods = goodsDao.get(goodsId);
						if (departStock != null
								&& departStock.getGoodsType() == 1) {
							departStock.setStockNum(goodsNum
									+ departStock.getStockNum());
							stockDao.updateEntity(departStock);
						} else {
							// 固定资产时添加状态以及备注
							if (goods.getGoodsType() == 2) {
								for (int i = 0; i < goodsNum; i++) {
									departStock = new DepartStock();
									departStock.setCategoryOne(goods
											.getCategoryOne());
									departStock.setCategoryTwo(goods
											.getCategoryTwo());
									departStock.setDepartmentId(useAlloct
											.getDepartmentId());
									departStock.setGoodsId(goodsId);
									departStock.setGoodsPrice(goodsPrice);
									departStock.setGoodsType(goods
											.getGoodsType());
									departStock.setStockType(useAlloct
											.getType());
									departStock.setStatus(1);
									departStock.setRemarks("该资产还未使用");
									departStock.setStockNum(1D);
									stockDao.addEntity(departStock);
								}
							} else {
								departStock = new DepartStock();
								departStock.setCategoryOne(goods
										.getCategoryOne());
								departStock.setCategoryTwo(goods
										.getCategoryTwo());
								departStock.setDepartmentId(useAlloct
										.getDepartmentId());
								departStock.setGoodsId(goodsId);
								departStock.setGoodsPrice(goodsPrice);
								departStock.setGoodsType(goods.getGoodsType());
								departStock.setRemarks(useAlloct.getRemarks());
								departStock.setStockType(useAlloct.getType());
								departStock.setStockNum(goodsNum);
								stockDao.addEntity(departStock);
							}
						}
						UseAlloctDetail detail = new UseAlloctDetail();
						detail.setUseAlloctId(useAlloct.getId());
						detail.setGoodsId(goods.getId());
						detail.setGoodsName(detail.getGoodsName());
						detail.setGoodsType(goods.getGoodsType());
						detail.setGoodsNum(goodsNum);
						detail.setPrice(goodsPrice);
						detail.setSingleMoney(goodsNum * goodsPrice);
						detail.setSpec(goods.getSpec());
						detail.setSupplierId(goods.getSupplierId());
						detail.setCategoryOne(goods.getCategoryOne());
						detail.setCategoryTwo(goods.getCategoryTwo());
						useAlloctDao.addEntity(detail);
					}
				}
			}
			 useAlloct.setStatus(1);
			 useAlloctDao.updateEntity(useAlloct);
			 goodsDao.delAllTemp(null, useAlloct.getOperatNo(),null);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
