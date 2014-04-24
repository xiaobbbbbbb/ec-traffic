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
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>
				<ul class="breadcrumb">
					<li><a href="#">基本信息</a> <span class="divider">/</span></li>
					<li class="active">任务管理</li>
				</ul>
				<div class="well">
					<table id="list" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th width="40px">序号</th>
								<th width="100px">任务编号</th>
								<th width="130px">接口名称</th>
								<th width="130px">查询数量</th>
								<th width="100px">任务状态</th>
								<th width="130px">查询时间</th>
								<th width="130px">响应数量</th>
								<th width="130px">响应时间</th>
								<th width="100px">操作</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td class="td_center">${sn.count}</td>
									<td class="td_center">${dto.taskNo}</td>
									<td class="td_center">
										<c:forEach items="${listInterfaces}" var="list" varStatus="st">
												<c:if test="${list.id == dto.interfaceId}">${list.name}</c:if>
												
										</c:forEach>
									</td>
									<td class="td_center">${dto.requestNums}</td>
									<td class="td_center">${dto.status } </td>
									<td class="td_center"><fmt:formatDate value="${dto.searchStime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
									<td class="td_center">${dto.responseNums}</td>
									<td class="td_center"><fmt:formatDate value="${dto.searchEtime}"
											pattern="yyyy-MM-dd HH:mm" /></td>
							
									<td class="td_center"><a href="${ctx}/task/updateUI?id=${dto.id}"><i class="icon-pencil"></i></a>
										<a onfocus="this.blur()" role="button" href="javascript:void(0)"
										onclick="$.ec.deleteData({url : '${ctx}/task/delete',param : {'ids':${dto.id}}});">
											<i class="icon-remove"></i>
									</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/task/list" id="pageForm">
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