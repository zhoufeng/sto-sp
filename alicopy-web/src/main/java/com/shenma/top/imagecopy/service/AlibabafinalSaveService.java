package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.service.SelfCatService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.shenma.common.util.DateUtil;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.ecxeption.DuplicateCopyException;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.util.CustomerStringUtil;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.OrderUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.shenma.top.imagecopy.util.prase.AliBaBaImageParse;
import com.shenma.top.imagecopy.util.reqdubbo.HtmlDetailDubboUtil;
import com.shenma.top.imagecopy.util.reqlocal.ImageSaveForLocalUtil;
import com.shenma.top.imagecopy.util.save.AliSaveBean;
import com.taobao.api.ApiException;

@Service
public class AlibabafinalSaveService {
	protected static Logger logger = Logger.getLogger("AlibabafinalSaveService");

	@Autowired
	private AlbumService albumService;// 相册

	@Autowired
	private OwnCatInfoDao ownCatInfoDao;
	
	@Autowired
	private AliConstant aliConstant;

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private AliCatItemAutoUtil aliCatItemAutoUtil;

	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	@Autowired
	private SelfCatService selfCatService;
	
	@Autowired
	private ImageSaveForLocalUtil imageSaveForDubboUtil;
	
	@Autowired
	private HtmlDetailDubboUtil detailDubboUtil;

	private static ThreadLocal<AliSaveBean> params = new ThreadLocal<AliSaveBean>();
	
	
	
	
	@Autowired
	private MemCachedUtil memCachedUtil;

