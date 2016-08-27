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
		<div class="col-xs-12" id="search_div">
			<div role="form" class="form-horizontal">
					<div class="control-label col-xs-1" style="text-align: left">单个商品网址<i style="color:red">(*)</i>:</div>
					<div class="col-xs-4 form-inline" id="search_url_mask" >
						<input type="text" class="form-control" id="search_url_input"
							placeholder="例如:http://detail.1688.com/offer/39470937732.html" />
					</div>
					<div class="col-xs-2 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在复制" >复 制<i class="icon-search icon-on-right bigger-110"></i></button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
					<div class="col-xs-4 form-inline">
							<input type="checkbox"   name="savePic" />
						 	同时复制图片到空间(否则只复制图片连接)
					</div>
			</div>
		</div>
		<div class="col-xs-12 space-12"></div>
		</form>
		<div class="col-xs-12" id="desc_1" >
			
		</div>
		
		<%-- <div class="col-xs-12"><h3 class="header smaller lighter green"><i class="icon-bullhorn"></i>设置<h3></div> -->
	 
	</div> --%>
	 <jsp:include page="selfdefind.jsp"></jsp:include>
		
		
</div>

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>
<script src="${URL_ROOT}/resources/js/common/json2.js"></script>

<script type="text/javascript">
	jQuery(function($) {
		function init(){
			if(window.location.href.indexOf("url=")>-1){
				$("#search_url_input").val(window.location.search.substring(5, window.location.search.length));
			}
		}
		init();
		$("#search_btn").on("click",function(){
			searchUrl();
		})
	
		function searchUrl(){
			var url=$("#search_url_input").val().toLocaleLowerCase().trim();
			if(url=="")alert("请输入url");
			if(url.indexOf("tmall.com")>-1||url.indexOf("taobao.com")>-1){
				alert("请点击左侧菜单栏的<淘宝复制到阿里>进行复制!");
				return;
			} 
			//if(!url.startWith("http"))alert("请输入合法的url地址!")
			var savPic=$("input[name='savePic'][type|='checkbox']:checked");
			var picStatus=false;
			if(savPic.size()==1)picStatus=true;
			var params=getExtraParams();
			$.extend(params,{"url":url,"picStatus":picStatus});
			var ajaxParam=JSON.stringify(params,function(key,value){
				if(typeof value==="boolean"){
				 	return value;
				}
				if(value==null||value==""){
					return undefined;
				}
				return value;
			});
			$("#search_btn").button("loading");
			$.ajax({
				type:"POST",
				contentType:"application/json",
				data:ajaxParam,
				url:URL_ROOT+"/top/finalproductcopy/saveAliItem",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					if(data.msg){
						alert(data.msg);
						$("#search_btn").button('reset');
						$("#desc_1").empty();
						return;
					}
					if(data.errorMsg){
						alert(data.errorMsg);
						$("#search_btn").button('reset');
						$("#desc_1").empty();
						$("#desc_1").append("复制错误:名称:"+data.name)
						return;
					}
					$("#desc_1").empty();
					$("#desc_1").append('<div class="alert alert-block alert-success col-xs-7" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>'
					+'复制成功!<br>'+'名称:'+data.name+'<br>'+
					'链接地址:'+'<a target="_blank" href="'+data.url+'">宝贝链接</a><br>'+
					'修改属性:'+'<a target="_blank" href="'+data.editurl+'">修改链接</a><br>'+
					'</div>')
					//$("div[name|='itempage']").css("display","block");
					$("#search_btn").button('reset');
				},error:function(data){
					$("#search_btn").button('reset');
					alert(data.errorMsg);
				}
				});
		}
});

	
	
</script>