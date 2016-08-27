package com.shenma.top.imagecopy.util.prase;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.UrlParseUtil;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;

/**
 * 分析天猫详情组成部分
 * @author Administrator
 *
 */
public class TopTmailImageParse extends BaseImageParse implements UrlParse {

	protected static Logger logger = Logger.getLogger("TopImageParse");
	private String title="";//宝贝标题
	@Override
	public ImageBean<ImageVoBean> parseImages(String url) {
		ImageBean<ImageVoBean> bean=new ImageBean<ImageVoBean>();
		try {
			Document doc = JsonpUtil.getAliDefaultConnet(url);
			title=UrlParseUtil.parseTitle(doc);
			//处理主图
			List<ImageVoBean> zhutuList=genZhutuImage(doc);
			
			//处理颜色分类
			List<ImageVoBean> yanseList=genYanseImage(doc);
			
			//处理详情
			List<ImageVoBean> xiangqinList=genXiangqinImage(doc);
			removeDuplicateWithOrder(xiangqinList);
			
			bean.getImages().addAll(zhutuList);
			bean.getImages().addAll(xiangqinList);
			bean.getImages().addAll(yanseList);
		} catch (Exception e) {
			logger.error("解析url出错",e);
			bean.setErrorCode(1);
			bean.setErrorMsg("服务器访问人数多,请稍后访问");
		}
		return bean;
	}
	
	/**
	 * 功能描述,处理颜色分类图片
	 * @return
	 */
	public List<ImageVoBean> genYanseImage(Document doc){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Elements images=doc.select("li[data-value]");
		for(Element ele:images){
			Element aEle=ele.select("a[style]").first();
			if(aEle==null)continue; //颜色没有图片的
			String style=aEle.attr("style");
			
			Pattern pattern = Pattern.compile("//.*\\.jpg");
			Matcher matcher = pattern.matcher(style);
			String descriptionurl=null;
			if(matcher.find()){
				descriptionurl=matcher.group(0);
			}
			if(descriptionurl.startsWith("//"))descriptionurl="http:"+descriptionurl;
			String imageUrl=descriptionurl;
			imageUrl=imageUrl.substring(0, imageUrl.lastIndexOf("_"));
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(imageUrl);
			vb.setType(2);
			vb.setName("颜色分类_"+title);
			list.add(vb);
		}
		return list;
	}
	
	
	/**
	 * 功能描述,处理主图图片
	 * @return
	 */
	public List<ImageVoBean> genZhutuImage(Document doc){
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		Element element=doc.getElementById("J_UlThumb");
		Elements images=element.select("img[src]");
		for(Element ele:images){
			String url=ele.attr("src");
			if(url.startsWith("//"))url="http:"+url;
			String imageUrl=url.substring(0, url.lastIndexOf("_"));
			ImageVoBean vb=new ImageVoBean();
			vb.setUrl(imageUrl);
			vb.setType(1);
			vb.setName("主图_"+title);
			list.add(vb);
		}
		return list;
	}
	
