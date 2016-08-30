package com.visionet.letsdesk.app.foundation.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.UserDao;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.foundation.Functions;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Service
public class SessionService {
    private static Log log = LogFactory.getLog(SessionService.class);
    
	
	@Autowired
    private SessionDAO sessionDAO;
	@Autowired
    private UserDao userDao;
	
	public List<Map<String,String>> getActiveSessionMap(){
		Collection<Session> sessions =  sessionDAO.getActiveSessions();
		List<Map<String,String>> result = Lists.newArrayList();
        for(Session session:sessions){
        	
        	Map<String,String> map = Maps.newHashMap();
        	map.put("id",session.getId().toString());
        	String loginName = Functions.userName(session);
        	map.put("loginName",loginName);
        	if(loginName!=null && !loginName.isEmpty()){
        		User vo = userDao.findByLoginName(loginName);
        		if(!BaseController.hasRole(SysConstants.ADMINISTRATOR) && vo.getOrgId().longValue() != BaseController.getCurrentOrgId()){
        			continue;
        		}
        		map.put("nickName",vo.getAliasName());
        		map.put("orgId",vo.getOrgId().toString());
        	}else{
        		continue;
        	}
        	map.put("host",session.getHost());
        	map.put("loginTime", DateUtil.convertToString(session.getStartTimestamp()));
        	map.put("lastAccessTime", DateUtil.convertToString(session.getLastAccessTime()));
        	result.add(map);
        }
        
        return result;
	}
	
	public int getActiveSessionNum(){
		return sessionDAO.getActiveSessions().size();
	}
	
	
	public Session logouSession(String sessionId){
		Session session = null;
		try {
			session = sessionDAO.readSession(sessionId);
            if(session != null) {
                session.setAttribute(SysConstants.SESSION_FORCE_LOGOUT_KEY, Boolean.TRUE);
            }
        } catch (Exception e) {
        	log.error(e.toString(),e);
        }
		return session;
	}

}
