<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8" />
		<title>控制台 - Bootstrap后台管理系统模版Ace下载</title>
		<meta name="keywords" content="Bootstrap模版,Bootstrap模版下载,Bootstrap教程,Bootstrap中文" />
		<meta name="description" content="站长素材提供Bootstrap模版,Bootstrap教程,Bootstrap中文翻译等相关Bootstrap插件下载" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<!-- basic styles -->
		<link href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/bootstrap.min.css" rel="stylesheet" />
		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/font-awesome.min.css" />

		<!--[if IE 7]>
		  <link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/font-awesome-ie7.min.css" />
		<![endif]-->

		<!-- page specific plugin styles -->
		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/jquery.gritter.css" />

		<!-- fonts -->



		<!-- ace styles -->

		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/ace.min.css" />
		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/ace-rtl.min.css" />
		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/ace-skins.min.css" />
		<link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/css/top.css" />

		<!--[if lte IE 8]>
		  <link rel="stylesheet" href="http://www.kongjishise.com/taobaoweb/resources/ace/assets/css/ace-ie.min.css" />
		<![endif]-->

		<!-- inline styles related to this page -->

		

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/html5shiv.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/respond.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
		var URL_ROOT='http://www.kongjishise.com/taobaoweb';
		
		var Constant={};
		Constant.URL_ROOT='http://www.kongjishise.com/taobaoweb';
		</script>
		<script src="http://libs.baidu.com/jquery/2.0.3/jquery.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/js/underscore/underscore.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/bootstrap.min.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/jquery-ui-1.10.3.custom.min.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/jquery.gritter.min.js"></script>
		
			<!-- ace scripts -->

		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/ace-extra.min.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/ace-elements.min.js"></script>
		<script src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/ace.min.js"></script>
	</head>

	<body>
		



		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							一键复制后台管理系统
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li class="grey">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-tasks"></i>
								<span class="badge badge-grey">4</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-ok"></i>
									还有4个任务完成
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">软件更新</span>
											<span class="pull-right">65%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:65%" class="progress-bar "></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">硬件更新</span>
											<span class="pull-right">35%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:35%" class="progress-bar progress-bar-danger"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">单元测试</span>
											<span class="pull-right">15%</span>
										</div>

										<div class="progress progress-mini ">
											<div style="width:15%" class="progress-bar progress-bar-warning"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">错误修复</span>
											<span class="pull-right">90%</span>
										</div>

										<div class="progress progress-mini progress-striped active">
											<div style="width:90%" class="progress-bar progress-bar-success"></div>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										查看任务详情
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="purple">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-bell-alt icon-animated-bell"></i>
								<span class="badge badge-important">8</span>
							</a>

							<ul class="pull-right dropdown-navbar navbar-pink dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-warning-sign"></i>
									8条通知
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-pink icon-comment"></i>
												新闻评论
											</span>
											<span class="pull-right badge badge-info">+12</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<i class="btn btn-xs btn-primary icon-user"></i>
										切换为编辑登录..
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-success icon-shopping-cart"></i>
												新订单
											</span>
											<span class="pull-right badge badge-success">+8</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										<div class="clearfix">
											<span class="pull-left">
												<i class="btn btn-xs no-hover btn-info icon-twitter"></i>
												粉丝
											</span>
											<span class="pull-right badge badge-info">+11</span>
										</div>
									</a>
								</li>

								<li>
									<a href="#">
										查看所有通知
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-envelope icon-animated-vertical"></i>
								<span class="badge badge-success">5</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-envelope-alt"></i>
									5条消息
								</li>

								<li>
									<a href="#">
										<img src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" 

/>
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Alex:</span>
												不知道写啥 ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>1分钟以前</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" 

/>
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Susan:</span>
												不知道翻译...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>20分钟以前</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" 

/>
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Bob:</span>
												到底是不是英文 ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>下午3:15</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="inbox.html">
										查看所有消息
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info">
									<small>欢迎光临,</small>
									Jason
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<li>
									<a href="#">
										<i class="icon-cog"></i>
										设置
									</a>
								</li>

								<li>
									<a href="#">
										<i class="icon-user"></i>
										个人资料
									</a>
								</li>

								<li class="divider"></li>

								<li>
									<a href="#">
										<i class="icon-off"></i>
										退出
									</a>
								</li>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		

		<div class="main-container" id="main-container">
			

			<div class="main-container-inner">
				<a class="menu-toggler" id="menu-toggler" href="#">
					<span class="menu-text"></span>
				</a>

				



