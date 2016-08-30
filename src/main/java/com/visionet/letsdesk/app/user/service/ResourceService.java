package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.user.entity.Resource;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.ResourceDao;
import com.visionet.letsdesk.app.user.repository.UserDao;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 资源 Service
 * 
 * @author xt
 */
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class ResourceService extends BaseService {
	private static Logger logger = LoggerFactory.getLogger(ResourceService.class);
    public static final ConcurrentHashMap<String,Long> LoginFrequency = new ConcurrentHashMap<String,Long>();

	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private UserDao userDao;


    @Transactional(readOnly = false)
    public void save(Resource entity) {
        resourceDao.save(entity);
    }
	
//	public List<Resource> findLeftMenuByUser(Long userId) {
//		if(BaseController.hasRole(SysConstants.ADMIN)){
//			return resourceDao.findLeftMenuByUser();
//		}else{
//			User user = userDao.findOne(userId);
//			List<String> permissions  = getPermissionList(user);
//			if(permissions.isEmpty()) return null;
//			
//			return resourceDao.findLeftMenuByUser(permissions);
//		}
//		
//	}

	public static List<String> getPermissionList(User user){
		Set<String> permSet = new HashSet<String>(); 
		for(Role role:user.getRoleSet()){
			String[] perms = StringUtils.split(role.getPermissions(), ",");
			for(String perm : perms){
				permSet.add(StringUtils.substringBefore(perm, ":"));
			}
		}
		return new ArrayList<String>(permSet);
	}
	
	@Transactional(readOnly = false)
	public void updateLastLoginOn(Long userId,Date lastLoginOn,String clientFlag) {
		try {

            User user = userDao.findOne(userId);
            user.setLastLogin(lastLoginOn);
            userDao.save(user);
		} catch (Exception e) {
			logger.error("updateLastLoginOn error:",e);
		}
		
	}


//    public static void CheckLoginFrequency(String username){
//        Long lastTime = LoginFrequency.get(username);
//        long now = DateUtil.getCurrentDate().getTime();
//        if(lastTime == null){
//            LoginFrequency.put(username,now);
//        }else {
//            int frequency = SystemDictService.GetLoginFrequency() ;
//            if(now - lastTime.longValue() <= frequency * 1000){
//                throwException(BusinessStatus.ACCESSDENIED, MessageSourceHelper.GetMessages("app.web.business.login.frequency", new Object[]{frequency + ""}));
//            }else{
//                LoginFrequency.put(username,now);
//            }
//        }
//    }

}
