package com.visionet.letsdesk.app.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MobileInterceptor extends HandlerInterceptorAdapter{
	private static Logger log = LoggerFactory.getLogger(MobileInterceptor.class);
	
//	private static boolean textEncryption = GetterUtil.getBoolean(PropsUtil.getProperty(PropsKeys.MOBILE_ALL_TEXT_ENCRYPTION), false);
	
//	private static String UploadUrlFlag="/mobile/attachment/fileUpload";
//	private static int MaxSize=102400000;
	
	/**
	 * 预处理,Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        /*
		String uri = request.getRequestURI();

		if (!"POST".equals(request.getMethod())) {
            return true;
        }

		StopWatch watch = new StopWatch();
		watch.start();
	
		request.setCharacterEncoding("UTF-8");

		//上传文件操作
		String fileName = request.getParameter(MobileKey.FN);
		
		if(fileName != null || uri.contains(UploadUrlFlag)){
			int formSize = request.getContentLength();

			if(formSize > MaxSize){
				BaseController.throwException(BusinessStatus.SIZE_LIMIT, MessageSourceHelper.GetMessages("app.interceptor.MobileInterceptor.filesize.check"));
			}

			if(fileName==null){
                if(uri.endsWith(UploadUrlFlag)){
                    fileName="unknown";
                }else if(uri.contains(UploadUrlFlag+ StringPool.FORWARD_SLASH)){
                    fileName = uri.substring(uri.indexOf(UploadUrlFlag) + UploadUrlFlag.length()+1);
                }
			}
            if(fileName==null){
                Map<String,String> exp = new HashMap<String,String>();
                exp.put("code", BusinessStatus.REQUIRE);
                exp.put("msg", "fileName is null!");
                throw new RestException(exp);
            }
			
			if(fileName.endsWith(MobileKey.PIC_FLAG)){
				fileName = fileName.substring(0,fileName.length()- MobileKey.PIC_FLAG.length());
			}

			try{
				//Tomcat GET 设置过 URIEncoding="UTF-8"，此处不需要转码
//				fileName = new String(fileName.getBytes("ISO-8859-1"), "UTF-8");
				String sign = UploadUtil.GetSignByName(fileName);
				if(sign.equals(UploadUtil.DEFAULT)){
                    String type = fileName.substring(fileName.lastIndexOf(".") + 1);
					BaseController.throwException(BusinessStatus.ACCESSDENIED, MessageSourceHelper.GetMessages("app.interceptor.MobileInterceptor.filetype.check")+type);
				}
				String[] ctxPath = UploadUtil.GetCreatePathWithSuffix(fileName, null);
				
				FileUtil.write(ctxPath[0], request.getInputStream());
				
				File file = new File(ctxPath[0]);
				request.setAttribute("sign", sign);
				request.setAttribute("realFileName", fileName);
				request.setAttribute("relativePath", ctxPath[1]);
				request.setAttribute("size", file.length());
			}catch(Exception e){
				log.error("mobile upload file error:",e);
				request.setAttribute("errorInfo", e.toString());
			}
		}
		
		watch.stop();
		*/
		return super.preHandle(request, response, handler);
	}

	/**
	 * 生成视图之前,调用了Service并返回ModelAndView，但未进行页面渲染
	 */
	@Override
	public void postHandle(HttpServletRequest request,HttpServletResponse response, Object handler,ModelAndView modelAndView) throws Exception{
		super.postHandle(request, response, handler, modelAndView);
	}
	
	/**
	 * 最后执行，可用于释放资源
	 * 可以根据ex是否为null判断是否发生了异常，进行日志记录
	 */
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception{
		super.afterCompletion(request, response, handler, ex);
	}
}
