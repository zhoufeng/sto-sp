package com.shenma.top.imagecopy.util.save;

import java.io.IOException;
import java.util.List;

import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.common.util.ImageUtil;
import com.shenma.taobao.service.product.ItemImgService;
import com.shenma.taobao.service.product.ItemJointService;
import com.shenma.top.imagecopy.service.ImageCopyService;
import com.shenma.top.imagecopy.util.bean.ImageVoBean;
import com.shenma.top.imagecopy.util.prase.TopTaobaoImageParse;
import com.taobao.api.ApiException;
import com.taobao.api.FileItem;
import com.taobao.api.request.ItemAddRequest;
import com.taobao.api.request.ItemImgUploadRequest;
import com.taobao.api.request.ItemJointPropimgRequest;
import com.taobao.api.response.ItempropsGetResponse;
import com.taobao.api.response.PictureUploadResponse;

@Component
public class TaobaoSavePicBussiness implements SaveBussiness {
	private ItemAddRequest topitem;
	private ItempropsGetResponse res;
	private Document document;
	private SaveBean bean;
	private Long numIid;
	private ItemImgService imgService=new ItemImgService();
	private ItemJointService itemJointService;
	private ImageCopyService imageCopyService;
	public TaobaoSavePicBussiness(SaveBean bean,Long numIid,ImageCopyService imageCopyService,ItemJointService itemJointService){
		this.topitem=bean.getTopitem();
		this.res=bean.getRes();
		this.document=bean.getDocument();
		this.bean=bean;
		this.numIid=numIid;
		this.imageCopyService=imageCopyService;
		this.itemJointService=itemJointService;
	}
	public TaobaoSavePicBussiness(){
		
	}
	@Override
	public void option() throws ApiException {
		saveZhutu();
		saveYanse();
		//saveDesc();
	}
	
	private void saveZhutu() throws ApiException{
		TopTaobaoImageParse parse=new TopTaobaoImageParse();
		List<ImageVoBean> list=parse.genZhutuImage(document);
		for(int i=0;i<list.size();i++){
			ImageVoBean bean=list.get(i);
			ItemImgUploadRequest itemImg=new ItemImgUploadRequest();
			itemImg.setNumIid(numIid);
			byte[] data=ImageUtil.readUrlImage(bean.getUrl());
			String suffix=bean.getUrl().substring(bean.getUrl().lastIndexOf("."),bean.getUrl().length());
			FileItem fileItem=new FileItem(bean.getName()+suffix,data);
			itemImg.setImage(fileItem);
			if(i==0)itemImg.setIsMajor(true);
			itemImg.setPosition(new Long(i));
			//imgService.upload(itemImg,SessionUtil.getTopSession().getAccess_token());
		}
	}
	
	private void saveYanse() throws ApiException{
		int i=1;
		for(String pidvid:bean.getYanseMap().keySet()){
			PictureUploadResponse res=imageCopyService.upload(bean.getYanseMap().get(pidvid), 0l, "颜色分类_"+i);
			ItemJointPropimgRequest req=new ItemJointPropimgRequest();
			req.setNumIid(numIid);
			String picPath=res.getPicture().getPicturePath();
			picPath=picPath.substring(picPath.indexOf("imgextra/")+9, picPath.length());
			req.setPicPath(picPath);
			req.setProperties(pidvid);
			//itemJointService.upload(req,SessionUtil.getTopSession().getAccess_token());
			i++;
		}
	}
	private void saveDesc() throws IOException, InterruptedException{
		TopTaobaoImageParse imageParse=new TopTaobaoImageParse();
		List<ImageVoBean> list=imageParse.genXiangqinImage(document);
	}
	public static void main(String[] args) {
		System.out.println("abcdef".indexOf("cde"));
	}
}
