package com.visionet.letsdesk.app.base.rest;

import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.web.MediaTypes;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@Component
public abstract class BaseRest {
    private static Log log = LogFactory.getLog(BaseRest.class);
    
	public static final int PAGE_SIZE = 10;
	
	//这里是集成环境的路径，不能修改它，如果要修改，请在initUlr设置本机的路径
	protected static String SEARCHER_URL = PropsUtil.getProperty(PropsKeys.SEARCHER_SOLR_URL);
	protected static String SMS_URL = PropsUtil.getProperty(PropsKeys.SMS_HTTP_URL);
	private  RestTemplate jdkTemplate;
	protected  RestTemplate httpClientRestTemplate;
	private  HttpComponentsClientHttpRequestFactory httpClientRequestFactory;
	// 设置处理HttpBasic Header的Interceptor
	protected ClientHttpRequestInterceptor interceptor = null;

	
	protected RestTemplate getRestTemplate(){
		return httpClientRestTemplate;
	}

	
	/**
	 * 这个方法用来设置登录用户信息
	 */
	public abstract void initUser();
	
	protected void setUser(ClientHttpRequestInterceptor interceptor){
		this.interceptor = interceptor;
	}
	
	@PostConstruct
	public  void initResource(){
		
		// 默认使用JDK Connection
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new StringHttpMessageConverter(Charset.forName("UTF-8")));
        messageConverters.add(new FormHttpMessageConverter());
//        messageConverters.add(new MappingJackson2XmlHttpMessageConverter());
        messageConverters.add(new MappingJackson2HttpMessageConverter());

        jdkTemplate = new RestTemplate(messageConverters);
		// (optional)设置20秒超时
		((SimpleClientHttpRequestFactory) jdkTemplate.getRequestFactory()).setConnectTimeout(20000);

		// 设置使用HttpClient4.0
		httpClientRestTemplate = new RestTemplate(messageConverters);
		httpClientRequestFactory = new HttpComponentsClientHttpRequestFactory();
		// (optional)设置20秒超时
		httpClientRequestFactory.setConnectTimeout(20000);

		httpClientRestTemplate.setRequestFactory(httpClientRequestFactory);
//		initUser();
//		if(interceptor==null){
//			throw new RuntimeException("not initial User");
//		}else{
//			httpClientRestTemplate.setInterceptors(Lists.newArrayList(interceptor));
//		}
		
	}
	
	@PreDestroy
	public void close(){
		try {
			// 退出时关闭HttpClient4连接池中的连接
			httpClientRequestFactory.destroy();
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
	}

    protected HttpEntity getHttpEntity(Object vo){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(com.google.common.net.HttpHeaders.ACCEPT_CHARSET, "UTF-8");
        headers.set(com.google.common.net.HttpHeaders.CONTENT_TYPE, MediaTypes.JSON_UTF_8);

        return new HttpEntity(vo, headers);
    }

	
}
