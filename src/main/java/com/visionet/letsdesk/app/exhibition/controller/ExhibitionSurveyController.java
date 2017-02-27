package com.visionet.letsdesk.app.exhibition.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.SysConstants;
import com.visionet.letsdesk.app.exhibition.entity.ExhibitionSurvey;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionSurveyService;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyListVo;
import com.visionet.letsdesk.app.exhibition.vo.ExhibitionSurveyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/exhibition/survey")
public class ExhibitionSurveyController extends BaseController{

    @Autowired
    private ExhibitionSurveyService exhibitionSurveyService;


    /**
     * @apiDescription 展厅问卷查询
     * @api {post} /mobile/exhibition/survey/search /mobile/exhibition/survey/search
     * @apiVersion 2.0.0
     * @apiName search
     * @apiGroup ExhibitionSurvey
     * @apiPermission user
     *
     * @apiParam {Long} id PK
     * @apiParam {String} queryName 展厅名/品牌/品类（模糊查询）
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "pageInfo":{
     *       "pageNumber":1,
     *       "pageSize":10
     *    },
     *    "queryName":"慕斯"
     *   }
     *
     * @apiSuccess {Long} id PK
     * @apiSuccessExample {json} Page<Manufacturer>
     *   {
     *     "content": [
     *       {
     *         "id": null,
     *         "pageInfo": null,
     *         "surveyId": null,
     *         "exhibitionName": "顾家沙发古北店",
     *         "address": "0",
     *         "dealerName": null,
     *         "categoryName": "家居饰品",
     *         "brandName": "顾家沙发",
     *         "cityName": null,
     *         "schedule": [
     *           64,
     *           64
     *         ],
     *         "createBy": 3,
     *         "updateDate": null,
     *         "userName": "郭嘉"
     *       },
     *       {
     *         "id": null,
     *         "pageInfo": null,
     *         "surveyId": null,
     *         "exhibitionName": "慕斯古北店",
     *         "address": "地址11",
     *         "dealerName": null,
     *         "categoryName": "家具",
     *         "brandName": "圣象",
     *         "cityName": null,
     *         "schedule": [
     *           64,
     *           64
     *         ],
     *         "createBy": 2,
     *         "updateDate": null,
     *         "userName": "XT"
     *       }
     *     ],
     *     "totalElements": 3,
     *     "last": true,
     *     "totalPages": 1,
     *     "firstPage": false,
     *     "lastPage": true,
     *     "size": 10,
     *     "number": 1,
     *     "sort": [
     *       {
     *         "direction": "DESC",
     *         "property": "id",
     *         "ignoreCase": false,
     *         "nullHandling": "NATIVE",
     *         "ascending": false
     *       }
     *     ],
     *     "numberOfElements": 3,
     *     "first": false
     *   }
     */
    @RequestMapping(value ="/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> search(@RequestBody ExhibitionSurveyVo vo) throws Exception {
//        Page<ExhibitionSurveyVo> page = exhibitionSurveyService.search(vo);
        if(!hasRole(SysConstants.SUBADMIN)){
            vo.setCreateBy(getCurrentUserId());
        }
        Page<ExhibitionSurveyListVo> page = exhibitionSurveyService.list(vo);

        return new ResponseEntity<Page<ExhibitionSurveyListVo>>(page, HttpStatus.OK);
    }