	@SuppressWarnings("unused")
	public Offer save(String url, boolean isSavaPic, MqRecordItem mqItem,Map<String,Object> selfInfo)
			throws IOException, AliReqException, ApiException, InterruptedException, IllegalAccessException, InvocationTargetException {
		
		long starttime=System.currentTimeMillis();
		// 准备数据
		AliSaveBean bean = new AliSaveBean();
		//String html=detailDubboUtil.fetchHtmlDetail(url);
		//Document doc =Jsoup.parse(html);
		Document doc=JsonpUtil.getAliDefaultConnet(url);
		
		bean.setDocument(doc);
		bean.setMqItem(mqItem);
		params.set(bean);

		bean.setUrl(url);
		bean.setPicSave(isSavaPic);// 设置是否需要保存图片.
		bean.setSelfInfo(selfInfo);

		Map<String, Object> detailConfig = getIDetailConfig(doc.html());
		Map<String, Object> detailData = getIDetailData(doc.html());
		bean.setDetailConfig(detailConfig);
		bean.setDetailData(detailData);
		AliBaBaImageParse aliBaBaImageParse = new AliBaBaImageParse();
		bean.setAliBaBaImageParse(aliBaBaImageParse);
		
		Offer offer = new Offer();
		bean.setOffer(offer);

		//获得标题
		String subject = getTitle(selfInfo);
		offer.setSubject(subject);// 设置标题
		// 获得相册(获得相册列表默认是按照时间降序排列的)
		if(isSavaPic){
			Album album = getAlbum(isSavaPic);
			bean.setAlbum(album);
		}

		
		String categoryId = detailConfig.get("catid").toString();// 叶子节点分类ID
		boolean customerCate=(boolean) selfInfo.get("customerCate");
		if(customerCate&&selfInfo.get("customerCateId")!=null){
			categoryId=(String) selfInfo.get("customerCateId");
		}
		bean.setCatsId(Integer.valueOf(categoryId));
		offer.setCategoryID(Integer.valueOf(categoryId));// 设置分类

		
		//重复判断
		boolean ignoreType=(boolean) selfInfo.get("ignoreType");
		String ignoreTypeVal=(String) selfInfo.get("ignoreTypeVal");
		if(ignoreType&&ignoreTypeVal.equals("T")){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("q", subject);
			params.put("pageNo",1);
			params.put("returnFields", new String[]{"offerId","subject"});
			List<OfferDetailInfo> ret=goodsService.search(params);
			for(OfferDetailInfo offerDetailInfo:ret){
				if(offerDetailInfo.getSubject().equals(subject)){
					throw new DuplicateCopyException("404","您的店铺里已经有是标题为:"+subject+"的商品!");
				}
			}

			
		}
		
		mqItem.setTitle(subject);
		mqRecordItemDao.saveAndFlush(mqItem);
		// 设置图片标题
		String image_prefix = null;
		if (subject.length() > 23) {
			image_prefix = subject.substring(0, 23);
		} else {
			image_prefix = subject;
		}
		bean.setImagePrefix(image_prefix);
		// 设置基本商品属性
		setCatProperties();

		// 设置重量
		if(selfInfo.containsKey("offerWeight")){
			offer.setOfferWeight(selfInfo.get("offerWeight").toString());
		}else{
			Element ele = doc.select(".parcel-unit-weight .value").first();
			if (ele != null) {
				offer.setOfferWeight(ele.text().trim());
			} else {
				offer.setOfferWeight("1");
			}
		}
		
		// 主图
		List<Integer> zhutuList=new ArrayList<>();
		if(selfInfo.containsKey("zhutuCategorys")){
			zhutuList=(List<Integer>) selfInfo.get("zhutuCategorys");
		}
		saveZhutu(zhutuList);
		// 详情
		String content = saveContent(selfInfo);
		
		offer.setOfferDetail(content);

		// 物流运费信息
		if(selfInfo.containsKey("freightType")){
			offer.setFreightType(selfInfo.get("freightType").toString());
		}else{
			/*List<DeliveryTemplateDescn> wuliList = wuliuService.getAllDeliveryTemplateDescn();
			if (wuliList.size() > 0) {
				offer.setFreightType("T");
				offer.setFreightTemplateId(wuliList.get(0).getTemplateId());
			}*/
		}
		if(selfInfo.containsKey("freightTemplateId")){
			offer.setFreightTemplateId(Long.valueOf(selfInfo.get("freightTemplateId").toString()));
		}
		// 价格范围
		savePriceRange();

		Element priceElement = doc.select(".price-reference").first();
		if (priceElement != null) {
			offer.setSupportOnlineTrade(Boolean.FALSE);
		} else {
			offer.setSupportOnlineTrade(Boolean.TRUE);
		}
		

		
		//自定义分类
		if(selfInfo.containsKey("userCategorys")){
			List<String> userCategorys=(List<String>) selfInfo.get("userCategorys");
			offer.setUserCategorys(userCategorys);
		}
		
		//混批
		if(selfInfo.containsKey("mixWholeSale")){
			boolean mixWholeSale=(boolean) selfInfo.get("mixWholeSale");
			offer.setMixWholeSale(mixWholeSale);
		}
		if(selfInfo.containsKey("pictureAuthOffer")){
			boolean pictureAuthOffer=(boolean) selfInfo.get("pictureAuthOffer");
			offer.setPictureAuthOffer(pictureAuthOffer);
		}
		if(selfInfo.containsKey("priceAuthOffer")){
			boolean priceAuthOffer=(boolean) selfInfo.get("pictureAuthOffer");
			offer.setPriceAuthOffer(priceAuthOffer);
		}
		
		//自定义价格
		if(selfInfo.containsKey("slefPrice")){
			String priceType=(String) selfInfo.get("priceType");
			double selfprice=Double.parseDouble(selfInfo.get("slefPrice").toString());
			if(!StringUtils.isEmpty(offer.getPriceRanges())){//pricePrage
				String lastPriceRanges="";
				String[] prstr=offer.getPriceRanges().split("`");
				for(String pr:prstr){
					String[] temparr=pr.split(":");
					double price=Double.parseDouble(temparr[1]);
					price = selfpriceset(priceType, selfprice, price);
					lastPriceRanges+=(Integer.valueOf(temparr[0])<3?"3":temparr[0]) + ":"+price+"`";
				}
				lastPriceRanges=lastPriceRanges.substring(0, lastPriceRanges.length()-1);
				offer.setPriceRanges(lastPriceRanges);
			}
			if(offer.getSkuList()!=null){
				for(Map<String,Object> obj:offer.getSkuList()){
					if(obj.get("price")!=null&&!StringUtils.isEmpty(obj.get("price").toString())){
						double price=Double.parseDouble(obj.get("price").toString());
						price =selfpriceset(priceType, selfprice, price);
						obj.put("price", String.valueOf(price));
					}
					if(obj.get("retailPrice")!=null&&!StringUtils.isEmpty(obj.get("retailPrice").toString())){
						double price=Double.parseDouble(obj.get("retailPrice").toString());
						price =selfpriceset(priceType, selfprice, price);
						obj.put("retailPrice", String.valueOf(price));
					}
				}
			}
		}
		//信息有效期
		if(selfInfo.containsKey("periodOfValidity")){
			Integer periodOfValidity=Integer.valueOf(selfInfo.get("periodOfValidity").toString());
			offer.setPeriodOfValidity(periodOfValidity);
		}
		
		//发货地址
		if(selfInfo.containsKey("sendGoodsAddressId")){
			String sendGoodsAddressId=selfInfo.get("sendGoodsAddressId").toString();
			offer.setSendGoodsAddressId(sendGoodsAddressId);
		}
		
		//自定义价格
		if(selfInfo.containsKey("selfPrice")){
			String priceType=(String) selfInfo.get("priceType");
			double selfprice=Double.parseDouble(selfInfo.get("selfPrice").toString());
			if(!StringUtils.isEmpty(offer.getPriceRanges())){//pricePrage
				String lastPriceRanges="";
				String[] prstr=offer.getPriceRanges().split("`");
				for(String pr:prstr){
					String[] temparr=pr.split(":");
					double price=Double.parseDouble(temparr[1]);
					price = selfpriceset(priceType, selfprice, price);
					lastPriceRanges+=temparr[0]+":"+price+"`";
				}
				lastPriceRanges=lastPriceRanges.substring(0, lastPriceRanges.length()-1);
				offer.setPriceRanges(lastPriceRanges);
			}
			if(offer.getSkuList()!=null){
				for(Map<String,Object> obj:offer.getSkuList()){
					if(obj.get("price")!=null&&!StringUtils.isEmpty(obj.get("price").toString())){
						double price=Double.parseDouble(obj.get("price").toString());
						price =selfpriceset(priceType, selfprice, price);
						obj.put("price", String.valueOf(price));
					}
					if(obj.get("retailPrice")!=null&&!StringUtils.isEmpty(obj.get("retailPrice").toString())){
						double price=Double.parseDouble(obj.get("retailPrice").toString());
						price =selfpriceset(priceType, selfprice, price);
						obj.put("retailPrice", String.valueOf(price));
					}
				}
			}
		}
		
		//销售方式
		Element xsspan=doc.select(".obj-seller-package span").first();
		if(xsspan!=null){
			String xsstr=xsspan.text();
			String[] xsstrs=xsstr.split("=");
			offer.getProductFeatures().put("7588903", xsstrs[0].substring(xsstrs[0].length()-1, xsstrs[0].length()));
			offer.getProductFeatures().put("100022873", xsstrs[1].substring(0, xsstrs[1].length()-1));
		}
		
		
		//临时
		if(selfInfo.containsKey("imageList")){
			String[] imageList=selfInfo.get("imageList").toString().split(",");
			List<String> imageUriList=new ArrayList<String>();
			Collections.addAll(imageUriList, imageList);
			offer.setImageUriList(imageUriList);
		}
		if(selfInfo.containsKey("selfDesc")){
			String offerDetail=selfInfo.get("selfDesc").toString();
			offer.setOfferDetail(offerDetail);
		}
		
		
		// 设置
		String offstr = JacksonJsonMapper.getInstance().writeValueAsString(offer);
		// 设置单位
		if(selfInfo.containsKey("offerUnit")){
			offer.getProductFeatures().put("1459", selfInfo.get("offerUnit").toString());
		}else{
			//offstr = "{\"1459\":\"" + detailConfig.get("unit") + "\","+ offstr.substring(1, offstr.length());
		}
		System.out.println(offstr);
		Long id = goodsService.newOffer(offstr);
		if(selfInfo.containsKey("expireed")){
			Thread.currentThread().sleep(500);
			boolean expireed=(boolean) selfInfo.get("expireed");
			if(expireed==true) {
				goodsService.expire(id.toString());
			}
		}
		offer.setOfferId(id);
		logger.debug(System.currentTimeMillis()-starttime);
		return offer;
	}

