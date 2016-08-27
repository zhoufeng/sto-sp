package com.shenma.top.imagecopy.util;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import net.rubyeye.xmemcached.exception.MemcachedException;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.jmx.BrokerViewMBean;
import org.apache.activemq.broker.jmx.ManagementContext;
import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.activemq.store.kahadb.KahaDBStore;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.shenma.aliutil.entity.goods.Offer;
import com.shenma.aliutil.service.AliToken;
import com.shenma.aliutil.util.AliConstant;
import com.shenma.aliutil.util.SessionUtil;
import com.shenma.aliutil.util.memcache.MemCachedUtil;
import com.shenma.top.imagecopy.dao.MqRecordDao;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.entity.MqRecord;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.service.AliBaBaSaveService;
import com.shenma.top.imagecopy.util.activemq.MQPooledConnectionFactory;
import com.shenma.top.imagecopy.util.activemq.RunServer;

//@Component
public class ActiveMqUtil implements InitializingBean {
	protected static Logger logger = Logger.getLogger("ActiveMqUtil");
	public static String jmxDomain = "jms-broker";
	public static int connectorPort = 2011;
	public static String connectorPath = "/jmxrmi";
	
	public static String queueName = "alicopy-task";
	
	public static String bindAddress="tcp://localhost:61616";
	
	@Autowired
	private AliBaBaSaveService aliBaBaSaveService;
	@Autowired
	private MqRecordDao mqRecordDao;
	
	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	private static String activemqDir="activemq/amq-in-action/kahadb";
	
	@Autowired
	private AliConstant aliConstant;
	
	// ConnectionFactory ：连接工厂，JMS 用它创建连接
    private static ConnectionFactory connectionFactory=MQPooledConnectionFactory.getMyActiveMQConnectionFactory();
	
    @Autowired
	private MemCachedUtil memCachedUtil;
	@Override
	public void afterPropertiesSet() throws Exception {
		// java代码调用activemq相关的类来构造并启动brokerService
	       BrokerService broker = new BrokerService();

	       // 以下是持久化的配置
	       // 持久化文件存储位置
	       activemqDir=(String)CustomerPropertyPlaceholderConfigurer.getContextProperty("activemqDir");
	       File dataFilterDir = new File(activemqDir);
	       KahaDBStore kaha = new KahaDBStore();
	       kaha.setDirectory(dataFilterDir);
	       // use a bigger journal file
	       kaha.setJournalMaxFileLength(1024*100);
	       // small batch means more frequent and smaller writes
	       kaha.setIndexWriteBatchSize(100);
	       // do the index write in a separate thread
	       kaha.setEnableIndexWriteAsync(true);
	       
	       broker.setPersistenceAdapter(kaha);
	       // create a transport connector
	       broker.addConnector(bindAddress);
	       broker.setUseJmx(true);
	       

	       // 以下是ManagementContext的配置，从这个容器中可以取得消息队列中未执行的消息数、消费者数、出队数等等
	       // 设置ManagementContext
	       ManagementContext context = broker.getManagementContext();
	       context.setConnectorPort(connectorPort);
	       context.setJmxDomainName(jmxDomain);
	       context.setConnectorPath(connectorPath);
	       broker.start();
	       receive();
	       
	}
	private  void receive(){

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
 
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE);
            //queue_name跟sender的保持一致，一个创建一个来接收
            destination = session.createQueue(queueName);
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
                    if(arg0 instanceof ObjectMessage){
                        try {
                        	ObjectMessage obj=(ObjectMessage) arg0;
                        	MqRecord mqRecord=(MqRecord) obj.getObject();
                        	for(String url:mqRecord.getUrlList()){
                        		 
                        		AliToken info=memCachedUtil.get(aliConstant.APP_KEY+"_"+mqRecord.getUserId());
                        		SessionUtil.setAliSession(info);
                        		MqRecord record=mqRecordDao.findOne(mqRecord.getId());
                        		record.setTaskOfferCount(record.getTaskOfferCount()+1);
                        		mqRecordDao.saveAndFlush(record);
                        		try {
                        			MqRecordItem entity=mqRecordItemDao.findByRecordIdAndUrl(mqRecord.getId(), url);
									Offer offer=aliBaBaSaveService.save(url,entity.getPicStatus()==1);
									entity.setOfferId(offer.getOfferId().toString());
									entity.setTitle(offer.getSubject());
									entity.setStatus(1);
									mqRecordItemDao.saveAndFlush(entity);
								} catch (Exception e) {
									logger.error(e);
									MqRecordItem entity=mqRecordItemDao.findByRecordIdAndUrl(mqRecord.getId(),url);
									entity.setErrorMsg(e.toString().substring(0,e.toString().length()>99?99:e.toString().length()));
									entity.setStatus(2);
									mqRecordItemDao.saveAndFlush(entity);
								}
                        	}
                        } catch (JMSException | TimeoutException | InterruptedException | MemcachedException | IOException e) {
                        	logger.error(e);
                        	try {
								session.rollback();
							} catch (JMSException e1) {
								logger.error(e);
							}
                        }finally{
                        	try {
								session.commit();
							} catch (JMSException e) {
								logger.error(e);
							}
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
	
	
	public static QueueViewMBean getState() throws IOException, MalformedObjectNameException{
		JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:"+
                RunServer.connectorPort+RunServer.connectorPath);
        //Map<String, String[]> environment = new HashMap<String, String[]>();
		// 用户名密码，在jmxremote.password文件中的密码
		//String[] credentials = new String[] { "admin", "activemq" };
		//environment.put("jmx.remote.credentials", credentials);
        JMXConnector connector = JMXConnectorFactory.connect(url, null);
        connector.connect();
        
        MBeanServerConnection connection = connector.getMBeanServerConnection();
 
         // 需要注意的是，这里的jms-broker必须和上面配置的名称相同
        ObjectName name = new ObjectName(RunServer.jmxDomain+":BrokerName=localhost,Type=Broker");
        BrokerViewMBean mBean =  (BrokerViewMBean)MBeanServerInvocationHandler.newProxyInstance(connection,  
                name, BrokerViewMBean.class, true);
        for(ObjectName queueNameObj : mBean.getQueues()) {
            QueueViewMBean queueMBean =  (QueueViewMBean)MBeanServerInvocationHandler
                        .newProxyInstance(connection, queueNameObj, QueueViewMBean.class, true);
            if(queueMBean.getName().equals(queueName)){
            	return queueMBean;
            }
        }
        return null;
	}
}
