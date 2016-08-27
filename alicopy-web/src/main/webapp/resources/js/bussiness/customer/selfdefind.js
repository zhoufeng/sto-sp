$(document).ready(function(){
//初始化
$(".chosen-select").chosen();
$(".widget-box.transparent").addClass("collapsed");
getAdresses();
//初始化选择类目
reqAliCate(0, 0,"first_cate");
$.ajax({
	type:"GET",
	contentType:"application/json",
	url:URL_ROOT+"/top/imagecopy/findCateList",
	dataType:"json",
	success:function(data,textStatus,jqXHR){
		for(var i=0;i<data.length;i++){
			$("#albumId").append("<option value="+data[i].id+">"+data[i].name+"</option>");
		}
	}
	
});

$.ajax({
	type:"GET",
	contentType:"application/json",
	url:URL_ROOT+"/top/productcopy/getTemplates",
	dataType:"json",
	success:function(data,textStatus,jqXHR){
		for(var i=0;i<data.length;i++){
			$("#freightTemplateId").append("<option value="+data[i].templateId+">"+data[i].templateName+"</option>");
		}
	}
	
});

$.ajax({
	type:"GET",
	contentType:"application/json",
	url:URL_ROOT+"/top/productcopy/findSelfCate",
	dataType:"json",
	success:function(data,textStatus,jqXHR){
		if(data.errorMsg){
			alert(data.errorMsg);
			return;
		}
		if(!data.cates)return;
		var cates = data.cates;
		//类别
		$("select[name|='userCategorys']").empty();
		if (data.cates&&data.cates.result.toReturn[0]) {
			//var kgstr = "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
			var kgstr ="&nbsp;&nbsp;&nbsp;&nbsp;";
			_.each(data.cates.result.toReturn[0].sellerCats, function(item) {
				var str = "";
				if (item.children && item.children.length > 0) {
					$("select[name|='userCategorys']").append(
							'<optgroup label="'+item.name+'"></optgroup>')
					_.each(item.children, function(child) {
						$("select[name|='userCategorys'] optgroup").last().append(
								'<option value="'+child.id+'" parentid="'+item.id+'">' + kgstr
										+ child.name + '</option>');
					})
					
				} else {
					$("select[name|='userCategorys']").append('<option value="'+item.id+'">' + item.name+ '</option>')
				}

			})
		}//end cate
		$("select[name|='userCategorys']").trigger("chosen:updated");
	}
	
});



//注册事件

//保存设置
$("#self_save_btn").on("click",function(){
	var params=getExtraParams();
	var ajaxParam=JSON.stringify(params,function(key,value){
		if(typeof value==="boolean"){
		 	return value;
		}
		if(value==null||value==""){
			return undefined;
		}
		return value;
	});
		
	$("#self_save_btn").button("loading");
	 $.ajax({
		type:"POST",
		contentType:"application/json",
		data:ajaxParam,
		url:URL_ROOT+"/top/productcopy/saveAliConfig",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			$("#self_save_btn").button('reset');
			alert("保存成功!");
		},
		error:function(){
			$("#self_save_btn").button('reset');
			alert("保存失败!")
		}
	});
});
//读取设置
$("#self_load_btn").on("click",function(){
	$.ajax({
		type:"GET",
		contentType:"application/json",
		url:URL_ROOT+"/top/productcopy/loadAliConfig",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			console.debug(data)
		}
		
	});
});


$("#first_cate").on("change",function(){
	$("#sec_cate").empty();
	$("#third_cate").empty();
	$("#third_cate").append('<option value="0">&nbsp;&nbsp;&nbsp;&nbsp;</option>')
	var cateId=$(this).val();
	var tradeType=$("option:selected",this).attr("tradeType");
	reqAliCate(cateId, tradeType,"sec_cate");
})
$("#sec_cate").on("change",function(){
	$("#third_cate").empty();
	var cateId=$(this).val();
	var tradeType=$("option:selected",this).attr("tradeType");
	var isLeaf=$("option:selected",this).attr("isLeaf");
	if(isLeaf=="true"){
		$("#third_cate").append('<option value="0">&nbsp;&nbsp;&nbsp;&nbsp;</option>')
	}
	reqAliCate(cateId, tradeType,"third_cate");
})



//请求类目
function reqAliCate(categoryId,tradeType,selectId){
	var cateUrl="http://offer.1688.com/offer/asyn/category_selector.json?callback=jQuery172026866585157943657_1425188564958&loginCheck=N&dealType=getSubCatInfo&scene=offer";
	var url=cateUrl+"&categoryId="+categoryId+"&tradeType="+tradeType;
	 $.ajax({
		type:"GET",
		contentType:"application/json",
		data:{"url":url},
		url:URL_ROOT+"/top/productcopy/alicateList",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			for(var i=0;i<data.length;i++){
				$("#"+selectId).append("<option value="+data[i].id+" tradeType="+data[i].tradeType+" isLeaf="+data[i].isLeaf+">"+data[i].name+(data[i].hasVirtual=="true"?"→ ":"")+"</option>");
				
			}
		}
		
	}); 
}
function getAdresses(){
	//发货地址
	$.ajax({
		type:"GET",
		contentType:"application/json",
		url:URL_ROOT+"/top/productcopy/getSendGoodsAddresses",
		dataType:"json",
		success:function(data,textStatus,jqXHR){
			$("#sendGoodsAddressId").html("");
			for(var i=0;i<data.length;i++){
				$("#sendGoodsAddressId").append("<option value="+data[i].deliveryAddressId+">"+data[i].location+data[i].address+"</option>");
			}
		}
		
	});
}
$("#addressrefresh").on("click",getAdresses);
$("#add_pro_btn").on("click",addDiyProp);
function addDiyProp(e){//添加产品属性
	var _this=this;
	var str='<div class="form-group"><div class="col-xs-1"><input type="text" placeholder="属性" data-proname="属性" data-diyprop="true" class="form-control" value="" name="extra_pro" maxlength="12"></div><div class="col-xs-2"><input type="text" placeholder="属性值" data-proname="属性值" data-diyprop="true"  name="extra_pro_value" maxlength="50" class="form-control"></div><div class="col-xs-1"  style="width:50px" ><a target="_self" title="删除" style="width:50px;line-height: 2.5" class="del-diy-prop"  href="#">删除</a></div></div>';
	$("#diy-prop-ctrl").append(str)
	var lastObj=$("#diy-prop-ctrl .del-diy-prop").last();
	lastObj.on("click",function(e){
		var formObj=lastObj.parent().parent();
		formObj.remove();
		lastObj.unbind("click");
		stopEvent(e);
	});
	 return stopEvent(e)
}
function stopEvent(e){
	//阻止默认浏览器动作(W3C) 
    if ( e && e.preventDefault ) 
        e.preventDefault(); 
    //IE中阻止函数器默认动作的方式 
    else
        window.event.returnValue = false; 
    return false;
}

