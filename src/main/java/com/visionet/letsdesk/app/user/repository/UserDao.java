package com.visionet.letsdesk.app.user.repository;

import com.visionet.letsdesk.app.user.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface UserDao extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {


    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    public User findByLoginName(String loginName);

    @Query("select u.id from User u where u.loginName=?1 and u.id <> ?2 ")
	Long checkByLoginName(String loginName, Long userId);

	@Query("select count(u) from User u where u.isLock=0 ")
	Long findUserCount();


    @Query("select u from User u where u.id in (?1) ")
    List<User> findByIdList(List<Long> ids);

    public List<User> findByOrgIdAndAliasName(Long companyId,String aliasName);

    @Query("select u.id,u.aliasName from User u inner join u.roleSet r " +
            "where u.isLock=0 and r.name in (?1) and u.orgId=?2 order by u.id ")
    List<Object[]> findUserWithRole(List<String> roleNames,Long orgId);

}
