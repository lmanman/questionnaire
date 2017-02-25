package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.base.rest.RestException;
import com.visionet.letsdesk.app.base.service.ServiceException;
import com.visionet.letsdesk.app.common.cache.EhcacheUtil;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.ImageUtil;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.security.utils.Digests;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.utils.Encodes;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.pingying.CnToSpell;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.UploadUtil;
import com.visionet.letsdesk.app.foundation.KeyWord;
import com.visionet.letsdesk.app.user.entity.Organization;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.OrganizationDao;
import com.visionet.letsdesk.app.user.repository.UserDao;
import com.visionet.letsdesk.app.user.repository.UserSearchSpecs;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserService extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(UserService.class);


    public static final int HASH_INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;


    @Autowired
    private UserDao userDao;
    @Autowired
    private OrganizationDao organizationDao;


    public User getUser(Long id) {
        return userDao.findOne(id);
    }

    /**
     * 使用二级缓存查询User
     * @param loginName
     * @return
     */
    public User findByLoginName(String loginName) {
        return userDao.findByLoginName(loginName);
    }


    public Page<User> searchUser(UserVo user) {
        PageInfo pageInfo = user.getPageInfo();
        if(pageInfo==null){
            pageInfo = new PageInfo();
        }

        return  userDao.findAll(UserSearchSpecs.searchUserByCondition(user), pageInfo.getPageRequestInfo());
    }

    @Transactional(readOnly = false)
    public void saveUser(User user) {
        if(user.getId()==null){
            throwException(BusinessStatus.REQUIRE,"id is null!");
        }

        this.checkUserInfo(user);

        user.setFirstLetter(CnToSpell.getPinYinHeadChar(user.getAliasName()).toUpperCase());
        userDao.save(user);
    }


    private void checkUserInfo(User user){
        if(userDao.checkByLoginName(user.getLoginName(), user.getId()) != null){
            throw new RestException(MessageSourceHelper.GetMessages("register.loginName.exist"));
        }
//        if(userDao.checkByAliasName(user.getAliasName(), user.getOrgId(),user.getId()) > 0){
//            throw new RestException(MessageSourceHelper.GetMessages("register.aliasName.exist"));
//        }
        if (AccountService.isSupervisor(user.getId())) {
            logger.warn("Operator{}want to modify admin!", BaseController.getCurrentUserName());
            throw new ServiceException(MessageSourceHelper.GetMessages("app.service.account.AccountService.modify.admin"));
        }
    }




    @Transactional(readOnly = false)
    public void updateUserRole(Long userId,Set<Role> roleSet) {
        User po = userDao.findOne(userId);
        po.setRoleSet(roleSet);
        userDao.save(po);
    }

    /**
     * 用户修改自己密码
     * 领导可以锁定秘书功能
     * @param user
     */
    @Transactional(readOnly = false)
    public void updateUserPasswd(User user) {
        User po = userDao.findOne(user.getId());

        // 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
        if (StringUtils.isNotBlank(user.getPlainPassword())) {
            entryptPassword(user);
            po.setPassword(user.getPassword());
            po.setPasswordSalt(user.getPasswordSalt());
        }

        userDao.save(po);
    }


    /**
     * 设定安全的密码，生成随机的salt并经过1024次 sha-1 hash
     * CAS 接口不支持salt
     */
    public static void entryptPassword(User user) {
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        user.setPasswordSalt(Encodes.encodeHex(salt));

        byte[] hashPassword = Digests.sha1(user.getPlainPassword().getBytes(), salt, HASH_INTERATIONS);
        user.setPassword(Encodes.encodeHex(hashPassword));
    }

    public static String getEntryptPassword(String plainPassword,String salt){
        byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), Encodes.decodeHex(salt), HASH_INTERATIONS);
        return Encodes.encodeHex(hashPassword);
    }




    @Transactional(readOnly = false)
    public void updateUserImg(Map<String, Object> params, Long currentUserId)
            throws Exception {
        String xStr = params.get("x") + "";
        String yStr = params.get("y") + "";
        String wStr = params.get("w") + "";
        String hStr = params.get("h") + "";
//        String filePath = (String) params.get("filePath");
        String fileType = (String) params.get("fileType");
        String userImage = (String) params.get("userImage");

        int x = 0;
        int y = 0;
        int w = 150;
        int h = 150;

        if (Validator.isNotNull(xStr)) {
            x = Integer.valueOf(xStr);
        }
        if (Validator.isNotNull(yStr)) {
            y = Integer.valueOf(yStr);
        }
        if (Validator.isNotNull(wStr)) {
            w = Integer.valueOf(wStr);
        }
        if (Validator.isNotNull(hStr)) {
            h = Integer.valueOf(hStr);
        }

        String realFilePath = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
        realFilePath = realFilePath.replace(StringPool.BACK_SLASH, StringPool.SLASH);
        if(realFilePath.startsWith(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH))){
            realFilePath = realFilePath.replaceFirst(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH),"");
        }
        realFilePath = realFilePath + userImage;

        String userCuttedImgPath = ImageUtil.saveImgByPoint(x, y, w, h, realFilePath, fileType);

        //保存用户信息
        User user = userDao.findOne(currentUserId);
        user.setAvatar(UploadUtil.GetDownloadPath() + userCuttedImgPath);
        userDao.save(user);

        EhcacheUtil.SetUser(user);

    }

    public User findByOrgIdAliasName(Long companyId,String aliasName){
        List<User> ul = userDao.findByOrgIdAndAliasName(companyId, aliasName);
        if(null != ul && ul.size()>0){
            return ul.get(0);
        }else{
            return null;
        }
    }

    public void checkOrgValid(Long userId){
        if(AccountService.isSupervisor(userId)){
            return;
        }
        User user = userDao.findOne(userId);
        if(user==null){
            throwException(BusinessStatus.NOTFIND,"userId not exist!");
        }
        Organization organization = organizationDao.findOne(user.getOrgId());
        if(organization==null){
            throwException(BusinessStatus.NOTFIND,"organization not exist!");
        }
        if(organization.getIsLock().intValue() == SysConstants.USER_ACTIVITY_DISABLED){
            throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.user.service.UserService.org.disabled"));
        }
    }

    @Transactional(readOnly = false)
    public void registerUser(User user) {
        this.checkUserInfo(user);
        if(user.getId()!=null) {
            throwException(BusinessStatus.ILLEGAL,"id exist!");
        }
        user.setFirstLetter(CnToSpell.getPinYinHeadChar(user.getAliasName()).toUpperCase());
        userDao.save(user);
        EhcacheUtil.SetUser(user);
    }


    /**
     * 逻辑delete用户
     * @param userId
     */
    @Transactional(readOnly = false)
    public void lockUser(Long userId,Integer isLock){
        User user = userDao.findOne(userId);
        if(user.getIsLock().intValue() == isLock.intValue()){
            if(isLock == KeyWord.DEL_STATUS){
                throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.user.service.UserService.user.lock.already"));
            }else {
                throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.user.service.UserService.user.unlock.already"));
            }
        }
        user.setIsLock(isLock);

        userDao.save(user);
    }
}
