package com.mobileSE.chatdiary.svc.service;

import org.springframework.web.multipart.MultipartFile;

public interface BaiduAipService {
    public String getImgDescription(MultipartFile image);

}
