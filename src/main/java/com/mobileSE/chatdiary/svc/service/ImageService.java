package com.mobileSE.chatdiary.svc.service;

import com.mobileSE.chatdiary.pojo.entity.DiaryImageEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public interface ImageService {
    public void uploadDiaryImageByDate(MultipartFile image, Date timestamp);

    public void uploadUserImageByUserId(MultipartFile image, Long userId);

    public String uploadImageByName(MultipartFile image, String filename);

    public List<DiaryImageEntity> getImageByDiaryId(Long id);
}
