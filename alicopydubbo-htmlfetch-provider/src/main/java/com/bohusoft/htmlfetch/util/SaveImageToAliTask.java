package com.bohusoft.htmlfetch.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.bohusoft.dubboapi.exception.DubboApiExceptionEnums;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.SessionUtil;

/*@Component
@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)*/
public class SaveImageToAliTask implements Callable<Map<String,Object>>{
	protected static Logger logger = Logger.getLogger("SaveImageToAliTask");
	private Map<String,Object> map=null;
	private String albumId;
	private String name;
	private byte[] data;
	private AlbumService albumService;
	private AliToken aliToken;
	
	
	
	public SaveImageToAliTask(String albumId,String name,byte[] data,Map<String,Object> map,AlbumService albumService){
		this.map=map;
		this.albumId=albumId;
		this.name=name;
		this.data=data;
		this.map=map;
		this.albumService=albumService;
		this.aliToken=SessionUtil.getAliSession();
		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Map<String,Object> call() {
		//在ThreadLocal里设置token
		try {
			SessionUtil.setAliSession(this.aliToken);
			Map<String, Object> imageBean = albumService.uploadImage(albumId,name, "", data);
			Map<String, Object> result = ((Map<String, Object>) imageBean.get("result"));
			List<Map<String, Object>> rtlist = (List<Map<String, Object>>) result.get("toReturn");
			String lastImageUrl = (String) rtlist.get(0).get("url");
			lastImageUrl = "http://i00.c.aliimg.com/" + lastImageUrl;
			map.put("newUrl", lastImageUrl);
		} catch (AliReqException aliE) {
			logger.error("保存到ali图片出错!" + aliE.getMessage()+"::::url:"+map.get("url"));
			map.put("errorMsg", "保存到ali图片出错");
			map.put("errorCode", aliE.getCode());
		}catch (Exception e) {
			logger.error("保存到ali图片出错!" + e.getMessage()+"::::url:"+map.get("url"));
			map.put("errorMsg", "保存到ali图片出错");
			map.put("errorCode", DubboApiExceptionEnums.COMMON_FAIL.getMessage());
		}
		return map;

	}
}
