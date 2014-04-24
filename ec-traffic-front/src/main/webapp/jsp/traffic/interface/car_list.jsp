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
<link href="${ctx}/media/js/jquery-ui-1.9.2.custom/development-bundle/themes/base/jquery.ui.all.css"
	type="text/css" rel="stylesheet" />
	<script src="${ctx}/media/js/jquery-ui-1.9.2.custom/jquery-ui-1.9.2.custom.js"
	type="text/javascript"></script>
<script type="text/javascript">

	$(function(){
		 $("#startTime,#endTime").datepickerStyle();
	});
		
	
	function selectAll(s) {
 		var checkboxs = $("input[type='checkbox']");
 		for (var i=0;i<checkboxs.length;i++) {
  			var e=checkboxs[i];
  			if(s==1)
  				e.checked=true;
  			if(s==2)
  				e.checked=!e.checked;
  			if(s==3)
  				e.checked=false;
 		}
	}
	
	function bindCars(){
		var interfaceId='${dto.interfaceId}';
		var  checkboxs=$("input[name='checkbox_name']:checkbox:checked");
		var  ids=new Array();
		for (var i=0;i<checkboxs.length;i++) {
  			var e=checkboxs[i].id;
  			ids.push(e);
 		}
		
		var url = "${ctx}/interface/bind", param = {
				"ids" :	ids=$.toArray(ids),
				"interfaceId" : interfaceId,
			};
			$.ajax({
				url : url,
				type : 'POST',
				data : param,
				success : function(data) {
						alert(data.message);
						document.location.reload();
				}
// 					document.location.reload();
				
			}); 
	}
	
	function clean(){
		$("#customerName,#carNo,#endTime,#startTime").val("");
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
					<li><a href="${ctx }/interface/list">接口管理</a><span class="divider">/</span></li><li class="active">绑定车辆</li>
				</ul>
				<form class="well form-inline" style="text-align: center;">
					<label>公司：</label><input type="text" id="customerName" name="customerName" value="${dto.customerName }"/> 
					<label>车牌号：</label><input type="text" id="carNo" name="carNo" value="${dto.carNo }"/> 
					<input type="text" id="startTime" name="startTime" class="input-small" style="cursor: pointer;" readonly="readonly"
						placeholder="起始时间" value="${dto.startTime}"> <input type="text" id="endTime" name="endTime"
						class="input-small" style="cursor: pointer;" readonly="readonly" placeholder="终止时间" value="${dto.endTime}">
						<input type="hidden" id="interfaceId" name="interfaceId" value="${dto.interfaceId }"/>
					<button type="submit" class="btn">搜索</button>
					<button class="btn" onclick="clean()">清空</button>
				</form>
				    
				<div class="well">
					<div class="btn-group">
    					<button class="btn" onclick="selectAll(1)">全选</button>
    					<button class="btn" onclick="selectAll(2)">反选</button>
    					<button class="btn" onclick="selectAll(3)">清空</button>
    					<button class="btn btn-primary" onfocus="this.blur()"
						onclick="bindCars()">绑定</button>
    				</div>
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
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ECPage.list }" var="dto" varStatus="sn">
								<tr>
									<td class="td_center">
										<input type="checkbox" id="${dto.id}_${dto.customerId}" name="checkbox_name"/>&nbsp;&nbsp;${sn.count}
									</td>
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
							
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<form action="${ctx}/interface/carlist" id="pageForm">
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