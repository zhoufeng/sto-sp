<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" autoFlush="false" buffer="50kb" %>

<script type="text/template" id="item">
    <td class="center">
        <label>
            <input type="checkbox" class="ace" name="topitem_checkbox"/>
            <span class="lbl"></span>
        </label>
    </td>
    <td>
        <img style="height:45px;" src="<@=picUrl@>"/>
    </td>
    <td>
        <a href="<@=detailUrl@>" target=‘_blank’><@=title@></a>
    </td>
    <td><@=price@></td>
    <td><@=num@></td>


</script>
<div class="page-content">
    <div id="search_div"  class="col-xs-12">
        <form class="form-horizontal" id="search" role="form" >

                <div role="form" class="form-horizontal">
                    <div class="row">
                    <div class="control-label col-xs-2" style="text-align: left">多个商品网址(按Enter键分隔):<i
                            style="color:red">(*)</i>:
                    </div>
                    <div class="col-xs-4">
                        <span class="block input-icon input-icon-right"> <textarea
                                id="search_url_input" class="autosize-transition form-control"> </textarea><i
                                class="icon-info-sign"></i> </span>
                    </div>
                    <div class="col-xs-2 form-inline">
                        <button id="search_btn" type="button"
                                class="form-control btn btn-primary" data-loading-text="正在复制">复 制<i
                                class="icon-search icon-on-right bigger-110"></i></button>
                    </div>
                    <!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
                    <div class="col-xs-4 form-inline">
                        <input type="checkbox" name="savePic"/>
                        同时复制图片到空间(否则只复制图片连接)
                    </div>
                    </div>
                </div>
        </form>
        </div>
        <div class="col-xs-12" id="desc_1" >

        </div>

    <div class="col-xs-12 space-12"></div>
    <div class="col-xs-12" id="desc_1">

    </div>
    <jsp:include page="selfdefind.jsp"></jsp:include>

</div>

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>
<script src="${URL_ROOT}/resources/js/common/json2.js"></script>

<script type="text/javascript">
    jQuery(function ($) {
        function init() {
            if (window.location.href.indexOf("url=") > -1) {
                $("#search_url_input").val(window.location.search.substring(5, window.location.search.length));
            }
        }

        init();
        $("#search_btn").on("click", function () {
            searchUrl();
        })

        function searchUrl() {
            var url=$("#search_url_input").val();
            var urlList=url.split("\n");
            var lastUrlList=[];
            _.each(urlList,function(url){
                if(url!=""){
                    lastUrlList.push(url.trim());
                }
            });


            if (url == ""||urlList.length==0)alert("请输入url");

            //if(!url.startWith("http"))alert("请输入合法的url地址!")
            var savPic = $("input[name='savePic'][type|='checkbox']:checked");
            var picStatus = false;
            if (savPic.size() == 1)picStatus = true;
            var params = getExtraParams();
            $.extend(params, {"urlList": lastUrlList, "picStatus": picStatus});
            var ajaxParam = JSON.stringify(params, function (key, value) {
                if (typeof value === "boolean") {
                    return value;
                }
                if (value == null || value == "") {
                    return undefined;
                }
                return value;
            });
            $("#search_btn").button("loading");
            $.ajax({
                type: "POST",
                contentType: "application/json",
                data: ajaxParam,
                url: URL_ROOT + "/top/finalproductcopy/batchSaveAliItem",
                dataType: "json",
                success: function (items, textStatus, jqXHR) {
                    $("#desc_1").empty();
                    _.each(items, function (item) {
                        if (item.errorCode) {
                            $("#desc_1").append('复制失败!' + '|' + '名称:' + item.name + "|原因:" + item.errorMsg + '<br>');
                        } else {
                            $("#desc_1").append('复制成功!|' + '名称:' + item.name + '|' +
                                    '链接地址:' + '<a target="_blank" href="' + item.url + '">宝贝链接</a>|' +
                                    '修改属性:' + '<a target="_blank" href="' + item.editurl + '">修改链接</a>' + '<br>');
                        }
                    });
                    //@TODO
                    $("#search_btn").button('reset');
                }, error: function (data) {
                    $("#search_btn").button('reset');
                    alert(data.errorMsg);
                }
            });
        }
    });

</script>