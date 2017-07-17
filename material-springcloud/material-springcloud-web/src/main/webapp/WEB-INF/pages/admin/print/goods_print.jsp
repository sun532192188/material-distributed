<%@page import="java.awt.Window"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/include/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>商品数据操作</title>
<link href="${ctx}/resources/platform/css/style.css" rel="stylesheet"
	type="text/css" />
<style type="text/css">
   *{
     font-size:14px;
     font-family: arial,sans-serif;
   }
    table{
        border-collapse: collapse;
        margin: auto;
    }
    table tr{
      height: 25px;
    }
    tr td{
        width: 60px;
        border: 1px solid blue;
        text-align: center;
    }
    .content td{
	  font-size: 15px;
	}
    
</style>
<script src="${ctx}/resources/js/jquery.min.js"></script>
<script type="text/javascript">
   $(function(){
	   var goodsList =  eval('${goodsList}');
	   var resultHtml = "";
	   var title = "${title}";
	   $.each(goodsList,function(i,obj){
		   if(i%50==0){
			  resultHtml += ' <tr style="height: 60px;border:none;">'; 
			  resultHtml += '<td colspan="7" style="font-size:35px;border:none;vertical-align:top;">'; 
			  resultHtml += title; 
			  resultHtml += '</td>'; 
			  resultHtml += '</tr>'; 
			  resultHtml += '<tr>'; 
			  resultHtml += ' <td colspan="3" style="text-align: left;border: none;">&nbsp;商品名称:&nbsp;${goodsName}</td>'; 
			  resultHtml += '<td colspan="2" style="text-align: left;border: none;">&nbsp;类别:&nbsp;${categoryOneName}</td>'; 
			  resultHtml += '<td colspan="=2" style="text-align: left;border: none;">&nbsp;子类:&nbsp;${categoryTwoName}</td>'; 
			  resultHtml += '</tr>'; 
			  resultHtml += '<tr style="font-family: SimHei;font-size: 16px;">'; 
			  resultHtml += '<td>序号</td>'; 
			  resultHtml += '<td>编码</td>'; 
			  resultHtml += '<td>物资名称</td>'; 
			  resultHtml += '<td>规格型号</td>'; 
			  resultHtml += '<td>单位</td>'; 
			  resultHtml += '<td>单价</td>'; 
			  resultHtml += '<td>备注</td>'; 
			  resultHtml += '</tr>'; 
		   }
		   resultHtml += '<tr class="content">';
		   resultHtml += '<td style="width: 8%;">'+(i+1)+'</td>';
		   resultHtml += '<td style="width: 20%;text-align: left;">'+obj.goodsNo+'</td>';
		   resultHtml += '<td style="width: 13%;text-align: left;">'+obj.goodsName+'</td>';
		   resultHtml += '<td style="width: 27%;text-align: left;">'+obj.specModel+'</td>';
		   resultHtml += ' <td style="width: 15%;text-align: center;">'+obj.spec+'</td>';
		   if(obj.price == null){
			   resultHtml += '<td style="width: 10%;text-align: right;"></td>';
		   }else{
			   resultHtml += '<td style="width: 10%;text-align: right;">'+obj.price+'</td>';
		   }
		   resultHtml += '<td style="width: 7%;"></td>';
		   resultHtml += '</tr>';
		   if((i+1)%50==0){
			      var CN = "${CN}";
			      var sumMoney = "${sumMoney}";
				  resultHtml += '<tr style="height: 64.4px;">'; 
				  resultHtml += '<td colspan="7" style="text-align: left;">&nbsp;说明:&nbsp;</td>'; 
				  resultHtml += '</tr>'; 
		 }
	   });
	  $("#goodsContent").append(resultHtml);
	   window.print();
	  var returnType = "${queryArgs.returnType}";
	  var url = "${ctx}/goods/queryGoodsPager";
	  location.href= url+"?returnType="+returnType; 
   });
</script>
</head>
<body style="width: 100%;">
    <table style="width:98%;" align="center" id="goodsContent">
    </table>
</body>
<script type="text/javascript">
	/* $(function() {
	  //  window.print();
	     var goodsName = "${queryArgs.goodsName}";
		var categoryOne =  "${queryArgs.categoryOne}";
		var categoryTwo =  "${queryArgs.categoryTwo}";
		var status =  "${queryArgs.status}";
		var supplierId =  "${queryArgs.supplierId}";
		var url = "${ctx}/goods/queryGoodsPager?goodsName=" + goodsName + "&categoryOne="
				+ categoryOne + "&categoryTwo=" + categoryTwo + "&status="
				+ status+"&supplierId="+supplierId;
	   // location.href= url; 
	}); */
</script>
</html>
