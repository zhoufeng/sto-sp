<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="false" buffer="50kb"%>
<style>
#sample-table-1  td {
	vertical-align: middle;
	max-width: 280px;
	font-size: 14px;
}

.personConfigPreviewItem {
	padding: 1px;
	display: block;
	font-size: 14px;
	line-height: 32px;
	overflow: hidden;
	color: #61676d
}

input.personConfigPreviewItem {
	color: #999;
	border: 1px solid #9a9a9a;
	font-size: 14px;
	line-height: 32px;
	-webkit-border-radius: 4px;
	-moz-border-radius: 4px;
	border-radius: 4px;
}
</style>
<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox"  data_id="<@=offerId@>"/>
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<img  style="width:32px;height:32px;"  src="<@=priceUnit@>"></img>
</td>
	<td>
	<div>
		<a href="<@=detailsUrl@>" target=‘_blank’><@=subjectRender@></a>
	</div>
	</td>
	<td>
<div title="点击可以修改标题"  class="personConfigPreviewItem" style="display: block;"><@=editSubject@></div>
 <input type="text"  class="personConfigPreviewItem" value="<@=editSubject@>" style="display: none;"></td>
	<td>修改</td>


</script>
<div class="page-content">


	<div class="row">
		

		<!-- <div class="col-xs-12"><h3 class="header smaller lighter green"><i class="icon-bullhorn"></i>设置<h3></div>  -->

		<div name="itempage">
			<form class="form-horizontal" id="search_form">
			<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right"
						for="form-field-6">关键词:</label>
			
			<div class="col-sm-1">
			<input type="text" id="wwName_input" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="form-field-6">商品分类:</label>
			<div class="col-sm-1">
			<select class="form-control" name="product_cate">
							<option value="">所有分类</option>
						</select>
			</div>
			<div class="col-sm-1">
			<button id="search_btn" type="button"
						class="form-control btn btn-primary" data-loading-text="正在查询">
						查 询<i class="icon-search icon-on-right bigger-110"></i>
					</button>
			</div>
			</div>
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right"
						for="form-field-6"><span style="color:red">替换商品词:</span></label>

					<div class="col-sm-1">
						<input data-rel="tooltip" name="rep_prev_word" type="text" title="" data-placement="bottom"
							data-original-title="输入需要替换的词! "  autocomplete="off" >
					</div>
					<label class="col-sm-1 control-label no-padding-right"
						for="form-field-6"><span style="color:red">替换成:</span></label>

					<div class="col-sm-2">
						<input data-rel="tooltip" type="text" name="rep_next_word"  title="" data-placement="bottom"
							data-original-title="输入代替原来的词!"  autocomplete="off" > <span
							class="help-button" >?</span>
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right"  >删除商品词:</label>

					<div class="col-sm-1">
						<input data-rel="tooltip" type="text" name="del_word"  data-placement="bottom"
							data-original-title="输入需要删除的词!"  autocomplete="off" >
					</div>
				</div>
				
				<div class="form-group">
					<label class="col-sm-1 control-label no-padding-right"
						for="form-field-6">增加商品词:</label>

					<div class="col-sm-1">
						<input data-rel="tooltip" type="text" name="add_prev_word" title="" data-placement="bottom"
							data-original-title="添加到标题头的词!"  autocomplete="off" >
					</div>
					<div class="col-sm-1">
						<input data-rel="tooltip" type="text"  name="add_next_word"  title="" data-placement="bottom"
							data-original-title="添加到标题尾的词!"  autocomplete="off" >
					</div>
					<div class="col-sm-1"></div>
					<div class="col-sm-1">
					<button id="save_taobao_btn" type="button"
						class="form-control btn btn-primary" data-loading-text="正在查询">将勾选的批量修改</button>
					</div>
					
				</div>
				
			</form>
			<div class="col-xs-12 ">
				<!-- PAGE CONTENT BEGINS -->
				<div class="row">
					<div class="col-xs-12">
						<div class="table-responsive">
							<table id="sample-table-1"
								class="table table-striped table-bordered table-hover table-striped table-text">
								<thead>
									<tr>
										<th class="center"><label> <input type="checkbox"
												class="ace" id="checkbox_all_input" /> <span class="lbl"></span>
												全选</label></th>
										<th>主图</th>
										<th>修改前标题</th>
										<th>修改后标题</th>
										<th>操作</th>

									</tr>
								</thead>
								<tbody id="listTbody" valign="middle">

								</tbody>


							</table>
						</div>
					</div>
				</div>

			</div>


			<div style="text-align: center;">
				<ul id='pageTable1'></ul>
			</div>
			<div id="spanTotal1" style="text-align: center;"></div>
		</div>
		<!-- end itempage -->

	</div>
