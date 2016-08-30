package com.visionet.letsdesk.app.foundation.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.cache.GuavaCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.Map;

@Controller
@RequestMapping(value = "/open/tools")
public class TestToolsController extends BaseController{
    private static Logger logger = LoggerFactory.getLogger(TestToolsController.class);



    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public ResponseEntity<?> test() throws Exception{
//        String token = weixinRestClient.getComponentToken();
//        Map<String,String> map = GetSuccMap();
//        map.put("token",token);


        return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
    }

    @RequestMapping(value = "/job",method = RequestMethod.GET)
    public ResponseEntity<?> job() throws Exception{
//        weixinRefreshJob.init();

        return new ResponseEntity<Map<String, String>>(GetSuccMap(), HttpStatus.OK);
    }

}
