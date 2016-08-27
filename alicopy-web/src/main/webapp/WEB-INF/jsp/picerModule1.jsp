<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!-- saved from url=(0014)about:internet -->
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en"> 
    <!-- 
    Smart developers always View Source. 
    
    This application was built using Adobe Flex, an open source framework
    for building rich Internet applications that get delivered via the
    Flash Player or to desktops via Adobe AIR. 
    
    Learn more about Flex at http://flex.org 
    // -->
    <head>
        <title>${title} </title>
        <meta name="google" value="notranslate" />         
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <!-- Include CSS to eliminate any default margins/padding and set the height of the html element and 
             the body element to 100%, because Firefox, or any Gecko based browser, interprets percentage as 
             the percentage of the height of its parent container, which has to be set explicitly.  Fix for
             Firefox 3.6 focus border issues.  Initially, don't display flashContent div so it won't show 
             if JavaScript disabled.
        -->
        <style type="text/css" media="screen"> 
            html, body  { height:100%; overflow:hidden;}
            html{overflow-y:hidden;}
            body { margin:0; padding:0; overflow:auto; text-align:center; 
                   background-color: #ffffff; }   
            object:focus { outline:none; }
            #flashContent { display:none; }
        </style>

        <!-- Enable Browser History by replacing useBrowserHistory tokens with two hyphens -->
        <!-- BEGIN Browser History required section -->
        <link rel="stylesheet" type="text/css" href="${URL_ROOT }/pub/flash/history/history.css" />
        <script type="text/javascript" src="${URL_ROOT }/pub/flash/history/history.js"></script>
        <!-- END Browser History required section -->  

        <script type="text/javascript" src="${URL_ROOT }/pub/flash/swfobject.js"></script>
        <script type="text/javascript">
            function request(paras)
            { 
                var url = location.href; 
                var paraString = url.substring(url.indexOf("?")+1,url.length).split("&"); 
                var paraObj = {} 
                for (i=0; j=paraString[i]; i++){ 
                    paraObj[j.substring(0,j.indexOf("=")).toLowerCase()] = j.substring(j.indexOf("=")+1,j.length); 
                } 
                return paraObj;
                var returnValue = paraObj[paras.toLowerCase()]; 
                if(typeof(returnValue)=="undefined"){ 
                    return ""; 
                }else{ 
                    return returnValue; 
                } 
            }
            // For version detection, set to min. required Flash Player version, or 0 (or 0.0.0), for no version detection. 
            var swfVersionStr = "11.5.0";
            // To use express install, set to playerProductInstall.swf, otherwise the empty string. 
            var xiSwfUrlStr = "${URL_ROOT }/pub/flash/playerProductInstall.swf";
            var flashvars =request();
            
            var params = {};
            params.quality = "high";
            params.bgcolor = "#ffffff";
            params.allowscriptaccess = "sameDomain";
            params.allowfullscreen = "true";
            params.wmode='window';
            var attributes = {};
            attributes.id = "TryIdea";
            attributes.name = "TryIdea";
            attributes.align = "middle";
            swfobject.embedSWF(
            "${swf}" , "flashContent", 
            "100%", "100%", 
            swfVersionStr, xiSwfUrlStr, 
            flashvars, params, attributes);
            // JavaScript enabled so display the flashContent div in case it is not replaced with a swf object.
            swfobject.createCSS("#flashContent", "display:block;text-align:left;");
        </script>
        <script type="text/javascript" src="http://a.tbcdn.cn/apps/isvportal/securesdk/securesdk.js" id="J_secure_sdk_script" data-appkey="${ppkey}"></script>
        <style type="text/css" media="screen">#flashContent {visibility:hidden}#flashContent {display:block;text-align:left;}</style>

    </head>
    <body>
        <!-- SWFObject's dynamic embed method replaces this alternative HTML content with Flash content when enough 
             JavaScript and Flash plug-in support is available. The div is initially hidden so that it doesn't show
             when JavaScript is disabled.
        -->
        <div id="flashContent">
            <p>
                为了运行拼图工厂,需要安装 版本为 11.5.0 以上的FLASH播放器，请点击下面的图标进行安装。某些浏览器可能无法安装成功，请换用火狐浏览器. 
            </p>
            <script type="text/javascript"> 
                var pageHost = ((document.location.protocol == "https:") ? "https://" : "http://"); 
                document.write("<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
                    + pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='安装 Adobe Flash player' style='display:inline' /></a>" ); 
            </script> 
        </div>

        <noscript>
            <object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%" id="TryIdea">
                <param name="movie" value="${swf}" />
                <param name="quality" value="high" />
                <param name="bgcolor" value="#ffffff" />
                <param name="allowScriptAccess" value="sameDomain" />
                <param name="allowFullScreen" value="true" />
                <!--[if !IE]>-->
                <object type="application/x-shockwave-flash" data="${swf}"  width="100%" height="100%">
                    <param name="quality" value="high" />
                    <param name="bgcolor" value="#ffffff" />
                    <param name="allowScriptAccess" value="sameDomain" />
                    <param name="allowFullScreen" value="true" />
                    <!--<![endif]-->
                    <!--[if gte IE 6]>-->
                    <p> 
                        Either scripts and active content are not permitted to run or Adobe Flash Player version
                        11.5.0 or greater is not installed.
                    </p>
                    <!--<![endif]-->
                    <a href="http://www.adobe.com/go/getflashplayer">
                        <img src="http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif" alt="Get Adobe Flash Player" style='display: inline'/>
                    </a>
                    <!--[if !IE]>-->
                </object>
                <!--<![endif]-->
            </object>
        </noscript>

    </body>

</html>
