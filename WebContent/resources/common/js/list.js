var currentStep = 0;
var max_line_num = 0; //当前最大行数
var max_cows_num = 0; //当前最大列数 
var lineNum = 0; //初始化行数
var colNum = 0; //初始化列数
var checkNum = 0; //当前选中行
var checkNumArr = new Array(); //多选行行数
var oneCheck = 0; //shift/ 第一次点击行数
var oneCheck2 = 0; //ctrl 第一次点击行数
var addType = "";
var lineMove = false;
var beforeCheckNum = "";
//表格功能控制属性
var addLine = true; //添加功能
var allDel = true; //批量删除功能
var shiftBtn = true; //shift多选功能
var ctrlBtn = true; //ctrl 多选功能
var stats = true;
var statstr = "升";
var searchStat = true;

function createList() {
	$("#popUp").css("display", "none"); //隐藏弹窗
	$("#fade").css("display", "none");
	var rowStr = prompt("请输入行列数 例：5行6列则输入 5,6", "5,6"); //具有交互性质的对话框
	var rowArr = rowStr.replace(" ", "").split(',');
	lineNum = parseInt(rowArr[0]);
	colNum = parseInt(rowArr[1]);
	if(isNaN(lineNum) || isNaN(colNum)) {
		alert("请正确输入行列数据");
	} else if(lineNum > 100 || colNum > 100) {
		alert("行列不允许超过100");
	} else {
		max_line_num = lineNum; //最大行数
		max_cows_num = colNum;
		var listHtml = "<table class='' border='1' id='' name=''>";
		listHtml += "<tbody>";
		listHtml += "<tr id='tr0' onclick='clickTr(this,event)'>";
		listHtml += "<th id='td0-0'>序列</th>";
		for(i = 1; i <= colNum; i++) {
			listHtml += "<th id='td0-" + i + "'>列头" + i + "</th>";
		}
		var operation = max_cows_num + 1;
		listHtml += "<th id='td0-" + operation + "'>操作</th></tr>";
		for(i = 1; i <= lineNum; i++) {
			listHtml += "<tr id='tr" + i + "' onclick='clickTr(this,event)'>";
			listHtml += "<td id='td" + i + "-0'>" + i + "</td>";
			for(j = 1; j <= colNum; j++) {
				listHtml += "<td id='td" + i + "-" + j + "'></td>";
			}
			listHtml += "<td id='td" + i + "-" + operation + "'>";
			listHtml += "<input type='button' value='查看' onclick='view(" + i + ");'/>";
			listHtml += "<input type='button' value='修改' onclick='modify(" + i + ");'/>";
			listHtml += "<input type='button' value='删除' onclick='deleteLine(" + i + ");'/>";
			listHtml += "</td></tr>";
		}
		listHtml += "</tbody></table>";
		$("#listDiv").html(listHtml);
	}
	$("#operationDiv").css("display", "block");
	trMove();
	$("table tbody tr th:gt(0)").each(function() {
		$(this).click(function() {
			var operation = max_cows_num + 1;
			for(i = 0; i <= max_line_num; i++) {
				$("table tbody tr #td" + i + "-" + operation + "").hide();
			}

		})
	});
	$("table tbody tr th:eq(0)").click(function() {
		if(stats) {
			var operation = max_cows_num + 1;
			for(i = 0; i <= max_line_num; i++) {
				$("table tbody tr #td" + i + "-" + operation + "").show();
			}
		} else {
			var operation = max_cows_num + 1;
			for(i = 0; i <= max_line_num; i++) {
				$("table tbody tr #td" + i + "-" + operation + "").hide();
			}
		}
	});
}
$(document).ready(function() {
	$("#modifyBtn").click(function() {
		var modifyListHtml = "<th id='td0-0'><input type='text' value='序列' readonly='readonly'/></th>";
		for(i = 1; i <= max_cows_num; i++) {
			var cowtext = $("table tbody #td0-" + i).text();
			modifyListHtml += "<th id='td0-" + i + "'><input type='text' value='" + cowtext + "'/></th>";
		}
		var operation = max_cows_num + 1;
		modifyListHtml += "<th id='td0-" + operation + "'><input type='text' value='操作' readonly='readonly'/></th>";
		var modifyBtnHtml = "<input type='button' value='确认' id='affirmBtn' onclick='modify_affirmBtn()'/>";
		modifyBtnHtml += "<input type='button' value='取消' id='cancelBtn' onclick='returnTable()'/>";
		$("#popUp").css("display", "block"); //显示弹窗
		$("#fade").css("display", "block");
		$("#popUp").html(modifyListHtml).append(modifyBtnHtml); //加载弹窗
	});
	$("#addBtn").click(function() {
		var max_line_ = max_line_num + 1;
		var addListHtml = "<td id='td" + max_line_num + "-0'><input type='text' value='" + max_line_ + "' readonly='readonly'/></td>";
		for(i = 1; i <= max_cows_num; i++) {
			var cowtext = $("table tbody #td0-" + i).text();
			addListHtml += "<td id='td" + max_line_num + "-" + i + "'><input type='text' value='' placeholder='" + cowtext + "'/></td>";
		}
		var addBtnHtml = "<input type='button' value='确认' id='affirmBtn' onclick='add_affirmBtn()'/>";
		addBtnHtml += "<input type='button' value='取消' id='cancelBtn' onclick='returnTable()'/>";
		$("#popUp").css("display", "block"); //显示弹窗
		$("#fade").css("display", "block");
		$("#popUp").html(addListHtml).append(addBtnHtml); //加载弹窗
	});
	$("#deletesBtn").click(function() { //全部删除（内容）
		if(confirm("确定要删除所有行吗？")) {
			$("table tr:gt(0)").each(
				function(index) {
					$(this).remove();
				}
			)
			max_line_num = 0; //最大行数减一
			$("#popUp").css("display", "none");
			$("#fade").css("display", "none");
		}
	});
	$("#deleteBtn").click(function() { //批量删除（内容）
		if(confirm("确定要删除所有选中行吗？")) {
			$("table tbody tr").each(function() {
				var st = $(this).attr("style");
				if(st == "background-color: yellow;") {
					$(this).remove();
					max_line_num = max_line_num - 1;
				}
			});
			sorting();
			$("#popUp").css("display", "none");
			$("#fade").css("display", "none");
		}
	});
	$("#insertBtn").click(function() { //插入行数
		if(checkNum == null || checkNum == 0) {
			alert("请点击一行");
		} else {
			//先增加一行
			max_line_num = max_line_num + 1;
			var addListHtml = "<tr id='tr" + max_line_num + "' onclick='clickTr(this,event)'>";
			addListHtml += "<td id='td" + max_line_num + "-0'>" + max_line_num + "</td>";
			for(j = 1; j <= colNum; j++) {
				addListHtml += "<td id='td" + max_line_num + "-" + j + "'></td>";
			}
			var operation = max_cows_num + 1;
			addListHtml += "<td id='td" + max_line_num + "-" + operation + "'>";
			addListHtml += "<input type='button' value='查看' onclick='view(" + max_line_num + ");'/>";
			addListHtml += "<input type='button' value='修改' onclick='modify(" + max_line_num + ");'/>";
			addListHtml += "<input type='button' value='删除' onclick='deleteLine(" + max_line_num + ");'/>";
			addListHtml += "</td></tr>";
			$("table tbody").append(addListHtml);
			//下移
			var upStep = max_line_num - 1; //倒数第二行
			var dowmStep = max_line_num; //最后一行
			for(i = checkNum; i < max_line_num; i++) {
				for(j = 1; j <= colNum; j++) {
					var thisLine = $("#td" + upStep + "-" + j).text(); //倒数第二行的数据
					$("#td" + dowmStep + "-" + j).text(thisLine);
				}
				upStep = upStep - 1;
				dowmStep = dowmStep - 1;
			}
			for(j = 1; j <= colNum; j++) {
				$("#td" + checkNum + "-" + j).text("");
			}
		}
		sorting();
	});
	$("#up").click(function() { //上移
		if(checkNum == null || checkNum == 0) {
			alert("请点击一行");
			return false;
		}
		if(checkNum <= 1) {
			alert('已经是最顶上了!');
			return false;
		}
		var upStep = checkNum - 1;
		for(j = 1; j <= colNum; j++) {
			var thisLine = $("#td" + checkNum + "-" + j).text();
			var upLint = $("#td" + upStep + "-" + j).text();
			$("#td" + upStep + "-" + j).text(thisLine);
			$("#td" + checkNum + "-" + j).text(upLint);
		}
		$("table tbody tr").each(function() {
			$(this).css("background-color", "#ffffff");
		});
		$('#tr' + upStep).css("background-color", "yellow");
		checkNum = upStep;
		event.stopPropagation(); //阻止事件冒泡
		$("#popUp").css("display", "none");
		$("#fade").css("display", "none");
	});
	$("#down").click(function() { //下移
		if(checkNum == null || checkNum == 0) {
			alert("请点击一行");
			return false;
		}
		if(checkNum >= max_line_num) {
			alert('已经是最后一项了!');
			return false;
		}
		var upStep = checkNum % 10 + 1;
		for(j = 1; j <= colNum; j++) {
			var thisLine = $("#td" + checkNum + "-" + j).text();
			var upLint = $("#td" + upStep + "-" + j).text();
			$("#td" + upStep + "-" + j).text(thisLine);
			$("#td" + checkNum + "-" + j).text(upLint);
		}
		$("table tbody tr").each(function() {
			$(this).css("background-color", "#ffffff");
		});
		$('#tr' + upStep).css("background-color", "yellow");
		checkNum = upStep;
		event.stopPropagation(); //阻止事件冒泡
		$("#popUp").css("display", "none");
		$("#fade").css("display", "none");
	});
	//查询按钮
	$("#searchBtn").click(function() {
		if(searchStat) {
			$("#searchDiv").css("display", "block");
			searchStat = false;
		} else {
			$("#searchDiv").css("display", "none");
			searchStat = true;
		}
	});
	//查询
	var search = {
		searchstr: function() {
			search.clearstr;
			var searchText = $('#txtSearch').val();
			var regExp = new RegExp(searchText, 'g');
			var indexId = 0;
			if(searchText != "") {
				$('table tbody tr td').each(function() {
					if(indexId == 0) {} else if(indexId == max_cows_num + 1) {
						indexId = -1;
					} else {
						var newHTML = $(this).html().replace(regExp, '<span class="focus">' + searchText + '</span>');
						$(this).html(newHTML);
					}
					indexId++;
				});
			}
		},
		clearstr: function() {
			$('table tbody tr td').each(function() {
				$(this).find('.focus').each(function() {
					$(this).replaceWith($(this).html());
				});
			});
		}
	}
	$('#btnSearch').bind("click", search.searchstr);
	$('#btn1Search').bind("click", search.clearstr);
});
//点击选中事件
function clickTr(obj, e) {
	//确定shift 和 ctrl 是否被按住
	var bSHIFT = e.shiftKey;
	var bCTRL = e.ctrlKey;
	var num = $.trim($(obj).find("td:first-child").parent().attr("id")).charAt(2);
	//都没按时
	if(!bCTRL && !bSHIFT) {
		oneCheck = 0; //shift 第一次点击行数
		oneCheck2 = 0;
		$('table tr').each(function() {
			$(this).css("background-color", "#ffffff");
		});
		$('#tr' + num).css("background-color", "yellow");
		checkNum = num;
	}
	//先判读是否按住shift
	if(bSHIFT && shiftBtn && !bCTRL) {
		if(checkNum <= num) {
			for(i = checkNum; i <= num; i++) {
				$('#tr' + i).css("background-color", "yellow");
			}
		} else {
			for(i = num; i <= checkNum; i++) {
				$('#tr' + i).css("background-color", "yellow");
			}
		}

	}
	if(bCTRL && ctrlBtn && !bSHIFT) {
		$('#tr' + num).css("background-color", "yellow");
	}
}
//点击查看按钮
function view(viewindex) {
	var viewListHtml = "";
	$("#tr" + viewindex).each(function() {
		$(this).find("td").each(function(index) {
			var view_val = $(this).text();
			if(index > max_cows_num) {

			} else {
				viewListHtml += "<td><input type='text' value='" + view_val + "' readonly='readonly'/></td>";
			}
		})
	});
	var viewBtnHtml = "<div>";
	viewBtnHtml += "<input type='button' value='返回' onclick='returnTable()'/>";
	viewBtnHtml += "<input type='button' value='修改' onclick='modify(" + viewindex + ");'/>";
	viewBtnHtml += "<input type='button' value='删除' onclick='deleteLine(" + viewindex + ");'/></div>";
	$("#popUp").css("display", "block"); //显示弹窗
	$("#fade").css("display", "block");
	$("#popUp").html(viewListHtml).append(viewBtnHtml); //加载弹窗
}
//点击删除按钮
function deleteLine(deleteindex) {
	if(confirm("确定要删除该行吗？")) {
		$("table #tr" + deleteindex).remove(); //该行删除
		//重新排序
		sorting();
		max_line_num = max_line_num - 1; //最大行数减一
		$("#popUp").css("display", "none");
		$("#fade").css("display", "none");
	}
}
//重新排序
function sorting() {
	$("table tr:gt(0)").each(
		function(index) {
			var indexs = index + 1;
			var id = "tr" + indexs;
			var views = "view(" + indexs + ")";
			var modifys = "modify(" + indexs + ")";
			var deleteLines = "deleteLine(" + indexs + ")";
			$(this).find(":first-child").text(indexs);
			$(this).attr("id", id);
			$(this).find("td:last-child").find("input:eq(0)").attr("onclick", views);
			$(this).find("td:last-child").find("input:eq(1)").attr("onclick", modifys);
			$(this).find("td:last-child").find("input:eq(2)").attr("onclick", deleteLines);
		}
	)
}
//点击修改按钮
function modify(modifyindex) {
	var modifyListHtml = "<td><input type='text' value='" + modifyindex + "' readonly='readonly'/></td>";
	$("#tr" + modifyindex).each(function() {
		$(this).find("td").each(function(index) {
			var modify_val = $(this).text();
			if(index == 0 || index > max_cows_num) {

			} else {
				modifyListHtml += "<td><input type='text' value='" + modify_val + "'/></td>";
			}
		})
	});
	var modifyBtnHtml = "<div>";
	modifyBtnHtml += "<input type='button' value='返回' onclick='returnTable()'/>";
	modifyBtnHtml += "<input type='button' value='保存' onclick='save(" + modifyindex + ");'/>";
	modifyBtnHtml += "<input type='button' value='删除' onclick='deleteLine(" + modifyindex + ");'/></div>";
	$("#popUp").css("display", "block"); //显示弹窗
	$("#fade").css("display", "block");
	$("#popUp").html(modifyListHtml).append(modifyBtnHtml); //加载弹窗
}
//保存按钮
function save(saveindex) {
	var saveListHtml = "<td id='td" + saveindex + "-0'>" + saveindex + "</td>";
	$("#popUp td").each(function(index) {
		$(this).find("input").each(function() {
			var save_val = $(this).val();
			if(index == 0 || index > max_cows_num) {

			} else {
				saveListHtml += "<td id='td" + saveindex + "-" + index + "'>" + save_val + "</td>";
			}
		})
	});
	var operation = max_cows_num + 1;
	saveListHtml += "<td id='td" + saveindex + "-" + operation + "'>";
	saveListHtml += "<input type='button' value='查看' onclick='view(" + saveindex + ");'/>";
	saveListHtml += "<input type='button' value='修改' onclick='modify(" + saveindex + ");'/>";
	saveListHtml += "<input type='button' value='删除' onclick='deleteLine(" + saveindex + ");'/>";
	saveListHtml += "</td>";
	$("table tbody #tr" + saveindex).html(saveListHtml); //完成头部修改工作
	$("#popUp").css("display", "none"); //隐藏弹窗
	$("#fade").css("display", "none");
}
//点击返回按钮（关闭弹窗）
function returnTable() {
	$("#popUp").css("display", "none"); //隐藏弹窗
	$("#fade").css("display", "none");
}
//修改头部的确认按钮
function modify_affirmBtn() {
	var modifyListHtml = "<th id='td0-0'>序列</th>";
	$("#popUp th").each(function(index) {
		$(this).find("input").each(function() {
			var modify_val = $(this).val();
			if(index == 0 || index > max_cows_num) {

			} else {
				modifyListHtml += "<th id='td0-" + index + "'>" + modify_val + "</th>";
			}
		})
	});
	var operation = max_cows_num + 1;
	modifyListHtml += "<th id='td0-" + operation + "'>操作</th></tr>";
	$("table tbody #tr0").html(modifyListHtml); //完成头部修改工作
	$("#popUp").css("display", "none"); //隐藏弹窗
	$("#fade").css("display", "none");
	$(function() {
		$("table tr th").each(function(i) {
			if(i > max_cows_num) {} else {
				$(this).bind("click", function() {
					stats = stats ? false : true;
					statstr = stats ? "降" : "升";
					$("#tip").show().html("当前按 <b>" +
						$(this).html() + statstr + "序</b> 排列");
					$("table").sortTable({
						onCol: i + 1,
						keepRelationships: true,
						sortDesc: stats,
					});
				});
			}
		});
	});
}
//添加新行的确认按钮
function add_affirmBtn() {
	max_line_num = max_line_num + 1;
	var addListHtml = "<tr id='tr" + max_line_num + "' onclick='clickTr(this,event)'>";
	$("#popUp td").each(function(index) {
		$(this).find("input").each(function() {
			var add_val = $(this).val();
			if(index > max_cows_num) {

			} else {
				addListHtml += "<td id='td" + max_line_num + "-" + index + "'>" + add_val + "</td>";
			}
		})
	});
	var operation = max_cows_num + 1;
	addListHtml += "<td id='td" + max_line_num + "-" + operation + "'>";
	addListHtml += "<input type='button' value='查看' onclick='view(" + max_line_num + ");'/>";
	addListHtml += "<input type='button' value='修改' onclick='modify(" + max_line_num + ");'/>";
	addListHtml += "<input type='button' value='删除' onclick='deleteLine(" + max_line_num + ");'/>";
	addListHtml += "</td></tr>";
	$("table tbody").append(addListHtml);
	$("#popUp").css("display", "none"); //隐藏弹窗
	$("#fade").css("display", "none");
}
//动态调整列宽
function trMove() {
	//鼠标移动
	$("th").bind("mousemove",
		function(event) {
			var th = $(this);
			//不给第一列和最后一列添加效果
			//if (th.prevAll().length <= 1 || th.nextAll().length < 1) {
			//	return;
			//}
			var left = th.offset().left;
			//距离表头边框线左右4像素才触发效果
			if(event.clientX - left < 4 || (th.width() - (event.clientX - left)) < 4) {
				th.css({
					'cursor': 'e-resize'
				});
			} else {
				th.css({
					'cursor': 'default'
				});
			}
		});
	//鼠标移动，竖线移动
	$("body").bind("mousemove", function(event) {
		if(lineMove == true) {
			$("#listDiv").css({
				"left": event.clientX
			}).show();
		}
	});
	//鼠标按下
	$("th").bind("mousedown",
		function(event) {
			var th = $(this);
			//与mousemove函数中同样的判断
			if(th.prevAll().length < 1 | th.nextAll().length < 1) {
				return;
			}
			var pos = th.offset();
			if(event.clientX - pos.left < 4 || (th.width() - (event.clientX - pos.left)) < 4) {
				var height = th.parent().parent().height();
				var top = pos.top;
				$("#listDiv").css({
					"height": height,
					"top": top,
					"left": event.clientX,
					"display": ""
				});
				//全局变量，代表当前是否处于调整列宽状态
				lineMove = true;
				//总是取前一个TH对象
				if(event.clientX - pos.left < th.width() / 2) {
					currTh = th.prev();
				} else {
					currTh = th;
				}
			}
		});
	//鼠标弹起
	$("body").bind("mouseup",
		function(event) {
			if(lineMove == true) {
				lineMove = false;
				var pos = currTh.offset();
				var index = currTh.prevAll().length;
				currTh.width(event.clientX - pos.left);
				currTh.parent().parent().find("tr").each(function() {
					$(this).children().eq(index).width(event.clientX - pos.left);
				});
			}
		});
	$("th").bind("mouseup",
		function(event) {
			if(lineMove == true) {
				lineMove = false;
				var pos = currTh.offset();
				var index = currTh.prevAll().length;
				currTh.width(event.clientX - pos.left);
				currTh.parent().parent().find("tr").each(function() {
					$(this).children().eq(index).width(event.clientX - pos.left);
				});
			}
		});
	$("body").bind("selectstart", function() {
		return !lineMove;
	});
	//排序
	$(function() {
		$("table tr th").each(function(i) {
			if(i > max_cows_num) {} else {
				$(this).bind("click", function() {
					stats = stats ? false : true;
					statstr = stats ? "降" : "升";
					$("#tip").show().html("当前按 <b>" +
						$(this).html() + statstr + "序</b> 排列");
					$("table").sortTable({
						onCol: i + 1,
						keepRelationships: true,
						sortDesc: stats,
					});
				});
			}
		});
	});
}