<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" %>
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
			$.extend(params,{"urlList":urlList,"picStatus":picStatus,"catId":AliCate.categoryModuleView.catsId});
			var ajaxParam=JSON.stringify(params,function(key,value){
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
				url:URL_ROOT+"/top/productcopy/batchTaobaoAll",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					_.each($("#sample-table-1 input[type|='checkbox']"),function(item){
						item.checked=false;
					});
					$("#save_taobao_btn").button('reset');
					$("#ret_desc").empty();
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
					cate="";
				}		
			$("#search_btn").button("loading");
			 $.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"cate":cate,"url":url,"cateUrl":cateUrl}),
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
				}
			});
		}
			});
		</script>