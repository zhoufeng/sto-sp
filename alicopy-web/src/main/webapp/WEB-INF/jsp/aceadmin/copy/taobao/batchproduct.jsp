<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="false" buffer="300kb" %>
<script type="text/template" id="itemcate">
<input type="checkbox" class="item-check"><label class="item-label"><@=name@></label>
</script>
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/merge.css" />
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/combobox-min.css" />
<style>

.icon-must, .icon-add, .icon-add-disabled, .icon-help, .icon-del-pic, .icon-info, .icon-menu-fold, .icon-menu-unfold, .icon-fold, .icon-warn, .icon-more, .icon-error, .icon-title, .icon-mixbatch, .icon-private, .icon-feedback, .icon-steppay, .icon-new, .icon-buyer-protect, .img-notice-icon, .img-suggest, .icon-page-fold, .icon-page-unfold {
    background: url("http://img.china.alibaba.com/cms/upload/offer/postoffer/postoffer-icon-v8.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}
</style>

<div class="page-content">
	
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->

			<div class="row-fluid">
				<div class="span12">
					<div class="widget-box">
						<div class="widget-header widget-header-blue widget-header-flat">
							<h4 class="lighter">淘宝复制阿里</h4>

							<div class="widget-toolbar">
								<label> <small class="green"> <b>Validation</b>
								</small> <input id="skip-validation" type="checkbox"
									class="ace ace-switch ace-switch-4"> <span class="lbl"></span>
								</label>
							</div>
						</div>

						<div class="widget-body">
							<div class="widget-main">
								<div id="fuelux-wizard" class="row-fluid"
									data-target="#step-container">
									<ul class="wizard-steps">
										<li data-target="#step1" class="active"><span
											class="step">1</span> <span class="title">输入要复制的地址</span></li>

										<li data-target="#step2" class=""><span class="step">2</span>
											<span class="title">选择类目</span></li>

										<li data-target="#step3" class=""><span class="step">3</span>
											<span class="title">发布结果</span></li>

										
									</ul>
								</div>

								<hr>
								<div class="step-content row-fluid position-relative"
									id="step-container">
									<div class="step-pane active" id="step1">
										<!-- <h3 class="lighter block green">Enter the following
											information</h3> -->

										<form class="form-horizontal" id="sample-form">
											
										
											<div class="form-group has-info">
												<label for="inputInfo"
													class="col-sm-2 control-label no-padding-right">淘宝或天猫多个网址(用enter键分隔):</label>

												<div class="col-sm-10" >
													<span class="block input-icon input-icon-right"> <textarea
														 id="taobaourl" class="autosize-transition form-control" > </textarea><i
														class="icon-info-sign"></i> </span>
												</div>
												
												
												<div class="help-block col-xs-12 col-sm-reset inline"></div>
											</div>
											<div class="form-group has-info">
											<div class="col-sm-2 control-label  no-padding-right" ></div>
											<div class="col-sm-10">
													<span class="block input-icon input-icon-right"> <input type="checkbox"  name="picStatus"   style="margin-left:10px" />同时复制详情图片到空间(否则只复制详情图片连接)</span>
												</div>
											</div>
											<jsp:include page="../selfdefind.jsp"></jsp:include>
										</form>
									</div>
									<div class="step-pane" id="step2" >
									
									<div class="category-module" style="margin: 0 auto;width: 800px;">
									<div class="category-ui category-grid width-4 view-custom" style="display: block;">
									<div class="grid-header">
									<div class="header-search"><input type="text" class="search-key"><div class="search-suggest">
									<ul class="suggest-box"></ul></div><div class="search-buttons"><button class="btn-search">查询</button><button class="btn-back">返回</button></div></div></div>
									<div class="grid-body"><div class="body-custom fd-clr">
									<div class="custom-box">
									<ul data-level="1" class="custom-item" style="display: block;"></ul></div>
									<div class="custom-box"><ul data-level="2" class="custom-item" style="display: none;"></ul></div>
									<div class="custom-box"><ul data-level="3" class="custom-item" style="display: none;"></ul></div>
									<div class="custom-box"><ul data-level="4" class="custom-item last" style="display: none;"></ul></div><ul class="custom-list"></ul></div><div class="body-search"><ul class="search-list"></ul></div></div><div class="grid-footer"><div class="f-button"></div></div></div></div>
									</div>
									<div class="step-pane" id="step3">
										<div class="center">
											<h3 class="green">发布结果</h3>
											<div class="ret"></div>
										</div>
									</div>
								</div>
								<hr>
								<div class="row-fluid wizard-actions">
									<button class="btn btn-prev" disabled="disabled">
										<i class="icon-arrow-left"></i> 上一步
									</button>

									<button class="btn btn-success btn-next" data-last="完成">
										下一步<i class="icon-arrow-right icon-on-right"></i>
									</button>
								</div>
							</div>
							<!-- /widget-main -->
						</div>
						<!-- /widget-body -->
					</div>
				</div>
			</div>
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
		<script src="${URL_ROOT}/resources/ace/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->
		<script src="${URL_ROOT}/resources/ace/assets/js/fuelux/fuelux.wizard.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.validate.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/additional-methods.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/bootbox.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.maskedinput.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.autosize.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/select2.min.js"></script>
		<script src="${URL_ROOT}/resources/js/bussiness/ali/alicate.js?d=6"></script>
		<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
		<style src="${URL_ROOT}/resources/css/jquery.loadmask.css"></style>
		<script src="${URL_ROOT}/resources/js/loadmask/jquery.loadmask.js?d=2"></script>
		<script src="${URL_ROOT}/resources/js/common/json2.js"></script>
		<!-- ace scripts -->

<script type="text/javascript">
			jQuery(function($) {
				aliList();
				$('textarea[class*=autosize]').autosize({append: "\n"});
				$('[data-rel=tooltip]').tooltip();
				$(".select2").css('width','200px').select2({allowClear:true})
				.on('change', function(){
					$(this).closest('form').validate().element($(this));
				}); 
			
				var $validation = false;
				$('#fuelux-wizard').ace_wizard().on('change' , function(e, info){
					if(info.step == 1 &&info.direction=='next') {
					var url=$("#taobaourl").val();
						if(url==""){
							alert("请输入淘宝或者天猫地址");
							return false;
						}
					}else if(info.step==2&&info.direction=='next'){
					if(!AliCate.categoryModuleView.catsId){
						alert("请先选择类目!");
						return false;
					}else{
				var url=$("#taobaourl").val();
				var urlList=url.split("\n");
				var lastUrlList=[];
				_.each(urlList,function(url){
				 	if(url!=""){
				 		lastUrlList.push(url.trim());
				 	}
				});
			
				var savPic=$("input[name='picStatus'][type|='checkbox']:checked");
				var picStatus=false;
				if(savPic.size()==1)picStatus=true;
					//var categoryId=this.properties.categoryId;
				var data={"catId":AliCate.categoryModuleView.catsId,"picStatus":picStatus};
				var params=getExtraParams();
					$.extend(params,data);
					var lparams={};
					lparams.urlList=lastUrlList;
					lparams.params=params;
					var ajaxParam=JSON.stringify(lparams,function(key,value){
						if(typeof value==="boolean"){
						 	return value;
						}
						if(value==null||value==""){
							return undefined;
						}
						return value;
					});
					
					var _maskDiv=$(".widget-body");
					_maskDiv.mask("正在请求数据,请稍后!");
					$.ajax({
						type:"POST",
						contentType:"application/json",
						data:ajaxParam,
						url:URL_ROOT+"/top/finalproductcopy/saveTaobaoItemList",
						dataType:"json",
						success:function(data,textStatus,jqXHR){
							_maskDiv.unmask();
							$("#step3 .ret").empty();
							$("#step3 .ret").append('<div  class="alert alert-block alert-success col-xs-12" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong></div>')
							if(data.msg){
								alert(data.msg);
								return;
							}
							_.each(data,function(item){
								if(item.errorCode){
									$("#step3 .ret div.alert").append('复制失败!'+'|'+'名称:'+item.name+"|原因:"+item.errorMsg+'<br>');
								}else{
									$("#step3 .ret div.alert").append('复制成功!|'+'名称:'+item.name+'|'+
									'链接地址:'+'<a target="_blank" href="'+item.url+'">宝贝链接</a>|'+
									'修改属性:'+'<a target="_blank" href="'+item.editurl+'">修改链接</a>'+'<br>');
								}
							});
						},
						error:function(data){
						$("##step3 .ret").empty();
							_maskDiv.unmask();
							alert("网络不给力!");
						}
					});
				
					}
							return true;
					}
				}).on('finished', function(e) {
					bootbox.dialog({
						message: "Thank you! Your information was successfully saved!", 
						buttons: {
							"success" : {
								"label" : "OK",
								"className" : "btn-sm btn-primary"
							}
						}
					});
				}).on('stepclick', function(e){
					//return false;//prevent clicking on steps
				});
				$('#modal-wizard .modal-header').ace_wizard();
				$('#modal-wizard .wizard-actions .btn[data-dismiss=modal]').removeAttr('disabled');
			})
		</script>
		