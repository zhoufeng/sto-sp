package com.shenma.top.imagecopy.util.fetchhtml;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FetchHtmlUtil {
	@Autowired
	protected FetchDetailHtmlUtil fetchDetailHtmlUtil;
	
	private  FetchParamsBean paramsBean=new FetchParamsBean();
	

	public String getDetailHtml(String url) throws IOException, InterruptedException{
		if(paramsBean.getDetatilType()==1){
			return fetchDetailHtmlUtil.getJsoupReq(url);
		}else if(paramsBean.getDetatilType()==2){
			return fetchDetailHtmlUtil.getNwJsForLaotu(paramsBean.getDetailSearchUrl()+"?url="+url);
		}else if(paramsBean.getDetatilType()==3){
			return fetchDetailHtmlUtil.getNwJsForLaotu(paramsBean.getDetailSearchUrl()+"?url="+url);
		}else{
			return fetchDetailHtmlUtil.getJsoupReq(url);
		}
	}
	
	public String getCateSearchHtml(String url) throws IOException, InterruptedException{
		if(paramsBean.getSearchType()==1){
			return fetchDetailHtmlUtil.getJsoupReq(url);
		}else if(paramsBean.getSearchType()==2){
			return fetchDetailHtmlUtil.getNwJsForLaotu(paramsBean.getCateSearchUrl()+"?url="+url);
		}else if(paramsBean.getSearchType()==3){
			return fetchDetailHtmlUtil.getNwJsForLaoGong(paramsBean.getCateSearchUrl()+"?url="+url);
		}else{
			return fetchDetailHtmlUtil.getJsoupReq(url);
		}
	}
	
	
	public  FetchParamsBean getParamsBean() {
		return paramsBean;
	}

	public  void setParamsBean(FetchParamsBean paramsBean) {
		this.paramsBean = paramsBean;
	}

}
