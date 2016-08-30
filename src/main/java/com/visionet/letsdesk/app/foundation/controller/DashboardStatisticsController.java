package com.visionet.letsdesk.app.foundation.controller;


import com.visionet.letsdesk.app.base.controller.BaseController;
import com.visionet.letsdesk.app.common.cache.EhcacheUtil;
import com.visionet.letsdesk.app.common.utils.BeanConvertMap;
import com.visionet.letsdesk.app.foundation.service.DashboardStatisticsService;
import com.visionet.letsdesk.app.user.service.SessionManageService;
import com.visionet.letsdesk.app.user.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value = "/mobile/dashboard")
public class DashboardStatisticsController extends BaseController{

    @Autowired
    private SessionManageService sessionManageService;
    @Autowired
    private DashboardStatisticsService dashboardStatisticsService;

    /**
     * 客户当前会话统计
     * @return
     */
    @RequestMapping(value = "/customer/talking",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> customerTalking() {

        return new ResponseEntity<ConcurrentHashMap<Long, Long>>(DashboardStatisticsService.GetCustomerTalkingId(), HttpStatus.OK);
    }
    /**
     * 客服当前会话统计
     * @return
     */
    @RequestMapping(value = "/kefu/talking",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> kefuTalking() {
        List<Long> onlineList = sessionManageService.getActiveUserId(getCurrentOrgId(),null);

        List<UserVo> voList =
                onlineList.stream().map(id -> BeanConvertMap.map(EhcacheUtil.GetUser(id), UserVo.class))
                .map(user -> {
                    user.setTalkingNum(DashboardStatisticsService.GetKefuTalkingNum(user.getId()));
                    return user;
                }).collect(Collectors.toList());

        return new ResponseEntity<List<UserVo>>(voList, HttpStatus.OK);
    }


    /**
     * 客户排队信息
     * @param channelId 0:全部
     * @return
     */
    @RequestMapping(value = "/customer/queue/{channelId}",method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> customerQueueNum(@PathVariable Long channelId) {
        List<DashboardStatisticsService.WaitingEntity> list;
        if(channelId==0) {
            list = dashboardStatisticsService.GetCustomerWaitingList(getCurrentOrgId());
        }else {
            list = dashboardStatisticsService.GetCustomerWaitingList(getCurrentOrgId(),channelId);
        }
        return new ResponseEntity<List<DashboardStatisticsService.WaitingEntity>>(list, HttpStatus.OK);
    }
}
