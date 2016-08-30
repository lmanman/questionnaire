package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.cache.EhcacheUtil;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.pingying.CnToSpell;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.user.entity.Organization;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.OrganizationDao;
import com.visionet.letsdesk.app.user.repository.UserDao;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RegisterService extends BaseService{
    private static Logger logger = LoggerFactory.getLogger(RegisterService.class);

	@Autowired
	private UserDao userDao;
    @Autowired
    private OrganizationDao organizationDao;
    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private RoleService roleService;


    /**
     * 注册新用户，新的域名则再注册新公司
     * @param vo
     * @return
     */
    @Transactional(readOnly = false)
	public User addOrgAndUser(UserVo vo) {
        User user = BeanConvertMap.map(vo, User.class);

        if(Validator.isNull(user.getLoginName())){
            throwException(BusinessStatus.ERROR,"loginName is null!");
        }
		//check User existed!
		this.checkLoginName(user.getLoginName());

		user.setIsLock(SysConstants.USER_ACTIVITY_ENABLED);
		UserService.entryptPassword(user);
		

		if(Validator.isNotNull(user.getAliasName())){
			user.setFirstLetter(CnToSpell.getPinYinHeadChar(user.getAliasName()).toUpperCase());
		}

        String domain = vo.getDomain()+"."+SysConstants.DOMAIN;
        vo.setDomain(domain);
        Organization organization = organizationDao.findByDomain(domain);
        if(organization==null) {    //域名不存在，注册新公司
            //该用户为新公司管理员
            Role role = new Role(roleService.findRoleIdByName(SysConstants.SUBADMIN));
            user.getRoleSet().add(role);

            organization = organizationService.registerOrg(vo);

        }else if(organization.getIsLock() == SysConstants.USER_ACTIVITY_DISABLED){  //公司已注销
            throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.user.service.RegisterService.domain.disabled"));
        }else {     //已有公司添加普通用户
            Role role = new Role(roleService.findRoleIdByName(SysConstants.CUSTOMER_SERVICE));
            user.getRoleSet().add(role);
        }

        user.setOrgId(organization.getId());
		userDao.save(user);

        EhcacheUtil.SetUser(user);
        return user;
	}
	
	/**
	 * 校验用户是否已注册
	 * @param loginName
	 * @return
	 */
	public boolean checkLoginName(String loginName){
		//使用二级缓存查询User
		if (userDao.findByLoginName(loginName) != null) {
            throwException(BusinessStatus.NAMEREPEAT, MessageSourceHelper.GetMessages("app.user.service.RegisterService.loginName.exist"));
		}
		return true;
	}



    /*
    @Transactional(readOnly = false)
    public void getValidateCode(Long phoneNumber) throws Exception{
        boolean openRegistration = GetterUtil.getBoolean(PropsUtil.getProperty(PropsKeys.OPEN_REGISTRATION), false);
        if(!openRegistration){
            throwException(BusinessStatus.ERROR,MessageSourceHelper.GetMessages("app.service.business.SMSMonitorService.close"));
        }
        String validateCode = Encodes.encodeHex(Digests.generateSalt(3));

        RegisterValidate rv = registerValidateService.findByPhoneNumber(phoneNumber);
        if(rv == null ){
            rv = new RegisterValidate();
            rv.setPhoneNumber(phoneNumber);
        }
        rv.setValidateCode(validateCode);
        rv.setValidateTime(DateUtil.getCurrentDate());
        registerValidateDao.save(rv);

        SMSUtil smsUtil = new SMSUtil(PropsUtil.getProperty(PropsKeys.SMS_HTTP_ACCOUNT),PropsUtil.getProperty(PropsKeys.SMS_HTTP_PASSWD));
        String ret = smsUtil.sendBatchMessage(phoneNumber.toString(),
                MessageSourceHelper.GetMessages("app.service.account.RegisterService.wsk.validcode") + validateCode);
        if(ret==null || new Long(ret.trim()).longValue()<=0){
            throwException(BusinessStatus.ERROR,MessageSourceHelper.GetMessages("app.service.account.RegisterService.sms.error")+",code:"+ret);
        }
    }
    */


    /*
    public void checkValidateCode(Long phoneNumber,String code){
        RegisterValidate rv = registerValidateDao.findByPhoneNumberAndValidateCode(phoneNumber, code);
        if (rv == null) {
            throwException(BusinessStatus.ERROR,MessageSourceHelper.GetMessages("app.service.account.RegisterService.validcode.error"));
        }else if(rv.getValidateTime() == null){
            throwException(BusinessStatus.ERROR,MessageSourceHelper.GetMessages("app.service.account.RegisterService.validcode.overdue"));
        }else{
            long codeTime = rv.getValidateTime().getTime();
            long nowTime = DateUtil.getCurrentDate().getTime();
            if(nowTime - codeTime> 1800000){
                throwException(BusinessStatus.ERROR,MessageSourceHelper.GetMessages("app.service.account.RegisterService.validcode.overdue"));
            }
        }
    }
    */

}
