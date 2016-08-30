package com.visionet.letsdesk.app.user.controller;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.user.entity.Organization;
import com.visionet.letsdesk.app.user.service.AccountService;
import com.visionet.letsdesk.app.user.service.OrganizationService;
import com.visionet.letsdesk.app.user.vo.OrganizationVo;
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
@RequestMapping("/console/2.0/org")
public class CsOrganizationController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(CsOrganizationController.class);

    @Autowired
    private OrganizationService organizationService;

    @RequiresRoles(value = { SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value ="/list", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> searchUser(@RequestBody OrganizationVo vo) throws Exception {
        Page<Organization> page = organizationService.findAllOrganizations(vo);
        List<OrganizationVo> listVo = BeanConvertMap.mapList(page.getContent(), OrganizationVo.class);
        return new ResponseEntity<Page<OrganizationVo>>(GetPageByList(page,listVo, OrganizationVo.class), HttpStatus.OK);
    }



    @RequiresRoles(value = { SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        if(id.longValue()==0){
            id = getCurrentOrgId();
        }else if(getCurrentOrgId().longValue() != id.longValue()){
            throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.web.business.FileUploadController.permision.deny"));
        }

        Organization org = organizationService.findOrganizationById(id);
        OrganizationVo vo = BeanConvertMap.map(org,OrganizationVo.class);
//        vo.setIsFollow(organizationService.isFollow(getCurrentUserId(),id)?1:0);

        return new ResponseEntity<OrganizationVo>(vo, HttpStatus.OK);
    }



    @RequiresRoles(value = { SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Organization organization) throws Exception{

        if(!AccountService.isSupervisor(getCurrentUserId()) && getCurrentOrgId().longValue() != organization.getId().longValue()){
            throwException(BusinessStatus.ILLEGAL,MessageSourceHelper.GetMessages("app.web.business.FileUploadController.permision.deny"));
        }

        organizationService.update(organization);
        return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
    }


    @RequiresRoles(SysConstants.ADMINISTRATOR)
    @RequestMapping(value = "delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        organizationService.deteteOrganization(id);
        return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
    }



    @RequestMapping(value = "checkName")
    @ResponseBody
    public ResponseEntity<?> checkName(@RequestParam("oldName") String oldName,
                                       @RequestParam("name") String name) {
        Map<String,String> map = Maps.newHashMap();
        if (name.equals(oldName)) {
            map.put("code","true");
        } else if (organizationService.findOrgByOrgFullName(name) == null) {
            map.put("code","true");
        }else{
            map.put("code","false");
        }

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "checkDomain")
    @ResponseBody
    public ResponseEntity<?> checkDomain(@RequestParam("oldDomain") String oldDomain,
                                         @RequestParam("domain") String domain) {
        Map<String,String> map = Maps.newHashMap();
        if (domain.equals(oldDomain)) {
            map.put("code","true");
        } else if (organizationService.findOrgByDomain(domain) == null) {
            map.put("code","true");
        }else{
            map.put("code","false");
        }

        return new ResponseEntity<Map<String, String>>(map, HttpStatus.OK);
    }


    /**
     * 组织二维码图片url （扫描加入）
     * @param orgId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/matrix/{orgId}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> matrix(@PathVariable("orgId") Long orgId) throws Exception{
        Organization org = organizationService.findOrganizationById(orgId);
        String[] url= OrganizationService.GetOrganizationMatrix(org);

        Map<String,String> map = Maps.newHashMap();
        map.put(MobileKey.CODE, BusinessStatus.OK);
        map.put("url",url[0]);
        map.put("href",url[1]);

        return new ResponseEntity<Map<String,String>>(map,HttpStatus.OK);
    }




}
