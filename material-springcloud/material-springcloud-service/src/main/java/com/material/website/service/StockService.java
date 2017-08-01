package com.material.website.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.material.website.api.StockAPI;
import com.material.website.args.StockArgs;
import com.material.website.dao.IGoodsDao;
import com.material.website.dao.IStockDao;
import com.material.website.dto.StockDto;
import com.material.website.system.Pager;
import com.material.website.systemcontext.ConverMapToSystemContext;
import com.material.website.util.BeanMapUtil;

/**
 * 库存业务实现类
 * @author sunxiaorong
 *
 */
@RestController
@Transactional 
public class StockService implements StockAPI {
	
	@Autowired
	private IStockDao stockDao;
	@Autowired
	private IGoodsDao goodsDao;

	@Override
	public Pager<StockDto> queryStockPager(Map<String, Object>map) {
		ConverMapToSystemContext.convertSystemContext(map);
		StockArgs stockArgs = (StockArgs) BeanMapUtil.convertMap(StockArgs.class, map);
		return stockDao.queryStockPager(stockArgs);
	}

	@Override
	public List<StockDto> queryStockList(Map<String, Object>map) {
		StockArgs stockArgs = (StockArgs) BeanMapUtil.convertMap(StockArgs.class, map);
		List<StockDto> list = stockDao.queryStockList(stockArgs);
		List<StockDto>stockList = new ArrayList<StockDto>();
		Double temNum = null;
		for(StockDto dto :list){
			Double price = Double.valueOf(dto.getGoodsPrice().replaceAll(",",""));
			if(dto.getStockType() == 1){
				temNum = goodsDao.queryTemp(dto.getGoodsId(), "alloct", price);
			}else if(dto.getStockType() == 2){
				temNum = goodsDao.queryTemp(dto.getGoodsId(), "use", price);
			}
			Double stockNum = dto.getStockNum()-temNum;
			if(stockNum > 0){
				dto.setStockNum(dto.getStockNum()-temNum);
				stockList.add(dto);
			}
		}
		return stockList;
	}

}
