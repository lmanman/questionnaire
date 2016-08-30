package com.visionet.letsdesk.app.user.repository;

import com.visionet.letsdesk.app.user.entity.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.QueryHint;
import java.util.List;

public interface RoleDao extends PagingAndSortingRepository<Role, Long>, JpaSpecificationExecutor<Role>  {

	@QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
	Role findByName(String name);
	
	@Query("from Role where type!='A' ")
	List<Role> findCommonRole();

}
