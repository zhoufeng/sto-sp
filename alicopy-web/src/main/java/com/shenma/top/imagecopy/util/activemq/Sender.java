package com.shenma.top.imagecopy.util.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {
    private static final int SEND_NUMBER = 2;
 
    public static void main(String[] args) {
//      ConnectionFactory 接口（连接工厂） 用户用来创建到JMS提供者的连接的被管对象。
//      JMS客户通过可移植的接口访问连接，这样当下层的实现改变时，代码不需要进行修改。
//      管理员在JNDI名字空间中配置连接工厂，这样，JMS客户才能够查找到它们。
//      根据消息类型的不同，用户将使用队列连接工厂，或者主题连接工厂。
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
//        Connection 接口（连接） 连接代表了应用程序和消息服务器之间的通信链路。
//        在获得了连接工厂后，就可以创建一个与JMS提供者的连接。根据不同的连接类型，
//        连接允许用户创建会话，以发送和接收队列和主题到目标。
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
//      Session 接口（会话） 表示一个单线程的上下文，用于发送和接收消息。
//      由于会话是单线程的，所以消息是连续的，就是说消息是按照发送的顺序一个一个接收的。
//      会话的好处是它支持事务。如果用户选择了事务支持，会话上下文将保存一组消息，直到事务被提交才发送这些消息。
//      在提交事务之前，用户可以使用回滚操作取消这些消息。一个会话允许用户创建消息生产者来发送消息，创建消息消费者来接收消息。
        Session session;
        // Destination ：消息的目的地;消息发送给谁.
//        Destination 接口（目标） 目标是一个包装了消息目标标识符的被管对象，
//        消息目标是指消息发布和接收的地点，或者是队列，或者是主题。
//        JMS管理员创建这些对象，然后用户通过JNDI发现它们。
//        和连接工厂一样，管理员可以创建两种类型的目标，点对点模型的队列，以及发布者/订阅者模型的主题。
        Destination destination;
        // MessageProducer：消息发送者
//        MessageProducer 接口（消息生产者） 由会话创建的对象，用于发送消息到目标。
//        用户可以创建某个目标的发送者，也可以创建一个通用的发送者，在发送消息时指定目标。
        MessageProducer producer;
        // TextMessage message;
        // 构造ConnectionFactory实例对象，此处采用ActiveMq的实现jar
 
        connectionFactory = new ActiveMQConnectionFactory(
        		ActiveMQConnection.DEFAULT_USER, //null
        		ActiveMQConnection.DEFAULT_PASSWORD, //null
                "tcp://localhost:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,
                    Session.AUTO_ACKNOWLEDGE); //1
             
            destination = session.createQueue(RunServer.queueName);
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置不持久化，可以更改
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); //2
            // 构造消息
            sendMessage(session, producer);
            session.commit();
 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }
 
    }
 
    public static void sendMessage(Session session, MessageProducer producer)
            throws Exception {
        for (int i = 1; i <= SEND_NUMBER; i++) {
            TextMessage message = session
                    .createTextMessage("ActiveMq 发送的消息" + i);
            // 发送消息到目的地方
            System.out.println("发送消息:" + i);
            producer.send(message);
        }
    }
     
}