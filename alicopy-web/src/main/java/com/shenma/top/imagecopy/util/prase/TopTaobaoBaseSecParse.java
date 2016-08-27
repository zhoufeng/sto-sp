package com.shenma.top.imagecopy.util.prase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.exception.BusinessException;

public class TopTaobaoBaseSecParse extends TopTaobaoImageParse {

	protected static Logger logger = Logger.getLogger("TopTaobaoBaseSecParse");
	public String gendescTaobao(Document doc) throws IOException, InterruptedException{
		Pattern pattern = Pattern.compile("//dsc.taobaocdn.com.*?(?=\' :)");
		
		Matcher matcher = pattern.matcher(doc.html());

		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		descriptionurl="http:"+descriptionurl;
		Document scriptdoc = JsonpUtil.getAliDefaultConnet(descriptionurl);
		String description=scriptdoc.body().html();
		description=description.replaceAll("\\\\ ", " ");
		description=description.substring(11, description.length()-2);
		return description;
	}
	
	
	public String getPrice(Document doc){
		Element ele=doc.select("input[name=current_price]").first();
		String price="";
		if(ele!=null){
			price=ele.val();
		}

		return price;
	}
	
	public String getSubject(Document doc){
		Element element=doc.select("#J_Title .tb-main-title").first();
		String subject=element.attr("data-title");
		return subject;
	}
	public Map<String,Object> getSkuInfo(Document doc) throws JsonParseException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,Object> skuInfo=getSkuConfig(doc.html());
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Elements eles=doc.select("#J_isku .J_Prop.tb-prop");
		Map<String,String> distinct=new HashMap<String,String>();
		Map<String,String> temp=new HashMap<String,String>();
		for(Element ele:eles){
			Map<String,Object> ret=new HashMap<String,Object>();
			String name=ele.select("ul[data-property]").first().attr("data-property").trim();
			ret.put("name", name);
			List<Map<String,Object>> prolist=new ArrayList<Map<String,Object>>();
			ret.put("childs", prolist);
			for(Element element:ele.select("li")){
				Map<String,Object> mapele=new HashMap<String,Object>();
				String datavalue=element.attr("data-value");
				Element aele=element.select("a").first();
				String atext=aele.text().trim();
				if(distinct.containsKey(atext)){
					atext+="_1";
				}else{
					distinct.put(atext, atext);
				}
				temp.put(datavalue, atext);
				mapele.put("value", atext);
				if("颜色分类".equals(atext))ret.put("isSpecPicAttr", true);
				if(aele.hasAttr("style")){
					ret.put("isSpecPicAttr", true);
					String stylestr=aele.attr("style");
					Pattern pattern = Pattern.compile("//.*?\\.jpg");
					Matcher matcher = pattern.matcher(stylestr);
					String imageUrl="";
					if(matcher.find()){
						imageUrl=matcher.group(0);
					}
					if(imageUrl.startsWith("//"))imageUrl="http:"+imageUrl;
					//imageUrl=lastChraraset(imageUrl.trim());
					mapele.put("imageUrl", imageUrl);
				}
				prolist.add(mapele);
			}
			list.add(ret);
		}
		result.put("skuList", list);
		Map<String,Object> skuMap=(Map<String, Object>) skuInfo.get("skuMap");
		Map<String,Object> skuMaps=new HashMap<String,Object>();
		if(skuMap!=null){
			for(String key:skuMap.keySet()){
				String[] skukeys=key.substring(1, key.length()-1).split(";"); //格式为 ;21433:129813;1627207:28332;
				String[] skuValues=new String[skukeys.length];//临时变量
				for(int i=0;i<skukeys.length;i++){
					String skuName=temp.get(skukeys[i]);
					skuValues[i]=skuName;
				}
				String keyValue=""; //格式为米白色(现货)>L
				for(String skuValue:skuValues){
					keyValue+=skuValue+">";
				}
				keyValue=keyValue.substring(0, keyValue.length()-1);
				
				Map<String,Object> skuMapValue=(Map<String, Object>) skuMap.get(key);
				String price=BeanUtils.getProperty(skuMapValue, "price");
				//String skuId=BeanUtils.getProperty(skuMapValue, "skuId");
				Map<String,Object> value=new HashMap<String,Object>();
				value.put("price", price);
				value.put("retailPrice", price);
				//value.put("cargoNumber", skuId);
				value.put("amountOnSale", 9999);
				
				//设置skuMaps的值
				skuMaps.put(keyValue, value);
			}
			result.put("skuMaps", skuMaps);
		}
		return result;
		
	}
	
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSkuConfig(String html)
			throws JsonParseException, JsonMappingException, IOException {
		Pattern pattern = Pattern.compile("(?<=valItemInfo      :)[\\s\\S]*?\\)\\;");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str = str.replaceAll("\\n", "");
		
		if(str=="")throw new BusinessException("401","请检查您的地址是否有误!");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			str=str.substring(0, str.length()-3);
			str=str.replace("propertyMemoMap", "\"propertyMemoMap\"");
			str=str.replace("defSelected", "\"defSelected\"");
			str=str.replace("skuMap", "\"skuMap\"");
			ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}

		return ret;
	}
	
	public static String asciiToString(String value)
	{
		StringBuffer sbu = new StringBuffer();
		String[] chars = value.split(",");
		for (int i = 0; i < chars.length; i++) {
			sbu.append((char) Integer.parseInt(chars[i]));
		}
		return sbu.toString();
	}
	
	/**
	 * 处理图片后缀
	 */
	private String lastChraraset(String imageUrl){
		Pattern pattern = Pattern.compile("http.*(?=_.*?\\.jpg)");
		Matcher matcher = pattern.matcher(imageUrl);
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		return descriptionurl;
	}
	public static Map<String, Object> obj2map(ScriptEngine engine,
			Object nativeObject) throws ScriptException, NoSuchMethodException {
		Map<String, Object> map = new HashMap<String, Object>();
		engine.put("map", map);
		engine.put("obj", nativeObject);
		String script = " 	function dosomething(){											"
				+ "							for (i in obj){											"
				+ "								map.put(i,obj[i]);									"
				+ "							}														" + "					} 																";
		engine.eval(script);
		Invocable inv = (Invocable) engine;
		inv.invokeFunction("dosomething");
		return map;
	}

	public static void main(String[] args) {

		 
	}
}
