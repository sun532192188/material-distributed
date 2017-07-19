package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.StaticsStorageDto;
import com.material.website.dto.StorageDto;
import com.material.website.entity.Storage;
import com.material.website.system.Pager;

/**
 * 入库客户端申明
 * @author Sunxiaorong
 *
 */
@RequestMapping("/StorageAPI")
public interface StorageAPI {

	/**
	 * 根据类型、时间获取入库数量
	 * @RequestParam type
	 * @return 
	 */
	@RequestMapping(value="/getStorageCount",method=RequestMethod.GET)
	public Integer getStorageCount(@RequestParam("type") Integer type);
	
	/**
	 * 添加入库
	 * @RequestParam storageArgs
	 * @return
	 */
	@RequestMapping(value="/addStorage",method=RequestMethod.GET)
	public boolean addStorage(@RequestParam Map<String, Object>map);
	
	/**
	 * 查询入库信息
	 * @RequestParam queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryStoragePager",method=RequestMethod.GET)
	public Pager<StorageDto> queryStoragePager(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据单号编号 查询商品信息
	 * @RequestParam id
	 * @return
	 */
	@RequestMapping(value="/queryGoodsById",method=RequestMethod.GET)
	public List<GoodsInstallDto> queryGoodsById(@RequestParam("id") Integer id);
	
	/**
	 * 统计
	 * @RequestParam queryArgs
	 * @return
	 */
	@RequestMapping(value="/staticsStoragePager",method=RequestMethod.GET)
	public Pager<StaticsStorageDto> staticsStoragePager(@RequestParam Map<String, Object>map);
	
	@RequestMapping(value="/queryStorageById",method=RequestMethod.GET)
	public Storage queryStorageById(@RequestParam("storageId") Integer storageId);
	
	/**
	 * 入账 入库信息
	 * @return
	 */
	@RequestMapping(value="/addLockStorage",method=RequestMethod.GET)
	public boolean addLockStorage(@RequestParam("storageId") Integer storageId);
	
	/**
	 * 入库信息更新初始化
	 * @RequestParam storageId
	 * @return
	 */
	@RequestMapping(value="/updateStorageInit",method=RequestMethod.GET)
	public Map<String, Object>updateStorageInit(@RequestParam("storageId") Integer storageId);
	
	/**
	 * 入库信息更新
	 * @RequestParam updsateArgs
	 * @return
	 */
	@RequestMapping(value="/updateStorageInfo",method=RequestMethod.GET)
	public boolean updateStorageInfo(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据物资编号查询入库信息
	 * @RequestParam operatNo
	 * @return
	 */
	@RequestMapping(value="/queryStorageByNo",method=RequestMethod.GET)
	public Storage queryStorageByNo(@RequestParam("operatNo") String operatNo);
}
