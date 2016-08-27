package com.shenma.top.imagecopy.service;

import java.util.Date;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.broker.jmx.QueueViewMBean;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.top.imagecopy.dao.MqRecordDao;
import com.shenma.top.imagecopy.dao.MqRecordItemDao;
import com.shenma.top.imagecopy.entity.MqRecord;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.util.ActiveMqUtil;
import com.shenma.top.imagecopy.util.activemq.MQPooledConnectionFactory;
import com.taobao.api.ApiException;

@Service
public class ActiveMqService {
	protected static Logger logger = Logger.getLogger("ActiveMqService");
	@Autowired
	private MqRecordDao mqRecordDao;
	
	@Autowired
	private static  ConnectionFactory connectionFactory=MQPooledConnectionFactory.getMyActiveMQConnectionFactory();
	
	@Autowired
	private MqRecordItemDao mqRecordItemDao;
	
	public void sendMessage(List<String> urlList) throws ApiException{

        Connection connection = null;
        Session session=null;
        Destination destination;
        MessageProducer producer;
        try {
            // 构造从工厂得到连接对象
            connection = connectionFactory.createConnection();
            // 启动
            connection.start();
            // 获取操作连接
            session = connection.createSession(Boolean.TRUE,Session.AUTO_ACKNOWLEDGE); //1
             
            destination = session.createQueue(ActiveMqUtil.queueName);
            // 得到消息生成者【发送者】
            producer = session.createProducer(destination);
            // 设置不持久化，可以更改
            producer.setDeliveryMode(DeliveryMode.PERSISTENT); //2
            // 构造消息
            String userId=SessionUtil.getAliSession().getMemberId();
            QueueViewMBean qmb=ActiveMqUtil.getState();
            long syNum=qmb.getQueueSize();
            long yjNum=qmb.getDequeueCount();
            MqRecord object=new MqRecord(userId, syNum+yjNum,urlList.size(),0);
            object.setUrlList(urlList);
            // 发送消息到目的地方
            MqRecord entity=mqRecordDao.save(object);
            object.setId(entity.getId());
            for(String url:urlList){
            	MqRecordItem mqItem=new MqRecordItem();
            	mqItem.setUserId(userId);
            	mqItem.setRecordId(entity.getId());
            	mqItem.setUrl(url);
            	mqItem.setStatus(0);
            	mqItem.setPicStatus(0);
            	mqItem.setCreateTime(new Date());
            	mqRecordItemDao.save(mqItem);
            }
            ObjectMessage message = session.createObjectMessage(object);
            producer.send(message);
            session.commit();
        } catch (Exception e) {
        	logger.error(e);
            throw new ApiException("插入消息队列错误");
        } finally {
            try {
                if (null != connection)connection.close();
                if(null!=session)session.close();
            } catch (Throwable ignore) {
            }
        }
	}
}
