package com.bohusoft.alicopy.save;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bohusoft.alicopy.parse.DetailHtmlParseBean;
import com.bohusoft.alicopy.parse.DetailHtmlParseUtil;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.top.imagecopy.dao.OwnCatInfoDao;
import com.shenma.top.imagecopy.dao.OwnCatInfoItemDao;
import com.shenma.top.imagecopy.ecxeption.CopyBussinessException;
import com.shenma.top.imagecopy.entity.OwnCatInfo;
import com.shenma.top.imagecopy.entity.OwnCatInfoItem;
import com.shenma.top.imagecopy.util.JacksonJsonMapper;

@Service
public class OfferPublishService {
	//private static ThreadLocal<SaveTempBean> tempBeanThreadLocal = new ThreadLocal<SaveTempBean>();  
	protected static Logger logger = Logger.getLogger("OfferPublishService");
	
	@Autowired
	private DetailHtmlParseUtil detailHtmlParseUtil;
	
	@Autowired
	private OwnCatInfoDao ownCatInfoDao;
	
	@Autowired
	private OwnCatInfoItemDao ownCatInfoItemDao;
	
	public Offer publish(String url,Map<String,Object> selfInfo) throws CopyBussinessException{
		Offer offer=new Offer();
		try {
		
			offer.setSupportOnlineTrade(Boolean.TRUE);
			
			DetailHtmlParseBean bean=detailHtmlParseUtil.parseByUrl(url);
			
			setSaleInfo(offer, bean);
			
			setSubject(offer, bean);
			
			setZhutu(offer, bean);
			
			setDesc(offer, bean);
			
			setBaseProperties(offer, bean);
		} catch (Exception e) {
			logger.error("保存出错:"+e.getMessage());
		}
		
		return offer;
	}
	
	/**
	 * 设置产品销售信息
	 * @param offer
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void setSaleInfo(Offer offer,DetailHtmlParseBean bean) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		if(bean.getPriceType()==1){ //按数量
			offer.setPriceRanges(bean.getPrice());
			offer.setSkuTradeSupport(false);
			
			
		}else if(bean.getPriceType()==2){ //按规格
			offer.setSkuTradeSupport(true);
			Map<String,String> productFeatures=offer.getProductFeatures();
			String beginAmount=BeanUtils.getProperty(bean, "otherProperties.beginAmount");
			if(beginAmount!=null){  //设置最小起订量
				productFeatures.put("3939", beginAmount);
			}
			
			//设置规格报价
			
		}
		
	}
	
	/**
	 * 设置标题
	 * @param offer
	 * @param bean
	 */
	private void setSubject(Offer offer,DetailHtmlParseBean bean){
		String subject=bean.getSubject();
		subject.substring(0,subject.length()>30?30:subject.length());
	}
	
	/**
	 * 设置主图
	 * @param offer
	 * @param bean
	 */
	private void setZhutu(Offer offer,DetailHtmlParseBean bean){
		List<String> zhutuList=bean.getZhutuList();
		offer.setImageUriList(zhutuList);
	}
	
	/**
	 * 设置详情
	 * @param offer
	 * @param bean
	 */
	private void setDesc(Offer offer,DetailHtmlParseBean bean){
		String desc=bean.getDesc();
		offer.setOfferDetail(desc);
	}
	
