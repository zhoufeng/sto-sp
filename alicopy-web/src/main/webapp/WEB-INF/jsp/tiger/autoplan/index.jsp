<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<style>
.font-size-18 {
	font-size: 18px
}


.autoplan-start-status-img{
	
}


</style>
<script>
jQuery(function($) {
	var play={
		currentState: 'stop',
		// 绑定事件
	　　　　initialize: function() {
	　　　　　　var self = this;
	　　　　　　//self.on("hover", self.transition);
	　　　　},
	 	// 状态转换
	　　　　transition: function(event,status){
	　　　　　　switch(status) {
	　　　　　　　　case "start":
	　　　　　　　　　　this.currentState = 'start';

	　　　　　　　　　　$("#play-info").html("系统状态:正在运行"+' <img width=30 height=30  src="${URL_ROOT}/resources/images/tiger/stop.png" />');
				 $("#btn-start").addClass("disabled");
				 $("#btn-stop").removeClass("disabled");
	　　　　　　　　　　break;
	　　　　　　　　case "stop":
	　　　　　　　　　　this.currentState = 'stop';
				 $("#play-info").html("系统状态:停止运行"+' <img width=30 height=30  src="${URL_ROOT}/resources/images/tiger/start.png" />');
	　　　　　　　　　　 $("#btn-stop").addClass("disabled");
	 			 $("#btn-start").removeClass("disabled");
	　　　　　　　　　　break;
	　　　　　　　　default:
	　　　　　　　　　　//console.log('Invalid State!');
	　　　　　　　　　　break;
	　　　　　　}
	　　　　}
	}
	$("#btn-start").on("click",function(event){
		play.transition(event,"start");
	});
	$("#btn-stop").on("click",function(event){
		play.transition(event,"stop");
	});
	
});
</script>
<jsp:include page="submenu.jsp"></jsp:include>

<div class="row">
	<label class=" control-label" id="play-info">系统状态:停止运行 <img width=30 height=30  src="${URL_ROOT}/resources/images/tiger/start.png" /></label>
</div>
<div class="row">
	<div class="btn-group btn-group-lg" role="group" id="autoplan_btns"
		aria-label="Large button group">
		<button type="button" class="btn btn-default " id="btn-start">开启</button>
		<button type="button" class="btn btn-default disabled" id="btn-stop">停止</button>
	</div>
</div>