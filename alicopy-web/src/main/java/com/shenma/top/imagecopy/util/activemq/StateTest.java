package com.shenma.top.imagecopy.util.activemq;

import java.util.HashMap;
import java.util.Map;

import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.QueueViewMBean;

public class StateTest {
 
    /**
     * 获取状态
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:"+
                RunServer.connectorPort+RunServer.connectorPath);
        Map<String, String[]> environment = new HashMap<String, String[]>();
		// 用户名密码，在jmxremote.password文件中的密码
		String[] credentials = new String[] { "admin", "activemq" };
		environment.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        
        MBeanServerConnection connection = connector.getMBeanServerConnection();
 
         // 需要注意的是，这里的jms-broker必须和上面配置的名称相同
        ObjectName name = new ObjectName(RunServer.jmxDomain+":BrokerName=localhost,Type=Broker");
        BrokerViewMBean mBean =  (BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection,  
                name, BrokerViewMBean.class, true);
        // System.out.println(mBean.getBrokerName());
         
        for(ObjectName queueName : mBean.getQueues()) {
            QueueViewMBean queueMBean =  (QueueViewMBean)MBeanServerInvocationHandler
                        .newProxyInstance(connection, queueName, QueueViewMBean.class, true);
            System.out.println("\n------------------------------\n");
 
            // 消息队列名称
            System.out.println("States for queue --- " + queueMBean.getName());
 
            // 队列中剩余的消息数
            System.out.println("Size --- " + queueMBean.getQueueSize());
 
            // 消费者数
            System.out.println("Number of consumers --- " + queueMBean.getConsumerCount());
 
            System.out.println("ProducerCount " +queueMBean.getProducerCount());
            // 出队数
            System.out.println("Number of dequeue ---" + queueMBean.getDequeueCount() );
        }
         
        }
 
    }