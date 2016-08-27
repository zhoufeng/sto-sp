<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="false" buffer="500kb" %>
<script type="text/template" id="item">
<input type="checkbox" class="item-check"><label class="item-label"><@=name@></label>
</script>
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/merge.css" />
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/combobox-min.css" />
<style>

.icon-must, .icon-add, .icon-add-disabled, .icon-help, .icon-del-pic, .icon-info, .icon-menu-fold, .icon-menu-unfold, .icon-fold, .icon-warn, .icon-more, .icon-error, .icon-title, .icon-mixbatch, .icon-private, .icon-feedback, .icon-steppay, .icon-new, .icon-buyer-protect, .img-notice-icon, .img-suggest, .icon-page-fold, .icon-page-unfold {
    background: url("http://img.china.alibaba.com/cms/upload/offer/postoffer/postoffer-icon-v8.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}


.ali-main-content{
	margin: 0 auto;
    width: 990px;
     font-family: "宋体";
    line-height: 22px;
}
.ali-layout {
    background: none repeat scroll 0 0 #fff;
    border: 1px s	olid #d7d7d7;
    border-radius: 5px;
    margin: 8px 0;
    padding: 8px 1px;
    width: 986px;
}
.ali-layout::after {
    clear: both;
    content: " ";
    display: block;
    height: 0;
}
.ali-tit {
    border-bottom: 1px solid #e4e4e4;
    height: 24px;
    line-height: 24px;
    margin-bottom: 10px;
    padding: 0 20px 11px;
     line-height: 1.1;
}
.ali-tit h3 {
    color: #666;
    float: left;
    font-size: 14px;
    font-weight: 700;
}
.viewcase-link {
    margin-left: 16px;
   
}
.fd-left {
    float: left;
    height:50%; margin-bottom:-12px;
}
.fd-right {
    float: right;
}
.content-con{
	color: #444;
	font-family: "宋体";
    line-height: 22px;

}
.content-con .form-line:after {
    clear: both;
    content: "";
    display: block;
    height: 0;
}
.content-con .form-line {
    margin: 4px 0;
    width: 100%;
}
.content-con .form-title {
    float: left;
    font-weight: 700;
    padding: 0 0 0 10px;
    text-align: right;
    width: 120px;
}
.content-con .form-title span {
    padding-left: 10px;
}
.icon-must {
    background-position: 0 3px;
    padding-left: 10px;
    font-weight: 700;
}
.content-con .form-context {
    float: left;
    padding: 0 30px 0 0;
    width: 822px;
}
.mod-offer-post-title .info-title-txt {
    margin-right: 8px;
    width: 370px;
}
.content-con .txt {
    border: 1px solid #a0a0a0;

    padding-left: 4px;
    vertical-align: middle;
}
button, input, select, textarea {
    font-size: 100%;
}

.product-speca-props:after, .mod-offer-post-properties .product-props:after, .mod-offer-post-properties .pro-item:after, .mod-offer-post-properties .pro-d:after {
    clear: both;
    content: " ";
    display: block;
    height: 0;
}
.mod-offer-post-properties .product-props, .mod-offer-post-properties .product-custom-props {
    background: none repeat scroll 0 0 #f9f9f9;
    border: 1px solid #eee;
    border-radius: 5px;
    line-height: 25px;
    padding: 10px 7px;
}
.fd-clr:after {
    clear: both;
    content: " ";
    display: block;
    height: 0;
}
.mod-offer-post-properties .pro-item {
    float: left;
    line-height: 24px;
    padding: 3px 0;
}
.fd-clr {
}

.mod-offer-post-properties .pro-t {
    float: left;
    text-align: right;
    width: 120px;
}

body:nth-of-type(1) .mod-offer-post-properties .pro-form {
    float: left;
}
.mod-offer-post-properties .pro-form {
    margin-right: 4px;
}
.mod-offer-post-properties .span-item {
    float: left;
    list-style: outside none none;
    padding-right: 15px;
}
.mod-offer-post-properties .pro-item span.diy-prop {
    height: 26px;
    padding: 0 2px;
    width: 136px;
}
.mod-offer-post-properties .span-item::after {
    clear: both;
    display: block;
    height: 0;
}
.mod-offer-post-properties .speca-props-ctrl {
    float: left;
    height: 26px;
    margin-right: 16px;
}
.submod-product-speca-props .product-speca-props, .submod-product-custom-props .product-custom-props {
    background: none repeat scroll 0 0 #f9f9f9;
    border: 1px solid #eee;
    border-radius: 5px;
    padding: 5px 10px;
}

.mod-offer-post-trade-info .trade-tit {
    float: left;
    font-weight: 700;
    padding-left: 10px;
    text-align: right;
    width: 120px;
}

.mod-offer-post-trade-info .trade-box {
    float: left;
    margin: 0;
    width: 800px;
}
.mod-offer-post-trade-info .trade-box {
    background: none repeat scroll 0 0 #fafafa;
    border: 1px solid #f0f0f0;
    border-radius: 5px;
    margin: 4px 30px;
    padding: 5px 10px;
}

.mod-offer-post-uppic .oup-preview {
    float: left;
    position: relative;
    width: 100%;
    z-index: 10;
}
.mod-offer-post-uppic .imgs {
    height: 112px;
    overflow: hidden;
}

.mod-offer-post-uppic .imgs li {
    float: left;
    position: relative;
    width: 144px;
}
.mod-offer-post-uppic .img-cont {
    background-position: 0 -139px;
    border: 1px solid #babcbd;
    cursor: pointer;
    height: 110px;
    position: relative;
    width: 110px;
}
.alicow, .offerscan-box .offerscan-star, .offerscan-box .offerscan-star span, .dot, .assistant .err-list li, .mod-offer-post-uppic .img-cont, .no-pic, .sys-info .dot {
    background: url("http://img.china.alibaba.com/cms/upload/offer/postoffer/postoffer-bg-v4.png") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
}

.mod-offer-post-uppic .full .img-cont {
    background-image: none;
    overflow: hidden;
}
.mod-offer-post-uppic .img-cont {
    background-position: 0 -139px;
    border: 1px solid #babcbd;
    cursor: pointer;
    height: 110px;
    position: relative;
    width: 110px;
}
.mod-offer-post-uppic .img-cont {
    cursor: pointer;
}
.mod-offer-post-uppic .full .cont-img {
    display: inline;
}
.mod-offer-post-uppic .cont-img {
    display: none;
    left: 50%;
    position: absolute;
    top: 50%;
}

.mod-offer-post-properties .diy-prop-ctrl {
    border-top: 1px solid #eee;
    line-height: 25px;
    margin-top: 4px;
    padding: 3px 0;
}

.mod-offer-post-properties .add-diy-prop {
    margin-right: 10px;
}
.icon-add {
    background-position: 0 -15px;
    display: inline-block;
    padding-left: 20px;
}
.submod-product-speca-props .add-defined-values {
    white-space: nowrap;
}
.mod-offer-post-properties input.diy-prop-name {
    width: 95px;
}

.mod-offer-post-properties .del-diy-prop {
    margin-left: 4px;
}
.pro-item span.diy-prop {
    height: 26px;
    padding: 0 2px;
    width: 136px;
}

.submod-product-speca-props input.speca-text {
    border: 1px solid #7e9db9;
    height: 25px;
    line-height: px;
    padding-left: 4px;
    font-size:11px;
    width: 80px;
}

.mod-offer-post-properties .pro-item a.del-lnk {
    visibility: hidden;
}

.mod-offer-post-properties .pro-item span.diy-prop-hover {
    -moz-border-bottom-colors: none;
    -moz-border-left-colors: none;
    -moz-border-right-colors: none;
    -moz-border-top-colors: none;
    background-color: #ececec;
    border-color: #aeaeae;
    border-image: none;
    border-style: solid;
    border-width: 0 1px 1px 0;
    height: 25px;
    width: 135px;
}
.mod-offer-post-properties span.diy-prop-hover a.del-lnk {
    visibility: visible;
}
.mod-offer-post-picture-matching {
    margin-top: 8px;
}

.mod-offer-post-picture-matching .form-context {
    background: none repeat scroll 0 0 #f9f9f9;
    border: 1px solid #eee;
    border-radius: 5px;
    padding: 5px 10px;
    position: relative;
    width: 800px;
}
.mod-offer-post-picture-matching .matching-pic-list {
    margin: 10px 0;
}
.mod-offer-post-picture-matching .matching-pic-list li {
    float: left;
    text-align: center;
    width: 78px;
}
.mod-offer-post-picture-matching .matching-pic-list li {
    text-align: center;
}

.mod-offer-post-picture-matching .matching-pic-list .img-con {
    background: none repeat scroll 0 0 #f5f5f5;
    border: 1px solid #b9bbbc;
    height: 66px;
    margin-left: 3px;
    position: relative;
    width: 66px;
}
.mod-offer-post-picture-matching .matching-pic-list .img-con span {
    border: 1px solid #fff;
    color: #999999;
    cursor: pointer;
    display: inline-block;
    height: 64px;
    line-height: 64px;
    overflow: hidden;
    text-align: center;
    width: 64px;
}

.mod-offer-post-picture-matching .matching-pic-list .empty .ctrl {
    visibility: hidden;
}
.mod-offer-post-picture-matching .matching-pic-list .ctrl {
    bottom: 0;
    position: absolute;
    right: 0;
    visibility: visible;
}

.matching-pic-list .ctrl a {
    background: url("http://img.china.alibaba.com/images/common/icon_v02/close6.gif") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    display: block;
    height: 16px;
    overflow: hidden;
    text-indent: -9999px;
    width: 16px;
}
.matching-pic-list .color-name {
    height: 24px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}
.mod-offer-post-picture-matching .matching-pic-list .no-pic {
    background: url("http://img.china.alibaba.com/cms/upload/offer/postoffer/postoffer-bg-v4.png") no-repeat scroll -23px -160px rgba(0, 0, 0, 0);
    border: 1px solid #f5f5f5;
}
.mod-offer-post-uppic .ctrl {
    bottom: 1px;
    display: none;
    font-size: 10pt;
    padding-top: 10px;
    position: absolute;
    right: 33px;
}

.mod-offer-post-uppic .imgs li a {
    background: url("http://img.china.alibaba.com/images/common/icon_v02/close6.gif") no-repeat scroll 0 0 rgba(0, 0, 0, 0);
    display: block;
    height: 16px;
    overflow: hidden;
    text-indent: -9999px;
    width: 16px;
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
											<span class="title">修改属性并发布</span></li>

										<li data-target="#step4" class=""><span class="step">4</span>
											<span class="title">完成</span></li>
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
													class="col-xs-12 col-sm-3 control-label no-padding-right">淘宝或天猫单个网址:</label>

												<div class="col-xs-12 col-sm-5">
													<span class="block input-icon input-icon-right"> <input
														type="text" id="taobaourl" class="width-100"> <i
														class="icon-info-sign"></i> </span>
												</div>
												<div class="help-block col-xs-12 col-sm-reset inline"></div>
											</div>

										
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
										
										<div class="ali-main-content content-con">
<div class="ali-layout ">
														
														
														
<div class="region-custom region-takla ui-sortable region-vertical region-offerweb-baseInfo"><span style="display: none;" class="placeholder">region-offerweb-baseInfo</span><div data-widget-name="offerweb_common_regionTitBaseInfo" class="widget-custom offerweb_common_regionTitBaseInfo"><div class="widget-custom-container"><div class="ali-tit" id="base-info">
    <div class="fd-left">
        <h3>填写基本信息</h3>
        <input type="checkbox"  id="picStatus"  disabled="disabled"   style="margin-left:10px" />同时复制详情图片到空间(否则只复制详情图片连接)(3.13提供该功能) 
    </div>
    <div class="fd-right">
        <a href="#" class="icon-feedback feedback-default">我要反馈</a>
    </div>
</div></div></div><div data-widget-name="offerweb_common_offerTitle" class="widget-custom offerweb_common_offerTitle"><div class="widget-custom-container"><div  class="info-title fd-clr mod mod-offer-post-title" id="title-info">
    
    <div class="form-line">
        <div class="form-title">
            <span class="icon-must">信息标题：</span>
        </div>
        <div class="form-context">
            <div class="fd-left"><input type="text" value="2015测试用的" class="info-title-txt txt desc-change-txt" name="subject" placeholder="建议在标题中包含产品名和产品特性关键词"></div>
            <div class="num"><span class="current-num">6</span>/<span class="total-num">30</span></div>
        </div>
    </div>
    <input type="hidden" value="true" name="title_widget">
    
    
</div></div></div><div data-widget-name="offerweb_common_productFeature" class="widget-custom offerweb_common_productFeature"><div class="widget-custom-container">




<div  class="mod mod-offer-post-properties fd-clr " id="properties">

	<div class="form-line fd-clr">
        <div class="form-title"><span>产品属性：</span></div>
		<div class="form-context">
		<form class="form-horizontal"   >
			<div class="product-props">
			</div>
		</form>
		</div>
	</div>
		<div class="form-line fd-clr submod-product-speca-props" id="speca-props">
        <div class="form-title"><span>产品规格：</span></div>
		<div class="form-context">
		<form class="form-horizontal"   >
			<div class="product-speca-props">
			</div>
		</form>
		</div>
	</div>
	
		<div class="form-line fd-clr submod-product-speca-props" id="custom-props" style="display: none;">
		<div class="form-title"><span>可定制属性：</span></div>
		<div class="form-context">
			<div class="product-custom-props fd-clr"></div>
		</div>
	</div>
	
<!-- <div  class="mod mod-offer-post-picture-matching fd-clr">
	<div class="form-line fd-clr">
        <div class="form-title"><span>匹配图片：</span></div>
        <div class="form-context">
            <div class="unit-pic-preview-box" style="display: none;">
                <span class="preview-icon">预览</span>
                <div class="preview-pic"><img src=""></div>
                <div class="preview-suggestion" style="display: none;"></div>
            </div>
            <ul class="matching-pic-list"><li title="橘色" class="pic-list empty"><div class="img-con"><span class="no-pic"><img class="matching-pic"></span><div class="ctrl"><a class="icon-del-pic" href="#remove" title="删除">删除</a></div></div><div class="color-name">橘色</div></li><li title="卡其色" class="pic-list empty"><div class="img-con"><span class="no-pic"><img class="matching-pic"></span><div class="ctrl"><a class="icon-del-pic" href="#remove" title="删除">删除</a></div></div><div class="color-name">卡其色</div></li></ul>
        </div>
    </div>
</div> -->



</div>









<!--匹配图片-->
<div class="mod mod-offer-post-picture-matching fd-clr  fd-hide" id="yansediv">
	<div class="form-line fd-clr">
        <div class="form-title"><span>匹配图片：</span></div>
        <div class="form-context">
            <!-- <div class="unit-pic-preview-box">
                <span class="preview-icon">预览</span>
                <div class="preview-pic"><img src=""></div>
                <div class="preview-suggestion"></div>
            </div> -->
            <ul class="matching-pic-list"></ul>
        </div>
    </div>
</div>
<!--匹配图片end--></div></div></div>
														
														
														
														
</div><!-- ali-layout -->

<div class="ali-layout ">
<div class="ali-tit" id="trade-info-box">
    <div class="fd-left">
        <h3>产品销售信息</h3>
    </div>
</div>

</div>

<div class="ali-layout ">
<div class="ali-tit" id="detail-and-pics">
    <div class="fd-left">
        <h3>图片和详细说明</h3>
        <a href="http://114.1688.com/kf/detail/34838352.html" class="viewcase-link">查看优质范例</a>
    </div>
  </div>  
  
 
    <div  class="widget-custom offerweb_common_offerPicture"><div class="widget-custom-container"><div  class="product-pics fd-clr mod mod-offer-post-uppic" id="uppic">
    
    <div class="form-line">
        <div class="form-title"><span>产品图片：</span></div>
        <div class="form-context">
            <div id="oup-preview" class="oup-preview">
                <ol class="imgs">
                    
                </ol>
                <div class="unit-pic-preview-box" style="display: none;">
                    <span class="preview-icon">预览</span>
                    <div class="preview-pic"><img src=""></div>
                    <div class="preview-suggestion" style="display: none;"></div>
                </div>
            </div>
            <div class="note">第一张图为默认主图，建议图片尺寸在750*750像素以上，图片请避免全文字。</div>
        </div>
    </div>
    <input type="hidden" value="" name="pictureUrl" id="picture1">
    <input type="hidden" value="" name="pictureUrl" id="picture2">
    <input type="hidden" value="" name="pictureUrl" id="picture3">
    <input type="hidden" value="true" name="picture_widget">
    
    
</div>
</div>
</div>
    
    
    <div data-widget-name="offerweb_common_offerDetails" class="widget-custom offerweb_common_offerDetails"><div class="widget-custom-container"><div  id="detail">
    
    <div class="form-line page-fold">
        <div class="form-title fd-clr">
            <span class="icon-must">详细说明：</span>
        </div>
        <div class="form-context">
            <div class="page-fold-content" style="display: block;">
                <div class="loading-box fd-hide">正在加载属性信息，请稍候..</div>
                <div class="form_editor">
                <textarea data-proname="详细说明" value="" name="details" id="offer-content"  cols="30" rows="100" ></textarea>
                </div>
                <div class="detail-err"></div>
            </div>
        </div>
    </div>
    <input type="hidden" value="true" name="details_widget">
    <input type="hidden" value="false" name="isTemplateUsed" id="isTemplateUsed">
    
<div class="dpl-loading dpl-loading-part" id="loading-part" style="margin-top: 1205.57px; margin-left: 915px;"><i class="dpl-ico"></i><span class="dpl-txt">详情内容奋力加载中...</span></div></div></div></div>
    
    
    

</div>


</div>
										
									</div>

									<div class="step-pane" id="step4">
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

			<!-- <div id="modal-wizard" class="modal">
				<div class="modal-dialog">
					<div class="modal-content">
						<div class="modal-header" data-target="#modal-step-contents">
							<ul class="wizard-steps">
								<li data-target="#modal-step1" class="active"><span
									class="step">1</span> <span class="title">Validation
										states</span></li>

								<li data-target="#modal-step2"><span class="step">2</span>
									<span class="title">Alerts</span></li>

								<li data-target="#modal-step3"><span class="step">3</span>
									<span class="title">Payment Info</span></li>

								<li data-target="#modal-step4"><span class="step">4</span>
									<span class="title">Other Info</span></li>
							</ul>
						</div>

						<div class="modal-body step-content" id="modal-step-contents">
							<div class="step-pane active" id="modal-step1">
								<div class="center">
									<h4 class="blue">Step 1</h4>
								</div>
							</div>

							<div class="step-pane" id="modal-step2">
								<div class="center">
									<h4 class="blue">Step 2</h4>
								</div>
							</div>

							<div class="step-pane" id="modal-step3">
								<div class="center">
									<h4 class="blue">Step 3</h4>
								</div>
							</div>

							<div class="step-pane" id="modal-step4">
								<div class="center">
									<h4 class="blue">Step 4</h4>
								</div>
							</div>
						</div>

						<div class="modal-footer wizard-actions">
							<button class="btn btn-sm btn-prev" disabled="disabled">
								<i class="icon-arrow-left"></i> Prev
							</button>

							<button class="btn btn-success btn-sm btn-next"
								data-last="Finish ">
								Next <i class="icon-arrow-right icon-on-right"></i>
							</button>

							<button class="btn btn-danger btn-sm pull-left"
								data-dismiss="modal">
								<i class="icon-remove"></i> Cancel
							</button>
						</div>
					</div>
				</div>
			</div> -->
			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	<div class="widget-box transparent">
											<div class="widget-header widget-header-flat">
												<h4 class="lighter">
													<i class="icon-star orange"></i>
													热门域名
												</h4>

												<div class="widget-toolbar">
													<a data-action="collapse" href="#">
														<i class="icon-chevron-up"></i>
													</a>
												</div>
											</div>

											<div class="widget-body"><div class="widget-body-inner" style="display: block;">
												<div class="widget-main no-padding">
													<table class="table table-bordered table-striped">
														<thead class="thin-border-bottom">
															<tr>
																<th>
																	<i class="icon-caret-right blue"></i>
																	名称
																</th>

																<th>
																	<i class="icon-caret-right blue"></i>
																	价格
																</th>

																<th class="hidden-480">
																	<i class="icon-caret-right blue"></i>
																	状态
																</th>
															</tr>
														</thead>

														<tbody>
															<tr>
																<td>internet.com</td>

																<td>
																	<small>
																		<s class="red">$29.99</s>
																	</small>
																	<b class="green">$19.99</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-info arrowed-right arrowed-in">销售中</span>
																</td>
															</tr>

															<tr>
																<td>online.com</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">$16.45</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-success arrowed-in arrowed-in-right">可用</span>
																</td>
															</tr>

															<tr>
																<td>newnet.com</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">$15.00</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-danger arrowed">待定</span>
																</td>
															</tr>

															<tr>
																<td>web.com</td>

																<td>
																	<small>
																		<s class="red">$24.99</s>
																	</small>
																	<b class="green">$19.95</b>
																</td>

																<td class="hidden-480">
																	<span class="label arrowed">
																		<s>无货</s>
																	</span>
																</td>
															</tr>

															<tr>
																<td>domain.com</td>

																<td>
																	<small>
																		<s class="red"></s>
																	</small>
																	<b class="green">$12.00</b>
																</td>

																<td class="hidden-480">
																	<span class="label label-warning arrowed arrowed-right">售完</span>
																</td>
															</tr>
														</tbody>
													</table>
												</div><!-- /widget-main -->
											</div></div><!-- /widget-body -->
										</div>
	
	
</div>


		<script src="${URL_ROOT}/resources/ace/assets/js/typeahead-bs2.min.js"></script>

		<!-- page specific plugin scripts -->

		<script src="${URL_ROOT}/resources/ace/assets/js/fuelux/fuelux.wizard.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.validate.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/additional-methods.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/bootbox.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.maskedinput.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/select2.min.js"></script>
		<script src="${URL_ROOT}/resources/js/bussiness/ali/alicate.js?d=2"></script>
		<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
		<style src="${URL_ROOT}/resources/css/jquery.loadmask.css"></style>
		<script src="${URL_ROOT}/resources/js/loadmask/jquery.loadmask.js?d=2"></script>
		<script src="${URL_ROOT}/resources/js/common/json2.js"></script>
		
		<script type="text/javascript" charset="utf-8" src="${URL_ROOT}/resources/js/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="${URL_ROOT}/resources/js/ueditor/ueditor.all.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="${URL_ROOT}/resources/js/ueditor/lang/zh-cn/zh-cn.js"></script>
		<script>
    var ue = UE.getEditor('offer-content', {
        toolbars: [
            ['fullscreen', 'source', 'undo', 'redo', 'bold', 'inserttitle', 'insertcode', 'fontfamily', 'fontsize', 'paragraph', 'link', 'forecolor', 'indent', 'fontborder', 'insertorderedlist', 'insertunorderedlist', 'customstyle', 'indent', 'justifyleft', 'justifyright', 'justifycenter', 'justifyjustify', 'emotion', 'horizontal', 'map', 'music', 'charts', 'inserttable', 'deletetable', 'insertparagraphbeforetable', 'insertrow', 'insertcol', 'mergeright', 'mergedown', 'deleterow', 'deletecol', 'splittorows', 'splittocols', 'splittocells', 'mergecells']
        ],
        autoHeightEnabled: false,
        autoFloatEnabled: true,
        initialFrameHeight: 400,
        height:400,
        serverUrl: ""
    });
    </script>
		<!-- ace scripts -->




<script type="text/javascript">
			jQuery(function($) {
				aliList();
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
						var data=AliCate.step3ModuleView.autosave();'
						var _maskDiv=$(".widget-body");
							_maskDiv.mask("正在请求数据,请稍后!");
							 $("#step4 .ret").append("<h3>正在后台发布,请稍后.......</h3>");
							$.ajax({
								type:"POST",
								contentType:"application/json",
								data:list[1],
								url:URL_ROOT+"/top/productcopy/saveToTabao",
								dataType:"json",
								success:function(data,textStatus,jqXHR){
									_maskDiv.unmask();
									if(data.errorCode){
										alert(data.errorMsg)
									}else{
											$("#step4 .ret").append('<div class="alert alert-block alert-success col-xs-12" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>'
											+'复制成功!<br>'+'名称:'+data.name+'<br>'+
											'链接地址:'+'<a target="_blank" href="'+data.url+'">宝贝链接</a><br>'+
											'修改属性:'+'<a target="_blank" href="'+data.editurl+'">修改链接</a><br>'+
									'</div>')
									}
								},
								error:function(data){
								$("#step4 .ret").empty();
									_maskDiv.unmask();
									alert(data.errorMsg);
								}
							});
						/* var _maskDiv=$(".widget-body");
						_maskDiv.mask("正在请求数据,请稍后!");
						$.ajax({
						type:"GET",
						contentType:"application/json",
						data:{"catsId":AliCate.categoryModuleView.catsId},
						url:URL_ROOT+"/top/productcopy/propertiesByCatid",
						dataType:"json",
						success:function(data,textStatus,jqXHR){
							_maskDiv.unmask();
							AliCate.step3ModuleView.parseData(JSON.parse(data));
							AliCate.step3ModuleView.reqTaobaoData();
						},
						error:function(data){
							_maskDiv.unmask();
							alert("出错!");
						} */
					});
					}
						//alert(2)
					}else if(info.step==8&&info.direction=='next'){
						var list=AliCate.step3ModuleView.saveData();
						if(list[0]){
						console.debug(list[1]);
							var _maskDiv=$(".widget-body");
							_maskDiv.mask("正在请求数据,请稍后!");
							 $("#step4 .ret").append("<h3>正在后台发布,请稍后.......</h3>");
							$.ajax({
								type:"POST",
								contentType:"application/json",
								data:list[1],
								url:URL_ROOT+"/top/productcopy/saveToTabao",
								dataType:"json",
								success:function(data,textStatus,jqXHR){
									_maskDiv.unmask();
									$("#step4 .ret").empty();
									if(data.errorCode){
										alert(data.errorMsg)
									}else{
											$("#step4 .ret").append('<div class="alert alert-block alert-success col-xs-12" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>'
											+'复制成功!<br>'+'名称:'+data.name+'<br>'+
											'链接地址:'+'<a target="_blank" href="'+data.url+'">宝贝链接</a><br>'+
											'修改属性:'+'<a target="_blank" href="'+data.editurl+'">修改链接</a><br>'+
									'</div>')
									}
								},
								error:function(data){
								$("#step4 .ret").empty();
									_maskDiv.unmask();
									alert(data.errorMsg);
								}
							});
							return true;
						}else{
							return false;
						}
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
			
			/* 	$.ajax({
				type:"GET",
				contentType:"application/json",
				data:{"url":"http://offer.1688.com/offer/asyn/category_selector.json?callback=jQuery172026866585157943657_1425188564958&loginCheck=N&dealType=getSubCatInfo&categoryId=0&scene=offer&tradeType=0"},
				url:URL_ROOT+"/top/productcopy/alicateList",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
				
					_.each(data,function(item){
						$("ul[data-level=1]").append('<li data-id="'+item.id+'" data-index="1" class="leaf"><input type="checkbox" class="item-check"><label class="item-label">'+item.name+'</label></li>');
					});
				}
				});
			 */

			
				
				$('#modal-wizard .modal-header').ace_wizard();
				$('#modal-wizard .wizard-actions .btn[data-dismiss=modal]').removeAttr('disabled');
			})
		</script>