<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'fixed')
		} catch (e) {
		}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="icon-signal"></i>
			</button>

			<button class="btn btn-info">
				<i class="icon-pencil"></i>
			</button>

			<button class="btn btn-warning">
				<i class="icon-group"></i>
			</button>

			<button class="btn btn-danger">
				<i class="icon-cogs"></i>
			</button>
		</div>

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span> <span class="btn btn-info"></span>

			<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
		</div>
	</div>
	<!-- #sidebar-shortcuts -->

	<ul class="nav nav-list">
		<li class="active"><a href="index.html"> <i
				class="icon-dashboard"></i> <span class="menu-text"> 控制台 </span> </a></li>

		<li><a href="typography.html"> <i class="icon-text-width"></i>
				<span class="menu-text"> 通用商品 </span> </a></li>
		<li class="active"><a href="#" class="dropdown-toggle"> <i
				class="icon-desktop"></i> <span class="menu-text"> 淘宝 </span> <b
				class="arrow icon-angle-down"></b> </a>

			<ul class="submenu">
				<li id="test1" class="active"><a href="http://www.kongjishise.com/taobaoweb/top/opt"> <i
						class="icon-double-angle-right"></i> 商品列表 </a></li>

				<li id="test2"><a href="buttons.html"> <i
						class="icon-double-angle-right"></i> 图片空间 </a></li>

				<li id="test3"><a href="treeview.html"> <i
						class="icon-double-angle-right"></i> 树菜单 </a></li>
			</ul></li>

		
		<li><a href="#" class="dropdown-toggle"> <i
				class="icon-desktop"></i> <span class="menu-text">阿里巴巴</span> <b
				class="arrow icon-angle-down"></b> </a>

			<ul class="submenu">
				<li><a href="http://www.kongjishise.com/taobaoweb/ali/opt"> <i
						class="icon-double-angle-right"></i> 商品列表 </a></li>

				<li><a href="buttons.html"> <i
						class="icon-double-angle-right"></i> 图片空间 </a></li>

				<li><a href="treeview.html"> <i
						class="icon-double-angle-right"></i> 树菜单 </a></li>
			</ul></li>
		
		
		
	</ul>
	<!-- /.nav-list -->


	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
			data-icon2="icon-double-angle-right"></i>
	</div>

	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'collapsed')
		} catch (e) {
		}
	</script>
</div>


				<div class="main-content">
						



<div class="breadcrumbs" id="breadcrumbs">
	<script type="text/javascript">
		try{
			ace.settings.check('breadcrumbs' , 'fixed')
		}catch(e){}
	</script>

	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">首页</a>
		</li>
		<li class="active">控制台</li>
	</ul><!-- .breadcrumb -->

	<div class="nav-search" id="nav-search">
		<form class="form-search">
			<span class="input-icon">
				<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
				<i class="icon-search nav-search-icon"></i>
			</span>
		</form>
	</div><!-- #nav-search -->
</div>


						
<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<img  style="height:45px;"  src="<@=picUrl@>"></img>
</td>
	<td>
		<a href="<@=detailUrl@>" target=‘_blank’><@=title@></a>
	</td>
	<td><@=price@></td>
	<td><@=modified@></td>

	<td>
<div style="margin:0 auto" class="grid_buttongroup">
	<button valed="btn_video_gen" id="btn_video_gen" type="button" class="btn btn-sm btn-primary"   data-loading-text="正在生成视频" >生成视频</button>
	<button id="btn_video_down" type="button" class="btn btn-sm disabled btn-primary"   data-loading-text="正在保存..." >下载视频 </button>
</div>
	</td>

</script>
<div class="page-content">
	<!-- <div class="page-header">
		<h1>
			淘宝 <small> <i class="icon-double-angle-right"></i> 商品列表 </small>
		</h1>
	</div> -->
	<!-- /.page-header -->



	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">登录</h3>
				</div>
				<div class="modal-body text-center">
					<p>还没有登录，点击下面的链接进行登录</p>
					<a id="aLogin" herf="" class="btn btn-large btn-primary"
						target='_blank'>登录</a>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>



	<div class="row">
		<div class="col-xs-12">
			<form role="form" class="form-horizontal">
				<div class="form-group">
					<label for="pay-1" class="control-label col-md-1">是否上架：</label>
					<div class="col-md-1 form-inline">
						<select name="" id="pay-1" class="form-control">
							<option value="1">出售中商品</option>
							<option value="2">库存中商品</option>
						</select>
					</div>
					<label for="name-2" class="control-label col-md-1">商品名称：</label>
					<div class="col-md-1 form-inline">
						<input type="text" class="form-control" id="name-2"
							placeholder="输入名称">
					</div>
					<div class="col-md-1 form-inline">
						<button id="search" type="button"
							class="form-control btn btn-primary">搜索</button>
					</div>
					<div class="col-md-1 form-inline">
						<button id="video_setting" type="button"
							class="form-control btn btn-primary"  data-toggle="modal" data-loading-text="正在保存..." data-target="#video_setting_div">设置</button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
				</div>
			</form>
		</div>
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover table-striped table-text">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											class="ace" /> <span class="lbl"></span> </label></th>
									<th>主图</th>
									<th>名称</th>
									<th>价格</th>
									<th>修改时间</th>
									<th>操作</th>

								</tr>
							</thead>
							<tbody id="listTbody">

							</tbody>


						</table>
					</div>
				</div>
			</div>
			
		</div>
		
		<div style="text-align: center;">
			<ul id='pageTable1'></ul>
		</div>
		<div id="spanTotal1" style="text-align: center;"></div>
	</div>
