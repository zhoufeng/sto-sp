<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="/tags/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <link rel="stylesheet" type="text/css" href="${URL_ROOT}/pub/css/reset.css" />
        <link rel="stylesheet" type="text/css" href="${URL_ROOT}/pub/css/main.css" />
        <title>拼图工厂</title>
        <script type="text/javascript" src="${URL_ROOT}/pub/js/jquery-1.7.1.js" charset="utf-8"></script>
        <script>
            //加这个，是因为IE下，会出现 auto未定义
            function auto(){};
        </script>
        <script type="text/javascript" src="http://a.tbcdn.cn/apps/isvportal/securesdk/securesdk.js" id="J_secure_sdk_script" data-appkey="${appKey} "></script>
    </head>
    <body>
        <!--页头-->
        <div  id='DivBannerWrap'>
            <div id='DivBanner' >
                <a href="<?php echo URL_ROOT?>/index.php" target="_blank"><img src="${URL_ROOT}/pub/img/131017/logofull.png" name="imgLogo" id='imgLogo' /></a>
                <div id='DivHelp'>
                    <div id='DivUserInfo' >
                       		<c:if test="${not empty username}">${username}</c:if>您好！
                    </div>
                    <!--                    <div style="height: 48px"></div>-->
                    <div>
                        <a  href="/index.php/Help" target="_blank" >帮助</a>
                        <span>&nbsp; <a  href="http://bangpai.taobao.com/group/thread/15160674-280151746.htm" target="_blank">淘帮派</a></span>
                        <span>&nbsp; <a  href="/index.php/Help#fee" target="_blank">定购指南</a></span>
                        <span>&nbsp; <a  href="http://fuwu.taobao.com/ser/detail.htm?service_id=25539" target="_blank">订购</a></span>
                    </div>
                </div>
            </div>
            <!--   <div id='DivBannerBottom'> </div>-->
        </div>
        <div id='DivQing'>
            <p id='Pnote' style='color: #356;font-size: 16px'> 
                拼图工厂推荐使用&nbsp;
                <a href="http://firefox.com.cn/" target="_blank">
                    <span style="display:inline-block; background:url(${URL_ROOT }/pub/img/131017/firefox.png) top left no-repeat; width:31px; height:30px;  vertical-align:middle;"></span>
                    <span style="font-size: 18px">火狐浏览器 </span> 
                </a>。
                其它浏览器的设置请参考
                <a href="http://bangpai.taobao.com/group/thread/15160674-280239861.htm?spm=0.0.0.0.M07dav" target="_blank">
                    <span style="font-size: 18px">关于安装</span>    
                </a>。 
                <!--    <span > <a href="http://fuwu.taobao.com/ser/detail.htm?service_id=25539" target="_blank">订购</a>七天内无条件退款</span>-->
            </p>
            <!--                <p id='Pqing'> 亲<?php if(isset($username) && ($username!=''))echo ',',$username ?>,选择您要处理的内容 </p>-->
        </div>
        <c:if test="${not empty content}"><jsp:include page="welcomeContentxq.jsp" /></c:if>
        <div id="footer">
            <p><a href="${URL_ROOT}/index.php">拼图工厂</a></p>
            <p id="cr">&copy; 2012 云邦科技</p>
        </div>
    </body>
</html>
