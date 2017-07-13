package com.material.website.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.StaticsStorageArgs;
import com.material.website.args.StorageAddArgs;
import com.material.website.args.StorageQueryArgs;
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
	@RequestMapping(value="/addStorage",method=RequestMethod.POST)
	public boolean addStorage(@RequestBody StorageAddArgs storageArgs);
	
	/**
	 * 查询入库信息
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryStoragePager",method=RequestMethod.POST)
	public Pager<StorageDto> queryStoragePager(@RequestBody StorageQueryArgs queryArgs);
	
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
	@RequestMapping(value="/staticsStoragePager",method=RequestMethod.POST)
	public Pager<StaticsStorageDto> staticsStoragePager(@RequestBody StaticsStorageArgs queryArgs);
	
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
	@RequestMapping(value="/updateStorageInfo")
	public boolean updateStorageInfo(@RequestBody StorageAddArgs updsateArgs);
	
	/**
	 * 根据物资编号查询入库信息
	 * @param operatNo
	 * @return
	 */
	@RequestLine("GET /queryStorageByNo/{operatNo}")
	public Storage queryStorageByNo(@Param("operatNo") String operatNo);
}
