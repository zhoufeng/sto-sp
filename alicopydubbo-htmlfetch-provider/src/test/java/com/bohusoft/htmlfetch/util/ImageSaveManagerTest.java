package com.bohusoft.htmlfetch.util;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.bohusoft.dubboapi.entity.ImageBean;
import com.bohusoft.htmlfetch.util.ImageSaveManager;

public class ImageSaveManagerTest {
	
	@Test
	public void testReqImage() throws InterruptedException, ExecutionException, IOException{
		String url="https://cbu01.alicdn.com/cms/upload/2015/551/394/2493155_759381252.jpg";
		ImageBean bean=ImageSaveManager.reqImage(url);
		File distfile=new File("d:/123.jpg");
		FileUtils.writeByteArrayToFile(distfile, bean.getData());
		Assert.assertTrue(distfile.exists());
	}
	
	
	@Test
	public void testReqImages() throws InterruptedException, ExecutionException, IOException{
		String[] urls=new String[]{"https://cbu01.alicdn.com/cms/upload/2015/551/394/2493155_759381252.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/733/094/2490337_1428245009.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/173/684/2486371_1648542067.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/766/074/2470667_1799871007.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/564/784/2487465_2015354421.jpg",
				"https://cbu01.alicdn.com/cms/upload/2015/414/574/2475414_16443940.jpg",
				"http://cbu01.alicdn.com/cms/upload/2014/053/290/2092350_1805353437.jpg"
		};
		List<String> urlList=Arrays.asList(urls);
		List<ImageBean> list=ImageSaveManager.reqImages(urlList);
		for(ImageBean bean:list){
			String url=bean.getUrl();
			FileUtils.writeByteArrayToFile(new File("d:/"+url.substring(url.lastIndexOf("/"), url.length())), bean.getData());
		}
		for(ImageBean bean:list){
			String url=bean.getUrl();
			Assert.assertTrue((new File("d:/"+url.substring(url.lastIndexOf("/"), url.length())).exists()));
		}
		
	}
}
