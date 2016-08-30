package com.visionet.letsdesk.app.common.thread;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.GetterUtil;


public interface TheadConstants {
	
	public static final long DEFAULT_DELAY = GetterUtil.getLong(PropsUtil.getProperty(PropsKeys.DEFAULT_DAEMON_DELAY), 2000L);

	public static final long WAITING_DELAY = GetterUtil.getLong(PropsUtil.getProperty(PropsKeys.DEFAULT_WAITING_DELAY), 30000L);

	public static final int DEFAULT_QUEUE_SIZE = Integer.parseInt(PropsUtil.getProperty(PropsKeys.DEFAULT_QUEUE_SIZE));
	
//	public static final String Thread_SMS="SMS";
//	public static final String Thread_MAIL="MAIL";
	public static final String Thread_Allocate_Pay="AllocatePay";
	public static final String Thread_Allocate_Free="AllocateFree";

	public static final String THREAD_POOL_LARGE = "LARGE_POOL";
	public static final String THREAD_POOL_SMALL = "SMALL_POOL";
	public static final String THREAD_POOL_SINGLE_LIST = "SINGLE_POOL";

	public static final int DEFAULT_KEEP_ALIVE_SECONDS = Integer.parseInt(PropsUtil.getProperty(PropsKeys.DEFAULT_KEEP_ALIVE_SECONDS));
	
	public static final int large_corePoolSize = Integer.parseInt(PropsUtil.getProperty(PropsKeys.LARGE_CORE_POOL_SIZE));
	public static final int large_maxPoolSize = Integer.parseInt(PropsUtil.getProperty(PropsKeys.LARGE_MAX_POOL_SIZE));
	public static final int large_queueCapacity = Integer.parseInt(PropsUtil.getProperty(PropsKeys.LARGE_QUEUE_CAPACITY));
	

	public static final int small_corePoolSize = Integer.parseInt(PropsUtil.getProperty(PropsKeys.SMALL_CORE_POOL_SIZE));
	public static final int small_maxPoolSize = Integer.parseInt(PropsUtil.getProperty(PropsKeys.SMALL_MAX_POOL_SIZE));
	public static final int small_queueCapacity = Integer.parseInt(PropsUtil.getProperty(PropsKeys.SMALL_QUEUE_CAPACITY));
	
	
}
