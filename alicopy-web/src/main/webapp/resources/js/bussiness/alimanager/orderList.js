function taobaoList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};

		Item = Backbone.Model.extend({
			defaults : {
				memberId : null,
				gmtCreate : null,
				gmtServiceEnd : null,
				bizStatus:null,
				bizStatusExt:null,
				paymentAmount:null,
				executePrice : null,
				statusMsg:null
			},
		});

		ItemCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+'/manager/getAllOrder'
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
				var html=this.template(this.model.toJSON());
				this.$el.html(html);
				 return this;
			},
			remove:function(){
				
			},
		
		});
		var ItemListView = Backbone.View.extend({
			el : $("#listTbody"),
			events : {
				"click .#add-btn" : "createOnEnter"
			},
			pageSize:20,
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

		    	var _this=this;
		    	var date=$("#date_input").val();
		    	var notFree=$("#cate_free").val();
		    	this.itemList.fetch({silent: true,data:{"date":date,"type":notFree}, success:function(collection, response){ //注释四
		    		
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
				this.initPage(response.totalElements,response.totalPages);
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
	        	var status=item.get("bizStatusExt");
	        	var statusMsg="";
	        	if(status=="service"){
	        		statusMsg="服务中";
	        	}else if(status=="audit_pass"){
	        		statusMsg="审核通过";
	        	}else if(status=="issue_ready"){
	        		statusMsg="待发布";
	        	}else if(status=="suspend"){
	        		statusMsg="挂起";
	        	}else if(status==" arrear_suspend"){
	        		statusMsg="欠费挂起";
	        	}else if(status==" closed"){
	        		statusMsg="关闭";
	        	}else{
	        		statusMsg="作废"
	        	}
	        	item.set({"statusMsg":statusMsg});
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