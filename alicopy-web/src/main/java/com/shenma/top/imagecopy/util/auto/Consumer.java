package com.shenma.top.imagecopy.util.auto;


import java.io.IOException;
import java.util.concurrent.BlockingQueue;

/***
 * 消费者
 * **/
public class Consumer extends Thread {
	/***
	 * 利用队列存储样品
	 * */
	 private BlockingQueue<String> bq;
	 public Consumer() {
		// TODO Auto-generated constructor stub
	}
	public Consumer(BlockingQueue<String> bq) {
		super();
		this.bq = bq;
	 
	}
	 
	
	@Override
	public void run() {
		boolean state=true;
		while(true){
			//System.out.println(getName()+"消费者准备消费集合元素");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  
			if(state==true){
				state=false;
				try{
					
					//尝试取出元素，如果队列为空，则被线程阻塞
					String url=bq.take();
					System.out.println("开始消费url:"+url);
					ParseOnePage onePage=new ParseOnePage();
					onePage.parse(url);
					state=true;
				}catch(Exception e){
					e.printStackTrace();
					state=true;
				}
			}
		}
		
	}
	
	
}

