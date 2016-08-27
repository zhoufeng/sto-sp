package com.shenma.top.imagecopy.service;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.entity.goods.OfferDetailInfo;
import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.service.WuliuService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.common.util.DateUtil;
import com.shenma.common.util.ImageUtil;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.ecxeption.DuplicateCopyException;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.OrderUtil;
import com.shenma.top.imagecopy.util.CustomerStringUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.shenma.top.imagecopy.util.exception.ErrorMessages;
import com.shenma.top.imagecopy.util.reqlocal.ImageSaveForLocalUtil;
import com.shenma.top.imagecopy.util.save.TaobaoSaveBean;
import com.shenma.top.imagecopy.util.strategy.OneToOneStrategy;
import com.shenma.top.imagecopy.util.strategy.OneToTwoStrategy;
import com.shenma.top.imagecopy.util.strategy.OthersStrategy;
import com.shenma.top.imagecopy.util.strategy.TopStrategyFactory;
import com.shenma.top.imagecopy.util.strategy.TopToAliContext;
import com.shenma.top.imagecopy.util.strategy.TopToAliIStrategy;
import com.shenma.top.imagecopy.util.strategy.TwoToOneStrategy;
import com.shenma.top.imagecopy.util.strategy.TwoTwoStrategy;
import com.taobao.api.ApiException;

@Service
public class TaobaoFinalSaveService {
	protected static Logger logger = Logger.getLogger("TaobaoFinalSaveService");

	@Autowired
	private AlbumService albumService;// 相册

	@Autowired
	private OwnCatInfoDao ownCatInfoDao;

	@Autowired
	private WuliuService wuliuService; // 物流

	@Autowired
	private GoodsService goodsService;

	@Autowired
	private AliCatItemAutoUtil aliCatItemAutoUtil;

	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	@Autowired
	private TaobaoParseService taobaoParseService;
	
	@Autowired
	private ImageSaveForLocalUtil imageSaveForDubboUtil;

	private static ThreadLocal<TaobaoSaveBean> params = new ThreadLocal<TaobaoSaveBean>();
	
	@Autowired
	private AliConstant aliConstant;

	@SuppressWarnings("unused")
	public Offer save(String url,String categoryId, boolean isSavaPic, MqRecordItem mqItem,Map<String,Object> selfInfo)
			throws IOException, AliReqException, ApiException, InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		// 准备数据
		
		TaobaoSaveBean bean = new TaobaoSaveBean();
		params.set(bean);
		bean.setParams(taobaoParseService.parseDataByUrl(url));

		bean.setUrl(url);
		bean.setPicSave(isSavaPic);// 设置是否需要保存图片.
		bean.setSelfInfo(selfInfo);
		Offer offer = new Offer();
		bean.setOffer(offer);
		String subject = getTitle(selfInfo);
		offer.setSubject(subject);//设置标题
		
		// 获得相册(获得相册列表默认是按照时间降序排列的)
		Album album = getAlbum();
		bean.setAlbum(album);

		
		offer.setSkuTradeSupport(Boolean.TRUE);
		offer.setSupportOnlineTrade(Boolean.TRUE);
		
		bean.setCatsId(Integer.valueOf(categoryId));
		offer.setCategoryID(Integer.valueOf(categoryId));// 设置分类

		
		
