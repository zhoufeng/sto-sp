package com.shenma.common.util;


import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;


/**

 * 该类实现了图片的合并功能，可以选择水平合并或者垂直合并。
 * 当然此例只是针对两个图片的合并，如果想要实现多个图片的合并，只需要自己实现方法 BufferedImage
 * mergeImage(BufferedImage[] imgs, boolean isHorizontal)即可；
 * 而且这个方法更加具有通用性，但是时间原因不实现了，方法和两张图片实现是一样的
 */

public class ImageUtil {
	protected static Logger logger = Logger.getLogger("ImageUtil");
    /**
     * @param fileUrl
     *            文件绝对路径或相对路径
     * @return 读取到的缓存图像
     * @throws IOException
     *             路径错误或者不存在该文件时抛出IO异常
     */
    public static BufferedImage getBufferedImage(String fileUrl)
            throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }

    /**
     * @param savedImg
     *            待保存的图像
     * @param saveDir
     *            保存的目录
     * @param fileName
     *            保存的文件名，必须带后缀，比如 "beauty.jpg"
     * @param format
     *            文件格式：jpg、png或者bmp
     * @return
     */
    public static boolean saveImage(BufferedImage savedImg, String saveDir,
            String fileName, String format) {
        boolean flag = false;

        // 先检查保存的图片格式是否正确
        String[] legalFormats = { "jpg", "JPG", "png", "PNG", "bmp", "BMP" };
        int i = 0;
        for (i = 0; i < legalFormats.length; i++) {
            if (format.equals(legalFormats[i])) {
                break;
            }
        }
        if (i == legalFormats.length) { // 图片格式不支持
            System.out.println("不是保存所支持的图片格式!");
            return false;
        }

        // 再检查文件后缀和保存的格式是否一致
        String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!postfix.equalsIgnoreCase(format)) {
            System.out.println("待保存文件后缀和保存的格式不一致!");
            return false;
        }
        File filedir = new File(saveDir);
        if(!filedir.exists())filedir.mkdirs();
        String fileUrl = saveDir + fileName;
        File file = new File(fileUrl);
        try {
            flag = ImageIO.write(savedImg, format, file);
        } catch (IOException e) {
        	logger.error("图片保存出错",e);
        }

        return flag;
    }

    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     * 
     * @param img1
     *            待合并的第一张图
     * @param img2
     *            带合并的第二张图
     * @param isHorizontal
     *            为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    public static BufferedImage mergeImage(BufferedImage img1,
            BufferedImage img2, boolean isHorizontal) throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();

        // 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);

        // 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) { // 水平方向合并
            DestImage = new BufferedImage(w1+w2, h1, BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            DestImage = new BufferedImage(w1, h1 + h2,
                    BufferedImage.TYPE_INT_RGB);
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
        }

        return DestImage;
    }

    public static boolean readUrlImage(String urlstr,String saveDir,String fileName){
    	URLConnection con=null;
    	BufferedInputStream in=null;
    	FileOutputStream file=null;;
    	try {
	    	URL url = new URL(urlstr);
	    	con=url.openConnection();
			//不超时
			//con.setConnectTimeout(30000);
			
			//不允许缓存
			con.setUseCaches(false);
			con.setDefaultUseCaches(false);
			in = new BufferedInputStream(con.getInputStream());
			File filedir = new File(saveDir);
	        if(!filedir.exists())filedir.mkdirs();
			file = new FileOutputStream(new File(saveDir+fileName)); 
			int t; 
			while ((t = in.read()) != -1) { 
				file.write(t);
				} 
			file.close(); 
			in.close(); 
    	} catch (Exception e) {
    		logger.error("主图保存出错", e);
			return false;
		}finally{
			try {
				file.close(); 
				in.close(); 
			} catch (IOException e) {
				logger.error("关闭流出错", e);
			}
		}
    	return true;
    }
    
    public static byte[] readUrlImage(String urlstr){
    	URLConnection urlConn=null;
    	InputStream in=null;
    	byte[] ret=null;
    	try {
	    	URL url = new URL(urlstr);
	    	urlConn=url.openConnection();
	    	urlConn.setConnectTimeout(5000);
	    	urlConn.setReadTimeout(20000);
	    	//int size =urlConn.getContentLength();
			//不超时
			//con.setConnectTimeout(30000);
			
			//不允许缓存
	    	ret=IOUtils.toByteArray(urlConn);
    	} catch (Exception e) {
    		logger.error("主图从远程下载出错", e);
		}finally{
			if (urlConn instanceof HttpURLConnection)
			      ((HttpURLConnection)urlConn).disconnect();
			urlConn=null;
		}
    	return ret;
    }
    
    public static boolean isConnect(String urlstr) {  
        URL url;  
        InputStream in=null;
        try {  
             url = new URL(urlstr);  
             in = url.openStream();  
             return true;
        } catch (Exception e1) {  
             return false;
        }finally{
        	try {
        		if(in!=null)in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
    }  
    

    /** 
     * 获得指定文件的byte数组 
     */  
    public static byte[] getBytes(String filePath){  
        byte[] buffer = null;  
        try {  
            File file = new File(filePath);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
        	logger.error("文件找不到", e);
        } catch (IOException e) {  
        	logger.error("获得指定文件的byte数组 出错", e);
        }  
        return buffer;  
    }  
    
    
    public static void main(String[] args) {
        // 读取待合并的文件
        BufferedImage bi1 = null;
        BufferedImage bi2 = null;
//        try {
//            bi1 = getBufferedImage("src/ImageProcessDemos/图像合并/垂直合并1.jpg");
//            bi2 = getBufferedImage("src/ImageProcessDemos/图像合并/垂直合并2.jpg");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
        // 调用mergeImage方法获得合并后的图像
        BufferedImage destImg = null;
//        try {
//            destImg = mergeImage(bi1, bi2, false);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // saveImage(BufferedImage savedImg, String saveDir, String fileName,
//        // String format)
//        // 保存图像
//        saveImage(destImg, "src/ImageProcessDemos/图像合并/", "垂直合并图像.png", "png");
//        System.out.println("垂直合并完毕!");

        System.out.println("下面是水平合并的情况：");

        try {
            bi1 = getBufferedImage("src/ImageProcessDemos/图像合并/luguo1.gif");
            bi2 = getBufferedImage("src/ImageProcessDemos/图像合并/luguo2.gif");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 调用mergeImage方法获得合并后的图像
        try {
            destImg = mergeImage(bi1, bi2, true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 保存图像
        saveImage(destImg, "src/ImageProcessDemos/图像合并/", "luguo.png", "png");
        System.out.println("水平合并完毕!");

    }
    
    
    

}
