//查询二级分类
function queryCategoryTwo(sourceId, targetId) {
	var categoryOneText = $("#" + sourceId + " option:selected").text();
	if (categoryOneText == '--请选择--') {
		operaAlert("warning", "警告提示", "请选择一级分类");
		return;
	}
	var categoryOneVal = $("#" + sourceId + " option:selected").val();
	$.ajax({
		type : "POST",
		url : "queryCategoryByParentId",
		data : {
			"parentId" : categoryOneVal
		},
		success : function(result) {
			if (result.status != 200) {
				operaAlert("warning", "警告提示", "查询失败");
				return;
			}
			$("#" + targetId).html("");
			var resultHtml = "<option value=''>--请选择--</option>";
			var resultList = result.resultList;
			$.each(resultList, function(i, obj) {
				resultHtml += "<option value='" + obj.id + "'>" + obj.name
						+ "</option>";
			});
			$("#" + targetId).append(resultHtml);
		},
		error : function(result) {
			operaAlert("danger", "错误提示", "查询失败");
			return;
		}
	});
}

// 查询供应商信息
function querySupplier(targetId, url) {
	$.ajax({
		type : "POST",
		url : url,
		success : function(result) {
			if (result.status != 200) {
				operaAlert("warning", "警告提示", "查询失败");
				return;
			}
			$("#" + targetId).html("");
			var resultHtml = "<option value=''>--全部--</option>";
			var resultList = result.resultList;
			$.each(resultList, function(i, obj) {
				resultHtml += "<option value='" + obj.id + "'>" + obj.shortName
						+ "</option>";
			});
			$("#" + targetId).append(resultHtml);
		},
		error : function(result) {
			operaAlert("danger", "错误提示", "查询失败");
			return;
		}
	});
}

// 查询部门信息
function queryAllDepart(targetId, url) {
	$.post(url, function(result) {
		if (result.status != 200) {
			operaAlert("warning", "警告提示", "查询失败");
			return;
		}
		$("#" + targetId).html("");
		var resultHtml = "<option value=''>--请选择--</option>";
		var resultList = result.resultList;
		$.each(resultList, function(i, obj) {
			resultHtml += "<option value='" + obj.id + "'>"
					+ obj.departmentName + "</option>";
		});
		$("#" + targetId).append(resultHtml);
	});
}

// 查询全部商品(验收/预存入库  月计划/部门出库/部门调拨使用)
var reciveSupplierId = "";
function queryAllGoods(targetId, url, keywords, supplierId, installUrl,returnPage, operatNo,categoryOne,categoryTwo) {
	var goodsName = $("#" + keywords).val();
	reciveSupplierId = supplierId;
	$.ajax({
		type : "POST",
		url : url,
		data : {
			"goodsName" : goodsName,
			"supplierId" : reciveSupplierId,
			"categoryOne":categoryOne,
			"categoryTwo":categoryTwo
		},
		success : function(result) {
			if (result.status != 200) {
				operaAlert("warning", "警告提示", "查询失败");
				return;
			}
			$("#" + targetId).html("");
			var resultHtml = "";
			var resultList = result.resultList;
			$.each(resultList,function(i, obj) {
				var trId = "goods"+obj.id;
				// onclick='selectedCheckbox(\""+trId+"\");'
				resultHtml += "<tr id='"+trId+"'>";
				resultHtml += '<td style="width:50px;"><input type="checkbox" name="check" value="'
						+ obj.id
						+ '&'
						+ obj.goodsName
						+'&'
						+ 1
						+'&'
						+obj.price+'"></td>';
				resultHtml += '<td style="width:50px;">'
						+ (i + 1) + '</td>';
				resultHtml += '<td style="width:150px;">'
					+ obj.goodsNo + '</td>';
				resultHtml += '<td style="width:100px;">'
						+ obj.goodsName + '</td>';
				resultHtml += '<td style="width:100px;">'
					+ obj.specModel + '</td>';
				resultHtml += '<td style="width:80px;">'
						+ obj.price + '</td>';
				if (obj.isDecimal == 1) {
					resultHtml += "<td style='width:80px;'><input type='text' class='courseinput' id='"
							+ obj.id
							+ "' onkeyup='checkNumber("
							+ obj.id
							+ ",\"1\",\""+trId+"\");'></td>";
				} else {
					resultHtml += "<td style='width:80px;'><input type='text' class='courseinput' id='"
							+ obj.id
							+ "' onkeyup='checkNumber("
							+ obj.id
							+ ",\"2\",\""+trId+"\");'></td>";
				}
				resultHtml += '<td style="width:80px;">'
						+ obj.spec + '</td>';
				resultHtml += "</tr>";
			});
			resultHtml += "<tr><td colspan='8' align='right'><input type='bottom' class='btn2'  value='添加商品' style='text-align:center' onclick='getChecked(\""
					+ installUrl
					+ "\",\""
					+ returnPage
					+ "\",\""
					+ supplierId
					+ "\",\""
					+ operatNo
					+ "\",\""
					+null+"\");'/></td></tr>";
			$("#" + targetId).append(resultHtml);
		 },
		 error : function(result) {
			operaAlert("danger", "错误提示", "查询失败");
			return;
		}
	});
}

