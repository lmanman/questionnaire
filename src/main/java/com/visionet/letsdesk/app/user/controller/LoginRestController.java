package com.visionet.letsdesk.app.user.controller;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.base.rest.RestException;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.UploadUtil;
import com.visionet.letsdesk.app.foundation.service.MobileClientService;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.service.AccountService;
import com.visionet.letsdesk.app.user.service.ResourceService;
import com.visionet.letsdesk.app.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginRestController extends BaseController {
	private static Logger log = LoggerFactory.getLogger(LoginRestController.class);

	@Autowired
	private AccountService accountService;
    @Autowired
    private UserService userService;
	@Autowired
	private MessageSourceHelper messageSourceHelper;
    @Autowired
    private MobileClientService mobileClientService;
    @Autowired
    private ResourceService resourceService;


    /**
     * @apiDescription 手机端用户登录
     * @api {post} /mobilelogin /mobilelogin
     * @apiVersion 2.0.0
     * @apiName mobilelogin
     * @apiGroup Login
     * @apiPermission annon
     * @apiParam {String} username 用户登录名
     * @apiParam {String} password 登录密码,明文
     * @apiParam {String} client_flag Android/IOS标志,默认Android
     * @apiParam {String} locale 语言 en:英文;zh:中文
     * @apiParam {String} loginCode  用户登录记录码 必填
     *
     * @apiParamExample {json} IOS:
     *      {"username":"sunyan","password":"123456","client_flag":"ios","model":"iphone5S","locale":"zh","loginCode":"123456"}
     * @apiParamExample {json} Android:
     *      {"username":"zhenghai","password":"123456","client_flag":"android","model":"Samsung Galaxy Note3 N9009","locale":"en","loginCode":"123456"}
     *
     * @apiSuccess {String} id  用户id  PK
     * @apiSuccess {String} apkpath  Android下载链接
     * @apiSuccess {String} iospath  IOS下载链接
     * @apiSuccess {String} version  app最新版本号
     * @apiSuccess {String} roleNames   角色名  多个role以逗号分割
     * @apiSuccess {String} permissions   权限明细  JSON数组，格式 - 菜单名:读写标志
     * @apiSuccess {String} code   成功标志
     *
     * @apiSuccessExample Success-Response:
     *   {
     *     "id" : "2137",
     *     "apkpath" : "http://localhost:2085/sloth2downloadFile/client/SlothBranNew.apk",
     *     "iospath" : "itms-services://?action=download-manifest&amp;url=https://raw.githubusercontent.com/sloth/wsk/master/kefu/manifest.plist",
     *     "permissions" : [ "meeting:view", "meeting:edit", "top2:view", "user:view" ],
     *     "code" : "10000",
     *     "roleNames" : "user, speaker",
     *     "version" : "1.4"
     *   }
     *
     * @apiError (Error 202) IncorrectCredentials UnknownAccount or password error.
     *
     * @apiErrorExample Error-Response:
     *     {"code":"10001","msg":"用户名或密码错误，请重试."}
     */
	@RequestMapping(value = "/mobilelogin", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> mobilelogin(@RequestBody Map<String,String> mapUser) throws Exception {
		String locale = mapUser.get("locale");
		String username = mapUser.get("username");
		try{
			
			UsernamePasswordToken token = new UsernamePasswordToken();
		    token.setUsername(username);  
		    token.setPassword(mapUser.get("password").toCharArray());  
			SecurityUtils.getSubject().login(token);
			
			BaseController.setLocale(locale);

            userService.checkOrgValid(getCurrentUserId());

			//登录聊天系统并把sid保存下来
//			Map<String, String> chatLoginMap = new HashMap<String,String>();
//			chatLoginMap.put("loginName", mapUser.get("username"));
//			Map<String,String> signInResultMap = chatConnection.signIn(chatLoginMap);
//			for(Map.Entry<String,String> entry : signInResultMap.entrySet()){
//				CookieUtil.saveCookie(entry.getKey(), EscapeUnescape.escape(entry.getValue()), response, request);
//			}

            String clientFlag = mapUser.get(SysConstants.CLIENT_FLAG)==null? SysConstants.CLIENT_ANDROID:mapUser.get(SysConstants.CLIENT_FLAG);
			resourceService.updateLastLoginOn(getCurrentUserId(), DateUtil.getCurrentDate(),clientFlag);
		}catch(UnknownAccountException uae){
			log.error("mobilelogin UnknownAccount: "+uae.toString());
			throw new RestException(messageSourceHelper.getMessage("login.failure"));
		}catch(IncorrectCredentialsException ice){
			log.error("mobilelogin IncorrectCredentials: "+ice.toString());
			throw new RestException(messageSourceHelper.getMessage("login.failure"));
		}catch(Exception e){
			log.error("mobilelogin error: ",e);
			
			User user = accountService.findByLoginName(username);
			if(user!=null&&user.getIsLock().equals(SysConstants.USER_ACTIVITY_DISABLED)){
				throw new RestException(messageSourceHelper.getMessage("login.user.disabled"));
			}
			
			throw new RestException(messageSourceHelper.getMessage("login.failure"));
		}
		
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(MobileKey.CODE, BusinessStatus.OK);
		map.put("id", getCurrentUserId().toString());
        map.put("aliasName", getCurrentUserName());
//		map.put("apkpath", PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN)+ UploadUtil.GetAPKDownloadPath());
//        map.put("iospath", UploadUtil.GetSMSIOSDownloadPath());
//		map.put(MobileKey.VER, mobileClientService.getLastVersion(clientFlag).getClientVersion());
        return new ResponseEntity<Map<String,Object>>(map ,HttpStatus.OK);
	}


    /**
     * @apiDescription 用户登出
     * @api {get} /mobilelogout /mobilelogout
     * @apiVersion 2.0.0
     * @apiName mobileLogout
     * @apiGroup Login
     * @apiPermission annon
     *
     * @apiSuccessExample Android:
     *     {"code":"10000"}
     */
    @RequestMapping(value="/mobilelogout")
    public ResponseEntity<?> mobileLogout() throws Exception {
//            resourceService.updateLastLogout(getCurrentUserId());
        SecurityUtils.getSubject().logout();

        return new ResponseEntity<Map<String,String>>(GetSuccMap() ,HttpStatus.OK);
    }
	
	
	@RequestMapping(value = "/mobileErr", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> mobileErr(HttpServletRequest request) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(MobileKey.CODE, (String)request.getAttribute(MobileKey.CODE));
		map.put(MobileKey.MSG, (String)request.getAttribute(MobileKey.MSG));
		
		return new ResponseEntity<Map<String,String>>(map ,HttpStatus.ACCEPTED);
	}
	
	@RequestMapping(value = "/mobileErr", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> mobileErrPost(HttpServletRequest request) {
		
		Map<String,String> map = new HashMap<String,String>();
		map.put(MobileKey.CODE, (String)request.getAttribute(MobileKey.CODE));
		map.put(MobileKey.MSG, (String)request.getAttribute(MobileKey.MSG));
		
		return new ResponseEntity<Map<String,String>>(map ,HttpStatus.ACCEPTED);
	}


    @RequestMapping(value = "/error/{code}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Map<String,String>> error(@PathVariable("code") String code) {

        Map<String,String> map = Maps.newHashMap();
        map.put(MobileKey.CODE, code);
        map.put(MobileKey.MSG, "system error!");

        HttpStatus status;
        if(code.equals("404")) {
            status = HttpStatus.NOT_FOUND;
        }else if(code.equals("403")){
            status = HttpStatus.FORBIDDEN;
        }else{
            status = HttpStatus.ACCEPTED;
        }
        return new ResponseEntity<Map<String,String>>(map ,status);
    }


    /**
     * @apiDescription 个人语言设置
     * @api {get} /mobile/language/:locale /mobile/language/:locale
     * @apiVersion 2.0.0
     * @apiName setUserLocale
     * @apiGroup Login
     * @apiPermission user
     * @apiParam {String} locale 语言代码 zh:中文;en:英文
     *
     * @apiSuccessExample Android:
     *     {"code":"10000"}
     */
	@RequestMapping(value = "/mobile/language/{locale}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<Map<String,String>> setUserLocale(@PathVariable("locale") String locale) {
		BaseController.setLocale(locale);
		
		return new ResponseEntity<Map<String,String>>(GetSuccMap() ,HttpStatus.ACCEPTED);
	}


    /**
     * @apiDescription 获取手机app版本及url
     * @api {get} /api/version/:type /api/version/:type
     * @apiVersion 2.0.0
     * @apiName mobileVersion
     * @apiGroup Login
     * @apiPermission annon
     * @apiParam {String} type 手机类型: IOS/Android/web.
     *
     * @apiSuccess {String} apkpath 安卓路径
     * @apiSuccess {String} iospath IOS路径
     * @apiSuccess {String} qrcodeUrl 二维码url
     * @apiSuccess {String} version 版本号
     *
     * @apiSuccessExample Android:
     *     {
     *       "apkpath" : "http://localhost:2085/sloth2downloadFile/client/SlothBranNew.apk",
     *       "qrcodeUrl" : "http://vn-functional.chinacloudapp.cn/letsdesk/downloadFile/client/qrcode-mobile.png",
     *       "version" : "2.1"
     *     }
     * @apiSuccessExample IOS:
     *     {
     *       "iospath" : "itms-services://?action=download-manifest&amp;url=https://raw.githubusercontent.com/sloth/wsk/master/kefu/manifest.plist",
     *       "qrcodeUrl" : "http://vn-functional.chinacloudapp.cn/letsdesk/downloadFile/client/qrcode-mobile.png",
     *       "version" : "2.2"
     *     }
     *
     * @apiError (Error 202) typeIllegal type类型不合法
     *
     * @apiErrorExample Error-Response:
     *     {"code":"10004","msg":"type is ILLEGAL:?"}
     */
    @RequestMapping(value="/api/version/{type}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> mobileVersion(@PathVariable("type") String type)  {
        if(Validator.isNull(type)){
            throwException(BusinessStatus.REQUIRE,"type is null!");
        }
        Map<String,String> map = Maps.newHashMap();
        if(type.equals(SysConstants.CLIENT_ANDROID)){
            map.put("apkpath", PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN)+ UploadUtil.GetAPKDownloadPath());
        }else if(type.equals(SysConstants.CLIENT_IOS)){
            map.put("iospath", UploadUtil.GetSMSIOSDownloadPath());
        }else{
            throwException(BusinessStatus.ILLEGAL,"type is ILLEGAL:"+type);
        }

        map.put(MobileKey.VER, mobileClientService.getLastVersion(type).getClientVersion());
        map.put("qrcodeUrl", PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN) + UploadUtil.GetQrcodeMaxPath());
        return new ResponseEntity(map , HttpStatus.OK);
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "redirect:/web/#/access/signin";
    }

    @RequestMapping(value="/weblogout")
    public String webLogout() throws Exception {
//            resourceService.updateLastLogout(getCurrentUserId());
        SecurityUtils.getSubject().logout();

        return this.login();
    }

}
