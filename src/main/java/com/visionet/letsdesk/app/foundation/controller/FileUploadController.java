package com.visionet.letsdesk.app.foundation.controller;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.attachment.vo.AttachmentVo;
import com.visionet.letsdesk.app.base.controller.BaseController;
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
     * @apiDescription 通用上传(登录验证)
     * @api {post} /mobile/upload /mobile/upload
     * @apiVersion 2.0.0
     * @apiName upload
     * @apiGroup Common
     * @apiPermission user
     *
     * @apiParam {HttpServletRequest} request form-data文件上传
     *
     * @apiSuccess {String} realName 文件原名
     * @apiSuccess {String} uuidName 文件原名(图片类型，文件名末尾会有“_width*height”表示图片宽高)
     * @apiSuccess {String} url 文件http相对路径（回传业务接口）
     * @apiSuccess {String} state 成功标志（成功：SUCCESS，flash接收用）
     *
     * @apiSuccessExample {json} List<AttachmentVo>
     *      [
     *        {
     *          "realName": "dev.p12",
     *          "uuidName": "94f35dca-b57d-4143-aaf5-51efe35d763a-7426.p12",
     *          "url": "/downloadFile/visionet/2016-10-12/certificate/94f35dca-b57d-4143-aaf5-51efe35d763a-7426.p12",
     *          "state":"SUCCESS"
     *        },
     *        {
     *          "realName": "prod.p12",
     *          "uuidName": "62fd8c75-6215-4022-b849-c586773e8360-5391.p12",
     *          "url": "/downloadFile/visionet/2016-10-12/certificate/62fd8c75-6215-4022-b849-c586773e8360-5391.p12",
     *          "state":"SUCCESS"
     *        },
     *        {
     *          "uuidName": "4b543020-4927-4f9d-a383-3a09f6ccf47d-199_800*600.jpg",
     *          "realName": "BingWallpaper-2016-07-01.jpg",
     *          "url": "/downloadFile/visionet/2016-10-18/image/4b543020-4927-4f9d-a383-3a09f6ccf47d-199_800*600.jpg",
     *          "state": "SUCCESS"
     *        }
     *      ]
     */
    @RequestMapping(value = "/mobile/upload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> upload(HttpServletRequest request)	throws Exception {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<AttachmentVo> resultList = Lists.newArrayList();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            resultList.add(FileUploadService.Upload(entity.getValue(),getCurrentOrgId()));
        }
        return new ResponseEntity<List<AttachmentVo>>(resultList, HttpStatus.OK);
    }


}
