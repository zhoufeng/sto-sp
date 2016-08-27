package com.bohusoft.aliapi.util;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PostConstruct;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.service.AliToken;

@Component
public class MemCachedUtil {
	protected static Logger logger = Logger.getLogger("MemCachedUtil");
	// 创建全局的唯一实例
		protected MemcachedClient client=null;
		@Value("#{aliUtilConfig['ali_memcache_url']}")
		private String memcacheUrl;
		
		@Value("#{aliUtilConfig['ali_memcache_timeout']}")
		private long timeOut;
		
		
	/**
     * 类级的内部类，也就是静态的成员式内部类，该内部类的实例与外部类的实例没有绑定关系，
     * 而且只有被调用到才会装载，从而实现了延迟加载
     */
    /*private static class SingletonHolder{
        *//**
         * 静态初始化器，由JVM来保证线程安全
         * @throws IOException 
         *//*
        private static XMemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(AliConstant.ali_memcache_url));
    }
	
	// 创建全局的唯一实例
	protected static MemcachedClient client=null;
	
	
	public static MemcachedClient getInstance(){
		if(client==null){
			try {
				XMemcachedClientBuilder builder=SingletonHolder.builder;
				//builder.setConnectionPoolSize(10);
				builder.getConfiguration().setSessionIdleTimeout(10000);
				client= builder.build();
			} catch (IOException e) {
				logger.equals(e);
			}
		}	
		return client;
	}*/
	
	@PostConstruct //初始化方法的注解方式  等同与init-method=init 
	public void init() throws IOException{
		XMemcachedClientBuilder builder=new XMemcachedClientBuilder(AddrUtil.getAddresses(memcacheUrl));
		builder.getConfiguration().setSessionIdleTimeout(timeOut);
		client= builder.build();
	}
		
		
	public MemCachedUtil(){
		
	}
	/**
	 * @param key   sessionId
	 * @param aliToken
	 * @throws MemcachedException 
	 * @throws InterruptedException 
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public void set(String key,AliToken aliToken) throws TimeoutException, InterruptedException, MemcachedException, IOException{
		client.set(key,0, aliToken);
	}
	
	public AliToken get(String key) throws TimeoutException, InterruptedException, MemcachedException, IOException{
		AliToken ret=client.get(key);
		return ret;
	}
	
	   /**
		 * 获取服务器上面所有的key
		 * @return
		 * @author liu787427876@126.com
	 * @throws TimeoutException 
	 * @throws InterruptedException 
	 * @throws MemcachedException 
	          * @date 2013-12-4
		 */
		/*public static List<String> getAllKeys() throws MemcachedException, InterruptedException, TimeoutException {
			logger.info("开始获取没有挂掉服务器中所有的key.......");
			List<String> list = new ArrayList<String>();
			KeyIterator iterator=client.getKeyIterator(AddrUtil.getOneAddress(AliConstant.ali_memcache_url));
			while (iterator.hasNext()) {
				String itemKey = iterator.next();
				list.add(itemKey);
			}
			logger.info("获取没有挂掉服务器中所有的key完成.......");
			return list;
		}*/
	
	public String getMemcacheId(String appKey,String memeberId){
		StringBuffer sb=new StringBuffer();
		sb.append(appKey).append("_").append(memeberId);
		return sb.toString();
	}


}