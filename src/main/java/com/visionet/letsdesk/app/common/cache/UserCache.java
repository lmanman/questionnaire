package com.visionet.letsdesk.app.common.cache;

import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.user.entity.User;

/**
 * 用户问题缓存数据结构
 * @author xt
 *
 */
public class UserCache {

    /**
     * 获取客服坐席信息
     * @param userId
     * @return
     */
    public static String getUserImgUrl(Long userId){
        User ui = EhcacheUtil.GetUser(userId);
        if(ui == null) return null;
        String url= ui.getAvatar();
        if(Validator.isNull(url)){
            return null;
        }else{
            return url;
        }
    }
    public static String getUserName(Long userId){
        User ui = EhcacheUtil.GetUser(userId);
        if(ui == null) return null;
        return ui.getAliasName();
    }
    public static String getUserLocale(Long userId){
        if(userId == null) return null;
        User ui = EhcacheUtil.GetUser(userId);
        if(ui == null) return null;
        return ui.getLocale();
    }




    /**
     * 获取客户部分信息(昵称、头像)
     * @param customerId
     * @return
     */
//    public static String getCustomerName(Long customerId){
//        CustomerInfo po=GuavaCacheUtil.getCustomer(customerId);
//        if(po==null){
//            return null;
//        }
//        return po.getCustomerName();
//    }
//    public static String getCustomerImgUrl(Long customerId){
//        CustomerInfo po=GuavaCacheUtil.getCustomer(customerId);
//        if(po==null){
//            return null;
//        }
//        return po.getHeadimgurl();
//    }
//    public static String getCustomerOpenId(Long customerId){
//        CustomerInfo po=GuavaCacheUtil.getCustomer(customerId);
//        if(po==null){
//            return null;
//        }
//        return po.getOpenId();
//    }
}
