package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.SupplierDto;
import com.material.website.entity.Supplier;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

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
	@RequestLine("GET /querySupplierList")
	public Pager<SupplierDto> querySupplierList(@RequestParam Map<String, Object>map);
	
	/**
	 * 添加供应商信息
	 * @param supplier
	 * @return
	 */
	@RequestLine("GET /addSupplier")
	public boolean addSupplier(@RequestParam Map<String, Object>map);
	
	/**
	 * 修改供应商信息
	 * @param supplier
	 * @return
	 */
	@RequestLine("GET /updateSupplier")
	public void updateSupplier(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据供应商编号查询供应商信息
	 * @param supplierId
	 * @return
	 */
	@RequestLine("GET /querySupplierById/{supplierId}")
	public Supplier querySupplierById(@Param("supplierId") Integer supplierId);
	
	/**
	 * 查询所有的供应商
	 * @return
	 */
	@RequestLine("GET /queryAllSupplier")
	public List<Supplier> queryAllSupplier();
	
}
