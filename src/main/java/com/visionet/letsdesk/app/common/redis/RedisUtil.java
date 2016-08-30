package com.visionet.letsdesk.app.common.redis;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.visionet.letsdesk.app.common.modules.mapper.JsonMapper;
import com.visionet.letsdesk.app.common.modules.redis.JedisTemplate;
import com.visionet.letsdesk.app.common.modules.redis.JedisUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisUtil {
	
	private static Logger _log = LoggerFactory.getLogger(RedisUtil.class);
	private static JsonMapper mapper = JsonMapper.nonDefaultMapper();

	private static final int DEFAULT_THREAD_COUNT = Integer.parseInt(PropsUtil.getProperty(PropsKeys.DEFAULT_THREAD_COUNT));
	private static final String HOST = PropsUtil.getProperty(PropsKeys.REDIS_HOST);
	private static final String REDIS_KEY = PropsUtil.getProperty(PropsKeys.REDIS_KEY);
	
	private static JedisPool pool = JedisPoolFactory.createJedisPool(JedisUtils.DEFAULT_HOST, JedisUtils.DEFAULT_PORT,JedisUtils.DEFAULT_TIMEOUT, DEFAULT_THREAD_COUNT);
	public static JedisTemplate jedisTemplate = new JedisTemplate(pool);
	
	
//	@PostConstruct
	public void setUp() {
		if(!needRedis()) return;
		try{
			// 清空数据库
			//jedisTemplate.flushDB();
			
			if(jedisTemplate!=null){
				refreshRemind();
			}
		}catch(Exception e){
			_log.error(e.getMessage());
		}
	}
	
//	@PreDestroy
	public void tearDown() {
		if(!needRedis()) return;
		pool.destroy();
	}
	
	/**
	 * 退出然后关闭Jedis连接。如果Jedis为null则无动作。
	 */
	public static void closeJedis(Jedis jedis) {
		if ((jedis != null) && jedis.isConnected()) {
			try {
				try {
					jedis.quit();
				} catch (Exception e) {
				}
				jedis.disconnect();
			} catch (Exception e) {
			}
		}
	}
	
	public static boolean needRedis(){
		if(HOST == null || HOST.isEmpty()){
			_log.warn("redis HOST is null! ");
			return false;
		}else{
			return true;
		}
	}
	
	

	/**
	 * 更新提醒数据
	 * jedis.set [key,value] : [TZH_type_userId,count]
	 */
	private synchronized void refreshRemind(){
	}
	
	/**
	 * 获取某类型的提醒数
	 * @param key
	 * @return
	 */
	public static Long getRemindNumber(String key){
		if(!needRedis()) return null;
		try{
			Long num = jedisTemplate.getAsLong(REDIS_KEY+key);
			return num == null ? 0L : num;
		}catch(Exception e){
			_log.error(e.toString());
			return null;
		}finally{
			closeJedis(pool.getResource());
		}
	}
	
	/**
	 * 设置某类型的提醒数
	 * @param key
	 * @param num
	 */
	public synchronized static void setRemindNumber(String key,Long num){
		if(!needRedis()) return;
		try{
			jedisTemplate.set(REDIS_KEY + key, num + StringPool.BLANK);
		}catch(Exception e){
			_log.error(e.getMessage());
		}finally{
			closeJedis(pool.getResource());
		}
	}
	
	/**
	 * 某类型的提醒数自+1
	 * @param key
	 */
	public synchronized static void incrRemindNumber(String key){
		if(!needRedis()) return;
		try{
			jedisTemplate.incr(REDIS_KEY+key);
		}catch(Exception e){
			_log.error(e.getMessage());
		}finally{
			closeJedis(pool.getResource());
		}
	}
	
	/**
	 * 某类型的提醒数  减 num，最小为0
	 * @param key
	 * @param num
	 */
	public synchronized static void decrRemindNumber(String key,long num){
		if(!needRedis()) return;
		try{
			jedisTemplate.getJedisPool().getResource().decrBy(REDIS_KEY + key, num);
			
			if(getRemindNumber(key) < 0){
				setRemindNumber(key, 0L);
			}
		}catch(Exception e){
			_log.error(e.getMessage());
		}finally{
			closeJedis(pool.getResource());
		}
	}
	
	/**
	 * 根据手机号查归属地
	 * @param phoneNumber
	 * @return cityName
	 */
	public static String getCityByPhone(String phoneNumber){
		if(!needRedis()) return null;
		try{
			return jedisTemplate.get(phoneNumber);
		}catch(Exception e){
			_log.error(e.getMessage());
			return null;
		}finally{
			closeJedis(pool.getResource());
		}
	}
	
	public synchronized static void setCityByPhone(String phoneNumber,String cityName){
		if(!needRedis()) return;
		try{
			jedisTemplate.set(phoneNumber,cityName);
		}catch(Exception e){
			_log.error(e.getMessage());
		}finally{
			closeJedis(pool.getResource());
		}
	}


}
