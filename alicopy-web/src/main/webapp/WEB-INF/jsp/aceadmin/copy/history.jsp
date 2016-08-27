<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<script type="text/template" id="item">
<td class="center">
		<label>
			<input type="checkbox" class="ace" name="topitem_checkbox"  data_id="<@=id@>" />
			<span class="lbl"></span>
		</label>
	</td>
	<td>
	<img  style="height:45px;"  src="<@=zhutuImage@>"></img>
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
	<img  style="height:45px;"  src="<@=zhutuImage@>"></img>
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
<a href="${URL_ROOT}/top/productcopy/recopy?url=<@=url@>"  target="_blank" class="recopy">重新复制</a>

<a href="javascript:void(0)" id="fankui">反馈</a>

</td>


</script>

<script type="text/template" id="status_3">
<td class="center">
</td>
	<td>
	<img  style="height:45px;"  src="<@=zhutuImage@>"></img>
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
 	<div class="col-xs-12 " >
 	
 	<div class="col-xs-2 form-inline">
		<button id="refresh_btn" type="button"
			class="form-control btn btn-primary"  data-loading-text="正在刷新" >刷新复制记录<i class="icon-search icon-on-right bigger-110"></i></button>
	</div>
 	
	<div class="col-xs-2 form-inline">
		<button id="delete_btn" type="button"
			class="form-control btn btn-primary" >清除复制记录<i class="icon-search icon-on-right bigger-110"></i></button>
	</div>
	
	
	
	<div class="col-xs-2 form-inline">
	<select class="form-control" name="product_cate">
							<option value="-1">全部</option>
							<option value="0">正在复制</option>
							<option value="10">正在排队复制中</option>
							<option value="1">复制成功</option>
							<option value="2">复制失败</option>
							<option value="10">正在排队复制</option>
							<option value="3">反馈</option>
							
							</select>
		</div>
	</div> 
	<div class="alert alert-block alert-success col-xs-7" style="margin-top: 20px;margin-bottom: 0px; "><button type="button" class="close" data-dismiss="alert"><strong> <i class="icon-remove"></i></strong></button><strong><i class="icon-ok"></i></strong>
			如果有复制不了的地址,点击在错误行点击反馈,我们将在最快的时间修复.
			<br></div>
		<div name="itempage" >
		<div class="col-xs-12">

		<div class="col-xs-9" id="refresh_info" >
			
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
	jQuery(function($) {
		taobaoList();
		Taobao.itemListView.loadItems();
		$("#checkbox_all_input").on("change",function(){
			var value=this.checked;
			_.each($("input[type|='checkbox']"),function(item){
				item.checked=value;
			});
			
		});
		$("select[name|='product_cate']").on("change",function(){
			var cate=$("select[name|='product_cate']").val();
			Taobao.itemListView.recordStatus=cate;
			Taobao.itemListView.loadItems();
		});
		
		
		$("#delete_btn").on("click",function(){
		
			var list=$("input[name='topitem_checkbox'][type|='checkbox']:checked");
			var index=0;
			var ids=new Array();
			_.each(list,function(item){
				ids.push($(item).attr("data_id"));
			});
			if(ids.length==0){
				alert("请选中要删除的记录!");
				return ;
			}
			$.ajax({
				type:"POST",
				contentType:"application/json",
				data:JSON.stringify({"ids":ids}),
				url:URL_ROOT+"/top/productcopy/delhistory",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					alert("清除成功!");
					Taobao.itemListView.loadItems();
				},
				error:function(){
					alert("清除失败!")
				}
			});
		});
		var refresh_ajax=function(){
			$.ajax({
				type:"GET",
				contentType:"application/json",
				url:URL_ROOT+"/top/productcopy/saveProcessMsg",
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					
					Taobao.itemListView.loadItems();
				},error:function(data){
					
				}
			});
		}
		
		
		$("#refresh_btn").on("click",function(){
			$("#refresh_btn").button("loading");
			Taobao.itemListView.loadItems();
			$("#refresh_btn").button("reset");
		});
		
		
});

	
	
</script>
