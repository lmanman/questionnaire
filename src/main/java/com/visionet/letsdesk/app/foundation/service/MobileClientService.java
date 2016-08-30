package com.visionet.letsdesk.app.foundation.service;

import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.modules.persistence.DynamicSpecifications;
import com.visionet.letsdesk.app.common.modules.persistence.SearchFilter;
import com.visionet.letsdesk.app.common.modules.MessageSourceHelper;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.utils.PageInfo;
import com.visionet.letsdesk.app.common.utils.SearchFilterUtil;
import com.visionet.letsdesk.app.foundation.entity.MobileClient;
import com.visionet.letsdesk.app.foundation.repository.MobileClientDao;
import com.visionet.letsdesk.app.foundation.vo.MobileClientVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class MobileClientService extends BaseService {

    private static Logger logger = LoggerFactory.getLogger(MobileClientService.class);

    public static String DomainServer = PropsUtil.getProperty(PropsKeys.SERVICE_DOMAIN).replaceAll("http:", "https:");

    @Autowired
    private MobileClientDao mobileClientDao;

    @Transactional(readOnly = false)
    public void saveClient(MobileClient mobileClient) {
        mobileClientDao.save(mobileClient);
    }


    public Page<MobileClient> searchMobileClient(MobileClientVo vo) throws Exception{
        Map<String, Object> searchParams = SearchFilterUtil.convertBean(vo);
        PageInfo pageInfo = vo.getPageInfo();
        if(pageInfo == null){
            pageInfo = new PageInfo();
        }

        Map<String, SearchFilter> filters = SearchFilterUtil.parse(searchParams);
        Specification<MobileClient> spec = DynamicSpecifications.bySearchFilter(filters.values(), MobileClient.class);

        PageRequest pageRequest = buildPageRequest(pageInfo.getPageNumber(), pageInfo.getPageSize(),pageInfo.getSortColumn());

        return mobileClientDao.findAll(spec, pageRequest);
    }




    public MobileClient getLastVersion(String clientType){
        List<MobileClient> list = mobileClientDao.findByClientType(clientType);
        if(list==null||list.isEmpty()){
            return new MobileClient();
        }

        return list.get(0);
    }




    public static final String plist1 = "<?xml version='1.0' encoding='UTF-8'?>"+
            "<!DOCTYPE plist PUBLIC '-//Apple//DTD PLIST 1.0//EN' 'http://www.apple.com/DTDs/PropertyList-1.0.dtd'>"+
            "<plist version='1.0'>"+
            "<dict>"+
            "<key>items</key>"+
            "<array>"+
            "<dict>"+
            "<key>assets</key>"+
            "<array>"+
            "<dict>"+
            "<key>kind</key>"+
            "<string>software-package</string>"+
            "<key>url</key>"+
            "<string>"+DomainServer+"downloadFile/client/FindestMeetingProj.ipa</string>"+
            "</dict>"+
            "<dict>"+
            "<key>kind</key>"+
            "<string>display-image</string>"+
            "<key>needs-shine</key>"+
            "<false/>"+
            "<key>url</key>"+
            "<string>"+DomainServer+"downloadFile/client/icon_5757.png</string>"+
            "</dict>"+
            "<dict>"+
            "<key>kind</key>"+
            "<string>full-size-image</string>"+
            "<key>needs-shine</key>"+
            "<false/>"+
            "<key>url</key>"+
            "<string>"+DomainServer+"downloadFile/client/icon_512512.png</string>"+
            "</dict>"+
            "</array>"+
            "<key>metadata</key>"+
            "<dict>"+
            "<key>bundle-identifier</key>"+
            "<string>com.anji.taozhihui</string>"+
            "<key>bundle-version</key>"+
            "<string>";

    public static final String plist2="</string>"+
            "<key>kind</key>"+
            "<string>software</string>"+
            "<key>subtitle</key>"+
            "<string>Apple</string>"+
            "<key>title</key>"+
            "<string>"+MessageSourceHelper.GetMessages("common.tzh")+"</string>"+
            "</dict>"+
            "</dict>"+
            "</array>"+
            "</dict>"+
            "</plist>";


    /**
     *
     * @param version 当前版本号
     * @param url https://tzh.anji.com/tzh
     */
    public static void writePlistFile(String version,String url){
        FileOutputStream fileoutputstream = null;
        File file = null;
        try {
            String xmlCode = plist1 + version + plist2;

            String path = PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH)
                    + StringPool.FORWARD_SLASH
                    + PropsUtil.getProperty(PropsKeys.MOBILE_CLIENT_UPLOAD_IOS_PLIST);

            logger.info("writePlistFile path="+path );
            file = new File(path);
            File fileParent = file.getParentFile();
            if (!fileParent.exists()) fileParent.mkdirs();
            fileoutputstream = new FileOutputStream(file);
            fileoutputstream.write(xmlCode.getBytes());
        } catch (IOException ioexception) {
            logger.error("writePlistFile ioexception:", ioexception);
        } catch (Exception ex1) {
            logger.error("writePlistFile error:", ex1);
        } finally {
            try {
                if(fileoutputstream!=null) fileoutputstream.close();
            } catch (IOException ioe) {
                logger.error("writePlistFile close IOException:", ioe);
            }
        }
    }

    public static void main(String[] args) {
//		MobileClientService.writePlistFile("1.1.2", "https://tzh.anji.com/tzh");
        System.out.println(plist1);
    }


}