//$("#self_sub_btn").on("click",function(){

$("select[name|='freightType']").on("change",function(){
		if($(this).val()=="T"){
			$("#freightTemplateId_label").css("display","block");
			$("#freightTemplateId").css("display","block");
				if($("#freightTemplateId").val()==null){
				alert("你还没有可供选择模板!保存将出错!请选择其他运费设置选项")
			}
		}else{
			$("#freightTemplateId_label").css("display","none");
			$("#freightTemplateId").css("display","none");
		}
	});
	$(".widget-header h4.lighter").on("click",function(){
	$(".widget-toolbar a").trigger("click");
	})
	});
	
	//设置html的aliconfig
	function loadExtraParams(params){
		var config=JSON.parse(params);
		if(config.expireed){
			$("input[name='expireed']").attr("checked","checked");
		}
	}
	
	function getExtraParams(){
		var params={};
		params.albumId=$("#albumId").val();
		params.periodOfValidity=$("input[name='periodOfValidity']:checked").value;
		var userCategorys=[];
		if($("#userCategorys").val()){
			$.each($("#userCategorys").val(),function(index,item){
				var obj=$("#userCategorys option[value='"+item+"'][parentid]" );
				if(obj.length>0){
				 userCategorys.push($(obj).attr("parentid")+":"+item);
				}else{
					 userCategorys.push(item);
				}
			});
		}
		params.userCategorys=userCategorys;
		
		var zhutuCategorys=[];
		var zhutuAs=$("#zhutuCategorys_chosen a[data-option-array-index]");
		$.each(zhutuAs,function(index,item){
				 zhutuCategorys.push(parseInt($(item).attr("data-option-array-index"))+1);
		});
		params.zhutuCategorys=zhutuCategorys;
		
		params.freightType=$("#freightType option:selected").val();
		if(params.freightType=="T"){
			params.freightTemplateId=$("#freightTemplateId").val();
		}
		params.sendGoodsAddressId=$("#sendGoodsAddressId").val();
		params.selfPrice=$("#selfPrice").val();
		params.priceType=$("#priceType option:selected").val(); 
		params.mixWholeSale=$("input[name='mixWholeSale']:checked").length>0?true:false;
		params.pictureAuthOffer=$("input[name='pictureAuthOffer']:checked").length>0?true:false;
		params.priceAuthOffer=$("input[name='priceAuthOffer']:checked").length>0?true:false;
		params.otherHref=$("input[name='otherHref']:checked").length>0?true:false;
		params.expireed=$("input[name='expireed']:checked").length>0?true:false;
		params.titleReplace=$("input[name='title_replace']:checked").length>0?true:false
		params.titleDelete=$("input[name='title_delete']:checked").length>0?true:false
		params.titleReplaceOld=$("#title_replace_old").val(); 
		params.titleReplaceNew=$("#title_replace_new").val(); 
		params.titleDeleteKey=$("#title_delete_key").val()
		params.contentReplace=$("input[name='content_replace']:checked").length>0?true:false
		params.contentDelete=$("input[name='content_delete']:checked").length>0?true:false
		params.contentReplaceOld=$("#content_replace_old").val(); 
		params.contentReplaceNew=$("#content_replace_new").val(); 
		params.contentDeleteKey=$("#content_delete_key").val();
		params.customerCate=$("input[name='customer_cate']:checked").length>0?true:false
		params.customerCateId=$("#third_cate option:selected").val(); 
		if(params.customerCateId=="0"){
			params.customerCateId=$("#sec_cate option:selected").val()
		}
		params.ignoreType=$("input[name='ignoreType']:checked").length>0?true:false;
		params.ignoreTypeVal=$("#ignoreTypeVal option:selected").val(); 
		params.offerWeight=$("input[name='offerWeight']").val();
		params.newAlbum=$("input[name='new_album']:checked").length>0?true:false;
		params.offerUnit=$("input[name='offerUnit']").val();
		var extraPros=$("input[name='extra_pro']");
		var extraProValues=$("input[name='extra_pro_value']");
		
		params.imageList=$("textarea[name='imageList']").val();
		params.selfDesc=$("textarea[name='selfDesc']").val();
		
		params.extra={};
		for(var i=0;i<extraPros.length;i++){
			params.extra[$(extraPros[i]).val()]=$(extraProValues[i]).val();
		}
		
		return params;
}