		//重复判断
		boolean ignoreType=(boolean) selfInfo.get("ignoreType");
		String ignoreTypeVal=(String) selfInfo.get("ignoreTypeVal");
		if(ignoreType&&ignoreTypeVal.equals("T")){
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("q", subject);
			params.put("pageNo",1);
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
		/*Element ele = doc.select(".parcel-unit-weight .value").first();
		if (ele != null) {
			offer.setOfferWeight(ele.text().trim());
		} else {
			
		}*/
		if(selfInfo.containsKey("offerWeight")){
			offer.setOfferWeight(selfInfo.get("offerWeight").toString());
		}else{
			offer.setOfferWeight("1");
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
			List<DeliveryTemplateDescn> wuliList = wuliuService.getAllDeliveryTemplateDescn();
			if (wuliList.size() > 0) {
				offer.setFreightType("T");
				offer.setFreightTemplateId(wuliList.get(0).getTemplateId());
			}
		}
		if(selfInfo.containsKey("freightTemplateId")){
			offer.setFreightTemplateId(Long.valueOf(selfInfo.get("freightTemplateId").toString()));
		}
		// 价格范围
		savePriceRange();
		
		
		//发货地址
		if(selfInfo.containsKey("sendGoodsAddressId")){
			String sendGoodsAddressId=selfInfo.get("sendGoodsAddressId").toString();
			offer.setSendGoodsAddressId(sendGoodsAddressId);
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
		
		
		if(selfInfo.containsKey("offerUnit")){
			offer.getProductFeatures().put("1459", selfInfo.get("offerUnit").toString());
		}
		
		if(offer.getPriceRanges()==null&&offer.getSkuList()!=null&&offer.getSkuList().size()==0){
			offer.setPriceRanges("3:"+bean.getParams().get("price").toString());
			offer.setAmountOnSale(9999);
		}else if(offer.getPriceRanges()!=null){
			String pricerangs=offer.getPriceRanges();
		}
		
		// 设置
		String offstr = JacksonJsonMapper.getInstance().writeValueAsString(offer);
		// 设置单位
		/*offstr = "{\"1459\":\"" + detailConfig.get("unit") + "\","
				+ offstr.substring(1, offstr.length());
		*/
		System.out.println(offstr);
		Long id = goodsService.newOffer(offstr);
		//设置是否默认下架
		if(selfInfo.containsKey("expireed")){
			Thread.sleep(500);
			boolean expireed=(boolean) selfInfo.get("expireed");
			if(expireed) goodsService.expire(id.toString());
		}
		offer.setOfferId(id);
		return offer;
	}

	/**
	 * 设置详情
	 * @param selfInfo
	 * @return
	 * @throws IOException
	 * @throws ApiException
	 * @throws AliReqException
	 */
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
		
		//字数50000限制
		if(content.length()>50000){
			content=content.substring(0, 50000);
		}
		return content;
	}
	/**
	 * 获得标题
	 * 
	 * @return
	 */
	private String getTitle(Map<String,Object> selfInfo) {
		TaobaoSaveBean bean = params.get();
		String subject = bean.getParams().get("subject").toString();
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
	 * 设置价格.
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	private void savePriceRange() throws JsonParseException,JsonMappingException, IOException {
		TaobaoSaveBean bean = params.get();
		Offer offer=bean.getOffer();
		Integer amountOnSale=0;
		if(!offer.getSkuTradeSupport()){ //没有什么用的,未来要舍弃
			if(offer.getSkuList()!=null)offer.setSkuList(null);
			if(offer.getAmountOnSale()==null)offer.setAmountOnSale(9999);
		}
		
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
	 * 填写详细信息
	 * 
	 * @throws IOException
	 * @throws AliReqException
	 * @throws ApiException
	 */
	private String setDescInfo(String otherHref) throws IOException, ApiException,
			AliReqException {
		TaobaoSaveBean bean = params.get();
		String str=bean.getParams().get("desc").toString();
		Document doc=Jsoup.parse(str);
		Elements images=doc.select("img[src]");
		if("C".equals(otherHref)){
			doc.select("a[href]").removeAttr("href");
			doc.select("area[href]").removeAttr("href");
		}else if("Q".equals(otherHref)){
			doc.select("table").remove();
		}
		str=doc.body().html();
		if(bean.isPicSave()){
			List<String> romteList=new ArrayList<String>();
			for(Element ele:images){
				String imageUrl=ele.attr("src");
				if(imageUrl.endsWith("_.webp")){
					imageUrl=imageUrl.substring(0, imageUrl.length()-6);
				}
				if(imageUrl.matches("(?i).+?\\.(jpg|jpeg|bmp|png)")){
					romteList.add(imageUrl);
				}else{
					continue;
				}
			}
			OrderUtil.removeDuplicateWithOrder(romteList);
			for (int i = 0; i < romteList.size(); i++) {
				String imageUrl = romteList.get(i);
				Map<String,Object> retMap=imageSaveForDubboUtil.saveImage(imageUrl, bean.getAlbum().getId(), bean.getImagePrefix()+"_详情", "");
				if(!retMap.containsKey("error")){
					String newUrl = (String) retMap.get("newUrl");
					if(!StringUtils.isEmpty(newUrl)){
						str = str.replaceAll(imageUrl, newUrl);
					}
				}
			}
			
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
		TaobaoSaveBean bean = params.get();
		// 主图
		List<ImageVoBean> imaglist = (List<ImageVoBean>) bean.getParams().get("zhutu");
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
		
		//if(imaglist.size()>3)imaglist=imaglist.subList(0, 3);
		List<String> imageList = new ArrayList<String>();

		Map<String, String> imageMap = new HashMap<String, String>();
		for (int i = 0; i < saveImagelist.size(); i++) {
			String imageUrl = saveImagelist.get(i).getUrl();
			Map<String,Object> retMap=imageSaveForDubboUtil.saveImage(imageUrl, bean.getAlbum().getId(), bean.getImagePrefix()+"_详情", "");
			if(!retMap.containsKey("error")){
				String lastImageUrl=(String) retMap.get("newUrl");
				imageMap.put(imageUrl, lastImageUrl);
				imageList.add(lastImageUrl);
			}
		}
		bean.getOffer().setImageUriList(imageList);
	}

	private void setCatProperties() throws JsonParseException,
			JsonMappingException, IOException, ApiException, AliReqException {
			Map<String, String> attributesMap = (Map<String, String>) params.get().getParams().get("attributesMap"); // 基本属性Map
			
			
			Map<String,Object> extraPro=(Map<String, Object>) params.get().getSelfInfo().get("extra");
			//BeanUtils.copyProperties(attributesMap,extraPro);
			for(String key:extraPro.keySet()){
				attributesMap.put(key, extraPro.get(key).toString());
			}
			
			deal(attributesMap);

	}

	@SuppressWarnings({ "unchecked", "unused" })
	private void deal(Map<String, String> attributesMap)throws JsonParseException, JsonMappingException, IOException,ApiException, AliReqException {
		TaobaoSaveBean bean = params.get();
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
		for (Map<String, Object> postatrribute : productFeatureList) {

			if (postatrribute.get("isSpecAttr").toString().equals("true")) {
				specAttrMap.put(postatrribute.get("name").toString(),postatrribute);
			} else {
				baseMap.put(postatrribute.get("name").toString(), postatrribute);
			}

		}

		// 给productFeatures赋基本属性值.

		for (String basekey : baseMap.keySet()) {
			Map<String, Object> productFeature = baseMap.get(basekey);

			for (String key : attributesMap.keySet()) {
				String value = attributesMap.get(key);
				if(key.equals("是否进口")){
					System.out.println(1);
				}
				if (productFeature.get("name").toString().equals(key)) {
					String fid = productFeature.get("fid").toString();
					String fieldType = productFeature.get("fieldType").toString();
					String inputType = productFeature.get("inputType").toString();
					String unit = (String) productFeature.get("unit");
					if (StringUtils.isNotEmpty(unit)) {
						String delstr = "（" + unit + "）";
						if (value.indexOf(delstr) != -1) {
							value = value.substring(0,
									value.length() - delstr.length());
						}else if(value.endsWith(unit)){
							value =value.substring(0,
									value.length() - unit.length());
						}

					}

					String pathValues = "";
					if (fieldType.equals("string")) {// 文本框
						if (productFeature.get("name").toString().equals("建议零售价")){
							value = value.substring(1);
							productFeatures.put("8021", value);
						}else{
							productFeatures.put(fid, value);
						}
					}else if (fieldType.equals("enum")&& inputType.equals("2")) { // 多选框
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
								if(fid.equals("42842855")){
									System.out.println(1);
								}
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
		for (String key : baseMap.keySet()) {
			Map<String, Object> postatrribute = baseMap.get(key);
			if (postatrribute.get("isNeeded").toString().equals("Y")&& !productFeatures.containsKey(postatrribute.get("fid").toString())) {
				String fieldType=postatrribute.get("fieldType").toString();
				String fid=postatrribute.get("fid").toString();
				if(fieldType.equals("string")){
					productFeatures.put(fid, "12");
				}else if(fieldType.equals("int")){
					productFeatures.put(fid, "12");
				}else if(fieldType.equals("enum")){
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
		productFeatures.put("3939", "3");
		offer.setProductFeatures(productFeatures);

		// 给productFeatures赋产品规格值
		//skuPics设置.
		setskuPics(specAttrMap);
		
		
		List<Map<String,Object>> skuList=new ArrayList<Map<String,Object>>();//添加到offer的skuList
		offer.setSkuList(skuList);
		
		Map<String,Object> taobaoParmas=params.get().getParams();
		List<Map<String,Object>> taobaoskuList=(List<Map<String, Object>>) taobaoParmas.get("skuList");
		
		TopToAliIStrategy strategy=TopStrategyFactory.getInstance(taobaoskuList.size(), specAttrMap.size());
		TopToAliContext context=new TopToAliContext(strategy);
		
		if(context!=null)context.operate(specAttrMap, taobaoParmas, taobaoskuList,offer);
		
	}

	
	/**
	 * //skuPics设置.
	 * @param specAttrMap
	 * @throws AliReqException 
	 * @throws ApiException 
	 */
	private void setskuPics(Map<String, Map<String, Object>> specAttrMap) throws ApiException, AliReqException{
		TaobaoSaveBean bean=params.get();
		Map<String,Object> taobaoParmas=params.get().getParams();
		List<Map<String,Object>> taobaoskuList=(List<Map<String, Object>>) taobaoParmas.get("skuList");
		Map<String,Object> alispecAttrMapisSpecPicAttrObj=null;
		Map<String,Object> taobaospecAttrMapisSpecPicAttrObj=null;
		for(String specAttrName:specAttrMap.keySet()){
			Map<String,Object> specAttrObj=specAttrMap.get(specAttrName);
			boolean isSpecPicAttr=(boolean) specAttrObj.get("isSpecPicAttr");
			if(isSpecPicAttr==true){
				alispecAttrMapisSpecPicAttrObj=specAttrObj;
			}
		}
		for(Map<String,Object> taobaospecAttrName:taobaoskuList){
			if(taobaospecAttrName.containsKey("isSpecPicAttr")){
				boolean isSpecPicAttr=(boolean) taobaospecAttrName.get("isSpecPicAttr");
				if(isSpecPicAttr==true){
					taobaospecAttrMapisSpecPicAttrObj=taobaospecAttrName;
				}
			}
		}
		if(alispecAttrMapisSpecPicAttrObj!=null&&taobaospecAttrMapisSpecPicAttrObj!=null){
			taobaospecAttrMapisSpecPicAttrObj.put("fid", alispecAttrMapisSpecPicAttrObj.get("fid"));
			Map<String,Object> skuPics=new HashMap<String,Object>();
			List<Map<String,String>> skuPicsList=new ArrayList<Map<String,String>>();
			List<Map<String,Object>> childslist=(List<Map<String, Object>>) taobaospecAttrMapisSpecPicAttrObj.get("childs");
			for(Map<String, Object> sku:childslist){
				Map<String,String> savesku=new HashMap<String,String>();
				Object imageUrl = sku.get("imageUrl");
				if(imageUrl==null)continue;
				Map<String,Object> remoteMap=imageSaveForDubboUtil.saveImage(imageUrl.toString(), bean.getAlbum().getId(), bean.getImagePrefix()+"_销属", "");
				String lastImageUrl = (String) remoteMap.get("newUrl");
				savesku.put(sku.get("value").toString(),lastImageUrl);
				skuPicsList.add(savesku);
			}
			skuPics.put(alispecAttrMapisSpecPicAttrObj.get("fid").toString(), skuPicsList);
			bean.getOffer().setSkuPics(skuPics);
		}
	}
	@SuppressWarnings("unchecked")
	private void diedai(String catId, Map<String, Object> productFeature,
			String value, String fid, Map<String, String> attributesMap,
			Map<String, String> saveMap, String pathValues)
			throws JsonParseException, JsonMappingException, IOException {
		List<Integer> childrenFids = (List<Integer>) productFeature
				.get("childrenFids");
		List<Map<String, Object>> featureIdValues = (List<Map<String, Object>>) productFeature.get("featureIdValues");
		if (childrenFids.size() > 0) {
			for (Map<String, Object> featureIdValue : featureIdValues) {
				if (featureIdValue.get("value").toString().equals(value)) {// 看是否有子项
					String vid = featureIdValue.get("vid").toString();
					if ("".equals(pathValues)) {
						pathValues += fid + ":" + vid;
					} else {
						pathValues += "%3E" + fid + ":" + vid;
					}
					List<Map<String, Object>> data = aliCatItemAutoUtil
							.getItem(catId, pathValues);
					for (Map<String, Object> dataobj : data) {
						
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
								
								saveMap.put(dataobj.get("fid").toString(),attributesMap.get(key));
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
	 * 获取要保存图片的相册.
	 * 
	 * @return
	 * @throws AliReqException
	 */
	private Album getAlbum() throws AliReqException {
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
		if(selfInfo.containsKey("newAlbum")){
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
			if (al.getName().indexOf("一键淘宝复制_")>-1) {
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
			album = albumService.createAlbum("一键淘宝复制_"+DateUtil.getStringDateShort());
		}
		return album;
	}


	@SuppressWarnings("unchecked")
	private String saveImage(String albumId, int i, String imageUrl,String prefix, String image_prefix, boolean isSavaPic)throws ApiException, AliReqException,BusinessException {
		if (!isSavaPic)return imageUrl;
		if(imageUrl.endsWith("startFlag.gif")||imageUrl.endsWith("endFlag.gif"))return imageUrl;
		byte[] imgByte = ImageUtil.readUrlImage(imageUrl);
		if (imgByte == null || imgByte.length == 0){
			throw new BusinessException(ErrorMessages.image_url_not_exit,"图片地址不存在url:" + imageUrl);
		}
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
	public static void main(String[] args) {
		String str="T恤短袖男2016夏装新款黑色圆领修身韩版t血恤男士夏季上衣包邮潮";
		System.out.println(CustomerStringUtil.getLength(str));
	}
}
