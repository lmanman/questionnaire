package com.visionet.letsdesk.app.base.rest;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

public class RestClientController extends BaseRest{
	
	@Override
	public void initUser() {
	}
	
	/**
	 * OAuth2的authorize接口 
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/api/sina/authorize", method = RequestMethod.POST)
	@ResponseBody
	public String sinaAuthorize(@RequestBody AuthorizeVo bean){
		/*
		 * //请求
		 * https://api.weibo.com/oauth2/authorize?client_id=123050457758183&redirect_uri=http://www.example.com/response&response_type=code
		 * //同意授权后会重定向
		 * http://www.example.com/response&code=CODE
		 */
		String url = "https://api.weibo.com/oauth2/authorize";
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("client_id", bean.getClient_id());
		map.add("redirect_uri", bean.getRedirect_uri());
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,headers);
		
		return getRestTemplate().postForObject(url,entity, String.class);
	}
	
	/**
	 * OAuth2的access_token接口 
	 * @param bean
	 * @return
	 */
	@RequestMapping(value = "/api/sina/accesstoken", method = RequestMethod.POST)
	@ResponseBody
	public String sinaaccessToken(@RequestBody AuthorizeVo bean){
		/*
		 *  {
		 *   "access_token": "ACCESS_TOKEN",
       	 *   "expires_in": 1234,
       	 *   "remind_in":"798114",
       	 *   "uid":"12341234"
 		 *  }
		 */
		String url = "https://api.weibo.com/oauth2/access_token";
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("client_id", bean.getClient_id());
		map.add("redirect_uri", bean.getRedirect_uri());
		map.add("grant_type", bean.getGrant_type());
	
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(map,headers);
		
		return getRestTemplate().postForObject(url,entity, String.class);
	}
	
	

}
