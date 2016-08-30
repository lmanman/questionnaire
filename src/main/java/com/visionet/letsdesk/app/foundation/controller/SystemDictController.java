package com.visionet.letsdesk.app.foundation.controller;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.foundation.entity.SystemDict;
import com.visionet.letsdesk.app.foundation.service.SystemDictService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/tools/dict")
public class SystemDictController extends BaseController {

    @Autowired
    private SystemDictService systemDictService;

    @RequestMapping(value = "list",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findList(@RequestParam("systemType") String type) {
        List<SystemDict> list;
        if(!Validator.isNull(type)){
            list = Lists.newArrayList();
            List<SystemDict> temp = systemDictService.findBySystemType(type);

            for(SystemDict dict:temp){
                if(dict.getSystemDesc()!=null && (dict.getSystemDesc().equals("0") || dict.getSystemDesc().equals(getCurrentOrgId().toString()))){
                    list.add(dict);
                }
            }
        }else{
            list = systemDictService.findAll();
        }

        return new ResponseEntity<List<SystemDict>>(list, HttpStatus.OK);
    }

    @RequestMapping(value = "name",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> findName(@RequestParam("systemType") String type,@RequestParam("systemCode") String code) {
        if(Validator.isNull(type) || Validator.isNull(code)){
            throwException(BusinessStatus.REQUIRE,"type or code is null!");
        }
        Map map = Maps.newHashMap();
        map.put("name",systemDictService.findSystemNameByCode(type,code));
        return new ResponseEntity<Map<String,String>>(map, HttpStatus.OK);
    }


}
