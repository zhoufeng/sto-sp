function taobaoList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};

		Item = Backbone.Model.extend({

		});

		ItemListCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+"/top/imagecopy/search"
		});

		var ItemView = Backbone.View.extend({
			tagName:"li",
			className:'imagecopy_li',
			template : _.template($('#item').html()),
			events : {
				"click #del" : "displayInfo",
				"dblclick td" : "edit",
				"blur input,select" : "close",
				"click #btn_video_gen" : "gen",
				"click i" : "down"
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				//this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				//this.model.bind('destroy', this.remove, this);
			},
			render : function() {
				 this.$el.html(this.template(this.model.toJSON()));
				 return this;
			},
			remove:function(){
				
			},
			zhutuCount:1,
			yanseCount:1,
			xiangqinCount:1,
			commonCount:1,
			down:function(event){
				event.preventDefault()
				if(!itemListView.albumId){
					$("#top_pic_space_div").modal();
					return;
				}
					
				
				var _this=this;
				var i=$(this.el).find("i");
				i.css("color","#7CFC00");
				var a='<i class="icon-spinner icon-spin orange bigger-125"></i>';
				var inner=$(this.el).find(".inner");
				inner.text("loading");
				inner.before(a);
				i.text("正在上传");
				var text=$(this.el).find(".text");
				text.css("opacity",1);
				var url=this.model.get("url");
				
				var divName=$(this.el).parents(".row").attr("id");
				var name=this.model.get("name")+"_"+this[divName+"Count"];
				$.ajax({
				type:"GET",
				contentType:"application/json",
				url:URL_ROOT+"/top/imagecopy/uploadImage",
				data:{"url":url,"pictureCategoryId":itemListView.albumId,"name":name},
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					if(!data.errorCode){
						_this[divName+"Count"]++;
						i.text("已经上传");
						inner.text("点击查看");
						text.css("opacity",0);
						text.remove(".icon-spinner");
						i.unbind("down",_this.down);
						
						$(_this.el).append('<div class="tags"><span class="label-holder"> <span class="label label-success">上传成功</span></span></div>');
						$(_this.el).find(".tools.tools-bottom").remove();
					}else{
						i.text("再次上传");
						i.css("color","#FFFFFF");
						text.find("i").remove();
						inner.text("");
						text.css("opacity",0.5);
						$(_this.el).append('<div class="tags"><span class="label-holder"> <span class="label label-danger">上传失败</span></span></div>');
					}
				}
				});
				
				
			},
		    displayInfo:function(){
		    	if(_.isEmpty(infoView)){
		    		infoView = new ItemInfoView({model : this.model});
		    	}else{
		    		infoView.model = this.model;
		    		infoView.model.unbind('change');
		    		infoView.model.bind('change', this.render, this);
		    		infoView.model.bind('change', infoView.render, infoView);
		    	}
		    }
		});
		
		
		var ItemListView = Backbone.View.extend({
			el : $("#image_content"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			zhutuDiv:null,
			albumId:null,
			// 绑定collection的相关事件
			initialize: function() {
				this.itemList=new ItemListCollection();
				this.itemList.bind('add', this.addOne, this);
		        // 调用fetch的时候触发reset
				this.itemList.bind('reset', this.addAll, this);
				//this.itemList.fetch();
	
		    },
		    loadItems:function(){
		    	var _listbody=$("#image_content");
		    	_listbody.mask("正在加载,清稍后");
		    	var _this=this;
		    	var urlparam=$("#search_url_input").val();
		    	this.itemList.fetch({silent: true,data:{"url":urlparam}, success:function(collection, response){ //注释四
		    		if(response.errorCode){
		    			var ale='<div class="alert alert-danger"><button type="button" class="close" data-dismiss="alert"><i class="icon-remove"></i></button><strong><i class="icon-remove"></i>'+"警告!"+'</strong>'+response.errorMsg+'<br></div>'
		    			$('#image_content').before(ale);
		    			$(ale).insertAfter("#search_div");
		    		}else{
		    			$('#image_content').html(" ");
		    		}
					if(response != null&&response.images){
						collection.reset(response.images);
					}else{
						itemListView.render();
					}
					_listbody.unmask();
					_this.initColorbox();
				}});
		    },
		    initColorbox:function(){
				var colorbox_params = {
						reposition:true,
						scalePhotos:true,
						scrolling:false,
						previous:'<i class="icon-arrow-left"></i>',
						next:'<i class="icon-arrow-right"></i>',
						close:'&times;',
						current:'{current} of {total}',
						maxWidth:'100%',
						maxHeight:'100%',
						onOpen:function(){
							document.body.style.overflow = 'hidden';
						},
						onClosed:function(){
							document.body.style.overflow = 'auto';
						},
						onComplete:function(){
							$.colorbox.resize();
						}
					};

				$('#image_content [data-rel="colorbox"]').colorbox(colorbox_params)
				$("#cboxLoadingGraphic").append("<i class='icon-spinner orange'></i>");//let's add a custom loading icon
				
		    },
	        addOne : function(item,b,c){
	        	
	        },
	        genDiv:function(divName,title){
	        	var title='<div class="col-xs-12"><h3 class="header smaller lighter green"><i class="icon-bullhorn"></i>'+title+'</h3></div>';
	        	var retDiv=$('<div class="row" id="'+divName+'">');
	        	retDiv.append(title);
	        	retDiv.append('<div class="col-xs-12"><div class="row-fluid"><ul class="ace-thumbnails" >');
	        	return retDiv;
	        },
	        addAll : function(images){
	        	
	        	this.zhutuDiv=this.genDiv("zhutu", "主图");
	        	this.yanseDiv=this.genDiv("yanse", "颜色分类");
	        	this.xiangqinDiv=this.genDiv("xiangqin", "详情");
	        	this.commonDiv=this.genDiv("common", "图片");
	        	var _this=this;
	        	var zhutuCount=0;
	        	var yanse=0;
	        	var xiangqin=0;
	        	var common=0;
	        	this.itemList.each(function(item,b,c){
	        		var view = new ItemView({model:item});
	        		if(item.get("type")==1){
	        			zhutuCount++;
	        			$(_this.zhutuDiv).find(".ace-thumbnails").append(view.render().el);
		        	}else if(item.get("type")==2){
		        		yanse++;
		        		$(_this.yanseDiv).find(".ace-thumbnails").append(view.render().el);
		        	}else if(item.get("type")==3){
		        		xiangqin++;
		        		$(_this.xiangqinDiv).find(".ace-thumbnails").append(view.render().el);
		        	}else {
		        		common++;
		        		$(_this.commonDiv).find(".ace-thumbnails").append(view.render().el);
		        	}
	        	});
	        	
	        	if(zhutuCount>0){
	        		$("#image_content").append(this.zhutuDiv);
	        	}
	        	if(yanse>0){
	        		$("#image_content").append(this.yanseDiv);
	        	}
	        	if(xiangqin>0){
	        		$("#image_content").append(this.xiangqinDiv);
	        	}
	        	if(common>0){
	        		$("#image_content").append(this.commonDiv);
	        	}
	        }
		});
		
		var itemListView = new ItemListView();
		Taobao.itemListView=itemListView;
		Backbone.history.start();
}
Taobao={};