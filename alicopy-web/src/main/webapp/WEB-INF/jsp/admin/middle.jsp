<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<script src="${URL_ROOT}/resources/js/underscore/underscore.js"></script>
<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<!--上传-->
<script type="text/javascript" src="${URL_ROOT}/resources/lib/uploadify/uploadify/jquery.uploadify.js"></script>
<link rel="stylesheet" type="text/css" href="${URL_ROOT}/resources/lib/uploadify/uploadify/uploadify.css" />

<!--上传-->
 <script type="text/template" id="listPxcSJ-template">
    <@  if (done) { @>
    <a id = "clear-completed" > Clear <@= done @>completed <@= done == 1 ? 'item': 'items' @></a>
    <@ } @>
    <div class="todo-count">
    <b><@= remaining @></b><@= remaining == 1 ? 'item': 'items' @>left 
    </div>
</script> 

<style>
    #divPxcSJBriefList>div{
        -moz-box-sizing: border-box;
        display: block;
        float: left;
        margin-right: 0.4%;
        margin-bottom: 5px;
        min-height: 120px;
        width: 24.5%;
    }
    #divPxcSJBriefList>div:last-child{
        clear: right;
    }
    #divPxcSJBriefList>div>.pxcsjbox{
        border:1px solid #ccc;
        padding: 3px;

    }
    .bordered{
        border:2px solid #ccc; 
    }
    #divPxcsjItems>.divItitle{
        padding: 5px;
        background-color: #eee;
    }
    #divPxcsjItems>.divIthumb{
        padding: 5px;
        min-height: 50px;
        max-height: 600px;
        overflow: scroll;
    }
    .divPXCSJItemThumb{
        position: relative;
    }
    .divRelative{
        position: relative;
    }
    .my-mask{
        color: #fff;
        position:absolute;
        left: 0;
        top: 0;
        text-align: center;
    }
    .divWFull { width: 100%; }
    .divFull{ width: 100%;  height:100%;  }

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
    .pxcsjitemimage{
        width:100%;
    }

</style>
<script type="text/template" id="pxcjs-template">
    <div id='divBorder' class='pxcsjbox' >
    <div class='divRelative'> 
    <img  src = "<@=thumbnailUrl@>" />
    <div id='divOp' class='my-mask divWFull alpha5 hidden'>
    <p> <@=name@></p>
    <p> <@=keywords @></p>
    <div>
    <button class='btn btn-default' id='btnModify' >修</button>
    <button class='btn btn-default' id = 'btnDelete' > 删</button>
    <button class='btn btn-default' id='btnUpload'>上传</button>
    <button class='btn btn-default' id='btnGenThumb'>缩</button>
    </div>
    </div>
    </div>
    </div>
</script>
<script type="text/template" id="pxcjsItem-template">
    <div id='divImage' class='divPXCSJItemThumb'> <img class='pxcsjitemimage' src = "<@=imageUrl@>" />
    <div id='divOp' class='my-mask alpha3 verticalWrap hidden divFull'>
    <div class='verticalHack'>
    <div class='verticalCnt'>
    <button class='btn btn-primary' id = 'btnDelete'> 删</button>
    <button class='btn btn-default' id='btnUp'>上</button>
    <button class='btn btn-default' id='btnDown'>下</button>
    <button class='btn btn-default' id='btnTop'>顶</button>
    <button class='btn btn-default' id='btnBottom'>底</button>
    </div>
    </div>
    </div>    
    </div>
</script>
<div id="divPxcSJ">
    <div class="row-fluid">
        <div class='span9' >
            <div id='pageTable1'></div>
        </div>
        <div  class="span3" >
            <button id='btnAdd' role="button" class="btn btn-primary" style="margin:20px 0px">        增加        </button>
        </div>
    </div>


    <div id="divPxcSJList" class="container-fluid">
        <div class="row-fluid">
            <div class="span9">

                <div id='divPxcSJBriefList' class="row-fluid">
                    <!--Sidebar content-->
                </div>
                <div><div id='pageTable2'></div><span id="spanTotal2"></span></div>
            </div>
            <div id='divPxcsjItems' class="span3 bordered">
                <div class='divItitle '>
                    <p id="pSName"> 名称:</p>
                    <p id="pSKeys"> 关键字：</p>
                    <button id="btnDeleteAll" class="btn btn-default">删除所有</button>
                    <a id="btnPreview" class="btn btn-default" target="_blank">预览</a>
                </div>
                <div class='divIthumb' id='divIthumb'>

                </div>
                <!--Body content-->
            </div>
        </div>
    </div>
