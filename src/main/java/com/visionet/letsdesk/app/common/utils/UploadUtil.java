package com.visionet.letsdesk.app.common.utils;

import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.file.FileUtil;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.base.controller.BaseController;

import java.io.File;
import java.util.Date;
import java.util.UUID;

public class UploadUtil {
	public static String PHOTO = "photo";
	public static String VIDEO = "video";
	public static String AUDIO = "audio";
	public static String DOCUMENT = "document";
	public static String DEFAULT = "default";

	/**
	 * path: /home/visionet/sloth/product_affix/cmcc/uploadFile/YYYYMMDD/stream/7efbd59d9741d34f
	 * relativePath: YYYYMMDD/stream/7efbd59d9741d34f
	 * name: 7efbd59d9741d34f
	 * @param sign
	 * @return
	 */
	public static String[] GetCreatePath(String sign){
		String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
		
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000);
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = dateStr + StringPool.FORWARD_SLASH + (sign == null ? DEFAULT : sign) + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name};
	}
	
	/**
	 * path: /home/visionet/sloth/product_affix/cmcc/uploadFile/YYYYMMDD/stream/7efbd59d9741d34f.mp4
	 * relativePath: YYYYMMDD/stream/7efbd59d9741d34f.mp4
	 * name: 7efbd59d9741d34f.mp4
	 * sign: video
	 * type: mp4
	 * realName: xxxx.mp4
	 * @param sign
	 * @return
	 */
	public static String[] GetCreatePathWithSuffix(String realName,String sign) throws Exception{
		String type = realName.substring(realName.lastIndexOf(".") + 1);
		sign = sign == null ? GetSignByType(type) : sign;
		String suffix = "";
		if(!type.isEmpty()){
			suffix = StringPool.PERIOD + type;
		}
		String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
		
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000) + suffix;
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = dateStr + StringPool.FORWARD_SLASH + (sign == null ? DEFAULT : sign) + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name,sign,type,realName};
	}
	
	public static String[] GetCreateTempPath(){
		String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH);
		
		String name = UUID.randomUUID().toString() + "-" + (int)(Math.random() * 10000);
		
		String dateStr = DateUtil.convertToString(new Date(), DateUtil.YMD1);
		
		String relativePath = "temp" + StringPool.FORWARD_SLASH + dateStr + StringPool.FORWARD_SLASH + name;
		
		path = path + StringPool.FORWARD_SLASH + relativePath;
		
		FileUtil.mkdirs((new File(path)).getParent());
		
		return new String[]{path,relativePath,name};
	}
	
	public static String GetSignByName(String fileName){
        if(!fileName.contains(".")){
            BaseController.throwException(BusinessStatus.ACCESSDENIED, MessageSourceHelper.GetMessages("app.interceptor.MobileInterceptor.filetype.check") + " null");
        }
		String type = fileName.substring(fileName.lastIndexOf(".") + 1);
		return GetSignByType(type);
	}
	
	public static String GetSignByType(String type){
		if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_DOCUMENT).contains(type.toLowerCase())) {
			return DOCUMENT;
		} else if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_IMG).contains(type.toLowerCase())) {
			return PHOTO;
		} else if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_VIDEO).contains(type.toLowerCase())) {
			return VIDEO;
		} else if (PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_TYPE_AUDIO).contains(type.toLowerCase())) {
			return AUDIO;
		}else{
			return DEFAULT;
		}		
	}
	
	public static String GetDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH);
	}
	
	public static String GetAPKDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_DOWNLOAD_ANDRIOD);
	}
	
	public static String GetIOSDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_DOWNLOAD_IOS);
	}
	
	public static String GetSMSIOSDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.SMS_DOWNLOAD_MOBILE_CLIENT_IOS);
	}
    public static String GetSMSAndroidDownloadPath(){
        return PropsUtil.getProperty(PropsKeys.SMS_DOWNLOAD_MOBILE_CLIENT_ANDROID);
    }

    public static String GetQrcodeMinPath(){
        return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_MIN);
    }
    public static String GetQrcodeMaxPath(){
        return PropsUtil.getProperty(PropsKeys.MOBILE_QRCODE_MAX);
    }

	
	public static String getDomainDownloadPath(){
		return PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN)+
				PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH);
	}
}