// 获取选中的复选框
function getChecked(installUrl, returnPage, supplierId, operatNo,departId) {
	var checkedStr = "";
	var stockType = $("#stockType").val();
	if(stockType == "" || stockType == null || stockType == undefined){
		stockType = "";
	}
	// js获取
	/*
	 * var checkedStr = document.getElementsByName("check"); for(var i = 0;i<checkedStr.length;i++){
	 * if(checkedStr[i].checked){ alert(checkedStr[i].value); } }
	 */
	// jquery 获取
	var num = 0;
	var isTrue = true;
    $('input[name="check"]:checked').each(function() {
		var goodsObj = $(this).val().split("&");
		var goodsId = goodsObj[0];
		var goodsName = goodsObj[1];
		var goodsNum = $("#" + goodsId).val();
		if(goodsNum == null || goodsNum == "" || goodsNum == undefined){
			var stockId = goodsObj[2];
			goodsNum = $("#" + stockId).val();
		}
		if (goodsNum == null || goodsNum == "" || goodsNum == undefined) {
			operaAlert("warning", "警告提示", "请填写商品【" + goodsName + "】数量");
			isTrue =  false;
		} else {
			var goodsPrice = goodsObj[3];
			if(goodsPrice != null || goodsPrice != "" || goodsPrice != undefined){
				goodsPrice = goodsPrice.replace(",","")
				checkedStr += goodsId + "," + goodsNum + ","+goodsPrice+";";
			}else{
				checkedStr += goodsId + "," + goodsNum + ";";
			}
			
		}
		num++;
	});
	if (num == 0) {
		operaAlert("warning", "警告提示", "请选择商品!");
		return;
	}
	if(!isTrue){
		return;
	}
	/*$("#goodsName").val("");
	$("#categoryOne").val("");
	$("#categoryTwo").val("");*/
	$(".tip").fadeOut(200);
	window.parent.location.href = installUrl + "?returnPage=" + returnPage
			+ "&goodsStr=" + checkedStr + "&supplierId=" + supplierId
			+ "&operatNo=" + operatNo+"&departId="+departId+"&stockType="+stockType;
}

function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	//获取时分秒
	/*var currentdate = year + seperator1 + month + seperator1 + strDate + " "
			+ date.getHours() + seperator2 + date.getMinutes() + seperator2
			+ date.getSeconds();*/  
	var currentdate = year + seperator1 + month + seperator1 + strDate;
	return currentdate;
}

//查询所有 的角色
function queryAllRole(targetId, url) {
	$.post(url, function(result) {
		if (result == null) {
			operaAlert("warning", "警告提示", "查询失败");
			return;
		}
		$("#" + targetId).html("");
		var resultHtml = "<option value=''>--请选择--</option>";
		$.each(result, function(i, obj) {
			resultHtml += "<option value='" + obj.id + "'>"
					+ obj.name + "</option>";
		});
		$("#" + targetId).append(resultHtml);
	});
}




