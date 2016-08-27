<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>博虎shopping微店</title>
<meta name="viewport"
	content="width=device-width,height=device-height,inital-scale=1.0,maximum-scale=1.0,user-scalable=no;">
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">
<meta charset="utf-8">
<link rel="stylesheet"
	href="${URL_ROOT}/resources/weixin/css/idangerous.swiper.css">
<link href="${URL_ROOT}/resources/weixin/css/iscroll.css" rel="stylesheet"
	type="text/css" />
<link href="${URL_ROOT}/resources/weixin/tpl/1110/css/cate.css" rel="stylesheet"
	type="text/css" />
<style></style>
<script src="${URL_ROOT}/resources/weixin/js/iscroll.js" type="text/javascript"></script>
<script type="text/javascript">var myScroll;

function loaded() {
myScroll = new iScroll('wrapper', {
snap: true,
momentum: false,
hScrollbar: false,
onScrollEnd: function () {
document.querySelector('#indicator > li.active').className = '';
document.querySelector('#indicator > li:nth-child(' + (this.currPageX+1) + ')').className = 'active';
}
 });
 
}

document.addEventListener('DOMContentLoaded', loaded, false);
</script>
</head>
<body>
	<!--music-->
	<style>
.btn_music {
	display: inline-block;
	width: 35px;
	height: 35px;
	background: url('${URL_ROOT}/resources/weixin/common/images/play.png')
		no-repeat center center;
	background-size: 100% auto;
	position: absolute;
	z-index: 100;
	left: 15px;
	top: 20px;
}

.btn_music.on {
	background-image:
		url("${URL_ROOT}/resources/weixin/common/images/stop.png");
}
</style>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"
		type="text/javascript"></script>
	<script>var playbox = (function(){
	//author:eric_wu
	var _playbox = function(){
		var that = this;
		that.box = null;
		that.player = null;
		that.src = null;
		that.on = false;
		//
		that.autoPlayFix = {
			on: true,
			//evtName: ("ontouchstart" in window)?"touchend":"click"
			evtName: ("ontouchstart" in window)?"touchstart":"mouseover"
			
		}

	}
	_playbox.prototype = {
		init: function(box_ele){
			this.box = "string" === typeof(box_ele)?document.getElementById(box_ele):box_ele;
			this.player = this.box.querySelectorAll("audio")[0];
			this.src = this.player.src;
			this.init = function(){return this;}
			this.autoPlayEvt(true);
			return this;
		},
		play: function(){
			if(this.autoPlayFix.on){
				this.autoPlayFix.on = false;
				this.autoPlayEvt(false);
			}
			this.on = !this.on;
			if(true == this.on){
				this.player.src = this.src;
				this.player.play();
			}else{
				this.player.pause();
				this.player.src = null;
			}
			if("function" == typeof(this.play_fn)){
				this.play_fn.call(this);
			}
		},
		handleEvent: function(evt){
			this.play();
		},
		autoPlayEvt: function(important){
			if(important || this.autoPlayFix.on){
				document.body.addEventListener(this.autoPlayFix.evtName, this, false);
			}else{
				document.body.removeEventListener(this.autoPlayFix.evtName, this, false);
			}
		}
	}
	//
	return new _playbox();
})();

playbox.play_fn = function(){
	this.box.className = this.on?"btn_music on":"btn_music";
}
</script>
	<script type="text/javascript">$(document).ready(function(){
	playbox.init("playbox");
	/*
	setTimeout(function() {
		// IE
		if(document.all) {
			document.getElementById("playbox").click();
		}
		// 其它浏览器
		else {
			var e = document.createEvent("MouseEvents");
			e.initEvent("click", true, true);
			document.getElementById("playbox").dispatchEvent(e);
		}
	}, 2000);
	*/
});

