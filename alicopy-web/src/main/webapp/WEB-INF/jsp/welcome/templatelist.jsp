<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<link  rel="stylesheet" href="${URL_ROOT}/resources/css/my-tbimage.css"/>
<link rel="stylesheet" href="${URL_ROOT}/resources/css/my-wizard-steps.css"/>
<!--<link rel="stylesheet" href="{URL_ROOT}/css/demo.css"/>-->
<link href="${URL_ROOT}/resources/js/jqueryplugin/zTree/css/zTreeStyle.css" rel="stylesheet">
<script src="${URL_ROOT}/resources/js/jqueryplugin/zTree/jquery.ztree.core-3.5.js"></script>
<script src="${URL_ROOT}/resources/js/underscore/underscore.js"></script>
<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<style>
    #DivQing {
        margin-left:auto;
        margin-right:auto;
        text-align: center;
    }
    #Pnote {
        font-size:26px;
        line-height: 40px;
        color:#666;
    }
</style>

<div class="container">

    <div id='DivQing'>
        <p id='Pnote' style='color: #356;font-size: 16px'> 
            推荐使用&nbsp;
            <a href="http://firefox.com.cn/" target="_blank">
                <span style="display:inline-block; background:url(${URL_ROOT}/resources/img/131017/firefox.png) top left no-repeat; width:31px; height:30px;  vertical-align:middle;"></span>
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
    <div class="m-wizard-steps">
        <ul>
            <li class="m-wizard-current firstli"><span>第一步</span>：选择商品</li>
            <li class=""><span>第二步</span>：选择模板</li>
            <li class=""><span>第三步</span>：编辑，导出数据包</li>
        </ul>
    </div>
    <div>
        <form class="form-inline " role="form" >
            <span>选择分类：</span>
            <div id='divSellercat' class="input-append">
                <input class="span2" id="appendedInput" type="text"  placeholder="选择分类"/>
                <button class="btn" id="appendedInputBtn" type="button"><span class="caret"></span></button>

            </div>
            <span>是否上架：</span>
            <select id="isInventorySelect" class="span2">
                <option value="1">出售中商品</option>
                <option value="2">全部商品</option>
            </select>
            <span>商品名称：</span>
            <input type="text" class="form-control" id="itemNameInput" placeholder="输入名称"/>
            <button id="btnLoadItems" type="button" class="btn btn-default"  >搜索</button>
        </form>
        <div id='pageTable1'></div>
        <table id="itemTable" class="table table-bordered table-condensed table-hover" >
            <thead>
                <tr class='my-tblist-tr'>
                    <th >主图1</th>
                    <th>名称</th>
                    <th>价格</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
            </tbody>
        </table>
        <div id='pageTable2'></div>
        <div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h3 id="myModalLabel">登录</h3>
            </div>
            <div class="modal-body text-center">
                <p >还没有登录，点击下面的链接进行登录</p>
                <a id="aLogin"  class="btn btn-large btn-primary"  target='_blank'>登录</a> 
                <a id="aLogin"  class="btn btn-large " href='${URL_ROOT}/template/PXCSJ/selectPXC' target='_blank'>只想浏览模板</a>
                <p>&nbsp;</p>
                <p class=" text-info " style="font-size:18px;"><i class="icon  icon-info-sign"></i>提示，<strong>手机详情工厂</strong>需要单独定购</p>
                <a class="btn btn-info" href="http://fuwu.taobao.com/ser/detail.htm?service_code=FW_GOODS-1927470" target="_blank">定购</a>
            </div>
            <div class="modal-footer">
                <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
            </div>
        </div> 
    </div>

    <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
        <ul id="treeDemo" class="ztree" style="margin-top:0; width:160px;"></ul>
    </div>
