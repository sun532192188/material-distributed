package com.material.website.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.material.website.args.GoodsQueryArgs;
import com.material.website.args.StaticsDepartPlanArgs;
import com.material.website.args.StaticsStorageArgs;
import com.material.website.args.StatisUseAlloctArgs;
import com.material.website.args.StockArgs;
import com.material.website.args.StorageQueryArgs;
import com.material.website.args.SupplierQueryArgs;
import com.material.website.dto.GoodsDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.MonthPlanGoodsDto;
import com.material.website.dto.StaticsDepartPlanDto;
import com.material.website.dto.StaticsStorageDto;
import com.material.website.dto.StatisUseAlloctDto;
import com.material.website.dto.StockDto;
import com.material.website.dto.SupplierDto;
import com.material.website.entity.Category;
import com.material.website.entity.Department;
import com.material.website.entity.MaterialConsume;
import com.material.website.entity.Storage;
import com.material.website.entity.Supplier;
import com.material.website.entity.UseAlloct;
import com.material.website.service.ICategorySercice;
import com.material.website.service.IDepartmentCenterService;
import com.material.website.service.IDepartmentService;
import com.material.website.service.IGoodsService;
import com.material.website.service.IStockService;
import com.material.website.service.IStorageService;
import com.material.website.service.ISupplierService;
import com.material.website.service.IUseAlloctService;
import com.material.website.system.Pager;
import com.material.website.util.BigDecimaUtil;
import com.material.website.util.ExcelUtil;
import com.material.website.util.JsonUtil;
import com.material.website.util.NumberToCN;
import com.material.website.web.MySqlImportAndExport;

/**
 * 数据打印、导出控制类
 * 
 * @author sunxiaorong
 * 
 */
@Controller
@RequestMapping(value = "dataOperatController")
public class DataOperatController {