</div>

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/alimanager/aliList.js"></script>
<script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>
<script
	src="${URL_ROOT}/resources/ace/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${URL_ROOT}/resources/ace/assets/js/jquery.dataTables.bootstrap.js"></script>
<script src="${URL_ROOT}/resources/js/zTree/jquery.ztree.core-3.5.js"></script>
<style src="${URL_ROOT}/resources/js/zTree/css/zTreeStyle.css"></style>
<style src="${URL_ROOT}/resources/css/jquery.loadmask.css"></style>
<script src="${URL_ROOT}/resources/js/loadmask/jquery.loadmask.js"></script>
<script src="${URL_ROOT}/resources/js/common/json2.js"></script>


<script type="text/javascript">
	jQuery(function($) {
	$('[data-rel=tooltip]').tooltip({container:'body'});

	$("#search_form").bind("input propertychange",function(){
		var obj={};
		 _.each($("#search_form").serializeArray(),function(item){
		 		obj[item.name]=item.value;
		 });
		Taobao.itemListView.changeAll(obj);
	});

	
		var cates = JSON.parse('${cates}');
		//类别
		$("select[name|='product_cate']").empty();
		var options = $("select[name|='product_cate']").append(
				'<option value="">所有分类</option>');
		if (cates.result && cates.result.toReturn[0].sellerCats) {
			var kgstr = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			_.each(cates.result.toReturn[0].sellerCats, function(item) {
				var str = "";
				if (item.children && item.children.length > 0) {
					$("select[name|='product_cate']").append(
							'<option value="'+item.id+'"><span class="color:red">'
									+ item.name + '</span></option>')
					_.each(item.children, function(child) {
						$("select[name|='product_cate']").append(
								'<option value="'+child.id+'">' + kgstr
										+ child.name + '</option>');
					})
					/* 	_.each(item.children,function(child){
							str+='<option value="'+child.id+'">'+child.name+'</option>';
						})
						$("select[name|='product_cate']").append('<optgroup label="'+item.name+'">'+str+'</optgroup>') */
				} else {
					$("select[name|='product_cate']").append(
							'<option value="'+item.id+'">' + item.name
									+ '</option>')
				}

			})
		}//end cate

		taobaoList();

		var searchShop = {};
		$("#search_btn").on("click", function() {
			serachAjax();
		})
		$("#checkbox_all_input").on("change", function() {
			var value = this.checked;
			_.each($("input[type|='checkbox']"), function(item) {
				item.checked = value;
			});

		});

		$("#save_taobao_btn").on("click",
						function() {
							var saveButton = $(this);
							//saveButton.button("loading");
							Taobao.itemListView.save();
							

						});
		function searchUrl() {
			var url = $("#search_url_input").val();
			$.ajax({
				type : "GET",
				contentType : "application/json",
				data : {
					"url" : url
				},
				url : URL_ROOT + "/top/productcopy/searchOneItem",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if (data.errorCode) {
						alert(data.errorMsg);
					}
					$("div[name|='itempage']").css("display", "block");
					Taobao.itemListView.current_page = 1;
					$("#save_taobao_btn").button('reset');
				}
			});
		}
		function serachAjax() {
			var name = $("#wwName_input").val();
			var cate = $("select[name|='product_cate']").val();
			var parentCate = $("select[name|='product_cate']").attr("parentid");
			var url = null;
			/* if(Taobao.itemListView.searchVoBean&&Taobao.itemListView.searchVoBean.shopName==name){
				url=Taobao.itemListView.searchVoBean.url;
			} */
			$("#search_btn").button("loading");
			$.ajax({
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"cate" : cate,
					"url" : name
				}),
				url : URL_ROOT + "/top/productcopy/searchShopUrl",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					if (data.errorCode) {
						alert(data.errorMsg);
					}
					$("#search_btn").button('reset');

					$("div[name|='itempage']").css("display", "block");
					Taobao.itemListView.searchVoBean = data;
					Taobao.itemListView.current_page = 1;
					Taobao.itemListView.localItemsByLocation(data);
					$("#save_taobao_btn").button('reset');
				}
			});
		}

		$("#btn_save_video_setting").on("click", function() {
			var id = $("input[name|='voide_setting_id']").val();
			var musicType = $("input[name|='musicType']:checked").val();
			var videoType = $("input[name|='videoType']:checked").val();
			var videoSize = $("input[name|='videoSize']:checked").val();
			$("#video_setting").button("loading");
			$.ajax({
				type : "POST",
				contentType : "application/json",
				data : JSON.stringify({
					"id" : id,
					"musicType" : musicType,
					"videoType" : videoType,
					"videoSize" : videoSize
				}),
				url : URL_ROOT + "/top/video/setting",
				dataType : "json",
				success : function(data, textStatus, jqXHR) {
					$("#video_setting").button('reset');
				}
			});
			$("#video_setting_div").modal("hide");

		});

	});
</script>