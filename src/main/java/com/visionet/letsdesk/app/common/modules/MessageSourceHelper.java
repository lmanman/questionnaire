package com.visionet.letsdesk.app.common.modules;

import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.utils.SpringContextUtil;
import com.visionet.letsdesk.app.base.controller.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service("messageSourceHelper")
public class MessageSourceHelper {
	private static Logger _log = LoggerFactory.getLogger(MessageSourceHelper.class);
	
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		if(locale == null){
			locale = new Locale("zh");
		}
		String msg = messageSource.getMessage(code, args, defaultMessage,locale);
		
		return msg != null ? msg.trim() : msg;
	}
	public String getMessage(String code) {
		Locale lc = null;
		try {
			if(BaseController.getLocale()!=null){
				lc = new Locale(BaseController.getLocale());
			}
		} catch (Exception e) {
			_log.warn("getLocale:",e.toString());
		}

		String msg = this.getMessage(code,null,"",lc);
		
		return msg != null ? msg.trim() : msg;
	}
	
	public static String GetMessages(String code) {
		String msg = ((MessageSourceHelper)SpringContextUtil.getBean("messageSourceHelper")).getMessage(code);
		
		return msg != null ? msg.trim() : msg;
	}

    public static String GetMessages(String code,Object[] args) {
        String locale=BaseController.getLocale() == null ?"zh":BaseController.getLocale();
        String msg = ((MessageSourceHelper)SpringContextUtil.getBean("messageSourceHelper")).getMessage(code, args, "", new Locale(locale));

        if(msg == null){
            return null;
        }

        return msg.replaceAll(StringPool.OPEN_CURLY_BRACE_ZH, StringPool.OPEN_CURLY_BRACE).replaceAll(StringPool.CLOSE_CURLY_BRACE_ZH, StringPool.CLOSE_CURLY_BRACE);
    }
	

	public void setMessageSource(ReloadableResourceBundleMessageSource messageSource) {
		this.messageSource = messageSource;
	}
}
