package com.visionet.letsdesk.app.common.thread;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.rest.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 线程管理类<br>
 * 初始化各任务分组，map对应线程池<br>
 * 多线程申请入口，调用任务封装
 * @author xt
 */
@Component
public class ThreadManage {
	private static Logger logger = LoggerFactory.getLogger(ThreadManage.class);
	
	public static final Map<String,String> GroupNameMap = Maps.newHashMap();
	public static final Map<String,int[]> ThreadPoolMap = Maps.newHashMap();
	//初始消息任务队列 <threadGroupName,Queue>
	public static final ConcurrentHashMap<String,LinkedBlockingQueue<MethodInvoker>> MessageQueueMap = new ConcurrentHashMap<>();

	public static final int[] LargePoolConfig = {TheadConstants.large_corePoolSize,TheadConstants.large_maxPoolSize,TheadConstants.large_queueCapacity};
	public static final int[] SmallPoolConfig = {TheadConstants.small_corePoolSize,TheadConstants.small_maxPoolSize,TheadConstants.small_queueCapacity};

	private static long IntoTaskQueue1=0;
	private static long IntoTaskQueue2=0;

	@PostConstruct
	public void initThread(){
		logger.info("~~~init ThreadManage !");
		setThreadGroup();
		setThreadName();

		ThreadPoolMap.forEach((key,value) -> TaskExecutorUtil.register(key));
	}
	
	@PreDestroy
	public void  destroyThread(){
		TaskExecutorUtil.destroyAll();
		logger.info("~~~destroy ThreadManage !");
    }

	/**
	 * 竞争任务bean，用来处理单线程任务队列
	 */
	protected static class QueueEntity {
		String dequeName;
		MethodInvoker invoker;

		QueueEntity(String dequeName, MethodInvoker invoker) {
			this.dequeName = dequeName;
			this.invoker = invoker;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof QueueEntity)) return false;

			QueueEntity that = (QueueEntity) o;

			if (!dequeName.equals(that.dequeName)) return false;

			return true;
		}

		@Override
		public int hashCode() {
			return dequeName.hashCode();
		}
	}
	
	protected static void setThreadGroup(){
		//set 2 pool
		ThreadPoolMap.put(TheadConstants.THREAD_POOL_LARGE, LargePoolConfig);				//大池
		ThreadPoolMap.put(TheadConstants.THREAD_POOL_SMALL, SmallPoolConfig);				//小池

		//set 2 BlockingQueue Map
		MessageQueueMap.put(TheadConstants.THREAD_POOL_LARGE, new LinkedBlockingQueue<MethodInvoker>());
		MessageQueueMap.put(TheadConstants.THREAD_POOL_SMALL, new LinkedBlockingQueue<MethodInvoker>());
		
	}
	
	protected static void setThreadName(){
//		GroupNameMap.put(TheadConstants.Thread_SMS, TheadConstants.THREAD_POOL_LARGE);				//SMS接口 -- 大池
		GroupNameMap.put(TheadConstants.Thread_Allocate_Pay, TheadConstants.THREAD_POOL_LARGE);		//付费公司 -- 大池
		GroupNameMap.put(TheadConstants.Thread_Allocate_Free, TheadConstants.THREAD_POOL_SMALL);	//免费公司 -- 小池

	}

	/**
	 * 根据业务标志，选择对应线程池
	 * @param threadName
	 * @return
	 */
	protected static String GetThreadGroupName(String threadName) {
		String tm=GroupNameMap.get(threadName);
		if(tm == null){
			logger.warn("ThreadManage: thread name not define:"+threadName);
			tm = TheadConstants.THREAD_POOL_SMALL;
		}
//		logger.info("ThreadManage: Apply thread name:"+threadName);
		
		return tm;
	}
	

	/**
	 * 线程申请入口方法
	 * @param target
	 * @param methodName
	 * @param args
	 * @throws Exception
	 */
	public static void threadAssign(String threadGroup,Object target, String methodName, Object... args) throws Exception{
//		logger.info("ThreadManage: target="+target);
		MessageQueueAssign(threadGroup, target, methodName, args);
	}


	/**
	 * 队列线程申请入口方法;
	 * 由客服坐席分配逻辑调用;
	 * 队列数据由 DaemonServer.init() 触发处理;
	 * @param threadName
	 * @param target
	 * @param methodName
	 * @param args
	 * @throws Exception
	 */
	public static void ThreadMessageQueueAssign(String threadName, Object target, String methodName,Object... args) throws InterruptedException{
//		logger.info("ThreadMessageQueueAssign: target="+target);
		MessageQueueAssign(GetThreadGroupName(threadName), target, methodName, args);
	}
	/**
	 * 任务封装
	 * 被封装到队列中的任务，由守护线程处理
	 * @param target
	 * @param methodName
	 * @param args
	 */
	private static void MessageQueueAssign(String threadGroup,Object target, String methodName, Object... args) throws InterruptedException{

		MethodInvoker methodInvoker = new MethodInvoker();
		methodInvoker.setArguments(args);
		methodInvoker.setTargetObject(target);
		methodInvoker.setTargetMethod(methodName);

		PutMessageTaskQueue(methodInvoker, threadGroup);
	}
	/**
	 * 将任务放入队列
	 * @param methodInvoker
	 * @param methodInvoker
	 */
	private static void PutMessageTaskQueue(MethodInvoker methodInvoker,String threadGroup) throws InterruptedException{
		BlockingQueue queue= ThreadManage.MessageQueueMap.get(threadGroup);
		if(queue==null){
			throw new RestException("threadGroup queue:"+threadGroup+" is null!");
		}
		queue.put(methodInvoker);
//		logger.info("ThreadManage: IntoTaskQueue1:"+ (++IntoTaskQueue1) +"  queue.size():" +queue.size());
	}


	protected static void PutMessageTaskQueue(String queueName,MethodInvoker methodInvoker,String threadGroup){
		try{
			BlockingQueue queue = ThreadManage.MessageQueueMap.get(threadGroup);

			queue.put(new QueueEntity(queueName, methodInvoker));	//有资源竞争的任务，做单线程排队
//			logger.info("ThreadManage: IntoTaskQueue2:" + (++IntoTaskQueue2) + "  queue.size():" + queue.size());
		}catch(Exception e){
			e.printStackTrace();
		}
	}



	
}
