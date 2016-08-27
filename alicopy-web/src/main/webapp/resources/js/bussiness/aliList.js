function aliList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};
		Item = Backbone.Model.extend({
			defaults : {
				cid : null,
				title : null,
				price : null,
				picUrl : null,
				modified : null
			},
		});

		ItemCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+'/ali/opt/onsale/item'
		});

		var ItemView = Backbone.View.extend({
			tagName : 'tr',
			template : _.template($('#item').html()),
			events : {
				"click #del" : "displayInfo",
				"dblclick td" : "edit",
				"blur input,select" : "close",
				"click .del" : "clear",
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				//this.model.bind('destroy', this.remove, this);
			},
			render : function() {
				 this.$el.html(this.template(this.model.toJSON()));
				 return this;
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
			el : $("#app"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			pageSize:10,
			current_page:1,
			// 绑定collection的相关事件
			initialize: function() {
				this.itemList=new ItemCollection();
				this.itemList.bind('add', this.addOne, this);
		        // 调用fetch的时候触发reset
				this.itemList.bind('reset', this.addAll, this);
				//this.itemList.fetch();
				var _this=this;
				this.current_page=1;
				this.itemList.fetch({silent: true,data:{"pageNo":this.current_page,"seller_cids":0}, success:function(collection, response){ //注释四
					if(response != null){
						collection.reset(response.toReturn);
					}else{
						itemListView.render();
					}
					_this.initPage(response.totalResults);
				}});
		    },
		    loadItems:function(){
		    	this.itemList.fetch({silent: true,data:{pageNo:this.current_page}, success:function(collection, response){ //注释四
					if(response != null&&response.items){
						collection.reset(response.items);
					}else{
						itemListView.render();
					}
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
	        	var view = new ItemView({model:item});
	        	$("#listTbody").append(view.render().el);
	        },
	        addAll : function(){
	        	this.itemList.each(this.addOne);
	        }
		});
		
		
		
		var itemListView = new ItemListView();
		Backbone.history.start();
}