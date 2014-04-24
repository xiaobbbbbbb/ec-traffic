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
		var cityid='${dto.cityId}';
		$("#startTime,#endTime").datepickerStyle();
		$("input[name='isValid'][value='${dto.isValid}']").attr(
				'checked', 'checked');
		$("input[name='isSpider'][value='${dto.isSpider}']").attr(
				'checked', 'checked');
		//城市不可修改
		if(cityid!=null&&cityid!=""){
// 			$("#parentName").attr('disabled','disabled');
		}
	});

	function submitForm() {
		var id=$("#id").val();
		var name=$("#name").val();
		var cityId=$("#cityId").val();
		var maxNum=$("#maxNum").val();
		if(name==""){alert("名称为空");return false;}
		if(cityId==""){alert("查询城市为空");return false;}
		if(maxNum==""){alert("最大查询量为空");return false;}
		var checkUrl="${ctx}/interface/check?name="+name;
		var url;
		if(id==null||id==""){
			url = "${ctx}/interface/add";
			$.ajax({
				url:checkUrl,
				type:'get',
				success:function(data){
					if(data.success){
						$.ec.ajaxSubmitForm({
							url : url,
							callback : frameHref
						});
					}
					else{
						alert(data.msg);
					}
				}
			});
		}else{
			url = "${ctx}/interface/update";
			$.ec.ajaxSubmitForm({
				url : url,
				callback : frameHref
			});
		}

	}

	function frameHref() {
		window.location.href = "${ctx}/interface/list";
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
					<li><a href="${ctx}/interface/list">接口管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>

				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="id" name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>名称：</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty"
									tip="请输入公司代码!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>查询城市：</label>
							<div class="controls">
								<input id="parentName" style="cursor: pointer;" readonly="readonly" onclick="showMenu();" type="text" value="${dto.cityName }" maxlength="100" />
                                <input id="cityId"  name="cityId" type="hidden" value="${dto.cityId}" />
                                <input id="cityPid" name="cityPid"  type="hidden" value="${dto.cityPid}" />
							    <%@ include file="../group/select_group.jsp"%>
							</div>
						</div>
						<div class="control-group">
								<label class="control-label"><span class="red">*</span>最大查询量：</label>
								<div class="controls">
									<input id="maxNum" name="maxNum" type="text"
										value="${dto.maxNum}" maxlength="100" />
								</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否爬虫：</label>
							<div class="controls">
								<label class="radio inline"> <input type="radio"
									name="isSpider" id="optionsRadios1" value=1 checked> 是
								</label> <label class="radio inline"> <input type="radio"
									name="isSpider" id="optionsRadios2" value=0> 否
								</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否有效：</label>
							<div class="controls">
								<label class="radio inline"> <input type="radio"
									name="isValid" id="optionsRadios3" value=1 checked> 是
								</label> <label class="radio inline"> <input type="radio"
									name="isValid" id="optionsRadios4" value=0> 否
								</label>
							</div>
						</div>

						<div class="control-group">
							<label class="control-label"><span class="red">*</span>描述：</label>
							<div class="controls">
								<textarea id="desction" name="desction" validateType="notEmpty"
									tip="请输入查勘员名称!">${dto.desction}</textarea>
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