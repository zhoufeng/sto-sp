<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>




<script type="text/template" id="item">
<a href="<@=url@>"
			data-rel="colorbox"><img alt="180x180" class="imagecopy_img" src="<@=url@>" />
							<div class="text">
								<div class="inner">点击查看</div>
							</div> </a>

						<div class="tools tools-bottom">
							<a href="#"> <i class="icon-paper-clip" >上传</i> </a>
						</div>

</script>


<div class="page-content">

	<!-- /.page-header -->

		<div class="col-xs-12" id="search_div">
				<form role="form" class="form-horizontal">
					<label for="name-2" class="control-label col-xs-1">商品网址：</label>
					<div class="col-xs-5 form-inline">
						<input type="text" class="form-control" id="search_url_input"
							placeholder="请输入宝贝详情地址" value="http://detail.tmall.com/item.htm?id=44007048521"  />
					</div>
					<div class="col-xs-2 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary">查看</button>
					</div>
					
					<div class="col-xs-2 form-inline">
						<button type="button"
							class="form-control btn btn-primary" data-toggle="modal" data-target="#top_pic_space_div">选择保存的相册</button>
					</div>
					<div class="col-xs-2 form-inline">
						<button id="top_pic_space_btn" type="button"
							class="form-control btn btn-primary" >查看图片空间</button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
			</form>
		
		</div>
		<div class="col-xs-12" id="desc_1" >
			<div class="alert alert-block alert-success col-xs-7" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>
			在上输入框输入,淘宝,天猫,京东,阿里巴巴,拍拍,唯品会详情页地址,或普通有图片的地址(同一项目录下最多500张图片)
			<br></div>
		</div>
	<div   id="image_content" style="min-height: 60px;width: 100%">
	</div>
	
</div>
<!-- /.page-content -->


<!-- 弹出框 -->
 <div id="top_pic_space_div" class="modal bs-example-modal-sm" role="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog modal-sm" >
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">保存图片设置</h4>
      </div>
 		
 	<div class="modal-body">
 	<form class="form-horizontal" role="form">
  <div class="form-group">
    <label for="name-2" class="control-label col-xs-2">相册分类:</label>
    <div class="col-xs-6">
     <select class="form-control " id="product_cate">
		
	</select>
    </div>
  </div>
  
	</form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btn_save_video_setting">保存设置</button>
      </div>
    </div>
  </div>
  </div>
      


<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/imageList.js?d=3"></script>
<style src="${URL_ROOT}/resources/css/jquery.loadmask.css"></style>
<script src="${URL_ROOT}/resources/js/loadmask/jquery.loadmask.js"></script>
<script src="${URL_ROOT}/resources/ace/assets/js/jquery.colorbox-min.js"></script>


<script type="text/javascript">
	jQuery(function($) {
	$(".top_pic_space").colorbox();
	taobaoList();
	$("#search_btn").on("click",function(){
		 var urlparam=$("#search_url_input").val();
		if(!urlparam){
			return ;
		} 
		if(urlparam!="http://www.vip.com/detail-209560-29640085.html")$("#desc_1").remove();
		Taobao.itemListView.loadItems();	
	});
	$("#search_btn").click();
	
	$.ajax({
		type:"GET",
		contentType:"application/json",
		url:URL_ROOT+"/top/imagecopy/findCateList",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			for(var i=0;i<data.length;i++){
				$("#product_cate").append("<option value="+data[i].id+">"+data[i].name+"</option>");
			}
		}
		
		});
	
	$("#btn_save_video_setting").on("click",function(){
		Taobao.itemListView.albumId=$("#product_cate").val();
		$("#top_pic_space_div").modal("hide");
	});
	
	
	$("#top_pic_space_btn").on("click",function(){
		window.open("http://picman.1688.com/album/album_list.htm");
	});
	
	function getImgNaturalDimensions(img, callback) {
    var nWidth, nHeight
    if (img.naturalWidth) { // 现代浏览器
        nWidth = img.naturalWidth
        nHeight = img.naturalHeight
    } else { // IE6/7/8
        var imgae = new Image()
        image.src = img.src
        image.onload = function() {
            callback(image.width, image.height)
        }
    }
    return [nWidth, nHeight]
}
	
})
		</script>