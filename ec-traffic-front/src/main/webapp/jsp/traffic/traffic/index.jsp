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
<link href="${ctx}/media/js/artDialog4.1.7/skins/default.css?4.1.7" type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="${ctx}/webjars/jquery/1.9.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${ctx}/webjars/bootstrap/2.3.2/js/bootstrap.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/core/jquery.ec-base.js"></script>
<script type="text/javascript"
	src="${ctx}/media/js/jquery.json-2.3.min.js"></script>
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
<script type="text/javascript">
	$(function() {
	});
	
	 //文件上传
    function uploadFile()
	{
		var url="${ctx}/upload/uploadUI";
		art.dialog.open(url, {
			id: 'uploadUI_',
		    width: 320,
		    height: 160,
            title: '数据批量导入',
            okVal:'立即上传',
		    ok: function () {
		    	//打开页面的对象
		    	var iframeObj = this.iframe.contentWindow;
		    	var tf=iframeObj.upload();
		    	if(tf)
		    	{
		    		$.loadOpen();  //打开load
		    	}
		    	return false;
		    },
		    cancel: true
        }).show();
    }
	 
	 function task(){
		 $.ajax({
			 url:'${ctx}/task/start',
			 type:'get',
			 success:function(date){
				 alert(date.msg);
			 }
		 });
	 }
	 function test1(){
		 var param = $('#test1').serialize();
		 alert(param);
			$.post('${ctx}/api/batchUploadCarinfos', param, function(data) {
			alert(data.msg);
		});
	 }
	 
	 function test2(){
		 var param = $('#test2').serialize();
		 alert(param);
			$.post('${ctx}/api/getTrafficItemsBycar', param, function(data) {
			alert(data.msg);
		});
	 }
// 	 function test(){
// // 		 var param = form.find('#test').serialize();
// // 			$.post(url, param, function(data) {
// // 				alert(data.msg);
// // 			});
// 		 var prama={
// 				"orgCode":"100010","action":"add",[
// 				{
// 					"carNo":"b123","carinfolist":"2323434"
// 				}]};
// 		 $.ajax({
// 			 url:'${ctx}/api/batchUploadCarinfos',
// 			 type:'post',
// 			 data:param,
// 			 dataType:'json',
// 			 success:function(date){
// 				 alert(date.msg);
// 			 }
// 		 });
// 	 }
</script>
</head>
<body>
	<!-- 页面头部 -->
	<%@ include file="../../common/head.jsp"%>
	<div class="container-fluid">
		<div class="row-fluid">
			<div>

				<div class="well" style="text-align: center;">
					<input type="hidden" value="${ctx}" id="basePath"/>
					<label for="exampleInputFile">File input</label> 
					
					<p class="help-block">Example block-level help text here.</p>
					<input type="button" value="上传文件" onclick="uploadFile();" >
<!-- 					<input type="button" value="test" onclick="test();" > -->
<!-- 					<form id="test1"> -->
<!-- 						<input type="text" name="orgCode" id="orgCode" value="100010"/> -->
<!-- 						<input type="text" name="action" id="action" value="del"/> -->
<!-- 						<input type="text" name="carinfolist[0].carNo" id="carinfolist[0].carNo" value="ye123"/> -->
<!-- 						<input type="text" name="carinfolist[1].carNo" id="carinfolist[1].carNo" value="ye1234"/> -->
<!-- 						<input type="text" name="carinfolist[1].carFrameNo" id="carinfolist[1].carFrameNo" value="45454"/> -->
<!-- 						<input type="text" name="carinfolist[0].carFrameNo" id="carinfolist[0].carFrameNo" value="45454"/> -->
<!-- 						<input type="button" value= "test1"  onclick="test1()" > -->
<!-- 					</form> -->
<!-- 					<form id="test2"> -->
<!-- 						<input type="text" name="orgCode" id="orgCode" value="100010"/> -->
<!-- 						<input type="text" name="action" id="action" value=""/> -->
<!-- 						<input type="text" name="carinfolist[0].carNo" id="carinfolist[0].carNo" value="ccc123"/> -->
<!-- 						<input type="text" name="carinfolist[1].carNo" id="carinfolist[1].carNo" value="ccc1234"/> -->
<!-- 						<input type="text" name="carinfolist[1].carFrameNo" id="carinfolist[1].carFrameNo" value="45454"/> -->
<!-- 						<input type="text" name="carinfolist[0].carFrameNo" id="carinfolist[0].carFrameNo" value="45454"/> -->
<!-- 						<input type="button"   value= "test2" onclick="test2()" > -->
<!-- 					</form> -->
				</div>
			</div>
		</div>
	</div>


</body>
</html>