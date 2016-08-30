package com.visionet.letsdesk.app.foundation;


import com.visionet.letsdesk.app.common.constant.SysConstants;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;


public class Functions {

  
    public static String userName(Session session) {
        PrincipalCollection principalCollection =
                (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
        if(principalCollection==null) return "";
        
        Object obj = principalCollection.getPrimaryPrincipal();
        return obj==null?"":obj.toString();
    }
 
    public static boolean isForceLogout(Session session) {
        return session.getAttribute(SysConstants.SESSION_FORCE_LOGOUT_KEY) != null;
    }
 
   

}

