package com.shenma.top.imagecopy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.common.util.ImageUtil;
import com.shenma.top.imagecopy.service.CommonImageService;
import com.shenma.top.imagecopy.util.bean.ImageBean;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.taobao.api.ApiException;

@Controller
@RequestMapping("/top/imagecopy")
public class ImageCopyController {
	protected static Logger logger = Logger.getLogger("ImageCopyController");
	@Autowired
	private CommonImageService commonImageService;
	@Autowired
    private AlbumService albumService;
	
	@RequestMapping(value="",method=RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response) throws ApiException{
		Map<String,Object> model=new HashMap<String, Object>();
		model.put("content", "copy/imageIndex.jsp");
		return new ModelAndView("aceadmin/index",model);
	}
	
	@RequestMapping(value="/search")
	@ResponseBody
	public ImageBean<ImageVoBean> list(@RequestParam("url") String url) throws ApiException{
		ImageBean<ImageVoBean> bean=commonImageService.parseUrl(url);
		return bean;
	}
	
	@RequestMapping(value="/uploadImage")
	@ResponseBody
	public Map<String,Object> uploadImage(@RequestParam("url") String url,@RequestParam("pictureCategoryId") Long pictureCategoryId,@RequestParam("name") String fileName){
		Map<String,Object> bean=null;
		try {
			String suffix=url.substring(url.lastIndexOf("."),url.length());
			fileName+=suffix;
			byte[] imgByte=ImageUtil.readUrlImage(url);
			long maxSize=(2<<16)*30;
			if(imgByte.length>maxSize){
				throw new ApiException("文件超过3M");
			}
			bean=albumService.uploadImage(pictureCategoryId.toString(), fileName, null, imgByte);
		} catch (ApiException e) {
			logger.error("淘宝图片上传失败", e);
		} catch (AliReqException e) {
			logger.error("淘宝图片上传失败", e);
		}
		return bean;
	}
	
	@RequestMapping(value="/findCateList")
	@ResponseBody
	public List<Album> findCateList(){
		List<Album> bean=null;
		try {
			bean=albumService.getAllAlbumList();
		}  catch (AliReqException e) {
			logger.error("图片分类请求错误！", e);
		}
		return bean;
	}
}