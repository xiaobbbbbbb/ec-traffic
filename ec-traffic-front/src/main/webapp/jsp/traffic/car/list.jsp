<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>欢迎使用车信违章运营系统</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/media/css/admin/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/media/font-awesome/css/font-awesome.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
	    $("#startDate,#endDate").datepickerStyle();
	});
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">基本信息</a> <span class="divider">/</span></li>
					<li class="active">车辆信息</li>
				</ul>
				<form class="well form-inline" action="${ctx}/car/queryList" style="text-align: center;">
					
					<span>
						<select id="customerId" name="customerId">
								<option value=-1>--公司--</option>
									<c:forEach items="${listCustomer}" var="list" varStatus="st">
										<option value="${list.id }"
											<c:if test="${list.id == dto.customerId}">selected="selected"</c:if>>
											${list.name}</option>
									</c:forEach>
						</select>
					</span>
					<span >
						车牌号：<input type="text" id="carNo" name="carNo" value="${dto.carNo }"/>
						车架号：<input type="text" id="carFrameNo" name="carFrameNo" value="${dto.carFrameNo }"/><br/>
						证书号：<input type="text" id="carRegistNo" name="carRegistNo" value="${dto.carRegistNo }"/>
 						引擎号：<input type="text" id="carEngineNo" name="carEngineNo" value="${dto.carEngineNo }"/>
					</span>
					选择时间段：<input type="text" id="startDate" name="startDate" value="${dto.startDate }"
						class="input-small" style="cursor: pointer;" readonly="readonly" placeholder="开始时间"> <input
						type="text"  id="endDate" name="endDate" class="input-small" value="${dto.endDate }"
						style="cursor: pointer;" readonly="readonly" placeholder="结束时间">
					<button type="submit" class="btn">搜索</button>
				</form>
				<div class="btn-toolbar">
					<button class="btn btn-primary" onfocus="this.blur()"
						onclick="href('${ctx}/car/addUI');">添加</button>
					<div class="btn-group"></div>
				</div>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th width="100px">车牌号</th>
								<th width="130px">姓名</th>
								<th width="130px">所属公司</th>
								<th width="130px">车架号</th>
								<th width="130px">证书号</th>
								<th width="130px">引擎号</th>
								<th width="130px">有效</th>
								<th width="100px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td class="td_center">${sn.count}</td>
									<td class="td_center">${dto.carNo}</td>
									<td class="td_center">${dto.userName}</td>
									<td class="td_center">${dto.name}</td>
									<td class="td_center">${dto.carFrameNo}</td>
									<td class="td_center">${dto.carRegistNo}</td>
									<td class="td_center">${dto.carEngineNo}</td>
									<td class="td_center"><c:choose>
											<c:when test="${dto.isExpired==1}">有效</c:when>
											<c:otherwise>
												<span class="red">无效</span>
											</c:otherwise>
										</c:choose>
									</td>
							
									<td class="td_center"><a href="${ctx}/car/updateUI?id=${dto.id}"><i class="icon-pencil"></i></a>
										<a onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/car/delete',param : {'ids':${dto.id}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/car/list" id="pageForm">
					<%@ include file="../../common/pager.jsp"%>
					</form>
				</div>
			</div>
		</div>
	</div>

	<div class="modal small hide fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
			<h3 id="myModalLabel">系统提示</h3>
		</div>
		<div class="modal-body">
			<p class="error-text">
				<i class="icon-warning-sign modal-icon"></i>是否确认删除！
			</p>
		</div>
		<div class="modal-footer">
			<button class="btn" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn btn-danger" data-dismiss="modal">确定</button>
		</div>
	</div>
</body>
</html>