	/**
	 * 功能描述,处理详情图片
	 * @return
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unused")
	public List<ImageVoBean> genXiangqinImage(Document doc) throws IOException, InterruptedException{
		Pattern pattern = Pattern.compile("(?<=descUrl\"\\:\").*?(?=\")");
		Matcher matcher = pattern.matcher(doc.html());
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		if(descriptionurl.startsWith("//"))descriptionurl="http:"+descriptionurl;
		Document scriptdoc = JsonpUtil.getAliDefaultConnet(descriptionurl); 
		List<ImageVoBean> list=new ArrayList<ImageVoBean>();
		String description=scriptdoc.body().html();
		description=description.substring(10, description.length()-2);
		Document descriptiondoc = Jsoup.parse(description);
		Elements images=descriptiondoc.select("img[src]");
		List<String> tempList=new ArrayList<String>();
		for(Element ele:images){
			String url=ele.attr("src");
			String imageUrl=url;
			ImageVoBean vb=new ImageVoBean();
			if(url.endsWith("_.webp")){
				imageUrl=imageUrl.substring(0, imageUrl.length()-6);
			}
			vb.setUrl(imageUrl);
			vb.setType(3);
			vb.setName("详情_"+title);
			list.add(vb);
		}
		
		return list;
	}
	
	
	/**
	 * 处理图片后缀
	 */
	private String lastChraraset(String imageUrl){
		Pattern pattern = Pattern.compile("http.*?\\.jpg");
		Matcher matcher = pattern.matcher(imageUrl);
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		return descriptionurl;
	}
	@SuppressWarnings("unchecked")
	public String getPrice(Document doc) throws JsonParseException, JsonMappingException, IOException{
		Pattern pattern = Pattern.compile("(?<=TShop.Setup\\()[\\s\\S]*?(?=\\)\\;)");
		Matcher matcher = pattern.matcher(doc.html());
		
		String obj=null;
		if(matcher.find()){
			obj=matcher.group(0);
		}
		Map<String, Object> map = JacksonJsonMapper.getInstance().readValue(obj, HashMap.class);
		Map<String, Object> detail=(Map<String, Object>) map.get("detail");
		String price=detail.get("defaultItemPrice").toString();
		if(price.indexOf("-")>-1){
			price=price.split("-")[1].trim();
		}
		return price;
	}
	@SuppressWarnings("unchecked")
	public String getSubject(Document doc) throws JsonParseException, JsonMappingException, IOException{
		Pattern pattern = Pattern.compile("(?<=TShop.Setup\\()[\\s\\S]*?(?=\\)\\;)");
		Matcher matcher = pattern.matcher(doc.html());
		
		String obj=null;
		if(matcher.find()){
			obj=matcher.group(0);
		}
		Map<String, Object> map = JacksonJsonMapper.getInstance().readValue(obj, HashMap.class);
		Map<String, Object> detail=(Map<String, Object>) map.get("itemDO");
		String subject=detail.get("title").toString();
		return subject;
	}
	public Map<String,Object> getSkuInfo(Document doc) throws JsonParseException, JsonMappingException, IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object> result=new HashMap<String,Object>();
		Map<String,String> transfMap=new HashMap<String,String>(); //键值映射 销售名:销售值
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		Elements eles=doc.select("#J_DetailMeta .tb-prop.tm-sale-prop");
		Map<String,String> distinct=new HashMap<String,String>();
		for(Element ele:eles){
			Map<String,Object> ret=new HashMap<String,Object>();
			String name=ele.select("ul[data-property]").first().attr("data-property").trim();
			ret.put("name", name);
			List<Map<String,Object>> prolist=new ArrayList<Map<String,Object>>();
			ret.put("childs", prolist);
			for(Element element:ele.select("li")){
				Map<String,Object> mapele=new HashMap<String,Object>();
				Element aele=element.select("a").first();
				String datavalue=element.attr("data-value");
				String atext=aele.text().trim();
				if(distinct.containsKey(atext)){
					atext+="_1";
				}else{
					distinct.put(atext, atext);
				}
				transfMap.put(datavalue, atext);
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
		
		Map<String, Object> skuInfo = getSkuConfig(doc.html());
		//设置skuMaps
		Map<String,Object> skuMaps=new HashMap<String,Object>();
		Map<String, Object> valItemInfo = (Map<String, Object>) skuInfo.get("valItemInfo");
		if(valItemInfo!=null){
			Map<String,Object> skuMap=(Map<String, Object>) valItemInfo.get("skuMap");
			for(String key:skuMap.keySet()){
				String[] skukeys=key.substring(1, key.length()-1).split(";"); //格式为 ;21433:129813;1627207:28332;
				String[] skuValues=new String[skukeys.length];//临时变量
				for(int i=0;i<skukeys.length;i++){
					String skuName=transfMap.get(skukeys[i]);
					skuValues[i]=skuName;
				}
				String keyValue=""; //格式为米白色(现货)>L
				for(String skuValue:skuValues){
					keyValue+=skuValue+">";
				}
				keyValue=keyValue.substring(0, keyValue.length()-1);
				
				Map<String,Object> skuMapValue=(Map<String, Object>) skuMap.get(key);
				String price=BeanUtils.getProperty(skuMapValue, "price");
				String skuId=BeanUtils.getProperty(skuMapValue, "skuId");
				String stock=BeanUtils.getProperty(skuMapValue, "stock");
				Map<String,Object> value=new HashMap<String,Object>();
				value.put("price", price);
				value.put("retailPrice", price);
				value.put("cargoNumber", skuId);
				value.put("amountOnSale", stock);
				
				//设置skuMaps的值
				skuMaps.put(keyValue, value);
			}
		}
		
		result.put("skuMaps", skuMaps);
		
		return result;
		
	}
	
	
	/**
	 * 
	 * 解析sku属性
	 * @param html
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSkuConfig(String html)
			throws JsonParseException, JsonMappingException, IOException {
		Pattern pattern = Pattern.compile("(?<=TShop.Setup\\()[\\s\\S]*?\\)\\;");
		//Pattern pattern = Pattern.compile("(?<=valItemInfo      :)[\\s\\S]*?\\)\\;");
		//Pattern pattern = Pattern.compile("(?<=Hub.config.set\\('sku',)[\\s\\S]*?\\)\\;");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str = str.replaceAll("\\n", "");
		//str = str.replaceAll("'", "\"");
		str=str.substring(0, str.length()-2);
		if(str=="")throw new BusinessException("401","请检查您的地址是否有误!");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}

		return ret;
	}
	
	public static void main(String[] args) throws IOException {
		TopTaobaoImageParse t=new TopTaobaoImageParse();
		Document doc = Jsoup.connect("http://detail.tmall.com/item.htm?spm=a1z10.4.w5003-8373969556.2.9tsk7s&id=39948030715&scene=taobao_shop").get(); 
		Pattern pattern = Pattern.compile("(?<=descUrl\"\\:\").*?(?=\")");
		Matcher matcher = pattern.matcher(doc.html());
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		System.out.println(descriptionurl);
		//System.out.println(t.lastChraraset("http://img02.taobaocdn.com/bao/uploaded/i2/806150520/TB2dHw5XVXXXXXeXpXXXXXXXXXX_!!806150520._30x30.jpg"));
	}

}
