package com.visionet.letsdesk.app.exhibition.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.dictionary.vo.ExhibitionVo;
import com.visionet.letsdesk.app.exhibition.entity.Exhibition;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/exhibition/store")
public class ExhibitionInfoController extends BaseController{

    @Autowired
    private ExhibitionInfoService exhibitionInfoService;

    /**
     * @apiDescription 展厅查询
     * @api {post} /mobile/exhibition/store/search /mobile/exhibition/store/search
     * @apiVersion 2.0.0
     * @apiName search
     * @apiGroup Exhibition
     * @apiPermission user
     *
     * @apiParam {String} name 展厅名称(模糊查询)
     * @apiParam {String} address 展厅地址
     * @apiParam {Dealer} dealer 经销商
     * @apiParam {BrandVo} dealer 品牌
     * @apiParam {Market} dealer 商场
     * @apiParam {City} dealer 城市
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "pageInfo":{
     *       "pageNumber":1,
     *       "pageSize":10
     *    },
     *    "name":"古北店"
     *   }     
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 展厅名称
     * @apiSuccess {String} address 展厅地址
     * @apiSuccess {Long} dealerId 经销商ID
     * @apiSuccess {Long} brandId 品牌ID
     * @apiSuccess {Long} marketId 商场ID
     * @apiSuccess {Long} cityId 城市ID
     *
     * @apiSuccessExample {json} Page<Manufacturer>
     *   {
     *     "content": [
     *       {
     *         "id": 1,
     *         "pageInfo": null,
     *         "name": "顾家沙发古北店",
     *         "address": "上海市普陀区真北路1108号",
     *         "dealer": {
     *           "id": 1,
     *           "name": "经销商A",
     *           "linkman": "周晟",
     *           "telephone": "18761556212",
     *           "email": "zhousheng@visionet.com.cn"
     *         },
     *         "brandVo": {
     *           "id": 17,
     *           "pageInfo": null,
     *           "name": "顾家沙发",
     *           "manufacturer": {
     *             "id": 2,
     *             "name": "家具商B",
     *             "address": "上海市普陀区真北路1108号",
     *             "telephone": "51099516",
     *             "linkman": "李四"
     *           },
     *           "type": 2,
     *           "parentId": 16,
     *           "categoryMain": {
     *             "id": 1,
     *             "level": 1,
     *             "name": "家具",
     *             "parentId": 0
     *           },
     *           "categoryFunction": {
     *             "id": 5,
     *             "level": 2,
     *             "name": "沙发",
     *             "parentId": 1
     *           },
     *           "categoryMaterial": {
     *             "id": 15,
     *             "level": 3,
     *             "name": "皮质",
     *             "parentId": 1
     *           },
     *           "categoryStyle": {
     *             "id": 23,
     *             "level": 4,
     *             "name": "现代",
     *             "parentId": 1
     *           },
     *           "categoryImport": {
     *             "id": 31,
     *             "level": 5,
     *             "name": "非进口",
     *             "parentId": 4
     *           }
     *         },
     *         "market": {
     *           "id": 1,
     *           "name": "红星美凯龙汶水店",
     *           "address": "汶水路1555号（沪太路1801号）",
     *           "cityId": 36
     *         },
     *         "city": {
     *           "id": 36,
     *           "cityName": "上海",
     *           "telephoneAreaCode": "021",
     *           "provinceId": "3",
     *           "provinceName": "上海市"
     *         },
     *         "createDate": 1472523372000
     *       }
     *     ],
     *     "last": true,
     *     "totalElements": 1,
     *     "totalPages": 1,
     *     "firstPage": true,
     *     "lastPage": true,
     *     "size": 10,
     *     "number": 0,
     *     "sort": [
     *       {
     *         "direction": "DESC",
     *         "property": "id",
     *         "ignoreCase": false,
     *         "nullHandling": "NATIVE",
     *         "ascending": false
     *       }
     *     ],
     *     "numberOfElements": 1,
     *     "first": true
     *   }
     */
    @RequestMapping(value ="/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> search(@RequestBody ExhibitionVo vo) throws Exception {

        Page<ExhibitionVo> page = exhibitionInfoService.search(vo);

        return new ResponseEntity<Page<ExhibitionVo>>(page, HttpStatus.OK);
    }



