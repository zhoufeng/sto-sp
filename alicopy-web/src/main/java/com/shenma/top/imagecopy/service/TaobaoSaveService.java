package com.shenma.top.imagecopy.service;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.taobao.service.cat.ItemCatService;
import com.shenma.taobao.service.product.ItemJointService;
import com.shenma.taobao.service.product.ItemService;
import com.shenma.taobao.service.wuliu.LogisticsAddressService;
import com.shenma.top.imagecopy.util.JsonpUtil;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.prase.TopTaobaoImageParse;
import com.shenma.top.imagecopy.util.save.SaveBean;
import com.shenma.top.imagecopy.util.save.SaveBussiness;
import com.shenma.top.imagecopy.util.save.TaobaoSaveAttrBussiness;
import com.shenma.top.imagecopy.util.save.TaobaoSavePicBussiness;
import com.shenma.top.imagecopy.util.save.TaobaoSaveSaleAttrBussiness;
import com.taobao.api.ApiException;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.response.ItemAddResponse;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.LogisticsAddressSearchResponse;
import com.taobao.api.response.PictureUploadResponse;

@Service
@Scope("prototype")
public class TaobaoSaveService extends WebApplicationObjectSupport{
	@Autowired
	private ItemCatService catService;
	
	@Autowired 
	private LogisticsAddressService addressService;
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private ImageCopyService imageCopyService;
	/**
	 * 功能描述:解析淘宝sku
	 * @param url
	 * @param type 1.代码集市, 2代表天猫
	 * @throws IOException
	 * @throws ApiException 
	 * @throws NumberFormatException 
	 * @throws InterruptedException 
	 */
	public ItemAddResponse save(String url) throws IOException, NumberFormatException, ApiException, InterruptedException{
		//url="http://item.taobao.com/item.htm?spm=686.1000925.1000774.13.jKBOUi&id=40470421302";
		ItemAddRequest topitem=new ItemAddRequest();
		Document document=JsonpUtil.getAliDefaultConnet(url);
		Element tempele=document.select("#J_Pine").first();
		String cidstr=tempele.attr("data-catid");
		Long cid=new Long(cidstr);
		topitem.setCid(cid);
		ItempropsGetResponse res=catService.findByOne(cid, new Long(1), "");
		LogisticsAddressSearchResponse  AddressRes=addressService.findByOne("get_def", "");
		SaveBean sb=new SaveBean(topitem, res, AddressRes, document);
		
		SaveBussiness saveb=new TaobaoSaveAttrBussiness(sb);
		saveb.option();
		
		SaveBussiness savesb=new TaobaoSaveSaleAttrBussiness(sb);
		savesb.option();
		if(StringUtils.isNotEmpty(topitem.getProps()))topitem.setProps(topitem.getProps().substring(1));
		if(StringUtils.isNotEmpty(topitem.getPropertyAlias()))topitem.setPropertyAlias(topitem.getPropertyAlias().substring(1));
		if(StringUtils.isNotEmpty(topitem.getInputPids()))topitem.setInputPids(topitem.getInputPids().substring(1));
		if(StringUtils.isNotEmpty(topitem.getInputStr()))topitem.setInputStr(topitem.getInputStr().substring(1));
		if(StringUtils.isNotEmpty(topitem.getSkuProperties()))topitem.setSkuProperties(topitem.getSkuProperties().substring(1));
		topitem.setType("fixed"); //一口价
		topitem.setStuffStatus("new"); //新旧程度
		setTitle(topitem,document);
		setDesc(document,topitem);
		setAddress(topitem,AddressRes);
		topitem.setApproveStatus("instock");
		//product_id 天猫
		//setPicPath(document,topitem);//主图地址
		
		setPrice(topitem,document);
		
		String[] skunum=topitem.getSkuProperties().split(",");
		
		StringBuffer skuQuantities=new StringBuffer();
		Long num=0l;
		for(int i=0;i<skunum.length-1;i++){
			skuQuantities.append(",").append(100);
			num+=100;
		}
		
		//主图,详情图片保存
		
		if(StringUtils.isNotEmpty(skuQuantities))topitem.setSkuQuantities(skuQuantities.toString().substring(1));
		topitem.setNum(num);
		
		
		StringBuffer skuprices=new StringBuffer();
		for(int i=0;i<skunum.length-1;i++){
			skuprices.append(",").append(topitem.getPrice());
		}
		if(StringUtils.isNotEmpty(skuprices))topitem.setSkuPrices(skuprices.toString().substring(1));
		
		StringBuffer skuOuterIds=new StringBuffer();
		for(int i=0;i<skunum.length-1;i++){
			skuOuterIds.append(",");
		}
		if(StringUtils.isNotEmpty(skuOuterIds))topitem.setSkuOuterIds(skuOuterIds.toString().substring(1));
		
		
		topitem.setLocationState(topitem.getLocationState().substring(0, topitem.getLocationState().length()-1));
		topitem.setLocationCity(topitem.getLocationCity().substring(0, topitem.getLocationCity().length()-1));
		
		ItemAddResponse response=itemService.add(topitem, "");
		//图片上传
		ImageCopyService imageCopyService=(ImageCopyService)this.getApplicationContext().getBean("imageCopyService");
		ItemJointService itemJointService=(ItemJointService)this.getApplicationContext().getBean("itemJointService");
		TaobaoSavePicBussiness picBussiness=new TaobaoSavePicBussiness(sb, response.getItem().getNumIid(),imageCopyService,itemJointService);
		picBussiness.option();

			return response;
	}
	
