package com.visionet.letsdesk.app.attachment.controller;


import com.visionet.letsdesk.app.attachment.service.AttachmentService;
import com.visionet.letsdesk.app.base.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
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
     * @apiDescription 问卷附件上传
     * @api {post} /mobile/attachment/survey/upload/:refId /mobile/attachment/survey/upload/:refId
     * @apiVersion 2.0.0
     * @apiName upload
     * @apiGroup Common
     * @apiPermission user
     *
     * @apiParam {HttpServletRequest} request form-data文件上传(注：文件key为字段名，唯一，如果某字段有多个附件使用下划线和数字:fieldName_1,fieldName_2,...)
     * @apiParam {Long} refId 问卷ID
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccess {Long} id 图片/音频/视频表PK
     * @apiSuccess {String} type photo:图片;audio:音频;video:视频
     * @apiSuccess {String} url 文件http下载相对路径
     *
     * @apiSuccessExample {json} List<Map<String,Object>>
     *   [
     *     {
     *       "code": "10000",
     *       "id": 2,
     *       "type": "photo",
     *       "url": "/downloadFile/2016-09-01/photo/c13372a0-460f-435f-871f-6328ca38ad24-1347.gif"
     *     },
     *     {
     *       "code": "10000",
     *       "id": 2,
     *       "type": "audio",
     *       "url": "/downloadFile/2016-09-01/audio/ba4aca9b-a2f4-406b-b28d-a5b466d17571-8768.mp3"
     *     },
     *     {
     *       "code": "10000",
     *       "id": 2,
     *       "type": "video",
     *       "url": "/downloadFile/2016-09-01/video/b64cf9ff-277e-4e28-b59f-d8b92dace01d-9282.mp4"
     *     },
     *     {
     *       "code": "10000",
     *       "id": 3,
     *       "type": "photo",
     *       "url": "/downloadFile/2016-09-01/photo/691ab3e7-563e-40e4-9580-7a7068b24c05-8619.jpg"
     *     }
     *   ]
     */
    @RequestMapping(value = "/survey/upload/{refId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> upload(HttpServletRequest request,@PathVariable Long refId)	throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<Map<String, Object>> resultList = attachmentService.upload(fileMap, refId,getCurrentOrgId());
        return new ResponseEntity<List<Map<String,Object>>>(resultList, HttpStatus.OK);
    }


    @RequestMapping(value = "/delete/{refId}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable Long refId)	throws Exception {
        //TODO
        return new ResponseEntity<Map<String,String>>(GetSuccMap(), HttpStatus.OK);
    }

}
