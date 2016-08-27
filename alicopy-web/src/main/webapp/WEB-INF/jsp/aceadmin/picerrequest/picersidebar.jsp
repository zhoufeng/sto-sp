<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>
<%@ taglib uri="/tags/core" prefix="c"%>


<div class="sidebar" id="sidebar">
	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'fixed')
		} catch (e) {
		}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="icon-signal"></i>
			</button>

			<button class="btn btn-info">
				<i class="icon-pencil"></i>
			</button>

			<button class="btn btn-warning">
				<i class="icon-group"></i>
			</button>

			<button class="btn btn-danger">
				<i class="icon-cogs"></i>
			</button>
		</div>

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span> <span class="btn btn-info"></span>

			<span class="btn btn-warning"></span> <span class="btn btn-danger"></span>
		</div>
	</div>
	<!-- #sidebar-shortcuts -->

	<ul class="nav nav-list">
		<li><a href="${URL_ROOT}/picerrequest/index?otherId=${sessionScope.aliInfo.appKey}"> <i
				class="icon-dashboard"></i> <span class="menu-text"> 控制台 </span> </a></li>

		<%-- <li><a href="${URL_ROOT}/top/opt"> <i class="icon-text-width"></i>
				<span class="menu-text"> 商品列表 </span> </a></li> --%>
				
				<li><a href="${URL_ROOT}/picerrequest/imagecopy?otherId=${sessionScope.aliInfo.appKey}" > 
			<i class="icon-picture"></i> <span class="menu-text"> 图片一键复制</span> </a>
				
			</li>
				
		  <li class="open"><a href="#" class="dropdown-toggle"> <i
				class="icon-desktop"></i> <span class="menu-text"> 阿里复制到阿里</span> <b
				class="arrow icon-angle-down"></b> </a>

			<ul class="submenu "  style="display: block; ">
				<li name="item_copy_li" ><a href="${URL_ROOT}/picerrequest/alicopy?otherId=${sessionScope.aliInfo.appKey}"> 
				<i class="icon-double-angle-right"></i> 单个宝贝复制</a></li>
				</ul>
			</li>
				
			
			
			 <li class="open"><a href="#" class="dropdown-toggle"> <i
				class="icon-desktop"></i> <span class="menu-text"> 淘宝复制到阿里</span> <b
				class="arrow icon-angle-down"></b> </a>
				<ul class="submenu "  style="display: block; ">
				<li name="item_copy_li"><a href="${URL_ROOT}/picerrequest/taobaocopy"> 
				<i class="icon-double-angle-right"></i>单个宝贝复制</a></li>
				
			</ul></li>
			
			<li id="item_copy_li"><a href="${URL_ROOT}/picerrequest/history"> <i
				class="icon-edit"></i> <span class="menu-text">复制历史</span> </a>
			</li>
			
	</ul>
		
		
		
	
	<!-- /.nav-list -->


	<div class="sidebar-collapse" id="sidebar-collapse">
		<i class="icon-double-angle-left" data-icon1="icon-double-angle-left"
			data-icon2="icon-double-angle-right"></i>
	</div>

	<script type="text/javascript">
		try {
			ace.settings.check('sidebar', 'collapsed')
		} catch (e) {
		}
		
		  _.each($(".nav.nav-list ul li"),function(item){
			var jItem=$(item);
			var ahref=jItem.find("a").first().attr("href");
			if(window.location==ahref){
				jItem.addClass("active");
			}else{
				jItem.removeClass("active");
			}
		}); 
		/* $("#item_copy_li").on("click",function(e){
			console.debug(e)
			$('#item_copy_li_modal').modal('show');
		});  */
	</script>
	<!-- Modal -->
<div class="modal fade bs-example-modal-lg" id="item_copy_li_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-lg">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">关闭</span></button>
        <h4 class="modal-title" id="myModalLabel">温馨提示</h4>
      </div>
      <div class="modal-body">
        	<h4>只有您订阅了至尊版才有该功能.</h4>
      </div>
      
    </div>
  </div>
</div>
</div>






