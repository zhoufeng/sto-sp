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
							placeholder="请输入宝贝详情地址" value="http://www.vip.com/detail-209560-29640085.html"  />
					</div>
					<div class="col-xs-1 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary">查看</button>
					</div>
					
					<div class="col-xs-1 form-inline">
						<button id="top_pic_space_btn" type="button"
							class="form-control btn btn-primary" >查看图片空间</button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
			</form>
		
		</div>
		<div class="col-xs-12" >
			<div class="alert alert-block alert-success col-xs-7" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>
			在上输入框输入,淘宝,天猫,京东,阿里巴巴,拍拍,唯品会详情页地址,或普通有图片的地址
			<br></div>
		</div>
	<div   id="image_content" style="min-height: 60px;width: 100%">
	</div>
	
</div>
<!-- /.page-content -->


<!-- 弹出框 -->
 <div id="top_pic_space_div" class="modal fade" role="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1000px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">视频参数设置</h4>
      </div>


 	</div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btn_save_video_setting">保存设置</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
      </div>
    </div>
  </div>
      


<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/guanlianList.js"></script>
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
		Taobao.itemListView.loadItems();	
	});
	$("#search_btn").click();
	
	
	
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