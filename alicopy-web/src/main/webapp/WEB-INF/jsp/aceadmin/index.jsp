<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>阿里一键生成后台管理</title>
		<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文" />
		<meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="${URL_ROOT}/resources/ace/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/jquery.gritter.css" />
		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/colorbox.css" />
		<!-- fonts -->



		<!-- ace styles -->

		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/ace.min.css" />
		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="${URL_ROOT}/resources/css/top.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="${URL_ROOT}/resources/ace/assets/js/html5shiv.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/respond.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
		var URL_ROOT='${URL_ROOT}';
		
		var Constant={};
		Constant.URL_ROOT='${URL_ROOT}';
		</script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery-2.0.3.min.js"></script>
		<script src="${URL_ROOT}/resources/js/underscore/underscore.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/bootstrap.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.gritter.min.js"></script>
		
			<!-- ace scripts -->

		<script src="${URL_ROOT}/resources/ace/assets/js/ace-extra.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/ace-elements.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/ace.min.js"></script>
	</head>

	<body>
		<jsp:include page="navbar.jsp"></jsp:include>
		<div class="main-container" id="main-container">
			

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				<jsp:include page="sidebar.jsp"></jsp:include>
				<div class="main-content">
						<jsp:include page="breadcrumbs.jsp"></jsp:include>
						<jsp:include page="${content}"></jsp:include>
				</div><!-- /.main-content -->
				<jsp:include page="setting_container.jsp"></jsp:include>
			</div><!-- /.main-container-inner -->

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="icon-double-angle-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->

		

		<!-- <![endif]-->

		<!--[if IE]>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<![endif]-->

		<!--[if !IE]> -->

		<!-- <script type="text/javascript">
			window.jQuery || document.write("<script src='${URL_ROOT}/resources/ace/assets/js/jquery-2.0.3.min.js'>"+"<"+"script>");
		</script> -->

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='${URL_ROOT}/resources/ace/assets/js/jquery-1.10.2.min.js'>"+"<"+"script>");
</script>
<![endif]-->

		<script type="text/javascript">
			if("ontouchend" in document) document.write("<script src='${URL_ROOT}/resources/ace/assets/js/jquery.mobile.custom.min.js'>"+"<"+"script>");
		</script>
		
		<script src="${URL_ROOT}/resources/ace/assets/js/typeahead-bs2.min.js"></script>

		

	

		<!-- inline scripts related to this page -->

		
	</body>
</html>

