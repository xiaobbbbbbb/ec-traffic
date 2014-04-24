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
		var customerId='${dto.id}';
		$("#startTime,#endTime").datepickerStyle();
		
		//默认状态
		$("input[name='isExpired'][value='${dto.isExpired}']").attr("checked","checked");
		$("input[name='isAllowchange'][value='${dto.isAllowchange}']").attr("checked","checked");
		//公司不可修改
		if(customerId!=null&&customerId!=""){
			$("#customerId").attr('disabled','disabled');
		}
	});

	function submitForm() {
		var carNo=$("#carNo").val();
		//数据校验
		if($("#carNo").val() == ""){
			alert('请输入车牌号');
			return false;
			} 
		if($("#customerId").val()== ""){
			alert('请选择公司');
			return false;
		}
		if($("#carFrameNo").val() == ""){
			alert('请输入车架号');
			return false;
			}
		if($("#carRegistNo").val() == ""){
			alert('请输入注册号');
			return false;
			}
		if($("#carEngineNo").val() == ""){
			alert('请输入引擎号');
			return false;
		}
		var url = "${ctx}/car/${dto.id == null ? 'add' : 'update'}";
	
		$.ec.ajaxSubmitForm({
			url : url,
			callback : frameHref()
		});

	}

	function frameHref() {
		window.location.href = "${ctx}/car/list";
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
					<li><a href="${ctx}">车辆管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>

				<div class="well">
					<form id="form" class="form-horizontal">
						<input name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>姓名：</label>
							<div class="controls">
								<input id="userName" name="userName" validateType="notEmpty"
									tip="请输入公司代码!" type="text" value="${dto.userName}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>车牌号：</label>
							<div class="controls">
								<input id="carNo" name="carNo" validateType="notEmpty"
									tip="请输入查勘员名称!" type="text" value="${dto.carNo}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>所属公司：</label>
							<div class="controls">

								<input  name="customerId" type="hidden"
									validateType="notEmpty" tip="请选择公司!" value="${dto.customerId}"
									maxlength="100" /> 
								<select id="customerId" name="customerId">
									<option value="">请选择</option>
									<c:forEach items="${listCustomer}" var="list" varStatus="st">
										<option value="${list.id }"
											<c:if test="${list.id == dto.customerId}">selected="selected"</c:if>>
											${list.name}</option>
									</c:forEach>
								</select>

							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>车架号：</label>
							<div class="controls">
								<input id="carFrameNo" name="carFrameNo" type="text" value="${dto.carFrameNo}"
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>证书号：</label>
							<div class="controls">
								<input id="carRegistNo" name="carRegistNo" type="text" value="${dto.carRegistNo}"
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>引擎号：</label>
							<div class="controls">
								<input id="carEngineNo" name="carEngineNo" type="text" value="${dto.carEngineNo}"
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否有效：</label>
							<div class="controls">
								<label class="radio inline"> <input type="radio"
									name="isExpired" id="optionsRadios1" value=1
									checked> 是
								</label> <label class="radio inline"> <input type="radio"
									name="isExpired" id="optionsRadios2" value=0
									>
									否
								</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否允许变更：</label>
							<div class="controls">
								<label class="radio inline"> <input type="radio"
									name="isAllowchange" id="optionsRadios3" value=1
									>
									是
								</label> <label class="radio inline"> <input type="radio"
									name="isAllowchange" id="optionsRadios4" value=0
									checked> 否
								</label>
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