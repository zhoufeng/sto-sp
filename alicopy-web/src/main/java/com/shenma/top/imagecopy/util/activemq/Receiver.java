package com.shenma.top.imagecopy.util.activemq;


import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
 
public class Receiver {
    public static void main(String[] args) {
        // ConnectionFactory ：连接工厂，JMS 用它创建连接
        ConnectionFactory connectionFactory;
        // Connection ：JMS 客户端到JMS Provider 的连接
        Connection connection = null;
        // Session： 一个发送或接收消息的线程
        final Session session;
        // Destination ：消息的目的地;消息发送给谁.
        Destination destination;
        // 消费者，消息接收者
//        MessageConsumer 接口（消息消费者） 由会话创建的对象，用于接收发送到目标的消息。
//        消费者可以同步地（阻塞模式），或异步（非阻塞）接收队列和主题类型的消息。
        MessageConsumer consumer;
 
        connectionFactory = new ActiveMQConnectionFactory(
                ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD,
                "tcp://localhost:61616");
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //queue_name跟sender的保持一致，一个创建一个来接收
            destination = session.createQueue(RunServer.queueName);
            consumer = session.createConsumer(destination);
            
//          //第一种情况
//          int i = 0;
//          while (i < 3) {
//              i++;
//              TextMessage message = (TextMessage) consumer.receive();
//              session.commit();
//              // TODO something....
//              System.out
//                      .println("收到消息：" +message.getText());
//          }
//          session.close();
//          connection.close();
            //----------------第一种情况结束----------------------
//          第二种方式
            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message arg0) {
                    if(arg0 instanceof TextMessage){
                        try {
                            System.out.println("arg0="+((TextMessage)arg0).getText());
                            session.commit();
                        } catch (JMSException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            //第三种情况
//           while (true) {
//            Message msg = consumer.receive(1000);
//            TextMessage message = (TextMessage) msg;
//            if (null != message) { 
//               System.out.println("收到消息:" + message.getText());
//            } 
//        }
        } catch (Exception e) {
            e.printStackTrace();
        }
      }
    }