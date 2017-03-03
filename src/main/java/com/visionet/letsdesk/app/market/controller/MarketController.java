package com.visionet.letsdesk.app.market.controller;

import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.market.entity.Market;
import com.visionet.letsdesk.app.market.service.MarketService;
import com.visionet.letsdesk.app.market.vo.MarketVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping(value = "/mobile/market")
public class MarketController extends BaseController{

    @Autowired
    private MarketService marketService;

    @RequestMapping(value ="/search", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> search(@RequestBody MarketVo vo) throws Exception {

        Page<MarketVo> page = marketService.searchMarker(vo);

        return new ResponseEntity<Page<MarketVo>>(page, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method= RequestMethod.GET)
    public ResponseEntity<?> detail(@PathVariable Long id){

        return new ResponseEntity<Market>(marketService.findMarkerById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<?> save(@RequestBody Market market) throws Exception{
        marketService.saveMarker(market);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        marketService.deleteMarket(id);
        return new ResponseEntity<Map<String,String>>(GetSuccMap() , HttpStatus.OK);
    }
}
