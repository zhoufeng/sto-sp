<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" />
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
	<td><@=modified@></td>

	<td>
<div style="margin:0 auto" class="grid_buttongroup">
	<button valed="btn_video_gen" id="btn_video_gen" type="button" class="btn btn-sm btn-primary"   data-loading-text="正在生成视频" >生成视频</button>
	<button id="btn_video_down" type="button" class="btn btn-sm disabled btn-primary"   data-loading-text="正在保存..." >下载视频 </button>
	<button id="btn_test" type="button" class="btn btn-sm  btn-primary"   data-loading-text="正在请求..." >itemget</button>
	<button id="btn_test1" type="button" class="btn btn-sm  btn-primary"   data-loading-text="正在请求..." >skus</button>
	<button id="btn_test2" type="button" class="btn btn-sm  btn-primary"   data-loading-text="正在请求..." >test2</button>
</div>
	</td>

</script>
<div class="page-content">
	<!-- <div class="page-header">
		<h1>
			淘宝 <small> <i class="icon-double-angle-right"></i> 商品列表 </small>
		</h1>
	</div> -->
	<!-- /.page-header -->



	<!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="myModalLabel">登录</h3>
				</div>
				<div class="modal-body text-center">
					<p>还没有登录，点击下面的链接进行登录</p>
					<a id="aLogin" herf="" class="btn btn-large btn-primary"
						target='_blank'>登录</a>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
				</div>
			</div>
		</div>
	</div>



	<div class="row">
		<div class="col-xs-12">
			<form role="form" class="form-horizontal">
				<div class="form-group">
					<label for="pay-1" class="control-label col-md-1">是否上架：</label>
					<div class="col-md-1 form-inline">
						<select name="" id="pay-1" class="form-control">
							<option value="1">出售中商品</option>
							<option value="2">库存中商品</option>
						</select>
					</div>
					<label for="name-2" class="control-label col-md-1">商品名称：</label>
					<div class="col-md-1 form-inline">
						<input type="text" class="form-control" id="productName"
							placeholder="输入名称">
					</div>
					<div class="col-md-1 form-inline">
						<button id="search" type="button"
							class="form-control btn btn-primary">搜索</button>
					</div>
					<div class="col-md-1 form-inline">
						<button id="video_setting" type="button"
							class="form-control btn btn-primary"  data-toggle="modal" data-loading-text="正在保存..." data-target="#video_setting_div">设置</button>
					</div>
					<!--  <div class="col-md-5 form-inline"></div>-->
					<div class="col-md-1 form-inline">
						<h3 class="smaller" style="margin-top:0px;margin-bottom: 0px;line-height: 1.5;">
							<a href="http://ugc.taobao.com/video/uploadVideo.htm?spm=0.0.0.0.LoYzsN" target="_blank">上传视频</a>
						</h3>
					</div>
					
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
				</div>
			</form>
		</div>
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover table-striped table-text">
							<thead>
								<tr>
									<th class="center"><label> <input type="checkbox"
											class="ace" /> <span class="lbl"></span> </label></th>
									<th>主图</th>
									<th>名称</th>
									<th>价格</th>
									<th>修改时间</th>
									<th>操作</th>

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
	</div>
</div>

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/taobaoList.js"></script>
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
		taobaoList();
		/* $("#search").on('click', function() {
			$("#myModal").modal("show");
		}) */
/* 		$("#video_setting").on("click",function(){
			
		}); */
		
		//请求视频设置参数
		$.ajax({
		type:"GET",
		contentType:"application/json",
		url:URL_ROOT+"/top/video/settingupdate",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			if(data.id){
				$("input[name|='voide_setting_id']").val(data.id);
				$("input[name|='musicType'][value="+data.musicType+"]").attr("checked",'checked');
				$("input[name|='videoType'][value="+data.videoType+"]").attr("checked",'checked');
				$("input[name|='videoSize'][value="+data.videoSize+"]").attr("checked",'checked');
			}
		}
		});
		$("#search").on("click",function(){
			if($("#pay-1")[0].selectedIndex==0){
				Taobao.itemListView.itemList.url=Constant.URL_ROOT+'/top/opt/onsale/item';
			}else{
				Taobao.itemListView.itemList.url=Constant.URL_ROOT+'/top/opt/inventory/item';
			}
			Taobao.itemListView.q=$("#productName").val();
			Taobao.itemListView.loadItems();
		})

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
	
	//音乐
	var playbox = (function(){
	//author:eric_wu
	var _playbox = function(){
		var that = this;
		that.box = null;
		that.player = null;
		that.src = null;
		that.on = false;
		//
		that.autoPlayFix = {
			on: true,
			//evtName: ("ontouchstart" in window)?"touchend":"click"
			evtName: ("ontouchstart" in window)?"touchstart":"mouseover"
			
		}

	}
	_playbox.prototype = {
		init: function(box_ele){
			this.box = "string" === typeof(box_ele)?document.getElementById(box_ele):box_ele;
			this.player = this.box.querySelectorAll("audio")[0];
			this.src = this.player.src;
			this.init = function(){return this;}
			this.autoPlayEvt(true);
			return this;
		},
		play: function(){
			var playbox=document.getElementById("playbox");
			if(!playbox)this.init("playbox");
			var musicType=$("input[name|='musicType']:checked").val()||4;
			//this.src=URL_ROOT+"/music"
			this.src="http://demo.pigcms.cn/tpl/static/attachment/music/default/"+musicType+".mp3";
		
			if(this.autoPlayFix.on){
				this.autoPlayFix.on = false;
				this.autoPlayEvt(false);
			}
			this.on = !this.on;
			if(true == this.on){
				this.player.src = this.src;
				this.player.play();
			}else{
				this.player.pause();
				//this.player.src = undefined;
			}
			if("function" == typeof(this.play_fn)){
				this.play_fn.call(this);
			}
		},
		handleEvent: function(evt){
			this.play();
		},
		autoPlayEvt: function(important){
			if(important || this.autoPlayFix.on){
				document.body.addEventListener(this.autoPlayFix.evtName, this, false);
			}else{
				document.body.removeEventListener(this.autoPlayFix.evtName, this, false);
			}
		}
	}
	//
	return new _playbox();
})();

