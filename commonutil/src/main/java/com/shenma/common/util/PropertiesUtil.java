package com.shenma.common.util;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import org.apache.log4j.Logger;


public class PropertiesUtil
{
	protected static Logger logger = Logger.getLogger("PropertiesUtil");
    public static void main(String[] args)
    {

        String readfile = "d:" + File.separator + "readfile.properties";
        String writefile = "d:" + File.separator + "writefile.properties";
        String readxmlfile = "d:" + File.separator + "readxmlfile.xml";
        String writexmlfile = "d:" + File.separator + "writexmlfile.xml";
        String readtxtfile = "d:" + File.separator + "readtxtfile.txt";
        String writetxtfile = "d:" + File.separator + "writetxtfile.txt";

        readPropertiesFile(readfile); //读取properties文件
        writePropertiesFile(writefile); //写properties文件
        readPropertiesFileFromXML(readxmlfile); //读取XML文件
        writePropertiesFileToXML(writexmlfile); //写XML文件
        readPropertiesFile(readtxtfile); //读取txt文件
        writePropertiesFile(writetxtfile); //写txt文件
    }

    
    /**
     * 功能描述:讲.properties的属性设置到类tranfCls的一些静态变量
     * @param filename properties的绝对文件路径
     * @param tranfCls 静态变量成员的类的类型
     */
    @SuppressWarnings("rawtypes")
	public static void setproPorpertiesWithClass(String filename,Class tranfCls){
    	Properties properties=readPropertiesFile(filename);
    	setCustomerProperties(properties, tranfCls);
    }
    
    //读取资源文件,并处理中文乱码
    public static Properties readPropertiesFile(String filename)
    {
        Properties properties = new Properties();
        try
        {
            InputStream inputStream = new FileInputStream(filename);
            properties.load(inputStream);
            inputStream.close(); //关闭流
        }
        catch (IOException e)
        {
           logger.error("读取Properties文件出错", e);
        }
        return properties;
    }

    @SuppressWarnings("rawtypes")
	private static void setCustomerProperties(Properties properties,Class tranfCls){
		Field [] fields = tranfCls.getDeclaredFields();
		try {
			for(Field field:fields){
				Class cls=field.getType();
				field.setAccessible(true);
				if("java.lang.String".equals(cls.getName())){
					String value=properties.getProperty(field.getName());
					if(value!=null)
						
							field.set(cls, value);
						
				}else if("java.lang.Integer".equals(cls.getName())){
					Integer value=Integer.valueOf(properties.getProperty(field.getName()));
					if(value!=null)field.set(cls, value);
				}
			}
		} catch (Exception e) {
			 logger.error("设置属性出错", e);
		}
	}
    
    
    
    //读取XML文件,并处理中文乱码
    public static void readPropertiesFileFromXML(String filename)
    {
        Properties properties = new Properties();
        try
        {
            InputStream inputStream = new FileInputStream(filename);
            properties.loadFromXML(inputStream);
            inputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        String username = properties.getProperty("username");
        String passsword = properties.getProperty("password");
        String chinese = properties.getProperty("chinese"); //XML中的中文不用处理乱码，正常显示
        System.out.println(username);
        System.out.println(passsword);
        System.out.println(chinese);
    }

    //写资源文件，含中文
    public static void writePropertiesFile(String filename)
    {
        Properties properties = new Properties();
        try
        {
            OutputStream outputStream = new FileOutputStream(filename);
            properties.setProperty("username", "myname");
            properties.setProperty("password", "mypassword");
            properties.setProperty("chinese", "中文");
            properties.store(outputStream, "author: shixing_11@sina.com");
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    //写资源文件到XML文件，含中文  
    public static void writePropertiesFileToXML(String filename)
    {
        Properties properties = new Properties();
        try
        {
            OutputStream outputStream = new FileOutputStream(filename);
            properties.setProperty("username", "myname");
            properties.setProperty("password", "mypassword");
            properties.setProperty("chinese", "中文");
            properties.storeToXML(outputStream, "author: shixing_11@sina.com");
            outputStream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
