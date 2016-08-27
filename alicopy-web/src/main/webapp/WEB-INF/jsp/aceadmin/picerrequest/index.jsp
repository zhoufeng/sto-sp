<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<div class="page-content">
	<!-- <div class="page-header">
		<h1>
			控制台 <small> <i class="icon-double-angle-right"></i> 查看 </small>
		</h1>
	</div> -->
	<!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->

			<div class="alert alert-block alert-success">
				<!-- <button type="button" class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button> -->

				<i class="icon-ok green"></i> 欢迎使用 <strong class="green">
					本软件主要目的是提供快速发布. 在复制完后要去除详情里一些其他店铺的信息(包括其他公司名称等不然会有审核不通过或者该宝贝遭人投诉)</strong> <strong class="red">宝贝复制目前已经能支持淘宝到阿里!在左侧菜单里,如果有什么操作不方便或者有bug的地方,第一时间联系客服的阿里旺旺:kongjishisecom</strong>
			
			</div>
		
			<div class="row">
				<div class="space-6"></div>

				<div class="col-sm-7 infobox-container">
					<div class="infobox infobox-green  ">
						<div class="infobox-icon">
							<i class="icon-comments"></i>
						</div>

						<div class="infobox-data">
							<span class="infobox-data-number">0</span>
							<div class="infobox-content">本月复制图片数</div>
						</div>
					</div>

					<div class="infobox infobox-blue  ">
						<div class="infobox-icon">
							<i class="icon-twitter"></i>
						</div>

						<div class="infobox-data">
							<span class="infobox-data-number">0</span>
							<div class="infobox-content">本月复制宝贝数</div>
						</div>

						<div class="badge badge-success">
							100%
						</div>
					</div>

					<div class="infobox infobox-pink  ">
						<div class="infobox-icon">
							<i class="icon-shopping-cart"></i>
						</div>

						<div class="infobox-data">
							<span class="infobox-data-number">0</span>
							<div class="infobox-content">图片上传失败次数</div>
						</div>
						<div class="stat stat-important">0%</div>
					</div>

					<div class="infobox infobox-red  ">
						<div class="infobox-icon">
							<i class="icon-beaker"></i>
						</div>

						<div class="infobox-data">
							<span class="infobox-data-number">0</span>
							<div class="infobox-content">宝贝复制失败次数</div>
						</div>
					</div>

					<div class="infobox infobox-orange2  ">
						<div class="infobox-chart">
							<span class="sparkline"
								data-values="196,128,202,177,154,94,100,170,224"></span>
						</div>

						<div class="infobox-data">
							<span class="infobox-data-number">0</span>
							<div class="infobox-content">登录次数</div>
						</div>

						<div class="badge badge-success">
							7.2% <i class="icon-arrow-up"></i>
						</div>
					</div>

					<div class="infobox infobox-blue2  ">
						<div class="infobox-progress">
							<div class="easy-pie-chart percentage" data-percent="30"
								data-size="46">
								<span class="percent">30</span>%
							</div>
						</div>

						<div class="infobox-data">
							<span class="infobox-text">复制限制数</span>

							<div class="infobox-content">
								<span class="bigger-110">~</span> 剩余1000
							</div>
						</div>
					</div>

					<div class="space-6"></div>

					

				</div>

				<div class="vspace-sm"></div>


				<!-- /span -->
			</div>
			<!-- /row -->

<!-- <h2>天猫类目查询已经修复.</h2> -->

	

			<!-- PAGE CONTENT ENDS -->
		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
</div>
<!-- /.page-content -->


<!-- page specific plugin scripts -->

		<!--[if lte IE 8]>
		  <script src="${URL_ROOT}/resources/ace/assets/js/excanvas.min.js"></script>
		<![endif]-->

		
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.easy-pie-chart.min.js"></script>
		<script src="${URL_ROOT}/resources/ace/assets/js/jquery.sparkline.min.js"></script>
		

<script type="text/javascript">
		jQuery(function($) {
			$('.easy-pie-chart.percentage').each(function(){
				var $box = $(this).closest('.infobox');
				var barColor = $(this).data('color') || (!$box.hasClass('infobox-dark') ? $box.css('color') : 'rgba(255,255,255,0.95)');
				var trackColor = barColor == 'rgba(255,255,255,0.95)' ? 'rgba(255,255,255,0.25)' : '#E2E2E2';
				var size = parseInt($(this).data('size')) || 50;
				$(this).easyPieChart({
					barColor: barColor,
					trackColor: trackColor,
					scaleColor: false,
					lineCap: 'butt',
					lineWidth: parseInt(size/10),
					animate: /msie\s*(8|7|6)/.test(navigator.userAgent.toLowerCase()) ? false : 1000,
					size: size
				});
			})
		
			$('.sparkline').each(function(){
				var $box = $(this).closest('.infobox');
				var barColor = !$box.hasClass('infobox-dark') ? $box.css('color') : '#FFF';
				$(this).sparkline('html', {tagValuesAttribute:'data-values', type: 'bar', barColor: barColor , chartRangeMin:$(this).data('min') || 0} );
			});
		
		
			
		
		})
	</script>
