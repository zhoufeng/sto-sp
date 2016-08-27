function taobaoList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};

		Item = Backbone.Model.extend({
			defaults : {
				id:null,
				title : null,
				url : null,
				status : null,
				statusMsg:null,
				eidtUrl:null,
				viewUrl:null,
				createTime : null
			},
		});

		ItemCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+'/top/productcopy/mqRecordList'
		});

		var ItemView = Backbone.View.extend({
			tagName : 'tr',
			template : _.template($('#item').html()),
			events : {
				"blur input,select" : "close",
				//"click a.recopy":"recopy",
				"click #fankui":"fankui"
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				this.model.bind('destroy', this.remove, this);
			},
			recopy:function(e){
				e.preventDefault();
				console.debug(e)
			},
			render : function() {
				var status=this.model.get("status");
				var viewstr=null;
				var vievObje=$(viewstr);
				if(status==2){
					viewstr=_.template($('#status_2').html())(this.model.toJSON());
				}else if(status==3||status==0){
					viewstr=_.template($('#status_3').html())(this.model.toJSON());
				}else{
					viewstr=this.template(this.model.toJSON());
				}
				 this.$el.html(viewstr);
				 return this;
			},
			remove:function(){
				
			},
			fankui:function(){
				$.ajax({
					type:"GET",
					contentType:"application/json",
					data:{"id":this.model.get("id")},
					url:URL_ROOT+"/top/productcopy/fankui",
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						Taobao.itemListView.loadItems();	
					},
					error:function(){
						alert("反馈失败!")
					}
				});
			}
		});
		var ItemListView = Backbone.View.extend({
			el : $("#app"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			pageSize:20,
			current_page:1,
			recordStatus:-1,
			searchVoBean:null,
			// 绑定collection的相关事件
			initialize: function() {
				this.itemList=new ItemCollection();
				this.itemList.bind('add', this.addOne, this);
				
		        // 调用fetch的时候触发reset
				this.itemList.bind('reset', this.addAll, this);
				//this.itemList.fetch();
				this.current_page=1;
				//this.loadItems(1);
		    },
		    loadItems:function(callback){
		    	
		    	var _listbody=$("#listTbody");
		    	_listbody.mask("正在加载,清稍后");

		    	var _this=this;
		    	this.itemList.fetch({silent: true,data:{"pageNo":_this.current_page,"status":this.recordStatus}, success:function(collection, response){ //注释四
		    		
		    		$('#listTbody').html(" ");
					if(response != null&&response.content){
						collection.reset(response.content);
					}else{
						itemListView.render();
					}
					_listbody.unmask();
					_this.initPage(response.totalElements,response.totalPages);
				}});
		    },
		    localItemsByLocation:function(response){
		    	$('#listTbody').html(" ");
				if(response != null&&response.items){
					this.itemList.reset(response.content);
				}else{
					itemListView.render();
				}
				this.initPage(response.totals,response.totalPages);
		    },
		    initPage:function(total,totalPages){
		    	var options = {
	                size:"small",
	                bootstrapMajorVersion:3,
	                alignment: 'center',
	                currentPage: this.current_page,
	                numberOfPages: 5,
	                totalPages: (totalPages==0?1:totalPages),
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
	        	var status=item.get("status");
	        	var statusMsg="";
	        	if(status==0){
	        		statusMsg="正在复制";
	        	}else if(status==1){
	        		statusMsg="复制成功";
	        	}else if(status==2){
	        		statusMsg="复制出错";
	        	}else if(status==3){
	        		statusMsg="复制出错(反馈中)";
	        	}else if(status==10){
	        		statusMsg="正在排队复制中";
	        	}
	        	var offerId=item.get("offerId");;
	        	var eidtUrl="http://offer.1688.com/offer/post/fill_product_info.htm?offerId="+offerId+"&operator=edit"
	        	var viewUrl="http://detail.1688.com/offer/"+offerId+".html";
	        	item.set({"statusMsg":statusMsg,"eidtUrl":eidtUrl,"viewUrl":viewUrl});
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
