package com.shenma.aliutil.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.entity.album.Album;
import com.shenma.aliutil.entity.album.Image;
import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.util.BeanUtil;



/**
 * 图片相册
 * @author mircle
 *
 */
@Service
public class AlbumService {
	@Autowired 
	private AlibabaRequestService requestService;
	
	/**
	 * 获取当前用户相册列表
	 * @return
	 * @throws AliReqException
	 */
	public List<Album> getAllAlbumList() throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("albumType", "MY");
		params.put("pageSize", 500);
		Map<String,Object> myRet=requestService.reqAliApp("ibank.album.list", params);
		List<Album> myList=BeanUtil.transf(myRet, Album.class);
		params.put("albumType", "CUSTOM");
		Map<String,Object> customRet=requestService.reqAliApp("ibank.album.list", params);
		List<Album> customList=BeanUtil.transf(customRet, Album.class);
		myList.addAll(customList);
		return myList;
	}
	
	public Page<Image> getImagesByAlbumId(String albumId,int page,int pageSize) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		Page<Image> customList=null;
		if("0".equals(albumId)){
			List<Album> albumList= getAllAlbumList();
			List<Image> list=new ArrayList<Image>();
			Page<Image> customtempList=null;
			for(Album album:albumList){
				String albumIdtemp=album.getId();
				params.put("albumId", albumIdtemp);
				params.put("pageNo", page);
				params.put("pageSize", pageSize);
				Map<String,Object> ret=requestService.reqAliApp("ibank.image.list", params);
				customtempList=BeanUtil.transfAliRequest(ret, Image.class);
				list.addAll(customtempList.getContent());
				
			}
			customList=new PageImpl<Image>(list);
		}else{
			params.put("albumId", albumId);
			params.put("pageNo", page);
			params.put("pageSize", pageSize);
			Map<String,Object> ret=requestService.reqAliApp("ibank.image.list", params);
			customList=BeanUtil.transfAliRequest(ret, Image.class);
		}
		return customList;
	}
	
	public Album findAlbumById(String albumId) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("albumId", new Long(albumId));
		Map<String,Object> ret=requestService.reqAliApp("ibank.album.get", params);
		Page<Album> customList=BeanUtil.transfAliRequest(ret, Album.class);
		return customList.getContent().size()>0?customList.getContent().get(0):null;
	}
	
	public Album createAlbum(String name) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("name", name);
		params.put("authority", 1);
		Map<String,Object> ret=requestService.reqAliApp("ibank.album.create", params);
		Page<Image> customList=BeanUtil.transfAliRequest(ret, Image.class);
		Album album=this.findAlbumById(customList.getContent().get(0).getAlbumId().toString());
		return album;
	}
	public boolean updateAlbum(String albumId,String name) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("albumId", albumId);
		params.put("name", name);
		params.put("authority", 1);
		requestService.reqAliApp("ibank.album.modify", params);
		//Page<Image> customList=BeanUtil.transfAliRequest(ret, Image.class);
		return true;
	}
	public boolean deleteAlbumByAlbumIds(String albumIds) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("albumIds", albumIds);
		Map<String,Object> ret=requestService.reqAliApp("ibank.album.delete", params);
		//Page<Image> customList=BeanUtil.transfAliRequest(ret, Image.class);
		return true;
	}
	
	public Map<String,Object> deleteImagesByIds(String... imageIds) throws AliReqException{
		String imageIdsParam="";
		for (int i = 0; i < imageIds.length; i++) {
			imageIdsParam+=imageIds[i]+";";
		}
		if(imageIds.length>1)imageIdsParam.substring(0,imageIdsParam.length()-1);
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("imageIds", imageIdsParam);
		Map<String,Object> ret=tryCountRequest("ibank.image.deleteByIds", params,2);
		return ret;
	}
	
	public Map<String,Object> tryCountRequest(String method,Map<String,Object> params,int counts) throws AliReqException{
		int count=0;
		Map<String,Object> ret=null;
		while (count < counts) {
			try {
				ret=requestService.reqAliApp(method, params);
				break;
			} catch (AliReqException e) {
				count++;
				if(e.getCode()==null){
					e.setCode("090008");
				}
				if(count==counts){
					throw new AliReqException(e.getCode(), e.getMessage());
				}
				/*if(e!=null&&e.getCode().equals("090008")){
					continue;  
				}else{
					throw new AliReqException(e.getCode(), e.getMessage());
				}*/
			}
		}
		return ret;
	}
	
	public static void main(String[] args) throws AliReqException {
		AlbumService service=new AlbumService();
		service.tryCountRequest("", null, 4);
	}
	
	/**
	 * 上传图片
	 * @param albumId
	 * @param imageName
	 * @param description
	 * @param imageBytes
	 * @return
	 * @throws AliReqException
	 */
	public Map<String,Object> uploadImage(String albumId,String imageName,String description,byte[] imageBytes) throws AliReqException{
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("albumId", albumId);
		params.put("name", imageName);
		params.put("imageBytes", imageBytes);
		if(description!=null)params.put("description", description);
		
		Map<String,Object> ret=tryCountRequest("ibank.image.upload", params,2);
		//Page<Image> customList=BeanUtil.transfAliRequest(ret, Image.class);
		return ret;
	}
	
	public Image uploadImageOne(String albumId,String imageName,String description,byte[] imageBytes) throws AliReqException{
		Map<String,Object> ret=uploadImage(albumId, imageName, description, imageBytes);
		Page<Image> customList=BeanUtil.transfAliRequest(ret, Image.class);
		if(customList.getTotalElements()>0){
			return customList.getContent().get(0);
		}else{
			throw new AliReqException("上传图片失败");
		}
	}
	
	
	
}
