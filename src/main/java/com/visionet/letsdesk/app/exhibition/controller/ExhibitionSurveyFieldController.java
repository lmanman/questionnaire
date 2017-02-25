package com.visionet.letsdesk.app.exhibition.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionSurveyFieldService;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyFieldVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value = "/mobile/exhibition/survey/field")
public class ExhibitionSurveyFieldController extends BaseController{


    @Autowired
    private ExhibitionSurveyFieldService exhibitionSurveyFieldService;

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id) throws Exception{

        return new ResponseEntity<List<ExhibitionSurveyFieldVo>>(exhibitionSurveyFieldService.findExhibitionField(id), HttpStatus.OK);
    }

}