</div>

<script src="http://www.kongjishise.com/taobaoweb/resources/js/backbone/backbone.js"></script>
<script src="http://www.kongjishise.com/taobaoweb/resources/js/bussiness/taobaoList.js"></script>
<script src="http://www.kongjishise.com/taobaoweb/resources/js/pagenation/bootstrap-paginator.js"></script>
<script
	src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/jquery.dataTables.min.js"></script>
<script
	src="http://www.kongjishise.com/taobaoweb/resources/ace/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="http://www.kongjishise.com/taobaoweb/resources/js/zTree/jquery.ztree.core-3.5.js"></script>
<style src="http://www.kongjishise.com/taobaoweb/resources/js/zTree/css/zTreeStyle.css"></style>
<style src="http://www.kongjishise.com/taobaoweb/resources/css/jquery.loadmask.css"></style>
<script src="http://www.kongjishise.com/taobaoweb/resources/js/loadmask/jquery.loadmask.js"></script>
<script src="http://www.kongjishise.com/taobaoweb/resources/js/common/json2.js"></script>


<script type="text/javascript">
	jQuery(function($) {
		taobaoList();
		/* $("#search").on('click', function() {
			$("#myModal").modal("show");
		}) */
/* 		$("#video_setting").on("click",function(){
			
		}); */
		
		//请求视频设置参数
		$.ajax({
		type:"GET",
		contentType:"application/json",
		url:URL_ROOT+"/top/video/settingupdate",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			if(data.id){
				$("input[name|='voide_setting_id']").val(data.id);
				$("input[name|='musicType'][value="+data.musicType+"]").attr("checked",'checked');
				$("input[name|='videoType'][value="+data.videoType+"]").attr("checked",'checked');
				$("input[name|='videoSize'][value="+data.videoSize+"]").attr("checked",'checked');
			}
		}
		});
		$("#search").on("click",function(){
			if($("#pay-1")[0].selectedIndex==0){
				Taobao.itemListView.itemList.url=Constant.URL_ROOT+'/top/opt/onsale/item';
			}else{
				Taobao.itemListView.itemList.url=Constant.URL_ROOT+'/top/opt/inventory/item';
			}
			
			Taobao.itemListView.loadItems();
		})
		$("#btn_save_video_setting").on("click",function(){
			var id=$("input[name|='voide_setting_id']").val();
			var musicType=$("input[name|='musicType']:checked").val();
			var videoType=$("input[name|='videoType']:checked").val();
			var videoSize=$("input[name|='videoSize']:checked").val();
			$("#video_setting").button("loading");
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"id":id,"musicType":musicType,"videoType":videoType,"videoSize":videoSize}),
				url:URL_ROOT+"/top/video/setting",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					$("#video_setting").button('reset');
				}
			});
				$("#video_setting_div").modal("hide");
			
		});
		
		function saveSetting(){
		}
	});
</script>

<!-- 弹出框 -->
 <div id="video_setting_div" class="modal fade" role="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1000px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">视频参数设置</h4>
      </div>
      <div class="modal-body">
	
	<div class="video_setting_div">
	
	<input type="hidden" name="voide_setting_id" />
	<h2 style="background:#3e89c3;" class="m-tit">
      <em><span style="color:white;">音乐背景设置区</span></em> <div style="margin-right:5px;margin-top:-2px;float:right;width:100px;" onclick="showaudiodlg()" class="div_button">试听 </div> 

    </h2>
    <div class="m-music-list">
      <ul>
        <li>
            <input type="radio" checked="checked" name="musicType" value=1 value1="http://video.video-maker.cn/audio/pure/1.mp3">
            <label>纯音乐1</label>
           	<a title="" href="##" class=""></a>  <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=2 value="http://video.video-maker.cn/audio/pure/2.mp3">
            <label>纯</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=3 value="http://video.video-maker.cn/audio/pure/3.mp3">
            <label>纯音乐3</label>
			 <a tit你好的le=""fd
fdfd f

	