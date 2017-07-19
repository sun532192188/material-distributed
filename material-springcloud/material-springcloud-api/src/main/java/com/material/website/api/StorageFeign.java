package com.material.website.api;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestParam;

import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.StaticsStorageDto;
import com.material.website.dto.StorageDto;
import com.material.website.entity.Storage;
import com.material.website.feign.config.FeignConfiguration;
import com.material.website.system.Pager;

import feign.Param;
import feign.RequestLine;

/**
 * 入库客户端申明
 * @author Sunxiaorong
 *
 */
@FeignClient(name="material-springcloud-service",configuration=FeignConfiguration.class)
public interface StorageFeign {

	/**
	 * 根据类型、时间获取入库数量
	 * @param type
	 * @return
	 */
	@RequestLine("GET /getStorageCount/{type}")
	public Integer getStorageCount(@Param("type") Integer type);
	
	/**
	 * 添加入库
	 * @param storageArgs
	 * @return
	 */
	@RequestLine("GET /addStorage}")
	public boolean addStorage(@RequestParam Map<String, Object>map);
	
	/**
	 * 查询入库信息
	 * @param queryArgs
	 * @return
	 */
	@RequestLine("GET /queryStoragePager}")
	public Pager<StorageDto> queryStoragePager(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据单号编号 查询商品信息
	 * @param id
	 * @return
	 */
	@RequestLine("GET /queryGoodsById/{id}")
	public List<GoodsInstallDto> queryGoodsById(@Param("id") Integer id);
	
	/**
	 * 统计
	 * @param queryArgs
	 * @return
	 */
	@RequestLine("GET /staticsStoragePager}")
	public Pager<StaticsStorageDto> staticsStoragePager(@RequestParam Map<String, Object>map);
	
	@RequestLine("GET /queryStorageById/{storageId}")
	public Storage queryStorageById(@Param("storageId") Integer storageId);
	
	/**
	 * 入账 入库信息
	 * @return
	 */
	@RequestLine("GET /addLockStorage/{storageId}")
	public boolean addLockStorage(@Param("storageId") Integer storageId);
	
	/**
	 * 入库信息更新初始化
	 * @param storageId
	 * @return
	 */
	@RequestLine("GET /updateStorageInit/{storageId}")
	public Map<String, Object>updateStorageInit(@Param("storageId") Integer storageId);
	
	/**
	 * 入库信息更新
	 * @param updsateArgs
	 * @return
	 */
	@RequestLine("GET /updateStorageInfo")
	public boolean updateStorageInfo(@RequestParam Map<String, Object>map);
	
	/**
	 * 根据物资编号查询入库信息
	 * @param operatNo
	 * @return
	 */
	@RequestLine("GET /queryStorageByNo/{operatNo}")
	public Storage queryStorageByNo(@Param("operatNo") String operatNo);
}