playbox.play_fn = function(){
	this.box.className = this.on?"btn_music on":"btn_music";
}
	
	
</script>

<!-- 弹出框 -->
 <div id="video_setting_div" class="modal fade" role="dialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-dialog" style="width:1000px">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">视频参数设置</h4>
      </div>
      <div class="modal-body">
	
	<div class="video_setting_div">
	
	<input type="hidden" name="voide_setting_id" />
	<h2 style="background:#3e89c3;" class="m-tit">
      <em><span style="color:white;">音乐背景设置区</span></em> <div style="color:white;margin-right:5px;margin-top:-2px;float:right;width:100px;" class="div_button">试听:
      <span id="playbox" class="btn_music"
		onclick="playbox.init(this).play();"><audio id="audio" loop
			src="http://demo.pigcms.cn/tpl/static/attachment/music/default/4.mp3"></audio>
		</span>
	</div> 

    </h2>
    <div class="m-music-list">
      <ul>
        <li>
            <input type="radio" checked="checked" name="musicType" value=1>
            <label>纯音乐1</label>
           	<a title="" href="##" class=""></a>  <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=2>
            <label>纯音乐2</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=3>
            <label>纯音乐3</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=4 >
            <label>纯音乐4</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=6>
            <label>纯音乐5</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
		 <li>
            <input type="radio" name="musicType" value=7>
            <label>纯音乐6</label>
             <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=8>
            <label>纯音乐7</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=9>
            <label>纯音乐8</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
        <li>
            <input type="radio" name="musicType" value=10 >
            <label>纯音乐9</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
        <li>
            <input type="radio" name="musicType" value=11>
            <label>人声1</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="musicType" value=12>
            <label>人声2</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="musicType" value=13 >
            <label>人声3</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="musicType" value=14 >
            <label>人声4</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="musicType" value=15 >
            <label>人声5</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="audio" value=16 >
            <label>人声6</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>      
		<li>
            <input type="radio" name="audio" value=17 >
            <label>人声7</label>
			 <a title="" href="##" class=""></a> <label style="margin-left: 15px">|</label>
        </li>
		<li>
            <input type="radio" name="audio" value=18 >
            <label>人声8</label>
			
        </li> 
      </ul>
    </div>
	
	  <h2 style="background:#3e89c3;color:white" class="m-tit">
      <em><span style="color:white;">视频特效设置区 </span></em> <div style="margin-right:5px;margin-top:-2px;float:right;width:100px;" onclick="showvideodlg()" class="div_button"> 查看演示 </div>

    </h2>
    <div class="m-music-list">
      <ul>
        <li>
            <input type="radio" checked="checked" name="videoType" value="1">
            <label>系统随机生成</label>
            <a title="" href="##" class=""></a>  |
        </li>
        <li>
            <input type="radio" name="videoType" value="2">
            <label>特效1-变白消失</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="3">
            <label>特效2-淡入淡出</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="4">
            <label>特效3-圆形消失</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="5">
            <label>特效4-矩形消失</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="6">
            <label>特效5-扇形消失</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="7">
            <label>特效6-圆形显示</label>
           <a title="" href="##" class=""></a>   |
        </li>
		  <li>
            <input type="radio" name="videoType" value="8">
            <label>特效7-矩形显示</label>
           <a title="" href="##" class=""></a>   |
        </li>
		  <li>
            <input type="radio" name="videoType" value="9">
            <label>特效8-扇形显示</label>
            <a title="" href="##" class=""></a>  |
        </li>
		  <li>
            <input type="radio" name="videoType" value="10">
            <label>特效9-中间撕开</label>
            
        </li>
      </ul>
    </div>
	
	
	 <h2 style="background:#3e89c3;color:white" class="m-tit">
      <em><span style="color:white;">视频尺寸预览</span></em>

    </h2>
    <div class="m-music-list">
      <ul>
         <li>
            <input type="radio" checked="checked" name="videoSize" value="800">
            <label>800 x 800</label>
             <a title="" href="##" class=""></a> |
        </li>
        <li>
            <input type="radio" name="videoSize" value="600">
            <label>600 x 600</label>
            <a title="" href="##" class=""></a>  |
        </li>
        <li>
            <input type="radio" name="videoSize" value="400">
            <label>400 x 400</label>            
        </li>
        
        
      </ul>
	  </div>
	  
	<!--  <div id="genbtn" class="submit-box">
        <a onclick="genOperation();" href="javascript:void(0);" hidefocus="true">确定生成视频</a>
    </div> -->
</div>


 </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" id="btn_save_video_setting">保存设置</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">关&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;闭</button>
      </div>
    </div>
  </div>
</div> 