    /**
     * @apiDescription 展厅问卷详情
     * @api {get} /mobile/exhibition/survey/:id /mobile/exhibition/survey/:id
     * @apiVersion 2.0.0
     * @apiName detail
     * @apiGroup ExhibitionSurvey
     * @apiPermission user
     * @apiParam {Long} id 展厅问卷id
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {Long} brand 品牌
     * @apiSuccess {Integer} floor 楼层
     * @apiSuccess {Integer} floorPosition 楼层位置
     * @apiSuccess {List-Integer} peripheryFacility 周边设施（10米以内）
     * @apiSuccess {Integer} exhibitionArea 展厅面积
     * @apiSuccess {List-ExhibitionSurveyPublicShow} publicShowList 公区摆展
     * @apiSuccess {List-Integer} publicAdType 公区广告类型
     * @apiSuccess {List-Integer} brandSponsorType 品牌赞助类型
     * @apiSuccess {List-Integer} hygiene 卫生
     * @apiSuccess {Integer} lighting 灯光
     * @apiSuccess {Integer} music 音乐
     * @apiSuccess {Integer} smell 气味
     * @apiSuccess {List-Integer} workbenchHygiene 工作台卫生
     * @apiSuccess {Integer} workbenchImage 工作台形象
     * @apiSuccess {List-Integer} discussionAreas 洽谈区
     * @apiSuccess {List-Integer} backgroundWallHygiene 背景墙卫生
     * @apiSuccess {List-Integer} designAreaHygiene 设计区卫生
     * @apiSuccess {Integer} designAreaImage 设计区形象
     * @apiSuccess {List-Integer} brandImagePlace 品牌形象位置
     * @apiSuccess {List-Integer} salesPromotionMaterials 促销物料
     * @apiSuccess {Integer} shopEmployeesNumber 营业员数量
     * @apiSuccess {Integer} designer 有无设计师
     * @apiSuccess {List-Integer} shopEmployeesImage 人员形象
     * @apiSuccess {Integer} welcomeGuest 迎送宾客
     * @apiSuccess {Integer} productionIntroduce 产品介绍
     * @apiSuccess {Integer} smileHello 微笑问好
     * @apiSuccess {List-Integer} violations 违规行为
     * @apiSuccess {Integer} gender 性别
     * @apiSuccess {Integer} age 年龄
     * @apiSuccess {Integer} exhibitVacant 展品空置
     * @apiSuccess {Integer} newProduction 是否有新品
     * @apiSuccess {Integer} guestInOut 顾客进店情况
     * @apiSuccess {List-Integer} guestSnack 顾客零食
     * @apiSuccess {List-Integer} guestDrink 茶水
     * @apiSuccess {List-Integer} promotionType 促销
     * @apiSuccess {List-Integer} promotionStyle 促销形式
     * @apiSuccess {Integer} specialOffer 特价款
     * @apiSuccess {ExhibitionSurveyPublicShow} publicShowList 公区摆展
     * @apiSuccess {Integer} publicExhibitionPriceTag 有无价签
     * @apiSuccess {Integer} publicExhibitionArea 公区摆展面积
     * @apiSuccess {Integer} publicExhibitionPlace 公区摆展位置
     * @apiSuccess {Integer} publicExhibitionFloor 楼层
     * @apiSuccess {Long} exhibitionId 展厅ID
     * @apiSuccess {Long} createBy 创建人
     * @apiSuccess {Long} updateBy 修改人
     * @apiSuccess {String} createDate 创建时间
     * @apiSuccess {String} updateDate 修改时
     *
     * @apiSuccessExample {json} ExhibitionVo
     *   {
     *     "id": 6,
     *     "pageInfo": null,
     *     "brand": 17,
     *     "floor": 2,
     *     "floorPosition": 3,
     *     "peripheryFacility": [
     *       5,
     *       14
     *     ],
     *     "exhibitionArea": 21,
     *     "publicShowList": [
     *       {
     *         "id": 1,
     *         "surveyId": 6,
     *         "publicExhibitionPriceTag": 1,
     *         "publicExhibitionArea": 26,
     *         "publicExhibitionFloor": 2,
     *         "publicExhibitionPlace": 30
     *       },
     *       {
     *         "id": 2,
     *         "surveyId": 6,
     *         "publicExhibitionPriceTag": 2,
     *         "publicExhibitionArea": 29,
     *         "publicExhibitionFloor": 3,
     *         "publicExhibitionPlace": 33
     *       }
     *     ],
     *     "publicAdType": [
     *       15,
     *       42
     *     ],
     *     "brandSponsorType": [
     *       43,
     *       45
     *     ],
     *     "hygiene": [
     *       46,
     *       51
     *     ],
     *     "lighting": 52,
     *     "music": 56,
     *     "smell": 60,
     *     "workbenchHygiene": [
     *       67,
     *       64
     *     ],
     *     "workbenchImage": 68,
     *     "discussionAreas": [
     *       72,
     *       75
     *     ],
     *     "backgroundWallHygiene": [
     *       76,
     *       79
     *     ],
     *     "designAreaHygiene": [
     *       80,
     *       83
     *     ],
     *     "designAreaImage": 84,
     *     "brandImagePlace": [
     *       88,
     *       91
     *     ],
     *     "salesPromotionMaterials": [
     *       92,
     *       102
     *     ],
     *     "shopEmployeesNumber": 103,
     *     "designer": 172,
     *     "shopEmployeesImage": [
     *       109,
     *       115
     *     ],
     *     "welcomeGuest": 116,
     *     "productionIntroduce": 119,
     *     "smileHello": 123,
     *     "violations": [
     *       127,
     *       134
     *     ],
     *     "gender": 135,
     *     "age": 137,
     *     "exhibitVacant": 140,
     *     "newProduction": 174,
     *     "guestInOut": 176,
     *     "guestSnack": [
     *       143,
     *       147
     *     ],
     *     "guestDrink": [
     *       148,
     *       152
     *     ],
     *     "promotionType": [
     *       153,
     *       157
     *     ],
     *     "promotionStyle": [
     *       158,
     *       164
     *     ],
     *     "specialOffer": 165,
     *     "exhibitionId": 1,
     *     "createBy": 3,
     *     "updateBy": null,
     *     "createDate": "2016-08-30 17:26:04",
     *     "updateDate": null,
     *     "queryBeginDate": null,
     *     "queryEndDate": null,
     *     "createByName": "郭嘉"
     *   }
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id) throws Exception{

        return new ResponseEntity<ExhibitionSurveyVo>(exhibitionSurveyService.findById(id), HttpStatus.OK);
    }


    /**
     * @apiDescription 展厅问卷新增/修改
     * @api {post} /mobile/exhibition/survey/save /mobile/exhibition/survey/save
     * @apiVersion 2.0.0
     * @apiName save
     * @apiGroup ExhibitionSurvey
     * @apiPermission user
     *
     * @apiParam {Long} brand 品牌
     * @apiParam {Integer} floor 楼层
     * @apiParam {Integer} floorPosition 楼层位置
     * @apiParam {List-Integer} peripheryFacility 周边设施（10米以内）
     * @apiParam {Integer} exhibitionArea 展厅面积
     * @apiParam {List-ExhibitionSurveyPublicShow} publicShowList 公区摆展
     * @apiParam {List-Integer} publicAdType 公区广告类型
     * @apiParam {List-Integer} brandSponsorType 品牌赞助类型
     * @apiParam {List-Integer} hygiene 卫生
     * @apiParam {Integer} lighting 灯光
     * @apiParam {Integer} music 音乐
     * @apiParam {Integer} smell 气味
     * @apiParam {List-Integer} workbenchHygiene 工作台卫生
     * @apiParam {Integer} workbenchImage 工作台形象
     * @apiParam {List-Integer} discussionAreas 洽谈区
     * @apiParam {List-Integer} backgroundWallHygiene 背景墙卫生
     * @apiParam {List-Integer} designAreaHygiene 设计区卫生
     * @apiParam {Integer} designAreaImage 设计区形象
     * @apiParam {List-Integer} brandImagePlace 品牌形象位置
     * @apiParam {List-Integer} salesPromotionMaterials 促销物料
     * @apiParam {Integer} shopEmployeesNumber 营业员数量
     * @apiParam {Integer} designer 有无设计师
     * @apiParam {List-Integer} shopEmployeesImage 人员形象
     * @apiParam {Integer} welcomeGuest 迎送宾客
     * @apiParam {Integer} productionIntroduce 产品介绍
     * @apiParam {Integer} smileHello 微笑问好
     * @apiParam {List-Integer} violations 违规行为
     * @apiParam {Integer} gender 性别
     * @apiParam {Integer} age 年龄
     * @apiParam {Integer} exhibitVacant 展品空置
     * @apiParam {Integer} newProduction 是否有新品
     * @apiParam {Integer} guestInOut 顾客进店情况
     * @apiParam {List-Integer} guestSnack 顾客零食
     * @apiParam {List-Integer} guestDrink 茶水
     * @apiParam {List-Integer} promotionType 促销
     * @apiParam {List-Integer} promotionStyle 促销形式
     * @apiParam {Integer} specialOffer 特价款
     * @apiParam {Long} exhibitionId 展厅ID
     * @apiParam {ExhibitionSurveyPublicShow} publicShowList 公区摆展
     * @apiParam {Integer} publicExhibitionPriceTag 有无价签
     * @apiParam {Integer} publicExhibitionArea 公区摆展面积
     * @apiParam {Integer} publicExhibitionPlace 公区摆展位置
     * @apiParam {Integer} publicExhibitionFloor 楼层
     *
     * @apiParamExample {json} 输入:
     *   {
     *       "exhibitionId":1,
     *       "floor":2,
     *       "age":137,
     *       "designAreaImage":84,
     *       "designer":172,
     *       "exhibitionArea":21,
     *       "exhibitVacant":140,
     *       "floorPosition":3,
     *       "gender":135,
     *       "guestInOut":176,
     *       "lighting":52,
     *       "music":56,
     *       "newProduction":174,
     *       "productionIntroduce":119,
     *       "shopEmployeesNumber":103,
     *       "smell":60,
     *       "smileHello":123,
     *       "specialOffer":165,
     *       "welcomeGuest":116,
     *       "workbenchImage":68,
     *       "backgroundWallHygiene":[76,79],
     *       "brandImagePlace":[88,91],
     *       "brandSponsorType":[43,45],
     *       "designAreaHygiene":[80,83],
     *       "discussionAreas":[72,75],
     *       "guestDrink":[148,152],
     *       "guestSnack":[143,147],
     *       "hygiene":[46,51],
     *       "peripheryFacility":[5,14],
     *       "promotionStyle":[158,164],
     *       "promotionType":[153,157],
     *       "publicAdType":[15,42],
     *       "salesPromotionMaterials":[92,102],
     *       "shopEmployeesImage":[109,115],
     *       "violations":[127,134],
     *       "workbenchHygiene":[67,64],
     *       "publicShowList":[{
     *           "publicExhibitionPriceTag":1,
     *           "publicExhibitionArea":26,
     *           "publicExhibitionFloor":2,
     *           "publicExhibitionPlace":30
     *       },{
     *           "publicExhibitionPriceTag":2,
     *           "publicExhibitionArea":29,
     *           "publicExhibitionFloor":3,
     *           "publicExhibitionPlace":33
     *       }]
     *   }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody ExhibitionSurvey exhibitionSurvey) throws Exception{
        if(exhibitionSurvey.getExhibitionId()==null){
            throwException(BusinessStatus.REQUIRE,"exhibitionId is null!");
        }
        //TODO 品牌选择
        if(exhibitionSurvey.getBrand()==null){
            exhibitionSurvey.setBrand(1);
        }
//        System.out.println(mapper.toJson(exhibitionSurvey));
        exhibitionSurvey.setCreateBy(getCurrentUserId());
        exhibitionSurveyService.save(exhibitionSurvey);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }


    /**
     * @apiDescription 展厅问卷删除
     * @api {get} /mobile/exhibition/survey/delete/:id /mobile/exhibition/survey/delete/:id
     * @apiVersion 2.0.0
     * @apiName delete
     * @apiGroup ExhibitionSurvey
     * @apiPermission user
     * @apiParam {Long} id 展厅问卷id
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/delete/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable Long id){
        exhibitionSurveyService.delete(id);
        return new ResponseEntity<Map<String,String>>(GetSuccMap(), HttpStatus.OK);
    }
}
