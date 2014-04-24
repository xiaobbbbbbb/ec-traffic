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
<script type="text/javascript" src="${ctx}/media/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript" src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
<script type="text/javascript" src="${ctx}/media/js/core/jquery.ec-base.js"></script>

<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function() {
	    $("#endDate,#startDate").datepickerStyle();
	    
	    $("select[name='type'] option[value='${dto.type}']").attr("selected","selected");
	    $("select[name='va_select'] option[value='${dto.isValid}']").attr("selected","selected");
	    $("select[name='ex_select'] option[value='${dto.isExpired}']").attr("selected","selected");
	});

	function submitForm() {
		var id=$("#id").val();
		var code=$("#code").val();
		var name=$("#name").val();
		var parentName=$("#name").val();
		var stratDate=$("#stratDate").val();
		var endDate=$("#endDate").val();
		if(code==""){
			alert("公司代码为空");
			return false;
		}
		if(name==""){
			alert("公司名称为空");
			return false;
		}
		if(stratDate==""){
			alert("合同起始时间为空");
			return false;
		}
		if(endDate==""){
			alert("合同终止日期为空");return false;
		}
		var checkUrl="${ctx}/customer/check?code="+code+"&name="+name;
		if(id=null||id==""){
		$.ajax({
			url:checkUrl,
			type:'get',
			success:function(data){
				if(data.success){
					$.ec.ajaxSubmitForm({
						url :"${ctx}/customer/add",
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
				url : "${ctx}/customer/update",
				callback : frameHref
			});
		}
	
	}

	function frameHref() {
		window.location.href = "${ctx}/customer/list";
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
					<li><a href="${ctx}/customer/list">客户管理</a> <span class="divider">/</span></li>
					<li class="active">${dto.id == null ? '添加' : '修改'}</li>
				</ul>
				
				<div class="well">
					<form id="form" class="form-horizontal">
						<input id="id" name="id" value="${dto.id }" type="hidden" />
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>公司代码：</label>
							<div class="controls">
								<input id="code" name="code" validateType="notEmpty" tip="请输入公司代码!" type="text" value="${dto.code}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>公司名称：</label>
							<div class="controls">
								<input id="name" name="name" validateType="notEmpty" tip="请输入查勘员名称!" type="text" value="${dto.name}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>类型：</label>
								<div class="controls">
								<select id="type" name="type" >
									<option value=1>保险</option>
									<option value=2>4S</option>
									<option value=3>油品</option>
									<option value=0>其它</option>
								</select>
							</div>
						</div>
						<div  class="control-group">
							<label class="control-label"><span class="red">*</span>查询城市：</label>
								<div class="controls">
								<input id="parentName" style="cursor: pointer;" readonly="readonly" onclick="showMenu();" type="text" value="${dto.cityName }" maxlength="100" />
                                <input id="cityId"  name="cityId"  type="hidden" value="${dto.cityId}" />
                                <input id="cityPid" name="cityPid" type="hidden" value="${dto.cityPid}" />
							    <%@ include file="../group/select_group.jsp"%>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>合同起始日期：</label>
							<div class="controls">
								<input id="startDate" style="cursor: pointer;" readonly="readonly" validateType="notEmpty" tip="请输入驾驶证到期日!" name="startDate" type="text" value="<fmt:formatDate value="${dto.startDate}"
											pattern="yyyy-MM-dd" />"
											
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>合同终止日期：</label>
							<div class="controls">
								<input id="endDate" style="cursor: pointer;" readonly="readonly" validateType="notEmpty" tip="请输入驾驶证到期日!" name="endDate" type="text" value="<fmt:formatDate value="${dto.endDate}"
											pattern="yyyy-MM-dd" />"
											
									maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否有效：</label>
							<div class="controls">
								<select  id="isExpired" name="isExpired" >
									<option value=1 >是</option>
									<option value=0>否</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>是否审核：</label>
							<div class="controls">
								<select  id="isValid" name="isValid">
									<option value=1>是</option>
									<option value=0>否</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>用户数量：</label>
							<div class="controls">
								<input id="carNums" name="carNums" validateType="notEmpty" tip="请输入驾驶证号!" type="text" value="${dto.carNums}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>可变更用户数量：</label>
							<div class="controls">
								<input id="carAllowchangeNums" name="carAllowchangeNums" validateType="notEmpty" tip="请输入驾驶证号!" type="text" value="${dto.carAllowchangeNums}" maxlength="100" />
							</div>
						</div>
						<div class="control-group">
							<label class="control-label"><span class="red">*</span>回调url：</label>
							<div class="controls">
								<input id="url" name="url" validateType="notEmpty" tip="请输入驾驶证号!" type="text" value="${dto.url}" maxlength="100" />
							</div>
						</div>
						<div class="form-actions">
							<input id="btnSubmit" class="btn btn-primary" type="button" onclick="submitForm();"
								value="保 存" />&nbsp; <input id="btnCancel" class="btn" type="button" value="返 回"
								onclick="history.go(-1)" />
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>