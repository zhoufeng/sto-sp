<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" autoFlush="false" buffer="50kb"%>

<%@ taglib uri="/tags/core" prefix="c"%>


<div class="breadcrumbs" id="breadcrumbs">
	 <script type="text/javascript">
		try{
			ace.settings.check('breadcrumbs' , 'fixed')
		}catch(e){}
	</script>

	<ul class="breadcrumb">
		<li>
			<i class="icon-home home-icon"></i>
			<a href="#">首页</a>
		</li>
		<li class="active">控制台</li>
	</ul><!-- .breadcrumb -->

	<div class="nav-search" id="nav-search">
		<form class="form-search">
			<span class="input-icon">
				<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
				<i class="icon-search nav-search-icon"></i>
			</span>
		</form>
	</div> 
	<!-- #nav-search -->
</div>

<%-- <div class="col-xs-12">
	<div class="tabbable">
		<ul class="nav nav-tabs" id="myTab">
			<li class="active"><a data-toggle="tab" href="#home"> <i
					class="green icon-home bigger-110"></i> 操作 </a></li>

			<li class=""><a href="${URL_ROOT}/top/productcopy/history"> 复制历史
					<span class="badge badge-danger">4</span> </a></li>
		</ul>

	</div>
</div>
<div class="col-xs-12 space-12"></div> --%>
