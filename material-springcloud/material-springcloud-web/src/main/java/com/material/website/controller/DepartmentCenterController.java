package com.material.website.controller;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.zh.validate.util.ValidUtil;

import com.material.website.args.DepartPlanAddArgs;
import com.material.website.args.DepartPlanQueryArgs;
import com.material.website.args.DepartStockQueryArgs;
import com.material.website.args.MaterialConsumeAddArgs;
import com.material.website.args.MaterialConsumeQueryArgs;
import com.material.website.args.StaticsDepartPlanArgs;
import com.material.website.args.StatisDepartConsumeArgs;
import com.material.website.args.StatisUseAlloctArgs;
import com.material.website.dto.CategoryDto;
import com.material.website.dto.DeparPlanDto;
import com.material.website.dto.DepartStockDto;
import com.material.website.dto.GoodsInstallDto;
import com.material.website.dto.MaterialConsumeDto;
import com.material.website.dto.MonthPlanGoodsDto;
import com.material.website.dto.StaticsDepartPlanDto;
import com.material.website.dto.StatisDepartCounsumeDto;
import com.material.website.dto.StatisUseAlloctDto;
import com.material.website.dto.StockDto;
import com.material.website.entity.Admin;
import com.material.website.entity.Department;
import com.material.website.entity.MaterialConsume;
import com.material.website.service.ICategorySercice;
import com.material.website.service.IDepartmentCenterService;
import com.material.website.service.IDepartmentService;
import com.material.website.service.IUseAlloctService;
import com.material.website.system.Auth;
import com.material.website.system.ManagerType;
import com.material.website.system.MaterialOperate;
import com.material.website.system.Pager;
import com.material.website.util.BigDecimaUtil;
import com.material.website.util.MaterialNoUtil;

/**
 * 部门中心(包括月计划操作 部门入库 出库 调拨)
 * 
 * @author sunxiaorong
 *
 */
@Controller
@RequestMapping(value = "/departmentCenter")
@Auth(ManagerType.EVERYONE)
public class DepartmentCenterController {

	@Inject
	private IDepartmentCenterService departCenterService;
	@Inject
	private IDepartmentService departmentService;
	@Inject
	private ICategorySercice categoryService;
	@Inject
	private IUseAlloctService useAlloctService;
	
	/**
	 * 部门月计划查询
	 * @param queryArgs
	 * @return
	 */
	@RequestMapping(value="/queryMonthPlanPager")
	public String queryMonthPlanPager(DepartPlanQueryArgs queryArgs,Model model,HttpSession session){
		Admin loginManager  = (Admin) session.getAttribute("loginManager");
		queryArgs.setDepartmentId(loginManager.getDepartId());
		Pager<DeparPlanDto>pages = departCenterService.queryPlanPager(queryArgs); 
		model.addAttribute("queryArgs",queryArgs);
		model.addAttribute("pages",pages);
		return "admin/departmentCenter/monthPlan/list";
	}
	
	/**
	 * 添加初始化
	 * @return
	 */
	@RequestMapping(value="/addInit")
	public String addInit(String returnPage,Model model,HttpSession session){
		Admin loginManager = (Admin) session.getAttribute("loginManager");
		if(StringUtils.isNotEmpty(returnPage)){
			Integer count = departCenterService.queryMaxCkCout(loginManager.getDepartId()); ;
			String operatNo = MaterialNoUtil.getNo(MaterialOperate.CK.getName(), count+1);
			model.addAttribute("operatNo",operatNo);
			session.setAttribute("CKNO",operatNo);
			return "admin/"+returnPage;
		}else{
			Department department = departmentService.loadDepartment(loginManager.getDepartId());
			model.addAttribute("departmentName",department.getDepartmentName());
			String md5Str = UUID.randomUUID().toString();
			session.setAttribute("md5Str", md5Str);
		}
		return "admin/departmentCenter/monthPlan/addPlan";
	}
	
