package com.visionet.letsdesk.app.attachment.controller;

import com.visionet.letsdesk.app.attachment.service.AttachmentService;
import com.visionet.letsdesk.app.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/attachment")
public class AttachementController extends BaseController {

    @Autowired
    private AttachmentService attachmentService;

    /**
     * 问卷附件上传
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload/{refId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> upload(HttpServletRequest request,@RequestBody Map<String,String> fieldMap,@PathVariable Long refId)	throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<Map<String, Object>> resultList = attachmentService.upload(fileMap,refId,fieldMap);
        return new ResponseEntity<List<Map<String,Object>>>(resultList, HttpStatus.OK);
    }

}
