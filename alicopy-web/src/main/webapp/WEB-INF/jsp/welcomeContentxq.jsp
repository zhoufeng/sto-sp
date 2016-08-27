<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<link rel="stylesheet" type="text/css" href="${URL_ROOT }/pub/css/welcome.css" />
<script>
//    $(document).ready(function() {
//        $("#linkXQ").hover(function() {
//            setActiveTab(1);
//        });
//        $("#linkZT").hover(function() {
//            setActiveTab(0);
//        });
//        $("#linkGL").hover(function() {
//            setActiveTab(2);
//        });
//        $("#linkHB").hover(function() {
//            setActiveTab(3);
//        });
//        setActiveTab(0);
//
//    });
//    var divs=new Array("#DivZTMS","#DivXQMS","#DivGLMS","#DivHBMS");
//    function setActiveTab(value){
//        for(var i=0;i<4;i++){
//            if(value==i){
//                $(divs[i]).show();
//            }else{
//                $(divs[i]).hide();
//            }
//        }
//        var po=108+value*232;
//        var css1=po+'px 0px';
//        $('#DivBtnIndicator').css("background-position",css1);
//    }
</script>
<!--提示-->
<jsp:include page="components/subAndAdvertis.jsp"></jsp:include>
<div id='DivContent'>
    <div id="DivBtnHeadLine"></div>

    <div id='DivNavi'>
        <div id="DivNaviCenter">
            <a   class="aActive" ><img src="${URL_ROOT }/pub/img/131017/xqbtn1.png" style="vertical-align:middle;"> <span>宝贝详情</span> </a> 
        </div>
    </div>
    <!--    <div id='DivBtnMain'> 
            <a id='linkZT' class='btnMain'  href='/index.php/Main/zt' target="_blank"> <span>美化主图</span> </a> 
            <a id='linkXQ' class='btnMain'  href='/index.php/Main/xq' target="_blank"> <span>宝贝详情</span> </a> 
    
            <a id='linkGL' class='btnMain'  href='/index.php/Main/gl' target="_blank" > <span>关联营销</span> </a> 
            <a id='linkHB' class='btnMain' href='/index.php/Main/hb' target="_blank" > <span>制作海报</span> </a> 
        </div>-->
</div>
<!--指示器-->
<!--<div id='DivBtnIndicator'></div>-->
<!-- 功能描述页头-->


<!-- 详情-->
<div id='DivXQMS' class='msClass'>
    <div class="DivfuncBtn"><a href="${URL_ROOT }/main/xq" target="_blank">开始制作详情</a></div>
    <div class='DivWhy2'>
        <p class="whytitle">为什么要重视详情？</p>
        <p class="whytxt">因为90%的买家最关注的是详情，整体而精美的详情页不仅能直接提高店铺档次,还大大增强买家们的购买欲望。</p>
    </div>
    
<!--    <div class='DivWhy'>
        <p>为什么要重视详情？</p>
        <p>因为90%的买家最关注的是详情，整体而精美的详情页不仅能直接提高店铺档次,还大大增强买家们的购买欲望。</p>
    </div>-->
    <div class='DivDiscraptor'>
        <p class='Pmsheader'>选择模板</p>
    </div>
    <script>
        function gotoPage(divPage,urlPage){
            //$(divPage).html('<div class="meta-item-loading"></div>');
            $(divPage).load(urlPage);
        }
    </script>
    <div id="divXQItems" class='msImageClass'>

        <jsp:include page="components/divXQItems.jsp"></jsp:include>
    </div>
<!--    <div class='DivDiscraptor'>
        <p class='Pmsheader'>功能描述</p>
        <div id='DivXQMSText' class='mstextClass' >
            <ul class='msulClass'>
                <li>用模板快速合成详情图片，使宝贝详情美观，大方，有品质</li>
                <li>高品质，丰富的模板</li>
                <li>提供大量的精美素材</li>            
                <li>丰富的表现形式</li>            
                <li>直接上传至淘宝图片空间</li>

            </ul>
        </div>
    </div>-->
    <p class='Pmsheader'>操作视频</p>
    <p><strong style="font-size:36px;">宝贝详情操作(15分钟)</strong></p>
    <p>
        <embed
            type="application/x-shockwave-flash"
            src="http://player.youku.com/player.php/sid/117450920/v.swf"
            id="movie_player"
            name="movie_player2"
            bgcolor="#FFFFFF"
            quality="high"
            allowfullscreen="true"
            flashvars="isShowRelatedVideo=false&showAd=0&show_pre=1&show_next=1&isAutoPlay=false&isDebug=false
            &UserID=&winType=interior&playMovie=true&MM
            Control=false&MMout=false&RecordCode=1001,1002,1003,1004,1005,1006,2001,3001,3002,3003,3004,3005,3
            007,3008,9999"
            pluginspage="http://www.macromedia.com/go/getflashplayer"
            width="950"
            height="600"/>
    </p>

</div>
