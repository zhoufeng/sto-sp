<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<img  style="height:45px;"  src="<@=picUrl@>"></img>
</td>
	<td>
		<a href="<@=detailUrl@>" target=‘_blank’><@=title@></a>
	</td>
	<td><@=price@></td>
	<td><@=num@></td>


</script>
<div class="page-content">


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
							<!-- <select class="form-control" name="product_cate">
							<option value="0">全部</option>
							</select> -->
							
							 <select class="form-control  col-xs-2" name="product_cate">
							    <option value="">全部</option>
							  </select>
														
							
						</div>

					</div>
					<div class="col-xs-1 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在查询" >查 询<i class="icon-search icon-on-right bigger-110"></i></button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
					<div class="col-xs-2 form-inline">
							<input type="checkbox"   name="savePic" />
						 	同时复制图片到空间(否则只复制图片连接)
					</div>
		</div>
		
		</form>
		
		<div class="col-xs-12">
			<jsp:include page="selfdefind.jsp"></jsp:include>
		</div>
		<div class="col-xs-12"  id="ret_desc">
			
		</div>
		<!-- <div class="col-xs-12 space-12"></div>
		
		<div class="col-xs-12"><h3 class="header smaller lighter green"><i class="icon-bullhorn"></i>设置<h3></div> -->
		
		<div name="itempage" style="display: none">
		
		 <div class="col-xs-12">
		 	<!-- <div class="col-xs-5"></div> -->
		<div class="col-xs-2" style="text-align: center; margin-top: 10px;">
			<button id="save_taobao_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在复制!" >将勾选的宝贝复制到仓库</button>
		</div>
		<div class="col-xs-2" style="text-align: center; margin-top: 10px;">
			<button id="save_all_taobao_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在复制!" >整个类目复制到仓库</button>
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

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/productcopy.js?d=24"></script>
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
		
		$("#save_all_taobao_btn").on("click",function(){
			var total=Taobao.itemListView.paginator_options.total;
			if(!total||total==0){
				alert("没有商品复制!");
				return ;
			}
			var r=confirm("该类目总共:"+total+"个商品!确定复制吗");
			if(r==true){
				var saveButton=$(this);
				var savPic=$("input[name='savePic'][type|='checkbox']:checked");
				var picStatus=false;
				if(savPic.size()==1)picStatus=true;
				var params=getExtraParams();
				$.extend(params,{"picStatus":picStatus});
				var lparams={};
				lparams.params=params;
				lparams.cateUrl=Taobao.itemListView.searchVoBean.cateUrl;
				lparams.url=Taobao.itemListView.searchVoBean.url;
				lparams.totalPages=Taobao.itemListView.searchVoBean.totalPages
				var ajaxParam=JSON.stringify(lparams,function(key,value){
					if(typeof value==="boolean"){
					 	return value;
					}
					if(value==null||value==""){
						return undefined;
					}
					return value;
				});
			
			$("#save_all_taobao_btn").button("loading");
			$.ajax({
				type:"POST",
				contentType:"application/json",
				data:ajaxParam,
				url:URL_ROOT+"/top/finalproductcopy/saveAliCateList",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					$("#save_all_taobao_btn").button('reset');
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
						$("#save_all_taobao_btn").button('reset');
						alert("请求服务器失败!")
					}
				});
			}
		});
		
		$("#save_taobao_btn").on("click",function(){
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
			
			var savPic=$("input[name='savePic'][type|='checkbox']:checked");
			var picStatus=false;
			if(savPic.size()==1)picStatus=true;
			var params=getExtraParams();
			$.extend(params,{"picStatus":picStatus});
			var lparams={};
			lparams.params=params;
			lparams.urlList=urlList;
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
				url:URL_ROOT+"/top/finalproductcopy/saveAliItemList",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					_.each($("input[type|='checkbox'][name='topitem_checkbox']"),function(item){
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
			var name=$("#wwName_input").val();
			var cate=$("select[name|='product_cate']").val();
			var parentCate=$("select[name|='product_cate']").attr("parentid");
			if(name!=Taobao.itemListView.currentUrl){
				cate="";
			}
			/* if(Taobao.itemListView.searchVoBean&&Taobao.itemListView.searchVoBean.shopName==name){
				url=Taobao.itemListView.searchVoBean.url;
			} */

			$("#search_btn").button("loading");
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"cateUrl":cate,"url":name}),
				url:URL_ROOT+"/top/productcopy/searchShopUrl",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					if(data.errorCode){
						alert(data.errorMsg);
					}
					$("#search_btn").button('reset');
					//类别
					if(data.cateItems){
					$("select[name|='product_cate']").empty();
					var options=$("select[name|='product_cate']").append('<option value="">所有分类</option>');
					var kgstr="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					 _.each(data.cateItems,function(item){
					 	var str="";
					 	if(item.children&&item.children.length>0){
					 	$("select[name|='product_cate']").append('<option value="'+item.id+'"><span class="color:red">'+item.name+'</span></option>')
					 		_.each(item.children,function(child){
					 			$("select[name|='product_cate']").append('<option value="'+child.id+'">'+kgstr+child.name+'</option>');
					 		})
					 	/* 	_.each(item.children,function(child){
					 			str+='<option value="'+child.id+'">'+child.name+'</option>';
					 		})
					 		$("select[name|='product_cate']").append('<optgroup label="'+item.name+'">'+str+'</optgroup>') */
					 	}else{
					 		$("select[name|='product_cate']").append('<option value="'+item.id+'">'+item.name+'</option>')
					 	}
					 	
					 })
					 $("select[name|='product_cate']").val(data.cate)
					}//end cate
					
					$("div[name|='itempage']").css("display","block");
					Taobao.itemListView.searchVoBean=data;
					Taobao.itemListView.current_page=1;
					Taobao.itemListView.currentUrl=name;
					Taobao.itemListView.localItemsByLocation(data);
					$("#save_taobao_btn").button('reset');
				},
				error:function(){
					$("#save_taobao_btn").button('reset');
				}
			});
		}
		
		$("#btn_save_video_setting").on("click",function(){
			var id=$("input[name|='voide_setting_id']").val();
			var musicType=$("input[name|='musicType']:checked").val();
			var videoType=$("input[name|='videoType']:checked").val();
			var videoSize=$("input[name|='videoSize']:checked").val();
			$("#video_setting").button("loading");
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"id":id,"musicType":musicType,"videoType":videoType,"videoSize":videoSize}),
				url:URL_ROOT+"/top/video/setting",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					$("#video_setting").button('reset');
				}
			});
				$("#video_setting_div").modal("hide");
			
		});
	
		
});	
	
</script>