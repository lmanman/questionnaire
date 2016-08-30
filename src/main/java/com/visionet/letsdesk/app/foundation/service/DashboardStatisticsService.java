package com.visionet.letsdesk.app.foundation.service;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class DashboardStatisticsService {
    private static Logger log = LoggerFactory.getLogger(DashboardStatisticsService.class);

    //客户当前会话Id <customerId,talkId>
    private static final ConcurrentHashMap<Long, Long> CustomerTalkingIdMap = new ConcurrentHashMap<>();
    //客服当前会话数 <kefuId,<channelId,talkNum>>
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long,AtomicInteger>> KefuTalkingNumMap = new ConcurrentHashMap<>();
    //各渠道排队数 <orgId,<channelId,queueNum>>
    private static final ConcurrentHashMap<Long, ConcurrentHashMap<Long,AtomicInteger>> ChannelQueueNumMap = new ConcurrentHashMap<>();
    //客户当前排队时间 <WaitingEntity>
    private static final ArrayBlockingQueue<WaitingEntity> CustomerWaitingQueue = new ArrayBlockingQueue<>(100000);


    public static class WaitingEntity {
        private Long customerId;
        private Long timestamp;
        private Long orgId;
        private Long channelId;

        WaitingEntity(Long customerId, Long channelId, Long orgId,Long timestamp) {
            this.customerId = customerId;
            this.timestamp = timestamp;
            this.orgId = orgId;
            this.channelId = channelId;
        }

        public Long getCustomerId() {
            return customerId;
        }

        public void setCustomerId(Long customerId) {
            this.customerId = customerId;
        }

        public Long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Long timestamp) {
            this.timestamp = timestamp;
        }

        public Long getOrgId() {
            return orgId;
        }

        public void setOrgId(Long orgId) {
            this.orgId = orgId;
        }

        public Long getChannelId() {
            return channelId;
        }

        public void setChannelId(Long channelId) {
            this.channelId = channelId;
        }

//        public String getCustomerName(){
//            return UserCache.getCustomerName(this.getCustomerId());
//        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof WaitingEntity)) return false;

            WaitingEntity entity = (WaitingEntity) o;

            if (!customerId.equals(entity.customerId)) return false;
            if (!orgId.equals(entity.orgId)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = customerId.hashCode();
            result = 31 * result + orgId.hashCode();
            return result;
        }
    }

    /**
     * 查找某客户未结束会话
     * @param customerId
     * @return
     */
    public static Long GetCustomerTalkingId(Long customerId) {
        return CustomerTalkingIdMap.get(customerId);
    }
    /**
     * 添加某客户未结束会话
     * @param customerId
     * @param talkId
     */
    public static void AddCustomerTalkingId(Long customerId,Long talkId) {
        CustomerTalkingIdMap.put(customerId,talkId);
    }

    /**
     * 移除客户会话（会话关闭）
     * @param customerId
     */
    public static void RemoveCustomerTalkingId(Long customerId) {
        CustomerTalkingIdMap.remove(customerId);
    }

    public static ConcurrentHashMap<Long, Long> GetCustomerTalkingId() {
        return CustomerTalkingIdMap;
    }



    /**
     * 查找某客服当前正在进行的会话数
     * @param kefuId
     * @return
     */
    public static int GetKefuTalkingNum(Long kefuId) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = KefuTalkingNumMap.get(kefuId);
        if(channelMap!=null){
            return channelMap.reduce(1,(key, value) ->value.get(),(a, b) -> a + b);
        }
        return 0;
    }
    /**
     * 查找某客服某渠道，当前正在进行的会话数
     * @param kefuId
     * @param channelId
     * @return
     */
    public static int GetKefuTalkingNum(Long kefuId,Long channelId) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = KefuTalkingNumMap.get(kefuId);
        if(channelMap!=null){
            return channelMap.getOrDefault(channelId,new AtomicInteger(0)).get();
        }
        return 0;
    }
    /**
     * 客服&渠道,会话加入
     * @param kefuId
     * @param channelId
     */
    public static void AddKefuTalkingNum(Long kefuId,Long channelId,int num) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = KefuTalkingNumMap.get(kefuId);
        if(channelMap!=null){
            AtomicInteger ai = channelMap.get(channelId);
            if(ai==null){
                channelMap.put(channelId,new AtomicInteger(num));
            }else {
                ai.addAndGet(num);
            }
        }else{
            KefuTalkingNumMap.put(kefuId,new ConcurrentHashMap<Long,AtomicInteger>(){{
                put(channelId,new AtomicInteger(num));
            }});
        }
    }
    public static void AddKefuTalkingNum(Long kefuId,Long channelId) {
        AddKefuTalkingNum(kefuId,channelId,1);
    }


    /**
     * 客服&渠道,会话结束
     * @param kefuId
     * @param channelId
     */
    public static void SubtractionKefuTalkingNum(Long kefuId,Long channelId) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = KefuTalkingNumMap.get(kefuId);
        if(channelMap!=null){
            AtomicInteger ai = channelMap.get(channelId);
            if(ai==null || ai.get()==0){
                channelMap.put(channelId,new AtomicInteger(0));
            }else {
                channelMap.get(channelId).addAndGet(-1);
            }
        }
    }


    /**
     * sum所有公司的排队数
     * @return
     */
    public static int GetChannelQueueNum() {
        ConcurrentHashMap<Long,ConcurrentHashMap<Long,AtomicInteger>> orglMap = ChannelQueueNumMap;
        if(!orglMap.isEmpty()){
            return orglMap.reduce(1, (key, value) -> value.reduce(1, (key2, value2) -> value2.get(), (a2, b2) -> a2 + b2), (a, b) -> a + b);
        }
        return 0;
    }
    public static int GetChannelQueueNum(Long orgId) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = ChannelQueueNumMap.get(orgId);
        if(channelMap!=null){
            return channelMap.reduce(1, (key, value) -> value.get(), (a, b) -> a + b);
        }
        return 0;
    }
    public static int GetChannelQueueNum(Long orgId,Long channelId) {
        ConcurrentHashMap<Long,AtomicInteger> channelMap = ChannelQueueNumMap.get(orgId);
        if(channelMap!=null){
            return channelMap.getOrDefault(channelId,new AtomicInteger(0)).get();
        }
        return 0;
    }
    public static ConcurrentHashMap<Long,AtomicInteger> GetChannelQueueMap(Long orgId) {
        return ChannelQueueNumMap.get(orgId);
    }
    public static List<WaitingEntity> GetCustomerWaitingList(Long orgId){
        List<WaitingEntity> list = Lists.newArrayList();
        CustomerWaitingQueue.iterator().forEachRemaining(e -> {
            if(e.orgId.longValue() == orgId.longValue()){
                list.add(e);
            }
        });
        return list;
    }
    public static List<WaitingEntity> GetCustomerWaitingList(Long orgId,Long channelId){
        List<WaitingEntity> list = Lists.newArrayList();
        CustomerWaitingQueue.iterator().forEachRemaining(e -> {
            if(e.orgId.longValue() == orgId.longValue() && e.channelId.longValue()==channelId.longValue()){
                list.add(e);
            }
        });
        return list;
    }

    /**
     * 排队+1
     * @param orgId
     * @param channelId
     */
    public static synchronized void AddChannelQueueNum(Long orgId,Long channelId,Long customerId,int num){
        try {
            WaitingEntity entity = new WaitingEntity(customerId, channelId, orgId,DateUtil.getCurrentDate().getTime());
            if (!CustomerWaitingQueue.contains(entity)) {
                CustomerWaitingQueue.put(entity);

                ConcurrentHashMap<Long, AtomicInteger> channelMap = ChannelQueueNumMap.get(orgId);
                if (channelMap != null) {
                    AtomicInteger ai = channelMap.get(channelId);
                    if (ai == null) {
                        channelMap.put(channelId, new AtomicInteger(num));
                    } else {
                        ai.addAndGet(num);
                    }
                } else {
                    ChannelQueueNumMap.put(orgId, new ConcurrentHashMap<Long, AtomicInteger>() {{
                        put(channelId, new AtomicInteger(num));
                    }});
                }
            }
        }catch (Exception e){
            log.error("AddChannelQueueNum error!",e);
        }
    }
    public static void AddChannelQueueNum(Long orgId,Long channelId,Long customerId) {
        AddChannelQueueNum(orgId,channelId,customerId,1);
    }

    /**
     * 排队-1
     * @param orgId
     * @param channelId
     */
    public static synchronized void SubtractionChannelQueueNum(Long orgId,Long channelId,Long customerId) {
        WaitingEntity entity = new WaitingEntity(customerId, channelId, orgId,DateUtil.getCurrentDate().getTime());
        if(CustomerWaitingQueue.contains(entity)) {
            CustomerWaitingQueue.remove(entity);
            ConcurrentHashMap<Long, AtomicInteger> channelMap = ChannelQueueNumMap.get(orgId);
            if (channelMap != null) {
                AtomicInteger ai = channelMap.get(channelId);
                if (ai == null || ai.get() == 0) {
                    channelMap.put(channelId, new AtomicInteger(0));
                } else {
                    channelMap.get(channelId).addAndGet(-1);
                }
            }
        }
    }

    /**
     * 将统计各数据，从DB同步到缓存
     */
    public void initStatisData(){

    }



}
