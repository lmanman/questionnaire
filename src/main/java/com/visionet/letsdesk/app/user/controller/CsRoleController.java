package com.visionet.letsdesk.app.user.controller;

import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.user.entity.Resource;
import com.visionet.letsdesk.app.user.entity.Role;
import com.visionet.letsdesk.app.user.service.ResourceService;
import com.visionet.letsdesk.app.user.service.RoleService;
import com.visionet.letsdesk.app.user.vo.RoleVo;
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
@RequestMapping("/console/role")
public class CsRoleController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CsRoleController.class);

    @Autowired
    private RoleService roleService;
    @Autowired
    private ResourceService resourceService;

    @RequiresRoles(value = { SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value ="/search", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> search(@RequestBody RoleVo vo) throws Exception {
        Page<Role> users = roleService.searchRoles(vo);
        return new ResponseEntity<Page<Role>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> list() {
        return new ResponseEntity<List<Role>>(roleService.allRoles(), HttpStatus.OK);
    }

    @RequestMapping(value = "allPermissions", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> allPermissions() {
        return new ResponseEntity<List<Resource>>(roleService.getAllPermissions(), HttpStatus.OK);
    }

    @RequiresRoles(SysConstants.ADMINISTRATOR)
    @RequestMapping(value = "save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody RoleVo vo) throws Exception{
        if(vo.getType()==null){
            vo.setType(SysConstants.COMMON_ROLE_TYPE);
        }

        if(Validator.isNull(vo.getPermissions())){
            throwException(BusinessStatus.NOTFIND,"permission is null!");
        }
        Role role = BeanConvertMap.map(vo, Role.class);
        if(vo.getId()!=null){
            Role po = roleService.getRole(vo.getId());
            SearchFilterUtil.copyBeans(po, role);
            roleService.saveRole(po);
        }else{
            roleService.saveRole(role);
        }


        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }

    @RequiresRoles(SysConstants.ADMINISTRATOR)
    @RequestMapping(value = "save/resource", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> saveResource(@RequestBody Resource resource) throws Exception{

        if(Validator.isNull(resource.getName())){
            throwException(BusinessStatus.NOTFIND,"name is null!");
        }
        if(Validator.isNull(resource.getHyperlink())){
            throwException(BusinessStatus.NOTFIND,"Hyperlink is null!");
        }
        if(resource.getType() == null){
            throwException(BusinessStatus.NOTFIND,"type is null!");
        }
        if(Validator.isNull(resource.getPermission())){
            throwException(BusinessStatus.NOTFIND,"Permission is null!");
        }else if(resource.getPermission().length()>30){
            throwException(BusinessStatus.LENGTH_LIMIT,"Permission is too long!");
        }
        resourceService.save(resource);

        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }



    @RequiresRoles(value = { SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/{id}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        Role role = roleService.getRole(id);
        RoleVo vo = BeanConvertMap.map(role,RoleVo.class);
//        vo.setResourceList(BeanMapper.mapList(roleService.getAllPermissions(), ResourceVo.class));
        return new ResponseEntity<RoleVo>(vo, HttpStatus.OK);
    }




    @RequiresRoles(SysConstants.ADMINISTRATOR)
    @RequestMapping(value = "delete/{id}")
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }


    @RequestMapping(value = "checkName")
    @ResponseBody
    public ResponseEntity<?> checkName(@RequestParam("oldName") String oldName,
                                       @RequestParam("name") String name) {
        Map<String,Boolean> map = Maps.newHashMap();
        if (name.equals(oldName)) {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_TRUE);
        } else if (roleService.findRoleByName(name) == null) {
            map.put(MobileKey.CODE, BusinessStatus.RESULT_TRUE);
        }else{
            map.put(MobileKey.CODE, BusinessStatus.RESULT_FALSE);
        }
        return new ResponseEntity<Map<String,Boolean>>(map , HttpStatus.OK);
    }


}
