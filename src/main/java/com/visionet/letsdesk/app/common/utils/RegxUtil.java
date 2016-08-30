package com.visionet.letsdesk.app.common.utils;

import com.google.common.collect.Lists;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.modules.string.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegxUtil {
    private static Logger logger = LoggerFactory.getLogger(RegxUtil.class);

    public static final String AT_REGX = "\\[@(.*?)\\]";

	public static final String IMG_REGX = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
	
	public static final String IMG_SRC = "src=\"?'?(.*?)(\"|'|>|\\s+)";
	
	public static final String IMG_WIDTH = "data-img-width=\"\\d+\"";
	
	public static final String IMG_HEIGHT = "data-img-heigth=\"\\d+\"";
	
	public static final String IMG_WIDTH_VALUE = "\\d+";
	
	 public static List<String> getImgStr(String htmlStr,int imgNum){
         Pattern p_image;   
         Matcher m_image;   
         List<String> pics = Lists.newArrayList();
         p_image = Pattern.compile 
                 (IMG_REGX,Pattern.CASE_INSENSITIVE);   
        m_image = p_image.matcher(htmlStr);
        int i = 0;
        while(m_image.find()){  
            Matcher m  = Pattern.compile(IMG_SRC).matcher(m_image.group());
             while(m.find()){
                 pics.add(m.group(1));
                 i++;
                 if(imgNum!=0 && i>=imgNum){
                     break;
                 }
             }
             break;
       }   
       return pics;   
     }
    public static List<String> getImgStr(String htmlStr){
        return getImgStr(htmlStr,1);
    }
	 
	public static List<Map<String, String>> getImgWidthAndHeight(String htmlStr){
		Pattern p_image = Pattern.compile 
                (IMG_REGX,Pattern.CASE_INSENSITIVE);
		Matcher m_image = p_image.matcher(htmlStr);
		List<Map<String,String>> list =new ArrayList<Map<String,String>>();
		while(m_image.find()){
			Map<String, String> map = new HashMap<String, String>();
			Matcher width = Pattern.compile(IMG_WIDTH).matcher(m_image.group());
			while(width.find()){
				Matcher widthValue = Pattern.compile(IMG_WIDTH_VALUE).matcher(width.group());
				while(widthValue.find()){
					map.put("width", widthValue.group());
					break;
				}
				break;
			}
			Matcher height = Pattern.compile(IMG_HEIGHT).matcher(m_image.group());
			while(height.find()){
				Matcher heightValue = Pattern.compile(IMG_WIDTH_VALUE).matcher(height.group());
				while(heightValue.find()){
					map.put("height", heightValue.group());
					break;
				}
				break;
			}
			
			list.add(map);
			
			break;
		}
		return list;
	}

    public static List<Long> GetAtMessage(String atStr){
        List<Long> list = Lists.newArrayList();
        try {
            Pattern pattern = Pattern.compile(AT_REGX);
            Matcher matcher = pattern.matcher(atStr);
            while (matcher.find()) {
                String content = matcher.group(1);
                if (!content.contains(StringPool.SPACE)) {
                    continue;
                }

                if (!content.contains(StringPool.SPACE)) {
                    continue;
                }

                String sid = content.substring(content.lastIndexOf(StringPool.SPACE)).trim();
                if (!StringUtil.isNumber(sid)) {
                    continue;
                }
                Long id = Long.parseLong(sid);
                if (list.contains(id)) {
                    continue;
                }
    //                String name = content.substring(0,content.lastIndexOf(StringPool.SPACE)).trim();
//                if(!name.equals(UserInfoService.getAliasName(id))){
//                    continue;
//                }
                list.add(id);
            }
        }catch (Exception e){
            logger.error("GetAtMessage error!",e);
        }
        return list;
    }
    public static String ReplaceAtMessage(String atStr,String url){
        try {
            if(url==null){      //默认跳转url
                url="#/me/0";
            }else {
                url = url.trim();
            }
            if (url.endsWith("{id}")) {
                url = url.substring(0, url.length() - 4);
            }
            url = PropsUtil.getProperty(PropsKeys.NGINX_DOMAIN) + url;

            Pattern pattern = Pattern.compile(AT_REGX);
            Matcher matcher = pattern.matcher(atStr);
            while (matcher.find()) {
                String content = matcher.group(1);
                if (!content.contains(StringPool.SPACE)) {
                    continue;
                }
                String src = "[@" + content + "]";

                if (!content.contains(StringPool.SPACE)) {
                    continue;
                }
                String name = content.substring(0,content.lastIndexOf(StringPool.SPACE)).trim();
                String id = content.substring(content.lastIndexOf(StringPool.SPACE)).trim();
                if (!StringUtil.isNumber(id)) {
                    continue;
                }
                atStr = atStr.replace(src, " <a href=\"" + url + id + "\">@" + name + "</a> ");
            }
        }catch (Exception e){
            logger.error("ReplaceAtMessage error!",e);
        }
        return atStr;
    }


    public static void testAtStr(String htmlStr){
        String regx ="<a\\s.*?href=\"([^\"]+)\"[^>]*>(.*?)</a>";

        Matcher matcher1 = Pattern.compile(regx).matcher(htmlStr);
        while (matcher1.find()) {
            System.out.println(matcher1.group(0));
            System.out.println(matcher1.group(1));
            System.out.println(matcher1.group(2));
        }

    }


	public static void main(String[] args) {

//		String testString = "<p><img src='http://ww4.sinaimg.cn/bmiddle/a.jpg'  data-img-width=\"550\" data-img-heigth=\"275\" style='max-width: 300px'/><p><img src='http://ww4.sinaimg.cn/bmiddle/6a815515tw1eefcqfzhkhj20c80det9r.jpg'  data-img-width=\"1\" data-img-heigth=\"2\"  style='max-width: 300px'/>";
		//regxImg(testString);
//        List<String> list = getImgStr(testString);

        String testString = "aaa[@张三 aa 3][@孙炎 xx 2] [@孙炎 xx 34] fasf [@sfd1] [@刘顺顺 x 3]...";
		List<Long> list = GetAtMessage(testString);
        for(Long xx:list){
            System.out.println(xx);
        }
        System.out.println(ReplaceAtMessage(testString, "#/me/"));
//        System.out.println(testString.replaceAll(AT_REGX, "???"));


//        testAtStr("评论test3 <a href=\"#/me/34\">@孙炎</a> 问题123<a href=\"#/me/3\">@刘顺顺</a>");



	}
}
