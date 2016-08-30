package com.visionet.letsdesk.app.user.repository;

import com.visionet.letsdesk.app.user.entity.Resource;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface ResourceDao extends PagingAndSortingRepository<Resource, Long> {

    @Query("from Resource  where type>=0 order by type,position ")
    public List<Resource> findLeftMenuByUser() ;

    @Query("from Resource where type>=0 and permission !='system'  order by type,position ")
    public List<Resource> findLeftMenuBySubadmin() ;

    @Query("from Resource where type>=0 and permission in (?1)  order by type,position ")
    public List<Resource> findLeftMenuByUser(List<String> permissions) ;

    @Query("from Resource  where type=0 order by position ")
    public List<Resource> findHeaderMenuByUser() ;

    @Query("from Resource r where position>0 order by type,position ")
    public List<Resource> findAllPermission() ;

}