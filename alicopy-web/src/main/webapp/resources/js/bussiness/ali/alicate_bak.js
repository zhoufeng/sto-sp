function aliList(){
		_.templateSettings = {
			interpolate : /\<\@\=(.+?)\@\>/gim,
			evaluate : /\<\@([\s\S]+?)\@\>/gim,
			escape : /\<\@\-(.+?)\@\>/gim
		};
		Item = Backbone.Model.extend({
			defaults : {
				id : null,
				isLeaf : null,
				isVirtual : null,
				name : null,
				tradeType : null
			}
		});

		ItemCollection = Backbone.Collection.extend({
			model : Item,
			url:Constant.URL_ROOT+'/top/productcopy/alicateList'
		});

		var ItemView = Backbone.View.extend({
			tagName : 'li',
			template : _.template($('#item').html()),
			events : {
				"click" : "cli"
			},
			initialize : function(){
				// 每次更新模型后重新渲染
				this.model.bind('change', this.render, this);
				// 每次删除模型之后自动移除UI
				//this.model.bind('destroy', this.remove, this);
			},
			cli:function(){
				this.$el.parent().find("li.current").removeClass("current");
				this.$el.addClass("current");
				categoryModuleView.reqAjax(this);
			},
			render : function() {
				this.$el.attr("data-id",this.model.get("id"));
				if(this.model.get("isLeaf")=="false")this.$el.addClass("leaf");
				 this.$el.html(this.template(this.model.toJSON()));
				 return this;
			}
		});
		
		var basepro={
				url:"http://offer.1688.com/offer/asyn/category_selector.json?callback=jQuery172026866585157943657_1425188564958&loginCheck=N&dealType=getSubCatInfo&scene=offer",
				events : {
					"click .#add-btn" : "createOnEnter"
				},
				categoryId:null,
				tradeType:null,
				isLeaf:false,
				// 绑定collection的相关事件
				initialize: function() {
					this.itemList=new ItemCollection();
					//this.itemList.bind('add', this.addOne, this);
			        // 调用fetch的时候触发reset
					this.itemList.bind('reset', this.addAll, this);
					//this.itemList.fetch();
					var _this=this;
					if(_this.dataLevel==1){
						this.itemList.fetch({silent: true,data:{"url":_this.url+"&categoryId=0&tradeType=0"}, success:function(collection, response){ //注释四
							if(response != null){
								collection.reset(response);
							}
							
						}});
				}
			    },
			    loadItems:function(){
			    	var _this=this;
			    	this.itemList.fetch({silent: true,data:{"url":this.url+"&categoryId="+this.categoryId+"&tradeType="+this.tradeType}, success:function(collection, response){ //注释四
			    		_this.$el.html(" ");
						if(response != null&&response){
							collection.reset(response);
						}
					}});
			    },
		        addOne : function(item,b,c){
		        	var view = new ItemView({model:item});
		        	this.$el.append(view.render().el);
		        },
		        addAll : function(){
		        	this.itemList.each(this.addOne,this);
		        }
		};
		var ItemListView1 = Backbone.View.extend(_.extend({
			el : $("ul[data-level=1]"),
			dataLevel:1
			
		},basepro));
		
		var ItemListView2 = Backbone.View.extend(_.extend({
			el : $("ul[data-level=2]"),
			dataLevel:2
		},basepro));
		
		var ItemListView3 = Backbone.View.extend(_.extend({
			el : $("ul[data-level=3]"),
			dataLevel:3
		},basepro));
		
		var ItemListView4 = Backbone.View.extend(_.extend({
			el : $("ul[data-level=4]"),
			dataLevel:4
		},basepro));
		
		var CategoryModuleView=Backbone.View.extend({
			el:$(".category-module"),
			catsId:null,
			reqAjax:function(itemview){
				this.catsId=null;
				if(itemview.model.get("isLeaf")=="true"){
					this.catsId=itemview.model.get("id");
					return;
				}
				var view=itemview.$el.parent();
				var dataLevel=$(view).attr("data-level");
				if(dataLevel==1){
					itemListView2.categoryId=itemview.model.get("id");
					itemListView2.tradeType=itemview.model.get("tradeType");
					itemListView2.isLeaf=itemview.model.get("isLeaf");
					itemListView3.categoryId==null;
					itemListView2.loadItems();
					itemListView2.$el.css("display","block");
					itemListView3.$el.css("display","none");
					itemListView4.$el.css("display","none");
				}else if(dataLevel==2){
					itemListView3.categoryId=itemview.model.get("id");
					itemListView3.tradeType=itemview.model.get("tradeType");
					itemListView3.isLeaf=itemview.model.get("isLeaf");
					itemListView3.loadItems();
					itemListView3.$el.css("display","block");
					itemListView4.$el.css("display","none");
				}else if(dataLevel==3){
					itemListView4.categoryId=itemview.model.get("id");
					itemListView4.tradeType=itemview.model.get("tradeType");
					itemListView4.isLeaf=itemview.model.get("isLeaf");
					itemListView4.loadItems();
					itemListView4.$el.css("display","block");
				}else if(dataLevel==4){
					
				}
			},
			
		});
		
		var Step3ModuleView=Backbone.View.extend({
			el:$("#step3"),
			properties:{},
			offer:{"productFeatures":{"3939":1}},
			events : {
				"click .add-diy-prop.icon-add" : "addDiyProp",
				"click span.speca-props-ctrl.fd-clr" : "addSpecAttrProp",
				"change input[name='subject']":"changeTitle"
			},
		    initialize: function() {
				
			},
			changeTitle:function(){
				var subject=$("input[name='subject']").first().val();
				$(".current-num").first().text(subject.length);
			},
			addDiyProp:function(e){//添加产品属性
				var _this=this;
				var str='<div class="form-group"><div class="pro-t col-sm-2"><input type="text" placeholder="属性" data-proname="属性" data-diyprop="true" class="txt diy-prop-name gray" value="" name="" maxlength="12"></div><span class="col-sm-1" style="width:5px" >:</span><div class="pro-d col-sm-2" style="width:120px"><input type="text" placeholder="属性值" data-proname="属性值" data-diyprop="true" value="" name="" maxlength="50" class="txt diy-prop-value gray"></div><div class="col-sm-1" style="" ><a target="_self" title="删除" class="del-diy-prop" href="#">删除</a></div></div>';
				$("#diy-prop-line").append(str)
				var lastObj=$("#diy-prop-line .del-diy-prop").last();
				lastObj.on("click",function(e){
					var formObj=lastObj.parent().parent();
					formObj.remove();
					lastObj.unbind("click");
					_this.stopEvent(e);
				});
				 return this.stopEvent(e)
			},
			addSpecAttrProp:function(e,val){
				var _this=this;
				if(!val)val="";
				var str='<span class="span-item diy-prop" style="float:none"><input type="text"  maxlength="24" class="speca-text" value="'+val+'" > <a class="del-lnk" href="#">删除</a></span>';
				$(e.currentTarget).before(str);
				var lastObj=$(e.currentTarget).parent().find(".del-lnk").last();
				lastObj.on("click",function(c){
					var formObj=lastObj.parent();
					formObj.remove();
					lastObj.unbind("click");
					_this.stopEvent(c);
				});
				return this.stopEvent(e);
			},

			stopEvent:function(e){
				//阻止默认浏览器动作(W3C) 
			    if ( e && e.preventDefault ) 
			        e.preventDefault(); 
			    //IE中阻止函数器默认动作的方式 
			    else
			        window.event.returnValue = false; 
			    return false;
			},
			parseData:function(pros){
				this.properties=pros;
				var productObj=this.$el.find(".product-props");
				var productSpecaObj=this.$el.find(".product-speca-props");
				var yanseObj=this.$el.find(".matching-pic-list");
				var zhutuObj=this.$el.find(".imgs");
				productObj.html("");
				productSpecaObj.html("");
				zhutuObj.html("");
				yanseObj.html("");
				this.offer={"productFeatures":{"3939":1}},
				this.eachItems(productObj,pros.productFeatureList);
			},
			parseItemData:function(productObj,aliItemObj,pros){

				aliItemObj.nextAll().remove();
				this.eachItems(aliItemObj,pros,1);
			},
			eachItems:function(productObj,productFeatureList,wrapper){
				var _this=this;
				var _productObj=productObj;
				_.each(productFeatureList,function(item){
					if(item.isSpecAttr){
						productObj=_this.$el.find(".product-speca-props");
					}else{
						productObj=_productObj;
					}
					if(item.fieldType=='enum'&&item.inputType==1){//下拉多选
						var str=_this.genSelectItem(item,wrapper);
						if(wrapper!=1){
							productObj.append(str);
						}else{
							productObj.after(str);
						}
						var selectObj=$(productObj).find("select").last();
						var aliItem=selectObj.parents(".ali_item").last();
						selectObj.on("change",function(a,b){
							var text=$(selectObj).find("option:selected").text()
							if(item.childrenFids.length>0){
								_this.reqByPath(productObj,aliItem,item.fid,$(this).val());
							}else if(item.childrenFids.length==0&&text=="其他"){
								var otherstr='<div class="col-sm-9 ali-other-box" style="width:195px"><input type="text" class="form-control" data-id="'+item.fid+'"  />';
								aliItem.append(otherstr);
							}else{
								aliItem.find(".ali-other-box").remove();
							}
						});

					}else if(item.fieldType=='enum'&&item.inputType==2){//多选
						productObj.append(_this.genCheckItem(item));
					}else if(item.fieldType=='string'&&item.inputType==0){
						productObj.append(_this.genStrInput(item));
					}
				});
				var addObj='<div id="diy-pro"><div id="diy-prop-line"></div><div class="diy-prop-ctrl pro-item fd-clr" id="diy-prop-ctrl"><div class="pro-t"><a target="_self" title="添加产品属性" href="#" class="add-diy-prop icon-add"  style="line-height:25px">添加产品属性</a></div><span style="color: #989898;">如果您觉得我们提供的产品属性无法满足您的需要，您可以手动添加产品属性</span></div></div>';
				if(wrapper!=1)productObj.append(addObj);
			},
			genCheckItem:function(item){//checkbox多选
				var fvstr="";
				_.each(item.featureIdValues,function(fv){
					fvstr+='<span  style=" padding-right: 15px;"><input name="form-field-checkbox" type="checkbox" class="ace" data-id='+item.fid+'><span class="lbl" >'+fv.value+'</span></span>';
				});
				if(item.isSpecAttr){
					fvstr=fvstr+'<span class="speca-props-ctrl fd-clr" style="float:none" data-id='+item.fid+'><a  class="ace add-defined-values icon-add" href="#" style="line-height:25px">增加自定义项</a></span>';
				}
				
				var htmltemp='<div class="ali_item" style="float:left;padding: 4px 6px;" data-type="checkbox" data-isSpecPicAttr='+item.isSpecPicAttr+' data-id='+item.fid+'><span class="col-sm-2 control-label no-padding-right" style="width:120px"><label class="'+(item.isNeeded=="Y"?"icon-must":"")+'"  for="form-field-1"> '+item.name+':</label></span>'+
				'<div class="col-sm-9 pro-d">'+fvstr+'</div></div>';
				htmltemp='<div class="form-group">'+htmltemp+'</div>';
				return htmltemp;
			},
			genSelectItem:function(item,wrapper){//下拉多选
			var fvstr='<option></option>';
			_.each(item.featureIdValues,function(fv){
				fvstr+='<option value="'+fv.vid+'">'+fv.value+'</option>';
			});
			var htmltemp='<div class="ali_item" style="float:left;padding: 4px 6px;"><span class="col-sm-2 control-label no-padding-right" style="width:120px"><label class="'+(item.isNeeded=="Y"?"icon-must":"")+'"  for="form-field-1"> '+item.name+':</label></span>'+
			'<div class="col-sm-9" style="width:120px"><select class="form-control" data-id="'+item.fid+'" >'+fvstr+'</select></div>'+
			'</div>';
			if(wrapper!=1){
				htmltemp='<div class="form-group">'+htmltemp+'</div>'
			}
			return htmltemp;
			},
			genStrInput:function(item){//input框
				var htmltemp='<div class="form-group"><div class="ali_item" style="float:left;padding: 4px 6px;"><span class="col-sm-2 control-label no-padding-right" style="width:120px"><label class="icon-must"  for="form-field-1"> '+item.name+':</label></span>'+
				'<div class="col-sm-9" style="width:195px"><input type="text" class="form-control" data-id="'+item.fid+'"  /></div></div></div>';
				return htmltemp;
			},

			reqByPath:function(productObj,aliItemObj,fid,vid){
				var _this=this;
				$.ajax({
					type:"GET",
					contentType:"application/json",
					data:{"catsId":AliCate.categoryModuleView.catsId,"path":fid+":"+vid},
					url:URL_ROOT+"/top/productcopy/propertiesByPath",
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						_this.parseItemData(productObj,aliItemObj,JSON.parse(data).data);
				}
				});
				
				
			},
			reqTaobaoData:function(){//请求淘宝数据
				var _this=this;
				var url=$("#taobaourl").val();
				$.ajax({
					type:"GET",
					contentType:"application/json",
					data:{"url":url},
					url:URL_ROOT+"/top/productcopy/parseTaobaoUrlByUrl",
					dataType:"json",
					success:function(data,textStatus,jqXHR){
						_this.parseTaobaoData(data);
				}
				});
			},
			parseTaobaoData:function(data){
				var _this=this;
				//加载主图
				var zhututemp='<li class="pic-list full"><div class="img-cont"><span></span><img class="cont-img" src="@imageUrl@" style="width: 110px; height: 110px; margin-left: -55px; margin-top: -55px;"></div>'+
                    '<div class="ctrl" style="display: block;"><a title="删除" href="#remove">删除</a></div> </li>';
				var imgsObj=$("ol.imgs");
				_this.offer.imageUriList=[];
				_.each(data.zhutu,function(item,i){
					_this.offer.imageUriList.push(item.url);
					imgsObj.append(zhututemp.replace(/@imageUrl@/,item.url));
					var delObj=imgsObj.find("a").last();
					delObj.on("click",function(e){
						delObj.unbind("click");
						$(e.currentTarget).parent().parent().remove();
					});
				});
				
				//设定价格
				this.offer.price=data.price;
				
				//加载内容
				ue.setContent(data.desc);
				
				//加载颜色
				var specaList=_this.$el.find(".product-speca-props .ali_item");
				_.each(specaList,function(speca){
					
					if($(speca).attr("data-isSpecPicAttr")=="true"){
						$("#yansediv").removeClass("fd-hide");
						var yanseBlankTemp='<li title="@name@" class="pic-list empty"><div class="img-con"><span class="no-pic"><img class="matching-pic"></span><div class="ctrl"><a class="icon-del-pic" href="#remove" title="删除">删除</a></div></div><div class="color-name">@name@</div></li>';
						var yanseTemp='<li title="@name@" class="pic-list"><div class="img-con"><span class=""><img height="64" class="matching-pic" src="@imageUrl@" style="display: inline;"></span><div class="ctrl"><a class="icon-del-pic" href="#remove" title="删除">删除</a></div></div><div class="color-name">@name@</div></li>';
						_.each(data.skuList,function(item){
							if(item.isSpecPicAttr){
								_this.offer.skuPics={};
								_this.offer.skuPics[$(speca).attr("data-id")]=[];
								_.each(item.childs,function(child){
									var addImagestr="";
									if(child.imageUrl){
										var oo={};
										oo[child.value]=child.imageUrl;
										_this.offer.skuPics[$(speca).attr("data-id")].push(oo);
										var isExit=false;
										var pros=$(speca).find("span.lbl");
										$.each(pros,function(pro){
											if($(pro).text()==child.value){
												isExit=true;
												return false;
											}
										});
										if(!isExit){
											$(".speca-props-ctrl.fd-clr[data-id='"+$(speca).attr("data-id")+"']").trigger("click",[child.value]);
											
										}
										addImagestr=yanseTemp.replace(/@name@/g,child.value).replace(/@imageUrl@/,child.imageUrl);
									}else{
										addImagestr=yanseBlankTemp.replace(/@name@/g,child.value);
									}
									$("#yansediv").find(".matching-pic-list").append(addImagestr);
								});
							}
							
						});//加载颜色结束
						
					}
				})
				
				
				//加载标题
				$("input[name='subject']").first().val(data.subject);
				$("input[name='subject']").first().change()
				
			},
			saveData:function(){
				//产品属性
				var subject=$("input[name='subject']").first().val();
				if(subject.length>30){
					alert("标题不能超过30长度!");
					return false;
				}
				var _this=this;
				var items=$("div.product-props .ali_item");
				var isVal=true;
				$.each(items,function(i,item){//验证必填项;
					
					if($(item).find("label.icon-must").length>0){
					 	//checkbox
						if($(item).attr("data-type")=="checkbox"){
					 		var cbarr=$(item).find("input[type='checkbox']:checked")
					 		if(cbarr.length>0){
					 			return true;
					 		}else{
					 			alert($(item).find("label").text()+"不能为空!");
					 			isVal=false;
					 			return false;
					 		}
					 	}
						
						var nodeType=$($(item).find('[data-id]').first())[0].nodeName;
						var fid=$($(item).find('[data-id]').first()).attr("data-id");
						if(nodeType=="SELECT"){
							var text=$($(item).find('[data-id] option:selected').first()).text();
							if(text==""){
								alert($(item).find("label.icon-must").text()+"不能为空!");
								isVal=false;
								return false;
							}
						}else if(nodeType=="INPUT"){
							var value=$($(item).find('[data-id]').first()).val();
							if(value==""){
								alert($(item).find("label.icon-must").text()+"不能为空!");
								isVal=false;
								return false;
							}
						}
					}
				});
				
				var specaitems=$("div.product-speca-props .ali_item");
				$.each(specaitems,function(i,item){//验证必填项;
					if($(item).find("label.icon-must").length>0){
						
						var cbarr=$(item).find("input[type='checkbox']:checked")
				 		if(cbarr.length>0){
				 			return true;
				 		}else{
				 			var inputcbarr=$(item).find("input[type='text']");
				 			if(inputcbarr.length==0){
				 				alert($(item).find("label").text()+"不能必须!");
					 			isVal=false;
					 			return false;
				 			}
				 			for(var i=0;i<inputcbarr.length;i++){
				 				var obj=inputcbarr[i];
				 				if(obj.value==""){
				 					alert($(item).find("label").text()+"自定义项有个为空!");
						 			isVal=false;
						 			return false;
				 				}
				 			}
				 			
				 		}
						
					}
				});
				if(!isVal)return [false,{}];
				$.each(items,function(i,item){
					
					//checkbox
					if($(item).attr("data-type")=="checkbox"){
				 		var cbarr=$(item).find("input[type='checkbox']:checked")
				 		if(cbarr.length>0){;
				 		var str="";
				 			_.each(cbarr,function(rr){
				 				str+=$(rr).parent().find("span").text()+"|";
				 			});
				 			str=str.substr(0,str.length-1);
				 			_this.offer.productFeatures[$(item).attr("data-id")]=str;
				 			return true;
				 		}
				 	}
					
					var nodeType=$($(item).find('[data-id]').first())[0].nodeName;
					var fid=$($(item).find('[data-id]').first()).attr("data-id");
					if(nodeType=="SELECT"){
						var value=$($(item).find('[data-id] option:selected').first()).text();
						
						if(value!="")_this.offer.productFeatures[fid]=value
					}else if(nodeType=="INPUT"){
						var value=$($(item).find('[data-id]').first()).val();
						if(value!="")_this.offer.productFeatures[fid]=value;
					}
					
				});
				
				var specaArray=[];
				$.each(specaitems,function(i,item){//设置产品规格的值;
					specaArray[i]=[];
					var fid=$(item).attr("data-id");
						var cbarr=$(item).find("input[type='checkbox']:checked");
						var str="";
				 		if(cbarr.length>0){
				 			
				 			_.each(cbarr,function(rr){
				 				var cbarrObj=$(rr).parent().find("span").text();
				 				
				 				str+=cbarrObj+"|";
				 				var oo={};
				 				oo[fid]=""+cbarrObj;
				 				specaArray[i].push(oo);
				 			});
				 		}
				 		var inputcbarr=$(item).find("input[type='text']");
			 			_.each(inputcbarr,function(rr){
			 				str+=$(rr).val()+"|";
			 				var oo={};
			 				oo[fid]=$(rr).val();
			 				specaArray[i].push(oo);
			 			});
			 			str=str.substr(0,str.length-1);
			 			_this.offer.productFeatures[$(item).attr("data-id")]=str;
				});
				_this.offer.skuList=[];
				var price=""+_this.offer.price;
				if(specaArray.length==1){
					for(var i=0;i<specaArray[0].length;i++){
						
						var skuObj={"retailPrice":price,"price":price,"amountOnSale":100,"specAttributes":specaArray[0][i]};
						_this.offer.skuList.push(skuObj);
					}
				}else if(specaArray.length==2){
					for(var i=0;i<specaArray[0].length;i++){
						for(var j=0;j<specaArray[1].length;j++){
							var skuObj={"retailPrice":price,"price":price,"amountOnSale":100,"specAttributes":_.extend(specaArray[0][i],specaArray[1][j])};
							_this.offer.skuList.push(skuObj);
						}
					}
				}
				
				
				this.offer.supportOnlineTrade=true;
				this.offer.skuTradeSupport=true;
				this.offer.bizType=this.properties.tradeType;
				this.offer.categoryID=this.properties.categoryId;
				this.offer.offerWeight="1";
				//标题
				this.offer.subject=subject;
				//详情
				this.offer.offerDetail=ue.getContent();
				
				var url=$("#taobaourl").val();
				//var picStatus=$("#picStatus:checked").val;
				return [isVal,JSON.stringify({"data":this.offer,"url":url,"picStatus":false})];
			},
			autosave:function(){
				var url=$("#taobaourl").val();
				var categoryId=this.properties.categoryId;
				return {"url":url,"catId":categoryId};
			}
			
		});
		
		var itemListView1 = new ItemListView1();
		var itemListView2 = new ItemListView2();
		var itemListView3 = new ItemListView3();
		var itemListView4 = new ItemListView4();
		var step3ModuleView=new Step3ModuleView();
		var categoryModuleView=new CategoryModuleView();
		AliCate.categoryModuleView=categoryModuleView;
		AliCate.step3ModuleView=step3ModuleView;
		Backbone.history.start();
}
AliCate={};
