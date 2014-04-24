<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="base.jsp"%>
<style type="text/css">
body {
	padding-top: 52px;
}

.sidebar-nav {
	padding: 9px 0;
}

@media ( max-width : 980px) { /* Enable use of floated navbar text */
	.navbar-text.pull-right {
		float: none;
		padding-left: 5px;
		padding-right: 5px;
	}
}

ul li {
	list-style: none;
}
</style>
<script type="text/javascript">
	function loginOut() {
		top.location.href = "${ctx}/ralasafe/login/loginOut";
	}

	function href(url) {
		top.location.href = url;
	}
</script>
<%@ include file="base.jsp"%>
<div class="navbar navbar-inverse navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container-fluid">
			<button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span class="icon-bar"></span>
			</button>
			<a class="brand" href="${ctx}/traffic/index">奥创科技-违章</a>
			<div class="nav-collapse collapse">
				<p class="navbar-text pull-right">
					登录为 <a href="#" class="navbar-link">${sessionScope.USER_SESSION.name}</a>&nbsp;&nbsp; <a
						href="javascript:loginOut();" class="navbar-link">退出</a>
				</p>
				<ul class="nav">
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">基本信息<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/customer/list">客户信息管理</a></li>
							<li><a href="${ctx}/car/list">车辆信息管理</a></li>
							<li><a href="${ctx}/city/list">服务城市管理</a></li>
							<li><a href="${ctx}/interface/list">接口管理</a></li>
<%-- 							<li><a href="${ctx}/task/list">任务分配</a></li> --%>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">违章信息<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/traffic/listIndex">违章查询</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">监控平台<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/monitor/interface">接口监控</a></li>
							<li><a href="${ctx}/task/list">任务监控</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">消息管理<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/traffic/download">违章下载</a></li>
							<li><a href="">消息推送</a></li>
						</ul>
					</li>
					<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"
						class="active">系统设置<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="${ctx}/ralasafe/user/list">用户管理</a></li>
							<li><a href="${ctx}/systemLog/list">系统日志</a></li>
						</ul>
					</li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>
</div>

<!-- 弹出层的提示 -->
<%@ include file="alert.jsp"%>

<link href="${ctx}/media/css/common/load.css" type="text/css" rel="stylesheet" />
<!-- 弹出层的提示 -->
<%@ include file="load.jsp"%>


