package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.service.WuliuService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.common.util.ImageUtil;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpCateUtil;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.shenma.top.imagecopy.util.exception.ErrorMessages;
import com.shenma.top.imagecopy.util.prase.TopTaobaoBaseParse;
import com.shenma.top.imagecopy.util.prase.TopTaobaoBaseSecParse;
import com.shenma.top.imagecopy.util.prase.TopTmailImageParse;
import com.taobao.api.ApiException;

@Service
public class TaobaoParseService {

	@Autowired
	private GoodsService goodsService;
	
	@Autowired
	private WuliuService wuliuService; // 物流
	
	@Autowired
	private AliConstant aliConstant;
	
	@Autowired
	private AlbumService albumService;// 相册
	public Map<String,Object> parseDataByUrl(String url) throws IOException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		Map<String,Object> ret=new HashMap<String,Object>();
		if(StringUtils.isEmpty(url)){
			throw new BusinessException("101","地址是空的");
		}
		String[] urls=url.split("\\?");
		url=urls[0];
		if(urls.length>1){
			String[] params=urls[1].split("&");
			for(String param:params){
				if(param.startsWith("id=")){
					url+="?"+param;
				}
			}
		}
		//Document doc=JsonpCateUtil.getAliDefaultConnet(url);
		Document doc=JsonpUtil.getAliDefaultConnet(url);
		url=url.trim();
		if(url.contains("taobao.com")){
			if(doc.html().indexOf("var g_config")>-1){
				//https://item.taobao.com/item.htm?spm=a219r.lm869.14.15.j0yO6r&id=527746136799&ns=1&abbucket=1#detail
				TopTaobaoBaseSecParse imageParse=new TopTaobaoBaseSecParse();
				//imageParse.setgConfig(doc);
				List<ImageVoBean> zhutuList=imageParse.genZhutuImage(doc);
				List<ImageVoBean> yanseList=imageParse.genYanseImage(doc);
				String desc=imageParse.gendescTaobao(doc);
				Map<String,Object> skuInfo=imageParse.getSkuInfo(doc);
				List<Map<String,Object>> skuList=(List<Map<String, Object>>) skuInfo.get("skuList");
				String price=imageParse.getPrice(doc);
				String subject=imageParse.getSubject(doc);
				Map<String,String> baseMap=taobaoBaseMap(doc);
				for(Map<String,Object> sku:skuList){
					String key=sku.get("name").toString();
					if(baseMap.containsKey(key))baseMap.remove(key);
				}
				ret.put("zhutu", zhutuList);
				ret.put("yanse", yanseList);
				ret.put("desc", desc);
				ret.put("skuList", skuList);
				ret.put("skuInfo", skuInfo);
				ret.put("price", price);
				ret.put("subject", subject);
				ret.put("attributesMap", baseMap);
			}else{
				TopTaobaoBaseParse imageParse=new TopTaobaoBaseParse();
				List<ImageVoBean> zhutuList=imageParse.genZhutuImage(doc);
				List<ImageVoBean> yanseList=imageParse.genYanseImage(doc);
				String desc=imageParse.gendescTaobao(doc);
				Map<String,Object> skuInfo=imageParse.getSkuInfo(doc);
				List<Map<String,Object>> skuList=(List<Map<String, Object>>) skuInfo.get("skuList");
				String price=imageParse.getPrice(doc);
				String subject=imageParse.getSubject(doc);
				Map<String,String> baseMap=taobaoBaseMap(doc);
				for(Map<String,Object> sku:skuList){
					String key=sku.get("name").toString();
					if(baseMap.containsKey(key))baseMap.remove(key);
				}
				ret.put("zhutu", zhutuList);
				ret.put("yanse", yanseList);
				ret.put("desc", desc);
				ret.put("skuList", skuList);
				ret.put("skuInfo", skuInfo);
				ret.put("price", price);
				ret.put("subject", subject);
				ret.put("attributesMap", baseMap);
			}
		}else if(url.contains("tmall.com")){
			TopTmailImageParse imageParse=new TopTmailImageParse();
			List<ImageVoBean> zhutuList=imageParse.genZhutuImage(doc);
			List<ImageVoBean> yanseList=imageParse.genYanseImage(doc);
			String desc=gendescTmail(doc);
			Map<String,Object> skuInfo=imageParse.getSkuInfo(doc);
			String price=imageParse.getPrice(doc);
			String subject=imageParse.getSubject(doc);
			Map<String,String> baseMap=tmailBaseMap(doc);
			List<Map<String,Object>> skuList=(List<Map<String, Object>>) skuInfo.get("skuList");
			for(Map<String,Object> sku:skuList){
				String key=sku.get("name").toString();
				if(baseMap.containsKey(key))baseMap.remove(key);
			}
			ret.put("zhutu", zhutuList);
			ret.put("yanse", yanseList);
			ret.put("desc", desc);
			ret.put("skuInfo", skuInfo);
			ret.put("skuList", skuList);
			ret.put("price", price);
			ret.put("subject", subject);
			ret.put("attributesMap", baseMap);
			//List<ImageVoBean> xiangqingList=imageParse.genXiangqinImage(doc);
		}
		
