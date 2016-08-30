package com.visionet.letsdesk.app.common.mobilepush;

import cn.jpush.api.JPushClient;
import cn.jpush.api.push.MessageResult;
import cn.jpush.api.push.NotificationParams;
import cn.jpush.api.push.ReceiverTypeEnum;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.common.thread.RegistThread;
import com.visionet.letsdesk.app.common.thread.TheadConstants;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Component
public class MobilePush {

	
	private static BlockingQueue<PushMsg> PushUserQueue = new ArrayBlockingQueue<PushMsg>(
			Integer.valueOf(PropsUtil.getProperty(PropsKeys.DEFAULT_QUEUE_SIZE)));
	
	private static BlockingQueue<PushMsg> PushTeamQueue = new ArrayBlockingQueue<PushMsg>(1000);
	
	private static final long TIMETOLIVE = 864000;
	
	private static Logger logger = LoggerFactory.getLogger(MobilePush.class);
	
	private static JPushClient jpush = new JPushClient(PropsUtil.getProperty(PropsKeys.MOBILESEND_MASTERSECERT), PropsUtil.getProperty(PropsKeys.MOBILESEND_APPKEY));
	
	private static Lock lock = new ReentrantLock();
	
	public static void push(){
//		logger.error("jpush mastersecert:" + PropsUtil.getProperty(PropsKeys.MOBILESEND_MASTERSECERT) + "\njpush mobilesend_appkey:" + PropsUtil.getProperty(PropsKeys.MOBILESEND_APPKEY));
		if(PushUserQueue.size() > 0 || PushTeamQueue.size() > 0){
			logger.info("mobile push start!\nPushUserQueue.size:" + PushUserQueue.size() + "\nPushTeamQueue.size:" + PushTeamQueue.size());
		}

		try{
            lock.lock();
			while(PushUserQueue.size() > 0) {

				PushMsg msg = PushUserQueue.take();
				try{
					NotificationParams params = new NotificationParams();
					params.setReceiverType(ReceiverTypeEnum.ALIAS);
                    params.setReceiverValue(GetPrefix()+msg.getAlias());
					
					params.setSendNo(msg.getSendNo());
					params.setTimeToLive(TIMETOLIVE);
					params.setAndroidNotificationTitle(msg.getTitle());

//                    logger.info("--content="+msg.getContent());
//                    logger.info("--getTitlet="+msg.getTitle());
//                    logger.info("--getReceiverValuet="+params.getReceiverValue());
//                    logger.info("--getSendNo="+msg.getSendNo());
//                    Map<String, Object> extra = msg.getExtra();
//                    Map<String,String> iosMap = (Map<String,String>)extra.get("ios");
//                    if(iosMap!=null){
//                        logger.info(extra.get("uid")+"----badge="+iosMap.get("badge"));
//                    }else{
//                        logger.info(extra.get("uid")+"----iosMap="+null);
//                    }

//					MessageResult result = jpush.sendNotification(msg.getContent(), params, msg.getExtra());
//					logger.info("jpush result errmsg:" + result.errmsg);
					new RegistThread(jpush, "sendNotification", new Object[]{msg.getContent(), params,msg.getExtra()});
					
				}catch(Exception e){
					logger.error("mobile push fail for exception!",e);
				}
				
			}
		}catch(Exception e){
			logger.error("MobilePush.push error: ",e);
		}finally {
            lock.unlock();
        }

		
		
		//群聊信息
		try{
            lock.lock();
			while(PushTeamQueue.size() > 0) {
				PushMsg msg = PushTeamQueue.take();
				try{
					NotificationParams params = new NotificationParams();
					params.setReceiverType(ReceiverTypeEnum.TAG);
                    params.setReceiverValue(GetPrefix()+msg.getAlias());

					params.setSendNo(msg.getSendNo());
					params.setTimeToLive(TIMETOLIVE);
					params.setAndroidNotificationTitle(msg.getTitle());
					
					MessageResult mr = jpush.sendNotification(msg.getContent(), params, msg.getExtra());
                    logger.warn("code:" + mr.errcode + "   " + "msg:" + mr.getErrorMessage());
//					new RegistThread(TheadConstants.Thread_MAIL,jpush, "sendNotification", new Object[]{msg.getContent(), params, msg.getExtra()});

				}catch(Exception e){
					logger.error("PushTeam fail for errcode!",e);
				}
			}
		}catch(Exception e){
			logger.error("mobile team push fail for exception!",e);
		}finally {
            lock.unlock();
        }

		
	}

	
	
