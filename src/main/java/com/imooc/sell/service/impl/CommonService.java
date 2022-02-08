package com.imooc.sell.service.impl;

import com.imooc.sell.enums.ResultEnum;
import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.SendSms;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
@Slf4j
public class CommonService {

    public static final Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Value("${uploadPath}")
    private String uploadPath;

    public String uploadFile(MultipartFile file, String openId) {
        if (file == null || file.isEmpty()) {
            logger.info("文件为空");
            throw new SellException(ResultEnum.FILE_EMPTY);
        }
        String fileName = file.getOriginalFilename();
        String fileRandomFix = openId + "_" + SendSms.newCaptcha(6) + "_" + fileName;
        File dest = new File(uploadPath +fileRandomFix);
        try {
            file.transferTo(dest);
            logger.info("上传成功");
            return "www.involute.cn:/image/"+fileRandomFix;
        } catch (IOException e) {
            return "上传失败:"+e.getMessage();
        }
    }
}
