package com.visionet.letsdesk.app.common.redis;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.GetterUtil;
import com.visionet.letsdesk.app.common.modules.redis.JedisUtils;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisPoolFactory {

	public static JedisPool createJedisPool(String defaultHost, int defaultPort, int defaultTimeout, int threadCount) {
		// 合并命令行传入的系统变量与默认值
		String host = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_HOST), defaultHost);
		String port = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_PORT), String.valueOf(defaultPort));
		String timeout = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_TIMEOUT), String.valueOf(defaultTimeout));

		// 设置Pool大小，设为与线程数等大，并屏蔽掉idle checking
		JedisPoolConfig poolConfig = JedisUtils.createPoolConfig(threadCount, threadCount);

		// create jedis pool
		return new JedisPool(poolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout));
	}
	
	public static JedisPool createJedisPool(String defaultHost, int defaultPort, 
			int defaultTimeout, int threadCount,int checkingIntervalSecs,int idleTimeSecs) {
		// 合并命令行传入的系统变量与默认值
		String host = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_HOST), defaultHost);
		String port = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_PORT), String.valueOf(defaultPort));
		String timeout = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.REDIS_TIMEOUT), String.valueOf(defaultTimeout));

		// 设置Pool大小，设为与线程数等大，并设置执行idle checking的间隔和idle时间.
		JedisPoolConfig poolConfig = JedisUtils.createPoolConfig(threadCount, threadCount,checkingIntervalSecs,idleTimeSecs);

		// create jedis pool
		return new JedisPool(poolConfig, host, Integer.valueOf(port), Integer.valueOf(timeout));
	}
}
