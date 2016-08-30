package com.visionet.letsdesk.app.user.repository;

import com.visionet.letsdesk.app.user.entity.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OrganizationDao extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {


    @Modifying
    @Query("update Organization o set o.isLock=1 where o.id=?1")
    void deleteOrg(Long id);

    Organization findByFullNameAndIsLock(String orgName,Integer isLock);

    Organization findByDomainAndIsLock(String domain,Integer isLock);
    Organization findByDomain(String domain);

}
