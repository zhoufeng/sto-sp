function taobaoList(){
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
			url:Constant.URL_ROOT+'/top/productcopy/items'
		});

		var ItemView = Backbone.View.extend({
			tagName : 'tr',
			template : _.template($('#item').html()),
			events : {
				"blur input,select" : "close",
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				this.model.bind('destroy', this.remove, this);
			},
			render : function() {
				 this.$el.html(this.template(this.model.toJSON()));
				
				 return this;
			},
			remove:function(){
				
			},
		
		});
		var ItemListView = Backbone.View.extend({
			el : $("#app"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			pageSize:24,
			currentUrl:null,
			paginator_options:null,
			current_page:1,
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
		    	//var cate=$("select[name|='product_cate']").val();
		    	var _this=this;
		    	this.itemList.fetch({silent: true,data:{"pageNo":_this.current_page,"url":_this.searchVoBean.url,"shopType":_this.searchVoBean.shopType,"cateUrl":_this.searchVoBean.cateUrl}, success:function(collection, response){ //注释四
		    		
		    		$('#listTbody').html(" ");
					if(response != null&&response.items){
						collection.reset(response.items);
					}else{
						itemListView.render();
					}
					_listbody.unmask();
					_this.initPage(response.totals,response.totalPages);
				}});
		    },
		    localItemsByLocation:function(response){
		    	$('#listTbody').html(" ");
				if(response != null&&response.items){
					this.itemList.reset(response.items);
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
	                totalPages: totalPages,
	                onPageClicked: _.bind(this.gotoPage, this)
	            };
		    	this.paginator_options=options;
		    	this.paginator_options.total=total;
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
		Taobao.itemListView=itemListView;
		Backbone.history.start();
}
Taobao={};