	private String saveContent(Map<String, Object> selfInfo)throws IOException, ApiException, AliReqException {
		String otherHref=null;
		if(selfInfo.containsKey("otherHref")){
			otherHref=(String) selfInfo.get("otherHref");
		}
		String content = setDescInfo(otherHref);
		if(selfInfo.containsKey("contentReplace")){ //标题替换
			boolean contentReplace=(boolean) selfInfo.get("contentReplace");
			if(contentReplace){
				String repOldStr=(String) selfInfo.get("contentReplaceOld");
				String repNewStr=(String) selfInfo.get("contentReplaceNew");
				if(repOldStr!=null){
					if(repNewStr==null)repNewStr="";
					content=content.replace(repOldStr, repNewStr);
				}
			}
		}
		if(selfInfo.containsKey("contentDelete")){ //标题删除
			boolean contentReplace=(boolean) selfInfo.get("contentDelete");
			if(contentReplace){
				String repDelStr=(String) selfInfo.get("contentDeleteKey");
				String[] repDels=repDelStr.split(",");
				for(String s:repDels){
					content=content.replace(s, "");
				}
			}
		}
		
		
		if(content.length()>50000){
			content=content.substring(0, 50000);
		}
		return content;
	}

	private double selfpriceset(String priceType, double selfprice, double price) {
		if("a".endsWith(priceType)){
			price=price+selfprice;
		}else if("s".endsWith(priceType)){
			price=price-selfprice;
		}else if("m".endsWith(priceType)){
			price=price*selfprice;
		}
		return price;
	}

