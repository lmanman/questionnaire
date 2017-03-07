package com.visionet.letsdesk.app.user.controller;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.base.rest.RestException;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.mapper.BeanMapper;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.service.AccountService;
import com.visionet.letsdesk.app.user.service.RoleService;
import com.visionet.letsdesk.app.user.service.SessionManageService;
import com.visionet.letsdesk.app.user.service.UserService;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/user")
public class UserController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private SessionManageService sessionManageService;


    /**
     * @apiDescription 获取当前登录用户详情
     * @api {get} /mobile/user/currentUser /mobile/user/currentUser
     * @apiVersion 2.0.0
     * @apiName currentUser
     * @apiGroup User
     * @apiPermission kefu
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} loginName 登录名
     * @apiSuccess {String} aliasName 昵称
     * @apiSuccess {String} trueName 真实姓名
     * @apiSuccess {String} avatar 头像url
     * @apiSuccess {String} locale 语言
     * @apiSuccess {String} phoneNumber 电话
     * @apiSuccess {String} email 邮箱
     * @apiSuccess {String} firstLetter 首字母
     * @apiSuccess {String} userType 经理模式:M;客服模式:K
     * @apiSuccess {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiSuccess {String} remark 备注
     * @apiSuccess {String} orgId 公司ID
     * @apiSuccess {String} isLock 是否注销(1:是)
     * @apiSuccess {String} lastLogin 最后登录日期
     *
     * @apiSuccessExample {json} UserVo
     *  {
     *    "id": 3,
     *    "pageInfo": null,
     *    "loginName": "liuss@visionet.com.cn",
     *    "aliasName": "刘顺顺",
     *    "trueName": null,
     *    "plainPassword": null,
     *    "userType": "K",
     *    "userStatus": "I",
     *    "avatar": null,
     *    "locale": null,
     *    "firstLetter": "LSS",
     *    "phoneNumber": null,
     *    "email": "liuss@visionet.com.cn",
     *    "remark": null,
     *    "orgId": 1,
     *    "isLock": 0,
     *    "lastLogin": "2016-08-23 13:49:00",
     *    "domain": null,
     *    "orgName": null,
     *    "queryName": null,
     *    "roleNameList": null,
     *    "talkingNum": null
     *  }
     */
	@RequestMapping(value = "/current", method = RequestMethod.GET)
	public ResponseEntity<?> currentUser() {
		User user = userService.getUser(getCurrentUserId());
        if (user == null) {
            throwException(BusinessStatus.NOTFIND,"user is not exist!");
        }

		return new ResponseEntity<UserVo>(BeanConvertMap.map(user, UserVo.class), HttpStatus.OK);
	}


    /**
     * @apiDescription 指定用户详情
     * @api {get} /mobile/user/:id /mobile/user/:id
     * @apiVersion 2.0.0
     * @apiName userDetail
     * @apiGroup User
     * @apiPermission kefu
     * @apiParam {Long} id 用户id
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} loginName 登录名
     * @apiSuccess {String} aliasName 昵称
     * @apiSuccess {String} trueName 真实姓名
     * @apiSuccess {String} avatar 头像url
     * @apiSuccess {String} locale 语言
     * @apiSuccess {String} phoneNumber 电话
     * @apiSuccess {String} email 邮箱
     * @apiSuccess {String} firstLetter 首字母
     * @apiSuccess {String} userType 经理模式:M;客服模式:K
     * @apiSuccess {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiSuccess {String} remark 备注
     * @apiSuccess {String} orgId 公司ID
     * @apiSuccess {String} isLock 是否注销(1:是)
     * @apiSuccess {String} lastLogin 最后登录日期
     *
     * @apiSuccessExample {json} UserVo
     *  {
     *    "id": 3,
     *    "pageInfo": null,
     *    "loginName": "liuss@visionet.com.cn",
     *    "aliasName": "刘顺顺",
     *    "trueName": null,
     *    "plainPassword": null,
     *    "userType": "K",
     *    "userStatus": "I",
     *    "avatar": null,
     *    "locale": null,
     *    "firstLetter": "LSS",
     *    "phoneNumber": null,
     *    "email": "liuss@visionet.com.cn",
     *    "remark": null,
     *    "orgId": 1,
     *    "isLock": 0,
     *    "lastLogin": "2016-08-23 13:49:00",
     *    "domain": null,
     *    "orgName": null,
     *    "queryName": null,
     *    "roleNameList": null,
     *    "talkingNum": null
     *  }
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> userDetail(@PathVariable Long id){
        User user = userService.getUser(id);
//        CheckOrgId(user.getOrgId());

        UserVo vo = BeanMapper.map(user, UserVo.class);
        vo.setRoleList(roleService.getUserRoleList(user.getRoleSet()));

        return new ResponseEntity<UserVo>(vo, HttpStatus.OK);
    }


    /**
     * @apiDescription 条件搜索用户
     * @api {post} /mobile/user/search /mobile/user/search
     * @apiVersion 2.0.0
     * @apiName search
     * @apiGroup User
     * @apiPermission user
     *
     * @apiParam {String} loginName 登录名(模糊查询)
     * @apiParam {String} aliasName 昵称(模糊查询)
     * @apiParam {String} trueName 真实姓名
     * @apiParam {String} phoneNumber 电话
     * @apiParam {String} email 邮箱(模糊查询)
     * @apiParam {String} firstLetter 首字母
     * @apiParam {String} userType 经理模式:M;客服模式:K
     * @apiParam {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiParam {String} queryName 通用查询（登录名or昵称or首字母；模糊查询）
     * @apiParam {String} roleNameList 权限集合（查询集合内任意一个权限）
     *
     * @apiParamExample {json} 输入:
     *  {
     *    "pageInfo":{
     *      "pageNumber":1,
     *      "pageSize":20,
     *      "sortTypeStr":"desc",
     *      "sortColumn": "id"
     *    },
     *    "loginName":"liuss",
     *    "aliasName": "顺顺",
     *    "trueName": "刘顺顺",
     *    "phoneNumber": "130xxxxxxxx",
     *    "email": "liuss@visionet.com.cn",
     *    "firstLetter": "LSS",
     *    "userType": "M",
     *    "userStatus": "I",
     *    "queryName": "顺",
     *    "roleNameList": ["manager","mobileService"]
     *  }
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} loginName 登录名
     * @apiSuccess {String} aliasName 昵称
     * @apiSuccess {String} trueName 真实姓名
     * @apiSuccess {String} avatar 头像url
     * @apiSuccess {String} locale 语言
     * @apiSuccess {String} phoneNumber 电话
     * @apiSuccess {String} email 邮箱
     * @apiSuccess {String} firstLetter 首字母
     * @apiSuccess {String} userType 经理模式:M;客服模式:K
     * @apiSuccess {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiSuccess {String} remark 备注
     * @apiSuccess {String} orgId 公司ID
     * @apiSuccess {String} isLock 是否注销(1:是)
     * @apiSuccess {String} lastLogin 最后登录日期
     *
     * @apiSuccessExample {json} Page<UserVo>
     *  {
     *    "content" : [{
     *    "id": 3,
     *    "pageInfo": null,
     *    "loginName": "liuss@visionet.com.cn",
     *    "aliasName": "刘顺顺",
     *    "trueName": null,
     *    "plainPassword": null,
     *    "userType": "K",
     *    "userStatus": "I",
     *    "avatar": null,
     *    "locale": null,
     *    "firstLetter": "LSS",
     *    "phoneNumber": null,
     *    "email": "liuss@visionet.com.cn",
     *    "remark": null,
     *    "orgId": 1,
     *    "isLock": 0,
     *    "lastLogin": "2016-08-23 13:49:00",
     *    "domain": null,
     *    "orgName": null,
     *    "queryName": null,
     *    "roleNameList": null,
     *    "updateDate": null,
     *    "talkingNum": null
     *   }],
     *    "size" : 2,
     *    "number" : 0,
     *    "sort" : [ {
     *      "direction" : "DESC",
     *      "property" : "id",
     *      "ignoreCase" : false,
     *      "ascending" : false
     *    } ],
     *    "totalElements" : 4,
     *    "numberOfElements" : 2,
     *    "firstPage" : true,
     *    "totalPages" : 2,
     *    "lastPage" : false
     *  }
     */
    @RequestMapping(value ="/search", method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> searchUser(@RequestBody UserVo vo) throws Exception {

        vo.setOrgId(getCurrentOrgId());
        vo.setIsLock(KeyWord.UN_DEL_STATUS);
        Page<User> page = userService.searchUser(vo);
        List<UserVo> listVo = Lists.newArrayList();
        page.getContent().stream().forEach(po->{
            UserVo userVo = BeanConvertMap.map(po, UserVo.class);
            userVo.setRoleDescs(Collections3.extractToString(po.getRoleSet(), "roleDesc", ", "));
            listVo.add(userVo);
        });

        return new ResponseEntity<Page<UserVo>>(GetPageByList(page, listVo, UserVo.class), HttpStatus.OK);
    }

    /**
     * @apiDescription 修改自己基本资料
     * @api {post} /mobile/user/update /mobile/user/update
     * @apiVersion 2.0.0
     * @apiName updateUser
     * @apiGroup User
     * @apiPermission user
     *
     * @apiParam {String} aliasName 昵称
     * @apiParam {String} trueName 真实姓名
     * @apiParam {String} avatar 头像url
     * @apiParam {String} locale 语言
     * @apiParam {String} phoneNumber 电话
     * @apiParam {String} email 邮箱
     * @apiParam {String} remark 备注
     *
     * @apiParamExample {json} 输入:
     *  {
     *    "id": 3,
     *    "aliasName": "顺顺",
     *    "trueName": "刘顺顺",
     *    "phoneNumber": "130xxxxxxxx",
     *    "avatar":"/downloadFile/2016-01-01/image/me.jpg"
     *    "email": "liuss@visionet.com.cn",
     *    "remark": "备注A"
     *  }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> updateUser(@RequestBody User vo) throws Exception{
        vo.setPlainPassword(null);
                System.out.println(mapper.toJson(vo));
		User po = userService.getUser(vo.getId());

        SearchFilterUtil.copyBeans(po, vo);
        userService.saveUser(po);

		return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
	}


    /**
     * @apiDescription 用户修改自己密码
     * @api {post} /mobile/user/update/passwd /mobile/user/update/passwd
     * @apiVersion 2.0.0
     * @apiName updateSelfPasswd
     * @apiGroup User
     * @apiPermission user
     *
     * @apiParam {Long} id	用户ID
     * @apiParam {String} plainPassword 新密码（明文）
     * @apiParam {String} password 旧密码（明文）
     *
     * @apiParamExample {json} 输入:
     *  {
     *  	"id":"179",
     *  	"plainPassword":"xxxxxx",
     *  	"password":"123456"
     *  }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     *
     * @apiError (Error 202) Excpetion 校验失败
     * @apiErrorExample Error-Response:
     *      {"code":"10001","msg":"原密码输入错误！"}
     *      {"code":"10001","msg":"新密码不能与原密码相同！"}
     */
	@RequestMapping(value = "/update/passwd", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> updateSelfPasswd(@RequestBody User user){
        System.out.println(user.getPlainPassword());

        if(Validator.isNull(user.getPlainPassword())){
            throwException(BusinessStatus.REQUIRE,"password is null!");
        }
        User old = userService.getUser(getCurrentUserId());

//        if(!old.getPassword().equals(UserService.getEntryptPassword(user.getPassword(), old.getPasswordSalt()))){
//            throw new RestException(MessageSourceHelper.GetMessages("app.web.account.UserRestController.passwd.error"));
//        }
//        if(old.getPassword().equals(UserService.getEntryptPassword(user.getPlainPassword(), old.getPasswordSalt()))){
//            throw new RestException(MessageSourceHelper.GetMessages("app.web.account.UserRestController.passwd.same"));
//        }

        old.setPassword(user.getPlainPassword());
        old.setPlainPassword(user.getPlainPassword());
		userService.updateUserPasswd(old);

		return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
	}


    /**
     * @apiDescription web头像上传
     * @api {post} /mobile/user/update/avatar /mobile/user/update/avatar
     * @apiVersion 2.0.0
     * @apiName updateUserImg
     * @apiGroup User
     * @apiPermission user
     *
     * @apiParam {String} x	横坐标
     * @apiParam {String} y	纵坐标
     * @apiParam {String} w	宽
     * @apiParam {String} h	高
     * @apiParam {String} fileType	原图类型
     * @apiParam {String} userImage	原图文件名(相对路径)
     *
     * @apiParamExample {json} 输入:
     *  {
     *    "x":200,
     *    "y":200,
     *    "w":200,
     *    "h":200,
     *    "fileType":"jpg",
     *    "userImage":"/downloadFile/2016-01-01/image/me.jpg"
     *  }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/update/avatar", method = RequestMethod.POST)
    public ResponseEntity<?> updateUserImg(@RequestBody Map<String, Object> params) throws Exception {
        params.put("isAdmin",AccountService.isSupervisor(getCurrentUserId()) ? "1" : "0");
        userService.updateUserImg(params, getCurrentUserId(),getCurrentOrgId());

        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }

    /**
     * @apiDescription 客服忙闲状态设置
     * @api {get} /mobile/user/online /mobile/user/online
     * @apiVersion 2.0.0
     * @apiName online
     * @apiGroup User
     * @apiPermission kefu
     *
     * @apiSuccess {User} user 客服信息
     *
     * @apiSuccessExample {json} List<User>
     *  [
     *    {
     *      "id": 3,
     *      "loginName": "liuss@visionet.com.cn",
     *      "aliasName": "刘顺顺",
     *      "trueName": null,
     *      "userType": "K",
     *      "userStatus": "I",
     *      "avatar": null,
     *      "locale": null,
     *      "firstLetter": "LSS",
     *      "phoneNumber": null,
     *      "email": "liuss@visionet.com.cn",
     *      "remark": null,
     *      "orgId": 1,
     *      "isLock": 0,
     *      "lastLogin": "2016-08-23 12:42:51",
     *      "roleNames": "customService"
     *    }
     *  ]
     */
    @RequestMapping(value = "/online")
    public ResponseEntity<?> online() throws Exception {
        List<User> onlineUsers = sessionManageService.getActiveUser(getCurrentOrgId(),Lists.newArrayList(SysConstants.CUSTOMER_SERVICE));

        return new ResponseEntity<List<User>>(onlineUsers , HttpStatus.OK);
    }


    /**
     * @apiDescription 管理员注册用户
     * @api {post} /mobile/user/register /mobile/user/register
     * @apiVersion 2.0.0
     * @apiName register
     * @apiGroup console-user
     * @apiPermission subadmin
     *
     * @apiParam {String} aliasName 用户昵称(必填)
     * @apiParam {String} plainPassword 登录密码,明文(必填)
     * @apiParam {String} loginName 登录名(必填)
     * @apiParam {String} trueName 分机号
     * @apiParam {String} jobNumber 工号
     * @apiParam {String} phoneNumber 电话
     * @apiParam {String} email 邮箱
     * @apiParam {String} userType 经理模式:M;客服模式:K
     * @apiParam {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiParam {Role} roleSet 权限集合（查询集合内任意一个权限）(默认客服坐席)
     *
     * @apiParamExample {json} 输入:
     *{
     *      "loginName": "test3",
     *      "aliasName": "测试3",
     *      "trueName": "A0932",
     *      "roleSet": [
     *        {
     *          "id": 3
     *        }
     *      ],
     *      "phoneNumber": "13511111111",
     *      "email": "test3@visionet.com.cn",
     *      "plainPassword":"111111"
     *}
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} loginName 登录名
     * @apiSuccess {String} aliasName 昵称
     * @apiSuccess {String} trueName 分机号
     * @apiSuccess {String} jobNumber 工号
     * @apiSuccess {String} avatar 头像url
     * @apiSuccess {String} locale 语言
     * @apiSuccess {String} phoneNumber 电话
     * @apiSuccess {String} email 邮箱
     * @apiSuccess {String} firstLetter 首字母
     * @apiSuccess {String} userType 经理模式:M;客服模式:K
     * @apiSuccess {String} userStatus 忙闲状态（B:忙;I:闲）
     * @apiSuccess {Long} softphoneGroupId 软电话坐席组（有此字段则同步软电话接口）(值来自于/console/channel/info/search的查询结果:phoneGroupId)
     * @apiSuccess {String} remark 备注
     * @apiSuccess {String} orgId 公司ID
     * @apiSuccess {String} isLock 是否注销(1:是)
     * @apiSuccess {String} lastLogin 最后登录日期
     * @apiSuccess {Role} roleSet 用户权限
     *
     * @apiSuccessExample User:
     *{
     *  "id": 10,
     *  "loginName": "test3",
     *  "aliasName": "测试3",
     *  "trueName": "A0932",
     *  "plainPassword": "111111",
     *  "userType": "K",
     *  "userStatus": null,
     *  "avatar": null,
     *  "locale": "zh",
     *  "firstLetter": "CS3",
     *  "phoneNumber": "13511111111",
     *  "email": "test3@visionet.com.cn",
     *  "softphoneGroupId":2,
     *  "remark": null,
     *  "orgId": 1,
     *  "isLock": 0,
     *  "lastLogin": null,
     *  "roleSet": [
     *    {
     *      "id": 3,
     *      "name": null,
     *      "roleDesc": null,
     *      "type": null,
     *      "permissions": null,
     *      "defaultUrl": null
     *    }
     *  ],
     *  "roleNames": ""
     *}
     *
     * @apiErrorExample Error-Response:
     *     {"code":"10008","msg":"该登录名已注册!"}
     */
    @RequiresRoles(value = {SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user) throws Exception{
        user.setId(null);

        if (user.getLoginName() == null) {
            throwException(BusinessStatus.REQUIRE, "loginName is null!");
        }
        if (user.getPlainPassword() == null) {
            throwException(BusinessStatus.REQUIRE, "plainPassword is null!");
        }
        if (user.getAliasName() == null) {
            throwException(BusinessStatus.REQUIRE, "aliasName is null!");
        }
        if (!AccountService.isSupervisor(getCurrentUserId())) {
            user.setOrgId(getCurrentOrgId());
        } else if (Validator.isNull(user.getOrgId())) {
            throwException(BusinessStatus.REQUIRE, "orgId is null!");
        }

        // bind roleList
        if (Collections3.isEmpty(user.getRoleSet())) {
            Role role = new Role(roleService.findRoleIdByName(SysConstants.CUSTOMER_SERVICE));
            user.getRoleSet().add(role);
        }


        user.setOrgId(getCurrentOrgId());
        user.setIsLock(SysConstants.USER_ACTIVITY_ENABLED);
        user.setLocale(getLocale());
        UserService.entryptPassword(user);
        //确保登录名唯一
        userService.registerUser(user);

        Map<String,Object> map = Maps.newHashMap();
        map.put("id",user.getId());
        map.put(MobileKey.CODE, BusinessStatus.OK);

        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
    }


    /**
     * @apiDescription 管理员户锁定或解锁用户
     * @api {get} /mobile/user/disable/:id/:isLock /mobile/user/disable/:id/:isLock
     * @apiVersion 2.0.0
     * @apiName disableUser
     * @apiGroup console-user
     * @apiPermission subadmin
     *
     * @apiParam {Long} id 用户ID
     * @apiParam {Integer} isLock 1:注销;0:恢复 (注：恢复注销用户时，软电话坐席此时不会自动恢复，需要再调修改密码接口才能恢复)
     *
     * @apiSuccess {String} code 成功标志
     *
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequiresRoles(value = {SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/disable/{id}/{isLock}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> disableUser(@PathVariable("id") Long id, @PathVariable("isLock") Integer isLock) throws RestException, Exception {
        userService.lockUser(id, isLock);
        return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
    }

}
