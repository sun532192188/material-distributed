package com.material.website.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.SupplierAPI;
import com.material.website.args.SupplierQueryArgs;
import com.material.website.dao.ISupplierDao;
import com.material.website.dto.SupplierDto;
import com.material.website.entity.Supplier;
import com.material.website.system.Pager;
import com.material.website.util.BeanMapUtil;

/**
 * 供应商业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class SupplierService implements SupplierAPI {

	@Autowired
	private ISupplierDao supplierDao;
	
	@Override
	public Pager<SupplierDto> querySupplierList(Map<String, Object>map) {
		SupplierQueryArgs supplierArgs = (SupplierQueryArgs) BeanMapUtil.convertMap(SupplierQueryArgs.class, map);
		return supplierDao.querySupplierList(supplierArgs);
	}

	@Override
	public boolean addSupplier(Map<String, Object>map) {
		try {
			Supplier supplier = (Supplier) BeanMapUtil.convertMap(Supplier.class, map);
			supplierDao.addEntity(supplier);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public void updateSupplier(Map<String, Object>map) {
		Supplier supplier = (Supplier) BeanMapUtil.convertMap(Supplier.class, map);
		supplierDao.updateEntity(supplier);
	}

	@Override
	public Supplier querySupplierById(Integer supplierId) {
		return supplierDao.get(supplierId);
	}

	@Override
	public List<Supplier> queryAllSupplier() {
		return supplierDao.queryAllSupplier();
	}
}
