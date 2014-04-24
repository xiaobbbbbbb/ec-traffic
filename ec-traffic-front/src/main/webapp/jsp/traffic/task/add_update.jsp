<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/base.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>欢迎使用车信违章运营系统</title>
<meta content="IE=edge,chrome=1" http-equiv="X-UA-Compatible">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="${ctx}/media/css/admin/bootstrap.css" rel="stylesheet" />
<link href="${ctx}/media/css/admin/theme.css" type="text/css"
	rel="stylesheet" />
<link href="${ctx}/media/font-awesome/css/font-awesome.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript"
	src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/core/jquery.ec-base.js"></script>

<link
	href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<script
	src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"
	type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
		$("#startTime,#endTime").datepickerStyle();
		
	});

	function submitForm() {
		var id=$("#id").val();
		var taskNo=$("#taskNo").val();
		var interfaceId=$("#interfaceId").val();
		if(taskNo==""){alert("任务编号为空"); return false;}
		if(interfaceId==""){alert("接口为空"); return false;}

		var checkUrl = "${ctx}/task/check?taskNo="+taskNo;
		if(id==null||id==""){
			$.ajax({
				url:checkUrl,
				type:'get',
				success:function(data){
					if(data.success){
						$.ec.ajaxSubmitForm({
							url : "${ctx}/task/add",
							callback : frameHref
						});
					}
					else{
						alert(data.msg);
					}
				}
			});
		}else{
			$.ec.ajaxSubmitForm({
				url : "${ctx}/task/update",
				callback : frameHref
			});
		}

	}

	function frameHref() {
// 		window.location.href = "${ctx}/task/list";
	}
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
					<li><a href="${ctx}">任务分配</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>

				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="id" name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>任务编号：</label>
							<div class="controls">
								<input id="taskNo" name="taskNo" validateType="notEmpty"
									tip="请输入公司代码!" type="text" value="${dto.taskNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>接口：</label>
							<div class="controls">
								<select id="interfaceId" name="interfaceId" >
									<option value="">请选择</option>
									<c:forEach items="${listInterfaces}" var="list" varStatus="st">
										<option value="${list.id }"
											<c:if test="${list.id == dto.interfaceId}">selected="selected"</c:if>>
											${list.name}</option>
									</c:forEach>
								</select>

							</div>
						</div>
						
						<div class="form-actions">
							<input id="btnSubmit" class="btn btn-primary" type="button"
								onclick="submitForm();" value="保 存" />&nbsp; <input
								id="btnCancel" class="btn" type="button" value="返 回"
								onclick="history.go(-1)" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>