	/**
	 * 添加月计划
	 * @param addArgs
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/addMonthPlan",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMonthPlan(DepartPlanAddArgs addArgs,Model model,HttpSession session){
		List validInfo = ValidUtil.newInstance().valid(addArgs);
		Map<String, Object>map  = new HashMap<String, Object>();
		if (validInfo.size() > 0) {
			map.put("status", 200);
			map.put("msg", "参数为空");
			return map;
		}
		try {
			String md5Str = (String) session.getAttribute("md5Str");
			addArgs.setTempId(md5Str);
			Admin admin = (Admin) session.getAttribute("loginManager");
			if(admin.getDepartId() != null){
				Department department = departmentService.loadDepartment(admin.getDepartId());
				addArgs.setDepartmentId(admin.getDepartId());
				addArgs.setDepartmentName(department.getDepartmentName());
			}
			departCenterService.addMonthPlan(addArgs);
			map.put("status", 200);
			map.put("msg", "添加成功");
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("status", 500);
			map.put("msg", "添加失败");
		}
		return map;
	}
	
	/**
	 * 部门查询商品初始化
	 * @return
	 */
	@RequestMapping(value="/queryGoodsInit")
	public String queryGoodsInit(Integer operatType,Integer stockType,String operatNo,Model model,HttpSession session){
		model.addAttribute("operatType",operatType);
		/*model.addAttribute("stockType",stockType);*/
		model.addAttribute("operatNo",operatNo);
		model.addAttribute("categoryList", queryCategoryOne());
		if(operatType == 3 || operatType == 4){
			return "admin/departmentCenter/stockList";
		}
		return "admin/departmentCenter/goodsList";
	}
	
	
	/**
	 * 根据计划编号查询商品信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/queryMonthPlanGoods")
	public String queryMonthPlanGoods(Integer planId,String departmentName,String sumMoney,Integer type,Model model,HttpSession session) throws UnsupportedEncodingException{
	    departmentName = new String(departmentName.getBytes("ISO-8859-1"),"UTF-8");
		List<MonthPlanGoodsDto> goodsList= departCenterService.queryMonthPlanGoods(planId);
		model.addAttribute("goodsList",goodsList);
		model.addAttribute("departmentName",departmentName);
		model.addAttribute("sumMoney",sumMoney);
		model.addAttribute("type",type);
		session.setAttribute("planId", planId);
		return "admin/departmentCenter/monthPlan/plan_goods";
	}
	
	/**
	 * 更新月计划状态
	 * @return
	 */
	@RequestMapping(value="/updatePlanStatus",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePlanStatus(HttpSession session){
		Integer planId = (Integer) session.getAttribute("planId");
		Map<String, Object>map = new HashMap<String,Object>();
		if(planId == null){
			map.put("status",500);
			map.put("msg","请求参数为空");
			return map;
		}
		boolean isSuccess = departCenterService.updateMonthPlanStatus(planId);
		if(isSuccess){
			map.put("status",200);
			map.put("msg","操作成功");
		}else{
			map.put("status",500);
			map.put("msg","操作失败");
		}
		return map;
	}
	
	/**
	 * 删除计划商品
	 * @param goodsId
	 * @return
	 */
	@RequestMapping(value="/delPlanGoods",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delPlanGoods(Integer goodsId){
		Map<String, Object>map = new HashMap<String,Object>();
		if(goodsId == null){
			map.put("status",500);
			map.put("msg","请求参数为空");
			return map;
		}
		boolean isSuccess = departCenterService.delPlanGoods(goodsId);
		if(isSuccess){
			map.put("status",200);
			map.put("msg","删除成功");
		}else{
			map.put("status",500);
			map.put("msg","删除失败");
		}
		return map;
	}
	
	
    /**
     * 组装月计划商品
     * @param planId
     * @param goodsStr
     * @param model
     * @return
     */
	@RequestMapping(value="/installPlanGoods")
	public String installPlanGoods(String goodsStr,String returnPage,Model model,HttpSession session){
		Integer planId = (Integer) session.getAttribute("planId");
		String isSuccess = departCenterService.updatePlanGoods(planId, goodsStr);
		if(isSuccess != null && StringUtils.isNotEmpty(isSuccess)){
			Admin loginMananger = (Admin) session.getAttribute("loginManager");
			if(loginMananger != null){
				Department department = departmentService.loadDepartment(loginMananger.getDepartId());
				model.addAttribute("departmentName",department.getDepartmentName());
			}
			model.addAttribute("sumPrice",isSuccess);
			model.addAttribute("type",0);
		}
		 List<MonthPlanGoodsDto> goodsList = departCenterService.queryMonthPlanGoods(planId);
		 model.addAttribute("goodsList",goodsList);
		 return "admin/"+returnPage;
	}
	
	/**
	 * 部门出库查询分页
	 * @param operatNo
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/materialConsumePager")
	public String materialConsumePager(MaterialConsumeQueryArgs queryArgs,HttpSession session,Model model) throws UnsupportedEncodingException{
        Admin admin = (Admin) session.getAttribute("loginManager");
        if(admin != null){
        	queryArgs.setDepartmentId(admin.getDepartId());
        }
        if(StringUtils.isNotEmpty(queryArgs.getOperatNo())){
        	queryArgs.setOperatNo(new String(queryArgs.getOperatNo().getBytes("ISO-8859-1"),"UTF-8"));
        }
        Pager<MaterialConsumeDto> pages = departCenterService.queryConsumePager(queryArgs);
        model.addAttribute("queryArgs",queryArgs);
        model.addAttribute("pages",pages);
	    return "admin/departmentCenter/outStock/list";
	}
	
	/**
	 * 部门库存查询分页
	 * @param operatNo
	 * @param startDate
	 * @param endDate
	 * @param model
	 * @param session
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/departStockPager")
	public String departStockPager(DepartStockQueryArgs  queryArgs,HttpSession session,Model model) throws UnsupportedEncodingException{
        Admin admin = (Admin) session.getAttribute("loginManager");
        if(admin != null){
        	queryArgs.setDepartmentId(admin.getDepartId());
        }
        if(StringUtils.isNotEmpty(queryArgs.getGoodsName())){
           String goodsName = queryArgs.getGoodsName();
           goodsName = new String(goodsName.getBytes("ISO-8859-1"),"UTF-8");
           queryArgs.setGoodsName(goodsName);
        }
        Pager<DepartStockDto> pages = departCenterService.queryDepartStockPager(queryArgs);
        model.addAttribute("queryArgs",queryArgs);
        model.addAttribute("pages",pages);
        model.addAttribute("categoryList", queryCategoryOne());
	    return "admin/"+queryArgs.getReturnPage();
	}
	
	/**
	 * 查询部门库存商品信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/queryDepartStockList", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDepartStockList(DepartStockQueryArgs queryArgs,HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Admin loginManager = (Admin) session.getAttribute("loginManager");
		queryArgs.setDepartmentId(loginManager.getDepartId());
		List<StockDto> resultList = departCenterService.queryDepartStockList(queryArgs);
		resultMap.put("status", 200);
		resultMap.put("msg", "查询成功");
		resultMap.put("resultList", resultList);
		return resultMap;
	}
	
	/**
	 *  部门物资出库
	 * @param addArgs
	 * @param model
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/departCK")
	@ResponseBody
	public Map<String, Object> departCK(MaterialConsumeAddArgs addArgs,Model model,HttpSession session){
		List validInfo = ValidUtil.newInstance().valid(addArgs);
		Map<String, Object>map  = new HashMap<String, Object>();
		if (validInfo.size() > 0) {
			map.put("status", 200);
			map.put("msg", "参数为空");
			return map;
		}
		try {
			
			Admin admin = (Admin) session.getAttribute("loginManager");
			if(admin.getDepartId() != null){
				addArgs.setDepartmentId(admin.getDepartId());
			}
			map  = departCenterService.addDepartOutStock(addArgs);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("status", 500);
			map.put("msg", "添加失败");
		}
		return map;
	}
	
	/**
	 *根据出库主键查询出库商品信息
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/queryCkGoodsList")
	public String queryCkGoodsList(Integer ckNo,String operatNo,String ckDate,Double sumMoney,Model model,HttpSession session) throws UnsupportedEncodingException, ParseException{
		List<GoodsInstallDto> goodsList= departCenterService.queryCkGoodsList(ckNo);
		model.addAttribute("goodsList",goodsList);
		Admin admin = (Admin) session.getAttribute("loginManager");
		Department depart = departmentService.loadDepartment(admin.getDepartId());
		model.addAttribute("departmentName",depart.getDepartmentName());
		model.addAttribute("sumMoney",BigDecimaUtil.formatMoney(sumMoney));
		model.addAttribute("operatNo", operatNo);
		ckDate = ckDate.split(" ")[0];
		model.addAttribute("ckDate",ckDate);
		return "admin/departmentCenter/outStock/stock_goods";
	}
	
	/**
	 * 更新固定资产状态初始化
	 * @param status
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/updateStatusInit")
	public String updateStautsInit(Integer fixedId,Integer status,Model model){
		model.addAttribute("status",status);
		model.addAttribute("fixedId",fixedId);
		return "admin/departmentCenter/departStock/fixedStatus";
	}
	
	/**
	 * 根据固定资产编号更改资产状态
	 * type: 1.未使用   2.使用中  3.已损坏   4.调拨给其他部门
	 * @return
	 */
	@RequestMapping(value = "/updateFixedStatus", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateFixedStatus(Integer fixedId,Integer status,String remarks,Integer targetDepartId,HttpSession session) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if(fixedId == null || status == null || StringUtils.isEmpty(remarks)){
			resultMap.put("status",500);
			resultMap.put("msg", "请求参数为空");
			return resultMap;
		}
		Admin loginManager = (Admin) session.getAttribute("loginManager");
		boolean isSuccess = departCenterService.updateFixedGoodsStatus(fixedId, status, remarks,loginManager.getDepartId(),targetDepartId);
		if(isSuccess){
			resultMap.put("status",200);
			resultMap.put("msg", "操作成功");
		}else{
			resultMap.put("status",500);
			resultMap.put("msg", "操作失败");
		}
		return resultMap;
	}
	
	/**
	 * 统计部门月计划
	 * @param queryArgs
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/staticsDepartPlan")
    public String staticsDepartPlan(StaticsDepartPlanArgs queryArgs,Model model) throws UnsupportedEncodingException{
    	if(StringUtils.isNotEmpty(queryArgs.getGoodsName())){
    		queryArgs.setGoodsName(new String(queryArgs.getGoodsName().getBytes("ISO-8859-1"),"UTF-8"));
    	}
    	if(StringUtils.isNotEmpty(queryArgs.getPlanName())){
    		queryArgs.setPlanName(new String(queryArgs.getPlanName().getBytes("ISO-8859-1"),"UTF-8"));
    	}
    	Pager<StaticsDepartPlanDto>pages = departCenterService.staticsDepartPlan(queryArgs);
    	model.addAttribute("pages",pages);
    	model.addAttribute("queryArgs",queryArgs);
    	model.addAttribute("categoryList", queryCategoryOne());
    	return "admin/tongji/departplan";
    }
	
	/**
	 * 部门入库统计
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/statisDepartStorage")
	public String statisDepartStorage(StatisUseAlloctArgs queryArgs,Model model,HttpSession session) throws UnsupportedEncodingException{
		if(StringUtils.isNotEmpty(queryArgs.getGoodsName())){
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName().getBytes("ISO-8859-1"),"UTF-8"));
		}
		Admin loginManager = (Admin) session.getAttribute("loginManager");
		queryArgs.setDepartId(loginManager.getDepartId());
	    Pager<StatisUseAlloctDto>pages = useAlloctService.statisUseAlloctPager(queryArgs);
	    model.addAttribute("pages",pages);
	    model.addAttribute("queryArgs",queryArgs);
	    model.addAttribute("categoryList",queryCategoryOne());
	    return "admin/tongji/depart_storage_statis";
	}
	
	
	/**
	 * 部门出库统计
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/statisDepartConsumePager")
	public String statisDepartConsumePager(StatisDepartConsumeArgs queryArgs,Model model,HttpSession session) throws UnsupportedEncodingException{
		if(StringUtils.isNotEmpty(queryArgs.getGoodsName())){
			queryArgs.setGoodsName(new String(queryArgs.getGoodsName().getBytes("ISO-8859-1"),"UTF-8"));
		}
		Admin loginManager = (Admin) session.getAttribute("loginManager");
		queryArgs.setDepartId(loginManager.getDepartId());
	    Pager<StatisDepartCounsumeDto>pages = departCenterService.statisDepartConsumePager(queryArgs);
	    model.addAttribute("pages",pages);
	    model.addAttribute("queryArgs",queryArgs);
	    model.addAttribute("categoryList",queryCategoryOne());
	    return "admin/tongji/depart_consume_statis";
	}
   
	
	/**
	 * 部门出库更新初始化
	 * @param storageId
	 * @param storageType 1.验收  2预存
	 * @return
	 */
	@RequestMapping(value="/updateConsumeInit")
	public String updateConsumeInit(Integer consumeId,Model model){
		Map<String,Object>resultMap = departCenterService.updateDepartConsumeInit(consumeId);
		model.addAttribute("goodsList",resultMap.get("resultList"));
		MaterialConsume consume =  (MaterialConsume) resultMap.get("consume");
		String time = new SimpleDateFormat("YYYY-MM-dd").format(consume.getConsumeDate());
		model.addAttribute("time",time);
		String sumMoney  = "";
		if(consume.getConsumeMoney() > 0){
			sumMoney = BigDecimaUtil.formatMoney(consume.getConsumeMoney());
		}else{
			sumMoney = "0";
		}
		model.addAttribute("sumMoney",sumMoney);
		model.addAttribute("consume",consume);
		return "admin/departmentCenter/outStock/updateOutstock";
	}
	
	
	/**
	 * 部门出库  修改入库
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/updateConsume",method={RequestMethod.POST,RequestMethod.GET})
	@ResponseBody
	public Map<String, Object> updateConsume(MaterialConsumeAddArgs updateArgs){
		Map<String, Object>map = new HashMap<String, Object>();
		List validInfo = ValidUtil.newInstance().valid(updateArgs);
		if (validInfo.size() > 0) {
			map.put("status", 500);
			map.put("msg", validInfo.get(0).toString());
			return map;
		}
		boolean  isTrue = departCenterService.updateDepartConsume(updateArgs);
		if(isTrue){
			map.put("status", 200);
			map.put("msg", "修改成功");
		}else{
			map.put("status", 500);
			map.put("msg", "修改失败");
		}
		return map;
	}
	
	
	/**
	 * 根据编号查询出库信息
	 * @param operatNo
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/queryConsumeByNo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryConsumeByNo(String operatNo) throws ParseException{
        Map<String, Object>map = new HashMap<String,Object>();
        if(StringUtils.isEmpty(operatNo)){
        	map.put("status", 500);
        	map.put("msg","请求参数为空");
        	return map;
        }
        MaterialConsume consume = departCenterService.queryConsumeInfo(operatNo,null);
        String timeStr = new SimpleDateFormat("YYYY-MM-dd").format(consume.getConsumeDate());
        map.put("time", timeStr);
        map.put("status", 200);
        map.put("msg", "请求成功");
        map.put("consume",consume);
        return map;
	}
	
	
	/**
	 * 将部门出库信息入账
	 * @param storageId
	 * @return
	 */
	@RequestMapping(value="/lockConsumeInfo",method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> lockConsumeInfo(Integer consumeId){
		Map<String,Object>map = new HashMap<String,Object>();
		if(consumeId == null){
			map.put("status", 500);
			map.put("msg", "请求参数为空");
			return map;
		}
		boolean isSuccess = departCenterService.addLockConsume(consumeId);
		if(isSuccess){
			map.put("status", 200);
			map.put("msg", "入账成功");
		}else{
			map.put("status", 500);
			map.put("msg", "入账失败");
		}
		return map;
	}
	
	/**
	 * 查询一级分类
	 * 
	 * @return
	 */
	public List<CategoryDto> queryCategoryOne() {
		List<CategoryDto> categoryList = categoryService.queryCategoryList(0);
		return categoryList;
	}
}