</div>
<form id='formPxcSJ' method="POST" class="form-horizontal">
    <div id="myModal" class="modal hide " tabindex="-1" role="dialog" aria-labelledby="myModalLabel"
         aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                ×
            </button>
            <h3 id="myModalLabel">
                增加手机详情
            </h3>
        </div>
        <div class="modal-body text-center">
            <div class="control-group">
                <label class="control-label" for="inputName">
                    名称
                </label>
                <div class="controls">
                    <input type="text" id="inputName" placeholder="输入名称" name="name"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputKeys">
                    关键字
                </label>
                <div class="controls">
                    <input type="text" id="inputKeys" placeholder="输入关键字" name="keywords" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="selectOp_owner">
                    设计师
                </label>
                <div class="controls">
                    <select id="selectOp_owner"  name="opOwner">
                        <option value="米修">米修</option>
                        <option value="小兰">小兰</option> 
                        <option value="贝贝">贝贝</option>
                        <option value="陶少">陶少</option>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="checkIs_publish">
                    是否发布
                </label>
                <div class="controls">
                    <input type="checkbox" id="checkIs_publish" name="publish" />
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button id='btnAdd1' type="submit" class="btn btn-primary">
                增加
            </button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">
                关闭
            </button>
        </div>
    </div>
</form>
<!-- <form id='formPxcSJ' class="form-horizontal">
    <div id="myModal" class="modal hide " tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                ×
            </button>
            <h3 id="myModalLabel">
                增加手机详情
            </h3>
        </div>
        <div class="modal-body text-center">
            <div class="control-group">
                <label class="control-label" for="inputName">
                    名称
                </label>
                <div class="controls">
                    <input type="text" id="inputName" placeholder="输入名称" name="name">
                </div>
            </div>
            <div class="control-group">
                <label class="control-label" for="inputKeys">
                    关键字
                </label>
                <div class="controls">
                    <input type="text" id="inputKeys" placeholder="输入关键字" name="keys">
                </div>
            </div>
        </div>
        <div class="modal-footer">
            <button id='btnAdd1' type="submit" class="btn btn-primary">
                增加
            </button>
            <button class="btn" data-dismiss="modal" aria-hidden="true">
                关闭
            </button>
        </div>
    </div>
</form> -->
<div>
    <div id="modalUpload" class="modal hide " tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                ×
            </button>
            <h3 id="myModalLabel">
                上传文件
            </h3>
        </div>
        <div class="modal-body text-center">
            <div class="text-center">
                <input type="file" name="file_upload" id="file_upload" />
            </div>
            <div id="divUploadQueue"></div>
        </div>
        <div class="modal-footer">
            <button id='btnUpload' class="btn btn-primary" >开始上传</button>

            <button class="btn" data-dismiss="modal" aria-hidden="true">
                关闭
            </button>
        </div>
    </div>
</div>
<div id="loginModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
        <h3 id="myModalLabel">登录</h3>
    </div>
    <div class="modal-body text-center">
        <p >还没有登录，点击下面的链接进行登录</p>
        <a id="aLogin"  class="btn btn-large btn-primary"  target='_blank'>登录</a>

    </div>
    <div class="modal-footer">
        <button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
    </div>
