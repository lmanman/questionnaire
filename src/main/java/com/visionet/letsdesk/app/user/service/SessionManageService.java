package com.visionet.letsdesk.app.user.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.foundation.Functions;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.UserDao;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service
public class SessionManageService {
    private static Logger log = LoggerFactory.getLogger(SessionManageService.class);
	
	@Autowired
    private SessionDAO sessionDAO;
    @Autowired
    private UserDao userDao;



    public List<User> getActiveUser(Long companyId,List<String> roleList){
        List<Long> idList = this.getActiveUserId(companyId,roleList);
        return userDao.findByIdList(idList);
    }


    /**
     * 获取在线客服Id
     * @param companyId
     * @param roleList - 只显示这些权限，空为全部
     * @return
     */
    public List<Long> getActiveUserId(Long companyId,List<String> roleList){
        Collection<Session> sessions =  sessionDAO.getActiveSessions();
        List<Long> result = Lists.newArrayList();
        for(Session session:sessions){

            String loginName = Functions.userName(session);
            if(Validator.isNull(loginName)){
                continue;
            }
            User user = userDao.findByLoginName(loginName);
            if(result.contains(user.getId())){
                continue;
            }
            if(user.getOrgId().longValue() != companyId.longValue()){
                continue;
            }

            boolean isAdmin = false;
            for (Role role : user.getRoleSet()) {
                if(role.getName().equals(SysConstants.SUBADMIN)||role.getName().equals(SysConstants.ADMINISTRATOR)){
                    isAdmin = true;
                    break;
                }
            }
            if(isAdmin){
                continue;
            }
            if(Collections3.isNotEmpty(roleList) && !AccountService.HasRole(user,roleList)){
                continue;
            }


            result.add(user.getId());

//            log.info("----onlineId="+user.getId());
        }

        return result;
    }

	
	public List<Map<String,String>> getActiveSessionMap(){
		Collection<Session> sessions =  sessionDAO.getActiveSessions();
		List<Map<String,String>> result = Lists.newArrayList();
        for(Session session:sessions){
        	
        	Map<String,String> map = Maps.newHashMap();
        	map.put("id",session.getId().toString());
        	String loginName = Functions.userName(session);
        	
        	if(Validator.isNull(loginName)){
        		continue;
        	}
        	map.put("loginName",loginName);
        	map.put("host",session.getHost());
        	map.put("loginTime",DateUtil.convertToString(session.getStartTimestamp()));
        	map.put("lastAccessTime",DateUtil.convertToString(session.getLastAccessTime()));
        	result.add(map);
        }
        
        return result;
	}

	public int getActiveSessionNum(){
		return getActiveSessionMap().size();
	}
	
	public boolean isActive(String loginName){
		Collection<Session> sessions =  sessionDAO.getActiveSessions();
        for(Session session:sessions){
        	String sessLoginName = Functions.userName(session);
        	if(sessLoginName.equals(loginName)){
        		return true;
        	}
        }
        return false;
	}

    public boolean isActive(Long userId){
        User user = userDao.findOne(userId);
        if(user ==null){
            return false;
        }
        //手机客服
        boolean mobile = user.getRoleSet().stream().anyMatch(r -> SysConstants.MOBILE_SERVICE.equals(r.getName()));
        if(mobile){
            return true;
        }

        return isActive(user.getLoginName());

    }
	
	
	public Session logoutSession(String sessionId){
		Session session = null;
		try {
			session = sessionDAO.readSession(sessionId);
            if(session != null) {
                session.setAttribute(SysConstants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
        } catch (Exception e) {
        	e.printStackTrace();
        }
		return session;
	}

}
