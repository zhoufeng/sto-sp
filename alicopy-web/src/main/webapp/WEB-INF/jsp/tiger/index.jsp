<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="${URL_ROOT}/resources/tiger/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<style>
/* <script data-main="${URL_ROOT}/resources/tiger/js/main.js" src="../lib/requirejs/require.js"></script> */
body {
	width: 1024px;
	margin: auto;
}
.nav default{
background-color:#ff0000
}
.nav li {
	padding-right: 10px
}
.breadcrumb > li + li::before {
    color: #ccc;
    content: "	|	";
    padding: 0 5px;
}
</style>
<body>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script src="http://cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="${URL_ROOT}/resources/tiger/lib/bootstrap/bootstrap.min.js"></script>
	<script>
		jQuery(function($) {
			$("#topMenu a");
		});
	</script>
	<div class="container">
		<div class="row navbar-default">
			<ul id="topMenu" class="nav nav-tabs ">
				<li role="presentation" class="active"><a href="${URL_ROOT}/tiger/autoplan">自动重发</a></li>
				<!-- <li role="presentation"><a href="#">阿里复制</a></li>
				<li role="presentation"><a href="#">淘宝复制</a></li>
				<li role="presentation"><a href="#">库存同步</a></li> -->
			</ul>
		</div>
	<jsp:include page="${content}"></jsp:include>
	</div>
</body>
</html>