	@Inject
	private IDepartmentCenterService departCenterService;
	@Inject
	private IDepartmentService departmentService;
	@Inject
	private IStorageService storageService;
	@Inject
	private ISupplierService supplierService;
	@Inject
	private IStockService stockService;
	@Inject
	private ICategorySercice categoryService;
	@Inject
	private IUseAlloctService useAlloctService;
	@Inject
	private IGoodsService goodsService;
	@Inject
	private IDepartmentService departService;
	
	
	/**
	 * 库存统计数据操作(导出 &打印)
	 * @param queryArgs
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/statisStockOperat")
	public String statisStockOperat(StockArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(queryArgs.getGoodsName())) {
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName()
					.getBytes("ISO-8859-1"), "UTF-8"));
		}
		Pager<StockDto> pages = stockService.queryStockPager(queryArgs);
		List<StockDto> resultList = pages.getRows();
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 50;
		}else{
			Integer pageNum = resultList.size()%50==0?resultList.size()/50:(resultList.size()/50)+1;
			buchongNum = 50*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
			StockDto dto = new StockDto();
      		resultList.add(dto);
      	}
		List<StockDto>stockList = new ArrayList<StockDto>();
	    Double sumMoney = 0.0;
    	for(StockDto dto:resultList){
    		if(dto.getGoodsPrice() != null && dto.getStockNum() != null){
    			String priceStr = dto.getGoodsPrice().replace(",","");
    			Double goodsPrice = Double.valueOf(priceStr);
    			String formatSingleMoney = BigDecimaUtil.formatMoney(goodsPrice * dto.getStockNum());
    			dto.setFormatSingleMoney(formatSingleMoney);
    			sumMoney += goodsPrice * dto.getStockNum();
    		}
    		stockList.add(dto);
    	}
    	String formatMoney = BigDecimaUtil.formatMoney(sumMoney);
		if (queryArgs.getOperatType() == 1) {
            String nowTime = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());
        	model.addAttribute("nowTime",nowTime);
        	if(queryArgs.getCategoryOne() != null){
        		Category category = categoryService.loadCategory(queryArgs.getCategoryOne());
        		model.addAttribute("categoryOneName",category.getCategoryName());
        	}else{
        		model.addAttribute("categoryOneName","全部");
        	}
        	if(queryArgs.getCategoryTwo() != null){
        		Category category = categoryService.loadCategory(queryArgs.getCategoryTwo());
        		model.addAttribute("categoryTwoName",category.getCategoryName());
        	}else{
        		model.addAttribute("categoryTwoName","全部");
        	}
        	if(queryArgs.getType() != null){
        		if(queryArgs.getType() == 1){
        			model.addAttribute("stockType","预存");
        		}else{
        			model.addAttribute("stockType","调拨");
        		}
        	}else{
        		model.addAttribute("stockType","全部");
        	}
        	model.addAttribute("sumMoney",formatMoney);
        	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(stockList));
            return "admin/print/stock_statis";
		} else {
			String title = "物资库存统计";//"";
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("总价");
			titleList.add("单位");
			titleList.add("商品类型");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (StockDto dto : stockList) {
				if(dto.getGoodsId() ==null || dto.getStockNum() == null ){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getGoodsPrice());
				list.add(dto.getStockNum());
				list.add(dto.getFormatSingleMoney());
				list.add(dto.getSpec());
				if(dto.getGoodsType() != null){
					if (dto.getGoodsType() == 1) {
						list.add("物资");
					} else {
						list.add("固定资产");
					}
				}
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName  = title+nowTime;
			exportPath = ExcelUtil.export(title,fileName,titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}

	/**
	 * 部门月计划统计 数据打印&导出
	 * 
	 * @param queryArgs
	 * @return
	 * @throws IOException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value = "/dpeartPlanOperat",method={RequestMethod.POST,RequestMethod.GET})
	public String dpeartPlanOperat(
			StaticsDepartPlanArgs queryArgs,Model model, HttpServletRequest request,HttpServletResponse response)
			throws IOException {
		if (StringUtils.isNotEmpty(queryArgs.getGoodsName())) {
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName()
					.getBytes("ISO-8859-1"), "UTF-8"));
		}
		Pager<StaticsDepartPlanDto> pages = departCenterService
				.staticsDepartPlan(queryArgs);
		List<StaticsDepartPlanDto> resultList = pages.getRows();
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 25;
		}else{
			Integer pageNum = resultList.size()%25==0?resultList.size()/25:(resultList.size()/25)+1;
			buchongNum = 25*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
      		StaticsDepartPlanDto dto = new StaticsDepartPlanDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOperatType() == 1) {
			String applyTime = new SimpleDateFormat("MM月").format(new Date());
			if(StringUtils.isNotEmpty(queryArgs.getMonthDate())){
				applyTime = queryArgs.getMonthDate().substring(queryArgs.getMonthDate().indexOf("-")+1)+"月";
			}
			model.addAttribute("applyTime",applyTime);
            String nowTime = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());
        	model.addAttribute("nowTime",nowTime);
        	if(queryArgs.getDepartId() != null){
        		Department department = departmentService.loadDepartment(queryArgs.getDepartId());
        		model.addAttribute("departmentName",department.getDepartmentName());
        		model.addAttribute("remarks",resultList.get(0).getRemarks());
        	}else{
        		model.addAttribute("departmentName","所有部门");
        		model.addAttribute("remarks","机关各部(处)日常办公用品另列计划。");
        	}
        	
        	Double sumMoney = 0.0;
        	for(StaticsDepartPlanDto dto:resultList){
        		if(dto.getSinglePrice() != null){
        			sumMoney += dto.getSinglePrice();
        		}
        	}
        	sumMoney = BigDecimaUtil.formatDouble(sumMoney);
        	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("sumMoney",BigDecimaUtil.formatMoney(sumMoney));
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
            return "admin/print/departplan_statics_plan";
		} else {
			String title = "月计划统计";//"";
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("编码");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("总价");
			titleList.add("商品类型");
			titleList.add("部门");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (StaticsDepartPlanDto dto : resultList) {
				if(dto.getDepartmentName() ==null || dto.getGoodsId() == null ){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getSinglePrice());
				if(dto.getGoodsType() != null){
					if (dto.getGoodsType() == 1) {
						list.add("物资");
					} else {
						list.add("固定资产");
					}
				}
				list.add(dto.getDepartmentName());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName  = title+nowTime;
			exportPath = ExcelUtil.export(title,fileName,titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * (大库存)库存入库统计 数据操作
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/storageOperat")
	public String storageOperat(StaticsStorageArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(queryArgs.getGoodsName())) {
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName()
					.getBytes("ISO-8859-1"), "UTF-8"));
		}
		Pager<StaticsStorageDto> pages = storageService.staticsStoragePager(queryArgs);
		List<StaticsStorageDto> resultList = pages.getRows();
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 50;
		}else{
			Integer pageNum = resultList.size()%50==0?resultList.size()/50:(resultList.size()/50)+1;
			buchongNum = 50*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
			StaticsStorageDto dto = new StaticsStorageDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOperatType() == 1) {
            String nowTime = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());
        	model.addAttribute("nowTime",nowTime);
        	String statisTime = "";
        	if(StringUtils.isNotEmpty(queryArgs.getEndDate())){
        		if(StringUtils.isNotEmpty(queryArgs.getStartDate())){
        			statisTime = queryArgs.getStartDate().replaceAll("-",".")+"-"+queryArgs.getEndDate().replaceAll("-",".");
        		}else{
        			statisTime = queryArgs.getEndDate();
        		}
        	}
        	model.addAttribute("statisTime",statisTime);
        	if(queryArgs.getSupplierId() != null){
        		Supplier supplier = supplierService.querySupplierById(queryArgs.getSupplierId());
        		model.addAttribute("supplierName",supplier.getShortName());
        	}else{
        		model.addAttribute("supplierName","所有供应商");
        	}
        	
        	Double sumMoney = 0.0;
        	for(StaticsStorageDto dto:resultList){
        		if(dto.getSingleMoney() != null){
        			sumMoney += dto.getSingleMoney();
        		}
        	}
        	sumMoney = BigDecimaUtil.formatDouble(sumMoney);
        	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("sumMoney",BigDecimaUtil.formatMoney(sumMoney));
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
            return "admin/print/stock_storage_statis";
		} else {
			String title = "库存入库统计";
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("金额");
			titleList.add("商品类型");
			titleList.add("供应商");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (StaticsStorageDto dto : resultList) {
				if(dto.getSupplierName() ==null || dto.getSupplierName() == null || dto.getGoodsId() ==null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getFormatPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getFormatMoney());
				
				if(dto.getGoodsType() != null){
					if (dto.getGoodsType() == 1) {
						list.add("物资");
					} else {
						list.add("固定资产");
					}
				}
				list.add(dto.getSupplierName());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 库存出库统计 数据操作(库存出库/部门入库)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/useAlloctOpeart")
	public String useAlloctOpeart(StatisUseAlloctArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(queryArgs.getGoodsName())) {
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName()
					.getBytes("ISO-8859-1"), "UTF-8"));
		}
		if (StringUtils.isNotEmpty(queryArgs.getTitle())) {
			queryArgs.setTitle(new String(queryArgs.getTitle()
					.getBytes("ISO-8859-1"), "UTF-8"));
		}
		Pager<StatisUseAlloctDto> pages = useAlloctService.statisUseAlloctPager(queryArgs);
		List<StatisUseAlloctDto> resultList = pages.getRows();
		Double sumMoney = 0.0;
    	for(StatisUseAlloctDto dto:resultList){
    		Double singleMoney = Double.valueOf(dto.getFormatMoney().replaceAll(",", ""));
    		if(singleMoney != null){
    			sumMoney += singleMoney;
    		}
    	}
    	String formatSumMoney = BigDecimaUtil.formatMoney(sumMoney);
    	model.addAttribute("sumMoney",formatSumMoney);
    	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
    	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
    	model.addAttribute("CN",CN);
    	Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 50;
		}else{
			Integer pageNum = resultList.size()%50==0?resultList.size()/50:(resultList.size()/50)+1;
			buchongNum = 50*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
      		StatisUseAlloctDto dto = new StatisUseAlloctDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOpeartType() == 1) {
            String nowTime = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());
        	model.addAttribute("nowTime",nowTime);
        	String statisTime = "";
        	if(StringUtils.isNotEmpty(queryArgs.getEndDate())){
        		if(StringUtils.isNotEmpty(queryArgs.getStartDate())){
        			statisTime = queryArgs.getStartDate().replaceAll("-",".")+"-"+queryArgs.getEndDate().replaceAll("-",".");
        		}else{
        			statisTime = queryArgs.getEndDate().replaceAll("-",".");
        		}
        	}
        	model.addAttribute("statisTime",statisTime);
        	if(queryArgs.getDepartId() != null){
        		Department department = departmentService.loadDepartment(queryArgs.getDepartId());
        		model.addAttribute("departmentName",department.getDepartmentName());
        	}else{
        		model.addAttribute("departmentName","所有部门");
        	}
        	model.addAttribute("title",queryArgs.getTitle());
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
            return "admin/print/stock_useAlloct_statis";
		} else {
			String title = "";
			if(StringUtils.isNotEmpty(queryArgs.getTitle())){
				 title = queryArgs.getTitle();
			}else{
				 title = "库存物资出库统计";
			}
			
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("总价");
			titleList.add("数量");
			titleList.add("商品类型");
			titleList.add("部门名称");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (StatisUseAlloctDto dto : resultList) {
				if(dto.getGoodsId() == null || dto.getGoodsNum() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getFormatPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getFormatMoney());
				if(dto.getGoodsType() != null){
					if (dto.getGoodsType() == 1) {
						list.add("物资");
					} else {
						list.add("固定资产");
					}
				}
				list.add(dto.getDepartmentName());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 物资验收  数据操作
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/yanshouAndYucunOperat")
	public String yanshouAndYucunOperat(StorageQueryArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if(StringUtils.isNotEmpty(queryArgs.getTitle())){
			queryArgs.setTitle(new String(queryArgs.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
		    model.addAttribute("title",queryArgs.getTitle());
		}
		Storage storage = storageService.queryStorageById(queryArgs.getStorageId());
		List<GoodsInstallDto> resultList = null;
		if(storage.getStatus() == 0){
			resultList = goodsService.queryAllTemp(null, storage.getStorageNo());
		}else{
			resultList = storageService.queryGoodsById(queryArgs.getStorageId());
		}
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 10;
		}else{
			Integer pageNum = resultList.size()%10==0?resultList.size()/10:(resultList.size()/10)+1;
			buchongNum = 10*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
      		GoodsInstallDto dto = new GoodsInstallDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOperatType() == 1) {
            String operatTime =  operatTime = new SimpleDateFormat("YYYY年MM月dd日").format(storage.getStorageDate());
        	model.addAttribute("operatTime",operatTime);
        	if(queryArgs.getSupplierId() != null){
        		Supplier supplier = supplierService.querySupplierById(queryArgs.getSupplierId());
        		model.addAttribute("supplierName",supplier.getShortName());
        	}else{
        		model.addAttribute("supplierName","全部");
        	}
        	
        	Double sumMoney = storage.getStorageMoney();
        	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String formatMoney = BigDecimaUtil.formatMoney(sumMoney);
        	model.addAttribute("sumMoney",formatMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("operatNo",storage.getStorageNo());
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
        	if(storage.getStorageNo().startsWith("YS")){
        		model.addAttribute("type","验收");
        	}else{
        		model.addAttribute("type","预存");
        	}
            return "admin/print/storage_yanshou_yucun";
		} else {
			String title = "";
			if(StringUtils.isNotEmpty(queryArgs.getTitle())){
				 title = queryArgs.getTitle();
			}else{
				 title = "物资验收入库单";
			}
			
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("物资编码");
			titleList.add("物资名称");
			titleList.add("品牌型号");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("金额");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (GoodsInstallDto dto : resultList) {
				if(dto.getGoodsId() == null || dto.getGoodsNum() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
			    list.add(dto.getGoodsNo());
			    list.add(dto.getGoodsName());
			    list.add(dto.getSpecModel());
			    list.add(dto.getSpec());
			    list.add(dto.getFormatPrice());
			    list.add(dto.getGoodsNum());
			    list.add(dto.getFormatMoney());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 供应商  数据操作
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/supplierOperat")
	public String supplierOperat(SupplierQueryArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if(StringUtils.isNotEmpty(queryArgs.getSupplierName())){
			queryArgs.setSupplierName(new String(queryArgs.getSupplierName().getBytes("ISO-8859-1"),"UTF-8"));
		}
		if(StringUtils.isNotEmpty(queryArgs.getAddress())){
			queryArgs.setAddress(new String(queryArgs.getAddress().getBytes("ISO-8859-1"),"UTF-8"));
		}
		Pager<SupplierDto>pages = supplierService.querySupplierList(queryArgs);
		if(StringUtils.isEmpty(queryArgs.getSupplierName())){
			queryArgs.setSupplierName("全部");
		}
		if(StringUtils.isEmpty(queryArgs.getPhone())){
			queryArgs.setPhone("全部");
		}
		if(StringUtils.isEmpty(queryArgs.getAddress())){
			queryArgs.setAddress(new String(queryArgs.getAddress().getBytes("ISO-8859-1"),"UTF-8"));
			queryArgs.setAddress("全部");
		}
		List<SupplierDto>resultList = pages.getRows();
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 10;
		}else{
			Integer pageNum = resultList.size()%10==0?resultList.size()/10:(resultList.size()/10)+1;
			buchongNum = 10*pageNum - resultList.size();
		}
      	for(int i=0;i<buchongNum;i++){
      		SupplierDto dto = new SupplierDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOperatType() == 1) {
			model.addAttribute("supplierName",queryArgs.getSupplierName());
		    model.addAttribute("phone",queryArgs.getPhone());
		    model.addAttribute("address",queryArgs.getAddress());
        	model.addAttribute("queryArgs",queryArgs);
        	model.addAttribute("resultList",resultList);
            return "admin/print/supplier_print";
		} else {
			String title = "供应商信息";
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("简称");
			titleList.add("全称");
			titleList.add("联系人");
			titleList.add("联系电话");
			titleList.add("地址");
			titleList.add("开户行");
			titleList.add("持卡人姓名");
			titleList.add("开户行所在地");
			titleList.add("银行卡帐号");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (SupplierDto dto : resultList) {
				if(dto.getId() == null || StringUtils.isEmpty(dto.getShortName())){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
			    list.add(dto.getShortName());
			    list.add(dto.getFullName());
			    list.add(dto.getLinkman());
			    list.add(dto.getPhone());
			    list.add(dto.getAddress());
			    list.add(dto.getBank());
			    list.add(dto.getCardholder());
			    list.add(dto.getBankAddr());
			    list.add(dto.getBankCard());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 物资领用 /调拨 数据操作
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/useAlloctDataOperat")
	public String useAlloctDataOperat(Integer useAlloctId,String operatNo,String departName,Integer operatType,Integer returnType,String title,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(departName)) {
			departName = new String(departName.getBytes("ISO-8859-1"), "UTF-8");
		}
		if (StringUtils.isNotEmpty(title)) {
			title = new String(title.getBytes("ISO-8859-1"), "UTF-8");
		}
		UseAlloct useAlloct = useAlloctService.queryUseAlloctNo(operatNo);
		List<GoodsInstallDto> resultList = null;
		if(useAlloct.getStatus() == 0){
			resultList  = goodsService.queryAllTemp(null, operatNo);
		}else{
			resultList  =  useAlloctService.queryGoodsList(useAlloctId);
		}
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 10;
		}else{
			Integer pageNum = resultList.size()%10==0?resultList.size()/10:(resultList.size()/10)+1;
			buchongNum = 10*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
			GoodsInstallDto dto = new GoodsInstallDto();
       		resultList.add(dto);
      	}
		if (operatType == 1) {
            String nowTime = new SimpleDateFormat("YYYY年MM月dd日").format(new Date());
        	model.addAttribute("nowTime",nowTime);
        	Double sumMoney = 0.0;
        	for(GoodsInstallDto dto:resultList){
        		if(dto.getSingleMoney() != null){
        			sumMoney += dto.getSingleMoney();
        		}
        	}
        	sumMoney = BigDecimaUtil.formatDouble(sumMoney);
        	BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("sumMoney",BigDecimaUtil.formatMoney(sumMoney));
        	model.addAttribute("operatNo",operatNo);
        	model.addAttribute("departName",departName);
        	model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
        	model.addAttribute("title",title);
        	model.addAttribute("returnType",returnType);
            return "admin/print/stock_useAlloct";
		} else {
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("总价");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (GoodsInstallDto dto : resultList) {
				if(dto.getGoodsId() == null || dto.getGoodsNum() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getSingleMoney());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 商品导出与打印
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/goodsDataOperat")
	public String goodsDataOperat(GoodsQueryArgs queryArgs,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		if (StringUtils.isNotEmpty(queryArgs.getGoodsName())) {
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName().getBytes("ISO-8859-1"),"UTF-8"));
		}
		if (StringUtils.isNotEmpty(queryArgs.getTitle())) {
			queryArgs.setTitle(new String(queryArgs.getTitle().getBytes("ISO-8859-1"),"UTF-8"));
		}
		List<GoodsDto> resultList = goodsService.queryGoodsPager(queryArgs).getRows();
		Integer buchongNum = 0;
		if(resultList.size() <= 0){
			buchongNum = 50;
		}else{
			Integer pageNum = resultList.size()%50==0?resultList.size()/50:(resultList.size()/50)+1;
			buchongNum =  50*pageNum - resultList.size();
		}
		for(int i=0;i<buchongNum;i++){
      		GoodsDto dto = new GoodsDto();
      		resultList.add(dto);
      	}
		if (queryArgs.getOperatType() == 1) {
			model.addAttribute("goodsName",queryArgs.getGoodsName().equals("")?"全部":queryArgs.getGoodsName());
			String categoryOneName = "";
			if(queryArgs.getCategoryOne() != null){
				 categoryOneName = categoryService.loadCategory(queryArgs.getCategoryOne()).getCategoryName();
			}else{
				categoryOneName = "全部";
			}
			String categoryTwoName = "";
			model.addAttribute("categoryOneName",categoryOneName);
			if(queryArgs.getCategoryTwo() != null){
				categoryTwoName = categoryService.loadCategory(queryArgs.getCategoryTwo()).getCategoryName();
			}else{
				categoryTwoName = "全部";
			}
			model.addAttribute("categoryTwoName",categoryTwoName);
        	model.addAttribute("title",queryArgs.getTitle());
        	String json = JsonUtil.newInstance().obj2json(resultList);
        	System.out.println(json);
        	model.addAttribute("goodsList",json);
        	model.addAttribute("queryArgs",queryArgs);
            return "admin/print/goods_print";
		} else {
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			/*titleList.add("备注");*/
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (GoodsDto dto : resultList) {
				if(dto.getId() == null || dto.getGoodsName() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getPrice());
				/*list.add("");*/
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName = queryArgs.getTitle()+nowTime;
			exportPath = ExcelUtil.export(queryArgs.getTitle(), fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 部门出库/部门调拨 数据导出与打印
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/departCKOperat")
	public String departCKOperat(Integer ckId,String title,Integer operatType,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		MaterialConsume consume = departCenterService.queryConsumeInfo(null, ckId);
		String returnPage = "";
		if (StringUtils.isNotEmpty(title)) {
			title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
		}
		List<GoodsInstallDto> resultList = null;
		if(consume.getStatus() == 0){
			resultList = goodsService.queryAllTemp(null, consume.getOperatNo());
		}else{
			resultList = departCenterService.queryCkGoodsList(ckId);
		}
		
		Integer buchongNum = 0;
		Integer pageNum  = 0;
		Department depart = departService.loadDepartment(consume.getDepartId());
		model.addAttribute("departName",depart.getDepartmentName());
		if(resultList != null){
			if(resultList.size() <= 0){
				buchongNum = 10;
			}else{
				pageNum = resultList.size()%10==0?resultList.size()/10:(resultList.size()/10)+1;
			    buchongNum = 10*pageNum - resultList.size();
			}
			if(consume.getType() == null || consume.getType() == 1){
			    returnPage = "admin/print/depart_ck_print";
			}else if(consume.getType() == 2){
				Department targetDepart = departService.loadDepartment(consume.getTargetDepartId());
				model.addAttribute("targetDepartName",targetDepart.getDepartmentName());
				  returnPage = "admin/print/depart_db_print";
			}
			for(int i=0;i<buchongNum;i++){
          		GoodsInstallDto dto = new GoodsInstallDto();
          		resultList.add(dto);
          	}
			model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
		}
		
		if (operatType == 1) {
			//type 1:部门使用   2:部门调拨
			String time = new SimpleDateFormat("YYYY年MM月dd日").format(consume.getConsumeDate());
			model.addAttribute("time",time);
			model.addAttribute("operatNo",consume.getOperatNo());
			String sumMoney = BigDecimaUtil.formatMoney(consume.getConsumeMoney());
			model.addAttribute("sumMoney",sumMoney);
			sumMoney = sumMoney.replaceAll(",", "");
			BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
            return returnPage;
		} else {
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("金额");
			/*titleList.add("备注");*/
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (GoodsInstallDto dto : resultList) {
				if(dto.getId() == null || dto.getGoodsName() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getFormatPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getFormatMoney());
				/*list.add("");*/
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName =title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	
	/**
	 * 部门月计划数据操作(根据月计划编号查询)
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings("deprecation")
	@RequestMapping(value="/departMonthPlan")
	public String departMonthPlan(Integer planId,String departmentName,String sumMoney,String time,Integer operatType,String title,Model model,HttpServletRequest request,HttpServletResponse response) throws UnsupportedEncodingException{
		departmentName = new String(departmentName.getBytes("ISO-8859-1"),"UTF-8");
		title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
		List<MonthPlanGoodsDto> resultList = departCenterService.queryMonthPlanGoods(planId);
		Integer buchongNum = 0;
		Integer pageNum  = 0;
		if(resultList != null){
			if(resultList.size() <= 0){
				buchongNum = 35;
			}else{
				pageNum = resultList.size()%35==0?resultList.size()/35:(resultList.size()/35)+1;
				buchongNum =  35*pageNum - resultList.size();
			}
			for(int i=0;i<buchongNum;i++){
				MonthPlanGoodsDto dto = new MonthPlanGoodsDto();
          		resultList.add(dto);
          	}
			model.addAttribute("resultList",JsonUtil.newInstance().obj2json(resultList));
		}
		
		if (operatType == 1) {
			model.addAttribute("time",time);
			model.addAttribute("departmentName",departmentName);
			BigDecimal numberOfMoney = new BigDecimal(sumMoney);
        	String CN = NumberToCN.number2CNMontrayUnit(numberOfMoney);
        	model.addAttribute("CN",CN);
        	model.addAttribute("sumMoney",BigDecimaUtil.formatMoney(Double.valueOf(sumMoney)));
        	model.addAttribute("title",title);
            return "admin/print/departplan_print";
		} else {
			List<String> titleList = new ArrayList<String>();
			titleList.add("序号");
			titleList.add("商品编号");
			titleList.add("商品名称");
			titleList.add("规格");
			titleList.add("单位");
			titleList.add("单价");
			titleList.add("数量");
			titleList.add("金额");
			Integer i = 1;
			List<List<Object>> dataList = new ArrayList<List<Object>>();
			for (MonthPlanGoodsDto dto : resultList) {
				if(dto.getId() == null || dto.getGoodsName() == null){
					continue;
				}
				List<Object> list = new ArrayList<Object>();
				list.add(i);
				list.add(dto.getGoodsNo());
				list.add(dto.getGoodsName());
				list.add(dto.getSpecModel());
				list.add(dto.getSpec());
				list.add(dto.getFormatPrice());
				list.add(dto.getGoodsNum());
				list.add(dto.getFormatMoney());
				dataList.add(list);
				i++;
			}
			String exportPath = request.getRealPath("/exportData/");
			if(!new File(exportPath).exists()){
				new File(exportPath).mkdirs();
			}
			String nowTime = new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date());
			String fileName =title+nowTime;
			exportPath = ExcelUtil.export(title, fileName, titleList, dataList, exportPath);
			download(request,response,exportPath);
			new File(exportPath).delete();
			System.out.println("已将下载文件从服务器清除...");
		}
		return null;
	}
	
	/**
	 * 下载浏览器
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/downloadBrowser")
	public static void downloadBrowser(HttpServletRequest request,HttpServletResponse response){
		try {
			InputStream is = MySqlImportAndExport.class.getClassLoader()
					.getResourceAsStream("jdbc.properties");
			Properties properties = new Properties();
			properties.load(is);
			String downloadUrl = properties.getProperty("browser.path");
			download(request, response, downloadUrl);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 文件下载
	 * @param request
	 * @param response
	 * @param fileName
	 * @param filePath
	 */
	public static void download(HttpServletRequest request,HttpServletResponse response, String filePath) {
		InputStream in = null;
		OutputStream out = null;
		try {
			// 设置响应头
			response.setContentType("application/force-download");
			String filepaths = URLDecoder.decode(filePath, "UTF-8");
			// 获取文件
			String fileName  = filePath.substring(filePath.lastIndexOf("\\")+1,filePath.length());
			// 中文名称
			String agent = request.getHeader("User-Agent").toLowerCase();
			if (agent.indexOf("firefox") > 0) {
				fileName = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
			} else {
				fileName = URLEncoder.encode(fileName, "UTF-8");
			}
			// 设置响应头，设置文件名称
			response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
			// 读取文件输入到内存中
			in = new FileInputStream(filepaths);
			// 输出流输出文件
			out = response.getOutputStream();
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			
		} catch (Exception e) {
		}finally{
			try {
				out.close();
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args) {
		for(int i = 0 ;i<1000;i++){
			Integer num = i%50;
			System.out.println(num);
		}
	}
}