	public static void addPushMsg(String type,String alias,String msgContent,Map<String, Object> extra,Integer subclass){
		addPushMsg(type, alias, MessageSourceHelper.GetMessages("chat.message.news.user"), msgContent, extra,subclass);
	}
	
	public static void addPushMsg(String type,String alias,String msgContent,Integer subclass){
		addPushMsg(type, alias, MessageSourceHelper.GetMessages("chat.message.news.user"), msgContent,null,subclass);
	}
	public static void addPushMsg(String type,String alias,String msgContent,Map<String, Object> extra){
		addPushMsg(type, alias, MessageSourceHelper.GetMessages("chat.message.news.user"), msgContent, extra,null);
	}

	/**
	 * @param type 通知类型 
	 * @param alias 用户别名
	 * @param title 通知标题
	 * @param msgContent 通知内容
	 * @param extra 额外数据
	 */
	public static void addPushMsg(String type, String alias, String title, String msgContent,
			Map<String, Object> extra,Integer subclass) {
		try {
			PushMsg msg = new PushMsg();
			msg.setSendNo(1);
			msg.setAlias(alias);
			msg.setTitle(title);
			msg.setContent(msgContent);

            SetExtraMap(extra);

			extra.put("type", type);
			
			msg.setExtra(extra);

			PushUserQueue.put(msg);
		} catch (Exception e) {
			logger.error("MobilePush.addPushMsg error: ",e);
		}
		
		
	}
	
	/**
	 * @param type 通知类型 
	 * @param alias 用户别名
	 * @param title 通知标题
	 * @param msgContent 通知内容
	 * @param extra 额外数据
	 */
	public static void addPushMsg(int type, String alias, String title, String msgContent,
			Map<String, Object> extra) {
		try {
			PushMsg msg = new PushMsg();
			msg.setSendNo(1);
			msg.setAlias(alias);
			msg.setTitle(title);
			msg.setContent(msgContent);

            SetExtraMap(extra);
			
			if(!extra.containsKey("type")){
				extra.put("type", type);
			}
			
			msg.setExtra(extra);

			PushUserQueue.put(msg);
		} catch (Exception e) {
			logger.error("MobilePush.addPushMsg error: ",e);
		}
		
		
	}
	
	/**
	 * 群聊信息推送
	 * @param type
	 * @param orgId
	 * @param title
	 * @param msgContent
	 * @param extra
	 */
	public void addOrgPushMsg(int type, Long orgId, String title,
            String msgContent,Map<String, Object> extra) {
		try {
            PushMsg msg = new PushMsg();
            msg.setSendNo(2);
            msg.setTag(orgId.toString());
            msg.setTitle(title);
            msg.setContent(msgContent);

            SetExtraMap(extra);
            extra.put("type", type);
            msg.setExtra(extra);

            PushTeamQueue.put(msg);

            logger.warn("addOrgPushMsg finish!");

        } catch (Exception e) {
			logger.error("MobilePush.addTeamPushMsg error: ",e);
		}
	}

    public static void SetExtraMap(Map<String, Object> extra){
        if(extra == null){
            extra = Maps.newHashMap();
            Map<String,String> iosMap = Maps.newHashMap();
            iosMap.put("sound", "default");
            iosMap.put("badge", "+1");
            extra.put("ios", iosMap);
        }else{
            Map<String,String> iosMap = (Map<String,String>)extra.get("ios");
            if(iosMap==null){
                iosMap = Maps.newHashMap();
                iosMap.put("sound", "default");
            }
            iosMap.put("badge", "+1");
            extra.put("ios", iosMap);
        }
    }
	
	public static boolean CheckTzhTest(){
		String flag = GetPrefix();
        if(Validator.isNull(flag)){
            return false;
        }else{
            return true;
        }
	}

    public static String GetPrefix(){
        String prefix = PropsUtil.getProperty(PropsKeys.MOBILESEND_PREFIX);
        if(Validator.isNull(prefix)){
            return "";
        }else{
            return prefix;
        }
    }
}
