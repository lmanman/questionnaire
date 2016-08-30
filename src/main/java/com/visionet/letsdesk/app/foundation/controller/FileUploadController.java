package com.visionet.letsdesk.app.foundation.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.foundation.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
public class FileUploadController extends BaseController {


    /**
     * 通用上传
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> upload(HttpServletRequest request)	throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<Map<String, Object>> resultList = Lists.newArrayList();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            String[] filePath = FileUploadService.Upload(entity.getValue());
            Map<String,Object> map = Maps.newHashMap();
            map.put("filePath", filePath[0]);
            map.put("url", PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH) + filePath[1]);
            resultList.add(map);
        }
        return new ResponseEntity<List<Map<String,Object>>>(resultList, HttpStatus.OK);
    }

}
