package com.visionet.letsdesk.app.foundation.service;

import com.visionet.letsdesk.app.common.utils.UploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.net.URLDecoder;

@Service
public class FileUploadService {

    public static String[] Upload(MultipartFile file) throws Exception{
        String fileName = URLDecoder.decode(file.getOriginalFilename(), "UTF-8");

        String[] tempPathArr = UploadUtil.GetCreatePathWithSuffix(fileName, null);
        File file1 = new File(tempPathArr[0]);
        file.transferTo(file1);

        return tempPathArr;
    }

}
