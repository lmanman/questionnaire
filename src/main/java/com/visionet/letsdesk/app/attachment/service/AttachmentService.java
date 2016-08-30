package com.visionet.letsdesk.app.attachment.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.visionet.letsdesk.app.attachment.entity.Audio;
import com.visionet.letsdesk.app.attachment.entity.Photo;
import com.visionet.letsdesk.app.attachment.entity.Video;
import com.visionet.letsdesk.app.attachment.repository.AudioDao;
import com.visionet.letsdesk.app.attachment.repository.PhotoDao;
import com.visionet.letsdesk.app.attachment.repository.VideoDao;
import com.visionet.letsdesk.app.base.service.BaseService;
import com.visionet.letsdesk.app.common.constant.BusinessStatus;
import com.visionet.letsdesk.app.common.constant.MobileKey;
import com.visionet.letsdesk.app.common.file.FileUtil;
import com.visionet.letsdesk.app.common.modules.ImageUtil;
import com.visionet.letsdesk.app.common.modules.props.PropsKeys;
import com.visionet.letsdesk.app.common.modules.props.PropsUtil;
import com.visionet.letsdesk.app.common.modules.time.DateUtil;
import com.visionet.letsdesk.app.common.modules.validate.Validator;
import com.visionet.letsdesk.app.common.utils.UploadUtil;
import com.visionet.letsdesk.app.foundation.service.FileUploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

@Service
public class AttachmentService extends BaseService{
    private static Logger log = LoggerFactory.getLogger(AttachmentService.class);

    @Autowired
    private AudioDao audioDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private VideoDao videoDao;


    public List<Map<String,Object>> upload(Map<String, MultipartFile> fileMap,Long refId,Map<String,String> fieldMap) throws Exception{
        List<Map<String, Object>> resultList = Lists.newArrayList();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
//            String[] filePath = FileUploadService.Upload();
            MultipartFile file = entity.getValue();
            String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");
            String[] filePath = UploadUtil.GetCreatePathWithSuffix(fileName, null);
            File file1 = new File(filePath[0]);
            file.transferTo(file1);
            String refType = fieldMap.get(filePath[5]);
            if(Validator.isNull(refType)){
                Map<String,Object> map = Maps.newHashMap();
                map.put(MobileKey.CODE, BusinessStatus.REQUIRE);
                map.put(MobileKey.MSG, "fieldMap.key not exist:"+filePath[5]);
                resultList.add(map);
                continue;
            }

            if(filePath[0].equals(UploadUtil.PHOTO)) {
                Photo photo = new Photo();
                photo.setFileType(filePath[4]);
                photo.setFileUrl(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH + filePath[1]));
                photo.setRealName(filePath[5]);
                photo.setRefId(refId);
                photo.setRefType(refType);
                photo.setSize(file.getSize());

                try{
                    int minWidth = Integer.parseInt(PropsUtil.getProperty(PropsKeys.UPLOAD_IMG_SIZE_MIN_WIDTH));
                    ImageUtil.losslessCut(file1.getAbsolutePath(), file1.getParent() + File.separatorChar + FileUtil.appendMark(file1.getName(), "-min"), minWidth);
                    photo.setMinUrl(FileUtil.appendMark(photo.getFileUrl(),"-min"));
                }catch (Exception e){
                    log.error("ImageUtil.losslessCut:{}",e.toString());
                }

                Long id = this.save(photo);

                Map<String,Object> map = Maps.newHashMap();
                map.put(MobileKey.CODE, BusinessStatus.OK);
                map.put("url", photo.getFileUrl());
                map.put("type", filePath[3]);
                map.put("id",id);
                resultList.add(map);

            }else if(filePath[0].equals(UploadUtil.VIDEO)){
                Video video = new Video();
                video.setFileType(filePath[4]);
                video.setFileUrl(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH + filePath[1]));
                video.setRealName(filePath[5]);
                video.setRefId(refId);
                video.setRefType(refType);
                video.setSize(file.getSize());
                Long id = this.save(video);

                Map<String,Object> map = Maps.newHashMap();
                map.put(MobileKey.CODE, BusinessStatus.OK);
                map.put("url", video.getFileUrl());
                map.put("type", filePath[3]);
                map.put("id",id);
                resultList.add(map);
            }else if(filePath[0].equals(UploadUtil.AUDIO)){
                Audio audio = new Audio();
                audio.setFileType(filePath[4]);
                audio.setFileUrl(PropsUtil.getProperty(PropsKeys.UPLOAD_FILE_DOWNLOAD_PATH + filePath[1]));
                audio.setRealName(filePath[5]);
                audio.setRefId(refId);
                audio.setRefType(refType);
                audio.setSize(file.getSize());
                Long id = this.save(audio);

                Map<String,Object> map = Maps.newHashMap();
                map.put(MobileKey.CODE, BusinessStatus.OK);
                map.put("url", audio.getFileUrl());
                map.put("type", filePath[3]);
                map.put("id",id);
                resultList.add(map);
            }


        }

        return resultList;
    }

    @Transactional(readOnly = false)
    public Long save(Audio audio){
        audio.setCreateDate(DateUtil.getCurrentDate());
        audioDao.save(audio);
        return audio.getId();
    }
    @Transactional(readOnly = false)
    public Long save(Video video){
        video.setCreateDate(DateUtil.getCurrentDate());
        videoDao.save(video);
        return video.getId();
    }
    @Transactional(readOnly = false)
    public Long save(Photo photo){
        photo.setCreateDate(DateUtil.getCurrentDate());
        photoDao.save(photo);
        return photo.getId();
    }



}