	private void setAddress(ItemAddRequest topitem,LogisticsAddressSearchResponse  res) throws ApiException{
		topitem.setLocationState(res.getAddresses().get(0).getProvince());
		topitem.setLocationCity(res.getAddresses().get(0).getCity());
	}
	
	private void setPicPath(Document document,ItemAddRequest topitem){
		TopTaobaoImageParse imageParse=new TopTaobaoImageParse();
		List<ImageVoBean> images=imageParse.genYanseImage(document);
		topitem.setPicPath(images.get(0)==null?"":images.get(0).getUrl());
	}


	private void setDesc(Document document,ItemAddRequest topitem) throws IOException, ApiException, InterruptedException{
		Pattern pattern = Pattern.compile("(?<=g_config.dynamicScript\\(\").*(?=\"\\))");
		Matcher matcher = pattern.matcher(document.html());
		String descriptionurl=null;
		if(matcher.find()){
			descriptionurl=matcher.group(0);
		}
		Document scriptdoc = Jsoup.connect(descriptionurl).get(); 
		String description=scriptdoc.body().html();
		description=description.substring(10, description.length()-2);
		
		TopTaobaoImageParse imageParse=new TopTaobaoImageParse();
		List<ImageVoBean> list=imageParse.genXiangqinImage(document);
		CommonImageService.removeDuplicateWithOrder(list);
		int i=1;
		for(ImageVoBean imageVoBean:list){
			PictureUploadResponse res=imageCopyService.upload(imageVoBean.getUrl(), 0l, "详情_"+topitem.getTitle()+i);
			String url=res.getPicture().getPicturePath();
			description=description.replace(imageVoBean.getUrl(), url);
			i++;
		}
		topitem.setDesc(description);
	}
	
	
	
	/**
	 * 设置模板
	 * @param topitem
	 * @param postageId
	 */
	private void setTemplate(ItemAddRequest topitem,Long postageId){
		topitem.setPostageId(postageId);
	}
	
	
	/**
	 * 设置价格
	 * @param topitem
	 */
	private void setPrice(ItemAddRequest topitem,Document document){
		Element tempele=document.select("#J_StrPrice .tb-rmb-num").first();
		String price=tempele.text();
		topitem.setPrice(price);
	}
	/**
	 * 设置数量
	 */
	private void setNum(ItemAddRequest topitem,Document document){
		Element tempele=document.select("#J_SpanStock").first();
		String num=tempele.text().trim();
		topitem.setNum(Long.valueOf(num));
	}
	/**
	 * 设置标题
	 */
	private void setTitle(ItemAddRequest topitem,Document document){
		Element tempele=document.select("#J_Title .tb-main-title").first();
		String title=tempele.text();
		Element subtitleEle=document.select("#J_Title .tb-subtitle").first();
		
		topitem.setTitle(title);
		if(subtitleEle!=null){
			String sellPoint=subtitleEle.text();
			topitem.setSellPoint(sellPoint);
		}
	}
	public static void main(String[] args) throws IOException, NumberFormatException, ApiException {
		TaobaoSaveService t=new TaobaoSaveService();
		//t.save("http://item.taobao.com/item.htm?spm=686.1000925.1000774.13.jKBOUi&id=40470421302");
		//t.setAttr();
	}
}
