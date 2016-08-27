<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<script type="text/template" id="item">

	<td>
	<@=memberId@>
</td>
	<td>
		<@=gmtCreate@>
	</td>
	<td><@=gmtServiceEnd@></td>
	<td><@=bizStatus@></td>
<td><@=statusMsg@></td>
<td><@=paymentAmount@></td>
<td><@=executePrice@></td>


</script>
<div class="page-content">
	<div class="row">

	<div class="col-xs-12">
		<div name="itempage" >
		<form class="form-horizontal" id="search_form">
			<div class="form-group">
			<label class="col-sm-1 control-label no-padding-right"
						for="form-field-6">日期:</label>
			
			<div class="col-sm-1">
			<input type="text" id="date_input" />
			</div>
			<label class="col-sm-1 control-label no-padding-right" for="form-field-6">付费类型:</label>
			<div class="col-sm-1">
			<select class="form-control"  id="cate_free">
							<option value="1">收费</option>
							<option value="0">全部</option>
						</select>
			</div>
			<div class="col-sm-1"></div>
			<div class="col-sm-1">
			<button id="search_btn" type="button"
						class="form-control btn btn-primary" data-loading-text="正在查询">
						查 询<i class="icon-search icon-on-right bigger-110"></i>
					</button>
			</div>
			</div>
			</form>
		
</div>
				<div class="col-xs-12">
					<div class="table-responsive">
						<table id="sample-table-1"
							class="table table-striped table-bordered table-hover table-striped table-text">
							<thead>
								<tr>
									<th class="center">会员memberId</th>
									<th>下单时间</th>
									<th>到期时间</th>
									<th>订单状态</th>
									<th>订单详细状态</th>
									<th>到帐金额</th>
									<th>执行金额</th>
								</tr>
							</thead>
							<tbody id="listTbody">

							</tbody>


						</table>
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

<script src="${URL_ROOT}/resources/js/bussiness/alimanager/orderList.js?d=3"></script>
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
		var dt1=new Date();
		var month=dt1.getMonth()+1>9?dt1.getMonth()+1:"0"+(dt1.getMonth()+1);
		var day=dt1.getDate()>9?dt1.getDate():"0"+dt1.getDate();
		var date=""+dt1.getFullYear()+month+day;
		$("#date_input").val(date);
		taobaoList();
		Taobao.itemListView.loadItems();
		$("#search_btn").on("click",function(){
			Taobao.itemListView.loadItems();
		});
});

	
	
</script>