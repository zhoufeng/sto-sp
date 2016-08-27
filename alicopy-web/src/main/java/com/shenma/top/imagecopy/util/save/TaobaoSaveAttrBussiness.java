package com.shenma.top.imagecopy.util.save;

import java.util.List;

import org.apache.commons.lang3.RandomUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.taobao.api.domain.ItemProp;
import com.taobao.api.domain.PropValue;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.response.ItempropsGetResponse;

public class TaobaoSaveAttrBussiness implements SaveBussiness{
	private ItemAddRequest topitem;
	private ItempropsGetResponse res;
	private Document document;
	public TaobaoSaveAttrBussiness(SaveBean bean){
		this.topitem=bean.getTopitem();
		this.res=bean.getRes();
		this.document=bean.getDocument();
	}
	@Override
	public void option() {
		StringBuffer props=new StringBuffer(topitem.getProps()==null?"":topitem.getProps());
		StringBuffer inputPids=new StringBuffer(topitem.getInputPids()==null?"":topitem.getInputPids());
		StringBuffer inputStr=new StringBuffer(topitem.getInputStr()==null?"":topitem.getInputStr());
		//获得cid
		
		//获得lli
		Elements elements=document.select(".attributes-list li");
		for(Element element:elements){
			String[] strs=element.text().split(":");
			String key=strs[0].trim();
			String value=strs[1].trim();
			List<ItemProp> list=res.getItemProps();
			inner:for(ItemProp itemProp:list){
				if(itemProp.getName().equals(key)){
					if(itemProp.getPropValues()==null){//自定义填项
						inputPids.append(",").append(itemProp.getPid());
						inputStr.append(",").append(value);
						break inner;
					}
					if(itemProp.getMulti()){//多项选择
						String[] muliKeys=value.split(" ");
						for(PropValue propValue:itemProp.getPropValues()){
							for(String val:muliKeys){
								if(propValue.getName().equals(val)){
									props.append(";").append(itemProp.getPid()).append(":").append(propValue.getVid());
									continue;
								}
							}
						}
					}else{//单选
						for(PropValue propValue:itemProp.getPropValues()){
							if(propValue.getName().equals(value)){
								props.append(";").append(itemProp.getPid()).append(":").append(propValue.getVid());
								break inner;
							}
						}
					}
					
				}
			}
		}
		topitem.setProps(props.toString());
		topitem.setInputPids(inputPids.toString());
		topitem.setInputStr(inputStr.toString());
		
	}
}
