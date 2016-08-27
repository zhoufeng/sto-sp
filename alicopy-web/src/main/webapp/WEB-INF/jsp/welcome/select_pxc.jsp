<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script src="${URL_ROOT }/resources/js/underscore/underscore.js"></script>
<script src="${URL_ROOT }/resources/js/backbone/backbone.js"></script>
<link rel="stylesheet" href="${URL_ROOT }/resources/css/my-wizard-steps.css"/>
<link rel="stylesheet" href="${URL_ROOT }/resources/css/my-searchlist.css"/>
<style>
    #divPxcSJList>div:last-child{
        clear: right;
    }
    .pxcsjbox{
        -moz-box-sizing: border-box;
        display: block;
        float: left;
        margin-right: 2px;
        margin-bottom: 15px;
        min-height: 30px;
        width:192px;
    }
    .divRelative{
        position: relative;
        padding:3px;
        border:1px solid #ccc;
    }
    .divRelative>.divCon{
        position: relative;
        width:184px;
    }
    .bordered{
        border:1px solid #ccc; 
    }
    .my-mask{
        color: #fff;
        position:absolute;
        left: 0;
        top: 0;
        width: 100%;height: 40px;
        /*text-align: center;*/

    }
    .mycenter{
        width: auto;
        margin-left: auto;
        margin-right: auro;
        text-align: center;
    }
    .divFull{ width: 100%;  height:100%;  }

    .mytable{
        border: 2px solid #FF975E
    }
    .mytable>tbody>tr>td{
        border-top: 0px #fff;
    }
    /*透明的解决方案*/
    .alpha3{
        background:rgba(0,0,0,0.3);
        filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#4c000000,endColorstr=#4c000000);zoom:1;
    }
    :root .alpha3{filter:none\9;}/*for IE9*/
    .alpha5{background:rgba(0,0,0,0.5);filter:progid:DXImageTransform.Microsoft.gradient(startColorstr=#7f000000,endColorstr=#7f000000);zoom:1;}
    :root .alpha5{filter:none\9;}/*for IE9*/
    /*垂直居中的方法，需要三个DIV，一个最外层，中间一层，最内层是*/
    .verticalWrap{
        margin: 0 auto;
        display:table;
    }
    .verticalHack{
        display:table-cell;
        vertical-align:middle;
        *position:absolute;*top:50%;
    }
    .verticalCnt{
        *position:relative;
        *top:-50%;
    }

    .marginrl5{
        margin-left: 5px;
        margin-right: 5px;
    }

</style>

<script type="text/template" id="pxcjs-template">
    <div class='divRelative'>
    <div class='divCon '>
    <img  src = "<@= thumbnailUrl @>" />
    <div id='divOp' class='my-mask alpha5 hidden verticalWrap'>
    <div class='verticalHack'>
    <div class='verticalCnt marginrl5'>
    <a class='btn btn-primary btn-small pull-left' id='btnSelect' target="_blank" href='${URL_ROOT }/main/xq?id=<@= id @>' >选中模板</a>
    <a class="btn btn-default btn-small pull-right" id='btnPreview' target='_blank' href="${URL_ROOT }/admin/template/preview/<@= id @>">预览</a>
    </div>
    </div>
    </div>
    </div>
    </div>
</script>
<script type="text/template" id="FLSelectorItem-template">
    <a><@= name @></a>
</script>

<div class="container">
   <%--  <div class="m-wizard-steps">
        <ul>
            <li class="m-wizard-done firstli "><span>第一步</span>：选择商品</li>
            <li class="m-wizard-current"><span>第二步</span>：选择模板</li>
            <li class=""><span>第三步</span>：编辑，导出数据包</li>
        </ul>
    </div>

    <table class='table mytable'>
        <tbody>
            <tr>
                <td width='20%' style="text-align: center; vertical-align: middle;
                    background-color: #eef">
                    <?php  if(!empty($item['num_iid'])){  ?>
                    <img src="<?php echo $item['pic_url']; ?>" alt="商品图片"/>
                    <?php  }  ?>
                </td>
                <td style="background-color: #eef; vertical-align: middle;font-size:20px;text-align: left;">
                    <div>
                        <?php  if(!empty($item['num_iid'])){  ?>
                        <p style='font-size:16px;'>名称：<?php echo $item['title']; ?></p>
                        <p style='font-size:16px;'>价格：<?php echo $item['price']; ?></p>
                        <?php  }else{  ?>
                        <p style='font-size:30px;'>没有商品</p> 
                        <?php  }  ?>
                    </div>
                </td>
                <td width='20%' style="text-align: center; vertical-align: middle;background-color: #FF975E">
                    <?php  if(!empty($item['num_iid'])){  ?>
                    <h3 class="icon icon-check" style="color:#fff">已选择商品</h3>
                    <div><span>&nbsp</span></div>
                    <div><a class="btn btn-success" href="<?php echo URL_ROOT; ?>/index.php">重选</a></div>
                    <?php  }else{  ?>
                    <p style='font-size:30px;'>没有选择商品</p> 
                    <div><span>&nbsp</span></div>
                    <div><a class="btn btn-success" href="<?php echo URL_ROOT; ?>/index.php">选择</a></div>
                    <?php  }  ?>
                </td>
            </tr>im
        </tbody>
    </table> --%>
    <div id="divPxcSJ">
        <div class="my-searchlist" id="divFLSelector" >
            <dl class="">
                <dt><i></i>行业分类</dt>
                <dd>
                    <ul id="ulHYFL">

                    </ul>
                    <div class="clearing"></div>
                </dd>
            </dl>
        </div>
        <div id='pageTable1'></div>
        <div id="divPxcSJList" class="row-fluid mycenter">
            <div class="pxcsjbox">
                <div class='divRelative'>
                    <div class='divCon' >
                        <div class='verticalWrap' style="height:418px">
                            <div class='verticalHack'>
                                <div class='verticalCnt marginrl5'>
                                    
                                    <a  target="_blank" href='${URL_ROOT }/main/xq' >
                                        <div><i class='icon icon-plus' style='color:#eee;font-size:100px;'></i></div>     
                                        <p style='font-size:20px;color:#888;font-weight: bold'>自定义</p>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--模板列表-->
        <div id='pageTable2'></div><div id="spanTotal2" style="text-align: center;"></div>
        <script type="text/javascript">
            $(function() {
            _.templateSettings = {
			    interpolate: /\<\@\=(.+?)\@\>/gim,
			    evaluate: /\<\@([\s\S]+?)\@\>/gim,
			    escape: /\<\@\-(.+?)\@\>/gim
			};
                function showError(response) {
                    var msg = '未知错误';
                    if (response.hasOwnProperty('responseText')) {
                        try {
                            var obj = JSON.parse(response.responseText).error_response;
                            msg = '错误码：' + obj.code + '\r\n错　误：' + obj.msg
                                    + '\r\n子　码：' + obj.sub_code + '\r\n子错误：' + obj.sub_msg;
                        } catch (err) {
                            msg += response.responseText;
                        }
                    }
                    alert(msg);
                }
                var PxcsjModel = Backbone.Model.extend({
                    url: '<?php echo URL_ROOT; ?>/index.php/template/PXCSJ/pxcsj',
                    defaults: {
                        id: null,
		                name: null,
		                keywords: null,
		                clickCount: null,
		                deleted: null,
		                vip: null,
		                publish: null,
		                fullUrl: null,
		                fullPath: null,
		                thumbnailUrl: null,
		                thumbnailPath: null,
		                previewUrl: null,
		                previewPath: null,
		                gmtCreate: null,
		                owner: null,
		                creator: null,
		                modifier: null
                    },
                    parse: function(response, options) {
                        if (options.hasOwnProperty('collection')) {
                            return response;
                        } else {
                            if (response.hasOwnProperty('error_response')) {
                                alert('返回数据出错：' + response.error_response.msg);
                                return false;
                            }
                            if (response.hasOwnProperty('id')) {
                                return response;
                            }
                            alert('返回数据有误');
                            return false;
                        }
                    }
                });
                var PxcsjCollection = Backbone.Collection.extend({
                    model: PxcsjModel,
                    url: '${URL_ROOT}/admin/template/onsale',
                    total_results: 0,
                    initialize: function() {
                    },
                    setTotal: function(value) {
                        this.total_results = value;
                        this.trigger('totalChanged');
                    },
                    parse: function(response) {
                        this.setTotal(parseInt(response.totalElements));
                        return response.content;
                    },
                    selectItem: null,
                    setSelect: function(model) {
                        this.selectItem = model;
                        this.trigger('selectItem', model);
                    }
                });
                var PxcSJView = Backbone.View.extend({
                    className: 'pxcsjbox',
                    template: _.template($('#pxcjs-template').html()),
                    initialize: function() {
                        this.listenTo(this.model, 'remove', this.remove);
                    },
                    events: {
                        "mouseover": 'onMouseOver',
                        "mouseout": 'onMouseOut'
                    },
                    onMouseOver: function() {
                        this.$('#divOp').removeClass('hidden');
                    },
                    onMouseOut: function() {
                        this.$('#divOp').addClass('hidden');
                    },
                    render: function() {
                        this.$el.html(this.template(this.model.toJSON()));
                        return this;
                    }
                });
                var PxcSJCollectionView = Backbone.View.extend({
                    el: $('#divPxcSJ'),
                    flsView: null,
                    initialize: function() {
                        this.listenTo(this.model.pxcsjs, 'add', this.addOne);
                        this.listenTo(this.model.pxcsjs, 'totalChanged', this.onTotalChanged);
                        this.flsView = new FLSelectorCollectionView({model: this.model});
                        //            fls.set();
                        this.listenTo(this.flsView, 'itemSelected', this.queryFl);
                        //this.loadItems(1);
                    },
                    addOne: function(model) {
                        var view = new PxcSJView({
                            model: model
                        });
                        var nod = view.render().$el;
                        nod.appendTo(this.$('#divPxcSJList'));
                    },
                    setTotal: function(value) {
                        this.total_results = value;
                        this.trigger('totalChanged');
                    },
                    onTotalChanged: function(model) {
                        var total = this.model.pxcsjs.total_results;
                        var options = {
                            currentPage: this.model.current_page,
                            totalPages: Math.max(Math.ceil(total / this.model.pageSize), 1),
                            alignment: 'center',
                            onPageClicked: _.bind(this.gotoPage, this)
                        };
                        $('#pageTable2,#pageTable1').bootstrapPaginator(options);
                        $('#spanTotal2').text('共：'+options.totalPages+"页  " + this.model.pxcsjs.total_results + ' 条记录');
                    },
                    gotoPage: function(event, originalEvent, type, page) {
                        event.stopPropagation();
                        this.model.current_page = page;
                        this.navigate1();
                    },
                    loadItems: function() {
                        for (var i = 0, l = this.model.pxcsjs.models.length; i < l; i++) {
                            this.model.pxcsjs.remove(this.model.pxcsjs.models[i]);
                        }
                        var data = {
                            pageSize: this.model.pageSize,
                            page: this.model.current_page
                        };
                        if (this.model.selectedItem !== null) {
                            data.fl = this.model.selectedItem.get('name');
                        }
                        this.model.pxcsjs.fetch({
                            url: '${URL_ROOT}/admin/template/list',
                            data: data,
                            success: function(model, response) {
                                //                    this.trigger('fetchedAll');
                            },
                            error: function() {
                                //当返回格式不正确或者是非json数据时，会执行此方法
                                alert('error');
                            }
                        });
                    },
                    navigate1: function() {
                        var str = '';
                        if (this.model.selectedItem !== null) {
                            var nn = this.model.selectedItem.get('name');
                            str = 'fl/' + encodeURIComponent(nn) + '/';
                        }
                        str += 'page/' + this.model.current_page;
                        this.router.navigate(str, {trigger: true});
                    },
                    selectedModel: null,
                    queryFl: function(model) {
                        this.model.selectedItem = model;
                        this.model.current_page = 1;
                        this.navigate1();
                    }
                });

                var FLSelectorModel = Backbone.Model.extend({
                    defaults: {
                        name: ''
                    }
                }
                );

                var FLSelectorCollection = Backbone.Collection.extend({
                    model: FLSelectorModel

                });

                var FLSelectorView = Backbone.View.extend({
                    tagName: 'li',
                    template: _.template($('#FLSelectorItem-template').html()),
                    events: {
                        'click': 'onClick'
                    },
                    isSelected: false,
                    select: function(value) {
                        this.isSelected = value;
                        if (value) {
                            this.$('a').addClass('hover');
                        } else {
                            this.$('a').removeClass('hover');
                        }
                    },
                    render: function() {
                        this.$el.html(this.template(this.model.toJSON()));
                        return this;
                    },
                    onClick: function() {
                        if (!this.isSelected) {
                            this.trigger('selected', this);
                            this.select(true);
                        }
                    }
                });

                var FLSelectorCollectionView = Backbone.View.extend({
                    el: $('#divFLSelector'),
                    initialize: function() {
                        //this.collection = new FLSelectorCollection();
                        this.listenTo(this.model.flSelectors, 'add', this.addOne);
                        this.listenTo(this.model, 'selectedItemChanged', this.onSelectedItemChange);
                        this.renderAll();
                    },
                    renderAll: function() {
                        var coll = this.model.flSelectors;
                        for (var i = 0; i < coll.length; i++) {
                            this.addOne(coll.at(i));
                        }

                    },
                    views: [],
                    findViewsByModel: function(model) {
                        if (model === null)
                            return null;
                        var n = model.get('name');
                        return _.find(this.views, function(view) {
                            return view.model.get('name') === n;
                        });
                    },
                    onSelectedItemChange: function() {
                        if (this.selectedView !== null) {
                            this.selectedView.select(false);
                        }
                        this.selectedView = this.findViewsByModel(this.model.selectedItem);
                        if (this.selectedView !== null) {
                            this.selectedView.select(true);
                        }

                    },
                    addOne: function(model) {
                        var view = new FLSelectorView({
                            model: model
                        });
                        var nod = view.render().$el;
                        nod.appendTo(this.$('#ulHYFL'));
                        this.views.push(view);
                        this.listenTo(view, 'selected', this.selectOne);
                    },
                    selectedView: null,
                    selectOne: function(view) {
                        if (this.selectedView !== null) {
                            this.selectedView.select(false);
                        }
                        this.selectedView = view;
                        this.trigger('itemSelected', view.model);
                    }

                });

                var DataProxy = Backbone.Model.extend({
                    pageSize: 5,
                    initialize: function() {
                        this.selectedItem = null;
                        this.current_page = 1;
                        var fls = [];
                        var ffs = Array('全部', '女装', '男装', '童装', '箱包', '鞋类', '母婴', '数码', '家电', '户外', '食品', '珠宝首饰', '家居', '化妆', '文化玩乐');
                        while (ffs.length > 0) {
                            fls.push({name: ffs.shift()});
                        }
                        this.flSelectors = new FLSelectorCollection(fls);
                        this.pxcsjs = new PxcsjCollection();
                    },
                    setSelectedItem: function(item) {
                        this.selectedItem = item;
                        this.trigger('selectedItemChanged');
                    }
                });
                var AppRouter = Backbone.Router.extend({
                    routes: {
                        "": "gotoFirst",
                        'page/:page': "gotoPage",
                        'fl/:fl/page/:page': "gotoFLPage"
                    },
                    initialize: function() {
                        this.datas = new DataProxy();
                        this.listView = new PxcSJCollectionView({model: this.datas});
                        this.listView.router = this;
                    },
                    gotoFirst: function() {
                        this.datas.current_page = 1;
                        this.datas.setSelectedItem(null);
                        this.listView.loadItems();
                    },
                    gotoPage: function(page) {
                        this.datas.current_page = page;
                        this.datas.setSelectedItem(null);
                        this.listView.loadItems();
                    },
                    gotoFLPage: function(fl, page) {
                        this.datas.setSelectedItem(this.datas.flSelectors.findWhere({name: fl}));
                        this.datas.current_page = page;
                        this.listView.loadItems();
                    }
                });



                //    var pxcsjs = new PxcsjCollection();


                var appRouter = new AppRouter();

                Backbone.history.start();
            });

        </script>
    </div>
</div>