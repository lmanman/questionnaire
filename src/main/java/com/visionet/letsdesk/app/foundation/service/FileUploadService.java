package com.visionet.letsdesk.app.foundation.service;

import com.visionet.letsdesk.app.attachment.vo.AttachmentVo;
import com.visionet.letsdesk.app.base.rest.RestException;
import com.visionet.letsdesk.app.common.file.FileUtil;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.string.StringPool;
import com.visionet.letsdesk.app.common.utils.UploadUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URLDecoder;

@Service
public class FileUploadService {

    private static Logger log = LoggerFactory.getLogger(FileUploadService.class);
    private final static long TEN_MIN_LONG=10 * 60 * 1000;

    public static AttachmentVo Upload(MultipartFile file,Long orgId) throws Exception{
        String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");

        String[] temp = UploadUtil.GetCreatePathWithSuffix(fileName, null,"Org"+orgId);
        if(UploadUtil.DEFAULT.equals(temp[3])){
            throw new RestException("file type illegal!");
        }
        File file1 = new File(temp[0]);
        file.transferTo(file1);

        AttachmentVo imageVo = new AttachmentVo();
        imageVo.setFullPath(temp[0]);
        imageVo.setRelativePath(temp[1]);
        imageVo.setUuidName(temp[2]);
        imageVo.setRealName(temp[5]);

        String type = imageVo.getUuidName().substring(imageVo.getUuidName().lastIndexOf(".") + 1);
        if(UploadUtil.IsImage(type)) {
            String suffix=GeneratePicWidthHgightSuffix(file1);
            if(suffix!=null){
                imageVo.setFullPath(FileUtil.appendMark(imageVo.getFullPath(), suffix));
                imageVo.setRelativePath(FileUtil.appendMark(imageVo.getRelativePath(), suffix));
                imageVo.setUuidName(FileUtil.appendMark(imageVo.getUuidName(), suffix));
                file1.renameTo(new File(imageVo.getFullPath()));
            }
        }
        return imageVo;
    }

    public static String GeneratePicWidthHgightSuffix(File pic){
        int[] size=GeneratePicWidthHgight(pic);
        if(size!=null){
            return StringPool.UNDERLINE + size[0] + StringPool.STAR + size[1];
        }else {
            return null;
        }
    }

    public static int[] GeneratePicWidthHgight(File pic){
        int[] size = null;
        try {
            String path = pic.getPath();
            if(path.startsWith(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH))){
                pic = new File(path.replaceFirst(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH),PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_ROOT_PATH) ));
            }
            if (pic.exists()) {
                BufferedImage bsrc = ImageIO.read(pic);
                size = new int[2];
                size[0] = bsrc.getWidth();
                size[1] = bsrc.getHeight();
            }
        }catch (Exception e){
            log.error("GeneratePicWidthHgight error!,pic:"+pic.getPath(),e.toString());
        }
        return size;
    }

//    public static void EncryptText(Encrypt encrypt) {
//        String md5Key = PropsUtil.getProperty(PropsKeys.MOBILE_TEXT_ENCRYPTION_PRIVATE_KEY);
//        Long timestamp = encrypt.getTimestamp();
//        String accessToken = encrypt.getAccessToken();
//
//        if (Validator.isNull(timestamp) || Validator.isNull(accessToken)) {
//            throwException(BusinessStatus.REQUIRE,"时间戳和密钥不能为空！");
//        }
//        Long timeLong=0l;
//        try {
//            timeLong = Long.valueOf(timestamp);
//        } catch (Exception e) {
//            throwException(BusinessStatus.ILLEGAL,"时间戳格式非法！");
//        }
//
//        long currentTimeMillis = System.currentTimeMillis();
//        if ((currentTimeMillis+60000) < timeLong) {
//            throwException(BusinessStatus.ILLEGAL,"时间戳超前！");
//        }
//
//        Long timeDif=currentTimeMillis-timeLong;
//        if (timeDif > TEN_MIN_LONG) {
//            throwException(BusinessStatus.ILLEGAL,"时间已过期");
//        }
//
//        String md5 = Md5Util32.md5(timestamp + md5Key);
//        if (!accessToken.equals(md5)) {
//            throwException(BusinessStatus.ILLEGAL,"MD5验证错误!");
//        }
//    }

}
