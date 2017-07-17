<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="/include/commons.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>物资出库商品</title>
<link href="${ctx}/resources/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/resources/platform/css/style.css" rel="stylesheet"
	type="text/css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/chuchur/css/base.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/chuchur/css/formui.css" />
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/chuchur/css/chur.css" />
<script type="text/javascript" src="${ctx}/resources/js/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/uploadify/jquery.uploadify.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/org.zh.plugins.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/validate/main.css" />
<script type="text/javascript"
	src="${ctx}/resources/js/jquery.validate.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jquery.cms.validate.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/org.zh.common.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/org.cp.common.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/My97DatePicker/lang/zh-cn.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/cityselect/jquery.cityselect.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/chuchur/js/churCommon.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-form.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/chuchur/js/chur-alert.1.0.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/chuchur/js/churCommon.js"></script>
<style type="text/css">
* {
	padding: 0px;
	margin: 0px;
}

tr {
	cursor: pointer;
}

.uploadCourse {
	padding: 20px;
}

.red_input {
	color: red;
	margin-left: 5px;
}

.ru {
	margin-top: 10px;
}

.ru ul li {
	list-style: none;
	display: inline;
	margin-left: 40px;
	font-size: 15px;
}
.qian {
	border: 1px solid white;
}

.input_text {
	margin-right: 100px;
}

.courseinput {
	width: 200px;
	height: 32px;
	line-height: 32px;
	border-top: solid 1px #a7b5bc;
	border-left: solid 1px #a7b5bc;
	border-right: solid 1px #ced9df;
	border-bottom: solid 1px #ced9df;
	text-indent: 10px;
}

.textinput {
	border-top: solid 1px #a7b5bc;
	border-left: solid 1px #a7b5bc;
	border-right: solid 1px #ced9df;
	border-bottom: solid 1px #ced9df;
	background: url(../images/inputbg.gif) repeat-x;
	padding: 10px;
	width: 745px;
	height: 135px;
	line-height: 20px;
	overflow: hidden;
}
</style>
<script type="text/javascript">

</script>
</head>
<body style="height: 100%; overflow-X: hidden;">
	<div class="ru">
			<ul>
				<li style="margin-left: 30px;">
			     <label style="font-weight: bold; font-size: 15px;">部门名称:</label>&nbsp;&nbsp;
			     <b style="font-size: 15px;" id="departmentName">${departmentName}</b>
			   </li>
			   <li><label style="font-weight: bold; font-size: 15px;">日期:</label><b style="font-size: 15px;" id="storageDate">${ckDate}</b></li>
			   <li><label style="font-weight: bold; font-size: 15px;" >合计金额:</label>
			      <b style="font-size: 15px;" id="sumPrice">${sumMoney}</b> <b style="font-size: 15px;">元</b>
			   </li>
			   <li><label style="font-weight: bold; font-size: 15px;">编号:</label><b style="font-size: 15px;" id="operatNo">${operatNo}</b></li>
		</ul>
	</div>
	<table class="table_ru">
		<tr style="background: #DCDCDC;">
			<td>序号</td>
			<td>名称</td>
			<td>型号</td>
			<td>单价</td>
			<td>数量</td>
			<td>单位</td>
			<td>金额</td>
		</tr>
		<c:forEach items="${goodsList}" var="goods" varStatus="in">
			<tr>
				<td>${in.count}</td>
				<td>${goods.goodsName}</td>
				<td>${goods.specModel}</td>
				<td>${goods.formatPrice}</td>
				<td>${goods.goodsNum}</td>
				<td>${goods.spec}</td>
				<td>${goods.formatMoney}</td>
			</tr>
		</c:forEach>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
	</table>
</body>
</html>