</div>
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
            url: '${URL_ROOT}/admin/template/',
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
                    if (response.hasOwnProperty('pxcsj_add_response')) {
                        return response.pxcsj_add_response.pxcsj;
                    } else if (response.hasOwnProperty('pxcsj_update_response')) {
                        return response.pxcsj_update_response.pxcsj;
                    } else if (response.hasOwnProperty('id')) {
                        return response;
                    }
                    alert('返回数据有误');
                    return false;
                }
            }
        });
        var PxcsjCollection = Backbone.Collection.extend({
            model: PxcsjModel,
            url: '{URL_ROOT}/admin/template/',
            total_results: 0,
            initialize: function() {
                this.on('addNew', this.onAdd);
                this.on('OneRemove', this.onRemove);
            },
            setTotal: function(value) {
                this.total_results = value;
                this.trigger('totalChanged');
            },
            onRemove: function() {
                this.setTotal(this.total_results - 1);
            },
            onAdd: function() {
                this.setTotal(this.total_results + 1);
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
//        className: 'span4',
            template: _.template($('#pxcjs-template').html()),
            initialize: function() {
                this.listenTo(this.model, 'change', this.render);
                this.listenTo(this.model, 'change:thumbnailUrl', this.render);
                this.listenTo(this.model, 'remove', this.remove);
//            this.listenTo(this.model, 'destroy', this.remove);
            },
            // The DOM events specific to an item.
            events: {
                "mouseover": 'onMouseOver',
                "mouseout": 'onMouseOut',
                "click #btnModify": "modify",
                "click #btnDelete": "removeitem",
                "click #btnUpload": "upload",
                "click #btnGenThumb": "genThumb",
                "click": 'onClick'
            },
            onMouseOver: function() {
                this.$('#divOp').removeClass('hidden');
            },
            onMouseOut: function() {
                this.$('#divOp').addClass('hidden');
            },
            addView: null,
            uploadVie: null,
            render: function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },
            modify: function() {
                this.addView.modify(this.model);
            },
            removeitem: function(event) {
                event.stopPropagation();
                if (confirm('确定要删除吗？')) {
                    this.model.destroy({
                        wait: true,
                        url: '${URL_ROOT}/admin/template/'+this.model.id,
                        error: function(model, response, options) {
                            showError(response);
                        },
                        success: function() {
                            pxcsjs.trigger("OneRemove");
                        }
                    });
                }
            },
            upload: function() {
                this.uploadView.show(this.model);
            },
            genThumb: function() {
                var m = this.model;
                url = '${URL_ROOT}/admin/template/genThumb/' + this.model.id;
                $.ajax({
                    url: url,
                    type: 'POST',
                    data: {
                        id: this.model.id
                    },
                    dataType: 'json',
                    timeout: 5000,
                    error: function(jqXHR, textStatus, errorThrown) {
                        var sc = jqXHR.getResponseHeader('REQUIRES_AUTH');
                        if (sc) {
                            $('#aLogin').attr('href', sc);
                            $('#loginModal').modal('show');
                            return;
                        }
                        showError(jqXHR);
                        return;
                    },
                    success: function(data, textStatus, jqXHR) {
                        var sc = jqXHR.getResponseHeader('REQUIRES_AUTH');
                        if (sc) {
                            $('#aLogin').attr('href', sc);
                            $('#loginModal').modal('show');
                            return;
                        }
                        if (data.hasOwnProperty('error_response')) {
                            alert(data.error_response.sub_msg);
                            return;
                        }
                        m.set('thumbnailUrl', data.thumbnailUrl + '?r=' + Math.round(Math.random() * 10000));
                    }
                });
            },
            onClick: function() {
                if (!this.isSelected) {
                    this.trigger('selected', this);
                    this.select(true);
                    pxcsjs.setSelect(this.model);
                }
            },
            isSelected: false,
            select: function(value) {
                this.isSelected = value;
                if (value) {
                    this.$('#divBorder').css('border', '1px solid #444');
                } else {
                    this.$('#divBorder').css('border', '1px solid #ccc');
                }
            }
        });
        var PxcSJCollectionView = Backbone.View.extend({
            el: $('#divPxcSJBriefList'),
            items: null,
            pageSize: 4,
            initialize: function() {
                this.listenTo(pxcsjs, 'add', this.addOne);
                this.listenTo(pxcsjs, 'fetchedAll', this.renderAll);
                this.listenTo(pxcsjs, 'totalChanged', this.onTotalChanged);
                this.loadItems(1);
            },
            addOne: function(model) {
                var view = new PxcSJView({
                    model: model
                });
                view.addView = this.addView;
                view.uploadView = this.uploadView;
                this.listenTo(view, 'selected', this.onSelectedOne);
                this.listenTo(view, 'removed', this.onViewRemoved);
                var nod = view.render().$el;
                var fis = this.$el.children(":eq(0)");
                if (fis.length === 0) {
                    nod.appendTo(this.$el);
                } else {
                    nod.insertBefore(fis);
                }
            },
            addView: null,
            uploadView: null,
            renderAll: function() {
                //            for (var i = 0, length = pxcsjs.length; i < length; i++) {
                //                this.renderOne(pxcsjs.at(i));
                //            }
            },
            renderOne: function(model) {
                var view = new PxcSJView({
                    model: model
                });
                view.addView = this.addView;
                view.uploadView = this.uploadView;
                this.$el.append(view.render().el);
            },
            onTotalChanged: function(model) {
                var total = pxcsjs.total_results;
                var options = {
                    currentPage: pxcsjs.current_page,
                    totalPages: Math.ceil(total / this.pageSize),
                    alignment: 'left',
                    onPageClicked: _.bind(this.gotoPage, this)
                };
                $('#pageTable2,#pageTable1').bootstrapPaginator(options);
                $("#spanTotal2").text('共：' + pxcsjs.total_results + ' 条记录');
            },
            gotoPage: function(event, originalEvent, type, page) {
                event.stopPropagation();
                this.loadItems(page);
            },
            loadItems: function(page) {
                pxcsjs.current_page = page;
                for (var i = 0, l = pxcsjs.models.length; i < l; i++) {
                    pxcsjs.remove(pxcsjs.models[i]);
                }
                pxcsjs.fetch({
                    url: '${URL_ROOT}/admin/template/list?pageSize=' + this.pageSize + '&page=' + page,
                    success: function(model, response) {
                        pxcsjs.trigger('fetchedAll');
                    },
                    error: function() {
                        //当返回格式不正确或者是非json数据时，会执行此方法
                        alert('error2');
                    }
                });
            },
            selectedView: null,
            onSelectedOne: function(view) {
                if (this.selectedView !== null) {
                    this.selectedView.select(false);
                }
                ;
                this.selectedView = view;
            },
            onViewRemoved: function(view) {
                this.stopListening(view, 'selected');
                this.stopListening(view, 'removed');
            }
        });
        var PxcSJEditView = Backbone.View.extend({
            el: $("#formPxcSJ"),
            events: {
                submit: "checkIn" //事件绑定，绑定Dom中id为check的元素
            },
            checkIn: function(e1, e2, e3) {
                var data = {
                    name: $('#inputName').val(),
                    keywords: $('#inputKeys').val(),
                    publish:$('#checkIs_publish').prop("checked"),
                    owner:$('#selectOp_owner').val()
                };
                if (this.statu === 0) {
                    var mode1 = new PxcsjModel();
                    mode1.set(data);
                    mode1.save([], {
                        success: function() {
                        console.debug(mode1)
                            pxcsjs.add(mode1, {
                                at: 0
                            });
                            pxcsjs.trigger('addNew');
                            alert('添加成功');
                        }
                    });
                } else {
                    this.model.set(data);
                    this.model.save([], {
                        success: function() {
                            alert('修改成功');
                        }
                    });
                }
                return false;
            },
            statu: 0,
            //0 add, 1 modifyu
            setstatus: function(value) {
                this.statu = value;
                if (value === 1) {
                    $('#myModalLabel').text('修改');
                    $('#btnAdd1').text('修改');
                } else {
                    $('#myModalLabel').text('增加');
                    $('#btnAdd1').text('增加');
                }
            },
            modify: function(model) {
                this.model = model;
                this.setstatus(1);
                $('#inputName').val(model.get('name'));
                $('#inputKeys').val(model.get('keywords'));
                $('#checkIs_publish').prop("checked",model.get('publish'));
                $('#selectOp_owner').val(model.get('owner'));
                $('#myModal').modal('show');
            },
            add: function() {
                this.setstatus(0);
                $('#inputName').val("");
                $('#inputKeys').val("");
                $('#checkIs_publish').attr("checked",false);
                $('#selectOp_owner').val('');
                $('#myModal').modal('show');
            }

        });
        var UploadView = Backbone.View.extend({
            el: $('#modalUpload'),
            formData: {'JSESSIONID': '${session.id}'},
            uploadOption: {
                auto: false,
                debug: false,
                fileObjName: 'filedata',
                buttonImageURL: '${URL_ROOT}/resources/lib/uploadify/uploadify/uploadify-cancel.png',
                removeCompleted: true,
                fileTypeDesc: '拼图工厂模板文件',
                fileTypeExts: '*.pxc',
                buttonText: '选择文件',
                fileSizeLimit: '3000K',
                queueID: 'divUploadQueue',
                formData: {'JSESSIONID': '${session.id}'},
                //            'swf': '{URL_ROOT}/lib/uploadify/uploadify.swf',
                swf: '${URL_ROOT}/resources/lib/uploadify/uploadify/swfupload.swf',
                uploader: '${URL_ROOT}/admin/template/addItem'
            },
            events: {
                "click  #btnUpload": "doUpload"
            },
            initialize: function() {

            },
            show: function(model) {
                this.model = model;
                this.uploadOption.formData.id = model.get('id');
                this.uploadOption.onQueueComplete = _.bind(this.onQueueComplete, this);
                this.uploadOption.onUploadSuccess = _.bind(this.onUploadSuccess, this);
                this.uploadOption.onUploadError = _.bind(this.onUploadError, this);
                $('#file_upload').uploadify(this.uploadOption);
                $('#modalUpload').modal('show');
            },
            onQueueComplete: function(quequeData) {
//                alert('上传结束' + this.model.get('id'));
            },
            onUploadError: function(file, errorCode, errorMsg, errorString) {
                alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
            },
            onUploadSuccess: function(file, data, response) {
                var model = new PxcSJItemModel(JSON.parse(data));
                pxcsjitems.add(model);
            },
            doUpload: function() {
                $('#file_upload').uploadify('upload', '*');
            }
        });
        var PxcSJItemModel = Backbone.Model.extend({
            url: '${URL_ROOT}/admin/template/item',
            defaults: {
                id: null,
                pxcxqId: null,
                name: null,
                deleted: null,
                vip: null,
                seq: null,
                width: null,
                height: null,
                url: null,
                path: null,
                imageUrl:null,
                imagePath:null
            },
            parse: function(response, options) {
                if (options.hasOwnProperty('collection')) {
                    return response;
                } else {
                    if (response.hasOwnProperty('error_response')) {
                        alert('返回数据出错：' + response.error_response.msg);
                        return false;
                    }
                    if (response.hasOwnProperty('pxcsjitem_add_response')) {
                        return response.pxcsjitem_add_response.pxcsj;
                    } else if (response.hasOwnProperty('pxcsjitem_update_response')) {
                        return response.pxcsjitem_update_response.pxcsj;
                    } else if (response.hasOwnProperty('id')) {
                        return response;
                    }
                    alert('返回数据有误');
                    return false;
                }
            },
            doDestroy: function() {
                var _collection = this.collection;
                this.destroy({
                    wait: true,
                    url: '${URL_ROOT}/admin/template/item/'+this.id,
                    error: function(model, response) {
                        showError(response);
                    },
                    success: function(model, response) {
                        _collection.trigger("OneRemove");
                    }
                });
            },
        });
        var PxcsjItemCollection = Backbone.Collection.extend({
            model: PxcSJItemModel,
            url: '${URL_ROOT}/admin/template/item',
            total_results: 0,
            initialize: function() {
                this.on('addNew', this.onAdd);
                this.on('OneRemove', this.onRemove);
            },
            setTotal: function(value) {
                this.total_results = value;
                this.trigger('totalChanged');
            },
            onRemove: function() {
                this.setTotal(this.total_results - 1);
            },
            onAdd: function() {
                this.setTotal(this.total_results + 1);
            },
            parse: function(response) {
                this.setTotal(parseInt(response.totalElements));
                return response.content;
            }
        });
        var PxcSJItemView = Backbone.View.extend({
            template: _.template($('#pxcjsItem-template').html()),
            initialize: function() {
                this.listenTo(this.model, 'change', this.render);
                this.listenTo(this.model, 'remove', this.remove);
            },
            // The DOM events specific to an item.
            events: {
                "mouseover": 'onMouseOver',
                "mouseout": 'onMouseOut',
                "click #btnDelete": "removeitem",
                "click #btnUp": "onUp",
                "click #btnDown": "onDown",
                "click #btnTop": "onTop",
                "click #btnBottom": "onBottom"
            },
            onMouseOver: function() {
                this.$('#divOp').height(this.$el.height());
                this.$('#divOp').removeClass('hidden');
            },
            onMouseOut: function() {
                this.$('#divOp').addClass('hidden');
            },
            render: function() {
                this.$el.html(this.template(this.model.toJSON()));
                return this;
            },
            removeitem: function(event) {
                event.stopPropagation();
                if (confirm('确定要删除吗？')) {
                    this.model.doDestroy();
                }
            },
            onUp: function() {
                var index=pxcsjitems.indexOf(this.model);
                if(index>0){
                	var prevModel=pxcsjitems.at(index-1);
                	var preSeq=prevModel.get('seq');
                	prevModel.set('seq',this.model.get('seq'));
                    prevModel.save();
                    this.model.set('seq',preSeq);
                    this.model.save();
                    var pre=this.$el.prev();
                    this.$el.after(pre);
                    pxcsjitems.sort('seq');

                }
                
            },
            onDown: function() {
                var index=pxcsjitems.indexOf(this.model);
                if(index<pxcsjitems.length-1){
                	var nextModel=pxcsjitems.at(index+1);
                	var nextSeq=nextModel.get('seq');
                	nextModel.set('seq',this.model.get('seq'));
                    nextModel.save();
                    this.model.set('seq',nextSeq);
                    this.model.save();
                    var pre=this.$el.next();
                    this.$el.before(pre);
                    pxcsjitems.sort('seq');
                }
            },
            onTop: function() {
                var index=pxcsjitems.indexOf(this.model);
                if(index>0){
                	var firstSeq=pxcsjitems.at(0).get('seq');
                	for(var i=0;i<index;i++){
                		var curModel=pxcsjitems.at(i);
                		var nextModel=pxcsjitems.at(i+1);
                		curModel.set('seq',nextModel.get('seq'));
                		curModel.save();
                	}
                	this.model.set('seq',firstSeq);
                	this.model.save();
                	pxcsjitems.sort('seq');
                	var fir=this.$el.parent().children(":first");
                	fir.before(this.$el);
                }
                
                
            },
            onBottom: function() {
                var index=pxcsjitems.indexOf(this.model);
                var lastIndex=pxcsjitems.length-1;
                if(index<lastIndex){
                	var lastSeq=pxcsjitems.at(lastIndex).get('seq');
                	for(var i=lastIndex;i>index;i--){
                		var curModel=pxcsjitems.at(i);
                		var preModel=pxcsjitems.at(i-1);
                		curModel.set('seq',preModel.get('seq'));
                		curModel.save();
                	}
                	this.model.set('seq',lastSeq);
                	this.model.save();
                	pxcsjitems.sort('seq');
                	var fir=this.$el.parent().children(":last");
                	fir.after(this.$el);
                }
            }
        });
        var PxcsjItemCollectionView = Backbone.View.extend({
            el: $('#divPxcsjItems'),
            initialize: function() {
                this.listenTo(pxcsjs, 'selectItem', this.onSelectItem);
                this.listenTo(pxcsjitems, 'add', this.addOne);
            },
            events: {
                "click #btnDeleteAll": "onDeleteAll"
            },
            onSelectItem: function(model) {
                this.model = model;
                this.$("#pSName").text('名  称：' + model.get('name'));
                this.$("#pSKeys").text('关键字：' + model.get('keywords'));
                this.$("#btnPreview").attr('href', "${URL_ROOT}/admin/template/preview/" + model.id);
                this.loadItems();
            },
            addOne: function(model) {
                var view = new PxcSJItemView({
                    model: model
                });
                var nod = view.render().$el;
                this.$('#divIthumb').append(nod);
            },
            onDeleteAll: function() {
                if (confirm('确定删除所有吗？')) {
                    for (var i = pxcsjitems.models.length - 1; i >= 0; i--) {
                        pxcsjitems.models[i].doDestroy();
                    }
                }
            },
            loadItems: function(page) {
                for (var i = 0, l = pxcsjitems.models.length; i < l; i++) {
                    pxcsjitems.remove(pxcsjitems.models[i]);
                }
                pxcsjitems.fetch({
                    url: '${URL_ROOT}/admin/template/itemlist/' + this.model.id,
                    success: function(model, response) {
                        pxcsjitems.trigger('fetchedAll');
                    },
                    error: function() {
                        //当返回格式不正确或者是非json数据时，会执行此方法
                        alert('error1');
                    }
                });
            }
        });
        var managerPxcSJView = Backbone.View.extend({
            el: $('#divPxcSJ'),
            initialize: function() {
//                this.listenTo(pxcsjs, 'add', this.addOne);
//                this.listenTo(pxcsjs, 'fetchedAll', this.renderAll);
            },
            addView: null,
            events: {
                "click #btnAdd": "add"
            },
            add: function() {
                this.addView.add();
            }
        });
        var pxcsjs = new PxcsjCollection();
        var pxcsjitems = new PxcsjItemCollection();
        pxcsjitems.comparator = 'seq';
        var pxcsjItemsView = new PxcsjItemCollectionView();
        var addView = new PxcSJEditView();
        var uploadView = new UploadView();
        var listView = new PxcSJCollectionView();
        listView.addView = addView;
        listView.uploadView = uploadView;
        var managerView = new managerPxcSJView();
        managerView.addView = addView;
        Backbone.history.start();
        Backbone.emulateHTTP = true;
    });
</script>
