<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!--[if lt IE 7]>      <html class="no-js lt-ie9 lt-ie8 lt-ie7"> <![endif]-->
<!--[if IE 7]>         <html class="no-js lt-ie9 lt-ie8"> <![endif]-->
<!--[if IE 8]>         <html class="no-js lt-ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html class="no-js"> <!--<![endif]-->
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>${title}</title>
        <meta name="description" content="">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <!-- Place favicon.ico and apple-touch-icon.png in the root directory -->

        <link rel="stylesheet" href="${URL_ROOT}/resources/css/bootstrap232/ie/normalize.css">
        <link rel="stylesheet" href="${URL_ROOT}/resources/css/bootstrap232/css/bootstrap.css">
        <link rel="stylesheet" href="${URL_ROOT}/resources/css/bootstrap232/css/bootstrap-responsive.css" > 
        <link rel="stylesheet" href="${URL_ROOT}/resources/lib/google-code-prettify/prettify.css">
        <!--[if lt IE 9]>
             <script src="{URL_ROOT}/js/html5shiv.js"></script>
        <![endif]-->
        <link rel="stylesheet" href="${URL_ROOT}/resources/css/bootstrap232/ie/font-awesome.min.css">
        <!--[if IE 7]>
            <link rel="stylesheet" href="{URL_ROOT}/css/bootstrap232/ie/font-awesome-ie7.min.css">
        <![endif]-->
        <link rel="stylesheet" href="${URL_ROOT}/resources/js/jqueryplugin/jquery-ui/custom-theme/jquery-ui-1.10.3.custom.css">
        <!--[if lt IE 9]>
            <link rel="stylesheet" href="{URL_ROOT}/js/jqueryplugin/jquery-ui/custom-theme/jquery.ui.1.10.3.ie.css">
        <![endif]-->
        <script src="${URL_ROOT}/resources/js/jquery-1.11.0.js"></script>
        <!--        <style>
                    body{
                        background-image:url({URL_ROOT}/img/bodybg.png);
                    }
                </style>-->
    </head>
    <body>
        <!-- Begin Navbar -->
        <div class="navbar navbar-inverse navbar-static-top">
            <div class="navbar-inner">
                <div class="container" >
                    <div>
                        <a class="brand" style="padding:6px 20px 4px 20px" href="${URL_ROOT}" ><img src="${URL_ROOT}/resources/img/sjxqlogo.png" alt="详情工厂"></a>

                    </div>
                    <ul class="nav navbar-nav">  
                        <li><a href="${URL_ROOT}/index.php/template/PXCSJ/selectPXC">浏览模板</a></li>
                    </ul>
                    <!--                    <div class="navbar-header">
                                            <button data-target=".nav-collapse" data-toggle="collapse" class="btn btn-navbar collapsed" type="button">
                                                <span class="icon-bar"></span>
                                                <span class="icon-bar"></span>
                                                <span class="icon-bar"></span>
                                            </button>
                                            <a class="brand" href="{URL_ROOT}/index.php" ><b>手机详情工厂</b></a>
                                        </div>-->
                    <div class="nav-collapse collapse pull-right">
                        <!--                  
                        <ul class="nav navbar-nav">  
                                                <li ><a href="#" >首页</a></li>
                                                <li><a href="#">选商品</a></li>
                                                <li><a href="#">选模板</a></li>
                                            </ul>-->
                        <ul class="nav">
                            <li><a href="http://bangpai.taobao.com/group/thread/15160674-289084022.htm" target="_blank">帮助</a></li>
                            <li><a href="http://fuwu.taobao.com/ser/detail.htm?service_code=FW_GOODS-1927470" target="_blank">订购</a></li>
                            <li class="dropdown">
                                <a class="dropdown-toggle" data-toggle="dropdown">联系客服</a>
                                <ul class="dropdown-menu">
                                    <li><a href="http://www.taobao.com/webww/ww.php?ver=3&amp;touid=freetop_gl%3Ahuahua&amp;siteid=cntaobao&amp;status=1&amp;charset=utf-8" target="_blank">客服1<img alt="点击这里给我发消息" border="0" src="http://amos.alicdn.com/realonline.aw?v=2&amp;uid=freetop_gl%3Ahuahua&amp;site=cntaobao&amp;s=1&amp;charset=utf-8" /></a></li>
                                    <li><a href="http://www.taobao.com/webww/ww.php?ver=3&amp;touid=freetop_gl%3Axiaopin1&amp;siteid=cntaobao&amp;status=1&amp;charset=utf-8" target="_blank">客服2<img alt="点击这里给我发消息" border="0" src="http://amos.alicdn.com/realonline.aw?v=2&amp;uid=freetop_gl%3Axiaopin1&amp;site=cntaobao&amp;s=1&amp;charset=utf-8" /></a></li>
                                    <li><a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=2e51aaf8b2082eff77471923fdf8db6fcdbbcd79e706b5052bbb9aa5761b93e9">QQ群<img border="0" src="http://pub.idqqimg.com/wpa/images/group.png" alt="拼图工厂技术交流群" title="拼图工厂技术交流群"></a></li>
                                </ul>
                            </li>
                            <li class="dropdown">
                                <a href="#" class="dropdown-toggle" data-toggle="dropdown">

                                    <i class="icon icon-user">

                                    </i> 
                                    <!--{ $us='未登录';
                                        if(isset($tbloginInfo) && $tbloginInfo->isValid() ){
                                            $us=$tbloginInfo->userName();
                                        }  
                                        echo $us;
                                    }-->
                                    <i class="icon icon-chevron-down"></i>
                                </a>
                                <ul class="dropdown-menu">
                                    <!--{ 
                                            if(isset($tbloginInfo) && $tbloginInfo->isValid() ){ }-->
                                    <li><a href="${URL_ROOT}/index.php/tbuser/Login/logout">退出</a></li>
                                    <!--{   }else{    }-->
                                    <li><a href="${URL_ROOT}/index.php/tbuser/Login/login">登录</a></li>
                                    <!--{   }    }-->
                                    <!--                                    <li class="divider"></li>-->
                                    <!--                                    <li><a href="#">关于</a></li>-->
                                </ul>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
        </div><!-- /.navbar -->
        <jsp:include page="${includejsp}"></jsp:include>
        <!--{ if(!(isset($nofoot) && $nofoot===true)){ }-->
        <div class="container">
            <div class="bottom">
                <div class='page-header'></div>
                <div class="row" >
                    <div class="col-lg-12"> 
                        <p class="text-center">&copy;2014 云邦科技</p>
                    </div>
                </div>
            </div>
        </div>
        <!--{ } }-->

        <!--[if lt IE 9]>
             <script src="{URL_ROOT}/js/respond.min.js"/>
        <![endif]-->

        <script src="${URL_ROOT}/resources/js/modernizr-2.6.2.min.js"></script>
        <script src="${URL_ROOT}/resources/css/bootstrap232/js/bootstrap.min.js"></script>
        <script src="${URL_ROOT}/resources/js/plugins.js"></script>
        <script src="${URL_ROOT}/resources/js/json2.js"></script>
        <script src="${URL_ROOT}/resources/js/jqueryplugin/jquery-ui/jquery-ui-1.10.3.custom.min.js"></script>
        <script src="${URL_ROOT}/resources/lib/google-code-prettify/prettify.js"></script>
        <script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>
    </body>
</html>

