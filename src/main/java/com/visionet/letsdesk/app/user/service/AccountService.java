package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.utils.Collections3;
import com.visionet.letsdesk.app.user.entity.User;
import com.visionet.letsdesk.app.user.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户管理业务类.
 * 
 * @author xt
 */
@Service
@Transactional(readOnly = true)
public class AccountService extends BaseService {

	@Autowired
	private UserDao userDao;



	/**
	 * 判断是否超级管理员.
	 */
	public static boolean isSupervisor(User user) {
		return ((user.getId() != null) && (user.getId() == 1L));
	}
	public static boolean isSupervisor(Long userId) {
		return ((userId != null) && (userId == 1L));
	}
	
	/**
	 * 判断是否超级管理员或辅助管理员
	 */
	public boolean isAdmin(Long userId) {
		if((userId != null) && (userId == 1L)){
			return true;
		}
		
		User user = getUser(userId);
		if(user == null) return false;
		
		if(SysConstants.ADMINISTRATOR.equals(user.getRoleNames()) || SysConstants.SUBADMIN.equals(user.getRoleNames())){
			return true;
		}
		
		return false;
	}

	/**
	 * 根据用户id查询用户
	 * @param id 用户id
	 * @return
	 */
	public User getUser(Long id) {
		return userDao.findOne(id);
	}



	/**
	 * 获取当前用户数量.
	 */
	public Long getUserCount() {
		return userDao.count();
	}

	/**
	 * 使用二级缓存查询User
	 * @param loginName
	 * @return
	 */
	public User findByLoginName(String loginName) {
		return userDao.findByLoginName(loginName);
	}



    /**
     * 用户user是否拥有roleName权限
     * @param user
     * @param roleName
     * @return
     */
    public static boolean HasRole(User user,String roleName){
        if(Collections3.isNotEmpty(user.getRoleSet())){
			return user.getRoleSet().stream().anyMatch(r -> r.getName().equals(roleName));
        }
        return false;
    }

    /**
     * 用户user是否拥有roleNameList中的一种
     * @param user
     * @param roleNameList
     * @return
     */
    public static boolean HasRole(User user,List<String> roleNameList){
        if(Collections3.isNotEmpty(user.getRoleSet()) && Collections3.isNotEmpty(roleNameList)){
            return user.getRoleSet().stream().anyMatch(r -> roleNameList.contains(r.getName()));
        }

        return false;
    }
}
