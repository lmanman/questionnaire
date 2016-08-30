package com.visionet.letsdesk.app.foundation.service;

import com.visionet.letsdesk.app.foundation.entity.SystemDict;
import com.visionet.letsdesk.app.foundation.repository.SystemDictDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SystemDictService {
    private static Logger log = LoggerFactory.getLogger(SystemDictService.class);

    @Autowired
    private SystemDictDao systemDictDao;


    public List<SystemDict> findAll(){
        return systemDictDao.findDictList();
    }


    public List<SystemDict> findBySystemType(String dicType){
        return systemDictDao.findBySystemType(dicType);
    }

    public List<Map<String,String>> findBySystemType2(String systemType){
        return findBySystemType(systemType).stream().map(dict -> new HashMap<String,String>(){
            {
                put("code", dict.getSystemCode());
                put("name", dict.getSystemName());
            }
        }).collect(Collectors.toList());
    }

    public List<SystemDict> findOtherBySystemType(String systemType,List<String> existCodes){
        return systemDictDao.findOtherBySystemType(systemType,existCodes);
    }

    public String findSystemNameByCode(String systemType,String systemCode){
        return systemDictDao.findBySystemTypeAndCode(systemType,systemCode);
    }

    public Map<String,String> getSystemDictMap(String systemType) {
        return findBySystemType(systemType).stream()
                .collect(Collectors.toConcurrentMap(
                        d -> d.getSystemCode(),
                        d -> d.getSystemName()
                ));

    }

}