</script>
	<span id="playbox" class="btn_music"
		onclick="playbox.init(this).play();"><audio id="audio" loop
			src="http://demo.pigcms.cn/tpl/static/attachment/music/default/4.mp3"></audio>
	</span>
	<div class="banner">
		<div id="wrapper">
			<div id="scroller">
				<ul id="thelist">
					<li><p>幻灯片一描述</p>
						<a href="javascript:void(0)"><img
							src="${URL_ROOT}/resources/weixin/attachment/focus/default/2.jpg" />
					</a>
					</li>
					<li><p>幻灯片二描述</p>
						<a href="javascript:void(0)"><img
							src="${URL_ROOT}/resources/weixin/attachment/focus/default/3.jpg" />
					</a>
					</li>
					<li><p>幻灯片描述三</p>
						<a href="javascript:void(0)"><img
							src="${URL_ROOT}/resources/weixin/attachment/focus/default/4.jpg" />
					</a>
					</li>
				</ul>
			</div>
		</div>
		<div id="nav">
			<div id="prev"
				onclick="myScroll.scrollToPage('prev', 0,400,2);return false">&larr;
				prev</div>
			<ul id="indicator">
				<li class="active"></li>
				<li></li>
				<li></li>
			</ul>
			<div id="next"
				onclick="myScroll.scrollToPage('next', 0);return false">next
				&rarr;</div>
		</div>
		<div class="clr"></div>
	</div>
	<div id="insert1"></div>
	<div class="device">
		<a class="arrow-left" href="#"></a><a class="arrow-right" href="#"></a>
		<div class="swiper-container">
			<div class="swiper-wrapper">
				<div class="swiper-slide">
					<div class="content-slide">
						<a
							href="http://demo.pigcms.cn/index.php?g=Wap&m=Photo&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/1.png" />
								</p>
								<p class="title">分类一</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268736&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/3.png" />
								</p>
								<p class="title">分类二</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268737&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/4.png" />
								</p>
								<p class="title">分类三</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268738&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/5.png" />
								</p>
								<p class="title">分类四</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268739&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/6.png" />
								</p>
								<p class="title">分类五</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268740&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/7.png" />
								</p>
								<p class="title">分类六</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268741&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/8.png" />
								</p>
								<p class="title">分类七</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268742&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/11.png" />
								</p>
								<p class="title">分类八</p>
							</div>
						</a><a
							href="/index.php?g=Wap&m=Index&a=lists&classid=268743&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><div
								class="mbg">
								<p class="ico">
									<img
										src="http://demo.pigcms.cn/tpl/static/attachment/icon/white/14.png" />
								</p>
								<p class="title">分类九</p>
							</div>
						</a>
					</div>
				</div>
			</div>
			<div class="pagination"></div>
		</div>
	</div>
	<!-- <script src="./tpl/static/tpl/com/js/jquery-1.10.1.min.js"
		type="text/javascript"></script> -->
	<script src="${URL_ROOT}/resources/weixin/js/idangerous.swiper-2.1.min.js"
		type="text/javascript"></script>
	<script>
		var mySwiper = new Swiper('.swiper-container', {
			pagination : '.pagination',
			loop : true,
			grabCursor : true,
			paginationClickable : true
		})
		$('.arrow-left').on('click', function(e) {
			e.preventDefault()
			mySwiper.swipePrev()
		})
		$('.arrow-right').on('click', function(e) {
			e.preventDefault()
			mySwiper.swipeNext()
		})
	</script>
	<script>
		var count = document.getElementById("thelist").getElementsByTagName(
				"img").length;

		var count2 = document.getElementsByClassName("menuimg").length;
		for (i = 0; i < count; i++) {
			document.getElementById("thelist").getElementsByTagName("img")
					.item(i).style.cssText = " width:"
					+ document.body.clientWidth + "px";

		}
		document.getElementById("scroller").style.cssText = " width:"
				+ document.body.clientWidth * count + "px";

		setInterval(function() {
			myScroll.scrollToPage('next', 0, 400, count);
		}, 3500);
		window.onresize = function() {
			for (i = 0; i < count; i++) {
				document.getElementById("thelist").getElementsByTagName("img")
						.item(i).style.cssText = " width:"
						+ document.body.clientWidth + "px";

			}
			document.getElementById("scroller").style.cssText = " width:"
					+ document.body.clientWidth * count + "px";
		}
	</script>
	<div class="copyright">© 2012-2014 博虎CMS版权所有</div>
	<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"
		type="text/javascript"></script>
	<br>
	<br>
	<script>
		function displayit(n) {
			for (i = 0; i < 4; i++) {
				if (i == n) {
					var id = 'menu_list' + n;
					if (document.getElementById(id).style.display == 'none') {
						document.getElementById(id).style.display = '';
						document.getElementById("plug-wrap").style.display = '';
					} else {
						document.getElementById(id).style.display = 'none';
						document.getElementById("plug-wrap").style.display = 'none';
					}
				} else {
					if ($('#menu_list' + i)) {
						$('#menu_list' + i).css('display', 'none');
					}
				}
			}
		}
		function closeall() {
			var count = document.getElementById("top_menu")
					.getElementsByTagName("ul").length;
			for (i = 0; i < count; i++) {
				document.getElementById("top_menu").getElementsByTagName("ul")
						.item(i).style.display = 'none';
			}
			document.getElementById("plug-wrap").style.display = 'none';
		}

		document.addEventListener('WeixinJSBridgeReady',
				function onBridgeReady() {
					WeixinJSBridge.call('hideToolbar');
				});
	</script>
	<style type="text/css">
