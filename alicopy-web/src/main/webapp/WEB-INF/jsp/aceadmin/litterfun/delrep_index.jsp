<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>
<script type="text/template" id="item">
	<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<img  style="height:45px;"  src="<@=type@>"></img>
</td>
	<td>
		<a href="<@=detailUrls@>" target=‘_blank’><@=subject@></a>
	</td>
	<td><@=price@></td>
	<td>
<a href="javascript:void(0)" id="del_offer">删除</a>
</td>


</script>
<div class="page-content">
	<!-- <div class="page-header">
		<h1>
			控制台 <small> <i class="icon-double-angle-right"></i> 查看 </small>
		</h1>
	</div> -->
	<!-- /.page-header -->


		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
		<div class="col-xs-3">
			<button id="search_btn" type="button"
				class="form-control btn btn-primary" data-loading-text="正在查询" >查询所有重复<i class="icon-search icon-on-right bigger-110"></i></button>
				
		</div>
		注明:根据标题是否相同,将列出所有重复的宝贝.
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
		 
		
</div>
<!-- /.page-content -->


<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="${URL_ROOT}/resources/ace/assets/js/excanvas.min.js"></script>
		<![endif]-->

<script src="${URL_ROOT}/resources/js/backbone/backbone.js"></script>
<script src="${URL_ROOT}/resources/js/bussiness/litterfun/delrep.js"></script>
<script
	src="${URL_ROOT}/resources/ace/assets/js/jquery.dataTables.min.js"></script>
<script
	src="${URL_ROOT}/resources/ace/assets/js/jquery.dataTables.bootstrap.js"></script>		

<script type="text/javascript">
		jQuery(function($) {
			taobaoList();
			$("#search_btn").on("click",function(){
				 $("#search_btn").button("loading");
				 $.ajax({
					type:"POST",
					contentType:"application/json",
					url:Constant.URL_ROOT+'/litterfun/searchRepProduct',
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						if(data.errorCode){
							alert(data.errorMsg);
						}
						Taobao.itemListView.localItemsByLocation(data);
						$("#search_btn").button('reset');
					},
					error:function(){
						$("#search_btn").button('reset');
					}});
		
				 
			 });
		
		})
	</script>
