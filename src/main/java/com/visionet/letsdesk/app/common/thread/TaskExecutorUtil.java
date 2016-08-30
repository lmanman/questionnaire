package com.visionet.letsdesk.app.common.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * 线程池
 * @author XT
 *
 */
public class TaskExecutorUtil {
	private static Logger logger = LoggerFactory.getLogger(TaskExecutorUtil.class);

	private static Map<String,ExecutorService> taskExecutors = new ConcurrentHashMap<String, ExecutorService>();
//	private static Map<String,ExecutorService> queueExecutors = new ConcurrentHashMap<String, ExecutorService>();
	private static ExecutorService taskExecutor;

	private static Lock lock = new ReentrantLock();

	protected synchronized static void register(){
		if(taskExecutor == null) {
			taskExecutor = Executors.newWorkStealingPool();
			logger.info("Create newWorkStealingPool");
		}
	}
	protected synchronized static void register(String threadGroup){
		if(taskExecutors.get(threadGroup) == null){
			taskExecutors.put(threadGroup, Executors.newScheduledThreadPool(ThreadManage.ThreadPoolMap.get(threadGroup)[1]));
			logger.info("Create newScheduledThreadPool,threadName:"+threadGroup);
		}
	}

	protected static void execute(Runnable runnable){
		getDynamicInstance().execute(runnable);
	}

	protected static void execute(String threadGroup, Runnable runnable){
		getInstance(threadGroup).execute(runnable);
	}

	protected static void executeBatch(String theadGroup, List<Callable<String>> callables) throws InterruptedException {
		getInstance(theadGroup).invokeAll(callables)
				.stream()
				.map(future -> {
					try {
						return future.get();
					} catch (Exception e) {
						throw new IllegalStateException(e);
					}
				})
				.forEach(System.out::println);
	}

//	public static void regist(String theadGroup,String queueName, Runnable runnable){
//		getSingleInstance("_"+queueName).execute(runnable);
//	}

	/**
	 * Spring thread pool
	 * @param threadGroup
	 * @return
	 */
//	private static TaskExecutor getSpringInstance(String threadGroup){
//		try {
//			lock.lock();
//			if(taskExecutors.get(threadGroup) == null){
//				ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//				taskExecutor.setCorePoolSize(ThreadManage.ThreadPoolMap.get(threadGroup)[0]);
//				taskExecutor.setMaxPoolSize(ThreadManage.ThreadPoolMap.get(threadGroup)[1]);
//				taskExecutor.setQueueCapacity(ThreadManage.ThreadPoolMap.get(threadGroup)[2]);
//				taskExecutor.initialize();
//				taskExecutors.put(threadGroup, taskExecutor);
//				logger.info("Create thread pool,threadGroup:" + threadGroup);
//			}
//			lock.unlock();
//		}finally {
//            lock.unlock();
//        }
//		return taskExecutors.get(threadGroup);
//	}

	/**
	 * 创建一个最大线程数目固定的线程池，该线程池用一个共享的无界队列来存储提交的任务
	 * @param threadGroup
	 * @return
	 */
	private static ExecutorService getInstance(String threadGroup){
		try {
            lock.lock();
            if(taskExecutors.get(threadGroup) == null){
				taskExecutors.put(threadGroup, Executors.newScheduledThreadPool(ThreadManage.ThreadPoolMap.get(threadGroup)[1]));
                logger.info("Create newScheduledThreadPool,threadName:"+threadGroup);
            }
		}finally {
			lock.unlock();
		}
		return taskExecutors.get(threadGroup);
	}


	/**
	 * 只有一个线程的线程池
	 * @param threadName
	 * @return
	 */
	private static ExecutorService getSingleInstance(String threadName){
		try {
            lock.lock();
            if(taskExecutors.get(threadName) == null){
				taskExecutors.put(threadName, Executors.newSingleThreadExecutor());
                logger.info("Create Single thread pool,threadName:"+threadName);
            }
		}finally {
			lock.unlock();
		}
		return taskExecutors.get(threadName);
	}
	
	/**
	 * 默认使用当前机器可用的CPU个数作为并行数
	 * @return
	 */
	private static ExecutorService getDynamicInstance(){
		try{
            lock.lock();
            if(taskExecutor == null) {
                taskExecutor = Executors.newWorkStealingPool();
                logger.info("Create newWorkStealingPool");
            }
		}finally {
			lock.unlock();
		}
		return taskExecutor;
	}
	
	
	
	public static int activeCount(String threadGroup){
		if(taskExecutors.get(threadGroup) == null) {
			return 0;
		}
		return ((ThreadPoolExecutor)(taskExecutors.get(threadGroup))).getActiveCount();
	}
	
	/**
	 * check poll whether full
	 * @param threadGroup
	 * @return idle num;
	 */
	public static int WorkIdleNum(String threadGroup){
		int active = activeCount(threadGroup);
		int max = ThreadManage.ThreadPoolMap.get(threadGroup)[1];
		return max - active;
	}


	/**
	 * 判断某线程池是否有空闲线程
	 * @param threadGroup
	 * @return
	 */
	public static boolean WorkIdle(String threadGroup){
		if(taskExecutors.get(threadGroup) == null){
			return false;
		}
		
		if(WorkIdleNum(threadGroup) > 0){
			return true;
		}else{
			return false;
		}
	}
	

	
	public static void destroy(String threadGroup){
		if(taskExecutors.get(threadGroup) != null){
			if(taskExecutors.get(threadGroup) instanceof ThreadPoolTaskExecutor){
				(taskExecutors.get(threadGroup)).shutdown();
			}
			taskExecutors.remove(threadGroup);
		}
		if(taskExecutor != null) {
			taskExecutor.shutdown();
			taskExecutor=null;
		}

	}
	
	public static void destroyAll(){
		taskExecutors.forEach((key, value) -> destroy(key));
	}

}
