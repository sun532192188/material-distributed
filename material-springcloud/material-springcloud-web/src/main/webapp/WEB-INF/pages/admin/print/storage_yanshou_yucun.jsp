<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>验收预存打印</title>
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
.content td{
  font-size: 15px;
}

.print_title td {
	font-family: SimHei;
}
 .header_ul li{
      float: left;
      list-style: none;
      margin-left:16%;
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
			  resultHtml += '<span style="width:34%;height:50px; border-bottom:1px solid red;margin-left: 33%;">'; 
			  resultHtml += '<span style="width:100%;height:45px; border-bottom:1px solid red;font-size: 35px;text-align: center;">'; 
			  resultHtml += '${title}'; 
			  resultHtml += '</span>'; 
			  resultHtml += '</span>'; 
			  resultHtml += '</td>'; 
			  resultHtml += '</tr>';
			  resultHtml += '<tr>';
			  resultHtml += '<td style="text-align: left;border: none;width: 100%;" colspan="10">';
              resultHtml += '<ul class="header_ul">';
              resultHtml += '<li style="margin-left:1%;">&nbsp;供应商:&nbsp;${supplierName}</li>';
              resultHtml += '<li>&nbsp;时间:&nbsp;${operatTime}</li>';
              resultHtml += '<li>&nbsp;编号:&nbsp;${operatNo}</li>';
              resultHtml += '</ul>';
              resultHtml += '</td>';
			  resultHtml += '</tr>';
			  
			  resultHtml += '<tr class="print_title">'; 
			  resultHtml += '<td>序号</td>'; 
			  resultHtml += '<td>编码</td>'; 
			  resultHtml += '<td>物资名称</td>'; 
			  resultHtml += '<td>规格型号</td>'; 
			  resultHtml += '<td>单位</td>'; 
			  resultHtml += '<td>单价</td>'; 
			  resultHtml += '<td>数量</td>'; 
			  resultHtml += '<td>金额</td>'; 
			  resultHtml += '<td>质量状况</td>'; 
			  resultHtml += '<td>备注</td>'; 
			  resultHtml += '</tr>'; 
		   }
		   resultHtml += '<tr class="content">';
		   resultHtml += '<td style="width: 8%;">'+(i+1)+'</td>';
		   resultHtml += '<td style="text-align: left; width: 11%;">'+obj.goodsNo+'</td>';
		   resultHtml += '<td style="text-align: left; width: 12%;">'+obj.goodsName+'</td>';
		   resultHtml += '<td style="text-align: left; width: 26%;">'+obj.specModel+'</td>';
		   resultHtml += '<td style="text-align: center; width: 5%;">'+obj.spec+'</td>';
		   resultHtml += '<td style="text-align: right; width: 9%;">'+obj.formatPrice+'</td>';
		   if(obj.goodsNum == null){
			   resultHtml += '<td style="text-align: right; width: 5%;"></td>'; 
		   }else{
			   resultHtml += '<td style="text-align: right; width: 5%;">'+obj.goodsNum+'</td>';
		   }
		   resultHtml += '<td style="text-align: right; width: 10%;">'+obj.formatMoney+'</td>';
		   resultHtml += '<td style="width: 9%;"></td>';
		   resultHtml += '<td style="width:7%;"></td>';
		   resultHtml += '</tr>'; 
		   
		   if((i+1)%10==0){
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="6" style="text-align: left;">&nbsp;合计金额(大写):&nbsp;${CN}</td>'; 
				  resultHtml += '<td colspan="4" style="text-align: left;">&nbsp;(小写):&nbsp;${sumMoney}元</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr style="height: 65px;">'; 
				  resultHtml += '<td colspan="10" style="text-align: left;">&nbsp;${type}记录:&nbsp;</td>'; 
				  resultHtml += '</tr>'; 
				  resultHtml += '<tr>'; 
				  resultHtml += '<td colspan="3" style="text-align: left; border: none;">&nbsp;采购办主任:</td>'; 
				  resultHtml += '<td colspan="2" style="text-align: left; border: none;">采购员:</td>'; 
				  resultHtml += '<td colspan="3" style="text-align: left; border: none;">验收人:</td>'; 
				  resultHtml += '<td colspan="2" style="text-align: left; border: none;">送货人:</td>'; 
				  resultHtml += '</tr>';  
				  rightHtml += '<div style="float: left;margin-top: 13%;">白<br/>联<br/>采<br/>购<br/>办<br/>留<br/>存<br/>，<br/>红<br/>联<br/>供<br/>应<br/>商<br/>留<br/>存</div>';
		   }
	   });
	   $("#goodsContent").append(resultHtml);
	   $("#rightContent").append(rightHtml);
		window.print();
		var operatNo = "${queryArgs.operatNo}";
		var supplierId = "${queryArgs.supplierId}";
		var startDate = "${queryArgs.startDate}";
		var endDate = "${queryArgs.endDate}";
		var type = "${queryArgs.type}";
		var forwardType = "${type}";
		var url = "${ctx}/storage/queryStorage";
		if (forwardType == '验收') {
			url = url + "?type=1"
		} else {
			url = url + "?type=2"
		}
		location.href = url ;/* + "&operatNo=" + operatNo + "&supplierId="
				+ supplierId + "&startDate=" + startDate + "&endDate="
				+ endDate; */
});
</script>
</head>
<body>
	<table  style="width: 96.5%;float:left;" id="goodsContent">
	</table>
    <div id="rightContent"></div>
</body>
</html>
