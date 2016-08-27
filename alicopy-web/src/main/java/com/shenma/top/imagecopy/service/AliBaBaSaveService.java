package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.cate.Postatrribute;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.entity.wuliu.DeliveryTemplateDescn;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.CateService;
import com.shenma.aliutil.service.GoodsService;
import com.shenma.aliutil.service.WuliuService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.common.util.ImageUtil;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoItemDao;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.exception.BusinessException;
import com.shenma.top.imagecopy.util.exception.ErrorMessages;
import com.shenma.top.imagecopy.util.prase.AliBaBaImageParse;
import com.taobao.api.ApiException;

@Service
public class AliBaBaSaveService {
	protected static Logger logger = Logger.getLogger("AliBaBaSaveService");
	@Autowired
	private CateService cateService;
	
	@Autowired
	private GoodsService goodsService;
	@Autowired
    private AlbumService albumService;
	
	@Autowired
    private WuliuService wuliuService;
	
	@Autowired
    private MqRecordItemDao mqRecordItemDao;
	
	private AliBaBaImageParse pa=new AliBaBaImageParse();
	
	@Autowired
	private OwnCatInfoDao ownCatInfoDao;
	
	
	@Autowired
	private OwnCatInfoItemDao catInfoItemDao;

	@Autowired
	private AliCatItemAutoUtil aliCatItemAutoUtil;
	
	@Autowired
	private AliConstant aliConstant;
	
	/**
	 * @param url
	 * @throws IOException
	 * @throws AliReqException
	 * @throws ApiException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws NoSuchMethodException 
	 * @throws InterruptedException 
	 */

