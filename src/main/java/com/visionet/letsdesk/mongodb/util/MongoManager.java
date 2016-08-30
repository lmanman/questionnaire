package com.visionet.letsdesk.mongodb.util;

import com.mongodb.*;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.GetterUtil;
import com.visionet.letsdesk.mongodb.domain.Pagination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.net.UnknownHostException;
import java.util.List;

public class MongoManager {
	private static Logger _log = LoggerFactory.getLogger(MongoManager.class);

	public static final String DB_NAME = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.MONGODB_NAME_HOST),"slothdb2");

	private static MongoClient mongo;

    static{
        String host = GetterUtil.getString(PropsUtil.getProperty(PropsKeys.MONGODB_POOL_HOST), "localhost");
        int port = GetterUtil.getInteger(PropsUtil.getProperty(PropsKeys.MONGODB_POOL_PORT), 27017);	// 端口
        int poolSize = GetterUtil.getInteger(PropsUtil.getProperty(PropsKeys.MONGODB_POOL_POOLSIZE), 10);// 连接数量
        int blockSize = GetterUtil.getInteger(PropsUtil.getProperty(PropsKeys.MONGODB_POOL_BLOCKSIZE), 100); // 等待队列长度
        // 初始化
        init(host,port,poolSize,blockSize);
    }
	
	private static String USER_ACTION_LOG = "userActionLog";
	private static String SYSTEM_ERROR_LOG = "sysErrorLog";
	private static String USER_NICKNAME = "userNickname";
	private static String REMIND = "remind";
	
	private MongoManager() {
		 
    }

	/**
     * 根据名称获取DB，相当于是连接
     * 
     * connectionsPerHost：每个主机的连接数
     * threadsAllowedToBlockForConnectionMultiplier：线程队列数，它以上面connectionsPerHost值相乘的结果就是线程队列最大值。
     * 	如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
     * maxWaitTime:最大等待连接的线程阻塞时间
     * connectTimeout：连接超时的毫秒。0是默认和无限
     * socketTimeout：socket超时。0是默认和无限
     * autoConnectRetry：这个控制是否在一个连接时，系统会自动重试
     *
     * @return
     */
	public static DB getDB() throws UnknownHostException,MongoException{
		return mongo.getDB(DB_NAME);
	}

	

	private static void init(final String ip, int port, int poolSize,int blockSize){
		try {
            if (mongo == null) {
                mongo = new MongoClient(ip, port);
                MongoClientOptions options = mongo.getMongoClientOptions();
//			options.autoConnectRetry = true;
//			options.connectionsPerHost = poolSize;
//			options.threadsAllowedToBlockForConnectionMultiplier = blockSize;
                _log.info("mongo init! host:"+ip+",port:"+port+",ConnectionsPerHost:"+options.getConnectionsPerHost());
            }
        }catch (Exception e){
            _log.error(e.toString(),e);
        }

	}
	
	public static DBCollection getUserLogUser()
			throws UnknownHostException,MongoException{
		return getDB().getCollection(USER_ACTION_LOG);

	}
	public static DBCollection getErrorLogUser()
			throws UnknownHostException,MongoException{
		return getDB().getCollection(SYSTEM_ERROR_LOG);

	}
	public static DBCollection getUserNickname()
			throws UnknownHostException,MongoException{
		return getDB().getCollection(USER_NICKNAME);
	}
	
	public static DBCollection getRemind()
			throws UnknownHostException,MongoException,Exception{
		return getDB().getCollection(REMIND);
	}
	
	public static <T> Page<T> GetPageByList(Pagination page,List<T> volist,Class<T> destinationClass){
		return new PageImpl<T>(volist, new PageRequest(page.getCurrentPage(),page.getPageSize()), page.getTotalNumber());
	}
	
}