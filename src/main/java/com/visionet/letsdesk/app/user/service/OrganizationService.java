package com.visionet.letsdesk.app.user.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.thread.TheadConstants;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.QRCodeUtil;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.user.entity.Organization;
import com.visionet.letsdesk.app.user.repository.OrganizationDao;
import com.visionet.letsdesk.app.user.vo.OrganizationVo;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.Map;

@Service
public class OrganizationService extends BaseService{

    @Autowired
    private OrganizationDao orgDao;

    /**
     * 用户自注册新公司
     * @param vo
     */
    @Transactional(readOnly=false)
    public Organization registerOrg(UserVo vo) {
        if (Validator.isNull(vo.getDomain())) {
            throwException(BusinessStatus.REQUIRE, "domain is null!");
        }
        if (Validator.isNull(vo.getOrgName())) {
            throwException(BusinessStatus.REQUIRE, "orgName is null!");
        }

        Organization organization = new Organization();
        organization.setDomain(vo.getDomain());
        organization.setShortName(vo.getOrgName());
        organization.setFullName(vo.getOrgName());
        organization.setEmail(vo.getEmail());
        organization.setIsLock(SysConstants.USER_ACTIVITY_ENABLED);
        organization.setCreateDate(DateUtil.getCurrentDate());

        this.checkOrgValid(organization.getDomain(), organization.getFullName());

        orgDao.save(organization);

        this.orgDataInitialize(organization);

        return organization;
    }

    /**
     * 新公司注册后的Default数据设置
     * @param org
     */
    @Transactional(readOnly=false)
    public void orgDataInitialize(Organization org){
        //Default分配逻辑

    }

    public boolean checkOrgValid(String domain,String fullName){
        if (Validator.isNull(domain)) {
            throwException(BusinessStatus.REQUIRE, "domain is null!");
        }
        if (Validator.isNull(fullName)) {
            throwException(BusinessStatus.REQUIRE, "orgName is null!");
        }
        if (orgDao.findByDomainAndIsLock(domain, SysConstants.USER_ACTIVITY_ENABLED) != null) {
            throwException(BusinessStatus.NAMEREPEAT, MessageSourceHelper.GetMessages("app.user.service.RegisterService.domain.exist"));
        }
        if (orgDao.findByFullNameAndIsLock(fullName, SysConstants.USER_ACTIVITY_ENABLED) != null) {
            throwException(BusinessStatus.NAMEREPEAT,MessageSourceHelper.GetMessages("app.user.service.RegisterService.orgName.exist"));
        }

        return true;
    }


    @Transactional(readOnly=false)
    public void update(Organization organization) throws Exception{
        if(Validator.isNull(organization.getId())){
            throwException(BusinessStatus.NOTFIND,"id not exist!");
        }
        Organization po = orgDao.findOne(organization.getId());
        SearchFilterUtil.copyBeans(po, organization);
        orgDao.save(po);
    }


    public Organization findOrganizationById(Long Id) {
        return orgDao.findOne(Id);
    }

    public Page<Organization> findAllOrganizations(OrganizationVo vo) throws Exception{
        PageInfo pageinfo = vo.getPageInfo();
        if(pageinfo==null){
            pageinfo = new PageInfo();
        }
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(vo);
        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);
        Specification<Organization> spec = DynamicSpecifications.bySearchFilter(filters.values(), Organization.class);
        PageRequest pageRequest = buildPageRequest(pageinfo.getPageNumber(), pageinfo.getPageSize(), pageinfo.getSortColumn());
        return orgDao.findAll(spec,pageRequest);
    }


    @Transactional(readOnly = false)
    public void deteteOrganization(Long id){
        orgDao.deleteOrg(id);
    }

    public Organization findOrgByOrgFullName(String orgName) {
        return orgDao.findByFullNameAndIsLock(orgName, SysConstants.USER_ACTIVITY_ENABLED);
    }


    public Organization findOrgByDomain(String domain) {
        return orgDao.findByDomainAndIsLock(domain, SysConstants.USER_ACTIVITY_ENABLED);
    }



    /**
     * 获取组织二维码
     * @param org
     * @throws Exception
     */
    public static String[] GetOrganizationMatrix(Organization org) throws Exception{
        Long orgId = org.getId();
        String logoPath = org.getLogoUrl();
        //二维码图片 下载 URL
        String matrixUrl =  PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH)
                + "matrix/org"+orgId+".jpg";
        //二维码图片 硬盘路径
        String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH)
                + "matrix/";
        String fileName="org"+orgId+".jpg";
        String logoUrl="";
        if(Validator.isNotNull(logoPath)) {
            logoUrl = PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN) + PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH) + logoPath;
            logoPath = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH) + logoPath;
        }
        //接口URL
        String interfaceUrl= PropsUtil.getProperty(PropsKeys.NGINX_DOMAIN)+ "#/v-info/"+orgId+"?name="+org.getFullName()
                +"&imgUrl="+logoUrl;

        if(new File(path+fileName).exists()){
            return new String[]{matrixUrl,interfaceUrl};
        }

        QRCodeUtil.encode("org" + orgId, interfaceUrl, logoPath, path, true);

        return new String[]{matrixUrl,interfaceUrl};
    }

    /**
     * 根据用户付费情况选择线程池类型
     * @param orgId
     * @return
     */
    public static String GetOrgPoolPayType(Long orgId){
        //TODO
        return TheadConstants.Thread_Allocate_Pay;
    }

}