</div>
<script >
    $(function() {
        function loadItems(params) {
            var pageNum = params.page;
            var url = '';
            if (parseInt(params.isOnSale) === 1) {
                url = '${URL_ROOT}/taobaoimage/SellerCat/onsaleItems';
            } else {
                url = '${URL_ROOT}/taobaoimage/SellerCat/all';
            }
            $.ajax({
                url: url,
                type: 'POST',
                data: params,
                //dataType: 'xml',
                timeout: 5000,
                error: function(jqXHR, textStatus, errorThrown) {
                    var sc = jqXHR.getResponseHeader('REQUIRES_AUTH');
                    alert(sc);
                    alert('error' + jqXHR);
                    return;
                },
                success: function(data, textStatus, jqXHR) {
                    var sc = jqXHR.getResponseHeader('REQUIRES_AUTH');
                    if (sc) {
                        $('#aLogin').attr('href', sc);
                        $('#myModal').modal('show');
                        return;
                    }

                    var table = $('#itemTable tbody');
                    table.empty();

                   
                           
                    var list=data.toReturn;
                    
                    _.each(list,function(obj){
                    	var html=$("<tr class='my-tblist-tr'></tr>");
                    	var imageUrl=obj.imageList.length==0?"":obj.imageList[0].size64x64URL;
                    	var picH = $("<td ><img src='" + imageUrl + "'/></td>");
                    	var titleH = $("<td><a target='_blank' href='" + obj.detailsUrl + "'>" + obj.subject + "</a></td>");
                    	var priceH = $("<td>" + obj.retailPrice + "</td>");
                    	var ltd = $('<td></td>');
                    	var btnH = $("<a class='btn btn-large btn-primary'  >制作</a>").attr('offerId', obj.offerId).appendTo(ltd);
                    	var selectPxcUrl = '${URL_ROOT}/secondStep?offerId=' + obj.offerId + '&title=' + encodeURIComponent(obj.subject) + '&price=' + obj.retailPrice + '&pic_url=' + encodeURIComponent(imageUrl);
                        btnH.attr('href', selectPxcUrl);
                    	html.append(picH).append(titleH).append(priceH).append(ltd);
                    	html.appendTo(table);
                    })
                    
                    
                    var total=data.total;
                    var pageSize = 20;
                    if (total > pageSize) {
                        var options = {
                            currentPage: pageNum,
                            totalPages: Math.ceil(total / pageSize),
                            alignment: 'right',
                            onPageClicked: handlePageClick
                        };
                        var page1 = $('#pageTable1');
                        page1.bootstrapPaginator(options);
                        $('#pageTable2').bootstrapPaginator(options);
                    } else {
                        $('#pageTable1').empty();
                        $('#pageTable2').empty();
                    }
                }
            });
        }
        var curPara = {
            seller_cname: '所有分类',
            seller_cids: 0,
            isOnSale: 1,
            title: '',
            page: 1
        };
        function curHash() {
            if (curPara.title === '') {
                return 'goto/' + curPara.seller_cname + '/' + curPara.seller_cids + '/' + curPara.isOnSale + '/' + curPara.page;
            } else {
                return 'goto/' + curPara.seller_cname + '/' + curPara.seller_cids + '/' + curPara.isOnSale + '/' + curPara.title + '/' + curPara.page;
            }
        }
        $('#btnLoadItems').click(function(event) {
            event.stopPropagation();
            curPara.isOnSale = $('#isInventorySelect').val();
            curPara.title = $('#itemNameInput').val();
            if (curPara.title === '输入名称') {
                curPara.title = '';
            }
            //获得分类值
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            if (zTree && zTree.getSelectedNodeIds()[0]) {
                curPara.seller_cids = zTree.getSelectedNodeIds()[0];
                if (curPara.seller_cids === -1) {
                    curPara.seller_cids = 0;
                }
            }
            curPara.seller_cname = $('#appendedInput').val();
            curPara.page = 1;
//            loadItems(curPara);
            appRouter.navigate(curHash());
        });
        function handlePageClick(event, originalEvent, type, page) {
            event.stopPropagation();
            curPara.page = page;
//            loadItems(curPara);
            appRouter.navigate(curHash());
        }

        var AppRouter = Backbone.Router.extend({
            routes: {
                "": "gotoFirst",
                'goto/:seller_cname/:seller_cids/:isOnSale/:page': "gotoPage",
                'goto/:seller_cname/:seller_cids/:isOnSale/:title/:page': "gotoPaget"
            },
            initialize: function() {

            },
            gotoFirst: function() {
                this.gotoPaget('所有分类', 0, 1, "", 1);
            },
            gotoPage: function(seller_cname, seller_cids, isOnSale, page) {
                this.gotoPaget(seller_cname, seller_cids, isOnSale, "", page);
            },
            gotoPaget: function(seller_cname, seller_cids, isOnSale, title, page) {
                curPara.seller_cname = seller_cname;
                curPara.seller_cids = seller_cids;
                $('#appendedInput').val(seller_cname);
                curPara.isOnSale = isOnSale;
                $('#isInventorySelect').val(isOnSale);
                curPara.title = title;
                $('#itemNameInput').val(title);
                curPara.page = page;
                loadItems(curPara);
            }
        });

        var appRouter = new AppRouter();
        Backbone.history.start();

        //初始分类树
        var _this = this;

        function initTree(items) {
            var setting = {
                view: {
                    dblClickExpand: false,
                    selectedMulti: false
                },
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                callback: {
                    onClick: onClick
                }
            };

            var zNodes = [];
            var rootitem = {id: -1, pId: 0, name: "所有分类", isParent: false};
            zNodes.push(rootitem);
            $.each(items, function(index, value) {
                var item = {};
                item.id = value.cid;
                item.pId = value.parent_cid;
                item.name = value.name;
                zNodes.push(item);
            });


            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }
        // 同步发送请求
        function sendService() {

            $.ajax({
                url: "${URL_ROOT}/taobaoimage/SellerCat",
                type: "GET",
                //dataType: "json",
                contentType: "application/json; charset=utf-8",
                async: false,
                success: function(resp, status, jqXHR) {
                    if (resp) {
                        var items = resp;
                        initTree(items);
                    } else {
                        var sc = jqXHR.getResponseHeader('REQUIRES_AUTH');
                        $('#aLogin').attr('href', sc);
                        $('#myModal').modal('show');
                        return;
                    }
                }
            });
        }
        //分类树点击事件
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            var nodes = zTree.getSelectedNodes();
            var v = "";
            nodes.sort(function compare(a, b) {
                return a.id - b.id;
            });
            for (var i = 0, l = nodes.length; i < l; i++) {
                v += nodes[i].name + ",";
            }
            if (v.length > 0)
                v = v.substring(0, v.length - 1);
            var appendedInputObj = $("#appendedInput");
            appendedInputObj.val(v);
            //console.debug(zTree.getSelectedNodeIds())//获得分类id的数组.eg:[426000528, 426172043]
        }
        //分类树弹出层函数
        function showMenu() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            if (!zTree)
                sendService();
            var appendedInputObj = $("#appendedInput");
            var appendedInputOffset = $("#appendedInput").offset();
            $('#treeDemo').width($('#divSellercat').width() - 12);
            $("#menuContent").css({left: appendedInputOffset.left + "px", top: appendedInputOffset.top + appendedInputObj.outerHeight() + "px"}).slideDown("fast");
            $("body").bind("mousedown", onBodyDown);
        }
        //分类树隐藏层函数
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }

        function onBodyDown(event) {
            if (!(event.target.id == "appendedInputBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
                hideMenu();
            }
        }

        $("#appendedInputBtn").on("click", showMenu);

       
    });
</script>

<script src="${URL_ROOT}/resources/js/placeholderIE.js"></script>

