package com.shenma.top.imagecopy.util.aop;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.shenma.aliutil.util.SessionUtil;
import com.shenma.top.imagecopy.entity.MqRecordItem;
import com.shenma.top.imagecopy.util.exception.BusinessException;

//声明该类为一个切面
@Aspect 
@Component
public class FreeUserInterceptor {
	public static Map<String,Integer> freeCount=new ConcurrentHashMap<String,Integer>();
	public static Integer maxCount=20;
	  //切入点要拦截的类
    @Pointcut("execution(* com.shenma.top.imagecopy.service.AlibabafinalSaveService.save(..))")
    private void aliMethod(){} //声明一个切入点,切入点的名称其实是一个方法

    @Pointcut("execution(* com.shenma.top.imagecopy.service.TaobaoFinalSaveService.save(..))")
    private void taobaoMethod(){} //声明一个切入点,切入点的名称其实是一个方法

    
    //前置通知(获取输入参数)
    @Before("aliMethod() && args(url,isSavaPic,mqItem)")//第一个参数为切入点的名称,第二个是测试获取输入参数，此处为string类型的，参数名称与方法中的名称相同,如果不获取输入参数，可以不要
    public void doAccessCheck(String url, boolean isSavaPic, MqRecordItem mqItem){
        System.out.println("前置通知:"+url);
    }
    
    //环绕通知（特别适合做权限系统）
    @Around("aliMethod() && args(url,isSavaPic,mqItem)")
    public Object doAliProfiling(ProceedingJoinPoint pjp,String url, boolean isSavaPic, MqRecordItem mqItem) throws Throwable{
    	Object object=null;
    	if(isSavaPic&&isFullCount()){
    		throw new BusinessException("401","您是活动用户,复制带图片宝贝的数量限制为20,不带图片的没有限制!");
    	}else{
    		object=pjp.proceed();
    	}
        return object;
    }
    
    //环绕通知（特别适合做权限系统）
    @Around("taobaoMethod() && args(url,categoryId,isSavaPic,mqItem)")
    public Object doTaobaoProfiling(ProceedingJoinPoint pjp,String url,String categoryId, boolean isSavaPic, MqRecordItem mqItem) throws Throwable{
    	Object object=null;
    	if(isSavaPic&&SessionUtil.getAliSession().getUserLevel()<1&&isFullCount()){
    		throw new BusinessException("401","您是活动用户,复制带图片宝贝的数量限制为20,不带图片的没有限制!");
    	}else{
    		object=pjp.proceed();
    	}
    	addCountOne();
        return object;
    }
    
    private boolean isFullCount(){
    	String memberId=SessionUtil.getAliSession().getMemberId();
    	if(freeCount.containsKey(memberId)){
    		Integer count=freeCount.get(memberId);
    		if(count<maxCount){
    			return false;
    		}else{
    			return true;
    		}
    	}else{
    		freeCount.put(memberId, 1);
    		return false;
    	}
    	
    }
    private void addCountOne(){
    	String memberId=SessionUtil.getAliSession().getMemberId();
    	if(freeCount.containsKey(memberId)){
    		Integer count=freeCount.get(memberId);
    		freeCount.put(memberId, count+1);
    	}
    }
}