	/**
	 * 设置价格.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void savePriceRange() throws JsonParseException,JsonMappingException, IOException {
		AliSaveBean bean = params.get();
		Offer offer = bean.getOffer();
		Document doc = bean.getDocument();
		Map<String, Object> detailData = bean.getDetailData();
		if (detailData != null) {
			List<List<Object>> priceRange = (List<List<Object>>) detailData.get("priceRangeOriginal");
			if (priceRange != null) {  
				offer.setSkuTradeSupport(Boolean.FALSE);
				String priceRangeStr = "";
				for (List<Object> pricelist : priceRange) {
					priceRangeStr += (((Integer)pricelist.get(0))<3?"3":pricelist.get(0).toString()) + ":"
							+ pricelist.get(1).toString() + "`";
				}
				if (priceRangeStr.equals("")) {
					priceRangeStr = null;
				} else {
					priceRangeStr = priceRangeStr.substring(0,
							priceRangeStr.length() - 1);
				}
				offer.setPriceRanges(priceRangeStr);
			}
		} else {
			Elements modDetailPrices=null;
			Element priceEle = doc.select("#mod-detail-price tr.original-price").first();//在标题下面的价格列表
			if(priceEle==null){
				modDetailPrices = doc.select("#mod-detail-price tr.price td[data-range]");
			}else{
				modDetailPrices = doc.select("#mod-detail-price tr.original-price td[data-range]");
			}
			String priceRangeStr = "";
			for (Element mod : modDetailPrices) {
				String dataRange = mod.attr("data-range");
				Map<String, Object> map = JacksonJsonMapper.getInstance().readValue(dataRange, HashMap.class);
				String begin = Integer.valueOf((String)map.get("begin"))<3?"3":(String)map.get("begin");
				String end = (String) map.get("end");
				String price = (String) map.get("price");
				priceRangeStr += begin + ":" + price + "`";
			}
			if (priceRangeStr.equals("")) {
				priceRangeStr = null;
			} else {
				priceRangeStr = priceRangeStr.substring(0,
						priceRangeStr.length() - 1);
			}
			offer.setPriceRanges(priceRangeStr);

			Element amountOnSale = doc.select("div.mod-detail-purchasing.mod-detail-purchasing-single[data-mod-config]").first();
			if (amountOnSale != null) {
				String dataconfig = amountOnSale.attr("data-mod-config");
				Map<String, Object> dataMap = JacksonJsonMapper.getInstance().readValue(dataconfig, HashMap.class);
				Integer amount = Integer.valueOf(dataMap.get("max").toString());
				if(amount>=1000000000)amount=100000000;
				offer.setAmountOnSale(amount);
			}
		}
	}

	/**
	 * 填写详细信息
	 * @param otherHref 是否去外链
	 * @throws IOException
	 * @throws AliReqException
	 * @throws ApiException
	 */
	@SuppressWarnings("unchecked")
	private String setDescInfo(String otherHref) throws IOException, ApiException,
			AliReqException {
		AliSaveBean bean = params.get();
		Element ele = bean.getDocument().select("#de-description-detail").first();
		if("C".equals(otherHref)){
			ele.select("a[href]").removeAttr("href");
			ele.select("area[href]").removeAttr("href");
		}else if("Q".equals(otherHref)){
			ele.select("table").remove();
		}
		String str = ele.html();
		List<ImageVoBean> imaglist = bean.getAliBaBaImageParse().genXiangqinImage(bean.getDocument(), bean.getUrl(),bean.getOffer().getSubject());
		
		if(bean.isPicSave()){
			List<String> romteList=new ArrayList<String>();
			for(ImageVoBean voBean:imaglist){
				String url=voBean.getUrl();
				if(url.endsWith("startFlag.gif")||url.endsWith("endFlag.gif"))continue;
				romteList.add(url);
			}
			
			OrderUtil.removeDuplicateWithOrder(romteList);
			for(String imageUrl:romteList){

				Map<String,Object> retMap=imageSaveForDubboUtil.saveImage(imageUrl, bean.getAlbum().getId(), bean.getImagePrefix()+"_详情", "");
				if(!retMap.containsKey("error")){
					String newUrl = (String) retMap.get("newUrl");
					if(!StringUtils.isEmpty(newUrl)){
						str = str.replaceAll(imageUrl, newUrl);
					}
				}
			}
			
			/*Map<String,Object> remoteMap=imageSaveForDubboUtil.saveImages(romteList, bean.getAlbum().getId(), bean.getImagePrefix()+"_详情","");
			List<Map<String,Object>> retList=(List<Map<String, Object>>) remoteMap.get("resultList");
			for (Map<String,Object> retMap:retList) {
				if(retMap.containsKey("errorMsg"))continue;
				String url = (String) retMap.get("url");
				String newUrl = (String) retMap.get("newUrl");
				if(!StringUtils.isEmpty(newUrl)){
					str = str.replaceAll(url, newUrl);
				}
			}*/
		}
		return str;
	}

	/**
	 * 保存主图
	 * 
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws ApiException
	 * @throws AliReqException
	 */
	private void saveZhutu(List<Integer> zhutuList) throws JsonParseException, JsonMappingException,
			IOException, ApiException, AliReqException {
		AliSaveBean bean = params.get();
		// 主图
		List<ImageVoBean> imaglist = bean.getAliBaBaImageParse().genZhutuImage(bean.getDocument(), bean.getUrl(),bean.getOffer().getSubject());
		
		List<ImageVoBean> saveImagelist=new ArrayList<ImageVoBean>();
		if(zhutuList.size()>0){
			for(Integer index:zhutuList){
				if(index<=imaglist.size()){
					saveImagelist.add(imaglist.get(index-1));
				}
			}
		}else{
			if(imaglist.size()>0)saveImagelist=imaglist.subList(0, imaglist.size()>5?5:imaglist.size());
		}

		List<String> imageList = new ArrayList<String>();
		Map<String, String> imageMap = new HashMap<String, String>();
		for (int i = 0; i < saveImagelist.size(); i++) {
			String imageUrl = saveImagelist.get(i).getUrl();
			if(!bean.isPicSave()){
				imageList.add(imageUrl);
				continue;
			}
			String lastImageUrl=null;
			Map<String,Object> retMap=imageSaveForDubboUtil.saveImage(imageUrl, bean.getAlbum().getId(), bean.getImagePrefix()+"_详情", "");
			if(!retMap.containsKey("error")){
				lastImageUrl=(String) retMap.get("newUrl");
				imageMap.put(imageUrl, lastImageUrl);
				imageList.add(lastImageUrl);
			}
		}
		bean.getOffer().setImageUriList(imageList);
	}

	@SuppressWarnings("unchecked")
	private void setCatProperties() throws JsonParseException,
			JsonMappingException, IOException, ApiException, AliReqException, IllegalAccessException, InvocationTargetException {
		Element attributes = params.get().getDocument().select("#mod-detail-attributes").first();
		if (attributes != null) {
			Elements feautres = attributes.select("td.de-feature");
			Elements fvalues = attributes.select("td.de-value");
			Map<String, String> attributesMap = new HashMap<String, String>(); // 基本属性Map
			if (fvalues != null && fvalues.size() > 0) { // 获得基本属性2种方式,目前不清楚有什么条件
				for (int i = 0; i < fvalues.size(); i++) {
					String name = feautres.get(i).text();
					String fValue = fvalues.get(i).text();
					attributesMap.put(name, fValue);
				}
			} else {
				for (Element feautre : feautres) {
					String nameValue = feautre.text();
					if (!StringUtils.isEmpty(nameValue)) {
						String[] nameValueArr = nameValue.split("：");
						String name = nameValueArr[0].trim();
						String fValue = nameValueArr[1].trim();
						attributesMap.put(name, fValue);
					}
				}
			}
			Map<String,Object> extraPro=(Map<String, Object>) params.get().getSelfInfo().get("extra");
			//BeanUtils.copyProperties(attributesMap,extraPro);
			for(String key:extraPro.keySet()){
				attributesMap.put(key, extraPro.get(key).toString());
			}
			deal(attributesMap);

		}

	

	}

