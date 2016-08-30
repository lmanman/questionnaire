package com.visionet.letsdesk.app.common.modules.time;

import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static Log log = LogFactory.getLog(DateUtil.class);
	
	public static final String YMD1 = "yyyy-MM-dd";
    public static final String YMD2 = "dd/MM/yy";
    public static final String YMD3 = "yyyyMMdd";
    public static final String YMD4 = "ddMMyyyy";
    public static final String YMD5 = "yyyy-MM";
    public static final String YMD6 = "HH";
    public static final String YMD7 = "yyyy-MM-dd HH:mm";
    public static final String YMD8 = "yyyy";
    public static final String YMD_FULL = "yyyy-MM-dd HH:mm:ss";
    public static final String NUMBER_INT = "###";
    public static final String NUMBER_FLOAT = "####.##";
    
    
    /**
	* Convert date type from String to Date
	* @param date Sample:2005-10-12
	* @return Date
	*/
    public static String convertToString(Date date,String format) {
    	if(date==null) return "";
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	return sdf.format(date);
    }
    
    public static String convertToString(Date date) {
		return convertToString(date,YMD_FULL);
	}
    
    public static Date seekDate(Date date,int dayNum) {
		return  new Date(date.getTime() + (((long)dayNum) * 86400000));
	}
    
    public static Date trimDate(Date date) {
    	Calendar rightNow = Calendar.getInstance();
    	rightNow.setTime(date);
    	
		rightNow.set(Calendar.HOUR_OF_DAY, 0);
		rightNow.set(Calendar.MINUTE, 0);
		rightNow.set(Calendar.SECOND, 0);
        rightNow.set(Calendar.MILLISECOND, 0);
        
    	return  rightNow.getTime();
	}

    public  static int getDayOfMonth(){
        Calendar rightNow = Calendar.getInstance();
        return rightNow.get(Calendar.DAY_OF_MONTH);
    }
    
    public static Date getCurrentDate() {
    	return new Date();
    }
    
    public static int getAge(String birth,String format) {
    	if(birth == null){
    		return 0;
    	}
    	try {
    		return getAge(convertFromString(birth,format));
		} catch (Exception e) {
			log.error(e.toString(),e);
			return 0;
		}
    	
    }
    public static int getAge(Date birth) {
    	if(birth == null){
    		return 0;
    	}
    	Calendar rightNow = Calendar.getInstance();
    	int nowYear = rightNow.get(Calendar.YEAR);
    	rightNow.setTime(birth);
    	int birthYear = rightNow.get(Calendar.YEAR);
    	
    	return nowYear - birthYear;
    	
    }
    
    public static DateFormat getCnDateFormat(String pattern){
		return new SimpleDateFormat(pattern);
	}
    
    public static Date convertFromString(String date,String format) throws Exception{
		if(date==null) return null;
		if(format == null) format = YMD_FULL;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.parse(date);
	}
    
    public static Date convertFromString(String date) throws Exception{
    	return convertFromString(date, null);
    }
    public static Date convertDate(String date) throws Exception{
    	return convertFromString(date, YMD1);
    }
    public static Date convertDateMutip(String date) throws Exception{
    	if(null == date) return null;
    	if(date.length() > 10){
    		return convertFromString(date);
    	}else{
    		return convertDate(date);
    	}
    }

    /**
     * 根据 timestamp 生成各类时间状态串
     *
     * @param timestamp 距1970 00:00:00 GMT的秒数
     * @return 时间状态串(如：刚刚5分钟前)
     */
    public static String getTimeState(Long timestamp) {

        try {
            long _timestamp = timestamp ;
            if (System.currentTimeMillis() - _timestamp < 1 * 60 * 1000) {
                return MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.just");
            } else if (System.currentTimeMillis() - _timestamp < 30 * 60 * 1000) {
                return ((System.currentTimeMillis() - _timestamp) / 1000 / 60)
                        + MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.minute.ago");
            } else {
                Calendar now = Calendar.getInstance();
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(_timestamp);
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.today")
                                    + " HH:mm");
                    return sdf.format(c.getTime());
                }
                if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)
                        && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
                        && c.get(Calendar.DATE) == now.get(Calendar.DATE) - 1) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.yesterday")
                                    + " HH:mm");
                    return sdf.format(c.getTime());
                } else if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.today.md")
                                    + " HH:mm:ss");
                    return sdf.format(c.getTime());
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(
                            MessageSourceHelper.GetMessages("app.common.utils.DateShowUtil.today.ymd")
                                    + " HH:mm:ss");
                    return sdf.format(c.getTime());
                }
            }
        } catch (Exception e) {
            log.error(e.toString(),e);
            return "";
        }
    }



	public static void main(String[] args) {
		
//		Calendar rightNow = Calendar.getInstance();
//		rightNow.set(Calendar.HOUR_OF_DAY, 0);
//		rightNow.set(Calendar.MINUTE, 0);
//		rightNow.set(Calendar.SECOND, 0);
//		
//		Date feedTimeBegin = rightNow.getTime();
//		
//		rightNow.add(Calendar.DATE, 1);
//		Date feedTimeEnd = rightNow.getTime();
//		
//		System.out.println(feedTimeBegin);
//		System.out.println(feedTimeEnd);
		
		try {
			System.out.println(getAge("1980-10-10",YMD1));
		} catch (Exception e) {
			log.error(e.toString(),e);
		}
		
		
	}
	

}