	@SuppressWarnings("unchecked")
	public Offer save(String url,boolean isSavaPic) throws IOException, AliReqException, ApiException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InterruptedException{
		Document doc=JsonpUtil.getAliDefaultConnet(url);
		Map<String,Object> detailConfig=getIDetailConfig(doc.html());
		Map<String,Object> detailData=getIDetailData(doc.html(),doc);
		//获得相册
		List<Album> albumlist=albumService.getAllAlbumList();
		Album album=null;
		for(Album al:albumlist){
			if(al.getName().equals("一键复制")){
				album=al;
			}
		}
		if(album==null){
			album=albumService.createAlbum("一键复制");
		}
		
		Offer offer=new Offer();
		String categoryId=detailConfig.get("catid").toString();
		offer.setCategoryID(Integer.valueOf(categoryId));
		//System.out.println(doc.html());
		String subject=doc.select("h1.d-title").first().text();
		offer.setSubject(subject);//标题
		String image_prefix=null;
		if(subject.length()>23){
			image_prefix=subject.substring(0,23);
		}else{
			image_prefix=subject;
		}
		//基本
		setBaseInfo(categoryId,offer,detailData,doc,album,image_prefix,isSavaPic);
		
		
		Element ele=doc.select(".parcel-unit-weight .value").first();
		/*if(ele!=null){
			offer.setOfferWeight(ele.text().trim());
		}else{
			offer.setOfferWeight("1");
		}*/
		
		//主图
		saveZhutu(doc,url,album,image_prefix,offer,isSavaPic,offer.getSubject());
		
		//详情
		String content=setDescInfo(doc,url,album,image_prefix,isSavaPic,offer.getSubject());
		offer.setOfferDetail(content); 
		
		//物流运费信息
		List<DeliveryTemplateDescn> wuliList=wuliuService.getAllDeliveryTemplateDescn();
		if(wuliList.size()>0){
			offer.setFreightType("T");
			offer.setFreightTemplateId(wuliList.get(0).getTemplateId());
		}
		
		//价格范围

		if(detailData!=null){
			List<List<Object>> priceRange=(List<List<Object>>) detailData.get("priceRangeOriginal");
			if(priceRange!=null){
				offer.setSkuTradeSupport(Boolean.FALSE);
				String priceRangeStr="";
				for(List<Object> pricelist:priceRange){
					priceRangeStr+=pricelist.get(0).toString()+":"+pricelist.get(1).toString()+"`";
				}
				if(priceRangeStr.equals("")){
					priceRangeStr=null;
				}else{
					priceRangeStr=priceRangeStr.substring(0,priceRangeStr.length()-1);
				}
				offer.setPriceRanges(priceRangeStr);
			}
		}else{
			Elements modDetailPrices=doc.select("#mod-detail-price tr.price td[data-range]");
			String priceRangeStr="";
			for(Element mod:modDetailPrices){
				String dataRange=mod.attr("data-range");
				Map<String,Object> map=JacksonJsonMapper.getInstance().readValue(dataRange, HashMap.class);
				String begin=(String) map.get("begin");
				String end=(String) map.get("end");
				String price=(String) map.get("price");
				priceRangeStr+=begin+":"+price+"`";
			}
			if(priceRangeStr.equals("")){
				priceRangeStr=null;
			}else{
				priceRangeStr=priceRangeStr.substring(0,priceRangeStr.length()-1);
			}
			offer.setPriceRanges(priceRangeStr);
			
			Element amountOnSale=doc.select("div.mod-detail-purchasing.mod-detail-purchasing-single[data-mod-config]").first();
			if(amountOnSale!=null){
				String dataconfig=amountOnSale.attr("data-mod-config");
				Map<String,Object> dataMap=JacksonJsonMapper.getInstance().readValue(dataconfig, HashMap.class);
				Integer amount=Integer.valueOf(dataMap.get("max").toString());
				offer.setAmountOnSale(amount);
			}
		}
		//是否支持网上交易
		offer.setSupportOnlineTrade(Boolean.TRUE);
		//设置
		String offstr=JacksonJsonMapper.getInstance().writeValueAsString(offer);
		offstr="{\"1459\":\""+detailConfig.get("unit")+"\","+offstr.substring(1, offstr.length());
		Long id=goodsService.newOffer(offstr);
		offer.setOfferId(id);
		return offer;
		
		/*
		//可以卖的数量
		if(skuData.containsKey("canBookCount")){
			Integer amountOnSale=(Integer) skuData.get("canBookCount");
			if(amountOnSale!=null)offer.setAmountOnSale(amountOnSale);
		}
		
		/*Map<String,Object> result=(Map<String,Object>)ret.get("result");
    	List<Map<String,Object>> toReturnList=(List<Map<String,Object>>)result.get("toReturn");
		
    	Map<String,Map<String,Object>> listmap=new HashMap<String, Map<String,Object>>(); 
    	for(Map<String,Object> obj:toReturnList){
    		//listmap
    	}*/
		
		//skuMap=HttpClientAliUtil.getSkuInfo(detailConfig.get("parentdcatid").toString(), detailConfig.get("catid").toString());
	}
	private void saveZhutu(Document doc,String url,Album album,String image_prefix,Offer offer,boolean isSavaPic,String title) throws JsonParseException, JsonMappingException,
			IOException, ApiException, AliReqException {
		//主图
		List<ImageVoBean> imaglist=pa.genZhutuImage(doc,url,title);
		List<String> imageList=new ArrayList<String>();
		
		Map<String,String> imageMap=new HashMap<String, String>();
		for(int i=0;i<imaglist.size();i++){
			String imageUrl=imaglist.get(i).getUrl();
			String lastImageUrl = saveImage(album.getId().toString(), i, imageUrl,"主图",image_prefix,isSavaPic);
			imageMap.put(imageUrl, lastImageUrl);
			imageList.add(lastImageUrl);
		}
		offer.setImageUriList(imageList);
	}
	@SuppressWarnings("unchecked")
	private String saveImage(String albumId, int i, String imageUrl,String prefix,String image_prefix,boolean isSavaPic)
			throws ApiException, AliReqException {
		if(!isSavaPic)return imageUrl;
		byte[] imgByte=ImageUtil.readUrlImage(imageUrl);
		if(imgByte==null||imgByte.length==0)throw new BusinessException(ErrorMessages.image_url_not_exit, "图片地址不存在url:"+imageUrl);
		long maxSize=(2<<16)*30;
		if(imgByte.length>maxSize){
			throw new ApiException("文件超过3M");
		}
		Map<String,Object> bean=albumService.uploadImage(albumId, image_prefix+"_"+prefix+"_"+(i+1), null, imgByte);
		if(bean.containsKey("code")){
			List<String> codelist=(List<String>) bean.get("code");
			if(codelist!=null&&codelist.size()>0&&codelist.get(0).equals("120007")){
				throw new BusinessException(ErrorMessages.album_space_full, "图片空间已满!请先删除!");
			}
		}
		Map<String,Object> result=(Map<String, Object>) bean.get("result");
		List<Map<String,Object>> rtlist=(List<Map<String, Object>>) result.get("toReturn");
		String lastImageUrl=(String) rtlist.get(0).get("url");
		lastImageUrl=aliConstant.image_uri_prefix+lastImageUrl;
		return lastImageUrl;
	}
	@SuppressWarnings("unchecked")
	private Map<String,Object> getIDetailConfig(String html) throws JsonParseException, JsonMappingException, IOException{
		Pattern pattern = Pattern.compile("(?<=iDetailConfig = )[\\s\\S]*?\\}\\;");
		Matcher matcher = pattern.matcher(html);
		String str="";
		if(matcher.find()){
			str=matcher.group(0);
		}
		str=str.replaceAll("\\n", "");
		str=str.replaceAll("'", "\"");
		str=str.replaceAll(" ", "");
		HashMap<String, Object> ret=null;
		if("{}".equals(str)){
			return null;
		}else{
			ret=JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	private Map<String,Object> getIDetailData(String html,Document doc) throws JsonParseException, JsonMappingException, IOException{
		Pattern pattern = Pattern.compile("(?<=iDetailData = )[\\s\\S]*?(?=\\}\\;)");
		Matcher matcher = pattern.matcher(doc.html());
		String str="";
		if(matcher.find()){
			str=matcher.group(0);
		}
		str+="}";
		str=str.replaceAll("\\n", "");
		str=str.replaceAll("\\t", "");
		str=str.replaceAll("&gt;", ">");
		str=str.replaceAll("'", "\"");
		str=str.replaceAll(" ", "");
		HashMap<String, Object> ret=null;
		if("{}".equals(str)){
			return null;
		}else{
			ret=JacksonJsonMapper.getInstance().readValue(str, HashMap.class);
		}
		ret=(HashMap<String, Object>) ret.get("sku");
		return ret;
	}
	public static void main(String[] args) throws IOException, AliReqException, ApiException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		AliBaBaSaveService l=new AliBaBaSaveService();
		//l.save("http://detail.1688.com/offer/42191930890.html");
	}
	
	/**
	 * 填写基本信息
	 * @throws AliReqException 
	 * @throws ApiException 
	 */
	@SuppressWarnings("unchecked")
	private void setBaseInfo(String cateId,Offer offer,Map<String,Object> detailData,Document doc,Album album,String image_prefix,boolean isSavaPic) throws AliReqException, ApiException{
		//产品属性
		Page<Postatrribute> page=cateService.offerPostFeaturesGet(cateId);
		Map<String,Postatrribute> allperopertiesMap=new HashMap<String, Postatrribute>(); //产品属性
		Map<String,Postatrribute> peropertiesMap=new HashMap<String, Postatrribute>(); //产品属性
		Map<String,Postatrribute> guigetiesMap=new HashMap<String, Postatrribute>();//规格属性
		for(Postatrribute postatrribute:page.getContent()){

			if(postatrribute.getIsSpecAttr()){
				guigetiesMap.put(postatrribute.getName(), postatrribute);
			}else{
				peropertiesMap.put(postatrribute.getName(), postatrribute);
			}
			allperopertiesMap.put(postatrribute.getName(), postatrribute);
		}
		Element attributes=doc.select("#mod-detail-attributes").first();
		Map<String,String> productFeatures=new HashMap<String,String>();
		if(attributes!=null){
		Elements feautres=attributes.select("td.de-feature");

		Elements fvalues=attributes.select("td.de-value");
		if(fvalues.size()>0){
			for(int i=0;i<fvalues.size();i++){
				String name=feautres.get(i).text();
				String fValue=fvalues.get(i).text();
				Postatrribute postatrribute=allperopertiesMap.get(name);
				if(postatrribute!=null){
					if(postatrribute.getShowType().equals("-1")){//数字输入框
						productFeatures.put(postatrribute.getFid().toString(), fValue);
						
					}else if(postatrribute.getShowType().equals("0")){// 0: 文本输入框（input）
						if(name.equals("建议零售价"))fValue=fValue.substring(1);
						productFeatures.put(postatrribute.getFid().toString(), fValue);
					}else if(postatrribute.getShowType().equals("1")){//1=下拉（list_box）
						productFeatures.put(postatrribute.getFid().toString(), fValue);
					}else if(postatrribute.getShowType().equals("2")){//多选（check_box）
						String v=fValue.replaceAll(",", "|").trim();
						productFeatures.put(postatrribute.getFid().toString(), v);
					}else if(postatrribute.getShowType().equals("3")){//单选（radio）
						productFeatures.put(postatrribute.getFid().toString(), fValue);
					} 
				}
				
			}
		}else{
			for(Element feautre:feautres){
				String nameValue=feautre.text();
				if(!StringUtils.isEmpty(nameValue)){
					String[] nameValueArr=nameValue.split("：");
					String name=nameValueArr[0].trim();
					String fValue=nameValueArr[1].trim();
					
					
					Postatrribute postatrribute=allperopertiesMap.get(name);
					if(postatrribute!=null){
						if(postatrribute.getShowType().equals("-1")){//数字输入框
							productFeatures.put(postatrribute.getFid().toString(), fValue);
							
						}else if(postatrribute.getShowType().equals("0")){// 0: 文本输入框（input）
							if(name.equals("建议零售价"))fValue=fValue.substring(1);
							productFeatures.put(postatrribute.getFid().toString(), fValue);
						}else if(postatrribute.getShowType().equals("1")){//1=下拉（list_box）
							productFeatures.put(postatrribute.getFid().toString(), fValue);
						}else if(postatrribute.getShowType().equals("2")){//多选（check_box）
							String v=fValue.replaceAll(",", "|").trim();
							productFeatures.put(postatrribute.getFid().toString(), v);
						}else if(postatrribute.getShowType().equals("3")){//单选（radio）
							productFeatures.put(postatrribute.getFid().toString(), fValue);
						} 
					}
					
					
					
					
					
				}
			}
		}
		for(Postatrribute postatrribute:page.getContent()){
			if(postatrribute.getIsNeeded().equals("Y")&&!productFeatures.containsKey(postatrribute.getFid())){
				if(postatrribute.getFeatureIdValues()!=null&&postatrribute.getFeatureIdValues().size()>0){
					productFeatures.put(postatrribute.getFid().toString(), postatrribute.getFeatureIdValues().get(0).getValue());
				}
			}
		}
		offer.setProductFeatures(productFeatures);
		
		}
		
		
		//产品规格,
		Elements elements=doc.select("div.obj-header span.obj-title");
		if(elements.size()>0){
		List<String> skuPros=new ArrayList<String>();
		for(Element ele:elements){
			skuPros.add(ele.text());
		}
		List<Map<String,Object>> skuList=new ArrayList<Map<String,Object>>();

		if(detailData.containsKey("skuMap")){
			offer.setSkuTradeSupport(Boolean.TRUE);
			Map<String,Object> skuMap=(Map<String, Object>) detailData.get("skuMap");
			for(String key:skuMap.keySet()){
				Map<String,Object> obj=new HashMap<String, Object>();
				String[] strs=key.split(">");
				Map<String,String> specAttributes=new HashMap<String, String>();
				obj.put("specAttributes", specAttributes);

				for(int i=0;i<strs.length;i++){
					String str=strs[i];
					String fid=skuPros.get(i);
					Postatrribute p=guigetiesMap.get(fid);
					if(p==null){
						if(fid.equals("颜色")){
							specAttributes.put("3216",str);
						}else if(fid.equals("尺码")){
							specAttributes.put("450",str);
						}
					}else{
						specAttributes.put(guigetiesMap.get(skuPros.get(i)).getFid().toString(),str);
					}
				
				}
				Map<String,Map<String,Object>> value=(Map<String, Map<String, Object>>) skuMap.get(key);
				obj.put("amountOnSale", value.get("canBookCount"));
				obj.put("price", value.get("price"));
				obj.put("retailPrice", value.get("retailPrice"));
				obj.put("cargoNumber", value.get("cargoNumber"));
				skuList.add(obj);
			}
		}
		offer.setSkuList(skuList);
		}
		
		//匹配图片
		if(detailData!=null&&detailData.containsKey("skuProps")){
			Map<String, Object> skuPics=new HashMap<String,Object>();
			List<Map<String,Object>> colorlist=new ArrayList<Map<String,Object>>();
			skuPics.put("3216", colorlist);
			offer.setSkuPics(skuPics);
			List<Map<String,Object>> skuPropsList=(List<Map<String, Object>>) detailData.get("skuProps");
			
			for(Map<String,Object> skuProps:skuPropsList){
				String prop=(String) skuProps.get("prop");
				Postatrribute posta=guigetiesMap.get(prop);
				String fid=null;
				if(posta!=null){
					fid=posta.getFid().toString();
				}else{
					if("颜色".equals(prop)){
						fid="3216";
					}else if(prop.equals("尺码")){
						fid="450";
					}
				}
				
				List<Map<String,Object>> valueList=(List<Map<String, Object>>) skuProps.get("value");
				Map<String,String> tempskuMap=new HashMap<String,String>();
				for(Map<String,Object> valueListObj:valueList){
					String oldValue=tempskuMap.get(fid)==null?"":tempskuMap.get(fid);
					tempskuMap.put(fid, oldValue+valueListObj.get("name").toString()+"|");
				}
				productFeatures.put(fid, tempskuMap.get(fid).substring(0, tempskuMap.get(fid).length()-1));
				
				if("颜色".equals(prop)){
					int i=0;
					for(Map<String,Object> valueListObj:valueList){
						Map<String,Object> obj=new HashMap<String, Object>(1);
						String color=(String) valueListObj.get("name");
						String imageUrl=(String) valueListObj.get("imageUrl");
						if(imageUrl!=null){
							imageUrl=saveImage(album.getId().toString(), i, imageUrl, "颜色",image_prefix,isSavaPic);
							obj.put(color, imageUrl);
						}
						colorlist.add(obj);
						i++;
					}
				}
				
			}
		}
		
	}
	
	
	
	/**
	 * 填写详细信息
	 * @throws IOException 
	 * @throws AliReqException 
	 * @throws ApiException 
	 */
	private String setDescInfo(Document doc,String url,Album album,String image_prefix,boolean isSavaPic,String title) throws IOException, ApiException, AliReqException{

		Element ele=doc.select("#de-description-detail").first();
		String str=ele.html();
		System.out.println(doc.html());
		List<ImageVoBean> imaglist=pa.genXiangqinImage(doc, url,title);
		
		Map<String,String> imageMap=new HashMap<String, String>();
		for(int i=0;i<imaglist.size();i++){
			String imageUrl=imaglist.get(i).getUrl();
			String lastImageUrl =null;
			try {
				lastImageUrl = saveImage(album.getId().toString(), i, imageUrl,"详情",image_prefix,isSavaPic);
			} catch (BusinessException e) {
				logger.error("请求图片地址出错:"+imageUrl);
				continue;
			}
			imageMap.put(imageUrl, lastImageUrl);
		}
		
		for(String key:imageMap.keySet()){
			str=str.replaceAll(key, imageMap.get(key));
		}
		
		
		return str;
	}
	
	public Page<MqRecordItem> MqRecordItemList(int page,int pageSize,final String memberId,final Integer status){
		page=page-1;
		Pageable pageable = new PageRequest(page, pageSize, new Sort(Direction.DESC, new String[]{"id"}));
		Page<MqRecordItem> retpage=mqRecordItemDao.findAll(new Specification<MqRecordItem>() {
			
			@Override
			public Predicate toPredicate(Root<MqRecordItem> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Path<String> userId = root.get("userId");
				Path<Integer> delStatus = root.get("delStatus");
				Path<Integer> statusPath = root.get("status");
				if(status!=-1){
					query.where(cb.equal(userId, memberId),cb.equal(delStatus, 0),cb.equal(statusPath, status));
				}else{
					query.where(cb.equal(userId, memberId),cb.equal(delStatus, 0));
				}
				return null;
			}
		}, pageable);

		return retpage;
	}
	
	public void delHistory(List<Integer> ids){
		String memberId=SessionUtil.getAliSession().getMemberId();
		List<MqRecordItem> list=mqRecordItemDao.findAll(ids);
		List<MqRecordItem> copyList=new ArrayList<MqRecordItem>();
		for(MqRecordItem m:list){
			if(!memberId.equals(m.getUserId()))throw new BusinessException("没有权限删除该记录");
			if(m.getStatus()==2){
				m.setDelStatus(1);
				mqRecordItemDao.saveAndFlush(m);
			}else{
				copyList.add(m);
			}
			
		}
		mqRecordItemDao.deleteInBatch(copyList);
	}
	
	public void genCatesToDb() throws AliReqException, JsonParseException, JsonMappingException, IOException, InterruptedException{
		
		List<OwnCatInfo> catInfoList=ownCatInfoDao.findAll();
		int index=0;
		for(int i=0;i<catInfoList.size();i++){
			OwnCatInfo catInfo=catInfoList.get(i);
			if(catInfo.getCatsId()==124736022){
				index=5;
			}
			/*if(catInfo.getCatsId()==1033670){
				index=10;
			}*/
			//if(index==5)continue;
			String properties=catInfo.getProperties();
			Map<String,Object> propertiesmap=JacksonJsonMapper.getInstance().readValue(properties, HashMap.class);
			List<Map<String,Object>> productFeatureList=(List<Map<String, Object>>) propertiesmap.get("productFeatureList");
			
			for(Map<String,Object> postatrribute:productFeatureList){
				/*if(postatrribute.get("name").equals("面料主材质分类")){
					System.out.println(1);
				}else{
					continue;
				}*/
				diedai(catInfo.getCatsId().toString(),postatrribute,"");
			}
			
		}
		
	}
	
	/**
	 * 根据catsId保存cateItems到数据库
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * 
	 */
	public void savecateItemBycateId(OwnCatInfo catInfo) throws JsonParseException, JsonMappingException, IOException, InterruptedException{
		String properties=catInfo.getProperties();
		Map<String,Object> propertiesmap=JacksonJsonMapper.getInstance().readValue(properties, HashMap.class);
		List<Map<String,Object>> productFeatureList=(List<Map<String, Object>>) propertiesmap.get("productFeatureList");
		
		for(Map<String,Object> postatrribute:productFeatureList){
			/*if(postatrribute.get("name").equals("面料主材质分类")){
				System.out.println(1);
			}else{
				continue;
			}*/
			diedai(catInfo.getCatsId().toString(),postatrribute,"");
		}
	}
	
	public void genCateOneToDb(Integer catsId){
		//OwnCatInfo catInfo=ownCatInfoDao.findByCatsId(catsId);
		//catInfo.ge
	}
	
	@SuppressWarnings("unchecked")
	private void diedai(String catId,Map<String, Object> productFeature,String pathValues) throws JsonParseException, JsonMappingException,
			IOException, InterruptedException {
		String fid=productFeature.get("fid").toString();
		List<Integer> childrenFids=(List<Integer>) productFeature.get("childrenFids");
		List<Map<String,Object>> featureIdValues=(List<Map<String, Object>>) productFeature.get("featureIdValues");
		if(childrenFids.size()>0){
			String newPathValue="";
			for(Map<String,Object> featureIdValue:featureIdValues){
					String vid=featureIdValue.get("vid").toString();
					if("".equals(pathValues)){
						newPathValue=fid+":"+vid;
					}else{
						newPathValue=pathValues+"%3E"+fid+":"+vid;
					}
					List<Map<String,Object>> data=aliCatItemAutoUtil.getItemByUrl(catId, newPathValue); 

					for(Map<String,Object> dataobj:data){
						List<Integer> list=(List<Integer>) dataobj.get("childrenFids");
						if(list.size()>0)diedai(catId,dataobj,newPathValue);
					}
					
			}
		}
	}
	
}
