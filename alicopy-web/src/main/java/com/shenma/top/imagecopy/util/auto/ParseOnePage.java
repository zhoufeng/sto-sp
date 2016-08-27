package com.shenma.top.imagecopy.util.auto;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ParseOnePage {
	protected static Logger logger = Logger.getLogger("ParseOnePage");
	Document doc=null;
	String url=null;
	public static String dirPath="d:\\test1"; 
	private HttpClient httpclient;
	public static Map<String,Boolean> cssjsMap=new ConcurrentHashMap<String, Boolean>();
	
	public static Map<String,Boolean> imagesMap=new ConcurrentHashMap<String, Boolean>();
	
	public static Map<String,String> urlsMap=new HashMap<String, String>();
	public void parse(String url) throws IOException{
		this.url=url;
		doc=get(url);
		String host=getHost(url);
		String tempurl=url.replace(host, "");
		String suffix=suffix(tempurl);
		String path=dirPath+tempurl+suffix;
		File file=new File(path);
		FileUtils.write(file, doc.html(), "utf-8");
		urlsMap.put(url, url+suffix);
		genCss();
		genScript();
		genImages();
		
        
	}
	
	public void parsehtml(String htmlstr) throws IOException{
		doc=Jsoup.parse(htmlstr);
		genCss();
		genScript();
		genImages();
	}
	
	private String suffix(String url){
		if(url.equals("")){
			return "/index.html";
		}else if(url.endsWith("/")){
			return "index.html";
		}else{
			return ".html";
		}
		
	}
	private Document get(String url) throws IOException{
		return Jsoup.connect(url).timeout(5000).userAgent("Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2159.4 Safari/537.36").get();
	}
	private void genImages() throws FileNotFoundException{
		Elements images = doc.select("img[src]");
		for(Element element:images){
			String url=element.attr("abs:src");
			if(imagesMap.containsKey(url))return;
			String host=getHost(url);
			String tempurl=url.replace(host, "");
			String path=dirPath+tempurl;
			File file=new File(path);
			if(!file.getParentFile().mkdirs()){
				file.getParentFile().mkdirs();
			}
			if(imagesMap.containsKey(url)){
				imagesMap.put(url, Boolean.TRUE);
				readUrlImage(url, file.getParent()+File.separator, file.getName());
			}
		}
	}
	private String getHost(String url){
		Pattern pattern = Pattern.compile("http\\://.*?(?=/)");
		Matcher matcher = pattern.matcher(url);
		String host=null;
		if(matcher.find()){
			host=matcher.group(0);
		}
		return host;
	}
	private void genScript() throws IOException{
		Elements imports = doc.select("script[src]");
		for(Element element:imports){
			String url=element.attr("abs:src");
			if(url.contains(".js")){
				url=url.substring(0,url.indexOf(".js")+3);
			}else{
				continue;
			}
			if(cssjsMap.containsKey(url))return;
			String host=getHost(url);
			String tempurl=url.replace(host, "");
			String path=dirPath+tempurl;
			File file=new File(path);
			if(!file.getParentFile().mkdirs()){
				file.getParentFile().mkdirs();
			}
			if(!cssjsMap.containsKey(url)){
				cssjsMap.put(url, Boolean.TRUE);
				FileUtils.write(file, getRequest(url), "utf-8");
			}
		}
	}
	private void genCss() throws IOException{
		Elements imports = doc.select("link[href]");
		for(Element element:imports){
			String url=element.attr("abs:href");
			if(url.contains(".css")){
				url=url.substring(0,url.indexOf(".css")+4);
			}else{
				continue;
			}
			if(cssjsMap.containsKey(url))return;
			String host=getHost(url);
			String tempurl=url.replace(host, "");
			String path=dirPath+tempurl;
			File file=new File(path);
			if(!file.getParentFile().mkdirs()){
				file.getParentFile().mkdirs();
			}
			if(!cssjsMap.containsKey(url)){
				cssjsMap.put(url, Boolean.TRUE);
				Document cssdoc=get(url);
				FileUtils.write(file, cssdoc.text(), "utf-8");
				genCssImages(cssdoc.text(),url,path);
			}
		}
		
	}
	private void genCssImages(String cssstr,String url,String dirPath){
		Pattern pattern = Pattern.compile("(?<=url\\().*?(?=\\))");
		Matcher matcher = pattern.matcher(cssstr);

		while(matcher.find()){
			 for (int i = 0; i <= matcher.groupCount(); i++) {
				 String imageUrl=matcher.group(i);
				 String[] strs=handleImagePath(imageUrl,url,dirPath);
				 try {
					 readUrlImage(strs[0], strs[1].substring(0, strs[1].lastIndexOf("/")+1), strs[1].substring(strs[1].lastIndexOf("/")+1, strs[1].length()));
				} catch (FileNotFoundException e) {
					continue;
				}
			 }
			 
		}
	}
	private String[] handleImagePath(String imageUrl,String url,String dirPath){
		if(imageUrl.startsWith("../")){
			File file=new File(dirPath);
			url=url.substring(0, url.lastIndexOf("/"));
			url=url.substring(0, url.lastIndexOf("/"))+imageUrl.substring(2, imageUrl.length());
			imageUrl=file.getParentFile().getParent()+imageUrl.substring(2, imageUrl.length());
		}else if(imageUrl.startsWith("http")){
			
		}else{
			File file=new File(dirPath);
			url=url.substring(0, url.lastIndexOf("/"));
			imageUrl=file.getParent()+"/"+imageUrl;
		}
		return new String[]{url,imageUrl};
	}
	
	public static boolean isConnect(String url){
		try {
	           //设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
	           HttpURLConnection.setFollowRedirects(false);
	           //到 URL 所引用的远程对象的连接
	           HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	          
	           con.setRequestMethod("HEAD");
	           //从 HTTP 响应消息获取状态码
	           return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	       } catch (Exception e) {
	           e.printStackTrace();
	           return false;
	        }
	}
    public static boolean readUrlImage(String urlstr,String saveDir,String fileName) throws FileNotFoundException{
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
    	} catch (FileNotFoundException e) {
    		throw new FileNotFoundException();
		}catch (Exception e) {
    		logger.error("主图保存出错", e);
			return false;
		}finally{
			try {
				if(file!=null)file.close(); 
				if(in!=null)in.close(); 
			} catch (IOException e) {
				logger.error("关闭流出错", e);
			}
		}
    	return true;
    }
	
    public HttpClient getHttpClient(){
		if(httpclient==null){
			//HttpState initialState = new HttpState();

		   // Cookie mycookie = new Cookie(".1688.com", "mycookie", "stuff", "/", null, false);
		   // mycookie.setValue(cookieValue);
		   // initialState.addCookie(mycookie);
	
		    // Get HTTP client instance
		    httpclient = new HttpClient();
		    httpclient.getHttpConnectionManager().
		        getParams().setConnectionTimeout(30000);
		   // httpclient.setState(initialState);
	
		    //httpclient.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
		}
	    return httpclient;
	}
    
	/**
	 * 根据类别id获得分类
	 * @param cateId
	 */
	public String getRequest(String url){
		GetMethod httpget = new GetMethod(url);
		String ret=null;
	    try {
			int result = getHttpClient().executeMethod(httpget);
			ret=httpget.getResponseBodyAsString();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			httpget.releaseConnection();
		}
	    return ret;
	}
    
	public static void main(String[] args) throws IOException {
		ParseOnePage onePage=new ParseOnePage();
		//http://www.orangesh.com/home/cn/index
		File file=new File("d:\\test.txt");
		String str=FileUtils.readFileToString(file);
		onePage.parsehtml(str);
		
	}
}
