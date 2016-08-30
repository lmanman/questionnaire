package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.user.entity.Resource;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.repository.ResourceDao;
import com.visionet.letsdesk.app.user.repository.RoleDao;
import com.visionet.letsdesk.app.user.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class RoleService extends BaseService {
	
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceDao resourceDao;
	
	public Role getRole(Long id) {
		return roleDao.findOne(id);
	}

    public Role findRoleByName(String roleName) {
        return roleDao.findByName(roleName);
    }
    public Long findRoleIdByName(String name){
        Role role = roleDao.findByName(name);
        if(role==null){
            throwException(BusinessStatus.NOTFIND,"role name is not exist!");
        }
        return role.getId();
    }



    public Page<Role> searchRoles(RoleVo vo) throws Exception{
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(vo);
        PageInfo pageInfo = vo.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);

        filters.put("id", new SearchFilter("id", SearchFilter.Operator.GT, 1L));
        if(filters.get("roleDesc")!=null){
            filters.put("roleDesc", new SearchFilter("roleDesc", SearchFilter.Operator.LIKE, filters.get("roleDesc").value));
        }
        if(filters.get("permissions")!=null){
            filters.put("permissions", new SearchFilter("permissions", SearchFilter.Operator.LIKE, filters.get("permissions").value));
        }

        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(),pageInfo.getSortColumn());

        return roleDao.findAll(DynamicSpecifications.bySearchFilter(filters.values(), Role.class), pageRequest);
    }
	

	
	@Transactional(readOnly = false)
	public void saveRole(Role entity) {
		roleDao.save(entity);
	}
	
	@Transactional(readOnly = false)
	public void deleteRole(Long id) {
		roleDao.delete(id);
	}
	
	
	public List<Resource> getAllPermissions() {
		return resourceDao.findAllPermission();
	}
	


}
