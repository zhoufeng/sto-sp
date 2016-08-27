function taobaoList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};

		Item = Backbone.Model.extend({
			defaults : {
				offerId : null,
				subject : null,
				detailsUrl:null,
				imageUrl : null,
				editSubject:null,
				subjectRender:null,
				priceUnit : null
			},
		});

		ItemCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+'/alimanager/title/searchproducts'
		});

		var ItemView = Backbone.View.extend({
			tagName : 'tr',
			template : _.template($('#item').html()),
			events : {
				"click div.personConfigPreviewItem" : "edit",
				"blur input.personConfigPreviewItem" : "repire",
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				this.model.bind('destroy', this.remove, this);
			},
			render : function() {
				var html=this.template(this.model.toJSON());
				this.$el.html(html);
				 return this;
			},
			repire:function(event){
				$(event.currentTarget).css("display","none");
				var div=$(event.currentTarget).prev("div");
				div.css("display","block");
				var val=$(event.currentTarget).val();
				this.model.set({"editSubject":val});
			},
			upvent:function(){
				//$(event.currentTarget).blur();
			},
			edit:function(event){
				$(event.currentTarget).css("display","none");
				var input=$(event.currentTarget).next("input");
				input.css("width",$(event.currentTarget).parent().width())
				input.css("display","block");
				input.focus();
			},
			save:function(){
				 $.ajax({
					type:"GET",
					contentType:"application/json",
					data:{"id":this.model.get("numIid")},
					url:URL_ROOT+"/top/productcopy/taobaoitem",
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						//console.debug(data);
					}
				});
			},

			down:function(){
				var form=$("#videoForm");
				if(form.length==0){
				var form=$("<form>");//定义一个form表单
					form.attr("style","display:none");
					form.attr("target","");
					form.attr("id","videoForm");		
					form.attr("method","post");
					var input1=$("<input>");
					input1.attr("type","hidden");
					input1.attr("name","exportData");
					input1.attr("value",(new Date()).getMilliseconds());
					$("body").append(form);//将表单放置在web中
					form.append(input1);
				}
				form.attr("action",URL_ROOT+"/top/video/download?num_iid="+this.model.get("numIid"));
				form.submit();//表单提交
				
				//windows.open(URL_ROOT+"/top/video/download?num_iid="+this.model.get("numIid"));
			},
			gen:function(){
				//请求视频设置参数
		
				$($(this.el)).find("button[id|='btn_video_gen']").button("loading");
				/*$.ajax({
				type:"GET",
				contentType:"application/json",
				url:URL_ROOT+"/top/video/task?num_iid="+this.model.get("numIid"),
				dataType:"json",
				success:function(data,textStatus,jqXHR){
					$.gritter.add({
						title: '温馨提示',
						time:3000,
						text: '视频正在后台生成,亲请稍后下载',
						//image: $path_assets+'/avatars/avatar3.png',
						sticky: false,
						before_open: function(){
							if($('.gritter-item-wrapper').length >= 3)
							{
								return false;
							}
						},
						class_name: 'gritter-info' + (!$('#gritter-light').get(0).checked ? ' gritter-light' : '')
					});
			

				}
				});*/
				
				$.gritter.add({
					title: '温馨提示',
					text: '<div style="color:red">'+	this.model.get("title")+'</div>正在后台生成,亲请稍后点击下载',
					//image: $path_assets+'/avatars/avatar3.png',
					sticky: false,
					time:2000,
					before_open: function(){
						if($('.gritter-item-wrapper').length >= 2)
						{
							return false;
						}
					},
					class_name: 'gritter-light'
				})
			     /* $.gritter.add({
			          // (string | mandatory) the heading of the notification
			          title: 'This is a regular notice!',
			          // (string | mandatory) the text inside the notification
			          text: 'This will fade out after a certain amount of time. Vivamus eget tincidunt velit. Cum sociis natoque penatibus et <a href="#" style="color:#ccc">magnis dis parturient</a> montes, nascetur ridiculus mus.',
			          // (string | optional) the image to display on the left
			          //image: 'http://a0.twimg.com/profile_images/59268975/jquery_avatar_bigger.png',
			          // (bool | optional) if you want it to fade out on its own or just sit there
			          sticky: false,
			          // (int | optional) the time you want it to be alive for before fading out
			          time: ''
			        });*/
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
			el : $("#listTbody"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			pageSize:10,
			current_page:1,
			obj:null,
			q:null,
			// 绑定collection的相关事件
			initialize: function() {
				this.itemList=new ItemCollection();
				this.itemList.bind('add', this.addOne, this);
		        // 调用fetch的时候触发reset
				this.itemList.bind('reset', this.addAll, this);
				//this.itemList.fetch();
				
				this.current_page=1;
				this.loadItems(1);
		    },
		    save:function(){
		    	var list = $("input[name='topitem_checkbox'][type|='checkbox']:checked");
		    	var _this = this;
		    	if(list&&list.length>0){
		    		var urlList = new Array();
		    		_.each(list,function(item){
		    			var offerId=$(item).attr("data_id");
		    			var model= _this.itemList.filter(function(book) {
		    				  return book.get("offerId") == offerId;
		    				});

		    			urlList.push({"offerId":model[0].get("offerId"),"subject":model[0].get("editSubject")});
		    		});
		    		$.ajax({
						type : "POST",
						contentType : "application/json",
						data : JSON.stringify({
							"urlList" : urlList
						}),
						url : URL_ROOT + "/alimanager/title/saveByIds",
						dataType : "json",
						success : function(data, textStatus, jqXHR) {
							if (data.errorCode) {
								alert(data.errorMsg);
							}
						

						}
					});
		    		
		    		
		    	}else{
		    		return;
		    	}
		    },
		    changeAll:function(obj){
		    	this.obj=obj;
		    	_.each(this.itemList.models,function(model){
		    		var initalSub=model.get("subject");
		    		var lastSub=initalSub;
		    		var lastSubRender=initalSub;
		    		if(obj.rep_prev_word!=""){
		    			lastSub=initalSub.replace(eval("/"+obj.rep_prev_word+"/g"),obj.rep_next_word);
		    			lastSubRender=initalSub.replace(eval("/"+obj.rep_prev_word+"/g"),'<span style="color:red">'+obj.rep_prev_word+'</span>');
		    		}
		    		if(obj.del_word!=""){
		    			lastSub=lastSub.replace(eval("/"+obj.del_word+"/g"),"");
		    			lastSubRender=lastSubRender.replace(eval("/"+obj.del_word+"/g"),'<span style="color:#000000">'+obj.del_word+'</span>');
		    		}
		    		if(obj.add_prev_word!=""){
		    			lastSub=obj.add_prev_word+lastSub;
		    		}
		    		if(obj.add_next_word!=""){
		    			lastSub=lastSub+obj.add_next_word;
		    		}
		    		model.set({"editSubject":lastSub,"subjectRender":lastSubRender});
		    		
		    	});
		    },
		    test:function(){
		    	
		    },
		    loadItems:function(callback){
		    	/*for (var i = 0, l = this.itemList.models.length; i < l; i++) {
                   this.itemList.models[i].destroy();
                }*/
		    	//var _maskDiv=$("<div id=\"fullbg\" />");
		    	var _listbody=$("#listTbody");
		    	_listbody.mask("正在加载,清稍后");
		    	//console.debug(_maskDiv.width(),_maskDiv.height())
		    	//$("<div class=\"datagrid-mask\" style=\"background:#666666;\"></div>").css({display:"block",width:_maskDiv.width(),height:_maskDiv.height()}).appendTo("#listTbody");
		    	//console.debug(_listbody.offset().top,_listbody.offset().top)
		    	//_maskDiv.css({top:_listbody.offset().top,left:_listbody.offset().top,width:_listbody.width(),height:_listbody.height()})
		    	//_listbody.wrap(_maskDiv);
		    	//_maskDiv.show();
		    	var _this=this;
		    	this.itemList.fetch({silent: true,data:{pageNo:this.current_page,"pageSize":this.pageSize,"sellerCids":""}, success:function(collection, response){ //注释四
		    		
		    		$('#listTbody').html(" ");
					if(response != null&&response.toReturn){
						collection.reset(response.toReturn);
					}else{
						itemListView.render();
					}
					_listbody.unmask();
					_this.initPage(response.total);
				}});
		    },
		    initPage:function(total){
		    	var options = {
	                size:"small",
	                bootstrapMajorVersion:3,
	                alignment: 'center',
	                currentPage: this.current_page,
	                numberOfPages: 5,
	                totalPages: Math.max(Math.ceil(total / this.pageSize), 1),
	                onPageClicked: _.bind(this.gotoPage, this)
	            };
		    	
				$('#pageTable1').bootstrapPaginator(options);
                $('#spanTotal1').text('共：'+options.totalPages+"页  " + total + ' 条记录');
		    },
		    gotoPage: function(event, originalEvent, type, page) {
                event.stopPropagation();
                this.current_page=page;
                this.loadItems();
                //this.model.current_page = page;
                //this.navigate1();
            },
	        addOne : function(item,b,c){
	        	item.set({"editSubject":item.get("subject"),"subjectRender":item.get("subject")});
	        	var view = new ItemView({model:item});
	        	$("#listTbody").append(view.render().el);
	        },
	        addAll : function(){
	        	this.itemList.each(this.addOne);
	        }
		});
		
		
		
		var itemListView = new ItemListView();
		Taobao.itemListView=itemListView;
		Backbone.history.start();
}
Taobao={};