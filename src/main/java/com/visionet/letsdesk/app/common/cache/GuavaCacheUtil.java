package com.visionet.letsdesk.app.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.visionet.letsdesk.app.base.rest.RestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.TimeUnit;

@Component
public class GuavaCacheUtil {
    private static Logger logger = LoggerFactory.getLogger(GuavaCacheUtil.class);

    @PostConstruct
    public void init(){
        initCache1();
        initCache2();
//        GetCacheCustomer();
    }

    @PreDestroy
    public void  destroy(){
    }

    private static LoadingCache<String, String> cache1 =null;
    private static LoadingCache<String, String> cache2 =null;
//    private static LoadingCache<Long, CustomerInfo> cache3 =null;

    private static synchronized void initCache1() throws RestException {
        // 设置缓存最大个数为50000，缓存过期时间为110分钟
        cache1 = CacheBuilder.newBuilder().maximumSize(50000)
                .expireAfterWrite(110, TimeUnit.MINUTES).build(new CacheLoader<String,String>() {
                    @Override
                    public String load(String key) throws Exception {
//                        if(key.startsWith(WeixinConstantKey.AUTHORIZER_ACCESS_TOKEN)) {
//                            return findWeixinToken(key);
//                        }else {
                            return null;
//                        }
                    }
                });
    }

    private static synchronized void initCache2() throws RestException {
        // 设置缓存最大个数为5000，缓存过期时间为18分钟
        cache2 = CacheBuilder.newBuilder().maximumSize(5000)
                .expireAfterWrite(18, TimeUnit.MINUTES).build(new CacheLoader<String,String>() {
                    @Override
                    public String load(String key) throws Exception {
//                        return queryRemindCount(key);
                        return null;
                    }
                });
    }

//    private static synchronized void GetCacheCustomer() throws RestException {
//        // 设置缓存最大个数为50000，缓存过期时间为4小时
//        cache3 = CacheBuilder.newBuilder().maximumSize(50000)
//                .expireAfterAccess(4, TimeUnit.HOURS).build(new CacheLoader<Long,CustomerInfo>() {
//                    @Override
//                    public CustomerInfo load(Long key) throws Exception {
//                        return findCustomer(key);
//                    }
//                });
//    }

//    private static CustomerInfo findCustomer(Long id){
//        if(id==null){
//            return null;
//        }
//        CustomerInfoDao customerInfoDao = (CustomerInfoDao)SpringContextUtil.getBean("customerInfoDao");
//        return customerInfoDao.findOne(id);
//    }
//    private static String findWeixinToken(String appid){
//        if(appid==null){
//            return null;
//        }
//        appid = appid.replaceFirst(WeixinConstantKey.AUTHORIZER_ACCESS_TOKEN,"");
//
//        WeixinAuthorizerTokenDao weixinAuthorizerTokenDao = (WeixinAuthorizerTokenDao)SpringContextUtil.getBean("weixinAuthorizerTokenDao");
//        WeixinAuthorizerToken token = weixinAuthorizerTokenDao.findByAuthorizerAppid(appid);
//        if(token==null){
//            return null;
//        }
////        logger.debug("---token.getAuthorizerAccessToken="+token.getAuthorizerAccessToken());
//        return token.getAuthorizerAccessToken();
//    }

//    public static CustomerInfo getCustomer(Long id) throws RestException{
//        if(cache3==null){
//            throw new RestException("Guava cache3 is null!");
//        }
//        CustomerInfo value=null;
//        try {
//            value = cache3.get(id);
//        }catch(Exception e){
//            logger.warn(e.toString());
//        }
//        return value;
//    }


    public static String get(String key) throws RestException{
        if(cache1==null){
            throw new RestException("Guava cache1 is null!");
        }
        String value=null;
        try {
            value = cache1.get(key);
        }catch(Exception e){
            logger.warn(e.toString());
        }
        return value;
    }

    public static void set(String key,String value){
        try {
            if (cache1 == null) {
                throw new RestException("Guava cache1 is null!");
            }
            cache1.put(key, value);
        }catch (Exception e){
            logger.error("GuavaCache refreshRemind error:",e);
        }
    }


    public static String get2(String key) throws RestException{
        if(cache2==null){
            throw new RestException("Guava cache2 is null!");
        }
        String value=null;
        try {
            value = cache2.get(key);
        }catch(Exception e){
            logger.warn(e.toString());
        }
        return value;
    }

    public static void set2(String key,String value){
        try {
            if (cache2 == null) {
                throw new RestException("Guava cache2 is null!");
            }
            cache2.put(key, value);
        }catch (Exception e){
            logger.error("GuavaCache refreshRemind error:",e);
        }
    }


    public static void refresh(String key){
        try {
            if (cache1 != null) {
                cache1.refresh(key);
            }
            if (cache2 != null) {
                cache2.refresh(key);
            }

        }catch (Exception e){
            logger.error("GuavaCache refreshRemind error:",e);
        }
    }
    public static void clear(){
        try {
            if (cache1!= null) {
                cache1.invalidateAll();
            }
            if (cache2!= null) {
                cache2.invalidateAll();
            }

        }catch (Exception e){
            logger.error("GuavaCache clearRemind error:",e);
        }
    }


    public static void main(String[] args) {
        try{
//            CustomerInfo customerInfo =  getCustomer(1L);
//            System.out.println(customerInfo.getCustomerName());
//            set("test","value1");
//            for(int i=0;i<30;i++){
//                System.out.println("----"+i+"----"+get("test"));
//                Thread.sleep(10000);
//            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
