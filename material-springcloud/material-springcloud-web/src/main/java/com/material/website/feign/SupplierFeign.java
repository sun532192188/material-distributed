package com.material.website.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.SupplierQueryArgs;
import com.material.website.dto.SupplierDto;
import com.material.website.entity.Supplier;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

/**
 * 供应商客户端申明
 * @author Sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface SupplierFeign {
	
	/**
	 * 分页查询供应商信息
	 * @param supplierArgs
	 * @return
	 */
	@RequestMapping(value="/querySupplierList",method=RequestMethod.POST)
	public Pager<SupplierDto> querySupplierList(@RequestBody SupplierQueryArgs supplierArgs);
	
	/**
	 * 添加供应商信息
	 * @param supplier
	 * @return
	 */
	public boolean addSupplier(Supplier supplier);
	
	/**
	 * 修改供应商信息
	 * @param supplier
	 * @return
	 */
	public void updateSupplier(Supplier supplier);
	
	/**
	 * 根据供应商编号查询供应商信息
	 * @param supplierId
	 * @return
	 */
	public Supplier querySupplierById(Integer supplierId);
	
	/**
	 * 查询所有的供应商
	 * @return
	 */
	public List<Supplier> queryAllSupplier();
	
}
