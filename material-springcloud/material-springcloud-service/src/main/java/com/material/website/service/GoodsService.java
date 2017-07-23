package com.material.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.GoodsAPI;
import com.material.website.args.GoodsAddArgs;
import com.material.website.args.GoodsQueryArgs;
import com.material.website.dao.IDepartmentCenterDao;
import com.material.website.dao.IGoodsDao;
import com.material.website.dao.IOpeartTempDao;
import com.material.website.dao.IStorageDao;
import com.material.website.dao.ISupplierDao;
import com.material.website.dao.IUseAlloctDao;
import com.material.website.dto.GoodsDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.entity.Goods;
import com.material.website.entity.MaterialConsume;
import com.material.website.entity.OperatTemp;
import com.material.website.entity.Storage;
import com.material.website.entity.Supplier;
import com.material.website.entity.UseAlloct;
import com.material.website.system.Pager;
import com.material.website.util.BeanMapUtil;
import com.material.website.util.BigDecimaUtil;

/**
 * 商品业务实现类
 * 
 * @author sunxiaorong
 * 
 */
@RestController
@Transactional 
public class GoodsService implements GoodsAPI {

	@Autowired
	private IGoodsDao goodsDao;
	@Autowired
	private IOpeartTempDao tempDao;
	@Autowired
	private IStorageDao storageDao;
	@Autowired
	private IUseAlloctDao useAlloctDao;
	@Autowired
	private IDepartmentCenterDao departCenterDao;
	@Autowired
	private ISupplierDao supplierDao;

	@Override
	public Pager<GoodsDto> queryGoodsPager(Map<String, Object>map) {
		GoodsQueryArgs queryArgs = (GoodsQueryArgs) BeanMapUtil.convertMap(GoodsQueryArgs.class, map);
		return goodsDao.queryGoodsPager(queryArgs);
	}

	@Override
	public boolean addGoods(Map<String, Object>map) {
		try {
			GoodsAddArgs goodsAddArgs = (GoodsAddArgs) BeanMapUtil.convertMap(GoodsAddArgs.class, map);
			Goods goods = new Goods();
			BeanUtils.copyProperties(goodsAddArgs, goods);
			goods.setStatus(0);
			goods.setPrice(BigDecimaUtil.formatDouble(goods.getPrice()));
			goodsDao.addEntity(goods);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateGoods(Map<String, Object>map) {
		try {
			GoodsAddArgs goodsArgs = (GoodsAddArgs) BeanMapUtil.convertMap(GoodsAddArgs.class, map);
			Goods goods = new Goods();
			BeanUtils.copyProperties(goodsArgs, goods);
			goods.setStatus(0);
			goods.setPrice(BigDecimaUtil.formatDouble(goods.getPrice()));
			goodsDao.updateEntity(goods);
			return true;
		} catch (BeansException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Goods loadGoods(Integer goodsId) {
		Goods goods = goodsDao.get(goodsId);
		return goods;
	}

	@Override
	public boolean delGoods(Integer goodsId) {
		try {
			goodsDao.delGoods(goodsId);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<GoodsDto> queryAllGoods(Map<String, Object>map) {
		GoodsQueryArgs queryArgs = (GoodsQueryArgs) BeanMapUtil.convertMap(GoodsQueryArgs.class, map);
		return goodsDao.queryAllGoods(queryArgs);
	}

	@Override
	public List<GoodsInstallDto> queryAllTemp(Integer supplierId, String operatNo) {
		List<GoodsInstallDto> goodsList = goodsDao.queryAllTemp(supplierId, operatNo,null);
		List<GoodsInstallDto>resultList = new ArrayList<GoodsInstallDto>();
		for(GoodsInstallDto dto:goodsList){
			Supplier supplier = supplierDao.get(dto.getSupplierId());
			dto.setSupplierName(supplier.getShortName());
			resultList.add(dto);
		}
		return resultList;
	}

	@Override
	public boolean addOperatTemp(Map<String, Object>map) {
		OperatTemp operatTemp = (OperatTemp) BeanMapUtil.convertMap(OperatTemp.class, map);
		goodsDao.addEntity(operatTemp);
		return true;
	}

	@Override
	public boolean delOperaTemp(Integer id) {
		try {
			OperatTemp temp = tempDao.get(id);
			if(temp.getOperatId() != null && temp.getOperatType() != null){
				Double totalMoney =  0.0;
				//1 2 验收 预存  3 4 调拨  领用    5部门出库   6部门调拨
				if(temp.getOperatType() == 1 || temp.getOperatType() == 2){
					//更新总金额
					Storage storage = storageDao.get(temp.getOperatId());
					totalMoney = storage.getStorageMoney()-temp.getGoodsNum()*temp.getPrice();
					storage.setStorageMoney(totalMoney);
					storageDao.updateEntity(storage);
				}else if(temp.getOperatType() == 3 || temp.getOperatType() == 4){
					UseAlloct useAlloct = useAlloctDao.get(temp.getOperatId());
					totalMoney =  useAlloct.getSumMoney()-temp.getGoodsNum()*temp.getPrice();
					useAlloct.setSumMoney(totalMoney);
					useAlloctDao.updateEntity(useAlloct);
				}else if(temp.getOperatType() == 5 || temp.getOperatType() == 6){
					MaterialConsume consume =  departCenterDao.queryConsumeInfo("", temp.getOperatId());
					totalMoney = consume.getConsumeMoney() - temp.getGoodsNum()*temp.getPrice();
					consume.setConsumeMoney(totalMoney);
					departCenterDao.update(consume);
				}
			}
			goodsDao.deleteEntity(temp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
	@Override
	public void delAllTemp(Integer suplierId, String operatNo){
		goodsDao.delAllTemp(suplierId, operatNo,null);
	}

	@Override
	public void updateTempGoodsNum(Map<String, Object>map) {
		OperatTemp temp = (OperatTemp) BeanMapUtil.convertMap(OperatTemp.class, map);
		 goodsDao.updateEntity(temp);
	}

	@Override
	public OperatTemp loadTemp(Integer goodsId, Integer supplierId,
			String operatNo,Double goodsPrice) {
		return tempDao.loadTemp(goodsId, supplierId, operatNo,goodsPrice);
	}

	@Override
	public List<Goods> queryGoodsByCategoryId(Integer categoryId) {
		return goodsDao.queryGoodsByCategoryId(categoryId);
	}

	@Override
	public boolean delTempData() {
		 try {
			goodsDao.delTempData();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
