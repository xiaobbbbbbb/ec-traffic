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
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/core/jquery.ec-business.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/artDialog4.1.7/jquery.artDialog.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/artDialog4.1.7/iframeTools.js"></script>
<link
	href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
<script
	src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"
	type="text/javascript"></script>
	<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function() {
		$("#startDate,#endDate").datepickerStyle();
	});

	//文件上传
	function downloadFile() {
		var url = "${ctx}/upload/uploadUI";
		art.dialog.open(url, {
			id : 'uploadUI_',
			width : 320,
			height : 160,
			title : '数据批量导入',
			okVal : '立即上传',
			ok : function() {
				//打开页面的对象
				var iframeObj = this.iframe.contentWindow;
				var tf = iframeObj.upload();
				if (tf) {
					$.loadOpen(); //打开load
				}
				return false;
			},
			cancel : true
		}).show();
	}

	function download() {
		var customerId = $("select").val();
		var startDate = $("#startDate").val();
		var endDate = $("#endDate").val();
		if (customerId == 0) {
			alert("请选择公司!");
			return;
		}
		if(startDate==""&&endDate==""){
			alert("请选择时间段！");
			return;
		}
		url = "${ctx}/traffic/downloadTrafficItems?customerId=" + customerId+"&startDate="+startDate+"&endDate="+endDate;
		window.location.href = url;
		// 		 $.ajax({
		// 			url:url,
		// 			type:'get'

		// 		 });
	}
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>

				<div class="well" style="text-align: center;">
					<form>
						<select name="customerId" id="customerId">
							<option value=0>选择公司</option>
							<c:forEach items="${listCustomer}" var="list" varStatus="st">
								<option value="${list.id }"
									<c:if test="${list.id == dto.customerId}">selected="selected"</c:if>>
									${list.name}</option>
							</c:forEach>
						</select> <input type="hidden" value="${ctx}" id="basePath" /> 选择时间段：<input
							type="text" id="startDate" name="startDate"
							value="${dto.startDate}" class="input-small"
							style="cursor: pointer;" readonly="readonly" placeholder="开始时间">
						<input type="text" id="endDate" name="endDate" class="input-small"
							value="${dto.endDate }" style="cursor: pointer;"
							readonly="readonly" placeholder="结束时间"> <label
							for="exampleInputFile">File input</label>

						<p class="help-block">Example block-level help text here.</p>
						<input type="button" value="下载" onclick="download();">
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>