package com.visionet.letsdesk.app.exhibition.controller;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionSurveyFieldService;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyFieldVo;
import com.visionet.letsdesk.app.foundation.KeyWord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/mobile/exhibition/survey/field")
public class ExhibitionSurveyFieldController extends BaseController{


    @Autowired
    private ExhibitionSurveyFieldService exhibitionSurveyFieldService;

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id) throws Exception{
        List<ExhibitionSurveyFieldVo> list = exhibitionSurveyFieldService.findExhibitionField(id);
        final List<ExhibitionSurveyFieldVo>[] listArr = new ArrayList[]{Lists.newArrayList(),Lists.newArrayList(),Lists.newArrayList()};

        list.stream().forEach(vo->{
            if(vo.getIndicator().equals(KeyWord.FIELD_INDICATOR_BASE) || vo.getIndicator().equals(KeyWord.FIELD_INDICATOR_ENVIRONMENT)) {
                listArr[0].add(vo);
            }else if(vo.getIndicator().equals(KeyWord.FIELD_INDICATOR_RECEPTION) || vo.getIndicator().equals(KeyWord.FIELD_INDICATOR_PROMOTION)){
                listArr[1].add(vo);
            }else if(vo.getIndicator().equals(KeyWord.FIELD_INDICATOR_PUBLIC_RESOURCE)){
                listArr[2].add(vo);
            }
        });
        return new ResponseEntity<List<ExhibitionSurveyFieldVo>[]>(listArr, HttpStatus.OK);
    }

    @RequestMapping(value = "/short/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> shortDetail(@PathVariable Long id) throws Exception{
        List<ExhibitionSurveyFieldVo> list = exhibitionSurveyFieldService.findExhibitionFieldShort(id);

        return new ResponseEntity<List<ExhibitionSurveyFieldVo>>(list, HttpStatus.OK);
    }

}
