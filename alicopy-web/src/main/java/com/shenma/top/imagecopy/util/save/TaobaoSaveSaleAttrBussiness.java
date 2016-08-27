package com.shenma.top.imagecopy.util.save;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.response.ItempropsGetResponse;

public class TaobaoSaveSaleAttrBussiness implements SaveBussiness{
	private ItemAddRequest topitem;
	private ItempropsGetResponse res;
	private Document document;
	private SaveBean bean;
	public TaobaoSaveSaleAttrBussiness(SaveBean bean){
		this.topitem=bean.getTopitem();
		this.res=bean.getRes();
		this.document=bean.getDocument();
		this.bean=bean;
	}
	@Override
	public void option() {
		StringBuffer skuProperties=new StringBuffer(topitem.getSkuProperties()==null?"":topitem.getSkuProperties());
		List<ItemProp> saleList=new ArrayList<ItemProp>();
		List<ItemProp> list=res.getItemProps(); 
		for(ItemProp itemProp:list){
			if(itemProp.getIsSaleProp()){
				saleList.add(itemProp);
			}

		}
		if(saleList.size()==1){
			ItemProp saleItem=saleList.get(0);
			parseProps(topitem, document, saleItem,skuProperties,1);
		}else if(saleList.size()==2){
			ItemProp saleItem=saleList.get(0);
			parseProps(topitem, document, saleItem,skuProperties,2);
			ItemProp lastsaleItem=saleList.get(1);
			parseProps(topitem, document, lastsaleItem,skuProperties,2);
			
			Elements firstEles=document.select("ul[data-property="+saleItem.getName()+"] li");
			Elements lastEles=document.select("ul[data-property="+lastsaleItem.getName()+"] li");
				for(Element elementI:firstEles){
					String pidVidI=elementI.attr("data-value");
					for(Element elementJ:lastEles){
						String pidVidJ=elementJ.attr("data-value");
						skuProperties.append(",").append(pidVidI+";"+pidVidJ);
					}
				}
		}
		topitem.setSkuProperties(skuProperties.toString());
		
		//颜色属性
		Map<String,String> map=new HashMap<String,String>();
		Elements yanseEles=document.select("ul[data-property="+"颜色分类"+"] li");
		for(Element elementI:yanseEles){
			String pidVidJ=elementI.attr("data-value");
			String style=elementI.select("a").attr("style");
			if(StringUtils.isEmpty(style))continue;
			String url=style.substring(style.indexOf("(")+1,style.indexOf(")"));
			url=url.substring(0, url.lastIndexOf("_"));
			map.put(pidVidJ, url);
		}
		bean.setYanseMap(map);
	}


	private void parseProps(ItemAddRequest topitem, Document document,
			ItemProp saleItem,StringBuffer skuProperties,Integer type) {
		StringBuffer props=new StringBuffer(topitem.getProps()==null?"":topitem.getProps());
		StringBuffer sropertyAlias=new StringBuffer(topitem.getPropertyAlias()==null?"":topitem.getPropertyAlias());
		Elements elements=document.select("ul[data-property="+saleItem.getName()+"] li");
		for(int i=0;i<elements.size();i++){
			Element element=elements.get(i);
			String pidVid=element.attr("data-value");
			String propName=element.select("span").text();
			String[] strs=pidVid.split(":");
			for(PropValue propValue:saleItem.getPropValues()){
				if(strs[1].equals(propValue.getVid().toString())){
					props.append(";").append(pidVid);
					if(type==1)skuProperties.append(",").append(pidVid);
					if(!propValue.getName().equals(propName)){
						sropertyAlias.append(";").append(pidVid).append(":").append(propName);
					}
				}
			}
		}
		topitem.setProps(props.toString());
		topitem.setPropertyAlias(sropertyAlias.toString());
	}
	
}
