package com.visionet.letsdesk.app.common.cache;

import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.foundation.entity.SystemDict;
import com.visionet.letsdesk.app.foundation.repository.SystemDictDao;
import com.visionet.letsdesk.app.foundation.service.DashboardStatisticsService;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.UserDao;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EhcacheUtil {
	
	private static Logger log = LoggerFactory.getLogger(EhcacheUtil.class);
	private static final String CACHE_NAME = "sloth2Cache";
	private static final String USER_KEY = "user_";
	private static final String CHANNEL_KEY = "channel_";
	public static final String DICT_KEY = "dict_";

//    public static final String PLUG_KEY = "plug_";
//    public static final String DEPT_KEY = "dept_";


    @Qualifier("sloth2Cache")
    @Autowired(required = true)
    private CacheManager ehcacheManager;

    @Autowired
    private UserDao userDao;
	@Autowired
	private SystemDictDao systemDictDao;
	@Autowired
	private DashboardStatisticsService dashboardStatisticsService;
//    @Autowired
//    private SystemPluginDao systemPluginDao;



	
	private static Cache cache;
	
	public void initData(){
		cache = ehcacheManager.getCache(CACHE_NAME);

		this.initUser();
        this.initDictList();
		dashboardStatisticsService.initStatisData();

    }
	
	public void  destroyData(){
		cache.removeAll();
    }
	
	/**
	 * 设置值
	 * @param key
	 * @param value
	 */
	public static synchronized void set(String key, Object value){
		
		try{
			Element element = new Element(key, value);
			cache.put(element);
		}catch(Exception e){
			log.error("cahce set error:"+e.toString());
		}
	}
	
	/**
	 * 获取所有Key
	 * @return
	 */
	public static List<?> getKeys(){
		try{
			return cache.getKeys();
		}catch(Exception e){
			log.error("cahce getKeys error:"+e.toString());
			return null;
		}
	}
	
	/**
	 * 删除
	 * @param key
	 */
	public static synchronized void remove(String key){
		 
		try{
			cache.remove(key);
		}catch(Exception e){
			log.error("cahce remove error:"+e.toString());
		}
	}
	
	/**
	 * 获取
	 * @param key
	 * @return
	 */
	public static String get(String key){
		
		try{
			Element element = cache.get(key);
			return element.getObjectValue()==null?null:element.getObjectValue().toString();
		}catch(Exception e){
			return null;
		}
		
	}
	
	public static Object getObjectVal(String key){
		try{
			Element element = cache.get(key);
			return element.getObjectValue()==null?null:element.getObjectValue();
		}catch(Exception e){
			return null;
		}
	}



	public void initUser(){
		List<User> userList = (List<User>)userDao.findAll();
		User all = new User();
		all.setId(0L);
		all.setAliasName(SysConstants.ADMINISTRATOR);
		userList.add(all);
		for(User user:userList){
			set(USER_KEY+user.getId(), user);
		}
		log.info("userList.size:{}",userList.size());
	}

    public static User GetUser(Long id) {
        if(id == null) return null;

        try {
            Element element = cache.get(USER_KEY+id);
            return (User)element.getObjectValue();
        } catch (Exception e) {
            log.error("EhcacheUtil GetUser error:"+e.toString() + " id:"+id);
            return null;
        }
    }
	public static void SetUser(User user){
		EhcacheUtil.set(EhcacheUtil.USER_KEY + user.getId(), user);
	}



    public void initDictList(){
        List<SystemDict> dictList = systemDictDao.findDictList();
        for(SystemDict dict:dictList){
            set(DICT_KEY+dict.getSystemType()+ StringPool.UNDERLINE+dict.getSystemCode(), dict.getSystemName());
        }
        log.debug("dicList.size:{}",dictList.size());
    }

    public static String GetSystemDictName(String systemType,String systemCode) {
        if(systemType == null||systemCode==null) return null;

        try {
            Element element = cache.get(DICT_KEY+systemType+StringPool.UNDERLINE+systemCode);
            return (String)element.getObjectValue();
        } catch (Exception e) {
            log.error("EhcacheUtil GetSystemDictName error:"+e.toString() + " systemType:"+systemType+ " systemCoded:"+systemCode);
            return null;
        }
    }

//    public static PublicWeixin GetWeixin(String account,Long companyId) {
//        if(account == null||companyId==null) return null;
//
//        try {
//            Element element = cache.get(WEIXIN_KEY+account+StringPool.UNDERLINE+companyId);
//            return (PublicWeixin)element.getObjectValue();
//        } catch (Exception e) {
//            log.error("EhcacheUtil GetWeixin error:"+e.toString() + " account:"+account+ " companyId:"+companyId);
//            return null;
//        }
//    }
//
//    public static void SetWeixinCache(PublicWeixin weixin) {
//        set(WEIXIN_KEY+weixin.getWeixinAccount()+ StringPool.UNDERLINE + weixin.getCompanyId(), weixin);
//    }


//    public static String GetPlugUrl(String code){
//        if(Validator.isNull(code)) return null;
//        try {
//            return (String)get(PLUG_KEY+code);
//        } catch (Exception e) {
//            log.debug("CacheService GetPlugUrl error:"+e.toString()+ " code:"+code);
//            return null;
//        }
//    }


//    public static String GetDept(Long id){
//        if(id==null || id==0) return null;
//        try {
//            return (String)get(DEPT_KEY+id);
//        } catch (Exception e) {
//            log.error("CacheService GetDept error:"+e.toString()+ " id:"+id);
//            return null;
//        }
//    }

}
