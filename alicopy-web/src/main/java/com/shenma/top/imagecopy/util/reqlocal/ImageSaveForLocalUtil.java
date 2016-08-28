package com.shenma.top.imagecopy.util.reqlocal;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;


import org.apache.activemq.util.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.exception.AliReqException;
import com.shenma.aliutil.service.AlbumService;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.common.util.ImageUtil;
import com.shenma.top.imagecopy.util.image.ImageSaveManager;
import com.taobao.api.ApiException;

@Component
public class ImageSaveForLocalUtil {
	protected static Logger logger = Logger.getLogger("ImageSaveForLocalUtil");
	@Autowired
	private AlbumService albumService;
	
	@Autowired
	private AliConstant aliConstant;
	public Map<String,Object> saveImage(String url,String albumId,String name,String description) throws ApiException, AliReqException{
		//url="http://imagecopy.kongjishise.com/test.jpg";
		Map<String, Object> ret=new HashMap<String,Object>(2);
		if(url.endsWith("startFlag.gif")||url.endsWith("endFlag.gif")){
			ret.put("newUrl", url);
			return ret;
		}
		//判断地址是否存在,不存在,直接返回
		if(!ImageUtil.isConnect(url)){
			ret.put("newUrl", url);
			return ret;
		}
		logger.debug(url);
		byte[] imgByte = null;
		try {
			imgByte = ImageSaveManager.reqImage(url).getData();
		} catch (Exception e) {
			logger.error("图片地址不存在url:" + url);
			ret.put("newUrl", url);
			return ret;
		}
		if (imgByte == null || imgByte.length == 0)	{
			logger.error("图片地址不存在url:" + url);
			ret.put("newUrl", url);
			return ret;
		}
			
		long maxSize = (2 << 16) * 11+58208; //1.5mb
		if (imgByte.length > maxSize) {
			//double s=((double)maxSize)/imgByte.length ;
			//double scale=Math.sqrt(s);
			ByteArrayOutputStream os=new ByteArrayOutputStream();
			try {
				//Thumbnails.of(new URL(url)).scale(scale).toOutputStream(os);
				//imgByte=os.toByteArray();
				Image img = Toolkit.getDefaultToolkit().createImage(new URL(url));
		        BufferedImage bi_scale = toBufferedImage(img);
		        ImageIO.write(bi_scale, "jpg",os);
		        imgByte=os.toByteArray();
			} catch (MalformedURLException e) {
				logger.error("请求不到图片:" + url);
				ret.put("newUrl", url);
				return ret;
			} catch (IOException e) {
				logger.error("转换图片发送异常:" + url);
				ret.put("newUrl", url);
				return ret;
			}
			
		}
		Map<String, Object> bean = null;
		try {
			bean =albumService.uploadImage(albumId,name, description, imgByte);
		} catch (AliReqException e) {
			if(e.getCode().equals("090008")){
				ret.put("newUrl", url);
				return ret;
			}else{
				logger.error("图片地址:["+url+"]错误");
				throw e;
			}
		}
		Map<String, Object> result = (Map<String, Object>) bean.get("result");
		List<Map<String, Object>> rtlist = (List<Map<String, Object>>) result.get("toReturn");
		String lastImageUrl = (String) rtlist.get(0).get("url");
		lastImageUrl = aliConstant.image_uri_prefix + lastImageUrl;
		ret.put("newUrl", lastImageUrl);
		return ret;
		
	}
	public static void main(String[] args) throws IOException {
		String url="https://gd3.alicdn.com/bao/uploaded/i3/25490090/TB2OUdztVXXXXX1XXXXXXXXXXXX_!!25490090.jpg";
		/*File outFile=new File("f:/2.jpg");
		try {
			Thumbnails.of(new URL(url)).sourceRegion(Positions.CENTER, 400,
				    400).size(200, 200).keepAspectRatio(false).toFile(outFile);
		} catch (MalformedURLException e) {
			logger.error("请求不到图片:" + url);
		} catch (IOException e) {
			logger.error("转换图片发送异常:" + url);
		}*/
		
		Image img = Toolkit.getDefaultToolkit().createImage(new URL(url));
        BufferedImage bi_scale = toBufferedImage(img);
        ImageIO.write(bi_scale, "jpg",new File("f:\\2.jpg"));
	}
	
	
	public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage)image;
         }
     
        // This code ensures that all the pixels in the image are loaded
         image = new ImageIcon(image).getImage();
     
        // Determine if the image has transparent pixels; for this method's
        // implementation, see e661 Determining If an Image Has Transparent Pixels
        //boolean hasAlpha = hasAlpha(image);
     
        // Create a buffered image with a format that's compatible with the screen
         BufferedImage bimage = null;
         GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            // Determine the type of transparency of the new buffered image
            int transparency = Transparency.OPAQUE;
           /* if (hasAlpha) {
                 transparency = Transparency.BITMASK;
             }*/
     
            // Create the buffered image
             GraphicsDevice gs = ge.getDefaultScreenDevice();
             GraphicsConfiguration gc = gs.getDefaultConfiguration();
             bimage = gc.createCompatibleImage(
                 image.getWidth(null), image.getHeight(null), transparency);
         } catch (HeadlessException e) {
            // The system does not have a screen
         }
     
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            //int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
            /*if (hasAlpha) {
                 type = BufferedImage.TYPE_INT_ARGB;
             }*/
             bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
         }
     
        // Copy image to buffered image
         Graphics g = bimage.createGraphics();
     
        // Paint the image onto the buffered image
         g.drawImage(image, 0, 0, null);
         g.dispose();
     
        return bimage;
     }
}
