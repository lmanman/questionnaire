package com.visionet.letsdesk.app.dictionary.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.dictionary.entity.Brand;
import com.visionet.letsdesk.app.dictionary.entity.Category;
import com.visionet.letsdesk.app.dictionary.entity.Manufacturer;
import com.visionet.letsdesk.app.dictionary.service.DictionaryService;
import com.visionet.letsdesk.app.dictionary.vo.BrandVo;
import com.visionet.letsdesk.app.dictionary.vo.ManufacturerVo;
import com.visionet.letsdesk.app.dictionary.vo.SundryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/dictionary")
public class DictionaryController extends BaseController{

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * @apiDescription 品牌查询
     * @api {post} /mobile/dictionary/brand/search /mobile/dictionary/brand/search
     * @apiVersion 2.0.0
     * @apiName searchBrand
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiParam {String} name 品牌名称(模糊查询)
     * @apiParam {Manufacturer} manufacturer 厂商
     * @apiParam {Integer} type 品牌(1:主品牌;2:子品牌;3:品牌系列)
     * @apiParam {Long} parentId 上级品牌ID
     * @apiParam {Category} categoryMain 品类（一级）
     * @apiParam {Category} categoryFunction 品类（二级）功能
     * @apiParam {Category} categoryMaterial 品类（二级）材质
     * @apiParam {Category} categoryStyle 品类（二级）风格
     * @apiParam {Category} categoryImport 品类（二级）是否进口
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "pageInfo":{
     *       "pageNumber":1,
     *       "pageSize":10
     *    },
     *    "name":"慕斯",
     *    "type":1,
     *    "manufacturer":{"id":1},
     *    "categoryMain":{"id":1},
     *    "categoryFunction":{"id":7},
     *    "categoryMaterial":{"id":22},
     *    "categoryStyle":{"id":24},
     *    "categoryImport":{"id":30}
     *   }
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 品牌名称
     * @apiSuccess {Manufacturer} manufacturer 厂商
     * @apiSuccess {Integer} type 品牌(1:主品牌;2:子品牌;3:品牌系列)
     * @apiSuccess {Long} parentId 上级品牌ID
     * @apiSuccess {Category} categoryMain 品类（一级）
     * @apiSuccess {Category} categoryFunction 品类（二级）功能
     * @apiSuccess {Category} categoryMaterial 品类（二级）材质
     * @apiSuccess {Category} categoryStyle 品类（二级）风格
     * @apiSuccess {Category} categoryImport 品类（二级）是否进口
     *
     * @apiSuccessExample {json} Page<BrandVo>
     *   {
     *     "content": [
     *       {
     *         "id": 15,
     *         "pageInfo": null,
     *         "name": "慕斯.爱迪奇",
     *         "manufacturer": {
     *           "id": 1,
     *           "name": "床垫厂商A",
     *           "address": "上海市普陀区真北路1108号",
     *           "telephone": "51099515",
     *           "linkman": "张三"
     *         },
     *         "type": 3,
     *         "parentId": 8,
     *         "categoryMain": {
     *           "id": 1,
     *           "level": 1,
     *           "name": "家具",
     *           "parentId": 0
     *         },
     *         "categoryFunction": {
     *           "id": 7,
     *           "level": 2,
     *           "name": "家具",
     *           "parentId": 1
     *         },
     *         "categoryMaterial": {
     *           "id": 22,
     *           "level": 3,
     *           "name": "其它",
     *           "parentId": 1
     *         },
     *         "categoryStyle": {
     *           "id": 24,
     *           "level": 4,
     *           "name": "欧美",
     *           "parentId": 1
     *         },
     *         "categoryImport": {
     *           "id": 30,
     *           "level": 5,
     *           "name": "纯进口",
     *           "parentId": 4
     *         }
     *       }
     *     ],
     *     "last": true,
     *     "totalElements": 1,
     *     "firstPage": true,
     *     "totalPages": 1,
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
     *     "first": true,
     *     "numberOfElements": 1
     *   }
     */
    @RequestMapping(value ="/brand/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchBrand(@RequestBody BrandVo vo) throws Exception {

        Page<BrandVo> page = dictionaryService.searchBrand(vo);

        return new ResponseEntity<Page<BrandVo>>(page, HttpStatus.OK);
    }


    /**
     * @apiDescription 品牌详情
     * @api {get} /mobile/dictionary/brand/:id /mobile/dictionary/brand/:id
     * @apiVersion 2.0.0
     * @apiName bindDetail
     * @apiGroup Dictionary
     * @apiPermission user
     * @apiParam {Long} id 品牌id
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 品牌名称
     * @apiSuccess {Manufacturer} manufacturer 厂商
     * @apiSuccess {Integer} type 品牌(1:主品牌;2:子品牌;3:品牌系列)
     * @apiSuccess {Long} parentId 上级品牌ID
     * @apiSuccess {Category} categoryMain 品类（一级）
     * @apiSuccess {Category} categoryFunction 品类（二级）功能
     * @apiSuccess {Category} categoryMaterial 品类（二级）材质
     * @apiSuccess {Category} categoryStyle 品类（二级）风格
     * @apiSuccess {Category} categoryImport 品类（二级）是否进口
     *
     * @apiSuccessExample {json} BrandVo
     *   {
     *     "id": 15,
     *     "pageInfo": null,
     *     "name": "慕斯.爱迪奇",
     *     "manufacturer": {
     *       "id": 1,
     *       "name": "床垫厂商A",
     *       "address": "上海市普陀区真北路1108号",
     *       "telephone": "51099515",
     *       "linkman": "张三"
     *     },
     *     "type": 3,
     *     "parentId": 8,
     *     "categoryMain": {
     *       "id": 1,
     *       "level": 1,
     *       "name": "家具",
     *       "parentId": 0
     *     },
     *     "categoryFunction": {
     *       "id": 7,
     *       "level": 2,
     *       "name": "家具",
     *       "parentId": 1
     *     },
     *     "categoryMaterial": {
     *       "id": 22,
     *       "level": 3,
     *       "name": "其它",
     *       "parentId": 1
     *     },
     *     "categoryStyle": {
     *       "id": 24,
     *       "level": 4,
     *       "name": "欧美",
     *       "parentId": 1
     *     },
     *     "categoryImport": {
     *       "id": 30,
     *       "level": 5,
     *       "name": "纯进口",
     *       "parentId": 4
     *     }
     *   }
     */
    @RequestMapping(value = "/brand/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> brandDetail(@PathVariable Long id){

        return new ResponseEntity<BrandVo>(dictionaryService.findBrandVoById(id), HttpStatus.OK);
    }


    /**
     * @apiDescription 品牌新增修改
     * @api {post} /mobile/dictionary/brand/save /mobile/dictionary/brand/save
     * @apiVersion 2.0.0
     * @apiName brandSave
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiParam {Long} id 有ID为修改，没有为新增
     * @apiParam {String} name 品牌名称
     * @apiParam {Long} manufacturerId 厂商ID
     * @apiParam {Integer} type 品牌(1:主品牌;2:子品牌;3:品牌系列)
     * @apiParam {Long} parentId 上级品牌ID
     * @apiParam {Long} categoryMainId 品类（一级）ID
     * @apiParam {Long} categoryFunctionId 品类（二级）功能ID
     * @apiParam {Long} categoryMaterialId 品类（二级）材质ID
     * @apiParam {Long} categoryStyleId 品类（二级）风格ID
     * @apiParam {Long} categoryImportId 品类（二级）是否进口ID
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "id":1,
     *     "name": "顾家沙发",
     *     "type":2,
     *     "parentId":16,
     *     "manufacturerId": 2,
     *     "categoryMainId": 1,
     *     "categoryFunctionId": 5,
     *     "categoryMaterialId": 15,
     *     "categoryStyleId": 23,
     *     "categoryImportId": 31
     *   }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/brand/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> brandSave(@RequestBody Brand brand) throws Exception{
        dictionaryService.saveBrand(brand);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }

    /**
     * @apiDescription 品类list
     * @api {get} /mobile/dictionary/category/list /mobile/dictionary/category/list
     * @apiVersion 2.0.0
     * @apiName categoryList
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 品类名称
     * @apiSuccess {Integer} level 品类等级
     * @apiSuccess {Long} parentId 上级品类
     *
     * @apiSuccessExample {json} Category
     *   [
     *     {
     *       "id": 1,
     *       "level": 1,
     *       "name": "家具",
     *       "parentId": 0
     *     },
     *     {
     *       "id": 2,
     *       "level": 1,
     *       "name": "建材",
     *       "parentId": 0
     *     },
     *     {
     *       "id": 5,
     *       "level": 2,
     *       "name": "沙发",
     *       "parentId": 1
     *     },
     *     {
     *       "id": 6,
     *       "level": 2,
     *       "name": "床垫",
     *       "parentId": 1
     *     },
     *     {
     *       "id": 15,
     *       "level": 3,
     *       "name": "皮质",
     *       "parentId": 1
     *     },
     *     {
     *       "id": 16,
     *       "level": 3,
     *       "name": "布艺",
     *       "parentId": 1
     *     },
     *     {
     *       "id": 26,
     *       "level": 4,
     *       "name": "中式",
     *       "parentId": 1
     *     },
     *     {
     *       "id": 30,
     *       "level": 5,
     *       "name": "纯进口",
     *       "parentId": 4
     *     }
     *   ]
     */
    @RequestMapping(value = "/category/list", method= RequestMethod.GET)
    public ResponseEntity<?> categoryList(){

        return new ResponseEntity<List<Category>>(dictionaryService.findCategoryList(), HttpStatus.OK);
    }


    /**
     * @apiDescription 品类新增修改
     * @api {post} /mobile/dictionary/category/save /mobile/dictionary/category/save
     * @apiVersion 2.0.0
     * @apiName categorySave
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiParam {Long} id 有ID为修改，没有为新增
     * @apiParam {String} name 品类名称
     * @apiParam {Integer} level 品类等级
     * @apiParam {Long} parentId 上级品类
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "id": 5,
     *     "level": 2,
     *     "name": "沙发",
     *     "parentId": 1
     *   }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/category/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> categorySave(@RequestBody Category category) throws Exception{
        dictionaryService.saveCategory(category);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }


    /**
     * @apiDescription 厂商查询
     * @api {post} /mobile/dictionary/manufacturer/search /mobile/dictionary/manufacturer/search
     * @apiVersion 2.0.0
     * @apiName searchManufacturer
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiParam {String} name 厂商名称(模糊查询)
     * @apiParam {String} address 厂商地址
     * @apiParam {String} telephone 联系电话
     * @apiParam {String} linkman 联系人
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "pageInfo":{
     *       "pageNumber":1,
     *       "pageSize":10
     *    },
     *    "name":"厂商",
     *    "address": "上海市普陀区真北路1108号",
     *    "telephone": "51099516",
     *    "linkman": "李四"
     *   }
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 厂商名称
     * @apiSuccess {String} address 厂商地址
     * @apiSuccess {String} telephone 联系电话
     * @apiSuccess {String} linkman 联系人
     *
     * @apiSuccessExample {json} Page<Manufacturer>
     *   {
     *     "content": [
     *       {
     *         "id": 2,
     *         "name": "家具商B",
     *         "address": "上海市普陀区真北路1108号",
     *         "telephone": "51099516",
     *         "linkman": "李四"
     *       },
     *       {
     *         "id": 1,
     *         "name": "床垫厂商A",
     *         "address": "上海市普陀区真北路1108号",
     *         "telephone": "51099515",
     *         "linkman": "张三"
     *       }
     *     ],
     *     "last": true,
     *     "totalElements": 2,
     *     "firstPage": true,
     *     "totalPages": 1,
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
     *     "first": true,
     *     "numberOfElements": 2
     *   }
     */
    @RequestMapping(value ="/manufacturer/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> searchManufacturer(@RequestBody ManufacturerVo vo) throws Exception {
        Page<Manufacturer> page = dictionaryService.searchManufacturer(vo);

        return new ResponseEntity<Page<Manufacturer>>(page, HttpStatus.OK);
    }


    /**
     * @apiDescription 厂商新增修改
     * @api {post} /mobile/dictionary/manufacturer/save /mobile/dictionary/manufacturer/save
     * @apiVersion 2.0.0
     * @apiName manufacturerSave
     * @apiGroup Dictionary
     * @apiPermission user
     *
     * @apiParam {Long} id 有ID为修改，没有为新增
     * @apiParam {String} name 厂商名称
     * @apiParam {String} address 厂商地址
     * @apiParam {String} telephone 联系电话
     * @apiParam {String} linkman 联系人
     *
     *
     * @apiParamExample {json} 输入:
     *   {
     *       "name":"床单商A",
     *       "address":"上海市普陀区真北路1108号",
     *       "telephone":"51099515",
     *       "linkman":"张三"
     *   }
     *
     * @apiSuccess {String} code 成功标志
     * @apiSuccessExample {json} Map<String,String>
     *  {
     *     "code": "10000"
     *  }
     */
    @RequestMapping(value = "/manufacturer/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> manufacturerSave(@RequestBody Manufacturer manufacturer) throws Exception{
        dictionaryService.saveManufacturer(manufacturer);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }

    @RequestMapping(value = "/collect/map", method= RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> typeMap(@RequestBody List<String> typeList){

        return new ResponseEntity<Map<String,List<SundryVo>>>(dictionaryService.findDictionaryMap(typeList), HttpStatus.OK);
    }


}
