package com.visionet.letsdesk.app.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserLogInterceptor extends HandlerInterceptorAdapter{
//	private static Logger log = LoggerFactory.getLogger(UserLogInterceptor.class);
//	public static boolean LogFlag = GetterUtil.getBoolean(PropsUtil.getProperty(PropsKeys.AUDIT_LOG_ENABLED), false);
//	private NamedThreadLocal<Long>  startTimeThreadLocal =  new NamedThreadLocal<Long>("StopWatch-StartTime");
	
	/**
	 * 预处理,Controller之前执行
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
        String uri = request.getRequestURI();

		if(LogFlag){
			//1、开始时间
			long beginTime = System.currentTimeMillis();
			//线程绑定变量（该数据只有当前请求的线程可见）
			startTimeThreadLocal.set(beginTime);
		}
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
