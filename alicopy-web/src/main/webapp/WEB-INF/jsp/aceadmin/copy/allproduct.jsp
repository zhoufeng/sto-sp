<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>
<style>
.save_process_tip {
    background: #000 none repeat scroll 0 0;
    box-shadow: 0 0 10px #000;
    color: #fff;
    display: none;
    opacity: 0.8;
    padding: 10px 15px;
    position: fixed;
    text-align: center;
    z-index: 5000;
}
</style>
<script type="text/template" id="item">
<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox"  data_id="<@=id@>" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<@=title@>
</td>
	<td>
		<a href="<@=url@>" target=‘_blank’>原地址</a>
	</td>
	<td><@=statusMsg@></td>
<td><@=errorMsg@></td>

	<td><@=createTime@></td>
<td><a href="<@=eidtUrl@>" target=‘_blank’>修改</a>
<a href="<@=viewUrl@>" target=‘_blank’>浏览</a>
</td>


</script>


<script type="text/template" id="status_2">
<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox" data_id="<@=id@>" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<@=title@>
</td>
	<td>
		<a href="<@=url@>" target=‘_blank’>原地址</a>
	</td>
	<td><@=statusMsg@></td>
<td><@=errorMsg@></td>

<td><@=createTime@></td>
<td>
<a href="<@=viewUrl@>" target=‘_blank’>重新复制</a>

<a href="javascript:void(0)" id="fankui">反馈</a>

</td>


</script>

<script type="text/template" id="status_3">
<td class="center">
</td>
	<td>
	<@=title@>
</td>
	<td>
		<a href="<@=url@>" target=‘_blank’>原地址</a>
	</td>
	<td><@=statusMsg@></td>
<td><@=errorMsg@></td>

<td><@=createTime@></td>
<td>


</td>


</script>

<div class="page-content">
	<div class="row">
	<form class="form-horizontal" id="search"  role="form">
		<div class="col-xs-12" id="search_div">
			<div role="form" class="form-horizontal">
					<div class="control-label col-xs-1" style="text-align: left">店铺首页地址<i style="color:red">(*)</i>:</div>
					<div class="col-xs-3 form-inline" id="search_url_mask" >
						<input type="text" class="form-control" id="search_url_input"
							placeholder="例如:http://ding.1688.com" />
					</div>
					<div class="col-xs-1 form-inline">
						<button id="search_btn" type="button"
							class="form-control btn btn-primary" data-loading-text="正在复制" >复 制<i class="icon-search icon-on-right bigger-110"></i></button>
					</div>
					<!-- <button type="button" class="close" data-dismiss="alert"><span>&times;</span><span class="sr-only">Close</span></button> -->
					<div class="col-xs-2 form-inline">
							<input type="checkbox"   name="savePic"  disabled="disabled"/>
						 	同时复制图片到空间(暂时取消)
					</div>
			</div>
		</div>
		</form>
	<div class="col-xs-12">
		<jsp:include page="selfdefind.jsp"></jsp:include>
	</div>
	<div name="itempage" >
		<div class="col-xs-12">
		<div class="col-xs-3">
			<button id="refresh_btn" type="button"
							class="form-control btn btn-primary "  >刷新进度<i class="icon-search icon-on-right bigger-110"></i></button>
		</div>
		</div>
		<div class="col-xs-12 " >
			<!-- PAGE CONTENT BEGINS -->
			<div class="row">
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover table-striped table-text">
							<thead>
								<tr>
									<th class="center">选择</th>
									<th class="center">标题</th>
									<th>原地址</th>
									<th>状态</th>
									<th>错误提示</th>
									<th>创建时间</th>
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
	
</div>
<div id="save_process_tip" class="save_process_tip" style="display: none;"></div>
<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/pagenation/bootstrap-paginator.js"></script>

<script src="${URL_ROOT}/resources/js/bussiness/mqrecordList.js?d=2"></script>
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
	$(document).ready(function() {
		taobaoList();
		Taobao.itemListView.loadItems();
		$("#search_btn").on("click",function(){
			searchUrl();
		})
		$("#refresh_btn").on("click",function(){
			Taobao.itemListView.loadItems();
		});
		var tipdiv=$("#save_process_tip")
		tipdiv.css("top",(document.body.clientHeight)/2+"px")
		tipdiv.css("left",(document.body.clientWidth)/2+"px")
		
		function searchUrl(){
			var url=$("#search_url_input").val().toLocaleLowerCase();
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
				url:URL_ROOT+"/top/productcopy/saveAll",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					if(data.errorCode){
						alert(data.errorMsg);
						$("#search_btn").button('reset');
						return;
					}
					alert("复制成功!请进入复制历史列表查看复制状态")
					//$("div[name|='itempage']").css("display","block");
					$("#search_btn").button('reset');
				},error:function(data){
					$("#search_btn").button('reset');
					alert(data.errorMsg);
				}
				});
			var process_ajax=function(){
				$.ajax({
					type:"GET",
					contentType:"application/json",
					url:URL_ROOT+"/top/productcopy/saveProcessMsg",
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						if(data.total==0){
						   $("#save_process_tip").text("复制完成!");
							$("#save_process_tip").css("display","none");
							clearInterval(inter);
						}else{
							$("#save_process_tip").text("正在复制第"+data.currentNum+"条,总计"+data.total+"记录");
							$("#save_process_tip").css("display","block");
						}
					},error:function(data){
						$("#save_process_tip").css("display","none");
						clearInterval(inter);
					}
				});
			}
			var inter=setInterval(process_ajax, 10000);
		}
});

	
	
</script>