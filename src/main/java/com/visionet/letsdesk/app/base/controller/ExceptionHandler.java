package com.visionet.letsdesk.app.base.controller;

import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.GetterUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.SocketException;

@Component
public class ExceptionHandler implements HandlerExceptionResolver {
	private static Logger _log = LoggerFactory.getLogger(ExceptionHandler.class);
	private static boolean LogFlag = GetterUtil.getBoolean(PropsUtil.getProperty(PropsKeys.SYSTEM_ERROR_LOG_ENABLED), false);
	

	
    @Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
    	
    	String uri = request.getRequestURI();
    	
    	/*
    	 * 浏览器访问一半断线或手动终止时，会出ClientAbortException和SocketException,
    	 * 若此时返回error页面或Controller，会继续出ClientAbortException，陷入死循环!
    	 */
    	if(ex instanceof SocketException){
			_log.error("resolveException SocketException:"+uri);
			return null;
		}else if(ex.getClass().getCanonicalName().endsWith("ClientAbortException")){
			//浏览器访问一半手动终止时，会出ClientAbortException
			_log.error("resolveException ClientAbortException:"+uri);
			return null;
		}
    	
    	_log.error("resolveException error: ",ex);
    	
//    	if(LogFlag){
//            Long currUserId  = null;
//            String currUserName =null;
//            try{
//                currUserId = BaseController.getCurrentUserId();
//                currUserName = BaseController.getCurrentUserName();
//            }catch (Exception e){
//                _log.error(e.toString());
//            }
//            if(currUserId!=null) {
//                try {
//                    SystemErrorLog errorLog = new SystemErrorLog();
//                    errorLog.setUrl(uri);
//                    errorLog.setErrorMessage((ex.getMessage() == null ? ex.toString() : ex.getMessage()) + (ex.getCause() == null ? "" : ("\r\n " + ex.getCause().getMessage())));
//                    if (errorLog.getErrorMessage().length() > 999) {
//                        errorLog.setErrorMessage(errorLog.getErrorMessage().substring(0, 999));
//                    }
//                    errorLog.setUserId(currUserId);
//                    errorLog.setUserName(currUserName);
//                    SystemErrorLogService.saveErrorLog(errorLog);
//
//                } catch (Exception e) {
//                    _log.error("saveErrorLog error: ", e);
//                }
//            }
//    	}
    	
    	if(uri.contains("/mobile/") || uri.contains("/console/")
    			|| uri.contains("/help") || uri.contains("/api/")
                || uri.contains("/register/") || uri.contains("/open/")){
        	
    		String code = BusinessStatus.ERROR;
    		String err = ex.getMessage()==null?ex.toString():ex.getMessage();
    		response.setStatus(202);
    		
    		if(BusinessStatus.ADMINDENY.equals(err)){
    			code = BusinessStatus.ADMINDENY;
    			err = MessageSourceHelper.GetMessages("permission.mobile.admin.denied");
                response.setHeader("permissionDeny", "user");
    		}else if(BusinessStatus.USERDENY.equals(err)){
    			code = BusinessStatus.USERDENY;
    			err = MessageSourceHelper.GetMessages("permission.console.user.denied");
                response.setHeader("permissionDeny", "admin");
    		}else if(ex instanceof AuthenticationException ||ex instanceof  UnauthorizedException){
    			code = BusinessStatus.ACCESSDENIED;
    			err = MessageSourceHelper.GetMessages("permission.authentication.denied");
    		}
    		request.setAttribute(MobileKey.CODE, code);
    		request.setAttribute(MobileKey.MSG, err);
    		return new ModelAndView("forward:/mobileErr");
    	}else{
	    	if(ex instanceof org.springframework.validation.BindException){
	    		 return new ModelAndView("forward:/error/400");
	    	}else if(ex instanceof UnauthorizedException){
	            return new ModelAndView("forward:/error/403");
	    	}else if(ex instanceof NoSuchRequestHandlingMethodException){
	            return new ModelAndView("forward:/error/404");
	        }
    	}
    	
    	
        return new ModelAndView("forward:/error/500");
    }  
  
}