package com.visionet.letsdesk.app.common.thread;


import com.visionet.letsdesk.app.base.vo.BaseVo;
import com.visionet.letsdesk.app.common.cache.GuavaCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MethodInvoker;

import java.util.List;


/**
 * 启动守护线程<br>
 * 定义子线程触发事件
 * @author xt
 */
@Component
public class DaemonServer {
	protected static Logger log = LoggerFactory.getLogger(DaemonServer.class);

	@Autowired
	private GuavaCacheUtil guavaCacheUtil;	//关联自动启动init
	@Autowired
	private ThreadManage threadManage;	//关联自动启动initThread方法(优先)！
	
	private static long TaskEnd1=0;

	public void init() throws Exception {
		ProcessWatchDog dog = new ProcessWatchDog();
		dog.setName("Daemon Server");
		dog.setDelay(TheadConstants.DEFAULT_DELAY);
		dog.start();
	}

	public void  destroy(){
		ThreadWatchdog.TaskFlag.set(false);
		System.out.println("~~~destroy DaemonServer!");
	}
	
	
	private class ProcessWatchDog extends ThreadWatchdog {
		public ProcessWatchDog(){
			log.info("~~~ProcessWatchDog Start!");
		}
		
		@Override
		protected void doOnIdle(MethodInvoker invoker) {
			try {
				invoker.prepare();
				invoker.invoke();
			} catch (Exception e) {
				log.error("ThreadManage: RegistThread "+invoker.getTargetClass().getName() + 
						" method " + invoker.getTargetMethod() +" error ", e.getCause());
				Thread.currentThread().interrupt();
			}finally{
				log.debug("ThreadManage: TaskEnd1 :" + (++TaskEnd1));
			}
		}
		
		@Override
		protected void doOnIdle(List<MethodInvoker> invokerList) {
			try {
				super.queueTrigger(invokerList);
			} catch (Exception e) {
				log.error(e.getMessage());
				Thread.currentThread().interrupt();
			}
		}

		@Override
		protected MethodInvoker getMethodInvoker() {
			MethodInvoker invoker = new MethodInvoker();
//			invoker.setTargetObject(allocateService);
//			invoker.setTargetMethod("allocateTalk");
			return invoker;
		}

		@Override
		protected List<BaseVo> getInitMessageList(){
//			List<Talk> talkList = talkDao.findByStatus(KeyWord.SESSION_STATUS_INIT);
//			log.debug("---waiting---talkList.size=" + talkList.size());
//			return MessageService.GenerateByTalk(talkList);
			return null;
		}
	}

}
