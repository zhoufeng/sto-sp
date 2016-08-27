<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<div class="col-sm-12">
			<div class="widget-box transparent">
				<div class="widget-header widget-header-flat">
					<h4 class="lighter">
						<i class="icon-star orange"></i>
						自定义设置(点击此处展开)
					</h4>

					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="icon-chevron-down"></i>
						</a>
					</div>
				</div>

				<div class="widget-body"><div class="widget-body-inner" style="display: block;">
					<div class="widget-main">
						<form name="self_form" role="form" class="form-horizontal">
						
						<div class="form-group" style="text-align:center">
						    
						    <div class="col-xs-2">
						    <label>
							<input name="expireed"  type="checkbox" class="ace"  >
							<span class="lbl"> 复制后直接下架</span>
						</label>
						</div>
						    <div class="col-xs-6">
						    <label>
							<input name="ignoreType"  type="checkbox" class="ace"  >
							<span class="lbl"> 忽略重复商品</span>
							</label>
							<select  id="ignoreTypeVal"  name="ignoreTypeVal"  >
										<option value="H"  selected="selected">根据已复制历史记录来区别</option>
										<option value="T">根据上架商品标题上否相同来区别</option>
								</select>
							</div>
						
						</div>
						<div class="form-group">
						
						<label class="control-label col-xs-1"  > <input name="title_replace"  type="checkbox" class="ace" >
							<span class="lbl"> 标题替换:</span>
						</label>
							<div class="input-group col-xs-2">
								<span class="input-group-addon">
									把
								</span>
								<input type="text" id="title_replace_old" class="form-control input-mask-phone">
							</div>
							<div class="input-group col-xs-2">
								<span class="input-group-addon">
									替换
								</span>
								<input type="text" id="title_replace_new" class="form-control input-mask-phone">
							</div>
							
							<label class="control-label col-xs-1"  > <input name="title_delete"  type="checkbox" class="ace" >
							<span class="lbl"> 标题删除:</span>
						</label>
							<div class="input-group col-xs-3">
								<span class="input-group-addon">
									把
								</span>
								<input type="text"   id="title_delete_key"  placeholder="多个关键字可以用逗号(,)分隔" class="form-control input-mask-phone">
								<span class="input-group-addon">
									删除
								</span>
							</div>
	
						</div>
						
						<div class="form-group">
						
						<label class="control-label col-xs-1"  > <input name="content_replace"  type="checkbox" class="ace" >
							<span class="lbl"> 详情替换:</span>
						</label>
							<div class="input-group col-xs-2">
								<span class="input-group-addon">
									把
								</span>
								<input type="text" id="content_replace_old" class="form-control input-mask-phone">
							</div>
							<div class="input-group col-xs-2">
								<span class="input-group-addon">
									替换
								</span>
								<input type="text" id="content_replace_new" class="form-control input-mask-phone">
							</div>
							
							<label class="control-label col-xs-1"  > <input name="content_delete"  type="checkbox" class="ace" >
							<span class="lbl"> 详情删除:</span>
						</label>
							<div class="input-group col-xs-3">
								<span class="input-group-addon">
									把
								</span>
								<input type="text"   id="content_delete_key"  placeholder="多个关键字可以用逗号(,)分隔" class="form-control input-mask-phone">
								<span class="input-group-addon">
									删除(如有敏感字)
								</span>
							</div>
	
						</div>
						<div class="form-group">
						<label class="control-label col-xs-1"  > <input name="customer_cate"  type="checkbox" class="ace" >
							<span class="lbl"> 类目选择:</span>
						</label>
						<div class="input-group col-xs-8">
						  
						    <select data-placeholder="选择一级类目"  id="first_cate" name="first_cate" class="width-200 "  style="width:200px">
						    		
							</select>
							<select data-placeholder="选择二级类目"  id="sec_cate" name="sec_cate" class="width-200 "  style="width:200px">
						    		
							</select>
							<select data-placeholder="选择三级类目"  id="third_cate" name="third_cate" class="width-200 "  style="width:200px">
						    		
							</select>
							(淘宝复制不要选该项,类目在最上面选择)
						   </div>
						</div>
						  <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2">相册分类:</label>
						    <div class="col-xs-2">
						     <select id="albumId" name="albumId" class="form-control ">
								
								<option value="">默认</option>
							</select>
						    </div>
						    <span class="col-xs-4" style="line-height: 2.5">选择的相册的照片数要小于300,否则默认保存到一键复制相册</span>
						  </div>
						  
						  
						  <div class="form-group">
						    <label for="name-2" class="control-label col-xs-1"> 图片方式:</label>
						    <div class="col-xs-6">
						    <label>
							<input type="checkbox" class="ace" name="new_album">
							<span class="lbl ">每一个宝贝的所有图片保存到新建的相册里(最多创建500个相册)</span>
						</div>
						</div>
						  
						 
						  
						  
						   <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2">信息有效期:</label>
						    <div class="col-xs-1">
						    <label>
							<input name="periodOfValidity" type="radio" class="ace"  value="10">
							<span class="lbl"> 10天</span>
						</label>
						    </div>
						    <div class="col-xs-1">
						    <label>
							<input name="periodOfValidity" type="radio" class="ace"  value="20">
							<span class="lbl"> 20天</span>
						</label>
						    </div>
						    <div class="col-xs-1">
						    <label>
							<input name="periodOfValidity" type="radio" class="ace"  value="30">
							<span class="lbl"> 1个月</span>
						</label>
						    </div>
						    
						     <div class="col-xs-1">
						    <label>
							<input name="periodOfValidity" type="radio" class="ace"  value="90">
							<span class="lbl"> 3个月</span>
							</label>
						    </div>
						    
						    <div class="col-xs-1">
						    <label>
							<input name="periodOfValidity" type="radio" class="ace"  value="180">
							<span class="lbl"> 6个月</span>
							</label>
						    </div>
						  </div>
						  
						    <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2">自定义分类:</label>
						    <div class="col-xs-3"  >
							<select data-placeholder="选择分类...(可多选)" id="userCategorys" name="userCategorys" class="width-100 chosen-select" multiple=""  >
										
										
								</select>
							  
							  </div>
							 <div class="col-xs-4 	" >
							 (如果显示不了分类,请在阿里巴巴后台启用自定义分类)
							 </div> 
							  
							  
						  </div>
						  <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2">主图选择:</label>
						    <div class="col-xs-4"  >
							<select data-placeholder="选择主图顺序(最多五张)" id="zhutuCategorys" name="zhutuCategorys" class="width-100 chosen-select" multiple=""  >
										<option value="1">第一张图片</option>
										<option value="2">第二张图片</option>
										<option value="3">第三张图片</option>
										<option value="4">第四张图片</option>
										<option value="5">第五张图片</option>
										<option value="6">第六张图片</option>
								</select>
							  
							  </div>
							  <span class="col-xs-6" style="line-height: 2.5">主图图片的顺序,默认是1,2,3顺序(如从淘宝复制过来的商品有很多张图片,可以从中挑出中意的图片顺序)</span>
							  
							  
						  </div>
						  <div class="form-group">
						   <label class="control-label col-xs-1" for="name-2">发货地址:</label>
						    <div class="col-xs-2" >
							<select  id="sendGoodsAddressId"  name="sendGoodsAddressId" class="form-control"    >
										
										
								</select>
							  
							  </div>
							
							  <input id="addressrefresh" type="button" class="ace"  style="line-height:2.5;width:60px" value="刷新">
							  <a href="http://e56.1688.com/foundation/send_goods_address_manage.htm" style="line-height:2.5" target="_blank">管理发货地址</a>
							  
							  </div>
						  <div class="form-group">
						   <label class="control-label col-xs-1" for="name-2">运费设置:</label>
						    <div class="col-xs-2" >
							<select  id="freightType"  name="freightType" class="form-control"    >
										<option value="D" >使用运费说明</option>
										<option value="T">使用运费模板</option>
										<option value="F">卖家承担运费</option>
								</select>
							  
							  </div>
							
						    <label class="control-label col-xs-1" for="name-2"  id="freightTemplateId_label" style="display:none">运费模板设置:</label>
						    <div class="col-xs-2" >
							<select  id="freightTemplateId"  name="freightTemplateId" class="form-control"  style="display:none"  >
										
								</select>
							  
							  </div>
							  
						  </div>
						  
						  <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2">单位重量:</label>
						    <div class="col-xs-2" >
								<input name="offerWeight"  class="form-control"  />
							  </div>
							  <span class="col-xs-1" style="line-height: 2.5">公斤/罐</span>
							  
							   <label class="control-label col-xs-1" for="name-2">计量单位:</label>
						    <div class="col-xs-2" >
								<input name="offerUnit"  class="form-control"  />
							  </div>
						  </div> 
						  
						 <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2"> 混批:</label>
						    <div class="col-xs-3">
						    <label>
							<input name="mixWholeSale" type="checkbox" class="ace"  >
							<span class="lbl "> 支持混批(先设置<a href="http://marketing.1688.com/marketing/mix_wholesale_set.htm?flag=offerFlag&&functionName=updateMixBatchSettings" target="_blank">混批规则</a>,该属性才有效)</span>
						</label>
						</div>
						</div>
						  
						    <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2"> 私密商品:</label>
						    <div class="col-xs-1">
						    <label>
							<input name="pictureAuthOffer"  type="checkbox" class="ace" >
							<span class="lbl"> 图片私密</span>
							</label>
							</div>
							<div class="col-xs-1">
						    <label>
							<input name="priceAuthOffer" type="checkbox" class="ace"  >
							<span class="lbl"> 价格私密</span>
							</label>
							</div>
						</div>
						
						<div class="form-group">
						   <label class="control-label col-xs-1" for="name-2">价格设置:</label>
						    <div class="col-xs-2" >
							<select  id="priceType"  name="priceType" class="form-control"    >
										<option value="a" >加上</option>
										<option value="s">减去</option>
										<option value="m">乘以</option>
										<option value="d">除以</option>
								</select>
							  </div>
							   <div class="col-xs-2" >
								<input id="selfPrice"  class="form-control"  />
							  </div>
						</div>
						 <div class="form-group">
						    <label class="control-label col-xs-1" for="name-2"> 去外链功能:</label>
						    <div class="col-xs-4">
						    <label>
							<input name="otherHref"  type="radio" class="ace" value="C" checked>
							<span class="lbl">是否去外链(详情里面的所有其他链接)</span>
							
							</label>
							</div>
							
							<div class="col-xs-4">
						    <label>
							<input name="otherHref" type="radio" class="ace"  value="Q">
							<span class="lbl"> 强力去外链(即删除关联/搭配模板等原店广告)</span>
							</label>
						    </div>
	
						</div>
					
						<div id="diy-prop-ctrl"><div  class="form-group" style="margin-left: 20px; "><a  id="add_pro_btn"  target="_self" title="添加产品属性" href="#"  class="col-xs-2"   style="margin: 0 0 0 0px;line-height:25px;  background: url('http://img.china.alibaba.com/cms/upload/offer/postoffer/postoffer-icon-v8.png') no-repeat scroll 0 0 rgba(0, 0, 0, 0);background-position: 0 -15px;display: inline-block;padding-left: 20px;">添加产品属性</a><span style="color: #989898;">比如在复制的商品里产品属性是品牌:"优衣库";但你的品牌是"啊木木",那你就加一行,前面的输入框填品牌,后面的输入框填啊木木,复制成功后品牌变为""啊木木"</span></div></div>												
					
					
					
						 <!-- <div class="col-xs-1 ">
							<button class="btn btn-primary" type="button" id="self_save_btn">
								<i class="icon-ok bigger-110"></i>
								保存设置						</button>

						</div>
						<div class="col-xs-1 ">
							<button class="btn btn-primary" type="button" id="self_load_btn">
								<i class="icon-ok bigger-110"></i>
								读取设置						</button>

						</div> -->
						</form>
					</div><!-- /widget-main -->
				</div></div><!-- /widget-body -->
			</div><!-- /widget-box -->
	<div class="text-warning warning" style="font-size: 16px;">
        <p>&nbsp;</p>

        <h3>操作说明</h3>
        <p>1：如何修改标题,加价格,去外链等功能：点击上面"自定义设置"这行最右边的向下箭头打开自定义设置进行设置</p>
        <p>2：如何复制后直接放入仓库(下架),不要直接默认发布，在"自定义设置"里,勾上"复制后直接下架"</p>
        <p>3：问题:我在复制后有时候经常会默认帮我填写123,答:因为该产品属性一定要填,复制的改商品没有改属性</p>
        <p style="padding-left: 20px">解决方法:在"自定义设置"--"添加产品属性",填上自己想要的属性值,复制中遇到该属性会填上您的属性值</p>
        <p>4：每天最多发布1000条，超出的产品请隔天再复制</p>
        <p>复制到仓库：由于接口仅支持复制后直接上架商品，所以复制到仓库的逻辑是先上架、马上再下架。阿里对于当天下架的宝贝不能马上再次直接上架，需要编辑宝贝后再发布. </p>
        <p>5：发布食品类目的产品，要先在阿里网站签署安全承诺证书才能发布产品 操作方法：<a href="http://work.1688.com/#app/offer/orderPost/http%3A%2F%2Foffer.1688.com%2Foffer%2Fpost%2Fpost_navigation.htm%3Ftracelog%3Dwork_1_m_orderPost" target="_blank">点击这里</a>发布一个食品类目的产品会提示如何签署 </p>
        <p>6：如果复制的产品来源是品牌店的，需要申请授权证明 <a href="http://work.china.alibaba.com/#app=creditdetails&menu=&channel="  target="_blank">直接点此进入</a></p>

    </div>
	</div>
	<link rel="stylesheet" href="${URL_ROOT}/resources/ace/assets/css/chosen.css" />		
	<script src="${URL_ROOT}/resources/ace/assets/js/chosen.jquery.min.js"></script>	
	<script src="${URL_ROOT}/resources/ace/assets/js/chosen.jquery.min.js"></script>		
	<script src="${URL_ROOT}/resources/js/bussiness/selfdefind.js?d=11.0"></script>