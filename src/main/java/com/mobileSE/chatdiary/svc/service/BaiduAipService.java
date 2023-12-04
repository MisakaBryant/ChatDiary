package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import org.springframework.web.multipart.MultipartFile;

public interface BaiduAipService {
    public String getImgDescription(MultipartFile image);

}
