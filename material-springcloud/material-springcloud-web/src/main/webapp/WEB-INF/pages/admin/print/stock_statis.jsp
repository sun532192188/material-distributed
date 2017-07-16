<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>库存统计数据操作</title>
<link href="${ctx}/resources/platform/css/style.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
   *{
     font-size:16px;
     font-family: arial,sans-serif;
   }
    table{
        border-collapse: collapse;
        margin: auto;
    }
    table tr{
      height: 24px;
    }
    tr td{
        width: 60px;
        border: 1px solid blue;
        text-align: center;
    }
    .header_title td{
      font-family: SimHei ;
    }
   .header_ul li{
     float: left;
     list-style: none;
     margin-left:18%;
   }
    .content td{
	  font-size: 15px;
	}
</style>
<script src="${ctx}/resources/js/jquery.min.js"></script>
<script type="text/javascript">
$(function(){
	   var resultList =  eval('${resultList}');
	   var resultHtml = "";
	   $.each(resultList,function(i,obj){
		   if(i%50==0){
			  resultHtml += '<tr style="height: 60px;">'; 
			  resultHtml += '<td colspan="9" style="font-size:35px;border: none;vertical-align:top;">'; 
			  resultHtml += '${nowTime}库存统计'; 
			  resultHtml += '</td>'; 
			  resultHtml += '</tr>';
			  resultHtml += '<tr class="header_title">';
              resultHtml += '<td style="text-align: left;border: none;width: 100%;" colspan="9">';
              resultHtml += '<ul class="header_ul">';
              resultHtml += '<li style="margin-left:0%;">&nbsp;类别:&nbsp;${categoryOneName}</li>';
              resultHtml += '<li>&nbsp;子类:&nbsp;${categoryTwoName}</li>';
              resultHtml += '<li>&nbsp;库存类型:&nbsp;${stockType}</li>';
              resultHtml += '<li>&nbsp金额:&nbsp;元</li>';
              resultHtml += '</ul>';
              resultHtml += '</td>';
			  resultHtml += '</tr>';
			  resultHtml += '<tr class="thCon">';
			  resultHtml += '<td style="width:6%;">序号</td>';
			  resultHtml += '<td>编码</td>'; 
			  resultHtml += '<td>物资名称</td>'; 
			  resultHtml += '<td>规格</td>'; 
			  resultHtml += '<td>单位</td>'; 
			  resultHtml += '<td>单价</td>'; 
			  resultHtml += '<td>数量</td>'; 
			  resultHtml += '<td>金额</td>'; 
			  resultHtml += '<td>备注</td>'; 
			  resultHtml += '</tr>'; 
		   }
		   resultHtml += '<tr class="content">';
		   resultHtml += '<td style="text-align: center; width: 5%;">'+(i+1)+'</td>';
		   resultHtml += '<td style="text-align: left; width: 13%;">'+obj.goodsNo+'</td>';
		   resultHtml += '<td style="text-align: left; width: 13%;">'+obj.goodsName+'</td>';
		   resultHtml += '<td style="text-align: left; width: 25%;">'+obj.specModel+'</td>';
		   resultHtml += '<td style="text-align: center; width: 8%;">'+obj.spec+'</td>';
		   resultHtml += '<td style="text-align: right; width: 9%;">'+obj.goodsPrice+'</td>';
		   if(obj.stockNum == null){
			   resultHtml += '<td style="text-align: right; width: 7%;"></td>'; 
		   }else{
			   resultHtml += '<td style="text-align: right; width: 7%;">'+obj.stockNum+'</td>';
		   }
		   resultHtml += '<td style="text-align: right; width: 10%;">'+obj.formatSingleMoney+'</td>';
		   resultHtml += '<td style="width: 7%;"></td>';
		   resultHtml += '</tr>'; 
		   if((i+1)%50==0){
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="6" style="text-align: left;">&nbsp;合计金额(大写):&nbsp;${CN}</td>'; 
				  resultHtml += '<td colspan="3" style="text-align: left;">&nbsp;(小写):&nbsp;${sumMoney}元</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr style="height: 65px;">'; 
				  resultHtml += '<td colspan="9" style="text-align: left;">&nbsp;说明:&nbsp;${remarks}</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="7" style="text-align: left;border:none;">&nbsp;部门领导:</td>'; 
				  resultHtml += '<td colspan="2" style="text-align: left;border:none;">经办人:</td>';  
				  resultHtml += '</tr>';  
		   }
	   });
	    $("#goodsContent").append(resultHtml);
	    window.print();
	    var goodsName = "${queryArgs.goodsName}";
	    var categoryOne = "${queryArgs.categoryOne}";
	    var categoryTwo = "${queryArgs.categoryTwo}";
	    var type = "${queryArgs.type}";
	    location.href="${ctx}/stock/queryStockPage?goodsName="+goodsName+"&categoryOne="+categoryOne+"&categoryTwo="+categoryTwo+"&type="+type;
});
</script>
</head>
<body>

    <table align="center" style="width: 95%;" id="goodsContent">
       <%--  <thead>
           <tr style="height: 60px;">
              <td colspan="9" style="font-size:35px;border: none;vertical-align:top;">
                  ${nowTime}库存统计
              </td>
           </tr>
           <tr class="header_title">
             <td colspan="2" style="text-align: left;border: none;">&nbsp;类别:&nbsp;${categoryOneName}</td>
             <td colspan="2" style="text-align: left;border: none;">&nbsp;子类:&nbsp;${categoryTwoName}</td>
             <td colspan="3" style="text-align: left;border: none;">&nbsp;库存类型:&nbsp;${stockType}</td>
             <td colspan="2" style="text-align: left;border: none;">&nbsp;金额:&nbsp;${sumMoney}元</td>
           </tr>
        </thead>
        <tbody>
           <tr>
            <td>序号</td>
            <td>编码</td>
            <td>物资名称</td>
            <td>规格型号</td>
            <td>单位</td>
            <td>单价</td>
            <td>数量</td>
            <td>金额</td>
            <td>备注</td>
           </tr>
           <c:forEach items="${resultList}" var ="stock" varStatus="in">
	            <tr>
	            
		            <td style="text-align: center; width: 5%;">${in.count}</td>
		            <td style="text-align: left; width: 12%;">${stock.goodsNo}</td>
		            <td style="text-align: left; width: 18%;">${stock.goodsName}</td>
		            <td style="text-align: left; width: 15%;">${stock.specModel}</td>
		            <td style="text-align: center; width: 7%;">${stock.spec}</td>
		            <td style="text-align: right; width:12%;">${stock.goodsPrice}</td>
		            <td style="text-align: right; width: 10%;">${stock.stockNum}</td>
		            <td style="text-align: right; width: 10%;">${stock.formatSingleMoney}</td>
		            <td style="width: 10%;"></td>
	           </tr>
           </c:forEach>
        </tbody>
        <tfoot>
           <tr>
              <td colspan="6" style="text-align: left;">&nbsp;合计金额(大写):&nbsp;${CN}</td>
              <td colspan="3" style="text-align: left;">&nbsp;(小写):&nbsp;${sumMoney}元</td>
           </tr>
           <tr style="height: 65px;">
              <td colspan="9" style="text-align: left;">&nbsp;说明:&nbsp;</td>
           </tr>
           <tr>
             <td colspan="7" style="text-align: left;border:none;">&nbsp;部门领导:</td>
             <td colspan="2" style="text-align: left;border:none;">经办人:</td>
           </tr>
        </tfoot> --%>
    </table>
</body>
</html>
