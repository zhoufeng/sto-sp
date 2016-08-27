<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"  autoFlush="false" buffer="50kb"%>
<%@ taglib uri="/tags/core" prefix="c"%>
<script>
(function($){
	//备份jquery的ajax方法
	var _ajax=$.ajax;
	//重写jquery的ajax方法
	$.ajax=function(opt){
		//备份opt中error和success方法
		var fn = {
			error:function(XMLHttpRequest, textStatus, errorThrown){},
			success:function(data, textStatus){}
		}
		if(opt.error){
			fn.error=opt.error;
		}
		if(opt.success){
			fn.success=opt.success;
		}
		
		//扩展增强处理
		var _opt = $.extend(opt,{
			error:function(XMLHttpRequest, textStatus, errorThrown){
				//错误方法增强处理
				
				fn.error(XMLHttpRequest, textStatus, errorThrown);
			},
			success:function(data, textStatus){
				//成功回调方法增强处理
				if(data&&data.code==666){
					if(data.loginUrl!=""){
						$("#user_login_modal a").first().attr("href",data.loginUrl);
					}
					$("#user_login_modal").modal();
					return;
				}
				fn.success(data, textStatus);
			}
		});
		_ajax(_opt);
	};
})(jQuery);
</script>

		<div class="navbar navbar-default" id="navbar">
			<script type="text/javascript">
				try{ace.settings.check('navbar' , 'fixed')}catch(e){}
			</script>

			<div class="navbar-container" id="navbar-container">
				<div class="navbar-header pull-left">
					<a href="#" class="navbar-brand">
						<small>
							<i class="icon-leaf"></i>
							阿里一键生成后台管理
						</small>
					</a><!-- /.brand -->
				</div><!-- /.navbar-header -->

				<div class="navbar-header pull-right" role="navigation">
					<ul class="nav ace-nav">
						<li>
						<a target="_blank" href="http://www.taobao.com/webww/ww.php?ver=3&touid=kongjishisecom&siteid=cntaobao&status=1&charset=utf-8" ><img border="0" src="http://amos.alicdn.com/online.aw?v=2&uid=kongjishisecom&site=cntaobao&s=1&charset=utf-8" alt="点击这里给我发消息" /></a>	</li>
						<li>
						<a  href="http://blog.kongjishise.com" target="_blank" style="cursor: pointer;">
						<span class="label label-xlg label-primary arrowed arrowed-right">演示文档</span>
						</a>
						</li>
						
						<li class="green">
							<a data-toggle="dropdown" class="dropdown-toggle" href="#">
								<i class="icon-envelope icon-animated-vertical"></i>
								<span class="badge badge-success">5</span>
							</a>

							<ul class="pull-right dropdown-navbar dropdown-menu dropdown-caret dropdown-close">
								<li class="dropdown-header">
									<i class="icon-envelope-alt"></i>
									5条消息
								</li>

								<li>
									<a href="#">
										<img src="${URL_ROOT}/resources/ace/assets/avatars/avatar.png" class="msg-photo" alt="Alex's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Alex:</span>
												不知道写啥 ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>1分钟以前</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="${URL_ROOT}/resources/ace/assets/avatars/avatar3.png" class="msg-photo" alt="Susan's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Susan:</span>
												不知道翻译...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>20分钟以前</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="#">
										<img src="${URL_ROOT}/resources/ace/assets/avatars/avatar4.png" class="msg-photo" alt="Bob's Avatar" />
										<span class="msg-body">
											<span class="msg-title">
												<span class="blue">Bob:</span>
												到底是不是英文 ...
											</span>

											<span class="msg-time">
												<i class="icon-time"></i>
												<span>下午3:15</span>
											</span>
										</span>
									</a>
								</li>

								<li>
									<a href="inbox.html">
										查看所有消息
										<i class="icon-arrow-right"></i>
									</a>
								</li>
							</ul>
						</li>

						<li class="light-blue">
							<a data-toggle="dropdown" href="#" class="dropdown-toggle">
								<img class="nav-user-photo" src="${URL_ROOT}/resources/ace/assets/avatars/user.jpg" alt="Jason's Photo" />
								<span class="user-info" style="max-width: 200px;">
									<small>欢迎光临,</small>
									<c:if test="${sessionScope.aliInfo!=null}">
	                                	${ sessionScope.aliInfo.sellerName}
	                                </c:if>
	                                <c:if test="${sessionScope.aliInfo==null}">
	                        			亲们
	                                </c:if>
								</span>

								<i class="icon-caret-down"></i>
							</a>

							<ul class="user-menu pull-right dropdown-menu dropdown-yellow dropdown-caret dropdown-close">
								<%-- <c:if test="${sessionScope.aliInfo!=null}">
                                	<li><a href="${URL_ROOT}/console/loginout"><i class="icon-off"></i>退出</a></li>
                                </c:if>
                                <c:if test="${sessionScope.aliInfo==null}">
                        			<li><a href="${URL_ROOT}/top/imagecopy"><i class="icon-off"></i>登录</a></li>
                                </c:if> --%>
							</ul>
						</li>
					</ul><!-- /.ace-nav -->
				</div><!-- /.navbar-header -->
			</div><!-- /.container -->
		</div>
		
		<!-- Modal -->
<div class="modal fade" id="user_login_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
                <button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button>
                <h3 id="myModalLabel">登录</h3>
            </div>
            <div class="modal-body text-center">
                <p>还没有登录，点击下面的链接进行登录</p>
                <a  href="${URL_ROOT }" class="btn btn-large btn-primary" id="aLogin">登录</a> 
                <p>&nbsp;</p>
                <p style="font-size:18px;" class=" text-info "><i class="icon  icon-info-sign"></i>提示:如果要续费请点击续费按钮<a target="_blank" href="http://pc.1688.com/product/operate.htm?key=k_bbyjfz" class="btn btn-info">续费</a></p>
                
            </div>
      <div class="modal-footer">
        	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      </div>
    </div>
  </div>
</div>
