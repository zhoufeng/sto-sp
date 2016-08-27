<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>

<div class="page-content">
	<!-- <div class="page-header">
		<h1>
			控制台 <small> <i class="icon-double-angle-right"></i> 查看 </small>
		</h1>
	</div> -->
	<!-- /.page-header -->


		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->

			<div class="alert alert-block alert-success">
				<!-- <button type="button" class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button> -->

				<i class="icon-ok green"></i><strong class="red"></strong>
			
			</div>
		
		</div>
		
		 <div class="col-xs-12">
			PAGE CONTENT BEGINS

			<div class="alert alert-block alert-success">
				<button type="button" class="close" data-dismiss="alert">
					<i class="icon-remove"></i>
				</button>
					<i class="icon-ok green"></i>注意事项:如果复制错误,请查看图片空间是否满了或者相册数量是否超过500个</br>
				<i class="icon-ok green"></i> <strong class="red">类目复制地址规范(2种分为首页地址和类目地址):</strong></br>
				 例如:首页地址:</br> 阿里巴巴:http://jkml2012.1688.com</br>天猫:https://emphiten.tmall.com</br>淘宝:https://tone-elegancy.taobao.com</br>
					类目地址:</br>阿里巴巴:http://jkml2012.1688.com/page/offerlist_63661771.htm</br>天猫:https://emphiten.tmall.com/category-1119157910.htm</br>淘宝:https://tone-elegancy.taobao.com/category-1144499933.htm
			</div>
		
		</div>
		
	
	
		<div class="col-xs-12">
		
			<h1>版本1.0.0</h1>
			<div class="col-xs-6">
			<h4 class="smaller">
				<i class="icon-ok bigger-200 green"></i>
				<span>修复内容</span>
			</h4>
			<div class="text-success success" style="font-size: 16px;">
			<p><i class="icon-caret-right blue"></i>1 : 修复复制淘宝链接时候去外链该功能错误问题.</p>
			<p><i class="icon-caret-right blue"></i>2 : 修复淘宝运费模板问题</p>
			<p><i class="icon-caret-right blue"></i>3 : 修复建议零售价不能复制问题</p>
			<p><i class="icon-caret-right blue"></i>4 : 修复淘宝复制自定义分类不能正确选择问题</p>
			</div>
			</div>
			<div class="col-xs-6">
			<h4 class="smaller">
				<i class="icon-ok bigger-200 green"></i>
				<span>新增功能</span>
			</h4>
			<div class="text-success success" style="font-size: 16px;">
			<p><i class="icon-caret-right blue"></i>1 : 增加复制后直接下架的功能选项</p>
			<p><i class="icon-caret-right blue"></i>2 : 增加忽略重复功能选项</p>
			<p><i class="icon-caret-right blue"></i>3 : 增加标题替换和删除功能选项</p>
			<p><i class="icon-caret-right blue"></i>4: 增加主图自由选择功能选项</p>
			</div>
		</div>
		<h4 class="smaller text-info" style="text-align: left;margin-top: 50px">
				如有其他的需求功能,请第一时间联系客服的阿里旺旺:<a target="_blank" href="http://amos.alicdn.com/msg.aw?spm=a261gw.7796156.0.0.yqDEF3&v=2&uid=kongjishisecom&site=cnalichn&s=4" ><img border="0" src="http://amos.alicdn.com/online.aw?v=2&uid=kongjishisecom&site=cnalichn&s=4&charset=UTF-8" alt="有事联系我" /></a>.我们在确定需求后会一个星期内更新版本.
				</br>
				如有有程序错误的问题,请第一时间联系客服的阿里旺旺:<a target="_blank" href="http://amos.alicdn.com/msg.aw?spm=a261gw.7796156.0.0.yqDEF3&v=2&uid=kongjishisecom&site=cnalichn&s=4" ><img border="0" src="http://amos.alicdn.com/online.aw?v=2&uid=kongjishisecom&site=cnalichn&s=4&charset=UTF-8" alt="有事联系我" /></a>我们在确定需求后会48小时内解决.
		</h4>
		</div>
		
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
