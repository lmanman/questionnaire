package com.visionet.letsdesk.app.base.rest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

/**
 * 处理HttpBasicHeader的Interceptor
 */
public  class HttpBasicInterceptor implements ClientHttpRequestInterceptor {

		private final String user;
		private final String password;

		public HttpBasicInterceptor(String user, String password) {
			this.user = user;
			this.password = password;
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
				throws IOException {
			request.getHeaders().set(com.google.common.net.HttpHeaders.AUTHORIZATION,encodeHttpBasic(user, password));
			return execution.execute(request, body);
		}
		
		/**
		 * 客户端对Http Basic验证的 Header进行编码.
		 */
		public static String encodeHttpBasic(String userName, String password) {
			String encode = userName + ":" + password;
			return "Basic " + Base64.encodeBase64String(encode.getBytes());
		}
	}