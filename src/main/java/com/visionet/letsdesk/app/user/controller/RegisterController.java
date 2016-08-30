package com.visionet.letsdesk.app.user.controller;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.service.OrganizationService;
import com.visionet.letsdesk.app.user.service.RegisterService;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping(value = "/register")
public class RegisterController extends BaseController {

	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private RegisterService registerService;



    /**
     * @apiDescription 用户注册
     * @api {post} /register /register
     * @apiVersion 2.0.0
     * @apiName register
     * @apiGroup User
     * @apiPermission annon
     * @apiParam {String} aliasName 用户昵称
     * @apiParam {String} plainPassword 登录密码,明文
     * @apiParam {String} email 邮箱
     * @apiParam {String} domain 域名
     * @apiParam {String} orgName 企业名称
     *
     * @apiParamExample {json} 输入:
     * {
     *     "aliasName":"微企admin",
     *     "plainPassword":"123456",
     *     "email":"xuetao@visionet.com.cn",
     *     "domain":"visionet",
     *     "orgName":"上海微企"
     * }
     *
     * @apiSuccess {String} aliasName  用户名
     * @apiSuccess {String} plainPassword  登录密码
     * @apiSuccess {String} email   邮箱
     * @apiSuccess {String} domain  域名前缀 (新域名则创建对应公司)
     * @apiSuccess {String} orgName 公司名称
     *
     * @apiSuccessExample Success-Response:
     * {
     *   "id": 2,
     *   "loginName": "xuetao@visionet.com.cn",
     *   "aliasName": "微企admin",
     *   "trueName": null,
     *   "userType": "K",
     *   "avatar": null,
     *   "locale": null,
     *   "firstLetter": "WQADMIN",
     *   "phoneNumber": null,
     *   "email": "xuetao@visionet.com.cn",
     *   "remark": null,
     *   "orgId": 1,
     *   "isLock": 0,
     *   "lastLogin": null,
     *   "roleNames": ""
     * }
     *
     * @apiError (Error 202) IncorrectCredentials UnknownAccount or password error.
     *
     * @apiErrorExample Error-Response:
     *     {"code":"10007","msg":"domain is null!"}
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> register(@Valid @RequestBody UserVo vo) throws Exception{
        if (Validator.isNull(vo.getEmail())) {
            throwException(BusinessStatus.REQUIRE, "email is null!");
        }else{
            vo.setLoginName(vo.getEmail());
        }
        if (Validator.isNull(vo.getAliasName())) {
            throwException(BusinessStatus.REQUIRE, "aliasName is null!");
        }
//        if (Validator.isNull(vo.getPhoneNumber())) {
//            throwException(BusinessStatus.REQUIRE, "phoneNumber is null!");
//        }
        if (Validator.isNull(vo.getPlainPassword())) {
            throwException(BusinessStatus.REQUIRE, "password is null!");
        }
        if (Validator.isNull(vo.getDomain())) {
            throwException(BusinessStatus.REQUIRE, "domain is null!");
        }


        User user = registerService.addOrgAndUser(vo);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }




    /**
     * @apiDescription 校验domain是否唯一
     * @api {get} /check/domain?domain=
     * @apiVersion 2.0.0
     * @apiName checkLoginName
     * @apiGroup User
     * @apiPermission annon
     * @apiParam {String} domain 域名
     *
     * @apiSuccess {String} code 是否唯一
     *
     * @apiSuccessExample 唯一:
     *     {
     *       "code" : true
     *     }
     * @apiSuccessExample 不唯一:
     *     {
     *       "code" : false
     *     }
     *
     * @apiError (Error 202) exception
     *
     */
    @RequestMapping(value = "/check/domain",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> checkLoginName(@RequestParam("domain") String domain) {
        Map<String,Boolean> map = Maps.newHashMap();
        if (organizationService.findOrgByDomain(domain) == null) {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_TRUE);
        } else {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_FALSE);
        }
        return new ResponseEntity<Map<String,Boolean>>(map , HttpStatus.OK);
    }

    /**
     * @apiDescription 校验公司名是否唯一
     * @api {get} /check/orgName?orgName=
     * @apiVersion 2.0.0
     * @apiName checkOrgName
     * @apiGroup User
     * @apiPermission annon
     * @apiParam {String} orgName 公司名
     *
     * @apiSuccess {String} code 是否唯一
     *
     * @apiSuccessExample 唯一:
     *     {
     *       "code" : true
     *     }
     * @apiSuccessExample 不唯一:
     *     {
     *       "code" : false
     *     }
     *
     * @apiError (Error 202) exception
     *
     */
    @RequestMapping(value = "/check/orgName",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> checkOrgName(@RequestParam("orgName") String orgName) {
        Map<String,Boolean> map = Maps.newHashMap();
        if (organizationService.findOrgByOrgFullName(orgName) == null) {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_TRUE);
        } else {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_FALSE);
        }
        return new ResponseEntity<Map<String,Boolean>>(map , HttpStatus.OK);
    }
}
