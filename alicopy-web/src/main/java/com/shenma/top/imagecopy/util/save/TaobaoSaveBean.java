package com.shenma.top.imagecopy.util.save;

import java.util.Map;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.top.imagecopy.entity.MqRecordItem;

public class TaobaoSaveBean {
	private Album album;
	private Offer offer;
	private boolean picSave;
	private Integer catsId;
	private String imagePrefix;
	private String url;//输入的url
	private Map<String,Object> params=null;
	private Map<String,Object> selfInfo;
	private MqRecordItem mqItem;
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
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
	public Map<String, Object> getParams() {
		return params;
	}
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
	public Map<String, Object> getSelfInfo() {
		return selfInfo;
	}
	public void setSelfInfo(Map<String, Object> selfInfo) {
		this.selfInfo = selfInfo;
	}
	public MqRecordItem getMqItem() {
		return mqItem;
	}
	public void setMqItem(MqRecordItem mqItem) {
		this.mqItem = mqItem;
	}
	
	
	
	
}
