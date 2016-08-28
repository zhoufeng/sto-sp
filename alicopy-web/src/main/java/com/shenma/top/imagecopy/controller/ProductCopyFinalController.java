package com.shenma.top.imagecopy.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shenma.top.imagecopy.dao.SaveTaskDao;
import com.shenma.top.imagecopy.service.AliForeBackSevice;
import com.shenma.top.imagecopy.service.TaobaoForeBackService;
import com.shenma.top.imagecopy.util.RunningTaskManager;
import com.shenma.top.imagecopy.util.bean.RunningTask;
import com.shenma.top.imagecopy.util.prase.cate.AliBaBaBusseness;
import com.taobao.api.ApiException;

@Controller
@RequestMapping("/top/finalproductcopy")
public class ProductCopyFinalController {
	
	@Autowired
	private AliForeBackSevice aliForeBackSevice;
	
	@Autowired
	private TaobaoForeBackService taobaoForeBackService;
	
	@Autowired
	private SaveTaskDao saveTaskDao;
	


	/**
	 * 阿里巴巴单个复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/saveAliItem")
	@ResponseBody
	public Map<String,Object> saveAliItem(@RequestBody Map<String,Object> variables) throws ApiException{
		return aliForeBackSevice.saveAliItem(variables,false);
	}


	/**
	 * 阿里巴巴批量复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/batchSaveAliItem")
	@ResponseBody
	public List<Map<String,Object>> batchSaveAliItem(@RequestBody Map<String,Object> variables) throws ApiException{
		String[] urlsList=variables.get("urls").toString().split("");
		List<Map<String,Object>> retList=new ArrayList<Map<String, Object>>();
		for(String url:urlsList){
			variables.put("url", url);
			Map<String,Object> ret=aliForeBackSevice.saveAliItem(variables,false);
			retList.add(ret);
		}
		//@TODO
		return	retList;
	}

	/**
	 * 淘宝单个复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 */
	@RequestMapping(value="/saveTaobaoItem")
	@ResponseBody 
	public Map<String,Object> saveTaobaoItem(@RequestBody Map<String,Object> variables) throws ApiException{
		return taobaoForeBackService.saveTaobaoItem(variables,false);
	}
	
	/**
	 * 阿里巴巴多选复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveAliItemList")
	@ResponseBody 
	public List<Map<String,Object>> saveAliItemList(@RequestBody Map<String,Object> variables) throws ApiException{
		List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
		List<String> urlList=(List<String>) variables.get("urlList");
		Map<String,Object> params=(Map<String, Object>) variables.get("params");
		if(urlList!=null&&urlList.size()>0){
			//RunningTask task=RunningTaskManager.addTask(urlList.size());
			for(int i=0;i<urlList.size();i++){
				String url=urlList.get(i);
				if(url.startsWith("//"))url="http:"+url;
				params.put("url", url);
				Map<String,Object> oneRet=aliForeBackSevice.saveAliItem(params,true);
				retList.add(oneRet);
				/*if(i==0){
					msg=(String) oneRet.get("msg");
					ret.put("msg", msg);
				}*/
				//RunningTaskManager.stepTask(task.getId(), 1);
			}
			//RunningTaskManager.delTask(task.getId());
			
		}
		return retList;
	}
	
	/**
	 * 阿里巴巴类目复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 * @throws InterruptedException 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/saveAliCateList")
	@ResponseBody 
	public List<Map<String,Object>> saveAliCateList(@RequestBody Map<String,Object> variables) throws ApiException, InterruptedException{
		List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
		Map<String,Object> params=(Map<String, Object>) variables.get("params");
		String cateUrl=(String) variables.get("cateUrl");
		String orgurl=(String) variables.get("url");
		Integer totalPages=(Integer) variables.get("totalPages");
		
		AliBaBaBusseness bus=new AliBaBaBusseness();
		List<String> urls=bus.getUrls(orgurl, cateUrl, totalPages);
		
		for(int i=0;i<urls.size();i++){
			String url=urls.get(i);
			if(url.startsWith("//"))url="http:"+url;
			params.put("url", url);
			Map<String,Object> oneRet=aliForeBackSevice.saveAliItem(params,true);
			retList.add(oneRet);
		}
		return retList;
	}
	
	
	@RequestMapping(value="/saveAll")
	@ResponseBody 
	public String saveAll(@RequestBody Map<String,Object> variables){
		return null;
	}
	
	/**
	 * 淘宝多选复制
	 * @param variables
	 * @return
	 * @throws ApiException
	 */
	@SuppressWarnings({ "unchecked" })
	@RequestMapping(value="/saveTaobaoItemList")
	@ResponseBody 
	public List<Map<String,Object>> saveTaobaoItemList(@RequestBody Map<String,Object> variables) throws ApiException{
		List<Map<String,Object>> retList=new ArrayList<Map<String,Object>>();
		String msg=null;
		List<String> urlList=(List<String>) variables.get("urlList");
		Map<String,Object> params=(Map<String, Object>) variables.get("params");
		for(int i=0;i<urlList.size();i++){
			String url=urlList.get(i);
			params.put("url", url);
			Map<String,Object> oneRet=taobaoForeBackService.saveTaobaoItem(params,true);
			retList.add(oneRet);
			/*if(i==0){
				msg=(String) oneRet.get("msg");
				ret.put("msg", msg);
			}*/
		}
		return retList;
	}
	

	
}
