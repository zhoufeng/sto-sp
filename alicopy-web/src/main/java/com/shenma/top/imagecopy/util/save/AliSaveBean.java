package com.shenma.top.imagecopy.util.save;

import java.util.Map;

import org.jsoup.nodes.Document;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.util.prase.AliBaBaImageParse;

/**
 * 保存过程中临时参数bean.
 * @author zhoufeng
 * @ClassName AliSaveBean
 * @Version 1.0.0
 */
public class AliSaveBean {
	private Document document;
	private Album album;
	private String imagePrefix;
	private Integer catsId;
	private Map<String,Object> detailConfig;
	private Map<String,Object> detailData;
	private Offer offer;
	private boolean picSave;
	private AliBaBaImageParse aliBaBaImageParse;
	private String url;//输入的url
	private Map<String,Object> selfInfo;
	private Integer type;  //0是普通  1.火拼   2.大促
	private Map<String, Map<String, Object>> specAttrMap;
	private Map<String, Map<String, Object>> baseMap;
	
	private MqRecordItem mqItem;
	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public String getImagePrefix() {
		return imagePrefix;
	}

	public void setImagePrefix(String imagePrefix) {
		this.imagePrefix = imagePrefix;
	}

	public Integer getCatsId() {
		return catsId;
	}

	public void setCatsId(Integer catsId) {
		this.catsId = catsId;
	}

	public Map<String, Object> getDetailConfig() {
		return detailConfig;
	}

	public void setDetailConfig(Map<String, Object> detailConfig) {
		this.detailConfig = detailConfig;
	}

	public Map<String, Object> getDetailData() {
		return detailData;
	}

	public void setDetailData(Map<String, Object> detailData) {
		this.detailData = detailData;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public boolean isPicSave() {
		return picSave;
	}

	public void setPicSave(boolean picSave) {
		this.picSave = picSave;
	}

	public AliBaBaImageParse getAliBaBaImageParse() {
		return aliBaBaImageParse;
	}

	public void setAliBaBaImageParse(AliBaBaImageParse aliBaBaImageParse) {
		this.aliBaBaImageParse = aliBaBaImageParse;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getSelfInfo() {
		return selfInfo;
	}

	public void setSelfInfo(Map<String, Object> selfInfo) {
		this.selfInfo = selfInfo;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Map<String, Map<String, Object>> getSpecAttrMap() {
		return specAttrMap;
	}

	public void setSpecAttrMap(Map<String, Map<String, Object>> specAttrMap) {
		this.specAttrMap = specAttrMap;
	}

	public Map<String, Map<String, Object>> getBaseMap() {
		return baseMap;
	}

	public void setBaseMap(Map<String, Map<String, Object>> baseMap) {
		this.baseMap = baseMap;
	}

	public MqRecordItem getMqItem() {
		return mqItem;
	}

	public void setMqItem(MqRecordItem mqItem) {
		this.mqItem = mqItem;
	}
	
	
	
}
