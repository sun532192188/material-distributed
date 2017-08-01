package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.SupplierDto;
import com.material.website.entity.Supplier;
import com.material.website.system.Pager;

/**
 * 供应商客户端申明
 * @author Sunxiaorong
 *
 */
@RequestMapping("/SupplierAPI")
public interface SupplierAPI {
	
	/**
	 * 分页查询供应商信息
	 * @RequestParam supplierArgs
	 * @return
	 */
	@RequestMapping(value="/querySupplierList",method=RequestMethod.GET)
	public Pager<SupplierDto> querySupplierList(@RequestParam Map<String, Object>map);
	
	/**
	 * 添加供应商信息
	 * @RequestParam supplier
	 * @return
	 */
	@RequestMapping(value="/addSupplier",method=RequestMethod.POST)
	public boolean addSupplier(@RequestParam("json") String json);
	
	/**
	 * 修改供应商信息
	 * @RequestParam supplier
	 * @return
	 */
	@RequestMapping(value="/updateSupplier",method=RequestMethod.POST)
	public void updateSupplier(@RequestParam("json") String json);
	
	/**
	 * 根据供应商编号查询供应商信息
	 * @RequestParam supplierId
	 * @return
	 */
	@RequestMapping(value="/querySupplierById",method=RequestMethod.GET)
	public Supplier querySupplierById(@RequestParam("supplierId") Integer supplierId);
	
	/**
	 * 查询所有的供应商
	 * @return
	 */
	@RequestMapping(value="/queryAllSupplier",method=RequestMethod.GET)
	public List<Supplier> queryAllSupplier();
	
}
