<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8" />
    <title>旺铺装修营销</title>
</head>
<link href="${URL_ROOT}/resources/css/main.css" rel="stylesheet" />
<script src="http://libs.baidu.com/jquery/1.9.0/jquery.min.js"> </script>
<style>
.header {
    position: relative;
    border-bottom: 1px solid #196ea5;
    text-align: center;
    width: 100%;
    height: 48px;
    font-size: 16px;
    line-height: 48px;
    background: none #1a93de;
    color: white;
    box-shadow: 0 2px 0 #116faa;
    text-shadow: 0 1px 1px rgba(11, 78, 121, 0.75);
    border: none;
}
.header .logo {
    text-decoration: none;
    margin: 2px 0 2px 22px;
    height: 44px;
    line-height: 35px;
    float: left;
    display: block;
    margin-right: 20px;
    padding: 0;
    border: 0;
    font-weight: inherit;
    font-style: inherit;
    font-size: 100%;
    font-family: "微软雅黑", "微软雅黑", "Microsoft YaHei", Helvetica, Tahoma, sans-serif;
    vertical-align: baseline;
}
.header .logo img {
    height: 44px;
}
.header ul,
ol {
    list-style: none;
}
.header li {
    float: left;
    width: 90px;
    list-style: none;
}
.header .cur {
    font-weight: bold;
    background-size: 100%;
    background-image: linear-gradient(#1a93df, #1273a8);
}
.header li a {
    display: block;
    text-align: center;
    font-size: 16px;
    font-weight: lighter;
    color: white;
    transition: all 0.5s 0;
}
.header li a:hover {
    background: #12689e;
    text-decoration: none
}

h2 {
	font-family: "微软雅黑", "微软雅黑", "Microsoft YaHei", Helvetica, Tahoma, sans-serif;
	color: #937bb8;
	 font-size: 24px;
    font-weight: normal;
}
.h2-border{
	padding:12px;
	border-bottom: 1px solid #dddddd;
}
</style>

<script>
$("ul .filter-title a").on("click",function(){
    var dataType = $(this).attr("data-type").val();
    if (dataType == "all") {

    } else if (dataType == "basic") {

    } else if (dataType == "extra") {

    }
});
</script>

<body>
    <div class="header">
        <a class="logo" href="${URL_ROOT }">
           <img src="${URL_ROOT }/resources/images/logo2.png" alt="旺铺装修营销">
        </a>
        <ul>
            <li class="cur">
                <a href="${URL_ROOT }">应用</a>
            </li>
            <li>
                <a href="http://blog.kongjishise.com"  target="_blank">帮助论坛</a>
            </li>
            
            <li>
                <a href="${URL_ROOT}/loginout"  target="_blank">退出登录</a>
            </li>
        </ul>
    </div>
    <div id="my-all-apps" class="mod-my-apps fd-hide" data-type="myapp" style="display: block;">
        <div>
        	<h2 class="h2-border">店铺装修</h2>
        </div>

        <ul class="fd-clr">
            <li class="obj-app-item"  style="width:305px;">
                <div class="link">
                    <a  class="route-link app-item"  style="width:301px;" href="${URL_ROOT}/xq"  target="_blank">
                        <div class="wrapper" style="width:285px;">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/xq_index.png) no-repeat;" class="app-icon"></dt>
                                <dd class="app-name">宝贝详情</dd>
                                <dd class="app-desc">整体而精美的详情页不仅能直接提高店铺档次,还大大激发买家们的购买欲望.</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            <li class="obj-app-item">
                <div class="link">
                    <a class="route-link app-item" href="${URL_ROOT}/main/zt"  target="_blank">
                        <div class="wrapper">
                            <dl>
                                <dt  style="background:url(${URL_ROOT}/resources/images/icon/zt_index.png) no-repeat;"  class="app-icon"></dt>
                                <dd class="app-name">酷炫主图</dd>
                                <dd class="app-desc">批量添加各类【促销标签】【水印】【边框】【自定义LOGO】【文字】到您宝贝主图上</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
            
            <li class="obj-app-item">
                <div class="link">
                    <a class="route-link app-item"  href="${URL_ROOT}/main/gl" target="_blank">
                        <div class="wrapper">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/glyx_index.png) no-repeat;"  class="app-icon"></dt>
                                <dd class="app-name">关联营销</dd>
                                <dd class="app-desc">关联推荐能以精美模板的形式把热销商品或店铺内其他商品批量安装到每个商品的描述页面。让买家在浏览宝贝的同时能看到更多的同类其他商品</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
             <li class="obj-app-item">
                <div class="link">
                    <a class="route-link app-item"  href="${URL_ROOT}/main/hb">
                        <div class="wrapper">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/kxhb_index.png) no-repeat;"  class="app-icon"></dt>
                                <dd class="app-name">酷炫海报</dd>
                                <dd class="app-desc">让你简单制作精美促销海报,各类模板</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
            <li class="obj-app-item">
                <div class="link">
                    <a class="route-link app-item"  href="javascript:alert('即将推出,敬请关注!')">
                        <div class="wrapper">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/kt_index.png) no-repeat;"  class="app-icon"></dt>
                                <dd class="app-name">免费抠图</dd>
                                <dd class="app-desc">为包年的卖家提供免费抠图</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
            
            
            
        </ul>
        
        
          <div style="padding-top: 20px;">
        	<h2 class="h2-border">店铺管理</h2>
  		 </div>
  		 
  		 
  		   <ul class="fd-clr">
            
            <li class="obj-app-item">
                <div class="link">
                    <a class="route-link app-item"  href="http://imagecopy.kongjishise.com/alicopy/picerrequest/login?accessToken=${sessionScope.aliInfo.accessToken}&memberId=${sessionScope.aliInfo.memberId}&appkey=${sessionScope.aliInfo.appKey}" target="_blank">
                        <div class="wrapper">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/copy.png) no-repeat;"  class="app-icon"></dt>
                                <dd class="app-name">宝贝一键复制</dd>
                                <dd class="app-desc">多功能全方位软件开店必备利器，帮你复制产品 搬家店铺产品,轻松开店.</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
            
        </ul>
  		 
  		  <div style="padding-top: 20px;">
        	<h2 class="h2-border">软件操作帮助</h2>
  		 </div>
  		 
  		 <ul class="fd-clr">
            
             <li class="obj-app-item"  style="width:305px;">
                <div class="link">
                    <a  class="route-link app-item"  style="width:301px;" href="http://blog.kongjishise.com"  target="_blank">
                        <div class="wrapper" style="width:285px;">
                            <dl>
                                <dt style="background:url(${URL_ROOT}/resources/images/icon/help.png) no-repeat;" class="app-icon"></dt>
                                <dd class="app-name">帮助论坛</dd>
                                <dd class="app-desc">如果有什么操作不明白的地方,点这图标</dd>
                            </dl>
                        </div>
                    </a>
                </div>
            </li>
            
            
        </ul>
    </div>
    
  
    
    
   
</body>

</html>