body {
	margin-bottom: 60px !important;
}

a,button,input {
	-webkit-tap-highlight-color: rgba(255, 0, 0, 0);
}

ul,li {
	list-style: none;
	margin: 0;
	padding: 0
}

.top_bar {
	position: fixed;
	z-index: 900;
	bottom: 0;
	left: 0;
	right: 0;
	margin: auto;
	font-family: Helvetica, Tahoma, Arial, Microsoft YaHei, sans-serif;
}

.top_menu {
	display: -webkit-box;
	border-top: 1px solid #3D3D46;
	display: block;
	width: 100%;
	background: rgba(255, 255, 255, 0.7);
	height: 48px;
	display: -webkit-box;
	display: box;
	margin: 0;
	padding: 0;
	-webkit-box-orient: horizontal;
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#524945),
		to(#48403c), color-stop(60%, #524945) );
	box-shadow: 0 1px 0 0 rgba(255, 255, 255, 0.1) inset;
}

.top_bar .top_menu>li {
	-webkit-box-flex: 1;
	position: relative;
	text-align: center;
}

.top_menu li:first-child {
	background: none;
}

.top_bar .top_menu>li>a {
	height: 48px;
	margin-right: 1px;
	display: block;
	text-align: center;
	color: #FFF;
	text-decoration: none;
	text-shadow: 0 1px rgba(0, 0, 0, 0.3);
	-webkit-box-flex: 1;
}

.top_bar .top_menu>li.home {
	max-width: 70px
}

.top_bar .top_menu>li.home a {
	height: 66px;
	width: 66px;
	margin: auto;
	border-radius: 60px;
	position: relative;
	top: -22px;
	left: 2px;
	background: url('tpl/Wap/default/common/images/home.png') no-repeat
		center center;
	background-size: 100% 100%;
}

.top_bar .top_menu>li>a label {
	overflow: hidden;
	margin: 0 0 0 0;
	font-size: 12px;
	display: block !important;
	line-height: 18px;
	text-align: center;
}

.top_bar .top_menu>li>a img {
	padding: 3px 0 0 0;
	height: 24px;
	width: 24px;
	color: #fff;
	line-height: 48px;
	vertical-align: middle;
}

.top_bar li:first-child a {
	display: block;
}

.menu_font {
	text-align: left;
	position: absolute;
	right: 10px;
	z-index: 500;
	background: -webkit-gradient(linear, 0 0, 0 100%, from(#524945),
		to(#48403c), color-stop(60%, #524945) );
	border-radius: 5px;
	width: 120px;
	margin-top: 10px;
	padding: 0;
	box-shadow: 0 1px 5px rgba(0, 0, 0, 0.3);
}

.menu_font.hidden {
	display: none;
}

.menu_font {
	top: inherit !important;
	bottom: 60px;
}

.menu_font li a {
	height: 40px;
	margin-right: 1px;
	display: block;
	text-align: center;
	color: #FFF;
	text-decoration: none;
	text-shadow: 0 1px rgba(0, 0, 0, 0.3);
	-webkit-box-flex: 1;
}

.menu_font li a {
	text-align: left !important;
}

.top_menu li:last-of-type a {
	background: none;
	overflow: hidden;
}

.menu_font:after {
	top: inherit !important;
	bottom: -6px;
	border-color: #48403c rgba(0, 0, 0, 0) rgba(0, 0, 0, 0);
	border-width: 6px 6px 0;
	position: absolute;
	content: "";
	display: inline-block;
	width: 0;
	height: 0;
	border-style: solid;
	left: 80%;
}

.menu_font li {
	border-top: 1px solid rgba(255, 255, 255, 0.1);
	border-bottom: 1px solid rgba(0, 0, 0, 0.2);
}

.menu_font li:first-of-type {
	border-top: 0;
}

.menu_font li:last-of-type {
	border-bottom: 0;
}

.menu_font li a {
	height: 40px;
	line-height: 40px !important;
	position: relative;
	color: #fff;
	display: block;
	width: 100%;
	text-indent: 10px;
	white-space: nowrap;
	text-overflow: ellipsis;
	overflow: hidden;
}

.menu_font li a img {
	width: 20px;
	height: 20px;
	display: inline-block;
	margin-top: -2px;
	color: #fff;
	line-height: 40px;
	vertical-align: middle;
}

.menu_font>li>a label {
	padding: 3px 0 0 3px;
	font-size: 14px;
	overflow: hidden;
	margin: 0;
}

#menu_list0 {
	right: 0;
	left: 10px;
}

#menu_list0:after {
	left: 20%;
}

#sharemcover {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0.7);
	display: none;
	z-index: 20000;
}

#sharemcover img {
	position: fixed;
	right: 18px;
	top: 5px;
	width: 260px;
	height: 180px;
	z-index: 20001;
	border: 0;
}

