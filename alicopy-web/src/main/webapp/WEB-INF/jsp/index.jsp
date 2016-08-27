<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache">
<title>Insert title here</title>

</head>
<script src="/sample/resources/js/jQuery.js" type="text/javascript"></script>
<script type="text/javascript">

(function($){
    // 保存原有的jquery ajax;
    var $_ajax = $.ajax;
	$.ajax1 = function(options){
		var originalSuccess,
			mySuccess,
			success_context;
	
		if (options.success) {
                        // save reference to original success callback
			originalSuccess = options.success;
			success_context = options.context ? options.context : $;
			
                        // 自定义callback
			mySuccess = function(data) {
				
				
									   
                           
                            if (data['access-denied']) {
                                  if (data.cause==='AUTHENTICATION_FAILURE') {
                                    alert('登录超时,请重新登录.');
					window.location.href = contextPath + '/';
                                  } else if (data.cause==='AUTHORIZATION_FAILURE') {
									   if (data=="noright")
									   {
                                        alert('对不起，你没有访问该资源的权限.');
									   }
				    }
                                   return;
						
							 
                                // call original success callback							
				originalSuccess.apply(success_context, arguments);
			};
                        // override success callback with custom implementation
			options.success = mySuccess;
		}
		
                // call original ajax function with modified arguments
		$_ajax.apply($, arguments);
	};
	}
})(jQuery);
function testCli(){
	// 发送任务完成请求
    $.ajax({
    	url:"/sample/main/ajax",
    	type: "post", 
    	dataType: "json", 
    	success: function (data) {
    	alert(data)
    	},
    	error: function (XMLHttpRequest, textStatus, errorThrown) {
    	console.debug(XMLHttpRequest,textStatus,errorThrown)
    	}
    });
    
    
    
}

</script>
<body>
<div onclick="testCli()">点击</div>
</body>
</html>