	/**
	 * 设置基本属性
	 * @param offer
	 * @param bean
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@SuppressWarnings("unchecked")
	private void setBaseProperties(Offer offer,DetailHtmlParseBean bean) throws JsonParseException, JsonMappingException, IOException{
		Map<String,String> attributesMap=bean.getBaseproperties();   //采集过来的属性，属性值
		OwnCatInfo catInfo = ownCatInfoDao.findByCatsId(bean.getCatsId());
		Map<String,Object> properties=JacksonJsonMapper.getInstance().readValue(catInfo.getProperties(), HashMap.class);
		Map<String, String> productFeatures = offer.getProductFeatures(); // 最后保存到offer的属性.
		
		List<Map<String, Object>> productFeatureList = (List<Map<String, Object>>) properties.get("productFeatureList");
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
						dealSecOrThirdCate(bean.getCatsId(), productFeature,value, attributesMap, productFeatures,pathValues,baseMap,specAttrMap);
					} else if (fieldType.equals("enum")&& inputType.equals("3")) { // 单选框
						value = value.replaceAll(",", "|").trim();
						productFeatures.put(fid, value);
						dealSecOrThirdCate(bean.getCatsId(), productFeature,value, attributesMap, productFeatures,pathValues,baseMap,specAttrMap);
					}  else if (fieldType.equals("enum")&& inputType.equals("1")) { // 下拉框
						productFeatures.put(fid, value);
						
						dealSecOrThirdCate(bean.getCatsId(), productFeature,value, attributesMap, productFeatures,pathValues,baseMap,specAttrMap);
					} else {
						productFeatures.put(fid, value);
					}
				}
			}
		}
		
		//将必填的选择给填上
		baseCloneMap.putAll(baseMap);
		for (String key : baseCloneMap.keySet()) {

			Map<String, Object> postatrribute = baseMap.get(key);
			if (postatrribute.get("isNeeded").toString().equals("Y")&& !productFeatures.containsKey(postatrribute.get("fid").toString())) {
				String fieldType=postatrribute.get("fieldType").toString();
				String fid=postatrribute.get("fid").toString();
				if(fieldType.equals("string")){
					productFeatures.put(fid, "123");
				}else if(fieldType.equals("enum")){
					
					List<Map<String, Object>> items = (List<Map<String, Object>>) postatrribute.get("featureIdValues");
					productFeatures.put(fid, items.get(0).get("value").toString());
				}else{
					productFeatures.put(fid, "12");
				}
				List<Map<String, Object>> items = (List<Map<String, Object>>) postatrribute.get("featureIdValues");
				if (items.size() > 0) {
					String pathValues = "";
					teshudiedai(bean.getCatsId(), postatrribute, productFeatures, pathValues);
				}

			}
		}
		
	}

	/**
	 * @param catsId  类目Id
	 * @param productFeature 产品属性的详细信息
	 * @param value 采集过来的值
	 * @param attributesMap  采集过来所有的值得hashmap
	 * @param saveMap  offer的productFeatures成员变量，最终保存的对象
	 * @param pathValues 根据路径判断是否有子的产品属性。
	 * @param baseMap 基本属性map
	 * @param specAttrMap 规格属性map
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	private void dealSecOrThirdCate(Integer catsId, Map<String, Object> productFeature,
			String value, Map<String, String> attributesMap,
			Map<String, String> saveMap, String pathValues,Map<String, Map<String, Object>> baseMap,Map<String, Map<String, Object>> specAttrMap)
			throws JsonParseException, JsonMappingException, IOException {
		String fid = productFeature.get("fid").toString();
		List<Integer> childrenFids = (List<Integer>) productFeature.get("childrenFids");
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
						
					List<Map<String, Object>> data = null; //获得子类的数据
					OwnCatInfoItem item=ownCatInfoItemDao.findByCatIdAndPathValues(Integer.valueOf(catsId), pathValues);
					HashMap<String, Object> retMap=JacksonJsonMapper.getInstance().readValue(item.getPathValues(), HashMap.class);
					if(retMap==null){
						data=new ArrayList<Map<String,Object>>();
					}else{
						data=(List<Map<String, Object>>) retMap.get("data");
					}
					for (Map<String, Object> dataobj : data) {

						if(dataobj.get("isSpecAttr")!=null&&dataobj.get("isSpecAttr").toString().equals("true")){
							specAttrMap.put(dataobj.get("name").toString(), dataobj);
						}else{
							baseMap.put(dataobj.get("name").toString(), dataobj);
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
								dealSecOrThirdCate(catsId, dataobj, v, attributesMap, saveMap, pathValues,baseMap,specAttrMap);
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
									dealSecOrThirdCate(catsId,dataobj, attributesMap.get(key),attributesMap, saveMap, pathValues,baseMap,specAttrMap);
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
	private void teshudiedai(Integer catsId, Map<String, Object> productFeature,Map<String, String> saveMap, String pathValues)throws JsonParseException, JsonMappingException, IOException {
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
			List<Map<String, Object>> data = null; //获得子类的数据
			OwnCatInfoItem item=ownCatInfoItemDao.findByCatIdAndPathValues(catsId, pathValues);
			HashMap<String, Object> retMap=JacksonJsonMapper.getInstance().readValue(item.getPathValues(), HashMap.class);
			if(retMap==null){
				data=new ArrayList<Map<String,Object>>();
			}else{
				data=(List<Map<String, Object>>) retMap.get("data");
			}
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
						teshudiedai(catsId, dataobj, saveMap, newPathValues);
					}  else {
						saveMap.put(dataobjid, "123");
					}
				} 

			}

		}
	}
	
}
