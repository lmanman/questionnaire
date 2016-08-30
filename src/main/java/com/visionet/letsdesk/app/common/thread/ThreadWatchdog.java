package com.visionet.letsdesk.app.common.thread;

import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.foundation.service.DashboardStatisticsService;
import com.visionet.letsdesk.app.user.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MethodInvoker;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 线程池监测及任务触发
 * @author xt
 */
public abstract class ThreadWatchdog extends Thread {
	protected static Logger log = LoggerFactory.getLogger(ThreadWatchdog.class);
	
	protected long delay = TheadConstants.DEFAULT_DELAY;
	private static long OutTaskQueue1=0;
	private static long OutTaskQueue2=0;
	private static long TaskEnd2=0;
	protected static AtomicBoolean TaskFlag = new AtomicBoolean(true);

	protected ThreadWatchdog() {
		setDaemon(true);
	}

	public void setDelay(long delay) {
		this.delay = delay;
	}

	abstract protected void doOnIdle(MethodInvoker invoker);
	abstract protected void doOnIdle(List<MethodInvoker> invokerList);
	abstract protected MethodInvoker getMethodInvoker();
	abstract protected List<BaseVo> getInitMessageList();

	/**
	 * 检查待处理线程队列和线程池状态(单向队列)
	 */
	private void checkAndDispatch(String poolName,LinkedBlockingQueue<MethodInvoker> queue) {
        try {
//            if (TaskExecutorUtil.WorkIdle(poolName)) {    //线程池有空闲
//				log.info("---queue.size="+queue.size());
//                MethodInvoker invoker = queue.take();
//				log.info("---msg="+((MessageVo)invoker.getArguments()[0]).getMessageContent());
//				//将待处理的单个任务包装为Runnable，放入对应线程池并发处理
//				Runnable runnable = () -> doOnIdle(invoker);
//				TaskExecutorUtil.execute(poolName, runnable);
//            } else {
//                log.warn("checkAndDispatch1 activeCount full : {} ",poolName);
//                this.nonExceptionSleep();
//            }
        } catch (Exception e) {
            log.error("ThreadWatchdog checkAndDispatch1 error:", e);
            this.nonExceptionSleep();
        }
	}

	/**
	 * 将db中的等待任务再次放入线程池中运行
	 */
	private void checkAndDispatch() {
//		try {
//			if(DashboardStatisticsService.GetChannelQueueNum()>0) {
////				getInitMessageList().parallelStream().forEach(vo -> {
//                getInitMessageList().forEach(vo -> {
//					String poolName = ThreadManage.GetThreadGroupName(OrganizationService.GetOrgPoolPayType(vo.getOrgId()));
//					if (TaskExecutorUtil.WorkIdle(poolName)) {    //线程池有空闲
//						log.debug("---talkId=" + vo.getId());
//						MethodInvoker invoker = getMethodInvoker();
//						invoker.setArguments(new Object[]{vo});
//						Runnable runnable = () -> doOnIdle(invoker);
//						TaskExecutorUtil.execute(poolName, runnable);
//					} else {
//						log.warn("checkAndDispatch2 activeCount full : {} ", poolName);
//					}
//				});
//			}
//		} catch (Exception e) {
//			log.error("ThreadWatchdog checkAndDispatch2 error:", e);
//		}finally {
//			this.nonExceptionSleep(TheadConstants.WAITING_DELAY);
//		}
	}


	/**
	 * 单线程处理队列中的任务，依次放入池中
	 * @param invokerList
	 */
	protected void queueTrigger(List<MethodInvoker> invokerList){
//		try {
//			invokerList.forEach(mi -> {
//				try{
////					log.info("ThreadManage: OutTaskQueue2 :" + (++OutTaskQueue2));
//					mi.prepare();
//					mi.invoke();
//				} catch (InvocationTargetException ite) {
//					log.error("ThreadManage: queueTrigger InvocationTargetException:", ite.getTargetException());
//
//					//如果是分配逻辑的异常
//					if(ite.getTargetException() instanceof AllocateRuntimeException){
//						AllocateRuntimeException are = (AllocateRuntimeException)ite.getTargetException();
//
//						//如果原业务需要更换队列
//						if(are.getMessage()!=null &are.getPara()!=null){
//							log.info("ThreadManage: Allocate msg:"+are.getMessage());
//							//设置新的入口参数
//							mi.setArguments(are.getPara());
//							//更换队列，到队尾重新排队
//							ThreadManage.PutMessageTaskQueue(are.getMessage(), mi, TheadConstants.THREAD_POOL_SINGLE_LIST);
//						}
//					}else{
//						log.error("ThreadManage: BlockingDeque queueTrigger MethodInvoker Error:", ite.getTargetException());
//					}
//
//				} catch (Exception e) {
//					log.error("ThreadManage: queueTrigger Exception:", e);
//				}finally{
//					log.debug("ThreadManage queueTrigger: TaskEnd2 :" + (++TaskEnd2));
//				}
//			});
//		} catch (Exception e) {
//			log.error("BlockingDeque queueTrigger loop Error:", e);
//		}
	}
	
	private void nonExceptionSleep(){
		try{
            Thread.currentThread().sleep(delay);
		}catch(InterruptedException e){
			log.error("ThreadWatchdog sleep error:",e);
		}
	}
	private void nonExceptionSleep(long delay2){
		try{
			Thread.currentThread().sleep(delay2);
		}catch(InterruptedException e){
			log.error("ThreadWatchdog sleep error:",e);
		}
	}

	/**
	 * 队列触发
	 */
	public void run() {
		this.nonExceptionSleep(TheadConstants.WAITING_DELAY);

		log.info("Thread name:{}  interval_milliscond:{}",this.getName(),this.delay);
		new Thread(() -> {
			System.out.println("---map.size="+ThreadManage.MessageQueueMap.size());
			ThreadManage.MessageQueueMap.forEachEntry(1, entry -> {
				log.info("TaskQueueMap --- {} = {}", entry.getKey(), entry.getValue());
				while (TaskFlag.get()) {
					checkAndDispatch(entry.getKey(), entry.getValue());
				}
			});
		}).start();

        new Thread(() -> {
            while (TaskFlag.get()) {
				checkAndDispatch();
            }
		}).start();

	}
}
