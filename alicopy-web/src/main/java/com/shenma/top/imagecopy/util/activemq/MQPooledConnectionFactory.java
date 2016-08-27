package com.shenma.top.imagecopy.util.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 链接工厂管理类
 * 自己工厂定义成了单例模式，连接池是静态块进行初始化，具体实现自己看着办
 */
public class MQPooledConnectionFactory {
	private static ActiveMQConnectionFactory connectionFactory;
	/**
	 * 获得自己创建的链接工厂，这个工厂只初始化一次
	 */
	public static ActiveMQConnectionFactory getMyActiveMQConnectionFactory() {
		if (null == connectionFactory) {
			connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,ActiveMQConnection.DEFAULT_PASSWORD, "tcp://localhost:61616");
		}
		return connectionFactory;
	}

}
