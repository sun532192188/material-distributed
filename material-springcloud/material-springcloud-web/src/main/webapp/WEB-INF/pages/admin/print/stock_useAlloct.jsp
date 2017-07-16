<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>调拨领用打印</title>
<link href="${ctx}/resources/platform/css/style.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
* {
	font-size: 20px;
	font-family: arial, sans-serif;
}

table {
	border-collapse: collapse;
	margin: auto;
}

table tr {
	height: 32px;
}

tr td {
	width: 60px;
	border: 1px solid blue;
	text-align: center;
}

.print_title td {
	font-family: SimHei;
	font-weight: bold;
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
	   var rightHtml = "";
	   $.each(resultList,function(i,obj){
		   if(i%10==0){
			  resultHtml += '<tr style="height: 70px; vertical-align: top;">'; 
			  resultHtml += '<td colspan="10" style="border: none;">'; 
			  resultHtml += '<span style="width:24%;height:50px; border-bottom:1px solid red;margin-left:38%;">'; 
			  resultHtml += '<span style="width:100%;height:45px; border-bottom:1px solid red;font-size: 35px;text-align: center;">'; 
			  resultHtml += '${title}'; 
			  resultHtml += '</span>'; 
			  resultHtml += '</span>'; 
			  resultHtml += '</td>'; 
			  resultHtml += '</tr>';
			  resultHtml += '<tr>';
			  resultHtml += '<td colspan="3" style="text-align: left; border: none;">&nbsp;部门名称:&nbsp;${departName}</td>';
			  resultHtml += '<td colspan="4" style="text-align: left; border: none;">&nbsp;时间:&nbsp;${nowTime}</td>';
			  resultHtml += '<td colspan="3" style="text-align: left; border: none;">&nbsp;编号:&nbsp;${operatNo}</td>';
			  resultHtml += '</tr>';
			  resultHtml += '<tr class="print_title">'; 
			  resultHtml += '<td style="font-family: SimHei; font-weight: bold;">序号</td>'; 
			  resultHtml += '<td>编码</td>'; 
			  resultHtml += '<td>物资名称</td>'; 
			  resultHtml += '<td>规格型号</td>'; 
			  resultHtml += '<td>单位</td>'; 
			  resultHtml += '<td>单价</td>'; 
			  resultHtml += '<td>数量</td>'; 
			  resultHtml += '<td>金额</td>'; 
			  resultHtml += '<td>供应商</td>'; 
			  resultHtml += '<td>备注</td>'; 
			  resultHtml += '</tr>'; 
		   }
		   resultHtml += '<tr class="content">';
		   resultHtml += '<td style="text-align: center; width: 5%;">'+(i+1)+'</td>';
		   resultHtml += '<td style="text-align: left; width: 11%;">'+obj.goodsNo+'</td>';
		   resultHtml += '<td style="text-align: left; width: 12%;">'+obj.goodsName+'</td>';
		   resultHtml += '<td style="text-align: left; width: 26%;">'+obj.specModel+'</td>';
		   resultHtml += '<td style="text-align: center; width: 5%;">'+obj.spec+'</td>';
		   resultHtml += '<td style="text-align: right; width: 9%;">'+obj.formatPrice+'</td>';
		   if(obj.goodsNum == null){
			   resultHtml += '<td style="text-align: right; width: 6%;"></td>'; 
		   }else{
			   resultHtml += '<td style="text-align: right; width: 6%;">'+obj.goodsNum+'</td>';
		   }
		   resultHtml += '<td style="text-align: right; width: 10%;">'+obj.formatMoney+'</td>';
		   resultHtml += '<td style="width: 10%;text-align: left;">'+obj.supplierName+'</td>';
		   resultHtml += '<td style="width: 8%;"></td>';
		   resultHtml += '</tr>'; 
		   if((i+1)%10==0){
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="6" style="text-align: left;">&nbsp;合计金额(大写):&nbsp;${CN}</td>'; 
				  resultHtml += '<td colspan="4" style="text-align: left;">&nbsp;(小写):&nbsp;${sumMoney}元</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr style="height: 65px;">'; 
				  resultHtml += '<td colspan="10" style="text-align: left;">&nbsp;${type}备注:&nbsp;</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="3" style="text-align: left; border: none;">&nbsp;采购办主任:</td>'; 
				  resultHtml += '<td colspan="4" style="text-align: left; border: none;">采购办保管员:</td>'; 
				  resultHtml += '<td colspan="3" style="text-align: left; border: none;">部门保管员:</td>'; 
				  resultHtml += '</tr>';  
				  rightHtml += '<div style="float: left;margin-top: 13%;">白<br/>联<br/>采<br/>购<br/>办<br/>留<br/>存<br/>，<br/>红<br/>联<br/>调<br/>拨<br/>部<br/>门<br/>留<br/>存</div>';
		   }
	   });
	   $("#goodsContent").append(resultHtml);
	   $("#rightContent").append(rightHtml);
	   window.print();
		var returnType = "${returnType}";
		var url = "${ctx}/useAlloct/queryUseAlloctPager";
		//1.调拨  2.领用
		if (returnType ==  1) {
			url += "?type=1";
		} else {
			url += "?type=2";
		}
		location.href = url;
});
</script>
</head>
<body>
 
	<table align="center" style="width:96.5%;float: left;" id="goodsContent">
	<%--	<thead>
			<tr style="height: 70px; vertical-align: top;">
				<td colspan="10" style="border: none;">
                 <span style="width:24%;height:50px; border-bottom:1px solid red;margin-left:38%;">
			       <span style="width:100%;height:45px; border-bottom:1px solid red;font-size: 35px;text-align: center;">
			         ${title}
			       </span>
			      </span>
				</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: left; border: none;">&nbsp;部门名称:&nbsp;${departName}</td>
				<td colspan="4" style="text-align: left; border: none;">&nbsp;时间:&nbsp;${nowTime}</td>
				<td colspan="3" style="text-align: left; border: none;">&nbsp;编号:&nbsp;${operatNo}</td>
			</tr>
			<tr class="print_title">
				<td style="font-family: SimHei; font-weight: bold;">序号</td>
				<td>编码</td>
				<td>物资名称</td>
				<td>规格型号</td>
				<td>单位</td>
				<td>单价</td>
				<td>数量</td>
				<td>金额</td>
				<td>供应商</td>
				<td>备注</td>
			</tr>

		</thead>
		<tbody>
			<c:forEach items="${resultList}" var="goods" varStatus="in">
				<tr>
					<td style="text-align: center; width: 5%;">${in.count}</td>
					<td style="text-align: left; width: 15%;">${goods.goodsNo}</td>
					<td style="text-align: left; width: 18%;">${goods.goodsName}</td>
					<td style="text-align: left; width: 15%;">${goods.specModel}</td>
					<td style="text-align: center; width: 5%;">${goods.spec}</td>
					<td style="text-align: right; width: 7%;">${goods.price}</td>
					<td style="text-align: right; width: 7%;">${goods.goodsNum}</td>
					<td style="text-align: right; width: 10%;">${goods.formatMoney}</td>
					<td style="width: 12%;text-align: left;">${goods.supplierName}</td>
					<td style="width: 8%;"></td>
				</tr>
			</c:forEach>
		</tbody>
		<tfoot>
			<tr>
				<td colspan="6" style="text-align: left;">&nbsp;合计金额(大写):&nbsp;${CN}</td>
				<td colspan="4" style="text-align: left;">&nbsp;(小写):&nbsp;${sumMoney}元</td>
			</tr>
			<tr style="height: 65px;">
				<td colspan="10" style="text-align: left;">&nbsp;${type}备注:&nbsp;</td>
			</tr>
			<tr>
				<td colspan="3" style="text-align: left; border: none;">&nbsp;采购办主任:</td>
				<td colspan="4" style="text-align: left; border: none;">采购办保管员:</td>
				<td colspan="3" style="text-align: left; border: none;">部门保管员:</td>
			</tr>
		</tfoot>--%>
	</table>
  <!-- <div style="float: left;margin-top: 18%;">白<br/>联<br/>采<br/>购<br/>办<br/>留<br/>存<br/>，<br/>红<br/>联<br/>调<br/>拨<br/>部<br/>门<br/>留<br/>存</div>  -->
   <div id="rightContent"></div>
</body>
</html>
