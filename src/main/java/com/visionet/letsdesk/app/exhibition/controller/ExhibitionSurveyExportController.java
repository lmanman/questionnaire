package com.visionet.letsdesk.app.exhibition.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.common.modules.office.ExcelUtil;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionSurveyExportService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping(value = "/mobile/exhibition/survey/export")
public class ExhibitionSurveyExportController extends BaseController{
    private static Logger log = LoggerFactory.getLogger(ExhibitionSurveyExportController.class);

    @Autowired
    private ExhibitionSurveyExportService exhibitionSurveyExportService;


    /**
     * @apiDescription 管理员导出用户Excel
     * @api {get} /mobile/exhibition/survey/export/:formId /mobile/exhibition/survey/export/:formId
     * @apiVersion 2.0.0
     * @apiName exportSurvey
     * @apiGroup SurveyExport
     * @apiPermission user
     *
     * @apiSuccessExample OutputStream
     *  Excel文件流
     */
    @RequiresRoles(value = {SysConstants.ADMINISTRATOR, SysConstants.SUBADMIN}, logical = Logical.OR)
    @RequestMapping(value = "/{formId}", method = RequestMethod.GET)
    public void exportSurvey(@PathVariable Long formId,HttpServletResponse response) throws Exception {
        List<String[]> list = exhibitionSurveyExportService.getExportList(formId);
        File filePath = new File(ExcelUtil.createExcel(list));

        OutputStream outputStream = null;
        FileInputStream SourceFile = new FileInputStream(filePath);
        try {
            String time = DateUtil.convertToString(DateUtil.getCurrentDate(), DateUtil.YMD1);
            outputStream = response.getOutputStream();
            response.setHeader("Content-disposition", "attachment; filename="
                    + java.net.URLEncoder.encode("user_info_" + time + ".xlsx", "utf-8"));
            byte readFromFile[] = new byte[1024];
            int len;
            while ((len = SourceFile.read(readFromFile)) > 0) {
                outputStream.write(readFromFile, 0, len);
            }
            outputStream.flush();
        } catch (IOException ie) {
            log.error(ie.toString(), ie);
            log.error("export user ioExeption:", ie);
        } catch (Exception e) {
            log.error(e.toString(), e);
            log.error("export user exeption:", e);
        } finally {
            try {
                if (outputStream != null) outputStream.close();
            } catch (Exception e2) {
                log.error("outputStream close exception:", e2);
            }
            try {
                SourceFile.close();
            } catch (Exception e2) {
                log.error("SourceFile close exception:", e2);
            }
            try {
                if (filePath.exists()) {
                    filePath.delete();
                }
            } catch (Exception e2) {
                log.error("delete export file exception:", e2);
            }
        }
    }

}