	@SuppressWarnings("unchecked")
	private void deal(Map<String, String> attributesMap)throws JsonParseException, JsonMappingException, IOException,ApiException, AliReqException {
		AliSaveBean bean = params.get();
		Offer offer = bean.getOffer();
		Map<String, String> productFeatures = new HashMap<String, String>(); // 最后保存到offer的属性.
		Integer catsId = bean.getCatsId();
		OwnCatInfo catInfo = ownCatInfoDao.findByCatsId(catsId);
		offer.setBizType(catInfo.getTradeYpe());
		String properties = catInfo.getProperties();
		Map<String, Object> propertiesmap = JacksonJsonMapper.getInstance().readValue(properties, HashMap.class);
		List<Map<String, Object>> productFeatureList = (List<Map<String, Object>>) propertiesmap.get("productFeatureList");
		Map<String, Map<String, Object>> baseMap = new HashMap<String, Map<String, Object>>(); // 产品属性
		Map<String, Map<String, Object>> specAttrMap = new HashMap<String, Map<String, Object>>();// 产品规格
		bean.setBaseMap(baseMap);
		bean.setSpecAttrMap(specAttrMap);
		for (Map<String, Object> postatrribute : productFeatureList) {

			if (postatrribute.get("isSpecAttr").toString().equals("true")) {
				specAttrMap.put(postatrribute.get("name").toString(),postatrribute);
			} else {
				baseMap.put(postatrribute.get("name").toString(), postatrribute);
			}

		}

		// 给productFeatures赋基本属性值.
		Map<String, Map<String, Object>> baseCloneMap = new HashMap<String, Map<String, Object>>(); // 产品属性
		baseCloneMap.putAll(baseMap);
		//建议零售价
		if(attributesMap.containsKey("建议零售价")){
			String value = attributesMap.get("建议零售价").substring(1);
			productFeatures.put("8021", value);
		}
		for (String basekey : baseCloneMap.keySet()) {
			Map<String, Object> productFeature = baseMap.get(basekey);

			for (String key : attributesMap.keySet()) {
				String value = attributesMap.get(key);
				if (productFeature.get("name").toString().equals(key)) {
					String fid = productFeature.get("fid").toString();
					String fieldType = productFeature.get("fieldType").toString();
					String inputType = productFeature.get("inputType").toString();
					String unit = (String) productFeature.get("unit");
					if (StringUtils.isNotEmpty(unit)) {
						String delstr = "（" + unit + "）";
						if (value.indexOf(delstr) != -1) {
							value = value.substring(0,value.length() - delstr.length());
						}
						if(value.endsWith(unit)){
							value =value.substring(0,value.length() - unit.length());
						}

					}
					String pathValues = "";
					if (fieldType.equals("string")) {// 文本框
						productFeatures.put(fid, value);
					} else if (fieldType.equals("enum")&& inputType.equals("2")) { // 多选框
						value = value.replaceAll(",", "|").trim();
						productFeatures.put(fid, value);
						diedai(bean.getCatsId().toString(), productFeature,value, fid, attributesMap, productFeatures,pathValues);
					} else if (fieldType.equals("enum")&& inputType.equals("3")) { // 单选框
						value = value.replaceAll(",", "|").trim();
						productFeatures.put(fid, value);
						diedai(bean.getCatsId().toString(), productFeature,value, fid, attributesMap, productFeatures,pathValues);
					}  else if (fieldType.equals("enum")&& inputType.equals("1")) { // 下拉框
						List<Map<String,Object>> featureIdValues=(List<Map<String, Object>>) productFeature.get("featureIdValues");
						if(featureIdValues.size()>0){
							boolean isOtherExit=false; //是否存在其他的选项
							boolean isExit=false; //是否存在其他的选项
							for(Map<String,Object> fidv:featureIdValues){
								if(value.equals(fidv.get("value").toString())){
									isExit=true;
								}
								if("其他".equals(fidv.get("value").toString())){
									isOtherExit=true;
								}
							}
							if(isExit){
								productFeatures.put(fid, value);
							}else{
								if(isOtherExit){
									productFeatures.put(fid, value);
								}
							}
						}
						diedai(bean.getCatsId().toString(), productFeature,value, fid, attributesMap, productFeatures,pathValues);
					} else {
						productFeatures.put(fid, value);
					}
				}
			}
		}
		baseCloneMap.putAll(baseMap);
		for (String key : baseCloneMap.keySet()) {

			Map<String, Object> postatrribute = baseMap.get(key);
			if (postatrribute.get("isNeeded").toString().equals("Y")&& !productFeatures.containsKey(postatrribute.get("fid").toString())) {
				String fieldType=postatrribute.get("fieldType").toString();
				String fid=postatrribute.get("fid").toString();
				if(fieldType.equals("string")){
					productFeatures.put(fid, "12");
				}else if(fieldType.equals("enum")){
					/*if(fid.equals("7869")){
						System.out.println(1);
					}*/
					List<Map<String, Object>> items = (List<Map<String, Object>>) postatrribute.get("featureIdValues");
					productFeatures.put(fid, items.get(0).get("value").toString());
				}else{
					productFeatures.put(fid, "12");
				}
				List<Map<String, Object>> items = (List<Map<String, Object>>) postatrribute.get("featureIdValues");
				if (items.size() > 0) {
					String pathValues = "";
					teshudiedai(bean.getCatsId().toString(), postatrribute, productFeatures, pathValues);
				}

			}
		}
		Map<String, Object> detailConfig = bean.getDetailConfig();
		if(detailConfig.containsKey("beginAmount")){
			Integer beginAmout=Integer.valueOf(detailConfig.get("beginAmount").toString());
			productFeatures.put("3939",beginAmout>3?detailConfig.get("beginAmount").toString():"3"); //beginAmount的值
		}else{
			productFeatures.put("3939", "3"); //beginAmount的值
		}
		offer.setProductFeatures(productFeatures);

		//处理项链的函数
		Map<String,String> tempxlMap=new HashMap<String,String>();
		// 给productFeatures赋产品规格值
		boolean isModify=false;//判断产品参数有没有被修改过
		
		Map<String, Object> detailData = bean.getDetailData();
		if (detailData == null)return;
		Map<String, Object> skuMap = (Map<String, Object>) detailData.get("skuMap");
		List<Map<String, Object>> skuPropsList = (List<Map<String, Object>>) detailData.get("skuProps");
		if(skuPropsList.size()>0){
			offer.setSkuTradeSupport(Boolean.TRUE);
			for(Map<String, Object> skuProps:skuPropsList){
				Map<String,Object> a=specAttrMap.get(skuProps.get("prop").toString());
				if(a==null){
					isModify=true;
					break;
				}
			}
			List<Map<String, Object>> skuList = new ArrayList<Map<String, Object>>();
			if(isModify){
				Map<String, Object> obj = new HashMap<String, Object>();
				Map<String, String> specAttributes = new HashMap<String, String>();
				obj.put("specAttributes", specAttributes);
				String price="999";
				Object priceObj=detailData.get("price");
				if(priceObj!=null){
					String[] priceRange=priceObj.toString().split("-");
					if(priceRange[0]!=null)price=priceRange[0];
				}
				obj.put("price", price);
				obj.put("amountOnSale",999999);
				obj.put("retailPrice",price);
				for(String specAttrKey:specAttrMap.keySet()){
					Map<String,Object> specAttr=specAttrMap.get(specAttrKey);
					List<Map<String,Object>> featureIdValues=(List<Map<String, Object>>) specAttr.get("featureIdValues");
					specAttributes.put(specAttr.get("fid").toString(), featureIdValues.get(0).get("value").toString());
					productFeatures.put(specAttr.get("fid").toString(), featureIdValues.get(0).get("value").toString());
				}
				skuList.add(obj);
			}else{
				if (detailData.containsKey("skuMap")) {
					if (skuMap != null) {
						for (String key : skuMap.keySet()) {
							Map<String, Object> obj = new HashMap<String, Object>();
							String[] strs = key.split(">");
							Map<String, String> specAttributes = new HashMap<String, String>();
							obj.put("specAttributes", specAttributes);
							
							String xlstr="";
							for (int i = 0; i < strs.length; i++) {
								String str = strs[i];
								String name = skuPropsList.get(i).get("prop").toString();
								Map<String, Object> productFeature = specAttrMap.get(name);
								if(productFeature!=null){
									specAttributes.put(productFeature.get("fid").toString(), str);
									xlstr+=productFeature.get("fid").toString()+str;
									
								}
							}
							Map<String, Map<String, Object>> value = (Map<String, Map<String, Object>>) skuMap.get(key);
							obj.put("amountOnSale", 999999);
							Object price=value.get("price");
							if(price==null){
								//offer.setSkuTradeSupport(Boolean.FALSE);
							}else{
								//offer.setSkuTradeSupport(Boolean.TRUE);
								obj.put("price", price);
								Object retailPrice=null;
								Object tmp=value.get("retailPrice");
								if(tmp==null||StringUtils.isEmpty(tmp.toString())){
									retailPrice=price;
								}else{
									retailPrice=value.get("retailPrice");
								}
								obj.put("retailPrice",retailPrice);
							}
							Object specId=value.get("specId");
							if(specId!=null)obj.put("cargoNumber", value.get("specId"));
							//obj.put("cargoNumber", value.get("cargoNumber"));
							if(!tempxlMap.containsKey(xlstr)){
								skuList.add(obj);
								tempxlMap.put(xlstr, xlstr);
							}
	
						}
					}
				}
			}
			offer.setSkuList(skuList);
		}
		
		// 匹配图片
		if (detailData != null && detailData.containsKey("skuProps")) {
			Map<String, Object> skuPics = new HashMap<String, Object>();
			List<Map<String, Object>> colorlist = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> skuProp : skuPropsList) {
				String prop = skuProp.get("prop").toString();
				List<Map<String, Object>> values = (List<Map<String, Object>>) skuProp.get("value");
				Map<String, Object> productFeature = specAttrMap.get(prop);
				if(productFeature==null)continue; //老的sku属性更新后没有了.
				String fid = productFeature.get("fid").toString();
				if(productFeature.get("isSpecPicAttr").toString().equals("true"))skuPics.put(fid, colorlist);
				offer.setSkuPics(skuPics);
				Map<String, String> tempskuMap = new HashMap<String, String>();
				for (Map<String, Object> value : values) {
					String oldValue = tempskuMap.get(fid) == null ? "": tempskuMap.get(fid);
					tempskuMap.put(fid, oldValue + value.get("name").toString()+ "|");
				}
				productFeatures.put(fid,tempskuMap.get(fid).substring(0,tempskuMap.get(fid).length() - 1));

				if (productFeature != null&& productFeature.get("isSpecPicAttr").toString().equals("true")) {
					for (Map<String, Object> value : values) {
						Map<String, Object> obj = new HashMap<String, Object>(1);
						String imageUrl = (String) value.get("imageUrl");
						String name = (String) value.get("name");
						if (imageUrl != null) {
							if(bean.isPicSave()){
								Map<String,Object> retMap=imageSaveForDubboUtil.saveImage(imageUrl, bean.getAlbum().getId(), bean.getImagePrefix()+"_销属", "");
								if(!retMap.containsKey("error")){
									imageUrl = (String) retMap.get("newUrl");
								}
							}
							obj.put(name, imageUrl);
						}
						colorlist.add(obj);
					}
				}
			}

		}

	}

	@SuppressWarnings("unchecked")
	private void diedai(String catId, Map<String, Object> productFeature,
			String value, String fid, Map<String, String> attributesMap,
			Map<String, String> saveMap, String pathValues)
			throws JsonParseException, JsonMappingException, IOException {
		List<Integer> childrenFids = (List<Integer>) productFeature.get("childrenFids");
		List<Map<String, Object>> featureIdValues = (List<Map<String, Object>>) productFeature.get("featureIdValues");
		AliSaveBean bean = params.get();
		if (childrenFids.size() > 0) {
			for (Map<String, Object> featureIdValue : featureIdValues) {
				if (featureIdValue.get("value").toString().equals(value)) {// 看是否有子项
					String vid = featureIdValue.get("vid").toString();
					if ("".equals(pathValues)) {
						pathValues += fid + ":" + vid;
					} else {
						pathValues += "%3E" + fid + ":" + vid;
					}

					List<Map<String, Object>> data = aliCatItemAutoUtil.getItem(catId, pathValues);
					for (Map<String, Object> dataobj : data) {

						if(dataobj.get("isSpecAttr")!=null&&dataobj.get("isSpecAttr").toString().equals("true")){
							bean.getSpecAttrMap().put(dataobj.get("name").toString(), dataobj);
						}else{
							bean.getBaseMap().put(dataobj.get("name").toString(), dataobj);
						}
						//有些是之前做的,但是不是必填项,现在强制.
						if((!attributesMap.containsKey(dataobj.get("name").toString()))&&dataobj.get("isNeeded").toString().equals("Y")){
							
							List<Map<String, Object>> datafeatureIdValues = (List<Map<String, Object>>) dataobj.get("featureIdValues");
							String dataobjid=dataobj.get("fid").toString();
							String fieldType = dataobj.get("fieldType").toString();
							String inputType = dataobj.get("inputType").toString();
							String v="123";
							if (fieldType.equals("enum")) { // 多选框
								v=datafeatureIdValues.get(0).get("value").toString();
								diedai(catId, dataobj, v, dataobjid, attributesMap, saveMap, pathValues);
							}
							saveMap.put(fid, v);
						}
						
						for (String key : attributesMap.keySet()) {
							if (dataobj.get("name").toString().equals(key)) {
								String unit = (String) dataobj.get("unit");
								String val=attributesMap.get(key);
								if (StringUtils.isNotEmpty(unit)) {
									String delstr = "（" + unit + "）";
									if (val.indexOf(delstr) != -1) {
										val = val.substring(0,val.length() - delstr.length());
									}
									if(value.endsWith(unit)){
										val =val.substring(0,val.length() - unit.length());
									}

								}
								saveMap.put(dataobj.get("fid").toString(),val);
								List<Integer> list = (List<Integer>) dataobj.get("childrenFids");
								if (list.size() > 0)
									diedai(params.get().getCatsId().toString(),dataobj, attributesMap.get(key),dataobj.get("fid").toString(),attributesMap, saveMap, pathValues);
							}

						}
						
						
					}
				}
			}
		}
	}

	/**
	 * 默认选项
	 */
	@SuppressWarnings("unchecked")
	private void teshudiedai(String catId, Map<String, Object> productFeature,Map<String, String> saveMap, String pathValues)throws JsonParseException, JsonMappingException, IOException {
		List<Integer> childrenFids = (List<Integer>) productFeature.get("childrenFids");
		String fid = productFeature.get("fid").toString();
		List<Map<String, Object>> featureIdValues = (List<Map<String, Object>>) productFeature.get("featureIdValues");
		if (childrenFids.size() > 0) {
			
			Map<String, Object> featureIdValue=featureIdValues.get(0);
			String vid = featureIdValue.get("vid").toString();
			String value = featureIdValue.get("value").toString();
			String newPathValues="";
			if ("".equals(pathValues)) {
				newPathValues=pathValues+ fid + ":" + vid;
			} else {
				newPathValues=pathValues+ "%3E" + fid + ":" + vid;
			}
			List<Map<String, Object>> data = aliCatItemAutoUtil.getItem(catId, newPathValues);
			for (Map<String, Object> dataobj : data) {
				if (dataobj.get("isNeeded").toString().equals("Y")) {
					List<Map<String, Object>> datafeatureIdValues = (List<Map<String, Object>>) productFeature.get("featureIdValues");
					String dataobjid=dataobj.get("fid").toString();
					String fieldType = dataobj.get("fieldType").toString();
					String inputType = dataobj.get("inputType").toString();
					if (fieldType.equals("string")) {// 文本框
						saveMap.put(dataobjid, "123");
					}else if (fieldType.equals("int")){
						saveMap.put(dataobjid, "123");
					} else if (fieldType.equals("enum")) { // 多选框
						saveMap.put(dataobjid, datafeatureIdValues.get(0).get("value").toString());
						teshudiedai(catId, dataobj, saveMap, newPathValues);
					}  else {
						saveMap.put(dataobjid, "123");
					}
				} 

			}

		}
	}

	/**
	 * 获得标题
	 * 
	 * @return
	 */
	private String getTitle(Map<String,Object> selfInfo) {
		Document doc = params.get().getDocument();
		String subject = doc.select("h1.d-title").first().text();
		if(selfInfo.containsKey("titleReplace")){ //标题替换
			boolean titleReplace=(boolean) selfInfo.get("titleReplace");
			if(titleReplace){
				String repOldStr=(String) selfInfo.get("titleReplaceOld");
				String repNewStr=(String) selfInfo.get("titleReplaceNew");
				subject=subject.replace(repOldStr, repNewStr);
			}
		}
		if(selfInfo.containsKey("titleDelete")){ //标题删除
			boolean titleReplace=(boolean) selfInfo.get("titleDelete");
			if(titleReplace){
				String repDelStr=(String) selfInfo.get("titleDeleteKey");
				String[] repDels=repDelStr.split(",");
				for(String s:repDels){
					subject=subject.replace(s, "");
				}
			}
		}
		if(CustomerStringUtil.getLength(subject)>30){
			subject=subject.substring(0, 30);
		}
		return subject;
	}

	/**
	 * 获取要保存图片的相册.
	 * 
	 * @return
	 * @throws AliReqException
	 */
	private Album getAlbum(boolean isSavaPic) throws AliReqException {
		Album album = null;
		Map<String,Object> selfInfo=params.get().getSelfInfo();
		if(selfInfo.containsKey("albumId")){
			album =albumService.findAlbumById(selfInfo.get("albumId").toString());
			if(album!=null&&album.getImageCount() <= 300){
				return album;
			}
		}
		List<Album> albumlist = albumService.getAllAlbumList();
		//设置每一个产品一个相册
		if(selfInfo.containsKey("newAlbum")&&isSavaPic){
			boolean newAlbum=(boolean) selfInfo.get("newAlbum");
			if(newAlbum){
				if(albumlist.size()>=500){
					throw new BusinessException("401","相册数量已满500个,请删除一些相册,再复制");
				}
				album = albumService.createAlbum(params.get().getOffer().getSubject());
				return album;
			}
		}
		
		int a = 0;
		for (Album al : albumlist) {
			if (al.getName().indexOf("一键阿里复制_")>-1) {
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
			album = albumService.createAlbum("一键阿里复制_"+DateUtil.getStringDateShort());
		}
		return album;
	}

	/**
	 * 获得一该商品商品的详细信息如catsId等
	 * 
	 * @param html
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getIDetailConfig(String html)
			throws JsonParseException, JsonMappingException, IOException {
		Pattern pattern = Pattern.compile("(?<=iDetailConfig = )[\\s\\S]*?\\}\\;");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str = str.replaceAll("\\n", "");
		str = str.replaceAll("'", "\"");
		//str = str.replaceAll(" ", "");
		if(str=="")throw new BusinessException("401","请检查您的地址是否有误!");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}
		return ret;
	}

	/**
	 * 获得商品的基本属性,颜色及尺寸等
	 * 
	 * @param html
	 * @param doc
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getIDetailData(String html)
			throws JsonParseException, JsonMappingException, IOException {
		Pattern pattern = Pattern.compile("(?<=iDetailData = )[\\s\\S]*?(?=\\}\\;)");
		Matcher matcher = pattern.matcher(html);
		String str = "";
		if (matcher.find()) {
			str = matcher.group(0);
		}
		str += "}";
		str = str.replaceAll("\\n", "");
		str = str.replaceAll("\\t", "");
		str = str.replaceAll("&gt;", ">");
		str = str.replaceAll("'", "\"");
		//str = str.replaceAll(" ", "");
		HashMap<String, Object> ret = null;
		if ("{}".equals(str)) {
			return null;
		} else {
			ret = JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}
		ret = (HashMap<String, Object>) ret.get("sku");
		return ret;
	}

	/** 
	  * 判断字符串是否是整数 
	  */  
	 /*public static boolean isInteger(String value) {  
	     try {  
	         Integer.parseInt(value);  
	         return true;  
	     } catch (NumberFormatException e) {  
	         return false;  
	     }  
	 } */
	/*@SuppressWarnings("unchecked")
	private String saveImage(String albumId, int i, String imageUrl,String prefix, String image_prefix, boolean isSavaPic)throws ApiException, AliReqException,BusinessException {
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
	}*/
}