		return ret;
	}
	
	private Map<String,String> taobaoBaseMap(Document doc){
		Elements elements=doc.select("#attributes li");
		Map<String, String> attributesMap = new HashMap<String, String>(); // 基本属性Map
		if(elements==null)return attributesMap;
		for(Element ele:elements){
			String text=ele.text();
			String title=ele.attr("title").trim();
			String name="";
			if("".equals(title)){
				name=text.substring(0,text.length()-1);
			}else if(text.indexOf(title)>-1){
				name=text.substring(0,text.lastIndexOf(title)-1);
				if(name.endsWith(":"))name=name.substring(0,name.length()-1);
				title=title.replaceAll(" ", "");
			}else{
				String[] checkbox=title.split(" ");
				String str="";
				for(String obj:checkbox){
					str+=obj.trim()+"|";
				}
				title=str.substring(0,str.length()-1);
			}
			
			attributesMap.put(name, title);
		}
		return attributesMap;
	}
	private Map<String,String> tmailBaseMap(Document doc){
		Elements elements=doc.select("#J_AttrUL li");
		Map<String, String> attributesMap = new HashMap<String, String>(); // 基本属性Map
		if(elements==null)return attributesMap;
		for(Element ele:elements){
			String text=ele.text();
			String title=ele.attr("title").trim();
			String name="";
			if("".equals(title)){
				name=text.substring(0,text.length()-1);
			}else if(text.indexOf(title)>-1){
				name=text.substring(0,text.lastIndexOf(title)-1);
				title=title.replaceAll(" ", "");
			}else{
				String[] checkbox=title.split(" ");
				String str="";
				for(String obj:checkbox){
					str+=obj.trim()+"|";
				}
				title=str.substring(0,str.length()-1);
			}
			
			attributesMap.put(name, title);
		}
		return attributesMap;
	}
	
	public Map<String,String> saveToTaobao(Map<String,Object> params,boolean picStatus) throws JsonGenerationException, JsonMappingException, IOException, AliReqException, ApiException{
		Map<String,String> ret=new HashMap<String,String>();
		
		// 物流运费信息
		List<DeliveryTemplateDescn> wuliList = wuliuService
				.getAllDeliveryTemplateDescn();
		if (wuliList.size() > 0) {
			params.put("freightType", "T");
			params.put("freightTemplateId", wuliList.get(0).getTemplateId());
		}
		saveImages(params,picStatus);
		// 设置
		String offstr = JacksonJsonMapper.getInstance().writeValueAsString(params);
		Long id=goodsService.newOffer(offstr);
		ret.put("offerId", id.toString());
		return ret;
	}
	private void saveImages(Map<String,Object> params,boolean picStatus) throws AliReqException, ApiException{
		Album album=getAlbum();
		String subject=params.get("subject").toString();
		// 设置图片标题
		String image_prefix = null;
		if (subject.length() > 23) {
			image_prefix = subject.substring(0, 23);
		} else {
			image_prefix = subject;
		}
		//主图
		List<String> imageList = new ArrayList<String>();
		List<Object> zhutulList=(List<Object>) params.get("imageUriList");
		for(int i=0;i<zhutulList.size();i++){
			String imageUrl = zhutulList.get(i).toString();
			String lastImageUrl = saveImage(album.getId(), i,imageUrl, "主图", image_prefix, true);
			imageList.add(lastImageUrl);
		}
		params.put("imageUriList", imageList);
		//imageList.clear();
		//sku图
		Map<String,Object> skuMap=(Map<String,Object>) params.get("skuPics");
		if(skuMap==null)return;
		for(String key:skuMap.keySet()){
			List<Map<String,Object>> skuList=(List<Map<String,Object>>) skuMap.get(key);
			for(int i=0;i<skuList.size();i++){
				Map<String,Object> m=skuList.get(i);
				for(String mkey:m.keySet()){
					String imageUrl = m.get(mkey).toString();
					String lastImageUrl = saveImage(album.getId(), i,imageUrl, mkey, image_prefix, true);
					m.put(mkey, lastImageUrl);
				}
			}
		}
		
		
	}
	private String gendescTmail(Document doc) throws IOException, InterruptedException{
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
		return description;
	}
	/**
	 * 获取要保存图片的相册.
	 * 
	 * @return
	 * @throws AliReqException
	 */
	private Album getAlbum() throws AliReqException {
		List<Album> albumlist = albumService.getAllAlbumList();
		Album album = null;
		int a = 0;
		for (Album al : albumlist) {
			if (al.getName().equals("一键淘宝复制")) {
				if (al.getImageCount() > 300) {
					a = 1;
				} else {
					a = 2;
					album = al;
				}
				break;
			}
		}
		if (a == 1||a==0) {
			album = albumService.createAlbum("一键淘宝复制");
		}
		return album;
	}
	@SuppressWarnings("unchecked")
	private String saveImage(String albumId, int i, String imageUrl,String prefix, String image_prefix, boolean isSavaPic)throws ApiException, AliReqException {
		if (!isSavaPic)return imageUrl;
		if(imageUrl.endsWith("startFlag.gif")||imageUrl.endsWith("endFlag.gif"))return imageUrl;
		byte[] imgByte = ImageUtil.readUrlImage(imageUrl);
		if (imgByte == null || imgByte.length == 0)
			throw new BusinessException(ErrorMessages.image_url_not_exit,
					"图片地址不存在url:" + imageUrl);
		long maxSize = (2 << 16) * 30;
		if (imgByte.length > maxSize) {
			throw new ApiException("文件超过3M");
		}
		Map<String, Object> bean = albumService.uploadImage(albumId,image_prefix + "_" + prefix + "_" + (i + 1), null, imgByte);
		Map<String, Object> result = (Map<String, Object>) bean.get("result");
		List<Map<String, Object>> rtlist = (List<Map<String, Object>>) result.get("toReturn");
		String lastImageUrl = (String) rtlist.get(0).get("url");
		lastImageUrl = aliConstant.image_uri_prefix + lastImageUrl;
		return lastImageUrl;
	}
}