.top_bar .top_menu>li>a:hover,.top_bar .top_menu>li>a:active {
	background-color: #333;
}

.menu_font li a:hover,.menu_font li a:active {
	background-color: #333;
}

.menu_font li:first-of-type a {
	border-radius: 5px 5px 0 0;
}

.menu_font li:last-of-type a {
	border-radius: 0 0 5px 5px;
}

#plug-wrap {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background: rgba(0, 0, 0, 0);
	z-index: 800;
}

#cate18 .device {
	bottom: 49px;
}

#cate18 #indicator {
	bottom: 240px;
}

#cate19 .device {
	bottom: 49px;
}

#cate19 #indicator {
	bottom: 330px;
}

#cate19 .pagination {
	bottom: 60px;
}
</style>
	<div class="top_bar" style="-webkit-transform:translate3d(0,0,0)">
		<nav>
		<ul id="top_menu" class="top_menu">
			<li><a onclick="javascript:displayit(0)"><img
					src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu2.png"><label>了解我们</label>
			</a>
			<ul id="menu_list0" class="menu_font" style=" display:none">
					<li><a href="tel:13888888888"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu1.png"><label>一键拨号</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Reply&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&sgssz=mp.weixin.qq.com"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu15.png"><label>留言板</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Forum&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&sgssz=mp.weixin.qq.com"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu8.png"><label>微论坛</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Company&a=map&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&companyid=24224"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu3.png"><label>一键导航</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Photo&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu7.png"><label>微相册</label>
					</a>
					</li>
				</ul>
			</li>
			<li><a onclick="javascript:displayit(1)"><img
					src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu18.png"><label>推广活动</label>
			</a>
			<ul id="menu_list1" class="menu_font" style=" display:none">
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Lottery&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=31290"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu11.png"><label>大转盘</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Guajiang&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=31292"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu10.png"><label>刮刮卡</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Coupon&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=31291"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu19.png"><label>优惠券</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=GoldenEgg&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=31294"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu17.png"><label>砸金蛋</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=LuckyFruit&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=31293"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu9.png"><label>水果机</label>
					</a>
					</li>
				</ul>
			</li>
			<li class="home"><a
				href="/index.php?g=Wap&m=Index&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"></a>
			</li>
			<li><a onclick="javascript:displayit(2)"><img
					src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu9.png"><label>电商应用</label>
			</a>
			<ul id="menu_list2" class="menu_font" style=" display:none">
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Store&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu9.png"><label>商城</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Groupon&a=grouponIndex&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu11.png"><label>团购</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Repast&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu2.png"><label>订餐</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Card&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu10.png"><label>会员卡</label>
					</a>
					</li>
				</ul>
			</li>
			<li><a onclick="javascript:displayit(3)"><img
					src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu5.png"><label>其他功能</label>
			</a>
			<ul id="menu_list3" class="menu_font" style=" display:none">
					<li><a
						href="#"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu18.png"><label>全景</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Wedding&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=6076"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu2.png"><label>喜帖</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Selfform&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=6417"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu17.png"><label>万能表单</label>
					</a>
					</li>
					<li><a
						href="http://demo.pigcms.cn/index.php?g=Wap&m=Vote&a=index&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko&id=6034"><img
							src="http://demo.pigcms.cn/tpl/User/default/common/images/photo/plugmenu8.png"><label>投票</label>
					</a>
					</li>
				</ul>
			</li>
		</ul>
		</nav>
	</div>
	<div id="plug-wrap" onclick="closeall()" style="display: none;"></div>
	<!-- share -->
	<script type="text/javascript">
		window.shareData = {
			"moduleName" : "Index",
			"moduleID" : '0',
			"imgUrl" : "http://demo.pigcms.cn/tpl/static/images/homelogo.png",
			"timeLineLink" : "http://demo.pigcms.cn/index.php?g=Wap&m=Index&a=index&token=occcgv1404262269",
			"sendFriendLink" : "http://demo.pigcms.cn/index.php?g=Wap&m=Index&a=index&token=occcgv1404262269",
			"weiboLink" : "http://demo.pigcms.cn/index.php?g=Wap&m=Index&a=index&token=occcgv1404262269",
			"tTitle" : "欢迎光临我们的微网站!",
			"tContent" : "欢迎您进入我们的微网站，在对黄框里首页 或者 home —— 当用户输入该关键词时，将会触发此回复。"
		};
	</script>
	<script>
		window.shareData.sendFriendLink = window.shareData.sendFriendLink
				.replace('http://demo.pigcms.cn', 'http://demo.pigcms.cn');
		document.addEventListener('WeixinJSBridgeReady',
				function onBridgeReady() {
					WeixinJSBridge.on('menu:share:appmessage', function(argv) {
						shareHandle('friend');
						WeixinJSBridge.invoke('sendAppMessage', {
							"img_url" : window.shareData.imgUrl,
							"img_width" : "640",
							"img_height" : "640",
							"link" : window.shareData.sendFriendLink,
							"desc" : window.shareData.tContent,
							"title" : window.shareData.tTitle
						}, function(res) {
							_report('send_msg', res.err_msg);
						})
					});

					WeixinJSBridge.on('menu:share:timeline', function(argv) {
						shareHandle('frineds');
						WeixinJSBridge.invoke('shareTimeline', {
							"img_url" : window.shareData.imgUrl,
							"img_width" : "640",
							"img_height" : "640",
							"link" : window.shareData.sendFriendLink,
							"desc" : window.shareData.tContent,
							"title" : window.shareData.tTitle
						}, function(res) {
							_report('timeline', res.err_msg);
						});
					});

					WeixinJSBridge.on('menu:share:weibo', function(argv) {
						shareHandle('weibo');
						WeixinJSBridge.invoke('shareWeibo', {
							"content" : window.shareData.tContent,
							"url" : window.shareData.sendFriendLink,
						}, function(res) {
							_report('weibo', res.err_msg);
						});
					});
				}, false)

		function shareHandle(to) {
			var submitData = {
				module : window.shareData.moduleName,
				moduleid : window.shareData.moduleID,
				token : 'occcgv1404262269',
				wecha_id : 'oqmEBjybcKqjL-XVKsLRhuHImfko',
				url : window.shareData.sendFriendLink,
				to : to
			};
			$.post('/index.php?g=Wap&m=Share&a=shareData&token=occcgv1404262269&wecha_id=oqmEBjybcKqjL-XVKsLRhuHImfko',
							submitData, function(data) {
							}, 'json');
		}
	</script>
</body>
</html>