//根据类型查询库存商品信息(领用/调拨使用)
function queryStockList(targetId, url, keywords, categoryOne,categoryTwo, installUrl,returnPage, operatNo,type,departId) {
	var goodsName = $("#" + keywords).val();
	$.ajax({
		type : "POST",
		url : url,
		data : {
			"goodsName":goodsName,
			"categoryOne":categoryOne,
			"categoryTwo":categoryTwo,
			"type":type,
		},
		success : function(result) {
			if (result.status != 200) {
				operaAlert("warning", "警告提示", "查询失败1111");
				return;
			}
			$("#" + targetId).html("");
			var resultHtml = "";
			var resultList = result.resultList;
			$.each(resultList,function(i, obj) {
				var trId = "stock"+obj.id;
				// onclick='selectedCheckbox(\""+trId+"\");'
				resultHtml += "<tr id='"+trId+"'>";
				resultHtml += '<td style="width:50px;"><input type="checkbox" name="check" value="'
						+ obj.goodsId
						+ '&'
						+ obj.goodsName
						+ '&'
						+obj.id
						+'&'
						+obj.goodsPrice+'"></td>';
				resultHtml += '<td style="width:50px;">'
						+ (i + 1) + '</td>';
				resultHtml += '<td style="width:150px;">'
					+ obj.goodsNo + '</td>';
				resultHtml += '<td style="width:100px;">'
						+ obj.goodsName + '</td>';
				resultHtml += '<td style="width:100px;">'
					+ obj.specModel + '</td>';
				resultHtml += '<td style="width:80px;">'
						+ obj.goodsPrice + '</td>';
				resultHtml += '<td style="width:80px;">'
					+ obj.stockNum + '</td>';
				if (obj.isDecimal == 1) {
					resultHtml += "<td style='width:80px;'><input type='text' class='courseinput' id='"
			 				+ obj.id
							+ "' onkeyup='checkNumber("
							+ obj.id
							+ ",\"1\",\""+trId+"\");'  onBlur='checkGoodsNum(\""+obj.id+"\",\""+obj.stockNum+"\",\""+obj.goodsName+"\",\""+obj.spec+"\",\""+trId+"\");'></td>";
				} else {
					resultHtml += "<td style='width:80px;'><input type='text' class='courseinput' id='"
							+ obj.id
							+ "' onkeyup='checkNumber("
							+ obj.id
							+ ",\"2\",\""+trId+"\");'    onBlur='checkGoodsNum(\""+obj.id+"\",\""+obj.stockNum+"\",\""+obj.goodsName+"\",\""+obj.spec+"\",\""+trId+"\");'></td>";
				}
				resultHtml += '<td style="width:80px;">'
						+ obj.spec + '</td>';
				resultHtml += "</tr>";
			});
			resultHtml += "<tr><td colspan='9' align='right'><input type='bottom' class='btn2'  value='确认添加' style='text-align:center'onclick='getChecked(\""
					+ installUrl
					+ "\",\""
					+ returnPage
					+ "\",\""
					+ null
					+ "\",\""
					+ operatNo
					+ "\",\""
					+departId+"\");'/></td></tr>";
			$("#" + targetId).append(resultHtml);
		 },
		 error : function(result) {
			operaAlert("danger", "错误提示", "查询失败");
			return;
		}
	});
}


/*//点击tr选中复选框
function selectedCheckbox(id){
	if($("#"+id).children().first().children().attr("checked")){
		$("#"+id).children().first().children().attr("checked",false);
	}else{
		$("#"+id).children().first().children().attr("checked",true);
	}
}*/

function checkNumber(id, type,checkdId) {
	var numStr = $("#" + id).val();
	if (type == "1") {
		numStr = numStr.replace(/[^\d.]/g, '');
	} else if(type == "2"){
		numStr = numStr.replace(/[^\d]/g, '');
	}
	if(numStr != null && numStr != "" && numStr != undefined){
		$("#" + id).val(numStr);
		$("#"+checkdId).children().first().children().attr("checked",true);
	}else{
		$("#"+checkdId).children().first().children().attr("checked",false);
	}
}

//检测输入数量是否大于库存数量
var checkGoodsNum = function(stockId,stockNum,stockGoodsName,spec,checkdId){
	var num = $("#"+stockId).val();
    if(num == null || num == undefined || num == ""){
    	return;
    }
	if(parseInt(num)>parseInt(stockNum)){
		operaAlert("warning", "警告提示", "商品[" + stockGoodsName + "]您填写的数量大于库存数"+stockNum);
		$("#chur").click(function() {
			$("#"+stockId).val("");
			$("#"+checkdId).children().first().children().attr("checked",false);
		});
	}
}
