package com.visionet.letsdesk.app.foundation;

public interface KeyWord {
	
	
	//删除状态
	public static int DEL_STATUS = 1;
	//非删除状态
	public static int UN_DEL_STATUS = 0;

    public static final String YES = "Y";
    public static final String NO = "N";

    public static final int Y = 1;
    public static final int N = 0;

    /*------------------ Brand -----------------------*/

    public static int BRAND_TYPE_MAIN=1;		//主品牌
    public static int BRAND_TYPE_SUB=2;		    //子品牌
    public static int BRAND_TYPE_SERIES=3;		//品牌系列

    /*------------------ Category -----------------------*/
    public static String CATEGORY_TYPE_FUNCTION="function";		//功能
    public static String CATEGORY_TYPE_MATERIAL="material";	    //材质
    public static String CATEGORY_TYPE_STYLE="style";		    //风格
    public static String CATEGORY_TYPE_import="import";		    //是否进口

    /*------------------ SurveyField -----------------------*/

    public static String SURVEY_FIELD_TABLE_BRAND="Brand";
    public static String SURVEY_FIELD_TABLE_SURVEY="ExhibitionSurvey";


    public static String FIELD_FORMAT_CHECKBOX="checkbox";
    public static String FIELD_FORMAT_RADIO="radio";
    public static String FIELD_FORMAT_TEXTAREA="textarea";

    /*------------------ Tag -----------------------*/


    public static String TAG_TYPE_MESSAGE="M";		//消息标签
    public static String TAG_TYPE_FAQ="F";		    //FAQ标签
    public static String TAG_TYPE_WORKORDER="W";	//工单标签
    public static String TAG_TYPE_CUSTOMER="C"; 	//客户标签
    public static String TAG_TYPE_UPDATE="U";		//特殊更新标签

    /*------------------ SystemDict -----------------------*/



    /*------------------ Attachment -----------------------*/
    public static final String ATTACHMENT_TYPE_SERVICE_FORM="S";

    public static final String ATTACHMENT_MEDIA_TYPE_FILE="file";
    public static final String ATTACHMENT_MEDIA_TYPE_AUDIO="audio";



    /*------------------ Monitor -----------------------*/
    public static String KEFU_TYPE_ONLINE="online";                //在线
    public static String KEFU_TYPE_OFFLINE="offline";              //离线
    public static String KEFU_TYPE_MOBILE="mobile";                //手机客服

    /*------------------ 自定义表单 ---------------------*/
    public static final String SURVEY_TYPE_TEMP = "A";            //草稿
    public static final String SURVEY_TYPE_PUBLISH = "B";         //发布
    public static final String SURVEY_TYPE_CLOSE = "C";           //结束

    public static final String SURVEY_TYPE_OTHERS = "OTHERS";           //结束


    /*------------------ User -----------------------*/

    //性别（男）
	public static final int GENDER_MALE = 1;
	//性别(女)
	public static final int GENDER_FAMALE = 0;

	//男头像id
	public static final Long GENDER_MALE_IMAGE_ID = 2l;
	//女头像 id
	public static final Long GENDER_FEMALE_IMAGE_ID = 1l;



    /*------------------ Customer -----------------------*/

    /*------------------ Others -----------------------*/
    public static String OPERATE_TYPE_LOGIN="login";
    public static String OPERATE_TYPE_LOGOUT="logout";




}
