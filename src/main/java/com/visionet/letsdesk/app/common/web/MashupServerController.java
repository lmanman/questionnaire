package com.visionet.letsdesk.app.common.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.visionet.letsdesk.app.common.modules.mapper.JsonMapper;

import java.util.Collections;
import java.util.Map;

/**
 * 输出JsonP格式的Mashup 服务端, 支持被跨域调用.
 * 
 * @author xt
 */
@Controller
public class MashupServerController {

	private static final String DEFAULT_JQUERY_JSONP_CALLBACK_PARM_NAME = "callback";

	private JsonMapper mapper = new JsonMapper();

	@RequestMapping(value = "/web/mashup", produces = "applicaiton/javascript")
	@ResponseBody
	public String execute(@RequestParam(DEFAULT_JQUERY_JSONP_CALLBACK_PARM_NAME) String callbackName) {

		// 设置需要被格式化为JSON字符串的内容.
		Map<String, String> map = Collections.singletonMap("content", "<p>Hello,world!</p>");

		// 渲染返回结果.
		return mapper.toJsonP(callbackName, map);
	}
}
