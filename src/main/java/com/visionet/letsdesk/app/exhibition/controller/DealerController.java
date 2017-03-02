package com.visionet.letsdesk.app.exhibition.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.exhibition.entity.Dealer;
import com.visionet.letsdesk.app.exhibition.service.ExhibitionInfoService;
import com.visionet.letsdesk.app.exhibition.vo.DealerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/dealer")
public class DealerController extends BaseController{

    @Autowired
    private ExhibitionInfoService exhibitionInfoService;

    /**
     * @apiDescription 经销商查询
     * @api {post} /mobile/dealer/search /mobile/dealer/search
     * @apiVersion 2.0.0
     * @apiName search
     * @apiGroup Dealer
     * @apiPermission user
     *
     * @apiParam {String} name 经销商名称(模糊查询)
     * @apiParam {String} email 经销商邮箱
     * @apiParam {String} telephone 联系电话
     * @apiParam {String} linkman 联系人
     *
     * @apiParamExample {json} 输入:
     *   {
     *     "pageInfo":{
     *       "pageNumber":1,
     *       "pageSize":10
     *    },
     *    "name":"经销"
     *   }
     *
     * @apiSuccess {Long} id PK
     * @apiSuccess {String} name 经销商名称
     * @apiSuccess {String} email 经销商邮箱
     * @apiSuccess {String} telephone 联系电话
     * @apiSuccess {String} linkman 联系人
     *
     * @apiSuccessExample {json} Page<Dealer>
     *   {
     *     "content": [
     *       {
     *         "id": 2,
     *         "name": "经销商B",
     *         "linkman": "XXX",
     *         "telephone": "187xxxxxxxx",
     *         "email": "xxx@visionet.com.cn"
     *       },
     *       {
     *         "id": 1,
     *         "name": "经销商A",
     *         "linkman": "周晟",
     *         "telephone": "18761556212",
     *         "email": "zhousheng@visionet.com.cn"
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
    @RequestMapping(value ="/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> search(@RequestBody DealerVo vo) throws Exception {

        Page<Dealer> page = exhibitionInfoService.searchDealer(vo);

        return new ResponseEntity<Page<Dealer>>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id){

        return new ResponseEntity<Dealer>(exhibitionInfoService.findDealerById(id), HttpStatus.OK);
    }


    /**
     * @apiDescription 经销商新增修改
     * @api {post} /mobile/dealer/save /mobile/dealer/save
     * @apiVersion 2.0.0
     * @apiName save
     * @apiGroup Dealer
     * @apiPermission user
     *
     * @apiParam {Long} id 有ID为修改，没有为新增
     * @apiParam {String} name 经销商名称
     * @apiParam {String} email 经销商邮箱
     * @apiParam {String} telephone 联系电话
     * @apiParam {String} linkman 联系人
     *
     *
     * @apiParamExample {json} 输入:
     *   {
     *       "name":"经销商B",
     *       "linkman":"XXX",
     *       "telephone":"187xxxxxxxx",
     *       "email":"xxx@visionet.com.cn"
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
    public ResponseEntity<?> save(@RequestBody Dealer dealer) throws Exception{
        exhibitionInfoService.saveDealer(dealer);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        exhibitionInfoService.deleteDealer(id);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }

}