    /**
     * @apiDescription 展厅详情
     * @api {get} /mobile/exhibition/store/:id /mobile/exhibition/store/:id
     * @apiVersion 2.0.0
     * @apiName detail
     * @apiGroup Exhibition
     * @apiPermission user
     * @apiParam {Long} id 展厅id
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 展厅名称
     * @apiSuccess {String} address 展厅地址
     * @apiSuccess {Dealer} dealer 经销商
     * @apiSuccess {BrandVo} dealer 品牌
     * @apiSuccess {Market} dealer 商场
     * @apiSuccess {City} dealer 城市
     *
     * @apiSuccessExample {json} ExhibitionVo
     *   {
     *     "id": 1,
     *     "pageInfo": null,
     *     "name": "顾家沙发古北店",
     *     "address": "上海市普陀区真北路1108号",
     *     "dealer": {
     *       "id": 1,
     *       "name": "经销商A",
     *       "linkman": "周晟",
     *       "telephone": "18761556212",
     *       "email": "zhousheng@visionet.com.cn"
     *     },
     *     "brandVo": {
     *       "id": 17,
     *       "pageInfo": null,
     *       "name": "顾家沙发",
     *       "manufacturer": {
     *         "id": 2,
     *         "name": "家具商B",
     *         "address": "上海市普陀区真北路1108号",
     *         "telephone": "51099516",
     *         "linkman": "李四"
     *       },
     *       "type": 2,
     *       "parentId": 16,
     *       "categoryMain": {
     *         "id": 1,
     *         "level": 1,
     *         "name": "家具",
     *         "parentId": 0
     *       },
     *       "categoryFunction": {
     *         "id": 5,
     *         "level": 2,
     *         "name": "沙发",
     *         "parentId": 1
     *       },
     *       "categoryMaterial": {
     *         "id": 15,
     *         "level": 3,
     *         "name": "皮质",
     *         "parentId": 1
     *       },
     *       "categoryStyle": {
     *         "id": 23,
     *         "level": 4,
     *         "name": "现代",
     *         "parentId": 1
     *       },
     *       "categoryImport": {
     *         "id": 31,
     *         "level": 5,
     *         "name": "非进口",
     *         "parentId": 4
     *       }
     *     },
     *     "market": {
     *       "id": 1,
     *       "name": "红星美凯龙汶水店",
     *       "address": "汶水路1555号（沪太路1801号）",
     *       "cityId": 36
     *     },
     *     "city": {
     *       "id": 36,
     *       "cityName": "上海",
     *       "telephoneAreaCode": "021",
     *       "provinceId": "3",
     *       "provinceName": "上海市"
     *     },
     *     "createDate": 1472523372000
     *   }
     */
    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id){

        return new ResponseEntity<ExhibitionVo>(exhibitionInfoService.findVoById(id), HttpStatus.OK);
    }


    /**
     * @apiDescription 展厅新增修改
     * @api {post} /mobile/exhibition/store/save /mobile/exhibition/store/save
     * @apiVersion 2.0.0
     * @apiName save
     * @apiGroup Exhibition
     * @apiPermission user
     *
     * @apiParam {Long} id 有ID为修改，没有为新增
     * @apiParam {String} name 展厅名称
     * @apiParam {String} address 展厅地址
     * @apiParam {Long} dealerId 经销商ID
     * @apiParam {Long} brandId 品牌ID
     * @apiParam {Long} marketId 商场ID
     * @apiParam {Long} cityId 城市ID
     *
     *
     * @apiParamExample {json} 输入:
     *   {
     *       "name":"慕斯古北店",
     *       "address":"上海市普陀区真北路1108号",
     *       "dealerId":2,
     *       "brandId":8,
     *       "marketId":1,
     *       "cityId":36
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
    public ResponseEntity<?> save(@RequestBody Exhibition exhibition) throws Exception{
        exhibitionInfoService.save(exhibition);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }



}
