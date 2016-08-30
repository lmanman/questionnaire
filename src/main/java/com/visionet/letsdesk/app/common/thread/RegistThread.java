package com.visionet.letsdesk.app.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MethodInvoker;


/**
 * 将收到的任务请求封装为MethodInvoker，放入队列中
 * @author XT
 */
public class RegistThread  implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(RegistThread.class);
	
	private static long TaskEnd1=0;
	
	private MethodInvoker methodInvoker;	
	
	/**
	 * 任务封装
	 * 被封装到队列中的任务，由守护线程处理
	 * @param target
	 * @param methodName
	 * @param args
	 */
	public RegistThread(Object target, String methodName, Object[] args){
		methodInvoker = new MethodInvoker();
		methodInvoker.setArguments(args);
		methodInvoker.setTargetObject(target);
		methodInvoker.setTargetMethod(methodName);
		
		TaskExecutorUtil.execute(this);
	}
	
	public RegistThread(String threadGroup,
			Object target, String methodName, Object[] args){
		
		methodInvoker = new MethodInvoker();
		methodInvoker.setArguments(args);
		methodInvoker.setTargetObject(target);
		methodInvoker.setTargetMethod(methodName);
		
		TaskExecutorUtil.execute(threadGroup, this);
		
	}
	

	public void run() {
		try {
			methodInvoker.prepare();
			methodInvoker.invoke();
		} catch (Exception e) {
			logger.error("RegistThread "+methodInvoker.getTargetClass().getName() + 
					" method " + methodInvoker.getTargetMethod() +" error:", e);
			Thread.currentThread().interrupt();
		}finally{
			logger.info("RegistThread: TaskEnd1 :" + addSelf());
		}
	}

    public static long addSelf(){
        return ++TaskEnd1;
    }
	


}
