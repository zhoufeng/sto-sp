<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"  %>
<script type="text/template" id="itemcate">
<input type="checkbox" class="item-check"><label class="item-label"><@=name@></label>
</script>
<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox" />
			<span class="lbl"></span>
		</label>
	</td>
	<td valign="center">
	<img  style="height:45px;"  src="<@=picUrl@>"></img>
</td>
	<td>
		<a href="<@=detailUrl@>" target=‘_blank’><@=title@></a>
	</td>
	<td><@=price@></td>
	<td><@=num@></td>
</script>
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/merge.css" />
<link rel="stylesheet"  href="${URL_ROOT}/resources/css/ali/product.css" />
<div class="page-content">
	<!-- /.page-header -->
	<div class="row">
		<div class="col-xs-12">
					<!-- PAGE CONTENT BEGINS -->
			<!-- PAGE CONTENT ENDS -->
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
     <div class="row">
	<form class="form-horizontal" id="search"  role="form">
		<div class="col-xs-12 space-12"></div>
		<div class="col-xs-12">
					<div class="control-label col-xs-1" id="" style="text-align: left;">店铺首页地址:</div>
					<div  id="search_mask_div"  >
						<div class="col-xs-3 form-inline" id="search_mask_div">
							<input type="text" class="form-control" id="wwName_input"
								placeholder="例:http://htczzz.1688.com"  />
						</div>
						<label for="name-2" class="control-label col-xs-1">店铺分类:</label>
						<div class="col-xs-2 form-inline">
							 <select class="form-control  col-xs-2" name="product_cate">
							    <option url="" value="">所有分类</option>
							  </select>
						</div>
					</div>
					<div class="col-xs-1 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在查询" >查 询<i class="icon-search icon-on-right bigger-110"></i></button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
					<div class="col-xs-2 form-inline">
							<input type="checkbox"   name="picStatus" />
						 	同时复制图片到空间(否则只复制图片连接)
					</div>
		</div>
		
		</form>
		
		<div class="col-xs-12">
			<jsp:include page="../selfdefind.jsp"></jsp:include>
		</div>
		
		<div class="col-xs-12"  id="ret_desc">
			
		</div>
		<!-- <div class="col-xs-12 space-12"></div>
		 
		<div class="col-xs-12"><h3 class="header smaller lighter green"><i class="icon-bullhorn"></i>设置<h3></div> -->
		
		<div name="itempage" style="display: none">
		
		 <div class="col-xs-12">
		 	<div class="col-xs-5"></div>
			<div class="col-xs-2" style="text-align: center; margin-top: 10px;">
			<button id="save_taobao_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在复制!" >将勾选的宝贝复制到仓库</button>
		</div>
		<div class="col-xs-2" style="text-align: center; margin-top: 18px;">
			<a href="${URL_ROOT}/top/productcopy/history"  target=‘_blank’>查询批量提交进度</a>
		</div>
		</div>
		<div class="col-xs-12 space-12"></div>
		<div class="col-xs-12 " >
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover table-striped table-text">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											class="ace" id="checkbox_all_input" /> <span class="lbl"></span> 全选</label></th>
									<th>主图</th>
									<th>名称</th>
									<th>价格</th>
									<th>已售数量</th>

								</tr>
							</thead>
							<tbody id="listTbody">

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
	</div><!-- end itempage -->
	</div>
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
		<script src="${URL_ROOT}/resources/ace/assets/js/typeahead-bs2.min.js"></script>
		<!-- page specific plugin scripts -->
	<script src="${URL_ROOT}/resources/js/bussiness/taobaocatecopy.js?d=1"></script>
	<script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/fuelux/fuelux.wizard.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.validate.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/additional-methods.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/bootbox.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.maskedinput.min.js"></script>
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
		$('[data-rel=tooltip]').tooltip();
		var searchShop={};
		taobaoList();
		$("#search_btn").on("click",function(){
			serachAjax();
		})
		$("#checkbox_all_input").on("change",function(){
			var value=this.checked;
			_.each($("input[name|='topitem_checkbox']"),function(item){
				item.checked=value;
			});
		});
		$("#save_taobao_btn").on("click",function(){
		if(!AliCate.categoryModuleView.catsId){
			alert("请先选择类目!");
			return false;
		}
			var saveButton=$(this);
			//saveButton.button("loading");
			var list=$("input[name='topitem_checkbox'][type|='checkbox']:checked");
			var index=0;
			var urlList=new Array();
			_.each(list,function(item){
				//saveButton.text("正在上传"+index+"/"+list.length)
				var a=$(item).parents("tr").find("a");
				urlList.push(a.attr("href"));
			});
			
			var savPic=$("input[name='picStatus'][type|='checkbox']:checked");
			var picStatus=false;
			if(savPic.size()==1)picStatus=true;
			var params=getExtraParams();
			
			$.extend(params,{"picStatus":picStatus,"catId":AliCate.categoryModuleView.catsId});
			var lparams={};
			lparams.urlList=urlList;
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
			
			$("#save_taobao_btn").button("loading");
			
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:ajaxParam,
				url:URL_ROOT+"/top/finalproductcopy/saveTaobaoItemList",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					
					_.each($("#sample-table-1 input[type|='checkbox']"),function(item){
						item.checked=false;
					});
					$("#save_taobao_btn").button('reset');
					$("#ret_desc").empty();
					if(data.msg){
						alert(data.msg);
						return;
					}
					
					$("#ret_desc").append('<div  class="alert alert-block alert-success col-xs-12" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong></div>')
					_.each(data,function(item){
						if(item.errorCode){
							$("#ret_desc div.alert").append('复制失败!'+'|'+'名称:'+item.name+"|原因:"+item.errorMsg+'<br>');
						}else{
							$("#ret_desc div.alert").append('复制成功!|'+'名称:'+item.name+'|'+
							'链接地址:'+'<a target="_blank" href="'+item.url+'">宝贝链接</a>|'+
							'修改属性:'+'<a target="_blank" href="'+item.editurl+'">修改链接</a>'+'<br>');
						}
					});
					
				},
				error:function(){
					$("#save_taobao_btn").button('reset');
					alert("请求服务器失败!")
				}
			});
			
		});
		function searchUrl(){
			var url=$("#search_url_input").val();
			$.ajax({
				type:"GET",
				contentType:"application/json",
				data:{"url":url},
				url:URL_ROOT+"/top/productcopy/searchOneItem",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					if(data.errorCode){
						alert(data.errorMsg);
					}
					$("div[name|='itempage']").css("display","block");
					Taobao.itemListView.searchVoBean=data;
					Taobao.itemListView.current_page=1;
					Taobao.itemListView.localItemsByLocation(data);
					$("#save_taobao_btn").button('reset');
				}
				});
		}
		function serachAjax(){
			var url=$("#wwName_input").val();
			var cate=$("select[name|='product_cate']").val();
			var cateUrl=$("select[name|='product_cate'] option:selected").attr("url");
			if(url!=Taobao.itemListView.currentUrl){
				cateUrl="";
			}		
			$("#search_btn").button("loading");
			$("#listTbody").mask("正在加载,清稍后");
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"url":url,"cateUrl":cateUrl}),
				url:URL_ROOT+"/top/productcopy/searchShopUrl",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					$("#listTbody").unmask();
					if(data.errorCode){
						alert(data.errorMsg);
					}
					$("#search_btn").button('reset');
					//类别
					if(data.cateItems){
					$("select[name|='product_cate']").empty();
					var options=$("select[name|='product_cate']").append('<option url="" value="">所有分类</option>');
					var kgstr="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					 _.each(data.cateItems,function(item){
					 	var str="";
					 	if(item.children&&item.children.length>0){
					 	$("select[name|='product_cate']").append('<option url='+item.url+' value="'+item.id+'"><span class="color:red">'+item.name+'</span></option>')
					 		_.each(item.children,function(child){
					 			$("select[name|='product_cate']").append('<option url='+child.url+' value="'+child.id+'">'+kgstr+child.name+'</option>');
					 		})
					 	}else{
					 		$("select[name|='product_cate']").append('<option url='+item.url+' value="'+item.id+'">'+item.name+'</option>')
					 	}
					 })
					 $("select[name|='product_cate']").val(data.cate)
					}//end cate
					$("div[name|='itempage']").css("display","block");
					Taobao.itemListView.searchVoBean=data;
					Taobao.itemListView.current_page=1;
					Taobao.itemListView.currentUrl=url;
					Taobao.itemListView.localItemsByLocation(data);
					$("#save_taobao_btn").button('reset');
				},
				error:function(){
					$("#save_taobao_btn").button('reset');
				}
			});
		}
			});
		</script>