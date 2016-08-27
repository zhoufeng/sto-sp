package com.shenma.top.imagecopy.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.common.util.ImageUtil;
import com.shenma.taobao.service.media.PictureService;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.response.PictureUploadResponse;

@Service
public class ImageCopyService {

	@Autowired
	private PictureService pictureService;
	
	public PictureUploadResponse upload(String picUrl,Long pictureCategoryId,String fileName) throws ApiException{
		Map<String,Object> params=new HashMap<String,Object>();
		String suffix=picUrl.substring(picUrl.lastIndexOf("."),picUrl.length());
		fileName+=suffix;
		byte[] imgByte=ImageUtil.readUrlImage(picUrl);
		long maxSize=(2<<16)*30;
		if(imgByte.length>maxSize){
			throw new ApiException("文件超过3M");
		}
		params.put("pictureCategoryId", pictureCategoryId);
		FileItem fileItem=new FileItem(fileName, imgByte);
		params.put("img", fileItem);
		params.put("imageInputTitle",fileName);
		PictureUploadResponse res= pictureService.upload(params,"");
		return res;
	}
}
